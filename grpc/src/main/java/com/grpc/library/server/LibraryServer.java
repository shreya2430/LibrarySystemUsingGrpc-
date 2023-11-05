package com.grpc.library.server;

import com.grpc.library.LibraryServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.grpc.library.Library.*;

public class LibraryServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8000)
                .addService(new LibraryServiceImpl())
                .build();

        System.out.println("Starting server...");
        server.start();
        System.out.println("Server started!");

        server.awaitTermination();
    }

    static class LibraryServiceImpl extends LibraryServiceGrpc.LibraryServiceImplBase {
        private Map<String, Book> books = new HashMap<>();

        @Override
        public void getBook(GetBookRequest request, StreamObserver<GetBookResponse> responseObserver) {
            String bookId = request.getBookId();
            if (books.containsKey(bookId)) {
                Book book = books.get(bookId);
                GetBookResponse response = GetBookResponse.newBuilder().setBook(book).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            } else {
                responseObserver.onError(Status.NOT_FOUND.withDescription("Book not found").asException());
            }
        }

        @Override
        public void deleteBook(DeleteBookRequest request, StreamObserver<Empty> responseObserver){
            String bookId = request.getBookId();
            if(books.containsKey(bookId)){
                books.remove(bookId);
                responseObserver.onNext(Empty.newBuilder().build());
                responseObserver.onCompleted();
            }
        }

        @Override
        public void getAllBooks(Empty request, StreamObserver<GetAllBooksResponse> responseObserver) {
            if (!books.isEmpty()) {
                List<Book> book = new ArrayList<Book>();
                for (Book b : books.values()) {
                    book.add(b);
                }
                GetAllBooksResponse response = GetAllBooksResponse.newBuilder().addAllBooks(book).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            } else {
                responseObserver.onError(Status.NOT_FOUND.withDescription("Book not found").asException());
            }
        }

        @Override
        public void addBook(AddBookRequest request, StreamObserver<Book> responseObserver) {
            Book newBook = request.getBook();
            books.put(newBook.getId(), newBook);
            responseObserver.onNext(newBook);
            responseObserver.onCompleted();
        }

    }
}


