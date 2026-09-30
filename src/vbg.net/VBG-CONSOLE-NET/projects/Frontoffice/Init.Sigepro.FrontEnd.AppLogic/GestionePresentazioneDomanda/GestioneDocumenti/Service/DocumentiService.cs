using Init.Sigepro.FrontEnd.AppLogic.AllegatiDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Utils;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti.Service
{
	public class DocumentiService
	{
		ISalvataggioDomandaStrategy _salvataggioStrategy;
		IAllegatiDomandaFoRepository _allegatiRepository;

		public DocumentiService(ISalvataggioDomandaStrategy salvataggioStrategy, IAllegatiDomandaFoRepository allegatiRepository)
		{
			this._salvataggioStrategy = salvataggioStrategy;
			this._allegatiRepository  = allegatiRepository;
		}

		public void SalvaEImpostaMd5(int idDomanda, int idDocumento, BinaryFile file)
		{
			var domanda = _salvataggioStrategy.GetById(idDomanda);

			var esitoSalvataggio = _allegatiRepository.SalvaAllegato( idDomanda , file , false );
			var md5 = new Hasher().ComputeHash(file.FileContent);

			domanda.WriteInterface.Documenti.AllegaFileADocumento(idDocumento, esitoSalvataggio.CodiceOggetto, esitoSalvataggio.NomeFile, esitoSalvataggio.FirmatoDigitalmente, md5);

			_salvataggioStrategy.Salva( domanda );
		}


	}
}
