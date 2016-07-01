using System;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using System.Collections.Generic;
using Init.Sigepro.FrontEnd.AppLogic.Entities.Visura;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces
{
	public interface IVisuraRepository
	{
		DatiDettaglioPratica GetDettaglioPratica(string aliasComune, string idPratica);
		List<PraticaPresentata> GetListaPratiche(string aliasComune, RichiestaListaPratiche richiesta);
	}

	public interface IDettaglioPraticaRepository
	{
		Istanze GetById(string aliasComune, int idPratica, bool leggiDatiConfigurazione);
	}
}
