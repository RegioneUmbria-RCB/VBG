using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
	internal class ParametriRicercaAnagraficheBuilder: AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriRicercaAnagrafiche>
	{
		public ParametriRicercaAnagraficheBuilder(IAliasSoftwareResolver aliasSoftwareResolver, IConfigurazioneAreaRiservataRepository repo):base(aliasSoftwareResolver, repo)
		{
		}


		#region IConfigurazioneBuilder<ParametriRicercaAnagrafiche> Members

		public ParametriRicercaAnagrafiche Build()
		{
			var cfg = GetConfig();

			var urlRicercaPf = cfg.UrlWsRicercheAnagrafiche.PersoneFisiche;
			var urlRicercaPg = cfg.UrlWsRicercheAnagrafiche.PersoneGiuridiche;


            if (!String.IsNullOrEmpty(ConfigurationManager.AppSettings["overrideUrlRicercheAnagrafichePf"]))
            {
                urlRicercaPf = ConfigurationManager.AppSettings["overrideUrlRicercheAnagrafichePf"];
            }

            if (!String.IsNullOrEmpty(ConfigurationManager.AppSettings["overrideUrlRicercheAnagrafichePg"]))
            {
                urlRicercaPg = ConfigurationManager.AppSettings["overrideUrlRicercheAnagrafichePg"];
            }

            return new ParametriRicercaAnagrafiche(urlRicercaPf, urlRicercaPg);
		}

		#endregion
	}
}
