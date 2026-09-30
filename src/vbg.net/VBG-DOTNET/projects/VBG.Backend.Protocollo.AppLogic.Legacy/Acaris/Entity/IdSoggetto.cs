using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using Init.SIGePro.Protocollo.AcarisBackofficeServicePort;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity
{
    public class IdSoggetto : IQueryObject
    {
        public int Id { get; }

        public string IdAcaris { get; }

        public IdSoggetto(IProtocolloSerializer serializer, BackofficeWSClient client, RepositoryId repositoryId, PrincipalId principalId, int idSoggetto)
        {
            this.Id = idSoggetto;

            using (var ws = client.CreaWebService())
            {
                using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                {
                    client.AggiungiTokenAContextScope();

                    var request = new query
                    {
                        repositoryId = new ObjectIdType { value = repositoryId.IdAcaris },
                        principalId = new PrincipalIdType { value = principalId.IdAcaris },
                        target = new QueryableObjectType { @object = nameof(enumObjectType.SoggettoPropertiesType) },
                        filter = new PropertyFilterType { filterType = enumPropertyFilter.none },
                        criteria = new QueryConditionType[]
                        {
                                new QueryConditionType
                                {
                                        propertyName = "dbKey",
                                        @operator = enumQueryOperator.equals,
                                        value = this.Id.ToString()
                                }
                        },
                        maxItems = 1
                    };

                    serializer.LogAndValidate("IdSoggettoQueryRequest.xml", request, "Inizio chiamata a query");

                    var response = ws.query(request);

                    serializer.LogAndValidate("IdSoggettoQueryResponse.xml", response, "Fine chiamata a query");

                    this.IdAcaris = response.@object.objects[0].objectId.value;
                }
            }
        }

        public override string ToString()
        {
            return $"IdSoggetto: [id={this.Id} idAcaris={this.IdAcaris}]";
        }
    }
}
