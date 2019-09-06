using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Text;
using System.Web;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriPhantomjsBuilder : AreaRiservataWebConfigBuilder, IConfigurazioneBuilder<ParametriPhantomjs>
    {
        private class Constants
        {
            public const string WebConfigKey = "Phantomjs.Path";
            public const string DefaultPath = "~/phantomjs/";
        }

        public ParametriPhantomjs Build()
        {
            var cfg = ConfigurationManager.AppSettings[Constants.WebConfigKey];

            if (String.IsNullOrEmpty(cfg))
            {
                cfg = Constants.DefaultPath;
            }

            return new ParametriPhantomjs(HttpContext.Current.Server.MapPath(cfg));
        }
    }
}
