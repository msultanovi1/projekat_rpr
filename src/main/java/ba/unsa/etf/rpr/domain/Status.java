package ba.unsa.etf.rpr.domain;

import java.util.Objects;

public class Status {

    private int id;
    private String status;
    private double score;
    private int idUser;
    private int idBook;

    public Status(){}

    public Status(int id, String status, double score, int idUser, int idBook) {
        this.id = id;
        this.status = status;
        this.score = score;
        this.idUser = idUser;
        this.idBook = idBook;
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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status1 = (Status) o;
        return id == status1.id && Double.compare(status1.score, score) == 0 && idUser == status1.idUser && idBook == status1.idBook && Objects.equals(status, status1.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, score, idUser, idBook);
    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", score=" + score +
                ", idUser=" + idUser +
                ", idBook=" + idBook +
                '}';
    }
}


