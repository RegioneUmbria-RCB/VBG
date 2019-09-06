using Init.SIGePro.Manager.DTO;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.AidaSmart.GestioneModalitaPagamento
{
    public class ModalitaPagamentoConsoleService : ServiziLocaliRestClient, IModalitaPagamentoConsoleService
    {
        private readonly IConsoleService _consoleService;
        private readonly ILog _log = LogManager.GetLogger(typeof(ModalitaPagamentoConsoleService));

        public ModalitaPagamentoConsoleService(IConsoleService consoleService)
        {
            _consoleService = consoleService;
        }


        public IEnumerable<BaseDto<string, string>> GetModalitaPagamento()
        {
            var parametriConsole = _consoleService.GetUrlServizi();
            var serviceUrl = parametriConsole.ModalitaPagamento;


            try
            {
                //serviceUrl += $"?limitarisultati=true&descrizione={HttpContext.Current.Server.UrlEncode(partial)}";

                var modalitaPagamento = QueryItem<List<ModalitaPagamentoConsoleDto>>(serviceUrl);

                return modalitaPagamento.Select(x => new BaseDto<string, string>
                {
                    Codice = x.codice,
                    Descrizione = x.descrizione
                });

            }
            catch (Exception ex)
            {
                _log.Error($"Chiamata al web service di lettura modalita pagamento all'url {serviceUrl}. fallita. Dettagli dell'errore: {ex.ToString()}");

                throw;
            }

        }
    }
}
