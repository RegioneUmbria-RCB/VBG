using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.StrategiaLetturaRiepilogo
{
	public class IndividuazioneCertificatoInvioDaProceduraOConfigurazione: IStrategiaIndividuazioneCertificatoInvio
	{
		IndividuazioneCertificatoInvioDaConfigurazione _configurazioneStrategy;
		IndividuazioneCertificatoInvioDaProcedura _proceduraStrategy;

		int _idRiepilogo = -1;

		public IndividuazioneCertificatoInvioDaProceduraOConfigurazione(IndividuazioneCertificatoInvioDaConfigurazione individuazioneRiepilogoDaConfigurazione, IndividuazioneCertificatoInvioDaProcedura individuazioneRiepilogoDaProcedura)
		{
			this._configurazioneStrategy = individuazioneRiepilogoDaConfigurazione;
			this._proceduraStrategy		 = individuazioneRiepilogoDaProcedura;
		}

		#region IStrategiaIndividuazioneRiepilogo Members

		public bool IsCertificatoDefinito
		{
			get 
			{
				if (this._proceduraStrategy.IsCertificatoDefinito)
				{
					this._idRiepilogo = this._proceduraStrategy.CodiceOggetto;
					return true;
				}

				if (this._configurazioneStrategy.IsCertificatoDefinito)
				{
					this._idRiepilogo = this._configurazioneStrategy.CodiceOggetto;
					return true;
				}

				return false;
			}
		}

		public int CodiceOggetto
		{
			get 
			{
				if (this._idRiepilogo == -1)
					throw new InvalidOperationException("Si sta cercando di leggere l'id del riepilogo della domanda ma non è stato possibile individuare un riepilogo della domanda tramite la procedura o tramite la configurazione");

				return this._idRiepilogo;
			}
		}

		#endregion
	}
}
