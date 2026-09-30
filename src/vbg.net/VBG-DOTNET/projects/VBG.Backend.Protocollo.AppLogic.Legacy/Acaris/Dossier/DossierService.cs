using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Folder;
using System.Configuration;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using Init.SIGePro.Protocollo.AcarisObjectServicePort;
using System;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Dossier
{
    internal class DossierService
    {
        private readonly IProtocolloSerializer _serializer;
        private readonly ProtocolloLogs _logger;
        private readonly IFolderTypeResolver _resolver;

        public DossierService(IProtocolloSerializer serializer, ProtocolloLogs logger, IFolderTypeResolver resolver)
        {
            this._serializer = serializer;
            this._logger = logger;
            this._resolver = resolver;
        }

        internal string CreaDossier(CreaDossierRequest creaDossierRequest)
        {
            var client = new ObjectWSClient(this._resolver.ObjectPortUrl, this._resolver.AccessToken);

            using (var ws = client.CreaWebService())
            {
                using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                {
                    client.AggiungiTokenAContextScope();

                    var properties = new DossierPropertiesType
                    {
                        aggiuntaOriClassificazioneDocumenti = true,
                        archivioCorrente = true,
                        codice = creaDossierRequest.CodiceDossier,
                        conservazioneCorrente = creaDossierRequest.AnniConservazione,
                        conservazioneGenerale = creaDossierRequest.AnniConservazioneGenerale,
                        creazioneFascicoli = true,
                        descrizione = creaDossierRequest.Descrizione,
                        dataCreazione = DateTime.Now,
                        datiPersonali = true,
                        datiSensibili = false,
                        datiRiservati = false,
                        idAOORespMat = creaDossierRequest.IdAOO,
                        idNodoRespMat = creaDossierRequest.IdNodo,
                        idStrutturaRespMat = creaDossierRequest.IdStruttura,
                        inserimentoDocumenti = true,
                        objectTypeId = enumArchiveObjectType.DossierPropertiesType,
                        paroleChiave = creaDossierRequest.ParolaChiave,
                        riclassificazioneFascicoli = false,
                        utenteCreazione = new CodiceFiscaleType { value = creaDossierRequest.IdentificativoUtente }
                    };

                    var request = new createFolder
                    {
                        typeId = enumFolderObjectType.DossierPropertiesType,
                        principalId = new PrincipalIdType { value = this._resolver.PrincipalID.IdAcaris },
                        repositoryId = new ObjectIdType { value = this._resolver.RepositoryID.IdAcaris },
                        properties = properties,
                        folderId = creaDossierRequest.SerieDossierId
                    };

                    this._serializer.LogAndValidate("CreateFolderDossierRequest.xml", request, "Inizio chiamata a createFolder per il dossier");

                    var response = ws.createFolder(request);

                    this._serializer.LogAndValidate("CreateFolderDossierResponse.xml", response, "Fine chiamata a createFolder per il dossier");

                    return response.objectId.value;
                }
            }
        }

        internal string RecuperaIdAcarisSerieDossierDaCodice(string serieDossier)
        {
            if (String.IsNullOrEmpty(serieDossier))
            {
                throw new ConfigurationErrorsException("Impossibile cercare la serie di dossier da utilizzare senza passare il codice della serie");
            }

            var client = new ObjectWSClient(this._resolver.ObjectPortUrl, this._resolver.AccessToken);
            using (var ws = client.CreaWebService())
            {
                using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                {
                    client.AggiungiTokenAContextScope();

                    var request = new query
                    {
                        repositoryId = new ObjectIdType { value = this._resolver.RepositoryID.IdAcaris },
                        principalId = new PrincipalIdType { value = this._resolver.PrincipalID.IdAcaris },
                        target = new QueryableObjectType { @object = nameof(enumObjectType.SerieDossierPropertiesType) },
                        filter = new PropertyFilterType { filterType = enumPropertyFilter.all },
                        criteria = new QueryConditionType[]
                        {
                            new QueryConditionType
                            {
                                    propertyName = "codice",
                                    @operator = enumQueryOperator.equals,
                                    value = serieDossier
                            }
                        },
                    };

                    this._logger.DebugFormat($"Inizio chiamata a query per id serie dossier {serieDossier}");
                    this._serializer.LogAndValidate("SerieDossierQueryRequest.xml", request);

                    var response = ws.query(request);

                    this._serializer.LogAndValidate("SerieDossierQueryResponse.xml", response);
                    this._logger.DebugFormat($"Fine chiamata a query per id serie dossier {serieDossier}");

                    if (response.@object.objects == null)
                    {
                        return null;
                    }

                    if (response.@object.objects.Length > 1)
                    {
                        throw new ConfigurationErrorsException($"Impossibile risalire univocamente alla serie di dossier con codice {serieDossier}");
                    }

                    return response.@object.objects[0].objectId.value;
                }
            }
        }

        internal string RicercaDossierPerCodice(string idSerieDossier, string codiceDossier)
        {
            var client = new ObjectWSClient(this._resolver.ObjectPortUrl, this._resolver.AccessToken);
            using (var ws = client.CreaWebService())
            {
                using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                {
                    client.AggiungiTokenAContextScope();

                    var request = new query
                    {
                        repositoryId = new ObjectIdType { value = this._resolver.RepositoryID.IdAcaris },
                        principalId = new PrincipalIdType { value = this._resolver.PrincipalID.IdAcaris },
                        target = new QueryableObjectType { @object = nameof(enumObjectType.DossierPropertiesType) },
                        filter = new PropertyFilterType { filterType = enumPropertyFilter.all },
                        criteria = new QueryConditionType[]
                        {
                            new QueryConditionType
                            {
                                    propertyName = "codice",
                                    @operator = enumQueryOperator.equals,
                                    value = codiceDossier
                            },
                            new QueryConditionType
                            {
                                    propertyName = "stato",
                                    @operator = enumQueryOperator.equals,
                                    value = "1"
                            },
                        },
                        navigationLimits = new NavigationConditionInfoType
                        {
                            parentNodeId = new ObjectIdType { value = idSerieDossier },
                            limitToChildren = true,
                            limitToChildrenSpecified = true
                        }
                    };

                    this._logger.DebugFormat($"Inizio chiamata a query per id dossier da codice {codiceDossier}");
                    this._serializer.LogAndValidate("IdDossierDaCodiceQueryRequest.xml", request);

                    var response = ws.query(request);

                    this._serializer.LogAndValidate("IdDossierDaCodiceQueryResponse.xml", response);
                    this._logger.DebugFormat($"Fine chiamata a query per id dossier da codice {codiceDossier}");

                    if (response.@object.objects == null)
                    {
                        return null;
                    }

                    if (response.@object.objects.Length > 1)
                    {
                        throw new ConfigurationErrorsException($"Impossibile risalire univocamente al dossier per con il codice {codiceDossier}");
                    }

                    return response.@object.objects[0].objectId.value;
                }
            }
        }
    }
}
