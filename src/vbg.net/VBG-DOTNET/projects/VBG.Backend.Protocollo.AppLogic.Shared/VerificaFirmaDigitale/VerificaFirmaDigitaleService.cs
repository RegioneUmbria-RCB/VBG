namespace VBG.Backend.Protocollo.AppLogic.Shared.VerificaFirmaDigitale
{
    public class VerificaFirmaDigitaleService
    {
        private readonly FirmaDigitaleServiceCreator _serviceCreator;
        public VerificaFirmaDigitaleService(FirmaDigitaleServiceCreator serviceCreator)
        {
            this._serviceCreator = serviceCreator;
        }

        public bool VerificaFirmaDigitale(byte[] file, string fileName)
        {
            var client = this._serviceCreator.CreateClient();

            var validationResult = this._serviceCreator.Call(ws =>
            {
                return ws.validateDocument(new wsDocument
                {
                    binary = file,
                    name = fileName
                }, null, false);
            });

            return validationResult?.IsFirmaValida() ?? false;



        }
    }
}