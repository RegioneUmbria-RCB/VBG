package it.gruppoinit.pal.gp.core.features.sorteggi.scheduler;

import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Sorteggitestata;
import it.gruppoinit.pal.gp.core.domain.Taskscheduler;
import it.gruppoinit.pal.gp.core.domain.Taskschedulerparametri;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.scheduler.TaskEnum;
import it.gruppoinit.pal.gp.core.features.scheduler.TaskSchedulerAbstractService;
import it.gruppoinit.pal.gp.core.features.sorteggi.FiltriSorteggioBean;
import it.gruppoinit.pal.gp.core.features.sorteggi.categorie.SorteggiCategorieService;
import it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri.AllaDataParam;
import it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri.AmministrazioneParam;
import it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri.ArchivioPraticheParam;
import it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri.ArrotondamentoParam;
import it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri.CategoriaSorteggioParam;
import it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri.CategorieEscluseParam;
import it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri.CodiceAlgoritmoParam;
import it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri.CodiceComuneParam;
import it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri.CodiciInterventoParam;
import it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri.CreaMovimentoParam;
import it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri.DallaDataParam;
import it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri.DocumentoTipoParam;
import it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri.EscludiIstanzeSorteggiateParam;
import it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri.EsitoParam;
import it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri.GruppiParam;
import it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri.MailDestinatarioParam;
import it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri.MailTipoParam;
import it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri.PercentualeParam;
import it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri.ResponsabileParam;
import it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri.SoftwareParam;
import it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri.SorteggiEsclusiParam;
import it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri.StatoParam;
import it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri.TipoMovimentoParam;
import it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri.TipoProceduraParam;
import it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri.TipoRicercaMovimentoParam;
import it.gruppoinit.pal.gp.core.features.sorteggi.testata.SorteggitestataService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;

public class SorteggiSchedulerServiceImpl extends TaskSchedulerAbstractService {

    private AlberoprocService alberoProcService;
    private AmministrazioniService amministrazioniService;
    private ComuniassociatiService comuniAssociatiService;
    private MailtipoService mailTipoService;
    private ResponsabiliService responsabiliService;
    private SorteggiCategorieService sorteggiCategorieService;
    private SorteggitestataService sorteggitestataService;
    private TipiMovimentoService tipiMovimentoService;

    public static TaskEnum getTipo() {

	return TaskEnum.SORTEGGI;
    }

    public SorteggiSchedulerServiceImpl(AlberoprocService alberoProcService, AmministrazioniService amministrazioniService,
	    ComuniassociatiService comuniAssociatiService, MailtipoService mailTipoService, ResponsabiliService responsabiliService,
	    SorteggiCategorieService sorteggiCategorieService, SorteggitestataService sorteggitestataService,
	    TipiMovimentoService tipiMovimentoService) {

	this.alberoProcService = alberoProcService;
	this.amministrazioniService = amministrazioniService;
	this.comuniAssociatiService = comuniAssociatiService;
	this.mailTipoService = mailTipoService;
	this.responsabiliService = responsabiliService;
	this.sorteggiCategorieService = sorteggiCategorieService;
	this.sorteggitestataService = sorteggitestataService;
	this.tipiMovimentoService = tipiMovimentoService;
    }

    @Override
    public void elabora(Taskscheduler taskscheduler) {

	try {
	    FiltriSorteggioBean filter = this.recuperaFiltri(taskscheduler.getTaskschedulerparametris());
	    ORMHelper.setSoftware(filter.getSoftware());
	    filter.setSalvaSorteggio(Boolean.TRUE);
	    filter.setDescrizioneSorteggio(taskscheduler.getDescrizione());
	    Sorteggitestata testata = new Sorteggitestata();
	    testata.setSoftware(new Software(filter.getSoftware()));
	    this.sorteggitestataService.sorteggia(testata, filter);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    private FiltriSorteggioBean recuperaFiltri(Set<Taskschedulerparametri> parametri) {

	try {
	    FiltriSorteggioBean filter = new FiltriSorteggioBean();
	    filter.setAmministrazione(new AmministrazioneParam(this.amministrazioniService).getValueFromParameters(parametri));
	    filter.setArrotondamento(new ArrotondamentoParam().getValueFromParameters(parametri));
	    filter.setCodiceAlgoritmo(new CodiceAlgoritmoParam().getValueFromParameters(parametri));
	    filter.setCodiceComune(new CodiceComuneParam(this.comuniAssociatiService).getValueFromParameters(parametri));
	    filter.setCodiciStatoIstanza(new StatoParam().getValueFromParameters(parametri));
	    filter.setDataAl(new AllaDataParam().getValueFromParameters(parametri));
	    filter.setDataDal(new DallaDataParam().getValueFromParameters(parametri));
	    filter.setIdDocumentoTipo(new DocumentoTipoParam().getValueFromParameters(parametri));
	    filter.setEsito(new EsitoParam().getValueFromParameters(parametri));
	    filter.setEscludiIstanzeSorteggiate(new EscludiIstanzeSorteggiateParam().getValueFromParameters(parametri));
	    filter.setGruppiDiIstanze(new GruppiParam().getValueFromParameters(parametri));
	    filter.setIdProcedura(new TipoProceduraParam().getValueFromParameters(parametri));
	    filter.setIdTipiArchivioIstanza(new ArchivioPraticheParam().getValueFromParameters(parametri));
	    filter.setIdCategoriaSorteggio(new CategoriaSorteggioParam().getValueFromParameters(parametri));
	    filter.setIdCategorieDaEscludere(new CategorieEscluseParam(this.sorteggiCategorieService).getValueFromParameters(parametri));
	    filter.setIdSorteggiDaEscludere(new SorteggiEsclusiParam(this.sorteggitestataService).getValueFromParameters(parametri));
	    filter.setMailDestinatario(new MailDestinatarioParam().getValueFromParameters(parametri));
	    filter.setMailTipo(new MailTipoParam(this.mailTipoService).getValueFromParameters(parametri));
	    filter.setPercentuale(new PercentualeParam().getValueFromParameters(parametri));
	    filter.setResponsabile(new ResponsabileParam(this.responsabiliService).getValueFromParameters(parametri));
	    filter.setScCodiciInterventoProc(new CodiciInterventoParam(this.alberoProcService).getValueFromParameters(parametri));
	    filter.setSoftware(new SoftwareParam().getValueFromParameters(parametri));
	    filter.setTipoMovimento(new TipoMovimentoParam().getValueFromParameters(parametri));
	    filter.setTipoMovimentoDaCreare(new CreaMovimentoParam(this.tipiMovimentoService).getValueFromParameters(parametri));
	    filter.setTipoRicercaMovimento(new TipoRicercaMovimentoParam().getValueFromParameters(parametri));
	    return filter;
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }
}
