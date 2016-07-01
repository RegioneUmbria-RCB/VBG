// -----------------------------------------------------------------------
// <copyright file="OneriRepository.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOneri
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
	using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
	using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
	using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.Common;



	internal class OneriRepository : IOneriRepository
	{
		AreaRiservataServiceCreator _serviceCreator;
		IAliasResolver _aliasResolver;

		public OneriRepository(AreaRiservataServiceCreator serviceCreator, IAliasResolver aliasResolver)
		{
			Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();
			Condition.Requires(aliasResolver, "aliasResolver").IsNotNull();

			this._serviceCreator = serviceCreator;
			this._aliasResolver = aliasResolver;
		}

		#region IOneriRepository Members

		public List<OnereDto> GetByIdInterventoIdEndo( int codiceIntervento, List<int> listaIdEndo)
		{
			using (var ws = _serviceCreator.CreateClient(_aliasResolver.AliasComune))
			{
				var rVal = ws.Service.GetListaOneriDaIdInterventoECodiciEndo(ws.Token, codiceIntervento, listaIdEndo.ToArray());

				return new List<OnereDto>(rVal);

			}
		}

		public IEnumerable<BaseDtoOfStringString> GetModalitaPagamento()
		{
			using (var ws = _serviceCreator.CreateClient(_aliasResolver.AliasComune))
			{
				return ws.Service.GetModalitaPagamento(ws.Token);
			}
		}

		#endregion
	}
}
