using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using Init.SIGePro.Protocollo.AcarisObjectServicePort;
using System.Collections.Generic;
using System;
using System.Linq;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity
{
    public class IdVoce : IIDAcaris
    {
        private static class Constants
        {
            internal const string _codiceParamName = "codice";
            internal const string _dbKeyTitolarioParamName = "dbKeyTitolario";
            internal const string _dbKeyPadreParamName = "dbKeyPadre";
            internal const string _dbKeyParamName = "dbKey";
            internal const string _primoLivelloParamName = "primoLivello";
        }

        public int Voce { get; }

        public string IdAcaris { get; }

        public int DbKey { get; }

        public IdVoce(IProtocolloSerializer serializer, ObjectWSClient client, RepositoryId repositoryId, PrincipalId principalId, int voce, int? idTitolario = null, int? vocePadre = null)
        {
            this.Voce = voce;

            using (var ws = client.CreaWebService())
            {
                using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                {
                    client.AggiungiTokenAContextScope();

                    List<QueryConditionType> criteri = new List<QueryConditionType>
                        {
                            new QueryConditionType
                            {
                                propertyName = Constants._codiceParamName,
                                @operator = enumQueryOperator.equals,
                                value = this.Voce.ToString()
                            },
                            new QueryConditionType
                            {
                                propertyName = Constants._primoLivelloParamName,
                                @operator = enumQueryOperator.equals,
                                value = vocePadre.HasValue ? "N" : "S"
                            }
                        };

                    if (idTitolario.HasValue)
                    {
                        criteri.Add(new QueryConditionType
                        {
                            propertyName = Constants._dbKeyTitolarioParamName,
                            @operator = enumQueryOperator.equals,
                            value = idTitolario.Value.ToString()
                        });
                    }

                    if (vocePadre.HasValue)
                    {
                        criteri.Add(new QueryConditionType
                        {
                            propertyName = Constants._dbKeyPadreParamName,
                            @operator = enumQueryOperator.equals,
                            value = vocePadre.Value.ToString()
                        });
                    }

                    var request = new query
                    {
                        repositoryId = new ObjectIdType { value = repositoryId.IdAcaris },
                        principalId = new PrincipalIdType { value = principalId.IdAcaris },
                        target = new QueryableObjectType { @object = nameof(enumObjectType.VocePropertiesType) },
                        filter = new PropertyFilterType { filterType = enumPropertyFilter.none },
                        criteria = criteri.ToArray(),
                        maxItems = 1
                    };

                    serializer.LogAndValidate("IdVoceQueryRequest.xml", request, "Inizio chiamata a query");

                    var response = ws.query(request);

                    serializer.LogAndValidate("IdVoceQueryResponse.xml", response, "Fine chiamata a query");

                    if (response.@object.objects == null)
                    {
                        throw new Exception("La classifica indicata è inesistente");
                    }

                    if (response.@object.objects.Length > 1)
                    {
                        throw new Exception($"Impossibile individuare in maniera univoca la classifica {voce} all'interno del titolario passato");
                    }

                    this.IdAcaris = response.@object.objects[0].objectId.value;
                    this.DbKey = response.@object.objects[0].properties
                                    .Where(x => x.queryName.propertyName == Constants._dbKeyParamName)
                                    .Select(x => Convert.ToInt32(x.value[0]))
                                    .FirstOrDefault();
                }
            }
        }

        public override string ToString()
        {
            return $"IdVoce: [voce={this.Voce} idAcaris={this.IdAcaris} dbKey={this.DbKey}]";
        }
    }
}
