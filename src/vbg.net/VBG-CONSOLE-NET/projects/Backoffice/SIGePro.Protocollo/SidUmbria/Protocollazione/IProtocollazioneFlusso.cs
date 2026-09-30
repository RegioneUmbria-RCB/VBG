using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloSidUmbriaService;

namespace Init.SIGePro.Protocollo.SidUmbria.Protocollazione
{
    public interface IProtocollazioneFlusso
    {
        string Flusso { get; }
        corrispondente[] GetCorrispondenti();
    }
}
