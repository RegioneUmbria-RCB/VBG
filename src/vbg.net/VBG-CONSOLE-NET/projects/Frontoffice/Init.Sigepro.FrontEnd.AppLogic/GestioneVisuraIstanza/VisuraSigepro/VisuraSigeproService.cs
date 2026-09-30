using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza.VisuraSigepro
{
	public class VisuraSigeproService : IVisuraService
	{
		private readonly WsDettaglioPraticaRepository _repository;

		internal VisuraSigeproService(WsDettaglioPraticaRepository repository)
		{
			_repository = repository;
		}

		public Istanze GetById(int idPratica, VisuraIstanzaFlags flags)
		{
			var istanza = _repository.GetById(idPratica, flags.LeggiDatiConfigurazione);

			return istanza;
		}

		public Istanze GetByUuid(string uuid)
		{
			var istanza = _repository.GetByUuid(uuid);

			return istanza;

		}

		//public IEnumerable<VisuraListItem> GetListaPratiche(RichiestaListaPratiche richiesta)
		//{
		//    var istanze = this._repository.GetListaPratiche(richiesta);

		//    if (istanze.LimiteRecordsSuperato.GetValueOrDefault(false))
		//    {
		//        throw new RecordCountException();
		//    }

		//    if (istanze.ListaPratiche == null)
		//    {
		//        return Enumerable.Empty<VisuraListItem>();
		//    }

		//    return istanze.ListaPratiche.Select(x => new VisuraListItem(x));
		//}
	}
}
