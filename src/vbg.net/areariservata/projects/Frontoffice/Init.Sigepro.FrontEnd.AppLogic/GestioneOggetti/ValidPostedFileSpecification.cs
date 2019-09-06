using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.Infrastructure;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti
{
	public class ValidPostedFileSpecification : IValidPostedFileSpecification
    {
		IConfigurazione<ParametriAllegati> _config;

		public ValidPostedFileSpecification(IConfigurazione<ParametriAllegati> config)
		{
			this._config = config;
		}

		public string ErrorMessage
        {
			get { return this._config.Parametri.WarningDimensioneMassimaAllegatoSuperata; }
		}

		public bool IsSatisfiedBy(HttpPostedFile item)
		{
			if(this._config.Parametri.DimensioneMassimaAllegato == 0)
			{
				return true;
			}

			return item.ContentLength <= this._config.Parametri.DimensioneMassimaAllegato;
		}
	}
}
