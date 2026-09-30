using System;
namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces
{
	public interface IDomandeEndoRepository
	{
		Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.InfoAreaTematica[] GetAreeTematiche(string alias, string software, int areaPadre);
		Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.InfoDomandaAreaTematica[] GetDomandeArea(string alias, int codiceArea);
		Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.InfoEndo[] GetEndoDomanda(string alias, int idDomanda);
		Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.AreaIndividuazioneEndoDto[] GetStrutturaDomande(string alias, string software);
	}
}
