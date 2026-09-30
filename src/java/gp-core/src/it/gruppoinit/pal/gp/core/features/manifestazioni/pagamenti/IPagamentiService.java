package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti;

import java.util.Date;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.exceptions.CreditoBorsellinoInsufficienteException;

public interface IPagamentiService {

    void stornaPresenza(MercatipresenzeD presenza);

    void stornaPresenza(MercatipresenzeD giornata, Autorizzazioni autorizzazione);

    void generaPagamentoPresenza(MercatipresenzeD presenza) throws CreditoBorsellinoInsufficienteException;

    public boolean verificaDataPosDebConcessionariAllaData(Date dataGiornata);

    public boolean nodoPagamentiAttivo(Mercati mercato);

    boolean isPresenzaConPagamentoAnnullabile(Integer idPresenza);
}
