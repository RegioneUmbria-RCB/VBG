using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Validation;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIride.PEC
{
    public class PecAdapter
    {
        const string SEGNATURA_FILENAME_REQUEST = "SegnaturaPECRequest.xml";
        const string SEGNATURA_VERIFICAINVIO_REQUEST = "SEgnaturaVerificaInvio.xml";

        ProtocolloSerializer _serializer;

        public PecAdapter(ProtocolloSerializer serializer)
        {
            this._serializer = serializer;
        }

        public string Adatta(string[] destinatari, IEnumerable<string> seriali, string idDocumento, string oggetto, string corpo, string mittente, string utente, string ruolo, bool invioInteroperabile)
        {
            var allegatiAdapter = new AllegatiAdapter();
            var allegati = allegatiAdapter.Adatta(seriali);

            var messaggio = new MessaggioIn
            {
                DocId = idDocumento,
                OggettoMail = oggetto,
                Ruolo = ruolo,
                TestoMail = corpo,
                Utente = utente,
                MittenteMail = mittente,
                DestinatariMail = destinatari,
                DMSerialPrincipale = allegati.AllegatoPrincipale,
                DMSerialAllegati = allegati.AllegatiSecondari
            };

            if (invioInteroperabile)
            {
                messaggio.InvioInteroperabile = "S";
            }

            var segnatura = _serializer.Serialize(SEGNATURA_FILENAME_REQUEST, messaggio, ProtocolloValidation.TipiValidazione.NO_NAMESPACE);
            return segnatura;
        }

        public string AdattaVerificaInvio(string idDocumento, string utente, string ruolo)
        {
            var messaggio = new VerificaInvioIn
            {
                DocId = idDocumento,
                Utente = utente,
                Ruolo = ruolo
            };

            var segnatura = _serializer.Serialize(SEGNATURA_VERIFICAINVIO_REQUEST, messaggio, ProtocolloValidation.TipiValidazione.NO_NAMESPACE);
            return segnatura;
        }
    }
}
