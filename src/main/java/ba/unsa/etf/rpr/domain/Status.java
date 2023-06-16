package ba.unsa.etf.rpr.domain;

import java.util.Objects;

public class Status {

    private int id;
    private String status;
    private double score;
    private User user;
    private Book book;

    public Status(){}

    public Status(int id, String status, double score, User user, Book book) {
        this.id = id;
        this.status = status;
        this.score = score;
        this.user = user;
        this.book = book;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status1 = (Status) o;
        return id == status1.id && Double.compare(status1.score, score) == 0 && Objects.equals(status, status1.status) && Objects.equals(user, status1.user) && Objects.equals(book, status1.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, score, user, book);
    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", score=" + score +
                ", user=" + user +
                ", book=" + book +
                '}';
    }
}


