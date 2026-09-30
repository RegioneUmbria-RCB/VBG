using System;
namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces
{
	public interface ITitoliRepository
	{
		Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.Titoli[] GetList(string aliasComune);
	}
}
