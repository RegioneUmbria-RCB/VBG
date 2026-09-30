using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using SIGePro.Net;
using SIGePro.WebControls.Ajax;
using Init.SIGePro.Manager.Logic.Ricerche;
using System.Collections.Generic;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using PersonalLib2.Exceptions;
using Init.SIGePro.Exceptions;
using PersonalLib2.Sql;
using Init.Utils.Web.UI;

namespace Sigepro.net.Istanze.Mercati
{
    public partial class RegistraPresenzeMercato : BasePage
    {
        int idxRecord = 0;
        int idxSpuntisti = 0;
        int idxDateSvolgimento = 0;
        int m_codiceuso = int.MinValue;

        public int IndiceRecord
        {
            get { return idxRecord; }
            set { idxRecord = value; }
        }
        public int IndiceSpuntisti
        {
            get { return idxSpuntisti; }
            set { idxSpuntisti = value; }
        }
        public int IndiceDateSvolgimento
        {
            get { return idxDateSvolgimento; }
            set { idxDateSvolgimento = value; }
        }

        protected int CodiceUso
        {
            get { return m_codiceuso; }
            set { m_codiceuso = value; }
        }
        protected int? IdTestata
        {
            get { return String.IsNullOrEmpty(Request.QueryString["IdTestata"]) ? (int?)null : Convert.ToInt32(Request.QueryString["IdTestata"]); }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            ImpostaScriptEliminazione(cmdElimina);

            if (!IsPostBack)
            {
                this.rplMercatiUso.InitParams["FkCodiceMercato"] = "";
                this.rplRicercaMercatiUso.InitParams["FkCodiceMercato"] = "";

                if (IdTestata.GetValueOrDefault(int.MinValue) > int.MinValue)
                {
                    multiView.ActiveViewIndex = 2;

                    BindDettaglio(new MercatiPresenzeTMgr(Database).GetById(IdComune, IdTestata.GetValueOrDefault(int.MinValue), useForeignEnum.Yes));
                }

                if (this.ddlTipologia.Item.Items.Count == 0)
                {
                    this.ddlTipologia.Item.Items.Add(new ListItem("Singola", "0"));
                    this.ddlTipologia.Item.Items.Add(new ListItem("Periodica", "1"));
                }
            }

            ViewSpuntisti();
            ViewOccupanti();

            if (!this.dtDallaDataRegistrazione.DateValue.HasValue)
                this.dtDallaDataRegistrazione.DateValue = DateTime.Now.Date;
        }

        protected void multiView_ActiveViewChanged(object sender, EventArgs e)
        {
            switch (multiView.ActiveViewIndex)
            {
                case (1):
                    Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;
                    return;
                case (2):
                    Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
                    return;
                case (3):
                    Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
                    return;
                default:
                    Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Ricerca;
                    return;
            }
        }

        protected MercatiPresenzeT Salva(MercatiPresenzeT cls)
        {
            MercatiPresenzeTMgr mgr = new MercatiPresenzeTMgr(Database);
            if (IsInserting)
            {
                cls = mgr.Insert(cls);
                mgr.AssegnaPresenzaOccupanti(cls);

                //svuoto il viestate per far comparire dopo l'inserimento la composizione del mercato
                ViewState["ViewOccupanti"] = null;

                string fmtUrl = "~/Istanze/Mercati/RegistraPresenzeMercato.aspx?Token={0}&Software={1}&IdTestata={2}";
                Response.Redirect(String.Format(fmtUrl, AuthenticationInfo.Token, Software, cls.Id.ToString() ));
            }
            else
            {
                cls = mgr.Update(cls);

                //4. Ricarico la classe per prendere anche la descrizione del mercato
                //e dell'uso dalle foreign
                cls = new MercatiPresenzeTMgr(Database).GetById(cls.Idcomune, cls.Id.GetValueOrDefault(int.MinValue), useForeignEnum.Yes);

                List<MercatiPresenzeD> presenze = new List<MercatiPresenzeD>();
                //2. Raccolta delle presenze degli spuntisti
                GetPresenzeSpuntisti(presenze);

                //3. Raccolta delle presenze degli occupanti
                GetPresenzeOccupanti(presenze);

                //4. Cancello il dettaglio
                mgr.DeleteDettagli(cls.Idcomune, cls.Id.GetValueOrDefault(int.MinValue));

                //5. Salvo il nuovo dettaglio
                mgr.InsertDettagli(presenze);
            }

            return cls;
        }

        #region Scheda ricerca
        protected void rplRicercaMercato_ValueChanged(object sender, EventArgs e)
        {
            this.rplRicercaMercatiUso.InitParams["FkCodiceMercato"] = this.rplRicercaMercato.Value;
            this.rplRicercaMercatiUso.Class = null;
        }
        public void cmdCerca_Click(object sender, EventArgs e)
        {
            gvLista.DataBind();
            multiView.ActiveViewIndex = 1;
        }
        public void cmdNuovo_Click(object sender, EventArgs e)
        {
            BindDettaglio(new MercatiPresenzeT());
        }
        public void cmdChiudi_Click(object sender, EventArgs e)
        {
			base.CloseCurrentPage();
        }
        #endregion

        #region Scheda lista
        public void cmdChiudiLista_Click(object sender, EventArgs e)
        {
            multiView.ActiveViewIndex = 0;
        }
        protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
        {
            string fmtUrl = "~/Istanze/Mercati/RegistraPresenzeMercato.aspx?Token={0}&Software={1}&IdTestata={2}";
            Response.Redirect(String.Format(fmtUrl, AuthenticationInfo.Token, Software, gvLista.DataKeys[gvLista.SelectedIndex].Value));
        }
        #endregion

        #region Scheda dettaglio
        protected void rplMercato_ValueChanged(object sender, EventArgs e)
        {
            this.rplMercatiUso.InitParams["FkCodiceMercato"] = this.rplMercato.Value;
            this.rplMercatiUso.Class = null;
        }
        protected void cmdSalva_Click(object sender, EventArgs e)
        {
            try
            {
                if (String.IsNullOrEmpty(this.rplMercato.Value))
                    throw new RequiredFieldException("Mercato obbligatorio");

                if (String.IsNullOrEmpty(this.rplMercatiUso.Value))
                    throw new RequiredFieldException("Giorno obbligatorio");

                if (IsInserting && this.ddlTipologia.Value == "1")
                {
                    this.IsInserting = true;

                    if (this.dtDallaDataRegistrazione.DateValue.GetValueOrDefault(DateTime.MinValue) == DateTime.MinValue || this.dtAllaDataRegistrazione.DateValue.GetValueOrDefault(DateTime.MinValue) == DateTime.MinValue)
                            throw new RequiredFieldException("E' necessario specificare l'intervallo di date per il quale si effettua la registrazione");

                    //registrazione multipla
                    multiView.ActiveViewIndex = 3;

                    BindDettaglioPeriodico();
                    BindRepeaterGiorniMercato();
                }
                else
                {
                    //registrazione singola
                    MercatiPresenzeTMgr mgr = new MercatiPresenzeTMgr(Database);
                    MercatiPresenzeT cls = null;

                    if (IsInserting)
                    {
                        cls = new MercatiPresenzeT();
                        cls.Idcomune = IdComune;
                        cls.Software = Software;
                        cls.Codiceresponsabile = AuthenticationInfo.CodiceResponsabile;
                        cls.Dataregistrazione = this.dtDallaDataRegistrazione.DateValue.GetValueOrDefault(DateTime.MinValue);
                        cls.Giornimercato = 1;
                        cls.Descrizione = this.txDescrizione.Value;
                        cls.Fkcodicemercato = Convert.ToInt32(this.rplMercato.Value);
                        cls.Fkidmercatiuso = Convert.ToInt32(this.rplMercatiUso.Value);
                        if (String.IsNullOrEmpty(cls.Descrizione))
                        {
                            cls.Descrizione = this.dtDallaDataRegistrazione.Text + " - ";
                            cls.Descrizione += "Assegnazione presenze del mercato " + this.rplMercato.Text + " per il giorno " + this.rplMercatiUso.Text;
                        }

                        Salva(cls);
                    }
                    else
                    {
                        Database.BeginTransaction();

                        //1. Aggiornamento della testata ( solo la descrizione )
                        int id = Convert.ToInt32(lblId.Item.Text);
                        cls = new MercatiPresenzeTMgr(Database).GetById(IdComune, id);
                        cls.Descrizione = this.txDescrizione.Value;

                        cls = Salva(cls);

                        BindDettaglio(cls);

                        Database.CommitTransaction();
                    }
                }
            }
            catch (RequiredFieldException rfe)
            {
                MostraErrore(rfe.Message, rfe);
            }
            catch (Exception ex)
            {
                BindDettaglio( new MercatiPresenzeT());
                MostraErrore(IsInserting ? AmbitoErroreEnum.Inserimento : AmbitoErroreEnum.Aggiornamento, ex);
            }
        }
        protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
        {
            multiView.ActiveViewIndex = 0;
        }
        protected void cmdElimina_Click(object sender, EventArgs e)
        {
            MercatiPresenzeTMgr mgr = new MercatiPresenzeTMgr(Database);

            int id = Convert.ToInt32(lblId.Item.Text);

            MercatiPresenzeT cls = mgr.GetById(IdComune, id);

            try
            {
                mgr.Delete(cls);

                cmdChiudiDettaglio_Click(sender, e);
            }
            catch (Exception ex)
            {
                MostraErrore(AmbitoErroreEnum.Cancellazione, ex);
            }
        }
        private void BindDettaglio(MercatiPresenzeT cls )
        {
            multiView.ActiveViewIndex = 2;
            this.IsInserting = cls.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

            if (IsInserting)
            {
                lblId.Item.Text = "Nuovo";

                this.txDescrizione.Value = String.Empty;
                this.rplMercato.Value = String.Empty;
                this.rplMercato.Text = String.Empty;
                this.rplMercatiUso.Value = String.Empty;
                this.rplMercatiUso.Text = String.Empty;
                rplMercato.ReadOnly = rplMercatiUso.ReadOnly = false;

                this.cmdRegContabile.Visible = this.cmdElimina.Visible = false;
                this.pnlPosteggi.Visible = this.pnlSpuntisti.Visible = false;
            }
            else
            {
                lblId.Item.Text = cls.Id.ToString();

                this.txDescrizione.Value = cls.Descrizione;
                this.rplMercato.Value = cls.Fkcodicemercato.ToString();
                this.rplMercato.Text = cls.Mercato.Descrizione;
                this.rplMercatiUso.Value = cls.Fkidmercatiuso.ToString();
                this.rplMercatiUso.Text = cls.MercatiUso.Descrizione;
                rplMercato.ReadOnly = rplMercatiUso.ReadOnly = true;

                CodiceUso = cls.Fkidmercatiuso.GetValueOrDefault(int.MinValue);

                this.cmdNuovoSpuntista.Attributes.Add("onClick", "AggiungiSpuntista(" + cls.Id.ToString() + "," + cls.Fkcodicemercato.ToString() + "," + cls.Fkidmercatiuso.ToString() + "); return false;");
                this.cmdRegContabile.Attributes.Add("onClick", "RegistraContabilita(" + cls.Id.ToString() + "); return false;");
                BindGridSpuntisti(cls);
                BindRepeater();

                this.cmdRegContabile.Visible = this.cmdElimina.Visible = true;
                this.pnlPosteggi.Visible = this.pnlSpuntisti.Visible = true;
            }
            ViewOccupanti();
            ViewSpuntisti();
        }
        #endregion

        #region Scheda registrazione periodica
        protected void cmdMultiChiudi_Click(object sender, EventArgs e)
        {
            multiView.ActiveViewIndex = 0;
        }

        protected void BindRepeaterGiorniMercato()
        {
            this.rptGiorniMercato.DataSource = GetSvolgimentoMercato(Convert.ToInt32(this.rplMultiMercatiUso.Value), this.dtMultiDallaData.DateValue.GetValueOrDefault(DateTime.MinValue), this.dtMultiAllaData.DateValue.GetValueOrDefault(DateTime.MinValue));
            this.rptGiorniMercato.DataBind();
        }

        protected List<DateTime> GetSvolgimentoMercato(int codiceUso, DateTime dallaData, DateTime allaData)
        {
            List<DateTime> retVal = new List<DateTime>();
            Mercati_Uso mu = new Mercati_UsoMgr(Database).GetById(IdComune, codiceUso);

            if (!string.IsNullOrEmpty(mu.FkGsId))
            {
                DayOfWeek giornoSvolgimento = GetDayOfWeek(Convert.ToInt16(mu.FkGsId));

                for (DateTime dt = dallaData; dt <= allaData; dt = dt.AddDays(1))
                {
                    if (dt.DayOfWeek == giornoSvolgimento)
                    {
                        retVal.Add(dt);
                    }
                }
            }
            return retVal;
        }

        protected DayOfWeek GetDayOfWeek(int codiceSigepro)
        {
            switch (codiceSigepro)
            {
                case 1: { return DayOfWeek.Monday;  }
                case 2: { return DayOfWeek.Tuesday;  }
                case 3: { return DayOfWeek.Wednesday;  }
                case 4: { return DayOfWeek.Thursday;  }
                case 5: { return DayOfWeek.Friday;  }
                case 6: { return DayOfWeek.Saturday;  }
                case 7: { return DayOfWeek.Sunday;  }
                default: { throw new Exception("Giorno non codificato"); break; }
            }
        }

        protected void rptGiorniMercato_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.AlternatingItem || e.Item.ItemType == ListItemType.Item)
            {
                DateTime dt = (DateTime)e.Item.DataItem;
                CheckBox chkDataMercato = (CheckBox)e.Item.FindControl("chkDataMercato");
                Label lblDataMercato = (Label)e.Item.FindControl("lblDataMercato");
                DateTextBox dtDataMercato = (DateTextBox)e.Item.FindControl("dtDataMercato");

                dtDataMercato.DateValue = dt;
                lblDataMercato.Text = dtDataMercato.Text;
                chkDataMercato.Checked = true;
            }
        }

        protected void cmdMultiConferma_Click(object sender, EventArgs e)
        {
            foreach (RepeaterItem r in this.rptGiorniMercato.Items)
            {
                if (r.ItemType == ListItemType.AlternatingItem || r.ItemType == ListItemType.Item)
                {
                    if (((CheckBox)r.FindControl("chkDataMercato")).Checked)
                    {
                        DateTextBox dtDataMercato = (DateTextBox)r.FindControl("dtDataMercato");

                        MercatiPresenzeT cls = new MercatiPresenzeT();
                        cls.Idcomune = IdComune;
                        cls.Software = Software;
                        cls.Codiceresponsabile = AuthenticationInfo.CodiceResponsabile;
                        cls.Dataregistrazione = dtDataMercato.DateValue.GetValueOrDefault(DateTime.MinValue);
                        cls.Giornimercato = 1;
                        cls.Descrizione = this.txMultiDescrizione.Value;
                        cls.Fkcodicemercato = Convert.ToInt32(this.rplMultiMercato.Value);
                        cls.Fkidmercatiuso = Convert.ToInt32(this.rplMultiMercatiUso.Value);
                        if (String.IsNullOrEmpty(cls.Descrizione))
                        {
                            cls.Descrizione = dtDataMercato.Text + " - ";
                            cls.Descrizione += "Assegnazione presenze del mercato " + this.rplMultiMercato.Text + " per il giorno " + this.rplMultiMercatiUso.Text;
                        }

                        Salva(cls);
                    }
                }
            }

            cmdMultiChiudi_Click(sender, e);
        }

        private void BindDettaglioPeriodico()
        {
            this.dtMultiDallaData.DateValue = this.dtDallaDataRegistrazione.DateValue;
            
            this.dtMultiAllaData.DateValue = this.dtAllaDataRegistrazione.DateValue;

            this.rplMultiMercato.Value = this.rplMercato.Value;
            this.rplMultiMercato.Text = this.rplMercato.Text;

            this.rplMultiMercatiUso.Value = this.rplMercatiUso.Value;
            this.rplMultiMercatiUso.Text = this.rplMercatiUso.Text;

            this.txMultiDescrizione.Value = this.txDescrizione.Value;

            if (String.IsNullOrEmpty(this.txMultiDescrizione.Value))
            {
                this.txMultiDescrizione.Value = "Assegnazione presenze del mercato " + this.rplMultiMercato.Text + " per il giorno " + this.rplMultiMercatiUso.Text;
            }

        }
        #endregion

        #region Spuntisti
        private void BindGridSpuntisti( MercatiPresenzeT cls )
        {
            MercatiPresenzeD m = new MercatiPresenzeD();
            m.Idcomune = cls.Idcomune;
            m.Fkidtestata = cls.Id;
            m.Spuntista = 1;
            m.UseForeign = useForeignEnum.Yes;

            this.rptSpuntisti.DataSource = new MercatiPresenzeDMgr(Database).GetList(m, "Anagrafe");
            this.rptSpuntisti.DataBind();
        }

        public void DeleteItem(object sender, ImageClickEventArgs e)
        {
            ImageButton img = (ImageButton)sender;

            MercatiPresenzeD m = new MercatiPresenzeD();
            m.Idcomune = IdComune;
            m.Id = Convert.ToInt32(img.CommandArgument);

            MercatiPresenzeDMgr mgr = new MercatiPresenzeDMgr(Database);

            MercatiPresenzeT cls = new MercatiPresenzeT();
            cls.Idcomune = m.Idcomune;
            cls.Id = m.Fkidtestata;

            mgr.Delete(m);

            BindGridSpuntisti(cls);
        }
        protected void rptSpuntisti_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            foreach (RepeaterItem r in rptSpuntisti.Items)
            {
                ImpostaScriptEliminazione((r.FindControl("imgCancella") as ImageButton));
            }
        }
        private void GetPresenzeSpuntisti(List<MercatiPresenzeD> arrayPresenze)
        {
            MercatiPresenzeT cls = new MercatiPresenzeTMgr(Database).GetById(IdComune, IdTestata.GetValueOrDefault(int.MinValue));

            foreach (RepeaterItem r in rptSpuntisti.Items)
            {
                if (r.ItemType == ListItemType.Item || r.ItemType == ListItemType.AlternatingItem)
                {
                    TextBox txCodiceAnagrafe = ((TextBox)r.FindControl("txCodiceAnagrafe"));
                    TextBox txPosteggio = ((TextBox)r.FindControl("txPosteggio"));
                    IntTextBox txPresenze = ((IntTextBox)r.FindControl("txPresenze"));

                    MercatiPresenzeD presenza = new MercatiPresenzeD();
                    presenza.Codiceanagrafe = txCodiceAnagrafe.Text;

                    if (!String.IsNullOrEmpty(txPosteggio.Text))
                    {
                        Mercati_D posteggio = new Mercati_D();
                        posteggio.IDCOMUNE = IdComune;
                        posteggio.FKCODICEMERCATO = cls.Fkcodicemercato;
                        posteggio.CODICEPOSTEGGIO = txPosteggio.Text;
                        posteggio.DISABILITATO = "0";

                        posteggio = new Mercati_DMgr(Database).GetByClass(posteggio);
                        if (posteggio == null)
                            throw new Exception("Attenzione, il posteggio n°" + txPosteggio.Text + " assegnato allo spuntista non esiste.");
                        presenza.Fkidposteggio = posteggio.IDPOSTEGGIO;
                    }

                    presenza.Fkidtestata = cls.Id;
                    presenza.Idcomune = cls.Idcomune;
                    presenza.Numeropresenze = txPresenze.ValoreInt.GetValueOrDefault(0);
                    presenza.Spuntista = 1;

                    arrayPresenze.Add(presenza);
                }
            }
        }
        private void ViewSpuntisti()
        {
            if (ViewState["ViewSpuntisti"] == null)
                ViewState["ViewSpuntisti"] = true;

            if (Convert.ToBoolean(ViewState["ViewSpuntisti"]))
                this.imgExpSpuntisti.ImageUrl = "~/Images/tee_1.gif";
            else
                this.imgExpSpuntisti.ImageUrl = "~/Images/tee_0.gif";

            this.pnlDettSpuntisti.Visible = Convert.ToBoolean(ViewState["ViewSpuntisti"]);
        }
        protected void imgExpSpuntisti_Click(object sender, ImageClickEventArgs e)
        {
            ViewState["ViewSpuntisti"] = !this.pnlDettSpuntisti.Visible;
            BindDettaglio(new MercatiPresenzeTMgr(Database).GetById(IdComune, IdTestata.GetValueOrDefault(int.MinValue), useForeignEnum.Yes));
        }
        #endregion

        #region Occupanti
        private void BindRepeater()
        {
            this.rptDettaglio.DataSource = GetPosteggi();
            this.rptDettaglio.DataBind();
        }
        private List<Mercati_D> GetPosteggi( )
        {
            MercatiPresenzeT cls = new MercatiPresenzeTMgr(Database).GetById(IdComune, IdTestata.GetValueOrDefault(int.MinValue));

            Init.SIGePro.Data.Mercati_D posteggio = new Init.SIGePro.Data.Mercati_D();
            posteggio.IDCOMUNE = IdComune;
            posteggio.FKCODICEMERCATO = cls.Fkcodicemercato;
            posteggio.OrderBy = "CODICEPOSTEGGIO ASC";

            return new Mercati_DMgr(Database).GetList(posteggio);
        }
        protected void rptDettaglio_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.AlternatingItem || e.Item.ItemType == ListItemType.Item)
            {
                //imposto il posteggio
                Mercati_D posteggio = ((Mercati_D)e.Item.DataItem);
                PresenzaPosteggio p = (PresenzaPosteggio)e.Item.FindControl("PresenzaPosteggio1");
                p.Posteggio = posteggio;

                //e la presenza
                MercatiPresenzeD mpd = new MercatiPresenzeD();

                mpd.Idcomune = IdComune;
                mpd.Fkidtestata = IdTestata;
                mpd.Fkidposteggio = posteggio.IDPOSTEGGIO;
                mpd.Spuntista = 0;

                mpd = new MercatiPresenzeDMgr(Database).GetByClass(mpd);

                if (mpd!=null)
                    p.IdDettaglio = mpd.Id.GetValueOrDefault(int.MinValue);
            }
        }
        private void GetPresenzeOccupanti(List<MercatiPresenzeD> arrayPresenze)
        {
            foreach (RepeaterItem r in rptDettaglio.Items)
            {
                if (r.ItemType == ListItemType.Item || r.ItemType == ListItemType.AlternatingItem)
                {
                    PresenzaPosteggio p = ((PresenzaPosteggio)r.FindControl("PresenzaPosteggio1"));
                    if (p.CodiceAnagrafe > int.MinValue)
                    {
                        MercatiPresenzeD filtro = arrayPresenze.Find(delegate(MercatiPresenzeD m) { return m.Fkidposteggio == p.IdPosteggio; });
                        

                        MercatiPresenzeD presenza = new MercatiPresenzeD();
                        presenza.Codiceanagrafe = p.CodiceAnagrafe.ToString();
                        presenza.Fkidposteggio = p.IdPosteggio;
                        presenza.Fkidtestata = IdTestata;
                        presenza.Idcomune = IdComune;
                        presenza.Numeropresenze = ( filtro == null ) ? p.NumeroPresenze : 0;
                        presenza.Spuntista = 0;
                        arrayPresenze.Add(presenza);


                        
                    }
                }
            }
        }
        private void ViewOccupanti()
        {
            if (ViewState["ViewOccupanti"] == null)
                ViewState["ViewOccupanti"] = true;

            if (Convert.ToBoolean(ViewState["ViewOccupanti"]))
                this.imgExpOccupanti.ImageUrl = "~/Images/tee_1.gif";
            else
                this.imgExpOccupanti.ImageUrl = "~/Images/tee_0.gif";

            this.pnlDettPosteggi.Visible = Convert.ToBoolean(ViewState["ViewOccupanti"]);
        }
        protected void imgExpOccupanti_Click(object sender, ImageClickEventArgs e)
        {
            ViewState["ViewOccupanti"] = !this.pnlDettPosteggi.Visible;
            BindDettaglio(new MercatiPresenzeTMgr(Database).GetById(IdComune, IdTestata.GetValueOrDefault(int.MinValue), useForeignEnum.Yes));
        }
        #endregion

        #region metodi di ricerca di ricercheplus
        [System.Web.Services.WebMethodAttribute(), System.Web.Script.Services.ScriptMethodAttribute()]
        public static string[] GetCompletionListUsi(string token, string dataClassType,
                                                  string targetPropertyName,
                                                  string descriptionPropertyNames,
                                                  string prefixText,
                                                  int count,
													string software,
													bool ricercaSoftwareTT,
                                                  Dictionary<string, string> initParams, string FkCodiceMercato)
        {
            try
            {
                RicerchePlusSearchComponent sc = new RicerchePlusSearchComponent(token, dataClassType, targetPropertyName, descriptionPropertyNames, prefixText, count, software , ricercaSoftwareTT, initParams);

                // Gestione di una ricerca custom
                sc.Searching += delegate(object sender, RicerchePlusEventArgs e)
                {
                    Init.SIGePro.Data.Mercati_Uso mercati_uso = (Init.SIGePro.Data.Mercati_Uso)e.SearchedClass;
                    mercati_uso.FkCodiceMercato = Convert.ToInt32( FkCodiceMercato );
                };

                return RicerchePlusCtrl.CreateResultList(sc.Find(true));
            }
            catch (Exception ex)
            {
                return RicerchePlusCtrl.CreateErrorResult(ex);
            }
        }

        #endregion

        protected void cmdRegContabile_Click(object sender, EventArgs e)
        {
            BindDettaglio(new MercatiPresenzeTMgr(Database).GetById(IdComune, IdTestata.GetValueOrDefault(int.MinValue), useForeignEnum.Yes));
        }

    }
}
