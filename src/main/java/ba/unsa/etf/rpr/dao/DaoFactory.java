package ba.unsa.etf.rpr.dao;


public class DaoFactory {

    private static final UserDao userDao = UserDaoSQLImpl.getInstance();
    private static final GenreDao genreDao = GenreDaoSQLImpl.getInstance();
    private static final AuthorDao authorDao = AuthorDaoSQLImpl.getInstance();
    private static final BookDao bookDao = BookDaoSQLImpl.getInstance();
    private static final StatusDao statusDao = StatusDaoSQLImpl.getInstance();


    public DaoFactory(){}

    public static UserDao userDao(){
        return userDao;
    }

    public static GenreDao genreDao(){
        return genreDao;
    }

    public static AuthorDao authorDao(){
        return authorDao;
    }

    public static BookDao bookDao(){
        return bookDao;
    }

    public static StatusDao statusDao(){
        return statusDao;
    }

}
