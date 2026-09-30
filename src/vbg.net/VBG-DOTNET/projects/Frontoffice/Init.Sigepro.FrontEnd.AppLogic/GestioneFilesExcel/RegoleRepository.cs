using Init.Sigepro.FrontEnd.AppLogic.Common;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneFilesExcel
{
    internal class RegoleRepository : IRegoleRepository
    {
        private readonly MappatureServiceCreator _serviceCreator;
        private readonly ISoftwareResolver _aliasResolver;

        public RegoleRepository(MappatureServiceCreator serviceCreator, ISoftwareResolver aliasResolver)
        {
            this._serviceCreator = serviceCreator;
            this._aliasResolver = aliasResolver;
        }

        public RegoleExcel All()
        {
            return this._serviceCreator.Call(ws =>
            {
                var mappature = ws.Service.GetMappature(ws.Token, this._aliasResolver.Software);

                var regole = mappature
                                .Where(x => x.Espressione.StartsWith(ExcelExpression.Constants.ExpressionIdentifier))
                                .Select(x => new MappaturaExcel(x.IdCampo, x.NomeCampo, x.Espressione));

                return new RegoleExcel(regole);
            });
        }
    }
}
