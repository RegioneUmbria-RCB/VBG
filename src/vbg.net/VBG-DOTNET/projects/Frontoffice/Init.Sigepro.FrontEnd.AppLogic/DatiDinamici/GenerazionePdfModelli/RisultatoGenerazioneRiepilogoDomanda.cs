// -----------------------------------------------------------------------
// <copyright file="IGeneratoreRiepilogoModelloDinamico.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli
{
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
}
