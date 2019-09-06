using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using log4net;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda.InvioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using System.Threading;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Utils;

namespace Init.Sigepro.FrontEnd.AppLogic.Services.Domanda
{
	public enum TipoInvioEnum
	{
		Firma,
		Sottoscrizione
	}


	public class InvioDomandaService
	{
		ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
		WorkflowInvioDomanda _workflowInvioDomanda;
		ILog m_logger = LogManager.GetLogger(typeof(InvioDomandaService));

		public InvioDomandaService(ISalvataggioDomandaStrategy salvataggioDomandaStrategy, WorkflowInvioDomanda workflowInvioDomanda)
		{
			this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
			this._workflowInvioDomanda = workflowInvioDomanda;
		}


		public EsitoInvio Invia(int idPresentazione, string pecDestinatario, string nominativoUtenteCheHaEffettuatoInvio)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idPresentazione);

			var result = this._workflowInvioDomanda.Processa(domanda, pecDestinatario, nominativoUtenteCheHaEffettuatoInvio);

			_salvataggioDomandaStrategy.Salva( domanda );

			return EsitoInvio.FromInvioIstanzaResult(result, domanda);
		}
	}
}
