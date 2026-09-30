using System;
using System.Collections.Generic;
using System.Text;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.Statistiche
{
	public partial class QueryStatisticheDatiDinamici
	{
		string m_commandText;
		public string CommandText
		{
			get { return m_commandText; }
		}


		List<KeyValuePair<string, object>> m_parameters = new List<KeyValuePair<string, object>>();

		public List<KeyValuePair<string, object>> Parameters
		{
			get { return m_parameters; }
		}



		internal QueryStatisticheDatiDinamici(string commendText, List<KeyValuePair<string, object>> parameters)
		{
			m_commandText = commendText;
			m_parameters = parameters;
		}
	}
}
