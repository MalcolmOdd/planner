package com.l2o.planner;

import java.util.List;
import java.util.UUID;

import org.hibernate.reactive.mutiny.Mutiny;

import com.l2o.planner.db.DbPerson;
import com.l2o.planner.dto.Person;
import com.l2o.planner.dto.PersonResponse;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaQuery;

@ApplicationScoped
public class PersonService {
    @Inject
    private Mutiny.SessionFactory sf;

    public Uni<List<PersonResponse>> getAll() {
	CriteriaQuery<DbPerson> query = sf.getCriteriaBuilder().createQuery(DbPerson.class);
	query.select(query.from(DbPerson.class));
	return sf.withSession(session -> session.createQuery(query).getResultList()
		.map(list -> list.stream().map(DbPerson::toResponse).toList()));
    }

    public Uni<PersonResponse> create(Person person) {
	DbPerson entity = new DbPerson(person);
	return sf.withTransaction(session -> session.persist(entity)).map(unused -> entity.toResponse());
    }

    public Uni<PersonResponse> get(UUID id) {
	return sf.withSession(session -> session.find(DbPerson.class, id)).map(DbPerson::toResponse);
    }

    public Uni<Void> delete(UUID id) {
	return sf.withTransaction(session -> session.find(DbPerson.class, id).flatMap(session::remove));
    }

    public Uni<PersonResponse> update(UUID id, Person person) {
	return sf
		.withTransaction(session -> session.find(DbPerson.class, id).map(dbEntity -> dbEntity.copyFrom(person)))
		.map(DbPerson::toResponse);
    }
}
