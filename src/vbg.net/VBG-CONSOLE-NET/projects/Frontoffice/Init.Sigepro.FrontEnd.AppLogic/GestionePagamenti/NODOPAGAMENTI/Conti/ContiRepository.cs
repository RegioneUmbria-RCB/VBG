using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.WsOneri;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI.Conti
{
    public class ContiRepository : IContiRepository
    {
        private readonly OneriServiceCreator _serviceCreator;
        private readonly ISoftwareResolver _softwareResolver;

        public ContiRepository(OneriServiceCreator areaRiservataServiceCreator, ISoftwareResolver softwareResolver)
        {
            this._serviceCreator = areaRiservataServiceCreator ?? throw new System.ArgumentNullException(nameof(areaRiservataServiceCreator));
            this._softwareResolver = softwareResolver ?? throw new System.ArgumentNullException(nameof(softwareResolver));
        }

        public ContoDto GetDatiContoDaCausaleOnere(string codiceComune, int causaleOnereId)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                return ws.Service.GetContoDaIdCausaleOnere(ws.Token, this._softwareResolver.Software, codiceComune, causaleOnereId);
            }
        }
    }
}
