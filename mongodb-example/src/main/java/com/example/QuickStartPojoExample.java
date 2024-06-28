package com.example;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Code from https://www.mongodb.com/docs/drivers/java/sync/current/quick-start/
 * 
 * More datails about the used API in:
 * >>> https://www.mongodb.com/docs/drivers/java/sync/current/fundamentals/data-formats/document-data-format-pojo/
 */
public class QuickStartPojoExample {

    public static void run(String connectionString) {
        /**
         * My undestanding is that `CodecProvider` specifies the rules to encode
         * and decode between the POJO and the BSONs documents. The codecs in
         * `getDefaultCodecRegistry()` are the ones that perform the default en-
         * code/decode processes and the `pojoCodecProvider` is just needed to
         * tell `database` and `collection` which POJOs/tables must be decoded,
         * in this case, any, since `automatic(true)` was specified.
         */
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            /**
             * I think all of this is very complicated, but with a cookbook to
             * make it *appear simple* instead of *being simple*. I can reprodu-
             * ce this elsewhere, but if someone asks me what I'm doing, I
             * couldn't quite explain.
             */
            MongoDatabase database = mongoClient.getDatabase("sample_mflix").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Movie> collection = database.getCollection("movies", Movie.class);

            Movie movie = collection.find(eq("title", "Back to the Future")).first();
            System.out.println(movie);
        }
    }
}
