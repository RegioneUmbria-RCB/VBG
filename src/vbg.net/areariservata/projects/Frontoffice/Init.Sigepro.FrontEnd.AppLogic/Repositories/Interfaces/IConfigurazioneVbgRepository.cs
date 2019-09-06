using System;
namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces
{
	public interface IConfigurazioneVbgRepository
	{
		Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService.Configurazione LeggiConfigurazioneComune(string software);
	}
}
