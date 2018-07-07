package edu.rylynn.netdata.dao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class MongoHandler implements MailDao{
    private MongoClient mongoClient;
    private MongoDatabase db;
    private MongoCollection<Document> collection;

    public MongoHandler(String host, int port) {
        mongoClient = new MongoClient(host, port);
        db = mongoClient.getDatabase("netdata");
        collection = db.getCollection("enron");

    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    @Override
    public void insertMail(Map email) {
        try {
            collection.insertOne(new Document(email));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
