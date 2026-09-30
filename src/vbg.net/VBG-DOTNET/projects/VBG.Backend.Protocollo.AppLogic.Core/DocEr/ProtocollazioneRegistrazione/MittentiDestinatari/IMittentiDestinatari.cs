using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.ProtocollazioneRegistrazione.MittentiDestinatari
{
    public interface IMittentiDestinatari
    {
        MittDestType[] GetMittenti();
        MittDestType[] GetDestinatari();
        FlussoType Flusso { get; }
        SmistamentoType GetSmistamento();
    }
}
