using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Validation;

namespace Init.SIGePro.Protocollo.JIride.PEC
{
    public class PecAdapter
    {
        const string SEGNATURA_FILENAME_REQUEST = "SegnaturaPECRequest.xml";

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
    }
}
