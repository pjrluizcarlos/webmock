package com.example.webmock.data.mock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MockDAO extends JpaRepository<MockEntity, Long> {

    Optional<MockEntity> findByUriAndMethod(String uri, String method);
    boolean existsByUriAndMethod(String uri, String method);
    void deleteAllByUriAndMethod(String uri, String method);

}
