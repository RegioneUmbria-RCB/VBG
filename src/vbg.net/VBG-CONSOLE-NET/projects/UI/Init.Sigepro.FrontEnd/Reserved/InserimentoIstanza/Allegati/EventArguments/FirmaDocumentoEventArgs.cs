namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Allegati.EventArguments
{
	using System;

	public class FirmaDocumentoEventArgs : EventArgs
	{
		public int CodiceOggetto { get; private set; }

		public FirmaDocumentoEventArgs(int codiceOggetto)
		{
			this.CodiceOggetto = codiceOggetto;
		}
	}
}