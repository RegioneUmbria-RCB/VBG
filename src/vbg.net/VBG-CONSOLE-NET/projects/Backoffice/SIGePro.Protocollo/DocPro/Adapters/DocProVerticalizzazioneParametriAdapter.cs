using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.DocPro.Adapters
{
    public class DocProVerticalizzazioneParametriAdapter
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
        public string ModalitaTrasmissioneArrivo { get; private set; }
        public string ModalitaTrasmissione { get; private set; }
        public string CodiceAmministrazione { get; private set; }


        public DocProVerticalizzazioneParametriAdapter(ProtocolloLogs logs, VerticalizzazioneProtocolloDocpro vert)
        {
            if (!vert.Attiva)
                throw new Exception("LA VERTICALIZZAZIONE PROTOCOLLO_DOCPRO NON È ATTIVA");

            _logs = logs;
            EstraiParametri(vert);
        }

        private void VerificaIntegritaParametri(VerticalizzazioneProtocolloDocpro paramVert)
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

                if (String.IsNullOrEmpty(paramVert.Uo))
                    throw new Exception("IL PARAMETRO UO RIGUARDANTE L'UFFICIO DI SMISTAMENTO NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Url))
                    throw new Exception("IL PARAMETRO URL RIGUARDANTE L'ENDPOINT DEL WEB SERVICE NON E' STATO VALORIZZATO");

            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        private VerticalizzazioneProtocolloDocpro EstraiParametri(VerticalizzazioneProtocolloDocpro paramVert)
        {
            try
            {
                _logs.Debug("Inizio recupero valori da verticalizzazione");
                
                VerificaIntegritaParametri(paramVert);
                
                InviaSegnatura = paramVert.InviaSegnatura == "1";
                Url = paramVert.Url;
                Username = paramVert.Operatore;
                Password = paramVert.Password;
                ApplicativoProtocollo = paramVert.ApplicativoProtocollo;
                Codiceente = paramVert.Codiceente;
                CodiceAoo = paramVert.CodiceAoo;
                Uo = paramVert.Uo;
                InviaAllMovAvvio = paramVert.InviaAllMovAvvio == "1";
                TipoDocumentoPrincipale = paramVert.TipoDocumentoPrincipale;
                TipoDocumentoAllegato = paramVert.TipoDocumentoAllegato;
                ModalitaTrasmissioneArrivo = paramVert.ModalitaTrasmissioneArrivo;
                ModalitaTrasmissione = paramVert.ModalitaTrasmissione;
                CodiceAmministrazione = String.IsNullOrEmpty(paramVert.Codiceamministrazione) ? paramVert.Codiceente : paramVert.Codiceamministrazione;

                _logs.Debug("Fine recupero valori da verticalizzazioni");

                return paramVert;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE PROTOCOLLO_DOCPRO", ex);
            }
        }

        private int TryIntParse(string valore, string nomeParametro)
        {
            try
            {
                int operatoreRicerche;
                bool isOperatoreParsed = Int32.TryParse(valore, out operatoreRicerche);

                if (!isOperatoreParsed)
                    throw new Exception(String.Format("INSERIRE UN VALORE NUMERICO PER IL PARAMETRO", nomeParametro));

                return operatoreRicerche;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE DI PARSING DEL PARAMETRO {0}", nomeParametro), ex);
            }
        }
    }
}
