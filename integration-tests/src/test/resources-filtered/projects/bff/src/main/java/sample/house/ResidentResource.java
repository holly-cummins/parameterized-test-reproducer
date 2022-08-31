package sample.house;

import sample.resident.Resident;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/resident")
public class ResidentResource {

    private Resident createResident(String sessionId) {
        final Resident abby = new Resident();
        return abby;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Resident getResident(@CookieParam("resident-name") String residentName) {
        return createResident(residentName);
    }


}
