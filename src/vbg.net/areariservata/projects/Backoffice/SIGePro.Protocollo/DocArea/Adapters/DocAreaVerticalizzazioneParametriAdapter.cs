using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;

namespace Init.SIGePro.Protocollo.DocArea.Adapters
{
    public class DocAreaVerticalizzazioneParametriAdapter
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
        public bool InviaNomeFileAttachment { get; private set; }
        public string CodiceAmministrazione { get; private set; }
        public bool UsaDenominazionePg { get; private set; }
        public bool MultiMittenteDestinatario { get; private set; }
        public ProtocolloEnum.FornitoreDocAreaEnum TipoFornitore { get; private set; }
        public bool IsAbilitatoWarningVerificaFirma { get; private set; }
        public bool GestionePec { get; private set; }
        public string UrlPec { get; private set; }
        public bool GestisciFascicolazione { get; private set; }
        public string AnnoFascicoloDefault { get; private set; }
        public string NumeroFascicoloDefault { get; private set; }
        public char SeparatoreFascicolo { get; private set; }
        public string UtenteCreazionePec { get; private set; }

        public DocAreaVerticalizzazioneParametriAdapter(ProtocolloLogs logs, VerticalizzazioneProtocolloDocarea vert)
        {
            if (!vert.Attiva)
                throw new Exception("La verticalizzazione PROTOCOLLO_DOCAREA non è attiva");

            _logs = logs;
            EstraiParametri(vert);
        }

        private void VerificaIntegritaParametri(VerticalizzazioneProtocolloDocarea paramVert)
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

        private VerticalizzazioneProtocolloDocarea EstraiParametri(VerticalizzazioneProtocolloDocarea paramVert)
        {
            try
            {
                VerificaIntegritaParametri(paramVert);
                
                this.InviaSegnatura = paramVert.InviaSegnatura == "1";
                this.Url = paramVert.Url;
                this.Username = paramVert.Operatore;
                this.Password = paramVert.Password;
                this.ApplicativoProtocollo = paramVert.ApplicativoProtocollo;
                this.Codiceente = paramVert.Codiceente;
                this.CodiceAoo = paramVert.CodiceAoo;
                this.Uo = paramVert.Uo;
                this.InviaAllMovAvvio = paramVert.InviaAllMovAvvio == "1";
                this.TipoDocumentoPrincipale = paramVert.TipoDocumentoPrincipale;
                this.TipoDocumentoAllegato = paramVert.TipoDocumentoAllegato;
                this.InviaNomeFileAttachment = paramVert.InviaNomefileAttach == "1";
                this.CodiceAmministrazione = String.IsNullOrEmpty(paramVert.Codiceamministrazione) ? paramVert.Codiceente : paramVert.Codiceamministrazione;
                this.UsaDenominazionePg = paramVert.UsaDenominazionePg == "1";
                this.MultiMittenteDestinatario = paramVert.MultiMittDest == "1";
                this.TipoFornitore = ProtocolloEnum.FornitoreDocAreaEnum.NON_DEFINITO;
                this.IsAbilitatoWarningVerificaFirma = paramVert.AbilitaWarnFirma == "1";
                this.GestionePec = paramVert.GestionePec == "1";
                this.UrlPec = paramVert.UrlPec;
                this.GestisciFascicolazione = paramVert.GestisciFascicolazione == "1";
                this.AnnoFascicoloDefault = paramVert.AnnoFascicoloDefault;
                this.NumeroFascicoloDefault = paramVert.NumeroFascicoloDefault;
                this.SeparatoreFascicolo = String.IsNullOrEmpty(paramVert.SeparatoreFascicolo) ? '/' : Convert.ToChar(paramVert.SeparatoreFascicolo);
                this.UtenteCreazionePec = paramVert.UtenteCreazionePec;

                ProtocolloEnum.FornitoreDocAreaEnum tipoDef;
                bool isTipoFornitoreParseble = Enum.TryParse(paramVert.TipoFornitore, out tipoDef);

                if (isTipoFornitoreParseble)
                    this.TipoFornitore = tipoDef;

                return paramVert;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE PROTOCOLLO_DOCAREA", ex);
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
