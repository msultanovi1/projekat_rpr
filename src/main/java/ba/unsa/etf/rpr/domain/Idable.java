package ba.unsa.etf.rpr.domain;

/**
 * Idable is an interface that has to be implemented be every class
 * that represents an entity in the Data Base
 */
public interface Idable {

    void setId(int id);

    int getId();
}
