
using SIGePro.Manager.VerticalizzazioniBase;
using System;

namespace SIGePro.Manager.Verticalizzazioni
{
    /// <summary>
    /// Gestione delle domande online 
    /// </summary>
    public partial class VerticalizzazioneAreaRiservata : Verticalizzazione
    {
        private static class Constants
        {
            public const string UrlServiziMobile = "SERVIZI_MOBILE_URL";
            public const string AliasSportelloServiziMobile = "SERVIZI_MOBILE_ALIAS_SPORTELLO";
            public const string IdSchedaEstremiDocumento = "ID_SCHEDA_ESTREMI_DOCUMENTO";
            public const string StatoinizialeIstanza = "STATO_INIZIALE_ISTANZA";
            public const string IntestazioneCertificatoInvio = "INTESTAZIONE_CERTIFICATO_INVIO";
            public const string DimensioneMassimaAllegati = "DIMENSIONE_MASSIMA_ALLEGATI";
            public const int DimensioneMassimaAllegatiDefault = 10485760;  // 10mb
            public const string WarningDimensioneMassimaAllegatiDefault = "Attenzione! Il file allegato supera la dimensione massima consentita ({0} MB).";
            public const string WarningDimensioneMassimaAllegati = "WARNING_DIMENSIONE_MASSIMA_ALL";
            public const string DescrizioneDelegaATrasmettere = "DESCR_DELEGA_A_TRASMETTERE";
            public const string UsernameUtenteAnonimo = "USERNAME_UTENTE_ANONIMO";
            public const string PasswordUtenteAnonimo = "PASSWORD_UTENTE_ANONIMO";
            public const string CiviciNumerici = "CIVICI_NUMERICI";
            public const string EsponentiNumerici = "ESPONENTI_NUMERICI";
            public const string UrlLogo = "URL_LOGO";
            public const string NascondiNoteMovimento = "NASCONDI_NOTE_MOVIMENTO";
            public const string IntegrazioniNoUploadAllegati = "INTEGR.NO_UPLOAD_ALLEGATI";
            public const string IntegrazioniNoUploadRiepiloghiSchedeDinamiche = "INTEGR.NO_UPLOAD_RIEPILOGHI_SD";
            public const string IntegrazioniNoInserimentoNote = "INTEGR_NO_INSERIMENTO_NOTE";
            public const string IntegrazioniNoNomiAllegati = "INTEGR_NO_NOMI_ALLEGATI";
            public const string IntegrazioniMessaggioTermineInvio = "INTEGR_MSG_TERMINE_INVIO";
            public const string TecnicoInSoggettiCollegati = "TECNICO_IN_SOGGETTI_COLLEGATI";
            public const string FlagSchedeDinamicheFirmateInRiepilogo = "FL_SCHEDE_FIRMATE_IN_RIEPILOGO";
            public const string UrlAuthenticationOverride = "URL_AUTHENTICATION_OVERRIDE";
            public const string AidaSmartCrossLoginUrl = "ASMART_CROSS_LOGIN_URL";
            public const string AidaSmartUrlNuovaDomanda = "ASMART_URL_NUOVA_DOMANDA";
            public const string AidaSmartUrlIstanzeInSospeso = "ASMART_URL_ISTANZE_IN_SOSPESO";
            public const string VisuraNascondiStatoIstanza = "VISURA_NASCONDI_STATO_ISTANZA";
            public const string VisuraNascondiResponsabili = "VISURA_NASCONDII_RESPONSABILI";
            public const string NascondiRigeneraRiepilogo = "NASCONDI_RIGENERA_RIEPILOGO";
            public const string NomeFileRicevuta = "NOME_FILE_RICEVUTA";
            public const string NomeFileRicevutaDefault = "certificato-di-invio.pdf";
            public const string DescrizioneFileRicevuta = "DESCRIZIONE_FILE_RICEVUTA";
            public const string DescrizioneFileRicevutaDefault = "Certificato di invio";
            public const string AbilitaTemplateDomanda = "ABILITA_TEMPLATE_DOMANDA";
            public const string IstanzePresentatePosizioneArchivio = "ISTANZEPRES_POSIZIONEARCHIVIO";
            public const string QuestionarioSoddisfazioneAttivo = "QUESTIONARIO_FO_ATTIVO";
            public const string ForzaStepLocalizzazioniSit = "FORZA_STEP_LOCALIZZAZIONI_SIT";

            public const string UsaAreaRiservataCore = "USA_AR_CORE";
            public const string BaseUrlFramework = "BASE_URL_FRAMEWORK";
            public const string BaseUrlCore = "BASE_URL_CORE";
            public const string DolUrlLayoutConfigService = "DOL_URL_LAYOUT_CONFIG_SERVICE";
            public const string DolUrlHomepageComune = "DOL_URL_HOMEPAGE_COMUNE";
            public const string DolUrlTerminiECondizioni = "DOL_URL_TERMINI_E_CONDIZIONI";
            public const string MaxRecordsRicercaPratiche = "MAX_RECORDS_RICERCA_PRATICHE";
            public const string AttivaCompilazioneOnceOnly = "ATTIVA_COMPILAZIONE_ONCE_ONLY";
            public const string DolModalitaInvioNotifiche = "DOL_MODALITA_INVIO_NOTIFICHE";
            public const string PagamentiPermettiAnnullamentoModello3 = "PAG_PERMETTI_ANNULLAMENTO_MOD3";
            public const string VerificaFirmaSoggettiRiepilogo = "VERIFICA_FIRMA_SOGG_RIEPILOGO";
        }

        private const string NOME_VERTICALIZZAZIONE = "AREA_RISERVATA";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;

        public VerticalizzazioneAreaRiservata()
        {

        }

        public VerticalizzazioneAreaRiservata(string idComuneAlias, string software) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software) { }


        public string StatoInizialeIstanza => this.GetString(Constants.StatoinizialeIstanza);
        public string MessaggioInvioFallito => this.GetString("MESSAGGIO_INVIO_FALLITO");
        public string IntestazioneDettaglioVisura => this.GetString("INTESTAZIONE_DETTAGLIO_VISURA");
        public string ImpostaAutoTecnico => this.GetString("IMPOSTA_AUTO_TECNICO");
        public string ScadCercaRichiedente => this.GetString("SCAD_CERCA_RICHIEDENTE");
        public string ScadCercaTecnico => this.GetString("SCAD_CERCA_TECNICO");
        public string ScadCercaAzienda => this.GetString("SCAD_CERCA_AZIENDA");
        public string ImpostaAutoRichiedente => this.GetString("IMPOSTA_AUTO_RICHIEDENTE");
        public string ScadCercaPartitaiva => this.GetString("SCAD_CERCA_PARTITAIVA");
        public string VisTCercaRichiedente => this.GetString("VIS_T_CERCA_RICHIEDENTE");
        public string VisTCercaTecnico => this.GetString("VIS_T_CERCA_TECNICO");
        public string VisTCercaAzienda => this.GetString("VIS_T_CERCA_AZIENDA");
        public string VisTCercaPartitaiva => this.GetString("VIS_T_CERCA_PARTITAIVA");
        public string VisTCercaSoggColl => this.GetString("VIS_T_CERCA_SOGG_COLL");
        public string VisNtCercaRichiedente => this.GetString("VIS_NT_CERCA_RICHIEDENTE");
        public string VisNtCercaTecnico => this.GetString("VIS_NT_CERCA_TECNICO");
        public string VisNtCercaAzienda => this.GetString("VIS_NT_CERCA_AZIENDA");
        public string VisNtCercaPartitaiva => this.GetString("VIS_NT_CERCA_PARTITAIVA");
        public string UrlApplicazioneFacct => this.GetString("URL_APPLICAZIONE_FACCT");
        public string WsNotificaistanzaUrl => this.GetString("WS_NOTIFICAISTANZA_URL");
        public string CodNaturaAutocertificabile => this.GetString("COD_NATURA_AUTOCERTIFICABILE");
        public string VerificaHashFilesFirmati => this.GetString("VERIFICA_HASH_FILES_FIRMATI");
        public string AtecoPrimariaIdCampo => this.GetString("ATECO_PRIMARIA_ID_CAMPO");
        public string VisNtCercaSoggColl => this.GetString("VIS_NT_CERCA_SOGG_COLL");
        public string VisFilCercaRichiedente => this.GetString("VIS_FIL_CERCA_RICHIEDENTE");
        public string VisFilCercaTecnico => this.GetString("VIS_FIL_CERCA_TECNICO");
        public string VisFilCercaAzienda => this.GetString("VIS_FIL_CERCA_AZIENDA");
        public string VisFilCercaPartitaiva => this.GetString("VIS_FIL_CERCA_PARTITAIVA");
        public string VisFilCercaSoggColl => this.GetString("VIS_FIL_CERCA_SOGG_COLL");
        public string ReturnToUrlPerServizi => this.GetString("RETURN_TO_URL_PER_SERVIZI");
        public string CentroServizi => this.GetString("CENTRO_SERVIZI");
        public string CentroServiziUrlBrevi => this.GetString("CENTRO_SERVIZI_URL_BREVI");
        public string UrlPaginaIniziale => this.GetString("URL_PAGINA_INIZIALE");
        public string AreaRiservataJavaAttiva => this.GetString("AREA_RISERVATA_JAVA_ATTIVA");
        public string UrlServiziMobile => this.GetString(Constants.UrlServiziMobile);
        public string AliasSportelloServiziMobile => this.GetString(Constants.AliasSportelloServiziMobile);
        public int? IdSchedaEstremiDocumento => this.GetInt(Constants.IdSchedaEstremiDocumento);
        public string IntestazioneCertificatoInvio => this.GetString(Constants.IntestazioneCertificatoInvio);
        public string WarningDimensioneMassimaAllegati => String.Format(this.GetStringOrDefault(Constants.WarningDimensioneMassimaAllegati, Constants.WarningDimensioneMassimaAllegatiDefault), this.DimensioneMassimaAllegati / 1048576);
        public int DimensioneMassimaAllegati => this.GetInt(Constants.DimensioneMassimaAllegati).GetValueOrDefault(Constants.DimensioneMassimaAllegatiDefault);
        public string DescrizioneDelegaATrasmettere => this.GetString(Constants.DescrizioneDelegaATrasmettere);
        public string UsernameUtenteAnonimo => this.GetString(Constants.UsernameUtenteAnonimo);
        public string PasswordUtenteAnonimo => this.GetString(Constants.PasswordUtenteAnonimo);
        public string CiviciNumerici => this.GetString(Constants.CiviciNumerici);
        public string EsponentiNumerici => this.GetString(Constants.EsponentiNumerici);
        public string UrlLogo => this.GetString(Constants.UrlLogo);
        public bool NascondiNoteMovimento => this.GetString(Constants.NascondiNoteMovimento) == "1";
        public bool IntegrazioniNoUploadAllegati => this.GetString(Constants.IntegrazioniNoUploadAllegati) == "1";
        public bool IntegrazioniNoUploadRiepiloghiSchedeDinamiche => this.GetString(Constants.IntegrazioniNoUploadRiepiloghiSchedeDinamiche) == "1";
        public bool IntegrazioniNoInserimentoNote => this.GetString(Constants.IntegrazioniNoInserimentoNote) == "1";
        public bool IntegrazioniNoNomiAllegati => this.GetString(Constants.IntegrazioniNoNomiAllegati) == "1";
        public bool TecnicoInSoggettiCollegati => this.GetString(Constants.TecnicoInSoggettiCollegati) == "1";
        public int FlagSchedeDinamicheFirmateInRiepilogo => this.GetInt(Constants.FlagSchedeDinamicheFirmateInRiepilogo) ?? 0;
        public string UrlAuthenticationOverride => this.GetString(Constants.UrlAuthenticationOverride);
        public string AidaSmartCrossLoginUrl => this.GetString(Constants.AidaSmartCrossLoginUrl);
        public string AidaSmartUrlNuovaDomanda => this.GetString(Constants.AidaSmartUrlNuovaDomanda);
        public string AidaSmartUrlIstanzeInSospeso => this.GetString(Constants.AidaSmartUrlIstanzeInSospeso);
        public bool VisuraNascondiStatoIstanza => this.GetString(Constants.VisuraNascondiStatoIstanza) == "1";
        public bool VisuraNascondiResponsabili => this.GetString(Constants.VisuraNascondiResponsabili) == "1";
        public bool NascondiRigeneraRiepilogo => this.GetString(Constants.NascondiRigeneraRiepilogo) == "1";
        public string NomeFileRicevuta => this.GetStringOrDefault(Constants.NomeFileRicevuta, Constants.NomeFileRicevutaDefault);
        public string DescrizioneFileRicevuta => this.GetStringOrDefault(Constants.DescrizioneFileRicevuta, Constants.DescrizioneFileRicevutaDefault);
        public bool AbilitaTemplateDomanda => this.GetString(Constants.AbilitaTemplateDomanda) == "1";
        public bool VisuraMostraPosizioneArchivio => this.GetString(Constants.IstanzePresentatePosizioneArchivio) == "1";
        public bool QuestionarioSoddisfazioneAttivo => this.GetString(Constants.QuestionarioSoddisfazioneAttivo) == "1";
        public bool ForzaStepLocalizzazioniSit => this.GetString(Constants.ForzaStepLocalizzazioniSit) == "1";
        public bool UsaAreaRiservataCore => this.GetString(Constants.UsaAreaRiservataCore) == "1";
        public string BaseUrlCore => this.GetString(Constants.BaseUrlCore);
        public string BaseUrlFramework => this.GetString(Constants.BaseUrlFramework);
        public string DolUrlLayoutConfigService => this.GetString(Constants.DolUrlLayoutConfigService);
        public string DolUrlHomepageComune => this.GetString(Constants.DolUrlHomepageComune);
        public string DolUrlTerminiECondizioni => this.GetString(Constants.DolUrlTerminiECondizioni);
        public int MaxRecordsRicercaPratiche => this.GetInt(Constants.MaxRecordsRicercaPratiche).GetValueOrDefault(200);

        public bool AttivaCompilazioneOnceOnly => this.GetInt(Constants.AttivaCompilazioneOnceOnly).GetValueOrDefault(0) == 1;
        public string DolModalitaInvioNotifiche => this.GetString(Constants.DolModalitaInvioNotifiche);
        public string IntegrazioniMessaggioTermineInvio => this.GetString(Constants.IntegrazioniMessaggioTermineInvio);

        public bool PagamentiPermettiAnnullamentoModello3 => this.GetInt(Constants.PagamentiPermettiAnnullamentoModello3).GetValueOrDefault(1) == 1;
        public bool VerificaFirmaSoggettiRiepilogo => this.GetInt(Constants.NascondiRigeneraRiepilogo).GetValueOrDefault(0) == 1;
    }
}
