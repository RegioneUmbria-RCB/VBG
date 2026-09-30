using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.SIGePro.Manager.DTO.StradarioComune;
using System;
using System.Collections.Generic;

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
            return this._stradarioRepository.GetByMatchParziale(this._idcomune, this._codiceComune, String.Empty, testo);
        }
    }
}
