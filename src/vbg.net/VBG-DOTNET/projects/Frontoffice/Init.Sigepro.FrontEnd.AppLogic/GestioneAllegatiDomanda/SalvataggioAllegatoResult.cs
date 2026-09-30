using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda
{
    public class SalvataggioAllegatoResult
    {
        public readonly int CodiceOggetto = -1;
        public readonly string NomeFile = string.Empty;
        public readonly bool FirmatoDigitalmente = false;

        public SalvataggioAllegatoResult(int codiceOggetto, string nomeFile, bool firmatoDigitalmente)
        {
            this.CodiceOggetto = codiceOggetto;
            this.NomeFile = nomeFile;
            this.FirmatoDigitalmente = firmatoDigitalmente;
        }
    }
}
