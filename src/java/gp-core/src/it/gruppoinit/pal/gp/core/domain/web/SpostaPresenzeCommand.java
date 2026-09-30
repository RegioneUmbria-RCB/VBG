package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.AutorizzazioniComposteSpostaPresenzeDTO;

public class SpostaPresenzeCommand extends BaseCommand {

    private Integer codiceIstanza;
    private AutorizzazioniComposteSpostaPresenzeDTO autorizzazioneSorgente;
    private Integer codiceAutorizzazioneDestinataria;

    public SpostaPresenzeCommand() {
        this.setAutorizzazioneSorgente(new AutorizzazioniComposteSpostaPresenzeDTO());
    }

    public Integer getCodiceIstanza() {
        return codiceIstanza;
    }

    public void setCodiceIstanza(Integer codiceIstanza) {
        this.codiceIstanza = codiceIstanza;
    }

    public AutorizzazioniComposteSpostaPresenzeDTO getAutorizzazioneSorgente() {
        return autorizzazioneSorgente;
    }

    public void setAutorizzazioneSorgente(AutorizzazioniComposteSpostaPresenzeDTO autorizzazioneSorgente) {
        this.autorizzazioneSorgente = autorizzazioneSorgente;
    }

    public Integer getCodiceAutorizzazioneDestinataria() {
        return codiceAutorizzazioneDestinataria;
    }

    public void setCodiceAutorizzazioneDestinataria(Integer codiceAutorizzazioneDestinataria) {
        this.codiceAutorizzazioneDestinataria = codiceAutorizzazioneDestinataria;
    }

}
