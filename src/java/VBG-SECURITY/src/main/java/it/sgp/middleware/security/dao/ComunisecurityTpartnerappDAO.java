package it.sgp.middleware.security.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.sgp.middleware.security.domain.ComunisecurityTpartnerapp;

@Repository
public interface ComunisecurityTpartnerappDAO extends JpaRepository<ComunisecurityTpartnerapp, Integer> {

    List<ComunisecurityTpartnerapp> findByToken(String token);

    List<ComunisecurityTpartnerapp> findByTokenAndCodicecomuneAndSoftware(String token, String codicecomune, String software);
}
