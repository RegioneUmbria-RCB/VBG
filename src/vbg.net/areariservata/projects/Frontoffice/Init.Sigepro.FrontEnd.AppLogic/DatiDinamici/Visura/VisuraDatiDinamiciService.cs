using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Visura
{
    public class VisuraDatiDinamiciService : IVisuraDatiDinamiciService
    {
        VisuraDyn2DataAccessProvider _dyn2DataAccessProvider;
        IAliasResolver _aliasResolver;
        IDatiDinamiciRepository _repository;
        IVisuraService _visuraService;

        internal VisuraDatiDinamiciService(VisuraDyn2DataAccessProvider dyn2DataAccessProvider, IAliasResolver aliasResolver, IDatiDinamiciRepository repository, IVisuraService visuraService)
        {
            this._dyn2DataAccessProvider = dyn2DataAccessProvider;
            this._aliasResolver = aliasResolver;
            this._repository = repository;
            this._visuraService = visuraService;
        }

        public ModelloDinamicoIstanza GetModello(int codiceIstanza, int idModello)
        {
            var loader = new ModelloDinamicoLoader(this._dyn2DataAccessProvider, _aliasResolver.AliasComune, ModelloDinamicoLoader.TipoModelloDinamicoEnum.Frontoffice);
            return new ModelloDinamicoIstanza(loader, idModello, codiceIstanza, 0, false);
        }

        
        public IEnumerable<VisuraTitoloModelloDinamicoIstanza> GetTitoliModelli(int codiceIstanza)
        {
            var istanza = this._visuraService.GetById(codiceIstanza, new VisuraIstanzaFlags { LeggiDatiConfigurazione = false });
            var intervento = Convert.ToInt32(istanza.CODICEINTERVENTOPROC);
            var endo = istanza.EndoProcedimenti.Select(x => Convert.ToInt32(x.CODICEINVENTARIO));

            var schede = this._repository.GetSchedeDaInterventoEEndo(intervento, endo, Enumerable.Empty<string>(), UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche.No);

            var schedeIntervento = schede.SchedeIntervento.Select(x => new VisuraTitoloModelloDinamicoIstanza
            {
                Id = x.Id,
                Descrizione = x.Descrizione
            });

            var schedeEndo = schede.SchedeEndoprocedimenti.Select(x => new VisuraTitoloModelloDinamicoIstanza
            {
                Id = x.Id,
                Descrizione = x.Descrizione
            });

            return schedeIntervento.Union(schedeEndo);
        }
        
    }
}
