using Init.SIGePro.Manager.DTO.TabelleDiBase;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneTabelleDiBase
{
    public interface ITitoliRepository
    {
        TitoloDto[] GetList();
    }
}
