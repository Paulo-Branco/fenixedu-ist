package pt.ist.fenixedu.libraryattendance.api.beans;

import org.fenixedu.spaces.domain.Space;
import pt.ist.fenixedu.libraryattendance.ui.LibraryAttendance;
import pt.ist.fenixedu.libraryattendance.ui.LibraryInformation;

public class FenixLibrary {
    String name;
    String id;
    int capacity;
    int freeSlots;
    int lockers;
    int freeLockers;

    public FenixLibrary(Space library) {
        LibraryInformation libInfo = new LibraryInformation(library);
        LibraryAttendance attendance = new LibraryAttendance("", library);

        this.name = library.getParent().getParent().getPresentationName();
        this.id = library.getExternalId();
        this.capacity = libInfo.getCapacity();
        this.freeSlots = libInfo.getCapacity() - LibraryAttendance.currentAttendaceCount(library);
        this.lockers = libInfo.getLockers();
        this.freeLockers = LibraryAttendance.getAvailableSpaces(attendance).size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getLockers() {
        return lockers;
    }

    public void setLockers(int lockers) {
        this.lockers = lockers;
    }

    public int getFreeSlots() {
        return freeSlots;
    }

    public void setFreeSlots(int freeSlots) {
        this.freeSlots = freeSlots;
    }

    public int getFreeLockers() {
        return freeLockers;
    }

    public void setFreeLockers(int freeLockers) {
        this.freeLockers = freeLockers;
    }
}
