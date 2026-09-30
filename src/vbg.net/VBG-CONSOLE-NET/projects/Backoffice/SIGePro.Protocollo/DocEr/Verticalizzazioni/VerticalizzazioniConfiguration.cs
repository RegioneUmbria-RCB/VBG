using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.DocEr.Verticalizzazioni
{
    public class VerticalizzazioniConfiguration
    {
        ProtocolloLogs _logs;

        public string TipoDocumentoPrincipale { get{ return "PRINCIPALE"; } }
        public string TipoDocumentoAllegato { get { return "ALLEGATO"; } }
        public string Applicazione { get; private set; }
        public string CodiceAoo { get; private set; }
        public string CodiceAmministrazione { get; private set; }
        public string CodiceEnte { get; private set; }
        public string Password { get; private set; }
        public string Username { get; private set; }
        public string UrlProtocollazione { get; private set; }
        public string UrlLogin { get; private set; }
        public string UrlGestioneDocumentale { get; private set; }
        public string UrlFascicolazione { get; private set; }
        public string Uo { get; private set; }
        public bool InviaSegnaturaFittizia { get; private set; }
        public string UrlRegParticolare { get; private set; }
        public string UrlPec { get; private set; }
        public Enumeretors.TipoInvioPec TipoInvioPec { get; private set; }
        public string TypeIdAnagraficaCustom { get; private set; }
        public string AnagCustomCodice { get; private set; }
        public string AnagCustomDescrizione { get; private set; }
        public bool IsLdapAuthentication { get; private set; }
        public int? PadNumeroProtocolloLength { get; private set; }
        public char PadNumeroProtocolloChar { get; private set; }
        public bool DisabilitaMetadati { get; private set; }
        public bool UsaDocumentiPec { get; private set; }
        public bool IgnoraWarnAnag { get; private set; }
        public int TentativiRetry { get; private set; }
        public bool CompatibilitaFasc { get; private set; }

        public VerticalizzazioniConfiguration(ProtocolloLogs logs, VerticalizzazioneProtocolloDocer vert)
        {
            if (!vert.Attiva)
                throw new Exception("La verticalizzazione PROTOCOLLO_DOCER non è attiva");

            _logs = logs;
            EstraiParametri(vert);
        }

        private void VerificaIntegritaParametri(VerticalizzazioneProtocolloDocer paramVert)
        {
            try
            {
                if (String.IsNullOrEmpty(paramVert.UrlLogin))
                    throw new Exception("PARAMETRO URL LOGIN NON VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.UrlGestioneDocs))
                    throw new Exception("PARAMETRO URL GESTIONE DOCS NON VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.UrlProtocollazione))
                    throw new Exception("PARAMETRO URL PROTOCOLLAZIONE NON VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Username))
                    throw new Exception("PARAMETRO USERNAME NON VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Password))
                    throw new Exception("PARAMETRO PASSWORD NON VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.CodiceEnte))
                    throw new Exception("PARAMETRO CODICE ENTE NON VALORIZZATO");

            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        private void EstraiParametri(VerticalizzazioneProtocolloDocer vert)
        {
            try
            {
                _logs.Debug("Inizio recupero valori da verticalizzazione");

                VerificaIntegritaParametri(vert);

                Applicazione = vert.Applicazione;
                CodiceAoo = vert.CodiceAoo;
                CodiceAmministrazione = vert.Codiceamministrazione;
                CodiceEnte = vert.CodiceEnte;
                Password = vert.Password;
                Username = vert.Username;
                UrlProtocollazione = vert.UrlProtocollazione;
                UrlLogin = vert.UrlLogin;
                UrlGestioneDocumentale = vert.UrlGestioneDocs;
                UrlFascicolazione = vert.UrlFascicolazione;
                Uo = vert.Uo;
                InviaSegnaturaFittizia = vert.InviaSegnatura == "1";
                UrlRegParticolare = vert.UrlRegParticolare;
                UrlPec = vert.UrlPec;
                AnagCustomCodice = vert.AnagCustomCodice;
                AnagCustomDescrizione = vert.AnagCustomDescrizione;

                TypeIdAnagraficaCustom = vert.TypeIdAnagCustom;

                TipoInvioPec = Enumeretors.TipoInvioPec.NESSUN_INVIO;

                if (vert.TipoInvioPec == "1")
                    TipoInvioPec = Enumeretors.TipoInvioPec.INVIO_AUTOMATICO;

                if (vert.TipoInvioPec == "2")
                    TipoInvioPec = Enumeretors.TipoInvioPec.INVIO_MANUALE;

                IsLdapAuthentication = vert.UsaLdapAuth == "1";

                PadNumeroProtocolloLength = (int?) null;
                PadNumeroProtocolloChar = '0';

                if (!String.IsNullOrEmpty(vert.PadNumProtoLength))
                    PadNumeroProtocolloLength = Convert.ToInt32(vert.PadNumProtoLength);

                if (!String.IsNullOrEmpty(vert.PadNumProtoChar))
                    PadNumeroProtocolloChar = vert.PadNumProtoChar.ToCharArray()[0];

                DisabilitaMetadati = vert.DisabilitaMetadati == "1";
                UsaDocumentiPec = vert.UsaDocumentiPec == "1";
                IgnoraWarnAnag = vert.IgnoraWarnAnag == "1";

                CompatibilitaFasc = vert.CompatibilitaFasc == "1";

                TentativiRetry = 0;

                if (!String.IsNullOrEmpty(vert.TentativiRetry))
                {
                    try
                    {
                        TentativiRetry = Convert.ToInt32(vert.TentativiRetry);
                    }
                    catch (Exception)
                    {

                    }
                }
                _logs.Debug("Fine recupero valori da verticalizzazioni");
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE PROTOCOLLO_DOCER", ex);
            }
        }
    }
}

