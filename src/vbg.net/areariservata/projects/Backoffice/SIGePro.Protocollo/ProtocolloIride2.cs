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
using Init.SIGePro.Protocollo.Iride2.Services;
using System.Web;
using System.Linq;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Iride2.Proxies;
using Init.SIGePro.Protocollo.Iride2;
using Init.SIGePro.Protocollo.Iride2.Builders;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Iride2.Fascicolazione;
using Init.SIGePro.Protocollo.Iride.Configuration;
using Init.SIGePro.Protocollo.Iride2.PosteWeb;
using Init.SIGePro.Protocollo.Iride2.CreaCopie;

namespace Init.SIGePro.Protocollo
{
    /// <summary>
    /// Descrizione di riepilogo per PROTOCOLLO_IRIDE.
    /// </summary>
    public class PROTOCOLLO_IRIDE2 : ProtocolloBase
    {
        #region Costruttori
        public PROTOCOLLO_IRIDE2()
        {
            //pProxyProtIride = new ProxyProtIride();
            //pProxyFascIride = new Init.SIGePro.Protocollo.ProxyFascIride.ProxyFascIride();
        }
        #endregion

        public static class Constants
        {
            public const string PERSONA_FISICA_IRIDE = "FI";
            public const string PERSONA_GIURIDICA_IRIDE = "GI";

        }


        #region Membri privati
        private ProxyProtIride _proxyProtIride = null;
        private FascicolazioneProxy _proxyFascIride = null;

        private IProtocolloIrideService _protocolloIrideService;
        private IFascicolazione _fascicolazione;

        VerticalizzazioniConfiguration _vert;

        #endregion

        #region Metodi pubblici e privati della classe

        #region Metodi per la fascicolazione di un protocollo

        public override DatiProtocolloFascicolato IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            _vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloIride(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

            using (_proxyProtIride = new ProxyProtIride(_vert.Url, ProxyAddress))
            {
                _protocolloIrideService = ProtocolloIrideFactory.Create(_vert.CodiceAmministrazione, _proxyProtIride, _protocolloLogs);

                _fascicolazione = FascicolazioneFactory.Create(_vert.Versione, _vert.UrlFasc, ProxyAddress, _protocolloLogs);

                using (_proxyFascIride = _fascicolazione.Proxy)
                    return Fascicolato(idProtocollo, annoProtocollo, numeroProtocollo);
            }
        }

        private FascicoloOut FascicoloEsistente(Fascicolo fascicolo)
        {
            FascicoloOut fascicoloOut;
            _protocolloLogs.DebugFormat("Verifica se il fascicolo è esistente: numero fascicolo da controllare: {0}, anno fascicolo: {1}", fascicolo.NumeroFascicolo, fascicolo.AnnoFascicolo.ToString());
            ////_log.Debug("fascicolo.NumeroFascicolo: " + fascicolo.NumeroFascicolo + "| Classe: ProtocolloIride, Metodo: FascicoloEsistente(fascicolo)");

            if (String.IsNullOrEmpty(fascicolo.NumeroFascicolo) || (fascicolo.AnnoFascicolo == 0))
                fascicoloOut = new FascicoloOut();
            else
                fascicoloOut = _fascicolazione.LeggiFascicolo(fascicolo.NumeroFascicolo, fascicolo.AnnoFascicolo.ToString(), fascicolo.Classifica, 0, Operatore.ToUpper(), Ruolo, _vert.CodiceAmministrazione, _vert.Aoo);

            return fascicoloOut;

        }

        private FascicoloOut FascicoloNuovo(Fascicolo fascicolo)
        {
            FascicoloIn pFascicoloIn = CreaFascicoloIn(fascicolo);

            _protocolloSerializer.Serialize(ProtocolloLogsConstants.CreaFascicoloRequestFileName, pFascicoloIn);
            _protocolloLogs.DebugFormat("Chiamata a CreaFascicolo");
            FascicoloOut pFascicoloOut = _proxyFascIride.CreaFascicolo(pFascicoloIn, _vert.CodiceAmministrazione, string.Empty);
            _protocolloLogs.DebugFormat("CreaFascicolo eseguito");
            _protocolloSerializer.Serialize(ProtocolloLogsConstants.CreaFascicoloResponseFileName, pFascicoloOut);

            if (pFascicoloOut.Id != 0)
                return pFascicoloOut;
            else
                throw new Exception("ERRORE GENERATO DAL WEB METHOD CREAFASCICOLO. MESSAGGIO: " + pFascicoloOut.Messaggio + ", ERRORE: " + pFascicoloOut.Errore);
        }

        public override DatiFascicolo Fascicola(Fascicolo fascicolo)
        {
            if (fascicolo == null)
                return null;

            _vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloIride(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            DatiFascicolo pFascicolo = null;
            try
            {
                using (_proxyProtIride = new ProxyProtIride(_vert.Url, ProxyAddress))
                {
                    _protocolloIrideService = ProtocolloIrideFactory.Create(_vert.CodiceAmministrazione, _proxyProtIride, _protocolloLogs);

                    _fascicolazione = FascicolazioneFactory.Create(_vert.Versione, _vert.UrlFasc, ProxyAddress, _protocolloLogs);
                    using (_proxyFascIride = _fascicolazione.Proxy)
                        pFascicolo = FascicolaProtocollo(fascicolo);
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA FASCICOLAZIONE, {0}", ex.Message), ex);
            }

            return pFascicolo;
        }

        private DatiFascicolo FascicolaProtocollo(Fascicolo fascicolo)
        {
            FascicoloOut fascicoloOut = FascicoloEsistente(fascicolo);
            int iFascicoloId;
            int iDocumentoId;
            DocumentoOut docOut;
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
                _protocolloLogs.DebugFormat("Chiamata a FascicolaDocumento, IdFascicolo: {0}, IdDocumento: {1}, AggiornaClassifica: {2}, Operatore: {3}, Ruolo: {4}, CodiceAmministrazione: {5}, CodiceAoo: {6}", iFascicoloId, iDocumentoId, _vert.AggiornaClassifica, Operatore.ToUpper(), Ruolo, _vert.CodiceAmministrazione, String.Empty);
                Init.SIGePro.Protocollo.Iride2.Fascicolazione.EsitoOperazione esito = _proxyFascIride.FascicolaDocumento(iFascicoloId, iDocumentoId, _vert.AggiornaClassifica, Operatore.ToUpper(), Ruolo, _vert.CodiceAmministrazione, string.Empty);

                if (!esito.Esito)
                    throw new Exception("Errore generato dal web method FascicolaDocumento durante la fascicolazione di una istanza.(id fascicolo: " + iFascicoloId + " id documento: " + iDocumentoId + ". Messaggio di errore: " + esito.Messaggio + ". " + esito.Errore + "\r\n");

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
                    var fascOut = _fascicolazione.LeggiFascicolo("", "", "", iFascicoloId, Operatore.ToUpper(), Ruolo, _vert.CodiceAmministrazione, _vert.Aoo);

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
                _protocolloLogs.DebugFormat("Chiamata a FascicolaDocumento, IdFascicolo: {0}, IdDocumento: {1}, AggiornaClassifica: {2}, Operatore: {3}, Ruolo: {4}, CodiceAmministrazione: {5}, CodiceAoo: {6}", iFascicoloId, iDocumentoId, _vert.AggiornaClassifica, Operatore.ToUpper(), Ruolo, _vert.CodiceAmministrazione, String.Empty);
                Init.SIGePro.Protocollo.Iride2.Fascicolazione.EsitoOperazione esito = _proxyFascIride.FascicolaDocumento(iFascicoloId, iDocumentoId, _vert.AggiornaClassifica, Operatore.ToUpper(), Ruolo, _vert.CodiceAmministrazione, string.Empty);

                if (!esito.Esito)
                    throw new ProtocolloException("Errore generato dal web method FascicolaDocumento durante la fascicolazione di una istanza.(id fascicolo: " + iFascicoloId + " id documento: " + iDocumentoId + ". Messaggio di errore: " + esito.Messaggio + ". " + esito.Errore + "\r\n");



                //Fascicolo i moviemnti della pratica se protocollati
                MovimentiMgr pMovimentiMgr = new MovimentiMgr(this.DatiProtocollo.Db);
                Movimenti _Movimento = new Movimenti();
                _Movimento.IDCOMUNE = DatiProtocollo.IdComune;
                _Movimento.CODICEISTANZA = DatiProtocollo.CodiceIstanza;
                List<Movimenti> list = pMovimentiMgr.GetList(_Movimento);
                foreach (Movimenti elem in list)
                {
                    if (!string.IsNullOrEmpty(elem.FKIDPROTOCOLLO) || (!string.IsNullOrEmpty(elem.NUMEROPROTOCOLLO) && elem.DATAPROTOCOLLO.HasValue))
                    {
                        //if (string.IsNullOrEmpty(elem.FKIDPROTOCOLLO))
                        //{
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
                        //}
                        //else
                        //    iDocumentoId = Convert.ToInt32(elem.FKIDPROTOCOLLO);

                        if (iDocumentoIdIstanza != iDocumentoId)
                        {
                            _protocolloLogs.DebugFormat("Chiamata a FascicolaDocumento, IdFascicolo: {0}, IdDocumento: {1}, AggiornaClassifica: {2}, Operatore: {3}, Ruolo: {4}, CodiceAmministrazione: {5}, CodiceAoo: {6}", iFascicoloId, iDocumentoId, _vert.AggiornaClassifica, Operatore.ToUpper(), Ruolo, _vert.CodiceAmministrazione, String.Empty);
                            esito = _proxyFascIride.FascicolaDocumento(iFascicoloId, iDocumentoId, _vert.AggiornaClassifica, Operatore.ToUpper(), Ruolo, _vert.CodiceAmministrazione, string.Empty);
                            if (!esito.Esito)
                                continue;
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
                    var fascOut = _fascicolazione.LeggiFascicolo("", "", "", iFascicoloId, Operatore.ToUpper(), Ruolo, _vert.CodiceAmministrazione, _vert.Aoo);

                    pDatiFascicolo.AnnoFascicolo = fascOut.Anno.ToString();
                    pDatiFascicolo.DataFascicolo = fascOut.Data.Value.ToString("dd/MM/yyyy");
                    pDatiFascicolo.NumeroFascicolo = fascOut.Numero;
                }
            }

            //Verifico se si intende fascicolare un movimento
            if (this.DatiProtocollo.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO)
            {
                Init.SIGePro.Protocollo.Iride2.Fascicolazione.EsitoOperazione esito;

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
                    esito = _proxyFascIride.FascicolaDocumento(iFascicoloId, iDocumentoId, _vert.AggiornaClassifica, Operatore.ToUpper(), Ruolo, _vert.CodiceAmministrazione, "");
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

        private FascicoloIn CreaFascicoloIn(Fascicolo fascicolo)
        {
            FascicoloIn fascicoloIn = new FascicoloIn();
            fascicoloIn.Anno = fascicolo.AnnoFascicolo.ToString();
            fascicoloIn.Data = this.GetDataFascicolo(fascicolo.DataFascicolo);
            fascicoloIn.Numero = fascicolo.NumeroFascicolo;
            fascicoloIn.Oggetto = fascicolo.Oggetto;
            fascicoloIn.Classifica = fascicolo.Classifica;
            fascicoloIn.Utente = Operatore.ToUpper();
            fascicoloIn.Ruolo = Ruolo;

            return fascicoloIn;
        }

        private string GetDataFascicolo(string data)
        {
            DateTime dtFasc;
            var isValidDate = DateTime.TryParse(data, out dtFasc);

            if (!isValidDate && String.IsNullOrEmpty(_vert.FormatoDataFasc))
                return data;

            return dtFasc.ToString(_vert.FormatoDataFasc);
        }

        public override DatiFascicolo CambiaFascicolo(Fascicolo fascicolo)
        {
            _vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloIride(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            DatiFascicolo pFascicolo = null;
            try
            {
                using (_proxyProtIride = new ProxyProtIride(_vert.Url, ProxyAddress))
                {
                    _protocolloIrideService = ProtocolloIrideFactory.Create(_vert.CodiceAmministrazione, _proxyProtIride, _protocolloLogs);
                    _fascicolazione = FascicolazioneFactory.Create(_vert.Versione, _vert.UrlFasc, ProxyAddress, _protocolloLogs);
                    using (_proxyFascIride = _fascicolazione.Proxy)
                    {
                        FascicoloOut fascicoloOut = FascicoloEsistente(fascicolo);

                        if (fascicoloOut.Id != 0)
                            pFascicolo = FascicolaProtocollo(fascicolo);
                        else
                            throw new ProtocolloException("Il fascicolo selezionato non esiste o non è stato passato!!");
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL CAMBIAMENTO DI UN FASCICOLO, {0}", ex.Message), ex);
            }

            return pFascicolo;
        }

        #endregion

        #region Metodi per la stampa di un'etichetta

        public override DatiEtichette StampaEtichette(string idProtocollo, DateTime? dataProtocollo, string numeroProtocollo, int numeroCopie, string stampante)
        {
            _vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloIride(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            DatiEtichette datiEtichette = new DatiEtichette();

            try
            {
                using (_proxyProtIride = new ProxyProtIride(_vert.Url, ProxyAddress))
                {
                    _protocolloIrideService = ProtocolloIrideFactory.Create(_vert.CodiceAmministrazione, _proxyProtIride, _protocolloLogs);

                    DataProtocollo = dataProtocollo;

                    var docOut = LeggiProtocolloDocumento(idProtocollo, AnnoProtocollo, numeroProtocollo);

                    if (docOut.IdDocumento != 0)
                        datiEtichette.IdEtichetta = docOut.IdDocumento.ToString().PadLeft(8, '0');
                    else
                        throw new Exception(String.Format("ERRORE GENERATO DAL WEB METHOD LEGGIPROTOCOLLO. MESSAGGIO: {0}, ERRORE: {1}", docOut.Messaggio, docOut.Errore));
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA STAMPA DI UN'ETICHETTA, {0}", ex.Message), ex);
            }

            return datiEtichette;
        }

        #endregion

        #region Metodi per creare la copia di un protocollo

        public override DatiProtocolloRes CreaCopie()
        {
            _vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloIride(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

            if (_vert.DisabilitaCreaCopie)
            {
                this._protocolloLogs.Debug("Funzionalità CreaCopie disabilitata tramite parametro DISABILITA_CREACOPIE della verticalizzazione PROTOCOLLO_IRIDE");
                return null;
            }

            using (_proxyProtIride = new ProxyProtIride(_vert.Url, ProxyAddress))
            {
                _protocolloIrideService = ProtocolloIrideFactory.Create(_vert.CodiceAmministrazione, _proxyProtIride, _protocolloLogs);

                var info = new CreaCopieInfo(this._protocolloLogs, this._protocolloSerializer, this._protocolloIrideService, this._vert, this.DatiProtocollo.Istanza.FKIDPROTOCOLLO,
                                             this.DatiProtocollo.Istanza.NUMEROPROTOCOLLO, this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString(), this.Operatore,
                                             this.Ruolo, this.Uo, this.ProxyAddress, this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value);

                var factory = CreaCopieFactory.Create(info);
                var documentoOut = factory.GeneraCopia();

                var retVal = CreaDatiProtocollo(documentoOut);

                return retVal;
            }
        }

        #endregion

        #region Metodi di messa alla firma
        public override DatiProtocolloRes MettiAllaFirma(DatiProtocolloIn proto)
        {
            _vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloIride(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            DatiProtocolloRes documentoRes = null;
            try
            {
                using (_proxyProtIride = new ProxyProtIride(_vert.Url, ProxyAddress))
                {
                    var protoIn = CreaProtocolloIn(proto);

                    _protocolloSerializer.Serialize(ProtocolloLogsConstants.InserisciDocumentoRequestFileName, protoIn);
                    _protocolloLogs.InfoFormat("Chiamata a web method InserisciDocumento da metti alla firma, request file: {0}", ProtocolloLogsConstants.InserisciDocumentoRequestFileName);
                    var docOut = _proxyProtIride.InserisciDocumento(protoIn);

                    _protocolloSerializer.Serialize(ProtocolloLogsConstants.InserisciDocumentoResponseFileName, docOut);

                    if (docOut.IdDocumento != 0 && String.IsNullOrEmpty(docOut.Errore))
                    {
                        _protocolloLogs.Info("MESSA ALLA FIRMA AVVENUTA CON SUCCESSO");
                        documentoRes = CreaDatiProtocollo(docOut);
                    }
                    else
                        throw new Exception(String.Format("METODO INSERISCIDOCUMENTO. MESSAGGIO DI ERRORE: {0}. ERRORE: {1}", docOut.Messaggio, docOut.Errore));
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA MESSA ALLA FIRMA, {0}", ex.Message), ex);
            }

            return documentoRes;
        }


        #endregion

        #region Metodi di protocollazione

        protected virtual ProtocolloOut InserisciProtocollo(ProtocolloIn protocolloIn)
        {
            return _protocolloIrideService.InserisciProtocollo(protocolloIn);
        }

        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn proto)
        {
            _vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloIride(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            DatiProtocolloRes protoRes = null;
            try
            {
                using (_proxyProtIride = new ProxyProtIride(_vert.Url, ProxyAddress))
                {
                    _protocolloIrideService = ProtocolloIrideFactory.Create(_vert.CodiceAmministrazione, _proxyProtIride, _protocolloLogs);
                    _protocolloLogs.Debug("#### Inizio Richiesta di Protocollazione ####");
                    _protocolloLogs.Debug("#### CreaProtocolloIn ####");
                    var protoIn = CreaProtocolloIn(proto);

                    _protocolloSerializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, protoIn);

                    _protocolloLogs.InfoFormat("Chiamata al web method InserisciProtocollo, file request: {0}", ProtocolloLogsConstants.ProtocollazioneRequestFileName);
                    var protoOut = InserisciProtocollo(protoIn);

                    _protocolloSerializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, protoOut);

                    if (protoOut.IdDocumento != 0)
                    {
                        _protocolloLogs.InfoFormat("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO, FLUSSO: {0}", protoIn.Origine);

                        if ((protoIn.Origine == "P" || protoIn.Origine == "I") && (this.Anagrafiche != null && this.Anagrafiche.Count > 1))
                        {
                            CreaCopiePerAmministrazioniInterne(proto, protoOut);
                        }
                        _protocolloLogs.InfoFormat("URL PEC PRESENTE: {0}, FLUSSO: {1}", !String.IsNullOrEmpty(_vert.UrlPec), protoIn.Origine);
                        if (!String.IsNullOrEmpty(_vert.UrlPec) && (protoIn.Origine == "P"))
                        {
                            InviaPec(proto, protoOut, protoIn.Ruolo);
                        }

                        protoRes = CreaDatiProtocollo(protoOut);
                    }
                    else
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEBSERVICE. METODO InserisciProtocollo. MESSAGGIO: {0}, ERRORE: {1}", protoOut.Messaggio, protoOut.Errore));

                    return protoRes;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE, {0}", ex.Message), ex);
            }
        }

        /// <summary>
        /// Si occupa di preparare i dati riguardanti la Pec.
        /// </summary>
        /// <param name="protoIn"></param>
        /// <param name="protoOut"></param>
        /// <param name="ruolo"></param>
        /// <returns></returns>
        private void InviaPec(DatiProtocolloIn protoIn, ProtocolloOut protoOut, string ruolo)
        {
            _protocolloLogs.Info("INIZIO FUNZIONALITA' DI INVIO PEC");
            try
            {
                if (String.IsNullOrEmpty(protoIn.Oggetto))
                    _protocolloLogs.WarnFormat("INVIO PEC non eseguito a causa dell'assenza dell'oggetto della mail, controllare l'oggetto di default in configurazione, protocollo numero: {0}, data: {1}", protoOut.NumeroProtocollo.ToString(), protoOut.DataProtocollo.ToString("dd/MM/yyyy"));
                else if (String.IsNullOrEmpty(_vert.MittenteMailPec))
                    _protocolloLogs.WarnFormat("INVIO PEC non eseguito a causa dell'assenza del mittente della mail, controllare il parametro MITTENTE_MAIL_PEC della verticalizzazione PROTOCOLLO_IRIDE, protocollo numero: {0}, data: {1}", protoOut.NumeroProtocollo.ToString(), protoOut.DataProtocollo.ToString("dd/MM/yyyy"));
                else if (String.IsNullOrEmpty(_vert.UrlPec))
                    _protocolloLogs.WarnFormat("INVIO PEC non eseguito a causa dell'assenza dell'url (end point) del servizio di invio mail PEC di Iride, controllare il parametro URL_PEC della verticalizzazione PROTOCOLLO_IRIDE, protocollo numero: {0}, data: {1}", protoOut.NumeroProtocollo.ToString(), protoOut.DataProtocollo.ToString("dd/MM/yyyy"));
                else
                {

                    Ruolo = ruolo;
                    IEnumerable<string> seriali;

                    if (protoOut.Allegati == null)
                    {
                        var docOut = LeggiDocumento(protoOut.IdDocumento);
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
                        var pec = PecFactory.Create(_vert.Versione, seriali, listaPec, _vert.Aoo, _vert.UsaInvioInteroperabilePec, _protocolloLogs, _protocolloSerializer);

                        var oggetto = protoIn.Oggetto;
                        if (_vert.UsaRifProtocolloOggettoPec)
                        {
                            oggetto = $"Pr. Num.: {protoOut.NumeroProtocollo}, Data Pr.: {protoOut.DataProtocollo.ToString("dd/MM/yyyy")} - {protoIn.Oggetto}";
                        }

                        pec.Invia(_vert.UrlPec, ProxyAddress, protoOut.IdDocumento.ToString(), Regex.Replace(oggetto, @"\r\n?|\n", "-"), protoIn.Corpo, _vert.MittenteMailPec, Operatore, ruolo, _vert.CodiceAmministrazione);

                        if (_vert.WarningPec)
                        {
                            _protocolloLogs.WarnFormat("PEC INVIATA CORRETTAMENTE AI SEGUENTI DESTINATARI: {0}", String.Join(", ", listaPec));
                        }
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

        private void CreaCopiePerAmministrazioniInterne(DatiProtocolloIn protocolloInput, ProtocolloOut protocolloOutputOrigine)
        {
            try
            {
                _protocolloLogs.DebugFormat("Inizio funzionalità crea copie del protocollo numero: {0}, data: {1}, id: {2}", protocolloOutputOrigine.NumeroProtocollo.ToString(), protocolloOutputOrigine.DataProtocollo.ToString("dd/MM/yyyy"), protocolloOutputOrigine.IdDocumento.ToString());

                var uoDestinatari = new List<UODestinataria>();

                var ammList = protocolloInput.Destinatari.Amministrazione.Where(amm => !String.IsNullOrEmpty(amm.PROT_UO) && !String.IsNullOrEmpty(amm.PROT_RUOLO)).ToList();
                if (ammList.Count > 0)
                {
                    if (protocolloInput.Destinatari.Anagrafe.Count == 0 && protocolloInput.Destinatari.Amministrazione.Where(x => String.IsNullOrEmpty(x.PROT_UO) && String.IsNullOrEmpty(x.PROT_RUOLO)).Count() == 0)
                        ammList = ammList.Skip(1).ToList();

                    ammList.ForEach(amm => uoDestinatari.Add(new UODestinataria { Carico = amm.PROT_UO, Data = DateTime.Now.ToString("dd/MM/yyyy"), TipoUO = "UO", NumeroCopie = "1" }));

                    var creaCopieService = new CreaCopieService(_vert.Url, ProxyAddress, _protocolloLogs, _protocolloSerializer);

                    var creaCopieOut = creaCopieService.CreaCopie(protocolloInput.Mittenti.Amministrazione[0].PROT_RUOLO,
                                                    protocolloOutputOrigine.IdDocumento,
                                                    protocolloOutputOrigine.AnnoProtocollo.ToString(),
                                                    protocolloOutputOrigine.NumeroProtocollo.ToString(),
                                                    Operatore,
                                                    _vert.CodiceAmministrazione, uoDestinatari.ToArray());

                    if (creaCopieOut.CopieCreate == null || creaCopieOut.CopieCreate.Length == 0)
                        _protocolloLogs.ErrorFormat("E' stato restituito un errore dal web service durante la funzionalità CreaCopie eseguita dopo la protocollazione, Id Protocollo Originale: {0}, numero/anno {1}/{2}, Errore: {3}",
                                                    protocolloOutputOrigine.IdDocumento,
                                                    protocolloOutputOrigine.NumeroProtocollo.ToString(),
                                                    protocolloOutputOrigine.AnnoProtocollo.ToString(),
                                                    creaCopieOut.Errore);
                    else
                        _protocolloLogs.InfoFormat("E' stata creata una copia con la funzionalità CreaCopie eseguita dopo la protocollazione, Id Protocollo Originale:{0}, Id Protocollo Copia: {1}, Numero/Anno Protocollo: {2}/{3}",
                                                    creaCopieOut.IdDocumentoSorgente.ToString(),
                                                    protocolloOutputOrigine.IdDocumento.ToString(),
                                                    protocolloOutputOrigine.NumeroProtocollo.ToString(),
                                                    protocolloOutputOrigine.AnnoProtocollo.ToString()
                                                    );

                    _protocolloLogs.DebugFormat("Fine funzionalità crea copie del protocollo numero: {0}, data: {1}, id: {2}", protocolloOutputOrigine.NumeroProtocollo.ToString(), protocolloOutputOrigine.DataProtocollo.ToString("dd/MM/yyyy"), protocolloOutputOrigine.IdDocumento.ToString());
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE AVVENUTO DURANTE LA CREAZIONE DELLE COPIE PER LE AMMINISTRAZIONI INTERNE, COPIA DOPO PROTOCOLLAZIONE, {0}", ex.Message), ex);
            }
        }

        protected DatiProtocolloRes CreaDatiProtocollo(ProtocolloOut protoOut)
        {
            try
            {
                var protoRes = new DatiProtocolloRes();

                protoRes.IdProtocollo = protoOut.IdDocumento.ToString();
                protoRes.AnnoProtocollo = protoOut.AnnoProtocollo.ToString();
                protoRes.DataProtocollo = protoOut.DataProtocollo.ToString("dd/MM/yyyy");

                protoRes.NumeroProtocollo = protoOut.NumeroProtocollo.ToString();

                if (ModificaNumero)
                    protoRes.NumeroProtocollo = protoRes.NumeroProtocollo.TrimStart(new char[] { '0' });

                if (AggiungiAnno)
                    protoRes.NumeroProtocollo += "/" + protoOut.AnnoProtocollo.ToString();

                if (!String.IsNullOrEmpty(protoOut.Errore))
                {
                    _protocolloLogs.Warn(protoOut.Errore);
                }

                protoRes.Warning = _protocolloLogs.Warnings.WarningMessage;

                if (!String.IsNullOrEmpty(_vert.MessaggioProtoOk) && !String.IsNullOrEmpty(_protocolloLogs.Warnings.WarningMessage))
                {
                    protoRes.Warning = _protocolloLogs.Warnings.WarningMessage.Replace(_vert.MessaggioProtoOk, "");
                }

                _protocolloLogs.InfoFormat("Dati protocollo restituiti, numero: {0}, anno: {1}, data: {2}", protoRes.NumeroProtocollo, protoRes.AnnoProtocollo, protoRes.DataProtocollo);

                return protoRes;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DOPO LA RESTITUZIONE DEI DATI, IL PROTOCOLLO POTREBBE ESSERE STATO CREATO, {0}", ex.Message), ex);
            }
        }

        private DatiProtocolloRes CreaDatiProtocollo(DocumentoOut protoOut)
        {
            try
            {
                var protoRes = new DatiProtocolloRes();

                protoRes.IdProtocollo = protoOut.IdDocumento.ToString();
                protoRes.AnnoProtocollo = protoOut.AnnoProtocollo.ToString();
                protoRes.DataProtocollo = protoOut.DataProtocollo.ToString("dd/MM/yyyy");

                protoRes.NumeroProtocollo = protoOut.NumeroProtocollo.ToString();

                if (ModificaNumero)
                    protoRes.NumeroProtocollo = protoRes.NumeroProtocollo.TrimStart(new char[] { '0' });

                if (AggiungiAnno)
                    protoRes.NumeroProtocollo += "/" + protoOut.AnnoProtocollo.ToString();

                if (!String.IsNullOrEmpty(protoOut.Errore))
                {
                    _protocolloLogs.Warn(protoOut.Errore);
                }

                protoRes.Warning = _protocolloLogs.Warnings.WarningMessage;

                if (!String.IsNullOrEmpty(_vert.MessaggioProtoOk) && !String.IsNullOrEmpty(_protocolloLogs.Warnings.WarningMessage))
                    protoRes.Warning = _protocolloLogs.Warnings.WarningMessage.Replace(_vert.MessaggioProtoOk, "");

                _protocolloLogs.InfoFormat("Dati protocollo restituiti, numero: {0}, anno: {1}, data: {2}", protoRes.NumeroProtocollo, protoRes.AnnoProtocollo, protoRes.DataProtocollo);

                return protoRes;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DOPO LA RESTITUZIONE DEI DATI, IL PROTOCOLLO POTREBBE ESSERE STATO CREATO, {0}", ex.Message), ex);
            }
        }

        protected ProtocolloIn CreaProtocolloIn(Data.DatiProtocolloIn datiProto)
        {
            var protoIn = new ProtocolloIn();

            //Setto i parametri della classe ProtocolloIn
            protoIn.Data = DateTime.Now.Date.ToString("dd/MM/yyyy");
            protoIn.Classifica = datiProto.Classifica;
            protoIn.TipoDocumento = datiProto.TipoDocumento;
            protoIn.Oggetto = datiProto.Oggetto;
            protoIn.Origine = datiProto.Flusso;
            protoIn.AggiornaAnagrafiche = _vert.AggiornaAnagrafiche;

            if (!String.IsNullOrEmpty(datiProto.TipoSmistamento))
                protoIn.OggettoBilingue = datiProto.TipoSmistamento;

            //Gestione Fascicolazione (come comportarsi con la protocollazione delle autorizzazioni???)
            if (!string.IsNullOrEmpty(_vert.NumeroPratica) && !GestisciFascicolazione)
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

        private void SetAllegati(ProtocolloIn protoIn, DatiProtocolloIn datiProtoIn)
        {
            try
            {
                protoIn.Allegati = new AllegatoIn[datiProtoIn.Allegati.Count];

                int iIndex = 0;
                foreach (ProtocolloAllegati protoAllegati in datiProtoIn.Allegati)
                {
                    //non dovrebbe essere necessario
                    if (protoAllegati.OGGETTO == null)
                        throw new ProtocolloException("Errore generato dal web method SetAllegati del protocollo Iride. Metodo: SetAllegati, modulo: ProtocolloIride. C'è un allegato con il campo OGGETTO null.\r\n");

                    protoIn.Allegati[iIndex] = new AllegatoIn();

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

        private void SetMittenti(ProtocolloIn protoIn, Data.DatiProtocolloIn datiProto)
        {
            try
            {
                protoIn.MittenteInterno = "";
                var mittentiDestinatariList = new List<MittenteDestinatarioIn>();

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

                            var mittentiDestinatari = new MittenteDestinatarioIn();
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

                            //listRecapiti.Add( new RecapitoIn{ TipoRecapito = "EMAIL", ValoreRecapito = amministrazione.EMAIL });
                            //mittentiDestinatari.Recapiti = new RecapitoIn[]

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
                                var rec = new IrideRecapitiEmailBuilder(amministrazione.PEC, _vert.SwapEmail, _vert.TipoRecapitoMail);
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

                            var mittentiDestinatari = new MittenteDestinatarioIn();

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
                            mittentiDestinatari.CodiceComuneNascita = !String.IsNullOrEmpty(protoAnagrafe.CodiceStatoEsteroNasc) ? (String.IsNullOrEmpty(_vert.View) ? String.Empty : GetDecodeCodiceIstatStato(protoAnagrafe.CodiceStatoEsteroNasc)) : GetDecodeCodiceIstatStato(protoAnagrafe.CodiceIstatComNasc);
                            mittentiDestinatari.CodiceComuneResidenza = !String.IsNullOrEmpty(protoAnagrafe.CodiceStatoEsteroRes) ? (String.IsNullOrEmpty(_vert.View) ? String.Empty : GetDecodeCodiceIstatStato(protoAnagrafe.CodiceStatoEsteroRes)) : GetDecodeCodiceIstatStato(protoAnagrafe.CodiceIstatComRes);

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
                                    var rec = new IrideRecapitiEmailBuilder(protoAnagrafe.Pec, _vert.SwapEmail, _vert.TipoRecapitoMail);
                                    mittentiDestinatari.Recapiti = rec.Recapiti;
                                }
                                else
                                {
                                    var pecs = new string[] { protoAnagrafe.Pec, protoAnagrafe.PecAnagrafica };

                                    var rec = new IrideRecapitiEmailBuilder(pecs, _vert.SwapEmail, _vert.TipoRecapitoMail);
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


        private void SetDestinatari(ProtocolloIn protoIn, Data.DatiProtocolloIn datiProto)
        {
            try
            {
                var mittentiDestinatariList = new List<MittenteDestinatarioIn>();

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
                            var mittentiDestinatari = new MittenteDestinatarioIn();

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
                        //if (mittentiDestinatariList.Where(x => x.CodiceFiscale == amministrazione.PARTITAIVA).Count() > 0)
                        //    throw new Exception("SONO PRESENTI PIU' DESTINATARI CON LO STESSO CODICE FISCALE / PARTITA IVA");

                        if (mittentiDestinatariList.Count >= 100)
                        {
                            _protocolloLogs.WarnFormat("Sono stati conteggiati {0} destinatari mentre il limite massimo è di 99", mittentiDestinatariList.Count.ToString());
                            return;
                        }

                        /*if (!String.IsNullOrEmpty(amministrazione.PROT_UO) || !String.IsNullOrEmpty(amministrazione.PROT_UO))
                            _protocolloLogs.InfoFormat("NEI DESTINATARI E' PRESENTE UN'AMMINISTRAZIONE INTERNA SI TRATTA QUINDI DI PROTOCOLLAZIONE MISTA, CODICE AMMINISTRAZIONE: {0}, DESCRIZIONE: {1}, UO: {2}, RUOLO: {3}, QUEST'AMMINISTRAZIONE NON SARA' USATA DIRETTAMENTE SULLA PROTOCOLLAZIONE, MA SULLA SUCCESSIVA CREAZIONE DELLE COPIE.", amministrazione.CODICEAMMINISTRAZIONE, amministrazione.AMMINISTRAZIONE, amministrazione.PROT_UO, amministrazione.PROT_RUOLO);
                        else
                        {*/
                        var mittentiDestinatari = new MittenteDestinatarioIn();

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
                            var rec = new IrideRecapitiEmailBuilder(amministrazione.PEC, _vert.SwapEmail, _vert.TipoRecapitoMail);
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

                    var mittentiDestinatari = new MittenteDestinatarioIn();

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

                    /*if (!(String.IsNullOrEmpty(anagrafe.NOMINATIVO) && String.IsNullOrEmpty(anagrafe.NOME)))
                        mittentiDestinatari.CognomeNome = ((string)(anagrafe.NOMINATIVO + " " + anagrafe.NOME)).TrimEnd();*/

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
                    mittentiDestinatari.CodiceComuneNascita = !String.IsNullOrEmpty(anagrafe.CodiceStatoEsteroNasc) ? (String.IsNullOrEmpty(_vert.View) ? String.Empty : GetDecodeCodiceIstatStato(anagrafe.CodiceStatoEsteroNasc)) : GetDecodeCodiceIstatStato(anagrafe.CodiceIstatComNasc);
                    mittentiDestinatari.CodiceComuneResidenza = !String.IsNullOrEmpty(anagrafe.CodiceStatoEsteroRes) ? (String.IsNullOrEmpty(_vert.View) ? String.Empty : GetDecodeCodiceIstatStato(anagrafe.CodiceStatoEsteroRes)) : GetDecodeCodiceIstatStato(anagrafe.CodiceIstatComRes);

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
                            var rec = new IrideRecapitiEmailBuilder(anagrafe.Pec, _vert.SwapEmail, _vert.TipoRecapitoMail);
                            mittentiDestinatari.Recapiti = rec.Recapiti;
                        }
                        else
                        {
                            var pecs = new string[] { anagrafe.Pec, anagrafe.PecAnagrafica };

                            var rec = new IrideRecapitiEmailBuilder(pecs, _vert.SwapEmail, _vert.TipoRecapitoMail);
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

        private string GetDecodeCodiceIstatStato(string codice)
        {
            string retVal = string.Empty;

            if (string.IsNullOrEmpty(_vert.View))
                return codice;
            else
            {
                if (string.IsNullOrEmpty(codice))
                    return codice;
                else
                {
                    DataBase db = new DataBase(_vert.ConnectionString, (ProviderType)Enum.Parse(typeof(ProviderType), _vert.Provider, true));
                    db.Connection.Open();

                    try
                    {
                        int count = 0;
                        string query = "select COD_IRIDE from " + (string.IsNullOrEmpty(_vert.Owner) ? string.Empty : _vert.Owner + ".") + _vert.View + " where COD_ISTAT = " + codice;
                        using (IDbCommand cmd = db.CreateCommand(query))
                        {
                            using (IDataReader reader = cmd.ExecuteReader())
                            {
                                if (reader != null)
                                {
                                    while (reader.Read())
                                    {
                                        retVal = reader["COD_IRIDE"].ToString();
                                        count++;
                                    }
                                }
                            }
                        }

                        switch (count)
                        {
                            case 0:
                                throw new Exception("Il codice " + codice + " non è stato trovato.\r\n");
                            case 1:
                                return retVal;
                            default:
                                throw new Exception("Il codice " + codice + " è stato trovato " + count + " volte.\r\n");
                        }
                    }
                    catch (Exception ex)
                    {
                        throw ex;
                    }
                    finally
                    {
                        db.Connection.Close();
                    }
                }
            }
        }
        #endregion

        #region Metodi per la fascicolazione di un protocollo

        private DatiProtocolloFascicolato Fascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            DatiProtocolloFascicolato datiProtFasc = new DatiProtocolloFascicolato();
            DocumentoOut pDocumentoOut = LeggiProtocolloDocumento(idProtocollo, annoProtocollo, numeroProtocollo);
            //_protocolloSerializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, pDocumentoOut);

            if (pDocumentoOut.IdDocumento != 0)
            {
                //Verifico se il protocollo è stato fascicolato
                if (pDocumentoOut.IdPratica == 0 && (String.IsNullOrEmpty(pDocumentoOut.NumeroPratica) || pDocumentoOut.AnnoPratica == 0))
                    datiProtFasc.Fascicolato = EnumFascicolato.no;
                else
                {
                    //FascicoloOut fascicoloOut;
                    var fascicoloOut = _fascicolazione.LeggiFascicolo(pDocumentoOut.NumeroPratica, pDocumentoOut.AnnoPratica.ToString(), pDocumentoOut.Classifica, pDocumentoOut.IdPratica, Operatore, Ruolo, _vert.CodiceAmministrazione, _vert.Aoo);

                    datiProtFasc.AnnoFascicolo = fascicoloOut.Anno.ToString();
                    datiProtFasc.Classifica = String.IsNullOrEmpty(fascicoloOut.Classifica) ? pDocumentoOut.Classifica : fascicoloOut.Classifica;
                    datiProtFasc.DataFascicolo = fascicoloOut.Data.Value.ToString("dd/MM/yyyy");
                    datiProtFasc.NumeroFascicolo = fascicoloOut.Numero;
                    datiProtFasc.Oggetto = fascicoloOut.Oggetto;
                    datiProtFasc.Fascicolato = EnumFascicolato.si;
                }
            }
            else
            {
                datiProtFasc.Fascicolato = EnumFascicolato.warning;
                datiProtFasc.NoteFascicolo = "Errore: " + pDocumentoOut.Messaggio + "." + pDocumentoOut.Errore;
            }


            return datiProtFasc;
        }

        #endregion

        #region Metodi per la lettura di un protocollo
        private DocumentoOut LeggiProtocolloDocumento(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            _protocolloLogs.InfoFormat("Inizio metodo LeggiProtocolloDocumento, idprotocollo {0}, annoprotocollo {1}, numeroprotocollo {2}", idProtocollo, annoProtocollo, numeroProtocollo);
            if (!string.IsNullOrEmpty(idProtocollo) || (!string.IsNullOrEmpty(numeroProtocollo) && !string.IsNullOrEmpty(annoProtocollo)))
            {
                DocumentoOut docOut = new DocumentoOut();
                GC.Collect();
                if ((String.IsNullOrEmpty(idProtocollo) || _vert.UsaNumAnnoLeggi) && !String.IsNullOrEmpty(numeroProtocollo))
                {
                    string[] sNumProtSplit = numeroProtocollo.Split(new Char[] { '/' });
                    string sNumProtocollo = sNumProtSplit[0];
                    _protocolloLogs.Info("Chiamata a LeggiProtocollo");
                    docOut = LeggiProtocollo(Convert.ToInt16(annoProtocollo), Convert.ToInt32(sNumProtocollo));
                    _protocolloLogs.Info("Fine Chiamata a LeggiProtocollo");
                }
                else
                {
                    _protocolloLogs.Debug("Chiamata a LeggiDocumento");
                    docOut = LeggiDocumento(Convert.ToInt32(idProtocollo));
                    _protocolloLogs.Debug("Fine chiamata a LeggiDocumento");
                }

                return docOut;
            }
            else
                throw new Exception("NON È POSSIBILE RILEGGERE IL PROTOCOLLO/DOCUMENTO");
        }

        protected virtual DocumentoOut LeggiProtocollo(short annoProtocollo, int numeroProtocollo)
        {
            _protocolloLogs.InfoFormat("Chiamata a web method LeggiProtocollo, numero protocollo: {0}, anno protocollo: {1}, operatore: {2}, ruolo: {3}", numeroProtocollo, annoProtocollo, Operatore.ToUpper(), Ruolo);
            var response = _protocolloIrideService.LeggiProtocollo(annoProtocollo, numeroProtocollo, Operatore.ToUpper(), Ruolo);
            _protocolloLogs.InfoFormat("Fine lettura del protocollo, numero: {0}, anno: {1}, operatore: {2}, ruolo: {3}", numeroProtocollo, annoProtocollo, Operatore.ToUpper(), Ruolo);

            if (_protocolloLogs.IsDebugEnabled)
                _protocolloSerializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, response);

            return response;
        }

        protected virtual DocumentoOut LeggiDocumento(int idProtocollo)
        {
            _protocolloLogs.InfoFormat("Chiamata a LeggiDocumento, id protocollo: {0}, operatore: {1}, ruolo: {2}", idProtocollo, Operatore.ToUpper(), Ruolo);
            var response = _protocolloIrideService.LeggiDocumento(idProtocollo, Operatore.ToUpper(), Ruolo);
            _protocolloLogs.InfoFormat("Fine lettura del documento, con id: {0}, operatore: {1}, ruolo: {2}", idProtocollo, Operatore.ToUpper(), Ruolo);

            if (_protocolloLogs.IsDebugEnabled)
                _protocolloSerializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, response);

            return response;
        }

        public override void CheckProtocolloLetto(string annoProtocollo, string numeroProtocollo, string idProtocollo, DatiProtocolloLetto pDatiProtocolloLetto)
        {
            if (String.IsNullOrEmpty(idProtocollo))
                base.CheckProtocolloLetto(annoProtocollo, numeroProtocollo, idProtocollo, pDatiProtocolloLetto);

        }

        public override AllOut LeggiAllegato()
        {
            var allegato = GetAllegato();
            //_protocolloSerializer.Serialize(ProtocolloLogsConstants.AllegatoResponseFileName, allegato, ProtocolloValidation.TipiValidazione.XSD, "", true);
            return allegato;
        }

        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            _vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloIride(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            DatiProtocolloLetto protocolloLetto = null;
            DocumentoOut documentoOut = null;
            try
            {
                _protocolloLogs.DebugFormat("URL: {0}, PROXY ADDRESS: {1}", _vert.Url, ProxyAddress);
                using (_proxyProtIride = new ProxyProtIride(_vert.Url, ProxyAddress))
                {
                    _protocolloIrideService = ProtocolloIrideFactory.Create(_vert.CodiceAmministrazione, _proxyProtIride, _protocolloLogs);
                    _protocolloLogs.Debug("#### Inizio Richiesta di LeggiProtocollo ####");
                    NumProtocollo = numeroProtocollo;
                    AnnoProtocollo = annoProtocollo;

                    _protocolloLogs.Debug("#### Chiamata a LeggiProtocollo ####");
                    documentoOut = LeggiProtocolloDocumento(idProtocollo, annoProtocollo, numeroProtocollo);
                    _protocolloLogs.Debug("##### Ricevuta risposta dal Protocollo IRIDE ####");

                    _protocolloLogs.DebugFormat("Inizio funzionalità di creazione dei dati del protocollo dopo la risposta del web service, Id Protocollo: {0}, Anno Protocollo: {1}, Numero Protocollo: {2}", idProtocollo, annoProtocollo, numeroProtocollo);
                    protocolloLetto = CreaDatiProtocolloLetto(documentoOut);
                    _protocolloLogs.DebugFormat("Fine funzionalità di creazione dei dati del protocollo dopo la risposta del web service, Id Protocollo: {0}, Anno Protocollo: {1}, Numero Protocollo: {2}", idProtocollo, annoProtocollo, numeroProtocollo);
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA LETTURA DEL PROTOCOLLO, {0}", ex.Message), ex);
            }

            return protocolloLetto;
        }

        protected DatiProtocolloLetto CreaDatiProtocolloLetto(DocumentoOut response)
        {
            try
            {
                var protoLetto = new DatiProtocolloLetto();

                if (response.IdDocumento != 0)
                {
                    protoLetto.IdProtocollo = response.IdDocumento.ToString();
                    protoLetto.AnnoProtocollo = response.AnnoProtocollo.ToString();
                    protoLetto.NumeroProtocollo = response.NumeroProtocollo.ToString();
                    protoLetto.DataProtocollo = response.DataProtocollo.ToString("dd/MM/yyyy");

                    if (!String.IsNullOrEmpty(response.Oggetto))
                        protoLetto.Oggetto = response.Oggetto;
                    if (!String.IsNullOrEmpty(response.Origine))
                        protoLetto.Origine = response.Origine;
                    if (!String.IsNullOrEmpty(response.Classifica))
                        protoLetto.Classifica = response.Classifica;
                    if (!String.IsNullOrEmpty(response.Classifica_Descrizione))
                        protoLetto.Classifica_Descrizione = response.Classifica_Descrizione;
                    if (!String.IsNullOrEmpty(response.TipoDocumento))
                        protoLetto.TipoDocumento = response.TipoDocumento;
                    if (!String.IsNullOrEmpty(response.TipoDocumento_Descrizione))
                        protoLetto.TipoDocumento_Descrizione = response.TipoDocumento_Descrizione;

                    if (!String.IsNullOrEmpty(response.MittenteInterno) && response.Origine != ProtocolloConstants.COD_ARRIVO)
                    {
                        protoLetto.MittentiDestinatari = new MittDestOut[] { new MittDestOut { IdSoggetto = response.MittenteInterno, CognomeNome = response.MittenteInterno_Descrizione } };
                    }

                    if (!String.IsNullOrEmpty(response.InCaricoA))
                        protoLetto.InCaricoA = response.InCaricoA;

                    if (!String.IsNullOrEmpty(response.InCaricoA_Descrizione))
                        protoLetto.InCaricoA_Descrizione = response.InCaricoA_Descrizione;

                    if (!String.IsNullOrEmpty(response.DocAllegati))
                        protoLetto.DocAllegati = response.DocAllegati;

                    if (!String.IsNullOrEmpty(response.NumeroPratica))
                        protoLetto.NumeroPratica = response.NumeroPratica;
                    if (!String.IsNullOrEmpty(response.AnnoNumeroPratica))
                        protoLetto.AnnoNumeroPratica = response.AnnoNumeroPratica;
                    protoLetto.DataInserimento = response.DataInserimento.ToString("dd/MM/yyyy");

                    if (protoLetto.Origine == "I")
                    {
                        protoLetto.MittentiDestinatari = new MittDestOut[]
                        {
                            new MittDestOut
                            {
                                IdSoggetto = response.MittenteInterno,
                                CognomeNome = response.MittenteInterno_Descrizione
                            }
                        };
                    }

                    //Sezione Mittenti/Destinatari
                    if (response.MittentiDestinatari != null)
                    {
                        protoLetto.MittentiDestinatari = new MittDestOut[response.MittentiDestinatari.Length];

                        int iIndex = 0;
                        foreach (MittenteDestinatarioOut pMittDestOut in response.MittentiDestinatari)
                        {
                            protoLetto.MittentiDestinatari[iIndex] = new MittDestOut();
                            protoLetto.MittentiDestinatari[iIndex].IdSoggetto = pMittDestOut.IdSoggetto.ToString();
                            if (!String.IsNullOrEmpty(pMittDestOut.CognomeNome))
                            {
                                switch (protoLetto.Origine)
                                {
                                    case "A":
                                        protoLetto.MittentiDestinatari[iIndex].CognomeNome = pMittDestOut.CognomeNome;
                                        break;
                                    case "P":
                                        protoLetto.MittentiDestinatari[iIndex].CognomeNome = pMittDestOut.CognomeNome;
                                        break;
                                }
                            }

                            iIndex++;
                        }
                    }

                    if (response.Allegati != null && response.Allegati.Length > 0)
                    {
                        var allegatiDistinct = response.Allegati.GroupBy(x => x.IDBase).Select(x => x.Key);
                        protoLetto.Allegati = allegatiDistinct.Select(x =>
                        {
                            var a = response.Allegati.Where(z => z.IDBase == x).OrderByDescending(y => y.Versione).First();
                            var nomeFile = a.NomeAllegato;
                            if (String.IsNullOrEmpty(nomeFile))
                            {
                                nomeFile = a.Commento;
                                if (!String.IsNullOrEmpty(a.TipoFile))
                                {
                                    nomeFile = String.Format("{0}.{1}", Path.GetFileNameWithoutExtension(nomeFile), a.TipoFile);
                                    if (!String.IsNullOrEmpty(a.SottoEstensione))
                                        nomeFile = String.Format("{0}.{1}.{2}", Path.GetFileNameWithoutExtension(nomeFile), a.SottoEstensione, a.TipoFile);
                                }
                            }
                            return new AllOut
                            {
                                Commento = nomeFile,
                                IDBase = a.IDBase.ToString(),
                                Serial = nomeFile,
                                Versione = a.Versione.ToString(),
                                Image = a.Image,
                                TipoFile = String.IsNullOrEmpty(a.TipoFile) ? "" : a.TipoFile,
                                ContentType = String.IsNullOrEmpty(a.TipoFile) ? "" : new OggettiMgr(this.DatiProtocollo.Db).GetContentType(nomeFile)
                            };
                        }).ToArray();
                    }
                }
                else
                {
                    throw new Exception(String.Format("ID DOCUMENTO UGUALE A 0, MESSAGGIO: {0}, ERRORE: {1}", response.Messaggio, response.Errore));
                }

                return protoLetto;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DAL WEB SERVICE DOPO LA LETTURA DEL PROTOCOLLO, {0}", ex.Message), ex);
            }
        }
        #endregion

        #endregion
    }
}

