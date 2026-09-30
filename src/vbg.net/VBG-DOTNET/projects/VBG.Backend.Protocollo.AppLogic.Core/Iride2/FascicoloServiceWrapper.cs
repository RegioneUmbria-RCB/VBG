using FascicolazioneIride2Service;
using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using ProtocolloIride2Service;
using VBG.Backend.Protocollo.AppLogic.Core.Iride2.Configuration;
using VBG.Backend.Protocollo.AppLogic.Core.Iride2.Fascicolazione;
using VBG.Backend.Protocollo.AppLogic.Core.SidUmbria.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Exceptions;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Iride2
{
    internal class FascicoloServiceWrapper
    {
        private readonly ProtocolloLogs _logs;
        private readonly ProtocolloSerializer _serializer;
        private readonly VerticalizzazioniConfiguration _vert;
        private readonly FascicolazioneClientServiceCreator _fascicolazioneClientServiceCreator;
        private readonly IFascicolazione _fascicolazioneService;
        private readonly string _operatore;
        private readonly string _ruolo;
        private readonly string _proxy;

        private readonly ProtocolloServiceWrapper _protocolloServiceWrapper;

        public FascicoloServiceWrapper(VerticalizzazioniConfiguration vert, string operatore, string ruolo, string proxy, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory)
        {
            _logs = logs;
            _serializer = serializer;
            _operatore = operatore;
            _ruolo = ruolo;
            _proxy = proxy;
            _vert = vert;
            _fascicolazioneClientServiceCreator = new FascicolazioneClientServiceCreator(logs, bindingFactory, proxy, vert.UrlFasc);
            _fascicolazioneService = FascicolazioneFactory.Create(vert.Versione, vert.UrlFasc, _proxy, _logs, _fascicolazioneClientServiceCreator);

            _protocolloServiceWrapper = new ProtocolloServiceWrapper(vert, operatore, ruolo, proxy, logs, serializer, bindingFactory);
        }

        public FascicoloOut LeggiFascicolo(string numero, string anno, string classifica, int idFascicolo, string operatore, string ruolo)
        {
            return _fascicolazioneService.LeggiFascicolo(numero, anno, classifica, idFascicolo, operatore, ruolo, _vert.CodiceAmministrazione, _vert.Aoo);
        }

        private FascicoloOut FascicoloEsistente(Shared.Data.Fascicolo fascicolo, string operatore, string ruolo)
        {
            FascicoloOut fascicoloOut;
            _logs.DebugFormat("Verifica se il fascicolo è esistente: numero fascicolo da controllare: {0}, anno fascicolo: {1}", fascicolo.NumeroFascicolo, fascicolo.AnnoFascicolo.ToString());
            ////_log.Debug("fascicolo.NumeroFascicolo: " + fascicolo.NumeroFascicolo + "| Classe: ProtocolloIride, Metodo: FascicoloEsistente(fascicolo)");

            if (String.IsNullOrEmpty(fascicolo.NumeroFascicolo) || (fascicolo.AnnoFascicolo == 0))
                fascicoloOut = new FascicoloOut();
            else
                fascicoloOut = LeggiFascicolo(fascicolo.NumeroFascicolo, fascicolo.AnnoFascicolo.ToString(), fascicolo.Classifica, 0, operatore, ruolo);

            return fascicoloOut;

        }

        private string GetDataFascicolo(string data)
        {
            DateTime dtFasc;
            var isValidDate = DateTime.TryParse(data, out dtFasc);

            if (!isValidDate && String.IsNullOrEmpty(_vert.FormatoDataFasc))
                return data;

            return dtFasc.ToString(_vert.FormatoDataFasc);
        }

        private FascicoloIn CreaFascicoloIn(Shared.Data.Fascicolo fascicolo)
        {
            FascicoloIn fascicoloIn = new FascicoloIn();
            fascicoloIn.Anno = fascicolo.AnnoFascicolo.ToString();
            fascicoloIn.Data = this.GetDataFascicolo(fascicolo.DataFascicolo);
            fascicoloIn.Numero = fascicolo.NumeroFascicolo;
            fascicoloIn.Oggetto = fascicolo.Oggetto;
            fascicoloIn.Classifica = fascicolo.Classifica;
            fascicoloIn.Utente = _operatore.ToUpper();
            fascicoloIn.Ruolo = _ruolo;

            return fascicoloIn;
        }

        private FascicoloOut FascicoloNuovo(Shared.Data.Fascicolo fascicolo)
        {
            FascicoloIn pFascicoloIn = CreaFascicoloIn(fascicolo);
            FascicoloOut pFascicoloOut = null;

            _serializer.LogAndValidate(ProtocolloLogsConstants.CreaFascicoloRequestFileName, pFascicoloIn);
            _logs.DebugFormat("Chiamata a CreaFascicolo");

            using (var ws = _fascicolazioneClientServiceCreator.CreateClient())
            {
                pFascicoloOut = ws.Service.CreaFascicolo(pFascicoloIn, _vert.CodiceAmministrazione, string.Empty);
            }
            
            _logs.DebugFormat("CreaFascicolo eseguito");
            _serializer.LogAndValidate(ProtocolloLogsConstants.CreaFascicoloResponseFileName, pFascicoloOut);

            if (pFascicoloOut.Id != 0)
                return pFascicoloOut;
            else
                throw new Exception("ERRORE GENERATO DAL WEB METHOD CREAFASCICOLO. MESSAGGIO: " + pFascicoloOut.Messaggio + ", ERRORE: " + pFascicoloOut.Errore);
        }

        public DatiFascicoloResponseType FascicolaProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo, Shared.Data.Fascicolo fascicolo, ResolveDatiProtocollazioneService datiProtocollo, string operatore, string ruolo)
        {
            FascicoloOut fascicoloOut = FascicoloEsistente(fascicolo, operatore, ruolo);
            int iFascicoloId;
            int iDocumentoId;
            DocumentoOut docOut;
            DatiFascicoloResponseType pDatiFascicolo = new DatiFascicoloResponseType();
            IstanzeMgr pIstanzaMgr = new IstanzeMgr(datiProtocollo.Db);

            _logs.InfoFormat("FASCICOLAZIONE AMBITO: {0}", datiProtocollo.TipoAmbito.ToString());
            if (datiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.NESSUNO)
            {
                int iDocumentoIdIstanza;

                _logs.DebugFormat("Lettura del protocollo durante la fase di fascicolazione, id protocollo: {0}, numero protocollo: {1}, data protocollo: {2}", idProtocollo, annoProtocollo, numeroProtocollo);
                docOut = _protocolloServiceWrapper.LeggiProtocolloDocumento(idProtocollo, annoProtocollo, numeroProtocollo);
                _logs.DebugFormat("Fine lettura del protocollo durante la fase di fascicolazione, id documento: {0}", docOut.IdDocumento.ToString());

                if (docOut.IdDocumento != 0)
                    iDocumentoId = docOut.IdDocumento;
                else
                    throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE DURANTE LA LETTURA DEL PROTOCOLLO, MESSAGGIO: {0}, ERRORE: {1}", docOut.Messaggio, docOut.Errore));

                //Usata per evitare di fascicolare ancora il movimento di avvio
                iDocumentoIdIstanza = iDocumentoId;

                _logs.DebugFormat("Id fascicolo: {0}", fascicoloOut.Id);

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
                        iFascicoloId = FascicoloNuovo(fascicolo).Id;
                        //_log.Debug("iFascicoloId: " + iFascicoloId);
                    }
                    else
                        throw new Exception("IL FASCICOLO SELEZIONATO NON ESISTE!!");

                }
                _logs.DebugFormat("Chiamata a FascicolaDocumento, IdFascicolo: {0}, IdDocumento: {1}, AggiornaClassifica: {2}, Operatore: {3}, Ruolo: {4}, CodiceAmministrazione: {5}, CodiceAoo: {6}", iFascicoloId, iDocumentoId, _vert.AggiornaClassifica, _operatore.ToUpper(), _ruolo, _vert.CodiceAmministrazione, String.Empty);
                
                using (var ws = _fascicolazioneClientServiceCreator.CreateClient())
                {
                    FascicolazioneIride2Service.EsitoOperazione esito = ws.Service.FascicolaDocumento(iFascicoloId, iDocumentoId, _vert.AggiornaClassifica, _operatore.ToUpper(), _ruolo, _vert.CodiceAmministrazione, string.Empty, string.Empty); //il parametro "Principale" non c'era prima. Ho messo string.Empty

                    if (!esito.Esito)
                        throw new Exception("Errore generato dal web method FascicolaDocumento durante la fascicolazione di una istanza.(id fascicolo: " + iFascicoloId + " id documento: " + iDocumentoId + ". Messaggio di errore: " + esito.Messaggio + ". " + esito.Errore + "\r\n");
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
                    //Il fascicolo non è esistente oppure non è passato
                    var fascOut = LeggiFascicolo("", "", "", iFascicoloId, _operatore.ToUpper(), _ruolo);

                    pDatiFascicolo.AnnoFascicolo = fascOut.Anno.ToString();
                    pDatiFascicolo.DataFascicolo = fascOut.Data.Value.ToString("dd/MM/yyyy");
                    pDatiFascicolo.NumeroFascicolo = fascOut.Numero;
                }
            }

            //Verifico se si intende fascicolare una pratica
            //_log.Debug("CodIstanza: " + CodIstanza + "| CLASSE: ProtocolloIride, METODO: FascicolaProtocollo(Fascicolo)");
            if (datiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
            {
                int iDocumentoIdIstanza;

                _logs.DebugFormat("Lettura del protocollo durante la fase di fascicolazione, id protocollo: {0}, numero protocollo: {1}, data protocollo: {2}", datiProtocollo.Istanza.FKIDPROTOCOLLO, datiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString(), datiProtocollo.Istanza.NUMEROPROTOCOLLO);
                docOut = _protocolloServiceWrapper.LeggiProtocolloDocumento(datiProtocollo.Istanza.FKIDPROTOCOLLO, datiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString(), datiProtocollo.Istanza.NUMEROPROTOCOLLO);

                _logs.DebugFormat("Fine lettura del protocollo durante la fase di fascicolazione, id documento: {0}", docOut.IdDocumento.ToString());

                if (docOut.IdDocumento != 0)
                    iDocumentoId = docOut.IdDocumento;
                else
                    throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE DURANTE LA LETTURA DEL PROTOCOLLO, MESSAGGIO: {0}, ERRORE: {1}", docOut.Messaggio, docOut.Errore));

                //Usata per evitare di fascicolare ancora il movimento di avvio
                iDocumentoIdIstanza = iDocumentoId;

                _logs.DebugFormat("Id fascicolo: {0}", fascicoloOut.Id);

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
                        iFascicoloId = FascicoloNuovo(fascicolo).Id;
                        //_log.Debug("iFascicoloId: " + iFascicoloId);
                    }
                    else
                        throw new Exception("IL FASCICOLO SELEZIONATO NON ESISTE!!");

                }
                _logs.DebugFormat("Chiamata a FascicolaDocumento, IdFascicolo: {0}, IdDocumento: {1}, AggiornaClassifica: {2}, Operatore: {3}, Ruolo: {4}, CodiceAmministrazione: {5}, CodiceAoo: {6}", iFascicoloId, iDocumentoId, _vert.AggiornaClassifica, _operatore.ToUpper(), _ruolo, _vert.CodiceAmministrazione, String.Empty);

                using (var ws = _fascicolazioneClientServiceCreator.CreateClient())
                {
                    var esito = ws.Service.FascicolaDocumento(iFascicoloId, iDocumentoId, _vert.AggiornaClassifica, _operatore.ToUpper(), _ruolo, _vert.CodiceAmministrazione, string.Empty, string.Empty);

                    if (!esito.Esito)
                        throw new ProtocolloException("Errore generato dal web method FascicolaDocumento durante la fascicolazione di una istanza.(id fascicolo: " + iFascicoloId + " id documento: " + iDocumentoId + ". Messaggio di errore: " + esito.Messaggio + ". " + esito.Errore + "\r\n");
                }


                //Fascicolo i moviemnti della pratica se protocollati
                MovimentiMgr pMovimentiMgr = new MovimentiMgr(datiProtocollo.Db);
                Movimenti _Movimento = new Movimenti();
                _Movimento.IDCOMUNE = datiProtocollo.IdComune;
                _Movimento.CODICEISTANZA = datiProtocollo.CodiceIstanza;
                List<Movimenti> list = pMovimentiMgr.GetList(_Movimento);
                foreach (Movimenti elem in list)
                {
                    if (!string.IsNullOrEmpty(elem.FKIDPROTOCOLLO) || (!string.IsNullOrEmpty(elem.NUMEROPROTOCOLLO) && elem.DATAPROTOCOLLO.HasValue))
                    {
                        //if (string.IsNullOrEmpty(elem.FKIDPROTOCOLLO))
                        //{
                        try
                        {
                            docOut = _protocolloServiceWrapper.LeggiProtocolloDocumento(elem.FKIDPROTOCOLLO, elem.DATAPROTOCOLLO.GetValueOrDefault(DateTime.MinValue).Year.ToString(), elem.NUMEROPROTOCOLLO);
                            if (docOut.IdDocumento != 0)
                            {
                                iDocumentoId = docOut.IdDocumento;
                            }
                            else
                                continue;
                        }
                        catch (Exception)
                        {
                            continue;
                        }
                        //}
                        //else
                        //    iDocumentoId = Convert.ToInt32(elem.FKIDPROTOCOLLO);

                        if (iDocumentoIdIstanza != iDocumentoId)
                        {
                            _logs.DebugFormat("Chiamata a FascicolaDocumento, IdFascicolo: {0}, IdDocumento: {1}, AggiornaClassifica: {2}, Operatore: {3}, Ruolo: {4}, CodiceAmministrazione: {5}, CodiceAoo: {6}", iFascicoloId, iDocumentoId, _vert.AggiornaClassifica, _operatore.ToUpper(), _ruolo, _vert.CodiceAmministrazione, String.Empty);

                            using (var ws = _fascicolazioneClientServiceCreator.CreateClient())
                            {
                                var esito = ws.Service.FascicolaDocumento(iFascicoloId, iDocumentoId, _vert.AggiornaClassifica, _operatore.ToUpper(), _ruolo, _vert.CodiceAmministrazione, string.Empty, string.Empty);

                                if (!esito.Esito)
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
                    //Il fascicolo non è esistente oppure non è passato
                    var fascOut = LeggiFascicolo("", "", "", iFascicoloId, _operatore.ToUpper(), _ruolo);

                    pDatiFascicolo.AnnoFascicolo = fascOut.Anno.ToString();
                    pDatiFascicolo.DataFascicolo = fascOut.Data.Value.ToString("dd/MM/yyyy");
                    pDatiFascicolo.NumeroFascicolo = fascOut.Numero;
                }
            }

            //Verifico se si intende fascicolare un movimento
            if (datiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.DA_MOVIMENTO)
            {
                _logs.DebugFormat("FascicoloOut.Id={0}", fascicoloOut.Id);

                if (fascicoloOut.Id != 0)
                {
                    iFascicoloId = fascicoloOut.Id;
                    //if (string.IsNullOrEmpty(_Movimento.FKIDPROTOCOLLO))
                    //{
                    _logs.Debug("Chiamata a LeggiProtocolloDocumento");
                    docOut = _protocolloServiceWrapper.LeggiProtocolloDocumento(datiProtocollo.Movimento.FKIDPROTOCOLLO, datiProtocollo.Movimento.DATAPROTOCOLLO.GetValueOrDefault(DateTime.MinValue).Year.ToString(), datiProtocollo.Movimento.NUMEROPROTOCOLLO);
                    _logs.DebugFormat("Fine chiamata a LeggiProtocolloDocumento, id {0}", docOut.IdDocumento);
                    if (docOut.IdDocumento == 0)
                        throw new Exception(String.Format("ERRORE GENERATO DAL WEB METHOD LEGGIPROTOCOLLO DURANTE LA FASCICOLAZIONE DI UN MOVIMENTO. MESSAGGIO DI ERRORE: {0}, ERRORE: {1}", docOut.Messaggio, docOut.Errore));

                    iDocumentoId = docOut.IdDocumento;

                    _logs.DebugFormat("Chiamata a FascicolaDocumento, IdFascicolo: {0}, IdDocumento: {1}, AggiornaClassifica: {2}, Operatore: {3}, Ruolo: {4}, CodiceAmministrazione: {5}, CodiceAoo: {6}", iFascicoloId, iDocumentoId, _vert.AggiornaClassifica, _operatore.ToUpper(), _ruolo, _vert.CodiceAmministrazione, String.Empty);

                    using (var ws = _fascicolazioneClientServiceCreator.CreateClient())
                    {
                        var esito = ws.Service.FascicolaDocumento(iFascicoloId, iDocumentoId, _vert.AggiornaClassifica, _operatore.ToUpper(), _ruolo, _vert.CodiceAmministrazione, string.Empty, string.Empty);

                        if (!esito.Esito)
                            throw new ProtocolloException("Errore generato dal web method FascicolaDocumento durante la fascicolazione di un movimento.(id fascicolo: " + iFascicoloId + " id documento: " + iDocumentoId + ". Messaggio di errore: " + esito.Messaggio + ". " + esito.Errore + "\r\n");
                    }

                    _logs.Debug("Documento Fascicolato");

                    pDatiFascicolo.AnnoFascicolo = fascicoloOut.Anno.ToString();
                    pDatiFascicolo.DataFascicolo = fascicoloOut.Data.Value.ToString("dd/MM/yyyy");
                    pDatiFascicolo.NumeroFascicolo = fascicoloOut.Numero;
                }
            }
            return pDatiFascicolo;
        }

        public DatiFascicoloResponseType CambiaFascicolo(string idProtocollo, string annoProtocollo, string numeroProtocollo, Shared.Data.Fascicolo fascicolo, ResolveDatiProtocollazioneService datiProtocollo, string operatore, string ruolo)
        {
            var fascicoloOut = FascicoloEsistente(fascicolo, operatore, ruolo);

            if (fascicoloOut.Id != 0)
                return FascicolaProtocollo(idProtocollo, annoProtocollo, numeroProtocollo, fascicolo, datiProtocollo, operatore, ruolo);
            else
                throw new ProtocolloException("Il fascicolo selezionato non esiste o non è stato passato!!");
        }

        public EtichetteResponseType StampaEtichette(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var docOut = _protocolloServiceWrapper.LeggiProtocolloDocumento(idProtocollo, annoProtocollo, numeroProtocollo);

            EtichetteResponseType datiEtichette = new EtichetteResponseType();

            if (docOut.IdDocumento != 0)
                datiEtichette.IdEtichetta = docOut.IdDocumento.ToString().PadLeft(8, '0');
            else
                throw new Exception(String.Format("ERRORE GENERATO DAL WEB METHOD LEGGIPROTOCOLLO. MESSAGGIO: {0}, ERRORE: {1}", docOut.Messaggio, docOut.Errore));

            return datiEtichette;
        }
    }
}
