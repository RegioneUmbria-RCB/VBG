using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;

namespace Init.Sigepro.FrontEnd.AppLogic.ReadInterface
{
	public interface IReadFacade : IReadDatiDomanda
	{
		ICittadinanzeService Cittadinanze { get; }
		IComuniService Comuni { get; }
		
		ITipiSoggettoService TipiSoggetto { get; }
		IAtecoRepository Ateco { get; }
		IInterventiRepository Interventi { get; }
		IStradarioRepository Stradario { get; }
		
	}
}
