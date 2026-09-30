using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	/// <summary>
	/// Descrizione di riepilogo per ProtocolloAnagrafe.
	/// </summary>
	public class RetStatoPratica 
	{
		private string _CodStatoPratica = "";
		public string CodStatoPratica
		{
			get { return _CodStatoPratica; }
			set { _CodStatoPratica = value; }
		}

		private string _StatoIter = "";
		public string StatoIter
		{
			get { return _StatoIter; }
			set { _StatoIter = value; }
		}

		private string _StatoPratica = "";
		public string StatoPratica
		{
			get { return _StatoPratica; }
			set { _StatoPratica = value; }
		}

		private int _ModificaIstanza = 1;
		public int ModificaIstanza
		{
			get { return _ModificaIstanza; }
			set { _ModificaIstanza = value; }
		}
	}
}