using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.Interfacce
{
    public interface ISmistamentoProvenienza
    {
        string GetOperatoreSmistamento(IVerticalizzazioniFactory verticalizzazioniFactory);
        bool IsSmistamentoAutomaticoDaOnline { get; }
    }
}
