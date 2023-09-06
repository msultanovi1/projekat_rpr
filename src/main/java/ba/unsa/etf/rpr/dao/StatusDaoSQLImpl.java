package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Status;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.MyBookListException;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents the implementation of the StatusDao interface using MySQL
 * It contains all the methods that are needed to work with the Status table in the database
 */
public class StatusDaoSQLImpl extends AbstractDao<Status> implements StatusDao{


    private static StatusDaoSQLImpl instance = null;

    private StatusDaoSQLImpl(){
        super("Status");
    }

    /**
     * This method represents the implementation of the Singleton Design Pattern in this application
     * @return an instance of this class
     */
    public static StatusDaoSQLImpl getInstance() {
        if(instance == null) instance = new StatusDaoSQLImpl();
        return instance;
    }

    public static void removeInstance(){
        if(instance!=null)
            instance = null;
    }

    @Override
    public Status rowToObject(ResultSet rs) throws MyBookListException {
        Status status = new Status();
        try {
            status.setId(rs.getInt("id"));
            status.setStatus(rs.getString("status"));
            status.setScore(rs.getLong("score"));
            status.setUser(DaoFactory.userDao().getById(rs.getInt("idUser")));
            status.setBook(DaoFactory.bookDao().getById(rs.getInt("idBook")));
        }
        catch (SQLException error) {
            throw new MyBookListException(error.getMessage(), error);
        }
        return status;
    }

    @Override
    public Map<String, Object> objectToRow(Status status) {
        Map<String, Object> tableRow = new LinkedHashMap<>();
        tableRow.put("id", status.getId());
        tableRow.put("status", status.getStatus());
        tableRow.put("score", status.getScore());
        tableRow.put("idUser", status.getUser().getId());
        tableRow.put("idBook", status.getBook().getId());
        return tableRow;
    }


    @Override
    public List<Status> searchByUser(User user) {
        try {
            return super.executeQuery("SELECT * FROM Status WHERE idUser = ?", new Object[]{user.getId()});
        } catch (MyBookListException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Status> searchByBook(Book book) {
        try {
            return super.executeQuery("SELECT * FROM Status WHERE idBook = ?", new Object[]{book.getId()});
        } catch (MyBookListException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Status searchByUserAndBook(User user, Book book) {
        try {
            return super.executeQueryUnique("SELECT * FROM Status WHERE idUser = ? AND idBook = ?", new Object[]{user.getId(), book.getId()});
        } catch (MyBookListException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Status searchById(int id) {
        try {
            return super.executeQueryUnique("SELECT * FROM Status WHERE id = ?", new Object[]{id});
        } catch (MyBookListException e) {
            throw new RuntimeException();
        }
    }


}
