using Init.SIGePro.Manager.DTO.Endoprocedimenti;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneEndoprocedimenti
{
    public interface IEndoDaIdInterventoRepository
    {
        IEnumerable<FamigliaEndoprocedimentoDto> GetPrincipali(int codiceIntervento);
        IEnumerable<FamigliaEndoprocedimentoDto> GetRichiesti(int codiceIntervento);
        IEnumerable<FamigliaEndoprocedimentoDto> GetRicorrenti(int codiceIntervento);
        IEnumerable<FamigliaEndoprocedimentoDto> GetAltri(int codiceIntervento);
    }
}
