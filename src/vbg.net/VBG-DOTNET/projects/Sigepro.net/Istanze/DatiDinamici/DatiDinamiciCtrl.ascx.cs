using Init.SIGePro.DatiDinamici.WebControls;
using Init.Utils;
using SIGePro.Net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using VBG.DatiDinamici;
using VBG.DatiDinamici.WebControls.MaschereSolaLettura;


namespace Sigepro.net.Istanze.DatiDinamici
{
    public partial class DatiDinamiciCtrl : System.Web.UI.UserControl
    {
        private static class Constants
        {
            public const int DettaglioSchedaViewId = 0;
            public const int AggiungiSchedaViewId = 1;
            public const int NessunaSchedaView = 2;
            public const int AggiungiSchedaAttivitaViewId = 3;
        }

        private bool g_RegistraScript = true;   // Se impostato a false non registra gli script di verifica modello

        public bool UsaFormAggiungiNuovaSchedaAttivita
        {
            get { object o = this.ViewState["UsaFormAggiungiNuvaSchedaAttivita"]; return o == null ? false : (bool)o; }
            set { this.ViewState["UsaFormAggiungiNuvaSchedaAttivita"] = value; }
        }


        public delegate List<int> GetListaIndiciSchedaDelegate(int modello);
        public event GetListaIndiciSchedaDelegate GetListaIndiciScheda;

        public delegate List<ElementoListaModelli> GetListaModelliDelegate();
        public event GetListaModelliDelegate GetListaModelli;

        public delegate ModelloDinamicoBase GetModelloDinamicoDaIdDelegate(int idModello, int indice);
        public event GetModelloDinamicoDaIdDelegate GetModelloDinamicoDaId;

        public delegate void AggiungiSchedaDelegate(int idModello);
        public event AggiungiSchedaDelegate AggiungiScheda;

        public delegate void EliminaSchedaDelegate(int idModello);
        public event EliminaSchedaDelegate EliminaScheda;

        public delegate string GetUrlPaginaStoricoDelegate(int idModello);
        public event GetUrlPaginaStoricoDelegate GetUrlPaginaStorico;

        public delegate bool VerificaEsistenzaStoricoDelegate(int idModello);
        public event VerificaEsistenzaStoricoDelegate VerificaEsistenzaStorico;

        public delegate IEnumerable<KeyValuePair<string, string>> GetListaSoftwareAttivitaDelegate();
        public event GetListaSoftwareAttivitaDelegate GetListaSoftwareAttivita;

        public delegate IMascheraSolaLettura GetMascheraSolaLetturaDelegate(int idModello);
        public event GetMascheraSolaLetturaDelegate GetMascheraSolaLettura;

        public event EventHandler Close;

        public bool SolaLettura
        {
            get { object o = this.ViewState["SolaLettura"]; return o == null ? false : (bool)o; }
            set { this.ViewState["SolaLettura"] = value; }
        }

        protected string NomeSchedaAttiva
        {
            get => this.ViewState["NomeSchedaAttiva"]?.ToString() ?? "";
            set => this.ViewState["NomeSchedaAttiva"] = value;
        }

        protected int NumeroSchede
        {
            get => Convert.ToInt32(this.ViewState["NumeroSchede"]?.ToString() ?? "0");
            set => this.ViewState["NumeroSchede"] = value.ToString();
        }

        protected bool HaAlmenoUnaScheda
        {
            get => Convert.ToBoolean(this.ViewState["HaAlmenoUnaScheda"]?.ToString() ?? "false");
            set => this.ViewState["HaAlmenoUnaScheda"] = value.ToString();
        }

        protected bool HaPiuDiUnaScheda
        {
            get => Convert.ToBoolean(this.ViewState["HaPiuDiUnaScheda"]?.ToString() ?? "false");
            set => this.ViewState["HaPiuDiUnaScheda"] = value.ToString();
        }

        public bool UtenteAmministratore
        {
            get => Convert.ToBoolean(this.ViewState["UtenteAmministratore"]?.ToString() ?? "false");
            set => this.ViewState["UtenteAmministratore"] = value.ToString();
        }

        public Func<string> ExtraQueryStringFunction { get; set; }

        /// <summary>
        /// Ottiene l'id della scheda selezionata
        /// </summary>
        protected int IdModelloSelezionato
        {
            get
            {
                string idModello = this.Request.QueryString["Modello"];
                if (String.IsNullOrEmpty(idModello))
                    return this.GetPrimoModello();

                return Convert.ToInt32(idModello);
            }
        }

        /// <summary>
        /// Ottiene l'indice della scheda selezionata
        /// </summary>
        protected int IndiceModello
        {
            get
            {
                string indice = this.Request.QueryString["Idx"];

                if (String.IsNullOrEmpty(indice))
                    return 0;

                return Convert.ToInt32(indice);
            }
        }


        /// <summary>
        /// Nome della chiave in querystring che contiene il codice univoco dell'oggetto
        /// a cui il modello è collegato
        /// </summary>
        public string NomeChiaveCodice
        {
            get { object o = this.ViewState["CodiceChiave"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["CodiceChiave"] = value; }
        }


        #region ciclo di vita della pagina

        protected void Page_Load(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(this.NomeChiaveCodice))
                throw new ArgumentException("Parametro NomeChiaveCodice non impostato per il controllo " + this.ID);

            BasePage.ImpostaScriptEliminazione(this.Page, this.cmdEliminaScheda);

            if (!this.IsPostBack)
            {
                this.renderer.DataSource = null;
                this.DataBind();
            }

        }

        public override void DataBind()
        {
            using (CodeProfiler.Track("Databind del modello dinamico"))
            {
                this.BindListaModelliAttivi();

                if (this.IdModelloSelezionato == -1)
                {
                    // Se non ho un modello selezionato mostro la sezione "nuova scheda"
                    if (!this.SolaLettura)
                        this.VisualizzaFormNuovaScheda();
                    else
                        this.VisualizzaNessunaScheda();
                }
                else
                {
                    // Effettua il binding del modello attivo
                    ModelloDinamicoBase modello = GetModelloDinamicoDaId(this.IdModelloSelezionato, this.IndiceModello);

                    // TODO: spostare la chiamata nel modello
                    modello.EseguiScriptCaricamento();

                    if (this.GetMascheraSolaLettura != null)
                        this.renderer.ImpostaMascheraSolaLettura(this.GetMascheraSolaLettura(this.IdModelloSelezionato));
                    else
                        this.renderer.ImpostaMascheraSolaLettura(new MascheraSolaLetturaVuota());

                    this.renderer.DataSource = modello;
                    this.renderer.DataBind();

                    List<int> listaIndici = GetListaIndiciScheda(this.IdModelloSelezionato);

                    if (!listaIndici.Contains(this.IndiceModello))
                        listaIndici.Add(this.IndiceModello);

                    this.rptMolteplicita.DataSource = listaIndici;
                    this.rptMolteplicita.DataBind();

                    this.rptMolteplicita.Visible = modello.ModelloMultiplo;
                }


                this.cmdSalva.Visible = this.cmdEliminaScheda.Visible = this.cmdStorico.Visible = (this.IdModelloSelezionato != -1);
                this.cmdAggiungiScheda.Visible = !this.cmdEliminaScheda.Visible;

                bool storicoPresente = this.StoricoVisibile();

                if (this.cmdStorico.Visible && !storicoPresente)
                    this.cmdStorico.Visible = false;

                if (this.cmdEliminaScheda.Visible && storicoPresente)
                    this.cmdEliminaScheda.Visible = false;
            }
        }

        private bool StoricoVisibile()
        {
            if (VerificaEsistenzaStorico == null)
                return false;

            return VerificaEsistenzaStorico(this.IdModelloSelezionato);
        }




        protected override void OnPreRender(EventArgs e)
        {
            if (this.SolaLettura)
            {
                this.cmdSalva.Visible = false;
                this.cmdEliminaScheda.Visible = false;
                this.cmdAggiungiScheda.Visible = false;
            }

            if (ModelloDinamicoRenderer.DataSourceAttuale != null && ModelloDinamicoRenderer.DataSourceAttuale.InSolaLetturaNelBo)
            {
                this.cmdSalva.Visible = false;
                this.cmdEliminaScheda.Visible = false;
            }

            if (this.UtenteAmministratore)
            {
                if (this.multiView.ActiveViewIndex != Constants.AggiungiSchedaViewId)
                {
                    this.cmdSalva.Visible = true;
                    this.cmdEliminaScheda.Visible = true;
                }
            }

            if (this.g_RegistraScript && this.renderer.DataSource != null)
            {
                // Registra i clientscript di tutti i controlli del modello
                foreach (CampoDinamicoBase campo in this.renderer.DataSource.Campi)
                {
                    if (String.IsNullOrEmpty(campo.ClientScript)) continue;

                    string scriptFmtString = @"function Fn{0}_ClientScript( el ){{{1}}}";
                    string script = String.Format(scriptFmtString, campo.Id, campo.ClientScript);

                    this.Page.ClientScript.RegisterClientScriptBlock(campo.GetType(), campo.Id + "_ClientScript", script, true);
                }


            }

            base.OnPreRender(e);




        }

        protected override void Render(HtmlTextWriter writer)
        {
            if (this.g_RegistraScript && this.renderer.DataSource != null)
            {
                // Registra i clientscript di tutti i controlli del modello
                foreach (CampoDinamicoBase campo in this.renderer.DataSource.Campi)
                {
                    if (String.IsNullOrEmpty(campo.ClientScript)) continue;

                    string startScriptFmtString = "Fn{0}_ClientScript( document.getElementById( g_datiDinamiciExtender.GetClientIdCampo('{0}') ) );"; ;
                    string startScript = String.Format(startScriptFmtString, campo.Id);

                    campo.ClientScript = "";

                    this.Page.ClientScript.RegisterStartupScript(campo.GetType(), campo.Id + "_ClientScriptStartup", startScript, true);
                }
            }



            base.Render(writer);
        }

        #endregion


        /// <summary>
        /// Ottiene il codice del primo modello disponibile
        /// </summary>
        /// <returns></returns>
        protected int GetPrimoModello()
        {
            List<ElementoListaModelli> modelliAttivi = GetListaModelli();

            if (modelliAttivi.Count > 0)
                return modelliAttivi[0].Id;

            return -1;
        }


        #region gestione dell'intestazione delle schede
        /// <summary>
        /// Visualizza la lista delle schede dell'istanza/anagrafe/attività corrente
        /// </summary>
        protected void BindListaModelliAttivi()
        {
            this.multiView.ActiveViewIndex = Constants.DettaglioSchedaViewId;

            List<ElementoListaModelli> modelliAttivi = GetListaModelli();//new IstanzeDyn2ModelliTMgr(Database).GetModelliIstanza(IdComune, Convert.ToInt32(CodiceIstanza), CodiceMovimento);

            //if (!SolaLettura)
            //{
            //	ElementoListaModelli nuovaScheda = new ElementoListaModelli(-1, "<i class='fa fa-plus' aria-hidden='true'></i> Nuova scheda");
            //	modelliAttivi.Add(nuovaScheda);
            //}

            this.NomeSchedaAttiva = modelliAttivi.Where(x => x.Id == this.IdModelloSelezionato).FirstOrDefault()?.Descrizione;
            this.HaAlmenoUnaScheda = modelliAttivi.Count() > 0;
            this.HaPiuDiUnaScheda = modelliAttivi.Count() > 1;
            //rptListaSchede.DataSource = modelliAttivi;
            //rptListaSchede.DataBind();
            this.NumeroSchede = Math.Max(0, modelliAttivi.Count - 1);
            this.rptPassaAScheda.DataSource = modelliAttivi.Where(x => x.Id != this.IdModelloSelezionato);
            this.rptPassaAScheda.DataBind();
            this.rptPassaAScheda.Visible = this.HaPiuDiUnaScheda || (String.IsNullOrEmpty(this.NomeSchedaAttiva) && this.HaAlmenoUnaScheda);
            this.lbAggiungiScheda.CssClass = String.IsNullOrEmpty(this.NomeSchedaAttiva) ? "SchedaAttiva" : "";
        }

        #endregion

        #region gestione del modello attivo

        /// <summary>
        /// Salva il modello corrente
        /// </summary>
        public void SalvaModello(object sender, EventArgs e)
        {

        }

        #endregion

        #region Navigazione di base della pagina

        /// <summary>
        /// Chiude la pagina corrente e ritorna alla pagina chiamante
        /// </summary>
        protected void cmdChiudi_Click(object sender, EventArgs e)
        {
            this.Close(this, EventArgs.Empty);
        }

        #endregion

        #region visualizzazione, inserimento e eliminazione schede
        /// <summary>
        /// Passa da una scheda ad un'altra
        /// </summary>
        protected void CambiaScheda(object sender, EventArgs e)
        {
            int idModello = Convert.ToInt32(((LinkButton)sender).CommandArgument);

            this.RedirectAllaScheda(idModello.ToString(), "0");
        }

        /// <summary>
        /// Passa alla visualizzazione della sezione "Nuova scheda"
        /// </summary>
        protected void VisualizzaFormNuovaScheda()
        {
            this.cmdStorico.Visible = false;
            this.g_RegistraScript = false;

            if (this.UsaFormAggiungiNuovaSchedaAttivita)
                this.VisualizzaFormNuovaSchedaAttivita();
            else
                this.VisualizzaFormNuovaSchedaStandard();
        }

        protected void VisualizzaFormNuovaSchedaStandard()
        {
            this.multiView.ActiveViewIndex = Constants.AggiungiSchedaViewId;

            this.hidIdNuovaScheda.Value = String.Empty;
            this.txtNuovaScheda.Text = String.Empty;
            this.cmdSalva.Visible = false;
        }

        protected void VisualizzaFormNuovaSchedaAttivita()
        {
            this.multiView.ActiveViewIndex = Constants.AggiungiSchedaAttivitaViewId;

            this.ddlCercaInModuloAtt.DataSource = GetListaSoftwareAttivita();
            this.ddlCercaInModuloAtt.DataBind();

            this.hidIdNuovaSchedaAtt.Value = String.Empty;
            this.txtNuovaSchedaAtt.Text = String.Empty;
        }

        protected void VisualizzaNessunaScheda()
        {
            this.multiView.ActiveViewIndex = Constants.NessunaSchedaView;
            this.g_RegistraScript = false;
        }

        /// <summary>
        /// Elimina la scheda visualizzata correntemente
        /// </summary>
        protected void EliminaSchedaAttiva(object sender, EventArgs e)
        {
            if (this.IdModelloSelezionato <= 0) return;

            try
            {
                EliminaScheda(this.IdModelloSelezionato);

                this.RedirectAllaScheda("", "");
            }
            catch (Exception ex)
            {
                (this.Page as BasePage).MostraErrore(BasePage.AmbitoErroreEnum.Cancellazione, ex);
            }

        }

        /// <summary>
        /// Aggiunge una nuova scheda all'istanza corrente
        /// </summary>
        protected void AggiungiNuovaScheda(object sender, EventArgs e)
        {
            try
            {
                var idNuovaScheda = String.Empty;

                if (this.UsaFormAggiungiNuovaSchedaAttivita)
                    idNuovaScheda = this.hidIdNuovaSchedaAtt.Value;
                else
                    idNuovaScheda = this.hidIdNuovaScheda.Value;

                if (String.IsNullOrEmpty(idNuovaScheda))
                    throw new ArgumentException("Selezionare la nuova scheda da aggiungere");

                AggiungiScheda(Convert.ToInt32(idNuovaScheda));

                this.RedirectAllaScheda(idNuovaScheda, "0");
            }
            catch (Exception ex)
            {
                (this.Page as BasePage).MostraErrore(BasePage.AmbitoErroreEnum.Inserimento, ex);
            }
        }

        #endregion



        /// <summary>
        /// Effettua il redirect ad una nuova scheda
        /// </summary>
        /// <param name="idNuovaScheda">Id della scheda da aprire</param>
        /// <param name="indice">Indice della scheda da aprire</param>
        protected void RedirectAllaScheda(string idNuovaScheda, string indice)
        {
            string url = this.Request.Url.AbsoluteUri;

            // Elimino la querystring dall'url
            if (!String.IsNullOrEmpty(this.Request.Url.Query))
            {
                int idx = url.IndexOf("?");
                url = url.Substring(0, idx);
            }

            // VD.Problema redirect a genova. Per far funzionare correttamente il redirect
            // devo far iniziare il path con // in modo che il browser non blocchi la richiesta di redirect
            // Se si è dietro ad un proxy la richiesta arriva in http e Request.Url.AbsoluteUri è sempre in http.
            // Normalmente il problema si risolve con un proxy redirect ma in alcuni casi come genova (apache)
            // non abbiamo il controllo del reverse proxy
            if (url.StartsWith("https"))
            {
                url = url.Substring(6);
            }
            else if (url.StartsWith("http"))
            {
                url = url.Substring(5);
            }

            string software = this.Request.QueryString["Software"];
            string token = this.Request.QueryString["Token"];
            string valoreChiave = this.Request.QueryString[this.NomeChiaveCodice];
            string baseUrl = this.Server.UrlEncode(this.Request.QueryString["BaseUrl"]);
            string returnTo = this.Server.UrlEncode(this.Request.QueryString["ReturnTo"]);

            string fmtUrl = "{0}?Modello={1}&Idx={2}&Software={3}&Token={4}&{5}={6}&BaseUrl={7}&ReturnTo={8}";

            string redirUrl = String.Format(fmtUrl, url,
                                                      idNuovaScheda,
                                                      indice,
                                                      software,
                                                      token,
                                                      this.NomeChiaveCodice,
                                                      valoreChiave,
                                                      baseUrl,
                                                      returnTo);

            if (this.ExtraQueryStringFunction != null)
            {
                var val = this.ExtraQueryStringFunction();

                redirUrl += val.StartsWith("&") ? val : "&" + val;
            }

            this.Response.Redirect(redirUrl);
        }


        /// <summary>
        /// Salvataggio dei dati del modello corrente
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        protected void cmdSalva_Click(object sender, EventArgs e)
        {
            using (CodeProfiler.Track("Salvataggio del modello dinamico"))
            {
                if (this.renderer.DataSource == null)
                    return;

                try
                {
                    this.renderer.DataSource.ValidaModello();
                }
                catch (ValidazioneModelloDinamicoException)
                {
                    this.MostraErroreSalvataggio();
                    return;
                }

                this.renderer.DataSource.Salva();

                if (this.renderer.DataSource.ErroriScript.Count() > 0)
                {
                    this.MostraErroreSalvataggio();
                    return;
                }

                this.DataBind();

                this.Page.ClientScript.RegisterStartupScript(this.GetType(), "notifica", "$('#salvataggioEffettuato').fadeIn();setTimeout('$(\\'#salvataggioEffettuato\\').fadeOut()',2000);", true);
            }
        }

        private void MostraErroreSalvataggio()
        {
            this.Page.ClientScript.RegisterStartupScript(this.GetType(), "notifica", "alert('Si sono verificati errori durante il salvataggio');", true);
            //DataBind();
        }

        protected void CambiaIndice(object sender, EventArgs e)
        {
            LinkButton lb = (LinkButton)sender;

            string nuovoIndice = lb.CommandArgument;

            this.RedirectAllaScheda(this.IdModelloSelezionato.ToString(), nuovoIndice);
        }

        protected void NuovaSchedaMultipla(object sender, ImageClickEventArgs e)
        {
            int nuovoIndice = this.CalcolaNuovoIndice();

            this.RedirectAllaScheda(this.IdModelloSelezionato.ToString(), nuovoIndice.ToString());
        }

        private int CalcolaNuovoIndice()
        {
            List<int> indici = GetListaIndiciScheda(this.IdModelloSelezionato);

            int nuovoIndice = 0;

            if (indici.Count > 0)
                nuovoIndice = indici[indici.Count - 1] + 1;

            return nuovoIndice;
        }

        protected void DuplicaSchedaMultipla(object sender, ImageClickEventArgs e)
        {
            int nuovoIndice = this.CalcolaNuovoIndice();

            ModelloDinamicoBase nuovaScheda = GetModelloDinamicoDaId(this.IdModelloSelezionato, nuovoIndice);
            ModelloDinamicoBase schedaAttuiale = GetModelloDinamicoDaId(this.IdModelloSelezionato, this.IndiceModello);

            nuovaScheda.CopiaDa(schedaAttuiale);

            nuovaScheda.Salva();

            this.RedirectAllaScheda(this.IdModelloSelezionato.ToString(), nuovoIndice.ToString());


        }

        protected void EliminaSchedaMultipla(object sender, ImageClickEventArgs e)
        {
            ModelloDinamicoBase schedaAttuale = GetModelloDinamicoDaId(this.IdModelloSelezionato, this.IndiceModello);

            try
            {
                schedaAttuale.Elimina();

                List<int> indici = GetListaIndiciScheda(this.IdModelloSelezionato);

                if (indici.Count == 0)
                    indici.Add(0);

                this.RedirectAllaScheda(this.IdModelloSelezionato.ToString(), indici[0].ToString());
            }
            catch (Exception ex)
            {
                (this.Page as BasePage).MostraErrore(BasePage.AmbitoErroreEnum.Cancellazione, ex);
            }

        }

        protected bool IndiceCorrente(object indice)
        {
            return (int)indice == this.IndiceModello;
        }

        protected string TestoIndice(object indice)
        {
            //return "[" + (Convert.ToInt32(indice) + 1).ToString() + "]&nbsp;";
            return "[" + (Convert.ToInt32(indice)).ToString() + "]&nbsp;";
        }

        protected string GetUrlStorico()
        {
            if (this.GetUrlPaginaStorico == null)
                return "";

            return this.GetUrlPaginaStorico(this.IdModelloSelezionato);
        }

        protected string StileSpanSoftwareTT()
        {
            if (this.NomeChiaveCodice.ToUpperInvariant() == "CODICEANAGRAFE")
                return "display:none";

            return "";
        }

        protected void lbAggiungiScheda_Click(object sender, EventArgs e)
        {
            this.RedirectAllaScheda("-1", "0");
        }
    }
}