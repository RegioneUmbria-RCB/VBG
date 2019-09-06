using Init.SIGePro.Data;
using Init.SIGePro.Manager.DTO.StradarioComune;
using log4net;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Web;

namespace Init.SIGePro.Manager.Logic.AidaSmart.GestioneStradario
{
    public class StradarioConsoleService : ServiziLocaliRestClient, IStradarioConsoleService
    {
        private readonly IConsoleService _consoleService;
        private readonly ILog _log = LogManager.GetLogger(typeof(StradarioConsoleService));

        public StradarioConsoleService(IConsoleService consoleService)
        {
            _consoleService = consoleService;
        }


        public IEnumerable<StradarioDto> FindStradario(string codiceComune, string comuneLocalizzazione, string partial)
        {
            var parametriConsole = _consoleService.GetUrlServizi();
            var serviceUrl = parametriConsole.ListaStradario;

            using (var wc = new WebClient())
            {
                try
                {
                    serviceUrl += $"?limitarisultati=true&descrizione={HttpContext.Current.Server.UrlEncode(partial)}";
                    
                    var elementiStradario = QueryItem<List<SdeProxyStradarioDto>>(serviceUrl);

                    return elementiStradario.Select(x => new StradarioDto
                    {
                        CodiceStradario = x.Id.codice,
                        CodViario = x.codviario,
                        NomeVia = $"{x.prefisso} {x.descrizione}"
                    });

                }
                catch (Exception ex)
                {
                    _log.Error($"Chiamata al web service di lettura stradario all'url {serviceUrl}. fallita. Dettagli dell'errore: {ex.ToString()}");

                    throw;
                }

            }
        }

        public Stradario GetById(int codiceStradario)
        {
            var parametriConsole = _consoleService.GetUrlServizi();
            var serviceUrl = parametriConsole.StradarioDaId;

            using (var wc = new WebClient())
            {
                try
                {
                    serviceUrl += HttpContext.Current.Server.UrlEncode(codiceStradario.ToString());

                    // ComuneLocalizzazione al momento non è usato

                   var x = QueryItem<SdeProxyStradarioDto>(serviceUrl);
                    
                    return new Stradario
                    {
                        CODICESTRADARIO = x.Id.codice.ToString(),
                        PREFISSO = x.prefisso,
                        DESCRIZIONE = x.descrizione
                    };

                }
                catch (Exception ex)
                {
                    _log.Error($"Chiamata al web service di lettura stradario all'url {serviceUrl}. fallita. Dettagli dell'errore: {ex.ToString()}");

                    throw;
                }
            }
        }


    }
}