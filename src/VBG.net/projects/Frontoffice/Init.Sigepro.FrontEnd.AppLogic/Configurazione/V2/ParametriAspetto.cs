using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
	public class ParametriAspetto : IParametriConfigurazione
	{
		public readonly string FileConfigurazioneContenuti;
		public readonly string IntestazioneCertificatoInvio;

		internal ParametriAspetto(string fileConfigurazioneContenuti, string intestazioneCertificatoInvio)
		{
			if (String.IsNullOrEmpty(fileConfigurazioneContenuti))
				throw new ArgumentNullException("fileConfigurazioneContenuti");

			this.FileConfigurazioneContenuti = fileConfigurazioneContenuti;
			this.IntestazioneCertificatoInvio = intestazioneCertificatoInvio;
		}
	}
}
