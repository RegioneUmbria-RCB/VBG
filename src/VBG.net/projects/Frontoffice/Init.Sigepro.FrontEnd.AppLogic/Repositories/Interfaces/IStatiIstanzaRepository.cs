using System;
namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces
{
	public interface IStatiIstanzaRepository
	{
		Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.StatiIstanza GetById(string aliasComune, string software, string codiceStato);
		Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.StatiIstanza[] GetList(string aliasComune, string software);
	}
}
