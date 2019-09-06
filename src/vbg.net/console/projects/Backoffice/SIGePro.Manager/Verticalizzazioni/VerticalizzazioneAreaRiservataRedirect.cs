using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Verticalizzazioni
{
    public partial class VerticalizzazioneAreaRiservataRedirect : Verticalizzazione
    {
        private static class Constants
        {
            public const string NOME_VERTICALIZZAZIONE = "AREARISERVATA_REDIRECT";
            public const string NomeFile = "NOME_FILE";
            public const string UrlRedirect = "URL_REDIRECT";
            public const string NomeFileDefault = "~/redirect-default.xml";
        }

        public VerticalizzazioneAreaRiservataRedirect(string idComuneAlias, string software) : base(idComuneAlias, Constants.NOME_VERTICALIZZAZIONE , software ){ }

        public string NomeFile
        {
            get
            {
                var str = GetString(Constants.NomeFile);

                if (String.IsNullOrEmpty(str))
                {
                    str = Constants.NomeFileDefault;
                }

                return str;
            }
        }

        public string UrlRedirect
        {
            get { return GetString(Constants.UrlRedirect); }
        }
    }
}
