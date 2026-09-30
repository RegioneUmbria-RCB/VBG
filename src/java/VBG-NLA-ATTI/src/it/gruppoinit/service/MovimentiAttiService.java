package it.gruppoinit.service;

import it.gruppoinit.domain.helper.MovimentiAtti;

public interface MovimentiAttiService {

    public void insert(MovimentiAtti movimentiAtti);

    public int findMaxId(String idcomune);

    /**
     * Ricerca movimenti atti per cocice movimento
     * 
     * @param codice
     * @return
     */
    public MovimentiAtti findByMovimento(int codice, String idcomune);

    public void update(MovimentiAtti movimentiAtti);

    public void insertCodiceOggetto(Integer codiceOggetto, Integer codice, String idcomune);
}
