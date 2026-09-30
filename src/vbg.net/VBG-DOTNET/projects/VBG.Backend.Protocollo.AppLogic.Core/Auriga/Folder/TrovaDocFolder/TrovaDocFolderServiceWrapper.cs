using VBG.Shared.Infrastructure.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using static VBG.Backend.Protocollo.AppLogic.Core.Auriga.Helper.CommonColumns;

namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga.Folder.TrovaDocFolder
{
    public class TrovaDocFolderServiceWrapper : ProxyServiceWrapper
    {
        private static class Constants
        {
            public const string Titolo = "RICERCA FASCICOLI";
            public const string NameSpace = @"http://trovadocfolder.webservices.repository2.auriga.eng.it";
            public const string ServiceName = "WSTrovaDocFolder";
            public const string RequestServiceName = "trov";
            public const string TipoOggettiDaCercareFolder = "F";
            public const string TipoOggettiDaCercareDocument = "D";
            public static readonly string[] LimitaEstrazioneAlCampoDefault =
            {
                ((int)DocumentField.FlagFU).ToString(),
                ((int)DocumentField.IdUnitOrFolder).ToString(),
                ((int)DocumentField.RegistrationDetails).ToString(),
                ((int)DocumentField.Assignees).ToString(),
                //////////////////
                ((int)DocumentField.GeneralProtocolDetails).ToString(),
                ((int)DocumentField.GeneralProtocolTimestamp).ToString(),
                ((int)DocumentField.Description).ToString(),
                ((int)DocumentField.ExternalNames).ToString(),
                ((int)DocumentField.OriginType).ToString(),
                ((int)DocumentField.CreationTimestamp).ToString(),
            };
        }

        public TrovaDocFolderServiceWrapper(ParametriRegoleInfo parametri, ProtocolloSerializer serializer, ProtocolloLogs log, ProxyRequestInfo request, IBindingFactory bindingFactory)
            : base(parametri, serializer, log, request, bindingFactory, Constants.Titolo, Constants.ServiceName)
        {
        }

        private LivelloGerarchiaType[] GetGerarchiaLivelli(string classifica)
        {
            var livelli = new List<LivelloGerarchiaType>();

            if (!string.IsNullOrEmpty(classifica) && classifica.IndexOf(".") != -1)
            {
                var splitted = classifica.Split('.');
                var counter = 1;

                foreach (var item in splitted)
                {
                    livelli.Add(new LivelloGerarchiaType
                    {
                        Nro = counter.ToString(),
                        Codice = item
                    });

                    counter++;
                }
            }
            else
            {
                livelli.Add(new LivelloGerarchiaType
                {
                    Nro = "1",
                    Codice = classifica
                });
            }

            return livelli.ToArray();
        }

        private TrovaDocFolderFiltriAvanzatiRegistrazioneDoc GetTrovaDocFolderFiltriAvanzatiRegistrazioneDoc(DateTime? dallaData, DateTime? allaData, string anno, string numeroProtocollo)
        {
            var retVal = new TrovaDocFolderFiltriAvanzatiRegistrazioneDoc
            {
                CategoriaReg = TrovaDocFolderFiltriAvanzatiRegistrazioneDocCategoriaReg.PG,
                CategoriaRegSpecified = true
            };

            if (dallaData.HasValue)
            {
                retVal.DataRegDa = dallaData.Value;
                retVal.DataRegDaSpecified = true;
            }

            if (allaData.HasValue)
            {
                retVal.DataRegA = allaData.Value;
            }

            if (!string.IsNullOrEmpty(anno))
            {
                retVal.AnnoReg = anno;
            }

            if (!string.IsNullOrEmpty(numeroProtocollo))
            {
                retVal.NumRegDa = numeroProtocollo;
                retVal.NumRegA = numeroProtocollo;
            }

            return retVal;
        }

        private TrovaDocFolderFiltriPrincipaliFiltroFullText GetFiltroFullText(string oggetto)
        {
            if (string.IsNullOrEmpty(oggetto))
                return null;

            return new TrovaDocFolderFiltriPrincipaliFiltroFullText
            {
                FlagTutteLeParole = "1",
                ListaParole = oggetto
            };
        }

        public ResponseInfo CercaProtocolli(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            try
            {
                using (var ws = this.CreaWebService())
                {
                    var trovaDocFolderRequest = new TrovaDocFolder
                    {
                        FiltriPrincipali = new TrovaDocFolderFiltriPrincipali
                        {
                            TipoOggettiDaCercare = Constants.TipoOggettiDaCercareDocument,
                            ClassifUA = null,
                            CercaInVistaUtente = null,
                            CercaInFolder = null,
                            SoloRecenti = null,
                            SoloDaLeggere = null,
                            FiltroFullText = this.GetFiltroFullText(leggiProtocolloRequest.Oggetto)
                        },
                        FiltriAvanzati = new TrovaDocFolderFiltriAvanzati
                        {
                            NewsConNotificheCondivisione = null,
                            NewsConNotificheAutomatiche = null,
                            NewsConOsservazioni = null,
                            TipoDocumento = null,
                            TipoFolder = null,
                            StatoDocumento = null,
                            StatoFolder = null,
                            DataAggiornamentoStatoDaSpecified = false,
                            DataAggiornamentoStatoASpecified = false,
                            SoloConLock = null,
                            ApplicazionePropietaria = null,
                            RegistrazioneDoc = this.GetTrovaDocFolderFiltriAvanzatiRegistrazioneDoc(leggiProtocolloRequest.DallaData, leggiProtocolloRequest.AllaData, leggiProtocolloRequest.AnnoProtocollo, leggiProtocolloRequest.NumeroProtocollo),
                            RuoloUtenteVsDocFolder = null,
                            AttributoAdd = null,
                        },
                        LimitaEstrazioneAlCampo = Constants.LimitaEstrazioneAlCampoDefault,
                        Ordinamento = new TrovaDocFolderPerCampo[]
                        {
                            new TrovaDocFolderPerCampo
                            {
                                VersoDecrescente = "1",
                                Value = ((int)DocumentField.GeneralProtocolTimestamp).ToString()
                            }
                        },
                        EstrazionePaginata = new PaginazioneType()
                        {
                            NroPagina = leggiProtocolloRequest.Pagina,
                            NroRecordInPagina = "100"
                        }
                    };

                    if (!string.IsNullOrEmpty(leggiProtocolloRequest.Classifica))
                    {
                        trovaDocFolderRequest.FiltriPrincipali.ClassifUA = new ClassifUAType
                        {
                            LivelloClassificazione = this.GetGerarchiaLivelli(leggiProtocolloRequest.Classifica)
                        };
                    }

                    var trovaDocFolderRequestXML = Utility.HtmlEncodeContent(this._serializer.Serialize("trovaDocFolderRequest.xml", trovaDocFolderRequest));

                    this._request.xml = trovaDocFolderRequestXML;
                    this._request.hash = this.getHashSHA1();

                    var service = this.CreateServiceRequest(Constants.NameSpace, Constants.RequestServiceName, TipoOperazione.TROVA_DOC_FOLDER_REQUEST);
                    var xmlService = this._serializer.Serialize(ProtocolloLogsConstants.ListaProtocolliRequestFileName, service);

                    this.LogInfoRequestWS(xmlService);

                    var serviceResponse = ws.AurigaProxy(service);
                    var responseXml = this._serializer.Serialize(ProtocolloLogsConstants.ListaProtocolliResponseFileName, serviceResponse);

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
                throw new Exception($"{this._titolo} fallita: {ex.Message}", ex);
            }
        }

        public ResponseInfo CercaFascicoli(Fascicolo fascicolo)
        {
            try
            {
                using (var ws = this.CreaWebService())
                {
                    var trovaDocFolderRequest = new TrovaDocFolder
                    {
                        FiltriPrincipali = new TrovaDocFolderFiltriPrincipali
                        {
                            TipoOggettiDaCercare = Constants.TipoOggettiDaCercareFolder,
                            CercaInVistaUtente = null,
                            CercaInFolder = null,
                            SoloRecenti = null,
                            SoloDaLeggere = null,
                            FiltroFullText = new TrovaDocFolderFiltriPrincipaliFiltroFullText
                            {
                                FlagTutteLeParole = "1",
                            },
                        },
                        FiltriAvanzati = new TrovaDocFolderFiltriAvanzati
                        {
                            NewsConNotificheCondivisione = null,
                            NewsConNotificheAutomatiche = null,
                            NewsConOsservazioni = null,
                            TipoDocumento = null,
                            TipoFolder = null,
                            StatoDocumento = null,
                            StatoFolder = null,
                            DataAggiornamentoStatoDaSpecified = false,
                            DataAggiornamentoStatoASpecified = false,
                            SoloConLock = null,
                            ApplicazionePropietaria = null,
                            RegistrazioneDoc = null,
                            RuoloUtenteVsDocFolder = null,
                            AttributoAdd = this.SetCriteriRicerca(fascicolo)
                        }
                    };

                    var trovaDocFolderRequestXML = Utility.HtmlEncodeContent(this._serializer.Serialize("trovaDocFolderRequest.xml", trovaDocFolderRequest));

                    this._request.xml = trovaDocFolderRequestXML;
                    this._request.hash = this.getHashSHA1();

                    var service = this.CreateServiceRequest(Constants.NameSpace, Constants.RequestServiceName, TipoOperazione.TROVA_DOC_FOLDER_REQUEST);
                    var xmlService = this._serializer.Serialize(ProtocolloLogsConstants.ListaFascicoliRequestFileName, service);

                    this.LogInfoRequestWS(xmlService);

                    var serviceResponse = ws.AurigaProxy(service);
                    var responseXml = this._serializer.Serialize(ProtocolloLogsConstants.ListaFascicoliResponseFileName, serviceResponse);

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
                throw new Exception($"{this._titolo} fallita: {ex.Message}", ex);
            }
        }

        private CriterioRicercaSuAttributoAddType[] SetCriteriRicerca(Fascicolo fascicolo)
        {
            var retVal = new List<CriterioRicercaSuAttributoAddType>();

            if (fascicolo != null)
            {
                if (fascicolo.Classifica != null)
                {
                    retVal.Add(new CriterioRicercaSuAttributoAddType
                    {
                        Nome = "Classificazione",
                        OperatoreLogico = CriterioRicercaSuAttributoAddTypeOperatoreLogico.uguale,
                        ValoreConfronto_1 = fascicolo.Classifica
                    });
                }

                if (fascicolo.AnnoFascicolo != null)
                {
                    retVal.Add(new CriterioRicercaSuAttributoAddType
                    {
                        Nome = "Anno fascicolo",
                        OperatoreLogico = CriterioRicercaSuAttributoAddTypeOperatoreLogico.uguale,
                        ValoreConfronto_1 = fascicolo.AnnoFascicolo.ToString()
                    });
                }
            }

            return retVal.ToArray();
        }
    }
}
