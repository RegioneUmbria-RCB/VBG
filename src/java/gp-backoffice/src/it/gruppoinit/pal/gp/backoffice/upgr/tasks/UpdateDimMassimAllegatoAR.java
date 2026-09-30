package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.FoArconfigurazione;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Softwareattivi;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.FoArconfigurazioneService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.SoftwareattiviService;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

@Component("upgrDimMassimAllegatoAR")
public class UpdateDimMassimAllegatoAR extends BaseJavaTask {

    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private SoftwareattiviService softwareattiviService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private FoArconfigurazioneService foArconfigurazioneService;

    @Override
    public void initialize() throws SetupRunException {

    }

    @Override
    public int run(Session session) throws SetupRunException {

	activityLogInfo("UpgrDimMassimAllegatoAR.run: INIZIO AGGIORNAMNENTO.....");
	///////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////
	activityLogInfo("UpgrDimMassimAllegatoAR.run: Recupero software attivi per l'installazione");
	List<Softwareattivi> softwareattivis = softwareattiviService.findAllAndExcludeTT(true);
	for (Softwareattivi softwareattivi : softwareattivis) {
	    String codSoftware = softwareattivi.getId().getFkSoftware();
	    activityLogInfo("UpgrDimMassimAllegatoAR.run: Recupero il valore del parametro {}.{} per il software {}",
		    new Object[] { WebConstants.VERTICALIZZAZIONE_AREA_RISERVATA, "DIMENSIONE_MASSIMA_ALLEGATI", codSoftware });
	    Integer dimMaxVerticalizzazione = null;
	    Verticalizzazioniparametri vp = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_AREA_RISERVATA,
		    "DIMENSIONE_MASSIMA_ALLEGATI", codSoftware);
	    if (vp != null && StringUtils.isNotBlank(vp.getValore())) {
		try {
		    dimMaxVerticalizzazione = Integer.parseInt(vp.getValore());
		} catch (NumberFormatException ne) {
		    activityLogInfo(
			    "UpgrDimMassimAllegatoAR.run: Impossibile recuperare il valore del parametro {}.{} per il software {}. Valore non numerico",
			    new Object[] { WebConstants.VERTICALIZZAZIONE_AREA_RISERVATA, "DIMENSIONE_MASSIMA_ALLEGATI", codSoftware });
		}
	    }
	    //////////////////////////////////////////////
	    if (dimMaxVerticalizzazione != null) {
		Software software = softwareService.findById(codSoftware);
		FoArconfigurazione foArconfigurazione = foArconfigurazioneService.findBySoftware(software);
		if (foArconfigurazione != null && foArconfigurazione.getDimensioneMassima() == null) {
		    try {
			activityLogInfo(
				"UpgrDimMassimAllegatoAR.run: Aggiorno il campo DimensioneMassima di foArconfigurazione per software {} con il valore {} ",
				new Object[] { codSoftware, dimMaxVerticalizzazione });
			String hql = "UPDATE FO_ARCONFIGURAZIONE  set DIMENSIONE_MASSIMA = ? where IDCOMUNE=? and SOFTWARE=?";
			int rowCount = 0;
			SQLQuery qCurrVal = session.createSQLQuery(hql);
			qCurrVal.setInteger(0, dimMaxVerticalizzazione);
			qCurrVal.setString(1, ORMHelper.getIdcomune());
			qCurrVal.setString(2, software.getCodice());
			rowCount = qCurrVal.executeUpdate();
			//			foArconfigurazione.setDimensioneMassima(dimMaxVerticalizzazione);
			//			
			//			foArconfigurazioneService.update(foArconfigurazione);
			session.flush();
			this.commitTransaction();
			session.flush();
		    } catch (Exception e) {
			activityLogInfo("UpgrDimMassimAllegatoAR.run:Aggiornamento foArconfigurazione non avvenuto : {}", new Object[] { e });
		    }
		}
	    }
	}
	return 0;
    }
}
