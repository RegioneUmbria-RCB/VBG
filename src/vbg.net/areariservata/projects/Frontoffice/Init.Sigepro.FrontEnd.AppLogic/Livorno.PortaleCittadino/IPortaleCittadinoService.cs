using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Livorno.PortaleCittadino
{
    public interface IPortaleCittadinoService
    {
        PCScheda GetSchedaDaIdIntervento(int idIntervento);
        void SincronizzaAllegati(int idDomanda);
    }
}
