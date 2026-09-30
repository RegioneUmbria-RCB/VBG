using Init.SIGePro.Protocollo.AcarisDocumentServicePort;
using Init.SIGePro.Protocollo.AcarisManagementServicePort;
using Init.SIGePro.Protocollo.AcarisNavigationServicePort;
using Init.SIGePro.Protocollo.AcarisObjectServicePort;
using Init.SIGePro.Protocollo.AcarisRelationshipsServicePort;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Document;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Allegati
{
    public class AllegatiAcarisService
    {
        private readonly IProtocolloSerializer _serializer;
        private readonly ParametriRegoleInfo _configurazione;

        public AllegatiAcarisService(IProtocolloSerializer serializer, ParametriRegoleInfo configurazione)
        {
            this._serializer = serializer;
            this._configurazione = configurazione;
        }

        public List<AllegatoAcaris> GetAllegati(string idClassificazionePrincipale)
        {
            var retVal = new List<AllegatoAcaris>();

            //allegato principale
            var docPrincipaleClassificazione = this.GetDocumentoDaClassificazione(idClassificazionePrincipale) ?? throw new Exception("Impossibile risalire all'allegato principale");
            var idDocPrincipale = Array.Find(docPrincipaleClassificazione.response
                                    .objects, x => x.properties
                                                   .Any(y => y.queryName.className == "DocumentoSemplicePropertiesType")
                                    )
                                    .objectId
                                    .value;

            var idDocumentoFisicoPrincipale = this.GetIdDocumentoFisico(idDocPrincipale);

            var contenutoFisicoPrincipale = this.GetContenutoFisico(idDocumentoFisicoPrincipale);

            retVal.Add(new AllegatoAcaris
            (
                idDocumentoFisicoPrincipale,
                this.ProperyValue(contenutoFisicoPrincipale.response.objects.FirstOrDefault()?.properties, "contentStreamFilename"),
                this.ProperyValue(contenutoFisicoPrincipale.response.objects.FirstOrDefault()?.properties, "contentStreamMimeType")
            ));

            //altri allegati
            var idGruppoAllegati = Array.Find(docPrincipaleClassificazione.response
                                    .objects, x => x.properties
                                                   .Any(y => y.queryName.className == "GruppoAllegatiPropertiesType")
                                    )?
                                    .objectId
                                    .value;

            var descendants = this.GetDescendants(idGruppoAllegati);

            if (descendants != null)
            {
                foreach (var desc in descendants.objects.objects)
                {
                    var idClassificazione = desc.objectId.value;
                    var docClassificazione = this.GetDocumentoDaClassificazione(idClassificazione) ?? throw new Exception("Impossibile risalire all'allegato secondario");
                    var idDocumento = Array.Find(docClassificazione.response
                                            .objects, x => x.properties
                                                            .Any(y => y.queryName.className == "DocumentoSemplicePropertiesType")
                                            )
                                            .objectId
                                            .value;
                    var idDocumentoFisico = this.GetIdDocumentoFisico(idDocumento);
                    var contenutoFisico = this.GetContenutoFisico(idDocumentoFisico);

                    retVal.Add(new AllegatoAcaris(

                        idDocumentoFisico,
                        this.ProperyValue(contenutoFisico.response.objects.FirstOrDefault()?.properties, "contentStreamFilename"),
                        this.ProperyValue(contenutoFisico.response.objects.FirstOrDefault()?.properties, "contentStreamMimeType")
                    ));
                }
            }

            return retVal;
        }

        private getChildrenResponse GetDocumentoDaClassificazione(string idClassificazione)
        {
            var client = new NavigationWSClient(this._configurazione.NavigationPortUrl, this._configurazione.AccessToken);
            using (var ws = client.CreaWebService())
            {
                using (var scope = new OperationContextScope(ws.InnerChannel))
                {
                    client.AggiungiTokenAContextScope();

                    var request = new getChildren
                    {
                        repositoryId = new Init.SIGePro.Protocollo.AcarisNavigationServicePort.ObjectIdType { value = this._configurazione.RepositoryID.IdAcaris },
                        principalId = new Init.SIGePro.Protocollo.AcarisNavigationServicePort.PrincipalIdType { value = this._configurazione.PrincipalID.IdAcaris },
                        folderId = new Init.SIGePro.Protocollo.AcarisNavigationServicePort.ObjectIdType { value = idClassificazione },
                        filter = new Init.SIGePro.Protocollo.AcarisNavigationServicePort.PropertyFilterType { filterType = Init.SIGePro.Protocollo.AcarisNavigationServicePort.enumPropertyFilter.none }
                    };

                    this._serializer.LogAndValidate("GetDocumentoDaClassificazioneRequest.xml", request, "GetDocumentoDaClassificazione: Inizio chiamata a getChildren");

                    var response = ws.getChildren(request);

                    this._serializer.LogAndValidate("GetDocumentoDaClassificazioneResponse.xml", response, "GetDocumentoDaClassificazione: Fine chiamata a getChildren");

                    if (response?.response?.objects?.Any() == true)
                    {
                        return response;
                    }
                }
            }

            return null;
        }

        private string GetIdDocumentoFisico(string idDocumento)
        {
            var client = new RelationshipsWSClient(this._configurazione.RelationshipsPortUrl, this._configurazione.AccessToken);

            using (var ws = client.CreaWebService())
            {
                using (var scope = new OperationContextScope(ws.InnerChannel))
                {
                    client.AggiungiTokenAContextScope();

                    var request = new getObjectRelationships
                    {
                        repositoryId = new Init.SIGePro.Protocollo.AcarisRelationshipsServicePort.ObjectIdType { value = this._configurazione.RepositoryID.IdAcaris },
                        principalId = new Init.SIGePro.Protocollo.AcarisRelationshipsServicePort.PrincipalIdType { value = this._configurazione.PrincipalID.IdAcaris },
                        objectId = new Init.SIGePro.Protocollo.AcarisRelationshipsServicePort.ObjectIdType { value = idDocumento },
                        typeId = Init.SIGePro.Protocollo.AcarisRelationshipsServicePort.enumRelationshipObjectType.DocumentCompositionPropertiesType,
                        typeIdSpecified = true,
                        direction = enumRelationshipDirectionType.source,
                        directionSpecified = true,
                        filter = new Init.SIGePro.Protocollo.AcarisRelationshipsServicePort.PropertyFilterType { filterType = Init.SIGePro.Protocollo.AcarisRelationshipsServicePort.enumPropertyFilter.all }
                    };

                    this._serializer.LogAndValidate("GetDocumentoFisicoRequest.xml", request, "GetIdDocumentoFisico: Inizio chiamata a getObjectRelationships");

                    var response = ws.getObjectRelationships(request);

                    this._serializer.LogAndValidate("GetDocumentoFisicoResponse.xml", response, "GetIdDocumentoFisico: Fine chiamata a getObjectRelationships");

                    if (response?.Any() == true)
                    {
                        return response[0].targetId.value;
                    }
                }
            }

            return null;
        }

        public getChildrenResponse GetContenutoFisico(string idDocumentoFisico)
        {
            var client = new NavigationWSClient(this._configurazione.NavigationPortUrl, this._configurazione.AccessToken);

            using (var ws = client.CreaWebService())
            {
                using (var scope = new OperationContextScope(ws.InnerChannel))
                {
                    client.AggiungiTokenAContextScope();

                    var request = new getChildren
                    {
                        repositoryId = new Init.SIGePro.Protocollo.AcarisNavigationServicePort.ObjectIdType { value = this._configurazione.RepositoryID.IdAcaris },
                        principalId = new Init.SIGePro.Protocollo.AcarisNavigationServicePort.PrincipalIdType { value = this._configurazione.PrincipalID.IdAcaris },
                        folderId = new Init.SIGePro.Protocollo.AcarisNavigationServicePort.ObjectIdType { value = idDocumentoFisico },
                        filter = new Init.SIGePro.Protocollo.AcarisNavigationServicePort.PropertyFilterType
                        {
                            filterType = Init.SIGePro.Protocollo.AcarisNavigationServicePort.enumPropertyFilter.list,
                            propertyList = new Init.SIGePro.Protocollo.AcarisNavigationServicePort.QueryNameType[]
                            {
                                new Init.SIGePro.Protocollo.AcarisNavigationServicePort.QueryNameType
                                {
                                    className = "ContenutoFisicoPropertiesType",
                                    propertyName = "contentStreamFilename"
                                },
                                new Init.SIGePro.Protocollo.AcarisNavigationServicePort.QueryNameType
                                {
                                    className = "ContenutoFisicoPropertiesType",
                                    propertyName = "contentStreamMimeType"
                                }
                            }
                        }
                    };

                    this._serializer.LogAndValidate("GetContenutoFisicoRequest.xml", request, "GetContenutoFisico: Inizio chiamata a getChildren");

                    var response = ws.getChildren(request);

                    this._serializer.LogAndValidate("GetContenutoFisicoiResponse.xml", response, "GetContenutoFisico: Fine chiamata a getChildren");

                    if (response?.response?.objects?.Any() == true)
                    {
                        return response;
                    }
                }
            }

            throw new Exception($"Non è stato trovato il contenuto fisico per l'allegato con id {idDocumentoFisico}");
        }

        public Init.SIGePro.Protocollo.AcarisObjectServicePort.acarisContentStreamType GetAllegato(string idDocumentoFisico)
        {
            var client = new ObjectWSClient(this._configurazione.ObjectPortUrl, this._configurazione.AccessToken);

            using (var ws = client.CreaWebService())
            {
                using (var scope = new OperationContextScope(ws.InnerChannel))
                {
                    client.AggiungiTokenAContextScope();

                    var request = new getContentStream
                    {
                        repositoryId = new Init.SIGePro.Protocollo.AcarisObjectServicePort.ObjectIdType { value = this._configurazione.RepositoryID.IdAcaris },
                        principalId = new Init.SIGePro.Protocollo.AcarisObjectServicePort.PrincipalIdType { value = this._configurazione.PrincipalID.IdAcaris },
                        documentId = new Init.SIGePro.Protocollo.AcarisObjectServicePort.ObjectIdType { value = idDocumentoFisico },
                        streamId = Init.SIGePro.Protocollo.AcarisObjectServicePort.enumStreamId.primary,
                        streamIdSpecified = true
                    };

                    this._serializer.LogAndValidate("GetAllegatoRequest.xml", request, "GetAllegato: Inizio chiamata a getContentStream");

                    var response = ws.getContentStream(request);

                    this._serializer.LogAndValidate("GetAllegatoResponse.xml", response, "GetAllegato: Fine chiamata a getContentStream");

                    if (response?.Any() == true)
                    {
                        return response?[0];
                    }
                }
            }

            throw new Exception($"Non è stato trovato l'allegato con id {idDocumentoFisico}");
        }

        private getDescendantsResponse GetDescendants(string idGruppoAllegati)
        {
            try
            {
                var client = new NavigationWSClient(this._configurazione.NavigationPortUrl, this._configurazione.AccessToken);
                using (var ws = client.CreaWebService())
                {
                    using (var scope = new OperationContextScope(ws.InnerChannel))
                    {
                        client.AggiungiTokenAContextScope();

                        var request = new getDescendants
                        {
                            repositoryId = new Init.SIGePro.Protocollo.AcarisNavigationServicePort.ObjectIdType { value = this._configurazione.RepositoryID.IdAcaris },
                            principalId = new Init.SIGePro.Protocollo.AcarisNavigationServicePort.PrincipalIdType { value = this._configurazione.PrincipalID.IdAcaris },
                            folderId = new Init.SIGePro.Protocollo.AcarisNavigationServicePort.ObjectIdType { value = idGruppoAllegati },
                            filter = new Init.SIGePro.Protocollo.AcarisNavigationServicePort.PropertyFilterType
                            {
                                filterType = Init.SIGePro.Protocollo.AcarisNavigationServicePort.enumPropertyFilter.none
                            }
                        };

                        this._serializer.LogAndValidate("GetDescendantsRequest.xml", request, "GetDescendants: Inizio chiamata a getDescendants");

                        var response = ws.getDescendants(request);

                        this._serializer.LogAndValidate("GetDescendantsResponse.xml", response, "GetDescendants: Fine chiamata a getDescendants");

                        if (response?.objects?.objects?.Any() == true)
                        {
                            return response;
                        }
                    }
                }
            }
            catch (Exception)
            {
                //non deve sollevare eccezioni   
            }

            return null;
        }

        private string ProperyValue(Init.SIGePro.Protocollo.AcarisNavigationServicePort.PropertyType[] props, string propertyName)
        {
            return props
                    .Where(x => x.queryName.propertyName == propertyName)
                    .Select(y => y.value)
                    .FirstOrDefault()?
                    .FirstOrDefault();
        }

        public creaDocumentoResponse CaricaAllegatoPrincipale(IDocumentResolver resolver, IdFolder idFolder, AllegatoAcaris allegato)
        {
            var client = new DocumentWSClient(this._configurazione.DocumentPortUrl, this._configurazione.AccessToken);

            using (var ws = client.CreaWebService())
            {
                using (var scope = new OperationContextScope(ws.InnerChannel))
                {
                    client.AggiungiTokenAContextScope();

                    var documentoArchivisticoIRC = new DocumentFactory(this._serializer, resolver, idFolder).GetDocumento(allegato, true);

                    var request = new creaDocumento
                    {
                        repositoryId = new Init.SIGePro.Protocollo.AcarisDocumentServicePort.ObjectIdType { value = resolver.RepositoryId.IdAcaris },
                        principalId = new Init.SIGePro.Protocollo.AcarisDocumentServicePort.PrincipalIdType { value = resolver.PrincipalId.IdAcaris },
                        datiCreazione = documentoArchivisticoIRC.Documento,
                        tipoOperazione = enumTipoOperazione.elettronico
                    };

                    this._serializer.LogAndValidate("CreaDocumentoPrincipaleRequest.xml", request, "Inizio chiamata a creaDocumento (principale)");

                    var response = ws.creaDocumento(request);

                    this._serializer.LogAndValidate("CreaDocumentoPrincipaleResponse.xml", response, "Fine chiamata a creaDocumento (principale)");


                    if (this._configurazione.AnnotaAllegatoPrincipale && response?.info?.objectIdClassificazione != null)
                    {
                        foreach (var annotazione in documentoArchivisticoIRC.Annotazioni)
                        {
                            this.AggiungiAnnotazioneDocumento(resolver, response.info.objectIdDocumento.value, annotazione);
                        }
                    }

                    return response;
                }
            }

            throw new Exception("Errore durante il caricamento dell'allegato principale, nessuna configurazione ne permette il caricamento.");
        }

        public List<creaDocumentoResponse> CaricaAllegatoSecondario(IDocumentResolver resolver, Init.SIGePro.Protocollo.AcarisDocumentServicePort.ObjectIdType idClassificazione, AllegatoAcaris allegato)
        {
            var client = new DocumentWSClient(this._configurazione.DocumentPortUrl, this._configurazione.AccessToken);

            using (var ws = client.CreaWebService())
            {
                using (var scope = new OperationContextScope(ws.InnerChannel))
                {
                    client.AggiungiTokenAContextScope();

                    var documentoArchivisticoIRC = new DocumentFactory(this._serializer, resolver, idClassificazione).GetDocumento(allegato, false);

                    var request = new creaDocumento
                    {
                        repositoryId = new Init.SIGePro.Protocollo.AcarisDocumentServicePort.ObjectIdType { value = resolver.RepositoryId.IdAcaris },
                        principalId = new Init.SIGePro.Protocollo.AcarisDocumentServicePort.PrincipalIdType { value = resolver.PrincipalId.IdAcaris },
                        datiCreazione = documentoArchivisticoIRC.Documento,
                        tipoOperazione = enumTipoOperazione.elettronico
                    };

                    this._serializer.LogAndValidate("CreaDocumentoSecondarioRequest.xml", request, "Inizio chiamata a creaDocumento (secondario)");

                    var response = ws.creaDocumento(request);

                    this._serializer.LogAndValidate("CreaDocumentoSecondarioResponse.xml", response, "Fine chiamata a creaDocumento (secondario)");


                    if (this._configurazione.AnnotaAllegatoSecondario && response.info?.objectIdDocumento != null)
                    {
                        foreach (var annotazione in documentoArchivisticoIRC.Annotazioni)
                        {
                            this.AggiungiAnnotazioneDocumento(resolver, response.info.objectIdDocumento.value, annotazione);
                        }
                    }
                    return new List<creaDocumentoResponse>() { response };
                }
            }

            throw new Exception("Impossibile caricare l'allegato secondario");
        }
        private void AggiungiAnnotazioneDocumento(IDocumentResolver resolver, String idDocumentoAcaris, Annotazione annotazione)
        {
            var client = new ManagementWSClient(this._configurazione.ManagementPortUrl, this._configurazione.AccessToken);

            using (var ws = client.CreaWebService())
            {
                using (var scope = new OperationContextScope(ws.InnerChannel))
                {
                    client.AggiungiTokenAContextScope();

                    var request = new addAnnotazioni
                    {
                        repositoryId = new Init.SIGePro.Protocollo.AcarisManagementServicePort.ObjectIdType { value = resolver.RepositoryId.IdAcaris },
                        principalId = new Init.SIGePro.Protocollo.AcarisManagementServicePort.PrincipalIdType { value = resolver.PrincipalId.IdAcaris },
                        objectId = new Init.SIGePro.Protocollo.AcarisManagementServicePort.ObjectIdType { value = idDocumentoAcaris },
                        annotazioni = new Init.SIGePro.Protocollo.AcarisManagementServicePort.AnnotazioniPropertiesType
                        {
                            annotazioneFormale = annotazione.Formale,
                            data = DateTime.Now,
                            descrizione = annotazione.Testo
                        }
                    };

                    this._serializer.LogAndValidate("AddAnnotazioniRequest.xml", request, "Inizio chiamata a addAnnotazioni");

                    var response = ws.addAnnotazioni(request);

                    this._serializer.LogAndValidate("AddAnnotazioniResponse.xml", response, "Fine chiamata a addAnnotazioni");
                }
            }
        }
    }
}
