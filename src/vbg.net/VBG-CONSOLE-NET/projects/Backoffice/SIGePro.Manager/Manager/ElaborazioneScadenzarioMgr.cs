using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Configuration;
using Init.SIGePro.Manager.Properties;

namespace Init.SIGePro.Manager.Manager
{
	[Serializable]
	public enum ComportamentoElaborazioneEnum
	{
		ElaboraAsincrono,
		ElaboraSincrono,
		NonElaborare,
		IgnoraParametroConfigurazione
	}

	public class ElaborazioneScadenzarioMgr
	{
		/// <summary>
		/// Effettuare l'elaborazione dello scadenzario
		/// </summary>
		/// <param name="idComune"></param>
		/// <param name="codiceIstanza"></param>
		/// <param name="comportamento"></param>
		public void Elabora(string idComune, int codiceIstanza, ComportamentoElaborazioneEnum comportamento)
		{
			//var forzaElaborazione = ConfigurationSettings.AppSettings["ForzaElaborazione"];

			if (Settings.Default.ForzaElaborazioneScadenzario != ComportamentoElaborazioneEnum.IgnoraParametroConfigurazione)
			{
				comportamento = Settings.Default.ForzaElaborazioneScadenzario;
			}


			switch (comportamento)
			{
				case(ComportamentoElaborazioneEnum.NonElaborare):
					return;
				case(ComportamentoElaborazioneEnum.ElaboraAsincrono):
					EffettuaElaborazioneAsincrona(idComune, codiceIstanza);
					return;
				case(ComportamentoElaborazioneEnum.ElaboraSincrono):
					EffettuaElaborazioneSincrona(idComune, codiceIstanza);
					return;
			}
		}

		private void EffettuaElaborazioneSincrona(string idComune, int codiceIstanza)
		{
			//throw new NotImplementedException();
		}

		private void EffettuaElaborazioneAsincrona(string idComune, int codiceIstanza)
		{
			//throw new NotImplementedException();
		}
	}
}
