package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.*;
import ba.unsa.etf.rpr.domain.*;
import ba.unsa.etf.rpr.exceptions.MyBookListException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * The JavaFX controller for adding and editing existing books in users list
 */
public class EditController extends WindowController{


    private final UserManager userManager = UserManager.getInstance();
    private final StatusManager statusManager = StatusManager.getInstance();
    private final BookManager bookManager = BookManager.getInstance();
    private final GenreManager genreManager = GenreManager.getInstance();
    private final AuthorManager authorManager = AuthorManager.getInstance();
    private final User user;
    private List<Genre> genres;
    private List<Status> statuses;
    private List<Author> authors;
    private List<Book> books;

    private final String[] readingStatus = {"read", "reading", "to be read", "on hold"};
    private final Double[] bookScore = {0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0};

    public ComboBox<Integer> idEditBoxField;
    public TextField bookNameEditBoxField;
    public TextField UINEditField;
    public TextField authorEditField;
    public TextField genreEditField;
    public ComboBox<String> statusEditBoxField;
    public ComboBox<Double> scoreEditBoxField;

    public Button buttonConfirmEdit;
    public Button buttonCloseEdit;

    public TextField idAddField;
    public ComboBox<String> bookNameAddBoxField;
    public TextField UINAddField;
    public TextField authorAddField;
    public TextField genreAddField;
    public ComboBox<String> statusAddBoxField;
    public ComboBox<Double> scoreAddBoxField;

    public Button buttonAddNewBook;
    public Button buttonConfirmAdd;
    public Button buttonCloseAdd;

    /**
     * A constructor that initializes the user that has invoked the opening of this window
     * and all the existing books in database along with authors and book genres
     * @param user the user that requested the opening of this window
     */
    public EditController(User user) {
        this.user = user;
        try {
            statuses = statusManager.searchByUser(user);
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
        try {
            statuses = statusManager.searchByUser(user);
            genres = genreManager.getAll();
            authors = authorManager.getAll();
            books = bookManager.getAll();
        }
        catch(MyBookListException e){
            openAlert(Alert.AlertType.ERROR, "Unable to access database" + e.getMessage());
        }

        idAddField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                idAddField.setText(newValue.replaceAll("\\D", ""));
            }
        });
        idEditBoxField.setItems(FXCollections.observableList(statuses.stream().map(Status::getId).collect(toList())));

        statusAddBoxField.getItems().addAll(readingStatus);
        statusEditBoxField.getItems().addAll(readingStatus);

        scoreAddBoxField.getItems().addAll(bookScore);
        scoreEditBoxField.getItems().addAll(bookScore);

        idEditBoxField.focusedProperty().addListener(((observableValue, oldValue, newValue) -> {
            String idEditBoxFieldText = String.valueOf(idEditBoxField.getValue());
            if(!idEditBoxFieldText.isEmpty() && !newValue){
                try{
                    Status status = statusManager.getById(Integer.parseInt(idEditBoxFieldText));
                    bookNameEditBoxField.setText(status.getBook().getName());
                    UINEditField.setText(String.valueOf(status.getBook().getUIN()));
                    authorEditField.setText(status.getBook().getAuthor().getName());
                    genreEditField.setText(status.getBook().getGenre().getName());
                    statusEditBoxField.setValue(status.getStatus());
                    scoreEditBoxField.setValue(status.getScore());

                    UINEditField.setEditable(false);
                    authorEditField.setEditable(false);
                    genreEditField.setEditable(false);

                }catch (MyBookListException exception){
                    openAlert(Alert.AlertType.ERROR, "Unable to edit" + exception.getMessage());
                }
            }
        }));

        UINEditField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                UINEditField.setText(newValue.replaceAll("\\D", ""));
            }
        });

        UINAddField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                UINAddField.setText(newValue.replaceAll("\\D", ""));
            }
        });
        //bookNameAddBoxField.getItems().addAll(String.valueOf(books));

        bookNameAddBoxField.setItems(FXCollections.observableList(books.stream().map(Book::getName).collect(toList())));

        bookNameAddBoxField.focusedProperty().addListener(((observableValue, oldValue, newValue) -> {
            String bookNameAddBoxFieldText = String.valueOf(bookNameAddBoxField.getValue());
            if(!bookNameAddBoxFieldText.isEmpty() && !newValue){
                try{
                    Book book = bookManager.searchByName(bookNameAddBoxFieldText);
                    idAddField.setText("55");
                    UINAddField.setText(String.valueOf(book.getUIN()));
                    authorAddField.setText(book.getAuthor().getName());
                    genreAddField.setText(book.getGenre().getName());

                }catch (MyBookListException exception){
                    openAlert(Alert.AlertType.ERROR, "Unable to edit" + exception.getMessage());
                }
            }
        }));

        emptyAllTextFields();
    }

    /**
     * Method that is responsible for adding a book to the list
     * In case of no errors, an alert will be shown that will confirm the successful addition/edit of a court case with the ID of the corresponding case
     * In case of any problems, an alert will pop up that will explain the reason of the error as well as uncover some of its extra details
     */
    public void confirmAdd(ActionEvent actionEvent) {
        try{
            String bookNameAddBoxFieldText = String.valueOf(bookNameAddBoxField.getValue());
            if (bookNameAddBoxFieldText.isEmpty()) {
                throw new MyBookListException("Book name field left empty.");
            }
            String idAddFieldText = idAddField.getText().trim();
            String UINAddFieldText = UINAddField.getText().trim();
            String genreAddFieldText = genreAddField.getText().trim();
            String authorAddFieldText = authorAddField.getText().trim();
            String statusAddBoxFieldText = statusAddBoxField.getValue();
            String scoreAddBoxFieldText = String.valueOf(scoreAddBoxField.getValue());

            Genre genre = genres.stream().filter(person -> person.getName().equalsIgnoreCase(genreAddFieldText)).findFirst().orElseThrow(() -> new MyBookListException("Provided genre does not exist."));
            Author author = authors.stream().filter(person -> person.getName().equalsIgnoreCase(authorAddFieldText)).findFirst().orElseThrow(() -> new MyBookListException("Provided author does not exist."));
            Book book = books.stream().filter(person -> person.getName().equalsIgnoreCase(bookNameAddBoxFieldText)).findFirst().orElseThrow(() -> new MyBookListException("Provided book does not exist."));

            //Book book = new Book(Integer.parseInt(idAddFieldText), bookNameAddBoxFieldText, Long.parseLong(UINAddFieldText), genre, author) ;
            Status status = new Status(Integer.parseInt(idAddFieldText), statusAddBoxFieldText, Double.parseDouble(scoreAddBoxFieldText), user, book);

            statusManager.add(status);
            openAlert(Alert.AlertType.INFORMATION, "Adding accomplished|Successfully added book status with ID of " + status.getId());
            emptyAllTextFields();

        }catch (MyBookListException exception){
            openAlert(Alert.AlertType.ERROR, "Encountered a problem during adding a book to your list." + exception.getMessage());
        }
    }

    /**
     * This method calls its parent's closeWindow method which closes this window
     */
    public void closeAdd(ActionEvent actionEvent) {
        closeWindow(buttonCloseAdd);
    }

    /**
     * Clicking on the corresponding button opens up new window where the user can add new books to database
     * if the book he wants to add isn't shown as an option, along with authors and genres
     */
    public void addNewBook(ActionEvent actionEvent) {
        openWindow("Add new book", "/fxml/addNewBookScreen.fxml", new AddNewBookController(), (Stage) buttonAddNewBook.getScene().getWindow(), true);
        try {
            books = bookManager.getAll();
        } catch (MyBookListException e) {
            openAlert(Alert.AlertType.ERROR, "Unable to access database" + e.getMessage());

        }
        bookNameAddBoxField.setItems(FXCollections.observableList(books.stream().map(Book::getName).collect(toList())));

    }

    /**
     * Method that is responsible for editing a book to the list
     * In case of no errors, an alert will be shown that will confirm the successful addition/edit of a court case with the ID of the corresponding case
     * In case of any problems, an alert will pop up that will explain the reason of the error as well as uncover some of its extra details
     */
    public void confirmEdit(ActionEvent actionEvent) {
        try{
            String idEditBoxFieldText = String.valueOf(idEditBoxField.getValue());
            if (idEditBoxFieldText.isEmpty()) {
                throw new MyBookListException("ID field left empty.");
            }

            String bookNameEditBoxFieldText = bookNameEditBoxField.getText().trim();
            String UINEditFieldText = UINEditField.getText().trim();
            String genreEditFieldText = genreEditField.getText().trim();
            String authorEditFieldText = authorEditField.getText().trim();
            String statusEditBoxFieldText = statusEditBoxField.getValue();
            String scoreEditBoxFieldText = String.valueOf(scoreEditBoxField.getValue());

            Genre genre = genres.stream().filter(person -> person.getName().equalsIgnoreCase(genreEditFieldText)).findFirst().orElseThrow(() -> new MyBookListException("Provided genre does not exist."));
            Author author = authors.stream().filter(person -> person.getName().equalsIgnoreCase(authorEditFieldText)).findFirst().orElseThrow(() -> new MyBookListException("Provided author does not exist."));
            Book book = books.stream().filter(person -> person.getName().equalsIgnoreCase(bookNameEditBoxFieldText)).findFirst().orElseThrow(() -> new MyBookListException("Provided book does not exist."));

            //Book book = new Book(Integer.parseInt(idEditBoxFieldText), bookNameEditBoxFieldText, Long.parseLong(UINEditFieldText), genre, author) ;
            Status status = new Status(Integer.parseInt(idEditBoxFieldText), statusEditBoxFieldText, Double.parseDouble(scoreEditBoxFieldText), user, book);

            statusManager.update(status);
            openAlert(Alert.AlertType.INFORMATION, "Editing accomplished|Successfully updated book status with ID of " + status.getId());
            emptyAllTextFields();

        }catch (MyBookListException exception){
            openAlert(Alert.AlertType.ERROR, "Encountered a problem during list editing." + exception.getMessage());
        }
    }

    /**
     * This method calls its parent's closeWindow method which closes this window
     */
    public void closeEdit(ActionEvent actionEvent) {
        closeWindow(buttonCloseEdit);
    }

    /**
     * Method that resets the contents of (almost) all fields and combo boxes in the shown window
     */
    private void emptyAllTextFields() {
        bookNameEditBoxField.setText("");
        UINEditField.setText("");
        authorEditField.setText("");
        genreEditField.setText("");
        statusEditBoxField.setValue("");
        scoreEditBoxField.setValue(null);

        idAddField.setText("");
        bookNameAddBoxField.setValue("");
        UINAddField.setText("");
        authorAddField.setText("");
        genreAddField.setText("");
        statusAddBoxField.setValue("");
        scoreAddBoxField.setValue(null);
    }
}
