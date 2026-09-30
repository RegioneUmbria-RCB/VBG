using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Ninject;

namespace Init.Sigepro.FrontEnd.AppLogic.ReadInterface.Imp
{
#if NET48_OR_GREATER
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
        } = default!;

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
        } = default!;


        //[Inject]
        //public IAtecoRepository Ateco
        //{
        //    get;
        //    set;
        //}

        [Inject]
        public IInterventiRepository Interventi
        {
            get;
            set;
        } = default!;

        [Inject]
        public IStradarioRepository Stradario
        {
            get;
            set;
        } = default!;


        [Inject]
        public ICittadinanzeService Cittadinanze
        {
            get;
            set;
        } = default!;

        #endregion
    }
#endif
}
