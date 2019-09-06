using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Iride2.PosteWeb;
using Init.SIGePro.Protocollo.Iride2.Proxies;

namespace Init.SIGePro.Protocollo.Iride2.PosteWeb
{
    internal class JIridePec : IPec
    {
        ProtocolloSerializer _serializer;
        ProtocolloLogs _logs;
        string _codiceAoo;
        string[] _destinatari;
        IEnumerable<string> _seriali;
        bool _invioInteroperabile;

        const string SEGNATURA_FILENAME_REQUEST = "SegnaturaPECRequest.xml";

        internal JIridePec(string[] destinatari, IEnumerable<string> seriali, string codiceAoo, ProtocolloLogs logs, ProtocolloSerializer serializer, bool invioInteroperabile)
        {
            logs.InfoFormat("ENTRATO SU J_IRIDE");
            _logs = logs;
            _serializer = serializer;
            _codiceAoo = codiceAoo;
            _destinatari = destinatari;
            _seriali = seriali;
            _invioInteroperabile = invioInteroperabile;
        }

        public string Invia(string url, string proxyAddress, string idDocumento, string oggetto, string corpo, string mittente, string utente, string ruolo, string codiceAmministrazione)
        {
            var allegatiAdapter = new AllegatiAdapter();
            var allegati = allegatiAdapter.Adatta(_seriali);

            var messaggio = new MessaggioIn
            {
                DocId = idDocumento,
                OggettoMail = oggetto,
                Ruolo = ruolo,
                TestoMail = corpo,
                Utente = utente,
                MittenteMail = mittente,
                DestinatariMail = _destinatari,
                DMSerialPrincipale = allegati.AllegatoPrincipale,
                DMSerialAllegati = allegati.AllegatiSecondari
            };

            if (this._invioInteroperabile)
            {
                messaggio.InvioInteroperabile = "S";
            }

            var segnatura = _serializer.Serialize(SEGNATURA_FILENAME_REQUEST, messaggio);

            var service = new PECIrideService(url, proxyAddress, _logs, _serializer);
            return service.InviaPEC(segnatura, codiceAmministrazione, _codiceAoo);
        }
    }
}
