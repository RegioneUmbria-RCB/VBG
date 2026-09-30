namespace VBG.Backend.Protocollo.AppLogic.Shared.VerificaFirmaDigitale
{
    public static class WsSignatureInformationExtension
    {
        private static class Constants
        {
            public const string FinalConclusionQES = "QES";
            public const string FinalConclusionAdES = "AdES";
        }

        public static bool IsFirmaValida(this wsSignatureInformation si)
        {
            // Potrebbe essere anche 
            return si.finalConclusion == Constants.FinalConclusionQES || si.finalConclusion == Constants.FinalConclusionAdES;

            //return si.finalConclusion == Constants.FinalConclusionQES;
        }

        public static bool IsCertificatoRevocato(this wsSignatureInformation si)
        {
            return si.certPathRevocationAnalysis.IsRevoked();
        }
    }
}
