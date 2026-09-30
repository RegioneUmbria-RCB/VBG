// -----------------------------------------------------------------------
// <copyright file="IGeneratoreRiepilogoModelloDinamico.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

	public class RisultatoGenerazioneRiepilogoDomanda
	{
		public int CodiceOggetto { get; private set; }
		public string Md5 { get; private set; }
		public BinaryFile File { get; private set; }


		public RisultatoGenerazioneRiepilogoDomanda(int codiceOggetto, BinaryFile file, string md5)
		{
			this.CodiceOggetto = codiceOggetto;
			this.File = file;
			this.Md5 = md5;
		}
	}


	public interface IGeneratoreRiepilogoModelloDinamico
	{
        //RisultatoGenerazioneRiepilogoDomanda GeneraRiepilogoEAllega( int idModello, string nomeRiepilogo, int indiceMolteplicita);
        BinaryFile GeneraRiepilogo(int idModello, string nomeRiepilogo, int indiceMolteplicita);

    }
}
