package it.sgp.middleware.security.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BaseDAO {

    @Modifying
    @Query(value = "commit", nativeQuery = true)
    void commit();
}
