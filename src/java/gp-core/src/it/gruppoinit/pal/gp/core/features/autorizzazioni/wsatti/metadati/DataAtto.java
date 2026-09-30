package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.metadati;

import java.text.SimpleDateFormat;
import java.util.Date;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniMetadati;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.AutorizzazioniMetadatiId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.IAutorizzazioneMetadato;

public class DataAtto implements IAutorizzazioneMetadato {

    private static String CHIAVE = "WSATTI_DATAATTO";
    private Integer idAutorizzazione;
    private Date dataAtto;
    private SimpleDateFormat format;

    public DataAtto(Integer idAutorizzazione, Date dataAtto, SimpleDateFormat format) {

	this.idAutorizzazione = idAutorizzazione;
	this.dataAtto = dataAtto;
	this.format = format;
    }

    @Override
    public AutorizzazioniMetadati toAutorizzazioniMetadati() {

	AutorizzazioniMetadatiId id = new AutorizzazioniMetadatiId(ORMHelper.getIdcomune(), this.idAutorizzazione, DataAtto.CHIAVE);
	AutorizzazioniMetadati metadato = new AutorizzazioniMetadati();
	metadato.setId(id);
	metadato.setValore(this.format.format(this.dataAtto));
	return metadato;
    }
}
