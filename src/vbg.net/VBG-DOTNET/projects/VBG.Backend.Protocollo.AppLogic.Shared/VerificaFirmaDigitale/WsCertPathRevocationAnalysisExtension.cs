namespace VBG.Backend.Protocollo.AppLogic.Shared.VerificaFirmaDigitale
{
    public static class WsCertPathRevocationAnalysisExtension
    {
        public static class Constants
        {
            public const string InvalidCerPathRevocationAnalysis = "INVALID";
        }

        public static bool IsRevoked(this wsCertPathRevocationAnalysis cerPathRevocationAnalysis)
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
}
