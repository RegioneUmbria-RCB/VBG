using System;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using System.Collections.Generic;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces
{
	public interface IIstanzePresentateRepository
	{
		List<FoVisuraCampi> GetFiltri(string alias, string software, TipoContestoVisuraEnum contestoVisura);
		RichiestaPraticheListaResponse GetListaPratiche(string idComune, string software, RichiestaPraticheListaRequest richiesta);
		RichiestaPraticaResponse GetDettaglioPratica(string alias, string software, string codiceIstanza);
		BinaryFile GetDocumentoPratica(string aliasComune, string software, string codiceOggetto);
	}
}
