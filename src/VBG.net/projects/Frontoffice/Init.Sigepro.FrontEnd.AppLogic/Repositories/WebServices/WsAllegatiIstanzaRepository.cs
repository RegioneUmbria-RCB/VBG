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


namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
{
	internal class WsAllegatiIstanzaRepository : IAllegatiIstanzaRepository
	{
		ILog _log = LogManager.GetLogger(typeof(WsAllegatiIstanzaRepository));
		IstanzeServiceCreator _serviceCreator;

		public WsAllegatiIstanzaRepository(IstanzeServiceCreator serviceCreator)
		{
			Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();

			this._serviceCreator = serviceCreator;
		}


		public void AggiungiAllegatoIstanza(string idComune, int codiceIstanza, string descrizioneFile, int codiceOggetto)
		{
			try
			{
				using (var wsIstanza = _serviceCreator.CreateClient(idComune))
				{

					wsIstanza.Service.AggiungiAllegatoAIstanza(wsIstanza.Token, codiceIstanza, descrizioneFile, codiceOggetto);
				}
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante l'aggiunta di un allegato alla domanda {0} nell'idComune {1}: {2}", codiceIstanza, idComune, ex.ToString());

				throw;
			}
		}
	}
}
