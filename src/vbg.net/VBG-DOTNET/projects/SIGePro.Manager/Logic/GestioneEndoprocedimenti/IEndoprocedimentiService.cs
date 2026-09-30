using Init.SIGePro.Manager.DTO.Endoprocedimenti;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneEndoprocedimenti
{
    public interface IEndoprocedimentiService
    {
        EndoprocedimentiAreaRiservataDto GetListaEndoDaIdInterventoECodiceComune(string codiceComune, int idIntervento, bool utenteTester);
    }
}
