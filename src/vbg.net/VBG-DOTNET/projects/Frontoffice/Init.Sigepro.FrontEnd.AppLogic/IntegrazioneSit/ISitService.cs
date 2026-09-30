using Init.Sigepro.FrontEnd.AppLogic.SigeproSitWebService;
namespace Init.Sigepro.FrontEnd.AppLogic.IntegrazioneSit
{
	public interface ISitService
	{
		EsitoValidazioneSit ValidaCampo(string nomeCampo, IParametriRicercaLocalizzazione parametriRicerca);
		CaratteristicheSit GetFeatures();
		string[] RicercaValori(string nomeCampo, IParametriRicercaLocalizzazione parametriRicerca);
	}
}
