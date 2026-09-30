using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
	internal class ParametriScadenzarioBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriScadenzario>
	{

		public ParametriScadenzarioBuilder(IAliasSoftwareResolver aliasSoftwareResolver, IConfigurazioneAreaRiservataRepository repo):base(aliasSoftwareResolver,repo)
		{

		}

		#region IBuilder<ParametriScadenzario> Members

		public ParametriScadenzario Build()
		{
			var cfg = GetConfig();

			var parametri = cfg.ParametriRicercaScadenzario;

			return new ParametriScadenzario(parametri.CercaComeTecnico, 
											parametri.CercaComeRichiedente, 
											parametri.CercaComeAzienda, 
											parametri.CercaPartitaIva, 
											parametri.CercaSoggettiCollegati);
		}

		#endregion
	}
}
