package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Idable;
import ba.unsa.etf.rpr.exceptions.MyBookListException;

import java.sql.*;
import java.util.*;

/**
 * AbstractDao represents an abstract class that implements shared methods by other DaoSQLImpl classes.
 * @param <Type> generic type tht implements Idable interface
 * @author Minela Sultanović
 */

public abstract class AbstractDao<Type extends Idable> implements Dao<Type>{

    private static Connection connection = null;
    private final String tableName;

    /**
     * Public constructor that initializes a string going to be used as name of the table for various kinds of SQL queries.
     * This contructor also establishes a connection to the database with stored information.
     * @param tableName is a String that represents name of the table that is going to be accessed in MySQL
     */
    public AbstractDao(String tableName){
        this.tableName = tableName;
        createConnection();
    }

    /**
     * Method that returns the connection to the Data Base
     * @return Connection to Data Base
     */
    public Connection getConnection(){
        createConnection();
        return connection;
    }

    /**
     * Method whicho creates a connection with the database using the information provided in the
     * applications.properties file.
     */
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

    /**
     * Method that is used to transform an item from a MySQL table row form to the appropriate Java Object form.
     * @param rs the item as a MySQL table row
     * @return a Bean object for specific table
     * @throws MyBookListException
     */
    public abstract Type rowToObject(ResultSet rs) throws MyBookListException;

    /**
     * Method that is used to transform an item from a Java Object form to the appropriate MySQL table row form.
     * @param object a Bean object for specific table
     * @return the item as a MySQL table row
     */
    public abstract Map<String, Object> objectToRow(Type object);

    public Type getById(int id) throws MyBookListException{
        return executeQueryUnique("SELECT * FROM "+ tableName+" WHERE id = ?", new Object[]{id});
    }

    public List<Type> getAll() throws MyBookListException{
        return executeQuery("SELECT * FROM " + tableName, null);
    }

    /**
     * Method that executes a custom query to the Data Base, and returns more than one record.
     * @param query String that represents a custom query
     * @param parameters array of needed parameters for the query
     * @return a list container of all items that fulfill the required query restrictions
     * @throws MyBookListException
     */
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

    /**
     * Method that executes a custom query to the Data Base and returns single record.
     * @param query query that returns single record
     * @param parameters list of needed parameters for the query
     * @return an item that is the result of the given query or null for no match found
     * @throws MyBookListException
     */
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

    /**
     * A helper method that prepares the needed columns and questionmarks for the add statement
     * @param row the table row on which the result will be based on
     * @return a pair of strings which represent the statement columns and appropriate number of questionmarks
     */
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

    /**
     * A helper method that is used to prepare an executeUpdate statement based off of the query and table row columns
     * @param query the query that is going to be sent
     * @param tableRow the table row whose columns will be used in the statement
     * @return the statement fully prepared for executing
     */
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

    /**
     * A helper method that prepares the columns that will be used in the update statement
     * @param row the table row on which the result will be based on
     * @return a string which represents the columns that will be updated
     */
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
