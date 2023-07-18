package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Idable;
import ba.unsa.etf.rpr.exceptions.MyBookListException;

import java.sql.*;
import java.util.*;

public abstract class AbstractDao<Type extends Idable> implements Dao<Type>{

    private static Connection connection = null;
    private final String tableName;

    public AbstractDao(String tableName){
        this.tableName = tableName;
        createConnection();
    }

    private static void createConnection() {
        try {
            Properties properties = new Properties();
            properties.load(ClassLoader.getSystemResource("application.properties.sample").openStream());
            String url = properties.getProperty("database.connection_string");
            String username = properties.getProperty("database.username");
            String password = properties.getProperty("database.password");
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        finally {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    connection.close();
                }
                catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }));
        }
    }

    public Connection getConnection(){
        createConnection();
        return connection;
    }

    public abstract Type rowToObject(ResultSet rs) throws MyBookListException;

    public abstract Map<String, Object> objectToRow(Type object);

    public Type getById(int id) throws MyBookListException{
        return executeQueryUnique("SELECT * FROM "+this.tableName+" WHERE id = ?", new Object[]{id});
    }

    public List<Type> getAll() throws MyBookListException{
        return executeQuery("SELECT * FROM " + tableName, null);
    }

    public List<Type> executeQuery(String query, Object[] parameters) throws MyBookListException{
        try {
            PreparedStatement stmt = getConnection().prepareStatement(query);
            if (parameters != null){
                for(int i = 1; i <= parameters.length; i++){
                    stmt.setObject(i, parameters[i-1]);
                }
            }
            ResultSet rs = stmt.executeQuery();
            ArrayList<Type> resultList = new ArrayList<>();
            while (rs.next()) {
                resultList.add(rowToObject(rs));
            }

            return resultList;
        } catch (SQLException e) {
            throw new MyBookListException((e.getMessage()), e);
        }
    }

    public Type executeQueryUnique(String query, Object[] parameters) throws MyBookListException{
        List<Type> result = executeQuery(query, parameters);
        if (result != null && result.size() == 1){
            return result.get(0);
        }else{
            throw new MyBookListException("Object does not exist");
        }
    }

    public void delete(int id) throws MyBookListException{
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";
        try{
            PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setObject(1, id);
            stmt.executeUpdate();
        }catch (SQLException e){
            throw new MyBookListException((e.getMessage()), e);
        }
    }

    public Type add(Type item) throws MyBookListException{
        Map<String, Object> row = objectToRow(item);
        Map.Entry<String, String> columns = prepareInsertParts(row);

        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ").append(tableName);
        builder.append(" (").append(columns.getKey()).append(") ");
        builder.append("VALUES (").append(columns.getValue()).append(")");

        try{
            PreparedStatement stmt = getConnection().prepareStatement(builder.toString(), Statement.RETURN_GENERATED_KEYS);
            // bind params. IMPORTANT treeMap is used to keep columns sorted so params are bind correctly
            int counter = 1;
            for (Map.Entry<String, Object> entry: row.entrySet()) {
                if (entry.getKey().equals("id")) continue; // skip ID
                stmt.setObject(counter, entry.getValue());
                counter++;
            }
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next(); // we know that there is one key
            item.setId(rs.getInt(1)); //set id to return it back */

            return item;
        }catch (SQLException e){
            throw new MyBookListException(e.getMessage(), e);
        }
    }

    private Map.Entry<String, String> prepareInsertParts(Map<String, Object> row) {
        StringBuilder columns = new StringBuilder();
        StringBuilder questions = new StringBuilder();

        int counter = 0;
        for (Map.Entry<String, Object> entry: row.entrySet()) {
            counter++;
            if (entry.getKey().equals("id")) continue;
            columns.append(entry.getKey());
            questions.append("?");
            if (row.size() != counter) {
                columns.append(",");
                questions.append(",");
            }
        }
        return new AbstractMap.SimpleEntry<>(columns.toString(), questions.toString());
    }

    public Type update(Type item) throws MyBookListException{
        Map<String, Object> row = objectToRow(item);
        String updateColumns = prepareUpdateParts(row);
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE ")
                .append(tableName)
                .append(" SET ")
                .append(updateColumns)
                .append(" WHERE id = ?");

        try{
            PreparedStatement stmt = getConnection().prepareStatement(builder.toString());
            int counter = 1;
            for (Map.Entry<String, Object> entry: row.entrySet()) {
                if (entry.getKey().equals("id")) continue; // skip ID
                stmt.setObject(counter, entry.getValue());
                counter++;
            }
            stmt.setObject(counter, item.getId());
            stmt.executeUpdate();
            return item;
        }catch (SQLException e){
            throw new MyBookListException(e.getMessage(), e);
        }
    }

    private String prepareUpdateParts(Map<String, Object> row) {
        StringBuilder columns = new StringBuilder();

        int counter = 0;
        for (Map.Entry<String, Object> entry: row.entrySet()) {
            counter++;
            if (entry.getKey().equals("id")) continue;
            columns.append(entry.getKey()).append("= ?");
            if (row.size() != counter) {
                columns.append(",");
            }
        }
        return columns.toString();
    }
}
