using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.WebControls.Common.RicercheStradario
{
    internal class RicercaEsattaConCodiceComune : RicercaEsatta
    {
        internal RicercaEsattaConCodiceComune(IStradarioRepository stradarioRepository, string idcomune)
            : base(stradarioRepository, idcomune, idcomune)
        {

        }
    }
}
