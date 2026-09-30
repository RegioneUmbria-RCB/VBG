using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Ninject;

namespace Init.Sigepro.FrontEnd.AppLogic.ReadInterface.Imp
{
    internal class ReadFacadeImp : IReadFacade
    {
        private readonly IIdDomandaResolver _idDomandaResolver;
        private readonly DomandeOnlineService _domandeOnlineService;

        public ReadFacadeImp(IIdDomandaResolver idDomandaResolver, DomandeOnlineService domandeOnlineService)
        {
            this._idDomandaResolver = idDomandaResolver;
            this._domandeOnlineService = domandeOnlineService;
        }


        #region IReadFacade Members

        [Inject]
        public IComuniService Comuni
        {
            get;
            set;
        }

        public IDomandaOnlineReadInterface Domanda
        {
            get
            {
                return this._domandeOnlineService.GetById(this._idDomandaResolver.IdDomanda).ReadInterface;
            }
        }

        public PresentazioneIstanzaDataKey DomandaDataKey
        {
            get { return this._domandeOnlineService.GetById(this._idDomandaResolver.IdDomanda).DataKey; }
        }

        [Inject]
        public ITipiSoggettoService TipiSoggetto
        {
            get;
            set;
        }


        [Inject]
        public IAtecoRepository Ateco
        {
            get;
            set;
        }

        [Inject]
        public IStradarioRepository Stradario
        {
            get;
            set;
        }


        [Inject]
        public ICittadinanzeService Cittadinanze
        {
            get;
            set;
        }

        #endregion
    }
}
