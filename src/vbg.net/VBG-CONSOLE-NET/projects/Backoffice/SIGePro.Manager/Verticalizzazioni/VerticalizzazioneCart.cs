using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Verticalizzazioni
{
    public partial class VerticalizzazioneCart
    {
        public static class Constants
        {
            public const string UrlAccettatore = "URL_ACCETTATORE";
        }

        public string UrlAccettatore
        {
            get { return GetString(Constants.UrlAccettatore); }
        }
    }
}
