package it.alveo.segnalazioniproxy.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SegnalazioneRequest {
    private String utente;
    private Comune comune;
    private Categoria categoria;
    private String stato;
    private String oggetto;
    private String testo;


    @Getter
    @Setter
    @ToString
    public static class Comune {
        private String codice;
        private String descrizione;
    }

    @Getter
    @Setter
    @ToString
    public static class Categoria {
        private int id;
        private String descrizione;
    }
}
