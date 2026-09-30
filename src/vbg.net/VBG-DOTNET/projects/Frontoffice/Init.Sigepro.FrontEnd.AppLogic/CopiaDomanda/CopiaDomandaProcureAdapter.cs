using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.CopiaDomanda
{
    public class CopiaDomandaProcureAdapter : ICopiaDomandaDatiAdapter
    {
        public CopiaDomandaProcureAdapter()
        {
        }

        public void Adatta(Istanze istanzaTemplate, DomandaOnline domanda)
        {
            foreach (var procura in istanzaTemplate.Procure)
            {
                var cfProcuratore = procura.Procuratore.CODICEFISCALE;
                var cfRappresentato = procura.Rappresentato.CODICEFISCALE;

                var idAllegatoProcura = istanzaTemplate.Richiedenti.Where(x => x.Richiedente.CODICEFISCALE == cfRappresentato && x.CodiceoggettoProcura.HasValue).Select(x => x.CodiceoggettoProcura.Value).DefaultIfEmpty(-1).First();

                domanda.WriteInterface.Procure.Aggiungi(cfRappresentato, cfProcuratore, idAllegatoProcura);
            }
        }
    }
}
