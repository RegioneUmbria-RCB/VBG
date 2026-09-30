namespace VBG.Backend.Protocollo.AppLogic.Shared.VerificaFirmaDigitale
{
    public static class WsValidationReportExtension
    {
        public static bool IsFirmaValida(this wsValidationReport validationResult)
        {
            if (validationResult.signatureInformationList == null || validationResult.signatureInformationList.Length == 0)
                return false;

            var contieneVerificheNonValide = validationResult
                        .signatureInformationList
                        .Any(x => !x.IsFirmaValida());

            return !contieneVerificheNonValide;
        }
    }
}
