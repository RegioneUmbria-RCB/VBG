using System.Collections.Generic;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
namespace Init.Sigepro.FrontEnd.AppLogic.Adapters
{
	public interface ILogicaEstrazioneSoggetti
	{
		PresentazioneIstanzaDataSet.ANAGRAFERow EstraiAzienda();
		PresentazioneIstanzaDataSet.ANAGRAFERow EstraiRichiedente();
		IEnumerable<PresentazioneIstanzaDataSet.ANAGRAFERow> EstraiListaRichiedenti();
		PresentazioneIstanzaDataSet.ANAGRAFERow EstraiTecnico();
	}
}
