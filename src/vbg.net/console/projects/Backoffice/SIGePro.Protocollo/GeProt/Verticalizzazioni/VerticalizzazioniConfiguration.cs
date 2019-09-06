using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.GeProt.Verticalizzazioni
{
    public class VerticalizzazioniConfiguration
    {
        ProtocolloLogs _logs;

        public string Url { get; private set; }
        public string CodiceAmministrazione { get; private set; }
        public string CodiceAoo { get; private set; }
        public string DenominazioneAmministrazione { get; private set; }
        public string DenominazioneAoo { get; private set; }
        public string IndirizzoTelematico { get; private set; }
        public string Operatore { get; private set; }
        public string Password { get; private set; }
        public string ProtoDocType { get; private set; }
        public bool InvioPec { get; private set; }

        public VerticalizzazioniConfiguration(VerticalizzazioneProtocolloGeprot vert, ProtocolloLogs logs)
        {
            if (!vert.Attiva)
                throw new Exception("La verticalizzazione PROTOCOLLO_GEPROT non è attiva");

            _logs = logs;

            EstraiParametri(vert);
        }

        private void VerificaIntegritaParametri(VerticalizzazioneProtocolloGeprot paramVert)
        {
            try
            {
                if (String.IsNullOrEmpty(paramVert.Url))
                    throw new Exception("IL PARAMETRO URL NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Operatore))
                    throw new Exception("IL PARAMETRO OPERATORE NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Password))
                    throw new Exception("IL PARAMETRO PASSWORD NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Codiceamministrazione))
                    throw new Exception("IL PARAMETRO CODICEAMMINISTRAZIONE NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Codiceaoo))
                    throw new Exception("IL PARAMETRO CODICEAOO NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Protdoctype))
                    throw new Exception("IL PARAMETRO PROTODOCTYPE NON E' STATO VALORIZZATO");

            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        private void EstraiParametri(VerticalizzazioneProtocolloGeprot vert)
        {
            try
            {
                _logs.Debug("Inizio recupero valori da verticalizzazione");

                VerificaIntegritaParametri(vert);

                Url = vert.Url;
                CodiceAmministrazione = vert.Codiceamministrazione;
                CodiceAoo = vert.Codiceaoo;
                DenominazioneAmministrazione = vert.Denominazioneamministrazione;
                DenominazioneAoo = vert.Denominazioneaoo;
                IndirizzoTelematico = vert.Indirizzotelematico;
                Operatore = vert.Operatore;
                Password = vert.Password;
                ProtoDocType = vert.Protdoctype;
                InvioPec = vert.InvioPec == "1";

                _logs.Debug("Fine recupero valori da verticalizzazioni");
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE PROTOCOLLO_GEPROT {0}", ex.Message));
            }
        }
    }
}
