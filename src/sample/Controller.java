package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.util.Pair;
import sample.dataModel.ContactItem;
import sample.dataModel.ContactsData;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

public class Controller {

    @FXML
    private BorderPane mainPane;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button editBtn;
    @FXML
    private Button addBtn;
    @FXML
    private HBox bottomHBox;
    @FXML
    private TableView<ContactItem> contactsTableView;

    private ObservableList<ContactItem> contactItems;

    @FXML
    public void initialize() {


        deleteBtn.prefWidthProperty().bind(bottomHBox.widthProperty().multiply(5));
        editBtn.prefWidthProperty().bind(bottomHBox.widthProperty().multiply(5));
        addBtn.prefWidthProperty().bind(bottomHBox.widthProperty().multiply(5));


        TableColumn firstNameCol = new TableColumn("First Name");
//        firstNameCol.setMaxWidth(200);
        firstNameCol.setMinWidth(120);
        firstNameCol.prefWidthProperty().bind(contactsTableView.widthProperty().multiply(0.2));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<ContactItem, String>("name"));

        TableColumn lastNameCol = new TableColumn("Last Name");
//        lastNameCol.setMaxWidth(200);
        lastNameCol.setMinWidth(120);
        lastNameCol.prefWidthProperty().bind(contactsTableView.widthProperty().multiply(0.2));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<ContactItem, String>("lastName"));

        TableColumn phoneNumber = new TableColumn("Phone Number");
//        phoneNumber.setMaxWidth(300);
        phoneNumber.setMinWidth(120);
        phoneNumber.prefWidthProperty().bind(contactsTableView.widthProperty().multiply(0.25));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<ContactItem, String>("phoneNumber"));

        TableColumn notes = new TableColumn("Notes");
//        notes.setMaxWidth(300);
        notes.setMinWidth(120);
        notes.prefWidthProperty().bind(contactsTableView.widthProperty().multiply(0.35));
        notes.setCellValueFactory(new PropertyValueFactory<ContactItem, String>("notes"));


        contactsTableView.getColumns().addAll(firstNameCol, lastNameCol, phoneNumber, notes);

//        contactItems.addAll(ContactsData.getInstance().getContacts());

//        contactsTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
//            @Override
//            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//
//            }
//        });
        contactsTableView.setItems(ContactsData.getInstance().getContacts());
        contactsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }

    public void printContacts() {
        for (ObservableList<ContactItem> item : Collections.singletonList(ContactsData.getInstance().getContacts())) {
            System.out.println(item.toString().replaceAll("\\[", "").replaceAll("]", ""));
        }
    }

    public void editContactItem() {
        ContactItem toEdit = contactsTableView.getSelectionModel().getSelectedItem();

        if (toEdit == null){
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setHeaderText("Edit Contact '" + toEdit.getName() + " " + toEdit.getLastName() + "'");
        dialog.initOwner(mainPane.getScene().getWindow());

        ButtonType ok = new ButtonType("Confirm Edit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ok, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField(toEdit.getName());
//        nameField.setText(toEdit.getName());
//        nameField.setPromptText("ישראל");
        TextField lastNameField = new TextField(toEdit.getLastName());
        lastNameField.setText(toEdit.getLastName());
//        lastNameField.setPromptText("ישראלי");
        TextField phoneNumField = new TextField(toEdit.getPhoneNumber());
//        phoneNumField.setPromptText("123456789");
        TextField notesField = new TextField(toEdit.getNotes());
//        notesField.setPromptText("");

        Label addNameLabel = new Label("Contact Name:");
        Font font = new Font("Times New Roman", 14);
        addNameLabel.setFont(font);
        Label addLastName = new Label("Contact Last Name:");
        addLastName.setFont(font);
        Label addPhoneNum = new Label("Phone number:");
        addPhoneNum.setFont(font);
        Label addNotes = new Label("Notes:");
        addNotes.setFont(font);

        grid.add(addNameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(addLastName, 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(addPhoneNum, 0, 2);
        grid.add(phoneNumField, 1, 2);
        grid.add(addNotes, 0, 3);
        grid.add(notesField, 1, 3);

//        Node okButton = dialog.getDialogPane().lookupButton(ok);
//        okButton.setDisable(true);
//        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
//            okButton.setDisable(newValue.trim().isEmpty());
//        });
//        lastNameField.textProperty().addListener((observable, oldValue, newValue) -> {
//            okButton.setDisable(newValue.trim().isEmpty());
//        });
//        phoneNumField.textProperty().addListener((observable, oldValue, newValue) -> {
//            okButton.setDisable(newValue.trim().isEmpty());
//        });
//        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
//            okButton.setDisable(newValue.trim().isEmpty());
//        });
//        Platform.runLater(nameField::requestFocus);

        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ok) {
            String name = nameField.getText();
            String lastName = lastNameField.getText();
            String phoneNum = phoneNumField.getText();
            String notes = notesField.getText();
            if (notes == null) notes = " ";
            toEdit.setName(name);
            toEdit.setLastName(lastName);
            toEdit.setPhoneNumber(phoneNum);
            toEdit.setNotes(notes);

            contactsTableView.refresh();
        } else {
        }

    }


    public void addContact() {
        ContactItem toAdd = contactItemInfoDialog();
        ContactsData.getInstance().addContact(toAdd);
    }

    public ContactItem contactItemInfoDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setHeaderText("Add New Contact");
        dialog.initOwner(mainPane.getScene().getWindow());

        ButtonType ok = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ok, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        nameField.setPromptText("ישראל");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("ישראלי");
        TextField phoneNumField = new TextField();
        phoneNumField.setPromptText("123456789");
        TextField notesField = new TextField();
        notesField.setPromptText("");

        Label addNameLabel = new Label("Contact Name:");
        Font font = new Font("Times New Roman", 14);
        addNameLabel.setFont(font);
        Label addLastName = new Label("Contact Last Name:");
        addLastName.setFont(font);
        Label addPhoneNum = new Label("Phone number:");
        addPhoneNum.setFont(font);
        Label addNotes = new Label("Notes:");
        addNotes.setFont(font);

        grid.add(addNameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(addLastName, 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(addPhoneNum, 0, 2);
        grid.add(phoneNumField, 1, 2);
        grid.add(addNotes, 0, 3);
        grid.add(notesField, 1, 3);

        Node okButton = dialog.getDialogPane().lookupButton(ok);
        okButton.setDisable(true);
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty());
        });
        lastNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty());
        });
        phoneNumField.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty());
        });
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty());
        });
        Platform.runLater(nameField::requestFocus);

        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ok) {
            System.out.println("Ok button pressed");
            String name = nameField.getText();
            String lastName = lastNameField.getText();
            String phoneNum = phoneNumField.getText();
            String notes = notesField.getText();
            if (notes == null) notes = " ";
            return new ContactItem(name, lastName, phoneNum, notes);
        } else {
            System.out.println("Cancel button pressed");
        }
        return null;

    }

    public void deleteContact() {
        ContactItem toDelete = contactsTableView.getSelectionModel().getSelectedItem();
        if (toDelete == null ){
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Delete Contact");
        alert.setContentText("Are you sure you want to delete '" + toDelete.getName() + " " + toDelete.getLastName() + "'?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            ContactsData.getInstance().deleteContact(toDelete);
        }
    }
}
