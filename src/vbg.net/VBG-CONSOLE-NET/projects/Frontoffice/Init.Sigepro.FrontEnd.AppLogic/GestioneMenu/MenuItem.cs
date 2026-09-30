using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu
{
    [Serializable]
    public class MenuItem
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
            public const string Star = "glyphicon-star";
        }

        private static Dictionary<string, string> _gliphIconsMappings = new Dictionary<string, string>
        {
            {"nuova-istanza", Icons.OpenFile},
            {"domande-in-istanza", Icons.Time},
            {"le-mie-istanza", Icons.OpenFolder},
            {"le-mie-scadenze", Icons.Calendar},
            {"archivio-pratiche", Icons.Book},
            {"modifica-password", Icons.Lock},
            {"esci", Icons.Off},
            {"associa-ad-applicazione-mobile",Icons.Phone}
        };


        [XmlElement]
        public string Titolo { get; set; }

        [XmlElement]
        public string Descrizione { get; set; }

        string _url;
        [XmlElement]
        public string Url
        {
            get { return this._url; }
            set
            {
                this._url = new MenuUrlParser(this.CompletaUrl).Completa(value);
            }
        }

        [XmlAttribute(AttributeName="id-icona")]
        public string IdIcona { get; set; }

        [XmlAttribute(AttributeName = "completa-url")]
        public bool CompletaUrl { get; set; }

        [XmlAttribute(AttributeName = "url-icona")]
        public string UrlIcona { get; set; }

        [XmlAttribute(AttributeName = "mostra-in-home-page")]
        public bool MostraInHomePage { get; set; }

        [XmlAttribute(AttributeName = "target")]
        public string Target { get; set; }

        private string _iconaBootstrap;

        [XmlAttribute(AttributeName = "icona-bs")]
        public string IconaBootstrap
        {
            get
            {
                if (!String.IsNullOrEmpty(this._iconaBootstrap))
                {
                    if (this._iconaBootstrap.IndexOf("glyphicon") < 0)
                    {
                        return "glyphicon-" + this._iconaBootstrap;
                    }

                    return this._iconaBootstrap;
                }

                if (!String.IsNullOrEmpty(this.IdIcona) && _gliphIconsMappings.ContainsKey(this.IdIcona))
                {
                    return _gliphIconsMappings[this.IdIcona];
                }

                return Icons.Star;
            }

            set { this._iconaBootstrap = value; }
        }

        public MenuItem()
        {
            MostraInHomePage = true;
            CompletaUrl = true;
        }
    }
}
