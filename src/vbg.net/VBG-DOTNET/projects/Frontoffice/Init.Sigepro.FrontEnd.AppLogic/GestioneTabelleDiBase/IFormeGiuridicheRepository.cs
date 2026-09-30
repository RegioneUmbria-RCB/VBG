using Init.SIGePro.Manager.DTO.TabelleDiBase;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneTabelleDiBase
{
    public interface IFormeGiuridicheRepository
    {
        FormaGiuridicaDto[] GetList(string aliasComune = "");
        FormaGiuridicaDto GetById(string id);
    }
}
