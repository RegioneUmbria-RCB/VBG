package it.gruppoinit.pal.gp.core.domain.web;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Tipimovimento;

public class TipimovimentoCommand extends BaseCommand {

    public class TipoSoggettoAutocomplete {

	private Integer id;
	private String descrizione;

	public Integer getId() {

	    return id;
	}

	public void setId(Integer id) {

	    this.id = id;
	}

	public String getDescrizione() {

	    return descrizione;
	}

	public void setDescrizione(String descrizione) {

	    this.descrizione = descrizione;
	}
    }

    public TipimovimentoCommand() {

	super();
	this.entity = new Tipimovimento();
    }

    private Tipimovimento entity;
    private Integer codiceMailtipoRicTel;
    private Integer codiceMailtipoComTel;
    // Usato solo per non far schiantare l'autocomplete
    private TipoSoggettoAutocomplete tipoSoggettoAutocomplete = new TipoSoggettoAutocomplete();
    private List<TipiMovSoggettoListItem> soggettiChePossonoEffettuareIlMovimento = new ArrayList<TipiMovSoggettoListItem>();

    public Tipimovimento getEntity() {

	return entity;
    }

    public void setEntity(Tipimovimento entity) {

	this.entity = entity;
    }

    public Integer getCodiceMailtipoRicTel() {

	return codiceMailtipoRicTel;
    }

    public void setCodiceMailtipoRicTel(Integer codiceMailtipoRicTel) {

	this.codiceMailtipoRicTel = codiceMailtipoRicTel;
    }

    public Integer getCodiceMailtipoComTel() {

	return codiceMailtipoComTel;
    }

    public void setCodiceMailtipoComTel(Integer codiceMailtipoComTel) {

	this.codiceMailtipoComTel = codiceMailtipoComTel;
    }

    public List<TipiMovSoggettoListItem> getSoggettiChePossonoEffettuareIlMovimento() {

	return soggettiChePossonoEffettuareIlMovimento;
    }

    public void aggiungiSoggettoCheEffettuaIlMovimento(int id, String descrizione) {

	TipiMovSoggettoListItem item = new TipiMovSoggettoListItem(id, descrizione);
	this.soggettiChePossonoEffettuareIlMovimento.add(item);
    }

    public TipoSoggettoAutocomplete getTipoSoggettoAutocomplete() {

	return tipoSoggettoAutocomplete;
    }
}
