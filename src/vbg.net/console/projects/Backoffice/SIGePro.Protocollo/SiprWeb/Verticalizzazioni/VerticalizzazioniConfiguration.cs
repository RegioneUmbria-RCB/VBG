using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.SiprWeb.Verticalizzazioni
{
    public class VerticalizzazioniConfiguration
    {
        ProtocolloLogs _logs;

        public string UrlListaClassifica { get; private set; }
        public string UrlListaTipiDocumento { get; private set; }
        public string UrlProtocolla { get; private set; }
        public string UrlLeggi { get; private set; }
        public bool UsaWsClassifiche { get; private set; }
        public bool UsaWsTipiDocumento { get; private set; }
        public string UrlAllegati { get; private set; }
        public string FtpPathAllegati { get; private set; }
        public string CodiceCC { get; private set; }
        public string NomeDirCondivisa { get; private set; }
        public string Dominio { get; private set; }
        public string Username { get; private set; }
        public string Password { get; private set; }

        public VerticalizzazioniConfiguration(ProtocolloLogs logs, VerticalizzazioneProtocolloSiprweb vert)
        {
            if (!vert.Attiva)
                throw new Exception("La verticalizzazione PROTOCOLLO_SIPRWEB non è attiva");

            _logs = logs;
            EstraiParametri(vert);
        }

        private void VerificaIntegritaParametri(VerticalizzazioneProtocolloSiprweb paramVert)
        {
            try
            {
                if (String.IsNullOrEmpty(paramVert.Urlprotocolla))
                    throw new Exception("IL PARAMETRO URL_PROTOCOLLA NON E' STATO VALORIZZATO");

            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        private void EstraiParametri(VerticalizzazioneProtocolloSiprweb vert)
        {
            try
            {
                _logs.Debug("Inizio recupero valori da verticalizzazione");

                VerificaIntegritaParametri(vert);

                UrlListaClassifica = vert.Urlclassifiche;
                UrlListaTipiDocumento = vert.Urltipidocumento;
                UrlProtocolla = vert.Urlprotocolla;
                UrlLeggi = vert.Urlleggi;
                UsaWsClassifiche = vert.UsaWsClassifiche == "1";
                UsaWsTipiDocumento = vert.UsaWsTipidoc == "1";
                UrlAllegati = vert.Urlallegati;
                FtpPathAllegati = vert.FtpPathAllegati;
                CodiceCC = vert.CodiceCc;
                NomeDirCondivisa = vert.NomeDirCondivisa;
                Dominio = vert.Dominio;
                Username = vert.Username;
                Password = vert.Password;

                _logs.Debug("Fine recupero valori da verticalizzazioni");
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE PROTOCOLLO_SIPREWB", ex);
            }
        }
    }
}
