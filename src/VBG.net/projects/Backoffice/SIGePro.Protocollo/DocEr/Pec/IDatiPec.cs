using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocEr.Pec
{
    public interface IDatiPec
    {
        string Oggetto { get; }
        FlussoType Flusso { get; }
        IntestazioneTypeDestinatari Destinatari { get; }
    }
}
