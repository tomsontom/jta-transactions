package at.bestsolution.quarkstests;

import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class ExampleResource {
    @Inject
    EntityManager em;

    @GET()
    @Path("list")
    @Produces(MediaType.TEXT_PLAIN)
    public String list() {
        return em.createQuery("FROM Gift",Gift.class).getResultStream()
            .map(Gift::toString)
            .collect(Collectors.joining("\n"));
    }

    @GET
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public String create() {
        Gift g = new Gift();
    	g.setName("Test");
    	em.persist(g);
    	em.flush();
    	if( g.getId().intValue() == 2 ) {
    		throw new IllegalStateException();
    	}
        return "create";
    }
}