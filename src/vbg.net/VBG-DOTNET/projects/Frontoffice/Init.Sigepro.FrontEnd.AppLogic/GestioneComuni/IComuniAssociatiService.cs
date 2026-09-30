using Init.SIGePro.Manager.DTO.Comuni;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneComuni
{
    public interface IComuniAssociatiService
    {
        Task<DatiComuneCompatto?> GetByCodiceComuneAsync(string codiceComune);
        IEnumerable<DatiComuneCompatto> GetComuniAssociati(string[]? codiciComuneDaEscludere = null);
        IEnumerable<DatiComuneCompatto> GetComuniAssociati(string software);
        Task<IEnumerable<DatiComuneCompatto>> GetComuniAssociatiAsync(string[]? codiciComuneDaEscludere = null);
        Task<IEnumerable<DatiComuneCompatto>> GetComuniAssociatiAsync(string software);
    }
}
