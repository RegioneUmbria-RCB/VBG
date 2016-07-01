using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneCertificatoInvio
{
	public class CertificatoDiInvioHtml
	{
		public string Html { get { return Encoding.UTF8.GetString(this.RawData); } }
		public byte[] RawData { get; private set; }
		public bool CertificatoAllegabileAIstanza { get; private set; }

		public CertificatoDiInvioHtml(byte[] rawHtml, bool certificatoAllegabileAIstanza)
		{
			this.RawData = rawHtml;
			this.CertificatoAllegabileAIstanza = certificatoAllegabileAIstanza;
		}
	}
}
