using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using ProtocolloKibernetesV2Service;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Kibernetes.V2
{
    public class AggiungiAllegatoV2Service :  IAggiungiAllegatiService
    {

        private readonly ILog _logger;
        private readonly IProtocolloSerializer _serializer;
        private readonly IParametriService _parametriService;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;

        public AggiungiAllegatoV2Service(IParametriService parametriService, ILog logger, IProtocolloSerializer serializer, IBindingFactory bindingFactory)
        {
            _logger = logger;
            _serializer = serializer;
            _parametriService = parametriService;
            _protocolloClientServiceCreator = new ProtocolloClientServiceCreator(logger, bindingFactory, parametriService.Url);
        }

        public InviaAllegatoResponse AggiungiAllegati(IEnumerable<ProtocolloAllegati> allegati, long numeroProtocollo, short annoProtocollo)
        {
            this._logger.Debug($"Inizio aggiunta allegati al protocollo numero {numeroProtocollo} e anno {annoProtocollo}");

            using (var ws = this._protocolloClientServiceCreator.CreateClient())
            {
                try
                {
                    var authInfo = new AuthInfo
                    {
                        NomeUtente = this._parametriService.UserName,
                        Password = this._parametriService.Password
                    };

                    this._serializer.LogAndValidate("AuthInfoRequest.xml", authInfo);

                    foreach (var allegato in allegati)
                    {
                        var documento = new DocumentiDati
                        {
                            Anno = annoProtocollo,
                            Numero = Convert.ToInt32(numeroProtocollo), // in fase di protocollazione è long ma quando carichi gli allegati è int ?????
                            Documento = new ExportResult
                            {
                                Buffer = allegato.OGGETTO,
                                FileName = allegato.NOMEFILE
                            }
                        };

                        this._serializer.LogAndValidate("DocumentiDatiRequest.xml", documento);

                        var wsResponse = ws.Service.InviaAllegato(authInfo, documento);

                        this._serializer.LogAndValidate("InviaAllegatoResponse.xml", wsResponse);

                        if (wsResponse.Errori != null && wsResponse.Errori.Length > 0)
                        {
                            throw new Exception(String.Join(", ", wsResponse.Errori));
                        }

                    }

                    this._logger.Debug($"Fine aggiunta allegati al protocollo numero {numeroProtocollo} e anno {annoProtocollo}");

                    return new InviaAllegatoResponse
                    {
                        Ok = true
                    };
                }
                catch (Exception ex)
                {
                    this._logger.WarnFormat("ERRORE GENERATO DURANTE L'INSERIMENTO DELL'ALLEGATO, DETTAGLIO ERRORE: {0}", ex.Message);
                    return new InviaAllegatoResponse
                    {
                        Ok = false,
                        DescrStato = ex.Message
                    };
                }
            }
        }
    }
}
