using Init.SIGePro.Manager.DTO.Endoprocedimenti;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Endoprocedimenti
{
    public interface IAllegatiEndoprocedimentiRepository
    {
        IEnumerable<AllegatiPerEndoprocedimentoDto> GetAllegatiProcedimenti(IEnumerable<int> codiciEndoSelezionati);
    }
}
