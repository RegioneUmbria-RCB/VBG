package it.gruppoinit.pal.gp.core.features.verticalizzazioni;

import java.util.Date;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Oggetti;

/**
 * L'interfaccia viene utilizzata per specializzare la gestione delle verticalizzazioni; in questo caso rappresenta la
 * verticalizzazione {@link WebConstants#VERTICALIZZAZIONE_COMPORTAMENTI_MERCATI}
 * 
 * 
 * @author smendichi
 *
 */
public interface IVerticalizzazioneComportamentiMercatiService {

    public static final String nomeVerticalizzazione = "COMPORTAMENTI_MERCATI";
    public static final String parAutorizUniqueNumeroComune = "AUTORIZ_UNIQUE_NUMERO_COMUNE";
    public static final String parBloccaAccessoMercNelFuturo = "BLOCCA_ACCESSO_MERC_NEL_FUTURO";
    public static final String parCodiceIstatBattitori = "CODICE_ISTAT_BATTITORI";
    public static final String parDataPosDebConcessionari = "DATA_POS_DEB_CONCESSIONARI";
    public static final String parGestisciProprietario = "GESTISCI_PROPRIETARIO";
    public static final String parNascondiBottoneConcPres = "NASCONDI_BOTTONE_CONC_PRES";
    public static final String parInsConcessionariPresenti = "INS_CONCESSIONARI_PRESENTI";
    public static final String parRestCercaComuneListEsclusi = "REST_CERCA_COMUNE_LIST_ESCUSI";
    public static final String parRestVerificaCFPIva = "REST_VERIFICA_CF_PIVA";
    public static final String parScCodiceIstNuovSpuntista = "SC_CODICE_IST_NUOV_SPUNTISTA";
    public static final String parServGradAddNomeGiorno = "SERV_GRAD_ADD_NOME_GIORNO";
    public static final String parUrlAppAmbulanteWeb = "URL_APP_AMBULANTE_WEB";
    public static final String parUrlAppSpuntaDigitale = "URL_APP_SPUNTA_DIGITALE";
    public static final String parSettoriNonSalvaMerceologie = "SETTORI_NON_SALVA_MERCEOLOGIE";
    public static final String parQueryOrdinamentoGraduatorieDefault = "CRIT_ORD_GRAD_MERC_PREDEFINITO";
    public static final String parQueryOrdinamentoGraduatorieSeCFGSpuntisti = "CRIT_ORD_GRAD_MERC_SE_CFGSPUNT";
    public static final String parChiusuraGiornateCheckGGPrecedenti = "CHIUS_GG_MERC_CHECK_GG_PREC";
    public static final String parAppVigiliBloccaChiusuraGiornataAlCheckPosteggiNonOccupati = "APP_VIGILI_BLOCCA_CHIUS_GG";
    public static final String parAppVigiliMessaggioChiusuraGiornataAlCheckPosteggiNonOccupati = "APP_VIGILI_BLOCCA_CHIUS_GG_MSG";
    public static final String MESSAGGIO_CHIUSURAGIORNATA_AL_CHECK_POSTEGGI_NON_OCCUPATI = "Attenzione! Non tutti i posteggi sono stati assegnati si intende procedere con la chiusura della giornata?";
    public static final String parAppVigiliMessaggioChiusuraGiornataMercato = "APP_VIGILI_MSG_CHIUS_GG";
    public static final String MESSAGGIO_CHIUSURAGIORNATAMERCATO_PREDEFINITA = "<p>E' stata richiesta la chiusura della giornata in corso, l'operazione bloccherà ulteriori modifiche alla giornata e sarà annullabile solo da un'operatore dell'ente. </p><p>Chiudere la giornata corrente?</p>";
    public static final String parAppVigiliVisualizzaTerminaAppello = "APP_VIGILI_VIS_TERMINA_APPELLO";
    public static final String parAppApmbulantiGiorniFiltroRicercaPagamenti = "APP_AMB_GG_FILTRO_RIC_PAGAM";
    public static final String PAR_COD_TIPODOC_STAMPA = "COD_TIPODOC_STAMPA";
    public static final String PAR_ATTIVA_GIORNATE_NULLE = "ATTIVA_GIORNATE_NULLE";
    public static final String REGISTRO_AUT_PONTE = "REGISTRO_AUT_PONTE";
    public static final String appambMostraBollPagam = "APP_AMB_MOSTRA_BOLL_PAGAM";
    public static final String PAR_APP_VIGILI_NASCONDI_INS_SPUNT = "APP_VIGILI_NASCONDI_INS_SPUNT";

    boolean isAttiva();

    boolean autorizUniqueNumeroComune();

    boolean bloccaAccessoMercNelFuturo();

    String codiceIstatBattitori();

    Date dataPosDebConcessionari();

    boolean gestisciProprietario();

    boolean insConcessionariPresenti();

    boolean nascondiBottoneConcPres();

    String restCercaComuneListEsclusi();

    boolean restVerificaCFPIva();

    String scCodiceIstNuovSpuntista();

    boolean servGradAddNomeGiorno();

    String urlAppAmbulanteWeb();

    String urlAppSpuntaDigitale();

    String settoriNonSalvaMerceologie();

    String criteriOrdinamentoGraduatoriePredefinito();

    String criteriOrdinamentoGraduatorieSeAttivaGradSpuntisti();

    boolean verificaChiusuraGiornatePrecedenti();

    boolean bloccaChiusuraGiornataAlCheckPosteggiNonOccupati();

    String messaggioGiornataAlCheckPosteggiNonOccupati();

    String messaggioChiusuraGiornataMercato();

    boolean visualizzaTerminaAppello();

    Integer codTipodocStampa();

    Oggetti tipoDocStampaAutXsl();

    Date dataInizioRicercaPagamentiAppAmbulanti();

    boolean isAttivaGiornateNulle();

    String registroAutPonte();

    boolean isNascondiInserimantoSpuntista();
}
