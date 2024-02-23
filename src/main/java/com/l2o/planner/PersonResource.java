package com.l2o.planner;

import java.util.List;
import java.util.UUID;

import com.l2o.planner.dto.Person;
import com.l2o.planner.dto.PersonResponse;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/v1/person")
public class PersonResource {
    private final PersonService personService;

    public PersonResource(PersonService personService) {
	this.personService = personService;
    }

    @GET
    public Uni<List<PersonResponse>> getAll() {
	return personService.getAll();
    }

    @POST
    public Uni<PersonResponse> create(Person person) {

	return personService.create(person);
    }

    @GET
    @Path("{id}")
    public Uni<PersonResponse> get(@PathParam("id") UUID id) {
	return personService.get(id);
    }

    @DELETE
    @Path("{id}")
    public Uni<Void> delete(@PathParam("id") UUID id) {
	return personService.delete(id);
    }

    @PUT
    @Path("{id}")
    public Uni<PersonResponse> update(@PathParam("id") UUID id, Person person) {
	return personService.update(id, person);
    }
}
