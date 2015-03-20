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
package pt.ist.fenixedu.integration.ui.struts.action.externalServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.fenixedu.bennu.struts.annotations.Mapping;
import org.fenixedu.cms.domain.Category;

import pt.ist.fenixframework.DomainObject;
import pt.ist.fenixframework.FenixFramework;

@Mapping(path = "/announcementsRSS", module = "external")
public class LegacyRSSAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        DomainObject obj = FenixFramework.getDomainObject(request.getParameter("announcementBoardId"));
        if (obj instanceof Category && FenixFramework.isDomainObjectValid(obj)) {
            Category cat = (Category) obj;
            response.setHeader("Location", cat.getRssUrl());
            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            return null;
        }
        response.sendError(404);
        return null;
    }
}
