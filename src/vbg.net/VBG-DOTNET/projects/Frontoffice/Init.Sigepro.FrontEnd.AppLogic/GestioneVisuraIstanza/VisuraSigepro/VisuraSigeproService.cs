using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using PersonalLib2.Data.Providers;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza.VisuraSigepro
{
    public class VisuraSigeproService : IVisuraService
    {
        WsDettaglioPraticaRepository _repository;

        public VisuraSigeproService(WsDettaglioPraticaRepository repository)
        {
            this._repository = repository;
        }

        public Istanze GetById(int idPratica, VisuraIstanzaFlags flags)
        {
            var istanza = this._repository.GetById(idPratica, flags.LeggiDatiConfigurazione);

            return istanza;
        }

        public Istanze GetByUuid(string uuid, bool effettuaSubVisuraMovimenti)
        {
            var istanza = this._repository.GetByUuid(uuid, effettuaSubVisuraMovimenti);

            return istanza;

        }

        public string GetUUIDDaCodiceIstanza(int codiceIstanza)
        {
            return this._repository.GetUUIDDaCodiceIstanza(codiceIstanza);
        }

        public IEnumerable<VisuraListItem> GetListaPratiche(RichiestaListaPraticheV3 richiesta)
        {
            var istanze = this._repository.GetListaPratiche(richiesta);

            if (istanze.LimiteRecordsSuperato)
            {
                throw new RecordCountException();
            }

            if (istanze.Pratiche == null)
            {
                return Enumerable.Empty<VisuraListItem>();
            }

            return istanze.Pratiche.Select(x => new VisuraListItem(x));
        }

        public async Task<VisuraListResponse> GetListaPratichePaginataAsync(RichiestaListaPraticheV3 richiesta, QueryPaginationRequest paginationRequest)
        {
            var istanze = await this._repository.GetListaPratichePaginataAsync(richiesta, paginationRequest);

            if (istanze.LimiteRecordsSuperato)
            {
                throw new RecordCountException();
            }

            var pratiche = istanze.Pratiche?.Select(x => new VisuraListItem(x)) ?? Enumerable.Empty<VisuraListItem>();

            return new VisuraListResponse()
            {
                Pratiche = pratiche,
                RecordCount = istanze.RecordCount,
                TotalPages = istanze.TotalPages
            };
        }
    }
}
