using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Legacy.Iride.PosteWeb;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Iride.PosteWeb
{
    public class IridePec : IPec
    {
        ProtocolloSerializer _serializer;
        ProtocolloLogs _logs;
        IEnumerable<string> _seriali;

        const string SEGNATURA_FILENAME_REQUEST = "SegnaturaPECInteropRequest.xml";


        public IridePec(IEnumerable<string> seriali, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            logs.DebugFormat("ENTRATO SU IRIDE");
            _serializer = serializer;
            _logs = logs;
            _seriali = seriali;
        }

        public string Invia(string url, string proxyAddress, string idDocumento, string oggetto, string corpo, string mittente, string utente, string ruolo, string codiceAmministrazione)
        {
            var allegatiAdapter = new AllegatiAdapter();
            var allegati = allegatiAdapter.Adatta(_seriali);

            var messaggio = new MessaggioInInterop
            {
                DocId = idDocumento,
                OggettoMail = oggetto,
                Ruolo = ruolo,
                TestoMail = corpo,
                Utente = utente,
                MittenteMail = mittente,
                DMSerialPrincipale = allegati.AllegatoPrincipale,
                DMSerialAllegati = allegati.AllegatiSecondari
            };

            var segnatura = _serializer.Serialize(SEGNATURA_FILENAME_REQUEST, messaggio);

            var service = new PECIrideService(url, proxyAddress, _logs, _serializer);
            return service.InviaPECInterop(segnatura, codiceAmministrazione, "");
        }
    }
}
