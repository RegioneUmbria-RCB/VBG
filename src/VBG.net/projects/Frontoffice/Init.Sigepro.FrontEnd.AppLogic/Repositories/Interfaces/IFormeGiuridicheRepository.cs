using System;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces
{
	public interface IFormeGiuridicheRepository
	{
		Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.FormeGiuridiche[] GetList(string aliasComune);
		FormeGiuridiche GetById(string id);
	}
}
