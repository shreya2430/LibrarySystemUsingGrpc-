# Library System with gRPC

This project is a simple library system built with gRPC, which includes a server to manage a collection of books and a client to interact with the library. The library supports basic operations such as adding, retrieving, deleting, and listing all books.

## Project Structure

- **Server** (`LibraryServer`): Manages book data and serves gRPC requests.
- **Client** (`LibraryClient`): Communicates with the server to perform library operations.
- **Protocol Buffers** (`library.proto`): Defines the gRPC service and message types.

## Features

- **Add a Book**: Add a new book to the library.
- **Get a Book**: Retrieve details of a book by its ID.
- **Delete a Book**: Remove a book by its ID.
- **Get All Books**: Retrieve a list of all books in the library.

## Prerequisites

- **Java 11+**
- **gRPC** and **Protocol Buffers**
- **Maven** (for dependency management)

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/shreya2430/LibrarySystemUsingGrpc-.git
cd grpc-library-system
```
### 2. Install Dependencies
Use Maven to install the required dependencies:
```bash
mvn clean install
```

### 3. Start the Server
To start the server, open a terminal and run:
```bash
java -cp target/grpc-1.0-SNAPSHOT.jar com.grpc.library.server.LibraryServer
```
This command initializes the library server, which will listen for incoming client requests. Keep this terminal open as the server needs to run in the background for client interactions.

### 4. Run the Client
In a new terminal window, you can run the client to perform library operations. Use the following command:
```bash
java -cp target/grpc-1.0-SNAPSHOT.jar com.grpc.library.client.LibraryClient
```

Usage
Below are examples of how to use the client for various operations:

- ### **Add a Book**
```java
Book newBook = Book.newBuilder()
    .setId("2")
    .setTitle("Sample Book 2")
    .setAuthor("John Doe")
    .build();

stub.addBook(AddBookRequest.newBuilder().setBook(newBook).build());
```
Expected Output:
```
Book added: Sample Book 2 by John Doe
```
- ### **Get a Book**
```java
try {
    GetBookResponse response = stub.getBook(GetBookRequest.newBuilder().setBookId("1").build());
    System.out.println("Found Book: " + response.getBook());
} catch (Exception e) {
    System.out.println("Book not found.");
}
```
Expected Output:
```
Found Book: Sample Book 1 by Jane Smith
```
- ### **Delete a Book**
```java
try {
    stub.deleteBook(DeleteBookRequest.newBuilder().setBookId("1").build());
} catch (Exception e) {
    System.out.println("Book not found.");
}
```
Expected Output:
```
Book deleted: Sample Book 1
```
- ### **Get All Books**
```java
try {
    GetAllBooksResponse response = stub.getAllBooks(Empty.newBuilder().build());
    System.out.println("All Books: " + response.getBooksList());
} catch (Exception e) {
    System.out.println("No books available.");
}
```
Expected Output:
```
All Books: [Sample Book 2 by John Doe, Sample Book 3 by Alice Johnson]
```
### Troubleshooting
Common Issues
Server Connection Error: If the client cannot connect to the server, ensure that the server is running and listening on the correct port (default is 8000).
Dependency Issues: If you encounter missing dependency errors, try running mvn clean install again to ensure all dependencies are downloaded.

Happy Coding!
