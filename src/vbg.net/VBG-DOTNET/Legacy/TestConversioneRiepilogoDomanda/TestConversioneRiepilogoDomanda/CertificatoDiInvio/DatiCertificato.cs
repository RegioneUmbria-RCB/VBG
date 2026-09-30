using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TestConversioneRiepilogoDomanda.CertificatoDiInvio
{
	public class DatiCertificato
	{
		string _xmlString;

		public DatiCertificato(string xmlString)
		{
			this._xmlString = xmlString;
		}

		internal string AsXmlString()
		{
			return this._xmlString;
		}
	}
}
