package ba.unsa.etf.rpr.domain;

import java.util.Objects;

public class Score {

    private int id;
    private double score;
    private Book book;
    private User user;

    public Score() {
    }

    public Score(int id, double score, Book book, User user) {
        this.id = id;
        this.score = score;
        this.book = book;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score1 = (Score) o;
        return id == score1.id && Double.compare(score1.score, score) == 0 && Objects.equals(book, score1.book) && Objects.equals(user, score1.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, score, book, user);
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", score=" + score +
                ", book=" + book +
                ", user=" + user +
                '}';
    }
}
