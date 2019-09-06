using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneFilesExcel
{
    class RegoleRepository : IRegoleRepository
    {
        AreaRiservataServiceCreator _serviceCreator;
        IAliasSoftwareResolver _aliasResolver;

        public RegoleRepository(AreaRiservataServiceCreator serviceCreator, IAliasSoftwareResolver aliasResolver)
        {
            this._serviceCreator = serviceCreator;
            this._aliasResolver = aliasResolver;
        }

        public RegoleExcel All()
        {
            using(var ws = this._serviceCreator.CreateClient(this._aliasResolver.AliasComune))
            {
                var mappature = ws.Service.GetMappature(ws.Token, this._aliasResolver.Software);

                var regole = mappature
                                .Where(x => x.Espressione.StartsWith(ExcelExpression.Constants.ExpressionIdentifier))
                                .Select(x => new MappaturaExcel(x.IdCampo, x.NomeCampo, x.Espressione));

                return new RegoleExcel(regole);
            }
        }
    }
}
