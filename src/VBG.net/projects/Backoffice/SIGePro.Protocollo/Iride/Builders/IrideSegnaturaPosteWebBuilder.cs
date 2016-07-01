using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Data;

namespace Init.SIGePro.Protocollo.Iride.Builders
{
    internal class IrideSegnaturaPosteWebBuilder
    {

        private static class Constants
        {
            public const string SEGNATURA_FILENAME_REQUEST = "SegnaturaPECRequest.xml";
        }

        private ProtocolloLogs _logs;
        private ProtocolloSerializer _serializer;
        private List<string> _destinatari;
        private List<string> _destinatariCC;
        private string _idDocumento;
        private string _utente;
        private string _ruolo;
        private string _oggettoMail;
        private string _testoMal;
        private string _mittenteMailPec;

        internal readonly string SegnaturaXml;

        internal IrideSegnaturaPosteWebBuilder(ProtocolloLogs logs, ProtocolloSerializer serializer, List<string> destinatari, 
            string idDocumento, string oggettoMail, string utente, string ruolo, string testoMail, string mittenteMailPec)
        {
            _logs = logs;
            _serializer = serializer;
            _idDocumento = idDocumento;
            _utente = utente;
            _ruolo = ruolo;
            _destinatari = destinatari;
            _oggettoMail = oggettoMail;
            _testoMal = testoMail;
            _mittenteMailPec = mittenteMailPec;

            SegnaturaXml = CreaSegnatura();
        }

        private string CreaSegnatura()
        {

            var messaggio = new MessaggioIn();
            messaggio.DocId = _idDocumento;
            messaggio.OggettoMail = _oggettoMail;
            messaggio.Utente = _utente;
            messaggio.Ruolo = _ruolo;
            messaggio.TestoMail = _testoMal;
            messaggio.MittenteMail = _mittenteMailPec;

            messaggio.DestinatariMail = _destinatari;

            string retVal = _serializer.Serialize(Constants.SEGNATURA_FILENAME_REQUEST, messaggio);

            return retVal;
        }
    }
}
