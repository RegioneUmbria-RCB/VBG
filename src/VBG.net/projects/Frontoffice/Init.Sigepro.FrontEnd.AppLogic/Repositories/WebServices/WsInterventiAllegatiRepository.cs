using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.Common;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
{
	internal class WsInterventiAllegatiRepository : IInterventiAllegatiRepository
	{
		AreaRiservataServiceCreator _serviceCreator;
		IAliasSoftwareResolver _aliasSoftwareResolver;

		public WsInterventiAllegatiRepository(IAliasSoftwareResolver aliasSoftwareResolver, AreaRiservataServiceCreator serviceCreator)
		{
			Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();
			Condition.Requires(aliasSoftwareResolver, "aliasSoftwareResolver").IsNotNull();

			_serviceCreator = serviceCreator;
			_aliasSoftwareResolver = aliasSoftwareResolver;
		}


		public IEnumerable<AllegatoInterventoDomandaOnlineDto> GetAllegatiDaIdintervento( int codiceIntervento, AmbitoRicerca ambitoRicerca)
		{
			using (var ws = _serviceCreator.CreateClient(this._aliasSoftwareResolver.AliasComune))
			{
				return ws.Service.GetDocumentiDaCodiceIntervento(ws.Token, codiceIntervento, ambitoRicerca);
			}
		}


		public IEnumerable<AlberoProcDocumentiCat> GetListaCategorieAllegati()
		{
			var alias = this._aliasSoftwareResolver.AliasComune;
			var software = this._aliasSoftwareResolver.Software;

			using (var ws = _serviceCreator.CreateClient(alias))
			{
				return new List<AlberoProcDocumentiCat>(ws.Service.GetCategorieAllegatiInterventoChePermettonoUpload(ws.Token, software));
			}
		}
	}
}
