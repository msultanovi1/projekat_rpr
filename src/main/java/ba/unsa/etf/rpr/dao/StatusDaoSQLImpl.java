package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Status;
import ba.unsa.etf.rpr.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatusDaoSQLImpl implements StatusDao{

    private Connection connection;

    private static StatusDaoSQLImpl instance = null;

    public StatusDaoSQLImpl(){
        try{
            this.connection = DataBaseDao.getInstance();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static StatusDaoSQLImpl getInstance() {
        if(instance == null) instance = new StatusDaoSQLImpl();
        return instance;
    }

    public static void removeInstance(){
        if(instance!=null)
            instance = null;
    }

    @Override
    public Status getById(int id) {
        String query = "SELECT * FROM Status WHERE id = ?";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Status status = new Status();
                status.setId(rs.getInt("id"));
                status.setStatus(rs.getString("status"));
                status.setScore(rs.getDouble("score"));
                status.setUser(new UserDaoSQLImpl().getById(rs.getInt("idUser")));
                status.setBook(new BookDaoSQLImpl().getById(rs.getInt("idBook")));
                rs.close();
                return status;
            }else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Status add(Status status) {
        try{
            PreparedStatement stmt = this.connection.prepareStatement("INSERT INTO Status(status, score, idUser, idBook) VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, status.getStatus());
            stmt.setDouble(2,status.getScore());
            stmt.setInt(3, status.getUser().getId());
            stmt.setInt(4, status.getBook().getId());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            status.setId(rs.getInt(1));
            return status;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Status update(Status status) {
        try{
            PreparedStatement stmt = this.connection.prepareStatement("UPDATE Status SET status = ?, score = ?, idUser  = ?, idBook = ? WHERE id = ?");
            stmt.setInt(5, status.getId());
            stmt.setString(1, status.getStatus());
            stmt.setDouble(2, status.getScore());
            stmt.setInt(3, status.getUser().getId());
            stmt.setInt(4, status.getBook().getId());
            stmt.executeUpdate();
            return status;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(int id) {
        try{
            PreparedStatement stmt = this.connection.prepareStatement("DELETE from Status WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Status> getAll() {
        List<Status> statuses = new ArrayList<>();
        try{
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * from Status");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Status status = new Status();
                status.setId(rs.getInt("id"));
                status.setStatus(rs.getString("status"));
                status.setScore(rs.getDouble("score"));
                status.setUser(new UserDaoSQLImpl().getById(rs.getInt("idUser")));
                status.setBook(new BookDaoSQLImpl().getById(rs.getInt("idBook")));
                statuses.add(status);
            }
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return statuses;
    }

    @Override
    public List<Book> searchByUserAndStatus(String status, User user) {
        List<Book> books = new ArrayList<>();
        try{
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM Status WHERE status = ? AND idUser = ?");
            stmt.setString(1, status);
            stmt.setInt(2, user.getId());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setUIN(rs.getLong("UIN"));
                book.setGenre(new GenreDaoSQLImpl().getById(rs.getInt("idGenre")));
                book.setAuthor(new AuthorDaoSQLImpl().getById(rs.getInt("idAuthor")));
                books.add(book);
            }
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return books;
    }
}
