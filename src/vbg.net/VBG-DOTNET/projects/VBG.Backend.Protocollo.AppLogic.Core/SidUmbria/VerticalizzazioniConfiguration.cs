using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Core.SidUmbria
{
    public class VerticalizzazioniConfiguration
    {
        ProtocolloLogs _logs;

        public string Token { get; private set; }
        public string Service { get; private set; }
        public string Url { get; private set; }
        public string DestinatarioCC { get; private set; }
        public string Versione { get; private set; }
        public bool UsaFtp { get; private set; }
        public string FtpUrl { get; private set; }
        public string FtpUsername { get; private set; }
        public string FtpPassword { get; private set; }
        public bool DisabilitaControlloP7m { get; private set; }

        public VerticalizzazioniConfiguration(VerticalizzazioneProtocolloSidumbria vert, ProtocolloLogs logs)
        {
            if (!vert.Attiva)
                throw new Exception("La verticalizzazione PROTOCOLLO_SIDUMBRIA non è attiva");

            _logs = logs;

            EstraiParametri(vert);
        }

        private void VerificaIntegritaParametri(VerticalizzazioneProtocolloSidumbria vert)
        {
            try
            {
                if (String.IsNullOrEmpty(vert.Url))
                    throw new Exception("IL PARAMETRO URL NON E' STATO VALORIZZATO");
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        private void EstraiParametri(VerticalizzazioneProtocolloSidumbria vert)
        {
            try
            {
                _logs.Debug("Inizio recupero valori da verticalizzazione");

                VerificaIntegritaParametri(vert);


                this.Url = vert.Url;
                this.Token = vert.Token;
                this.Service = vert.Service;
                this.DestinatarioCC = vert.DestinatarioCc;
                this.UsaFtp = vert.UsaFtp == "1";
                this.FtpUrl = vert.FtpUrl;
                this.FtpUsername = vert.FtpUsername;
                this.FtpPassword = vert.FtpPassword;
                this.Versione = vert.Versione;
                this.DisabilitaControlloP7m = vert.DisabilitaControlloP7m == "1";

                _logs.Debug("Fine recupero valori da verticalizzazioni");
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE PROTOCOLLO_SIDUMBRIA, ERRORE: {0}", ex.Message), ex);
            }
        }
    }
}
