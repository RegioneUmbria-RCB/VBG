using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core.Elios
{
    public class EliosAutenticazioneService
    {
        private readonly string _userName;
        private readonly string _password;
        private readonly string _ente;
        private readonly ProtocolloSerializer _serializer;
        private readonly ClientAutenticazioneServiceCreator _clientAutenticazioneServiceCreator;

        public EliosAutenticazioneService(string url, string userName, string password, string ente, ProtocolloSerializer protocolloSerializer, IBindingFactory bindingFactory, ProtocolloLogs logs)
        {
            this._userName = userName;
            this._password = password;
            this._ente = ente;
            this._serializer = protocolloSerializer;
            this._clientAutenticazioneServiceCreator = new ClientAutenticazioneServiceCreator(logs, bindingFactory, url);
        }

        public string Login()
        {
            try
            {
                using (var ws = this._clientAutenticazioneServiceCreator.CreateClient())
                {
                    var response = ws.Service.Login(this._userName, this._password, this._ente);

                    this._serializer.LogAndValidate("Loginresponse.xml", response);

                    return response.Token;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"Errore durante la login con utente: {this._userName}, pass: {this._password}, ente: {this._ente}. Errore: {ex.Message}");
            }
        }

    }
}
