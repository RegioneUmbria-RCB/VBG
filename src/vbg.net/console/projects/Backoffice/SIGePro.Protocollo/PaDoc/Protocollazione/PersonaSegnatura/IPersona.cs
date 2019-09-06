using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.PaDoc.Protocollazione.PersonaSegnatura
{
    public interface IPersona
    {
        string Denominazione { get; }
        object[] GetPersona();
        IndirizzoPostale GetIndirizzoPostale();
        IndirizzoTelematico IndirizzoTelematico { get; }
    }
}
