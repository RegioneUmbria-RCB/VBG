using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.WsVerificaFirmaDigitale;
using System.Security.Cryptography.X509Certificates;

namespace Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale
{
	public class EsitoVerificaFirma
	{
		public IEnumerable<DettaglioFirma> DettagliFirme{get;private set;}

		internal EsitoVerificaFirma(wsValidationReport validationResult)
		{
			this.DettagliFirme = validationResult
									.signatureInformationList
									.Select(x => new DettaglioFirma(x));
		}
	}

	public class DettaglioFirma
	{
		internal DettaglioFirma(wsSignatureInformation x)
		{
			var cert = new X509Certificate( x.signatureLevelAnalysis.levelBES.signingCertificate );

			this.Soggetto = cert.Subject;
			this.Emittente = cert.Issuer;
			this.ValidoDal = cert.GetEffectiveDateString();
			this.ValidoAl = cert.GetExpirationDateString();
			this.Seriale = cert.GetSerialNumberString();

			this.EsitoVerificaFirma = x.IsFirmaValida();
			this.EsitoVerificaRevoca = !x.IsCertificatoRevocato();

			this.DettaglioVerificaRevoca = x.certPathRevocationAnalysis
											.certificatePathVerification
											.Select(z => new DettaglioVerificaRevoca(z));
		}
		public string Soggetto { get; private set; }
		public string Emittente { get; private set; }
		public string ValidoDal { get; private set; }
		public string ValidoAl { get; private set; }
		public string Seriale { get; private set; }

		public IEnumerable<DettaglioVerificaRevoca> DettaglioVerificaRevoca { get; set; }

		public bool EsitoVerificaFirma { get; private set; }
		public bool EsitoVerificaRevoca { get; private set; }
	}

	public class DettaglioVerificaRevoca
	{
		internal DettaglioVerificaRevoca(wsCertificateVerification z)
		{
			var cert = new X509Certificate(z.certificate);

			this.Soggetto = cert.Subject;
			this.Emittente = cert.Issuer;
			this.VerificaValiditaAlTempoDellaFirma = true;// z.validityPeriodVerification;	////AAAAAAAHAHHH!!!!!!
			this.Revocato = z.IsRevoked();
			this.DataDiRevoca = z.certificateStatus.revocationDate == DateTime.MinValue ? String.Empty : z.certificateStatus.revocationDate.ToString();

	
		}
		public string Soggetto { get; private set; }
		public string Emittente { get; private set; }
		public bool VerificaValiditaAlTempoDellaFirma { get; private set; }
		public bool Revocato { get; private set; }
		public string DataDiRevoca { get; private set; }
	}

}
