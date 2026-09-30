using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using CuttingEdge.Conditions;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
{
	internal class WsAreaRiservataRepositoryBase
	{
		protected AreaRiservataServiceCreator _serviceCreator;

		public WsAreaRiservataRepositoryBase(AreaRiservataServiceCreator serviceCreator)
		{
			Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();

			_serviceCreator = serviceCreator;
		}
	}
}
