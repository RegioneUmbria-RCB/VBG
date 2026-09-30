using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.Smistamenti
{
    public interface ISmistamentoFlusso
    {
        string GetUoSmistamento(IVerticalizzazioniFactory verticalizzazioniFactory);
    }
}
