/**
 * Copyright © 2013 Instituto Superior Técnico
 *
 * This file is part of FenixEdu IST Parking.
 *
 * FenixEdu IST Parking is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FenixEdu IST Parking is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FenixEdu IST Parking.  If not, see <http://www.gnu.org/licenses/>.
 */
package pt.ist.fenixedu.parking.domain;

import org.fenixedu.bennu.core.groups.Group;

public class ParkingFile extends ParkingFile_Base {

    public static final long MAX_FILE_SIZE = 3145728; // 3MB

    public ParkingFile() {
        super();
    }

    public ParkingFile(String filename, String displayName, byte[] content, Group permittedGroup) {
        this();
        init(filename, displayName, content, permittedGroup);
    }

    @Override
    public void delete() {
        setNewParkingDocument(null);
        super.delete();
    }
}
