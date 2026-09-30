using Init.SIGePro.Manager.DTO.Oneri;

namespace Init.SIGePro.Manager.Logic.ServiziConsole.GestioneOneri.GestioneConti
{
    public interface IContiService
    {
        ContoDto GetContoDaIdCausaleOnere(string codiceComune, string softwareSuCuiCercareCausale, int idCausaleOnere);
    }
}
