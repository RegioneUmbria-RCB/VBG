using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.FileUpload;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

namespace AreaRiservataCore.Pages.Movimenti
{
    internal class MovimentiFileUploadService : IDatiDinamiciFileUploadService
    {
        private readonly IOggettiService _oggettiService;

        public MovimentiFileUploadService(IOggettiService oggettiService)
        {
            this._oggettiService = oggettiService;
        }
        public DatiFileCaricato GetDatiFileCaricato(int codiceOggetto)
        {
            var file = this._oggettiService.GetById(codiceOggetto);

            if (file == null)
                throw new Exception("L'oggetto identificato dal codiceoggetto " + codiceOggetto + " non è stato trovato");

            return new DatiFileCaricato
            {
                CodiceOggetto = codiceOggetto,
                FileName = file.FileName,
                SizeInBytes = file.FileContent.Length,
                MimeType = file.MimeType
            };
        }

        public DatiFileCaricato SalvaAllegato(BinaryFile file, bool verificaFirmaDigitale)
        {
            var codiceOggetto = this._oggettiService.InserisciOggetto(file);

            return new DatiFileCaricato
            {
                CodiceOggetto = codiceOggetto,
                FileName = file.FileName,
                SizeInBytes = file.Size,
                MimeType = file.MimeType
            };

        }
    }
}