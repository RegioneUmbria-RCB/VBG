using Init.SIGePro.Manager.DTO.StradarioComune;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.WebControls.Common.RicercheStradario
{
    internal interface IRicercaIndirizzo
    {
        IEnumerable<StradarioDto> Cerca(string testo);
    }
}
