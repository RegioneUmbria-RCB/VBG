using System;
using System.Collections.Generic;
using System.Xml.Serialization;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;
using System.IO;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione
{
	[Serializable]
	[XmlRoot(ElementName = "MenuNavigazione")]
	public class MenuNavigazioneFlyweight
	{
		private static class Constants
		{
			public const string SegnapostoAliasComune = "{alias}";
		}

		public string TitoloPagina { get; set; }
		public string DescrizionePagina { get; set; }
		public List<ElementoMenuNavigazione> VociMenu { get; set; }

		public MenuNavigazioneFlyweight()
		{
			VociMenu = new List<ElementoMenuNavigazione>();
		}

		public static MenuNavigazioneFlyweight LoadFrom(string path, string aliasComune)
		{
			return LoadFrom(File.ReadAllBytes(path),aliasComune);
		}

		public static MenuNavigazioneFlyweight LoadFrom(byte[] fileContent, string aliasComune)
		{
			var menu = fileContent.DeserializeXML<MenuNavigazioneFlyweight>();

			foreach (var voce in menu.VociMenu)
			{
				voce.Url = voce.Url.Replace(Constants.SegnapostoAliasComune, aliasComune); 
			}

			return menu;
		}
	}

	[Serializable]
	public class ElementoMenuNavigazione
	{
		public string Descrizione { get; set; }
		public string DescrizioneEstesa { get; set; }
		public string Url { get; set; }
		public string Target { get; set; }
	}

}
