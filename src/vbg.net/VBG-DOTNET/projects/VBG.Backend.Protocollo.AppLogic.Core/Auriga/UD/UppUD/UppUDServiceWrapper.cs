using VBG.Shared.Infrastructure.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga.UD.UppUD
{
    public class UppUDServiceWrapper : ProxyServiceWrapper
    {


        private static class Constants
        {
            public const string Titolo = "AGGIORNA PROTOCOLLO PER FASCICOLAZIONE";
            public const string NameSpace = @"http://updunitadoc.webservices.repository2.auriga.eng.it";
            public const string ServiceName = "WSUpdUd";
            public const string RequestServiceName = "upd";
        }

        public UppUDServiceWrapper(ParametriRegoleInfo parametri, ProtocolloSerializer serializer, ProtocolloLogs log, ProxyRequestInfo request, IBindingFactory bindingFactory)
            : base(parametri, serializer, log, request, bindingFactory, Constants.Titolo, Constants.ServiceName)
        {
        }

        public ResponseInfo FascicolaProtocollo(string idProtocollo, string idFascicolo)
        {
            try
            {
                using (var ws = this.CreaWebService())
                {
                    var updateProtocolloRequest = new UDDaAgg
                    {
                        EstremiXIdentificazioneUD = new EstremiXIdentificazioneUDType
                        {
                            Item = idProtocollo
                        },
                        AggCollocazioneClassificazioneUD = new UDDaAggAggCollocazioneClassificazioneUD
                        {
                            AggClassifFascicoli = new UDDaAggAggCollocazioneClassificazioneUDAggClassifFascicoli
                            {
                                Items = new ClassifFascicoloType[]
                                {
                                    new ClassifFascicoloType
                                    {
                                        Item = new EstremiXIdentificazioneFolderNoLibType
                                        {
                                            Item = idFascicolo,
                                            ItemElementName = ItemChoiceType6.IdFolder
                                        }
                                    }
                                },
                                ItemsElementName = new ItemsChoiceType1[]
                                {
                                    ItemsChoiceType1.ClassifFascicoloDaAggiungere
                                }
                            }
                        }
                    };

                    var updateProtocolloRequestXML = Utility.HtmlEncodeContent(this._serializer.Serialize("updateProtocolloRequest.xml", updateProtocolloRequest));

                    this._request.xml = updateProtocolloRequestXML;
                    this._request.hash = this.getHashSHA1();

                    var service = this.CreateServiceRequest(Constants.NameSpace, Constants.RequestServiceName, TipoOperazione.UPDATE_PROTOCOLLO_REQUEST);
                    var xmlService = this._serializer.Serialize(ProtocolloLogsConstants.UpdateProtocolloRequestFileName, service);

                    this.LogInfoRequestWS(xmlService);

                    var serviceResponse = ws.AurigaProxy(service);
                    var responseXml = this._serializer.Serialize(ProtocolloLogsConstants.UpdateProtocolloResponseFileName, serviceResponse);

                    this.LogInfoResponseWS(responseXml);

                    var response = new ResponseInfoAdapter(serviceResponse).Adatta();

                    if (response.WsResult != "1")
                        throw new Exception(response.WsError);

                    this.LogSuccess();

                    return response;

                }
            }
            catch (Exception ex)
            {
                this._logs.ErrorFormat("Errore durante la fascicolazione del protocollo {0} nel fascicolo {1}: {2}", idProtocollo, idFascicolo, ex.Message);

                throw new Exception($"{this._titolo} fallita: {ex.Message}", ex);
            }
        }

        public ResponseInfo FascicolaProtocollo(string idProtocollo, string numeroFascicolo, string annoFascicolo, string codClassifica)
        {
            try
            {
                using (var ws = this.CreaWebService())
                {
                    var updateProtocolloRequest = new UDDaAgg
                    {
                        EstremiXIdentificazioneUD = new EstremiXIdentificazioneUDType
                        {
                            Item = idProtocollo
                        },
                        AggCollocazioneClassificazioneUD = new UDDaAggAggCollocazioneClassificazioneUD
                        {
                            AggClassifFascicoli = new UDDaAggAggCollocazioneClassificazioneUDAggClassifFascicoli
                            {
                                Items = new ClassifFascicoloType[]
                                {
                                    new ClassifFascicoloType
                                    {
                                        Item = new ClassifUAType
                                        {
                                           AnnoAperturaUA = annoFascicolo,
                                           NroProgrUA = numeroFascicolo,
                                           LivelloClassificazione = Utility.GetLivelloGerarchiaDaClassifica(codClassifica)
                                        }
                                    }
                                },
                                ItemsElementName = new ItemsChoiceType1[]
                                {
                                    ItemsChoiceType1.ClassifFascicoloDaAggiungere
                                }
                            }
                        }
                    };

                    var updateProtocolloRequestXML = Utility.HtmlEncodeContent(this._serializer.Serialize("updateProtocolloRequest.xml", updateProtocolloRequest));

                    this._request.xml = updateProtocolloRequestXML;
                    this._request.hash = this.getHashSHA1();

                    var service = this.CreateServiceRequest(Constants.NameSpace, Constants.RequestServiceName, TipoOperazione.UPDATE_PROTOCOLLO_REQUEST);
                    var xmlService = this._serializer.Serialize(ProtocolloLogsConstants.UpdateProtocolloRequestFileName, service);

                    this.LogInfoRequestWS(xmlService);

                    var serviceResponse = ws.AurigaProxy(service);
                    var responseXml = this._serializer.Serialize(ProtocolloLogsConstants.UpdateProtocolloResponseFileName, serviceResponse);

                    this.LogInfoResponseWS(responseXml);

                    var response = new ResponseInfoAdapter(serviceResponse).Adatta();

                    if (response.WsResult != "1")
                        throw new Exception(response.WsError);

                    this.LogSuccess();

                    return response;

                }
            }
            catch (Exception ex)
            {
                this._logs.ErrorFormat("Errore durante la fascicolazione del protocollo {0} nel fascicolo {1}/{2}/{3}: {4}", idProtocollo, numeroFascicolo, annoFascicolo, codClassifica, ex.Message);
                throw new Exception($"{this._titolo} fallita: {ex.Message}", ex);
            }
        }
    }
}
