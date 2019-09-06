using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloItCityService;
using Init.SIGePro.Protocollo.Serialize;

namespace Init.SIGePro.Protocollo.ItCity.LeggiProtocollo
{
    public class LeggiProtocolloServiceWrapper : ServiceWrapperBase
    {
        public LeggiProtocolloServiceWrapper(string url, LoginWsInfo loginInfo, ProtocolloLogs logs, ProtocolloSerializer serializer) : base(url, loginInfo, logs, serializer)
        {

        }

        public ProtocolloItCityService.Protocollo LeggiProtocollo(LeggiProtocolloRequestInfo info)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    base.Logs.Info($"RICHIESTA DI LETTURA DEL PROTOCOLLO NUMERO: {info.Numero}, ANNO: {info.Anno}, SIGLA: {info.Sigla}");
                    var response = ws.RicercaProtocollo(base.LoginInfo.Username, base.LoginInfo.Password, base.LoginInfo.Identificativo, info.Anno, info.Numero, info.Numero, info.Sigla);

                    if (response.Exitcode != 0)
                    {
                        throw new Exception(response.ExitMessage);
                    }

                    if (response.Protocollo.Length > 1)
                    {
                        throw new Exception("SONO STATI TROVATI PIU' PROTOCOLLI");
                    }

                    if (response.Protocollo.Length == 0)
                    {
                        throw new Exception("NESSUN PROTOCOLLO TROVATO");
                    }

                    base.Logs.Info($"RICHIESTA DI LETTURA DEL PROTOCOLLO NUMERO: {info.Numero}, ANNO: {info.Anno}, SIGLA: {info.Sigla}, AVVENUTA CON SUCCESSO");
                    return response.Protocollo[0];
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"LETTURA DEL PROTOCOLLO NUMERO: {info.Numero}, ANNO: {info.Anno}, SIGLA: {info.Sigla} FALLITA, {ex.Message}", ex);
            }
        }
    }
}
