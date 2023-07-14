package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.dao.*;
import ba.unsa.etf.rpr.domain.*;

import java.util.List;

public class App
{
    public static void main( String[] args ) {

        /*
        UserDaoSQLImpl userDaoSQL = new UserDaoSQLImpl();
        BookDaoSQLImpl bookDaoSQL = new BookDaoSQLImpl();
        GenreDaoSQLImpl genreDaoSQL = new GenreDaoSQLImpl();
        AuthorDaoSQLImpl authorDaoSQL = new AuthorDaoSQLImpl();
        StatusDaoSQLImpl statusDaoSQL = new StatusDaoSQLImpl();


        Genre sciFi = genreDaoSQL.add( new Genre(1, "science fiction") );
        Genre magRel = genreDaoSQL.add( new Genre(2, "magical realism") );
        Genre fiction = genreDaoSQL.add( new Genre(3, "fiction") );
        Genre novel = genreDaoSQL.add( new Genre(4, "novel") );
        Genre horror = genreDaoSQL.add( new Genre(5, "horror") );
        Genre novella = genreDaoSQL.add( new Genre(6, "novella") );
        Genre fairyTale = genreDaoSQL.add( new Genre(7, "fairy tale") );
        Genre mystery = genreDaoSQL.add( new Genre(8, "mystery") );

        Author yokoTawada = authorDaoSQL.add( new Author(1, "Yoko Tawada") );
        Author toshikazuKawaguchi = authorDaoSQL.add( new Author(2, "Toshikazu Kawaguchi") );
        Author erinMorgenstern = authorDaoSQL.add( new Author(3, "Erin Morgenstern") );
        Author sallyRooney = authorDaoSQL.add( new Author(4, "Sally Rooney") );
        Author mattHaig = authorDaoSQL.add( new Author(5, "Matt Haig") );
        Author pauloCoelho = authorDaoSQL.add( new Author(6, "Paulo Coelho") );
        Author leilaMottlay = authorDaoSQL.add( new Author(7, "Leila Mottlay") );
        Author hanyaYanagihara = authorDaoSQL.add( new Author(8, "Hanya Yanagihara") );
        Author claireKeegan = authorDaoSQL.add( new Author(9, "Claire Keegan") );
        Author guillermoFunke = authorDaoSQL.add( new Author(10, "Guillermo del Toro Gornelia Funke") );
        Author branokoCopic = authorDaoSQL.add( new Author(11, "Branko Copic") );
        Author jKRowling = authorDaoSQL.add( new Author(12, "J. K. Rowling") );


        Book book1 = bookDaoSQL.add( new Book(1, "The last children of Tokyo", 7647843, sciFi, yokoTawada) );
        Book book2 = bookDaoSQL.add( new Book(2, "Before the coffee gets cold", 4378938, magRel, toshikazuKawaguchi) );
        Book book3 = bookDaoSQL.add( new Book(3, "The starless sea", 3268239, fiction, erinMorgenstern) );
        Book book4 = bookDaoSQL.add( new Book(4, "Normal people", 6473829, novel, sallyRooney) );
        Book book5 = bookDaoSQL.add( new Book(5, "The midnight library", 7466323, fiction, mattHaig) );
        Book book6 = bookDaoSQL.add( new Book(6, "The Alchemist", 1234321, novel, pauloCoelho) );
        Book book7 = bookDaoSQL.add( new Book(7, "Nightcrawling", 6756458, horror, leilaMottlay) );
        Book book8 = bookDaoSQL.add( new Book(8, "A little life", 9998876, novel, hanyaYanagihara) );
        Book book9 = bookDaoSQL.add( new Book(9, "Foster", 8765436, novella, claireKeegan) );
        Book book10 = bookDaoSQL.add( new Book(10, "Pan's labyrinth", 1277024, fairyTale, guillermoFunke) );
        Book book11 = bookDaoSQL.add( new Book(11, "Ježeva kućica" , 2311693, fiction, branokoCopic) );
        Book book12 = bookDaoSQL.add( new Book(12, "Harry Potter and the Sorcerer's Stone", 4637969, fiction, jKRowling) );

        User klipovaca = userDaoSQL.add( new User(1, "Kemal Lipovaca", " ", " ") );
        User abalesic = userDaoSQL.add( new User(2, "Ajna Balesic", "", "") );
        User msultanovic = userDaoSQL.add( new User(3, "Minela Sultanovic", "", "") );
        User esultanovic = userDaoSQL.add( new User(4, "Erna Sultanovic", "","") );


        Status status1 = statusDaoSQL.add( new Status(1, "read", 10, abalesic, book11) );
        Status status2 = statusDaoSQL.add( new Status(2, "read", 10, klipovaca, book6) );
        Status status3 = statusDaoSQL.add( new Status(3, "reading", 0, msultanovic, book8) );
        Status status4 = statusDaoSQL.add( new Status(4, "reading", 0, abalesic, book4) );
        Status status5 = statusDaoSQL.add( new Status(5, "read", 8, klipovaca, book12) );
        Status status6 = statusDaoSQL.add( new Status(6, "read", 7.5, esultanovic, book5) );
        Status status7 = statusDaoSQL.add( new Status(7, "reading", 9, esultanovic, book3) );



        System.out.println("\nkemo ima id" + klipovaca.getId());
        System.out.println("\najna ima id" + abalesic.getId());
        System.out.println("\nja imam id" + msultanovic.getId());
        System.out.println("\nerna ima id" + esultanovic.getId());

        List<Book> books = bookDaoSQL.getAll();
        List<Genre> genres = genreDaoSQL.getAll();
        List<Author> authors = authorDaoSQL.getAll();
        List<User> users = userDaoSQL.getAll();
        List<Status> statuses = statusDaoSQL.getAll();

        System.out.println("\nBooks:");
        for(Book book : books)
            System.out.println(book);
        System.out.println("\nGenres:");
        for(Genre genre : genres)
            System.out.println(genre);
        System.out.println("\nAuthors:");
        for(Author author : authors)
            System.out.println(author);
        System.out.println("\nUsers:");
        for(User user : users)
            System.out.println(user);
        System.out.println("\nStatuses:");
        for(Status status : statuses)
            System.out.println(status);

        Genre probaGenre = genreDaoSQL.add( new Genre(55, "proba123") );
        Author probaAuthor = authorDaoSQL.add( new Author(55, "proba123") );
        Book probaBook = bookDaoSQL.add( new Book(55, "Proba Book 123", 7633843, probaGenre, probaAuthor) );
        User probaUser = userDaoSQL.add( new User(55, "Proba User", " ", " ") );
        Status probaStatus = statusDaoSQL.add( new Status(55, "proba123", 5, probaUser, probaBook) );

        Genre testGenre = new Genre(18, "test genre");
        Author testAuthor = new Author(26, "test author");
        Book testBook = new Book(27, "test book", 5555555, testGenre, testAuthor);
        User testUser = new User(12, "test user", "","");
        Status testStatus = new Status(18, "test status", 3, testUser, testBook);

        genreDaoSQL.update(testGenre);
        authorDaoSQL.update(testAuthor);
        bookDaoSQL.update(testBook);
        userDaoSQL.update(testUser);
        statusDaoSQL.update(testStatus);


        System.out.println("\nAfter update:\n" + genreDaoSQL.getById(55) + '\n' +
                authorDaoSQL.getById(55) + '\n' + bookDaoSQL.getById(55) + '\n' +
                userDaoSQL.getById(55) + '\n' + statusDaoSQL.getById(55));


        authorDaoSQL.delete(26);
        genreDaoSQL.delete(18);
        bookDaoSQL.delete(27);
        statusDaoSQL.delete(18);
        userDaoSQL.delete(12);


        books = bookDaoSQL.getAll();
        genres = genreDaoSQL.getAll();
        authors = authorDaoSQL.getAll();
        users = userDaoSQL.getAll();
        statuses = statusDaoSQL.getAll();

        System.out.println("\nAfter deletion:\n" + books + '\n' + genres + '\n' + authors + '\n' + users + '\n' + statuses);
        
         */


    }
}
