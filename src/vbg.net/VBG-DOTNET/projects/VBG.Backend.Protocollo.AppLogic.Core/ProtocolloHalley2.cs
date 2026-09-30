using HalleyFascicolaService;
using Newtonsoft.Json;
using SIGePro.Manager.VerticalizzazioniBase;
using System.Net.Security;
using System.Security.Cryptography.X509Certificates;
using System.ServiceModel;
using System.ServiceModel.Channels;
using VBG.Backend.Protocollo.AppLogic.Core.ConsultaDocumentiHalley;
using VBG.Backend.Protocollo.AppLogic.Core.CustomEncoder;
using VBG.Backend.Protocollo.AppLogic.Core.Halley2;
using VBG.Backend.Protocollo.AppLogic.Core.Halley2.Fascicolazione;
using VBG.Backend.Protocollo.AppLogic.Core.Halley2.Fascicolazione.Enum;
using VBG.Backend.Protocollo.AppLogic.Core.Halley2.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Core.ProtocolloHalley2Service;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizzazioni.Core;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    public class PROTOCOLLO_HALLEY2 : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        public PROTOCOLLO_HALLEY2(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            _verticalizzazioniFactory = verticalizzazioniFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            try
            {
                var serializer = new Halle2Serializer(this._protocolloLogs, this._protocolloValidation);

                this._protocolloLogs.DebugFormat("Inizio protocollazione");
                serializer.LogAndValidate(ProtocolloLogsConstants.DatiProtocolloInFileName, protoIn);

                var verticalizzazione = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloHalley2>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);

                var parametri = Parametri.FromVerticalizzazione(verticalizzazione);

                var resolver = new Halley2ProtocollazioneResolver(parametri);

                var request = new ProtocolloHalley2Request
                {
                    Segnatura = this.GeneraSegnatura(protoIn, resolver, this.Anagrafiche),
                    Allegati = protoIn
                                .RecuperaAllegati()
                                .Select(x => new Halle2FileRequest
                                {
                                    File = new FileType
                                    {
                                        CidFile = x.OGGETTO,
                                        NomeFile = x.NOMEFILE
                                    }
                                })
                                .ToList()
                };

                if (request.Allegati.Count > 0)
                {
                    //setto principale sul primo allegato
                    request.Allegati.First().Principale = true;
                }

                var response = new Halley2ProtocollazioneProxyService(serializer, resolver).Protocolla(request);

                return new DatiProtocolloResponseType
                {
                    AnnoProtocollo =response.Anno,
                    DataProtocollo = DateTime.Now.ToString("dd/MM/yyyy"),
                    Errore = 
                        response.CodErrore == "0" ? 
                            null : 
                            new ErroreProtocolloType 
                            { 
                                Descrizione = $"{response.CodErrore}-{response.DesErrore}"
                            },
                    IdProtocollo = null,
                    Messaggio = null,
                    NumeroProtocollo = response.NumeroProtocollo,
                    Warning = null,
                };
            }
            catch (Exception ex)
            {
                this._protocolloLogs.Error(ex);
                throw (ex);
            }
        }

        private estraiProtocolliRequest CreateEstraiProtocolloRequestFromLeggiProtocolloRequest(LeggiProtocolloRequest leggiProtocolloRequest, string username, string password)
        {
            var request = new estraiProtocolliRequest();

            if (!string.IsNullOrWhiteSpace(leggiProtocolloRequest.IdProtocollo))
            {
                request.idProtocollo = int.Parse(leggiProtocolloRequest.IdProtocollo);
            }

            request.annoProto = leggiProtocolloRequest.AnnoProtocollo;
            request.numeroProto = leggiProtocolloRequest.NumeroProtocollo;
            request.dataProtocollazioneDa = leggiProtocolloRequest.DallaData;
            request.dataProtocollazioneA = leggiProtocolloRequest.AllaData;

            if ("A".Equals(leggiProtocolloRequest.Flusso))
            {
                request.tipologia = "Arrivo";
            }
            else if ("P".Equals(leggiProtocolloRequest.Flusso))
            {
                request.tipologia = "Partenza";
            }
            else if ("I".Equals(leggiProtocolloRequest.Flusso))
            {
                request.tipologia = "Interno";
            }

            request.soloFatture = false;

            var operatore = new Halley2.datiOperatore();
            operatore.username = username;
            operatore.password = password;
            var utenteEnte = new Halley2.datiOperatore();
            utenteEnte.username = username;
            utenteEnte.password = password;

            request.operatore = operatore;
            request.utenteEnte = utenteEnte;

            return request;
        }

        private estraiProtocolliResponse EstraiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            try
            {
                var verticalizzazione = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloHalley2>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);
                var parametri = Parametri.FromVerticalizzazione(verticalizzazione);

                HttpTransportBindingElement transportBinding = new HttpTransportBindingElement();
                EndpointAddress endPointAddress = new EndpointAddress(parametri.UrlEstraiProto);
                if (endPointAddress.Uri.Scheme.ToLower() == ProtocolloConstants.HTTPS)
                {
                    transportBinding = new HttpsTransportBindingElement();
                    System.Net.ServicePointManager.SecurityProtocol = System.Net.SecurityProtocolType.Tls12 | System.Net.SecurityProtocolType.Tls13;
                    System.Net.ServicePointManager.ServerCertificateValidationCallback = (object sender, X509Certificate certificate, X509Chain chain, SslPolicyErrors sslPolicyErrors) => true;
                }

                var binding = new CustomBinding(
                            new CustomTextMessageBindingElement("iso-8859-1", "text/xml", MessageVersion.Soap11),
                            transportBinding)
                {
                    SendTimeout = new TimeSpan(0, 15, 0),
                    ReceiveTimeout = new TimeSpan(0, 15, 0)
                };

                var client = new esProtoPortClient(binding, endPointAddress);
                var request = CreateEstraiProtocolloRequestFromLeggiProtocolloRequest(leggiProtocolloRequest, parametri.UsernameAuri, parametri.PasswordAuri);

                return client.estraiProtocolli(request);
            }
            catch (Exception ex)
            {
                this._protocolloLogs.Error("Errore in EstraiProtocollo Halley2", ex);
                throw ex;
            }
        }

        private (string Codice, string Descrizione) CreaClassifica(datiProto dati)
        {
            if (dati.classificazione == null)
                return (null, null);

            var codici = new List<string>();
            var descrizioni = new List<string>();

            if (dati.classificazione.categoria != null)
            {
                codici.Add(dati.classificazione.categoria.codice);
                descrizioni.Add(dati.classificazione.categoria.descrizione);
            }

            if (dati.classificazione.classe != null)
            {
                codici.Add(dati.classificazione.classe.codice);
                descrizioni.Add(dati.classificazione.classe.descrizione);
            }

            if (dati.classificazione.sottoclasse != null)
            {
                codici.Add(dati.classificazione.sottoclasse.codice);
                descrizioni.Add(dati.classificazione.sottoclasse.descrizione);
            }

            return (
                string.Join(".", codici),
                string.Join("-", descrizioni)
            );
        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            try
            {
                this._protocolloLogs.Info($"Inizio LeggiProtocollo - idProtocollo {leggiProtocolloRequest.IdProtocollo}, annoProtocollo {leggiProtocolloRequest.AnnoProtocollo}, numeroProtocollo {leggiProtocolloRequest.NumeroProtocollo}");
                var response = EstraiProtocollo(leggiProtocolloRequest);

                List<DatiProtocolloLettoResponseType> returnList = new List<DatiProtocolloLettoResponseType>();

                if (response.esito)
                {
                    estraiProtocolliResponseProtocolli responseProtocolli = (estraiProtocolliResponseProtocolli)response.Item;
                    if (responseProtocolli.protocollo != null)
                    {
                        foreach (datiProto dati in responseProtocolli.protocollo)
                        {
                            DatiProtocolloLettoResponseType datiProtocolloLetto = new DatiProtocolloLettoResponseType();
                            
                            //if(dati.idProtocollo != null)
                            //{
                                datiProtocolloLetto.IdProtocollo = dati.idProtocollo + "";
                            //}
                            datiProtocolloLetto.NumeroProtocollo = dati.numero;
                            datiProtocolloLetto.AnnoProtocollo = dati.anno;
                            datiProtocolloLetto.DataProtocollo = dati.dataRegis;
                            datiProtocolloLetto.Oggetto = dati.oggetto;

                            if ("Arrivo".Equals(dati.tipologia))
                            {
                                datiProtocolloLetto.Origine = "A"; //Credo fosse origine
                            }
                            else if ("Partenza".Equals(dati.tipologia))
                            {
                                datiProtocolloLetto.Origine = "P";
                            }
                            else if ("Interno".Equals(dati.tipologia))
                            {
                                datiProtocolloLetto.Origine = "I";
                            }

                            var (codice, descrizione) = CreaClassifica(dati);

                            datiProtocolloLetto.Classifica = codice;
                            datiProtocolloLetto.Classifica_Descrizione = descrizione;


                            if (dati.fascicoli != null && dati.fascicoli.Length > 0)
                            {
                                var fascicolo = dati.fascicoli.FirstOrDefault();

                                datiProtocolloLetto.NumeroPratica = fascicolo.numero;
                                datiProtocolloLetto.AnnoNumeroPratica = fascicolo.anno;
                            }



                            List<MittDestOutType> mitts = new List<MittDestOutType>();
                            //if (dati.uffici != null && dati.uffici.Length > 0)
                            //{
                            //    foreach ( datiUfficio ufficio in dati.uffici)
                            //    {

                            //        MittDestOut mitt = new MittDestOut();
                            //        mitt.IdSoggetto = ufficio.idUfficio + "";
                            //        mitt.CognomeNome = ufficio.descrizione;
                            //        mitts.Add(mitt);

                            //    }
                            //}
                            if (dati.anagrafiche != null)
                            {
                                if (dati.anagrafiche.Item != null)
                                {
                                    String cognomenome = null;
                                    if (dati.anagrafiche.Item is AmministrazioneType amministrazioneType)
                                    {
                                        cognomenome = amministrazioneType.Denominazione;
                                    }else if (dati.anagrafiche.Item is personaFisType personaFisType)
                                    {
                                        cognomenome = personaFisType.Cognome + " " + personaFisType.Nome;
                                    }
                                    else if (dati.anagrafiche.Item is personaGiuType personaGiuType)
                                    {
                                        cognomenome = personaGiuType.Denominazione;
                                    }

                                    if (cognomenome != null)
                                    {
                                        MittDestOutType mitt = new MittDestOutType();
                                        mitt.CognomeNome = cognomenome;
                                        mitts.Add(mitt);
                                    }

                                }
                                if (dati.anagrafiche.ufficio != null && dati.anagrafiche.ufficio.Length > 0)
                                {
                                    foreach (datiUfficio ufficio in dati.anagrafiche.ufficio)
                                    {

                                        MittDestOutType mitt = new MittDestOutType();
                                        mitt.IdSoggetto = ufficio.idUfficio + "";
                                        mitt.CognomeNome = ufficio.descrizione;
                                        mitts.Add(mitt);

                                    }
                                }
                            }
                            datiProtocolloLetto.MittentiDestinatari = mitts.ToArray();
                            datiProtocolloLetto.InCaricoA_Descrizione = dati.uffici?.FirstOrDefault()?.descrizione;
                            datiProtocolloLetto.InCaricoA = dati.uffici?.FirstOrDefault()?.idUfficio.ToString();

                            List<AllegatoResponseType> allegati = new List<AllegatoResponseType>();
                            if (dati.allegati != null && dati.allegati.Length > 0)
                            {
                                foreach(Halley2.Allegato allegato in dati.allegati)
                                {
                                    AllegatoResponseType allOut = new AllegatoResponseType();
                                    allOut.Serial = allegato.nomeFile;
                                    allOut.IDBase = allegato.nomeFile;
                                    allegati.Add(allOut);
                                }
                            }
                            datiProtocolloLetto.Allegati = allegati.ToArray();

                            returnList.Add(datiProtocolloLetto);
                        }
                    }
                }
                else
                {
                    Errore errore = (Errore)response.Item;
                    returnList.Add(new DatiProtocolloLettoResponseType(new Exception(errore.descrizione)));
                }

                return returnList;

            }
            catch (Exception e)
            {
                this._protocolloLogs.Error("Errore in LeggiProtocollo Halley2", e);
                List<DatiProtocolloLettoResponseType> returnList = new List<DatiProtocolloLettoResponseType>();
                returnList.Add(new DatiProtocolloLettoResponseType(e));
                return returnList;
            }

        }

        public override AllegatoResponseType LeggiAllegato()
        {


            try
            {

                var verticalizzazione = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloHalley2>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);
                var parametri = Parametri.FromVerticalizzazione(verticalizzazione);
                String username = parametri.UsernameAuri;
                String pwd = parametri.PasswordAuri;
                String rootUrl = parametri.UrlConsultaDoc;
                EndpointAddress endPointAddress = new EndpointAddress(rootUrl);

                HttpTransportBindingElement transportBinding = new HttpTransportBindingElement();

                if (endPointAddress.Uri.Scheme.ToLower() == ProtocolloConstants.HTTPS)
                {
                    transportBinding = new HttpsTransportBindingElement();
                    System.Net.ServicePointManager.SecurityProtocol = System.Net.SecurityProtocolType.Tls12 | System.Net.SecurityProtocolType.Tls13;
                    System.Net.ServicePointManager.ServerCertificateValidationCallback = (object sender, X509Certificate certificate, X509Chain chain, SslPolicyErrors sslPolicyErrors) => true;
                }

                var binding = new CustomBinding(
                            new CustomTextMessageBindingElement("iso-8859-1", "text/xml", MessageVersion.Soap11),
                            transportBinding)
                {
                    SendTimeout = new TimeSpan(0, 15, 0),
                    ReceiveTimeout = new TimeSpan(0, 15, 0)
                };



                var client = new PortTypeConsdocClient(binding,
                        endPointAddress);
                var request = new RequestConsultaDoc();

                var operatore = new ConsultaDocumentiHalley.datiOperatore();
                operatore.username = username;
                operatore.password = pwd;
           
                request.utenteEnte = operatore;
                request.operatore = username;
                request.password = pwd;

                if (string.IsNullOrWhiteSpace(this.IdAllegato))
                {
                    throw new Exception("Il nome del file è obbligatorio");
                }

                if (!string.IsNullOrWhiteSpace(this.NumProtocollo))
                {
                    request.NumeroProto = int.Parse(this.NumProtocollo);
                }
                if (!string.IsNullOrWhiteSpace(this.AnnoProtocollo))
                {
                    request.AnnoProto = int.Parse(this.AnnoProtocollo);
                }
                //altri campi da capire

                var response = client.ConsultaDocumentoAsync(request).ConfigureAwait(false).GetAwaiter().GetResult();


                if(response.ResponseConsultaDocData.coderrore != 0)
                {
                    throw new Exception(response.ResponseConsultaDocData.deserrore);
                }

                AllegatoResponseType allOut = null;
                int count = 0;
                foreach(DocumentoType documentoType in response.ResponseConsultaDocData.Allegati.Documento){
                    
                    if(count > 1)
                    {
                        throw new Exception("Sono stati trovati 2 file con lo stesso nome");
                    }

                    if (this.IdAllegato.Equals(documentoType.nomeFile))
                    {
                        allOut = new AllegatoResponseType();
                        allOut.Serial = documentoType.nomeFile;
                        allOut.TipoFile = Path.GetExtension(documentoType.nomeFile).TrimStart('.');
                        allOut.Image = documentoType.b64File;
                        count = count + 1;
                    }
                }

                if(count == 0)
                {
                    throw new Exception("Non sono stati trovati file con nome " + this.IdAllegato);
                }

                return allOut;

            }
            catch(Exception e)
            {
                return new AllegatoResponseType(e);
            }

        }

        private byte[] GeneraSegnatura(DatiProtocolloIn protoIn, Halley2ProtocollazioneResolver resolver, List<IAnagraficaAmministrazione> anagrafiche)
        {
            return new HalleySegnaturaBuilder(this._protocolloSerializer,protoIn, resolver.CasellaEmail, anagrafiche).Build();
        }

        public override ListaFascicoliResponseType GetFascicoli(Shared.Data.Fascicolo fascicolo)
        {
            try
            {
                var serializer = new Halle2Serializer(this._protocolloLogs, this._protocolloValidation);

                this._protocolloLogs.DebugFormat("Inizio GetFascicoli Halley2");
                serializer.LogAndValidate(ProtocolloLogsConstants.ListaFascicoliRequestFileName, fascicolo);

                var verticalizzazione = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloHalley2>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);
                var parametri = Parametri.FromVerticalizzazione(verticalizzazione);

                var resolver = new Halley2FascicolazioneResolver(parametri);
                var service = new Halley2FascicolazioneProxyService(serializer, resolver);

                var annoFascicolo = fascicolo.AnnoFascicolo.HasValue ? fascicolo.AnnoFascicolo.Value.ToString() : null;
                int? progressivoFascicolo = string.IsNullOrEmpty(fascicolo.NumeroFascicolo) ? null : Convert.ToInt32(fascicolo.NumeroFascicolo);
                var (categoria, classe, sottoclasse) = Utils.ParseClassifica(fascicolo.Classifica);

                if (progressivoFascicolo == null)
                {
                    throw new Exception("E' necessario passare il NumeroFascicolo e l'anno per effettuare la ricerca");
                }
                
                var responseConsulta = service.ConsultaFascicoli(new ConsultaFascicoloRequest()
                {
                    Anno = annoFascicolo,
                    Progressivo = progressivoFascicolo,
                    Categoria = categoria,
                    Classe = classe,
                    SottoClasse = sottoclasse,
                });

                if (responseConsulta == null)
                {
                    throw new Exception($"Si è verificato un errore in GetFascicoli - ConsultaFascicoli: Anno {annoFascicolo}, Numero {progressivoFascicolo}, Categoria {categoria}, Classe {classe}, Sottoclasse {sottoclasse}");
                }

                

                if (!responseConsulta.Esito)
                {
                    if (responseConsulta.FaultCodeEnum == ServiziAggiuntiviFaultCode.NessunFascicoloEstratto)
                    {
                        return new ListaFascicoliResponseType()
                        {
                            Errore = new ErroreProtocolloType()
                            {
                                Descrizione = $"FaultCode: {responseConsulta.Errore.codice}, Descrizione: {responseConsulta.Errore.descrizione}",
                                StackTrace = $"Non è stato trovato alcun fascicolo con i seguenti parametri: Anno {annoFascicolo}, Numero {progressivoFascicolo}, Categoria {categoria}, Classe {classe}, Sottoclasse {sottoclasse}",
                            }
                        };
                    }
                    else
                    {
                        throw new Exception($"Si è verificato un errore. ConsultaFascicoli ha risposto: {responseConsulta.Errore.descrizione}. FaultCode: {responseConsulta.Errore.codice}");
                    }
                }

                return new ListaFascicoliResponseType()
                {
                    Fascicolo = responseConsulta.fascicoli.Select(f => f.ToDatiFascType()).ToArray()
                };
            }
            catch (Exception ex)
            {
                this._protocolloLogs.Error(ex);
                throw (ex);
            }
        }

        public override DatiFascicoloResponseType Fascicola(Shared.Data.Fascicolo fascicolo)
        {
            try
            {
                var serializer = new Halle2Serializer(this._protocolloLogs, this._protocolloValidation);

                this._protocolloLogs.DebugFormat("Inizio Fascicola Halley2");
                serializer.LogAndValidate(ProtocolloLogsConstants.CreaFascicoloRequestFileName, fascicolo);

                var verticalizzazione = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloHalley2>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);
                var parametri = Parametri.FromVerticalizzazione(verticalizzazione);

                var resolver = new Halley2FascicolazioneResolver(parametri);
                var service = new Halley2FascicolazioneProxyService(serializer, resolver);

                var annoFascicolo = fascicolo.AnnoFascicolo.HasValue ? fascicolo.AnnoFascicolo.Value.ToString() : null;
                int? progressivoFascicolo = string.IsNullOrEmpty(fascicolo.NumeroFascicolo) ? null : Convert.ToInt32(fascicolo.NumeroFascicolo);
                int? idFascicolo = null;
                bool creatoNuovoFascicolo = false;

                // se non viene indicato ne creo uno nuovo
                if (progressivoFascicolo == null)
                {
                    var responseInserisciFascicolo = service.InserisciFascicolo(fascicolo.Classifica, fascicolo.Oggetto);

                    annoFascicolo = responseInserisciFascicolo.anno;
                    progressivoFascicolo = Convert.ToInt32(responseInserisciFascicolo.numero);
                    idFascicolo = responseInserisciFascicolo.idFascicolo;
                    creatoNuovoFascicolo = true;
                }
                else // verifico che esista, se esiste ne prendo i valori altrimenti ne creo uno nuovo
                {
                    var responseConsulta = service.ConsultaFascicoli(new ConsultaFascicoloRequest()
                    {
                        Anno = annoFascicolo,
                        Progressivo = progressivoFascicolo
                    });

                    if (responseConsulta == null)
                    {
                        throw new Exception($"Si è verificato un errore in Fascicola - ConsultaFascicoli: Anno {annoFascicolo}, Numero {progressivoFascicolo}");
                    }

                    if (!responseConsulta.Esito)
                    {
                        if (responseConsulta.FaultCodeEnum == ServiziAggiuntiviFaultCode.NessunFascicoloEstratto)
                        {
                            var responseInserisciFascicolo = service.InserisciFascicolo(fascicolo.Classifica, fascicolo.Oggetto);

                            annoFascicolo = responseInserisciFascicolo.anno;
                            progressivoFascicolo = Convert.ToInt32(responseInserisciFascicolo.numero);
                            idFascicolo = responseInserisciFascicolo.idFascicolo;
                            creatoNuovoFascicolo = true;
                        }
                        else
                        {
                            throw new Exception($"Si è verificato un errore. ConsultaFascicoli ha risposto: {responseConsulta.Errore.descrizione}. FaultCode: {responseConsulta.Errore.codice}");
                        }
                    }

                    if (responseConsulta.fascicoli.Length > 1)
                    {
                        throw new Exception($"Sono stati trovati più fascicoli cercando per: Anno {annoFascicolo}, Numero {progressivoFascicolo}");
                    }

                    var fascicoloTrovato = responseConsulta.fascicoli.FirstOrDefault();
                    annoFascicolo = fascicoloTrovato.anno;
                    progressivoFascicolo = Convert.ToInt32(fascicoloTrovato.numero);
                    idFascicolo = fascicoloTrovato.idFascicolo;
                }                

                var responseFascicolaProtocollo = service.FascicolaProtocollo(new fascicolaProtocolloRequest()
                {
                    idFascicolo = idFascicolo.Value,
                    idProtocollo = Convert.ToInt32(base.IdProtocollo)
                });

                if (responseFascicolaProtocollo.esito)
                {
                    return new DatiFascicoloResponseType()
                    {
                        AnnoFascicolo = annoFascicolo,
                        NumeroFascicolo = progressivoFascicolo.ToString(),
                        DataFascicolo = DateTime.Today.ToShortDateString()
                    };
                }
                else
                {
                    return new DatiFascicoloResponseType()
                    {
                        AnnoFascicolo = annoFascicolo,
                        NumeroFascicolo = progressivoFascicolo.ToString(),
                        DataFascicolo = string.Empty,
                        Errore = new ErroreProtocolloType()
                        {
                            Descrizione = responseFascicolaProtocollo.descrizione,
                            StackTrace = $"Errore durante la chiamata FascicolaProtocollo. FaultCode: {responseFascicolaProtocollo.codice}, CreatoNuovoFascicolo: {creatoNuovoFascicolo}"
                        }
                    };
                }
                
            }
            catch (Exception ex)
            {
                this._protocolloLogs.Error(ex);
                throw (ex);
            }
        }

        public override DatiProtocolloFascicolatoResponseType IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            try
            {
                this._protocolloLogs.Info($"Inizio IsFascicolato - idProtocollo {idProtocollo}, annoProtocollo {annoProtocollo}, numeroProtocollo {numeroProtocollo}");
                var response = EstraiProtocollo(new LeggiProtocolloRequest()
                {
                    IdProtocollo = idProtocollo,
                    AnnoProtocollo = annoProtocollo,
                    NumeroProtocollo = numeroProtocollo
                });

                this._protocolloLogs.Info($"WS response: {JsonConvert.SerializeObject(response, Formatting.Indented)}");

                estraiProtocolliResponseProtocolli responseProtocolli = (estraiProtocolliResponseProtocolli)response.Item;


                if (responseProtocolli.protocollo != null && responseProtocolli.protocollo.Length > 0)
                {
                    var protocollo = responseProtocolli.protocollo.FirstOrDefault();

                    if (protocollo == null || protocollo.fascicoli == null || protocollo.fascicoli.Length == 0)
                    {
                        this._protocolloLogs.Info("Non sono stati trovati fascicoli");
                        return new DatiProtocolloFascicolatoResponseType()
                        {
                            Fascicolato = EnumFascicolatoType.no
                        };
                    }

                    this._protocolloLogs.Info($"Sono stati trovati {protocollo.fascicoli.Length} fascicoli");

                    var fascicolo = protocollo.fascicoli.FirstOrDefault();
                    this._protocolloLogs.Info($"Il primo fascicolo ha il numero: {fascicolo.numero}");

                    var (codiceClassifica, descrizioneClassifica) = CreaClassifica(protocollo);

                    return new DatiProtocolloFascicolatoResponseType
                    {
                        Fascicolato = String.IsNullOrEmpty(fascicolo.numero) ? EnumFascicolatoType.no : EnumFascicolatoType.si,
                        AnnoFascicolo = fascicolo.anno,
                        Classifica = codiceClassifica, // la classifica del protocollo. Il fascicolo non ce l'ha
                        NumeroFascicolo = fascicolo.numero,
                        Oggetto = fascicolo.descrizione
                    };
                }

                return new DatiProtocolloFascicolatoResponseType();
            }
            catch (Exception e)
            {
                this._protocolloLogs.Error("Errore in IsFascicolato Halley2", e);
                return new DatiProtocolloFascicolatoResponseType(e);
            }
        }
    }
}
