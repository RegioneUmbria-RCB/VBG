using VBG.AppLogic.SSU.DataAccess;
using VBG.AppLogic.SSU.DataAccess.Dto;

namespace VBG.AppLogic.SSU.GeneratoreRicevute.DataAccess
{
    public interface IDomandeSsuDefaultAliasRepository
    {
        List<(int FkIdDomanda, string Alias, string Software)> GetDomandeElaborabili();
        Task SetNonElaborabileAsync(string token, int idDomanda, bool isElaborabile, string idComune);
        IEnumerable<FoDomandeSsu> GetNonElaborabili(string token, string? idComune = null);
        IEnumerable<FoDomandaSsuMinimalDto> GetStatiDomande(string token);
        int GetTotErrori(string token);
        IEnumerable<FoDomandeSsuAudit> GetErrori(string token, string idComune, int? idDomanda = null);
        void DeleteErrori(string token, string idComune, int idDomanda);
    }
}
