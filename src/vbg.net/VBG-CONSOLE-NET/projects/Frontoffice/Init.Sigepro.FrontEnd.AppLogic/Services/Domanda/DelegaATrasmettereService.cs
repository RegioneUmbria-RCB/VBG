using System;
using Init.Sigepro.FrontEnd.AppLogic.AllegatiDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.Services.Domanda
{
	public class DelegaATrasmettereService
	{
		ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
		IAllegatiDomandaFoRepository _allegatiDomandaFoRepository;


		public DelegaATrasmettereService(ISalvataggioDomandaStrategy salvataggioDomandaStrategy, IAllegatiDomandaFoRepository allegatiDomandaFoRepository)
		{
			this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
			this._allegatiDomandaFoRepository = allegatiDomandaFoRepository;
		}

		public void EliminaDelegaATrasmettere(int idDomanda)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

			domanda.WriteInterface.DelegaATrasmettere.EliminaDelegaATrasmettere();

			_salvataggioDomandaStrategy.Salva(domanda);
		}

		public void SalvaAllegato(int idDomanda, BinaryFile file, bool verificaFirma)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

			var alias = domanda.ReadInterface.AltriDati.AliasComune;

			var fileSalvato = _allegatiDomandaFoRepository.SalvaAllegato( idDomanda, file, verificaFirma);

			domanda.WriteInterface.DelegaATrasmettere.SalvaAllegato(fileSalvato.CodiceOggetto, fileSalvato.NomeFile, fileSalvato.FirmatoDigitalmente);

			_salvataggioDomandaStrategy.Salva(domanda);
		}

        public void SalvaDocumentoIdentita(int idDomanda, BinaryFile file, bool verificaFirma = false)
        {
            var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

            var alias = domanda.ReadInterface.AltriDati.AliasComune;

            var fileSalvato = _allegatiDomandaFoRepository.SalvaAllegato(idDomanda, file, verificaFirma);

            domanda.WriteInterface.DelegaATrasmettere.SalvaDocumentoIdentita(fileSalvato.CodiceOggetto, fileSalvato.NomeFile, fileSalvato.FirmatoDigitalmente);

            _salvataggioDomandaStrategy.Salva(domanda);
        }

        public void EliminaDocumentoIdentita(int idDomanda)
        {
            var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

            domanda.WriteInterface.DelegaATrasmettere.EliminaDocumentoIdentita();

            _salvataggioDomandaStrategy.Salva(domanda);
        }
    }
}
