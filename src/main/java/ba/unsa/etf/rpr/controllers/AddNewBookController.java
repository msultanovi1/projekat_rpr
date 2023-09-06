package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.AuthorManager;
import ba.unsa.etf.rpr.business.BookManager;
import ba.unsa.etf.rpr.business.GenreManager;
import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Genre;
import ba.unsa.etf.rpr.exceptions.MyBookListException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * The JavaFX controller for adding books that don't exist in database along with authors and genres
 */
public class AddNewBookController extends WindowController{

    private final BookManager bookManager = BookManager.getInstance();
    private final GenreManager genreManager = GenreManager.getInstance();
    private final AuthorManager authorManager = AuthorManager.getInstance();
    private List<Genre> genres;
    private List<Author> authors;
    private List<Book> books;

    public TextField addBookName;
    public TextField addBookUIN;
    public ComboBox<String> addBookGenre;
    public ComboBox<String> addBookAuthor;

    public Button buttonConfirmBookAdd;
    public Button buttonCloseBookAdd;

    /**
     * A constructor that initializes all existing books in database along with authors and book genres
     */
    public AddNewBookController(){
        try {
            genres = genreManager.getAll();
            authors = authorManager.getAll();
            books = bookManager.getAll();
        }catch (MyBookListException exception){
            openAlert(Alert.AlertType.ERROR, "Unexpected error occurred|" + exception.getMessage());
        }
    }

    /**
     * The method that gets called right before the opening of this window
     * Its purpose is to initialize all and restrict some JavaFX components shown in the created window
     */
    @FXML
    public void initialize(){
        addBookUIN.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                addBookUIN.setText(newValue.replaceAll("\\D", ""));
            }
        });
        addBookGenre.setItems(FXCollections.observableList(genres.stream().map(Genre::getName).collect(toList())));
        addBookAuthor.setItems(FXCollections.observableList(authors.stream().map(Author::getName).collect(toList())));
    }

    /**
     * Method that is responsible for adding a new genre to the database
     * In case of no errors, an alert will be shown that will confirm the successful addition/edit of a court case with the ID of the corresponding case
     * In case of any problems, an alert will pop up that will explain the reason of the error as well as uncover some of its extra details
     */
    public void addNewGenre(ActionEvent actionEvent){
        try {
            String addBookGenreText = addBookGenre.getValue();
            for(Genre genre : genres){
                if(genre.getName().equals(addBookGenreText)){
                    throw new MyBookListException("Provided genre is already in database.");
                }
            }
            Genre genreAdd = new Genre(55, addBookGenreText);
            genreManager.add(genreAdd);
            genres.add(genreAdd);
            addBookGenre.getItems().add(genreAdd.getName());
            openAlert(Alert.AlertType.INFORMATION, "Addition success|Successfully added new genre with ID of " + genreAdd.getId() + " to database.");
        }catch (MyBookListException exception){
            openAlert(Alert.AlertType.ERROR, "Encountered a problem during adding a genre to database." + exception.getMessage());
        }
    }

    /**
     * Method that is responsible for adding a new author to the database
     * In case of no errors, an alert will be shown that will confirm the successful addition/edit of a court case with the ID of the corresponding case
     * In case of any problems, an alert will pop up that will explain the reason of the error as well as uncover some of its extra details
     */
    public void addNewAuthor(ActionEvent actionEvent){
        try {
            String addBookAuthorText = addBookAuthor.getValue();
            for(Author author : authors){
                if(author.getName().equals(addBookAuthorText)){
                    throw new MyBookListException("Provided author is already in database.");
                }
            }
            Author authorAdd = new Author(55, addBookAuthorText);
            authorManager.add(authorAdd);
            authors.add(authorAdd);
            addBookAuthor.getItems().add(authorAdd.getName());
            openAlert(Alert.AlertType.INFORMATION, "Addition success|Successfully added new author with ID of " + authorAdd.getId() + " to database.");
        }catch (MyBookListException exception){
            openAlert(Alert.AlertType.ERROR, "Encountered a problem during adding an author to database." + exception.getMessage());
        }
    }

    /**
     * Method that is responsible for adding a new book to the database
     * In case of no errors, an alert will be shown that will confirm the successful addition/edit of a court case with the ID of the corresponding case
     * In case of any problems, an alert will pop up that will explain the reason of the error as well as uncover some of its extra details
     */
    public void confirmBookAdd(ActionEvent actionEvent) {
        try {
            String addBookNameText = addBookName.getText().trim();
            String addBookUINText = addBookUIN.getText().trim();
            String addBookGenreText = addBookGenre.getValue();
            String addBookAuthorText = addBookAuthor.getValue();

            if(addBookNameText.isEmpty()){
                throw new MyBookListException("Book name field left empty.");
            }
            if(addBookUINText.isEmpty()){
                throw new MyBookListException("Book UIN field left empty.");
            }
            if(addBookGenreText.isEmpty()){
                throw new MyBookListException("Book genre field left empty.");
            }
            if(addBookAuthorText.isEmpty()){
                throw new MyBookListException("Book author field left empty.");
            }

            Genre genre = genres.stream().filter(person -> person.getName().equalsIgnoreCase(addBookGenreText)).findFirst().orElseThrow(() -> new MyBookListException("Provided genre does not exist."));
            Author author = authors.stream().filter(person -> person.getName().equalsIgnoreCase(addBookAuthorText)).findFirst().orElseThrow(() -> new MyBookListException("Provided author does not exist."));
            Book book = new Book(55, addBookNameText, Long.parseLong(addBookUINText), genre, author);
            bookManager.add(book);

            openAlert(Alert.AlertType.INFORMATION, "Addition success|Successfully added new book with ID of " + book.getId() + " to database.");

        }catch (MyBookListException exception){
            openAlert(Alert.AlertType.ERROR, "Encountered a problem while adding a book to database." + exception.getMessage());
        }
    }

    /**
     * This method calls its parent's closeWindow method which closes this window
     */
    public void closeBookAdd(ActionEvent actionEvent) {
        closeWindow(buttonCloseBookAdd);
    }

}
