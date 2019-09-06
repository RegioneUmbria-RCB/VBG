namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Allegati.EventArguments
{
	using System;

	public class BaseGrigliaDocumentiEventArgs : EventArgs
	{
		public int IdAllegato { get; private set; }

		public BaseGrigliaDocumentiEventArgs(int idAllegato)
		{
			this.IdAllegato = idAllegato;
		}
	}
}