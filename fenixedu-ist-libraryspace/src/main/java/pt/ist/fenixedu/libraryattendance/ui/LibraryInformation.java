/**
 * Copyright © 2013 Instituto Superior Técnico
 *
 * This file is part of FenixEdu IST Library Attendance.
 *
 * FenixEdu IST Library Attendance is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FenixEdu IST Library Attendance is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FenixEdu IST Library Attendance.  If not, see <http://www.gnu.org/licenses/>.
 */
package pt.ist.fenixedu.libraryattendance.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.fenixedu.academic.domain.person.RoleType;
import org.fenixedu.academic.ui.renderers.providers.AbstractDomainObjectProvider;
import org.fenixedu.spaces.domain.Space;

import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;
import pt.ist.fenixWebFramework.renderers.converters.EnumConverter;

public class LibraryInformation implements Serializable {
    public static class PlaceProvider extends AbstractDomainObjectProvider {
        @Override
        public Object provide(Object source, Object currentValue) {
            LibraryInformation attendance = (LibraryInformation) source;
            Set<Space> availableSpaces = new HashSet<Space>();
            for (Space space : attendance.getLibrary().getChildren()) {
                if (LibraryAttendance.currentAttendaceCount(space) < space.getAllocatableCapacity()) {
                    availableSpaces.add(space);
                }
            }
            return availableSpaces;
        }
    }

    public static class RoleTypeProvider implements DataProvider {
        @Override
        public Object provide(Object source, Object currentValue) {
            List<RoleType> roles = new ArrayList<RoleType>();
            roles.add(RoleType.STUDENT);
            roles.add(RoleType.TEACHER);
            roles.add(RoleType.ALUMNI);
            return roles;
        }

        @Override
        public Converter getConverter() {
            return new EnumConverter(RoleType.class);
        }
    }

    private Space library;

    private int capacity;

    private int lockers;

    public LibraryInformation() {
    }

    public LibraryInformation(Space library) {
        setLibrary(library);
        setCapacity(library.getAllocatableCapacity());
        setLockers(library.getChildren().size());

    }

    public Space getLibrary() {
        return library;
    }

    public void setLibrary(Space library) {
        this.library = library;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setLockers(int lockers) {
        this.lockers = lockers;
    }

    public int getLockers() {
        return lockers;
    }

}