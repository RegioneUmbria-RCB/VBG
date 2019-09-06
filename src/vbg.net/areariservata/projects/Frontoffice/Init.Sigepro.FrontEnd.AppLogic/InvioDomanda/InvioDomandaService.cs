// -----------------------------------------------------------------------
// <copyright file="InvioDomandaService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.InvioDomanda
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
	using log4net;

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


		public InvioIstanzaResult Invia(int idPresentazione, string pecDestinatario)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idPresentazione);

			var result = this._workflowInvioDomanda.Processa(domanda, pecDestinatario);

            if (result.Esito != InvioIstanzaResult.TipoEsitoInvio.ErroreInvio)
            {
                domanda.ImpostaComePresentata();
            }
			_salvataggioDomandaStrategy.Salva(domanda);

			return result;
		}

		public void MarcaDomandaComePresentata(int idPresentazione)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idPresentazione);

			domanda.ImpostaComePresentata();

			_salvataggioDomandaStrategy.Salva(domanda);
		}
	}
}
