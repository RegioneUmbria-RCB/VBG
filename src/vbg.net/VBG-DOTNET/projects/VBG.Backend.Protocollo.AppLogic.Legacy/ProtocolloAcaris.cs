using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Logic.GestioneContesti;
using Init.SIGePro.Protocollo.AcarisObjectServicePort;
using PersonalLib2.Data;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Allegati;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Document;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Fascicolazione;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Folder;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Mail;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Metadati;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Protocollazione.CopiaCortesia;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Protocollazione.Lettura;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Protocollazione.Registrazioni;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Sottofascicolazione;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Metadati;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.VerificaFirmaDigitale;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizazioni.Legacy;

namespace VBG.Backend.Protocollo.AppLogic.Legacy
{
    public class PROTOCOLLO_ACARIS : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IBindingFactory _bindingFactory;

        public PROTOCOLLO_ACARIS(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._bindingFactory = bindingFactory;
        }

        public override void InizializzaProtocolloBase(ResolveDatiProtocollazioneService datiProtocolloService)
        {
            base.InizializzaProtocolloBase(datiProtocolloService);
            base._protocolloSerializer = new AcarisSerializer(this._protocolloLogs, this._protocolloValidation);
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            try
            {
                var serializer = new AcarisSerializer(this._protocolloLogs, this._protocolloValidation);

                this._protocolloLogs.DebugFormat("Inizio protocollazione");
                serializer.LogAndValidate(ProtocolloLogsConstants.DatiProtocolloInFileName, protoIn);

                var datiProtocollo = new ProtocolloExt(serializer, this._protocolloLogs, protoIn, this, this._verticalizzazioniFactory);

                serializer.LogAndValidate("Configurazione.xml", datiProtocollo.Configurazione, "Configurazione attuale");

                var datiDelContesto = this.RecuperaDatiContesto(this.DatiProtocollo.IdComune, this.DatiProtocollo.CodiceIstanza);

                var codiceDossier = "";

                this._protocolloLogs.DebugFormat($"Tipo di fascicolazione: {datiProtocollo.Configurazione.TipoFascicolazione}");
                if (datiProtocollo.Configurazione.TipoFascicolazione == "FascicolazioneSuDossierService")
                {
                    this._protocolloLogs.DebugFormat($"Gestione sedi abilitata: {datiProtocollo.Configurazione.GestioneSediAbilitata}");
                    //2. Tento il recupero del codice della sede se abilitata la gestione delle sedi
                    if (datiProtocollo.Configurazione.GestioneSediAbilitata)
                    {
                        codiceDossier = this.RecuperaCodiceSede(datiDelContesto);
                        if (!String.IsNullOrEmpty(codiceDossier) && datiProtocollo.Configurazione.CodiceDossierLength.HasValue)
                        {
                            codiceDossier = codiceDossier.PadLeft(datiProtocollo.Configurazione.CodiceDossierLength.Value, '0');
                        }
                    }
                    else
                    {
                        var destinatario = this.RecuperaTitolareDossierDaSoggettiProtocollo(protoIn);
                        codiceDossier = string.IsNullOrEmpty(destinatario.CodiceFiscale) ? destinatario.PartitaIVA : destinatario.CodiceFiscale;
                        this._protocolloLogs.DebugFormat($"Codice del dossier da utilizzare: {codiceDossier}");
                        if (datiDelContesto == null)
                        {
                            datiDelContesto = new Dictionary<string, List<Dyn2Dato>>();
                        }

                        if (!datiDelContesto.ContainsKey(Contesti.CodiceFiscale) || !datiDelContesto[Contesti.CodiceFiscale].Any(x => !String.IsNullOrEmpty(x.Valore)))
                        {
                            if (datiDelContesto.ContainsKey(Contesti.CodiceFiscale))
                            {
                                datiDelContesto.Remove(Contesti.CodiceFiscale);
                            }

                            datiDelContesto.Add(
                                Contesti.CodiceFiscale,
                                new List<Dyn2Dato>()
                                {
                                    new Dyn2Dato
                                    {
                                        ContestoCampo = Contesti.CodiceFiscale,
                                        Valore = destinatario.CodiceFiscale,
                                        ValoreDecodificato = destinatario.CodiceFiscale,
                                    }
                                }
                            );
                        }

                        if (!datiDelContesto.ContainsKey(Contesti.PartitaIva) || !datiDelContesto[Contesti.PartitaIva].Any(x => !String.IsNullOrEmpty(x.Valore)))
                        {
                            if (datiDelContesto.ContainsKey(Contesti.PartitaIva))
                            {
                                datiDelContesto.Remove(Contesti.PartitaIva);
                            }
                            datiDelContesto.Add(Contesti.PartitaIva, new List<Dyn2Dato>
                            {
                                new Dyn2Dato
                                {
                                    ContestoCampo = Contesti.PartitaIva,
                                    Valore = destinatario.PartitaIVA,
                                    ValoreDecodificato = destinatario.PartitaIVA,
                                }
                            });
                        }

                        if (!datiDelContesto.ContainsKey(Contesti.Soggetto) || !datiDelContesto[Contesti.Soggetto].Any(x => !String.IsNullOrEmpty(x.Valore)))
                        {
                            if (datiDelContesto.ContainsKey(Contesti.Soggetto))
                            {
                                datiDelContesto.Remove(Contesti.Soggetto);
                            }
                            datiDelContesto.Add(Contesti.Soggetto, new List<Dyn2Dato>
                            {
                                new Dyn2Dato
                                {
                                    ContestoCampo = Contesti.Soggetto,
                                    Valore = destinatario.Nominativo,
                                    ValoreDecodificato = destinatario.Nominativo,
                                }
                            });
                        }
                    }
                }


                this._protocolloLogs.DebugFormat("Inizializzazione del resolver per la descrizione del fasicolo");
                var descFascicoloResolver = new DescrizioneFascicoloResolver(datiProtocollo, protoIn.RecuperaMetadati(), datiDelContesto, this._protocolloLogs, serializer, this._bindingFactory);
                this._protocolloLogs.DebugFormat("Inizializzazione del resolver per la descrizione del sottofascicolo");
                var descSottofascicoloResolver = new DescrizioneSottofascicoloResolver(datiProtocollo);

                this._protocolloLogs.DebugFormat("Creazione della FolderResolverFactory");
                var folderResolver = new FolderResolverFactory(datiProtocollo, descFascicoloResolver).Get();
                this._protocolloLogs.DebugFormat("Creazione del FolderService");
                var folderService = new FolderService(serializer, this._protocolloLogs, folderResolver);

                IdFolder idFolder = null;

                this._protocolloLogs.DebugFormat($"Ambito della protocollazione {this.DatiProtocollo.TipoAmbito}");

                if (this.DatiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.DA_MOVIMENTO)
                {
                    if (!datiProtocollo.CodiceIstanza.HasValue)
                    {
                        throw new ConfigurationErrorsException("Non è possibile protocollare un movimento senza passare l'istanza a cui appartiene");
                    }

                    //non deve essere creato nessun dossier/fascicolo ma utilizzato quello dell'istanza
                    var ist = new IstanzeMgr(datiProtocollo.Db).GetById(datiProtocollo.Idcomune, datiProtocollo.CodiceIstanza.Value);
                    if (String.IsNullOrEmpty(ist.NUMEROPROTOCOLLO) || !ist.DATAPROTOCOLLO.HasValue)
                    {
                        throw new ConfigurationErrorsException("Non è possibile protocollare un movimento senza aver prima protocollato l'istanza");
                    }

                    idFolder = folderService.GetFolderDaEstremiProtocollo(this, datiProtocollo.Configurazione, ist.NUMEROPROTOCOLLO, ist.DATAPROTOCOLLO.Value.Year);
                }
                else
                {
                    this._protocolloLogs.DebugFormat($"Serie di fascicoli prevista: {datiProtocollo.Configurazione.SerieFascicoli}");
                    if (!String.IsNullOrEmpty(datiProtocollo.Configurazione.SerieFascicoli))
                    {

                        this._protocolloLogs.DebugFormat($"Recupero dell'identificativo della serie di fascicoli");
                        var idSerieFascicoli = folderService.RecuperaIdAcarisSerieFascicoliDaCodice(datiProtocollo.Configurazione.SerieFascicoli);
                        idFolder = folderService.GetFolderPerDescrizioneESerieFascicoli(folderResolver.TypeId, idSerieFascicoli, descFascicoloResolver);
                    }
                    // se il fascicolo non esiste
                    if (idFolder == null)
                    {
                        this._protocolloLogs.DebugFormat($"Id del folder non ancora trovato, si procede alla creazione");
                        var fascicolaRequest = new FascicolaRequest
                        {
                            IdentificativoUtente = this.Operatore,
                            Configurazione = datiProtocollo.Configurazione,
                            IdProtocollo = this.DatiProtocollo.Istanza != null ? this.DatiProtocollo.Istanza.FKIDPROTOCOLLO : null,
                            Protocollo = this,
                            TipoAmbito = this.DatiProtocollo.TipoAmbito,
                            CodiceDossier = codiceDossier,
                            TemplateDescrizioneDossier = datiProtocollo.Configurazione.TemplateDescrizioneDossier,
                            DatiContestoDossier = datiDelContesto
                        };

                        idFolder = new FascicolazioneFactory(serializer, this._protocolloLogs, folderResolver, datiProtocollo.Configurazione).Fascicola(fascicolaRequest);
                    }

                    this._protocolloLogs.DebugFormat("Verifico se abilitata la sottofascicolazione");
                    if (datiProtocollo.Configurazione.GestioneSottofascicoloAbilitata)
                    {
                        var parentIdFolder = idFolder;
                        var sottofascicoloResolver = new SottofascicoloResolverFactory(datiProtocollo, descSottofascicoloResolver).Get();
                        var sottofascicoloService = new SottofascicoloService(serializer, this._protocolloLogs, sottofascicoloResolver);

                        // ricerca sottofascicolo
                        idFolder = sottofascicoloService.GetSottofascicoloPerDescrizioneEIdFascicolo(enumFolderObjectType.SottofascicoloPropertiesType, idFolder, descSottofascicoloResolver);

                        if (this.DatiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
                        {
                            // se sto protocollando un'istanza creo il sottofascicolo
                            if (idFolder == null)
                            {
                                idFolder = new IdFolder(sottofascicoloService.CreaSottofascicolo(parentIdFolder));
                            }
                        }
                        else
                        {
                            // se il sottofascicolo esiste
                            if (idFolder != null)
                            {
                                // creazione del sottofascicolo
                                idFolder = new IdFolder(sottofascicoloService.CreaSottofascicolo(parentIdFolder));
                            }
                            else
                            {
                                throw new ConfigurationErrorsException("È attiva la gestione del sottofascicolo, ma l'istanza non ne ha uno associato");
                            }
                        }
                    }

                }


                this._protocolloLogs.DebugFormat("Inizializzazione service per il caricamento degli allegati");
                var allegatiService = new AllegatiAcarisService(serializer, datiProtocollo.Configurazione);

                var serviceCreator = new FirmaDigitaleServiceCreator(this._protocolloLogs, this._bindingFactory);
                var servizioVerificaFirma = new VerificaFirmaDigitaleService(serviceCreator);

                var resolver = new DocumentResolver(datiProtocollo.Configurazione, protoIn);

                var metadati = this.GetMetadati(protoIn.RecuperaAllegati().First());
                var allegato = new AllegatoAcaris(servizioVerificaFirma, protoIn.RecuperaAllegati().First(), metadati);
                var classificazionePrincipale = allegatiService.CaricaAllegatoPrincipale(resolver, idFolder, allegato).info.objectIdClassificazione;

                foreach (var a in protoIn.RecuperaAllegati().Skip(1))
                {
                    metadati = this.GetMetadati(a);
                    allegato = new AllegatoAcaris(servizioVerificaFirma, a, metadati);
                    allegatiService.CaricaAllegatoSecondario(resolver, classificazionePrincipale, allegato);
                }

                //protocollazione
                IRegistrazioneAPIResolver registrazioneAPIResolver;

                switch (protoIn.Flusso)
                {
                    case "A":
                        registrazioneAPIResolver = new RegistrazioneAPIArrivoResolver(datiProtocollo, classificazionePrincipale.value);
                        break;
                    case "I":
                        throw new NotImplementedException($"Impossibile specificare il flusso {protoIn.Flusso} per la protocollazione");
                    case "P":
                        registrazioneAPIResolver = new RegistrazioneAPIPartenzaResolver(datiProtocollo, classificazionePrincipale.value);
                        break;
                    default:
                        throw new NotImplementedException($"Impossibile specificare il flusso {protoIn.Flusso} per la protocollazione");
                }

                var protocolloResolver = new ProtocollazioneDocumentoEsistenteResolver(registrazioneAPIResolver, datiProtocollo.Configurazione);

                var protocolloService = new ProtocollazioneService(serializer, this._protocolloLogs, protocolloResolver);

                var registrazioneResponse = protocolloService.Protocolla();

                var response = protocolloService.CreaRegistrazioneResponseToDatiProtocolloRes(registrazioneResponse);

                var warnings = new List<string>();

                //copia cortesia
                try
                {
                    if (protoIn.Flusso == "P" && datiProtocollo.Configurazione.InviaCopiaCortesia)
                    {
                        new CopiaCortesiaService(serializer, datiProtocollo.Configurazione).Invia(registrazioneResponse.identificazioneCreazione.registrazioneId.value);
                    }
                }
                catch (Exception ex)
                {
                    this._protocolloLogs.Error(ex);
                    warnings.Add($"Non è stato possibile inviare la copia cortesia per via del seguente errore: {ex.Message}");
                }


                //invio mail
                if (protoIn.Flusso == "P" && datiProtocollo.DatiProtocollo.Destinatari.PecPresenti().Any())
                {
                    try
                    {
                        this._protocolloLogs.DebugFormat("Invio mail ai destinatari");
                        var pecMittente = datiProtocollo.DatiProtocollo.Mittenti.Amministrazione[0].PEC;
                        new MailService(serializer, datiProtocollo.Configurazione).InviaMail(pecMittente, response.IdProtocollo);
                        this._protocolloLogs.DebugFormat("Fine invio mail ai destinatari");
                    }
                    catch (Exception ex)
                    {
                        this._protocolloLogs.Error(ex);
                        warnings.Add($"Non è stato possibile inviare la PEC a causa del seguente errore: {ex.Message}");
                    }
                }

                if (warnings.Any())
                {
                    response.Warning = $"La protocollazione è avvenuta correttamente, tuttavia: {string.Join(" - ", warnings)}";
                }

                this._protocolloLogs.DebugFormat("Fine protocollazione");

                return response;
            }
            catch (Exception ex)
            {
                this._protocolloLogs.Error(ex);
                throw;
            }
        }

        private SoggettoAcaris RecuperaTitolareDossierDaSoggettiProtocollo(DatiProtocolloIn protoIn)
        {
            switch (protoIn.Flusso)
            {
                case "A":
                    if (protoIn.Mittenti?.Anagrafe?.Any() == true)
                    {
                        return SoggettoAcaris.FromAnagrafe(protoIn.Mittenti.Anagrafe[0]);
                    }
                    else
                    {
                        return SoggettoAcaris.FromAmministrazione(protoIn.Mittenti.Amministrazione[0]);
                    }
                case "P":
                    if (protoIn.Destinatari?.Anagrafe?.Any() == true)
                    {
                        return SoggettoAcaris.FromAnagrafe(protoIn.Destinatari.Anagrafe[0]);
                    }
                    else
                    {
                        return SoggettoAcaris.FromAmministrazione(protoIn.Destinatari.Amministrazione[0]);
                    }
                default:
                    throw new NotImplementedException($"Impossibile risalire al soggetto destinatario per il flusso di protocollazione {protoIn.Flusso}");
            }
        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(Shared.WsDataClass.LeggiProtocolloRequest leggiProtocolloRequest)
        {
            if (String.IsNullOrEmpty(leggiProtocolloRequest.IdProtocollo) && (String.IsNullOrEmpty(leggiProtocolloRequest.NumeroProtocollo) || String.IsNullOrEmpty(leggiProtocolloRequest.AnnoProtocollo)))
            {
                throw new ConfigurationErrorsException("Impossibile eseguire la lettura delle informazioni del protocollo senza passare l'id del protocollo oppure numero e anno");
            }

            var serializer = new AcarisSerializer(this._protocolloLogs, this._protocolloValidation);

            foreach (var config in this.GetConfigurazioni(serializer, this.DatiProtocollo.Db, this.Operatore, this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.IdComune, this.DatiProtocollo.CodiceComune, this.DatiProtocollo.Software, leggiProtocolloRequest.IdProtocollo))
            {
                try
                {
                    this._protocolloLogs.DebugFormat($"Inizio lettura protocollazione con [{config.IdAoo}], [{config.IdStruttura}], [{config.IdNodo}]");

                    var req = String.IsNullOrEmpty(leggiProtocolloRequest.IdProtocollo)
                                                   ? Acaris.Protocollazione.Lettura.LeggiProtocolloRequest.FromEstremiProtocollo(this, leggiProtocolloRequest.NumeroProtocollo, Convert.ToInt32(leggiProtocolloRequest.AnnoProtocollo))
                                                   : Acaris.Protocollazione.Lettura.LeggiProtocolloRequest.FromIdProtocollo(this, leggiProtocolloRequest.IdProtocollo);

                    var protocolloService = new LeggiProtocolloService(this._protocolloSerializer, config, req);

                    var datiProtocollo = protocolloService.LeggiProtocollo();

                    if ((!String.IsNullOrEmpty(this.Uo) || !String.IsNullOrEmpty(this.Ruolo)) && datiProtocollo.Allegati?.Any() == true)
                    {
                        datiProtocollo.Allegati.ToList().ForEach(x =>
                        {
                            x.Uo = this.Uo;
                            x.Ruolo = this.Ruolo;
                        });
                    }

                    this._protocolloLogs.DebugFormat($"Fine lettura protocollazione con [{config.IdAoo}], [{config.IdStruttura}], [{config.IdNodo}]");

                    if (datiProtocollo != null)
                    {
                        return new List<DatiProtocolloLettoResponseType>() { datiProtocollo };
                    }
                }
                catch (Exception ex)
                {
                    //non deve essere sollevata nessuna eccezione
                    this._protocolloLogs.Warn(ex.Message);
                }
            }

            return new List<DatiProtocolloLettoResponseType>();
        }

        public override AllegatoResponseType LeggiAllegato()
        {
            var serializer = new AcarisSerializer(this._protocolloLogs, this._protocolloValidation);

            foreach (var config in this.GetConfigurazioni(serializer, this.DatiProtocollo.Db, this.Operatore, this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.IdComune, this.DatiProtocollo.CodiceComune, this.DatiProtocollo.Software, this.IdProtocollo))
            {
                try
                {
                    this._protocolloLogs.DebugFormat($"Inizio download dell'allegato con id {this.IdAllegato}, [{config.IdAoo}], [{config.IdStruttura}], [{config.IdNodo}]");

                    var allegatiService = new AllegatiAcarisService(this._protocolloSerializer, config);

                    var contenutoFisico = allegatiService.GetContenutoFisico(this.IdAllegato);

                    var allegato = allegatiService.GetAllegato(this.IdAllegato);

                    this._protocolloLogs.DebugFormat($"Fine download dell'allegato con id {this.IdAllegato}, [{config.IdAoo}], [{config.IdStruttura}], [{config.IdNodo}]");

                    if (allegato != null)
                    {
                        return new AllegatoResponseType
                        {
                            IDBase = this.IdAllegato,
                            Commento = this.ProperyValue(contenutoFisico.response.objects[0].properties, "contentStreamFilename"),
                            Serial = this.ProperyValue(contenutoFisico.response.objects[0].properties, "contentStreamFilename"),
                            Image = allegato.streamMTOM,
                            ContentType = this.ProperyValue(contenutoFisico.response.objects[0].properties, "contentStreamMimeType")
                        };
                    }
                }
                finally
                {
                    //non deve essere sollevata nessuna eccezione
                }
            }

            return new AllegatoResponseType();
        }

        public override DatiProtocolloFascicolatoResponseType IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            if (String.IsNullOrEmpty(idProtocollo) && (String.IsNullOrEmpty(numeroProtocollo) || String.IsNullOrEmpty(annoProtocollo)))
            {
                throw new ConfigurationErrorsException("Impossibile eseguire verifica della fascicolazione senza passare l'id del protocollo oppure numero e anno");
            }

            var serializer = new AcarisSerializer(this._protocolloLogs, this._protocolloValidation);
            var datiProtocollo = new ProtocolloExt(serializer, this._protocolloLogs, this.DatiProtocollo, this.Operatore, this._verticalizzazioniFactory);

            foreach (var config in this.GetConfigurazioni(serializer, this.DatiProtocollo.Db, this.Operatore, this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.IdComune, this.DatiProtocollo.CodiceComune, this.DatiProtocollo.Software, idProtocollo))
            {
                try
                {
                    this._protocolloLogs.DebugFormat($"Inizio controllo se protocollo fascicolato [{config.IdAoo}], [{config.IdStruttura}], [{config.IdNodo}]");

                    var folderResolver = new FolderResolverFactory(datiProtocollo).Get();

                    var folderService = new FolderService(serializer, this._protocolloLogs, folderResolver);

                    var request = new GetDatiFascicoloRequest
                    {
                        AnnoProtocollo = String.IsNullOrEmpty(idProtocollo) ? Convert.ToInt32(this.AnnoProtocollo) : (int?)null,
                        CodiceComune = this.DatiProtocollo.CodiceComune,
                        Db = this.DatiProtocollo.Db,
                        IdComune = this.DatiProtocollo.IdComune,
                        IdComuneAlias = this.DatiProtocollo.IdComuneAlias,
                        IdProtocollo = idProtocollo,
                        NumeroProtocollo = String.IsNullOrEmpty(idProtocollo) ? numeroProtocollo : null,
                        Operatore = this.Operatore,
                        Software = this.DatiProtocollo.Software
                    };

                    var response = folderService.GetDatiFascicolo(datiProtocollo.Configurazione, request);

                    this._protocolloLogs.DebugFormat($"Fine controllo se protocollo fascicolato [{config.IdAoo}], [{config.IdStruttura}], [{config.IdNodo}]");

                    return response;
                }
                finally
                {
                    //non deve andare in errore
                }
            }

            return new DatiProtocolloFascicolatoResponseType
            {
                Fascicolato = EnumFascicolatoType.nondefinito
            };
        }

        private IEnumerable<OggettiMetadati> GetMetadati(ProtocolloAllegati allegato)
        {
            var metadati = new List<OggettiMetadati>();

            //1. Metadati della firma
            metadati.AddRange(new OggettiMetadatiMgr(this.DatiProtocollo.Db).GetMetadatiVerificaFirma(this.DatiProtocollo.IdComune, Convert.ToInt32(allegato.CODICEOGGETTO)));

            //2. Metadatp della conversione
            if (allegato.Metadati.ContainsKey("CONVERTITO_DA"))
            {
                metadati.Add(new OggettiMetadati
                {
                    Chiave = "CONVERTITO_DA",
                    Codiceoggetto = Convert.ToInt32(allegato.CODICEOGGETTO),
                    Idcomune = allegato.IDCOMUNE,
                    Valore = allegato.Metadati["CONVERTITO_DA"]
                });
            }

            if (allegato.Metadati.ContainsKey("CONVERTITO_IN"))
            {
                metadati.Add(new OggettiMetadati
                {
                    Chiave = "CONVERTITO_IN",
                    Codiceoggetto = Convert.ToInt32(allegato.CODICEOGGETTO),
                    Idcomune = allegato.IDCOMUNE,
                    Valore = allegato.Metadati["CONVERTITO_IN"]
                });
            }

            return metadati;
        }

        private string ProperyValue(Init.SIGePro.Protocollo.AcarisNavigationServicePort.PropertyType[] props, string propertyName)
        {
            return props
                    .Where(x => x.queryName.propertyName == propertyName)
                    .Select(y => y.value)
                    .FirstOrDefault()?
                    .FirstOrDefault();
        }

        private List<ParametriRegoleInfo> GetConfigurazioni(AcarisSerializer serializer, DataBase db, string operatore, string idComuneAlias, string idComune, string codiceComune, string software, string idProtocollo)
        {
            this._protocolloLogs.Debug("inizio GetConfigurazioni");

            var configurazioni = new List<ParametriRegoleInfo>();

            var idAoo = "";
            var idStruttura = "";
            var idNodo = "";

            //1. Se presente idProtocollo tento di risalire alla configurazione con cui era stato chiesto
            if (!String.IsNullOrEmpty(idProtocollo))
            {
                this._protocolloLogs.Debug("GetConfigurazioni - 1");
                var metadati = new ProtocolloMetadatiMgr(db).GetMetadati(idComune, idProtocollo);

                idAoo = metadati.Where(x => x.Metadato == "IDAOO").Select(x => x.Valore).FirstOrDefault();
                idStruttura = metadati.Where(x => x.Metadato == "IDSTRUTTURA").Select(x => x.Valore).FirstOrDefault();
                idNodo = metadati.Where(x => x.Metadato == "IDNODO").Select(x => x.Valore).FirstOrDefault();
            }

            //2. Se il riferimento AOO è vuoto, lo riprendo dalla verticalizzazione
            if (String.IsNullOrEmpty(idAoo))
            {
                this._protocolloLogs.Debug("GetConfigurazioni - 2");
                var vert = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloAcaris>(idComuneAlias, software, codiceComune);
                if (!vert.IdAOO.HasValue)
                {
                    throw new ArgumentNullException($"Impossibile risalire al parametro AOO associato al protocollo con identificativo {idProtocollo}");
                }
                idAoo = vert.IdAOO.Value.ToString();
                this._protocolloLogs.DebugFormat("idAoo: {0}", idAoo);
            }

            //3. Se ho anche idStruttura e idNodo posso uscire
            if (!String.IsNullOrEmpty(idStruttura) && !String.IsNullOrEmpty(idNodo))
            {
                this._protocolloLogs.Debug("GetConfigurazioni - 3");
                this._protocolloLogs.DebugFormat("idAoo: {0}, idStruttura: {1}, idNodo: {2}, operatore: {3}, idComune: {4}, software: {5}, codiceComune: {6}", idAoo, idStruttura, idNodo, operatore, idComune, software, codiceComune);
                configurazioni.Add(new ParametriRegoleInfoAdapter(serializer, operatore, idComune, software, codiceComune, Convert.ToInt32(idAoo), Convert.ToInt32(idStruttura), Convert.ToInt32(idNodo), this._verticalizzazioniFactory).Adatta());
                return configurazioni;
            }

            //4. Se è stata effettuata una lettura indicando anche UO e Ruolo, utilizzo quelli come configurazione
            if (!String.IsNullOrEmpty(this.Uo) && !String.IsNullOrEmpty(this.Ruolo))
            {
                this._protocolloLogs.Debug("GetConfigurazioni - 4");
                this._protocolloLogs.DebugFormat("idAoo: {0}, idStruttura: {1}, idNodo: {2}, operatore: {3}, idComune: {4}, software: {5}, codiceComune: {6}", idAoo, this.Uo, this.Ruolo, operatore, idComune, software, codiceComune);
                configurazioni.Add(new ParametriRegoleInfoAdapter(serializer, operatore, idComune, software, codiceComune, Convert.ToInt32(idAoo), Convert.ToInt32(this.Uo), Convert.ToInt32(this.Ruolo), this._verticalizzazioniFactory).Adatta());
                return configurazioni;
            }

            //5. Se sono ancora vuoti struttura o nodo, cerco tra tutte le amministrazioni configurate per il protocollo e tento una lettura con tutte le configurazioni possibili
            this._protocolloLogs.Debug("GetConfigurazioni - 5");

            var filtro = new AmministrazioniProtocollo
            {
                Idcomune = idComune
            };
            if (!String.IsNullOrEmpty(codiceComune))
            {
                filtro.OthersWhereClause.Add($"(CODICECOMUNE IS NULL OR CODICECOMUNE = '{codiceComune}')");
            }
            filtro.OthersWhereClause.Add($"(SOFTWARE IN ('TT','{software}'))");

            filtro.OthersWhereClause.Add("PROT_UO IS NOT NULL");
            filtro.OthersWhereClause.Add("PROT_RUOLO IS NOT NULL");
            foreach (var amministrazione in new AmministrazioniProtocolloMgr(db).GetList(filtro))
            {
                idStruttura = amministrazione.ProtUo;
                idNodo = amministrazione.ProtRuolo;

                var config = new ParametriRegoleInfoAdapter(serializer, operatore, idComune, software, codiceComune, Convert.ToInt32(idAoo), Convert.ToInt32(idStruttura), Convert.ToInt32(idNodo), this._verticalizzazioniFactory).Adatta();

                if (config != null)
                {
                    configurazioni.Add(config);
                }

                this._protocolloLogs.DebugFormat("idAoo: {0}, idStruttura: {1}, idNodo: {2}, operatore: {3}, idComune: {4}, software: {5}, codiceComune: {6}", idAoo, idStruttura, idNodo, operatore, idComune, software, codiceComune);
            }

            return configurazioni;
        }

        public override DatiFascicoloResponseType Fascicola(Fascicolo fascicolo)
        {
            throw new NotImplementedException();
        }

        public override DatiFascicoloResponseType CambiaFascicolo(Fascicolo fascicolo)
        {
            throw new NotImplementedException();
        }

        public override ListaFascicoliResponseType GetFascicoli(Fascicolo fascicolo)
        {
            throw new NotImplementedException();
        }

        public override List<MetadatoType> RecuperaMetadati()
        {
            return new List<MetadatoType>
            {
                new MetadatoType
                {
                    Chiave = MetadatiConstants.AnniConservazioneCorrente,
                    Valore = null
                },
                new MetadatoType
                {
                    Chiave = MetadatiConstants.AnniConservazioneGenerale,
                    Valore = null
                },
                new MetadatoType
                {
                    Chiave = MetadatiConstants.DescrizioneFascicolo,
                    Valore = null
                },
                new MetadatoType
                {
                    Chiave = MetadatiConstants.SerieDossier,
                    Valore = null
                },
                new MetadatoType
                {
                    Chiave = MetadatiConstants.SerieFascicoli,
                    Valore = null
                },
                new MetadatoType
                {
                    Chiave = MetadatiConstants.TemplateNumeroFascicolo,
                    Valore = null
                }
            };
        }

        private Dictionary<string, List<Dyn2Dato>> RecuperaDatiContesto(string idComune, string codiceIstanza)
        {
            if (string.IsNullOrEmpty(codiceIstanza))
            {
                return null;
            }

            return new Dyn2DatiService(this.DatiProtocollo.Db, idComune, Convert.ToInt32(codiceIstanza)).RecuperaDaIstanza(Contesti.Contesto);
        }

        private string RecuperaCodiceSede(Dictionary<string, List<Dyn2Dato>> datiContesto)
        {
            if (datiContesto == null || datiContesto.Count == 0)
            {
                throw new ConfigurationErrorsException("Errore, impossibile calcolare il codice della sede operativa da utilizzare");
            }

            if (datiContesto[Contesti.Codice] == null || !datiContesto[Contesti.Codice].Any())
            {
                //devo censire la nuova sede
                return null;
            }

            var codiceSede = datiContesto[Contesti.Codice].First(x => !String.IsNullOrEmpty(x.Valore));

            if (codiceSede != null)
            {
                return codiceSede.Valore;
            }

            //2. devo censire la nuova sede
            return null;
        }
    }
}
