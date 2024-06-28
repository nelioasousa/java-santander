package com.example;

public class App {
    public static void main(String[] args) {
        String connectionString = "mongodb+srv://database.example.com";
        System.out.println("PING SAMPLE CODE:");
        MongoClientConnectionExample.run(connectionString);
        System.out.println("\n");
        System.out.println("SIMPLE BSON QUERYING:");
        QuickStart.run(connectionString);
        System.out.println("\n");
        System.out.println("BSON TO POJO AUTOMATIC DECODING:");
        QuickStartPojoExample.run(connectionString);
    }
}
