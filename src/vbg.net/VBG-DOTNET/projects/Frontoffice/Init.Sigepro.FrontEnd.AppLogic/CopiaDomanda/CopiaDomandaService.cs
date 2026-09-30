using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.EndoAcquisiti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.CopiaDomanda
{
    public class CopiaDomandaService
    {
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        private readonly IVisuraService _visuraService;
        private readonly List<ICopiaDomandaDatiAdapter> _datiAdapters;
        private readonly DomandeOnlineService _domandeOnlineService;


        public CopiaDomandaService(ISalvataggioDomandaStrategy salvataggioDomandaStrategy, DomandeOnlineService domandeOnlineService, IVisuraService visuraService, 
            IResolveDescrizioneIntervento resolveDescrizioneIntervento, ILogicaSincronizzazioneTipiSoggetto logicaSincronizzazioneTipiSoggetto, 
            IEndoprocedimentiService endoprocedimentiService, IEndoAcquisitiService endoAcquisitiService)
        {
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            this._domandeOnlineService = domandeOnlineService;
            this._visuraService = visuraService;

            this._datiAdapters = new List<ICopiaDomandaDatiAdapter>
            {
                new CopiaDomandaAltriDatiAdapter(resolveDescrizioneIntervento),
                new CopiaDomandaAnagraficheAdapter(logicaSincronizzazioneTipiSoggetto),
                new CopiaDomandaLocalizzazioniAdapter(),
                new CopiaDomandaDatiDinamiciAdapter(),
                new CopiaDomandaProcureAdapter(),
                new CopiaDomandaEndoprocedimentiAdapter(endoprocedimentiService, endoAcquisitiService),
                new CopiaDomandaAllegatiAdapter()
            };

        }

        public int CopiaDaUUIDTemplate( string uuIdDomandaTemplate )
        {
            var istanzaTemplate = this._visuraService.GetByUuid(uuIdDomandaTemplate, false);
            var nuovoId = this._domandeOnlineService.GetProssimoIdDomanda();

            var nuovaDomanda = this._salvataggioDomandaStrategy.GetById(nuovoId);

            foreach (var adapter in this._datiAdapters)
            {
                adapter.Adatta(istanzaTemplate, nuovaDomanda);
            }

            this._salvataggioDomandaStrategy.Salva(nuovaDomanda);

            return nuovoId;
        }
    }
}
