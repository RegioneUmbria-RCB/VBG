package it.sgp.middleware.security.service;

import java.util.List;

import it.sgp.middleware.security.domain.ComunisecurityTpartnerapp;

public interface ComunisecurityTpartnerappService extends BaseService<ComunisecurityTpartnerapp, Integer> {

    public List<ComunisecurityTpartnerapp> findByToken(String token);

    public List<ComunisecurityTpartnerapp> findByTokenComuneESoftware(String token, String codicecomune, String software);

    public String getTokenPartnerAppPerComuneESoftware(String token, String codicecomune, String software);
}
