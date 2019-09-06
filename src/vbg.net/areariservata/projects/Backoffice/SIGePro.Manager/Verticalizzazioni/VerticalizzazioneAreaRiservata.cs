
using System;
using System.IO;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Verticalizzazioni
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
            public const string TecnicoInSoggettiCollegati = "TECNICO_IN_SOGGETTI_COLLEGATI";
            public const string FlagSchedeDinamicheFirmateInRiepilogo = "FL_SCHEDE_FIRMATE_IN_RIEPILOGO";
            public const string UrlAuthenticationOverride = "URL_AUTHENTICATION_OVERRIDE";
            public const string AidaSmartCrossLoginUrl = "ASMART_CROSS_LOGIN_URL";
            public const string AidaSmartUrlNuovaDomanda = "ASMART_URL_NUOVA_DOMANDA";
            public const string AidaSmartUrlIstanzeInSospeso = "ASMART_URL_ISTANZE_IN_SOSPESO";
            public const string VisuraNascondiStatoIstanza = "VISURA_NASCONDI_STATO_ISTANZA";
            public const string VisuraNascondiResponsabili = "VISURA_NASCONDII_RESPONSABILI";

        }

        private const string NOME_VERTICALIZZAZIONE = "AREA_RISERVATA";

        public VerticalizzazioneAreaRiservata()
        {

        }

        public VerticalizzazioneAreaRiservata(string idComuneAlias, string software) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software) { }


        /// <summary>
        /// (Obsoleto: vd.tabella FR_ARCONFIGURAZIONE)Stato iniziale in cui si deve trovare un'istanza al momento dell'inserimento in Sigepro
        /// </summary>
        public string StatoInizialeIstanza
        {
            get { return GetString(Constants.StatoinizialeIstanza); }
            set { SetString(Constants.StatoinizialeIstanza, value); }
        }

        /// <summary>
        /// (Obsoleto: vd.tabella FR_ARCONFIGURAZIONE) Messaggio da visualizzare nel frontoffice nel caso in cui la creazione dell'istanza fallisca
        /// </summary>
        public string MessaggioInvioFallito
        {
            get { return GetString("MESSAGGIO_INVIO_FALLITO"); }
            set { SetString("MESSAGGIO_INVIO_FALLITO", value); }
        }

        /// <summary>
        /// (Obsoleto: vd.tabella FR_ARCONFIGURAZIONE) Definisce il messaggio visualizzato nell'intestazione della pagina che riepiloga l'istanza on-line appena inviata che è la stessa della visura.
        /// </summary>
        public string IntestazioneDettaglioVisura
        {
            get { return GetString("INTESTAZIONE_DETTAGLIO_VISURA"); }
            set { SetString("INTESTAZIONE_DETTAGLIO_VISURA", value); }
        }

        /// <summary>
        /// Specifica se l'anagrafica dell'utente deve essere aggiunta automaticamente come tecnico alle anagrafiche della domanda (nel caso in cui l'utente sia un tecnico). Valori possibili: 0 e 1
        /// </summary>
        public string ImpostaAutoTecnico
        {
            get { return GetString("IMPOSTA_AUTO_TECNICO"); }
            set { SetString("IMPOSTA_AUTO_TECNICO", value); }
        }

        /// <summary>
        /// Utilizzato nella ricerca scadenze. Specifica se l'utente loggato deve essere ricercato nel campo "richiedente" dell'istanza. Valori possibili: 0 e 1
        /// </summary>
        public string ScadCercaRichiedente
        {
            get { return GetString("SCAD_CERCA_RICHIEDENTE"); }
            set { SetString("SCAD_CERCA_RICHIEDENTE", value); }
        }

        /// <summary>
        /// Utilizzato nella ricerca scadenze. Specifica se l'utente loggato deve essere ricercato nel campo "tecnico" dell'istanza. Valori possibili: 0 e 1
        /// </summary>
        public string ScadCercaTecnico
        {
            get { return GetString("SCAD_CERCA_TECNICO"); }
            set { SetString("SCAD_CERCA_TECNICO", value); }
        }

        /// <summary>
        /// Utilizzato nella ricerca scadenze. Specifica se l'utente loggato deve essere ricercato nel campo "azienda" dell'istanza. Valori possibili: 0 e 1
        /// </summary>
        public string ScadCercaAzienda
        {
            get { return GetString("SCAD_CERCA_AZIENDA"); }
            set { SetString("SCAD_CERCA_AZIENDA", value); }
        }

        /// <summary>
        /// Specifica se l'anagrafica dell'utente deve essere aggiunta automaticamente alle anagrafiche della domanda (nel caso in cui l'utente non sia un tecnico). Valori possibili: 0 e 1
        /// </summary>
        public string ImpostaAutoRichiedente
        {
            get { return GetString("IMPOSTA_AUTO_RICHIEDENTE"); }
            set { SetString("IMPOSTA_AUTO_RICHIEDENTE", value); }
        }

        /// <summary>
        /// Utilizzato nella ricerca scadenze. Specifica se il codice fiscale dell'utente loggato deve essere ricercato anche nel campo "partitaiva" dell'anagrafica. Valori possibili: 0 e 1
        /// </summary>
        public string ScadCercaPartitaiva
        {
            get { return GetString("SCAD_CERCA_PARTITAIVA"); }
            set { SetString("SCAD_CERCA_PARTITAIVA", value); }
        }

        /// <summary>
        /// Utilizzato nella visura quando l'utente è un tecnico. Specifica se l'utente loggato deve essere ricercato nel campo "richiedente" dell'istanza. Valori possibili: 0 e 1
        /// </summary>
        public string VisTCercaRichiedente
        {
            get { return GetString("VIS_T_CERCA_RICHIEDENTE"); }
            set { SetString("VIS_T_CERCA_RICHIEDENTE", value); }
        }

        /// <summary>
        /// Utilizzato nella visura quando l'utente è un tecnico. Specifica se l'utente loggato deve essere ricercato nel campo "tecnico" dell'istanza. Valori possibili: 0 e 1
        /// </summary>
        public string VisTCercaTecnico
        {
            get { return GetString("VIS_T_CERCA_TECNICO"); }
            set { SetString("VIS_T_CERCA_TECNICO", value); }
        }

        /// <summary>
        /// Utilizzato nella visura quando l'utente è un tecnico. Specifica se l'utente loggato deve essere ricercato nel campo "azienda" dell'istanza. Valori possibili: 0 e 1
        /// </summary>
        public string VisTCercaAzienda
        {
            get { return GetString("VIS_T_CERCA_AZIENDA"); }
            set { SetString("VIS_T_CERCA_AZIENDA", value); }
        }

        /// <summary>
        /// Utilizzato nella visura quando l'utente è un tecnico. Specifica se il codice fiscale dell'utente loggato deve essere ricercato anche nel campo "partitaiva" delle anagrafiche dell'istanza. Valori possibili: 0 e 1
        /// </summary>
        public string VisTCercaPartitaiva
        {
            get { return GetString("VIS_T_CERCA_PARTITAIVA"); }
            set { SetString("VIS_T_CERCA_PARTITAIVA", value); }
        }

        /// <summary>
        /// Utilizzato nella visura quando l'utente è un tecnico. Specifica se l'utente loggato deve essere ricercato anche tra i soggetti collegati. Valori possibili: 0 e 1
        /// </summary>
        public string VisTCercaSoggColl
        {
            get { return GetString("VIS_T_CERCA_SOGG_COLL"); }
            set { SetString("VIS_T_CERCA_SOGG_COLL", value); }
        }

        /// <summary>
        /// Utilizzato nella visura quando l'utente non è un tecnico. Specifica se l'utente loggato deve essere ricercato nel campo "richiedente" dell'istanza. Valori possibili: 0 e 1
        /// </summary>
        public string VisNtCercaRichiedente
        {
            get { return GetString("VIS_NT_CERCA_RICHIEDENTE"); }
            set { SetString("VIS_NT_CERCA_RICHIEDENTE", value); }
        }

        /// <summary>
        /// Utilizzato nella visura quando l'utente non è un tecnico. Specifica se l'utente loggato deve essere ricercato nel campo "tecnico" dell'istanza. Valori possibili: 0 e 1
        /// </summary>
        public string VisNtCercaTecnico
        {
            get { return GetString("VIS_NT_CERCA_TECNICO"); }
            set { SetString("VIS_NT_CERCA_TECNICO", value); }
        }

        /// <summary>
        /// Utilizzato nella visura quando l'utente non è un tecnico. Specifica se l'utente loggato deve essere ricercato nel campo "azienda" dell'istanza. Valori possibili: 0 e 1
        /// </summary>
        public string VisNtCercaAzienda
        {
            get { return GetString("VIS_NT_CERCA_AZIENDA"); }
            set { SetString("VIS_NT_CERCA_AZIENDA", value); }
        }

        /// <summary>
        /// Utilizzato nella visura quando l'utente non è un tecnico. Specifica se il codice fiscale dell'utente loggato deve essere ricercato anche nel campo "partitaiva" delle anagrafiche dell'istanza. Valori possibili: 0 e 1
        /// </summary>
        public string VisNtCercaPartitaiva
        {
            get { return GetString("VIS_NT_CERCA_PARTITAIVA"); }
            set { SetString("VIS_NT_CERCA_PARTITAIVA", value); }
        }

        /// <summary>
        /// Url completo dell'applicazione FACCT che costruisce la domanda online secondo l'RFC 186 del CART (es: http://10.20.46.8:8080/CART-FACCT/presentazionedomanda/elencoEndo.htm)
        /// </summary>
        public string UrlApplicazioneFacct
        {
            get { return GetString("URL_APPLICAZIONE_FACCT"); }
            set { SetString("URL_APPLICAZIONE_FACCT", value); }
        }

        /// <summary>
        /// (Obsoleto: non più utilizzato) Url del web service di notifica acquisizione istanza utilizzato da DOCAREA.
        /// </summary>
        public string WsNotificaistanzaUrl
        {
            get { return GetString("WS_NOTIFICAISTANZA_URL"); }
            set { SetString("WS_NOTIFICAISTANZA_URL", value); }
        }

        /// <summary>
        /// (Obsoleto: non più utilizzato) Per verificare se il procedimento selezionato dall'utente è autocertificabile (non si sa a cosa serva)
        /// </summary>
        public string CodNaturaAutocertificabile
        {
            get { return GetString("COD_NATURA_AUTOCERTIFICABILE"); }
            set { SetString("COD_NATURA_AUTOCERTIFICABILE", value); }
        }

        /// <summary>
        /// Valori ammessi: 0 e 1. Se == 1 allora verrà verificato l'hash dei files firmati digitalmente durante gli step di firma dei riepiloghi dei dati dinamici, firma del riepilogo degli allegati presentati e firma del riepilogo della domanda 
        /// </summary>
        public string VerificaHashFilesFirmati
        {
            get { return GetString("VERIFICA_HASH_FILES_FIRMATI"); }
            set { SetString("VERIFICA_HASH_FILES_FIRMATI", value); }
        }

        /// <summary>
        /// Id del campo dinamico in cui riportare il codice dell'attività ateco che è stata selezionata in fase di individuazione dell'intervento
        /// </summary>
        public string AtecoPrimariaIdCampo
        {
            get { return GetString("ATECO_PRIMARIA_ID_CAMPO"); }
            set { SetString("ATECO_PRIMARIA_ID_CAMPO", value); }
        }

        /// <summary>
        /// Utilizzato nella visura quando l'utente non è un tecnico. Specifica se l'utente loggato deve essere ricercato anche tra i soggetti collegati. Valori possibili: 0 e 1
        /// </summary>
        public string VisNtCercaSoggColl
        {
            get { return GetString("VIS_NT_CERCA_SOGG_COLL"); }
            set { SetString("VIS_NT_CERCA_SOGG_COLL", value); }
        }

        /// <summary>
        /// Utilizzato nella visura quando viene specificato un filtro nel campo "richiedente". Specifica se il codice fiscale inserito deve essere ricercato nel campo "richiedente" dell'istanza. Valori possibili: 0 e 1
        /// </summary>
        public string VisFilCercaRichiedente
        {
            get { return GetString("VIS_FIL_CERCA_RICHIEDENTE"); }
            set { SetString("VIS_FIL_CERCA_RICHIEDENTE", value); }
        }

        /// <summary>
        /// Utilizzato nella visura quando viene specificato un filtro nel campo "richiedente". Specifica se il codice fiscale inserito deve essere ricercato nel campo "tecnico" dell'istanza. Valori possibili: 0 e 1
        /// </summary>
        public string VisFilCercaTecnico
        {
            get { return GetString("VIS_FIL_CERCA_TECNICO"); }
            set { SetString("VIS_FIL_CERCA_TECNICO", value); }
        }

        /// <summary>
        /// Utilizzato nella visura quando viene specificato un filtro nel campo "richiedente". Specifica se il codice fiscale inserito deve essere ricercato nel campo "azienda" dell'istanza. Valori possibili: 0 e 1
        /// </summary>
        public string VisFilCercaAzienda
        {
            get { return GetString("VIS_FIL_CERCA_AZIENDA"); }
            set { SetString("VIS_FIL_CERCA_AZIENDA", value); }
        }

        /// <summary>
        /// Utilizzato nella visura quando viene specificato un filtro nel campo "richiedente". Specifica se il codice fiscale specificato deve essere ricercato anche nel campo "partitaiva" delle anagrafiche dell'istanza. Valori possibili: 0 e 1
        /// </summary>
        public string VisFilCercaPartitaiva
        {
            get { return GetString("VIS_FIL_CERCA_PARTITAIVA"); }
            set { SetString("VIS_FIL_CERCA_PARTITAIVA", value); }
        }

        /// <summary>
        /// Utilizzato nella visura quando viene specificato un filtro nel campo "richiedente". Specifica se il codice fiscale specificato deve essere ricercato anche tra i soggetti collegati dell'istanza. Valori possibili: 0 e 1
        /// </summary>
        public string VisFilCercaSoggColl
        {
            get { return GetString("VIS_FIL_CERCA_SOGG_COLL"); }
            set { SetString("VIS_FIL_CERCA_SOGG_COLL", value); }
        }


        /// <summary>
        /// Specifica l'url a cui tornare quando si accede all'Area Riservata tramite url parlante
        /// </summary>
        public string ReturnToUrlPerServizi
        {
            get { return GetString("RETURN_TO_URL_PER_SERVIZI"); }
            set { SetString("RETURN_TO_URL_PER_SERVIZI", value); }
        }

        /// <summary>
        /// Se presente abilita 
        /// l'accesso diretto ad un singolo servizio tramite url parlante o url diretto con codice intervento (dipende dal parametro 
        /// CENTRO_SERVIZI_URL_BREVI
        /// </summary>
        public string CentroServizi
        {
            get { return GetString("CENTRO_SERVIZI"); }
            set { SetString("CENTRO_SERVIZI", value); }
        }

        /// <summary>
        /// Ha valore solo 
        /// se attivo il parametro CENTRO_SERVIZI. Se presente vincola l'accesso all'Area Riservata solo tramite url parlanti es. /servizi/servizio001
        /// </summary>
        public string CentroServiziUrlBrevi
        {
            get { return GetString("CENTRO_SERVIZI_URL_BREVI"); }
            set { SetString("CENTRO_SERVIZI_URL_BREVI", value); }
        }

        /// <summary>
        /// Url della pabina iniziale dell'area riservata
        /// </summary>
        public string UrlPaginaIniziale
        {
            get { return GetString("URL_PAGINA_INIZIALE"); }
            set { SetString("URL_PAGINA_INIZIALE", value); }
        }

        /// <summary>
        /// Indica se e' attivo l'applicativo area riservata java. Valori ammessi S (Si) N (No)
        /// </summary>
        public string AreaRiservataJavaAttiva
        {
            get { return GetString("AREA_RISERVATA_JAVA_ATTIVA"); }
            set { SetString("AREA_RISERVATA_JAVA_ATTIVA", value); }
        }

        public string UrlServiziMobile
        {
            get { return GetString(Constants.UrlServiziMobile); }
            set { SetString(Constants.UrlServiziMobile, value); }
        }

        public string AliasSportelloServiziMobile
        {
            get { return GetString(Constants.AliasSportelloServiziMobile); }
            set { SetString(Constants.AliasSportelloServiziMobile, value); }
        }

        public int? IdSchedaEstremiDocumento
        {
            get { return GetInt(Constants.IdSchedaEstremiDocumento); }
            set { SetString(Constants.IdSchedaEstremiDocumento, value.ToString()); }
        }

        public string IntestazioneCertificatoInvio
        {
            get { return GetString(Constants.IntestazioneCertificatoInvio); }
        }

        /// <summary>
        /// Il messaggio per avvisare l'utente che ha superato la dimensione massima dei file caricabili per una domanda. Il messaggio di default e' '<b>Attenzione!
        /// E' stato raggiunto il limite di 30 Mb di allegati e la domanda non può essere inviata. <br />Si prega di verificare la dimensione dei singoli file ottimizzandone la risoluzione.</b>'
        /// </summary>
        public string WarningDimensioneMassimaAllegati
        {
            get
            {
                var str = GetString(Constants.WarningDimensioneMassimaAllegati);

                if (String.IsNullOrEmpty(str))
                    str = Constants.WarningDimensioneMassimaAllegatiDefault;

                return String.Format(str, (int)(DimensioneMassimaAllegati / 1048576));
            }
        }

        public int DimensioneMassimaAllegati
        {
            get
            {
                var strVal = GetInt(Constants.DimensioneMassimaAllegati);

                if (!strVal.HasValue)
                    return Constants.DimensioneMassimaAllegatiDefault;

                return strVal.Value;
            }
        }

        public string DescrizioneDelegaATrasmettere
        {
            get
            {
                return GetString(Constants.DescrizioneDelegaATrasmettere);
            }
        }

        public string UsernameUtenteAnonimo
        {
            get
            {
                return GetString(Constants.UsernameUtenteAnonimo);
            }
        }

        public string PasswordUtenteAnonimo
        {
            get
            {
                return GetString(Constants.PasswordUtenteAnonimo);
            }
        }


        public string CiviciNumerici
        {
            get
            {
                return GetString(Constants.CiviciNumerici);
            }
        }

        public string EsponentiNumerici
        {
            get
            {
                return GetString(Constants.EsponentiNumerici);
            }
        }

        public string UrlLogo
        {
            get
            {
                return GetString(Constants.UrlLogo);
            }
        }

        public bool NascondiNoteMovimento
        {
            get
            {
                return GetString(Constants.NascondiNoteMovimento) == "1";
            }
        }

        public bool IntegrazioniNoUploadAllegati
        {
            get
            {
                return GetString(Constants.IntegrazioniNoUploadAllegati) == "1";
            }
        }

        public bool IntegrazioniNoUploadRiepiloghiSchedeDinamiche
        {
            get
            {
                return GetString(Constants.IntegrazioniNoUploadRiepiloghiSchedeDinamiche) == "1";
            }
        }
        public bool IntegrazioniNoInserimentoNote
        {
            get
            {
                return GetString(Constants.IntegrazioniNoInserimentoNote) == "1";
            }
        }

        public bool IntegrazioniNoNomiAllegati
        {
            get
            {
                return GetString(Constants.IntegrazioniNoNomiAllegati) == "1";
            }
        }

        public bool TecnicoInSoggettiCollegati
        {
            get
            {
                return GetString(Constants.TecnicoInSoggettiCollegati) == "1";
            }
        }

        public int FlagSchedeDinamicheFirmateInRiepilogo => GetInt(Constants.FlagSchedeDinamicheFirmateInRiepilogo).GetValueOrDefault(0);
        public string UrlAuthenticationOverride => GetString(Constants.UrlAuthenticationOverride);
        public string AidaSmartCrossLoginUrl => GetString(Constants.AidaSmartCrossLoginUrl);
        public string AidaSmartUrlNuovaDomanda => GetString(Constants.AidaSmartUrlNuovaDomanda);
        public string AidaSmartUrlIstanzeInSospeso => GetString(Constants.AidaSmartUrlIstanzeInSospeso);
        public bool VisuraNascondiStatoIstanza => GetString(Constants.VisuraNascondiStatoIstanza) == "1";
        public bool VisuraNascondiResponsabili => GetString(Constants.VisuraNascondiResponsabili) == "1";
        
    }
}
