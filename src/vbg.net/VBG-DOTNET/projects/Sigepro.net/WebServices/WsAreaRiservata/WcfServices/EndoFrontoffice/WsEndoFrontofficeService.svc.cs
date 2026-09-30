using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Logic.GestioneEndoprocedimenti;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.EndoFrontoffice
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsEndoFrontofficeService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsEndoFrontofficeService.svc or WsEndoFrontofficeService.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public partial class WsEndoFrontofficeService : WcfServiceBase, IWsEndoFrontofficeService
    {
        public List<FamigliaEndoFrontoffice> GetFamiglieEndoFrontoffice(string token, string software)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return new InventarioProcedimentiMgr(db)
                    .GetFamiglieEndoFrontoffice(authInfo.IdComune, software)
                    .Select(x => new FamigliaEndoFrontoffice
                    {
                        Codice = x.Codice,
                        Descrizione = x.Descrizione
                    })
                    .ToList();
            }
        }

        public List<CategoriaEndoFrontoffice> GetCategorieEndoFrontoffice(string token, string software, int codiceFamiglia)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return new InventarioProcedimentiMgr(db)
                    .GetCategorieEndoFrontoffice(authInfo.IdComune, software, codiceFamiglia)
                    .Select(x => new CategoriaEndoFrontoffice
                    {
                        Codice = x.Codice,
                        Descrizione = x.Descrizione
                    })
                    .ToList();
            }
        }

        public List<EndoBreveFrontoffice> GetListaEndoFrontoffice(string token, string software, int codiceCategoria)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return new InventarioProcedimentiMgr(db)
                .GetEndoFrontoffice(authInfo.IdComune, software, codiceCategoria)
                .Select(x => new EndoBreveFrontoffice
                {
                    Codice = x.Codice,
                    Descrizione = x.Descrizione
                })
                .ToList();
            }
        }

        public RisultatoCaricamentoGerarchiaEndo GetGerarchiaEndo(string token, int valore, LivelloCaricamentoGerarchia livelloRicerca)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                if (livelloRicerca == LivelloCaricamentoGerarchia.Endo)
                {
                    var endo = new InventarioProcedimentiMgr(db).GetById(authInfo.IdComune, valore);

                    if (!endo.Codicetipo.HasValue)
                        return new RisultatoCaricamentoGerarchiaEndo { Endo = valore };

                    var tipo = new TipiEndoMgr(db).GetById(endo.Codicetipo.Value, authInfo.IdComune);

                    if (!tipo.Codicefamigliaendo.HasValue)
                        return new RisultatoCaricamentoGerarchiaEndo { Categoria = endo.Codicetipo.Value, Endo = valore };

                    return new RisultatoCaricamentoGerarchiaEndo { Famiglia = tipo.Codicefamigliaendo.Value, Categoria = endo.Codicetipo.Value, Endo = valore };
                }

                if (livelloRicerca == LivelloCaricamentoGerarchia.Categoria)
                {
                    var tipo = new TipiEndoMgr(db).GetById(valore, authInfo.IdComune);

                    if (!tipo.Codicefamigliaendo.HasValue)
                        return new RisultatoCaricamentoGerarchiaEndo { Categoria = valore };

                    return new RisultatoCaricamentoGerarchiaEndo { Famiglia = tipo.Codicefamigliaendo.Value, Categoria = valore, Endo = -1 };
                }

                return new RisultatoCaricamentoGerarchiaEndo { Famiglia = valore };
            }
        }

        public RisultatoRicercaTestualeEndo RicercaTestualeEndo(string token, string software, string partial, TipoRicercaEnum tipoRicerca)
        {
            var authInfo = CheckToken(token);

            return new RicercaTestualeEndo(authInfo, software).TrovaEndo(partial, tipoRicerca);
        }

    }
}
