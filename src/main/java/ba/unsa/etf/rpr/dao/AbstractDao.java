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

    public Connection getConnection(){
        createConnection();
        return connection;
    }

    private static void createConnection() {
        try {
            Properties properties = new Properties();
            properties.load(ClassLoader.getSystemResource("application.properties.sample").openStream());
            String url = properties.getProperty("db.connection_string");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");
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

    public abstract Type rowToObject(ResultSet rs) throws MyBookListException;

    public abstract Map<String, Object> objectToRow(Type object);

    public Type getById(int id) throws MyBookListException{
        return executeQueryUnique("SELECT * FROM "+ tableName+" WHERE id = ?", new Object[]{id});
    }

    public List<Type> getAll() throws MyBookListException{
        return executeQuery("SELECT * FROM " + tableName, null);
    }

    public List<Type> executeQuery(String query, Object[] parameters) throws MyBookListException{
        List<Type> resultList = new ArrayList<>();
        try {
            PreparedStatement statement = getConnection().prepareStatement(query);
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i = i + 1) {
                    statement.setObject(i + 1, parameters[i]);
                }
            }
            try {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    resultList.add(rowToObject(rs));
                }
            }
            finally {
                statement.close();
            }
        }
        catch (SQLException e) {
            throw new MyBookListException(e.getMessage(), e);
        }
        return resultList;
    }

    public Type executeQueryUnique(String query, Object[] parameters) throws MyBookListException{
        List<Type> result = executeQuery(query, parameters);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    public void delete(int id) throws MyBookListException{
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";
        try{
            PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setObject(1, id);
            stmt.executeUpdate();
            stmt.close();
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
            PreparedStatement statement = prepareStatement(builder.toString(), row);
            try (statement) {
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();
                rs.next();
                item.setId(rs.getInt(1));
            }
        }
        catch (SQLException e){
            throw new MyBookListException(e.getMessage(), e);
        }
        return item;

    }

    private Map.Entry<String, String> prepareInsertParts(Map<String, Object> row) {
        StringBuilder columns = new StringBuilder();
        StringBuilder questions = new StringBuilder();

        int counter = 0;
        for (Map.Entry<String, Object> entry: row.entrySet()) {
            if (counter != 0) {
                columns.append(entry.getKey());
                questions.append("?");
                if (counter != row.size() - 1) {
                    columns.append(", ");
                    questions.append(", ");
                }
            }
            counter = counter + 1;
        }
        return new AbstractMap.SimpleEntry<>(columns.toString(), questions.toString());
    }

    public void update(Type item) throws MyBookListException{
        Map<String,Object> tableRow = objectToRow(item);
        String updateColumns = prepareUpdateParts(tableRow);
        StringBuilder query = new StringBuilder();
        query.append("UPDATE ").append(tableName).append(" SET ").append(updateColumns).append(" WHERE id = ?");
        try {
            PreparedStatement statement = prepareStatement(query.toString(), tableRow);
            statement.setObject(tableRow.size(), item.getId());
            statement.executeUpdate();
            statement.close();
        }
        catch (SQLException exception) {
            throw new MyBookListException(exception.getMessage(), exception);
        }
    }

    private PreparedStatement prepareStatement(String query, Map<String,Object> tableRow) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        int counter = 0;
        for (Map.Entry<String,Object> entry : tableRow.entrySet()) {
            if (counter != 0) {
                statement.setObject(counter, entry.getValue());
            }
            counter = counter + 1;
        }
        return statement;
    }
    private String prepareUpdateParts(Map<String, Object> row) {
        StringBuilder columns = new StringBuilder();

        int counter = 0;
        for (Map.Entry<String, Object> entry: row.entrySet()) {
            if (counter != 0) {
                columns.append(entry.getKey()).append(" = ?");
                if (counter != row.size() - 1) {
                    columns.append(", ");
                }
            }
            counter = counter + 1;
        }
        return columns.toString();
    }
}
