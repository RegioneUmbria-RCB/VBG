using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.Commands
{
	public class FirmaDigitalmenteRiepilogo : Command
	{
		public readonly string IdComune;
		public readonly int IdMovimento;
		public readonly int CodiceOggetto;
		public readonly string NomeFile;

		public FirmaDigitalmenteRiepilogo(string idComune, int idMovimento, int codiceOggetto, string nomeFile)
		{
			this.IdComune = idComune;
			this.IdMovimento = idMovimento;
			this.CodiceOggetto = codiceOggetto;
			this.NomeFile = nomeFile;
		}
	}
}
