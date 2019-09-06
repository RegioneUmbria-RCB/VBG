using System;
using System.IO;
using System.Text;
using System.Xml.Serialization;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Verticalizzazioni;
using Init.Utils;
using System.Text.RegularExpressions;
using System.Collections.Generic;
using Init.SIGePro.Exceptions.Protocollo;
using PersonalLib2.Exceptions;
using PersonalLib2.Data;
using System.Data;
using log4net;
using Init.SIGePro.Protocollo.Validation;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Data;
using System.ServiceModel;
using System.Web;
using System.Linq;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.JIride.Protocollazione;
using Init.SIGePro.Protocollo.JIride;
using Init.SIGePro.Protocollo.JIride.PEC;
using Init.SIGePro.Protocollo.JIride.Fascicolazione;
using Init.SIGePro.Protocollo.FascicolazioneJIrideService;

namespace Init.SIGePro.Protocollo
{
    /// <summary>
    /// Descrizione di riepilogo per PROTOCOLLO_IRIDE.
    /// </summary>
    public class PROTOCOLLO_JIRIDE : ProtocolloBase
    {
        public PROTOCOLLO_JIRIDE()
        {

        }

        public static class Constants
        {
            public const string PERSONA_FISICA_IRIDE = "FI";
            public const string PERSONA_GIURIDICA_IRIDE = "GI";

        }

        ProtocollazioneServiceWrapper _protocolloService;
        FascicolazioneServiceWrapper _fascicolazione;

        ParametriRegoleInfo _vert;

        public override DatiProtocolloFascicolato IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            _vert = new ParametriRegoleInfo(_protocolloLogs, new VerticalizzazioneProtocolloJIride(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

            _protocolloService = new ProtocollazioneServiceWrapper(_vert.Url, _protocolloLogs, _protocolloSerializer, _vert.CodiceAmministrazione, _vert.Aoo);
            _fascicolazione = new FascicolazioneServiceWrapper(_vert.UrlFasc, _protocolloLogs, _protocolloSerializer, _vert.CodiceAmministrazione, _vert.Aoo);

            return Fascicolato(idProtocollo, annoProtocollo, numeroProtocollo);
        }

        private FascicoloOutXml FascicoloEsistente(Fascicolo fascicolo)
        {
            if (String.IsNullOrEmpty(fascicolo.NumeroFascicolo) || (fascicolo.AnnoFascicolo == 0))
            {
                return new FascicoloOutXml();
            }
            else
            {
                return _fascicolazione.LeggiFascicolo(fascicolo.NumeroFascicolo, fascicolo.AnnoFascicolo.ToString(), fascicolo.Classifica, 0, Operatore.ToUpper(), Ruolo);
            }
        }

        private FascicoloOutXml FascicoloNuovo(Fascicolo fascicolo)
        {
            var info = new FascicolazioneInfo(fascicolo.AnnoFascicolo.ToString(), fascicolo.DataFascicolo, fascicolo.NumeroFascicolo, fascicolo.Oggetto, fascicolo.Classifica, this.Operatore, this.Ruolo, _vert);
            var fascicoloOut = _fascicolazione.CreaFascicolo(info);

            return fascicoloOut;
        }

        public override DatiFascicolo Fascicola(Fascicolo fascicolo)
        {
            try
            {
                if (fascicolo == null)
                {
                    return null;
                }

                _vert = new ParametriRegoleInfo(_protocolloLogs, new VerticalizzazioneProtocolloJIride(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                _protocolloService = new ProtocollazioneServiceWrapper(_vert.Url, _protocolloLogs, _protocolloSerializer, _vert.CodiceAmministrazione, _vert.Aoo);
                _fascicolazione = new FascicolazioneServiceWrapper(_vert.UrlFasc, _protocolloLogs, _protocolloSerializer, _vert.CodiceAmministrazione, _vert.Aoo);

                var retVal = FascicolaProtocollo(fascicolo);
                return retVal;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA FASCICOLAZIONE, {0}", ex.Message), ex);
            }
        }

        private DatiFascicolo FascicolaProtocollo(Fascicolo fascicolo)
        {
            FascicoloOutXml fascicoloOut = FascicoloEsistente(fascicolo);
            int iFascicoloId;
            int iDocumentoId;
            DocumentoOutXml docOut;
            DatiFascicolo pDatiFascicolo = new DatiFascicolo();
            IstanzeMgr pIstanzaMgr = new IstanzeMgr(this.DatiProtocollo.Db);

            _protocolloLogs.InfoFormat("FASCICOLAZIONE AMBITO: {0}", this.DatiProtocollo.TipoAmbito.ToString());
            if (this.DatiProtocollo.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.NESSUNO)
            {
                int iDocumentoIdIstanza;

                _protocolloLogs.DebugFormat("Lettura del protocollo durante la fase di fascicolazione, id protocollo: {0}, numero protocollo: {1}, data protocollo: {2}", this.IdProtocollo, this.AnnoProtocollo, this.NumProtocollo);
                docOut = LeggiProtocolloDocumento(this.IdProtocollo, this.AnnoProtocollo, this.NumProtocollo);
                _protocolloLogs.DebugFormat("Fine lettura del protocollo durante la fase di fascicolazione, id documento: {0}", docOut.IdDocumento.ToString());

                if (docOut.IdDocumento != 0)
                    iDocumentoId = docOut.IdDocumento;
                else
                    throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE DURANTE LA LETTURA DEL PROTOCOLLO, MESSAGGIO: {0}, ERRORE: {1}", docOut.Messaggio, docOut.Errore));

                //Usata per evitare di fascicolare ancora il movimento di avvio
                iDocumentoIdIstanza = iDocumentoId;

                _protocolloLogs.DebugFormat("Id fascicolo: {0}", fascicoloOut.Id);

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
                var esito = _fascicolazione.FascicolaDocumento(iFascicoloId, iDocumentoId, _vert.AggiornaClassifica, Operatore.ToUpper(), Ruolo, "");

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
                    var fascOut = _fascicolazione.LeggiFascicolo("", "", "", iFascicoloId, Operatore.ToUpper(), Ruolo);

                    pDatiFascicolo.AnnoFascicolo = fascOut.Anno.ToString();
                    pDatiFascicolo.DataFascicolo = fascOut.Data.Value.ToString("dd/MM/yyyy");
                    pDatiFascicolo.NumeroFascicolo = fascOut.Numero;
                }
            }

            //Verifico se si intende fascicolare una pratica
            //_log.Debug("CodIstanza: " + CodIstanza + "| CLASSE: ProtocolloIride, METODO: FascicolaProtocollo(Fascicolo)");
            if (this.DatiProtocollo.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA)
            {
                int iDocumentoIdIstanza;

                _protocolloLogs.DebugFormat("Lettura del protocollo durante la fase di fascicolazione, id protocollo: {0}, numero protocollo: {1}, data protocollo: {2}", this.DatiProtocollo.Istanza.FKIDPROTOCOLLO, this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString(), this.DatiProtocollo.Istanza.NUMEROPROTOCOLLO);
                docOut = LeggiProtocolloDocumento(this.DatiProtocollo.Istanza.FKIDPROTOCOLLO, this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString(), this.DatiProtocollo.Istanza.NUMEROPROTOCOLLO);

                _protocolloLogs.DebugFormat("Fine lettura del protocollo durante la fase di fascicolazione, id documento: {0}", docOut.IdDocumento.ToString());

                if (docOut.IdDocumento != 0)
                    iDocumentoId = docOut.IdDocumento;
                else
                    throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE DURANTE LA LETTURA DEL PROTOCOLLO, MESSAGGIO: {0}, ERRORE: {1}", docOut.Messaggio, docOut.Errore));

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
                        iFascicoloId = FascicoloNuovo(fascicolo).Id;
                    }
                    else
                        throw new Exception("IL FASCICOLO SELEZIONATO NON ESISTE!!");

                }
                var esito = _fascicolazione.FascicolaDocumento(iFascicoloId, iDocumentoId, _vert.AggiornaClassifica, Operatore.ToUpper(), Ruolo, this.DatiProtocollo.Istanza.FKIDPROTOCOLLO);

                //Fascicolo i moviemnti della pratica se protocollati
                MovimentiMgr pMovimentiMgr = new MovimentiMgr(this.DatiProtocollo.Db);
                Movimenti _Movimento = new Movimenti();
                _Movimento.IDCOMUNE = DatiProtocollo.IdComune;
                _Movimento.CODICEISTANZA = DatiProtocollo.CodiceIstanza;
                List<Movimenti> list = pMovimentiMgr.GetList(_Movimento);

                foreach (Movimenti elem in list)
                {
                    _protocolloLogs.InfoFormat("AGGIORNAMENTO DEI FASCICOLI DEI MOVIMENTI, ISTANZA: {0} MOVIMENTO: {1}, NUMERO PROTOCOLLO: {2}, DATAPROTOCOLLO: {3}", elem.CODICEISTANZA, elem.CODICEMOVIMENTO, elem.FKIDPROTOCOLLO, elem.DATAPROTOCOLLO.HasValue ? elem.DATAPROTOCOLLO.Value.ToString("dd/MM/yyyy") : "");
                    if (!string.IsNullOrEmpty(elem.FKIDPROTOCOLLO) || (!string.IsNullOrEmpty(elem.NUMEROPROTOCOLLO) && elem.DATAPROTOCOLLO.HasValue))
                    {
                        try
                        {
                            docOut = LeggiProtocolloDocumento(elem.FKIDPROTOCOLLO, elem.DATAPROTOCOLLO.GetValueOrDefault(DateTime.MinValue).Year.ToString(), elem.NUMEROPROTOCOLLO);
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

                        if (iDocumentoIdIstanza != iDocumentoId)
                        {
                            try
                            {
                                esito = _fascicolazione.FascicolaDocumento(iFascicoloId, iDocumentoId, _vert.AggiornaClassifica, Operatore.ToUpper(), Ruolo, "");
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
                    //Il fascicolo non è esistente oppure non è passato
                    var fascOut = _fascicolazione.LeggiFascicolo("", "", "", iFascicoloId, Operatore.ToUpper(), Ruolo);

                    pDatiFascicolo.AnnoFascicolo = fascOut.Anno.ToString();
                    pDatiFascicolo.DataFascicolo = fascOut.Data.Value.ToString("dd/MM/yyyy");
                    pDatiFascicolo.NumeroFascicolo = fascOut.Numero;
                }
            }

            //Verifico se si intende fascicolare un movimento
            if (this.DatiProtocollo.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO)
            {
                EsitoOperazione esito;

                _protocolloLogs.DebugFormat("FascicoloOut.Id={0}", fascicoloOut.Id);

                if (fascicoloOut.Id != 0)
                {
                    iFascicoloId = fascicoloOut.Id;
                    //if (string.IsNullOrEmpty(_Movimento.FKIDPROTOCOLLO))
                    //{
                    _protocolloLogs.Debug("Chiamata a LeggiProtocolloDocumento");
                    docOut = LeggiProtocolloDocumento(this.DatiProtocollo.Movimento.FKIDPROTOCOLLO, this.DatiProtocollo.Movimento.DATAPROTOCOLLO.GetValueOrDefault(DateTime.MinValue).Year.ToString(), this.DatiProtocollo.Movimento.NUMEROPROTOCOLLO);
                    _protocolloLogs.DebugFormat("Fine chiamata a LeggiProtocolloDocumento, id {0}", docOut.IdDocumento);
                    if (docOut.IdDocumento == 0)
                        throw new Exception(String.Format("ERRORE GENERATO DAL WEB METHOD LEGGIPROTOCOLLO DURANTE LA FASCICOLAZIONE DI UN MOVIMENTO. MESSAGGIO DI ERRORE: {0}, ERRORE: {1}", docOut.Messaggio, docOut.Errore));

                    iDocumentoId = docOut.IdDocumento;

                    _protocolloLogs.DebugFormat("Chiamata a FascicolaDocumento, IdFascicolo: {0}, IdDocumento: {1}, AggiornaClassifica: {2}, Operatore: {3}, Ruolo: {4}, CodiceAmministrazione: {5}, CodiceAoo: {6}", iFascicoloId, iDocumentoId, _vert.AggiornaClassifica, Operatore.ToUpper(), Ruolo, _vert.CodiceAmministrazione, String.Empty);
                    esito = _fascicolazione.FascicolaDocumento(iFascicoloId, iDocumentoId, _vert.AggiornaClassifica, Operatore.ToUpper(), Ruolo, "");
                    if (!esito.Esito)
                        throw new ProtocolloException("Errore generato dal web method FascicolaDocumento durante la fascicolazione di un movimento.(id fascicolo: " + iFascicoloId + " id documento: " + iDocumentoId + ". Messaggio di errore: " + esito.Messaggio + ". " + esito.Errore + "\r\n");

                    _protocolloLogs.Debug("Documento Fascicolato");

                    pDatiFascicolo.AnnoFascicolo = fascicoloOut.Anno.ToString();
                    pDatiFascicolo.DataFascicolo = fascicoloOut.Data.Value.ToString("dd/MM/yyyy");
                    pDatiFascicolo.NumeroFascicolo = fascicoloOut.Numero;
                }
            }
            return pDatiFascicolo;
        }

        public override DatiFascicolo CambiaFascicolo(Fascicolo fascicolo)
        {
            try
            {
                _protocolloLogs.InfoFormat("RICHIESTA DI CAMBIO FASCICOLO, NUMERO: {0}, CLASSIFICA: {1}, ANNO: {2}", fascicolo.NumeroFascicolo, fascicolo.Classifica, fascicolo.AnnoFascicolo);
                _vert = new ParametriRegoleInfo(_protocolloLogs, new VerticalizzazioneProtocolloJIride(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                _protocolloService = new ProtocollazioneServiceWrapper(_vert.Url, _protocolloLogs, _protocolloSerializer, _vert.CodiceAmministrazione, _vert.Aoo);
                _fascicolazione = new FascicolazioneServiceWrapper(_vert.UrlFasc, _protocolloLogs, _protocolloSerializer, _vert.CodiceAmministrazione, _vert.Aoo);

                bool isCopia = _protocolloService.IsCopia(this.DatiProtocollo.Istanza.FKIDPROTOCOLLO);

                if (isCopia)
                {
                    throw new Exception("NON E' POSSIBILE MODIFICARE IL FASCICOLO DI UNA COPIA");
                }

                if (String.IsNullOrEmpty(fascicolo.NumeroFascicolo))
                {
                    var retVal = FascicolaProtocollo(fascicolo);
                    _protocolloLogs.InfoFormat("CAMBIO FASCICOLO AVVENUTO CORRETTAMENTE, NUMERO: {0}, CLASSIFICA: {1}, ANNO: {2}", fascicolo.NumeroFascicolo, fascicolo.Classifica, fascicolo.AnnoFascicolo);
                    return retVal;
                }
                else
                {
                    var fascicoloOut = FascicoloEsistente(fascicolo);
                    if (fascicoloOut.Id != 0)
                    {
                        var retVal = FascicolaProtocollo(fascicolo);
                        _protocolloLogs.InfoFormat("CAMBIO FASCICOLO AVVENUTO CORRETTAMENTE, NUMERO: {0}, CLASSIFICA: {1}, ANNO: {2}", fascicolo.NumeroFascicolo, fascicolo.Classifica, fascicolo.AnnoFascicolo);
                        return retVal;
                    }
                    else
                    {
                        throw new ProtocolloException($"IL FASCICOLO {fascicolo.NumeroFascicolo} SELEZIONATO NON ESISTE!!");
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL CAMBIAMENTO DI UN FASCICOLO, {0}", ex.Message), ex);
            }
        }

        public override DatiEtichette StampaEtichette(string idProtocollo, DateTime? dataProtocollo, string numeroProtocollo, int numeroCopie, string stampante)
        {
            DatiEtichette datiEtichette = new DatiEtichette();

            try
            {
                //using (_proxyProtIride = new ProtocollazioneProxy(_vert.Url, ProxyAddress))
                //{
                _vert = new ParametriRegoleInfo(_protocolloLogs, new VerticalizzazioneProtocolloJIride(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                _protocolloService = new ProtocollazioneServiceWrapper(_vert.Url, _protocolloLogs, _protocolloSerializer, _vert.CodiceAmministrazione, _vert.Aoo);

                DataProtocollo = dataProtocollo;

                var docOut = LeggiProtocolloDocumento(idProtocollo, AnnoProtocollo, numeroProtocollo);

                if (docOut.IdDocumento != 0)
                    datiEtichette.IdEtichetta = docOut.IdDocumento.ToString().PadLeft(8, '0');
                else
                    throw new Exception(String.Format("ERRORE GENERATO DAL WEB METHOD LEGGIPROTOCOLLO. MESSAGGIO: {0}, ERRORE: {1}", docOut.Messaggio, docOut.Errore));
                //}
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA STAMPA DI UN'ETICHETTA, {0}", ex.Message), ex);
            }

            return datiEtichette;
        }

        public override DatiProtocolloRes CreaCopie()
        {
            _vert = new ParametriRegoleInfo(_protocolloLogs, new VerticalizzazioneProtocolloJIride(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

            if (_vert.DisabilitaCreaCopie)
            {
                this._protocolloLogs.Debug("Funzionalità CreaCopie disabilitata tramite parametro DISABILITA_CREACOPIE della verticalizzazione PROTOCOLLO_IRIDE");
                return null;
            }

            _protocolloService = new ProtocollazioneServiceWrapper(_vert.Url, _protocolloLogs, _protocolloSerializer, _vert.CodiceAmministrazione, _vert.Aoo);

            var info = new CreaCopieInfo(this._protocolloLogs, this._protocolloSerializer, this._vert, this.DatiProtocollo.Istanza.FKIDPROTOCOLLO,
                                            this.DatiProtocollo.Istanza.NUMEROPROTOCOLLO, this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString(), this.Operatore,
                                            this.Ruolo, this.Uo, this.ProxyAddress, this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value, CreaCopieInfo.TipoAssegnazione.CONOSCENZA);

            var documentoOut = _protocolloService.GeneraCopia(info);
            var adapter = new ProtocollazioneOutAdapter(base.ModificaNumero, base.AggiungiAnno, base._protocolloLogs, this._vert.MessaggioProtoOk);
            return adapter.Adatta(documentoOut);
        }

        public override DatiProtocolloRes MettiAllaFirma(DatiProtocolloIn proto)
        {
            _vert = new ParametriRegoleInfo(_protocolloLogs, new VerticalizzazioneProtocolloJIride(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            _protocolloService = new ProtocollazioneServiceWrapper(_vert.Url, _protocolloLogs, _protocolloSerializer, _vert.CodiceAmministrazione, _vert.Aoo);
            var protoIn = CreaProtocolloIn(proto);

            _protocolloSerializer.Serialize(ProtocolloLogsConstants.InserisciDocumentoRequestFileName, protoIn);
            _protocolloLogs.InfoFormat("Chiamata a web method InserisciDocumento da metti alla firma, request file: {0}", ProtocolloLogsConstants.InserisciDocumentoRequestFileName);
            var docOut = _protocolloService.InserisciDocumento(protoIn);

            _protocolloSerializer.Serialize(ProtocolloLogsConstants.InserisciDocumentoResponseFileName, docOut);

            if (docOut.IdDocumento != 0 && String.IsNullOrEmpty(docOut.Errore))
            {
                _protocolloLogs.Info("MESSA ALLA FIRMA AVVENUTA CON SUCCESSO");

                var adapterResponse = new ProtocollazioneOutAdapter(base.ModificaNumero, base.AggiungiAnno, base._protocolloLogs, this._vert.MessaggioProtoOk);
                var response = adapterResponse.Adatta(docOut);

                return response;
            }
            else
            {
                throw new Exception(String.Format("METODO INSERISCIDOCUMENTO. MESSAGGIO DI ERRORE: {0}. ERRORE: {1}", docOut.Messaggio, docOut.Errore));
            }
        }

        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn proto)
        {
            _vert = new ParametriRegoleInfo(_protocolloLogs, new VerticalizzazioneProtocolloJIride(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            _protocolloService = new ProtocollazioneServiceWrapper(_vert.Url, _protocolloLogs, _protocolloSerializer, _vert.CodiceAmministrazione, _vert.Aoo);
            var protoIn = CreaProtocolloIn(proto);

            var protoOut = _protocolloService.InserisciProtocollo(protoIn);

            _protocolloLogs.InfoFormat("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO, FLUSSO: {0}", protoIn.Origine);

            if ((protoIn.Origine == "P" || protoIn.Origine == "I") && (this.Anagrafiche != null && this.Anagrafiche.Count > 1))
            {
                _protocolloService.CreaCopiePerAmministrazioniInterne(proto, protoOut, this.Operatore);
            }
            _protocolloLogs.InfoFormat("URL PEC PRESENTE: {0}, FLUSSO: {1}", !String.IsNullOrEmpty(_vert.UrlPec), protoIn.Origine);
            if (!String.IsNullOrEmpty(_vert.UrlPec) && (protoIn.Origine == "P"))
            {
                InviaPec(proto, protoOut, protoIn.Ruolo);
            }

            var adapterResponse = new ProtocollazioneOutAdapter(base.ModificaNumero, base.AggiungiAnno, base._protocolloLogs, this._vert.MessaggioProtoOk);
            var response = adapterResponse.Adatta(protoOut);

            return response;
        }

        private void InviaPec(DatiProtocolloIn protoIn, ProtocolloOutXml protoOut, string ruolo)
        {
            _protocolloLogs.Info("INIZIO FUNZIONALITA' DI INVIO PEC");
            try
            {
                if (String.IsNullOrEmpty(protoIn.Oggetto))
                    _protocolloLogs.WarnFormat("INVIO PEC non eseguito a causa dell'assenza dell'oggetto della mail, controllare l'oggetto di default in configurazione, protocollo numero: {0}, data: {1}", protoOut.NumeroProtocollo.ToString(), protoOut.DataProtocollo.Value.ToString("dd/MM/yyyy"));
                else if (String.IsNullOrEmpty(_vert.MittenteMailPec))
                    _protocolloLogs.WarnFormat("INVIO PEC non eseguito a causa dell'assenza del mittente della mail, controllare il parametro MITTENTE_MAIL_PEC della verticalizzazione PROTOCOLLO_IRIDE, protocollo numero: {0}, data: {1}", protoOut.NumeroProtocollo.ToString(), protoOut.DataProtocollo.Value.ToString("dd/MM/yyyy"));
                else if (String.IsNullOrEmpty(_vert.UrlPec))
                    _protocolloLogs.WarnFormat("INVIO PEC non eseguito a causa dell'assenza dell'url (end point) del servizio di invio mail PEC di Iride, controllare il parametro URL_PEC della verticalizzazione PROTOCOLLO_IRIDE, protocollo numero: {0}, data: {1}", protoOut.NumeroProtocollo.ToString(), protoOut.DataProtocollo.Value.ToString("dd/MM/yyyy"));
                else
                {
                    Ruolo = ruolo;
                    IEnumerable<string> seriali;

                    if (protoOut.Allegati == null)
                    {
                        var docOut = _protocolloService.LeggiDocumento(protoOut.IdDocumento.ToString(), this.Operatore.ToUpper(), this.Ruolo);
                        seriali = docOut.Allegati.Select(x => x.Serial.ToString());
                    }
                    else
                        seriali = protoOut.Allegati.Select(x => x.Serial.ToString());


                    if (_vert.WarningPec)
                    {
                        var anagraficheNoPEC = this.Anagrafiche.Where(x => String.IsNullOrEmpty(x.Pec)).Select(x => x.NomeCognome);
                        if (anagraficheNoPEC.Count() > 0)
                        {
                            _protocolloLogs.WarnFormat("I SEGUENTI DESTINATARI NON PRESENTANO UN INDIRIZZO PEC, AI QUALI NON E' STATA QUINDI INVIATA: {0}", String.Join(", ", anagraficheNoPEC));
                        }

                        var anagraficheNoMezzo = this.Anagrafiche.Where(x => x.MezzoInvio != _vert.MezzoPec).Select(x => x.NomeCognome);

                        if (anagraficheNoMezzo.Count() > 0)
                        {
                            _protocolloLogs.WarnFormat("I SEGUENTI DESTINATARI NON PRESENTANO IL MEZZO DI INVIO PEC, AI QUALI NON E' STATA QUINDI INVIATA: {0}", String.Join(", ", anagraficheNoMezzo));
                        }
                    }

                    var listaPec = this.Anagrafiche.Where(y => !String.IsNullOrEmpty(y.Pec) && y.MezzoInvio == _vert.MezzoPec).
                                                                        GroupBy(x => x.Pec.ToUpperInvariant()).
                                                                        Select(x => x.Key).ToArray();



                    _protocolloLogs.InfoFormat("NUMERO DESTINATARI PER INVIO PEC: {0}", listaPec.Length);

                    if (listaPec.Length > 0)
                    {
                        var adapter = new PecAdapter(_protocolloSerializer);

                        var oggetto = protoIn.Oggetto;
                        if (_vert.UsaRifProtocolloOggettoPec)
                        {
                            oggetto = $"Pr. Num.: {protoOut.NumeroProtocollo}, Data Pr.: {protoOut.DataProtocollo.Value.ToString("dd/MM/yyyy")} - {protoIn.Oggetto.Replace("\r\n", " ")}";
                        }

                        var requestXml = adapter.Adatta(listaPec, seriali, protoOut.IdDocumento.ToString(), Regex.Replace(oggetto, @"\r\n?|\n", "-"), protoIn.Corpo, _vert.MittenteMailPec, Operatore, ruolo, _vert.UsaInvioInteroperabilePec);
                        var pecServiceWrapper = new PECServiceWrapper(_vert.UrlPec, _protocolloLogs, _protocolloSerializer);

                        if (_vert.WarningPec)
                        {
                            _protocolloLogs.WarnFormat("PEC INVIATA CORRETTAMENTE AI SEGUENTI DESTINATARI: {0}", String.Join(", ", listaPec));
                        }

                        pecServiceWrapper.InviaPEC(requestXml, _vert.CodiceAmministrazione, _vert.Aoo);
                    }
                }
            }
            catch (Exception ex)
            {
                _protocolloLogs.WarnFormat(String.Format("PROBLEMA DURANTE LA FUNZIONALITA' DI INVIO PEC, ERRORE: {0}", ex.Message));
            }
            finally
            {
            }
        }

        private ProtocolloInXml CreaProtocolloIn(Data.DatiProtocolloIn datiProto)
        {
            var protoIn = new ProtocolloInXml();

            protoIn.Data = DateTime.Now.Date.ToString("dd/MM/yyyy");
            protoIn.Classifica = datiProto.Classifica;
            protoIn.TipoDocumento = datiProto.TipoDocumento;
            protoIn.Oggetto = datiProto.Oggetto;
            protoIn.Origine = datiProto.Flusso;
            protoIn.AggiornaAnagrafiche = _vert.AggiornaAnagrafiche;

            if (!String.IsNullOrEmpty(datiProto.TipoSmistamento))
                protoIn.OggettoBilingue = datiProto.TipoSmistamento;

            //Gestione Fascicolazione (come comportarsi con la protocollazione delle autorizzazioni???)
            if (!String.IsNullOrEmpty(_vert.NumeroPratica) && !GestisciFascicolazione)
            {
                //Fascicolazione con fascicolo faldone precedentemente creato
                protoIn.NumeroPratica = _vert.NumeroPratica;
                protoIn.AnnoPratica = DateTime.Now.Year.ToString();
            }

            protoIn.Utente = Operatore.ToUpper();

            _protocolloLogs.Debug("#### SetAllegati ####");
            //Setto gli allegati
            SetAllegati(protoIn, datiProto);

            _protocolloLogs.Debug("#### SetMittenti ####");
            //Setto i mittenti
            SetMittenti(protoIn, datiProto);

            _protocolloLogs.Debug("#### SetDestinatari ####");
            //Setto i destinatari
            SetDestinatari(protoIn, datiProto);

            return protoIn;
        }

        private void SetAllegati(ProtocolloInXml protoIn, DatiProtocolloIn datiProtoIn)
        {
            try
            {
                protoIn.Allegati = new AllegatoInXml[datiProtoIn.Allegati.Count];

                int iIndex = 0;
                foreach (ProtocolloAllegati protoAllegati in datiProtoIn.Allegati)
                {
                    //non dovrebbe essere necessario
                    if (protoAllegati.OGGETTO == null)
                        throw new ProtocolloException("Errore generato dal web method SetAllegati del protocollo Iride. Metodo: SetAllegati, modulo: ProtocolloIride. C'è un allegato con il campo OGGETTO null.\r\n");

                    protoIn.Allegati[iIndex] = new AllegatoInXml();

                    protoIn.Allegati[iIndex].ContentType = protoAllegati.MimeType;
                    protoIn.Allegati[iIndex].Image = protoAllegati.OGGETTO;

                    if (!String.IsNullOrEmpty(protoAllegati.Extension))
                        protoIn.Allegati[iIndex].TipoFile = protoAllegati.Extension.Substring(1);

                    protoIn.Allegati[iIndex].Commento = protoAllegati.Descrizione;
                    protoIn.Allegati[iIndex].NomeAllegato = protoAllegati.NOMEFILE;

                    iIndex++;
                }
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
                        protoIn.MittenteInterno = String.IsNullOrEmpty(_vert.UoSmistamento) ? datiProto.Mittenti.Amministrazione[0].PROT_UO : _vert.UoSmistamento;
                        protoIn.Ruolo = String.IsNullOrEmpty(_vert.UoSmistamento) ? datiProto.Mittenti.Amministrazione[0].PROT_RUOLO : _vert.UoSmistamento;
                        //Se il flusso è in Partenza occorre settare anche InCaricoA e Ruolo con PROT_UO
                        if (protoIn.Origine == "P")
                        {
                            protoIn.MittenteInterno = datiProto.Mittenti.Amministrazione[0].PROT_UO;

                            if (!_vert.DisabilitaCaricoPartenza)
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
                        foreach (Amministrazioni amministrazione in datiProto.Mittenti.Amministrazione)
                        {
                            //if (mittentiDestinatariList.Where(x => x.CodiceFiscale == amministrazione.PARTITAIVA).Count() > 0)
                            //    throw new Exception("SONO PRESENTI PIU' MITTENTI CON LO STESSO CODICE FISCALE / PARTITA IVA");

                            var mittentiDestinatari = new MittenteDestinatarioInXml();
                            if (mittentiDestinatariList.Count >= 100)
                            {
                                _protocolloLogs.WarnFormat("Sono state conteggiati {0} mittenti mentre il limite massimo è di 99", mittentiDestinatariList.Count.ToString());
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
                            if (!String.IsNullOrEmpty(DatiProtocollo.CodiceIstanza))
                            {
                                var istMgr = new IstanzeMgr(this.DatiProtocollo.Db);
                                mittentiDestinatari.DataRicevimento = istMgr.GetById(DatiProtocollo.IdComune, Convert.ToInt32(DatiProtocollo.CodiceIstanza)).DATA.Value.ToString("dd/MM/yyyy");
                            }
                            if (!String.IsNullOrEmpty(DatiProtocollo.CodiceMovimento))
                            {
                                var movMgr = new MovimentiMgr(this.DatiProtocollo.Db);
                                mittentiDestinatari.DataRicevimento = movMgr.GetById(DatiProtocollo.IdComune, Convert.ToInt32(DatiProtocollo.CodiceMovimento)).DATA.Value.ToString("dd/MM/yyyy");
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
                                _protocolloLogs.WarnFormat("Sono state conteggiati {0} mittenti mentre il limite massimo è di 99", mittentiDestinatariList.Count.ToString());
                                return;
                            }

                            var mittentiDestinatari = new MittenteDestinatarioInXml();

                            string codiceFiscalePartitaIva = "";

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
                            if (this.DatiProtocollo.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA)
                                mittentiDestinatari.DataRicevimento = this.DatiProtocollo.Istanza.DATA.Value.ToString("dd/MM/yyyy");

                            if (this.DatiProtocollo.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO)
                                mittentiDestinatari.DataRicevimento = this.DatiProtocollo.Movimento.DATA.Value.ToString("dd/MM/yyyy");

                            if (!String.IsNullOrEmpty(protoAnagrafe.Pec))
                            {
                                if (protoAnagrafe.Pec.Equals(protoAnagrafe.PecAnagrafica) || String.IsNullOrEmpty(protoAnagrafe.PecAnagrafica))
                                {
                                    var rec = new RecapitiEmailAdapter(protoAnagrafe.Pec, this._vert.TipoRecapitoEmail);
                                    mittentiDestinatari.Recapiti = rec.Recapiti;
                                }
                                else
                                {
                                    var pecs = new string[] { protoAnagrafe.Pec, protoAnagrafe.PecAnagrafica };

                                    var rec = new RecapitiEmailAdapter(pecs, _vert.TipoRecapitoEmail);
                                    mittentiDestinatari.Recapiti = rec.Recapiti;
                                }
                            }

                            mittentiDestinatariList.Add(mittentiDestinatari);
                        }
                        else
                            if (TipoInserimento == ProtocolloEnum.Source.PROT_IST_MOV_AUT_BO)
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


        private void SetDestinatari(ProtocolloInXml protoIn, Data.DatiProtocolloIn datiProto)
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

                    foreach (Amministrazioni amministrazione in amministrazioniEsterne)
                    {
                        if (mittentiDestinatariList.Count >= 100)
                        {
                            _protocolloLogs.WarnFormat("Sono stati conteggiati {0} destinatari mentre il limite massimo è di 99", mittentiDestinatariList.Count.ToString());
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
                            if (!String.IsNullOrEmpty(_vert.UrlPec))
                            {
                                string warn = "";
                                if (String.IsNullOrEmpty(amministrazione.PARTITAIVA))
                                    warn = String.Format("LA PEC E LA PARTITA IVA DELL'AMMINISTRAZIONE {0}, (CODICE {1}), NON SONO VALORIZZATI, E' PROBABILE QUINDI CHE LA PEC NON SIA STATA INVIATA DAL SERVIZIO POSTE WEB DI IRIDE", amministrazione.AMMINISTRAZIONE, amministrazione.CODICEAMMINISTRAZIONE);
                                else
                                    warn = String.Format("LA PEC DELL'AMMINISTRAZIONE {0}, (CODICE {1}), NON E' VALORIZZATA, CONTROLLARE SU IRIDE, SE L'ANAGRAFICA CON PARTITA IVA {2} ABBIA IL RECAPITO EMAIL VALORIZZATO.", amministrazione.AMMINISTRAZIONE, amministrazione.CODICEAMMINISTRAZIONE, amministrazione.PARTITAIVA);

                                _protocolloLogs.Warn(warn);
                            }
                        }

                        mittentiDestinatariList.Add(mittentiDestinatari);
                        //}
                    }
                }

                foreach (ProtocolloAnagrafe anagrafe in datiProto.Destinatari.Anagrafe)
                {
                    if (mittentiDestinatariList.Where(x => x.CodiceFiscale == anagrafe.CODICEFISCALE || x.CodiceFiscale == anagrafe.PARTITAIVA).Count() > 0)
                        throw new Exception("SONO PRESENTI PIU' DESTINATARI CON LO STESSO CODICE FISCALE / PARTITA IVA");

                    if (mittentiDestinatariList.Count >= 100)
                    {
                        _protocolloLogs.WarnFormat("Sono state conteggiati {0} destinatari mentre il limite massimo è di 99", mittentiDestinatariList.Count.ToString());
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

                    string codiceFiscalePartitaIva = "";

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

                    if (!String.IsNullOrEmpty(anagrafe.Pec))
                    {
                        if (anagrafe.Pec.Equals(anagrafe.PecAnagrafica) || String.IsNullOrEmpty(anagrafe.PecAnagrafica))
                        {
                            var rec = new RecapitiEmailAdapter(anagrafe.Pec, this._vert.TipoRecapitoEmail);
                            mittentiDestinatari.Recapiti = rec.Recapiti;
                        }
                        else
                        {
                            var pecs = new string[] { anagrafe.Pec, anagrafe.PecAnagrafica };

                            var rec = new RecapitiEmailAdapter(pecs, this._vert.TipoRecapitoEmail);
                            mittentiDestinatari.Recapiti = rec.Recapiti;
                        }
                    }
                    else
                    {
                        if (!String.IsNullOrEmpty(_vert.UrlPec))
                        {
                            string warn = "";
                            if (String.IsNullOrEmpty(codiceFiscalePartitaIva))
                                warn = String.Format("LA PEC E IL CODICE FISCALE/PARTITA IVA DELL'ANAGRAFICA {0}, (CODICE {1}), NON SONO VALORIZZATI, E' PROBABILE QUINDI CHE LA PEC NON SIA STATA INVIATA DAL SERVIZIO POSTE WEB DI IRIDE", anagrafe.NOMINATIVO, anagrafe.CODICEANAGRAFE);
                            else
                                warn = String.Format("LA PEC DELL'ANAGRAFICA {0}, (CODICE {1}), NON E' VALORIZZATA, CONTROLLARE SU IRIDE, SE L'ANAGRAFICA CON CODICE FISCALE {2} ABBIA IL RECAPITO EMAIL VALORIZZATO.", anagrafe.NOMINATIVO, anagrafe.CODICEANAGRAFE, codiceFiscalePartitaIva);

                            _protocolloLogs.Warn(warn);
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

        #region Metodi per la fascicolazione di un protocollo

        private DatiProtocolloFascicolato Fascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var datiProtFasc = new DatiProtocolloFascicolato();
            var docOut = LeggiProtocolloDocumento(idProtocollo, annoProtocollo, numeroProtocollo);
            if (docOut.IdDocumento != 0)
            {
                //Verifico se il protocollo è stato fascicolato
                if (docOut.IdPratica == 0 && (String.IsNullOrEmpty(docOut.NumeroPratica) || docOut.AnnoPratica == 0))
                {
                    datiProtFasc.Fascicolato = EnumFascicolato.no;
                }
                else
                {
                    bool isCopia = _protocolloService.IsCopia(idProtocollo);
                    string numeroPratica = docOut.NumeroPratica;
                    string annoPratica = docOut.AnnoPratica.ToString();
                    int idPratica = docOut.IdPratica;
                    string classifica = docOut.Classifica;

                    if (isCopia)
                    {
                        var altriFascicoli = docOut.AltriFascicoli;

                        if (altriFascicoli != null && altriFascicoli.Length > 0)
                        {
                            numeroPratica = altriFascicoli[0].NumeroAltroFascicolo;
                            annoPratica = altriFascicoli[0].AnnoAltroFascicolo.ToString();
                            idPratica = 0;

                            var datiAltroFascicolo = altriFascicoli[0].AnnoNumeroAltroFascicolo.Split('/');
                            if (datiAltroFascicolo != null && datiAltroFascicolo.Length > 1)
                            {
                                classifica = datiAltroFascicolo[1];
                            }
                        }
                    }

                    var fascicoloOut = _fascicolazione.LeggiFascicolo(numeroPratica, annoPratica, classifica, idPratica, Operatore, Ruolo);

                    datiProtFasc.AnnoFascicolo = fascicoloOut.Anno.ToString();
                    datiProtFasc.Classifica = String.IsNullOrEmpty(fascicoloOut.Classifica) ? docOut.Classifica : fascicoloOut.Classifica;
                    datiProtFasc.DataFascicolo = fascicoloOut.Data.Value.ToString("dd/MM/yyyy");
                    datiProtFasc.NumeroFascicolo = fascicoloOut.NumeroSenzaClassifica;
                    datiProtFasc.Oggetto = fascicoloOut.Oggetto;
                    datiProtFasc.Fascicolato = EnumFascicolato.si;
                }
            }
            else
            {
                datiProtFasc.Fascicolato = EnumFascicolato.warning;
                datiProtFasc.NoteFascicolo = "Errore: " + docOut.Messaggio + "." + docOut.Errore;
            }


            return datiProtFasc;
        }

        #endregion

        #region Metodi per la lettura di un protocollo
        private DocumentoOutXml LeggiProtocolloDocumento(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            _protocolloLogs.InfoFormat("Inizio metodo LeggiProtocolloDocumento, idprotocollo {0}, annoprotocollo {1}, numeroprotocollo {2}", idProtocollo, annoProtocollo, numeroProtocollo);
            if (!string.IsNullOrEmpty(idProtocollo) || (!string.IsNullOrEmpty(numeroProtocollo) && !string.IsNullOrEmpty(annoProtocollo)))
            {
                var docOut = new DocumentoOutXml();
                GC.Collect();
                if ((String.IsNullOrEmpty(idProtocollo) || _vert.UsaNumAnnoLeggi) && !String.IsNullOrEmpty(numeroProtocollo))
                {
                    string[] sNumProtSplit = numeroProtocollo.Split(new Char[] { '/' });
                    string sNumProtocollo = sNumProtSplit[0];

                    docOut = _protocolloService.LeggiProtocollo(Convert.ToInt16(annoProtocollo), Convert.ToInt32(sNumProtocollo), Operatore.ToUpper(), Ruolo);
                }
                else
                {
                    docOut = _protocolloService.LeggiDocumento(idProtocollo, Operatore.ToUpper(), Ruolo);
                }

                return docOut;
            }
            else
                throw new Exception("NON È POSSIBILE RILEGGERE IL PROTOCOLLO/DOCUMENTO");
        }

        public override void CheckProtocolloLetto(string annoProtocollo, string numeroProtocollo, string idProtocollo, DatiProtocolloLetto pDatiProtocolloLetto)
        {
            if (String.IsNullOrEmpty(idProtocollo))
            {
                base.CheckProtocolloLetto(annoProtocollo, numeroProtocollo, idProtocollo, pDatiProtocolloLetto);
            }
        }

        public override AllOut LeggiAllegato()
        {
            var allegato = GetAllegato();
            return allegato;
        }

         public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            bool isCopia = false;
            if (!String.IsNullOrEmpty(idProtocollo))
            {
                var arrIdProtocollo = idProtocollo.Split('-');
                isCopia = arrIdProtocollo.Length > 1 && arrIdProtocollo[1] == "COPIA";
                _protocolloLogs.InfoFormat("idProtocollo: {0}, idProtocollo dopo formattazione: {1}", idProtocollo, arrIdProtocollo[0]);
            }

            _vert = new ParametriRegoleInfo(_protocolloLogs, new VerticalizzazioneProtocolloJIride(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            _protocolloService = new ProtocollazioneServiceWrapper(_vert.Url, _protocolloLogs, _protocolloSerializer, _vert.CodiceAmministrazione, _vert.Aoo);
            NumProtocollo = numeroProtocollo;
            AnnoProtocollo = annoProtocollo;
            var documentoOut = LeggiProtocolloDocumento(idProtocollo, annoProtocollo, numeroProtocollo);
            var adapterOut = new LeggiProtocolloResponseAdapter();

            return adapterOut.Adatta(documentoOut, this.DatiProtocollo.Db, isCopia);
        }

        #endregion


    }
}

