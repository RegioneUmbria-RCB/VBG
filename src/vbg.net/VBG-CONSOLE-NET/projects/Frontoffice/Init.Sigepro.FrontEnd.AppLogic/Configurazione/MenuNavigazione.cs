using System;
using System.Linq;
using System.Collections.Generic;
using System.Xml.Serialization;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;
using System.IO;
using System.Text.RegularExpressions;

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
			return LoadFrom(File.ReadAllBytes(path)/*,aliasComune*/);
		}

		public static MenuNavigazioneFlyweight LoadFrom(byte[] fileContent/*, string aliasComune*/)
		{
			var menu = fileContent.DeserializeXML<MenuNavigazioneFlyweight>();

			// foreach (var voce in menu.VociMenu)
			// {
			// 	voce.Url = voce.Url.Replace(Constants.SegnapostoAliasComune, aliasComune); 
			// }

			return menu;
		}

        public IEnumerable<ElementoMenuNavigazione> GetMenuUtente()
        {
            return this.VociMenu.Where(x => x.MenuUtente)
                .Union(
                    this.VociMenu.Where(x => x.Descrizione.ToLowerInvariant() == "esci")
                )
                .Union(
                    this.VociMenu.Where(x => x.Descrizione.ToLowerInvariant() == "modifica password")
                );
        }
	}

	[Serializable]
	public class ElementoMenuNavigazione
	{
        private static class Icons
        {
            public const string OpenFile = "glyphicon-open-file";
            public const string Time = "glyphicon-time";
            public const string OpenFolder = "glyphicon-folder-open";
            public const string Calendar = "glyphicon-calendar";
            public const string Book = "glyphicon-book";
            public const string Lock = "glyphicon-lock";
            public const string Off = "glyphicon-off";
            public const string Phone = "glyphicon-phone";
        }

        private static Dictionary<string, string> _gliphIconsMappings = new Dictionary<string, string>
        {
            {"nuova-segnalazione---domanda", Icons.OpenFile},
            {"nuova-domanda", Icons.OpenFile},
            {"nuova-istanza", Icons.OpenFile},
            
            {"domande-in-sospeso", Icons.Time},
            {"istanze-in-sospeso", Icons.Time},
            {"istanze-in-compilazione", Icons.Time},
            
            {"le-mie-pratiche", Icons.OpenFolder},
            {"istanze-inviate", Icons.OpenFolder},

            {"le-mie-scadenze", Icons.Calendar},
            {"scadenzario", Icons.Calendar},
            
            {"archivio-pratiche", Icons.Book},
            {"modifica-password", Icons.Lock},
            
            {"esci", Icons.Off},
            {"log-out", Icons.Off},
            
            {"associa-ad-applicazione-mobile",Icons.Phone}
        };

		public string Descrizione { get; set; }
		public string DescrizioneEstesa { get; set; }
		public string Url { get; set; }
		public string Target { get; set; }
        public string TipoIcona { get; set; }
        public string UrlIcona { get; set; }
        public bool MenuUtente { get; set; }

        public string GetDescrizioneEstesa2() {
            var desc = String.IsNullOrEmpty(this.DescrizioneEstesa) ? String.Empty: Regex.Replace(this.DescrizioneEstesa, "<b>.*</b> ", "").Trim();
    
            if (String.IsNullOrEmpty(desc))
            {
                return string.Empty;
            }

            return desc[0].ToString().ToUpper() + desc.Substring(1);
        }

        [XmlIgnore]
        internal bool IsVoceMenuUtente
        {
            get
            {
                if (this.GlyphIcon == Icons.Off)
                {
                    return true;
                }

                if (this.GlyphIcon == Icons.Lock)
                {
                    return true;
                }

                return this.MenuUtente;
            }
        }

        [XmlIgnore]
        public string GlyphIcon
        {
            get
            {
                if (!String.IsNullOrEmpty(this.UrlIcona))
                {
                    return String.Empty;
                }

                var tipoIcona = this.TipoIcona;

                if ( String.IsNullOrEmpty(tipoIcona))
                {
                    tipoIcona = TipoIconaDaDescrizione();
                }

                tipoIcona = TipoIconaToGlyphIcon(tipoIcona);

                return String.IsNullOrEmpty(tipoIcona) ? "glyphicon-star" : tipoIcona;
            }
        }

        private string TipoIconaToGlyphIcon(string tipoIcona)
        {
            if (_gliphIconsMappings.ContainsKey(tipoIcona))
            {
                return _gliphIconsMappings[tipoIcona];
            }

            return String.Empty;
        }

        private string TipoIconaDaDescrizione()
        {
            var descrizione = Regex.Replace(this.Descrizione, "[^a-zA-Z0-9]", "-");
            return descrizione.ToLowerInvariant();
        }
	}

}
