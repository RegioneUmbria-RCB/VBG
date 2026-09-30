using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
using VBG.BlazorComponentsLibrary.DesignComuni.Services.GestioneOggetti;

namespace Init.Sigepro.FrontEnd.CoreServices.Shared
{
    public class SharedComponentsFileService : ISharedComponentsFileService
    {
        private readonly IOggettiService _oggettiService;
        private readonly IConfigurazione<ParametriAllegati> _parametriAllegati;
        private readonly IVerificaFirmaDigitaleService _verificaFirmaService;

        public SharedComponentsFileService(IOggettiService oggettiService, IConfigurazione<ParametriAllegati> parametriAllegati, IVerificaFirmaDigitaleService verificaFirmaService)
        {
            this._oggettiService = oggettiService;
            this._parametriAllegati = parametriAllegati;
            this._verificaFirmaService = verificaFirmaService;
        }

        public long DimensioneMassimaDocumento => this._parametriAllegati.Parametri.DimensioneMassimaAllegato;

        public FileReference DownloadById(int codiceOggetto)
        {
            var file = this._oggettiService.GetById(codiceOggetto);

            return new FileReference
            {
                ContentType = file.MimeType,
                FileContent = file.FileContent,
                FileName = file.FileName
            };
        }

        public string GetNomeFile(int codiceOggetto) => this._oggettiService.GetNomeFile(codiceOggetto);

        public int Save(FileReference file) => this._oggettiService.InserisciOggetto(BinaryFile.FromFileData(file.FileName, file.ContentType, file.FileContent));

        public bool VerificaFirmaDigitale(FileReference file)
        {
            var esito = this._verificaFirmaService.VerificaFirmaDigitale(BinaryFile.FromFileData(file.FileName, file.ContentType, file.FileContent));

            return esito.Stato == StatoVerificaFirma.FirmaValida;
        }
    }
}
