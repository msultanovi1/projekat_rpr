package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.bussines.*;
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
    private final Double[] bookScore = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0};

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

    public EditController(User user) {
        this.user = user;
        try {
            statuses = statusManager.searchByUser(user);
            genres = genreManager.getAll();
            authors = authorManager.getAll();
            books = bookManager.getAll();
        }catch (MyBookListException exception){
            openAlert(Alert.AlertType.ERROR, "Unexpected error occured|" + exception.getMessage());
        }
    }

    @FXML
    public void initialize(){
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
        bookNameAddBoxField.getItems().addAll(String.valueOf(books));
        //bookNameAddBoxField.setItems(FXCollections.observableList(statuses.stream().map(Status::getBook).collect(toList())));

        emptyAllTextFields();
    }

    public void confirmAdd(ActionEvent actionEvent) {
    }

    public void closeAdd(ActionEvent actionEvent) {
        closeWindow(buttonCloseAdd);
    }

    public void addNewBook(ActionEvent actionEvent) {
        openWindow("Add new book", "/fxml/addNewBookScreen.fxml", new ChangeController(user), (Stage)buttonAddNewBook.getScene().getWindow(), true);
    }

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

            Book book = new Book(Integer.parseInt(idEditBoxFieldText), bookNameEditBoxFieldText, Long.parseLong(UINEditFieldText), genre, author) ;
            Status status = new Status(Integer.parseInt(idEditBoxFieldText), statusEditBoxFieldText, Double.parseDouble(scoreEditBoxFieldText), user, book);

            statusManager.update(status);
            openAlert(Alert.AlertType.INFORMATION, "Editing accomplished|Successfully updated book status with ID of " + status.getId());
            emptyAllTextFields();

        }catch (MyBookListException exception){
            openAlert(Alert.AlertType.ERROR, "Encountered a problem during list editing." + exception.getMessage());
        }
    }

    public void closeEdit(ActionEvent actionEvent) {
        closeWindow(buttonCloseEdit);
    }

    private void emptyAllTextFields() {
        bookNameEditBoxField.setText("");
        UINEditField.setText("");
        authorEditField.setText("");
        genreEditField.setText("");
        statusEditBoxField.setValue("");
        scoreEditBoxField.setValue(null);
    }
}
