using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.PaDoc.Verticalizzazioni
{
    public class VerticalizzazioniConfiguration
    {
        ProtocolloLogs _logs;

        public string UrlResponseService { get; private set; }
        public string UrlProto { get; private set; }
        public string Dominio { get; private set; }
        public string CodiceAmministrazione { get; private set; }
        public string CodiceAoo { get; private set; }

        public string UrlLeggi { get; private set; }
        public string Username { get; private set; }
        public string Password { get; private set; }
        public string Verb { get; private set; }

        public VerticalizzazioniConfiguration(ProtocolloLogs logs, VerticalizzazioneProtocolloPadoc vert)
        {
            if (!vert.Attiva)
                throw new Exception("La verticalizzazione PROTOCOLLO_PADOC non è attiva");

            _logs = logs;
            EstraiParametri(vert);
        }

        private void VerificaIntegritaParametri(VerticalizzazioneProtocolloPadoc paramVert)
        {
            try
            {
                if (String.IsNullOrEmpty(UrlResponseService))
                    throw new Exception("URL SERVLET DI RISPOSTA NON PRESENTE");

                if (String.IsNullOrEmpty(Dominio))
                    throw new Exception("DOMINIO NON PRESENTE");

                if (String.IsNullOrEmpty(CodiceAmministrazione))
                    throw new Exception("CODICE AMMINISTRAZIONE NON PRESENTE");

                if (String.IsNullOrEmpty(CodiceAoo))
                    throw new Exception("CODICE AOO NON PRESENTE");
                
                if (String.IsNullOrEmpty(UrlProto))
                    throw new Exception("URL SERVIZIO REST NON PRESENTE");

            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        private void EstraiParametri(VerticalizzazioneProtocolloPadoc paramVert)
        {
            try
            {
                _logs.Debug("Inizio recupero valori da verticalizzazione");

                UrlResponseService = paramVert.UrlResponseService;
                Dominio = paramVert.Dominio;
                CodiceAmministrazione = paramVert.CodiceAmministrazione;
                CodiceAoo = paramVert.CodiceAoo;
                UrlProto = paramVert.UrlProto;
                UrlLeggi = paramVert.UrlLeggiProto;
                Username = paramVert.Username;
                Password = paramVert.Password;
                Verb = String.IsNullOrEmpty(paramVert.MetodoProto) ? "register" : paramVert.MetodoProto;

                VerificaIntegritaParametri(paramVert);
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE PROTOCOLLO_PADOC", ex);
            }
        }
    }
}
