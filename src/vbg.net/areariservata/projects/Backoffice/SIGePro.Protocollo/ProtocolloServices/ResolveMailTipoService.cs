using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.MailTipoService;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ProtocolloServices
{
    public class ResolveMailTipoService : BaseResolveMailTipoService, IResolveMailTipoService
    {
        public string Oggetto { get; private set; }
        public string Corpo { get; private set; }

        public ResolveMailTipoService(ProtocolloLogs logs, int codiceMailTipo, string token) : base(logs)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    var request = new MailtipoRequest
                    {
                        token = token,
                        codicemailtipo = codiceMailTipo
                    };

                    var response = ws.Mailtipo(request);

                    if (response == null)
                    {
                        throw new Exception("IL SERVIZIO HA RESTITUITO DEI VALORI NULL");
                    }

                    this.Oggetto = response.oggetto;
                    this.Corpo = response.corpo;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE LA RICHIESTA DELLA MAIL TIPO, {ex.Message}", ex);
            }
        }
    }
}
