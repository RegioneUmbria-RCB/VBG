using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.Sicraweb.Verticalizzazioni
{
    public class VerticalizzazioniConfiguration
    {
        ProtocolloLogs _logs;

        public string Url { get; private set; }
        public string CodiceAmministrazione { get; private set; }
        public string CodiceAoo { get; private set; }
        public bool UsaMonoMittDest { get; private set; }
        public bool InviaSegnatura { get; private set; }
        public string ConnectionString { get; private set; }
        public string UrlWsAllegati { get; private set; }

        public VerticalizzazioniConfiguration(ProtocolloLogs logs, VerticalizzazioneProtocolloSicraweb vert)
        {
            if (!vert.Attiva)
                throw new Exception("La verticalizzazione PROTOCOLLO_SICRAWEB non è attiva");

            _logs = logs;
            EstraiParametri(vert);
        }

        private void VerificaIntegritaParametri(VerticalizzazioneProtocolloSicraweb paramVert)
        {
            try
            {
                if (String.IsNullOrEmpty(paramVert.Url))
                    throw new Exception("PARAMETRO URL NON VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Connectionstring))
                    throw new Exception("PARAMETRO CONNECTIONSTRING NON VALORIZZATO");
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        private void EstraiParametri(VerticalizzazioneProtocolloSicraweb vert)
        {
            try
            {
                _logs.Debug("Inizio recupero valori da verticalizzazione");

                VerificaIntegritaParametri(vert);

                this.Url = vert.Url;
                this.CodiceAmministrazione = vert.Codiceamministrazione;
                this.CodiceAoo = vert.Codiceaoo;
                this.ConnectionString = vert.Connectionstring;
                this.InviaSegnatura = vert.InviaSegnatura == "1";
                this.UsaMonoMittDest = vert.UsaMonoMittdest == "1";
                this.UrlWsAllegati = vert.UrlWsAllegati;

                _logs.Debug("Fine recupero valori da verticalizzazioni");
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE PROTOCOLLO_SIPRWEB", ex);
            }
        }
    }
}

