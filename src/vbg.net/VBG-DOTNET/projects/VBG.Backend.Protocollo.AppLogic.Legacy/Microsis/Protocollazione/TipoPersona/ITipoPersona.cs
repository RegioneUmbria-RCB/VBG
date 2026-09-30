using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Microsis.Protocollazione.TipoPersona
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
