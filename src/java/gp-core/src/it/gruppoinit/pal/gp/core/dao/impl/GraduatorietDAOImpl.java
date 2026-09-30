package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.BandiinputDAO;
import it.gruppoinit.pal.gp.core.dao.GraduatoriedDAO;
import it.gruppoinit.pal.gp.core.dao.GraduatorietDAO;
import it.gruppoinit.pal.gp.core.dao.Istanzedyn2datiDAO;
import it.gruppoinit.pal.gp.core.dao.TipibandooutputDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.GraduatorieDyn2DatiArrayComparator;
import it.gruppoinit.pal.gp.core.dao.helper.GraduatorieDyn2DatiComparator;
import it.gruppoinit.pal.gp.core.domain.Bandiinput;
import it.gruppoinit.pal.gp.core.domain.Campigraduatoria;
import it.gruppoinit.pal.gp.core.domain.Graduatoried;
import it.gruppoinit.pal.gp.core.domain.Graduatoriet;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipibandocampigraduat;
import it.gruppoinit.pal.gp.core.domain.Tipibandooutput;
import it.gruppoinit.pal.gp.core.domain.Tipigraduatoried;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GraduatorietDAOImpl extends BaseDAOImpl<Graduatoriet, PkId> implements GraduatorietDAO {

    private static final Logger log = LoggerFactory.getLogger(GraduatorietDAOImpl.class);
    @Autowired
    private GraduatoriedDAO graduatoriedDAO;
    @Autowired
    private Istanzedyn2datiDAO istanzedyn2datiDAO;
    @Autowired
    private TipibandooutputDAO tipibandooutputDAO;
    @Autowired
    private BandiinputDAO bandiinputDAO;

    @Override
    public List<Graduatoriet> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public List<Tipibandooutput> findTipiBandiOutput(Integer tipiGraduatorieId) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipigraduatorietId", tipiGraduatorieId, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id.codice"));
	List<Tipibandooutput> tipibandooutputs = tipibandooutputDAO.findByFilterTable(ft);
	return tipibandooutputs;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void compilaGraduatoriaSingoloIntervento(Graduatoriet entity) {

	// §§§BEGIN§§§
	Set<Tipibandocampigraduat> tipibandocampigraduats = entity.getTipigraduatoriet().getTipibandocampigraduats();
	int numeroDiValoriOrdinamento = tipibandocampigraduats.size();
	Integer[] ordinamentoAscDesc = new Integer[numeroDiValoriOrdinamento];
	Object[] values = new Object[] { entity.getId().getCodice(), entity.getId().getIdcomune() };
	List dynList = this.findListaDyn2DatiPerGraduatoria(entity, ordinamentoAscDesc, values);
	flush();
	clear();
	/**
	 * inserisco le informazioni recuperate al passo precedente in GRADUATORIED e CAMPIGRADUATORIA
	 */
	Campigraduatoria campigraduatoria = null;
	Graduatoried graduatoried = null;
	Set<Campigraduatoria> campigraduatorias = new HashSet<Campigraduatoria>(0);
	// BEGIN ORDINAMENTO SOFTWARE
	// SI ASSUME CHE I VALORI DA ORDINARE SIANO RICONDUCIBILI A VALORI NUMERICI
	// ES. DATE IN YYYYMMDD
	if (numeroDiValoriOrdinamento > 1) {
	    Collections.sort(dynList, new GraduatorieDyn2DatiArrayComparator(ordinamentoAscDesc));
	} else {
	    Collections.sort(dynList, new GraduatorieDyn2DatiComparator(ordinamentoAscDesc[0]));
	}
	// END BEGIN ORDINAMENTO SOFTWARE
	int pos = 1;/* indice utilizzato per salvare la posizione in graduatoria */
	Istanze istanze = null;
	for (Iterator iterator = dynList.iterator(); iterator.hasNext();) {
	    Object obj = iterator.next();
	    graduatoried = new Graduatoried();
	    graduatoried.setGraduatoriet(entity);
	    graduatoried.setPosizione(pos);
	    if (obj instanceof Object[]) {
		Object[] istanzedyn2dati = (Object[]) obj;
		for (int j = 0; j < istanzedyn2dati.length; j++) {
		    campigraduatoria = new Campigraduatoria();
		    campigraduatoria.setDyn2Campi(((Istanzedyn2dati) istanzedyn2dati[j]).getDyn2Campi());
		    campigraduatoria.setGraduatoried(graduatoried);
		    campigraduatoria.setValore(((Istanzedyn2dati) istanzedyn2dati[j]).getValore());
		    campigraduatoria.setOrdine(j);
		    campigraduatorias.add(campigraduatoria);
		    istanze = ((Istanzedyn2dati) istanzedyn2dati[j]).getIstanza();
		}
	    } else {
		Istanzedyn2dati istanzedyn2dati = (Istanzedyn2dati) obj;
		campigraduatoria = new Campigraduatoria();
		campigraduatoria.setDyn2Campi(istanzedyn2dati.getDyn2Campi());
		campigraduatoria.setGraduatoried(graduatoried);
		campigraduatoria.setValore(istanzedyn2dati.getValore());
		campigraduatoria.setOrdine(1);
		campigraduatorias.add(campigraduatoria);
		istanze = istanzedyn2dati.getIstanza();
	    }
	    graduatoried.setIstanza(istanze);
	    graduatoried.setCampigraduatorias(campigraduatorias);
	    graduatoriedDAO.insert(graduatoried);
	    pos++;
	}
	flush();
	clear();
	/**
	 * -DISTRIBUZIONE- eseguo la distribuzione dei valori in base ai tipocalcolo configurati per quel tipo di bando
	 */
	//	List<Tipibandooutput> tipibandooutputs = this.findTipiBandiOutput(entity.getTipigraduatoriet().getId().getCodice());
	//	List<Bandiinput> bandiinputs = this.findTipiBandiInput(entity.getBandi().getId().getCodice());
	//	// itero sui tipi di calcolo
	//	for (Iterator iterator = tipibandooutputs.iterator(); iterator.hasNext();) {
	//	    Tipibandooutput tipibandooutput = (Tipibandooutput) iterator.next();
	//	    BigDecimal valoreDaDistribuire = new BigDecimal(0);
	//	    // recupero il valore da distribuire
	//	    for (Iterator iterator2 = bandiinputs.iterator(); iterator2.hasNext();) {
	//		Bandiinput bandiinput = (Bandiinput) iterator2.next();
	//		if (bandiinput.getTipibandoinput().getId().equals(tipibandooutput.getTipibandoinput().getId())) {
	//		    valoreDaDistribuire = bandiinput.getValore();
	//		    break;
	//		}
	//	    }
	//	    // applico il tipo di calcolo
	//	    if (tipibandooutput.getTipocalcolo().equals(WebConstants.BANDI_TIPOCALCOLO_ELEMENTO)
	//		    || tipibandooutput.getTipocalcolo().equals(WebConstants.BANDI_TIPOCALCOLO_VALORE)) {
	//		values = new Object[] { entity.getId().getIdcomune(), entity.getId().getCodice() };
	//		List<Graduatoried> graduatorieds = getHibernateTemplate().find(
	//			"FROM Graduatoried _graduatoried WHERE _graduatoried.graduatoriet.id.idcomune=? "
	//				+ " AND _graduatoried.graduatoriet.id.codice=? order by _graduatoried.posizione ASC", values);
	//		BigDecimal valoreRimanente = valoreDaDistribuire;
	//		for (Iterator iterator2 = graduatorieds.iterator(); iterator2.hasNext();) {
	//		    Graduatoried graduatoried2 = (Graduatoried) iterator2.next();
	//		    values = new Object[] { graduatoried2.getId().getIdcomune(), graduatoried2.getIstanza().getId().getCodice(),
	//			    tipibandooutput.getDyn2CampiOut().getId().getCodice() };
	//		    List<Istanzedyn2dati> istanzedyn2datisOut = getHibernateTemplate().find(
	//			    "FROM Istanzedyn2dati i WHERE i.id.idcomune=? AND i.id.codiceistanza=? AND i.id.fkD2cId=?", values);
	//		    List<Istanzedyn2dati> istanzedyn2datisRif = new ArrayList<Istanzedyn2dati>();
	//		    if (tipibandooutput.getTipocalcolo().equals(WebConstants.BANDI_TIPOCALCOLO_VALORE)) {
	//			values = new Object[] { graduatoried2.getId().getIdcomune(), graduatoried2.getIstanza().getId().getCodice(),
	//				tipibandooutput.getDyn2CampiRif().getId().getCodice() };
	//			istanzedyn2datisRif = getHibernateTemplate().find(
	//				"FROM Istanzedyn2dati i WHERE i.id.idcomune=? AND i.id.codiceistanza=? AND i.id.fkD2cId=?", values);
	//		    }
	//		    Istanzedyn2dati istanzedyn2datiOut;
	//		    if (istanzedyn2datisOut.size() == 0) {
	//			istanzedyn2datiOut = new Istanzedyn2dati();
	//			istanzedyn2datiOut.getId().setFkD2cId(tipibandooutput.getDyn2CampiOut().getId().getCodice());
	//			istanzedyn2datiOut.getId().setCodiceistanza(graduatoried2.getIstanza().getId().getCodice());
	//			istanzedyn2datiOut.getId().setIndice(0);
	//			istanzedyn2datiOut.getId().setIndiceMolteplicita(0);
	//			istanzedyn2datiOut.setIstanza(graduatoried2.getIstanza());
	//			istanzedyn2datiOut.setDyn2Campi(tipibandooutput.getDyn2CampiOut());
	//		    } else {
	//			istanzedyn2datiOut = istanzedyn2datisOut.get(0);
	//		    }
	//		    if (tipibandooutput.getTipocalcolo().equals(WebConstants.BANDI_TIPOCALCOLO_VALORE)) {
	//			if (!(istanzedyn2datisRif == null || istanzedyn2datisRif.size() == 0)) {
	//			    BigDecimal valoreRif = new BigDecimal(istanzedyn2datisRif.get(0).getValore().replace(',', '.'));
	//			    BigDecimal diff = valoreRimanente.subtract(valoreRif);
	//			    if (log.isDebugEnabled()) {
	//				log.debug("\nTOTALE: " + valoreDaDistribuire + "\nRICHIESTO: " + valoreRif + "\nRIMANENZA: " + valoreRimanente
	//					+ "\nDIFF(RIMANENZA - RICHIESTO): " + diff + "\n\n");
	//			    }
	//			    if (diff.compareTo(new BigDecimal(0)) >= 0) {
	//				istanzedyn2datiOut.setValore(istanzedyn2datisRif.get(0).getValore());
	//				valoreRimanente = diff;
	//				if (log.isDebugEnabled()) {
	//				    log.debug("\nISTANZA: " + graduatoried2.getIstanza().getNumeroistanza() + "\nPOSIZIONE: "
	//					    + graduatoried2.getPosizione() + "\nCAMPO: " + istanzedyn2datiOut.getDyn2Campi().getId().getCodice()
	//					    + "-" + istanzedyn2datiOut.getDyn2Campi().getNomecampo() + "\nASSEGNATO: "
	//					    + istanzedyn2datisRif.get(0).getValore() + "\nRIMANENZA: " + valoreRimanente + "\n");
	//				}
	//			    } else {
	//				istanzedyn2datiOut.setValore(valoreRimanente.toPlainString());
	//				valoreRimanente = new BigDecimal(0);
	//				if (log.isDebugEnabled()) {
	//				    log.debug("\nISTANZA: " + graduatoried2.getIstanza().getNumeroistanza() + "\nPOSIZIONE: "
	//					    + graduatoried2.getPosizione() + "\nCAMPO: " + istanzedyn2datiOut.getDyn2Campi().getId().getCodice()
	//					    + "-" + istanzedyn2datiOut.getDyn2Campi().getNomecampo() + "\nASSEGNATO: "
	//					    + valoreRimanente.toPlainString() + "\nRIMANENZA: " + valoreRimanente + "\n");
	//				}
	//			    }
	//			} else {
	//			    String message = "La pratica (" + graduatoried2.getIstanza().getNumeroistanza();
	//			    message += ") non ha inserito un valore obbligatorio nel campo (" + tipibandooutput.getDyn2CampiRif().getEtichetta()
	//				    + "-" + tipibandooutput.getDyn2CampiRif().getNomecampo() + ") delle schede per l'assegnazione dei valori";
	//			    log.error(message);
	//			    throw new BusinessValidationException(message);
	//			}
	//		    } else if (tipibandooutput.getTipocalcolo().equals(WebConstants.BANDI_TIPOCALCOLO_ELEMENTO)) {
	//			BigDecimal uno = new BigDecimal(1);
	//			if (valoreRimanente.compareTo(uno) >= 0) {
	//			    istanzedyn2datiOut.setValore("1");
	//			} else {
	//			    istanzedyn2datiOut.setValore("0");
	//			}
	//			valoreRimanente = (valoreRimanente.subtract(uno).compareTo(new BigDecimal(0)) > 0) ? valoreRimanente.subtract(uno)
	//				: new BigDecimal(0);
	//			if (log.isDebugEnabled()) {
	//			    log.debug("\nISTANZA: " + graduatoried2.getIstanza().getNumeroistanza() + "\nPOSIZIONE: " + graduatoried2.getPosizione()
	//				    + "\nCAMPO: " + istanzedyn2datiOut.getDyn2Campi().getId().getCodice() + "-"
	//				    + istanzedyn2datiOut.getDyn2Campi().getNomecampo() + "\nTOTALE: " + valoreDaDistribuire + "\nASSEGNATO: "
	//				    + istanzedyn2datiOut.getValore() + "\nRIMANENZA: " + valoreRimanente + "\n");
	//			}
	//		    } else {
	//			// tipo calcolo non corretto
	//			throw new NotImplementedException("Tipo calcolo non corretto.");
	//		    }
	//		    istanzedyn2datiDAO.update(istanzedyn2datiOut);
	//		    flush();
	//		    clear();
	//		}
	//	    } else if (tipibandooutput.getTipocalcolo().equals(WebConstants.BANDI_TIPOCALCOLO_MERCATI)) {
	//		// TODO manca la logica per questo tipo di calcolo
	//		throw new NotImplementedException("Il tipo calcolo MERCATI non è implementato.");
	//	    } else {
	//		// tipo calcolo inesistente
	//		throw new NotImplementedException("Tipo calcolo inesistente.");
	//	    }
	//	}
	/**
	 * -DISTRIBUZIONE- eseguo la distribuzione dei valori in base ai tipocalcolo configurati per quel tipo di bando
	 */
	this.updateDistribuisciValoriGraduatorie(entity);
	// §§§END§§§
    }

    @Override
    public void updateDistribuisciValoriGraduatorie(Graduatoriet entity) {

	/**
	 * -DISTRIBUZIONE- eseguo la distribuzione dei valori in base ai tipocalcolo configurati per quel tipo di bando
	 */
	Object[] values = new Object[] { entity.getId().getCodice(), entity.getId().getIdcomune() };
	List<Tipibandooutput> tipibandooutputs = this.findTipiBandiOutput(entity.getTipigraduatoriet().getId().getCodice());
	List<Bandiinput> bandiinputs = this.findTipiBandiInput(entity.getBandi().getId().getCodice());
	// itero sui tipi di calcolo
	for (Iterator iterator = tipibandooutputs.iterator(); iterator.hasNext();) {
	    Tipibandooutput tipibandooutput = (Tipibandooutput) iterator.next();
	    BigDecimal valoreDaDistribuire = new BigDecimal(0);
	    // recupero il valore da distribuire
	    for (Iterator iterator2 = bandiinputs.iterator(); iterator2.hasNext();) {
		Bandiinput bandiinput = (Bandiinput) iterator2.next();
		if (bandiinput.getTipibandoinput().getId().equals(tipibandooutput.getTipibandoinput().getId())) {
		    valoreDaDistribuire = bandiinput.getValore();
		    break;
		}
	    }
	    // applico il tipo di calcolo
	    if (tipibandooutput.getTipocalcolo().equals(WebConstants.BANDI_TIPOCALCOLO_ELEMENTO)
		    || tipibandooutput.getTipocalcolo().equals(WebConstants.BANDI_TIPOCALCOLO_VALORE)) {
		values = new Object[] { entity.getId().getIdcomune(), entity.getId().getCodice() };
		List<Graduatoried> graduatorieds = getHibernateTemplate().find(
			"FROM Graduatoried _graduatoried WHERE _graduatoried.graduatoriet.id.idcomune=? "
				+ " AND _graduatoried.graduatoriet.id.codice=? order by _graduatoried.posizione ASC", values);
		BigDecimal valoreRimanente = valoreDaDistribuire;
		for (Iterator iterator2 = graduatorieds.iterator(); iterator2.hasNext();) {
		    Graduatoried graduatoried2 = (Graduatoried) iterator2.next();
		    values = new Object[] { graduatoried2.getId().getIdcomune(), graduatoried2.getIstanza().getId().getCodice(),
			    tipibandooutput.getDyn2CampiOut().getId().getCodice() };
		    List<Istanzedyn2dati> istanzedyn2datisOut = getHibernateTemplate().find(
			    "FROM Istanzedyn2dati i WHERE i.id.idcomune=? AND i.id.codiceistanza=? AND i.id.fkD2cId=?", values);
		    List<Istanzedyn2dati> istanzedyn2datisRif = new ArrayList<Istanzedyn2dati>();
		    if (tipibandooutput.getTipocalcolo().equals(WebConstants.BANDI_TIPOCALCOLO_VALORE)) {
			values = new Object[] { graduatoried2.getId().getIdcomune(), graduatoried2.getIstanza().getId().getCodice(),
				tipibandooutput.getDyn2CampiRif().getId().getCodice() };
			istanzedyn2datisRif = getHibernateTemplate().find(
				"FROM Istanzedyn2dati i WHERE i.id.idcomune=? AND i.id.codiceistanza=? AND i.id.fkD2cId=?", values);
		    }
		    Istanzedyn2dati istanzedyn2datiOut;
		    if (istanzedyn2datisOut.size() == 0) {
			istanzedyn2datiOut = new Istanzedyn2dati();
			istanzedyn2datiOut.getId().setFkD2cId(tipibandooutput.getDyn2CampiOut().getId().getCodice());
			istanzedyn2datiOut.getId().setCodiceistanza(graduatoried2.getIstanza().getId().getCodice());
			istanzedyn2datiOut.getId().setIndice(0);
			istanzedyn2datiOut.getId().setIndiceMolteplicita(0);
			istanzedyn2datiOut.setIstanza(graduatoried2.getIstanza());
			istanzedyn2datiOut.setDyn2Campi(tipibandooutput.getDyn2CampiOut());
		    } else {
			istanzedyn2datiOut = istanzedyn2datisOut.get(0);
		    }
		    if (tipibandooutput.getTipocalcolo().equals(WebConstants.BANDI_TIPOCALCOLO_VALORE)) {
			if (!(istanzedyn2datisRif == null || istanzedyn2datisRif.size() == 0)) {
			    BigDecimal valoreRif = new BigDecimal(istanzedyn2datisRif.get(0).getValore().replace(',', '.'));
			    BigDecimal diff = valoreRimanente.subtract(valoreRif);
			    if (log.isDebugEnabled()) {
				log.debug("\nTOTALE: " + valoreDaDistribuire + "\nRICHIESTO: " + valoreRif + "\nRIMANENZA: " + valoreRimanente
					+ "\nDIFF(RIMANENZA - RICHIESTO): " + diff + "\n\n");
			    }
			    if (diff.compareTo(new BigDecimal(0)) >= 0) {
				istanzedyn2datiOut.setValore(istanzedyn2datisRif.get(0).getValore());
				valoreRimanente = diff;
				if (log.isDebugEnabled()) {
				    log.debug("\nISTANZA: " + graduatoried2.getIstanza().getNumeroistanza() + "\nPOSIZIONE: "
					    + graduatoried2.getPosizione() + "\nCAMPO: " + istanzedyn2datiOut.getDyn2Campi().getId().getCodice()
					    + "-" + istanzedyn2datiOut.getDyn2Campi().getNomecampo() + "\nASSEGNATO: "
					    + istanzedyn2datisRif.get(0).getValore() + "\nRIMANENZA: " + valoreRimanente + "\n");
				}
			    } else {
				istanzedyn2datiOut.setValore(valoreRimanente.toPlainString());
				valoreRimanente = new BigDecimal(0);
				if (log.isDebugEnabled()) {
				    log.debug("\nISTANZA: " + graduatoried2.getIstanza().getNumeroistanza() + "\nPOSIZIONE: "
					    + graduatoried2.getPosizione() + "\nCAMPO: " + istanzedyn2datiOut.getDyn2Campi().getId().getCodice()
					    + "-" + istanzedyn2datiOut.getDyn2Campi().getNomecampo() + "\nASSEGNATO: "
					    + valoreRimanente.toPlainString() + "\nRIMANENZA: " + valoreRimanente + "\n");
				}
			    }
			} else {
			    String message = "La pratica (" + graduatoried2.getIstanza().getNumeroistanza();
			    message += ") non ha inserito un valore obbligatorio nel campo (" + tipibandooutput.getDyn2CampiRif().getEtichetta()
				    + "-" + tipibandooutput.getDyn2CampiRif().getNomecampo() + ") delle schede per l'assegnazione dei valori";
			    log.error(message);
			    throw new BusinessValidationException(message);
			}
		    } else if (tipibandooutput.getTipocalcolo().equals(WebConstants.BANDI_TIPOCALCOLO_ELEMENTO)) {
			BigDecimal uno = new BigDecimal(1);
			if (valoreRimanente.compareTo(uno) >= 0) {
			    istanzedyn2datiOut.setValore("1");
			} else {
			    istanzedyn2datiOut.setValore("0");
			}
			valoreRimanente = (valoreRimanente.subtract(uno).compareTo(new BigDecimal(0)) > 0) ? valoreRimanente.subtract(uno)
				: new BigDecimal(0);
			if (log.isDebugEnabled()) {
			    log.debug("\nISTANZA: " + graduatoried2.getIstanza().getNumeroistanza() + "\nPOSIZIONE: " + graduatoried2.getPosizione()
				    + "\nCAMPO: " + istanzedyn2datiOut.getDyn2Campi().getId().getCodice() + "-"
				    + istanzedyn2datiOut.getDyn2Campi().getNomecampo() + "\nTOTALE: " + valoreDaDistribuire + "\nASSEGNATO: "
				    + istanzedyn2datiOut.getValore() + "\nRIMANENZA: " + valoreRimanente + "\n");
			}
		    } else {
			// tipo calcolo non corretto
			throw new NotImplementedException("Tipo calcolo non corretto.");
		    }
		    istanzedyn2datiDAO.update(istanzedyn2datiOut);
		    flush();
		    clear();
		}
	    } else if (tipibandooutput.getTipocalcolo().equals(WebConstants.BANDI_TIPOCALCOLO_MERCATI)) {
		// TODO manca la logica per questo tipo di calcolo
		throw new NotImplementedException("Il tipo calcolo MERCATI non è implementato.");
	    } else {
		// tipo calcolo inesistente
		throw new NotImplementedException("Tipo calcolo inesistente.");
	    }
	}
    }

    @Override
    public List<Bandiinput> findTipiBandiInput(Integer bandiId) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("bandiId", bandiId, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id.codice"));
	List<Bandiinput> bandiinputs = bandiinputDAO.findByFilterTable(ft);
	return bandiinputs;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Graduatoriet> findByFilter(Graduatoriet entity) {

	// §§§BEGIN§§§
	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("bandi.id", entity.getBandi().getId()));
	List<Graduatoriet> gList = getHibernateTemplate().findByCriteria(det);
	return gList;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public Class<Graduatoriet> getEntityClass() {

	return Graduatoriet.class;
    }

    @Override
    public List findListaDyn2DatiPerGraduatoria(Graduatoriet entity, Integer[] ordinamentoAscDesc, Object[] values) {

	// §§§BEGIN§§§
	StringBuilder hqlOrderValori = new StringBuilder("");
	String aliasOrder = "";
	StringBuilder hqlOrderJoin = new StringBuilder("");
	StringBuilder hqlOrderAND = new StringBuilder("");
	String aliasFilter = "";
	StringBuilder hqlFilterJoin = new StringBuilder("");
	StringBuilder hqlFilterAND = new StringBuilder("");
	/**
	 * -SELEZIONE E ORDINAMENTO- recupero la lista di oggetti istanzedyn2dati contenenti i valori dei campi
	 * individuati per la graduatoria, ordinata in base ai criteri in TIPIBANDOCAMPIGRADUAT.
	 */
	Set<Tipibandocampigraduat> tipibandocampigraduats = entity.getTipigraduatoriet().getTipibandocampigraduats();
	String ordinamento = "";
	int i = 0;
	// itero sui campi per l'ordinamento
	Tipibandocampigraduat tipibandocampigraduat;
	for (Iterator iterator = tipibandocampigraduats.iterator(); iterator.hasNext();) {
	    tipibandocampigraduat = (Tipibandocampigraduat) iterator.next();
	    aliasOrder = " _istanzedyn2dati" + i;
	    hqlOrderValori.append(aliasOrder).append(", ");
	    hqlOrderJoin.append(" left join _istanzes.istanzedyn2datis ").append(aliasOrder).append(" ");
	    hqlOrderAND.append(" AND ").append(aliasOrder).append(".id.fkD2cId = ").append(tipibandocampigraduat.getDyn2Campi().getId().getCodice());
	    ordinamento = tipibandocampigraduat.getOrdinamentoascdesc();
	    if (ordinamento.equals("ASC")) {
		ordinamentoAscDesc[i] = Integer.valueOf(GraduatorieDyn2DatiComparator.ORDER_ASC);
	    } else {
		ordinamentoAscDesc[i] = Integer.valueOf(GraduatorieDyn2DatiComparator.ORDER_DESC);
	    }
	    i++;
	}
	// itero sui campi per i criteri d'ammissione
	Tipigraduatoried tipigraduatoried;
	int objectSize = 2;
	if (!entity.getTipigraduatoriet().getTipigraduatorieds().isEmpty()) {
	    objectSize += entity.getTipigraduatoriet().getTipigraduatorieds().size();
	}
	if (entity.getBandi().getDataDalla() != null) {
	    objectSize += 1;
	}
	if (entity.getBandi().getDataAlla() != null) {
	    objectSize += 1;
	}
	values = new Object[objectSize];
	values[0] = entity.getId().getCodice();
	values[1] = entity.getId().getIdcomune();
	int a = 2;
	Date allaData = entity.getBandi().getDataAlla();
	Date dallaData = entity.getBandi().getDataDalla();
	if (dallaData != null) {
	    Calendar dallaDataC = Calendar.getInstance();
	    dallaDataC.setTime(dallaData);
	    dallaDataC.set(Calendar.HOUR, 0);
	    dallaDataC.set(Calendar.MINUTE, 0);
	    dallaDataC.set(Calendar.SECOND, 0);
	    values[a] = dallaDataC.getTime();
	    a++;
	}
	if (allaData != null) {
	    Calendar allaDataC = Calendar.getInstance();
	    allaDataC.setTime(allaData);
	    allaDataC.set(Calendar.HOUR, 23);
	    allaDataC.set(Calendar.MINUTE, 59);
	    allaDataC.set(Calendar.SECOND, 59);
	    values[a] = allaDataC.getTime();
	    a++;
	}
	for (Iterator iterator = entity.getTipigraduatoriet().getTipigraduatorieds().iterator(); iterator.hasNext();) {
	    tipigraduatoried = (Tipigraduatoried) iterator.next();
	    aliasFilter = " _istanzedyn2dati" + i;
	    hqlFilterJoin.append(" inner join _istanzes.istanzedyn2datis ").append(aliasFilter).append(" ");
	    hqlFilterAND.append(" AND ").append(aliasFilter).append(".id.fkD2cId = ").append(tipigraduatoried.getDyn2Campi().getId().getCodice())
		    .append(" AND ").append(aliasFilter).append(".valore like ? ");
	    values[a] = tipigraduatoried.getValore();
	    a++;
	    i++;
	}
	String lclHqlOrderValori = hqlOrderValori.toString();
	if (lclHqlOrderValori.indexOf(",") >= 0) {
	    lclHqlOrderValori = lclHqlOrderValori.substring(0, lclHqlOrderValori.lastIndexOf(","));
	}
	String hqlQuery = "SELECT " + lclHqlOrderValori + " FROM Graduatoriet _graduatoriet " +
			  " inner join _graduatoriet.bandi as _bandi inner join _bandi.alberoproc _alberoproc " +
			  " inner join _alberoproc.istanzes _istanzes " + hqlOrderJoin.toString() + hqlFilterJoin.toString() + " WHERE " +
			  " _graduatoriet.id.codice = ? AND  _graduatoriet.id.idcomune = ? ";
	if (dallaData != null) {
	    hqlQuery += " AND _istanzes.data >= ? ";
	}
	if (allaData != null) {
	    hqlQuery += " AND _istanzes.data <= ? ";
	}
	hqlQuery += hqlOrderAND.toString() + hqlFilterAND.toString();
	return getHibernateTemplate().find(hqlQuery, values);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List findListaDyn2DatiPerGraduatoriaPerIntervento(Graduatoriet entity, Integer[] ordinamentoAscDesc, Object[] values,
	    Integer codiceIntervento) {

	// §§§BEGIN§§§
	StringBuffer hqlOrderValori = new StringBuffer("");
	String aliasOrder = "";
	StringBuffer hqlOrderJoin = new StringBuffer("");
	StringBuffer hqlOrderAND = new StringBuffer("");
	// String hqlFilterValori = "";
	String aliasFilter = "";
	StringBuffer hqlFilterJoin = new StringBuffer("");
	StringBuffer hqlFilterAND = new StringBuffer("");
	/**
	 * -SELEZIONE E ORDINAMENTO- recupero la lista di oggetti istanzedyn2dati contenenti i valori dei campi
	 * individuati per la graduatoria, ordinata in base ai criteri in TIPIBANDOCAMPIGRADUAT.
	 */
	Set<Tipibandocampigraduat> tipibandocampigraduats = entity.getTipigraduatoriet().getTipibandocampigraduats();
	String ordinamento = "";
	int i = 0;
	// itero sui campi per l'ordinamento
	Tipibandocampigraduat tipibandocampigraduat;
	for (Iterator iterator = tipibandocampigraduats.iterator(); iterator.hasNext();) {
	    tipibandocampigraduat = (Tipibandocampigraduat) iterator.next();
	    aliasOrder = " _istanzedyn2dati" + i;
	    hqlOrderValori.append(aliasOrder).append(", ");
	    hqlOrderJoin.append(" left join _istanzes.istanzedyn2datis ").append(aliasOrder).append(" ");
	    hqlOrderAND.append(" AND ").append(aliasOrder).append(".id.fkD2cId = ").append(tipibandocampigraduat.getDyn2Campi().getId().getCodice());
	    ordinamento = tipibandocampigraduat.getOrdinamentoascdesc();
	    if (ordinamento.equals("ASC")) {
		ordinamentoAscDesc[i] = Integer.valueOf(GraduatorieDyn2DatiComparator.ORDER_ASC);
	    } else {
		ordinamentoAscDesc[i] = Integer.valueOf(GraduatorieDyn2DatiComparator.ORDER_DESC);
	    }
	    i++;
	}
	// itero sui campi per i criteri d'ammissione
	Tipigraduatoried tipigraduatoried;
	if (entity.getTipigraduatoriet().getTipigraduatorieds().size() > 0) {
	    values = new Object[entity.getTipigraduatoriet().getTipigraduatorieds().size() + 2];
	    values[0] = codiceIntervento;
	    //	    values[1] = entity.getId().getCodice();
	    values[1] = entity.getId().getIdcomune();
	}
	int a = 2;
	Date allaData = entity.getBandi().getDataAlla();
	Date dallaData = entity.getBandi().getDataDalla();
	if (dallaData != null) {
	    Calendar dallaDataC = Calendar.getInstance();
	    dallaDataC.setTime(dallaData);
	    dallaDataC.set(Calendar.HOUR, 0);
	    dallaDataC.set(Calendar.MINUTE, 0);
	    dallaDataC.set(Calendar.SECOND, 0);
	    values[a] = dallaDataC.getTime();
	    a++;
	}
	if (allaData != null) {
	    Calendar allaDataC = Calendar.getInstance();
	    allaDataC.setTime(dallaData);
	    allaDataC.set(Calendar.HOUR, 23);
	    allaDataC.set(Calendar.MINUTE, 59);
	    allaDataC.set(Calendar.SECOND, 59);
	    values[a] = allaDataC.getTime();
	    a++;
	}
	for (Iterator iterator = entity.getTipigraduatoriet().getTipigraduatorieds().iterator(); iterator.hasNext();) {
	    tipigraduatoried = (Tipigraduatoried) iterator.next();
	    aliasFilter = " _istanzedyn2dati" + i;
	    // hqlFilterValori += ", " + aliasFilter + " ";
	    hqlFilterJoin.append(" inner join _istanzes.istanzedyn2datis ").append(aliasFilter).append(" ");
	    hqlFilterAND.append(" AND ").append(aliasFilter).append(".id.fkD2cId = ").append(tipigraduatoried.getDyn2Campi().getId().getCodice())
		    .append(" AND ").append(aliasFilter).append(".valore like ? ");
	    values[a] = tipigraduatoried.getValore();
	    a++;
	    i++;
	}
	String _hqlOrderValori = hqlOrderValori.toString();
	if (_hqlOrderValori.indexOf(",") > 0) {
	    _hqlOrderValori = _hqlOrderValori.substring(0, _hqlOrderValori.lastIndexOf(","));
	}
	String hqlQuery = "SELECT " + _hqlOrderValori + " FROM Istanze _istanzes " + hqlOrderJoin.toString() + hqlFilterJoin.toString() +
			  " WHERE _istanzes.alberoproc.id.codice = ? AND  _istanzes.id.idcomune = ? ";
	if (dallaData != null) {
	    hqlQuery += " AND _istanzes.data >= ? ";
	}
	if (allaData != null) {
	    hqlQuery += " AND _istanzes.data <= ? ";
	}
	hqlQuery += hqlOrderAND.toString() + hqlFilterAND.toString();
	//List d = new ArrayList();
	List dynList = getHibernateTemplate().find(hqlQuery, values);
	// CODICE IDEA PER GESTIRE IL NUOVO ORDINAMENTO
	//	for (Object var : dynList) {
	//	    Object[] istanzedyn2dati = (Object[]) var;
	//	    for (int j = 0; j < istanzedyn2dati.length; j++) {
	//	    }
	//	    Object[] newArray = new Object[istanzedyn2dati.length + 1];
	//	    Istanzedyn2dati ic2d = new Istanzedyn2dati();
	//	    String v = String.valueOf((Double) Math.random() * 100);
	//	    ic2d.setValore(v);
	//	    ic2d.setValoredecodificato(v);
	//	    newArray[0] = ic2d;
	//	    int p = 1;
	//	    for (int j = 0; j < istanzedyn2dati.length; j++) {
	//		newArray[p] = (Istanzedyn2dati) istanzedyn2dati[j];
	//		p++;
	//	    }
	//	    d.add(newArray);
	//	}
	return dynList;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }
}
