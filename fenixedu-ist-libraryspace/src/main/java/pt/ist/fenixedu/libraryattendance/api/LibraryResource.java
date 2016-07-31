package pt.ist.fenixedu.libraryattendance.api;

import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.spaces.domain.Space;
import pt.ist.fenixedu.libraryattendance.api.beans.FenixLibrary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.stream.Collectors;

@Path("/fenix/v1")
public class LibraryResource {

    public final static String JSON_UTF8 = "application/json; charset=utf-8";

    @GET
    @Produces(JSON_UTF8)
    @Path("libraries")
    public List<FenixLibrary> library() {
        return Bennu.getInstance().getLibrariesSet().stream().filter(Space::isActive).map(FenixLibrary::new)
                .collect(Collectors.toList());
    }

}
