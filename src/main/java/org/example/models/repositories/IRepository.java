package org.example.models.repositories;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IRepository<T> {

    List<T> findAllRepository() throws SQLException;

    Optional<T> findByIdRepository(Long id) throws SQLException;

    Optional<T> insertRepository(T object) throws SQLException;

    void updateReposiory(T object) throws SQLException;

    void deleteRepository(Long id) throws SQLException;
}
