using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale.VerificaFirmaRest;

namespace Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale
{
    public class VerificaFirmaDigitaleRestService : IVerificaFirmaDigitaleService, IFirmaDigitaleMetadataService
    {
        private readonly IOggettiService _oggettiService;
        private readonly VerificaFirmaDigitaleRestClient _verificaFirmaDigitaleRestClient;

        public VerificaFirmaDigitaleRestService(IOggettiService oggettiService, VerificaFirmaDigitaleRestClient verificaFirmaDigitaleRestClient)
        {
            this._oggettiService = oggettiService;
            this._verificaFirmaDigitaleRestClient = verificaFirmaDigitaleRestClient;
        }

        public BinaryFile GetFileInChiaro(BinaryFile fileFirmato)
        {
            var result = this._verificaFirmaDigitaleRestClient.ScaricaFileNonFirmato(fileFirmato);
            return result;
        }


        public EsitoVerificaFirmaDigitale VerificaFirmaDigitale(BinaryFile file)
        {
            var result = this._verificaFirmaDigitaleRestClient.VerificaFirmaDigitale(file);

            return new EsitoVerificaFirmaDigitale(result);
        }

        public EsitoVerificaFirmaDigitale VerificaFirmaDigitale(int codiceOggetto)
        {
            var oggetto = this._oggettiService.GetById(codiceOggetto);

            return this.VerificaFirmaDigitale(oggetto);
        }

    }
}
