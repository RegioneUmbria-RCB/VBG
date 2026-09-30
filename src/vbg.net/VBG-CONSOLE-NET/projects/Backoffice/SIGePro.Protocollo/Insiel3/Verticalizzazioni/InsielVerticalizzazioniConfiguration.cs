using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.Insiel3.Protocollazione.MittentiDestinatari;

namespace Init.SIGePro.Protocollo.Insiel3.Verticalizzazioni
{
    public class InsielVerticalizzazioniConfiguration
    {
        ProtocolloLogs _logs;

        public string CodiceRegistro { get; private set; }
        public string CodiceUfficioOperante { get; private set; }
        public string CodiceUtente{ get; private set; }
        public bool DisabilitaAnnullaProtocollo { get; private set; }
        public bool EscludiClassifica { get; private set; }
        public string Password { get; private set; }
        public bool TipiDocumentoWs { get; private set; }
        public string Url { get; private set; }
        public string UrlUploadFile { get; private set; }
        public bool IsMonfalcone { get; private set; }

        public TipoGestioneAnagraficaEnum.TipoGestione TipoGestionePec { get; private set; }
                
        public InsielVerticalizzazioniConfiguration(ProtocolloLogs logs, VerticalizzazioneProtocolloInsiel vert)
        {
            if (!vert.Attiva)
                throw new Exception("La verticalizzazione PROTOCOLLO_INSIEL non è attiva");

            _logs = logs;
            EstraiParametri(vert);
        }

        private void VerificaIntegritaParametri(VerticalizzazioneProtocolloInsiel paramVert)
        {
            if (String.IsNullOrEmpty(paramVert.Codiceutente))
                throw new Exception("IL PARAMETRO CODICEUTENTE NON E' STATO VALORIZZATO");

            if (String.IsNullOrEmpty(paramVert.Password))
                throw new Exception("IL PARAMETRO PASSWORD NON E' STATO VALORIZZATO");

            if (String.IsNullOrEmpty(paramVert.Url))
                throw new Exception("IL PARAMETRO URL RIGUARDANTE L'ENDPOINT DEL WEB SERVICE NON E' STATO VALORIZZATO");

            if (String.IsNullOrEmpty(paramVert.UrlUploadfile))
                throw new Exception("IL PARAMETRO URL UPLOAD FILE RIGUARDANTE L'ENDPOINT DEL WEB SERVICE DI UPLOAD FILE NON E' STATO VALORIZZATO");
        }

        public void EstraiParametri(VerticalizzazioneProtocolloInsiel vert)
        {
            try
            {
                _logs.Debug("Inizio recupero valori da verticalizzazione");

                VerificaIntegritaParametri(vert);

                CodiceRegistro = vert.Codiceregistro;
                CodiceUfficioOperante = vert.CodiceUfficioOperante;
                CodiceUtente = vert.Codiceutente;
                DisabilitaAnnullaProtocollo = vert.DisabilitaAnnullaProtocollo == "1";
                EscludiClassifica = vert.EscludiClassifica == "1";
                Password = vert.Password;
                TipiDocumentoWs = vert.TipiDocumentoWs == "1";
                Url = vert.Url;
                UrlUploadFile = vert.UrlUploadfile;
                IsMonfalcone = vert.AttivaMonf == "1";

                TipoGestionePec = TipoGestioneAnagraficaEnum.TipoGestione.NO_PEC;
                if (!String.IsNullOrEmpty(vert.TipoGestionePec))
                    TipoGestionePec = (TipoGestioneAnagraficaEnum.TipoGestione)Enum.Parse(typeof(TipoGestioneAnagraficaEnum.TipoGestione), vert.TipoGestionePec);
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE PROTOCOLLO_INSIEL", ex);
            }
        }
    }
}
