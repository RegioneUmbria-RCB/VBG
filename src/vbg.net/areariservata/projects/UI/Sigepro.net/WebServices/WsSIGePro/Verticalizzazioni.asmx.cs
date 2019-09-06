
using System;
using System.Collections;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Authentication;
using Init.SIGePro.Verticalizzazioni;

namespace SIGePro.Net.WebServices.WsSIGePro
{
    /// <summary>
    /// Descrizione di riepilogo per Verticalizzazioni.
    /// </summary>
    [WebService(Namespace = "http://init.sigepro.it")]
    public class Verticalizzazioni : System.Web.Services.WebService
    {
        public Verticalizzazioni()
        {
            //CODEGEN: chiamata richiesta da Progettazione servizi Web ASP.NET.
            InitializeComponent();
        }

        #region Codice generato da Progettazione componenti

        //Richiesto da Progettazione servizi Web 
        private IContainer components = null;

        /// <summary>
        /// Metodo necessario per il supporto della finestra di progettazione. Non modificare
        /// il contenuto del metodo con l'editor di codice.
        /// </summary>
        private void InitializeComponent()
        {
        }

        /// <summary>
        /// Pulire le risorse in uso.
        /// </summary>
        protected override void Dispose(bool disposing)
        {
            if (disposing && components != null)
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #endregion


        #region Gestione della verticalizzazione WSANAGRAFE_PIACENZA
        [WebMethod]
        public bool IsVerticalizzazioneWsanagrafePiacenzaAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneWsanagrafePiacenza vert = new VerticalizzazioneWsanagrafePiacenza(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneWsanagrafePiacenza GetVerticalizzazioneWsanagrafePiacenza(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneWsanagrafePiacenza(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione WSANAGRAFE_PARIX
        [WebMethod]
        public bool IsVerticalizzazioneWsanagrafeParixAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneWsanagrafeParix vert = new VerticalizzazioneWsanagrafeParix(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneWsanagrafeParix GetVerticalizzazioneWsanagrafeParix(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneWsanagrafeParix(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione FILESYSTEM_PROTOCOLLO
        [WebMethod]
        public bool IsVerticalizzazioneFilesystemProtocolloAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneFilesystemProtocollo vert = new VerticalizzazioneFilesystemProtocollo(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneFilesystemProtocollo GetVerticalizzazioneFilesystemProtocollo(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneFilesystemProtocollo(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione TIPO_INSTALLAZIONE
        [WebMethod]
        public bool IsVerticalizzazioneTipoInstallazioneAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneTipoInstallazione vert = new VerticalizzazioneTipoInstallazione(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneTipoInstallazione GetVerticalizzazioneTipoInstallazione(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneTipoInstallazione(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione WSANAGRAFE_TERNI
        [WebMethod]
        public bool IsVerticalizzazioneWsanagrafeTerniAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneWsanagrafeTerni vert = new VerticalizzazioneWsanagrafeTerni(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneWsanagrafeTerni GetVerticalizzazioneWsanagrafeTerni(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneWsanagrafeTerni(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione FILESYSTEM_CMIS
        [WebMethod]
        public bool IsVerticalizzazioneFilesystemCmisAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneFilesystemCmis vert = new VerticalizzazioneFilesystemCmis(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneFilesystemCmis GetVerticalizzazioneFilesystemCmis(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneFilesystemCmis(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione NLA-RICEZIONE-PEC
        [WebMethod]
        public bool IsVerticalizzazionenlanicezionepecattiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneNlaricezionepec vert = new VerticalizzazioneNlaricezionepec(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneNlaricezionepec GetVerticalizzazioneNlaricezionepec(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneNlaricezionepec(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione CART
        [WebMethod]
        public bool IsVerticalizzazioneCartAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneCart vert = new VerticalizzazioneCart(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneCart GetVerticalizzazioneCart(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneCart(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione ALLINEAMENTO_STRADARIO_ATTIVO
        [WebMethod]
        public bool IsVerticalizzazioneAllineamentoStradarioAttivoAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneAllineamentoStradarioAttivo vert = new VerticalizzazioneAllineamentoStradarioAttivo(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneAllineamentoStradarioAttivo GetVerticalizzazioneAllineamentoStradarioAttivo(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneAllineamentoStradarioAttivo(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione ALLINEAMENTO_STRADARIO_7DBTL
        [WebMethod]
        public bool IsVerticalizzazioneAllineamentoStradario7dbtlAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneAllineamentoStradario7dbtl vert = new VerticalizzazioneAllineamentoStradario7dbtl(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneAllineamentoStradario7dbtl GetVerticalizzazioneAllineamentoStradario7dbtl(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneAllineamentoStradario7dbtl(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione STRADARIO_RAVENNA
        [WebMethod]
        public bool IsVerticalizzazioneStradarioRavennaAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneStradarioRavenna vert = new VerticalizzazioneStradarioRavenna(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneStradarioRavenna GetVerticalizzazioneStradarioRavenna(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneStradarioRavenna(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione STAMPE_ONERI
        [WebMethod]
        public bool IsVerticalizzazioneStampeOneriAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneStampeOneri vert = new VerticalizzazioneStampeOneri(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneStampeOneri GetVerticalizzazioneStampeOneri(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneStampeOneri(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione STAT_PRATICHE_FILTRO_ESTESO
        [WebMethod]
        public bool IsVerticalizzazioneStatPraticheFiltroEstesoAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneStatPraticheFiltroEsteso vert = new VerticalizzazioneStatPraticheFiltroEsteso(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneStatPraticheFiltroEsteso GetVerticalizzazioneStatPraticheFiltroEsteso(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneStatPraticheFiltroEsteso(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione ANAGRAFE_PG_TO_PF
        [WebMethod]
        public bool IsVerticalizzazioneAnagrafePgToPfAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneAnagrafePgToPf vert = new VerticalizzazioneAnagrafePgToPf(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneAnagrafePgToPf GetVerticalizzazioneAnagrafePgToPf(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneAnagrafePgToPf(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione TASTI_ISTANZA_SOPRA
        [WebMethod]
        public bool IsVerticalizzazioneTastiIstanzaSopraAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneTastiIstanzaSopra vert = new VerticalizzazioneTastiIstanzaSopra(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneTastiIstanzaSopra GetVerticalizzazioneTastiIstanzaSopra(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneTastiIstanzaSopra(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione DISABILITA_ELAB_ALERT_JSCRIPT
        [WebMethod]
        public bool IsVerticalizzazioneDisabilitaElabAlertJscriptAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneDisabilitaElabAlertJscript vert = new VerticalizzazioneDisabilitaElabAlertJscript(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneDisabilitaElabAlertJscript GetVerticalizzazioneDisabilitaElabAlertJscript(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneDisabilitaElabAlertJscript(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione AUTENTICAZIONE_LDAP
        [WebMethod]
        public bool IsVerticalizzazioneAutenticazioneLdapAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneAutenticazioneLdap vert = new VerticalizzazioneAutenticazioneLdap(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneAutenticazioneLdap GetVerticalizzazioneAutenticazioneLdap(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneAutenticazioneLdap(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione C4RETESUAP
        [WebMethod]
        public bool IsVerticalizzazioneC4retesuapAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneC4retesuap vert = new VerticalizzazioneC4retesuap(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneC4retesuap GetVerticalizzazioneC4retesuap(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneC4retesuap(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione GIS_POMEZIA
        [WebMethod]
        public bool IsVerticalizzazioneGisPomeziaAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneGisPomezia vert = new VerticalizzazioneGisPomezia(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneGisPomezia GetVerticalizzazioneGisPomezia(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneGisPomezia(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione CENTER_PAGE
        [WebMethod]
        public bool IsVerticalizzazioneCenterPageAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneCenterPage vert = new VerticalizzazioneCenterPage(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneCenterPage GetVerticalizzazioneCenterPage(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneCenterPage(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione DOC_AREA
        [WebMethod]
        public bool IsVerticalizzazioneDocAreaAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneDocArea vert = new VerticalizzazioneDocArea(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneDocArea GetVerticalizzazioneDocArea(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneDocArea(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione IMPORT_CCIAA
        [WebMethod]
        public bool IsVerticalizzazioneImportCciaaAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneImportCciaa vert = new VerticalizzazioneImportCciaa(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneImportCciaa GetVerticalizzazioneImportCciaa(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneImportCciaa(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione WSANAGRAFE
        [WebMethod]
        public bool IsVerticalizzazioneWsanagrafeAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneWsanagrafe vert = new VerticalizzazioneWsanagrafe(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneWsanagrafe GetVerticalizzazioneWsanagrafe(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneWsanagrafe(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione SMTP_MAILER
        [WebMethod]
        public bool IsVerticalizzazioneSmtpMailerAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneSmtpMailer vert = new VerticalizzazioneSmtpMailer(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneSmtpMailer GetVerticalizzazioneSmtpMailer(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneSmtpMailer(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione MOSTRA_AUTOR_ONERI_LISTA
        [WebMethod]
        public bool IsVerticalizzazioneMostraAutorOneriListaAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneMostraAutorOneriLista vert = new VerticalizzazioneMostraAutorOneriLista(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneMostraAutorOneriLista GetVerticalizzazioneMostraAutorOneriLista(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneMostraAutorOneriLista(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione SIT_CARTECH
        [WebMethod]
        public bool IsVerticalizzazioneSitCartechAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneSitCartech vert = new VerticalizzazioneSitCartech(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneSitCartech GetVerticalizzazioneSitCartech(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneSitCartech(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione ANAGRAFELEGALERAPPRESENTANTE
        [WebMethod]
        public bool IsVerticalizzazioneAnagrafelegalerappresentanteAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneAnagrafelegalerappresentante vert = new VerticalizzazioneAnagrafelegalerappresentante(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneAnagrafelegalerappresentante GetVerticalizzazioneAnagrafelegalerappresentante(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneAnagrafelegalerappresentante(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione AREA_RISERVATA
        [WebMethod]
        public bool IsVerticalizzazioneAreaRiservataAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneAreaRiservata vert = new VerticalizzazioneAreaRiservata(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneAreaRiservata GetVerticalizzazioneAreaRiservata(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneAreaRiservata(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione ACQUISIZIONE_TWAIN
        [WebMethod]
        public bool IsVerticalizzazioneAcquisizioneTwainAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneAcquisizioneTwain vert = new VerticalizzazioneAcquisizioneTwain(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneAcquisizioneTwain GetVerticalizzazioneAcquisizioneTwain(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneAcquisizioneTwain(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione GIS_ABITAT
        [WebMethod]
        public bool IsVerticalizzazioneGisAbitatAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneGisAbitat vert = new VerticalizzazioneGisAbitat(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneGisAbitat GetVerticalizzazioneGisAbitat(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneGisAbitat(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione TECNICO_RICHIEDENTE
        [WebMethod]
        public bool IsVerticalizzazioneTecnicoRichiedenteAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneTecnicoRichiedente vert = new VerticalizzazioneTecnicoRichiedente(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneTecnicoRichiedente GetVerticalizzazioneTecnicoRichiedente(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneTecnicoRichiedente(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione I_ATTIVITA
        [WebMethod]
        public bool IsVerticalizzazioneIAttivitaAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneIAttivita vert = new VerticalizzazioneIAttivita(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneIAttivita GetVerticalizzazioneIAttivita(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneIAttivita(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione WS_IMPORTAZIONEISTANZA
        [WebMethod]
        public bool IsVerticalizzazioneWsImportazioneistanzaAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneWsImportazioneistanza vert = new VerticalizzazioneWsImportazioneistanza(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneWsImportazioneistanza GetVerticalizzazioneWsImportazioneistanza(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneWsImportazioneistanza(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione PAGINA_ONERI_PERSONALE
        [WebMethod]
        public bool IsVerticalizzazionePaginaOneriPersonaleAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazionePaginaOneriPersonale vert = new VerticalizzazionePaginaOneriPersonale(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazionePaginaOneriPersonale GetVerticalizzazionePaginaOneriPersonale(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazionePaginaOneriPersonale(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione SIT_ATTIVO
        [WebMethod]
        public bool IsVerticalizzazioneSitAttivoAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneSitAttivo vert = new VerticalizzazioneSitAttivo(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneSitAttivo GetVerticalizzazioneSitAttivo(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneSitAttivo(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione SIT_ESC
        [WebMethod]
        public bool IsVerticalizzazioneSitEscAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneSitEsc vert = new VerticalizzazioneSitEsc(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneSitEsc GetVerticalizzazioneSitEsc(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneSitEsc(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione SIT_CORE
        [WebMethod]
        public bool IsVerticalizzazioneSitCoreAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneSitCore vert = new VerticalizzazioneSitCore(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneSitCore GetVerticalizzazioneSitCore(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneSitCore(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione DIS_ONERIIMPORTOISTRUTTORIA
        [WebMethod]
        public bool IsVerticalizzazioneDisOneriimportoistruttoriaAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneDisOneriimportoistruttoria vert = new VerticalizzazioneDisOneriimportoistruttoria(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneDisOneriimportoistruttoria GetVerticalizzazioneDisOneriimportoistruttoria(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneDisOneriimportoistruttoria(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione VIS_ONERIIMPORTOISTRUTTORIA
        [WebMethod]
        public bool IsVerticalizzazioneVisOneriimportoistruttoriaAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneVisOneriimportoistruttoria vert = new VerticalizzazioneVisOneriimportoistruttoria(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneVisOneriimportoistruttoria GetVerticalizzazioneVisOneriimportoistruttoria(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneVisOneriimportoistruttoria(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione FRONTOFFICEENDBODYSCRIPT
        [WebMethod]
        public bool IsVerticalizzazioneFrontofficeendbodyscriptAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneFrontofficeendbodyscript vert = new VerticalizzazioneFrontofficeendbodyscript(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneFrontofficeendbodyscript GetVerticalizzazioneFrontofficeendbodyscript(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneFrontofficeendbodyscript(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione MODIFICA_INTERVENTO
        [WebMethod]
        public bool IsVerticalizzazioneModificaInterventoAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneModificaIntervento vert = new VerticalizzazioneModificaIntervento(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneModificaIntervento GetVerticalizzazioneModificaIntervento(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneModificaIntervento(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione PROT_ACCESSO_OPERATORI_ATER
        [WebMethod]
        public bool IsVerticalizzazioneProtAccessoOperatoriAterAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneProtAccessoOperatoriAter vert = new VerticalizzazioneProtAccessoOperatoriAter(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneProtAccessoOperatoriAter GetVerticalizzazioneProtAccessoOperatoriAter(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneProtAccessoOperatoriAter(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione PROT_COLLEGA
        [WebMethod]
        public bool IsVerticalizzazioneProtCollegaAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneProtCollega vert = new VerticalizzazioneProtCollega(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneProtCollega GetVerticalizzazioneProtCollega(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneProtCollega(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione PROT_COLLEGA_PROPRI
        [WebMethod]
        public bool IsVerticalizzazioneProtCollegaPropriAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneProtCollegaPropri vert = new VerticalizzazioneProtCollegaPropri(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneProtCollegaPropri GetVerticalizzazioneProtCollegaPropri(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneProtCollegaPropri(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione PROT_DISABILITA_CLASSIF_MULTI
        [WebMethod]
        public bool IsVerticalizzazioneProtDisabilitaClassifMultiAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneProtDisabilitaClassifMulti vert = new VerticalizzazioneProtDisabilitaClassifMulti(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneProtDisabilitaClassifMulti GetVerticalizzazioneProtDisabilitaClassifMulti(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneProtDisabilitaClassifMulti(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione PROT_ASSEGNAZIONI_MULTIPLE
        [WebMethod]
        public bool IsVerticalizzazioneProtAssegnazioniMultipleAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneProtAssegnazioniMultiple vert = new VerticalizzazioneProtAssegnazioniMultiple(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneProtAssegnazioniMultiple GetVerticalizzazioneProtAssegnazioniMultiple(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneProtAssegnazioniMultiple(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione PROT_DIVIDI_ASSEGNAZIONI
        [WebMethod]
        public bool IsVerticalizzazioneProtDividiAssegnazioniAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneProtDividiAssegnazioni vert = new VerticalizzazioneProtDividiAssegnazioni(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneProtDividiAssegnazioni GetVerticalizzazioneProtDividiAssegnazioni(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneProtDividiAssegnazioni(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione PROT_ALBERO_ATER
        [WebMethod]
        public bool IsVerticalizzazioneProtAlberoAterAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneProtAlberoAter vert = new VerticalizzazioneProtAlberoAter(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneProtAlberoAter GetVerticalizzazioneProtAlberoAter(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneProtAlberoAter(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione PROT_CLASS_RESPONSABILI
        [WebMethod]
        public bool IsVerticalizzazioneProtClassResponsabiliAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneProtClassResponsabili vert = new VerticalizzazioneProtClassResponsabili(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneProtClassResponsabili GetVerticalizzazioneProtClassResponsabili(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneProtClassResponsabili(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione LINKSTAMPEAGGIUNTIVE
        [WebMethod]
        public bool IsVerticalizzazioneLinkstampeaggiuntiveAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneLinkstampeaggiuntive vert = new VerticalizzazioneLinkstampeaggiuntive(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneLinkstampeaggiuntive GetVerticalizzazioneLinkstampeaggiuntive(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneLinkstampeaggiuntive(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione HELPBASE
        [WebMethod]
        public bool IsVerticalizzazioneHelpbaseAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneHelpbase vert = new VerticalizzazioneHelpbase(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneHelpbase GetVerticalizzazioneHelpbase(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneHelpbase(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione SIT_CTC
        [WebMethod]
        public bool IsVerticalizzazioneSitCtcAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneSitCtc vert = new VerticalizzazioneSitCtc(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneSitCtc GetVerticalizzazioneSitCtc(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneSitCtc(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione BASEURL
        [WebMethod]
        public bool IsVerticalizzazioneBaseurlAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneBaseurl vert = new VerticalizzazioneBaseurl(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneBaseurl GetVerticalizzazioneBaseurl(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneBaseurl(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione FILESYSTEM
        [WebMethod]
        public bool IsVerticalizzazioneFilesystemAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneFilesystem vert = new VerticalizzazioneFilesystem(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneFilesystem GetVerticalizzazioneFilesystem(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneFilesystem(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione SIT_DEFAULT
        [WebMethod]
        public bool IsVerticalizzazioneSitDefaultAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneSitDefault vert = new VerticalizzazioneSitDefault(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneSitDefault GetVerticalizzazioneSitDefault(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneSitDefault(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione SIT_NAUTILUS
        [WebMethod]
        public bool IsVerticalizzazioneSitNautilusAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneSitNautilus vert = new VerticalizzazioneSitNautilus(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneSitNautilus GetVerticalizzazioneSitNautilus(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneSitNautilus(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione EXPORT_SCHEDA_BANDI_RER
        [WebMethod]
        public bool IsVerticalizzazioneExportSchedaBandiRerAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneExportSchedaBandiRer vert = new VerticalizzazioneExportSchedaBandiRer(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneExportSchedaBandiRer GetVerticalizzazioneExportSchedaBandiRer(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneExportSchedaBandiRer(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione GEST_CANCELLAZIONI
        [WebMethod]
        public bool IsVerticalizzazioneGestCancellazioniAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneGestCancellazioni vert = new VerticalizzazioneGestCancellazioni(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneGestCancellazioni GetVerticalizzazioneGestCancellazioni(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneGestCancellazioni(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione SIT_INITMAPGUIDE
        [WebMethod]
        public bool IsVerticalizzazioneSitInitmapguideAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneSitInitmapguide vert = new VerticalizzazioneSitInitmapguide(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneSitInitmapguide GetVerticalizzazioneSitInitmapguide(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneSitInitmapguide(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione SORTEGGIORAPIDO
        [WebMethod]
        public bool IsVerticalizzazioneSorteggiorapidoAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneSorteggiorapido vert = new VerticalizzazioneSorteggiorapido(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneSorteggiorapido GetVerticalizzazioneSorteggiorapido(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneSorteggiorapido(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione AUTENTICAZIONE_SSO
        [WebMethod]
        public bool IsVerticalizzazioneAutenticazioneSsoAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneAutenticazioneSso vert = new VerticalizzazioneAutenticazioneSso(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneAutenticazioneSso GetVerticalizzazioneAutenticazioneSso(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneAutenticazioneSso(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione SIT_QUAESTIOFLORENZIA
        [WebMethod]
        public bool IsVerticalizzazioneSitQuaestioflorenziaAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneSitQuaestioflorenzia vert = new VerticalizzazioneSitQuaestioflorenzia(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneSitQuaestioflorenzia GetVerticalizzazioneSitQuaestioflorenzia(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneSitQuaestioflorenzia(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione STC
        [WebMethod]
        public bool IsVerticalizzazioneStcAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneStc vert = new VerticalizzazioneStc(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneStc GetVerticalizzazioneStc(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneStc(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione REPLICAISTANZE
        [WebMethod]
        public bool IsVerticalizzazioneReplicaistanzeAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneReplicaistanze vert = new VerticalizzazioneReplicaistanze(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneReplicaistanze GetVerticalizzazioneReplicaistanze(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneReplicaistanze(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione SISTEMAPAGAMENTI_ATTIVO
        [WebMethod]
        public bool IsVerticalizzazioneSistemapagamentiAttivoAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneSistemapagamentiAttivo vert = new VerticalizzazioneSistemapagamentiAttivo(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneSistemapagamentiAttivo GetVerticalizzazioneSistemapagamentiAttivo(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneSistemapagamentiAttivo(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione INFOCAMERA
        [WebMethod]
        public bool IsVerticalizzazioneInfocameraAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneInfocamera vert = new VerticalizzazioneInfocamera(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneInfocamera GetVerticalizzazioneInfocamera(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneInfocamera(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione ANAGRAFE
        [WebMethod]
        public bool IsVerticalizzazioneAnagrafeAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneAnagrafe vert = new VerticalizzazioneAnagrafe(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneAnagrafe GetVerticalizzazioneAnagrafe(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneAnagrafe(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione SIT_7DBTL
        [WebMethod]
        public bool IsVerticalizzazioneSit7dbtlAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneSit7dbtl vert = new VerticalizzazioneSit7dbtl(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneSit7dbtl GetVerticalizzazioneSit7dbtl(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneSit7dbtl(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione ALLINEAMENTO_ANAGRAFE_ATTIVO
        [WebMethod]
        public bool IsVerticalizzazioneAllineamentoAnagrafeAttivoAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneAllineamentoAnagrafeAttivo vert = new VerticalizzazioneAllineamentoAnagrafeAttivo(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneAllineamentoAnagrafeAttivo GetVerticalizzazioneAllineamentoAnagrafeAttivo(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneAllineamentoAnagrafeAttivo(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione ALLINEAMENTO_ANAGRAFE_CHIOGGIA
        [WebMethod]
        public bool IsVerticalizzazioneAllineamentoAnagrafeChioggiaAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneAllineamentoAnagrafeChioggia vert = new VerticalizzazioneAllineamentoAnagrafeChioggia(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneAllineamentoAnagrafeChioggia GetVerticalizzazioneAllineamentoAnagrafeChioggia(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneAllineamentoAnagrafeChioggia(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione ALLINEAMENTO_ANAGRAFE_CESENA
        [WebMethod]
        public bool IsVerticalizzazioneAllineamentoAnagrafeCesenaAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneAllineamentoAnagrafeCesena vert = new VerticalizzazioneAllineamentoAnagrafeCesena(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneAllineamentoAnagrafeCesena GetVerticalizzazioneAllineamentoAnagrafeCesena(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneAllineamentoAnagrafeCesena(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione WSANAGRAFE_CESENA
        [WebMethod]
        public bool IsVerticalizzazioneWsanagrafeCesenaAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneWsanagrafeCesena vert = new VerticalizzazioneWsanagrafeCesena(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneWsanagrafeCesena GetVerticalizzazioneWsanagrafeCesena(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneWsanagrafeCesena(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione WSANAGRAFE_PERUGIA
        [WebMethod]
        public bool IsVerticalizzazioneWsanagrafePerugiaAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneWsanagrafePerugia vert = new VerticalizzazioneWsanagrafePerugia(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneWsanagrafePerugia GetVerticalizzazioneWsanagrafePerugia(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneWsanagrafePerugia(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione WSANAGRAFE_RAVENNA
        [WebMethod]
        public bool IsVerticalizzazioneWsanagrafeRavennaAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneWsanagrafeRavenna vert = new VerticalizzazioneWsanagrafeRavenna(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneWsanagrafeRavenna GetVerticalizzazioneWsanagrafeRavenna(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneWsanagrafeRavenna(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione AIDA
        [WebMethod]
        public bool IsVerticalizzazioneAidaAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneAida vert = new VerticalizzazioneAida(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneAida GetVerticalizzazioneAida(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneAida(authInfo.Alias, software);
        }
        #endregion

        #region Gestione della verticalizzazione STANDARD_NOMENCLATURA_OGGETTI
        [WebMethod]
        public bool IsVerticalizzazioneStandardNomenclaturaOggettiAttiva(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            VerticalizzazioneStandardNomenclaturaOggetti vert = new VerticalizzazioneStandardNomenclaturaOggetti(authInfo.Alias, software);

            return vert.Attiva;
        }

        [WebMethod]
        public VerticalizzazioneStandardNomenclaturaOggetti GetVerticalizzazioneStandardNomenclaturaOggetti(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new ArgumentException("Token non valido");

            return new VerticalizzazioneStandardNomenclaturaOggetti(authInfo.Alias, software);
        }
        #endregion

    }
}
