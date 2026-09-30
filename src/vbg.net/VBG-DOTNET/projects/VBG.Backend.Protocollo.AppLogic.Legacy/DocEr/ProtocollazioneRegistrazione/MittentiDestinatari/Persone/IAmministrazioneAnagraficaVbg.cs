using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.ProtocollazioneRegistrazione.MittentiDestinatari.Persone
{
    public interface IAmministrazioneAnagraficaVbg
    {
        string CodiceFiscalePartitaIva { get; }
        string Nominativo { get; }
        string Tipo { get; }
        string CodiceVbg { get; }
    }
}
