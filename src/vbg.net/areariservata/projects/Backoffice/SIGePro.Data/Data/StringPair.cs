using System;
using System.Collections;

namespace Init.SIGePro.Data
{
	/// <summary>
	/// Descrizione di riepilogo per StringPair.
	/// </summary>
	public class StringPair
	{
		private string m_first;
		private string m_second;

		public string First
		{
			get { return m_first; }
			set { m_first = value; }
		}

		public string Second
		{
			get { return m_second; }
			set { m_second = value; }
		}


		public StringPair( string first , string second )
		{
			m_first = first;
			m_second = second;
		}
	}
}
