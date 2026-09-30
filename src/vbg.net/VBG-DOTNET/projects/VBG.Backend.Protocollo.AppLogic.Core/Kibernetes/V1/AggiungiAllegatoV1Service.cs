using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using System;
using System.Collections.Generic;
using System.ServiceModel;
using System.ServiceModel.Channels;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Core.Kibernetes.V1.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.Kibernetes.V1
{
    public class AggiungiAllegatoV1Service : IAggiungiAllegatiService
    {
        private readonly ILog _logs;
        private readonly IParametriService _parametriService;
        private readonly AnagraficaClientServiceCreator _anagraficaClientServiceCreator;
        public AggiungiAllegatoV1Service(IParametriService parametriService, ILog logs, IBindingFactory bindingFactory)
        {
            this._parametriService = parametriService;
            this._logs = logs;
            this._anagraficaClientServiceCreator = new AnagraficaClientServiceCreator(logs, bindingFactory, this._parametriService.Url);
        }

        public InviaAllegatoResponse AggiungiAllegati(IEnumerable<ProtocolloAllegati> allegati, long numeroProtocollo, short annoProtocollo)
        {
            try
            {
                using (var ws = this._anagraficaClientServiceCreator.CreateClient())
                {
                    using (OperationContextScope scope = new OperationContextScope(ws.Service.InnerChannel))
                    {
                        var isPrincipale = true;
                        foreach (var allegato in allegati)
                        {
                            _logs.InfoFormat("INSERIMENTO DELL'ALLEGATO CODICE {0}, NOME {1}, AL PROTOCOLLO NUMERO {2}, ANNO {3}, PRINCIPALE {4}", allegato.CODICEOGGETTO, allegato.NOMEFILE, numeroProtocollo, annoProtocollo, isPrincipale);
                            AggiungiCredenzialiAContextScope(this._parametriService.UserName, this._parametriService.Password);
                            var response = ws.Service.setAllegato4Protocollo(this._parametriService.IstatEnte.Value, annoProtocollo, numeroProtocollo, Convert.ToBase64String(allegato.OGGETTO), allegato.NOMEFILE, allegato.Descrizione, isPrincipale);
                            if (response.CodStato.Equals(1))
                            {
                                throw new Exception(response.Descizione);
                            }
                        }

                    }
                }

                return new InviaAllegatoResponse
                {
                    Ok = true
                };

            }
            catch (Exception ex)
            {
                _logs.WarnFormat("ERRORE GENERATO DURANTE L'INSERIMENTO DELL'ALLEGATO, DETTAGLIO ERRORE: {0}", ex.Message);
                return InviaAllegatoResponse.FromException(ex);
            }
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
