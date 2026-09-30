using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.IOC;
using Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.Attivita;
using Init.SIGePro.Manager.Logic.DatiDinamici.ModelliFactory;
using Init.SIGePro.Manager.Logic.GestioneSchedeAttivita;
using Ninject;
using SIGePro.Net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Script.Services;
using System.Web.Services;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Standard.Scripts;
using VBG.DatiDinamici.WebControls.MaschereSolaLettura;

namespace Sigepro.net.Istanze.DatiDinamici
{
    public partial class AttivitaDyn2Modelli : BasePage
    {
        #region Gestione della classe Attivita
        protected int CodiceAttivita
        {
            get { return Convert.ToInt32(this.Request.QueryString["CodiceAttivita"]); }
        }

        private IAttivita m_attivita;

        private IAttivita Attivita
        {
            get
            {
                if (this.m_attivita == null)
                    this.m_attivita = new IAttivitaMgr(this.Database).GetById(this.IdComune, this.CodiceAttivita);

                return this.m_attivita;
            }
        }

        public override string Software
        {
            get
            {
                return "TT";
            }
        }
        #endregion


        #region ciclo di vita della pagina

        protected void Page_Load(object sender, EventArgs e)
        {
            this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;

            if (!this.IsPostBack)
                this.DataBind();
        }

        public override void DataBind()
        {
            var att = new IAttivitaMgr(this.Database).GetById(this.IdComune, this.CodiceAttivita);

            this.lblCodiceAttivita.Text = att.Id.ToString();
            this.lblDenominazione.Text = att.Denominazione;

        }

        #endregion

        [Inject]
        public ISchedeDinamicheAttivitaService _schedeDinamicheAttivitaService { get; set; }

        #region handler degli eventi del controllo di gestione modelli

        public List<ElementoListaModelli> GetListaModelli()
        {
            var modelli = new IAttivitaDyn2ModelliTMgr(this.Database).GetModelliCollegati(this.IdComune, this.CodiceAttivita);

            return modelli;
        }


        public ModelloDinamicoBase GetModelloDinamicoDaId(int idModello, int indice)
        {
            var dap = new AttivitaDyn2DataAccessFactory(this.AuthenticationInfo, this._schedeDinamicheAttivitaService, this.CodiceAttivita);
            var loader = new ModelloDinamicoLoader(dap, this.IdComune, ContestoScriptEnum.Backoffice);
            return new BackendModelliFactory().CreaModelloAttivita(loader, idModello, indice, false);
        }

        public void AggiungiScheda(int idModello)
        {
            this._schedeDinamicheAttivitaService.AggiungiSchedaDinamicaAdAttivita(this.CodiceAttivita, idModello);
        }

        public void EliminaScheda(int idModello)
        {
            this._schedeDinamicheAttivitaService.EliminaModello(this.CodiceAttivita, idModello);
        }

        public void Close(object sender, EventArgs e)
        {
            base.CloseCurrentPage();
        }

        public List<int> GetListaIndiciScheda(int idModello)
        {
            var mgr = new IAttivitaDyn2ModelliTMgr(this.Database);
            return mgr.GetListaIndiciScheda(this.IdComune, idModello, this.CodiceAttivita);
        }

        public IMascheraSolaLettura GetMascheraSolaLettura(int idModello)
        {
            //var mgr = new IAttivitaDyn2DatiMgr(Database);
            var listaCampi = this._schedeDinamicheAttivitaService.GetCampiModelloPresentiAncheNelleIstanze(this.CodiceAttivita, idModello);

            return new MascheraSolaLetturaDaId(listaCampi);
        }

        #endregion

        public string GetUrlPaginaStorico(int idModello)
        {
            string url = "~/Istanze/DatiDinamici/Storico/AttivitaDyn2Storico.aspx?Token={0}&CodiceAttivita={1}&IdModello={2}";

            return this.ResolveClientUrl(String.Format(url, this.Token, this.CodiceAttivita, idModello));
        }

        public bool VerificaEsistenzaStorico(int idModello)
        {
            var mgr = new IAttivitaDyn2ModelliTStoricoMgr(this.Database);
            int cnt = mgr.ContaRigheStorico(this.IdComune, idModello, this.CodiceAttivita);

            return cnt > 0;
        }

        public IEnumerable<KeyValuePair<string, string>> GetListaSoftwareAttivita()
        {
            // Logica estrazione del software dall'id attività:
            // - A partire dal software dell'ultima istanza dell'attività (codiceistanzaultima) leggo il valore del parametro GRUPPOSOFTWARE della verticalizzazione I_ATTIVITA
            // - Se la verticalizzazione non è attiva o se grupposoftware non è valorizzato
            //		- Restituisco la lista di tutti i software attivi nell'installazione + il software TT (con il testo "Archivi di base")
            // - Se la verticalizzazione è attiva e il parametro è valorizzato
            //		- Restituisco la lista dei sw contenuti nel parametro
            var mgr = new IAttivitaMgr(this.Database);

            var list = mgr.GetSoftwareAttiviDaIdAttivita(this.IdComune, this.IdComuneAlias, this.CodiceAttivita);

            list.Add(new Software { CODICE = "TT", DESCRIZIONE = "Archivi di base" });

            return list.Select(x => new KeyValuePair<string, string>(x.CODICE, x.DESCRIZIONE));


        }

        [WebMethod()]
        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        public static object GetListaModelliDisponibili(string token, int codice, string partial, string software)
        {
            var authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            if (authInfo == null)
                return new object[] { new { label = -1, value = "Token non valido" } };

            using (var db = authInfo.CreateDatabase())
            {
                return new IAttivitaDyn2ModelliTMgr(db).GetModelliNonUtilizzati(authInfo.IdComune, codice, partial, software)
                                                       .Select(x => new { label = x.Descrizione, value = x.Codice });
            }
        }
    }
}
