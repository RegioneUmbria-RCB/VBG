using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

namespace Init.Sigepro.FrontEnd.AppLogic.AllegatiDomanda
{
	public class SalvataggioAllegatoResult
	{
		public readonly int CodiceOggetto = -1;
		public readonly string NomeFile = String.Empty;
		public readonly bool FirmatoDigitalmente = false;

		public SalvataggioAllegatoResult(int codiceOggetto, string nomeFile, bool firmatoDigitalmente)
		{
			this.CodiceOggetto = codiceOggetto;
			this.NomeFile = nomeFile;
			this.FirmatoDigitalmente = firmatoDigitalmente;
		}
	}

	public interface IAllegatiDomandaFoRepository
	{
		void EliminaAllegato(int idDomanda, int idAllegato);
		BinaryFile LeggiAllegato(int idDomanda, int idAllegato);
		SalvataggioAllegatoResult SalvaAllegato(int idDomanda, BinaryFile file, bool richiedeFirmaDigitale);
		SalvataggioAllegatoResult SalvaAllegatoConfrontaHash(int idDomanda, BinaryFile file, string hashConfronto);
	}
}
