package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Score;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreDaoSQLImpl implements ScoreDao{

    private Connection connection;

    public ScoreDaoSQLImpl(){
        try{
            this.connection = DataBaseDao.getInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Score getById(int id) {
        String query = "SELECT * FROM Score WHERE id = ?";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Score score = new Score();
                score.setId(rs.getInt("id"));
                score.setScore(rs.getDouble("score"));
                score.setBook(new BookDaoSQLImpl().getById(rs.getInt("idBook")));
                score.setUser(new UserDaoSQLImpl().getById(rs.getInt("idUser")));
                rs.close();
                return score;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Score add(Score item) {
        try{
            PreparedStatement stmt = this.connection.prepareStatement("INSERT INTO Score(score, idBook, idUser) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setDouble(1, item.getScore());
            stmt.setInt(2, item.getBook().getId());
            stmt.setInt(3, item.getUser().getId());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            item.setId(rs.getInt(1));
            return item;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Score update(Score item) {
        try{
            PreparedStatement stmt = this.connection.prepareStatement("UPDATE Book SET score = ?, idBook = ?, idUser = ? WHERE id = ?");
            stmt.setInt(5, item.getId());
            stmt.setDouble(1, item.getScore());
            stmt.setInt(2, item.getBook().getId());
            stmt.setInt(3, item.getUser().getId());
            stmt.executeUpdate();
            return item;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(int id) {
        try{
            PreparedStatement stmt = this.connection.prepareStatement("DELETE from Score WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Score> getAll() {
        List<Score> scores = new ArrayList<>();
        try{
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM Score");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Score score = new Score();
                score.setId(rs.getInt("id"));
                score.setScore(rs.getDouble("score"));
                score.setBook(new BookDaoSQLImpl().getById(rs.getInt("idBook")));
                score.setUser(new UserDaoSQLImpl().getById(rs.getInt("idUser")));
                scores.add(score);
            }
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return scores;
    }
}
