using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using log4net;
using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.Services.Domanda
{
	public class DomicilioElettronicoService
	{
		ILog _log = LogManager.GetLogger(typeof(DomicilioElettronicoService));
		ISalvataggioDomandaStrategy _salvataggioStrategy;

		public DomicilioElettronicoService(ISalvataggioDomandaStrategy salvataggioStrategy)
		{
			Condition.Requires(salvataggioStrategy, "salvataggioStrategy").IsNotNull();

			this._salvataggioStrategy = salvataggioStrategy;
		}

		public void ImpostaDomicilioElettronico(int idPresentazione, string indirizzo)
		{
			var domanda = _salvataggioStrategy.GetById(idPresentazione);

			domanda.WriteInterface.AltriDati.ImpostaDomicilioElettronico(indirizzo);

			_salvataggioStrategy.Salva(domanda);
		}
	}
}
