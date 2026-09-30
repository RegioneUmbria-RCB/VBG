using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using PersonalLib2.Data.Providers;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza
{
    public interface IVisuraService
    {
        Istanze GetById(int idPratica, VisuraIstanzaFlags flags);
        Istanze GetByUuid(string uuid, bool effettuaSubVisuraMovimenti);
        IEnumerable<VisuraListItem> GetListaPratiche(RichiestaListaPraticheV3 richiesta);
        Task<VisuraListResponse> GetListaPratichePaginataAsync(RichiestaListaPraticheV3 richiesta, QueryPaginationRequest paginationRequest);

        string GetUUIDDaCodiceIstanza(int codiceIstanza);
    }
}
