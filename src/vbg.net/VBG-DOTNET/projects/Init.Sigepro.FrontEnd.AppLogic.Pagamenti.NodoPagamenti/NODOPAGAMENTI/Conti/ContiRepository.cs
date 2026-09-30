using Init.SIGePro.Manager.DTO.Oneri;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.Conti
{
    public class ContiRepository : IContiRepository
    {
        private readonly ContiServiceCreator _serviceCreator;

        public ContiRepository(ContiServiceCreator areaRiservataServiceCreator)
        {
            this._serviceCreator = areaRiservataServiceCreator;
        }

        public ContoDto GetDatiContoDaCausaleOnere(string codiceComune, int causaleOnereId)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                return ws.Service.GetContoDaIdCausaleOnere(ws.Token, causaleOnereId);
            }
        }
    }
}
