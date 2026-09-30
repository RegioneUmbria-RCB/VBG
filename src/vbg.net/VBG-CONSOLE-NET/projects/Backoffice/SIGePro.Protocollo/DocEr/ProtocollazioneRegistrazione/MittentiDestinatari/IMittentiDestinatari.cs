using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione.MittentiDestinatari
{
    public interface IMittentiDestinatari
    {
        MittDestType[] GetMittenti();
        MittDestType[] GetDestinatari();
        FlussoType Flusso { get; }
        SmistamentoType GetSmistamento();
    }
}
