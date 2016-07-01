using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Iride2.Proxies;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Iride2.Proxies.Protocollo;

namespace Init.SIGePro.Protocollo.Iride2.Builders
{
    internal class IrideSegnaturaPosteWebInteropBuilder
    {

        private static class Constants
        {
            public const string SEGNATURA_FILENAME_REQUEST = "SegnaturaPECInteropRequest.xml";
        }

        private ProtocolloLogs _logs;
        private ProtocolloSerializer _serializer;
        private string _idDocumento = "";
        private string _utente = "";
        private string _ruolo = "";
        private string _oggettoMail = "";
        private string _testoMal = "";
        private string _mittenteMailPec = "";
        private string _allegatoPrincipale = "";
        private string _allegatiSecondari = "";

        internal readonly string SegnaturaXml;

        internal IrideSegnaturaPosteWebInteropBuilder(ProtocolloLogs logs, ProtocolloSerializer serializer, string utente, string ruolo, string mittenteMailPec, 
                                                        DatiProtocolloIn protoIn, DocumentoOut docOut)
        {
            _logs = logs;
            _serializer = serializer;
            _idDocumento = docOut.IdDocumento.ToString();
            _utente = utente;
            _ruolo = ruolo;
            _oggettoMail = protoIn.Oggetto;
            _testoMal = protoIn.Corpo;
            _mittenteMailPec = mittenteMailPec;
            
            SetAllegati(docOut);

            SegnaturaXml = CreaSegnatura();
        }

        private void SetAllegati(DocumentoOut docOut)
        {
            if (docOut.Allegati == null || docOut.Allegati.Length == 0)
                throw new Exception(String.Format("ALLEGATI NON PRESENTI, POSSIBILI PROBLEMI CON L'INVIO DELLA PEC INTEROP, ID DOCUMENTO: {0}, NUMERO PROTOCOLLO: {1}, DATA PROTOCOLLO: {2}", _idDocumento, docOut.NumeroProtocollo, docOut.DataProtocollo.ToString("dd/MM/yyyy")));
            else
            {
                _allegatoPrincipale = docOut.Allegati.ToList().First().Serial.ToString();

                if (docOut.Allegati.Length > 1)
                {
                    var listAllegatiSecondari = new List<String>();

                    docOut.Allegati.ToList().ForEach(x =>
                    {
                        listAllegatiSecondari.Add(x.Serial.ToString());
                        _logs.InfoFormat("ALLEGATO CODICE: {0}, NOME FILE: {1} VALORIZZATO PER L'INVIO DELLA PEC INTEROP", x.IDBase, x.NomeAllegato);
                    });

                    _allegatiSecondari = String.Join("|", listAllegatiSecondari.Skip(1));
                }
            }
        }

        private string CreaSegnatura()
        {

            var messaggio = new MessaggioInInterop();
            messaggio.DocId = _idDocumento;
            messaggio.OggettoMail = _oggettoMail;
            messaggio.Utente = _utente;
            messaggio.Ruolo = _ruolo;
            messaggio.TestoMail = _testoMal;
            messaggio.MittenteMail = _mittenteMailPec;
            messaggio.DMSerialPrincipale = String.IsNullOrEmpty(_allegatoPrincipale) ? "0" : _allegatoPrincipale;
            messaggio.DMSerialAllegati = String.IsNullOrEmpty(_allegatiSecondari) ? "0" : _allegatiSecondari;

            string retVal = _serializer.Serialize(Constants.SEGNATURA_FILENAME_REQUEST, messaggio);

            return retVal;
        }
    }
}
