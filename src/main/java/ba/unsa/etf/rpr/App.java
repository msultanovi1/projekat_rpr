package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.dao.*;
import ba.unsa.etf.rpr.domain.*;
import com.sun.tools.javac.jvm.Gen;

public class App
{
    public static void main( String[] args ) {

        Genre sciFi = new Genre(1, "science fiction");
        Genre magRel = new Genre(2, "magical realism");
        Genre fiction = new Genre(3, "fiction");
        Genre novel = new Genre(4, "novel");
        Genre horror = new Genre(5, "horror");
        Genre novella = new Genre(6, "novella");
        Genre fairyTale = new Genre(7, "fairy tale");

        Author yokoTawada = new Author(1, "Yoko Tawada");
        Author toshikazuKawaguchi = new Author(2, "Toshikazu Kawaguchi");
        Author erinMorgenstern = new Author(3, "Erin Morgenstern");
        Author sallyRooney = new Author(4, "Sally Rooney");
        Author mattHaig = new Author(5, "Matt Haig");
        Author pauloCoelho = new Author(6, "Paulo Coelho");
        Author leilaMottlay = new Author(7, "Leila Mottlay");
        Author hanyaYanagihara = new Author(8, "Hanya Yanagihara");
        Author claireKeegan = new Author(9, "Claire Keegan");
        Author guillermoFunke = new Author(10, "Guillermo del Toro Gornelia Funke");


        Book book1 = new Book(1, "The last children of Tokyo", 7647843, sciFi, yokoTawada);
        Book book2 = new Book(2, "Before the coffee gets cold", 4378938, magRel, toshikazuKawaguchi);
        Book book3 = new Book(3, "The starless sea", 3268239, fiction, erinMorgenstern);
        Book book4 = new Book(4, "Normal people", 6473829, novel, sallyRooney);
        Book book5 = new Book(5, "The midnight library", 7466323, fiction, mattHaig);
        Book book6 = new Book(6, "The Alchemist", 1234321, novel, pauloCoelho);
        Book book7 = new Book(7, "Nightcrawling", 6756458, horror, leilaMottlay);
        Book book8 = new Book(8, "A little life", 9998876, novel, hanyaYanagihara);
        Book book9 = new Book(9, "Foster", 8765436, novella, claireKeegan);
        Book book10 = new Book(10, "Pan's labyrinth", 1277024, fairyTale, guillermoFunke);

        User klipovaca = new User(1, "Kemal Lipovaca", " ", " ");
        User abalesic = new User(2, "Ajna Balesic", "", "");
        User msultanovic = new User(3, "Ajna Balesic", "", "");


        UserDaoSQLImpl userDaoSQL = new UserDaoSQLImpl();
        BookDaoSQLImpl bookDaoSQL = new BookDaoSQLImpl();
        GenreDaoSQLImpl genreDaoSQL = new GenreDaoSQLImpl();
        AuthorDaoSQLImpl authorDaoSQL = new AuthorDaoSQLImpl();





    }
}
