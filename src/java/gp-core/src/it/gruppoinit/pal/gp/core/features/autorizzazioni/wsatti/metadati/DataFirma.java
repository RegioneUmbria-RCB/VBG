package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.metadati;

import java.text.SimpleDateFormat;
import java.util.Date;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniMetadati;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.AutorizzazioniMetadatiId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.IAutorizzazioneMetadato;

public class DataFirma implements IAutorizzazioneMetadato {

    private static String CHIAVE = "WSATTI_DATAFIRMA";
    private Integer idAutorizzazione;
    private Date dataFirma;
    private SimpleDateFormat format;

    public DataFirma(Integer idAutorizzazione, Date dataFirma, SimpleDateFormat format) {

	this.idAutorizzazione = idAutorizzazione;
	this.dataFirma = dataFirma;
	this.format = format;
    }

    @Override
    public AutorizzazioniMetadati toAutorizzazioniMetadati() {

	AutorizzazioniMetadatiId id = new AutorizzazioniMetadatiId(ORMHelper.getIdcomune(), this.idAutorizzazione, CHIAVE);
	AutorizzazioniMetadati metadato = new AutorizzazioniMetadati();
	metadato.setId(id);
	metadato.setValore(this.format.format(this.dataFirma));
	return metadato;
    }
}
