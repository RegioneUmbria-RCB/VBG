using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.InsertDocumento
{
    public class InsertDocumentoService : ItalSoftClient
    {
        private readonly ProtocolloClientServiceCreator _clientCreator;

        public InsertDocumentoService(ILog logger, ProtocolloSerializer serializer, IBindingFactory bindingFactory, string urlProtocollazione) : base(logger, serializer, urlProtocollazione, "")
        {
            _clientCreator = new ProtocolloClientServiceCreator(logger, bindingFactory, urlProtocollazione);
        }

        public InsertDocumentoResponse InsertDocumento(InsertDocumentoRequest request)
        {
            using (var client = _clientCreator.CreateClient())
            {
                base.Serialize("InsertDocumentoRequest.xml", request);

                try
                {
                    base.LogInfo($"Chiamata a InsertDocumento per il file {request.NomeFile}");
                    var wsResponse = client.Service.InsertDocumento(request.Token, request.NomeFile, Convert.ToBase64String(request.Content), out var messageResult);
                    base.Serialize("InsertDocumentoResponse.xml", wsResponse);

                    var response = InsertDocumentoResponse.FromallegatoPrecaricato(wsResponse);

                    if (String.IsNullOrEmpty(response.NomeFile))
                    {
                        response.NomeFile = request.NomeFile;
                    }

                    return response;
                }
                catch (Exception ex)
                {
                    base.LogInfo($"Errore durante il log della chiamata a InsertDocumento: {ex}");
                    base.LogDebug($"La request che ha generato l'errore. Token: {request.Token}, Estensione: {request.Estensione}");

                    throw;
                }
            }
        }
    }
}
