using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.FileUpload
{

    public class DatiDinamiciDomandaFileUploadService : IDatiDinamiciFileUploadService
    {
        private readonly int _idDomanda;
        private readonly IAllegatiDomandaFoRepository _allegatiDomandaFoRepository;

        public DatiDinamiciDomandaFileUploadService(int idDomanda, IAllegatiDomandaFoRepository allegatiDomandaFoRepository)
        {
            this._idDomanda = idDomanda;
            this._allegatiDomandaFoRepository = allegatiDomandaFoRepository;
        }

        public DatiFileCaricato GetDatiFileCaricato(int codiceOggetto)
        {
            var file = this._allegatiDomandaFoRepository.LeggiAllegato(this._idDomanda, codiceOggetto);

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
            var salvataggioResult = this._allegatiDomandaFoRepository.SalvaAllegato(this._idDomanda, file, verificaFirmaDigitale);

            return new DatiFileCaricato
            {
                CodiceOggetto = salvataggioResult.CodiceOggetto,
                FileName = file.FileName,
                SizeInBytes = file.Size,
                MimeType = file.MimeType
            };
        }
    }
}
