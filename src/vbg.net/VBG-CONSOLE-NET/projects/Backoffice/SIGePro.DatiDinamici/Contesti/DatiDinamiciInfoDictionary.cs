using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Contesti
{


	public partial class DatiDinamiciInfoDictionaryItem
	{
		public string NomePropertyClasse { get; protected set; }
		public ContestoModelloEnum TipoContestoEnum { get; protected set; }
		public string Descrizione { get; protected set; }


		public DatiDinamiciInfoDictionaryItem(string nomePropertyClasse, ContestoModelloEnum tipoContesto, string descrizione)
		{
			NomePropertyClasse = nomePropertyClasse;
			TipoContestoEnum = tipoContesto;
			Descrizione = descrizione;
		}
	}


	/// <summary>
	/// Rappresenta le informazioni utilizzate per creare e valorizzare le classi generate a runtime
	/// </summary>
	public static class DatiDinamiciInfoDictionary
	{
		static Dictionary<ContestoModelloEnum, DatiDinamiciInfoDictionaryItem> m_items;

		public static Dictionary<ContestoModelloEnum, DatiDinamiciInfoDictionaryItem> Items
		{
			get { if (m_items == null) Initialize(); return m_items; }
		}

		private static void Initialize()
		{
			m_items = new Dictionary<ContestoModelloEnum, DatiDinamiciInfoDictionaryItem>();

			m_items.Add(ContestoModelloEnum.Istanza, new DatiDinamiciInfoDictionaryItem("IstanzaCorrente"	, ContestoModelloEnum.Istanza, "Istanza"));
			m_items.Add(ContestoModelloEnum.Anagrafe, new DatiDinamiciInfoDictionaryItem("AnagraficaCorrente", ContestoModelloEnum.Anagrafe, "Anagrafe"));
			m_items.Add(ContestoModelloEnum.Attivita, new DatiDinamiciInfoDictionaryItem("AttivitaCorrente"	, ContestoModelloEnum.Attivita, "Attività"));
		}
	}
}
