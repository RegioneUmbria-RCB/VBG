using System;
namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces
{
	public interface ISoftwareRepository
	{
		Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.Software GetById(string aliasComune, string codice);
	}
}
