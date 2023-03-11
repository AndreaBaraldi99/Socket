package es3;

public class Book {

    private String title;

    public Book(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public boolean equals(Object obj){
        if(obj == null) return false;
        if(obj == this) return true;
        if(!(obj instanceof Book)) return false;
        Book book = (Book) obj;
        return book.getTitle().equals(this.title);
    }

    public int hashCode(){
        return title.hashCode();
    }
}
