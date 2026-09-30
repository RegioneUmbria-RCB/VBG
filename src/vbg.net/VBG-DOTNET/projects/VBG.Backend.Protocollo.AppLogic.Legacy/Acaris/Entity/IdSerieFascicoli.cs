using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using Init.SIGePro.Protocollo.AcarisObjectServicePort;
using System;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity
{
    public class IdSerieFascicoli : IIDAcaris
    {
        public string Codice { get; }
        public string IdAcaris { get; }

        private readonly IdVoceComposta _idVoce;

        public IdSerieFascicoli(IProtocolloSerializer serializer, ObjectWSClient client, RepositoryId repositoryId, PrincipalId principalId, string codiceSerie, IdVoceComposta idVoce)
        {
            this.Codice = codiceSerie;
            this._idVoce = idVoce;

            using (var ws = client.CreaWebService())
            {
                using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                {
                    client.AggiungiTokenAContextScope();

                    var request = new query
                    {
                        repositoryId = new ObjectIdType { value = repositoryId.IdAcaris },
                        principalId = new PrincipalIdType { value = principalId.IdAcaris },
                        target = new QueryableObjectType { @object = nameof(enumObjectType.SerieFascicoliPropertiesType) },
                        filter = new PropertyFilterType { filterType = enumPropertyFilter.none },
                        criteria = new QueryConditionType[]
                        {
                            new QueryConditionType
                            {
                                    propertyName = "codice",
                                    @operator = enumQueryOperator.equals,
                                    value = this.Codice
                            }
                        },
                        navigationLimits = new NavigationConditionInfoType
                        {
                            limitToChildren = true,
                            parentNodeId = new ObjectIdType { value = idVoce.IdAcaris }
                        },
                        maxItems = 1
                    };

                    serializer.LogAndValidate("IdSerieFascicoliQueryRequest.xml", request, "Inizio chiamata a query");

                    var response = ws.query(request);

                    serializer.LogAndValidate("IdSerieFascicoliQueryResponse.xml", response, "Fine chiamata a query");

                    if (response.@object.objects == null)
                    {
                        throw new Exception($"Impossibile trovare la serie di fascicoli chiamata {codiceSerie}");
                    }

                    this.IdAcaris = response.@object.objects[0].objectId.value;
                }
            }
        }

        public override string ToString()
        {
            return $"IdSerieFascicoli: [codice={this.Codice} voce={this._idVoce.Voce} idAcaris={this.IdAcaris}]";
        }
    }
}
