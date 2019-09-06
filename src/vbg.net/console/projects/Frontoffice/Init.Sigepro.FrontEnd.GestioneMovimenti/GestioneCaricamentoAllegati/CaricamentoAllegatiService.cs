using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneCaricamentoAllegati
{
    public class CaricamentoAllegatiService
    {
        public class EsitoCaricamentoAllegato
        {
            public readonly bool FirmatoDigitalmente;
            public readonly int CodiceOggetto;
            public readonly BinaryFile FileCaricato;

            public EsitoCaricamentoAllegato(bool firmatoDigitalmente, int codiceOggetto, BinaryFile fileCaricato)
            {
                this.CodiceOggetto = codiceOggetto;
                this.FirmatoDigitalmente = firmatoDigitalmente;
                this.FileCaricato = fileCaricato;
            }
        }

        IVerificaFirmaDigitaleService _verificaFirmaDigitaleService;
        IOggettiService _oggettiService;

        public CaricamentoAllegatiService(IVerificaFirmaDigitaleService verificaFirmaDigitaleService, IOggettiService oggettiService)
        {
            this._verificaFirmaDigitaleService = verificaFirmaDigitaleService;
            this._oggettiService = oggettiService;
        }

        public EsitoCaricamentoAllegato Carica(BinaryFile file)
        {
            var esitoValidazione = _verificaFirmaDigitaleService.VerificaFirmaDigitale(file);

            var firmatoDigitalmente = new FirmaValidaSpecification().IsSatisfiedBy(esitoValidazione);

            var codiceOggetto = this._oggettiService.InserisciOggetto(file.FileName, file.MimeType, file.FileContent);

            return new EsitoCaricamentoAllegato(firmatoDigitalmente, codiceOggetto, file);
        }
    }
}
