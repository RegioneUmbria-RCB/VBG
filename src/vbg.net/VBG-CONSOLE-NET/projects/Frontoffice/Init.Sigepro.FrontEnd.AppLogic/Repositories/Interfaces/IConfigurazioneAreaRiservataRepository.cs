using System;
namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces
{
	public interface IConfigurazioneAreaRiservataRepository
	{
		Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.ConfigurazioneAreaRiservataDto DatiConfigurazione(string idComune, string software);
	}
}
