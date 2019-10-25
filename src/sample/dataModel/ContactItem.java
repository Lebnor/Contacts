package sample.dataModel;

import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class ContactItem {
    SimpleStringProperty name;
    SimpleStringProperty lastName;
    SimpleStringProperty phoneNumber;
    SimpleStringProperty notes;

    public ContactItem(String name, String lastName, String phoneNumber, String notes) {
        this.name = new SimpleStringProperty(name);
        this.lastName = new SimpleStringProperty(lastName);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.notes = new SimpleStringProperty(notes);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getNotes() {
        return notes.get();
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactItem that = (ContactItem) o;
        return name.equals(that.name) &&
                lastName.equals(that.lastName) &&
                phoneNumber.equals(that.phoneNumber) &&
                notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, phoneNumber, notes);
    }

    @Override
    public String toString() {
        return name.get() + " " + lastName.get() + " (" + phoneNumber.get() + ") -" + notes.get() + "-";

    }
}
