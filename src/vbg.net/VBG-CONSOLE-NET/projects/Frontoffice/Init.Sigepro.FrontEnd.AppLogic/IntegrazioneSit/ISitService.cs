using Init.Sigepro.FrontEnd.AppLogic.SigeproSitWebService;
namespace Init.Sigepro.FrontEnd.AppLogic.IntegrazioneSit
{
	public interface ISitService
	{
		EsitoValidazioneSit ValidaCampo(string codiceComune, string nomeCampo, IParametriRicercaLocalizzazione parametriRicerca);
		CaratteristicheSit GetFeatures(string codiceComune);
		string[] RicercaValori(string codiceComune,string nomeCampo, IParametriRicercaLocalizzazione parametriRicerca);
	}
}
