package it.gruppoinit.pal.gp.core.scheduler.task;

import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.FiereMostre;
import it.gruppoinit.pal.gp.core.domain.FiereMostreMerceologie;
import it.gruppoinit.pal.gp.core.domain.FiereMostrePeriodi;
import it.gruppoinit.pal.gp.core.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.FiereMostreService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImportTaskFiereSUAPE extends ImportTask {

    private static final Logger log = LoggerFactory.getLogger(ImportTaskFiereSUAPE.class);
    @Autowired
    private FiereMostreService fiereMostreService;
    @Autowired
    private ComuniService comuniService;

    @Override
    public void process(List<Comuniassociati> listComuniImportAutomatico) {

	log.info("process...");
	ORMHelper.setIdcomuneAlias(deployProps.getProperty("idcomunealias"));
	ORMHelper.setIdcomune(deployProps.getProperty("idcomune"));
	try {
	    JdbcFiereSUAPE jdbcSUAPE = new JdbcFiereSUAPE(dbProps, deployProps, "jdbcSUAPE.properties");
	    List<IstanzaDaElaborareDTO> list = jdbcSUAPE.getIstanze();
	    for (IstanzaDaElaborareDTO istanza : list) {
		if (isListed(listComuniImportAutomatico, istanza.getCODICECOMUNE())) {
		    _process(istanza);
		    jdbcSUAPE.updateIstanzaElaborata(istanza);
		} else {
		    log.info("process: L'istanza con codicecomune={} non ha l'import automatico quindi la escludo", istanza.getCODICECOMUNE());
		}
	    }
	    //process comuni onsite
	    for (Comuniassociati comuniassociati : listComuniImportAutomatico) {
		if (BooleanUtils.isTrue(comuniassociati.getOnsite())) {
		    String jdbcFileName = "jdbc" + comuniassociati.getId().getCodicecomune() + ".properties";
		    log.info("process comune ONSITE: {}", jdbcFileName);
		    try {
			JdbcFiereSUAPE jdbcSUAPEONSITE = new JdbcFiereSUAPE(dbProps, deployProps, jdbcFileName);
			List<IstanzaDaElaborareDTO> listIstanze = jdbcSUAPEONSITE.getIstanze();
			for (IstanzaDaElaborareDTO istanza : listIstanze) {
			    _process(istanza);
			    jdbcSUAPEONSITE.updateIstanzaElaborata(istanza);
			}
		    } catch (Exception e) {
			log.error("process: errore nel processamento del comune ONSITE {}", jdbcFileName, e);
		    }
		}
	    }
	} catch (Exception e) {
	    log.error("process", e);
	} finally {
	    ORMHelper.destroyORMHelper();
	}
	log.info("process...end");
    }

    private void _process(IstanzaDaElaborareDTO istanza) throws Exception {

	log.info("process idcomune={}, codiceistanza={}", istanza.getIDCOMUNE(), istanza.getCODICEISTANZA());
	FiereMostre fm = new FiereMostre();
	Comuni comune = new Comuni(istanza.getCODICECOMUNE());
	comune = comuniService.findByCodiceComune(comune);
	Comuni comuneSvolgimento = new Comuni(istanza.getCOMUNE_SVOLGIMENTO());
	comuneSvolgimento = comuniService.findByCodiceComune(comuneSvolgimento);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	fm.setComune(comune);
	fm.setComuneSvolgimento(comuneSvolgimento);
	fm.setClassificazione(istanza.getCLASSIFICAZIONE());
	fm.setDataAutorizzazione(sdf.parse(istanza.getAUTORIZDATA()));
	fm.setDataIstanza(sdf.parse(istanza.getDATAISTANZA()));
	fm.setDenominazione(istanza.getDENOMINAZIONE());
	fm.setIdIstanza(istanza.getCODICEPRATICATEL());
	fm.setLuogoSvolgimento(istanza.getLUOGO_SVOLGIMENTO());
	fm.setNumAutorizzazione(istanza.getAUTORIZNUMERO());
	fm.setNumeroIstanza(istanza.getNUMEROISTANZA());
	fm.setNumProtocolloIstanza(istanza.getNUMEROPROTOCOLLO());
	fm.setOrganizzatore(formatOrganizzatore(istanza.getNOMINATIVORICHIEDENTE(), istanza.getNOMERICHIEDENTE(), istanza.getCFRICHIEDENTE(),
		istanza.getPIVARICHIEDENTE()));
	fm.setQualifica(istanza.getQUALIFICA());
	List<FiereMostrePeriodi> periodi = new ArrayList<FiereMostrePeriodi>();
	for (Periodo p : istanza.getPERIODI()) {
	    FiereMostrePeriodi periodo = new FiereMostrePeriodi();
	    periodo.setDal(p.getDAL());
	    periodo.setAl(p.getAL());
	    periodi.add(periodo);
	}
	List<FiereMostreMerceologie> merceologie = new ArrayList<FiereMostreMerceologie>();
	for (String m : istanza.getMERCEOLOGIE()) {
	    FiereMostreMerceologie merceologia = new FiereMostreMerceologie();
	    merceologia.setMerceologia(m);
	    merceologie.add(merceologia);
	}
	fiereMostreService.insert(fm, periodi, merceologie);
    }
}
