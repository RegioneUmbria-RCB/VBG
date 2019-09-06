using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Microsis.Protocollazione.TipoPersona
{
    public interface ITipoPersona
    {
        string Nome { get; }
        string Cognome {get; }
        string CodiceFiscale { get; }
        string RagioneSociale { get; }
        string PartitaIva { get; }
        string Protocollo { get; }
        string DataProtocollo { get; }
    }
}
