using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Utils;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda
{
    public class DocumentiService
    {
        private readonly ISalvataggioDomandaStrategy _salvataggioStrategy;
        private readonly IAllegatiDomandaFoRepository _allegatiRepository;

        public DocumentiService(ISalvataggioDomandaStrategy salvataggioStrategy, IAllegatiDomandaFoRepository allegatiRepository)
        {
            this._salvataggioStrategy = salvataggioStrategy;
            this._allegatiRepository = allegatiRepository;
        }

        public void SalvaEImpostaMd5(int idDomanda, int idDocumento, BinaryFile file)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            var esitoSalvataggio = this._allegatiRepository.SalvaAllegato(idDomanda, file, false);
            var md5 = new Hasher().ComputeHash(file.FileContent);

            domanda.WriteInterface.Documenti.AllegaFileADocumento(idDocumento, esitoSalvataggio.CodiceOggetto, esitoSalvataggio.NomeFile, esitoSalvataggio.FirmatoDigitalmente, md5);

            this._salvataggioStrategy.Salva(domanda);
        }


    }
}
