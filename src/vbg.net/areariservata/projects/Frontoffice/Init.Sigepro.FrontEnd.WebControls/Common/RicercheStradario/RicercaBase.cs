using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.WebControls.Common.RicercheStradario
{
    internal class RicercaBase
    {
        protected IStradarioRepository _stradarioRepository;
        protected string _idcomune;
        protected string _codiceComune;

        public RicercaBase(IStradarioRepository stradarioRepository, string idcomune, string codiceComune)
        {
            this._stradarioRepository = stradarioRepository;
            this._idcomune = idcomune;
            this._codiceComune = codiceComune;
        }
    }
}
