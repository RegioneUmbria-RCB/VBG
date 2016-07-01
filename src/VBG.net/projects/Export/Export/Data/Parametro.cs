using System;

namespace Export.Data
{
	/// <summary>
	/// Descrizione di riepilogo per Parametri.
	/// </summary>
	public class Parametro
	{
		public Parametro()
		{
		}

		string nome;
		public string NOME
		{
			get { return nome; }
			set { nome = value; }
		}

		string descrizione;
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

		string valore;
		public string VALORE
		{
			get { return valore; }
			set { valore = value; }
		}
	}
}
