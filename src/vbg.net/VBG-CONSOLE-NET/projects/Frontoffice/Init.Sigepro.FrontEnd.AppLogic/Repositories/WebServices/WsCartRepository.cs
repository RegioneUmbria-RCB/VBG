using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Common;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
{
	internal class WsCartRepository : ICartRepository
	{
		AreaRiservataServiceCreator _serviceCreator;
		IAliasSoftwareResolver _aliasSoftwareResolver;

		public WsCartRepository(IAliasSoftwareResolver aliasSoftwareResolver, AreaRiservataServiceCreator serviceCreator)
		{
			Condition.Requires(aliasSoftwareResolver, "aliasSoftwareResolver").IsNotNull();
			Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();

			this._serviceCreator = serviceCreator;
			this._aliasSoftwareResolver = aliasSoftwareResolver;
		}

		#region ICartRepository Members

		public string GetUrlSchedaCARTEndo( int codEndo)
		{
			using (var ws = _serviceCreator.CreateClient( this._aliasSoftwareResolver.AliasComune))
			{
				return ws.Service.GetUrlSchedaCARTEndo(ws.Token, codEndo);
			}
		}

		public string GetUrlSchedaCARTIntervento( int codIntervento)
		{
			using (var ws = _serviceCreator.CreateClient(this._aliasSoftwareResolver.AliasComune))
			{
				return ws.Service.GetUrlSchedaCARTIntervento(ws.Token, codIntervento);
			}
		}




		public string GetCodiceAttivitaBdrDaIdIntervento(int codiceIntervento)
		{
			using (var ws = _serviceCreator.CreateClient(this._aliasSoftwareResolver.AliasComune))
			{
				return ws.Service.GetCodiceAttivitaBdrDaIdIntervento(ws.Token, this._aliasSoftwareResolver.Software, codiceIntervento);
			}
		}

		#endregion
	}
}
