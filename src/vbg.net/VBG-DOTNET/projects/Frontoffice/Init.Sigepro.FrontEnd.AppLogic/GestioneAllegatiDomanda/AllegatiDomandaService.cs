using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda
{
    public class AllegatiDomandaService
    {
        public class FileAllegatoADomandaResult : SalvataggioAllegatoResult
        {
            public int IdAllegato { get; private set; }

            public FileAllegatoADomandaResult(int idAllegato, SalvataggioAllegatoResult esitoSalvataggio) : base(esitoSalvataggio.CodiceOggetto, esitoSalvataggio.NomeFile, esitoSalvataggio.FirmatoDigitalmente)
            {
                this.IdAllegato = idAllegato;
            }
        }

        private readonly IAllegatiDomandaFoRepository _repository;
        private readonly ISalvataggioDomandaStrategy _salvataggioStrategy;

        public AllegatiDomandaService(ISalvataggioDomandaStrategy salvataggioStrategy, IAllegatiDomandaFoRepository repository)
        {
            this._salvataggioStrategy = salvataggioStrategy;
            this._repository = repository;
        }

        public FileAllegatoADomandaResult AllegaADomanda(int idDomanda, BinaryFile file, bool richiedeFirmaDigitale)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            var result = this.AllegaADomandaSenzaSalvare(domanda, file, richiedeFirmaDigitale);

            this._salvataggioStrategy.Salva(domanda);

            return result;
        }

        internal FileAllegatoADomandaResult AllegaADomandaSenzaSalvare(DomandaOnline domanda, BinaryFile file, bool richiedeFirmaDigitale)
        {
            var result = this._repository.SalvaAllegato(domanda.DataKey.IdPresentazione, file, richiedeFirmaDigitale);

            var idAllegato = domanda.WriteInterface.Allegati.Allega(result.CodiceOggetto, result.NomeFile, string.Empty, result.FirmatoDigitalmente, string.Empty);

            return new FileAllegatoADomandaResult(idAllegato, result);
        }

        internal FileAllegatoADomandaResult AllegaADomandaSenzaSalvare(DomandaOnline domanda, int codiceOggetto)
        {
            var result = this._repository.SalvaAllegato(domanda.DataKey.IdPresentazione, codiceOggetto);

            var idAllegato = domanda.WriteInterface.Allegati.Allega(result.CodiceOggetto, result.NomeFile, string.Empty, result.FirmatoDigitalmente, string.Empty);

            return new FileAllegatoADomandaResult(idAllegato, result);
        }

        public void RimuoviDaDomanda(int idDomanda, int codiceOggetto)
        {
            this._repository.EliminaAllegato(idDomanda, codiceOggetto);
        }

        public string GetChecksumOggetto(int codiceOggetto)
        {
            return this._repository.LeggiChecksumAllegato(codiceOggetto);
        }
    }
}
