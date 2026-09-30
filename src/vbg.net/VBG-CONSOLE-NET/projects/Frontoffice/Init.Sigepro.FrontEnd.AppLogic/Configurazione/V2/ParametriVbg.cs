using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
	public class ParametriVbg : IParametriConfigurazione
	{
		public readonly string FileConfigurazioneContenuti;
		public readonly string IntestazioneCertificatoInvio;
        public readonly string ResponsabileTrattamento;
        public readonly string DataProtectionOfficer;
        public readonly string DenominazioneComune;


        internal ParametriVbg(string fileConfigurazioneContenuti, string intestazioneCertificatoInvio, string responsabileTrattamento,string dataProtectionOfficer, string denominazioneComune)
		{
			if (String.IsNullOrEmpty(fileConfigurazioneContenuti))
				throw new ArgumentNullException("fileConfigurazioneContenuti");

			this.FileConfigurazioneContenuti = fileConfigurazioneContenuti;
			this.IntestazioneCertificatoInvio = intestazioneCertificatoInvio;
            this.ResponsabileTrattamento = responsabileTrattamento;
            this.DataProtectionOfficer = dataProtectionOfficer;
            this.DenominazioneComune = denominazioneComune;
        }

    }
}
