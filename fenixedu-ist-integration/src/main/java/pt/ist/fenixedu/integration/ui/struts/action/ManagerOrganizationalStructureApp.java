/**
 * Copyright © 2013 Instituto Superior Técnico
 *
 * This file is part of FenixEdu IST Integration.
 *
 * FenixEdu IST Integration is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FenixEdu IST Integration is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FenixEdu IST Integration.  If not, see <http://www.gnu.org/licenses/>.
 */
package pt.ist.fenixedu.integration.ui.struts.action;

import org.fenixedu.academic.ui.struts.action.commons.FacesEntryPoint;
import org.fenixedu.bennu.struts.annotations.Mapping;
import org.fenixedu.bennu.struts.portal.StrutsApplication;
import org.fenixedu.bennu.struts.portal.StrutsFunctionality;

@StrutsApplication(path = "organizational-structure", titleKey = "title.manager.organizationalStructureManagement",
        bundle = "ManagerResources", accessGroup = "#managers", hint = "Manager")
public class ManagerOrganizationalStructureApp {
    @StrutsFunctionality(app = ManagerOrganizationalStructureApp.class, path = "manage",
            titleKey = "link.manager.organizationalStructureManagement")
    @Mapping(path = "/organizationalStructureManagament/listAllUnits", module = "manager")
    public static class OrganizationalStructurePage extends FacesEntryPoint {
    }
}
