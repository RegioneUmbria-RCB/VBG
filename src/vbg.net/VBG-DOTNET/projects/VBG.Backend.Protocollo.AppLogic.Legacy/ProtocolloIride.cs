using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text.RegularExpressions;
using VBG.Backend.Protocollo.AppLogic.Legacy.Iride;
using VBG.Backend.Protocollo.AppLogic.Legacy.Iride.Configuration;
using VBG.Backend.Protocollo.AppLogic.Legacy.Iride.Fascicolazione;
using VBG.Backend.Protocollo.AppLogic.Legacy.Iride.PosteWeb;
using VBG.Backend.Protocollo.AppLogic.Legacy.Iride.Proxies;
using VBG.Backend.Protocollo.AppLogic.Legacy.Iride.Services;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Exceptions;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizzazioni.Legacy;


namespace VBG.Backend.Protocollo.AppLogic.Legacy
{
    /// <summary>
    /// Descrizione di riepilogo per PROTOCOLLO_IRIDE.
    /// </summary>
    public class PROTOCOLLO_IRIDE : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        #region Costruttori
        public PROTOCOLLO_IRIDE(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
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

        private VerticalizzazioniConfiguration _vert;

        #endregion

        #region Metodi pubblici e privati della classe

        #region Metodi per la fascicolazione di un protocollo

        public override DatiProtocolloFascicolatoResponseType IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            this._vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloIride>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            try
            {
                using (this._proxyProtIride = new ProxyProtIride(this._vert.Url, this.ProxyAddress))
                {
                    this._protocolloIrideService = ProtocolloIrideFactory.Create(this._vert.CodiceAmministrazione, this._proxyProtIride);

                    this._fascicolazione = FascicolazioneFactory.Create(this._vert.Versione, this._vert.UrlFasc, this.ProxyAddress, this._protocolloLogs);

                    using (this._proxyFascIride = this._fascicolazione.Proxy)
                        return this.Fascicolato(idProtocollo, annoProtocollo, numeroProtocollo);
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA VERIFICA DI FASCICOLAZIONE DI UN PROTOCOLLO, {0}", ex.Message), ex);
            }
        }

        private FascicoloOut FascicoloEsistente(Shared.Data.Fascicolo fascicolo)
        {
            FascicoloOut fascicoloOut;
            this._protocolloLogs.DebugFormat("Verifica se il fascicolo è esistente: numero fascicolo da controllare: {0}, anno fascicolo: {1}", fascicolo.NumeroFascicolo, fascicolo.AnnoFascicolo.ToString());
            ////_log.Debug("fascicolo.NumeroFascicolo: " + fascicolo.NumeroFascicolo + "| Classe: ProtocolloIride, Metodo: FascicoloEsistente(fascicolo)");

            if (String.IsNullOrEmpty(fascicolo.NumeroFascicolo) || (fascicolo.AnnoFascicolo == 0))
                fascicoloOut = new FascicoloOut();
            else
                fascicoloOut = this._fascicolazione.LeggiFascicolo(fascicolo.NumeroFascicolo, fascicolo.AnnoFascicolo.ToString(), fascicolo.Classifica, 0, this.Operatore.ToUpper(), this.Ruolo, this._vert.CodiceAmministrazione, this._vert.Aoo);

            return fascicoloOut;

        }

        private FascicoloOut FascicoloNuovo(Shared.Data.Fascicolo fascicolo)
        {
            var pFascicoloIn = this.CreaFascicoloIn(fascicolo);

            this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.CreaFascicoloRequestFileName, pFascicoloIn);
            this._protocolloLogs.DebugFormat("Chiamata a CreaFascicolo");
            var pFascicoloOut = this._proxyFascIride.CreaFascicolo(pFascicoloIn, this._vert.CodiceAmministrazione, string.Empty);
            this._protocolloLogs.DebugFormat("CreaFascicolo eseguito");
            this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.CreaFascicoloResponseFileName, pFascicoloOut);

            if (pFascicoloOut.Id != 0)
                return pFascicoloOut;
            else
                throw new Exception("ERRORE GENERATO DAL WEB METHOD CREAFASCICOLO. MESSAGGIO: " + pFascicoloOut.Messaggio + ", ERRORE: " + pFascicoloOut.Errore);
        }

        public override DatiFascicoloResponseType Fascicola(Shared.Data.Fascicolo fascicolo)
        {
            if (fascicolo == null)
                return null;

            this._vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloIride>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            DatiFascicoloResponseType pFascicolo = null;

            try
            {
                using (this._proxyProtIride = new ProxyProtIride(this._vert.Url, this.ProxyAddress))
                {
                    this._protocolloIrideService = ProtocolloIrideFactory.Create(this._vert.CodiceAmministrazione, this._proxyProtIride);

                    this._fascicolazione = FascicolazioneFactory.Create(this._vert.Versione, this._vert.UrlFasc, this.ProxyAddress, this._protocolloLogs);
                    using (this._proxyFascIride = this._fascicolazione.Proxy)
                        pFascicolo = this.FascicolaProtocollo(fascicolo);
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA FASCICOLAZIONE, {0}", ex.Message), ex);
            }

            return pFascicolo;
        }

        private DatiFascicoloResponseType FascicolaProtocollo(Shared.Data.Fascicolo fascicolo)
        {
            var fascicoloOut = this.FascicoloEsistente(fascicolo);
            int iFascicoloId;
            int iDocumentoId;
            DocumentoOut docOut;
            var pDatiFascicolo = new DatiFascicoloResponseType();
            var pIstanzaMgr = new IstanzeMgr(this.DatiProtocollo.Db);

            this._protocolloLogs.InfoFormat("FASCICOLAZIONE AMBITO: {0}", this.DatiProtocollo.TipoAmbito.ToString());
            if (this.DatiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.NESSUNO)
            {
                int iDocumentoIdIstanza;

                this._protocolloLogs.DebugFormat("Lettura del protocollo durante la fase di fascicolazione, id protocollo: {0}, numero protocollo: {1}, data protocollo: {2}", this.IdProtocollo, this.AnnoProtocollo, this.NumProtocollo);
                docOut = this.LeggiProtocolloDocumento(this.IdProtocollo, this.AnnoProtocollo, this.NumProtocollo);
                this._protocolloLogs.DebugFormat("Fine lettura del protocollo durante la fase di fascicolazione, id documento: {0}", docOut.IdDocumento.ToString());

                if (docOut.IdDocumento != 0)
                    iDocumentoId = docOut.IdDocumento;
                else
                    throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE DURANTE LA LETTURA DEL PROTOCOLLO, MESSAGGIO: {0}, ERRORE: {1}", docOut.Messaggio, docOut.Errore));

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
                        iFascicoloId = this.FascicoloNuovo(fascicolo).Id;
                        //_log.Debug("iFascicoloId: " + iFascicoloId);
                    }
                    else
                        throw new Exception("IL FASCICOLO SELEZIONATO NON ESISTE!!");

                }
                this._protocolloLogs.DebugFormat("Chiamata a FascicolaDocumento, IdFascicolo: {0}, IdDocumento: {1}, AggiornaClassifica: {2}, Operatore: {3}, Ruolo: {4}, CodiceAmministrazione: {5}, CodiceAoo: {6}", iFascicoloId, iDocumentoId, this._vert.AggiornaClassifica, this.Operatore.ToUpper(), this.Ruolo, this._vert.CodiceAmministrazione, String.Empty);
                var esito = this._proxyFascIride.FascicolaDocumento(iFascicoloId, iDocumentoId, this._vert.AggiornaClassifica, this.Operatore.ToUpper(), this.Ruolo, this._vert.CodiceAmministrazione, string.Empty);

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
                    var fascOut = this._fascicolazione.LeggiFascicolo("", "", "", iFascicoloId, this.Operatore.ToUpper(), this.Ruolo, this._vert.CodiceAmministrazione, this._vert.Aoo);

                    pDatiFascicolo.AnnoFascicolo = fascOut.Anno.ToString();
                    pDatiFascicolo.DataFascicolo = fascOut.Data.Value.ToString("dd/MM/yyyy");
                    pDatiFascicolo.NumeroFascicolo = fascOut.Numero;
                }
            }

            //Verifico se si intende fascicolare una pratica
            //_log.Debug("CodIstanza: " + CodIstanza + "| CLASSE: ProtocolloIride, METODO: FascicolaProtocollo(Fascicolo)");
            if (this.DatiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
            {
                int iDocumentoIdIstanza;

                this._protocolloLogs.DebugFormat("Lettura del protocollo durante la fase di fascicolazione, id protocollo: {0}, numero protocollo: {1}, data protocollo: {2}", this.DatiProtocollo.Istanza.FKIDPROTOCOLLO, this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString(), this.DatiProtocollo.Istanza.NUMEROPROTOCOLLO);
                docOut = this.LeggiProtocolloDocumento(this.DatiProtocollo.Istanza.FKIDPROTOCOLLO, this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString(), this.DatiProtocollo.Istanza.NUMEROPROTOCOLLO);

                this._protocolloLogs.DebugFormat("Fine lettura del protocollo durante la fase di fascicolazione, id documento: {0}", docOut.IdDocumento.ToString());

                if (docOut.IdDocumento != 0)
                    iDocumentoId = docOut.IdDocumento;
                else
                    throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE DURANTE LA LETTURA DEL PROTOCOLLO, MESSAGGIO: {0}, ERRORE: {1}", docOut.Messaggio, docOut.Errore));

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
                        iFascicoloId = this.FascicoloNuovo(fascicolo).Id;
                        //_log.Debug("iFascicoloId: " + iFascicoloId);
                    }
                    else
                        throw new Exception("IL FASCICOLO SELEZIONATO NON ESISTE!!");

                }
                this._protocolloLogs.DebugFormat("Chiamata a FascicolaDocumento, IdFascicolo: {0}, IdDocumento: {1}, AggiornaClassifica: {2}, Operatore: {3}, Ruolo: {4}, CodiceAmministrazione: {5}, CodiceAoo: {6}", iFascicoloId, iDocumentoId, this._vert.AggiornaClassifica, this.Operatore.ToUpper(), this.Ruolo, this._vert.CodiceAmministrazione, String.Empty);
                var esito = this._proxyFascIride.FascicolaDocumento(iFascicoloId, iDocumentoId, this._vert.AggiornaClassifica, this.Operatore.ToUpper(), this.Ruolo, this._vert.CodiceAmministrazione, string.Empty);

                if (!esito.Esito)
                    throw new ProtocolloException("Errore generato dal web method FascicolaDocumento durante la fascicolazione di una istanza.(id fascicolo: " + iFascicoloId + " id documento: " + iDocumentoId + ". Messaggio di errore: " + esito.Messaggio + ". " + esito.Errore + "\r\n");



                //Fascicolo i moviemnti della pratica se protocollati
                var pMovimentiMgr = new MovimentiMgr(this.DatiProtocollo.Db);
                var _Movimento = new Movimenti();
                _Movimento.IDCOMUNE = this.DatiProtocollo.IdComune;
                _Movimento.CODICEISTANZA = this.DatiProtocollo.CodiceIstanza;
                var list = pMovimentiMgr.GetList(_Movimento);
                foreach (var elem in list)
                {
                    if (!string.IsNullOrEmpty(elem.FKIDPROTOCOLLO) || (!string.IsNullOrEmpty(elem.NUMEROPROTOCOLLO) && elem.DATAPROTOCOLLO.HasValue))
                    {
                        //if (string.IsNullOrEmpty(elem.FKIDPROTOCOLLO))
                        //{
                        try
                        {
                            docOut = this.LeggiProtocolloDocumento(elem.FKIDPROTOCOLLO, elem.DATAPROTOCOLLO.GetValueOrDefault(DateTime.MinValue).Year.ToString(), elem.NUMEROPROTOCOLLO);
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
                            this._protocolloLogs.DebugFormat("Chiamata a FascicolaDocumento, IdFascicolo: {0}, IdDocumento: {1}, AggiornaClassifica: {2}, Operatore: {3}, Ruolo: {4}, CodiceAmministrazione: {5}, CodiceAoo: {6}", iFascicoloId, iDocumentoId, this._vert.AggiornaClassifica, this.Operatore.ToUpper(), this.Ruolo, this._vert.CodiceAmministrazione, String.Empty);
                            esito = this._proxyFascIride.FascicolaDocumento(iFascicoloId, iDocumentoId, this._vert.AggiornaClassifica, this.Operatore.ToUpper(), this.Ruolo, this._vert.CodiceAmministrazione, string.Empty);
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
                    var fascOut = this._fascicolazione.LeggiFascicolo("", "", "", iFascicoloId, this.Operatore.ToUpper(), this.Ruolo, this._vert.CodiceAmministrazione, this._vert.Aoo);

                    pDatiFascicolo.AnnoFascicolo = fascOut.Anno.ToString();
                    pDatiFascicolo.DataFascicolo = fascOut.Data.Value.ToString("dd/MM/yyyy");
                    pDatiFascicolo.NumeroFascicolo = fascOut.Numero;
                }
            }

            //Verifico se si intende fascicolare un movimento
            if (this.DatiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.DA_MOVIMENTO)
            {
                EsitoOperazione esito;

                this._protocolloLogs.DebugFormat("FascicoloOut.Id={0}", fascicoloOut.Id);

                if (fascicoloOut.Id != 0)
                {
                    iFascicoloId = fascicoloOut.Id;
                    //if (string.IsNullOrEmpty(_Movimento.FKIDPROTOCOLLO))
                    //{
                    this._protocolloLogs.Debug("Chiamata a LeggiProtocolloDocumento");
                    docOut = this.LeggiProtocolloDocumento(this.DatiProtocollo.Movimento.FKIDPROTOCOLLO, this.DatiProtocollo.Movimento.DATAPROTOCOLLO.GetValueOrDefault(DateTime.MinValue).Year.ToString(), this.DatiProtocollo.Movimento.NUMEROPROTOCOLLO);
                    this._protocolloLogs.DebugFormat("Fine chiamata a LeggiProtocolloDocumento, id {0}", docOut.IdDocumento);
                    if (docOut.IdDocumento == 0)
                        throw new Exception(String.Format("ERRORE GENERATO DAL WEB METHOD LEGGIPROTOCOLLO DURANTE LA FASCICOLAZIONE DI UN MOVIMENTO. MESSAGGIO DI ERRORE: {0}, ERRORE: {1}", docOut.Messaggio, docOut.Errore));

                    iDocumentoId = docOut.IdDocumento;

                    this._protocolloLogs.DebugFormat("Chiamata a FascicolaDocumento, IdFascicolo: {0}, IdDocumento: {1}, AggiornaClassifica: {2}, Operatore: {3}, Ruolo: {4}, CodiceAmministrazione: {5}, CodiceAoo: {6}", iFascicoloId, iDocumentoId, this._vert.AggiornaClassifica, this.Operatore.ToUpper(), this.Ruolo, this._vert.CodiceAmministrazione, String.Empty);
                    esito = this._proxyFascIride.FascicolaDocumento(iFascicoloId, iDocumentoId, this._vert.AggiornaClassifica, this.Operatore.ToUpper(), this.Ruolo, this._vert.CodiceAmministrazione, "");
                    if (!esito.Esito)
                        throw new ProtocolloException("Errore generato dal web method FascicolaDocumento durante la fascicolazione di un movimento.(id fascicolo: " + iFascicoloId + " id documento: " + iDocumentoId + ". Messaggio di errore: " + esito.Messaggio + ". " + esito.Errore + "\r\n");

                    this._protocolloLogs.Debug("Documento Fascicolato");

                    pDatiFascicolo.AnnoFascicolo = fascicoloOut.Anno.ToString();
                    pDatiFascicolo.DataFascicolo = fascicoloOut.Data.Value.ToString("dd/MM/yyyy");
                    pDatiFascicolo.NumeroFascicolo = fascicoloOut.Numero;
                }
            }
            return pDatiFascicolo;
        }

        private FascicoloIn CreaFascicoloIn(Shared.Data.Fascicolo fascicolo)
        {
            var fascicoloIn = new FascicoloIn();
            fascicoloIn.Anno = fascicolo.AnnoFascicolo.ToString();

            fascicoloIn.Data = this.GetDataFascicolo(fascicolo.DataFascicolo);

            fascicoloIn.Numero = fascicolo.NumeroFascicolo;
            fascicoloIn.Oggetto = fascicolo.Oggetto;
            fascicoloIn.Classifica = fascicolo.Classifica;
            fascicoloIn.Utente = this.Operatore.ToUpper();
            fascicoloIn.Ruolo = this.Ruolo;

            return fascicoloIn;
        }

        private string GetDataFascicolo(string data)
        {
            DateTime dtFasc;
            var isValidDate = DateTime.TryParse(data, out dtFasc);

            if (!isValidDate && String.IsNullOrEmpty(this._vert.FormatoDataFasc))
                return data;

            return dtFasc.ToString(this._vert.FormatoDataFasc);
        }

        public override DatiFascicoloResponseType CambiaFascicolo(Shared.Data.Fascicolo fascicolo)
        {
            this._vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloIride>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            DatiFascicoloResponseType pFascicolo = null;
            try
            {
                using (this._proxyProtIride = new ProxyProtIride(this._vert.Url, this.ProxyAddress))
                {
                    this._protocolloIrideService = ProtocolloIrideFactory.Create(this._vert.CodiceAmministrazione, this._proxyProtIride);
                    this._fascicolazione = FascicolazioneFactory.Create(this._vert.Versione, this._vert.UrlFasc, this.ProxyAddress, this._protocolloLogs);
                    using (this._proxyFascIride = this._fascicolazione.Proxy)
                    {
                        var fascicoloOut = this.FascicoloEsistente(fascicolo);

                        if (fascicoloOut.Id != 0)
                            pFascicolo = this.FascicolaProtocollo(fascicolo);
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

        public override EtichetteResponseType StampaEtichette(string idProtocollo, DateTime? dataProtocollo, string numeroProtocollo, int numeroCopie, string stampante)
        {
            this._vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloIride>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            var datiEtichette = new EtichetteResponseType();

            try
            {
                using (this._proxyProtIride = new ProxyProtIride(this._vert.Url, this.ProxyAddress))
                {
                    this._protocolloIrideService = ProtocolloIrideFactory.Create(this._vert.CodiceAmministrazione, this._proxyProtIride);

                    this.DataProtocollo = dataProtocollo;

                    var docOut = this.LeggiProtocolloDocumento(idProtocollo, this.AnnoProtocollo, numeroProtocollo);

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
        /*
        private DatiProtocolloRes CraeCopie()
        {
            Action<int> laMiaAction = (idProtocollo) => { LeggiDocumento(idProtocollo); };

            mioCreaCopie.CreaCopie(laMiaAction);

            laMiaAction(12);
        }
        */

        public override DatiProtocolloResponseType CreaCopie()
        {
            this._vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloIride>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            try
            {
                this._protocolloLogs.Debug("Inizio funzionalità CreaCopie");
                using (this._proxyProtIride = new ProxyProtIride(this._vert.Url, this.ProxyAddress))
                {
                    this._protocolloIrideService = ProtocolloIrideFactory.Create(this._vert.CodiceAmministrazione, this._proxyProtIride);
                    DatiProtocolloResponseType protoRes = null;
                    this._protocolloLogs.DebugFormat("Crea copie - Recupero dei dati dell'istanza, codice istanza: {0}, idcomune: {1}", this.DatiProtocollo.CodiceIstanza, this.DatiProtocollo.IdComune);
                    var ist = new IstanzeMgr(this.DatiProtocollo.Db).GetById(this.DatiProtocollo.IdComune, Convert.ToInt32(this.DatiProtocollo.CodiceIstanza));
                    this._protocolloLogs.DebugFormat("Crea copie - dati dell'istanza recuperati, codice istanza: {0}, idcomune: {1}", this.DatiProtocollo.CodiceIstanza, this.DatiProtocollo.IdComune);

                    if (ist == null)
                        throw new Exception(String.Format("L'ISTANZA {0} CON IDCOMUNE {1} NON ESISTE", this.DatiProtocollo.CodiceIstanza, this.DatiProtocollo.IdComune));

                    this._protocolloLogs.DebugFormat("Crea copie - dati dell'istanza recuperati, codice istanza: {0}, idcomune: {1}", this.DatiProtocollo.CodiceIstanza, this.DatiProtocollo.IdComune);

                    if (this._vert.DisabilitaCreaCopie)
                    {
                        this._protocolloLogs.Debug("Funzionalità CreaCopie disabilitata tramite parametro DISABILITA_CREACOPIE della verticalizzazione PROTOCOLLO_IRIDE");
                        return null;
                    }

                    var docOut = this.LeggiProtocolloDocumento(ist.FKIDPROTOCOLLO, ist.DATAPROTOCOLLO.Value.Year.ToString(), ist.NUMEROPROTOCOLLO);

                    if (docOut.IdDocumento == 0)
                        throw new Exception(String.Format("ERRORE DURANTE IL RECUPERO DEI DATI DAL WEB METHOD LeggiProtocollo DEL WEB SERVICE PER LA CREAZIONE DELLE COPIE, MESSAGGIO: {0}, ERRORE: {1}", docOut.Messaggio, docOut.Errore));
                    var amm = new AmministrazioniMgr(this.DatiProtocollo.Db).GetByIdProtocollo(this.DatiProtocollo.IdComune, Convert.ToInt32(this.CodAmministrazione), this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune);
                    var creaCopie = new CreaCopieService(this._vert.Url, this.ProxyAddress, this._protocolloLogs, this._protocolloSerializer);
                    var creaCopieOut = creaCopie.CreaCopie(uo: amm.PROT_UO,
                                                            ruolo: amm.PROT_RUOLO,
                                                            idDocumento: docOut.IdDocumento,
                                                            annoProtocollo: docOut.AnnoProtocollo.ToString(),
                                                            numeroProtocollo: docOut.NumeroProtocollo.ToString(),
                                                            operatoreIride: this.Operatore,
                                                            codiceEnte: this._vert.CodiceAmministrazione);

                    if ((creaCopieOut.CopieCreate == null) || (creaCopieOut.CopieCreate.Length != 1))
                        throw new Exception(String.Format("MESSAGGIO: {0}, ERRORE: {1}", creaCopieOut.Messaggio, creaCopieOut.Errore));

                    docOut = this.LeggiProtocolloDocumento(creaCopieOut.CopieCreate[0].IdDocumentoCopia.ToString(), string.Empty, string.Empty);

                    if (docOut.IdDocumento == 0)
                        throw new Exception(String.Format("ERRORE RESTITUITO DA CREACOPIE DURANTE LA LETTURA DEL PROTOCOLLO, MESSAGGIO: {0}, ERRORE: {1}", docOut.Messaggio, docOut.Errore));

                    protoRes = this.CreaDatiProtocollo(docOut);

                    return protoRes;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL CREA COPIE, {0}", ex.Message), ex);
            }
        }

        private CreaCopieIn CreaCopieIn()
        {
            var creaCopieIn = new CreaCopieIn();

            var docOut = this.LeggiProtocolloDocumento(this.DatiProtocollo.Istanza.FKIDPROTOCOLLO, this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString(), this.DatiProtocollo.Istanza.NUMEROPROTOCOLLO);

            if (docOut.IdDocumento == 0)
                throw new Exception(String.Format("ERRORE DURANTE IL RECUPERO DEI DATI DAL WEB METHOD LeggiProtocollo DEL WEB SERVICE PER LA CREAZIONE DELLE COPIE, MESSAGGIO: {0}, ERRORE: {1}", docOut.Messaggio, docOut.Errore));

            creaCopieIn.AnnoProtocollo = docOut.AnnoProtocollo.ToString();
            creaCopieIn.NumeroProtocollo = docOut.NumeroProtocollo.ToString();
            creaCopieIn.IdDocumento = docOut.IdDocumento.ToString();
            var ammMgr = new AmministrazioniMgr(this.DatiProtocollo.Db);
            var amm = ammMgr.GetByIdProtocollo(this.DatiProtocollo.IdComune, Convert.ToInt32(this.CodAmministrazione), this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune);
            creaCopieIn.UODestinatarie = new UODestinataria[1];
            creaCopieIn.UODestinatarie[0] = new UODestinataria();
            creaCopieIn.UODestinatarie[0].Carico = amm.PROT_UO;
            creaCopieIn.UODestinatarie[0].TipoUO = "UO";
            creaCopieIn.UODestinatarie[0].Data = DateTime.Now.ToString("dd/MM/yyyy");
            creaCopieIn.Utente = this.Operatore;
            creaCopieIn.Ruolo = amm.PROT_RUOLO;
            creaCopieIn.UODestinatarie[0].NumeroCopie = "1";


            return creaCopieIn;
        }

        #endregion

        #region Metodi di messa alla firma
        public override DatiProtocolloResponseType MettiAllaFirma(DatiProtocolloIn proto)
        {
            this._vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloIride>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            DatiProtocolloResponseType documentoRes = null;
            try
            {
                using (this._proxyProtIride = new ProxyProtIride(this._vert.Url, this.ProxyAddress))
                {
                    this._protocolloIrideService = ProtocolloIrideFactory.Create(this._vert.CodiceAmministrazione, this._proxyProtIride);

                    var protoIn = this.CreaProtocolloIn(proto);

                    this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.InserisciDocumentoRequestFileName, protoIn);
                    this._protocolloLogs.InfoFormat("Chiamata a web method InserisciDocumento da metti alla firma, request file: {0}", ProtocolloLogsConstants.InserisciDocumentoRequestFileName);
                    var docOut = this._proxyProtIride.InserisciDocumento(protoIn);

                    this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.InserisciDocumentoResponseFileName, docOut);

                    if (docOut.IdDocumento != 0)
                    {
                        this._protocolloLogs.Info("MESSA ALLA FIRMA AVVENUTA CON SUCCESSO");
                        documentoRes = this.CreaDatiProtocollo(docOut);
                    }
                    else
                        throw new Exception("ERRORE GENERATO DAL WEB METHOD INSERISCIDOCUMENTO. MESSAGGIO DI ERRORE: " + docOut.Messaggio + "." + docOut.Errore + "\r\n");
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
            return this._protocolloIrideService.InserisciProtocollo(protocolloIn);
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn proto)
        {
            this._vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloIride>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            DatiProtocolloResponseType protoRes = null;
            try
            {
                using (this._proxyProtIride = new ProxyProtIride(this._vert.Url, this.ProxyAddress))
                {
                    this._protocolloIrideService = ProtocolloIrideFactory.Create(this._vert.CodiceAmministrazione, this._proxyProtIride);
                    this._protocolloLogs.Debug("#### Inizio Richiesta di Protocollazione ####");
                    this._protocolloLogs.Debug("#### CreaProtocolloIn ####");
                    var protoIn = this.CreaProtocolloIn(proto);

                    this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneRequestFileName, protoIn);

                    this._protocolloLogs.InfoFormat("Chiamata al web method InserisciProtocollo, file request: {0}", ProtocolloLogsConstants.ProtocollazioneRequestFileName);
                    var protoOut = this.InserisciProtocollo(protoIn);

                    this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneResponseFileName, protoOut);

                    if (protoOut.IdDocumento != 0)
                    {
                        this._protocolloLogs.InfoFormat("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO, FLUSSO: {0}", protoIn.Origine);

                        if ((protoIn.Origine == "P" || protoIn.Origine == "I") && (this.Anagrafiche != null && this.Anagrafiche.Count > 1))
                        {
                            this.CreaCopiePerAmministrazioniInterne(proto, protoOut);
                        }

                        this._protocolloLogs.InfoFormat("URL PEC PRESENTE: {0}, FLUSSO: {1}", !String.IsNullOrEmpty(this._vert.UrlPec), protoIn.Origine);
                        if (!String.IsNullOrEmpty(this._vert.UrlPec) && (protoIn.Origine == "P"))
                        {
                            this.InviaPec(proto, protoOut, protoIn.Ruolo);
                        }

                        protoRes = this.CreaDatiProtocollo(protoOut);
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
            this._protocolloLogs.Info("INIZIO FUNZIONALITA' DI INVIO PEC");
            try
            {
                if (String.IsNullOrEmpty(protoIn.Oggetto))
                    this._protocolloLogs.WarnFormat("INVIO PEC non eseguito a causa dell'assenza dell'oggetto della mail, controllare l'oggetto di default in configurazione, protocollo numero: {0}, data: {1}", protoOut.NumeroProtocollo.ToString(), protoOut.DataProtocollo.ToString("dd/MM/yyyy"));
                else if (String.IsNullOrEmpty(this._vert.MittenteMailPec))
                    this._protocolloLogs.WarnFormat("INVIO PEC non eseguito a causa dell'assenza del mittente della mail, controllare il parametro MITTENTE_MAIL_PEC della verticalizzazione PROTOCOLLO_IRIDE, protocollo numero: {0}, data: {1}", protoOut.NumeroProtocollo.ToString(), protoOut.DataProtocollo.ToString("dd/MM/yyyy"));
                else if (String.IsNullOrEmpty(this._vert.UrlPec))
                    this._protocolloLogs.WarnFormat("INVIO PEC non eseguito a causa dell'assenza dell'url (end point) del servizio di invio mail PEC di Iride, controllare il parametro URL_PEC della verticalizzazione PROTOCOLLO_IRIDE, protocollo numero: {0}, data: {1}", protoOut.NumeroProtocollo.ToString(), protoOut.DataProtocollo.ToString("dd/MM/yyyy"));
                else
                {
                    this.Ruolo = ruolo;
                    var docOut = this.LeggiDocumento(protoOut.IdDocumento);
                    var seriali = docOut.Allegati.Select(x => x.Serial.ToString());

                    if (this._vert.WarningPec)
                    {
                        var anagraficheNoPEC = this.Anagrafiche.Where(x => String.IsNullOrEmpty(x.Pec)).Select(x => x.NomeCognome);
                        if (anagraficheNoPEC.Count() > 0)
                        {
                            this._protocolloLogs.WarnFormat("I SEGUENTI DESTINATARI NON PRESENTANO UN INDIRIZZO PEC, AI QUALI NON E' STATA QUINDI INVIATA: {0}", String.Join(", ", anagraficheNoPEC));
                        }

                        var anagraficheNoMezzo = this.Anagrafiche.Where(x => x.MezzoInvio != this._vert.MezzoPec).Select(x => x.NomeCognome);

                        if (anagraficheNoMezzo.Count() > 0)
                        {
                            this._protocolloLogs.WarnFormat("I SEGUENTI DESTINATARI NON PRESENTANO IL MEZZO DI INVIO PEC, AI QUALI NON E' STATA QUINDI INVIATA: {0}", String.Join(", ", anagraficheNoMezzo));
                        }
                    }

                    var listaPec = this.Anagrafiche.Where(y => !String.IsNullOrEmpty(y.Pec) && y.MezzoInvio == this._vert.MezzoPec).
                                                                        GroupBy(x => x.Pec.ToUpperInvariant()).
                                                                        Select(x => x.Key).ToArray();

                    this._protocolloLogs.InfoFormat("NUMERO DESTINATARI PER INVIO PEC: {0}", listaPec.Length);

                    if (listaPec.Length > 0)
                    {
                        var pec = PecFactory.Create(this._vert.Versione, seriali, listaPec, this._vert.Aoo, this._protocolloLogs, this._protocolloSerializer);
                        pec.Invia(this._vert.UrlPec, this.ProxyAddress, protoOut.IdDocumento.ToString(), Regex.Replace(protoIn.Oggetto, @"\r\n?|\n", "-"), protoIn.CorpoMail, this._vert.MittenteMailPec, this.Operatore, ruolo, this._vert.CodiceAmministrazione);

                        if (this._vert.WarningPec)
                        {
                            this._protocolloLogs.WarnFormat("PEC INVIATA CORRETTAMENTE AI SEGUENTI DESTINATARI: {0}", String.Join(", ", listaPec));
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                this._protocolloLogs.WarnFormat(String.Format("PROBLEMA DURANTE LA FUNZIONALITA' DI INVIO PEC, ERRORE: {0}", ex.Message));
            }
            finally
            {

            }
        }

        private void CreaCopiePerAmministrazioniInterne(DatiProtocolloIn protocolloInput, ProtocolloOut protocolloOutputOrigine)
        {
            try
            {
                this._protocolloLogs.DebugFormat("Inizio funzionalità crea copie del protocollo numero: {0}, data: {1}, id: {2}", protocolloOutputOrigine.NumeroProtocollo.ToString(), protocolloOutputOrigine.DataProtocollo.ToString("dd/MM/yyyy"), protocolloOutputOrigine.IdDocumento.ToString());

                var uoDestinatari = new List<UODestinataria>();

                var ammList = protocolloInput.Destinatari.Amministrazione.Where(amm => !String.IsNullOrEmpty(amm.PROT_UO) && !String.IsNullOrEmpty(amm.PROT_RUOLO)).ToList();
                if (ammList.Count > 0)
                {
                    if (protocolloInput.Destinatari.Anagrafe.Count == 0 && protocolloInput.Destinatari.Amministrazione.Where(x => String.IsNullOrEmpty(x.PROT_UO) && String.IsNullOrEmpty(x.PROT_RUOLO)).Count() == 0)
                        ammList = ammList.Skip(1).ToList();

                    ammList.ForEach(amm => uoDestinatari.Add(new UODestinataria { Carico = amm.PROT_UO, Data = DateTime.Now.ToString("dd/MM/yyyy"), TipoUO = "UO", NumeroCopie = "1" }));

                    var creaCopieService = new CreaCopieService(this._vert.Url, this.ProxyAddress, this._protocolloLogs, this._protocolloSerializer);

                    var creaCopieOut = creaCopieService.CreaCopie(protocolloInput.Mittenti.Amministrazione[0].PROT_RUOLO,
                                                    protocolloOutputOrigine.IdDocumento,
                                                    protocolloOutputOrigine.AnnoProtocollo.ToString(),
                                                    protocolloOutputOrigine.NumeroProtocollo.ToString(),
                                                    this.Operatore,
                                                    this._vert.CodiceAmministrazione, uoDestinatari.ToArray());

                    if (creaCopieOut.CopieCreate == null || creaCopieOut.CopieCreate.Length == 0)
                        this._protocolloLogs.ErrorFormat("E' stato restituito un errore dal web service durante la funzionalità CreaCopie eseguita dopo la protocollazione, Id Protocollo Originale: {0}, numero/anno {1}/{2}, Errore: {3}",
                                                    protocolloOutputOrigine.IdDocumento,
                                                    protocolloOutputOrigine.NumeroProtocollo.ToString(),
                                                    protocolloOutputOrigine.AnnoProtocollo.ToString(),
                                                    creaCopieOut.Errore);
                    else
                        this._protocolloLogs.InfoFormat("E' stata creata una copia con la funzionalità CreaCopie eseguita dopo la protocollazione, Id Protocollo Originale:{0}, Id Protocollo Copia: {1}, Numero/Anno Protocollo: {2}/{3}",
                                                    creaCopieOut.IdDocumentoSorgente.ToString(),
                                                    protocolloOutputOrigine.IdDocumento.ToString(),
                                                    protocolloOutputOrigine.NumeroProtocollo.ToString(),
                                                    protocolloOutputOrigine.AnnoProtocollo.ToString()
                                                    );

                    this._protocolloLogs.DebugFormat("Fine funzionalità crea copie del protocollo numero: {0}, data: {1}, id: {2}", protocolloOutputOrigine.NumeroProtocollo.ToString(), protocolloOutputOrigine.DataProtocollo.ToString("dd/MM/yyyy"), protocolloOutputOrigine.IdDocumento.ToString());
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE AVVENUTO DURANTE LA CREAZIONE DELLE COPIE PER LE AMMINISTRAZIONI INTERNE, COPIA DOPO PROTOCOLLAZIONE, {0}", ex.Message), ex);
            }
        }

        protected DatiProtocolloResponseType CreaDatiProtocollo(ProtocolloOut protoOut)
        {
            try
            {
                var protoRes = new DatiProtocolloResponseType();

                protoRes.IdProtocollo = protoOut.IdDocumento.ToString();
                protoRes.AnnoProtocollo = protoOut.AnnoProtocollo.ToString();
                protoRes.DataProtocollo = protoOut.DataProtocollo.ToString("dd/MM/yyyy");

                protoRes.NumeroProtocollo = protoOut.NumeroProtocollo.ToString();

                if (this.ModificaNumero)
                    protoRes.NumeroProtocollo = protoRes.NumeroProtocollo.TrimStart(new char[] { '0' });

                if (this.AggiungiAnno)
                    protoRes.NumeroProtocollo += "/" + protoOut.AnnoProtocollo.ToString();

                if (!String.IsNullOrEmpty(protoOut.Errore))
                {
                    this._protocolloLogs.Warn(protoOut.Errore);
                }

                protoRes.Warning = this._protocolloLogs.Warnings.WarningMessage;

                if (!String.IsNullOrEmpty(this._vert.MessaggioProtoOk) && !String.IsNullOrEmpty(this._protocolloLogs.Warnings.WarningMessage))
                {
                    protoRes.Warning = this._protocolloLogs.Warnings.WarningMessage.Replace(this._vert.MessaggioProtoOk, "");
                }

                this._protocolloLogs.InfoFormat("Dati protocollo restituiti, numero: {0}, anno: {1}, data: {2}", protoRes.NumeroProtocollo, protoRes.AnnoProtocollo, protoRes.DataProtocollo);

                return protoRes;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DOPO LA RESTITUZIONE DEI DATI, IL PROTOCOLLO POTREBBE ESSERE STATO CREATO, {0}", ex.Message), ex);
            }
        }

        private DatiProtocolloResponseType CreaDatiProtocollo(DocumentoOut protoOut)
        {
            try
            {
                var protoRes = new DatiProtocolloResponseType();

                protoRes.IdProtocollo = protoOut.IdDocumento.ToString();
                protoRes.AnnoProtocollo = protoOut.AnnoProtocollo.ToString();
                protoRes.DataProtocollo = protoOut.DataProtocollo.ToString("dd/MM/yyyy");

                protoRes.NumeroProtocollo = protoOut.NumeroProtocollo.ToString();

                if (this.ModificaNumero)
                    protoRes.NumeroProtocollo = protoRes.NumeroProtocollo.TrimStart(new char[] { '0' });

                if (this.AggiungiAnno)
                    protoRes.NumeroProtocollo += "/" + protoOut.AnnoProtocollo.ToString();

                if (!String.IsNullOrEmpty(protoOut.Errore))
                {
                    this._protocolloLogs.Warn(protoOut.Errore);
                }

                protoRes.Warning = this._protocolloLogs.Warnings.WarningMessage;

                if (!String.IsNullOrEmpty(this._vert.MessaggioProtoOk) && !String.IsNullOrEmpty(this._protocolloLogs.Warnings.WarningMessage))
                    protoRes.Warning = this._protocolloLogs.Warnings.WarningMessage.Replace(this._vert.MessaggioProtoOk, "");


                this._protocolloLogs.InfoFormat("Dati protocollo restituiti, numero: {0}, anno: {1}, data: {2}", protoRes.NumeroProtocollo, protoRes.AnnoProtocollo, protoRes.DataProtocollo);

                return protoRes;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DOPO LA RESTITUZIONE DEI DATI, IL PROTOCOLLO POTREBBE ESSERE STATO CREATO, {0}", ex.Message), ex);
            }
        }

        protected ProtocolloIn CreaProtocolloIn(DatiProtocolloIn datiProto)
        {
            var protoIn = new ProtocolloIn();

            //Setto i parametri della classe ProtocolloIn
            protoIn.Data = DateTime.Now.Date.ToString("dd/MM/yyyy");
            protoIn.Classifica = datiProto.Classifica;
            protoIn.TipoDocumento = datiProto.TipoDocumento;
            protoIn.Oggetto = datiProto.Oggetto;
            protoIn.Origine = datiProto.Flusso;
            protoIn.AggiornaAnagrafiche = this._vert.AggiornaAnagrafiche;

            //Gestione Fascicolazione (come comportarsi con la protocollazione delle autorizzazioni???)
            if (!string.IsNullOrEmpty(this._vert.NumeroPratica) && !this.GestisciFascicolazione)
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

        private void SetAllegati(ProtocolloIn protoIn, DatiProtocolloIn datiProtoIn)
        {
            try
            {
                protoIn.Allegati = new AllegatoIn[datiProtoIn.NumeroAllegatiPresenti];

                int iIndex = 0;
                foreach (var protoAllegati in datiProtoIn.RecuperaAllegati())
                {
                    //non dovrebbe essere necessario
                    if (protoAllegati.OGGETTO == null)
                        throw new ProtocolloException("Errore generato dal web method SetAllegati del protocollo Iride. Metodo: SetAllegati, modulo: ProtocolloIride. C'è un allegato con il campo OGGETTO null.\r\n");

                    protoIn.Allegati[iIndex] = new AllegatoIn();

                    protoIn.Allegati[iIndex].ContentType = protoAllegati.MimeType;
                    protoIn.Allegati[iIndex].Image = protoAllegati.OGGETTO;

                    if (!String.IsNullOrEmpty(protoAllegati.Extension))
                        protoIn.Allegati[iIndex].TipoFile = protoAllegati.Extension.Substring(1);

                    protoIn.Allegati[iIndex].Commento = protoAllegati.NOMEFILE;

                    iIndex++;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL SETTAGGIO DEGLI ALLEGATI, {0}", ex.Message), ex);
            }
        }

        private void SetMittenti(ProtocolloIn protoIn, DatiProtocolloIn datiProto)
        {
            try
            {
                var mittentiDestinatariList = new List<MittenteDestinatarioIn>();

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
                            if (mittentiDestinatariList.Where(x => x.CodiceFiscale == amministrazione.PARTITAIVA).Count() > 0)
                                throw new Exception("SONO PRESENTI PIU' MITTENTI CON LO STESSO CODICE FISCALE / PARTITA IVA");


                            var mittentiDestinatari = new MittenteDestinatarioIn();
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

                            var mittentiDestinatari = new MittenteDestinatarioIn();

                            if (!String.IsNullOrEmpty(protoAnagrafe.CODICEFISCALE))
                                mittentiDestinatari.CodiceFiscale = protoAnagrafe.CODICEFISCALE;
                            else if (!String.IsNullOrEmpty(protoAnagrafe.PARTITAIVA))
                                mittentiDestinatari.CodiceFiscale = protoAnagrafe.PARTITAIVA;

                            /*if (!(String.IsNullOrEmpty(protoAnagrafe.NOMINATIVO) && String.IsNullOrEmpty(protoAnagrafe.NOME)))
                                mittentiDestinatari.CognomeNome = String.Concat(protoAnagrafe.NOMINATIVO, " ", protoAnagrafe.NOME).TrimEnd(); //((string)(protoAnagrafe.NOMINATIVO + " " + protoAnagrafe.NOME)).TrimEnd();*/
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
                            mittentiDestinatari.CodiceComuneNascita = !String.IsNullOrEmpty(protoAnagrafe.CodiceStatoEsteroNasc) ? (String.IsNullOrEmpty(this._vert.View) ? String.Empty : this.GetDecodeCodiceIstatStato(protoAnagrafe.CodiceStatoEsteroNasc)) : this.GetDecodeCodiceIstatStato(protoAnagrafe.CodiceIstatComNasc);
                            mittentiDestinatari.CodiceComuneResidenza = !String.IsNullOrEmpty(protoAnagrafe.CodiceStatoEsteroRes) ? (String.IsNullOrEmpty(this._vert.View) ? String.Empty : this.GetDecodeCodiceIstatStato(protoAnagrafe.CodiceStatoEsteroRes)) : this.GetDecodeCodiceIstatStato(protoAnagrafe.CodiceIstatComRes);

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

        private void SetDestinatari(ProtocolloIn protoIn, Shared.Data.DatiProtocolloIn datiProto)
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

                    foreach (var amministrazione in amministrazioniEsterne)
                    {
                        //if (mittentiDestinatariList.Where(x => x.CodiceFiscale == amministrazione.PARTITAIVA).Count() > 0)
                        //    throw new Exception("SONO PRESENTI PIU' DESTINATARI CON LO STESSO CODICE FISCALE / PARTITA IVA");

                        if (mittentiDestinatariList.Count >= 100)
                        {
                            this._protocolloLogs.WarnFormat("Sono stati conteggiati {0} destinatari mentre il limite massimo è di 99", mittentiDestinatariList.Count.ToString());
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

                    var mittentiDestinatari = new MittenteDestinatarioIn();

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
                    mittentiDestinatari.CodiceComuneNascita = !String.IsNullOrEmpty(anagrafe.CodiceStatoEsteroNasc) ? (String.IsNullOrEmpty(this._vert.View) ? String.Empty : this.GetDecodeCodiceIstatStato(anagrafe.CodiceStatoEsteroNasc)) : this.GetDecodeCodiceIstatStato(anagrafe.CodiceIstatComNasc);
                    mittentiDestinatari.CodiceComuneResidenza = !String.IsNullOrEmpty(anagrafe.CodiceStatoEsteroRes) ? (String.IsNullOrEmpty(this._vert.View) ? String.Empty : this.GetDecodeCodiceIstatStato(anagrafe.CodiceStatoEsteroRes)) : this.GetDecodeCodiceIstatStato(anagrafe.CodiceIstatComRes);

                    if (!String.IsNullOrEmpty(anagrafe.CITTA))
                    {
                        mittentiDestinatari.Localita = anagrafe.CITTA;
                    }

                    if (!String.IsNullOrEmpty(anagrafe.Mezzo))
                        mittentiDestinatari.Mezzo = anagrafe.Mezzo;

                    mittentiDestinatari.Nazionalita = "100"; //N.B.: Il valore deve essere ricavato da una tabella Iride

                    mittentiDestinatariList.Add(mittentiDestinatari);
                }

                if (mittentiDestinatariList.Count == 0)
                {
                    var amministrazioniInterne = datiProto.Destinatari.Amministrazione.Where(x => !String.IsNullOrEmpty(x.PROT_UO) || !String.IsNullOrEmpty(x.PROT_UO)).ToList();
                    var primaAmministrazione = amministrazioniInterne.First();

                    protoIn.InCaricoA = primaAmministrazione.PROT_UO;
                    protoIn.Ruolo = primaAmministrazione.PROT_RUOLO;

                    var destinatario = new MittenteDestinatarioIn
                    {
                        CognomeNome = primaAmministrazione.AMMINISTRAZIONE,
                        CodiceFiscale = !String.IsNullOrEmpty(primaAmministrazione.PARTITAIVA) ? primaAmministrazione.PARTITAIVA : String.Empty,
                        Indirizzo = !String.IsNullOrEmpty(primaAmministrazione.INDIRIZZO) ? primaAmministrazione.INDIRIZZO : String.Empty,
                        Localita = !String.IsNullOrEmpty(primaAmministrazione.CITTA) ? primaAmministrazione.CITTA : String.Empty,
                        Mezzo = !String.IsNullOrEmpty(primaAmministrazione.Mezzo) ? primaAmministrazione.Mezzo : String.Empty,
                        TipoPersona = Constants.PERSONA_GIURIDICA_IRIDE
                    };

                    mittentiDestinatariList.Add(destinatario);
                }

                if (mittentiDestinatariList.Count > 0 && datiProto.Flusso == ProtocolloConstants.COD_PARTENZA)
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

            if (string.IsNullOrEmpty(this._vert.View))
                return codice;
            else
            {
                if (string.IsNullOrEmpty(codice))
                    return codice;
                else
                {
                    var db = new DataBase(this._vert.ConnectionString, (ProviderType)Enum.Parse(typeof(ProviderType), this._vert.Provider, true));
                    db.Connection.Open();

                    try
                    {
                        int count = 0;
                        string query = "select COD_IRIDE from " + (string.IsNullOrEmpty(this._vert.Owner) ? string.Empty : this._vert.Owner + ".") + this._vert.View + " where COD_ISTAT = " + codice;
                        using (var cmd = db.CreateCommand(query))
                        {
                            using (var reader = cmd.ExecuteReader())
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

        private DatiProtocolloFascicolatoResponseType Fascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var datiProtFasc = new DatiProtocolloFascicolatoResponseType();
            var pDocumentoOut = this.LeggiProtocolloDocumento(idProtocollo, annoProtocollo, numeroProtocollo);
            //_protocolloSerializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, pDocumentoOut);

            if (pDocumentoOut.IdDocumento != 0)
            {
                //Verifico se il protocollo è stato fascicolato
                if (pDocumentoOut.IdPratica == 0 && (String.IsNullOrEmpty(pDocumentoOut.NumeroPratica) || pDocumentoOut.AnnoPratica == 0))
                    datiProtFasc.Fascicolato = EnumFascicolatoType.no;
                else
                {
                    //FascicoloOut fascicoloOut;
                    var fascicoloOut = this._fascicolazione.LeggiFascicolo(pDocumentoOut.NumeroPratica, pDocumentoOut.AnnoPratica.ToString(), pDocumentoOut.Classifica, pDocumentoOut.IdPratica, this.Operatore, this.Ruolo, this._vert.CodiceAmministrazione, this._vert.Aoo);

                    datiProtFasc.AnnoFascicolo = fascicoloOut.Anno.ToString();
                    datiProtFasc.Classifica = String.IsNullOrEmpty(fascicoloOut.Classifica) ? pDocumentoOut.Classifica : fascicoloOut.Classifica;
                    datiProtFasc.DataFascicolo = fascicoloOut.Data.Value.ToString("dd/MM/yyyy");
                    datiProtFasc.NumeroFascicolo = fascicoloOut.Numero;
                    datiProtFasc.Oggetto = fascicoloOut.Oggetto;
                    datiProtFasc.Fascicolato = EnumFascicolatoType.si;
                }
            }
            else
            {
                datiProtFasc.Fascicolato = EnumFascicolatoType.warning;
                datiProtFasc.NoteFascicolo = "Errore: " + pDocumentoOut.Messaggio + "." + pDocumentoOut.Errore;
            }


            return datiProtFasc;
        }

        #endregion

        #region Metodi per la lettura di un protocollo
        private DocumentoOut LeggiProtocolloDocumento(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            this._protocolloLogs.DebugFormat("Inizio metodo LeggiProtocolloDocumento, idprotocollo {0}, annoprotocollo {1}, numeroprotocollo {2}", idProtocollo, annoProtocollo, numeroProtocollo);
            if (!string.IsNullOrEmpty(idProtocollo) || (!string.IsNullOrEmpty(numeroProtocollo) && !string.IsNullOrEmpty(annoProtocollo)))
            {
                var docOut = new DocumentoOut();
                GC.Collect();
                if (String.IsNullOrEmpty(idProtocollo) || this._vert.UsaNumAnnoLeggi)
                {
                    string[] sNumProtSplit = numeroProtocollo.Split(new Char[] { '/' });
                    string sNumProtocollo = sNumProtSplit[0];
                    this._protocolloLogs.Debug("Chiamata a LeggiProtocollo");
                    docOut = this.LeggiProtocollo(Convert.ToInt16(annoProtocollo), Convert.ToInt32(sNumProtocollo));
                    this._protocolloLogs.Debug("Fine Chiamata a LeggiProtocollo");
                }
                else
                {
                    this._protocolloLogs.Debug("Chiamata a LeggiDocumento");
                    docOut = this.LeggiDocumento(Convert.ToInt32(idProtocollo));
                    this._protocolloLogs.Debug("Fine chiamata a LeggiDocumento");
                }

                return docOut;
            }
            else
                throw new Exception("NON È POSSIBILE RILEGGERE IL PROTOCOLLO/DOCUMENTO");
        }

        protected virtual DocumentoOut LeggiProtocollo(short annoProtocollo, int numeroProtocollo)
        {
            this._protocolloLogs.InfoFormat("Chiamata a web method LeggiProtocollo, numero protocollo: {0}, anno protocollo: {1}, operatore: {2}, ruolo: {3}", numeroProtocollo, annoProtocollo, this.Operatore.ToUpper(), this.Ruolo);
            var response = this._protocolloIrideService.LeggiProtocollo(annoProtocollo, numeroProtocollo, this.Operatore.ToUpper(), this.Ruolo);
            this._protocolloLogs.InfoFormat("Fine lettura del protocollo, numero: {0}, anno: {1}, operatore: {2}, ruolo: {3}", numeroProtocollo, annoProtocollo, this.Operatore.ToUpper(), this.Ruolo);

            if (this._protocolloLogs.IsDebugEnabled)
                this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, response);

            return response;
        }

        protected virtual DocumentoOut LeggiDocumento(int idProtocollo)
        {
            this._protocolloLogs.InfoFormat("Chiamata a LeggiDocumento, id protocollo: {0}, operatore: {1}, ruolo: {2}", idProtocollo, this.Operatore.ToUpper(), this.Ruolo);
            var response = this._protocolloIrideService.LeggiDocumento(idProtocollo, this.Operatore.ToUpper(), this.Ruolo);
            this._protocolloLogs.InfoFormat("Fine lettura del documento, con id: {0}, operatore: {1}, ruolo: {2}", idProtocollo, this.Operatore.ToUpper(), this.Ruolo);

            if (this._protocolloLogs.IsDebugEnabled)
                this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, response);

            return response;
        }

        public override void CheckProtocolloLetto(string annoProtocollo, string numeroProtocollo, string idProtocollo, DatiProtocolloLettoResponseType pDatiProtocolloLetto)
        {
            this._vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloIride>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            if (String.IsNullOrEmpty(idProtocollo))
                base.CheckProtocolloLetto(annoProtocollo, numeroProtocollo, idProtocollo, pDatiProtocolloLetto);

        }

        public override AllegatoResponseType LeggiAllegato()
        {
            this._vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloIride>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            var allegato = this.LeggiAllegatoDaLeggiProtocollo();

            return allegato;
        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            this._vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloIride>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            DatiProtocolloLettoResponseType protocolloLetto = null;
            DocumentoOut documentoOut = null;
            try
            {
                using (this._proxyProtIride = new ProxyProtIride(this._vert.Url, this.ProxyAddress))
                {
                    this._protocolloIrideService = ProtocolloIrideFactory.Create(this._vert.CodiceAmministrazione, this._proxyProtIride);
                    this._protocolloLogs.Debug("#### Inizio Richiesta di LeggiProtocollo ####");
                    this.NumProtocollo = leggiProtocolloRequest.NumeroProtocollo;
                    this.AnnoProtocollo = leggiProtocolloRequest.AnnoProtocollo;
                    this._protocolloLogs.Debug("#### Chiamata a LeggiProtocollo ####");
                    documentoOut = this.LeggiProtocolloDocumento(leggiProtocolloRequest.IdProtocollo, leggiProtocolloRequest.AnnoProtocollo, leggiProtocolloRequest.NumeroProtocollo);
                    this._protocolloLogs.Debug("##### Ricevuta risposta dal Protocollo IRIDE ####");
                    this._protocolloLogs.DebugFormat("Inizio funzionalità di creazione dei dati del protocollo dopo la risposta del web service, Id Protocollo: {0}, Anno Protocollo: {1}, Numero Protocollo: {2}", leggiProtocolloRequest.IdProtocollo, leggiProtocolloRequest.AnnoProtocollo, leggiProtocolloRequest.NumeroProtocollo);
                    protocolloLetto = this.CreaDatiProtocolloLetto(documentoOut);
                    this._protocolloLogs.DebugFormat("Fine funzionalità di creazione dei dati del protocollo dopo la risposta del web service, Id Protocollo: {0}, Anno Protocollo: {1}, Numero Protocollo: {2}", leggiProtocolloRequest.IdProtocollo, leggiProtocolloRequest.AnnoProtocollo, leggiProtocolloRequest.NumeroProtocollo);
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA LETTURA DEL PROTOCOLLO, {0}", ex.Message), ex);
            }

            return new List<DatiProtocolloLettoResponseType>() { protocolloLetto };
        }

        protected DatiProtocolloLettoResponseType CreaDatiProtocolloLetto(DocumentoOut response)
        {
            try
            {
                var protoLetto = new DatiProtocolloLettoResponseType();

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
                    if (!String.IsNullOrEmpty(response.MittenteInterno))
                        protoLetto.MittenteInterno = response.MittenteInterno;
                    if (!String.IsNullOrEmpty(response.MittenteInterno_Descrizione))
                        protoLetto.MittenteInterno_Descrizione = response.MittenteInterno_Descrizione;
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
                        protoLetto.MittentiDestinatari = new MittDestOutType[]
                        {
                            new MittDestOutType
                            {
                                IdSoggetto = response.MittenteInterno,
                                CognomeNome = response.MittenteInterno_Descrizione
                            }
                        };
                    }

                    //Sezione Mittenti/Destinatari
                    if (response.MittentiDestinatari != null)
                    {
                        protoLetto.MittentiDestinatari = new MittDestOutType[response.MittentiDestinatari.Length];

                        int iIndex = 0;
                        foreach (var pMittDestOut in response.MittentiDestinatari)
                        {
                            protoLetto.MittentiDestinatari[iIndex] = new MittDestOutType();
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
                            var nomeFile = a.Commento;
                            if (!String.IsNullOrEmpty(a.TipoFile))
                                nomeFile = String.Format("{0}.{1}", Path.GetFileNameWithoutExtension(nomeFile), a.TipoFile);

                            return new AllegatoResponseType
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

