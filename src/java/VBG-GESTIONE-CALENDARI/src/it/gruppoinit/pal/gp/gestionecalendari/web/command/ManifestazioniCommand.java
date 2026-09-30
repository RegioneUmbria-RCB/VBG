package it.gruppoinit.pal.gp.gestionecalendari.web.command;

import it.gruppoinit.pal.gp.core.domain.FesteSagre;
import it.gruppoinit.pal.gp.core.domain.FiereMostre;
import it.gruppoinit.pal.gp.core.domain.FiereMostreMerceologie;
import it.gruppoinit.pal.gp.core.domain.FiereMostrePeriodi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ManifestazioniCommand {

    private FesteSagre festeSagre;
    private FiereMostre fiereMostre;
    private String tipoManifestazione;
    private List<FiereMostrePeriodiCommand> periodi;
    private List<FiereMostreMerceologieCommand> merceologie;
    private FiereMostrePeriodi periodo;
    private FiereMostreMerceologie merceologia;
    private FiereMostreMerceologie merceologiaAltro;

    public ManifestazioniCommand() {

    }

    public void init() {

	this.periodi = new ArrayList<FiereMostrePeriodiCommand>();
	this.merceologie = new ArrayList<FiereMostreMerceologieCommand>();
	this.setPeriodo(new FiereMostrePeriodi());
	this.setMerceologia(new FiereMostreMerceologie());
	this.setMerceologiaAltro(new FiereMostreMerceologie());
    }

    public FesteSagre getFesteSagre() {

	return festeSagre;
    }

    public void setFesteSagre(FesteSagre festeSagre) {

	this.festeSagre = festeSagre;
    }

    public FiereMostre getFiereMostre() {

	return fiereMostre;
    }

    public void setFiereMostre(FiereMostre fiereMostre) {

	this.fiereMostre = fiereMostre;
    }

    public String getTipoManifestazione() {

	return tipoManifestazione;
    }

    public void setTipoManifestazione(String tipoManifestazione) {

	this.tipoManifestazione = tipoManifestazione;
    }

    public List<FiereMostrePeriodiCommand> getPeriodi() {

	return periodi;
    }

    public void setPeriodi(List<FiereMostrePeriodiCommand> periodi) {

	this.periodi = periodi;
    }

    public List<FiereMostreMerceologieCommand> getMerceologie() {

	return merceologie;
    }

    public void setMerceologie(List<FiereMostreMerceologieCommand> merceologie) {

	this.merceologie = merceologie;
    }

    public FiereMostrePeriodi getPeriodo() {

	return periodo;
    }

    public void setPeriodo(FiereMostrePeriodi periodo) {

	this.periodo = periodo;
    }

    public FiereMostreMerceologie getMerceologia() {

	return merceologia;
    }

    public void setMerceologia(FiereMostreMerceologie merceologia) {

	this.merceologia = merceologia;
    }

    public void populatePeriodi(Set<FiereMostrePeriodi> entityPeriodi) {

	periodi = new ArrayList<FiereMostrePeriodiCommand>();
	for (FiereMostrePeriodi f : entityPeriodi) {
	    FiereMostrePeriodiCommand c = new FiereMostrePeriodiCommand();
	    c.setDal(f.getDal());
	    c.setAl(f.getAl());
	    periodi.add(c);
	    Collections.sort(periodi, new FiereMostrePeriodiCommandComparator());
	}
    }

    public void populateMerceologie(Set<FiereMostreMerceologie> entityMerceologie) {

	merceologie = new ArrayList<FiereMostreMerceologieCommand>();
	for (FiereMostreMerceologie m : entityMerceologie) {
	    FiereMostreMerceologieCommand f = new FiereMostreMerceologieCommand();
	    f.setMerceologia(m.getMerceologia());
	    merceologie.add(f);
	    Collections.sort(merceologie, new FiereMostreMerceologieCommandComparator());
	}
    }

    public FiereMostreMerceologie getMerceologiaAltro() {

	return merceologiaAltro;
    }

    public void setMerceologiaAltro(FiereMostreMerceologie merceologiaAltro) {

	this.merceologiaAltro = merceologiaAltro;
    }
}
