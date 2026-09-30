using Init.SIGePro.Manager.DTO.TabelleDiBase;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneTabelleDiBase
{
    public interface IElenchiProfessionaliRepository
    {
        List<ElencoProfessionaleDto> GetList();
    }
}
