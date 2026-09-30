namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Allegati.EventArguments
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Web;

	public class CompilaDocumentoEventArgs : BaseGrigliaDocumentiEventArgs
	{
		public CompilaDocumentoEventArgs(int idAllegato)
			: base(idAllegato)
		{
		}
	}
}