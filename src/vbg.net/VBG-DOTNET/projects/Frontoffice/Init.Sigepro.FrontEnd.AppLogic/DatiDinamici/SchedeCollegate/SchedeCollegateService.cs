using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.SchedeCollegate
{
    public class SchedeCollegateService : ISchedeCollegateService
    {
        private readonly WsDatiDinamiciServiceCreator _serviceCreator;

        public SchedeCollegateService(WsDatiDinamiciServiceCreator serviceCreator)
        {
            this._serviceCreator = serviceCreator;
        }

        public IEnumerable<int> MarcaSchedeCollegateComeNonCompilate(DomandaOnline domanda, int idModello)
        {
            var modelliDellaDomanda = domanda.ReadInterface
                                              .DatiDinamici
                                              .Modelli
                                              .Where(x => x.IdModello != idModello && x.Compilato)
                                              .Select(x => x.IdModello);

            if (!modelliDellaDomanda.Any()) 
            {
                return Enumerable.Empty<int>();
            }

            return this._serviceCreator.Call(ws =>
            {
                var idSchedeDaInvalidare = ws.Service.GetIdSchedeDaMarcareComeNonCompilate(ws.Token, idModello, modelliDellaDomanda.ToArray());

                if (!(idSchedeDaInvalidare?.Any() ?? false))
                {
                    return Enumerable.Empty<int>();
                }

                var schedeDaRicompilare = new List<int>();

                foreach (var idScheda in idSchedeDaInvalidare)
                {
                    if (modelliDellaDomanda.Contains(idScheda))
                    {
                        domanda.WriteInterface.DatiDinamici.ModificaStatoCompilazioneModello(idScheda, 0, false);
                        schedeDaRicompilare.Add(idScheda);
                    }
                }

                return schedeDaRicompilare;
            });
        }
    }
}
