using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using PersonalLib2.Data;
using Init.SIGePro.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.Folium
{
    public class VerticalizzazioniWrapper
    {
        DataBase _db;
        ProtocolloLogs _logs;
        string _idComune;
        string _software;

        public string Password { get; private set; }
        public string Url { get; private set; }
        public string Username { get; private set; }
        public string CodiceEnte { get; private set; }
        public string Applicazione { get; private set; }
        public string Aoo { get; private set; }
        public string Registro { get; private set; }
        public string Binding { get; private set; }
        public string CodiceCC { get; private set; }
        public bool UsaWsClassifiche { get; private set; }
        public bool IsContenuto { get; private set; }
        public bool DisabilitaMailArrivo { get; private set; }
        public string UrlWsMail { get; private set; }
        public bool UsaWsMail { get; private set; }

        public VerticalizzazioniWrapper(ProtocolloLogs logs, VerticalizzazioneProtocolloFolium paramVert)
        {
            if (!paramVert.Attiva)
                throw new Exception("LA VERTICALIZZAZIONE PROTOCOLLO_FOLIUM NON E' ATTIVA");

            _logs = logs;
            EstraiParametri(paramVert);
        }

        private void VerificaIntegritaParametri(VerticalizzazioneProtocolloFolium paramVert)
        {
            try
            {
                if (String.IsNullOrEmpty(paramVert.Username))
                    throw new Exception("IL PARAMETRO USERNAME NON E' STATO VALORIZZATO, QUESTO PARAMETRO E' NECESSARIO PER ESEGUIRE L'AUTENTICAZIONE");

                if (String.IsNullOrEmpty(paramVert.Password))
                    throw new Exception("IL PARAMETRO PASSWORD NON E' STATO VALORIZZATO, QUESTO PARAMETRO E' NECESSARIO PER ESEGUIRE L'AUTENTICAZIONE");

                if (String.IsNullOrEmpty(paramVert.CodiceEnte))
                    throw new Exception("IL PARAMETRO CODICE_ENTE NON E' STATO VALORIZZATO, QUESTO PARAMETRO E' NECESSARIO PER ESEGUIRE L'AUTENTICAZIONE");

                if (String.IsNullOrEmpty(paramVert.Applicazione))
                    throw new Exception("IL PARAMETRO APPLICAZIONE NON E' STATO VALORIZZATO, QUESTO PARAMETRO E' NECESSARIO PER ESEGUIRE L'AUTENTICAZIONE");

                if (String.IsNullOrEmpty(paramVert.Aoo))
                    throw new Exception("IL PARAMETRO AOO NON E' STATO VALORIZZATO, QUESTO PARAMETRO E' NECESSARIO PER ESEGUIRE L'AUTENTICAZIONE");

                if (String.IsNullOrEmpty(paramVert.Url))
                    throw new Exception("IL PARAMETRO URL RIGUARDANTE L'ENDPOINT DEL WEB SERVICE NON E' STATO VALORIZZATO");

            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        private VerticalizzazioneProtocolloFolium EstraiParametri(VerticalizzazioneProtocolloFolium paramVert)
        {
            try
            {
                _logs.Debug("Inizio recupero valori da verticalizzazione PROTOCOLLO_FOLIUM");

                this.VerificaIntegritaParametri(paramVert);
                this.Username = paramVert.Username;
                this.Password = paramVert.Password;
                this.CodiceEnte = paramVert.CodiceEnte;
                this.Applicazione = paramVert.Applicazione;
                this.Aoo = paramVert.Aoo;
                this.Registro = paramVert.Registro;
                this.Url = paramVert.Url;
                this.Binding = paramVert.Binding;
                this.CodiceCC = paramVert.CodiceCc;
                this.UsaWsClassifiche = paramVert.UsaWsclassifiche == "1";
                this.IsContenuto = paramVert.Contenuto == "1";
                this.DisabilitaMailArrivo = paramVert.DisabilitaMailArrivo == "1";
                this.UrlWsMail = paramVert.UrlWsMail;
                this.UsaWsMail = !String.IsNullOrEmpty(paramVert.UrlWsMail);

                return paramVert;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE PROTOCOLLO_FOLIUM, {0}", ex.Message), ex);
            }
        }

    }
}
