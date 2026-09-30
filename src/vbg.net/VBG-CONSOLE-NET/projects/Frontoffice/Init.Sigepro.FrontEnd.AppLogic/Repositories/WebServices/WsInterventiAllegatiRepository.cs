using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
{
    internal class WsInterventiAllegatiRepository : IInterventiAllegatiRepository
    {
        private readonly AreaRiservataServiceCreator _serviceCreator;
        private readonly IAliasSoftwareResolver _aliasSoftwareResolver;

        public WsInterventiAllegatiRepository(IAliasSoftwareResolver aliasSoftwareResolver, AreaRiservataServiceCreator serviceCreator)
        {
            Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();
            Condition.Requires(aliasSoftwareResolver, "aliasSoftwareResolver").IsNotNull();

            this._serviceCreator = serviceCreator;
            this._aliasSoftwareResolver = aliasSoftwareResolver;
        }


        public IEnumerable<AllegatoInterventoDomandaOnlineDto> GetAllegatiDaIdintervento(int codiceIntervento, AmbitoRicerca ambitoRicerca)
        {
            using (var ws = this._serviceCreator.CreateClient(this._aliasSoftwareResolver.AliasComune))
            {
                return ws.Service.GetDocumentiDaCodiceIntervento(ws.Token, codiceIntervento, ambitoRicerca);
            }
        }

        public IEnumerable<AlberoProcDocumentiCat> GetListaCategorieAllegati()
        {
            var alias = this._aliasSoftwareResolver.AliasComune;
            var software = this._aliasSoftwareResolver.Software;

            using (var ws = this._serviceCreator.CreateClient(alias))
            {
                return new List<AlberoProcDocumentiCat>(ws.Service.GetCategorieAllegatiInterventoChePermettonoUpload(ws.Token, software));
            }
        }
    }
}
