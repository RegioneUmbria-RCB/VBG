package it.sgp.middleware.security.service;

import java.util.List;

import it.sgp.middleware.security.domain.ComunisecurityParam;

public interface ComunisecurityParamService extends BaseService<ComunisecurityParam, String> {

    List<ComunisecurityParam> findAll();

    ComunisecurityParam findById(String id);
}
