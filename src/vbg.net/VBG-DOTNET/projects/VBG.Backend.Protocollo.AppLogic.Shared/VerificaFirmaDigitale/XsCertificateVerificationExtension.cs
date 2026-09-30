namespace VBG.Backend.Protocollo.AppLogic.Shared.VerificaFirmaDigitale
{
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
}
