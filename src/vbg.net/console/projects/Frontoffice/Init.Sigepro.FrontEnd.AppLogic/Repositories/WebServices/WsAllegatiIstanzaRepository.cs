using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;

using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using log4net;
using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza.VisuraSigepro;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
{
	internal class WsAllegatiIstanzaRepository : IAllegatiIstanzaRepository
	{
		ILog _log = LogManager.GetLogger(typeof(WsAllegatiIstanzaRepository));
		VisuraIstanzeServiceCreator _serviceCreator;

		public WsAllegatiIstanzaRepository(VisuraIstanzeServiceCreator serviceCreator)
		{
			Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();

			this._serviceCreator = serviceCreator;
		}


		public void AggiungiAllegatoIstanza(int codiceIstanza, string descrizioneFile, int codiceOggetto)
		{
			try
			{
				using (var wsIstanza = _serviceCreator.CreateClient())
				{

					wsIstanza.Service.AggiungiAllegatoAIstanza(wsIstanza.Token, codiceIstanza, descrizioneFile, codiceOggetto);
				}
			}
			catch (Exception ex)
			{
				_log.Error($"Errore durante l'aggiunta di un allegato alla domanda {codiceIstanza}: {ex.ToString()}");

				throw;
			}
		}
	}
}
