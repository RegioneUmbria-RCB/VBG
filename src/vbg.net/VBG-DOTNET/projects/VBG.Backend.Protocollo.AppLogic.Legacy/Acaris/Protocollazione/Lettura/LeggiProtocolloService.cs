using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Allegati;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Classificazione;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client;
using PersonalLib2.Data;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using Init.SIGePro.Protocollo.AcarisObjectServicePort;
using System;
using System.Linq;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Protocollazione.Lettura
{
    public class LeggiProtocolloService
    {
        private readonly IProtocolloSerializer _serializer;
        private readonly ParametriRegoleInfo _configurazione;
        private String _idProtocollo;
        private readonly String _numeroProtocollo;
        private readonly int? _annoProtocollo;

        public LeggiProtocolloService(IProtocolloSerializer serializer, ParametriRegoleInfo config, LeggiProtocolloRequest request)
        {
            this._serializer = serializer;

            this._idProtocollo = request.IdProtocollo;
            this._numeroProtocollo = request.NumeroProtocollo;
            this._annoProtocollo = request.AnnoProtocollo;

            this._configurazione = config;
        }
        internal DatiProtocolloLettoResponseType LeggiProtocollo()
        {
            var leggiProtocolloResponse = this.GetProperties();

            var leggiProtocolloProperties = leggiProtocolloResponse
                                       .FirstOrDefault()?
                                       .properties;

            var idClassificazione = this.ProperyValue(leggiProtocolloProperties, "idClassificazione");
            var classificazioneResponse = new ClassificazioneService(this._serializer, this._configurazione).GetClassificazione(idClassificazione)
                                            ?? throw new Exception("Il protocollo richiesto non è stato trovato");
            var allegatiResponse = new AllegatiAcarisService(this._serializer, this._configurazione).GetAllegati(idClassificazione);

            return new DatiProtocolloLettoResponseType
            {
                Allegati = allegatiResponse.Select(x => new AllegatoResponseType
                {
                    IDBase = x.Id,
                    Commento = x.NomeFile,
                    Serial = x.NomeFile,
                    ContentType = x.MimeType
                }).ToArray(),
                AnnoNumeroPratica = "",
                AnnoProtocollo = this.ProperyValue(leggiProtocolloProperties, "dataProtocollo").Substring(6),
                Classifica = classificazioneResponse.IndiceClassificazione,
                Classifica_Descrizione = classificazioneResponse.IndiceClassificazioneEstesa,
                DataAnnullamento = "",
                DataInserimento = "",
                DataProtocollo = this.ProperyValue(leggiProtocolloProperties, "dataProtocollo"),
                DataProtocolloMittente = "",
                DocAllegati = "",
                Errore = null,
                IdProtocollo = this._idProtocollo,
                InCaricoA = "",
                InCaricoA_Descrizione = "",
                MittenteInterno = "",
                MittenteInterno_Descrizione = "",
                MittentiDestinatari = null,
                MotivoAnnullamento = "",
                NumeroPratica = "",
                NumeroProtocollo = this.ProperyValue(leggiProtocolloProperties, "codice"),
                NumeroProtocolloMittente = "",
                Oggetto = this.ProperyValue(leggiProtocolloProperties, "oggetto"),
                Origine = "",
                TipoDocumento = "",
                TipoDocumento_Descrizione = "",
                Warning = ""
            };
        }

        //registrazioneView meglio al posto di RegistrazionePropertiesType
        //navigationLimits = new AcarisOfficialBookServicePort.NavigationConditionInfoType
        //            {
        //limitToChildren = "si",
        //parentNodeId = //identificativo della serie
        //}
        //per cambiare fascicolo c'è un operazione dell' ObjectSerive ( moveDocument )
        public string GetIdProtocollo()
        {
            var client = new OfficialBookWSClient(this._configurazione.OfficialBookPortUrl, this._configurazione.AccessToken);
            using (var ws = client.CreaWebService())
            {
                using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                {
                    client.AggiungiTokenAContextScope();

                    var request = new Init.SIGePro.Protocollo.AcarisOfficialBookServicePort.query
                    {
                        repositoryId = new Init.SIGePro.Protocollo.AcarisOfficialBookServicePort.ObjectIdType { value = this._configurazione.RepositoryID.IdAcaris },
                        principalId = new Init.SIGePro.Protocollo.AcarisOfficialBookServicePort.PrincipalIdType { value = this._configurazione.PrincipalID.IdAcaris },
                        target = new Init.SIGePro.Protocollo.AcarisOfficialBookServicePort.QueryableObjectType
                        {
                            @object = "RegistrazionePropertiesType"
                        },
                        filter = new Init.SIGePro.Protocollo.AcarisOfficialBookServicePort.PropertyFilterType { filterType = Init.SIGePro.Protocollo.AcarisOfficialBookServicePort.enumPropertyFilter.all },
                        criteria = new Init.SIGePro.Protocollo.AcarisOfficialBookServicePort.QueryConditionType[]
                        {
                        new Init.SIGePro.Protocollo.AcarisOfficialBookServicePort.QueryConditionType
                        {
                            propertyName = "codice",
                            @operator = Init.SIGePro.Protocollo.AcarisOfficialBookServicePort.enumQueryOperator.equals,
                            value = this._numeroProtocollo
                        },
                        new Init.SIGePro.Protocollo.AcarisOfficialBookServicePort.QueryConditionType
                        {
                            propertyName = "dataProtocollo",
                            @operator = Init.SIGePro.Protocollo.AcarisOfficialBookServicePort.enumQueryOperator.greaterThanOrEqualTo,
                            value = $"01/01/{this._annoProtocollo}"
                        },
                        new Init.SIGePro.Protocollo.AcarisOfficialBookServicePort.QueryConditionType
                        {
                            propertyName = "dataProtocollo",
                            @operator = Init.SIGePro.Protocollo.AcarisOfficialBookServicePort.enumQueryOperator.lessThanOrEqualTo,
                            value = $"31/12/{this._annoProtocollo}"
                        }
                        }
                    };

                    this._serializer.LogAndValidate("IdProtocolloRequest.xml", request, $"Inizio chiamata a query con NumeroProtocollo: {this._numeroProtocollo}, AnnoProtocollo: {this._annoProtocollo}, {this._configurazione.IdAoo}, {this._configurazione.IdStruttura}, {this._configurazione.IdNodo}");

                    var response = ws.query(request);

                    this._serializer.LogAndValidate("IdProtocolloResponse.xml", response, $"Fine chiamata a query con NumeroProtocollo: {this._numeroProtocollo}, AnnoProtocollo: {this._annoProtocollo}, {this._configurazione.IdAoo}, {this._configurazione.IdStruttura}, {this._configurazione.IdNodo}");

                    if (response == null || response.@object == null || response.@object.objects?.Any() != true)
                    {
                        return null;
                    }
                    if (response.@object.objects.Length > 1)
                    {
                        throw new Exception($"Sono stati trovati {response.@object.objects.Count()} protocolli cercando con il numero {this._numeroProtocollo} per l'anno {this._annoProtocollo}");
                    }
                    return response.@object.objects[0].objectId.value;
                }
            }
        }

        public ObjectResponseType[] GetProperties()
        {
            if (String.IsNullOrEmpty(this._idProtocollo))
            {
                this._idProtocollo = this.GetIdProtocollo();
            }

            if (String.IsNullOrEmpty(this._idProtocollo))
            {
                throw new Exception("Il protocollo richiesto non è stato trovato");
            }

            return this.GetProtocolloProperties();
        }

        public ObjectResponseType[] GetProtocolloProperties()
        {
            var client = new ObjectWSClient(this._configurazione.ObjectPortUrl, this._configurazione.AccessToken);
            using (var ws = client.CreaWebService())
            {
                using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                {
                    client.AggiungiTokenAContextScope();

                    var request = new getPropertiesMassive
                    {
                        repositoryId = new ObjectIdType { value = this._configurazione.RepositoryID.IdAcaris },
                        principalId = new PrincipalIdType { value = this._configurazione.PrincipalID.IdAcaris },
                        identifiers = new ObjectIdType[]
                    {
                            new ObjectIdType
                            {
                                value = this._idProtocollo
                            }
                    },
                        filter = new PropertyFilterType { filterType = enumPropertyFilter.all }
                    };

                    this._serializer.LogAndValidate("LeggiProtocolloRequest.xml", request, "Inizio chiamata a getPropertiesMassive");

                    var response = ws.getPropertiesMassive(request);

                    this._serializer.LogAndValidate("LeggiProtocolloResponse.xml", response, "Fine chiamata a getPropertiesMassive");

                    return response;
                }
            }
        }

        private string ProperyValue(PropertyType[] props, string propertyName)
        {
            if (props == null)
            {
                return null;
            }

            return props
                    .Where(x => x.queryName.propertyName == propertyName)
                    .Select(y => y.value)
                    .FirstOrDefault()?
                    .FirstOrDefault();
        }
    }
}
