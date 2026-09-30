using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVbgConsole
{
    public interface IVbgConsoleService
    {
        IEnumerable<UrlAccessoConsole> GetUrlNuovaDomanda();
        UrlAccessoConsole GetUrlNuovaDomanda(string codiceComune);
        UrlAccessoConsole GetUrlIstanzeInSospeso(string codiceComune);
        IEnumerable<UrlAccessoConsole> GetUrlIstanzeInSospeso();
        string GenerateConsoleUrl(UrlAccessoConsole urlAccessoConsole, AnagraficaUtente datiUtente, IEnumerable<IQuerystringParameter> querystringParameters);
    }
}
