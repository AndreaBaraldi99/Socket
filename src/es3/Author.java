package es3;

import java.util.HashSet;
import java.util.UUID;

public class Author{

    private String name;
    private String id;
    private HashSet<Book> books;

    public Author(String name){
        this.name = name;
        this.id = UUID.randomUUID().toString();
        books = new HashSet<Book>();
    }

    public String getName(){
        return name;
    }

    public String getId(){
        return id;
    }

    public boolean addBook(String title){
        return books.add(new Book(title));
    }

    public HashSet<Book> getBooks(){
        return books;
    }

    public String toString(){
        return "Author: " + name + " ID: " + id;
    }

    public boolean equals(Object obj){
        if(obj == null) return false;
        if(obj == this) return true;
        if(!(obj instanceof Author)) return false;
        Author author = (Author) obj;
        return author.getName().equals(this.name);
    }

    public int hashCode(){
        return name.hashCode();
    }
}