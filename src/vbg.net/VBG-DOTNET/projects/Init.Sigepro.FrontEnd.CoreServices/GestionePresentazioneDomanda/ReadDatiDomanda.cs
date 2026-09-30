using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.ReadInterface;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;

namespace Init.Sigepro.FrontEnd.CoreServices.GestionePresentazioneDomanda
{
    public class ReadDatiDomanda : IReadDatiDomanda
    {
        private readonly IIdDomandaResolver _idDomandaResolver;
        private readonly DomandeOnlineService _domandeOnlineService;

        public PresentazioneIstanzaDataKey? DomandaDataKey => this.GetDomandaCorrente()?.DataKey;

        public IDomandaOnlineReadInterface? Domanda => this.GetDomandaCorrente()?.ReadInterface;

        public ReadDatiDomanda(IIdDomandaResolver idDomandaResolver, DomandeOnlineService domandeOnlineService)
        {
            this._idDomandaResolver = idDomandaResolver;
            this._domandeOnlineService = domandeOnlineService;
        }

        private DomandaOnline GetDomandaCorrente()
        {
            return this._domandeOnlineService.GetById(this._idDomandaResolver.IdDomanda);

        }
    }
}
