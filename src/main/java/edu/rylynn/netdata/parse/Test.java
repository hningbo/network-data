package edu.rylynn.netdata.parse;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import edu.rylynn.netdata.dao.MongoHandler;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

public class Test {
    public static void main(String[] args) {
        MongoHandler handler = new MongoHandler("localhost", 27017);
        MongoClient client = handler.getMongoClient();
        MongoCollection<Document> documents = client.getDatabase("netdata").getCollection("enron");
        System.out.println(documents.find(Filters.eq("Target","ginger.sinclair@enron.com")).first());
    }
}
