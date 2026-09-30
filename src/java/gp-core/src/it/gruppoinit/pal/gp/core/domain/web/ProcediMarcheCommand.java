/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain.web;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.helper.ProcedimentoProcediMarche;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.procedimarche.IdDescrizione;

/**
 * @author francol
 *
 */
public class ProcediMarcheCommand extends BaseCommand {

    Integer idProcedimento = null;
    Integer idInventarioproc = null;
    ProcedimentoProcediMarche procedimento = null;
    List<ProcedimentoProcediMarche> elencoProcedimenti = new ArrayList<ProcedimentoProcediMarche>();
    List<IdDescrizione> serieArchivistiche = new ArrayList<IdDescrizione>();
    List<IdDescrizione> tipiFascicolo = new ArrayList<IdDescrizione>();
    boolean canEdit = false;

    public Integer getIdProcedimento() {

	return idProcedimento;
    }

    public void setIdProcedimento(Integer idProcedimento) {

	this.idProcedimento = idProcedimento;
    }

    public Integer getIdInventarioproc() {

	return idInventarioproc;
    }

    public void setIdInventarioproc(Integer idInventarioproc) {

	this.idInventarioproc = idInventarioproc;
    }

    public ProcedimentoProcediMarche getProcedimento() {

	return procedimento;
    }

    public void setProcedimento(ProcedimentoProcediMarche procedimento) {

	this.procedimento = procedimento;
    }

    public List<ProcedimentoProcediMarche> getElencoProcedimenti() {

	return elencoProcedimenti;
    }

    public void setElencoProcedimenti(List<ProcedimentoProcediMarche> elencoProcedimenti) {

	this.elencoProcedimenti = elencoProcedimenti;
    }

    public List<IdDescrizione> getSerieArchivistiche() {

	return serieArchivistiche;
    }

    public void setSerieArchivistiche(List<IdDescrizione> serieArchivistiche) {

	this.serieArchivistiche = serieArchivistiche;
    }

    public List<IdDescrizione> getTipiFascicolo() {

	return tipiFascicolo;
    }

    public void setTipiFascicolo(List<IdDescrizione> tipiFascicolo) {

	this.tipiFascicolo = tipiFascicolo;
    }

    public boolean isCanEdit() {

	return canEdit;
    }

    public void setCanEdit(boolean canEdit) {

	this.canEdit = canEdit;
    }
}
