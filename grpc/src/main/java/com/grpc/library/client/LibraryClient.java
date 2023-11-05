package com.grpc.library.client;

import com.grpc.library.LibraryServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.List;

import static com.grpc.library.Library.*;

public class LibraryClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8000)
                .usePlaintext()
                .build();

        LibraryServiceGrpc.LibraryServiceBlockingStub stub = LibraryServiceGrpc.newBlockingStub(channel);

        // Add a book
        Book newBook = Book.newBuilder()
                .setId("2")
                .setTitle("Sample Book 2")
                .setAuthor("John2")
                .build();

        Book addedBook = stub.addBook(AddBookRequest.newBuilder().setBook(newBook).build());
        System.out.println("Added Book: " + addedBook);

        // Get a book
        try {
            GetBookResponse response = stub.getBook(GetBookRequest.newBuilder().setBookId("1").build());
            Book foundBook = response.getBook();
            System.out.println("Found Book: " + foundBook);
        } catch (Exception e) {
            System.out.println("Book not found.");
        }

        //Delete a book
        try{
            stub.deleteBook(DeleteBookRequest.newBuilder().setBookId("1").build());
        } catch (Exception e){
            System.out.println("Book not found.");
        }

        // Get all book
        try {
            GetAllBooksResponse response = stub.getAllBooks(Empty.newBuilder().build());
            List<Book> foundBooks = response.getBooksList();
            System.out.println("All Books: " + foundBooks);
        } catch (Exception e) {
            System.out.println("Book not found.");
        }

        channel.shutdown();
    }
}


