using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Iride.Proxies;

namespace Init.SIGePro.Protocollo.Iride.Services
{
    internal interface IProtocolloIrideService
    {
        ProtocolloOut InserisciProtocollo(ProtocolloIn protocolloIn);
        DocumentoOut LeggiProtocollo(short annoProtocollo, int numeroProtocollo, string operatore, string ruolo);
        DocumentoOut LeggiDocumento(int idProtocollo, string operatore, string ruolo);
    }
}
