using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.ProtocolloInterfaces
{
    public interface ILeggiProtoMittentiDestinatari
    {
        string InCaricoA { get; }
        string InCaricoADescrizione { get; }

        MittDestOut[] GetMittenteDestinatario();

        string Flusso { get; }
    }
}
