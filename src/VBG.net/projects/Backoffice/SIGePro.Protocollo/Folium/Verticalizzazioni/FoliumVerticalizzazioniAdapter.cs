using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using PersonalLib2.Data;
using Init.SIGePro.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.Folium.Verticalizzazioni
{
    public class FoliumVerticalizzazioniAdapter
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

        public FoliumVerticalizzazioniAdapter(ProtocolloLogs logs, VerticalizzazioneProtocolloFolium paramVert)
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
                _logs.Debug("Inizio recupero valori da verticalizzazione");


                VerificaIntegritaParametri(paramVert);
                Username = paramVert.Username;
                Password = paramVert.Password;
                CodiceEnte = paramVert.CodiceEnte;
                Applicazione = paramVert.Applicazione;
                Aoo = paramVert.Aoo;
                Registro = paramVert.Registro;
                Url = paramVert.Url;
                Binding = paramVert.Binding;
                CodiceCC = paramVert.CodiceCc;
                UsaWsClassifiche = paramVert.UsaWsclassifiche == "1";
                
                return paramVert;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE PROTOCOLLO_DOCAREA", ex);
            }
        }

    }
}
