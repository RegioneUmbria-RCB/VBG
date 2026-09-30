using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneDelegaATrasmettere
{
    public class DelegaATrasmettereService : IDelegaATrasmettereService
    {
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        private readonly IAllegatiDomandaFoRepository _allegatiDomandaFoRepository;

        public DelegaATrasmettereService(ISalvataggioDomandaStrategy salvataggioDomandaStrategy, IAllegatiDomandaFoRepository allegatiDomandaFoRepository)
        {
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            this._allegatiDomandaFoRepository = allegatiDomandaFoRepository;
        }

        public void EliminaDelegaATrasmettere(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            domanda.WriteInterface.DelegaATrasmettere.EliminaDelegaATrasmettere();

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public void SalvaAllegato(int idDomanda, BinaryFile file, bool verificaFirma)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var alias = domanda.ReadInterface.AltriDati.AliasComune;

            var fileSalvato = this._allegatiDomandaFoRepository.SalvaAllegato(idDomanda, file, verificaFirma);

            domanda.WriteInterface.DelegaATrasmettere.SalvaAllegato(fileSalvato.CodiceOggetto, fileSalvato.NomeFile, fileSalvato.FirmatoDigitalmente);

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public void SalvaAllegato(int idDomanda, int codiceOggetto)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var alias = domanda.ReadInterface.AltriDati.AliasComune;

            var fileSalvato = this._allegatiDomandaFoRepository.SalvaAllegato(idDomanda, codiceOggetto);

            domanda.WriteInterface.DelegaATrasmettere.SalvaAllegato(fileSalvato.CodiceOggetto, fileSalvato.NomeFile, fileSalvato.FirmatoDigitalmente);

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public void SalvaDocumentoIdentita(int idDomanda, BinaryFile file, bool verificaFirma = false)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var alias = domanda.ReadInterface.AltriDati.AliasComune;

            var fileSalvato = this._allegatiDomandaFoRepository.SalvaAllegato(idDomanda, file, verificaFirma);

            domanda.WriteInterface.DelegaATrasmettere.SalvaDocumentoIdentita(fileSalvato.CodiceOggetto, fileSalvato.NomeFile, fileSalvato.FirmatoDigitalmente);

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public void SalvaDocumentoIdentita(int idDomanda, int codiceOggetto)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var alias = domanda.ReadInterface.AltriDati.AliasComune;

            var fileSalvato = this._allegatiDomandaFoRepository.SalvaAllegato(idDomanda, codiceOggetto);

            domanda.WriteInterface.DelegaATrasmettere.SalvaDocumentoIdentita(fileSalvato.CodiceOggetto, fileSalvato.NomeFile, fileSalvato.FirmatoDigitalmente);

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public void EliminaDocumentoIdentita(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            domanda.WriteInterface.DelegaATrasmettere.EliminaDocumentoIdentita();

            this._salvataggioDomandaStrategy.Salva(domanda);
        }
    }
}
