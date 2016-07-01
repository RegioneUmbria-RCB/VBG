using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.WebControls
{
	/// <summary>
	/// Rappresenta il dizionario di tutti i tipi controllo utilizzabili nei campi dinamici
	/// </summary>
	public static class ControlliDatiDinamiciDictionary
	{
		static Dictionary<TipoControlloEnum, ControlliDatiDinamiciDictionaryItem> m_items;

		public static Dictionary<TipoControlloEnum, ControlliDatiDinamiciDictionaryItem> Items
		{
			get
			{
				if (m_items == null)
					Initialize();

				return m_items;
			}
		}

		/// <summary>
		/// Rappresenta i tipi di elementi che possono essere utilizzati durante la fase di design di un campo dinamico
		/// </summary>
		public static Dictionary<TipoControlloEnum, ControlliDatiDinamiciDictionaryItem> DesignTimeItems
		{
			get
			{
				if (m_items == null)
					Initialize();

				Dictionary<TipoControlloEnum, ControlliDatiDinamiciDictionaryItem> ret = new Dictionary<TipoControlloEnum, ControlliDatiDinamiciDictionaryItem>();

				foreach (TipoControlloEnum enm in m_items.Keys)
				{
					if (m_items[enm].VisibileInDesigner)
						ret[enm] = m_items[enm];
				}

				return ret;
			}
		}

		private static void Initialize()
		{
			m_items = new Dictionary<TipoControlloEnum, ControlliDatiDinamiciDictionaryItem>();

			m_items.Add(TipoControlloEnum.Checkbox, new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.Checkbox.ToString(), "Casella di spunta", true, typeof(DatiDinamiciCheckBox)));
			m_items.Add(TipoControlloEnum.Data, new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.Data.ToString(), "Data", true, typeof(DatiDinamiciDateTextBox)));
			m_items.Add(TipoControlloEnum.Label, new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.Label.ToString(), "Etichetta o descrizione estesa", false, typeof(DatiDinamiciLabel)));
			m_items.Add(TipoControlloEnum.Lista, new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.Lista.ToString(), "Lista valori", true, typeof(DatiDinamiciListBox)));
			m_items.Add(TipoControlloEnum.ListaSIGePro, new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.ListaSIGePro.ToString(), "Lista valori da db", true, typeof(DatiDinamiciSigeproListBox)));
			m_items.Add(TipoControlloEnum.MultiLista, new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.MultiLista.ToString(), "Multi lista valori", true, typeof(DatiDinamiciMultiListBox)));
			m_items.Add(TipoControlloEnum.NumericoDouble, new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.NumericoDouble.ToString(), "Numerico decimale", true, typeof(DatiDinamiciDoubleTextBox)));
			m_items.Add(TipoControlloEnum.NumericoIntero, new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.NumericoIntero.ToString(), "Numero intero", true, typeof(DatiDinamiciIntTextBox)));
			m_items.Add(TipoControlloEnum.Ricerca, new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.Ricerca.ToString(), "Ricerca nel db", true, typeof(DatiDinamiciSearch2)));
			m_items.Add(TipoControlloEnum.Testo, new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.Testo.ToString(), "Testo", true, typeof(DatiDinamiciTextBox)));
			m_items.Add(TipoControlloEnum.Titolo, new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.Titolo.ToString(), "Titolo", false, typeof(DatiDinamiciTitolo)));
			m_items.Add(TipoControlloEnum.Upload, new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.Upload.ToString(), "Upload", true, typeof(DatiDinamiciUpload)));
			m_items.Add(TipoControlloEnum.Bottone, new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.Bottone.ToString(), "Bottone", true, typeof(DatiDinamiciButton)));
			m_items.Add(TipoControlloEnum.Localizzazione, new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.Localizzazione.ToString(), "Localizzazione", true, typeof(DatiDinamiciLocalizzazioniListBox)));


		}
	}
}
