using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione.Protocollazione
{
    public class ProtocollazioneEsitoResponse
    {
        public esito EsitoProtocollazione { get; private set; }
        public bool IsErrore { get; private set; }
        public string Errore { get; private set; }

        public ProtocollazioneEsitoResponse(esito esitoProtocollazione, string errore)
        {
            EsitoProtocollazione = esitoProtocollazione;
            IsErrore = !String.IsNullOrEmpty(errore);
            Errore = errore;
        }
    }
}
