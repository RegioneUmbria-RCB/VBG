using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.RedirectFineDomanda
{
    public interface IRedirectFineDomandaService
    {
        bool RedirectAFineDomandaAttivo(int codiceIntervento);
        TestoBoxFineDomanda GetTestiBox();
        string GeneraUrlRedirect(int idDomanda);
    }
}
