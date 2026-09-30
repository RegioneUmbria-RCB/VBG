using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielMercatoService2;
using Init.SIGePro.Protocollo.InsielMercato.Services;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.InsielMercato2.Protocollazione
{
    public interface IProtocollazione
    {
        direction1 Flusso { get; }
        sender[] GetMittenti();
        recipient[] GetDestinatari();
        document[] GetAllegati();
        string Registro { get; }
        string CodiceUfficioOperante { get; }
        DateTime? DataSpedizione { get; }
    }
}
