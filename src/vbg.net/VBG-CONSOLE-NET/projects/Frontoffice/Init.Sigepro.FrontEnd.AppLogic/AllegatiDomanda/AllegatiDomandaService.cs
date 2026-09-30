using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.AllegatiDomanda
{
	public class AllegatiDomandaService
	{
		public class FileAllegatoADomandaResult : SalvataggioAllegatoResult
		{
			public int IdAllegato { get; private set; }

			public FileAllegatoADomandaResult(int idAllegato, SalvataggioAllegatoResult esitoSalvataggio): base( esitoSalvataggio.CodiceOggetto, esitoSalvataggio.NomeFile, esitoSalvataggio.FirmatoDigitalmente )
			{
				this.IdAllegato = idAllegato;
			}
		}

		IAllegatiDomandaFoRepository _repository;
		ISalvataggioDomandaStrategy _salvataggioStrategy;

		public AllegatiDomandaService( ISalvataggioDomandaStrategy salvataggioStrategy, IAllegatiDomandaFoRepository repository)
		{
			this._salvataggioStrategy = salvataggioStrategy;
			this._repository = repository;
		}

		public FileAllegatoADomandaResult AllegaADomanda(int idDomanda, BinaryFile file, bool richiedeFirmaDigitale)
		{
			var domanda = this._salvataggioStrategy.GetById(idDomanda);

			var result = AllegaADomandaSenzaSalvare(domanda, file, richiedeFirmaDigitale);

			this._salvataggioStrategy.Salva(domanda);

			return  result;
		}

		internal FileAllegatoADomandaResult AllegaADomandaSenzaSalvare(DomandaOnline domanda , BinaryFile file, bool richiedeFirmaDigitale)
		{
			var result = this._repository.SalvaAllegato(domanda.DataKey.IdPresentazione, file, richiedeFirmaDigitale);

			var idAllegato = domanda.WriteInterface.Allegati.Allega(result.CodiceOggetto, result.NomeFile, String.Empty, result.FirmatoDigitalmente, string.Empty);

			return new FileAllegatoADomandaResult(idAllegato, result);
		}

		public void RimuoviDaDomanda(int idDomanda, int codiceOggetto)
		{
			this._repository.EliminaAllegato(idDomanda, codiceOggetto);
		}
	}
}
