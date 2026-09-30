/*using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.EGrammata2.Protocollazione.Segnatura.Request;

namespace Init.SIGePro.Protocollo.EGrammata2.Protocollazione
{
    public static class ProtocollazioneAllegatiExtensions
    {
        public static AllegaArrIn InseriscieConfiguraAllegati(this ProtocolloAllegati allegato, int idx, ProtocollazioneService wrapper)
        {
            wrapper.InserisciAllegato(allegato);

            return new AllegaArrIn
            {
                NomeFile = allegato.NOMEFILE,
                Tipo = idx == 0 ? "0" : "1"
            };
        }
    }
}
*/