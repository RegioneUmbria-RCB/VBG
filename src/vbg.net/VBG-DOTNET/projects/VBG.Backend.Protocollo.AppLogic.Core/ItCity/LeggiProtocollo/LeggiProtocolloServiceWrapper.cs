using VBG.Shared.Infrastructure.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItCity.LeggiProtocollo
{
    public class LeggiProtocolloServiceWrapper
    {
        private readonly LoginWsInfo _loginInfo;
        private readonly ProtocolloLogs _logs;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;

        public LeggiProtocolloServiceWrapper(string url, LoginWsInfo loginInfo, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory)
        {
            this._loginInfo = loginInfo;
            this._logs = logs;
            this._protocolloClientServiceCreator = new ProtocolloClientServiceCreator(logs, bindingFactory, url);
        }

        public ItCityService.Protocollo LeggiProtocollo(LeggiProtocolloRequestInfo info)
        {
            try
            {
                using (var ws = this._protocolloClientServiceCreator.CreateClient())
                {
                    this._logs.Info($"RICHIESTA DI LETTURA DEL PROTOCOLLO NUMERO: {info.Numero}, ANNO: {info.Anno}, SIGLA: {info.Sigla}");
                    var response = ws.Service.RicercaProtocollo(this._loginInfo.Username, this._loginInfo.Password, this._loginInfo.Identificativo, info.Anno, info.Numero, info.Numero, info.Sigla);

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

                    this._logs.Info($"RICHIESTA DI LETTURA DEL PROTOCOLLO NUMERO: {info.Numero}, ANNO: {info.Anno}, SIGLA: {info.Sigla}, AVVENUTA CON SUCCESSO");
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
