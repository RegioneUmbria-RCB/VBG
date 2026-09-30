using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.MittentiDestinatari;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Verticalizzazioni
{
    public class InsielVerticalizzazioniConfiguration
    {
        ProtocolloLogs _logs;

        public string CodiceRegistro { get; private set; }
        public string CodiceUfficioOperante { get; private set; }
        public Utente Utente { get; private set; }
        public bool DisabilitaAnnullaProtocollo { get; private set; }
        public bool EscludiClassifica { get; private set; }
        public string Password { get; private set; }
        public bool TipiDocumentoWs { get; private set; }
        public string Url { get; private set; }
        public string UrlUploadFile { get; private set; }
        public bool IsMonfalcone { get; private set; }
        public bool UsaLivelliClassifica { get; private set; }
        public string Iteratti { get; private set; }
        public bool UsaWsClassifiche { get; private set; }
        public bool InviaPec { get; private set; }
        public string MittentePec { get; private set; }
        public bool UsaPredisponiAnagrafica { get; private set; }
        public bool DisattivaCtrlDocs { get; private set; }
        public bool DisabilitaValidazioneCapIta { get; private set; }

        public TipoGestioneAnagraficaEnum.TipoGestione TipoGestionePec { get; private set; }
        public TipoGestioneAnagraficaEnum.TipoAggiornamento TipoAggiornamentoAnagrafica { get; private set; }

        public InsielVerticalizzazioniConfiguration(ProtocolloLogs logs, ParametriRegoleInfo par)
        {
            // TODO verifica l'utilità
            //if (!par.Attiva)
            //    throw new Exception("La verticalizzazione PROTOCOLLO_INSIEL non è attiva");

            _logs = logs;
            EstraiParametri(par);
        }

        private void VerificaIntegritaParametri(ParametriRegoleInfo par)
        {
            if (String.IsNullOrEmpty(par.Utente.Codice))
                throw new Exception("IL PARAMETRO CODICEUTENTE NON E' STATO VALORIZZATO");

            //if (String.IsNullOrEmpty(paramVert.Password))
            //    throw new Exception("IL PARAMETRO PASSWORD NON E' STATO VALORIZZATO");

            if (String.IsNullOrEmpty(par.UrlWS))
                throw new Exception("IL PARAMETRO URL RIGUARDANTE L'ENDPOINT DEL WEB SERVICE NON E' STATO VALORIZZATO");

            //if (String.IsNullOrEmpty(par.UrlUploadFile))
            //    throw new Exception("IL PARAMETRO URL UPLOAD FILE RIGUARDANTE L'ENDPOINT DEL WEB SERVICE DI UPLOAD FILE NON E' STATO VALORIZZATO");
        }

        public void EstraiParametri(ParametriRegoleInfo par)
        {
            try
            {
                _logs.Debug("Inizio recupero valori da verticalizzazione");

                VerificaIntegritaParametri(par);

                this.CodiceRegistro = par.CodiceRegistro;
                this.CodiceUfficioOperante = par.CodiceUfficioOperante;
                this.Utente = par.Utente;
                this.DisabilitaAnnullaProtocollo = par.DisabilitaAnnullaProtocollo == "1";
                this.EscludiClassifica = par.EscludiClassifica == "1";
                //this.Password = vert.Password;
                this.TipiDocumentoWs = par.TipiDocumentoWs == "1";
                this.Url = par.UrlWS;
                this.UrlUploadFile = par.UrlUploadFile;
                this.IsMonfalcone = par.AttivaMonf == "1";
                this.UsaLivelliClassifica = par.UsaLivelliClassifica == "1";
                this.Iteratti = par.TipoUfficioIteratti;
                this.UsaWsClassifiche = par.UsaWsClassifiche == "1";
                this.InviaPec = par.InviaPec == "1";
                this.DisabilitaValidazioneCapIta = par.DisabilitaValidazioneCapIta == "1";
                this.MittentePec = par.MittentePec;
                this.DisattivaCtrlDocs = par.DisattivaCtrlDocs == "1";
                this.UsaPredisponiAnagrafica = par.UsaPredisponiAnagrafica == "1";

                this.TipoGestionePec = TipoGestioneAnagraficaEnum.TipoGestione.RICERCA_CODICE_FISCALE;
                if (!String.IsNullOrEmpty(par.TipoGestionePec))
                {
                    TipoGestioneAnagraficaEnum.TipoGestione tmpTipoGestionePec;
                    var isParsable = Enum.TryParse(par.TipoGestionePec, out tmpTipoGestionePec);

                    if (!isParsable)
                    {
                        throw new Exception($"IL VALORE {par.TipoGestionePec} RELATIVO AL PARAMETRO TIPO_GESTIONE_PEC NON E' CORRETTO");
                    }

                    this.TipoGestionePec = tmpTipoGestionePec;
                }

                this.TipoAggiornamentoAnagrafica = TipoGestioneAnagraficaEnum.TipoAggiornamento.NO_AGGIORNAMENTO;
                if (!String.IsNullOrEmpty(par.TipoAggiornamentoAnagrafica))
                {
                    TipoGestioneAnagraficaEnum.TipoAggiornamento tmpTipoAggiornamento;
                    var isParsable = Enum.TryParse(par.TipoAggiornamentoAnagrafica, out tmpTipoAggiornamento);

                    if (!isParsable)
                    {
                        throw new Exception($"IL VALORE {par.TipoAggiornamentoAnagrafica} RELATIVO AL PARAMETRO TIPO_AGGIORNAMENTO_ANAG NON E' CORRETTO");
                    }

                    this.TipoAggiornamentoAnagrafica = tmpTipoAggiornamento;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE PROTOCOLLO_INSIELREST, {ex.Message}", ex);
            }
        }
    }
}
