using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.Halley.Adapters
{
    public class HalleyVerticalizzazioneParametriAdapter
    {
        DataBase _db;
        ProtocolloLogs _logs;
        string _idComune;
        string _software;

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
        public string UrlWsDizionario { get; private set; }
        public bool UsaWsClassifiche { get; private set; }
        public string CodiceAmministrazione { get; private set; }
        public bool GeneraSuapXml { get; private set; }
        public string RadiceAlberoEdilizia { get; private set; }
        public bool InviaCf { get; private set; }
        public bool IsMultiMittDest { get; private set; }

        public HalleyVerticalizzazioneParametriAdapter(ProtocolloLogs logs, VerticalizzazioneProtocolloHalley paramVert)
        {
            if (!paramVert.Attiva)
                throw new Exception("LA VERTICALIZZAZIONE PROTOCOLLO_HALLEY NON È ATTIVA");

            _logs = logs;
            EstraiParametri(paramVert);
        }

        private void VerificaIntegritaParametri(VerticalizzazioneProtocolloHalley paramVert)
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

        private VerticalizzazioneProtocolloHalley EstraiParametri(VerticalizzazioneProtocolloHalley paramVert)
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
                UrlWsDizionario = paramVert.UrlWsDizionario;
                UsaWsClassifiche = paramVert.UsaWsClassifiche == "1";
                CodiceAmministrazione = String.IsNullOrEmpty(paramVert.Codiceamministrazione) ? paramVert.Codiceente : paramVert.Codiceamministrazione;
                GeneraSuapXml = paramVert.GeneraSuapXml == "1";
                RadiceAlberoEdilizia = paramVert.RadiceAlberoEdilizia;
                InviaCf = paramVert.InviaCf == "1";
                IsMultiMittDest = paramVert.MultiMittDest == "1";

                return paramVert;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE PROTOCOLLO_HALLEY", ex);
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
