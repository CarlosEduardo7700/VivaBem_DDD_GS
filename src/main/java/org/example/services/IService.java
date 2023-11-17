package org.example.services;

import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

public interface IService<T> {

    Response findAllService() throws SQLException;

    Response findByIdService(Long id) throws SQLException;

    Response insertService(T object) throws SQLException;

    Response updateService(Long id, T object) throws SQLException;

    Response deleteService(Long id) throws SQLException;
}
