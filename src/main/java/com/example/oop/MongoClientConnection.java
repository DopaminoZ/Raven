package com.example.oop;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class MongoClientConnection {
    private static String connectionString = "mongodb+srv://alibagoury:Gold12121$@socialmedia.rhtgxdv.mongodb.net/?retryWrites=true&w=majority&appName=Socialmedia";
    private static MongoClient client = MongoClients.create(connectionString);
    private static MongoDatabase db = client.getDatabase("accountData");
    static MongoCollection col = db.getCollection("accountCollection");
    public static void writeData(Document x){
        col.insertOne(x);
    }

}
