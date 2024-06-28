package com.example;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Code from https://www.mongodb.com/docs/drivers/java/sync/current/quick-start/
 */
public class QuickStart {
    public static void run(String connectionString) {
        /**
         * Instantiation of a `MongoClient` with default settings (see
         * `MongoClientSettings`) and driver informations (see
         * `MongoDriverInformation`).
         */
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            /**
             * A `MongoDatabase` is a database connection?
             * Or all instances talk back to `MongoClient` asking for an availa-
             * ble connection from the connection pool?
             */
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> collection = database.getCollection("movies");
            
            /**
             * Not much to talk. Using the database is the easy part. Database
             * management, deployment, configuration, and secure connection are
             * the hard ones.
             */
            Document doc = collection.find(eq("title", "Back to the Future")).first();
            if (doc != null) {
                System.out.println(doc.toJson());
            } else {
                System.out.println("No matching documents found.");
            }
        }
    }
}
