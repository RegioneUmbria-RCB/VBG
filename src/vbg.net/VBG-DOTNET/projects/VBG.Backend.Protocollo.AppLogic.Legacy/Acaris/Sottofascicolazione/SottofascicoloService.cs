using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Folder;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using Init.SIGePro.Protocollo.AcarisObjectServicePort;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Sottofascicolazione
{
    public class SottofascicoloService
    {
        private readonly IProtocolloSerializer _serializer;
        private readonly ProtocolloLogs _logger;
        private readonly IFolderTypeResolver _resolver;

        public SottofascicoloService(IProtocolloSerializer serializer, ProtocolloLogs logger, IFolderTypeResolver resolver)
        {
            this._serializer = serializer;
            this._logger = logger;
            this._resolver = resolver;
        }

        public IdFolder GetSottofascicoloPerDescrizioneEIdFascicolo(enumFolderObjectType tipoFascicoloPropertyName, IdFolder idFolder, IDescrizioneSottofascicoloResolver descrizioneSottofascicoloResolver)
        {
            var client = new ObjectWSClient(this._resolver.ObjectPortUrl, this._resolver.AccessToken);

            using (var ws = client.CreaWebService())
            {
                using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                {
                    client.AggiungiTokenAContextScope();

                    var querySottofascicolo = new query
                    {
                        repositoryId = new ObjectIdType { value = this._resolver.RepositoryID.IdAcaris },
                        principalId = new PrincipalIdType { value = this._resolver.PrincipalID.IdAcaris },
                        target = new QueryableObjectType { @object = tipoFascicoloPropertyName.ToString() },
                        filter = new PropertyFilterType { filterType = enumPropertyFilter.all },
                        criteria = new QueryConditionType[]
                        {
                                new QueryConditionType
                                {
                                        propertyName = "descrizione",
                                        @operator = enumQueryOperator.like,
                                        value = descrizioneSottofascicoloResolver.Get()
                                }
                        },
                        navigationLimits = new NavigationConditionInfoType
                        {
                            parentNodeId = new ObjectIdType
                            {
                                value = idFolder.IdAcaris
                            },
                            limitToChildren = true,
                            limitToChildrenSpecified = true
                        }
                    };

                    this._serializer.LogAndValidate("IdSottofascicoloSuIdFascicoloQueryRequest.xml", querySottofascicolo, "Inizio chiamata a query");

                    var response = ws.query(querySottofascicolo);

                    this._serializer.LogAndValidate("IdSottofascicoloSuIdFascicoloQueryResponse.xml", response, "Fine chiamata a query");

                    if (response.@object.objects == null)
                    {
                        return null;
                    }

                    return new IdFolder(response.@object.objects[0].objectId.value);
                }
            }
        }

        public string CreaSottofascicolo(IdFolder parentIdFolder)
        {
            var client = new ObjectWSClient(this._resolver.ObjectPortUrl, this._resolver.AccessToken);
            using (var ws = client.CreaWebService())
            {
                using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                {
                    client.AggiungiTokenAContextScope();

                    var request = new createFolder
                    {
                        typeId = this._resolver.TypeId,
                        principalId = new PrincipalIdType { value = this._resolver.PrincipalID.IdAcaris },
                        repositoryId = new ObjectIdType { value = this._resolver.RepositoryID.IdAcaris },
                        properties = this._resolver.Properties,
                        folderId = new ObjectIdType { value = parentIdFolder.IdAcaris }
                    };

                    this._serializer.LogAndValidate("CreateFolderRequest.xml", request, "Inizio chiamata a createFolder");

                    var response = ws.createFolder(request);

                    this._serializer.LogAndValidate("CreateFolderResponse.xml", response, "Fine chiamata a createFolder");

                    return response.objectId.value;
                }
            }
        }
    }
}
