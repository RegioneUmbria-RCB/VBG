using System;
namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces
{
	public interface IConfigurazioneContenutiRepository
	{
		Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.ConfigurazioneContenutiDto GetConfigurazione(string alias, string software);
	}
}
