using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.WebControls.Common.RicercheStradario
{
    internal class RicercaEsatta : RicercaBase, IRicercaIndirizzo
    {
        internal RicercaEsatta(IStradarioRepository stradarioRepository, string idcomune, string codiceComune)
            : base(stradarioRepository, idcomune, codiceComune)
        {

        }

        public IEnumerable<StradarioDto> Cerca(string testo)
        {
            var value = this._stradarioRepository.GetByIndirizzo(this._idcomune, this._codiceComune, testo);

            if (value == null)
            {
                return Enumerable.Empty<StradarioDto>();
            }

            return new[] {
                    value.ToStradarioDto()
                };
        }
    }

}
