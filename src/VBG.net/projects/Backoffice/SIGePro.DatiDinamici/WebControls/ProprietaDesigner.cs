using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.WebControls
{
	public enum TipoControlloEditEnum
	{
		TextBox,
		ListBox
	}

	public partial class ProprietaDesigner
	{
		private string m_descrizione;
		private TipoControlloEditEnum m_tipoControlloEditing;
		private string m_nomeProprieta;
		string m_listaValori = "";
		string m_valoreDefault = "";
		private bool m_visibileInDesigner;

		public bool VisibileInDesigner
		{
			get { return m_visibileInDesigner; }
			set { m_visibileInDesigner = value; }
		}


		public string ValoreDefault
		{
			get { return m_valoreDefault; }
			set { m_valoreDefault = value; }
		}

		public string NomeProprieta
		{
			get { return m_nomeProprieta; }
			set { m_nomeProprieta = value; }
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

		public TipoControlloEditEnum TipoControlloEditing
		{
			get { return m_tipoControlloEditing; }
			set { m_tipoControlloEditing = value; }
		}

		public string Descrizione
		{
			get { return m_descrizione; }
			set { m_descrizione = value; }
		}


		public ProprietaDesigner(string nomeProprieta, string descrizione, string valoreDefault) : this(nomeProprieta, descrizione, valoreDefault, true) { }
		public ProprietaDesigner(string nomeProprieta, string descrizione, string valoreDefault, bool visibileInDesigner) : this(nomeProprieta, descrizione, TipoControlloEditEnum.TextBox, "", valoreDefault, visibileInDesigner) { }

		public ProprietaDesigner(string nomeProprieta, string descrizione, TipoControlloEditEnum tipoControllo, string listaValori, string valoreDefault) : this(nomeProprieta, descrizione, tipoControllo, listaValori, valoreDefault, true) { }
		public ProprietaDesigner(string nomeProprieta, string descrizione, TipoControlloEditEnum tipoControllo, string listaValori, string valoreDefault, bool visibileInDesigner)
		{
			m_nomeProprieta = nomeProprieta;
			m_descrizione = descrizione;
			m_tipoControlloEditing = tipoControllo;
			m_listaValori = listaValori;
			m_valoreDefault = valoreDefault;
			m_visibileInDesigner = visibileInDesigner;
		}

	}
}
