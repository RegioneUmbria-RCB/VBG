using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu
{
    public class SezioneMenu
    {
        public string Titolo { get; set; }

        string _url = "#";
        public string Url
        {
            get { return this._url; }
            set
            {
                this._url = new MenuUrlParser(true).Completa(value);
            }
        }

        public bool HaLink
        {
            get
            {
                return this.Url != "#";
            }
        }

        public List<MenuItem> Items { get; set; }

        public SezioneMenu()
        {
            this.Items = new List<MenuItem>();
        }
    }
}
