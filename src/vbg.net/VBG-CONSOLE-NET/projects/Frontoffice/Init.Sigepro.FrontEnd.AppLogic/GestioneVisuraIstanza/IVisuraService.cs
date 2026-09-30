using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza
{
	public interface IVisuraService
	{
		Istanze GetById(int idPratica, VisuraIstanzaFlags flags);
		Istanze GetByUuid(string uuid);
		// IEnumerable<VisuraListItem> GetListaPratiche(RichiestaListaPratiche richiesta);
	}
}
