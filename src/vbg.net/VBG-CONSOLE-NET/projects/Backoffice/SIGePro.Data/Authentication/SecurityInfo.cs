using System;

namespace Init.SIGePro.Authentication
{
	/// <summary>
	/// Descrizione di riepilogo per SecurityInfo.
	/// </summary>
	//Non utilizzata
	public class SecurityInfo
	{
		public SecurityInfo()
		{
		}


		/// <summary>
		/// Codice istat del comune installato
		/// </summary>
		private string codiceistat;
		public string CodiceIstat
		{
			get { return codiceistat; }
			set { codiceistat = value; }
		}
	}
}
