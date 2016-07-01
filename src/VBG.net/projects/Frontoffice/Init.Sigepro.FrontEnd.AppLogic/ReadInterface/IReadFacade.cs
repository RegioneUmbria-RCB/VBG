using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;

namespace Init.Sigepro.FrontEnd.AppLogic.ReadInterface
{
	public interface IReadFacade
	{
		ICittadinanzeService Cittadinanze { get; }
		IComuniService Comuni { get; }
		IDomandaOnlineReadInterface Domanda { get; }
		ITipiSoggettoService TipiSoggetto { get; }
		IAtecoRepository Ateco { get; }
		IInterventiRepository Interventi { get; }
		IStradarioRepository Stradario { get; }
		PresentazioneIstanzaDataKey DomandaDataKey { get; }
	}
}
