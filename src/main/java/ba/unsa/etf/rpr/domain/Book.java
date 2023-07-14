package ba.unsa.etf.rpr.domain;

import java.util.Objects;

public class Book implements Idable{

    private int id;
    private String name;
    private long UIN;
    private Genre genre;

    private Author author;

    public Book() {
    }

    public Book(int id, String name, long UIN, Genre genre, Author author) {
        this.id = id;
        this.name = name;
        this.UIN = UIN;
        this.genre = genre;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUIN() {
        return UIN;
    }

    public void setUIN(long UIN) {
        this.UIN = UIN;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && UIN == book.UIN && Objects.equals(name, book.name) && Objects.equals(genre, book.genre) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, UIN, genre, author);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", UIN=" + UIN +
                ", genre=" + genre +
                ", author=" + author +
                '}';
    }
}
