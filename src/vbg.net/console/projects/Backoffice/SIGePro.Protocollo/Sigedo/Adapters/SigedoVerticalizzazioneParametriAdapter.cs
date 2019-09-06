using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.Sigedo.Adapters
{
    public class SigedoVerticalizzazioneParametriAdapter
    {
        ProtocolloLogs _logs;

        public string ApplicativoProtocollo { get; private set; }
        public string CodiceEnte { get; private set; }
        public string CodiceAoo { get; private set; }
        public bool InviaSegnatura { get; private set; }
        public string UsernameWs { get; private set; }
        public string PasswordWs { get; private set; }
        public string TipoDocumentoAllegato { get; private set; }
        public string Uo { get; private set; }
        public string UrlProto { get; private set; }
        public string UrlQueryService { get; private set; }
        public string AreaLeggiProto { get; private set; }
        public string ModelloLeggiProto { get; private set; }
        public string Stato { get; private set; }
        public string AreaAllegati { get; private set; }
        public string ModelloAllegati { get; private set; }
        public string AreaSoggetti { get; private set; }
        public string ModelloSoggetti { get; private set; }
        public int OperatoreRicerche { get; private set; }
        public string UsernameQueryService { get; private set; }
        public string PasswordQueryService { get; private set; }
        public string TipoDocumentoPrincipale { get; private set; }
        public string UrlStrutturaSoa { get; private set; }
        public string CanaleStrutturaSoa { get; private set; }
        public bool InviaCodiceFiscale { get; private set; }
        public string UrlWsAllegati { get; private set; }
        public string UsernameWsAllegati { get; private set; }
        public string PasswordWsAllegati { get; private set; }
        public string UrlCgiUo { get; private set; }
        public string DescrizioneEnte { get; private set; }
        public string CodiceAmministrazione { get; private set; }
        public int OperatoreRicercheClassifiche { get; private set; }
        public bool UsaWsClassifiche { get; private set; }
        public string TipoRegistro { get; private set; }
        public bool UsaSmistamentoAction { get; private set; }


        public SigedoVerticalizzazioneParametriAdapter(ProtocolloLogs logs, VerticalizzazioneProtocolloSigedo paramVert)
        {
            if (!paramVert.Attiva)
                throw new Exception("LA VERTICALIZZAZIONE PROTOCOLLO_SIGEDO NON È ATTIVA");

            _logs = logs;
            EstraiParametri(paramVert);
        }

        private void VerificaIntegritaParametri(VerticalizzazioneProtocolloSigedo paramVert)
        {
            try
            {
                if (String.IsNullOrEmpty(paramVert.OperatoreRicerche))
                    throw new Exception("IL PARAMETRO OPERATORE_RICERCHE NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.UsernameWs))
                    throw new Exception("IL PARAMETRO USERNAME_WS NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.PasswordWs))
                    throw new Exception("IL PARAMETRO PASSWORD_WS NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Codiceente))
                    throw new Exception("IL PARAMETRO CODICE_ENTE NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.CodiceAoo))
                    throw new Exception("IL PARAMETRO CODICE_AOO NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Uo))
                    throw new Exception("IL PARAMETRO UO RIGUARDANTE L'UFFICIO DI SMISTAMENTO NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Uo))
                    throw new Exception("IL PARAMETRO URL_PROTO RIGUARDANTE L'ENDPOINT DEL WEB SERVICE NON E' STATO VALORIZZATO");

            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        private VerticalizzazioneProtocolloSigedo EstraiParametri(VerticalizzazioneProtocolloSigedo paramVert)
        {
            try
            {
                _logs.Debug("Inizio recupero valori da verticalizzazione");

                VerificaIntegritaParametri(paramVert);

                ApplicativoProtocollo = paramVert.ApplicativoProtocollo;
                CodiceEnte = paramVert.Codiceente;
                CodiceAoo = paramVert.CodiceAoo;
                InviaSegnatura = paramVert.InviaSegnatura == "1";
                UsernameWs = paramVert.UsernameWs;
                PasswordWs = paramVert.PasswordWs;
                TipoDocumentoPrincipale = paramVert.TipoDocumentoPrincipale;
                TipoDocumentoAllegato = paramVert.TipoDocumentoAllegato;
                Uo = paramVert.Uo;
                UrlProto = paramVert.UrlProto;
                UrlQueryService = paramVert.UrlQueryservice;
                AreaLeggiProto = paramVert.AreaLeggiproto;
                ModelloLeggiProto = paramVert.ModelloLeggiproto;
                Stato = paramVert.Stato;
                AreaAllegati = paramVert.AreaAllegati;
                ModelloAllegati = paramVert.ModelloAllegati;
                AreaSoggetti = paramVert.AreaSoggetti;
                ModelloSoggetti = paramVert.ModelloSoggetti;
                OperatoreRicerche = TryIntParse(paramVert.OperatoreRicerche, "OPERATORE_RICERCHE");
                UsernameQueryService = paramVert.UsernameQueryservice;
                PasswordQueryService = paramVert.PasswordQueryservice;
                TipoDocumentoPrincipale = paramVert.TipoDocumentoPrincipale;
                UrlStrutturaSoa = paramVert.UrlStrutturasoa;
                CanaleStrutturaSoa = paramVert.CanaleStrutturasoa;
                InviaCodiceFiscale = paramVert.InviaCf == "1";
                UrlWsAllegati = paramVert.UrlWsAllegati;
                UsernameWsAllegati = paramVert.UsernameWsAllegati;
                PasswordWsAllegati = paramVert.PasswordWsAllegati;
                UrlCgiUo = paramVert.UrlCgiUo;
                DescrizioneEnte = paramVert.DescrizioneEnte;
                CodiceAmministrazione = String.IsNullOrEmpty(paramVert.Codiceamministrazione) ? paramVert.Codiceente : paramVert.Codiceamministrazione;
                OperatoreRicercheClassifiche = TryIntParse(paramVert.OperatoreRicercheClassifiche, "OPERATORE_RICERCHE_CLASSIFICHE");
                UsaWsClassifiche = paramVert.UsaWsClassifiche == "1";
                TipoRegistro = paramVert.TipoRegistro;
                UsaSmistamentoAction = paramVert.UsaSmistamentoAction == "1";

                _logs.Debug("Fine recupero valori da verticalizzazioni");

                return paramVert;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE PROTOCOLLO_SIGEDO", ex);
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
