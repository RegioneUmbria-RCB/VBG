using System;
using System.Collections.Generic;
using System.Text;

namespace SIGePro.WebControls.DatiDinamici
{
	/*

	/// <summary>
	/// Attributo che serve a marcare le proprietà modificabili all'interno del designer dei dati dinamici
	/// </summary>
	public class VisibleInDesignerAttribute : Attribute
	{
		private TipoControlloEditEnum m_tipoControllo;
		private string m_description = String.Empty;
		string m_listaValori = "";

		public string Description
		{
			get { return m_description; }
			set { m_description = value; }
		}

		public TipoControlloEditEnum TipoControllo
		{
			get { return m_tipoControllo; }
		}

		public List<KeyValuePair<string, string>> ValoriLista
		{
			get
			{
				List<KeyValuePair<string, string>> ret = new List<KeyValuePair<string, string>>();

				string[] elementi = m_listaValori.Split(',');

				for (int i = 0; i < elementi.Length; i++)
				{
					string[] keyVal = elementi[i].Trim().Split('=');
					string key = "";
					string val = "";

					val = keyVal[0];

					if (keyVal.Length == 1)
						key = val;
					else
						key = keyVal[1];


					ret.Add(new KeyValuePair<string, string>(key, val));
				}

				return ret;
			}
		}

		public VisibleInDesignerAttribute()
		{

		}

		public VisibleInDesignerAttribute(string descrizione) : this(descrizione, TipoControlloEditEnum.TextBox, "") { }

		public VisibleInDesignerAttribute(string descrizione, TipoControlloEditEnum tipoControllo, string listaValori)
		{
			Description = descrizione;
			m_tipoControllo = tipoControllo;
			m_listaValori = listaValori;
		}
	}*/
}
