using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Reflection;

namespace Init.SIGePro.DatiDinamici.WebControls
{
	/// <summary>
	/// Rappresenta un tipo di controllo utilizzabile nella gestione dei dati dinamici di SIGepro
	/// </summary>
	public class ControlliDatiDinamiciDictionaryItem
	{


		string m_tipoCampo;
		string m_descrizione;
		bool m_visibileInDesigner;
		Type m_tipoElemento;

		public string TipoCampo
		{
			get
			{
				return m_tipoCampo;
			}
			set
			{
				m_tipoCampo = value;
			}

		}

		public string Descrizione
		{
			get
			{
				return m_descrizione;
			}
			set
			{
				m_descrizione = value;
			}
		}

		public bool VisibileInDesigner
		{
			get
			{
				return m_visibileInDesigner;
			}
			set
			{
				m_visibileInDesigner = value;
			}
		}

		public Type TipoElemento
		{
			get
			{
				return m_tipoElemento;
			}
			set
			{
				m_tipoElemento = value;
			}
		}

		public string GetDescrizioneControllo()
		{
			var mi = TipoElemento.GetMethod("HelpControllo", BindingFlags.Static | BindingFlags.Public);

			if (mi == null)
				return string.Empty;

			var value = (string)mi.Invoke(null, null);

			return value ?? String.Empty;
		}

		public Dictionary<string, EditablePropertyDetails> GetProprietaEditabili()
		{
			Dictionary<string, EditablePropertyDetails> ret = new Dictionary<string, EditablePropertyDetails>();

			ProprietaDesigner[] proprieta = null;
			MethodInfo mi = TipoElemento.GetMethod("GetProprietaDesigner", BindingFlags.Static | BindingFlags.Public);

			proprieta = (ProprietaDesigner[])mi.Invoke(null, null);

			foreach (ProprietaDesigner prop in proprieta)
			{
				if (!prop.VisibileInDesigner)
					continue;

				string id = prop.NomeProprieta;
				string description = prop.Descrizione;
				TipoControlloEditEnum tipoControllo = prop.TipoControlloEditing;
				List<KeyValuePair<string, string>> valoriLista = prop.ValoriLista;
				string valoreDefault = prop.ValoreDefault;

				if (String.IsNullOrEmpty(description))
					description = id;

				ret.Add(id, new EditablePropertyDetails(id, description, tipoControllo, valoriLista, valoreDefault));
			}

			return ret;
		}

		public ControlliDatiDinamiciDictionaryItem(string tipoCampo, string descrizione, bool visibileInDesigner, Type tipoelemento)
		{
			this.TipoCampo = tipoCampo;
			this.Descrizione = descrizione;
			this.VisibileInDesigner = visibileInDesigner;
			this.TipoElemento = tipoelemento;
		}
	}
}
