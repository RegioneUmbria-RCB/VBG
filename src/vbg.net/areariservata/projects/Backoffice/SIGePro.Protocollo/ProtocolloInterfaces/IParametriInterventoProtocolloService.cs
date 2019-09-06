using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;

namespace Init.SIGePro.Protocollo.ProtocolloInterfaces
{
    public interface IParametriInterventoProtocolloService
    {
        int? CodiceTestoTipo { get; }
        string OggettoDefault { get; }
    }
}
