package com.example.Trading.repository;

import com.example.Trading.entity.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepo extends CrudRepository<Session,Long> {
    Session findByUserId(Long id);

}
