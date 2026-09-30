using Init.SIGePro.Manager.DTO.Comuni;
using Init.SIGePro.Manager.DTO.StradarioComune;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni
{
    public interface IStradarioRepository
    {
        StradarioEstesoDto GetByCodiceStradario(int codiceStradario);
        StradarioEstesoDto GetByCodiceStradario(string aliasComune, int codiceStradario);
        StradarioEstesoDto GetByIndirizzo(string aliasComune, string codiceComune, string indirizzo);
        List<StradarioDto> GetByMatchParziale(string aliasComune, string codiceComune, string comuneLocalizzazione, string indirizzo);
        ValueTask<List<StradarioDto>> GetByMatchParzialeAsync(string codiceComune, string comuneLocalizzazione, string indirizzo);
        ValueTask<List<StradarioDto>> GetByMatchParzialeAsyncIncludiDisabilitateAsync(string codiceComune, string comuneLocalizzazione, string indirizzo);

        IEnumerable<ColoreStradarioDto> GetListaColori(string aliasComune = "");

        StradarioDto GetByCodViario(string alias, string codViario);

        IEnumerable<DatiComuneCompatto> GetComuniStradario(string codiceComune);
    }
}
