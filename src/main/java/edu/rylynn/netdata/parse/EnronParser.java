package edu.rylynn.netdata.parse;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import edu.rylynn.netdata.dao.MongoHandler;
import edu.rylynn.netdata.util.FileUtil;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class EnronParser {
    private List<String> filenames;

    private MongoDatabase db;

    public EnronParser(String filename) {
        filenames = new ArrayList<>();
        filenames.add(filename);
        db = new MongoHandler("localhost", 27017).getMongoClient().getDatabase("netdata");
    }

    public EnronParser(String[] filenames) {
        this.filenames = new ArrayList<>();
        Collections.addAll(this.filenames, filenames);
        db = new MongoHandler("localhost", 27017).getMongoClient().getDatabase("netdata");
    }

    public EnronParser(List<String> filenames) {
        this.filenames = filenames;
        db = new MongoHandler("localhost", 27017).getMongoClient().getDatabase("netdata");
    }

    public static void main(String[] args) {
        System.out.println("Begin to read file....");
        List<String> filenames = FileUtil.getAllFile("../../../maildir");
        System.out.println(filenames);
        System.out.println("All file read...");
        try {
            new EnronParser(filenames).parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<String> getFilenames() {
        return filenames;
    }

    public void setFilenames(List<String> filenames) {
        this.filenames = filenames;
    }

    public void parse() throws FileNotFoundException {
        MongoCollection<Document> collection = db.getCollection("enron");
        int i = 0;
        for (String file : filenames) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuffer sb = new StringBuffer();
            try {
                String line = br.readLine();
                while (line != null) {
                    sb.append(line);
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String content = sb.toString();
            //delete the redundant char
            String from;
            String to;
            String subject;
            String messageId;
            try {
                messageId = content.split("Message-ID:")[1].split("Date")[0].replace("\n","")
                        .replace(" ","");
                from = content.split("From: ")[1].split("To")[0].replace("\n", "")
                        .replace(" ", "").replace("\t", "");
                to = content.split("To: ")[1].split("Subject")[0].replace("\n", "")
                        .replace(" ", "").replace("\t", "");

                if (content.contains("Cc: ")) {
                    subject = content.split("Subject: ")[1].split("Cc: ")[0].replace("\n", "")
                            .replace(" ", "").replace("\t", "");
                } else {
                    subject = content.split("Subject: ")[1].split("Mime-Version")[0].replace("\n", "")
                            .replace(" ", "").replace("\t", "");
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
                continue;
            }
            List<String> senders = new ArrayList<>();
            List<String> recievers = new ArrayList<>();
            Collections.addAll(senders, from.split(","));
            Collections.addAll(recievers, to.split(","));

            for (String sender : senders) {
                for (String reciever : recievers) {
                    if (collection.find(Filters.eq("MessageId", messageId)).first() == null) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("Source", sender);
                        map.put("Target", reciever);
                        map.put("MessageId", messageId);
                        map.put("Weight", 1);
                        collection.insertOne(new Document(map));
                        System.out.println(i++ + " record is inserted....");
                    } else {
                        System.out.println(messageId+" exist...");
                        continue;
                    }

                }
            }
        }
    }
}
