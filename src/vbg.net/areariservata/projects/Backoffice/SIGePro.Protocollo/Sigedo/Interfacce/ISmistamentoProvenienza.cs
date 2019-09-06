using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Sigedo.Interfacce
{
    public interface ISmistamentoProvenienza
    {
        string GetOperatoreSmistamento();
        bool IsSmistamentoAutomaticoDaOnline { get; }
    }
}
