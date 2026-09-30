using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.WebControls.Common.RicercheStradario
{
    internal class RicercaParziale : RicercaBase, IRicercaIndirizzo
    {
        internal RicercaParziale(IStradarioRepository stradarioRepository, string idcomune, string codiceComune)
            : base(stradarioRepository, idcomune, codiceComune)
        {

        }

        public IEnumerable<StradarioDto> Cerca(string testo)
        {
            return _stradarioRepository.GetByMatchParziale(this._idcomune, this._codiceComune, String.Empty, testo);
        }
    }
}
