
//using System;
//using System.Configuration;
//using System.ComponentModel;
//using System.Web.Services;
//using Init.SIGePro.Authentication;
//using Init.SIGePro.Manager;
//using Init.SIGePro.Exceptions.Token;
//using System.Xml;
//using System.Xml.Schema;
//using System.IO;
//using System.Globalization;
//using System.Text;
//using System.Xml.Serialization;
//using System.Web;
//using Init.SIGePro.Protocollo;
//using Init.SIGePro.Utils;
//using Init.SIGePro.Exceptions.Protocollo;
//using log4net;
//using Init.SIGePro.Protocollo.Data;
//using Init.SIGePro.Protocollo.Manager;
//using Sigepro.net.WebServices.WsSIGePro;
//using PersonalLib2.Data;
//using Init.SIGePro.Data;

//namespace SIGePro.Net.WebServices.WsSIGePro
//{
//    [WebService(Namespace = "http://init.sigepro.it")]
//    public class CWSSigeproProtocollazione : SigeproWebService
//    {
//        /*const int ERR_LETTURA_PROT_FAILED = 58001;
//        const int ERR_PROT_FAILED = 58002;
//        const int ERR_STAMPA_ETIC_FAILED = 58003;
//        const int ERR_PROT_ANN_FAILED = 58004;
//        const int ERR_LISTA_CLASSIFICHE_FAILED = 58005;
//        const int ERR_LISTA_TIPI_DOCUMENTO_FAILED = 58006;
//        const int ERR_PROT_FASC_FAILED = 58007;
//        const int ERR_LISTA_MOTIVI_ANNULLAMENTO_FAILED = 58008;
//        const int ERR_ANNULLA_PROT_FAILED = 58009;
//        const int ERR_CREA_FASC_FAILED = 58010;
//        const int ERR_CAMBIA_FASC_FAILED = 58011;
//        const int ERR_LISTA_FASCICOLI_FAILED = 58012;
//        const int ERR_METTI_ALLA_FIRMA = 58013;
//        const int ERR_CREA_COPIE = 58014;*/

//        ILog _log = LogManager.GetLogger(typeof(CWSSigeproProtocollazione));

//        public CWSSigeproProtocollazione()
//        {
//            //CODEGEN: chiamata richiesta da Progettazione servizi Web ASP.NET.
//            InitializeComponent();
//        }

//        #region Codice generato da Progettazione componenti

//        //Richiesto da Progettazione servizi Web 
//        private IContainer components = null;

//        /// <summary>
//        /// Metodo necessario per il supporto della finestra di progettazione. Non modificare
//        /// il contenuto del metodo con l'editor di codice.
//        /// </summary>
//        private void InitializeComponent()
//        {
//        }

//        /// <summary>
//        /// Pulire le risorse in uso.
//        /// </summary>
//        protected override void Dispose(bool disposing)
//        {
//            if (disposing && components != null)
//            {
//                components.Dispose();
//            }
//            base.Dispose(disposing);
//        }

//        #endregion

//        [WebMethod(Description = "Metodo usato per creare una copia di un protocollo", EnableSession = false)]
//        public DatiProtocolloRes CreaCopie(string token, string codiceIstanza, string codiceAmministrazione)
//        {
//            _log.DebugFormat("Inizio funzionalità CreaCopie, Token: {0}, Codice Istanza: {1}, Codice Amministrazione: {2}", token, codiceIstanza, codiceAmministrazione);

//            try
//            {
//                var authInfo = this.CheckToken(token);
//                var software = this.GetSoftware(authInfo, codiceIstanza);
//                var codiceComune = this.GetCodiceComune(authInfo, codiceIstanza);

//                using(var mgr = new ProtocolloMgr(authInfo, software, codiceComune))
//                    return mgr.CreaCopie(codiceAmministrazione);

//            }
//            catch (Exception ex)
//            {
//                _log.ErrorFormat("ERRORE GENERATO DURANTE IL CREA COPIE, {0}", ex.ToString());
//                return new DatiProtocolloRes(ex);
//            }
//        }

//        [WebMethod(Description = "Metodo usato per mettere alla firma un documento dalla maschera", EnableSession = false)]
//        public DatiProtocolloRes MettiAllaFirmaXml(string token, string codiceMovimento, Dati file)
//        {

//            _log.DebugFormat("Inizio funzionalità MettiAllaFirmaXml, token: {0}, Codice Movimento: {1}", token, codiceMovimento);
            
//            try
//            {
//                var authInfo = this.CheckToken(token);

//                var software = this.GetSoftware(authInfo, "", codiceMovimento);
//                var codiceComune = this.GetSoftware(authInfo, "", codiceMovimento);

//                using(var mgr = new ProtocolloMgr(authInfo, software, codiceComune, "", codiceMovimento))
//                    return mgr.MettiAllaFirma(file);

//            }
//            catch (Exception ex)
//            {
//                _log.ErrorFormat("ERRORE GENERATO DURANTE LA MESSA ALLA FIRMA, {0}", ex.ToString());
//                return new DatiProtocolloRes(ex);
//            }
//        }

//        [WebMethod(Description = "Metodo usato per effettuare una protocollazione generica", EnableSession = false)]
//        public DatiProtocolloRes ProtocollazioneXml(string token, string software, Dati file, string codiceComune)
//        {
//            try
//            {
//                return Protocollazione(token, software, codiceComune, (int)Source.PROT_IST_MOV_AUT_BO, "", "", file);
//            }
//            catch (Exception ex)
//            {
//                _log.ErrorFormat("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE GENERICA, {0}", ex.ToString());
//                return new DatiProtocolloRes(ex);
//            }
//        }

//        [WebMethod(Description = "Metodo usato per effettuare la protocollazione di una istanza senza passare dalla maschera", EnableSession = false)]
//        public DatiProtocolloRes ProtocollazioneIstanza(string token, string codiceIstanza, int source)
//        {
//            try
//            {
//                var authInfo = this.CheckToken(token);
//                var software = this.GetSoftware(authInfo, codiceIstanza);
//                var codiceComune = this.GetCodiceComune(authInfo, codiceIstanza);

//                return this.Protocollazione(token, software, codiceComune, source, codiceIstanza, "", null, authInfo);
//            }
//            catch (Exception ex)
//            {
//                _log.ErrorFormat("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE ISTANZA AUTOMATICA (SENZA PASSARE DALLA MASCHERA), {0}", ex.ToString());
//                return new DatiProtocolloRes(ex);
//            }
//        }

//        [WebMethod(Description = "Metodo usato per effettuare la protocollazione di una istanza dalla maschera", EnableSession = false)]
//        public DatiProtocolloRes ProtocollazioneIstanzaXml(string token, string codiceIstanza, Dati file)
//        {
//            try
//            {
//                var authInfo = this.CheckToken(token);
//                var software = this.GetSoftware(authInfo, codiceIstanza);
//                var codiceComune = this.GetCodiceComune(authInfo, codiceIstanza);

//                return this.Protocollazione(token, software, codiceComune,(int)Source.PROT_IST_MOV_AUT_BO, codiceIstanza, "", file, authInfo);
//            }
//            catch (Exception ex)
//            {
//                _log.ErrorFormat("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE ISTANZA DALLA MASCHERA, {0}", ex.ToString());
//                return new DatiProtocolloRes(ex);
//            }
//        }

//        [WebMethod(Description = "Metodo usato per effettuare la protocollazione di un movimento senza passare dalla maschera", EnableSession = false)]
//        public DatiProtocolloRes ProtocollazioneMovimento(string token, string codiceMovimento)
//        {
//            try
//            {
//                var authInfo = this.CheckToken(token);
//                var software = this.GetSoftware(authInfo, "", codiceMovimento);
//                var codiceComune = this.GetCodiceComune(authInfo, "", codiceMovimento);

//                return Protocollazione(token, software, codiceComune, (int)Source.PROT_IST_MOV_AUT_BO, "", codiceMovimento, null, authInfo);
//            }
//            catch (Exception ex)
//            {
//                _log.ErrorFormat("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE DI UN MOVIMENTO SENZA PASSARE DALLA MASCHERA, {0}", ex.ToString());
//                return new DatiProtocolloRes(ex);
//            }
//        }

//        [WebMethod(Description = "Metodo usato per effettuare la protocollazione di un movimento dalla maschera", EnableSession = false)]
//        public DatiProtocolloRes ProtocollazioneMovimentoXml(string token, string codiceMovimento, Dati file)
//        {
//            try
//            {
//                var authInfo = this.CheckToken(token);
//                var software = this.GetSoftware(authInfo, "", codiceMovimento);
//                var codiceComune = this.GetCodiceComune(authInfo, "", codiceMovimento);

//                return Protocollazione(token, software, codiceComune, (int)Source.PROT_IST_MOV_AUT_BO, "", codiceMovimento, file, authInfo);
//            }
//            catch (Exception ex)
//            {
//                _log.ErrorFormat("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE DI UN MOVIMENTO DALLA MASCHERA, {0}", ex.ToString());
//                return new DatiProtocolloRes(ex);
//            }
//        }

//        [WebMethod(Description = "Metodo usato per ottenere la lista dei tipi documento", EnableSession = false)]
//        public ListaTipiDocumento GetTipiDocumento(string token, string software, string codiceComune)
//        {
            
//            _log.DebugFormat("Inizio funzionalità GetTipiDocumento, Token: {0}, Software: {1}, Codice Comune: {2}", token, software, codiceComune);
            
//            try
//            {
//                var authInfo = this.CheckToken(token);
    
//                using(var mgr = new ProtocolloMgr(authInfo, software, codiceComune))
//                    return mgr.ListaTipiDocumento();
//            }
//            catch (Exception ex)
//            {
//                _log.ErrorFormat("ERRORE GENERATO DURANTE IL RECUPERO DEI TIPI DOCUMENTO, {0}", ex.ToString());
//                return new ListaTipiDocumento(ex);
//            }
//        }

//        [WebMethod(Description = "Metodo usato per ottenere la lista delle classifiche", EnableSession = false)]
//        public ListaTipiClassifica GetClassifiche(string token, string software, string codiceComune)
//        {
//            _log.DebugFormat("Inizio funzionalità GetClassifiche, Token: {0}, Software: {1}, Codice Comune: {2}", token, software, codiceComune);

//            try
//            {
//                var authInfo = this.CheckToken(token);
    
//                using(var mgr = new ProtocolloMgr(authInfo, software, codiceComune))
//                    return mgr.ListaClassifiche();
//            }
//            catch (Exception ex)
//            {
//                _log.ErrorFormat("ERRORE GENERATO DURANTE IL RECUPERO DELLE CLASSIFICHE, {0}", ex.ToString());
//                return new ListaTipiClassifica(ex);
//            }
//        }

//        [WebMethod(Description = "Metodo usato per rileggere un documento protocollato", EnableSession = false)]
//        public DatiProtocolloLetto LeggiProtocollo(string token, string idProtocollo, string annoProtocollo, string numProtocollo, string software, string codiceComune)
//        {
//            _log.DebugFormat("Inizio funzionalità LeggiProtocollo, Token: {0}, Id Protocollo: {1}, Anno Protocollo: {2}, Numero Protocollo: {3}, Software: {4}, Codice Comune: {5}", token, idProtocollo, annoProtocollo, numProtocollo, software, codiceComune);

//            try
//            {
//                var authInfo = this.CheckToken(token);
    
//                using(var mgr = new ProtocolloMgr(authInfo, software, codiceComune))
//                    return mgr.LeggiProtocollo(idProtocollo, annoProtocollo, numProtocollo);
//            }
//            catch (Exception ex)
//            {
//                _log.ErrorFormat("ERRORE GENERATO DURANTE LA LETTURA DEL PROTOCOLLO, {0}", ex.ToString());
//                return new DatiProtocolloLetto(ex);
//            }
//        }

//        [WebMethod(Description = "Metodo usato per leggere i file allegati al protocollo", EnableSession = false)]
//        public AllOut LeggiAllegato(string token, string idBase, string software, string codiceComune)
//        {
//            _log.DebugFormat("Inizio funzionalità LeggiAllegato, token: {0}, Id Base: {1}, Software: {2}, Codice Comune", token, idBase, software, codiceComune);

//            try
//            {
//                var authInfo = this.CheckToken(token);
//                using (var mgr = new ProtocolloMgr(authInfo, software, codiceComune))
//                {
//                    string[] arrDatiProt = idBase.Split('|');

//                    string idProtocollo = arrDatiProt[0];
//                    string numProtocollo = arrDatiProt[1];
//                    string annoProtocollo = arrDatiProt[2];
//                    string idAllegato = arrDatiProt[3];

//                    return mgr.LeggiAllegato(idProtocollo, numProtocollo, annoProtocollo, idAllegato);
//                }
//            }
//            catch (Exception ex)
//            {
//                _log.ErrorFormat("ERRORE GENERATO DURANTE LA LETTURA DI UN ALLEGATO, {0}", ex.ToString());
//                return new AllOut(ex);
//            }
//        }

//        [WebMethod(Description = "Metodo usato per effettuare la stampa di etichette (Protocollo SIDOP)", EnableSession = false)]
//        public DatiEtichette StampaEtichette(string token, string idProtocollo, string numeroProtocollo, DateTime? dataProtocollo, int numeroCopie, string stampante, string software, string codiceComune)
//        {
//            _log.DebugFormat("Inizio funzionalità StampaEtichette, Token: {0}, Id Protocollo: {1}, Numero Protocollo: {2}, Data Protocollo: {3}, Numero Copie: {4}, Stampante: {5}, Software: {6}, Codice Comune: {7}", token, idProtocollo, numeroProtocollo, dataProtocollo.HasValue ? dataProtocollo.Value.ToString("dd/MM/yyyy") : "", numeroCopie.ToString(), stampante, software, codiceComune);
            
//            try
//            {
//                var authInfo = this.CheckToken(token);

//                using (var mgr = new ProtocolloMgr(authInfo, software, codiceComune, "", ""))
//                    return mgr.StampaEtichette(idProtocollo, dataProtocollo, numeroProtocollo, numeroCopie, stampante);
//            }
//            catch (Exception ex)
//            {
//                _log.ErrorFormat("ERRORE GENERATO DURANTE LA STAMPA DELLE ETICHETTE, {0}", ex.ToString());
//                return new DatiEtichette(ex);
//            }
//        }

//        [WebMethod(Description = "Metodo usato per ottenere la lista dei motivi di annullamento", EnableSession = false)]
//        public ListaMotiviAnnullamento GetMotiviAnnullamento(string token, string software, string codiceComune)
//        {
//            _log.DebugFormat("Inizio funzionalità GetMotiviAnnullamento, Token: {0}, Software: {1}, Codice Comune: {2}", token, software, codiceComune);

//            try
//            {
//                var authInfo = this.CheckToken(token);

//                using(var mgr = new ProtocolloMgr(authInfo, software, codiceComune))
//                    return mgr.ListaMotivoAnnullamento();
//            }
//            catch (Exception ex)
//            {
//                _log.ErrorFormat("ERRORE DURANTE IL RECUPERO DEI MOTIVI DI ANNULLAMENTO DI UN PROTOCOLLO, {0}", ex.ToString());
//                return new ListaMotiviAnnullamento(ex);
//            }
//        }

//        [WebMethod(Description = "Metodo usato per annullare un protocollo", EnableSession = false)]
//        public void AnnullaProtocollo(string token, string idProtocollo, string annoProtocollo, string numeroProtocollo, string motivoAnnullamento, string noteAnnullamento, string software, string codiceComune)
//        {

//            _log.DebugFormat("Inizio funzionalità AnnullaProtocollo, Token: {0}, Id Protocollo: {1}, Anno Protocollo: {2}, Numero Protocollo: {3}, Motivo Annullamento: {4}, Note Annullamento: {5}, Software: {6}, Codice Comune: {7}", token, idProtocollo, annoProtocollo, numeroProtocollo, motivoAnnullamento, noteAnnullamento, software, codiceComune);

//            try
//            {
//                var authInfo = CheckToken(token);
                
//                using(var mgr = new ProtocolloMgr(authInfo, software, codiceComune))
//                    mgr.AnnullaProtocollo(idProtocollo, annoProtocollo, numeroProtocollo, motivoAnnullamento, noteAnnullamento);
//            }
//            catch (Exception ex)
//            {
//                _log.ErrorFormat("ERRORE GENERATO DURANTE L'ANNULLAMENTO DEL PROTOCOLLO, {0}", ex.ToString());
//                throw;
//            }
            
//        }

//        [WebMethod(Description = "Metodo usato per stabilire se un protocollo è annullato", EnableSession = false)]
//        public DatiProtocolloAnnullato IsAnnullato(string token, string idProtocollo, string annoProtocollo, string numeroProtocollo, string software, string codiceComune)
//        {
//            _log.DebugFormat("Inizio funzionalità EAnnullato, Token: {0}, Id Protocollo: {1}, Anno Protocollo: {2}, Numero Protocollo: {3}, Software: {4}", token, idProtocollo, annoProtocollo, numeroProtocollo, software);

//            try
//            {
//                var authInfo = this.CheckToken(token);
                
//                using(var mgr = new ProtocolloMgr(authInfo, software, codiceComune))
//                    return mgr.EAnnullato(idProtocollo, annoProtocollo, numeroProtocollo);
//            }
//            catch (Exception ex)
//            {
//                _log.ErrorFormat("ERRORE GENERATO DURANTE LA VERIFICA SE UN PROTOCOLLO E' ANNULLATO, {0}", ex.ToString());
//                return new DatiProtocolloAnnullato(ex);
//            }
//        }

//        [WebMethod(Description = "Metodo usato per ottenere la lista dei fascicoli associati ad una istanza", EnableSession = false)]
//        public ListaFascicoli GetFascicoli(string token, string codiceIstanza)
//        {
//            _log.DebugFormat("Inizio funzionalità GetFascicoli, token: {0}, Codice Istanza: {1}", token, codiceIstanza);
            
//            try
//            {
//                var authInfo = this.CheckToken(token);
//                var software = this.GetSoftware(authInfo, codiceIstanza);
//                var codiceComune = this.GetCodiceComune(authInfo, codiceIstanza);

//                using(var mgr = new ProtocolloMgr(authInfo, software, codiceComune, codiceIstanza))
//                    return mgr.ListaFascicoli();
//            }
//            catch (Exception ex)
//            {
//                _log.ErrorFormat("ERRORE GENERATO DURANTE IL RECUPERO DELLA LISTA FASCICOLI ASSOCIATI AD UN'ISTANZA, {0}", ex.ToString());
//                return new ListaFascicoli(ex);
//            }
//        }

//        [WebMethod(Description = "Metodo usato per stabilire se un protocollo è fascicolato", EnableSession = false)]
//        public DatiProtocolloFascicolato IsFascicolato(string token, string idProtocollo, string annoProtocollo, string numeroProtocollo, string software, string codiceComune)
//        {
//            _log.DebugFormat("Inizio funzionalità EFascicolato, Token: {0}, Id Protocollo: {1}, Anno Protocollo: {2} Numero Protocollo: {3}, Software: {4}, Codice Comune: {5}", token, idProtocollo, annoProtocollo, numeroProtocollo, software, codiceComune);

//            try
//            {
//                var authInfo = this.CheckToken(token);
//                using(var mgr = new ProtocolloMgr(authInfo, software, codiceComune))
//                    return mgr.EFascicolato(idProtocollo, annoProtocollo, numeroProtocollo);
//            }
//            catch (Exception ex)
//            {
//                _log.ErrorFormat("ERRORE GENERATO DURANTE LA VERIFICA SE UN PROTOCOLLO E' FASCICOLATO, {0}", ex.ToString());
//                return new DatiProtocolloFascicolato(ex);
//            }
//        }

//        [WebMethod(Description = "Metodo usato per effettuare una fascicolazione generica", EnableSession = false)]
//        public DatiFascicolo FascicolazioneXml(string token, string software, string file, string codiceComune)
//        {
//            try
//            {
//                var authInfo = this.CheckToken(token);
//                var mgr = new ProtocolloMgr(authInfo, software, codiceComune);
//                return Fascicolazione(token, software, codiceComune, (int)SourceFascicolazione.FASC_IST_MOV_AUT_BO, "", "", file, authInfo);
                
//            }
//            catch (Exception ex)
//            {
//                _log.ErrorFormat("ERRORE DURANTE LA FASCICOLAZIONE GENERICA, {0}", ex.ToString());
//                return new DatiFascicolo(ex);
//            }
//        }

//        [WebMethod(Description = "Metodo usato per effettuare la fascicolazione di una istanza senza passare dalla maschera", EnableSession = false)]
//        public DatiFascicolo FascicolazioneIstanza(string token, string codiceIstanza, int source)
//        {
//            try
//            {
//                var authInfo = this.CheckToken(token);
//                var software = this.GetSoftware(authInfo, codiceIstanza);
//                var codiceComune = this.GetCodiceComune(authInfo, codiceIstanza);

//                var mgr = new ProtocolloMgr(authInfo, software, codiceComune, codiceIstanza);
                
//                return this.Fascicolazione(token, software, codiceComune, source, codiceIstanza, "", "", authInfo);
//            }
//            catch (Exception ex)
//            {
//                _log.ErrorFormat("ERRORE GENERATO DURANTE LA FASCICOLAZIONE DI UN'ISTANZA SENZA PASSARE PER LA MASCHERA, {0}", ex.ToString());
//                return new DatiFascicolo(ex);
//            }
            
//        }

//        [WebMethod(Description = "Metodo usato per effettuare la fascicolazione di una istanza dalla maschera", EnableSession = false)]
//        public DatiFascicolo FascicolazioneIstanzaXml(string token, string codiceIstanza, string file)
//        {
//            try
//            {
//                var authInfo = this.CheckToken(token);
//                var software = this.GetSoftware(authInfo, codiceIstanza);
//                var codiceComune = this.GetCodiceComune(authInfo, codiceIstanza);

//                var mgr = new ProtocolloMgr(authInfo, software, codiceComune, codiceIstanza);

//                return this.Fascicolazione(token, software, codiceComune, (int)SourceFascicolazione.FASC_IST_MOV_AUT_BO, codiceIstanza, "", "", authInfo);
//            }
//            catch(Exception ex)
//            {
//                _log.ErrorFormat("ERRORE GENERATO DURANTE LA FASCICOLAZIONE DI UN'ISTANZA DALLA MASCHERA, {0}", ex.ToString());
//                return new DatiFascicolo(ex);
//            }
//        }

//        [WebMethod(Description = "Metodo usato per effettuare la fascicolazione di un movimento senza passare dalla maschera", EnableSession = false)]
//        public DatiFascicolo FascicolazioneMovimento(string token, string codiceMovimento)
//        {
//            try
//            {
//                var authInfo = this.CheckToken(token);
//                var software = this.GetSoftware(authInfo, "", codiceMovimento);
//                var codiceComune = this.GetCodiceComune(authInfo, "", codiceMovimento);

//                var mgr = new ProtocolloMgr(authInfo, software, codiceComune, "", codiceMovimento);
//                return Fascicolazione(token, software, codiceComune, (int)SourceFascicolazione.FASC_IST_MOV_AUT_BO, "", codiceMovimento, "", authInfo);
//            }
//            catch (Exception ex)
//            {
//                _log.ErrorFormat("ERRORE GENERATO DURANTE LA FASCICOLAZIONE DI UN MOVIMENTO SENZA PASSARE DALLA MASCHERA, {0}", ex.ToString());
//                return new DatiFascicolo(ex);
//            }
//        }

//        [WebMethod(Description = "Metodo usato per effettuare la fascicolazione di un movimento dalla maschera", EnableSession = false)]
//        public DatiFascicolo FascicolazioneMovimentoXml(string token, string codiceMovimento, string file)
//        {
//            try
//            {
//                var authInfo = this.CheckToken(token);
//                var software = this.GetSoftware(authInfo, "", codiceMovimento);
//                var codiceComune = this.GetCodiceComune(authInfo, "", codiceMovimento);

//                var mgr = new ProtocolloMgr(authInfo, software, codiceComune, "", codiceMovimento);
//                return Fascicolazione(token, software, codiceComune, (int)SourceFascicolazione.FASC_IST_MOV_AUT_BO, "", codiceMovimento, file, authInfo);
//            }
//            catch (Exception ex)
//            {
//                _log.ErrorFormat("ERRORE GENERATO DURANTE LA FASCICOLAZIONE DI UN MOVIMENTO DALLA MASCHERA, {0}", ex.ToString());
//                return new DatiFascicolo(ex);
//            }

            
//        }
        
//        private DatiFascicolo Fascicolazione(string token, string software, string codiceComune, int source, string codiceIstanza = "", string codiceMovimento = "", string file = "", AuthenticationInfo authInfo = null)
//        {
//            _log.DebugFormat("Inizio funzionalità Fascicolazione, Token: {0}, Codice Istanza: {1}, Codice Movimento: {2}, Software: {3}", token, codiceIstanza, codiceMovimento, software);

//            try
//            {
//                if(authInfo == null)
//                    authInfo = this.CheckToken(token);
                
//                using(var mgr = new ProtocolloMgr(authInfo, software, codiceComune, codiceIstanza, codiceMovimento))
//                    return mgr.Fascicola(file, source);
//            }
//            catch (Exception ex)
//            {
//                _log.ErrorFormat("ERRORE GENERATO DURANTE LA FASCICOLAZIONE, {0}", ex.ToString());
//                return new DatiFascicolo(ex);
//            }
//        }

//        [WebMethod(Description = "Metodo usato per cambiare fascicolo ed assegnarvi il protocollo", EnableSession = false)]
//        public DatiFascicolo CambiaFascicoloIstanzaXml(string token, string codiceIstanza, DatiFasc datiFasc, string codiceComune)
//        {
//            _log.DebugFormat("Inizio funzionalità CambiaFascicoloIstanzaXml, Token: {0}, Codice Istanza: {1}, Codice Comune: {2}", token, codiceIstanza, codiceComune);

//            try
//            {
//                var authInfo = CheckToken(token);

//                var software = GetSoftware(authInfo, codiceIstanza);
//                var mgr = new ProtocolloMgr(authInfo, software, codiceComune, codiceIstanza);

//                return mgr.CambiaFascicolo(datiFasc);
//            }
//            catch (Exception ex)
//            {
//                _log.ErrorFormat("ERRORE GENERATO DURANTE LA FUNZIONALITA' CAMBIA FASCICOLO PER ASSEGNARGLI IL PROTOCOLLO, {0}", ex.ToString());
//                return new DatiFascicolo(ex);
//            }
//        }

//        private DatiProtocolloRes Protocollazione(string token, string software, string codiceComune, int source, string codiceIstanza = "", string codiceMovimento = "", Dati dati = null, AuthenticationInfo authInfo = null)
//        {
//            _log.DebugFormat("Inizio funzionalità Protocollazione, Token: {0}, Codice Istanza: {1}, codice movimento: {2}, software: {3}, Codice Comune: {4}", token, codiceIstanza, codiceMovimento, software, codiceComune);

//            try
//            {
//                if(authInfo == null)
//                    authInfo = this.CheckToken(token);

//                var mgr = new ProtocolloMgr(authInfo, software, codiceComune, codiceIstanza, codiceMovimento);

//                var provenienza = authInfo.CodiceResponsabile.HasValue ? TipoProvenienza.BACKOFFICE : TipoProvenienza.ONLINE;

//                return mgr.Protocollazione(provenienza, dati, source);
//            }
//            catch (Exception ex)
//            {
//                _log.ErrorFormat("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE, {0}", ex.ToString());
//                throw;
//            }
//        }

//        private Istanze GetIstanza(AuthenticationInfo authInfo, DataBase db, string codiceIstanza)
//        {
//            if (String.IsNullOrEmpty(codiceIstanza))
//                return null;

//            var mgr = new IstanzeMgr(db);
//            var istanza = mgr.GetById(authInfo.IdComune, Convert.ToInt32(codiceIstanza));

//            return istanza;
//        }

//        private Init.SIGePro.Data.Movimenti GetMovimento(AuthenticationInfo authInfo, DataBase db, string codiceMovimento)
//        {
//            if (String.IsNullOrEmpty(codiceMovimento))
//                return null;

//            var mgr = new MovimentiMgr(db);
//            var movimento = mgr.GetById(authInfo.IdComune, Convert.ToInt32(codiceMovimento));

//            return movimento;
//        }

//        private Istanze GetIstanzaFromMovimento(AuthenticationInfo authInfo, DataBase db, string codiceMovimento)
//        {
//            var movimento = GetMovimento(authInfo, db, codiceMovimento);
//            if (movimento == null)
//                return null;

//            return GetIstanza(authInfo, db, movimento.CODICEISTANZA);
//        }

//        private Istanze GetIstanza(AuthenticationInfo authInfo, string codiceIstanza = "", string codiceMovimento = "")
//        {
//            using (var db = authInfo.CreateDatabase())
//            {
//                var istanza = GetIstanza(authInfo, db, codiceIstanza);
//                if (istanza == null)
//                    istanza = GetIstanzaFromMovimento(authInfo, db, codiceMovimento);

//                return istanza;
//            }
//        }

//        private string GetCodiceComune(AuthenticationInfo authInfo, string codiceIstanza = "", string codiceMovimento = "")
//        {
//            var istanza = GetIstanza(authInfo, codiceIstanza, codiceMovimento);

//            if (istanza == null)
//                throw new Exception("ISTANZA NON VALORIZZATA");

//            if (String.IsNullOrEmpty(istanza.CODICECOMUNE))
//                throw new Exception("CODICE COMUNE NON VALORIZZATO");

//            return istanza.CODICECOMUNE;
//        }

//        private string GetSoftware(AuthenticationInfo authInfo, string codiceIstanza = "", string codiceMovimento = "")
//        {
//            var istanza = GetIstanza(authInfo, codiceIstanza, codiceMovimento);

//            if (istanza == null)
//                throw new Exception("ISTANZA NON VALORIZZATA");

//            if (String.IsNullOrEmpty(istanza.SOFTWARE))
//                throw new Exception("SOFTWARE NON VALORIZZATO");

//            return istanza.SOFTWARE;
//        }

//    }
//}
