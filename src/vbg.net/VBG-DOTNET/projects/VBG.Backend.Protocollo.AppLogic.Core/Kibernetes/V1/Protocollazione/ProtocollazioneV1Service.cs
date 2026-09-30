using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using ProtocolloKibernetesService;
using System.ServiceModel;
using System.ServiceModel.Channels;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Kibernetes.V1.Protocollazione
{
    public class ProtocollazioneV1Service : IProtocollazioneService
    {
        private readonly ILog _logs;
        private readonly IProtocolloSerializer _serializer;
        private readonly IParametriService _parametriService;
        private readonly IAggiungiAllegatiService _allegatiService;
        private readonly List<IAnagraficaAmministrazione> _soggetti;
        private readonly string _operatore;
        private readonly AnagraficaClientServiceCreator _anagraficaClientServiceCreator;

        public ProtocollazioneV1Service(IParametriService parametriService, ILog logs, IProtocolloSerializer serializer, IBindingFactory bindingFactory, List<IAnagraficaAmministrazione> soggetti, string operatore)
        {
            _logs = logs;
            _serializer = serializer;
            _parametriService = parametriService;
            _allegatiService = new AggiungiAllegatoV1Service(parametriService, logs, bindingFactory);
            _soggetti = soggetti;
            _operatore = operatore;
            this._anagraficaClientServiceCreator = new AnagraficaClientServiceCreator(logs, bindingFactory, parametriService.Url);
        }

        public ProtocollazioneResponse Protocolla(DatiProtocolloIn datiProtocollo)
        {
            var dati = DatiProtocolloInsertFactory.Create(datiProtocollo);

            var proto = ProtocollazioneV1Factory.Create(this._parametriService, _soggetti, dati, _operatore);

            var response = new StatusProtocollo();

            using (var ws = this._anagraficaClientServiceCreator.CreateClient())
            {
                using (OperationContextScope scope = new OperationContextScope(ws.Service.InnerChannel))
                {
                    AggiungiCredenzialiAContextScope(this._parametriService.UserName, this._parametriService.Password);
                    response = proto.Protocolla(ws.Service, _logs, _serializer);
                }
            }

            if (datiProtocollo.HaAllegati())
            {
                this._allegatiService.AggiungiAllegati(datiProtocollo.RecuperaAllegati(), response.Numero, response.Anno);

            }

            return ProtocollazioneResponse.FromStatusProtocollo(response);
        }

        private void AggiungiCredenzialiAContextScope(string username, string password)
        {
            if (!String.IsNullOrEmpty(username))
            {
                var credentials = GetCredentials(username, password);
                var request = new HttpRequestMessageProperty();

                request.Headers[System.Net.HttpRequestHeader.Authorization] = "Basic " + credentials;

                OperationContext.Current.OutgoingMessageProperties.Add(HttpRequestMessageProperty.Name, request);
            }
        }

        private string GetCredentials(string username, string password)
        {
            var credentials = username + ":" + password;

            return Convert.ToBase64String(Encoding.UTF8.GetBytes(credentials));
        }
    }
}
