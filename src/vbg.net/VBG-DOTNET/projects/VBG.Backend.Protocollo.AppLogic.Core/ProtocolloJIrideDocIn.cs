using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn;
using VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.Fascicolazione;
using VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.Fascicolazione.Lettura;
using VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.Protocollazione.LeggiProtocollo;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizzazioni.Core;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    public class PROTOCOLLO_JIRIDEDOCIN : ProtocolloBase
    {
        public static class Constants
        {
            public const string PERSONA_FISICA_IRIDE = "FI";
            public const string PERSONA_GIURIDICA_IRIDE = "GI";

        }

        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IBindingFactory _bindingFactory;

        public PROTOCOLLO_JIRIDEDOCIN(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._bindingFactory = bindingFactory;
        }

        private ProtocollazioneServiceWrapper _protocolloService;
        private FascicolazioneServiceWrapper _fascicolazione;

        private ParametriRegoleInfo _vert;
        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn proto)
        {
            this._vert = new ParametriRegoleInfo(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloJIrideDocIn>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            var protoIn = this.CreaProtocolloIn(proto);
            var conf = new ProtocollazioneConfiguration(proto, this.Anagrafiche, this._vert, this._protocolloLogs, this._protocolloSerializer, this.Ruolo, this.Operatore);
            var factory = ProtocollazioneFactory.Create(conf, this._bindingFactory);
            var protoOut = factory.Inserisci(protoIn);


            var adapterResponse = new ProtocollazioneOutAdapter(base.ModificaNumero, base.AggiungiAnno, base._protocolloLogs, this._vert.MessaggioProtoOk);
            var response = adapterResponse.Adatta(protoOut);

            return response;
        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(Shared.WsDataClass.LeggiProtocolloRequest leggiProtocolloRequest)
        {
            this._vert = new ParametriRegoleInfo(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloJIrideDocIn>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            this._protocolloLogs.Debug($"Inizio metodo LeggiProtocollo con idProtocollo: {leggiProtocolloRequest.IdProtocollo}, annoProtocollo: {leggiProtocolloRequest.AnnoProtocollo}, numeroProtocollo: {leggiProtocolloRequest.NumeroProtocollo}");

            this._protocolloLogs.Debug("Inizio creazione dei parametri della request");

            var request = new LeggiProtocolloBuilder(this._vert, leggiProtocolloRequest.IdProtocollo, leggiProtocolloRequest.NumeroProtocollo, leggiProtocolloRequest.AnnoProtocollo, this.Operatore, this.Ruolo).Build();
            this._protocolloLogs.Debug("Fine creazione dei parametri della request");

            this._protocolloLogs.Debug("Inizio chiamata al web service per la lettura del protocollo");
            var response = new LeggiProtocolloService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).Leggi(request);
            this._protocolloLogs.Debug("Fine chiamata al web service per la lettura del protocollo");

            this._protocolloLogs.Debug($"Inizio adattamento della risposta della lettura del protocollo con id {response.DocumentoOut.IdDocumento}");
            var retVal = response.ToDatiProtocolloLetto();
            this._protocolloLogs.Debug($"Fine adattamento della risposta della lettura del protocollo con id {response.DocumentoOut.IdDocumento}");

            this._protocolloLogs.Debug("Fine metodo LeggiProtocollo");

            return new List<DatiProtocolloLettoResponseType>() { retVal };
        }


        public override void CheckProtocolloLetto(string annoProtocollo, string numeroProtocollo, string idProtocollo, DatiProtocolloLettoResponseType pDatiProtocolloLetto)
        {
            if (String.IsNullOrEmpty(idProtocollo))
            {
                base.CheckProtocolloLetto(annoProtocollo, numeroProtocollo, idProtocollo, pDatiProtocolloLetto);
            }
        }

        public override AllegatoResponseType LeggiAllegato()
        {
            return base.LeggiAllegatoDaLeggiProtocollo();
        }

        public override DatiFascicoloResponseType Fascicola(Fascicolo fascicolo)
        {
            try
            {
                if (fascicolo == null)
                {
                    return null;
                }

                this._vert = new ParametriRegoleInfo(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloJIrideDocIn>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
                this._protocolloService = new ProtocollazioneServiceWrapper(this._vert.Url, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory, this._vert.CodiceAmministrazione, this._vert.Aoo);
                this._fascicolazione = new FascicolazioneServiceWrapper(this._vert.UrlFasc, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory, this._vert.CodiceAmministrazione, this._vert.Aoo);

                var retVal = this.FascicolaProtocollo(fascicolo);
                return retVal;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA FASCICOLAZIONE, {0}", ex.Message), ex);
            }
        }

        public override DatiFascicoloResponseType CambiaFascicolo(Fascicolo fascicolo)
        {
            try
            {
                this._protocolloLogs.InfoFormat("RICHIESTA DI CAMBIO FASCICOLO, NUMERO: {0}, CLASSIFICA: {1}, ANNO: {2}", fascicolo.NumeroFascicolo, fascicolo.Classifica, fascicolo.AnnoFascicolo);
                this._vert = new ParametriRegoleInfo(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloJIrideDocIn>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
                this._protocolloService = new ProtocollazioneServiceWrapper(this._vert.Url, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory, this._vert.CodiceAmministrazione, this._vert.Aoo);
                this._fascicolazione = new FascicolazioneServiceWrapper(this._vert.UrlFasc, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory, this._vert.CodiceAmministrazione, this._vert.Aoo);

                var isCopia = this._protocolloService.IsCopia(this.DatiProtocollo.Istanza.FKIDPROTOCOLLO);

                if (isCopia)
                {
                    throw new Exception("NON E' POSSIBILE MODIFICARE IL FASCICOLO DI UNA COPIA");
                }

                if (String.IsNullOrEmpty(fascicolo.NumeroFascicolo))
                {
                    var retVal = this.FascicolaProtocollo(fascicolo);
                    this._protocolloLogs.InfoFormat("CAMBIO FASCICOLO AVVENUTO CORRETTAMENTE, NUMERO: {0}, CLASSIFICA: {1}, ANNO: {2}", fascicolo.NumeroFascicolo, fascicolo.Classifica, fascicolo.AnnoFascicolo);
                    return retVal;
                }
                else
                {
                    var fascicoloOut = this.FascicoloEsistente(fascicolo);
                    if (fascicoloOut.Id != 0)
                    {
                        var retVal = this.FascicolaProtocollo(fascicolo);
                        this._protocolloLogs.InfoFormat("CAMBIO FASCICOLO AVVENUTO CORRETTAMENTE, NUMERO: {0}, CLASSIFICA: {1}, ANNO: {2}", fascicolo.NumeroFascicolo, fascicolo.Classifica, fascicolo.AnnoFascicolo);
                        return retVal;
                    }
                    else
                    {
                        throw new Exception($"IL FASCICOLO {fascicolo.NumeroFascicolo} SELEZIONATO NON ESISTE!!");
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL CAMBIAMENTO DI UN FASCICOLO, {0}", ex.Message), ex);
            }
        }

        public override DatiProtocolloFascicolatoResponseType IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            this._vert = new ParametriRegoleInfo(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloJIrideDocIn>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            this._protocolloService = new ProtocollazioneServiceWrapper(this._vert.Url, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory, this._vert.CodiceAmministrazione, this._vert.Aoo);
            this._fascicolazione = new FascicolazioneServiceWrapper(this._vert.UrlFasc, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory, this._vert.CodiceAmministrazione, this._vert.Aoo);

            return this.Fascicolato(idProtocollo, annoProtocollo, numeroProtocollo);
        }

        private FascicoloOutXml FascicoloEsistente(Fascicolo fascicolo)
        {
            if (String.IsNullOrEmpty(fascicolo.NumeroFascicolo) || (fascicolo.AnnoFascicolo == 0))
            {
                return new FascicoloOutXml();
            }
            else
            {
                var leggiFascicoloRequest = new LeggiFascicoloRequestBuilder(this._vert, fascicolo, null, this.Operatore, this.Ruolo).Build();
                var leggiFascicoloResponse = new FascicolazioneService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).LeggiFascicolo(leggiFascicoloRequest);
                return leggiFascicoloResponse;
            }
        }

        private DatiProtocolloFascicolatoResponseType Fascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var leggiProtocolloRequest = new LeggiProtocolloBuilder(this._vert, idProtocollo, numeroProtocollo, annoProtocollo, this.Operatore, this.Ruolo).Build();
            var protocolloLetto = new LeggiProtocolloService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).Leggi(leggiProtocolloRequest);

            //Verifico l'esistenza del protocollo passato
            if (protocolloLetto.DocumentoOut.IdDocumento == 0)
            {
                return new DatiProtocolloFascicolatoResponseType
                {
                    Fascicolato = EnumFascicolatoType.warning,
                    NoteFascicolo = "Errore: " + protocolloLetto.DocumentoOut.Messaggio + "." + protocolloLetto.DocumentoOut.Errore
                };
            }

            //Verifico se il protocollo è stato fascicolato
            if (protocolloLetto.DocumentoOut.IdPratica == 0 && (String.IsNullOrEmpty(protocolloLetto.DocumentoOut.NumeroPratica) || protocolloLetto.DocumentoOut.AnnoPratica == 0))
            {
                return new DatiProtocolloFascicolatoResponseType
                {
                    Fascicolato = EnumFascicolatoType.no
                };
            }

            //Se il protocollo è una copia, e sono presenti i riferimenti agli altri fascicoli, allora
            //recupero i riferimenti del fascicolo dal primo elemento degli altri fascicoli
            var leggiFascicoloRequest = this.CreaLeggiFascicoloRequest(idProtocollo, protocolloLetto.DocumentoOut);
            var leggiFascicoloResponse = new FascicolazioneService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).LeggiFascicolo(leggiFascicoloRequest);

            return new DatiProtocolloFascicolatoResponseType
            {
                AnnoFascicolo = leggiFascicoloResponse.Anno.ToString(),
                Classifica = String.IsNullOrEmpty(leggiFascicoloResponse.Classifica)
                                ? protocolloLetto.DocumentoOut.Classifica
                                : leggiFascicoloResponse.Classifica,
                DataFascicolo = leggiFascicoloResponse.Data.Value.ToString("dd/MM/yyyy"),
                NumeroFascicolo = leggiFascicoloResponse.NumeroSenzaClassifica,
                Oggetto = leggiFascicoloResponse.Oggetto,
                Fascicolato = EnumFascicolatoType.si
            };
        }

        private LeggiFascicoloRequest CreaLeggiFascicoloRequest(string idProtocollo, DocumentoOutXml protocollo)
        {
            //Se il protocollo è una copia, e sono presenti i riferimenti agli altri fascicoli, allora
            //recupero i riferimenti del fascicolo dal primo elemento degli altri fascicoli
            var isCopia = this._protocolloService.IsCopia(idProtocollo);

            if (isCopia && protocollo.AltriFascicoli != null && protocollo.AltriFascicoli.Length > 0)
            {
                var fascicoloDaAltriFascicoli = new Fascicolo
                {
                    AnnoFascicolo = protocollo.AltriFascicoli[0].AnnoAltroFascicolo,
                    Classifica = protocollo.AltriFascicoli[0].AnnoNumeroAltroFascicolo.Split('/').Length > 1
                                    ? protocollo.AltriFascicoli[0].AnnoNumeroAltroFascicolo.Split('/')[1]
                                    : null,
                    NumeroFascicolo = protocollo.AltriFascicoli[0].NumeroAltroFascicolo,

                };

                return new LeggiFascicoloRequestBuilder(this._vert, fascicoloDaAltriFascicoli, 0, this.Operatore, this.Ruolo).Build();
            }

            var fascicolo = new Fascicolo
            {
                AnnoFascicolo = protocollo.AnnoPratica,
                Classifica = protocollo.Classifica,
                NumeroFascicolo = protocollo.NumeroPratica,

            };

            return new LeggiFascicoloRequestBuilder(this._vert, fascicolo, protocollo.IdPratica, this.Operatore, this.Ruolo).Build();
        }

        private DatiFascicoloResponseType FascicolaProtocollo(Fascicolo fascicolo)
        {
            //this._vert = new ParametriRegoleInfo(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloJIrideDocIn>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

            var fascicoloOut = this.FascicoloEsistente(fascicolo);
            int iFascicoloId;
            int iDocumentoId;

            DatiFascicoloResponseType pDatiFascicolo = new DatiFascicoloResponseType();
            IstanzeMgr pIstanzaMgr = new IstanzeMgr(this.DatiProtocollo.Db);

            this._protocolloLogs.InfoFormat("FASCICOLAZIONE AMBITO: {0}", this.DatiProtocollo.TipoAmbito.ToString());
            if (this.DatiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.NESSUNO)
            {
                int iDocumentoIdIstanza;

                this._protocolloLogs.DebugFormat($"Lettura del protocollo durante la fase di fascicolazione, id protocollo: {this.IdProtocollo}, numero protocollo: {this.NumProtocollo}, anno: {this.AnnoProtocollo}");
                var leggiProtocolloRequest = new LeggiProtocolloBuilder(this._vert, this.IdProtocollo, this.NumProtocollo, this.AnnoProtocollo, this.Operatore, this.Ruolo).Build();
                var protocolloLetto = new LeggiProtocolloService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).Leggi(leggiProtocolloRequest);
                this._protocolloLogs.DebugFormat("Fine lettura del protocollo durante la fase di fascicolazione, id documento: {0}", protocolloLetto.DocumentoOut.IdDocumento.ToString());

                if (protocolloLetto.DocumentoOut.IdDocumento != 0)
                {
                    iDocumentoId = protocolloLetto.DocumentoOut.IdDocumento;
                }
                else
                {
                    throw new Exception($"ERRORE RESTITUITO DAL WEB SERVICE DURANTE LA LETTURA DEL PROTOCOLLO, MESSAGGIO: {protocolloLetto.DocumentoOut.Messaggio}, ERRORE: {protocolloLetto.DocumentoOut.Errore}");
                }

                //Usata per evitare di fascicolare ancora il movimento di avvio
                iDocumentoIdIstanza = iDocumentoId;

                this._protocolloLogs.DebugFormat("Id fascicolo: {0}", fascicoloOut.Id);

                if (fascicoloOut.Id != 0)
                    iFascicoloId = fascicoloOut.Id;
                else
                {
                    //_log.Debug("fascicolo.NumeroFascicolo: " + fascicolo.NumeroFascicolo + ", fascicolo.AnnoFascicolo: " + fascicolo.AnnoFascicolo + "| CLASSE: ProtocolloIride, METODO: FascicolaProtocollo(Fascicolo)");
                    //Prima sia se il fascicolo non era passato che se non era esistente veniva sempre creato
                    //il nuovo fascicolo. Ora viene creato solo se non è passato; se non esiste si comporta come il Cambio Fascicolo
                    if (String.IsNullOrEmpty(fascicolo.NumeroFascicolo) || (fascicolo.AnnoFascicolo == 0))
                    {
                        //Il fascicolo non è passato quindi viene creato
                        var fascicoloNuovoRequest = new FascicoloNuovoRequestBuilder(this._vert, fascicolo, this.Operatore, this.Ruolo).Build();
                        var fascicoloNuovoResponse = new FascicolazioneService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).FascicoloNuovo(fascicoloNuovoRequest);
                        iFascicoloId = fascicoloNuovoResponse.Id;
                    }
                    else
                        throw new Exception("IL FASCICOLO SELEZIONATO NON ESISTE!!");

                }
                var esito = this._fascicolazione.FascicolaDocumento(iFascicoloId, iDocumentoId, this._vert.AggiornaClassifica, this.Operatore.ToUpper(), this.Ruolo, "");

                if (fascicoloOut.Id != 0)
                {
                    //Il fascicolo è esistente
                    pDatiFascicolo.AnnoFascicolo = fascicoloOut.Anno.ToString();
                    pDatiFascicolo.DataFascicolo = fascicoloOut.Data.Value.ToString("dd/MM/yyyy");
                    pDatiFascicolo.NumeroFascicolo = fascicoloOut.Numero;
                }
                else
                {
                    var leggiFascicoloRequest = new LeggiFascicoloRequestBuilder(this._vert, new Fascicolo(), iFascicoloId, this.Operatore, this.Ruolo).Build();
                    var leggiFascicoloResponse = new FascicolazioneService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).LeggiFascicolo(leggiFascicoloRequest);

                    pDatiFascicolo.AnnoFascicolo = leggiFascicoloResponse.Anno.ToString();
                    pDatiFascicolo.DataFascicolo = leggiFascicoloResponse.Data.Value.ToString("dd/MM/yyyy");
                    pDatiFascicolo.NumeroFascicolo = leggiFascicoloResponse.Numero;
                }
            }

            //Verifico se si intende fascicolare una pratica
            if (this.DatiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
            {
                int iDocumentoIdIstanza;

                this._protocolloLogs.DebugFormat("Lettura del protocollo durante la fase di fascicolazione, id protocollo: {0}, numero protocollo: {1}, data protocollo: {2}", this.DatiProtocollo.Istanza.FKIDPROTOCOLLO, this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString(), this.DatiProtocollo.Istanza.NUMEROPROTOCOLLO);
                var leggiProtocolloRequest = new LeggiProtocolloBuilder(this._vert, this.DatiProtocollo.Istanza.FKIDPROTOCOLLO, this.DatiProtocollo.Istanza.NUMEROPROTOCOLLO, this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString(), this.Operatore, this.Ruolo).Build();
                var protocolloLetto = new LeggiProtocolloService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).Leggi(leggiProtocolloRequest);
                this._protocolloLogs.DebugFormat($"Fine lettura del protocollo durante la fase di fascicolazione, id documento: {protocolloLetto.DocumentoOut.IdDocumento}");

                if (protocolloLetto.DocumentoOut.IdDocumento != 0)
                    iDocumentoId = protocolloLetto.DocumentoOut.IdDocumento;
                else
                    throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE DURANTE LA LETTURA DEL PROTOCOLLO, MESSAGGIO: {0}, ERRORE: {1}", protocolloLetto.DocumentoOut.Messaggio, protocolloLetto.DocumentoOut.Errore));

                //Usata per evitare di fascicolare ancora il movimento di avvio
                iDocumentoIdIstanza = iDocumentoId;

                if (fascicoloOut.Id != 0)
                    iFascicoloId = fascicoloOut.Id;
                else
                {
                    //_log.Debug("fascicolo.NumeroFascicolo: " + fascicolo.NumeroFascicolo + ", fascicolo.AnnoFascicolo: " + fascicolo.AnnoFascicolo + "| CLASSE: ProtocolloIride, METODO: FascicolaProtocollo(Fascicolo)");
                    //Prima sia se il fascicolo non era passato che se non era esistente veniva sempre creato
                    //il nuovo fascicolo. Ora viene creato solo se non è passato; se non esiste si comporta come il Cambio Fascicolo
                    if (String.IsNullOrEmpty(fascicolo.NumeroFascicolo) || (fascicolo.AnnoFascicolo == 0))
                    {
                        //Il fascicolo non è passato quindi viene creato
                        var fascicoloNuovoRequest = new FascicoloNuovoRequestBuilder(this._vert, fascicolo, this.Operatore, this.Ruolo).Build();
                        var fascicoloNuovoResponse = new FascicolazioneService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).FascicoloNuovo(fascicoloNuovoRequest);
                        iFascicoloId = fascicoloNuovoResponse.Id;
                    }
                    else
                        throw new Exception("IL FASCICOLO SELEZIONATO NON ESISTE!!");

                }
                var esito = this._fascicolazione.FascicolaDocumento(iFascicoloId, iDocumentoId, this._vert.AggiornaClassifica, this.Operatore.ToUpper(), this.Ruolo, this.DatiProtocollo.Istanza.FKIDPROTOCOLLO);

                //Fascicolo i moviemnti della pratica se protocollati
                MovimentiMgr pMovimentiMgr = new MovimentiMgr(this.DatiProtocollo.Db);
                Movimenti _Movimento = new Movimenti();
                _Movimento.IDCOMUNE = this.DatiProtocollo.IdComune;
                _Movimento.CODICEISTANZA = this.DatiProtocollo.CodiceIstanza;
                var list = pMovimentiMgr.GetList(_Movimento);

                foreach (var elem in list)
                {
                    this._protocolloLogs.InfoFormat("AGGIORNAMENTO DEI FASCICOLI DEI MOVIMENTI, ISTANZA: {0} MOVIMENTO: {1}, NUMERO PROTOCOLLO: {2}, DATAPROTOCOLLO: {3}", elem.CODICEISTANZA, elem.CODICEMOVIMENTO, elem.FKIDPROTOCOLLO, elem.DATAPROTOCOLLO.HasValue ? elem.DATAPROTOCOLLO.Value.ToString("dd/MM/yyyy") : "");
                    if (!string.IsNullOrEmpty(elem.FKIDPROTOCOLLO) || (!string.IsNullOrEmpty(elem.NUMEROPROTOCOLLO) && elem.DATAPROTOCOLLO.HasValue))
                    {
                        try
                        {
                            var leggiProtMovimentoRequest = new LeggiProtocolloBuilder(this._vert, elem.FKIDPROTOCOLLO, elem.NUMEROPROTOCOLLO, elem.DATAPROTOCOLLO.GetValueOrDefault(DateTime.MinValue).Year.ToString(), this.Operatore, this.Ruolo).Build();
                            var protMovimentoLetto = new LeggiProtocolloService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).Leggi(leggiProtMovimentoRequest);

                            if (protMovimentoLetto.DocumentoOut.IdDocumento != 0)
                            {
                                iDocumentoId = protMovimentoLetto.DocumentoOut.IdDocumento;
                            }
                            else
                                continue;
                        }
                        catch (Exception)
                        {
                            continue;
                        }

                        if (iDocumentoIdIstanza != iDocumentoId)
                        {
                            try
                            {
                                esito = this._fascicolazione.FascicolaDocumento(iFascicoloId, iDocumentoId, this._vert.AggiornaClassifica, this.Operatore.ToUpper(), this.Ruolo, "");
                            }
                            catch (Exception)
                            {
                                continue;
                            }
                        }
                    }
                }

                if (fascicoloOut.Id != 0)
                {
                    //Il fascicolo è esistente
                    pDatiFascicolo.AnnoFascicolo = fascicoloOut.Anno.ToString();
                    pDatiFascicolo.DataFascicolo = fascicoloOut.Data.Value.ToString("dd/MM/yyyy");
                    pDatiFascicolo.NumeroFascicolo = fascicoloOut.Numero;
                }
                else
                {
                    var leggiFascicoloRequest = new LeggiFascicoloRequestBuilder(this._vert, new Fascicolo(), iFascicoloId, this.Operatore, this.Ruolo).Build();
                    var leggiFascicoloResponse = new FascicolazioneService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).LeggiFascicolo(leggiFascicoloRequest);

                    pDatiFascicolo.AnnoFascicolo = leggiFascicoloResponse.Anno.ToString();
                    pDatiFascicolo.DataFascicolo = leggiFascicoloResponse.Data.Value.ToString("dd/MM/yyyy");
                    pDatiFascicolo.NumeroFascicolo = leggiFascicoloResponse.Numero;
                }
            }

            //Verifico se si intende fascicolare un movimento
            if (this.DatiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.DA_MOVIMENTO)
            {
                this._protocolloLogs.DebugFormat("FascicoloOut.Id={0}", fascicoloOut.Id);

                if (fascicoloOut.Id != 0)
                {
                    iFascicoloId = fascicoloOut.Id;
                    //if (string.IsNullOrEmpty(_Movimento.FKIDPROTOCOLLO))
                    //{
                    this._protocolloLogs.Debug("Chiamata a LeggiProtocolloDocumento");
                    var leggiProtocolloRequest = new LeggiProtocolloBuilder(this._vert, this.DatiProtocollo.Movimento.FKIDPROTOCOLLO, this.DatiProtocollo.Movimento.NUMEROPROTOCOLLO, this.DatiProtocollo.Movimento.DATAPROTOCOLLO.GetValueOrDefault(DateTime.MinValue).Year.ToString(), this.Operatore, this.Ruolo).Build();
                    var protocolloLetto = new LeggiProtocolloService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).Leggi(leggiProtocolloRequest);
                    this._protocolloLogs.DebugFormat($"Fine chiamata a LeggiProtocolloDocumento, id {protocolloLetto.DocumentoOut.IdDocumento}");
                    if (protocolloLetto.DocumentoOut.IdDocumento == 0)
                    {
                        throw new Exception($"ERRORE GENERATO DAL WEB METHOD LEGGIPROTOCOLLO DURANTE LA FASCICOLAZIONE DI UN MOVIMENTO. MESSAGGIO DI ERRORE: {protocolloLetto.DocumentoOut.Messaggio}, ERRORE: {protocolloLetto.DocumentoOut.Errore}");
                    }

                    iDocumentoId = protocolloLetto.DocumentoOut.IdDocumento;

                    this._protocolloLogs.DebugFormat("Chiamata a FascicolaDocumento, IdFascicolo: {0}, IdDocumento: {1}, AggiornaClassifica: {2}, Operatore: {3}, Ruolo: {4}, CodiceAmministrazione: {5}, CodiceAoo: {6}", iFascicoloId, iDocumentoId, this._vert.AggiornaClassifica, this.Operatore.ToUpper(), this.Ruolo, this._vert.CodiceAmministrazione, String.Empty);
                    var esito = this._fascicolazione.FascicolaDocumento(iFascicoloId, iDocumentoId, this._vert.AggiornaClassifica, this.Operatore.ToUpper(), this.Ruolo, "");
                    if (!esito.Esito)
                    {
                        throw new Exception("Errore generato dal web method FascicolaDocumento durante la fascicolazione di un movimento.(id fascicolo: " + iFascicoloId + " id documento: " + iDocumentoId + ". Messaggio di errore: " + esito.Messaggio + ". " + esito.Errore + "\r\n");
                    }

                    this._protocolloLogs.Debug("Documento Fascicolato");

                    pDatiFascicolo.AnnoFascicolo = fascicoloOut.Anno.ToString();
                    pDatiFascicolo.DataFascicolo = fascicoloOut.Data.Value.ToString("dd/MM/yyyy");
                    pDatiFascicolo.NumeroFascicolo = fascicoloOut.Numero;
                }
            }

            return pDatiFascicolo;
        }

        private ProtocolloInXml CreaProtocolloIn(Shared.Data.DatiProtocolloIn datiProto)
        {
            var protoIn = new ProtocolloInXml();

            protoIn.Data = DateTime.Now.Date.ToString("dd/MM/yyyy");
            protoIn.Classifica = datiProto.Classifica;
            protoIn.TipoDocumento = datiProto.TipoDocumento;
            protoIn.Oggetto = datiProto.Oggetto;
            protoIn.Origine = datiProto.Flusso;
            protoIn.AggiornaAnagrafiche = this._vert.AggiornaAnagrafiche;

            if (!String.IsNullOrEmpty(datiProto.TipoSmistamento))
                protoIn.OggettoBilingue = datiProto.TipoSmistamento;

            //Gestione Fascicolazione (come comportarsi con la protocollazione delle autorizzazioni???)
            if (!String.IsNullOrEmpty(this._vert.NumeroPratica) && !this.GestisciFascicolazione)
            {
                //Fascicolazione con fascicolo faldone precedentemente creato
                protoIn.NumeroPratica = this._vert.NumeroPratica;
                protoIn.AnnoPratica = DateTime.Now.Year.ToString();
            }

            protoIn.Utente = this.Operatore.ToUpper();

            this._protocolloLogs.Debug("#### SetAllegati ####");
            //Setto gli allegati
            this.SetAllegati(protoIn, datiProto);

            this._protocolloLogs.Debug("#### SetMittenti ####");
            //Setto i mittenti
            this.SetMittenti(protoIn, datiProto);

            this._protocolloLogs.Debug("#### SetDestinatari ####");
            //Setto i destinatari
            this.SetDestinatari(protoIn, datiProto);

            return protoIn;
        }

        private void SetAllegati(ProtocolloInXml protoIn, DatiProtocolloIn datiProtoIn)
        {
            try
            {
                protoIn.Allegati = new AllegatoInXml[datiProtoIn.NumeroAllegatiPresenti];

                var iIndex = 0;
                datiProtoIn
                    .RecuperaAllegati()
                    .ToList()
                    .ForEach(x =>
                    {
                        if (x.OGGETTO == null)
                            throw new Exception($"OGGETTO {x.CODICEOGGETTO} NOME FILE {x.NOMEFILE} E' NULL");

                        protoIn.Allegati[iIndex] = new AllegatoInXml();

                        protoIn.Allegati[iIndex].ContentType = x.MimeType;
                        protoIn.Allegati[iIndex].Image = x.OGGETTO;

                        if (!String.IsNullOrEmpty(x.Extension))
                            protoIn.Allegati[iIndex].TipoFile = x.Extension.Substring(1);

                        protoIn.Allegati[iIndex].Commento = x.Descrizione;
                        protoIn.Allegati[iIndex].NomeAllegato = x.NOMEFILE;

                        iIndex++;
                    }
                );
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL SETTAGGIO DEGLI ALLEGATI, {0}", ex.Message), ex);
            }
        }

        private void SetMittenti(ProtocolloInXml protoIn, DatiProtocolloIn datiProto)
        {
            try
            {
                protoIn.MittenteInterno = "";
                var mittentiDestinatariList = new List<MittenteDestinatarioInXml>();

                //Verifico le amministrazioni (interne ed esterne)
                if (datiProto.Mittenti.Amministrazione.Count >= 1)
                {
                    if ((!String.IsNullOrEmpty(datiProto.Mittenti.Amministrazione[0].PROT_UO)) && (!String.IsNullOrEmpty(datiProto.Mittenti.Amministrazione[0].PROT_RUOLO)))
                    {
                        protoIn.MittenteInterno = String.IsNullOrEmpty(this._vert.UoSmistamento) ? datiProto.Mittenti.Amministrazione[0].PROT_UO : this._vert.UoSmistamento;
                        protoIn.Ruolo = String.IsNullOrEmpty(this._vert.UoSmistamento) ? datiProto.Mittenti.Amministrazione[0].PROT_RUOLO : this._vert.UoSmistamento;
                        //Se il flusso è in Partenza occorre settare anche InCaricoA e Ruolo con PROT_UO
                        if (protoIn.Origine == "P")
                        {
                            protoIn.MittenteInterno = datiProto.Mittenti.Amministrazione[0].PROT_UO;

                            if (!this._vert.DisabilitaCaricoPartenza)
                            {
                                protoIn.InCaricoA = datiProto.Mittenti.Amministrazione[0].PROT_UO;
                                protoIn.Ruolo = datiProto.Mittenti.Amministrazione[0].PROT_RUOLO; //modificato per test Ravenna
                            }
                        }
                    }
                    else if ((String.IsNullOrEmpty(datiProto.Mittenti.Amministrazione[0].PROT_UO)) ^ (String.IsNullOrEmpty(datiProto.Mittenti.Amministrazione[0].PROT_RUOLO)))
                        throw new Exception("PER ESEGUIRE UNA PROTOCOLLAZIONE CON IRIDE È NECESSARIO CHE L'AMMINISTRAZIONE INTERNA ABBIA SETTATO SIA L'UNITÀ ORGANIZZATIVA CHE IL RUOLO!\r\n");
                    else
                    {
                        //Ciclo per le amministrazioni esterne
                        foreach (var amministrazione in datiProto.Mittenti.Amministrazione)
                        {
                            //if (mittentiDestinatariList.Where(x => x.CodiceFiscale == amministrazione.PARTITAIVA).Count() > 0)
                            //    throw new Exception("SONO PRESENTI PIU' MITTENTI CON LO STESSO CODICE FISCALE / PARTITA IVA");

                            var mittentiDestinatari = new MittenteDestinatarioInXml();
                            if (mittentiDestinatariList.Count >= 100)
                            {
                                this._protocolloLogs.WarnFormat("Sono state conteggiati {0} mittenti mentre il limite massimo è di 99", mittentiDestinatariList.Count.ToString());
                                return;
                            }

                            mittentiDestinatari.Nome = String.Empty;
                            mittentiDestinatari.CodiceComuneResidenza = String.Empty;
                            mittentiDestinatari.DataNascita = String.Empty;
                            mittentiDestinatari.CodiceComuneNascita = String.Empty;
                            mittentiDestinatari.Nazionalita = String.Empty;
                            mittentiDestinatari.DataInvio_DataProt = String.Empty;
                            mittentiDestinatari.Spese_NProt = String.Empty;

                            mittentiDestinatari.TipoPersona = Constants.PERSONA_GIURIDICA_IRIDE;

                            if (String.IsNullOrEmpty(amministrazione.AMMINISTRAZIONE))
                                throw new Exception(String.Format("DESCRIZIONE AMMINISTRAZIONE ID {0} NON VALORIZZATA", amministrazione.CODICEAMMINISTRAZIONE));

                            mittentiDestinatari.CognomeNome = amministrazione.AMMINISTRAZIONE.TrimEnd();

                            if (!String.IsNullOrEmpty(amministrazione.UFFICIO))
                                mittentiDestinatari.CognomeNome = String.Concat(amministrazione.AMMINISTRAZIONE, " - ", amministrazione.UFFICIO.TrimEnd());

                            mittentiDestinatari.CodiceFiscale = !String.IsNullOrEmpty(amministrazione.PARTITAIVA) ? amministrazione.PARTITAIVA : String.Empty;
                            mittentiDestinatari.Indirizzo = !String.IsNullOrEmpty(amministrazione.INDIRIZZO) ? amministrazione.INDIRIZZO : String.Empty;

                            if (!String.IsNullOrEmpty(amministrazione.CITTA))
                            {
                                mittentiDestinatari.Localita = amministrazione.CITTA;
                            }

                            mittentiDestinatari.Mezzo = !String.IsNullOrEmpty(amministrazione.Mezzo) ? amministrazione.Mezzo : String.Empty;

                            //Setto il campo DataRicevimento che, in seguito ai test fatti a Cesena, è necessario settare
                            //nel caso in cui il flusso è in A
                            if (!String.IsNullOrEmpty(this.DatiProtocollo.CodiceIstanza))
                            {
                                var istMgr = new IstanzeMgr(this.DatiProtocollo.Db);
                                mittentiDestinatari.DataRicevimento = istMgr.GetById(this.DatiProtocollo.IdComune, Convert.ToInt32(this.DatiProtocollo.CodiceIstanza)).DATA.Value.ToString("dd/MM/yyyy");
                            }
                            if (!String.IsNullOrEmpty(this.DatiProtocollo.CodiceMovimento))
                            {
                                var movMgr = new MovimentiMgr(this.DatiProtocollo.Db);
                                mittentiDestinatari.DataRicevimento = movMgr.GetById(this.DatiProtocollo.IdComune, Convert.ToInt32(this.DatiProtocollo.CodiceMovimento)).DATA.Value.ToString("dd/MM/yyyy");
                            }

                            if (!String.IsNullOrEmpty(amministrazione.PEC))
                            {
                                var rec = new RecapitiEmailAdapter(amministrazione.PEC, this._vert.TipoRecapitoEmail);
                                mittentiDestinatari.Recapiti = rec.Recapiti;
                            }

                            mittentiDestinatariList.Add(mittentiDestinatari);
                        }
                    }
                }

                if (datiProto.Mittenti.Anagrafe.Count >= 1)
                {
                    foreach (var protoAnagrafe in datiProto.Mittenti.Anagrafe)
                    {
                        if (mittentiDestinatariList.Where(x => x.CodiceFiscale == protoAnagrafe.CODICEFISCALE || x.CodiceFiscale == protoAnagrafe.PARTITAIVA).Count() == 0)
                        {

                            if (mittentiDestinatariList.Count >= 100)
                            {
                                this._protocolloLogs.WarnFormat("Sono state conteggiati {0} mittenti mentre il limite massimo è di 99", mittentiDestinatariList.Count.ToString());
                                return;
                            }

                            var mittentiDestinatari = new MittenteDestinatarioInXml();

                            var codiceFiscalePartitaIva = "";

                            if (!String.IsNullOrEmpty(protoAnagrafe.CODICEFISCALE))
                                codiceFiscalePartitaIva = protoAnagrafe.CODICEFISCALE;
                            else if (!String.IsNullOrEmpty(protoAnagrafe.PARTITAIVA))
                                codiceFiscalePartitaIva = protoAnagrafe.PARTITAIVA;

                            mittentiDestinatari.CodiceFiscale = codiceFiscalePartitaIva;
                            mittentiDestinatari.CognomeNome = protoAnagrafe.NOMINATIVO;
                            mittentiDestinatari.Nome = protoAnagrafe.NOME ?? "";

                            if (protoAnagrafe.TIPOANAGRAFE == "F")
                            {
                                mittentiDestinatari.DataNascita = protoAnagrafe.DATANASCITA.HasValue ? protoAnagrafe.DATANASCITA.Value.ToString("dd/MM/yyyy") : String.Empty;
                                mittentiDestinatari.TipoPersona = Constants.PERSONA_FISICA_IRIDE;
                            }
                            else
                            {
                                mittentiDestinatari.DataNascita = protoAnagrafe.DATANOMINATIVO.GetValueOrDefault(DateTime.MinValue) == DateTime.MinValue ? String.Empty : protoAnagrafe.DATANOMINATIVO.Value.ToString("dd/MM/yyyy");
                                mittentiDestinatari.TipoPersona = Constants.PERSONA_GIURIDICA_IRIDE;
                            }

                            mittentiDestinatari.Indirizzo = protoAnagrafe.INDIRIZZO ?? "";

                            if (!String.IsNullOrEmpty(protoAnagrafe.Mezzo))
                                mittentiDestinatari.Mezzo = protoAnagrafe.Mezzo;

                            //Modificate per problemi con il Comune di Ravenna
                            mittentiDestinatari.CodiceComuneNascita = protoAnagrafe.CodiceIstatComNasc;
                            mittentiDestinatari.CodiceComuneResidenza = protoAnagrafe.CodiceIstatComRes;

                            if (!String.IsNullOrEmpty(protoAnagrafe.CITTA))
                            {
                                mittentiDestinatari.Localita = protoAnagrafe.CITTA;
                            }

                            mittentiDestinatari.Nazionalita = "100"; //N.B.: Il valore deve essere ricavato da una tabella Iride

                            //Setto il campo DataRicevimento che, in seguito ai test fatti a Cesena, è necessario settare
                            //nel caso in cui il flusso è in A
                            if (this.DatiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
                                mittentiDestinatari.DataRicevimento = this.DatiProtocollo.Istanza.DATA.Value.ToString("dd/MM/yyyy");

                            if (this.DatiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.DA_MOVIMENTO)
                                mittentiDestinatari.DataRicevimento = this.DatiProtocollo.Movimento.DATA.Value.ToString("dd/MM/yyyy");

                            if (!String.IsNullOrEmpty(protoAnagrafe.PecProtocollazione))
                            {
                                if (protoAnagrafe.PecProtocollazione.Equals(protoAnagrafe.PecAnagrafica) || String.IsNullOrEmpty(protoAnagrafe.PecAnagrafica))
                                {
                                    var rec = new RecapitiEmailAdapter(protoAnagrafe.PecProtocollazione, this._vert.TipoRecapitoEmail);
                                    mittentiDestinatari.Recapiti = rec.Recapiti;
                                }
                                else
                                {
                                    var pecs = new string[] { protoAnagrafe.PecProtocollazione, protoAnagrafe.PecAnagrafica };

                                    var rec = new RecapitiEmailAdapter(pecs, this._vert.TipoRecapitoEmail);
                                    mittentiDestinatari.Recapiti = rec.Recapiti;
                                }
                            }

                            mittentiDestinatariList.Add(mittentiDestinatari);
                        }
                        else
                            if (this.TipoInserimento == Source.PROT_IST_MOV_AUT_BO)
                                throw new Exception("SONO PRESENTI PIU' MITTENTI CON LO STESSO CODICE FISCALE / PARTITA IVA");
                    }
                }
                if (mittentiDestinatariList.Count > 0)
                    protoIn.MittentiDestinatari = mittentiDestinatariList.Take(99).ToArray();

            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL SETTAGGIO DEI MITTENTI, {0}", ex.Message), ex);
            }
        }

        private void SetDestinatari(ProtocolloInXml protoIn, Shared.Data.DatiProtocolloIn datiProto)
        {
            try
            {
                var mittentiDestinatariList = new List<MittenteDestinatarioInXml>();

                //Verifico le amministrazioni (interne ed esterne)
                if (datiProto.Destinatari.Amministrazione.Count >= 1)
                {
                    if ((!String.IsNullOrEmpty(datiProto.Destinatari.Amministrazione[0].PROT_UO)) && (!String.IsNullOrEmpty(datiProto.Destinatari.Amministrazione[0].PROT_RUOLO)))
                    {
                        if (protoIn.Origine == "A")
                        {
                            protoIn.InCaricoA = datiProto.Destinatari.Amministrazione[0].PROT_UO;
                            protoIn.Ruolo = datiProto.Destinatari.Amministrazione[0].PROT_RUOLO; //modificato per test Ravenna
                        }

                        //Se il flusso è Interno occorre settare anche il Tag MittentiDestinatari
                        if (protoIn.Origine == "I")
                        {
                            protoIn.InCaricoA = datiProto.Destinatari.Amministrazione[0].PROT_UO;
                            var mittentiDestinatari = new MittenteDestinatarioInXml();

                            mittentiDestinatari.Nome = String.Empty;
                            mittentiDestinatari.CodiceComuneResidenza = String.Empty;
                            mittentiDestinatari.DataNascita = String.Empty;
                            mittentiDestinatari.CodiceComuneNascita = String.Empty;
                            mittentiDestinatari.Nazionalita = String.Empty;
                            mittentiDestinatari.DataInvio_DataProt = String.Empty;
                            mittentiDestinatari.Spese_NProt = String.Empty;

                            if (String.IsNullOrEmpty(datiProto.Destinatari.Amministrazione[0].AMMINISTRAZIONE))
                                throw new Exception(String.Format("DESCRIZIONE AMMINISTRAZIONE ID {0} NON VALORIZZATA", datiProto.Destinatari.Amministrazione[0].CODICEAMMINISTRAZIONE));

                            mittentiDestinatari.CognomeNome = datiProto.Destinatari.Amministrazione[0].AMMINISTRAZIONE.TrimEnd();

                            if (!String.IsNullOrEmpty(datiProto.Destinatari.Amministrazione[0].UFFICIO))
                                mittentiDestinatari.CognomeNome = String.Concat(datiProto.Destinatari.Amministrazione[0].AMMINISTRAZIONE, " - ", datiProto.Destinatari.Amministrazione[0].UFFICIO.TrimEnd());

                            mittentiDestinatari.CodiceFiscale = !String.IsNullOrEmpty(datiProto.Destinatari.Amministrazione[0].PARTITAIVA) ? datiProto.Destinatari.Amministrazione[0].PARTITAIVA : String.Empty;
                            mittentiDestinatari.Indirizzo = !String.IsNullOrEmpty(datiProto.Destinatari.Amministrazione[0].INDIRIZZO) ? datiProto.Destinatari.Amministrazione[0].INDIRIZZO : String.Empty;

                            if (!String.IsNullOrEmpty(datiProto.Destinatari.Amministrazione[0].CITTA))
                            {
                                mittentiDestinatari.Localita = datiProto.Destinatari.Amministrazione[0].CITTA;
                            }

                            mittentiDestinatari.Mezzo = !String.IsNullOrEmpty(datiProto.Destinatari.Amministrazione[0].Mezzo) ? datiProto.Destinatari.Amministrazione[0].Mezzo : String.Empty;

                            mittentiDestinatari.TipoPersona = Constants.PERSONA_GIURIDICA_IRIDE;

                            mittentiDestinatariList.Add(mittentiDestinatari);
                            protoIn.MittentiDestinatari = mittentiDestinatariList.ToArray();

                        }
                    }
                    else if ((String.IsNullOrEmpty(datiProto.Destinatari.Amministrazione[0].PROT_RUOLO)) ^ (String.IsNullOrEmpty(datiProto.Destinatari.Amministrazione[0].PROT_RUOLO)))
                        throw new Exception("PER ESEGUIRE UNA PROTOCOLLAZIONE CON IRIDE È NECESSARIO CHE L'AMMINISTRAZIONE INTERNA ABBIA SETTATO SIA L'UNITÀ ORGANIZZATIVA CHE IL RUOLO!");
                }

                if (protoIn.Origine == ProtocolloConstants.COD_PARTENZA)
                {

                    var amministrazioniEsterne = datiProto.Destinatari.Amministrazione.Where(x => String.IsNullOrEmpty(x.PROT_UO) && String.IsNullOrEmpty(x.PROT_UO));

                    foreach (var amministrazione in amministrazioniEsterne)
                    {
                        if (mittentiDestinatariList.Count >= 100)
                        {
                            this._protocolloLogs.WarnFormat("Sono stati conteggiati {0} destinatari mentre il limite massimo è di 99", mittentiDestinatariList.Count.ToString());
                            return;
                        }
                        var mittentiDestinatari = new MittenteDestinatarioInXml();

                        mittentiDestinatari.Nome = String.Empty;
                        mittentiDestinatari.CodiceComuneResidenza = String.Empty;
                        mittentiDestinatari.DataNascita = String.Empty;
                        mittentiDestinatari.CodiceComuneNascita = String.Empty;
                        mittentiDestinatari.Nazionalita = String.Empty;
                        mittentiDestinatari.DataInvio_DataProt = String.Empty;
                        mittentiDestinatari.Spese_NProt = String.Empty;

                        if (String.IsNullOrEmpty(amministrazione.AMMINISTRAZIONE))
                            throw new Exception(String.Format("DESCRIZIONE AMMINISTRAZIONE ID {0} NON VALORIZZATA", amministrazione.AMMINISTRAZIONE));

                        mittentiDestinatari.CognomeNome = amministrazione.AMMINISTRAZIONE.TrimEnd();

                        if (!String.IsNullOrEmpty(amministrazione.UFFICIO))
                            mittentiDestinatari.CognomeNome = String.Concat(amministrazione.AMMINISTRAZIONE, " - ", amministrazione.UFFICIO.TrimEnd());

                        mittentiDestinatari.CodiceFiscale = !String.IsNullOrEmpty(amministrazione.PARTITAIVA) ? amministrazione.PARTITAIVA : String.Empty;
                        mittentiDestinatari.Indirizzo = !String.IsNullOrEmpty(amministrazione.INDIRIZZO) ? amministrazione.INDIRIZZO : String.Empty;

                        if (!String.IsNullOrEmpty(amministrazione.CITTA))
                        {
                            mittentiDestinatari.Localita = amministrazione.CITTA;
                        }

                        mittentiDestinatari.Mezzo = !String.IsNullOrEmpty(amministrazione.Mezzo) ? amministrazione.Mezzo : String.Empty;

                        mittentiDestinatari.TipoPersona = Constants.PERSONA_GIURIDICA_IRIDE;

                        if (!String.IsNullOrEmpty(amministrazione.PEC))
                        {
                            var rec = new RecapitiEmailAdapter(amministrazione.PEC, this._vert.TipoRecapitoEmail);
                            mittentiDestinatari.Recapiti = rec.Recapiti;
                        }
                        else
                        {
                            if (!String.IsNullOrEmpty(this._vert.UrlPec))
                            {
                                var warn = "";
                                if (String.IsNullOrEmpty(amministrazione.PARTITAIVA))
                                    warn = String.Format("LA PEC E LA PARTITA IVA DELL'AMMINISTRAZIONE {0}, (CODICE {1}), NON SONO VALORIZZATI, E' PROBABILE QUINDI CHE LA PEC NON SIA STATA INVIATA DAL SERVIZIO POSTE WEB DI IRIDE", amministrazione.AMMINISTRAZIONE, amministrazione.CODICEAMMINISTRAZIONE);
                                else
                                    warn = String.Format("LA PEC DELL'AMMINISTRAZIONE {0}, (CODICE {1}), NON E' VALORIZZATA, CONTROLLARE SU IRIDE, SE L'ANAGRAFICA CON PARTITA IVA {2} ABBIA IL RECAPITO EMAIL VALORIZZATO.", amministrazione.AMMINISTRAZIONE, amministrazione.CODICEAMMINISTRAZIONE, amministrazione.PARTITAIVA);

                                this._protocolloLogs.Warn(warn);
                            }
                        }

                        mittentiDestinatariList.Add(mittentiDestinatari);
                        //}
                    }
                }

                foreach (var anagrafe in datiProto.Destinatari.Anagrafe)
                {
                    if (mittentiDestinatariList.Where(x => x.CodiceFiscale == anagrafe.CODICEFISCALE || x.CodiceFiscale == anagrafe.PARTITAIVA).Count() > 0)
                        throw new Exception("SONO PRESENTI PIU' DESTINATARI CON LO STESSO CODICE FISCALE / PARTITA IVA");

                    if (mittentiDestinatariList.Count >= 100)
                    {
                        this._protocolloLogs.WarnFormat("Sono state conteggiati {0} destinatari mentre il limite massimo è di 99", mittentiDestinatariList.Count.ToString());
                        return;
                    }

                    var mittentiDestinatari = new MittenteDestinatarioInXml();

                    mittentiDestinatari.Nome = String.Empty;
                    mittentiDestinatari.CodiceComuneResidenza = String.Empty;
                    mittentiDestinatari.DataNascita = String.Empty;
                    mittentiDestinatari.CodiceComuneNascita = String.Empty;
                    mittentiDestinatari.Nazionalita = String.Empty;
                    mittentiDestinatari.DataInvio_DataProt = String.Empty;
                    mittentiDestinatari.Spese_NProt = String.Empty;

                    var codiceFiscalePartitaIva = "";

                    if (!String.IsNullOrEmpty(anagrafe.CODICEFISCALE))
                        codiceFiscalePartitaIva = anagrafe.CODICEFISCALE;
                    else if (!String.IsNullOrEmpty(anagrafe.PARTITAIVA))
                        codiceFiscalePartitaIva = anagrafe.PARTITAIVA;

                    if (!String.IsNullOrEmpty(codiceFiscalePartitaIva))
                        mittentiDestinatari.CodiceFiscale = codiceFiscalePartitaIva;

                    mittentiDestinatari.CognomeNome = anagrafe.NOMINATIVO;
                    mittentiDestinatari.Nome = anagrafe.NOME ?? "";

                    if (anagrafe.TIPOANAGRAFE == "F")
                    {
                        mittentiDestinatari.DataNascita = anagrafe.DATANASCITA.GetValueOrDefault(DateTime.MinValue) == DateTime.MinValue ? "" : anagrafe.DATANASCITA.Value.ToString("dd/MM/yyyy");
                        mittentiDestinatari.TipoPersona = Constants.PERSONA_FISICA_IRIDE;
                    }
                    else
                    {
                        mittentiDestinatari.DataNascita = anagrafe.DATANOMINATIVO.GetValueOrDefault(DateTime.MinValue) == DateTime.MinValue ? "" : anagrafe.DATANOMINATIVO.Value.ToString("dd/MM/yyyy");
                        mittentiDestinatari.TipoPersona = Constants.PERSONA_GIURIDICA_IRIDE;
                    }

                    mittentiDestinatari.Indirizzo = anagrafe.INDIRIZZO ?? "";

                    //Modificate per problemi con il Comune di Ravenna
                    mittentiDestinatari.CodiceComuneNascita = anagrafe.CodiceIstatComNasc;
                    mittentiDestinatari.CodiceComuneResidenza = anagrafe.CodiceIstatComRes;

                    if (!String.IsNullOrEmpty(anagrafe.CITTA))
                    {
                        mittentiDestinatari.Localita = anagrafe.CITTA;
                    }

                    if (!String.IsNullOrEmpty(anagrafe.Mezzo))
                        mittentiDestinatari.Mezzo = anagrafe.Mezzo;

                    mittentiDestinatari.Nazionalita = "100"; //N.B.: Il valore deve essere ricavato da una tabella Iride

                    if (!String.IsNullOrEmpty(anagrafe.PecProtocollazione))
                    {
                        if (anagrafe.PecProtocollazione.Equals(anagrafe.PecAnagrafica) || String.IsNullOrEmpty(anagrafe.PecAnagrafica))
                        {
                            var rec = new RecapitiEmailAdapter(anagrafe.PecProtocollazione, this._vert.TipoRecapitoEmail);
                            mittentiDestinatari.Recapiti = rec.Recapiti;
                        }
                        else
                        {
                            var pecs = new string[] { anagrafe.PecProtocollazione, anagrafe.PecAnagrafica };

                            var rec = new RecapitiEmailAdapter(pecs, this._vert.TipoRecapitoEmail);
                            mittentiDestinatari.Recapiti = rec.Recapiti;
                        }
                    }
                    else
                    {
                        if (!String.IsNullOrEmpty(this._vert.UrlPec))
                        {
                            var warn = "";
                            if (String.IsNullOrEmpty(codiceFiscalePartitaIva))
                                warn = String.Format("LA PEC E IL CODICE FISCALE/PARTITA IVA DELL'ANAGRAFICA {0}, (CODICE {1}), NON SONO VALORIZZATI, E' PROBABILE QUINDI CHE LA PEC NON SIA STATA INVIATA DAL SERVIZIO POSTE WEB DI IRIDE", anagrafe.NOMINATIVO, anagrafe.CODICEANAGRAFE);
                            else
                                warn = String.Format("LA PEC DELL'ANAGRAFICA {0}, (CODICE {1}), NON E' VALORIZZATA, CONTROLLARE SU IRIDE, SE L'ANAGRAFICA CON CODICE FISCALE {2} ABBIA IL RECAPITO EMAIL VALORIZZATO.", anagrafe.NOMINATIVO, anagrafe.CODICEANAGRAFE, codiceFiscalePartitaIva);

                            this._protocolloLogs.Warn(warn);
                        }
                    }

                    mittentiDestinatariList.Add(mittentiDestinatari);
                }

                if (mittentiDestinatariList.Count == 0 && datiProto.Flusso == ProtocolloConstants.COD_PARTENZA)
                {
                    var amministrazioniInterne = datiProto.Destinatari.Amministrazione.Where(x => !String.IsNullOrEmpty(x.PROT_UO) || !String.IsNullOrEmpty(x.PROT_UO)).ToList();
                    var primaAmministrazione = amministrazioniInterne.First();

                    protoIn.Origine = ProtocolloConstants.COD_INTERNO;

                    protoIn.InCaricoA = primaAmministrazione.PROT_UO;
                    protoIn.Ruolo = primaAmministrazione.PROT_RUOLO;
                }

                if (mittentiDestinatariList.Count > 0)
                    protoIn.MittentiDestinatari = mittentiDestinatariList.Take(99).ToArray();
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL SETTAGGIO DEI DESTINATARI, {0}", ex.Message), ex);
            }
        }

        public override ListaFirmatari GetFirmatari()
        {
            return new ListaFirmatari
            {
                Firmatari = new List<Firmatario>()
            };
        }

    }
}
