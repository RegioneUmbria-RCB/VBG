using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using Init.SIGePro.Protocollo.AcarisBackofficeServicePort;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity
{
    public class IdStruttura : IQueryObject
    {
        public int Id { get; }

        public string IdAcaris { get; }

        public IdStruttura()
        {
            //costruttore vuoto solamente per problemi di serializzazione
        }

        public IdStruttura(IProtocolloSerializer serializer, BackofficeWSClient client, RepositoryId repositoryId, PrincipalId principalId, int idStruttura)
        {
            this.Id = idStruttura;

            using (var ws = client.CreaWebService())
            {
                using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                {
                    client.AggiungiTokenAContextScope();

                    var request = new query
                    {
                        repositoryId = new ObjectIdType { value = repositoryId.IdAcaris },
                        principalId = new PrincipalIdType { value = principalId.IdAcaris },
                        target = new QueryableObjectType { @object = nameof(enumObjectType.StrutturaPropertiesType) },
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

                    serializer.LogAndValidate("IdStrutturaQueryRequest.xml", request, "Inizio chiamata a query");

                    var response = ws.query(request);

                    serializer.LogAndValidate("IdStrutturaQueryResponse.xml", response, "Fine chiamata a query");

                    this.IdAcaris = response.@object.objects[0].objectId.value;
                }
            }
        }

        public override string ToString()
        {
            return $"IdStruttura: [id={this.Id} idAcaris={this.IdAcaris}]";
        }
    }
}