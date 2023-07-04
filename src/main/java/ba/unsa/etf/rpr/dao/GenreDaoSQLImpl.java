package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreDaoSQLImpl implements GenreDao{

    private Connection connection;

    private static GenreDaoSQLImpl instance = null;

    public GenreDaoSQLImpl(){
        try{
            this.connection = DataBaseDao.getInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static GenreDaoSQLImpl getInstance() {
        if(instance == null) instance = new GenreDaoSQLImpl();
        return instance;
    }

    public static void removeInstance(){
        if(instance!=null)
            instance = null;
    }

    @Override
    public Genre getById(int id) {
        String query = "SELECT * FROM Genre WHERE id = ?";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Genre genre = new Genre();
                genre.setId(rs.getInt("id"));
                genre.setName(rs.getString("name"));
                rs.close();
                return genre;
            }else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Genre add(Genre genre) {
        String insert = "INSERT INTO Genre(name) VALUES(?)";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, genre.getName());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            genre.setId(rs.getInt(1));
            return genre;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Genre update(Genre item) {
        String update = "UPDATE Genre SET name = ? where id = ?";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(update);
            stmt.setObject(1, item.getName());
            stmt.setObject(2, item.getId());
            stmt.executeUpdate();
            return item;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(int id) {
        String delete = "DELETE FROM Genre WHERE id = ?";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(delete);
            stmt.setObject(1, id);
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Genre> getAll() {
        String query = "SELECT * FROM Genre";
        List<Genre> genres = new ArrayList<>();
        try{
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Genre genre = new Genre();
                genre.setId(rs.getInt("id"));
                genre.setName(rs.getString("name"));
                genres.add(genre);
            }
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return genres;
    }
}
