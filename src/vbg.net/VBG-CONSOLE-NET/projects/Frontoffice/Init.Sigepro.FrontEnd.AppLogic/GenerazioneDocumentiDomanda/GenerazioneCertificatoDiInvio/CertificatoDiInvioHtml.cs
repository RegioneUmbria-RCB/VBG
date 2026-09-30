using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio
{
	public class CertificatoDiInvioHtml
	{
        public readonly string Html;
        public readonly bool CertificatoAllegabileAIstanza;

		public CertificatoDiInvioHtml(string html, bool certificatoAllegabileAIstanza)
		{
			this.Html= html;
			this.CertificatoAllegabileAIstanza = certificatoAllegabileAIstanza;
		}
	}
}
