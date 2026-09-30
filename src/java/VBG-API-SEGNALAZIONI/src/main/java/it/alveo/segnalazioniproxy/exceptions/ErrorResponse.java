package it.alveo.segnalazioniproxy.exceptions;

public class ErrorResponse {
    private String codice;
    private String messaggio;
    private String dettaglio;

    public ErrorResponse(String codice, String messaggio, String dettaglio) {
        this.codice = codice;
        this.messaggio = messaggio;
        this.dettaglio = dettaglio;
    }
}
