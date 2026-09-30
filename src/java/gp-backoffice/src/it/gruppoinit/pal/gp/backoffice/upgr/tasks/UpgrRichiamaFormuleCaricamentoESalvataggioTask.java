package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.IUpgrRichiamaFormuleCaricamentoESalvataggioService;
import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.UpgrRichiamaFormuleCaricamentoESalvataggioRequest;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

@Component("upgrUpgrRichiamaFormuleCaricamentoESalvataggioTask")
public class UpgrRichiamaFormuleCaricamentoESalvataggioTask extends BaseJavaTask {

    private IUpgrRichiamaFormuleCaricamentoESalvataggioService service;

    @Autowired
    public void setService(IUpgrRichiamaFormuleCaricamentoESalvataggioService service) {

	this.service = service;
    }

    @Override
    public void initialize() throws SetupRunException {

	//implementazione non necessaria
    }

    /**
     * <pre>
     *  &lt;java-task id="UPGR_RICHIAMA_FORMULE_CARICAMENTO_E_SALVATAGGIO" 
     *  		spring-bean-id="upgrUpgrRichiamaFormuleCaricamentoESalvataggioTask"
     *  		java-class="it.gruppoinit.pal.gp.backoffice.upgr.tasks.UpgrRichiamaFormuleCaricamentoESalvataggioTask" 
     * 			fail-on-error="true">
     *         &lt;param name="idScheda" value="12">&lt;/param>
     *         &lt;param name="listaScId" value="1,2,7862,91000001">&lt;/param>
     *  &lt;/java-task>
     * </pre>
     */
    @Override
    public int run(Session arg0) throws SetupRunException {

	String listaScId = getParameterValue("listaScId");
	if (StringUtils.isBlank(listaScId)) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il task UPGR_RICHIAMA_FORMULE_CARICAMENTO_E_SALVATAGGIO senza valorizzare il parametro listaScId");
	}
	String idScheda = getParameterValue("idScheda");
	if (StringUtils.isBlank(idScheda)) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il task UPGR_RICHIAMA_FORMULE_CARICAMENTO_E_SALVATAGGIO senza valorizzare il parametro idScheda");
	}
	UpgrRichiamaFormuleCaricamentoESalvataggioRequest request = new UpgrRichiamaFormuleCaricamentoESalvataggioRequest();
	request.setListaScId(listaScId);
	request.setIdScheda(Integer.parseInt(idScheda));
	this.service.elaboraFormule(request);
	return 0;
    }
}
