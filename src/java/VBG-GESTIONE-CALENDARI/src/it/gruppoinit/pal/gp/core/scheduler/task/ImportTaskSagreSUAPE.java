package it.gruppoinit.pal.gp.core.scheduler.task;

import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.FesteSagre;
import it.gruppoinit.pal.gp.core.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.FesteSagreService;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImportTaskSagreSUAPE extends ImportTask {

    private static final Logger log = LoggerFactory.getLogger(ImportTaskSagreSUAPE.class);
    @Autowired
    private FesteSagreService festeSagreService;
    @Autowired
    private ComuniService comuniService;

    @Override
    public void process(List<Comuniassociati> listComuniImportAutomatico) {

	log.info("process...");
	ORMHelper.setIdcomuneAlias(deployProps.getProperty("idcomunealias"));
	ORMHelper.setIdcomune(deployProps.getProperty("idcomune"));
	try {
	    JdbcSagreSUAPE jdbcSUAPE = new JdbcSagreSUAPE(dbProps, deployProps, "jdbcSUAPE.properties");
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
			JdbcSagreSUAPE jdbcSUAPEONSITE = new JdbcSagreSUAPE(dbProps, deployProps, jdbcFileName);
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
	FesteSagre fs = new FesteSagre();
	Comuni comune = new Comuni(istanza.getCODICECOMUNE());
	comune = comuniService.findByCodiceComune(comune);
	Comuni comuneSvolgimento = new Comuni(istanza.getCOMUNE_SVOLGIMENTO());
	comuneSvolgimento = comuniService.findByCodiceComune(comuneSvolgimento);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	fs.setComune(comune);
	fs.setComuneSvolgimento(comuneSvolgimento);
	fs.setDataAutorizzazione(sdf.parse(istanza.getAUTORIZDATA()));
	fs.setDataIstanza(sdf.parse(istanza.getDATAISTANZA()));
	fs.setDenominazione(istanza.getDENOMINAZIONE());
	fs.setIdIstanza(istanza.getCODICEPRATICATEL());
	fs.setLuogoSvolgimento(istanza.getLUOGO_SVOLGIMENTO());
	fs.setNumAutorizzazione(istanza.getAUTORIZNUMERO());
	fs.setNumeroIstanza(istanza.getNUMEROISTANZA());
	fs.setNumProtocolloIstanza(istanza.getNUMEROPROTOCOLLO());
	fs.setOrganizzatore(formatOrganizzatore(istanza.getNOMINATIVORICHIEDENTE(), istanza.getNOMERICHIEDENTE(), istanza.getCFRICHIEDENTE(),
		istanza.getPIVARICHIEDENTE()));
	Periodo p = istanza.getPERIODI().get(0);
	fs.setDal(p.getDAL());
	fs.setAl(p.getAL());
	fs.setTipologia(istanza.getTIPOLOGIA());
	festeSagreService.insert(fs);
    }
}
