using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.Tinn.Verticalizzazioni
{
    public class VerticalizzazioniConfiguration
    {
        ProtocolloLogs _logs;

        public bool InviaSegnatura { get; private set; }
        public string Url { get; private set; }
        public string Username { get; private set; }
        public string Password { get; private set; }
        public string ApplicativoProtocollo { get; private set; }
        public string Codiceente { get; private set; }
        public string CodiceAoo { get; private set; }
        public string Uo { get; private set; }
        public bool InviaAllMovAvvio { get; private set; }
        public string TipoDocumentoPrincipale { get; private set; }
        public string TipoDocumentoAllegato { get; private set; }
        public bool UsaWsClassifiche { get; private set; }
        public string CodiceAmministrazione { get; private set; }

        public VerticalizzazioniConfiguration(ProtocolloLogs logs, VerticalizzazioneProtocolloTinn vert)
        {
            if (!vert.Attiva)
                throw new Exception("La verticalizzazione PROTOCOLLO_TINN non è attiva");
            
            _logs = logs;
            EstraiParametri(vert);
        }

        private void VerificaIntegritaParametri(VerticalizzazioneProtocolloTinn paramVert)
        {
            try
            {
                if (String.IsNullOrEmpty(paramVert.Operatore))
                    throw new Exception("IL PARAMETRO OPERATORE NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Password))
                    throw new Exception("IL PARAMETRO PASSWORD_WS NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Codiceente))
                    throw new Exception("IL PARAMETRO CODICE_ENTE NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.CodiceAoo))
                    throw new Exception("IL PARAMETRO CODICE_AOO NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Url))
                    throw new Exception("IL PARAMETRO URL RIGUARDANTE L'ENDPOINT DEL WEB SERVICE NON E' STATO VALORIZZATO");
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        private void EstraiParametri(VerticalizzazioneProtocolloTinn vert)
        {
            try
            {
                _logs.Debug("Inizio recupero valori da verticalizzazione");

                VerificaIntegritaParametri(vert);

                InviaSegnatura = vert.InviaSegnatura == "1";
                Url = vert.Url;
                Username = vert.Operatore;
                Password = vert.Password;
                ApplicativoProtocollo = vert.ApplicativoProtocollo;
                Codiceente = vert.Codiceente;
                CodiceAoo = vert.CodiceAoo;
                Uo = vert.Uo;
                InviaAllMovAvvio = vert.InviaAllMovAvvio == "1";
                TipoDocumentoPrincipale = vert.TipoDocumentoPrincipale;
                TipoDocumentoAllegato = vert.TipoDocumentoAllegato;
                UsaWsClassifiche = vert.UsaWsClassifiche == "1";
                CodiceAmministrazione = String.IsNullOrEmpty(vert.Codiceamministrazione) ? vert.Codiceente : vert.Codiceamministrazione;

                _logs.Debug("Fine recupero valori da verticalizzazioni");
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE PROTOCOLLO_TINN", ex);
            }
        }
    }
}

