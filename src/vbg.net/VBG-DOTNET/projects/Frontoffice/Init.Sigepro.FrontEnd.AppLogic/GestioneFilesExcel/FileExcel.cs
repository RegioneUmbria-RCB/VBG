using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneFilesExcel
{
    class FileExcel
    {
        private DocumentoDomanda.AllegatoUtente _allegatoUtente;
        private IOggettiService _oggettiService;
        private IFirmaDigitaleMetadataService _firmaDigitaleMetadataService;

        public FileExcel(DocumentoDomanda.AllegatoUtente allegatoUtente, IOggettiService oggettiService, IFirmaDigitaleMetadataService firmaDigitaleMetadataService)
        {
            this._allegatoUtente = allegatoUtente;
            this._oggettiService = oggettiService;
            this._firmaDigitaleMetadataService = firmaDigitaleMetadataService;
        }

        public BinaryFile GetFile()
        {
            var file = this._oggettiService.GetById(this._allegatoUtente.CodiceOggetto);

            if (this._allegatoUtente.FirmatoDigitalmente)
            {
                return this._firmaDigitaleMetadataService.GetFileInChiaro(file);
            }

            return file;
        }
    }
}
