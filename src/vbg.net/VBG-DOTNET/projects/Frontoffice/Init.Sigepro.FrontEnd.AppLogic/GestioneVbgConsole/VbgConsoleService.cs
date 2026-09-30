using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVbgConsole.Authentication;
using Init.Sigepro.FrontEnd.AppLogic.WsUrlAccessoConsoleService;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVbgConsole
{
    public class VbgConsoleService : IVbgConsoleService
    {
        private readonly IUrlConsoleRepository _urlConsoleRepository;
        private readonly IComuniAssociatiService _comuniAssociatiService;
        private readonly IVbgCrossLoginClient _crossLoginClient;
        private readonly IUrlEncoder _urlEncoder;

        public VbgConsoleService(IUrlConsoleRepository urlConsoleRepository, IComuniAssociatiService comuniAssociatiService, IVbgCrossLoginClient crossLoginClient, IUrlEncoder urlEncoder)
        {
            _urlConsoleRepository = urlConsoleRepository ?? throw new ArgumentNullException(nameof(urlConsoleRepository));
            _comuniAssociatiService = comuniAssociatiService ?? throw new ArgumentNullException(nameof(comuniAssociatiService));
            _crossLoginClient = crossLoginClient;
            _urlEncoder = urlEncoder;
        }

        public string GenerateConsoleUrl(UrlAccessoConsole urlAccessoConsole, AnagraficaUtente datiUtente, IEnumerable<IQuerystringParameter> querystringParameters)
        {
            var token = this._crossLoginClient.GetCrossLoginToken(urlAccessoConsole.CrossLoginUrl, datiUtente);
            var url = UrlBuilder.Url(urlAccessoConsole.Url, mp =>
            {
                mp.Add("Token", token);
                mp.Add("AUTH_TYPE", "AUTH_TYPE_LOGIN");

                querystringParameters.Where(x => !String.IsNullOrEmpty(x.ParameterStringValue))
                                     .ToList()
                                     .ForEach(x => mp.Add(x.ParameterName, x.ParameterStringValue));
            });

            return url;
        }

        public IEnumerable<UrlAccessoConsole> GetUrlIstanzeInSospeso()
        {
            return GetUrlConsole((url) => url?.UrlIstanzeInSospeso);
        }

        public UrlAccessoConsole GetUrlIstanzeInSospeso(string codiceComune)
        {
            return GetUrlIstanzeInSospeso().Where(x => x.CodiceComune == codiceComune && !String.IsNullOrEmpty(x.Url)).FirstOrDefault();
        }

        public IEnumerable<UrlAccessoConsole> GetUrlNuovaDomanda()
        {
            return GetUrlConsole((url) => url?.UrlNuovaDomanda);
        }

        public UrlAccessoConsole GetUrlNuovaDomanda(string codiceComune)
        {
            return GetUrlNuovaDomanda().Where(x => x.CodiceComune == codiceComune && !String.IsNullOrEmpty(x.Url)).FirstOrDefault();
        }

        private IEnumerable<UrlAccessoConsole> GetUrlConsole(Func<UrlConsole, string> urlSelector)
        {
            var urlAccesso = this._urlConsoleRepository.GetUrlAccesso();

            // La verticalizzazione per gli url localizzati non è attiva, restituisco l'url di default
            if (!urlAccesso.UrlLocalizzatiAttivi)
            {
                return new[]
                {
                    new UrlAccessoConsole
                    {
                        CodiceComune = "NONUSARE",
                        Comune = "Default",
                        CrossLoginUrl = urlAccesso.Default.CrossLoginUrl,
                        Url = urlSelector(urlAccesso.Default)
                    }
                };
            }

            var comuniAssociati = this._comuniAssociatiService.GetComuniAssociati();
            var dictionary = urlAccesso.UrlLocalizzati.ToDictionary(x => x.CodiceComune);

            return comuniAssociati.Select(comune => new UrlAccessoConsole
            {
                CodiceComune = comune.CodiceComune,
                Comune = comune.Comune,
                CrossLoginUrl = dictionary.ContainsKey(comune.CodiceComune)? dictionary[comune.CodiceComune].CrossLoginUrl : "",
                Url = dictionary.ContainsKey(comune.CodiceComune)? urlSelector(dictionary[comune.CodiceComune]) : ""
            });
        }
    }
}
