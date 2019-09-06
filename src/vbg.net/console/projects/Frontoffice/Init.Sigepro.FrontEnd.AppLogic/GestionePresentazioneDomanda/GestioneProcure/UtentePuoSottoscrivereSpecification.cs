using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.Infrastructure;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneProcure
{
	public interface IUtentePuoSottoscrivereSpecification : ISpecification<string>
	{
	}

	public class UtentePuoSottoscrivereSpecification : IUtentePuoSottoscrivereSpecification
	{
		IProcureReadInterface _procure;

		public UtentePuoSottoscrivereSpecification(IProcureReadInterface procure)
		{
			this._procure = procure;
		}



		#region ISpecification<string> Members

		public bool IsSatisfiedBy(string codiceFiscaleUtente)
		{
			return this._procure
						.Procure
						.Where( x => x.Procuratore != null && 
								x.Procuratore.CodiceFiscale.ToUpperInvariant() == codiceFiscaleUtente.ToUpperInvariant())
						.Count() > 0 ;
		}

		#endregion
	}
}
