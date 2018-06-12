package edu.rylynn.netdata.dao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class MongoHandler {
    private MongoClient mongoClient;
    private MongoDatabase db;
    private MongoCollection<Document> collection;

    public MongoHandler(String host, int port) {
        mongoClient = new MongoClient(host, port);
        db = mongoClient.getDatabase("netdata");
        collection = db.getCollection("mail");

    }

    public void insertOne(Map<String, Object> email) {
        try {
            collection.insertOne(new Document(email));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
