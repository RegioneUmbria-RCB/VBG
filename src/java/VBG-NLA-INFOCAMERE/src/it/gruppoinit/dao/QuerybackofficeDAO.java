package it.gruppoinit.dao;

import java.util.List;

import it.gruppoinit.domain.helper.ModuloHelper;

public interface QuerybackofficeDAO {

    public List<ModuloHelper> findCodici(String idComune, List<String> codici);
}
