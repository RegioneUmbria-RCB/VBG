using System;
namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces
{
	public interface IElenchiProfessionaliRepository
	{
		System.Collections.Generic.List<Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.ElenchiProfessionaliBase> GetList(string aliasComune);
	}
}
