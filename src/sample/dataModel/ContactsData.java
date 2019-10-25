package sample.dataModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class ContactsData {

    private static ContactsData contactsData = new ContactsData();
    private static String fileName = "contacts.txt";
    private ObservableList<ContactItem> contacts;


    private ContactsData() {
    }

    public ObservableList<ContactItem> getContacts() {
        return contacts;
    }

    public static ContactsData getInstance() {
        return contactsData;
    }

    public void loadContacts() throws Exception {
        contacts = FXCollections.observableArrayList();
        BufferedReader scanner = Files.newBufferedReader(Paths.get(fileName));
        try {
            String input;
            while ((input = scanner.readLine()) != null) {
                String[] data = input.split("\t");


                String notes = "";

                String name = data[0];
                String lastName = data[1];
                String phoneNumber = data[2];
                if (data.length == 4) {
                    notes = data[3];
                }

                contacts.add(new ContactItem(name, lastName, phoneNumber, notes));
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    public void StoreContacts() throws IOException {

        FXCollections.observableArrayList(contacts);
        Path path = Paths.get(fileName);
        BufferedWriter writer = Files.newBufferedWriter(path);

        try {
            Iterator<ContactItem> iterator = contacts.iterator();
            while (iterator.hasNext()) {
                ContactItem item = iterator.next();
                writer.write(String.format("%s\t%s\t%s\t%s", item.getName(),
                        item.getLastName(),
                        item.getPhoneNumber(),
                        item.getNotes()));

                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public void addContact(ContactItem newContact) {
        this.contacts.add(newContact);
    }

    public void deleteContact(ContactItem toDelete) {
        this.contacts.remove(toDelete);
    }
}
