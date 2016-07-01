using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Verticalizzazioni;
using PersonalLib2.Data;

namespace Init.SIGePro.Protocollo.EGrammata2.Verticalizzazioni
{
    public class VerticalizzazioniConfiguration
    {

        DataBase _db;
        ProtocolloLogs _logs;
        string _idComune;
        string _software;

        public string CodiceEnte { get; private set; }
        public string Username { get; private set; }
        public string Password { get; private set; }
        public string UserApp { get; private set; }
        public string PasswordApp { get; private set; }
        public string UrlProtocolla { get; private set; }
        public string UrlLeggiProto { get; private set; }
        public string Postazione { get; private set; }
        public string UrlProtoAllegati { get; private set; }
        public string UrlLeggiAnagrafiche { get; private set; }

        public VerticalizzazioniConfiguration(ProtocolloLogs logs, VerticalizzazioneProtocolloEgrammata2 vert)
        {
            if (!vert.Attiva)
                throw new Exception("La verticalizzazione PROTOCOLLO_EGRAMMATA2 non è attiva");

            _logs = logs;
            EstraiParametri(vert);

        }

        private void VerificaIntegritaParametri(VerticalizzazioneProtocolloEgrammata2 paramVert)
        {
            try
            {
                if (String.IsNullOrEmpty(paramVert.UrlProtocollo))
                    throw new Exception("PARAMETRO URL_PROTOCOLLO NON VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.UrlProtoallegati))
                    throw new Exception("PARAMETRO URL_PROTOCOLLO NON VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.UrlLeggiAnagrafiche))
                    throw new Exception("PARAMETRO URL_LEGGIANAGRAFICHE NON VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Codiceente))
                    throw new Exception("PARAMETRO CODICEENTE NON VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Username))
                    throw new Exception("PARAMETRO USERNAME NON VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Userapp))
                    throw new Exception("PARAMETRO USERAPP NON VALORIZZATO");
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        private void EstraiParametri(VerticalizzazioneProtocolloEgrammata2 vert)
        {
            try
            {
                _logs.Debug("Inizio recupero valori da verticalizzazione");

                VerificaIntegritaParametri(vert);

                this.UrlProtocolla = vert.UrlProtocollo;
                this.CodiceEnte = vert.Codiceente;
                this.Username = vert.Username;
                this.Password = vert.Password;
                this.UserApp = vert.Userapp;
                this.PasswordApp = vert.PasswordUserapp;
                this.UrlLeggiProto = vert.UrlLeggiproto;
                this.Postazione = vert.Postazione;
                this.UrlProtoAllegati = vert.UrlProtoallegati;
                this.UrlLeggiAnagrafiche = vert.UrlLeggiAnagrafiche;

                _logs.Debug("Fine recupero valori da verticalizzazioni");
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE PROTOCOLLO_EGRAMMATA2, {0}", ex.Message), ex);
            }
        }

    }
}
