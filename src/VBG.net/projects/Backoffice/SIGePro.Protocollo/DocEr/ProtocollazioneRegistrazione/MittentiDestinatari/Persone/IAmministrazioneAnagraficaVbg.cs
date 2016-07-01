using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione.MittentiDestinatari.Persone
{
    public interface IAmministrazioneAnagraficaVbg
    {
        string CodiceFiscalePartitaIva { get; }
        string Nominativo { get; }
        string Tipo { get; }
        string CodiceVbg { get; }
    }
}
