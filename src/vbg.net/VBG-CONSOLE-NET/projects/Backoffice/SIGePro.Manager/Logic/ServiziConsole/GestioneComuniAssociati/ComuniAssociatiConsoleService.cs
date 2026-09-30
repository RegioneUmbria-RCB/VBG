using Init.SIGePro.Manager.Logic.ServiziConsole;
using Init.SIGePro.Manager.Logic.ServiziConsole.GestioneComuniAssociati;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.ServiziConsole.GestioneComuniAssociati
{
    public class ComuniAssociatiConsoleService : ServiziLocaliRestClient, IComuniAssociatiConsoleService
    {
        private readonly DataBase _db;
        private readonly IConsoleService _consoleService;

        public ComuniAssociatiConsoleService(DataBase db, IConsoleService consoleService)
        {
            this._db = db;
            this._consoleService = consoleService;
        }

        public IEnumerable<ComuniMgr.DatiComuneCompatto> GetComuniAssociati(string software)
        {
            var urlServizi = this._consoleService.GetUrlServizi();
            var serviceUrl = urlServizi.ComuniAssociati(software);

            var lista = QueryItem<List<ComuniAssociatiItem>>(serviceUrl);

            return lista.Select(x => new ComuniMgr.DatiComuneCompatto
            {
                CodiceComune = x.codiceComune,
                Comune = x.comune,
                Provincia = x.provincia,
                SiglaProvincia = x.siglaProvincia,
                Cf = x.cf
            });
        }

        public string  GetPecComuniAssociato(string codiceComune, string software)
        {
            var parametriSdeProxy = this._consoleService.GetUrlServizi();
            var serviceUrl = parametriSdeProxy.ComuniAssociatiPec(codiceComune, software);

            var item = QueryItem<PecComuneAssociatoItem>(serviceUrl);

            return item?.descrizione;
        }
    }
}
