package com.example.samokatclient.repositories.session;

import com.example.samokatclient.entities.session.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends CrudRepository<Session, String> {

}
