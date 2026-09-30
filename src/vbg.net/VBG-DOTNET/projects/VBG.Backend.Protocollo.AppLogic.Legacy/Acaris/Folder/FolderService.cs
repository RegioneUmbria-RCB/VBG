using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Fascicolazione;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Protocollazione.Lettura;
using System.Configuration;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared;
using System;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using Init.SIGePro.Protocollo.AcarisObjectServicePort;
using Init.SIGePro.Protocollo.AcarisNavigationServicePort;
using PropertyType = Init.SIGePro.Protocollo.AcarisObjectServicePort.PropertyType;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Folder
{
    public class FolderService
    {
        private readonly IProtocolloSerializer _serializer;
        private readonly ProtocolloLogs _logger;
        private readonly IFolderTypeResolver _resolver;

        public FolderService(IProtocolloSerializer serializer, ProtocolloLogs logger, IFolderTypeResolver resolver)
        {
            this._serializer = serializer;
            this._logger = logger;
            this._resolver = resolver;
        }

        public createFolderResponse CreaFolder(string folderIdParent)
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
                        principalId = new Init.SIGePro.Protocollo.AcarisObjectServicePort.PrincipalIdType { value = this._resolver.PrincipalID.IdAcaris },
                        repositoryId = new Init.SIGePro.Protocollo.AcarisObjectServicePort.ObjectIdType { value = this._resolver.RepositoryID.IdAcaris },
                        properties = this._resolver.Properties,
                        folderId = new Init.SIGePro.Protocollo.AcarisObjectServicePort.ObjectIdType { value = folderIdParent }
                    };

                    this._serializer.LogAndValidate("CreateFolderRequest.xml", request, "Inizio chiamata a createFolder");

                    var response = ws.createFolder(request);

                    this._serializer.LogAndValidate("CreateFolderResponse.xml", response, "Fine chiamata a createFolder");

                    return response;
                }
            }
        }

        public createFolderResponse CreaFascicolo()
        {
            try
            {
                var wsClient = new ObjectWSClient(this._resolver.ObjectPortUrl, this._resolver.AccessToken);

                this._serializer.LogAndValidate("voce.xml", this._resolver.Voce);
                this._serializer.LogAndValidate("idtitolario.xml", this._resolver.IdTitolario);

                //recupero la voce padre
                var vocePadre = new IdVoceComposta(this._serializer, wsClient, this._resolver.RepositoryID, this._resolver.PrincipalID, this._resolver.Voce, this._resolver.IdTitolario);
                if (string.IsNullOrEmpty(vocePadre?.IdAcaris))
                {
                    return null;
                }

                //recupero la serie padre
                var seriePadre = new IdSerieFascicoli(this._serializer, wsClient, this._resolver.RepositoryID, this._resolver.PrincipalID, this._resolver.CodiceSerie, vocePadre);
                if (string.IsNullOrEmpty(seriePadre?.IdAcaris))
                {
                    return null;
                }

                //creo il folder
                return this.CreaFolder(seriePadre.IdAcaris);
            }
            catch (Exception ex)
            {
                throw new Exception($"Impossibile creare il fascicolo con le configurazioni attuali - {ex.StackTrace}");
            }
        }

        public IdFolder GetFolderPerDescrizioneEDossier(enumFolderObjectType tipoFascicoloPropertyName, string idDossier, string descrizioneFascicolo)
        {
            var client = new ObjectWSClient(this._resolver.ObjectPortUrl, this._resolver.AccessToken);

            //1. Verifico la presenza del dossier dell'azienda
            using (var ws = client.CreaWebService())
            {
                using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                {
                    client.AggiungiTokenAContextScope();

                    var queryDossier = new query
                    {
                        repositoryId = new Init.SIGePro.Protocollo.AcarisObjectServicePort.ObjectIdType { value = this._resolver.RepositoryID.IdAcaris },
                        principalId = new Init.SIGePro.Protocollo.AcarisObjectServicePort.PrincipalIdType { value = this._resolver.PrincipalID.IdAcaris },
                        target = new QueryableObjectType { @object = tipoFascicoloPropertyName.ToString() },
                        filter = new Init.SIGePro.Protocollo.AcarisObjectServicePort.PropertyFilterType { filterType = Init.SIGePro.Protocollo.AcarisObjectServicePort.enumPropertyFilter.all },
                        criteria = new QueryConditionType[]
                        {
                                new QueryConditionType
                                {
                                        propertyName = "oggetto",
                                        @operator = enumQueryOperator.like,
                                        value = descrizioneFascicolo
                                }
                        },
                        navigationLimits = new NavigationConditionInfoType
                        {
                            parentNodeId = new Init.SIGePro.Protocollo.AcarisObjectServicePort.ObjectIdType
                            {
                                value = idDossier
                            },
                            limitToChildren = true,
                            limitToChildrenSpecified = true
                        }
                    };

                    this._serializer.LogAndValidate("IdFascicoloSuDossierQueryRequest.xml", queryDossier, "Inizio chiamata a query");

                    var response = ws.query(queryDossier);

                    this._serializer.LogAndValidate("IdFascicoloSuDossierQueryResponse.xml", response, "Fine chiamata a query");

                    if (response.@object.objects == null)
                    {
                        return null;
                    }

                    return new IdFolder(response.@object.objects[0].objectId.value);
                }
            }
        }

        public IdFolder GetFolderPerDescrizioneESerieFascicoli(enumFolderObjectType tipoFascicoloPropertyName, string idSerieFascicoli, DescrizioneFascicoloResolver descrizioneFascicoloResolver)
        {
            var client = new ObjectWSClient(this._resolver.ObjectPortUrl, this._resolver.AccessToken);

            using (var ws = client.CreaWebService())
            {
                using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                {
                    client.AggiungiTokenAContextScope();

                    var querySerieFascicoli = new query
                    {
                        repositoryId = new Init.SIGePro.Protocollo.AcarisObjectServicePort.ObjectIdType { value = this._resolver.RepositoryID.IdAcaris },
                        principalId = new Init.SIGePro.Protocollo.AcarisObjectServicePort.PrincipalIdType { value = this._resolver.PrincipalID.IdAcaris },
                        target = new QueryableObjectType { @object = tipoFascicoloPropertyName.ToString() },
                        filter = new Init.SIGePro.Protocollo.AcarisObjectServicePort.PropertyFilterType { filterType = Init.SIGePro.Protocollo.AcarisObjectServicePort.enumPropertyFilter.all },
                        criteria = new QueryConditionType[]
                        {
                                new QueryConditionType
                                {
                                        propertyName = "oggetto",
                                        @operator = enumQueryOperator.like,
                                        value = descrizioneFascicoloResolver.Get()
                                }
                        },
                        navigationLimits = new NavigationConditionInfoType
                        {
                            parentNodeId = new Init.SIGePro.Protocollo.AcarisObjectServicePort.ObjectIdType
                            {
                                value = idSerieFascicoli
                            },
                            limitToChildren = true,
                            limitToChildrenSpecified = true
                        }
                    };

                    this._serializer.LogAndValidate("IdFascicoloSuSerieFascicoliQueryRequest.xml", querySerieFascicoli, "Inizio chiamata a query");

                    var response = ws.query(querySerieFascicoli);

                    this._serializer.LogAndValidate("IdFascicoloSuSerieFascicoliQueryResponse.xml", response, "Fine chiamata a query");

                    if (response.@object.objects == null)
                    {
                        return null;
                    }

                    return new IdFolder(response.@object.objects[0].objectId.value);
                }
            }
        }

        public IdFolder GetFolderPerCodiceEDossier(enumFolderObjectType tipoFascicoloPropertyName, string idDossier, string numero)
        {
            var client = new ObjectWSClient(this._resolver.ObjectPortUrl, this._resolver.AccessToken);

            //1. Verifico la presenza del dossier dell'azienda
            using (var ws = client.CreaWebService())
            {
                using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                {
                    client.AggiungiTokenAContextScope();

                    var queryDossier = new query
                    {
                        repositoryId = new Init.SIGePro.Protocollo.AcarisObjectServicePort.ObjectIdType { value = this._resolver.RepositoryID.IdAcaris },
                        principalId = new Init.SIGePro.Protocollo.AcarisObjectServicePort.PrincipalIdType { value = this._resolver.PrincipalID.IdAcaris },
                        target = new QueryableObjectType { @object = tipoFascicoloPropertyName.ToString() },
                        filter = new Init.SIGePro.Protocollo.AcarisObjectServicePort.PropertyFilterType { filterType = Init.SIGePro.Protocollo.AcarisObjectServicePort.enumPropertyFilter.all },
                        criteria = new QueryConditionType[]
                        {
                                new QueryConditionType
                                {
                                        propertyName = "codice",
                                        @operator = enumQueryOperator.equals,
                                        value = numero
                                }
                        },
                        navigationLimits = new NavigationConditionInfoType
                        {
                            parentNodeId = new Init.SIGePro.Protocollo.AcarisObjectServicePort.ObjectIdType
                            {
                                value = idDossier
                            },
                            limitToChildren = true,
                            limitToChildrenSpecified = true
                        }
                    };

                    this._serializer.LogAndValidate("IdFascicoloSuDossierQueryRequest.xml", queryDossier, "Inizio chiamata a query");

                    var response = ws.query(queryDossier);

                    this._serializer.LogAndValidate("IdFascicoloSuDossierQueryResponse.xml", response, "Fine chiamata a query");

                    if (response.@object.objects == null)
                    {
                        return null;
                    }

                    return new IdFolder(response.@object.objects[0].objectId.value);
                }
            }
        }

        public IdFolder GetFolderDaIdProtocollo(ProtocolloBase protocollo, ParametriRegoleInfo config, String idProtocollo)
        {
            var leggiProtocolloRequest = Protocollazione.Lettura.LeggiProtocolloRequest.FromIdProtocollo(protocollo, idProtocollo);

            var leggiProtocolloResponse = new LeggiProtocolloService(this._serializer, config, leggiProtocolloRequest).GetProtocolloProperties() ?? throw new Exception("Il protocollo non esiste");

            var leggiProtocolloProperties = leggiProtocolloResponse
                                       .FirstOrDefault()?
                                       .properties;

            var idClassificazione = this.PropertyValue(leggiProtocolloProperties, "idClassificazione");

            var folderDaClassificazioneResponse = this.GetFolderDaClassificazione(idClassificazione);

            return new IdFolder(folderDaClassificazioneResponse.@object.objectId.value);
        }

        public IdFolder GetFolderDaEstremiProtocollo(ProtocolloBase protocollo, ParametriRegoleInfo config, string numeroProtocollo, int annoProtocollo)
        {
            var leggiProtocolloRequest = Protocollazione.Lettura.LeggiProtocolloRequest.FromEstremiProtocollo(protocollo, numeroProtocollo, annoProtocollo);

            var idProtocollo = new LeggiProtocolloService(this._serializer, config, leggiProtocolloRequest).GetIdProtocollo();

            return this.GetFolderDaIdProtocollo(protocollo, config, idProtocollo);
        }

        public DatiProtocolloFascicolatoResponseType GetDatiFascicolo(ParametriRegoleInfo config, GetDatiFascicoloRequest request)
        {
            //1.Effettuo la lettura del protocollo
            var leggiProtocolloResponse = new LeggiProtocolloService(this._serializer, config, request.ToLeggiProtocolloRequest()).GetProtocolloProperties() ?? throw new Exception("Il protocollo non esiste");

            var leggiProtocolloProperties = leggiProtocolloResponse
                                      .FirstOrDefault()?
                                      .properties;

            var idClassificazione = this.PropertyValue(leggiProtocolloProperties, "idClassificazione");

            var response = this.GetFolderDaClassificazione(idClassificazione);

            if (response == null || response.@object == null || response.@object.properties?.Any() != true)
            {
                return new DatiProtocolloFascicolatoResponseType
                {
                    Fascicolato = EnumFascicolatoType.no
                };
            }

            var folderProperties = response.@object.properties;

            return new DatiProtocolloFascicolatoResponseType
            {
                AnnoFascicolo = this.PropertyValue(folderProperties, "dataCreazione").Substring(0, 4),
                Classifica = this.PropertyValue(folderProperties, "indiceClassificazioneEstesa"),
                DataFascicolo = $"{this.PropertyValue(folderProperties, "dataCreazione").Substring(8, 2)}/{this.PropertyValue(folderProperties, "dataCreazione").Substring(5, 2)}/{this.PropertyValue(folderProperties, "dataCreazione").Substring(0, 4)}",
                Fascicolato = EnumFascicolatoType.si,
                NumeroFascicolo = this.PropertyValue(folderProperties, "codice"),
                Oggetto = this.PropertyValue(folderProperties, "oggetto"),
                NoteFascicolo = this.PropertyValue(folderProperties, "descrizione"),
            };
        }

        private getFolderParentResponse GetFolderDaClassificazione(string idClassificazione)
        {
            var client = new NavigationWSClient(this._resolver.NavigationPortUrl, this._resolver.AccessToken);

            using (var ws = client.CreaWebService())
            {
                using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                {
                    client.AggiungiTokenAContextScope();

                    var request = new getFolderParent
                    {
                        repositoryId = new Init.SIGePro.Protocollo.AcarisNavigationServicePort.ObjectIdType { value = this._resolver.RepositoryID.IdAcaris },
                        principalId = new Init.SIGePro.Protocollo.AcarisNavigationServicePort.PrincipalIdType { value = this._resolver.PrincipalID.IdAcaris },
                        folderId = new Init.SIGePro.Protocollo.AcarisNavigationServicePort.ObjectIdType { value = idClassificazione },
                        filter = new Init.SIGePro.Protocollo.AcarisNavigationServicePort.PropertyFilterType { filterType = Init.SIGePro.Protocollo.AcarisNavigationServicePort.enumPropertyFilter.all }
                    };

                    this._serializer.LogAndValidate("getFolderParentRequest.xml", request, "Inizio chiamata a getFolderParent");

                    var response = ws.getFolderParent(request);

                    this._serializer.LogAndValidate("getFolderParentResponse.xml", response, "Fine chiamata a getFolderParent");

                    if (!String.IsNullOrEmpty(response?.@object?.objectId?.value))
                    {
                        return response;
                    }
                }
            }

            return null;
        }

        internal string RecuperaIdAcarisSerieFascicoliDaCodice(string serieFascicoli)
        {
            if (String.IsNullOrEmpty(serieFascicoli))
            {
                throw new ConfigurationErrorsException("Impossibile cercare la serie di fascicoli da utilizzare senza passare il codice della serie");
            }

            var client = new ObjectWSClient(this._resolver.ObjectPortUrl, this._resolver.AccessToken);
            using (var ws = client.CreaWebService())
            {
                using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                {
                    client.AggiungiTokenAContextScope();

                    var request = new query
                    {
                        repositoryId = new Init.SIGePro.Protocollo.AcarisObjectServicePort.ObjectIdType { value = this._resolver.RepositoryID.IdAcaris },
                        principalId = new Init.SIGePro.Protocollo.AcarisObjectServicePort.PrincipalIdType { value = this._resolver.PrincipalID.IdAcaris },
                        target = new QueryableObjectType { @object = nameof(Init.SIGePro.Protocollo.AcarisObjectServicePort.enumObjectType.SerieFascicoliPropertiesType) },
                        filter = new Init.SIGePro.Protocollo.AcarisObjectServicePort.PropertyFilterType { filterType = Init.SIGePro.Protocollo.AcarisObjectServicePort.enumPropertyFilter.all },
                        criteria = new QueryConditionType[]
                        {
                            new QueryConditionType
                            {
                                    propertyName = "codice",
                                    @operator = enumQueryOperator.equals,
                                    value = serieFascicoli
                            }
                        },
                    };

                    this._logger.DebugFormat($"Inizio chiamata a query per id serie fascicoli {serieFascicoli}");
                    this._serializer.LogAndValidate("SerieFascicoliQueryRequest.xml", request);

                    var response = ws.query(request);

                    this._serializer.LogAndValidate("SerieFascicoliQueryResponse.xml", response);
                    this._logger.DebugFormat($"Fine chiamata a query per id serie fascicoli {serieFascicoli}");

                    if (response.@object.objects == null)
                    {
                        return null;
                    }

                    if (response.@object.objects.Length > 1)
                    {
                        throw new ConfigurationErrorsException($"Impossibile risalire univocamente alla serie di fascicoli con codice {serieFascicoli}");
                    }

                    return response.@object.objects[0].objectId.value;
                }
            }
        }

        private string PropertyValue(PropertyType[] props, string propertyName)
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

        private string PropertyValue(Init.SIGePro.Protocollo.AcarisNavigationServicePort.PropertyType[] props, string propertyName)
        {
            return props
                    .Where(x => x.queryName.propertyName == propertyName)
                    .Select(y => y.value)
                    .FirstOrDefault()?
                    .FirstOrDefault();
        }
    }
}
