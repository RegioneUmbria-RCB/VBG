using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.WebControls.Common.RicercheStradario
{
    internal interface IRicercaIndirizzo
    {
        IEnumerable<StradarioDto> Cerca(string testo);
    }
}
