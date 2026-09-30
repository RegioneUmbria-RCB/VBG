package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDyn2modellit;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndo;
import it.gruppoinit.pal.gp.core.domain.AlberoprocLeggi;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campiproprieta;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellidtesti;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.InventarioprocLeggi;
import it.gruppoinit.pal.gp.core.domain.InventarioprocTipititolo;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocdyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentioneri;
import it.gruppoinit.pal.gp.core.domain.Leggi;
import it.gruppoinit.pal.gp.core.domain.Leggitipi;
import it.gruppoinit.pal.gp.core.domain.Naturaendo;
import it.gruppoinit.pal.gp.core.domain.Normative;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipicontromovimento;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedureavvio;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.AdempimentoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.AllegatoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.AmministrazioneType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.CampoDinamicoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.CampoSchedaType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.CampoStaticoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.CausaliOneriType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.DeterminazioneEsitoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.FamigliaProcedimentoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.InterventoDocumentiType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.InterventoSchedeType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.InterventoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.LeggeType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.LeggiTipiType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.NaturaType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.NormativaType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.PosizioneCampoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.ProcedimentoAllegatiType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.ProcedimentoLeggeType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.ProcedimentoOneriType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.ProcedimentoSchedeType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.ProcedimentoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.ProceduraType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.ProcedureAvvioType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.ProprietaCampoDinamicoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.PubblicaDocumentoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.PubblicaType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.SchedaType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.TipoCampoDinamicoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.TipoCampoStaticoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.TipoContromovimentoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.TipoFirmaType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.TipoMovimentoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.TipoTitoloType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.TipologiaProcedimentoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.ValoreParametroType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.ValoriCampoCheckboxType;

import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.xml.datatype.DatatypeFactory;

public class ImportExportSIVBGServiceHelper {

    public ProcedimentoType populateSIVBGProcedimentoType(Inventarioprocedimenti endo) {

	ProcedimentoType procedimentoSIVBG = null;
	if (endo != null) {
	    procedimentoSIVBG = new ProcedimentoType();
	    procedimentoSIVBG.setCodice(String.valueOf(endo.getId().getCodice()));
	    procedimentoSIVBG.setProcedimento(endo.getProcedimento());
	    procedimentoSIVBG.setAmministrazione(populateSIVBGAmministrazioneType(endo.getAmministrazioni()));
	    procedimentoSIVBG.setInfoGenerali(endo.getDatigenerali());
	    procedimentoSIVBG.setInfoAdempimenti(endo.getAdempimenti());
	    procedimentoSIVBG.setInfoRequisiti(endo.getCampoapplicazione()); // ?? TODO
	    procedimentoSIVBG.setTipologia(populateSIVBGTipologiaProcedimentoType(endo.getTipoendo()));
	    HashMap<String, Integer> mappaProcedureInserite = new HashMap<String, Integer>();
	    HashMap<String, String> mappaMovimentiInseriti = new HashMap<String, String>();
	    procedimentoSIVBG
		    .setMovimentoAttivazione(populateSIVBGTipoMovimentoType(endo.getTipomovimento(), mappaProcedureInserite, mappaMovimentiInseriti));
	    procedimentoSIVBG.setNatura(populateSIVBGNaturaType(endo.getNaturaendo()));
	    if (endo.getInventarioprocTipititolos() != null) {
		for (InventarioprocTipititolo inventarioprocTipititolo : endo.getInventarioprocTipititolos()) {
		    procedimentoSIVBG.getTipoTitolo().add(populateSIVBGTipoTitoloType(inventarioprocTipititolo));
		}
	    }
	    if (endo.getDocumentis() != null) {
		for (Allegati allegati : endo.getAllegatis()) {
		    procedimentoSIVBG.getAllegati().add(populateSIVBGProcedimentoAllegatiType(allegati));
		}
	    }
	    if (endo.getInventarioprocdyn2modellits() != null) {
		for (Inventarioprocdyn2modellit inventarioprocdyn2modellit : endo.getInventarioprocdyn2modellits()) {
		    procedimentoSIVBG.getSchedeDinamiche().add(populateSIVBGProcedimentoSchedeType(inventarioprocdyn2modellit));
		}
	    }
	    if (endo.getInventarioprocedimentioneris() != null) {
		for (Inventarioprocedimentioneri inventarioprocedimentionere : endo.getInventarioprocedimentioneris()) {
		    procedimentoSIVBG.getOneri().add(populateSIVBGProcedimnetoOneriType(inventarioprocedimentionere));
		}
	    }
	    if (endo.getInventarioprocLeggis() != null) {
		for (InventarioprocLeggi inventarioprocLeggi : endo.getInventarioprocLeggis()) {
		    procedimentoSIVBG.getLeggi().add(populateSIVBGProcedimentoLeggeType(inventarioprocLeggi));
		}
	    }
	}
	return procedimentoSIVBG;
    }

    private ProcedimentoSchedeType populateSIVBGProcedimentoSchedeType(Inventarioprocdyn2modellit inventarioprocdyn2modellit) {

	ProcedimentoSchedeType procedimentoSchedeType = null;
	if (inventarioprocdyn2modellit != null) {
	    procedimentoSchedeType = new ProcedimentoSchedeType();
	    procedimentoSchedeType.setFacoltativa(inventarioprocdyn2modellit.getFlagFacoltativa());
	    if (inventarioprocdyn2modellit.getOrdine() != null) {
		procedimentoSchedeType.setOrdine(String.valueOf(inventarioprocdyn2modellit.getOrdine()));
	    }
	    procedimentoSchedeType.setPubblica(inventarioprocdyn2modellit.getFlagPubblica());
	    procedimentoSchedeType.setTipoFirma(inventarioprocdyn2modellit.getFlagTipofirma());
	    procedimentoSchedeType.setScheda(populateSIVBGSchedeType(inventarioprocdyn2modellit.getDyn2Modellit()));
	}
	return procedimentoSchedeType;
    }

    public InterventoType populateSIVBGInterventoType(Alberoproc alberoproc) {

	InterventoType interventoSIVBG = null;
	if (alberoproc != null) {
	    interventoSIVBG = new InterventoType();
	    interventoSIVBG.setId(String.valueOf(alberoproc.getId().getCodice()));
	    interventoSIVBG.setCodice(alberoproc.getScCodice());
	    interventoSIVBG.setDescrizione(alberoproc.getScDescrizione());
	    interventoSIVBG.setNote(alberoproc.getScNote());
	    if (alberoproc.getScOrdine() != null) {
		interventoSIVBG.setOrdine(String.valueOf(alberoproc.getScOrdine()));
	    }
	    HashMap<String, Integer> mappaProcedureInserite = new HashMap<String, Integer>();
	    HashMap<String, String> mappaMovimentiInseriti = new HashMap<String, String>();
	    interventoSIVBG.setProcedura(populateSIVBGProceduraType(alberoproc.getTipoProcedura(), mappaProcedureInserite, mappaMovimentiInseriti));
	    interventoSIVBG.setDisabilitato(alberoproc.getScAttivo());
	    if (alberoproc.getAlberoprocDocumentis() != null) {
		for (AlberoprocDocumenti alberoprocDocumenti : alberoproc.getAlberoprocDocumentis()) {
		    interventoSIVBG.getDocumenti().add(populateSIVBGInterventoDocumentiType(alberoprocDocumenti));
		}
	    }
	    if (alberoproc.getAlberoprocDyn2modellits() != null) {
		for (AlberoprocDyn2modellit alberoprocDyn2modellit : alberoproc.getAlberoprocDyn2modellits()) {
		    interventoSIVBG.getSchedeDinamiche().add(populateSIVBGInterventoSchedeType(alberoprocDyn2modellit));
		}
	    }
	    if (alberoproc.getAlberoprocLeggis() != null) {
		for (AlberoprocLeggi alberoprocLeggi : alberoproc.getAlberoprocLeggis()) {
		    interventoSIVBG.getLeggi().add(populateSIVBGLeggeType(alberoprocLeggi));
		}
	    }
	    if (alberoproc.getAlberoprocEndos() != null) {
		for (AlberoprocEndo alberoprocEndo : alberoproc.getAlberoprocEndos()) {
		    interventoSIVBG.getAdempimenti().add(populateSIVBGAdempimentoType(alberoprocEndo));
		}
	    }
	    interventoSIVBG.setPubblica(pubblicaDocumentoSIGEPRO2pubblicaDocumentoSIVBG(alberoproc.getScPubblica()));
	}
	return interventoSIVBG;
    }

    private InterventoSchedeType populateSIVBGInterventoSchedeType(AlberoprocDyn2modellit alberoprocDyn2modellit) {

	InterventoSchedeType schedaSIVBG = null;
	if (alberoprocDyn2modellit != null) {
	    schedaSIVBG = new InterventoSchedeType();
	    schedaSIVBG
		    .setNecessaria(alberoprocDyn2modellit.getFlagFacoltativa() == null || alberoprocDyn2modellit.getFlagFacoltativa() ? false : true); // TODO
	    if (alberoprocDyn2modellit.getFlagPubblica() != null) {
		schedaSIVBG.setPubblica(
			alberoprocDyn2modellit.getFlagPubblica() ? PubblicaType.PUBBLICA_NEI_SERVIZI_ON_LINE : PubblicaType.NON_PUBBLICARE); // TODO
	    }
	    if (alberoprocDyn2modellit.getFlagTipofirma() != null) {
		if (alberoprocDyn2modellit.getFlagTipofirma().intValue() == 0) {
		    schedaSIVBG.setTipoFirma(TipoFirmaType.LA_SCHEDA_NON_NECESSITA_DI_FIRMA);
		} else if (alberoprocDyn2modellit.getFlagTipofirma().intValue() == 1) {
		    schedaSIVBG.setTipoFirma(TipoFirmaType.LA_SCHEDA_RICHIEDE_LA_FIRMA);
		} else if (alberoprocDyn2modellit.getFlagTipofirma().intValue() == 2) {
		    schedaSIVBG.setTipoFirma(TipoFirmaType.LA_SCHEDA_VA_FIRMATA_A_BLOCCHI);
		}
	    }
	    schedaSIVBG.setScheda(populateSIVBGSchedeType(alberoprocDyn2modellit.getDyn2Modellit()));
	}
	return schedaSIVBG;
    }

    private SchedaType populateSIVBGSchedeType(Dyn2Modellit alberoprocDyn2modellit) {

	SchedaType schedaDinSIVBG = null;
	if (alberoprocDyn2modellit != null) {
	    schedaDinSIVBG = new SchedaType();
	    schedaDinSIVBG.setId(String.valueOf(alberoprocDyn2modellit.getId().getCodice()));
	    schedaDinSIVBG.setCodice(String.valueOf(alberoprocDyn2modellit.getCodiceScheda()));
	    schedaDinSIVBG.setNome(alberoprocDyn2modellit.getDescrizione());
	    if (alberoprocDyn2modellit.getDyn2Modellids() != null) {
		for (Dyn2Modellid dyn2Modellid : alberoprocDyn2modellit.getDyn2Modellids()) {
		    schedaDinSIVBG.getCampi().add(populateSIVBGCampoSchedaType(dyn2Modellid));
		}
	    }
	}
	return schedaDinSIVBG;
    }

    private PosizioneCampoType populatePosizioneCampoType(Integer posOrizzontale, Integer posVerticale) {

	PosizioneCampoType posizioneCampoType = new PosizioneCampoType();
	if (posOrizzontale != null && posVerticale != null) {
	    posizioneCampoType.setColonna(posOrizzontale);
	    posizioneCampoType.setRiga(posVerticale);
	    return posizioneCampoType;
	}
	return null;
    }

    private CampoStaticoType populateSIVBGCampoStaticoType(Dyn2Modellidtesti dyn2Modellidtesti) {

	CampoStaticoType campoStaticoSIVBG = null;
	if (dyn2Modellidtesti != null) {
	    campoStaticoSIVBG = new CampoStaticoType();
	    campoStaticoSIVBG.setCodice(String.valueOf(dyn2Modellidtesti.getId().getCodice()));
	    campoStaticoSIVBG.setTestoFisso(dyn2Modellidtesti.getTesto());
	    if (dyn2Modellidtesti.getDyn2Basetipitesto() != null && dyn2Modellidtesti.getDyn2Basetipitesto().getTipotesto() != null) {
		if (dyn2Modellidtesti.getDyn2Basetipitesto().getTipotesto().equalsIgnoreCase("TE")) {
		    campoStaticoSIVBG.setTipoCampo(TipoCampoStaticoType.TESTO_ESTESO);
		} else {
		    campoStaticoSIVBG.setTipoCampo(TipoCampoStaticoType.TITOLO);
		}
	    }
	}
	return campoStaticoSIVBG;
    }

    private CampoSchedaType populateSIVBGCampoSchedaType(Dyn2Modellid dyn2Modellid) {

	CampoSchedaType campoSchedaSIVBG = null;
	if (dyn2Modellid != null) {
	    campoSchedaSIVBG = new CampoSchedaType();
	    campoSchedaSIVBG.setCodice(String.valueOf(dyn2Modellid.getId().getCodice()));
	    if (dyn2Modellid.getDyn2Campi() != null) {
		campoSchedaSIVBG.setDescrizione(dyn2Modellid.getDyn2Campi().getDescrizione());
	    } else {
		campoSchedaSIVBG.setDescrizione("");
	    }
	    campoSchedaSIVBG.setPosizione(populatePosizioneCampoType(dyn2Modellid.getPosorizzontale(), dyn2Modellid.getPosverticale()));
	    if (dyn2Modellid.getDyn2Modellidtesti() != null) {
		// si tratta di un campo di testo
		campoSchedaSIVBG.setCampoStatico(populateSIVBGCampoStaticoType(dyn2Modellid.getDyn2Modellidtesti()));
	    } else {
		// si tratta di un campo dinamico
		campoSchedaSIVBG.setCampoDinamico(populateSIVBGCampoDinamicoType(dyn2Modellid.getDyn2Campi()));
	    }
	}
	return campoSchedaSIVBG;
    }

    private CampoDinamicoType populateSIVBGCampoDinamicoType(Dyn2Campi dyn2Campi) {

	CampoDinamicoType campoDinamicoSIVBG = null;
	if (dyn2Campi != null) {
	    campoDinamicoSIVBG = new CampoDinamicoType();
	    campoDinamicoSIVBG.setCodice(String.valueOf(dyn2Campi.getId().getCodice()));
	    campoDinamicoSIVBG.setNome(dyn2Campi.getNomecampo());
	    campoDinamicoSIVBG.setDescrizione(dyn2Campi.getDescrizione());
	    campoDinamicoSIVBG.setEtichetta(dyn2Campi.getEtichetta());
	    campoDinamicoSIVBG.setProprieta(populateSIVBGProprietaCampoDinamicoType(dyn2Campi));
	    campoDinamicoSIVBG.setValoreUtente(null); // TODO
	}
	return campoDinamicoSIVBG;
    }

    private ProprietaCampoDinamicoType populateSIVBGProprietaCampoDinamicoType(Dyn2Campi dyn2Campi) {

	ProprietaCampoDinamicoType proprietaCampoDinamicoType = null;
	if (dyn2Campi != null) {
	    proprietaCampoDinamicoType = new ProprietaCampoDinamicoType();
	    if (dyn2Campi.getTipodato() != null) {
		if (dyn2Campi.getTipodato().equalsIgnoreCase("checkbox")) {
		    proprietaCampoDinamicoType.setTipoCampo(TipoCampoDinamicoType.CHECKBOX);
		} else if (dyn2Campi.getTipodato().equalsIgnoreCase("data")) {
		    proprietaCampoDinamicoType.setTipoCampo(TipoCampoDinamicoType.DATA);
		} else if (dyn2Campi.getTipodato().equalsIgnoreCase("lista")) {
		    proprietaCampoDinamicoType.setTipoCampo(TipoCampoDinamicoType.LISTA);
		} else if (dyn2Campi.getTipodato().equalsIgnoreCase("numericoDouble")) {
		    proprietaCampoDinamicoType.setTipoCampo(TipoCampoDinamicoType.NUMERICO_DOUBLE);
		} else if (dyn2Campi.getTipodato().equalsIgnoreCase("numericoIntero")) {
		    proprietaCampoDinamicoType.setTipoCampo(TipoCampoDinamicoType.NUMERICO_INTERO);
		} else if (dyn2Campi.getTipodato().equalsIgnoreCase("Testo")) {
		    proprietaCampoDinamicoType.setTipoCampo(TipoCampoDinamicoType.TESTO);
		}
	    }
	    for (Dyn2Campiproprieta proprieta : dyn2Campi.getDyn2Campiproprietas()) {
		if (proprieta.getId().getProprieta().equalsIgnoreCase("Obbligatorio")) {
		    if (proprieta.getValore() != null && proprieta.getValore().equalsIgnoreCase("false")) {
			proprietaCampoDinamicoType.setObbligatorio(false);
		    } else {
			proprietaCampoDinamicoType.setObbligatorio(true);
		    }
		} else if (proprieta.getId().getProprieta().equalsIgnoreCase("MultiLine")) {
		    if (proprieta.getValore() != null && proprieta.getValore().equalsIgnoreCase("false")) {
			proprietaCampoDinamicoType.setMultiplo(false);
		    } else {
			proprietaCampoDinamicoType.setMultiplo(true);
		    }
		} else if (proprieta.getId().getProprieta().equalsIgnoreCase("MaxLength")) {
		    if (proprieta.getValore() != null) {
			if (Integer.valueOf(proprieta.getValore()) > 9999) {
			    proprietaCampoDinamicoType.setLunghezza(9999);
			} else {
			    proprietaCampoDinamicoType.setLunghezza(Integer.valueOf(proprieta.getValore()));
			}
		    }
		} else if (proprieta.getId().getProprieta().equalsIgnoreCase("ElementiLista")) {
		    if (proprieta.getValore() != null) {
			String[] tokens = proprieta.getValore().split(";");
			if (tokens != null) {
			    for (String token : tokens) {
				ValoreParametroType valore = new ValoreParametroType();
				String[] codDesc = token.split("\\$");
				if (codDesc != null && codDesc.length == 2) {
				    valore.setCodice(codDesc[0]);
				    valore.setDescrizione(codDesc[1]);
				} else {
				    valore.setCodice(token);
				    valore.setDescrizione(token);
				}
				proprietaCampoDinamicoType.getValoreLista().add(valore);
			    }
			}
		    }
		} else if (proprieta.getId().getProprieta().startsWith("Valore")) {
		    if (proprieta.getId().getProprieta().equalsIgnoreCase("ValoreFalse")) {
			if (proprietaCampoDinamicoType.getValoriCheckBox() == null) {
			    proprietaCampoDinamicoType.setValoriCheckBox(new ValoriCampoCheckboxType());
			}
			proprietaCampoDinamicoType.getValoriCheckBox().setValoreNonSelezionato(proprieta.getValore());
		    } else if (proprieta.getId().getProprieta().equalsIgnoreCase("ValoreTrue")) {
			if (proprietaCampoDinamicoType.getValoriCheckBox() == null) {
			    proprietaCampoDinamicoType.setValoriCheckBox(new ValoriCampoCheckboxType());
			}
			proprietaCampoDinamicoType.getValoriCheckBox().setValoreSelezionato(proprieta.getValore());
		    }
		}
	    }
	    // proprietaCampoDinamicoType.setPosizione(value) TODO
	}
	return proprietaCampoDinamicoType;
    }

    private LeggeType populateSIVBGLeggeType(AlberoprocLeggi alberoprocLeggi) {

	LeggeType leggeSIVBG = null;
	if (alberoprocLeggi != null) {
	    leggeSIVBG = new LeggeType();
	    leggeSIVBG.setDescrizione(alberoprocLeggi.getLegge().getLeDescrizione());
	    leggeSIVBG.setId(String.valueOf(alberoprocLeggi.getId().getCodice()));
	    leggeSIVBG.setLink(alberoprocLeggi.getLegge().getLeLink());
	    leggeSIVBG.setAllegato(populateSIVBGAllegatoType(alberoprocLeggi.getLegge().getOggetto()));
	    leggeSIVBG.setTipoLegge(populateSIVBGLeggiTipiType(alberoprocLeggi.getLegge().getLeggitipi()));
	    leggeSIVBG.setTipoNormativa(populateSIVBGNormativaType(alberoprocLeggi.getLegge().getNormative()));
	}
	return leggeSIVBG;
    }

    private InterventoDocumentiType populateSIVBGInterventoDocumentiType(AlberoprocDocumenti alberoprocDocumenti) {

	InterventoDocumentiType documentoInterventoSIVBG = null;
	if (alberoprocDocumenti != null) {
	    documentoInterventoSIVBG = new InterventoDocumentiType();
	    documentoInterventoSIVBG.setDescrizione(alberoprocDocumenti.getDescrizione());
	    documentoInterventoSIVBG.setId(String.valueOf(alberoprocDocumenti.getId().getCodice()));
	    documentoInterventoSIVBG.setNecessario(alberoprocDocumenti.getRichiesto());
	    documentoInterventoSIVBG.setNote(alberoprocDocumenti.getNote());
	    if (alberoprocDocumenti.getOrdine() != null) {
		documentoInterventoSIVBG.setOrdine(String.valueOf(alberoprocDocumenti.getOrdine()));
	    }
	    documentoInterventoSIVBG.setPubblica(pubblicaDocumentoSIGEPRO2pubblicaDocumentoSIVBG(alberoprocDocumenti.getPubblica()));
	    documentoInterventoSIVBG.setRichiedeFirma(alberoprocDocumenti.getFoRichiedefirma());
	    documentoInterventoSIVBG.setTipoDownload(alberoprocDocumenti.getFoTipodownload());
	    documentoInterventoSIVBG.setAllegato(populateSIVBGAllegatoType(alberoprocDocumenti.getOggetto()));
	}
	return documentoInterventoSIVBG;
    }

    private AdempimentoType populateSIVBGAdempimentoType(AlberoprocEndo alberoprocEndo) {

	AdempimentoType adempimentoSIVBG = null;
	if (alberoprocEndo != null) {
	    adempimentoSIVBG = new AdempimentoType();
	    if (alberoprocEndo.getAzione() != null) {
		adempimentoSIVBG.setAzione(String.valueOf(alberoprocEndo.getAzione().getAzId()));
	    }
	    adempimentoSIVBG.setDisabilitato(false);
	    adempimentoSIVBG.setPrincipale(alberoprocEndo.getFlagPrincipale());
	    adempimentoSIVBG.setNecessario(alberoprocEndo.getFlagRichiesto());
	    adempimentoSIVBG.setCodiceProcedimento(String.valueOf(alberoprocEndo.getId().getCodiceinventario()));
	}
	return adempimentoSIVBG;
    }

    private AmministrazioneType populateSIVBGAmministrazioneType(Amministrazioni amministrazioni) {

	AmministrazioneType amministrazioneSIVBG = null;
	if (amministrazioni != null) {
	    amministrazioneSIVBG = new AmministrazioneType();
	    amministrazioneSIVBG.setId(String.valueOf(amministrazioni.getId().getCodice()));
	    amministrazioneSIVBG.setAmministrazione(amministrazioni.getAmministrazione());
	}
	return amministrazioneSIVBG;
    }

    private TipoMovimentoType populateSIVBGTipoMovimentoType(Tipimovimento tipomovimento, HashMap<String, Integer> mappaProcedureInserite,
	    HashMap<String, String> mappaMovimentiInseriti) {

	TipoMovimentoType tipoMovimentoSIVBG = null;
	if (tipomovimento != null) {
	    tipoMovimentoSIVBG = new TipoMovimentoType();
	    tipoMovimentoSIVBG.setId(tipomovimento.getId().getTipomovimento());
	    mappaMovimentiInseriti.put(tipomovimento.getId().getTipomovimento(), tipomovimento.getId().getTipomovimento());
	    tipoMovimentoSIVBG.setDescrizione(tipomovimento.getMovimento());
	    tipoMovimentoSIVBG.setFlagSospensione(tipomovimento.getFlagRichiestaintegrazione());
	    tipoMovimentoSIVBG.setFlagInterruzione(tipomovimento.getFlagInterruzione());
	    tipoMovimentoSIVBG.setTipologiaEsito(String.valueOf(tipomovimento.getTipologiaesito()));
	    tipoMovimentoSIVBG.setFlagProroga(tipomovimento.getFlagProroga());
	    tipoMovimentoSIVBG.setGgProroga(tipomovimento.getGgproroga());
	    tipoMovimentoSIVBG.setFlagCds(tipomovimento.getFlagCds());
	    tipoMovimentoSIVBG.setFlagPubblicaMovimento(tipomovimento.getFlagPubblicamovimento());
	    tipoMovimentoSIVBG.setFlagPubblicaParere(tipomovimento.getFlagPubblicaparere());
	    tipoMovimentoSIVBG.setFlagPubblicaAllegati(tipomovimento.getFlagPubblicaallegati());
	    if (tipomovimento.getTipicontromovimentos() != null) {
		for (Tipicontromovimento tipicontromovimento : tipomovimento.getTipicontromovimentos()) {
		    tipoMovimentoSIVBG.getMovimentiCollegati()
			    .add(populateSIVBGTipoContromovimentoType(tipicontromovimento, mappaProcedureInserite, mappaMovimentiInseriti));
		}
	    }
	}
	return tipoMovimentoSIVBG;
    }

    private TipoContromovimentoType populateSIVBGTipoContromovimentoType(Tipicontromovimento tipicontromovimento,
	    HashMap<String, Integer> mappaProcedureInserite, HashMap<String, String> mappaMovimentiInseriti) {

	TipoContromovimentoType tipocontromovimentoSIVBG = null;
	if (tipicontromovimento != null) {
	    tipocontromovimentoSIVBG = new TipoContromovimentoType();
	    tipocontromovimentoSIVBG.setId(String.valueOf(tipicontromovimento.getId().getCodice()));
	    if (!mappaMovimentiInseriti.containsKey(tipicontromovimento.getTipocontromovimento().getId().getTipomovimento())) {
		tipocontromovimentoSIVBG.setTipoContromovimento(
			populateSIVBGTipoMovimentoType(tipicontromovimento.getTipocontromovimento(), mappaProcedureInserite, mappaMovimentiInseriti));
	    } else {
		TipoMovimentoType tipoMovimento = new TipoMovimentoType();
		tipoMovimento.setDescrizione(tipicontromovimento.getTipocontromovimento().getMovimento());
		tipoMovimento.setId(tipicontromovimento.getTipocontromovimento().getId().getTipomovimento());
		tipoMovimento.setFlagSospensione(tipicontromovimento.getTipocontromovimento().getFlagRichiestaintegrazione());
		tipoMovimento.setFlagInterruzione(tipicontromovimento.getTipocontromovimento().getFlagInterruzione());
		tipoMovimento.setTipologiaEsito(String.valueOf(tipicontromovimento.getTipocontromovimento().getTipologiaesito()));
		tipoMovimento.setFlagProroga(tipicontromovimento.getTipocontromovimento().getFlagProroga());
		tipoMovimento.setGgProroga(tipicontromovimento.getTipocontromovimento().getGgproroga());
		tipoMovimento.setFlagCds(tipicontromovimento.getTipocontromovimento().getFlagCds());
		tipoMovimento.setFlagPubblicaMovimento(tipicontromovimento.getTipocontromovimento().getFlagPubblicamovimento());
		tipoMovimento.setFlagPubblicaParere(tipicontromovimento.getTipocontromovimento().getFlagPubblicaparere());
		tipoMovimento.setFlagPubblicaAllegati(tipicontromovimento.getTipocontromovimento().getFlagPubblicaallegati());
		tipocontromovimentoSIVBG.setTipoContromovimento(tipoMovimento);
	    }
	    tipocontromovimentoSIVBG
		    .setAmministrazioneMovimento(populateSIVBGAmministrazioneType(tipicontromovimento.getAmministrazioniTipiMovimento()));
	    tipocontromovimentoSIVBG
		    .setAmministrazioneControMovimento(populateSIVBGAmministrazioneType(tipicontromovimento.getAmministrazioniTipiContromovimento()));
	    tipocontromovimentoSIVBG.setComportamento(tipicontromovimento.getSoloseesitonegativo());
	    tipocontromovimentoSIVBG.setComportamentoNonDuplicare(tipicontromovimento.getSeprecedente());
	    tipocontromovimentoSIVBG.setObbligatorio(tipicontromovimento.getFlagbase());
	    if (tipicontromovimento.getPropostostc() != null) {
		tipocontromovimentoSIVBG.setComportamentoSTC(Integer.valueOf(tipicontromovimento.getPropostostc()));
	    }
	    try {
		if (tipicontromovimento.getDatacreazione() != null) {
		    GregorianCalendar gc = new GregorianCalendar();
		    gc.setTimeInMillis(tipicontromovimento.getDatacreazione().getTime());
		    DatatypeFactory df = DatatypeFactory.newInstance();
		    tipocontromovimentoSIVBG.setDataInizioValidita(df.newXMLGregorianCalendar(gc)); // TODO ???
		}
	    } catch (Exception e) {
	    }
	    if (tipicontromovimento.getTipiprocedure() != null) {
		if (!mappaProcedureInserite.containsKey(String.valueOf(tipicontromovimento.getTipiprocedure().getId().getCodice()))) {
		    tipocontromovimentoSIVBG.setProcedura(
			    populateSIVBGProceduraType(tipicontromovimento.getTipiprocedure(), mappaProcedureInserite, mappaMovimentiInseriti));
		} else {
		    ProceduraType proceduraSIVBG = new ProceduraType();
		    proceduraSIVBG.setId(String.valueOf(tipicontromovimento.getTipiprocedure().getId().getCodice()));
		    proceduraSIVBG.setProcedura(tipicontromovimento.getTipiprocedure().getProcedura());
		    proceduraSIVBG.setNote(tipicontromovimento.getTipiprocedure().getNote());
		    proceduraSIVBG.setDurata(tipicontromovimento.getTipiprocedure().getGiorni());
		    proceduraSIVBG.setPrevedeAtto(tipicontromovimento.getTipiprocedure().getFlagattochiusura());
		    proceduraSIVBG.setNatura(populateSIVBGNaturaType(tipicontromovimento.getTipiprocedure().getNaturaendo()));
		    proceduraSIVBG.setPrevedeCDS(tipicontromovimento.getTipiprocedure().getFlagprevedecds());
		    proceduraSIVBG.setDeterminazioneInizioIstanza(tipicontromovimento.getTipiprocedure().getDeterminazioneinizioistanza());
		    proceduraSIVBG.setDeterminazioneEfficacia(tipicontromovimento.getTipiprocedure().getDeterminazioneefficacia());
		    proceduraSIVBG.setDeterminazioneEsito(
			    populateSIVBGDeterminazioneEsitoType(tipicontromovimento.getTipiprocedure().getDeterminazioneesito()));
		    tipocontromovimentoSIVBG.setProcedura(proceduraSIVBG);
		}
	    } else {
		tipocontromovimentoSIVBG.setProcedura(null);
	    }
	}
	return tipocontromovimentoSIVBG;
    }

    private ProceduraType populateSIVBGProceduraType(Tipiprocedure tipiprocedure, HashMap<String, Integer> mappaProcedureInserite,
	    HashMap<String, String> mappaMovimentiInseriti) {

	ProceduraType proceduraSIVBG = null;
	if (tipiprocedure != null && !mappaProcedureInserite.containsKey(String.valueOf(tipiprocedure.getId().getCodice()))) {
	    proceduraSIVBG = new ProceduraType();
	    proceduraSIVBG.setId(String.valueOf(tipiprocedure.getId().getCodice()));
	    proceduraSIVBG.setProcedura(tipiprocedure.getProcedura());
	    proceduraSIVBG.setNote(tipiprocedure.getNote());
	    proceduraSIVBG.setDurata(tipiprocedure.getGiorni());
	    proceduraSIVBG.setPrevedeAtto(tipiprocedure.getFlagattochiusura());
	    proceduraSIVBG.setNatura(populateSIVBGNaturaType(tipiprocedure.getNaturaendo()));
	    proceduraSIVBG.setPrevedeCDS(tipiprocedure.getFlagprevedecds());
	    proceduraSIVBG.setDeterminazioneInizioIstanza(tipiprocedure.getDeterminazioneinizioistanza());
	    proceduraSIVBG.setDeterminazioneEfficacia(tipiprocedure.getDeterminazioneefficacia());
	    proceduraSIVBG.setDeterminazioneEsito(populateSIVBGDeterminazioneEsitoType(tipiprocedure.getDeterminazioneesito()));
	    mappaProcedureInserite.put(String.valueOf(tipiprocedure.getId().getCodice()), tipiprocedure.getId().getCodice());
	    if (tipiprocedure.getTipimovimentoDeterminazione() != null) {
		if (!mappaMovimentiInseriti.containsKey(tipiprocedure.getTipimovimentoDeterminazione().getId().getTipomovimento())) {
		    proceduraSIVBG.setDeterminazioneMovimento(populateSIVBGTipoMovimentoType(tipiprocedure.getTipimovimentoDeterminazione(),
			    mappaProcedureInserite, mappaMovimentiInseriti));
		} else {
		    TipoMovimentoType tipoMovimentoSIVBG = new TipoMovimentoType();
		    tipoMovimentoSIVBG.setId(tipiprocedure.getTipimovimentoDeterminazione().getId().getTipomovimento());
		    tipoMovimentoSIVBG.setDescrizione(tipiprocedure.getTipimovimentoDeterminazione().getMovimento());
		    tipoMovimentoSIVBG.setFlagSospensione(tipiprocedure.getTipimovimentoDeterminazione().getFlagRichiestaintegrazione());
		    tipoMovimentoSIVBG.setFlagInterruzione(tipiprocedure.getTipimovimentoDeterminazione().getFlagInterruzione());
		    tipoMovimentoSIVBG.setTipologiaEsito(String.valueOf(tipiprocedure.getTipimovimentoDeterminazione().getTipologiaesito()));
		    tipoMovimentoSIVBG.setFlagProroga(tipiprocedure.getTipimovimentoDeterminazione().getFlagProroga());
		    tipoMovimentoSIVBG.setGgProroga(tipiprocedure.getTipimovimentoDeterminazione().getGgproroga());
		    tipoMovimentoSIVBG.setFlagCds(tipiprocedure.getTipimovimentoDeterminazione().getFlagCds());
		    tipoMovimentoSIVBG.setFlagPubblicaMovimento(tipiprocedure.getTipimovimentoDeterminazione().getFlagPubblicamovimento());
		    tipoMovimentoSIVBG.setFlagPubblicaParere(tipiprocedure.getTipimovimentoDeterminazione().getFlagPubblicaparere());
		    tipoMovimentoSIVBG.setFlagPubblicaAllegati(tipiprocedure.getTipimovimentoDeterminazione().getFlagPubblicaallegati());
		    proceduraSIVBG.setDeterminazioneMovimento(tipoMovimentoSIVBG);
		}
	    } else {
		proceduraSIVBG.setDeterminazioneMovimento(null);
	    }
	    for (Tipiprocedureavvio tipiprocedureavvio : tipiprocedure.getTipiProcedureavvios()) {
		proceduraSIVBG.getMovimentiAvvio()
			.add(populateSIVBGProcedureAvvioType(tipiprocedureavvio, mappaProcedureInserite, mappaMovimentiInseriti));
	    }
	}
	return proceduraSIVBG;
    }

    private ProcedureAvvioType populateSIVBGProcedureAvvioType(Tipiprocedureavvio tipiprocedureavvio, HashMap<String, Integer> mappaProcedureInserite,
	    HashMap<String, String> mappaMovimentiInseriti) {

	ProcedureAvvioType procedureAvvioType = null;
	if (tipiprocedureavvio != null) {
	    procedureAvvioType = new ProcedureAvvioType();
	    procedureAvvioType.setDefaultsn(tipiprocedureavvio.getDefaultsn());
	    if (!mappaMovimentiInseriti.containsKey(tipiprocedureavvio.getTipoMovimento().getId().getTipomovimento())) {
		procedureAvvioType.setTipoMovimento(
			populateSIVBGTipoMovimentoType(tipiprocedureavvio.getTipoMovimento(), mappaProcedureInserite, mappaMovimentiInseriti));
	    } else {
		TipoMovimentoType tipoMovimentoSIVBG = new TipoMovimentoType();
		tipoMovimentoSIVBG.setId(tipiprocedureavvio.getTipoMovimento().getId().getTipomovimento());
		tipoMovimentoSIVBG.setDescrizione(tipiprocedureavvio.getTipoMovimento().getMovimento());
		tipoMovimentoSIVBG.setFlagSospensione(tipiprocedureavvio.getTipoMovimento().getFlagRichiestaintegrazione());
		tipoMovimentoSIVBG.setFlagInterruzione(tipiprocedureavvio.getTipoMovimento().getFlagInterruzione());
		tipoMovimentoSIVBG.setTipologiaEsito(String.valueOf(tipiprocedureavvio.getTipoMovimento().getTipologiaesito()));
		tipoMovimentoSIVBG.setFlagProroga(tipiprocedureavvio.getTipoMovimento().getFlagProroga());
		tipoMovimentoSIVBG.setGgProroga(tipiprocedureavvio.getTipoMovimento().getGgproroga());
		tipoMovimentoSIVBG.setFlagCds(tipiprocedureavvio.getTipoMovimento().getFlagCds());
		tipoMovimentoSIVBG.setFlagPubblicaMovimento(tipiprocedureavvio.getTipoMovimento().getFlagPubblicamovimento());
		tipoMovimentoSIVBG.setFlagPubblicaParere(tipiprocedureavvio.getTipoMovimento().getFlagPubblicaparere());
		tipoMovimentoSIVBG.setFlagPubblicaAllegati(tipiprocedureavvio.getTipoMovimento().getFlagPubblicaallegati());
		procedureAvvioType.setTipoMovimento(tipoMovimentoSIVBG);
	    }
	}
	return procedureAvvioType;
    }

    private DeterminazioneEsitoType populateSIVBGDeterminazioneEsitoType(Integer determinazioneesito) {

	if (determinazioneesito != null) {
	    if (determinazioneesito == 0) {
		return DeterminazioneEsitoType.QUALSIASI;
	    } else if (determinazioneesito == 1) {
		return DeterminazioneEsitoType.NEGATIVO;
	    } else if (determinazioneesito == 2) {
		return DeterminazioneEsitoType.POSITIVO;
	    } else {
		return null;
	    }
	} else {
	    return null;
	}
    }

    private NaturaType populateSIVBGNaturaType(Naturaendo naturaendo) {

	NaturaType naturaSIVBG = null;
	if (naturaendo != null) {
	    naturaSIVBG = new NaturaType();
	    naturaSIVBG.setId(String.valueOf(naturaendo.getId().getCodice()));
	    naturaSIVBG.setNatura(naturaendo.getNatura());
	    naturaSIVBG.setCompatibilita(naturaendo.getBinariodipendenze());
	}
	return naturaSIVBG;
    }

    private TipologiaProcedimentoType populateSIVBGTipologiaProcedimentoType(Tipiendo tipoendo) {

	TipologiaProcedimentoType tipologiaProcedimentoSIVBG = null;
	if (tipoendo != null) {
	    tipologiaProcedimentoSIVBG = new TipologiaProcedimentoType();
	    tipologiaProcedimentoSIVBG.setId(String.valueOf(tipoendo.getId().getCodice()));
	    tipologiaProcedimentoSIVBG.setTipo(tipoendo.getTipo());
	    tipologiaProcedimentoSIVBG.setNote(tipoendo.getNote());
	    if (tipoendo.getOrdine() != null) {
		tipologiaProcedimentoSIVBG.setOrdine(String.valueOf(tipoendo.getOrdine()));
	    }
	    tipologiaProcedimentoSIVBG.setFamiglia(populateSIVBGFamigliaProcedimentoType(tipoendo.getTipifamiglieendo()));
	}
	return tipologiaProcedimentoSIVBG;
    }

    private FamigliaProcedimentoType populateSIVBGFamigliaProcedimentoType(Tipifamiglieendo tipifamiglieendo) {

	FamigliaProcedimentoType famigliaProcSIVBG = null;
	if (tipifamiglieendo != null) {
	    famigliaProcSIVBG = new FamigliaProcedimentoType();
	    famigliaProcSIVBG.setFamiglia(tipifamiglieendo.getTipo());
	    famigliaProcSIVBG.setId(String.valueOf(tipifamiglieendo.getId().getCodice()));
	    famigliaProcSIVBG.setNote(tipifamiglieendo.getNote());
	    if (tipifamiglieendo.getOrdine() != null) {
		famigliaProcSIVBG.setOrdine(String.valueOf(tipifamiglieendo.getOrdine()));
	    }
	}
	return famigliaProcSIVBG;
    }

    private ProcedimentoAllegatiType populateSIVBGProcedimentoAllegatiType(Allegati allegatoInventarioProcedimento) {

	ProcedimentoAllegatiType procedimentoAllegatoSIVBG = null;
	if (allegatoInventarioProcedimento != null) {
	    procedimentoAllegatoSIVBG = new ProcedimentoAllegatiType();
	    procedimentoAllegatoSIVBG.setId(String.valueOf(allegatoInventarioProcedimento.getId().getCodice()));
	    procedimentoAllegatoSIVBG.setDescrizione(allegatoInventarioProcedimento.getAllegato());
	    procedimentoAllegatoSIVBG.setLink(allegatoInventarioProcedimento.getIndirizzoweb());
	    procedimentoAllegatoSIVBG.setAllegato(populateSIVBGAllegatoType(allegatoInventarioProcedimento.getOggetti()));
	    procedimentoAllegatoSIVBG.setPubblica(pubblicaDocumentoSIGEPRO2pubblicaDocumentoSIVBG(allegatoInventarioProcedimento.getPubblica()));
	    procedimentoAllegatoSIVBG.setRichiesto(allegatoInventarioProcedimento.getRichiesto());
	    if (allegatoInventarioProcedimento.getOrdine() != null) {
		procedimentoAllegatoSIVBG.setOrdine(String.valueOf(allegatoInventarioProcedimento.getOrdine()));
	    }
	    procedimentoAllegatoSIVBG.setRichiedeFirma(allegatoInventarioProcedimento.getFoRichiedefirma());
	    procedimentoAllegatoSIVBG.setTipoDownload(allegatoInventarioProcedimento.getFoTipodownload());
	}
	return procedimentoAllegatoSIVBG;
    }

    private AllegatoType populateSIVBGAllegatoType(Oggetti oggetto) {

	AllegatoType allegatoSIVBG = null;
	if (oggetto != null) {
	    allegatoSIVBG = new AllegatoType();
	    allegatoSIVBG.setId(String.valueOf(oggetto.getId().getCodice()));
	    allegatoSIVBG.setAllegato(oggetto.getNomefile());
	}
	return allegatoSIVBG;
    }

    private ProcedimentoLeggeType populateSIVBGProcedimentoLeggeType(InventarioprocLeggi inventarioprocLeggi) {

	ProcedimentoLeggeType procLeggeSIVBG = null;
	if (inventarioprocLeggi != null) {
	    procLeggeSIVBG = new ProcedimentoLeggeType();
	    procLeggeSIVBG.setId(String.valueOf(inventarioprocLeggi.getId().getCodice()));
	    procLeggeSIVBG.setRiferimenti(inventarioprocLeggi.getRiferimenti());
	    procLeggeSIVBG.setLegge(populateSIVBGLeggeType(inventarioprocLeggi.getLeggi()));
	}
	return procLeggeSIVBG;
    }

    private LeggeType populateSIVBGLeggeType(Leggi leggi) {

	LeggeType leggeSIVBG = null;
	if (leggi != null) {
	    leggeSIVBG = new LeggeType();
	    leggeSIVBG.setDescrizione(leggi.getLeDescrizione());
	    leggeSIVBG.setId(String.valueOf(leggi.getId().getCodice()));
	    leggeSIVBG.setLink(leggi.getLeLink());
	    leggeSIVBG.setAllegato(populateSIVBGAllegatoType(leggi.getOggetto()));
	    leggeSIVBG.setTipoLegge(populateSIVBGLeggiTipiType(leggi.getLeggitipi()));
	    leggeSIVBG.setTipoNormativa(populateSIVBGNormativaType(leggi.getNormative()));
	}
	return leggeSIVBG;
    }

    private NormativaType populateSIVBGNormativaType(Normative normative) {

	NormativaType normativaSIVBG = null;
	if (normative != null) {
	    normativaSIVBG = new NormativaType();
	    normativaSIVBG.setId(String.valueOf(normative.getId().getCodice()));
	    normativaSIVBG.setNormativa(normative.getNormativa());
	}
	return normativaSIVBG;
    }

    private LeggiTipiType populateSIVBGLeggiTipiType(Leggitipi leggitipi) {

	LeggiTipiType tipoLeggeSIVBG = null;
	if (leggitipi != null) {
	    tipoLeggeSIVBG = new LeggiTipiType();
	    tipoLeggeSIVBG.setId(String.valueOf(leggitipi.getId().getCodice()));
	    tipoLeggeSIVBG.setDescrizione(leggitipi.getLtDescrizione());
	}
	return tipoLeggeSIVBG;
    }

    private ProcedimentoOneriType populateSIVBGProcedimnetoOneriType(Inventarioprocedimentioneri inventarioprocedimentionere) {

	ProcedimentoOneriType procOnereSIVBG = null;
	if (inventarioprocedimentionere != null) {
	    procOnereSIVBG = new ProcedimentoOneriType();
	    procOnereSIVBG.setId(String.valueOf(inventarioprocedimentionere.getId().getCodice()));
	    if (inventarioprocedimentionere.getImporto() != null) {
		procOnereSIVBG.setImporto(Double.parseDouble(String.valueOf(inventarioprocedimentionere.getImporto())));
	    }
	    procOnereSIVBG.setCausale(populateSIVBGCausaliOneriType(inventarioprocedimentionere.getTipicausalioneri()));
	}
	return procOnereSIVBG;
    }

    private CausaliOneriType populateSIVBGCausaliOneriType(Tipicausalioneri tipicausalioneri) {

	CausaliOneriType causale = null;
	if (tipicausalioneri != null) {
	    causale = new CausaliOneriType();
	    causale.setId(String.valueOf(tipicausalioneri.getId().getCodice()));
	    causale.setDescrizione(tipicausalioneri.getCoDescrizione());
	}
	return causale;
    }

    private TipoTitoloType populateSIVBGTipoTitoloType(InventarioprocTipititolo inventarioprocTipititolo) {

	TipoTitoloType tipoTitoloSIVBG = null;
	if (inventarioprocTipititolo != null) {
	    tipoTitoloSIVBG = new TipoTitoloType();
	    tipoTitoloSIVBG.setId(String.valueOf(inventarioprocTipititolo.getId().getCodice()));
	    tipoTitoloSIVBG.setDescrizione(inventarioprocTipititolo.getTipotitolo());
	}
	return tipoTitoloSIVBG;
    }

    private PubblicaDocumentoType pubblicaDocumentoSIGEPRO2pubblicaDocumentoSIVBG(Integer val) {

	if (val != null) {
	    if (val == 0) {
		return PubblicaDocumentoType.NON_PUBBLICARE;
	    } else if (val == 1) {
		return PubblicaDocumentoType.PUBBLICA_INFO_E_AREARISERVATA;
	    } else if (val == 2 || val == 4) {
		return PubblicaDocumentoType.PUBBLICA_AREARISERVATA;
	    } else if (val == 3) {
		return PubblicaDocumentoType.PUBBLICA_INFO;
	    } else {
		return null;
	    }
	} else {
	    return null;
	}
    }
}
