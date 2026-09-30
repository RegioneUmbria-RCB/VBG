using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Livorno.PortaleCittadino
{
    public interface ILogicaSincronizzazioneAllegati
    {
        void Sincronizza(DomandaOnline domanda, IPortaleCittadinoService portaleService);
    }
}
