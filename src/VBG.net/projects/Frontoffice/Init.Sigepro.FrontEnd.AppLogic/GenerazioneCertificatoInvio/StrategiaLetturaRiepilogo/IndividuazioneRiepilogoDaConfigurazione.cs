using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneCertificatoInvio.StrategiaLetturaRiepilogo
{
	public class IndividuazioneCertificatoInvioDaConfigurazione : IStrategiaIndividuazioneCertificatoInvio	
	{
		IConfigurazione<ParametriInvio> _configurazione;

		public IndividuazioneCertificatoInvioDaConfigurazione(IConfigurazione<ParametriInvio> configurazione)
		{
			Condition.Requires(configurazione, "configurazione").IsNotNull();

			this._configurazione = configurazione;
		}



		public bool IsCertificatoDefinito
		{
			get 
			{
				return _configurazione.Parametri.CodiceOggettoInvioConFirmaDigitale.HasValue;
			}
		}

		public int CodiceOggetto
		{
			get{
				if (!_configurazione.Parametri.CodiceOggettoInvioConFirmaDigitale.HasValue)
					throw new InvalidOperationException("Si sta cercando di leggere il riepilogo della domanda ma in configurazione non è presente nessun riepilogo");

				return _configurazione.Parametri.CodiceOggettoInvioConFirmaDigitale.Value;
			}
			
		}
	}
}
