using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.WsVerificaFirmaDigitale;

namespace Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale
{
	public static class WsValidationReportExtension
	{
		private static class Constants
		{
			public const string EsitoVerificaRevocaNonValido = "INVALID";
			public const string EsitoVerificaRevocaIndeterminato = "UNDETERMINED";
		}

		public static bool IsFirmaValida(this wsValidationReport validationResult)
		{
			if (validationResult.signatureInformationList == null || validationResult.signatureInformationList.Length == 0)
				return false;

			return validationResult
						.signatureInformationList
						.Where(x => !x.IsFirmaValida())
						.Count() == 0;
		}

		public static bool IsCertificatoRevocato(this wsValidationReport validationResult)
		{
			if (validationResult.signatureInformationList == null || validationResult.signatureInformationList.Length == 0)
				return false;

			return validationResult
						.signatureInformationList
						.Where(x => x.IsCertificatoRevocato())
						.Count() > 0;
		}
	}

	public static class XsCertificateVerificationExtension
	{
		public static class Constants
		{
			public const string RevokedStatus = "REVOKED";
		}


		public static bool IsRevoked(this wsCertificateVerification item)
		{
			return item.certificateStatus.status == Constants.RevokedStatus;
		}
	}


	public static class WsCertPathRevocationAnalysisExtension
	{
		public static class Constants
		{
			public const string InvalidCerPathRevocationAnalysis = "INVALID";
		}

		public static bool IsRevoked( this wsCertPathRevocationAnalysis cerPathRevocationAnalysis)
		{
			if (cerPathRevocationAnalysis.summary != Constants.InvalidCerPathRevocationAnalysis)
				return false;

			foreach (var item in cerPathRevocationAnalysis.certificatePathVerification)
			{
				if (item.IsRevoked())
					return true;
			}

			return false;
		}
	}

	public static class WsSignatureInformationExtension
	{
		private static class Constants
		{
			public const string FinalConclusionQES = "QES";
		}

		public static bool IsFirmaValida(this wsSignatureInformation si)
		{
			return si.finalConclusion == Constants.FinalConclusionQES;
		}

		public static bool IsCertificatoRevocato(this wsSignatureInformation si)
		{
			return si.certPathRevocationAnalysis.IsRevoked();
		}
	}
}
