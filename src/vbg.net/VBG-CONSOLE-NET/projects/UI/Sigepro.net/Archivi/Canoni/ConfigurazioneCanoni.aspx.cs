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
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using PersonalLib2.Sql;
using Init.SIGePro.Exceptions;
using System.Collections.Generic;
using Init.SIGePro.Manager.Logic.Ricerche;
using SIGePro.WebControls.Ajax;
using Init.Utils.Web.UI;

namespace Sigepro.net.Archivi.Canoni
{
    public partial class ConfigurazioneCanoni : BasePage
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            ImpostaScriptEliminazione(cmdElimina);

            BindComboTipoRipartizioneOmi();

            if (multiView.ActiveViewIndex == 1)
            {
                BindGridCoefficienti();
                BindListPertinenze();
                BindGridPertinenze();
            }
        }

        private void BindGridConfiguraioneAree()
        {
            this.rptConfigAree.DataBind();
        }

        private void BindComboTipoRipartizioneOmi()
        {
            if (this.ddlTipoRipartizioneOmi.Item.Items.Count == 0)
            {
                this.ddlTipoRipartizioneOmi.Item.Items.Add(new ListItem("Superficie complessiva", "1"));
                this.ddlTipoRipartizioneOmi.Item.Items.Add(new ListItem("Superficie parziale", "2"));
            }
        }
        
        protected void multiView_ActiveViewChanged(object sender, EventArgs e)
        {
            switch (multiView.ActiveViewIndex)
            {
                case (1):
                    Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
                    return;
                default:
                    Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;
                    return;
            }
        }
        private void BindDettaglio(CanoniConfigurazione cc)
        {
            this.IsInserting = cc.Anno.GetValueOrDefault(int.MinValue) == int.MinValue;

            this.txAnno.Item.ValoreInt = cc.Anno;
            this.txPercAddizRegionale.Item.ValoreDouble = cc.PercAddizRegionale.GetValueOrDefault(double.MinValue);

            this.rplCausaleAddRegionale.Value = (cc.FkCoId.GetValueOrDefault(int.MinValue) == int.MinValue) ? string.Empty : cc.FkCoId.ToString();
            if (cc.CausaleAddizionaleRegionale != null)
                this.rplCausaleAddRegionale.Text = cc.CausaleAddizionaleRegionale.CoDescrizione;
            else
                this.rplCausaleAddRegionale.Text = "";

            this.txPercAddizComunale.Item.ValoreDouble = cc.PercAddizComunale.GetValueOrDefault(double.MinValue);

            this.rplCausaleAddComunale.Value = (cc.FkCoIdAddizComunale.GetValueOrDefault(int.MinValue) == int.MinValue) ? string.Empty : cc.FkCoIdAddizComunale.ToString();
            if (cc.CausaleAddizionaleComunale != null)
                this.rplCausaleAddComunale.Text = cc.CausaleAddizionaleComunale.CoDescrizione;
            else
                this.rplCausaleAddComunale.Text = "";

            this.txValoreBaseOMI.Item.ValoreDouble = cc.ValoreBaseOMI.GetValueOrDefault(double.MinValue);
            if (cc.TipoRipartizioneOMI.GetValueOrDefault(int.MinValue) != int.MinValue)
                this.ddlTipoRipartizioneOmi.Item.SelectedValue = cc.TipoRipartizioneOMI.ToString();

            this.rplCausaleTotale.Value = (cc.FkCoIdTotale.GetValueOrDefault(int.MinValue) == int.MinValue) ? string.Empty : cc.FkCoIdTotale.ToString();
            if (cc.CausaleTotaleCalcolo != null)
                this.rplCausaleTotale.Text = cc.CausaleTotaleCalcolo.CoDescrizione;
            else
                this.rplCausaleTotale.Text = "";

            this.txCanoneMinimo.Item.ValoreDouble = cc.CanoneMinimo.GetValueOrDefault(double.MinValue);
            this.cmdSalva.Visible = true;
            this.cmdCopiaConfigurazione.Visible = IsInserting;
            this.cmdConfigurazioneCanoni.Visible = !IsInserting;
            this.cmdElimina.Visible = !IsInserting;
            this.txAnno.Item.ReadOnly = !IsInserting;


            CanoniTipiSuperfici cts = new CanoniTipiSuperfici();
            cts.Idcomune = IdComune;
            cts.Software = Software;
            cts.Pertinenza = 1;
            List<CanoniTipiSuperfici> l = new CanoniTipiSuperficiMgr(Database).GetList(cts);

            this.cmdConfigurazionePertinenze.Visible = !IsInserting && l.Count > 0;
            
            // se la configurazione è stata utilizzata in qualche calcolo non posso fare modifiche e tolgo i bottoni
            //salva e elimina
            if (!IsInserting)
            {
                IstanzeCalcoloCanoniT filtro = new IstanzeCalcoloCanoniT();
                filtro.Idcomune = cc.Idcomune;
                filtro.Anno = cc.Anno;

                List<IstanzeCalcoloCanoniT> list = new IstanzeCalcoloCanoniTMgr(Database).GetList(filtro);

                this.cmdSalva.Visible = (list.Count == 0);
                this.cmdElimina.Visible = (list.Count == 0);
                this.cmdSalvaCoefficienti.Visible = (list.Count == 0);
                this.cmdSalvaPertinenze.Visible = (list.Count == 0);

                BindGridConfiguraioneAree();

                this.cmdAggiungiCoefficiente.Attributes.Add("onClick", "AggiungiCoefficiente('" + Token + "','" + Software + "','" + this.txAnno.Value + "'); return false;");
            }

            BindGridCoefficienti();

            BindListPertinenze();
            BindGridPertinenze();
        }
        private List<CanoniCategorie> GetColumns()
        {
            //arraylist contenente i nomi delle colonne
            CanoniCategorie cc = new CanoniCategorie();
            cc.Idcomune = AuthenticationInfo.IdComune;
            cc.Software = Software;
            cc.OrderBy = "Descrizione";

            return new CanoniCategorieMgr(AuthenticationInfo.CreateDatabase()).GetList(cc);
        }

        #region Lista
        protected void cmdNuovo_Click(object sender, EventArgs e)
        {
            BindDettaglio(new CanoniConfigurazione());

            this.pnlConfigTipiSuperfici.Visible = false;
            this.pnlConfigPertinenze.Visible = false;
            this.pnlConfigurazione.Visible = false;

            this.multiView.ActiveViewIndex = 1;
        }
        protected void cmdCloseList_Click(object sender, EventArgs e)
        {
            base.CloseCurrentPage();
        }
        protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
        {
            int anno = Convert.ToInt32(gvLista.DataKeys[gvLista.SelectedIndex].Value);
            CanoniConfigurazione cc = new CanoniConfigurazioneMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, anno, useForeignEnum.Yes);
            BindDettaglio(cc);
            this.pnlConfigTipiSuperfici.Visible = false;
            this.pnlConfigPertinenze.Visible = false;
            this.pnlConfigurazione.Visible = false;

            this.multiView.ActiveViewIndex = 1;
        }
        #endregion

        #region Scheda
        protected void cmdSalva_Click(object sender, EventArgs e)
        {
            CanoniConfigurazione cc = new CanoniConfigurazione();

            try 
            {
                cc.Idcomune = IdComune;
                cc.Anno = this.txAnno.Item.ValoreInt;
                cc.CanoneMinimo = this.txCanoneMinimo.Item.ValoreDouble;
                cc.FkCoId = String.IsNullOrEmpty(this.rplCausaleAddRegionale.Value) ? (int?)null : Convert.ToInt32(this.rplCausaleAddRegionale.Value);
                cc.FkCoIdAddizComunale = String.IsNullOrEmpty(this.rplCausaleAddComunale.Value) ? (int?)null : Convert.ToInt32(this.rplCausaleAddComunale.Value);
                cc.FkCoIdTotale = String.IsNullOrEmpty(this.rplCausaleTotale.Value) ? (int?)null : Convert.ToInt32(this.rplCausaleTotale.Value);
                cc.PercAddizComunale = this.txPercAddizComunale.Item.ValoreDouble;
                cc.PercAddizRegionale = this.txPercAddizRegionale.Item.ValoreDouble;
                cc.Software = Software;
                cc.ValoreBaseOMI = this.txValoreBaseOMI.Item.ValoreDouble;
                cc.TipoRipartizioneOMI = Convert.ToInt16(this.ddlTipoRipartizioneOmi.Value);
                
                CanoniConfigurazioneMgr mgr = new CanoniConfigurazioneMgr(AuthenticationInfo.CreateDatabase());

                if (IsInserting)
                    cc = mgr.Insert(cc);
                else
                    cc = mgr.Update(cc);

                cc = mgr.GetById(cc.Idcomune, cc.Anno.GetValueOrDefault(int.MinValue), useForeignEnum.Yes);

                BindDettaglio(cc);
            }
            catch (RequiredFieldException rfe)
            {
                MostraErrore("Attenzione, i campi contrassegnati con un asterisco sono obbligatori.", rfe);
            }
            catch (Exception ex)
            {
                MostraErrore("Errore durante il salvataggio: " + ex.Message, ex);
            }
        }
        protected void cmdElimina_Click(object sender, EventArgs e)
        {
            try
            {
                CanoniConfigurazione cc = new CanoniConfigurazione();

                cc.Idcomune = AuthenticationInfo.IdComune;
                cc.Anno = this.txAnno.Item.ValoreInt;

                CanoniConfigurazioneMgr mgr = new CanoniConfigurazioneMgr(AuthenticationInfo.CreateDatabase());

                mgr.Delete(cc);

                multiView.ActiveViewIndex = 0;
            }
            catch (Exception ex)
            {
                MostraErrore("Errore durante l'eliminazione: " + ex.Message, ex);
            }
        }
        protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
        {
            multiView.ActiveViewIndex = 0;
        }
        protected void imgCancellaZona_Click(object sender, EventArgs e)
        {
            CanoniConfigAree c = new CanoniConfigAree();
            c.Idcomune = IdComune;
            c.Id = Convert.ToInt32( (sender as ImageButton).CommandArgument );

            CanoniConfigAreeMgr mgr = new CanoniConfigAreeMgr(Database);
            mgr.Delete(c);

            this.rptConfigAree.DataBind();
        }

        protected void rptConfigAree_ItemCreated(object sender, RepeaterItemEventArgs e)
        {
            ImageButton i = (e.Item.FindControl("imgCancellaZona") as ImageButton);
            if (i != null)
                ImpostaScriptEliminazione(i);
        }
        #endregion

        #region metodi di ricerca di ricercheplus



        [System.Web.Services.WebMethodAttribute(), System.Web.Script.Services.ScriptMethodAttribute()]
        public static string[] GetCompletionListCausaliOneri(string token,
                                                    string dataClassType,
                                                    string targetPropertyName,
                                                    string descriptionPropertyNames,
                                                    string prefixText,
                                                    int count,
                                                    string software,
                                                    bool ricercaSoftwareTT,
                                                    Dictionary<string, string> initParams)
        {
            try
            {
                RicerchePlusSearchComponent sc = new RicerchePlusSearchComponent(token, dataClassType, targetPropertyName, descriptionPropertyNames, prefixText, count, software, ricercaSoftwareTT, initParams);

                // Gestione di una ricerca custom
                sc.Searching += delegate(object sender, RicerchePlusEventArgs e)
                {
                    Init.SIGePro.Data.TipiCausaliOneri causali = (Init.SIGePro.Data.TipiCausaliOneri)e.SearchedClass;
                    causali.CoDisabilitato = 0;
                };

                return RicerchePlusCtrl.CreateResultList(sc.Find(true));
            }
            catch (Exception ex)
            {
                return RicerchePlusCtrl.CreateErrorResult(ex);
            }
        }

        #endregion

        #region Copia della configurazione
        protected void cmdCopiaConfigurazione_Click(object sender, EventArgs e)
        {
            BindComboConfigurazioni();
            this.pnlConfigurazione.Visible = true;
            this.pnlConfigTipiSuperfici.Visible = false;
            this.pnlConfigPertinenze.Visible = false;
        }

        protected void BindComboConfigurazioni()
        { 
            CanoniConfigurazione cc = new CanoniConfigurazione();
            cc.Idcomune = IdComune;
            cc.Software = Software;

            this.ddlConfigurazione.Item.DataTextField = "Anno";
            this.ddlConfigurazione.Item.DataValueField = "Anno";
            this.ddlConfigurazione.Item.DataSource = new CanoniConfigurazioneMgr(Database).GetList(cc);
            this.ddlConfigurazione.Item.DataBind();
        }

        protected void cmdOk_Click(object sender, EventArgs e)
        {
            try
            {
                int anno = Convert.ToInt32(this.ddlConfigurazione.Item.SelectedValue);

                CanoniConfigurazioneMgr mgr = new CanoniConfigurazioneMgr(Database);

                CanoniConfigurazione cc = new CanoniConfigurazioneMgr(Database).GetById(IdComune, anno);
                cc.Anno = this.txNuovoAnno.Item.ValoreInt;
                mgr.Insert(cc);

                CanoniCoefficienti filtro = new CanoniCoefficienti();
                filtro.Idcomune = cc.Idcomune;
                filtro.Anno = anno;
                List<CanoniCoefficienti> l = new CanoniCoefficientiMgr(Database).GetList(filtro);
                foreach (CanoniCoefficienti cls in l)
                {
                    cls.Anno = cc.Anno;
                    CanoniCoefficientiMgr ccm = new CanoniCoefficientiMgr(Database);
                    ccm.Insert(cls);
                }

                PertinenzeCoefficienti filtroP = new PertinenzeCoefficienti();
                filtroP.Idcomune = cc.Idcomune;
                filtroP.Anno = anno;
                List<PertinenzeCoefficienti> listP = new PertinenzeCoefficientiMgr(Database).GetList(filtroP);
                foreach (PertinenzeCoefficienti cls in listP)
                {
                    cls.Anno = cc.Anno;
                    PertinenzeCoefficientiMgr pcm = new PertinenzeCoefficientiMgr(Database);
                    pcm.Insert(cls);
                }

                BindDettaglio(cc);

                this.pnlConfigTipiSuperfici.Visible = false;
                this.pnlConfigPertinenze.Visible = false;
                this.pnlConfigurazione.Visible = false;
            }
            catch (RequiredFieldException rfe)
            {
                MostraErrore("Attenzione, per effettuare la copia è necessario specificare: \r\n1 - La configurazione da copiare\r\n2 - L'anno di riferimento", rfe);
            }
            catch (Exception ex)
            {
                MostraErrore(AmbitoErroreEnum.Aggiornamento, ex);
            }
        }

        protected void cmdAnnulla_Click(object sender, EventArgs e)
        {
            this.pnlConfigTipiSuperfici.Visible = false;
            this.pnlConfigPertinenze.Visible = false;
            this.pnlConfigurazione.Visible = false;
        }
        #endregion

        #region Coefficienti
        private List<CanoniTipiSuperfici> GetTipiSuperficiRows(CanoniTipiSuperfici filtro)
        {
            //arraylist contenente i nomi delle righe
            List<CanoniTipiSuperfici> retVal = new List<CanoniTipiSuperfici>();
            retVal = new CanoniTipiSuperficiMgr(AuthenticationInfo.CreateDatabase()).GetList(filtro);

            return retVal;
        }
        protected DataTable GridCoefficientiSource
        {
            get
            {
                if (Session["GridCoefficientiSource"] == null)
                {
                    Session["GridCoefficientiSource"] = SetGridCoefficientiDataSource();
                }

                return (DataTable)Session["GridCoefficientiSource"];
            }
            set { Session["GridCoefficientiSource"] = value; }
        }
        private DataTable SetGridCoefficientiDataSource()
        {
            List<CanoniCategorie> cols = GetColumns();

            CanoniTipiSuperfici cts = new CanoniTipiSuperfici();
            cts.Idcomune = AuthenticationInfo.IdComune;
            cts.Software = Software;
            cts.Pertinenza = 0;
            cts.OrderBy = "TipoSuperficie";

            List<CanoniTipiSuperfici> rows = GetTipiSuperficiRows(cts);

            DataTable dt = new DataTable();
            DataColumn dc = new DataColumn();
            dc.ColumnName = "TIPOSUPERFICIE";
            dc.Caption = "Tipo superficie";
            dt.Columns.Add(dc);

            foreach (CanoniCategorie col in cols)
            {
                dc = new DataColumn();
                dc.ColumnName = col.Descrizione;
                dc.Caption = col.Descrizione;
                dt.Columns.Add(dc);
            }

            foreach (CanoniTipiSuperfici row in rows)
            {
                DataRow dr = dt.NewRow();

                dr["TIPOSUPERFICIE"] = row.TipoSuperficie;

                foreach (CanoniCategorie col in cols)
                {
                    dr[col.Descrizione] = "C_" + row.Id.ToString() + "_" + col.Id.ToString();
                }

                dt.Rows.Add(dr);
            }
            return dt;
        }
        private void BindGridCoefficienti()
        {
            gvCoefficienti.DataSource = GridCoefficientiSource;
            gvCoefficienti.DataBind();
        }
        protected void gvCoefficienti_RowDataBound(object sender, GridViewRowEventArgs e)
        {
            if (e.Row.RowType == DataControlRowType.Header)
            {
                e.Row.Cells[0].Text = "Tipo superficie";
            }
            else if (e.Row.RowType == DataControlRowType.DataRow)
            {
                for (int i = 1; i < e.Row.Cells.Count; i++)
                {
                    TableCell tc = e.Row.Cells[i];
                    string[] arVal = tc.Text.Split(Convert.ToChar("_"));

                    //per ogni cella inserisco una griglia che mostri gli oneri da poter impostare
                    int? FkCcId = null;
                    int? FkTsId = null;

                    FkTsId = Convert.ToInt32(arVal[1]);
                    FkCcId = Convert.ToInt32(arVal[2]);

                    CanoniCoefficienti cc = new CanoniCoefficienti();
                    cc.Idcomune = IdComune;
                    cc.Software = Software;
                    cc.FkCcId = FkCcId;
                    cc.FkTsId = FkTsId;
                    cc.Anno = this.txAnno.Item.ValoreInt;

                    if (cc.Anno > int.MinValue)
                        cc = new CanoniCoefficientiMgr(Database).GetByClass(cc);
                    else
                        cc = null;

                    DoubleTextBox dtb = new DoubleTextBox();
                    dtb.ID = "txCoefficiente_" + arVal[1] + "_" + arVal[2];
                    dtb.FormatString = "N8";
                    dtb.Columns = 15;
                    if (cc != null)
                        dtb.ValoreDouble = cc.Coefficiente.GetValueOrDefault(double.MinValue);

                    tc.Text = "";
                    tc.Controls.Add(dtb);
                }
            }
        }
        protected void cmdConfigurazioneCanoni_Click(object sender, EventArgs e)
        {
            this.pnlConfigTipiSuperfici.Visible = true;
            this.pnlConfigPertinenze.Visible = false;
            this.pnlConfigurazione.Visible = false;
            
            Session["GridCoefficientiSource"] = null;
            BindGridCoefficienti();
        }
        protected void cmdSalvaCoefficienti_Click(object sender, EventArgs e)
        {
            Database.BeginTransaction();

            CanoniCoefficienti cc = new CanoniCoefficienti();
            cc.Idcomune = IdComune;
            cc.Software = Software;
            cc.Anno = this.txAnno.Item.ValoreInt;

            CanoniCoefficientiMgr mgr = new CanoniCoefficientiMgr(Database);
            mgr.DeleteDettagli(cc);

            foreach (GridViewRow gvr in gvCoefficienti.Rows)
            {
                if (gvr.RowType == DataControlRowType.DataRow)
                {
                    for (int i = 1; i < gvr.Cells.Count; i++)
                    {
                        TableCell tc = gvr.Cells[i];
                        DoubleTextBox dtb = (tc.Controls[0] as DoubleTextBox);

                        if (dtb.ValoreDouble > 0)
                        {
                            string sName = dtb.ID;
                            string[] arValori = sName.Split(Convert.ToChar("_"));

                            cc = new CanoniCoefficienti();
                            cc.Idcomune = IdComune;
                            cc.Anno = this.txAnno.Item.ValoreInt;
                            cc.Coefficiente = dtb.ValoreDouble;
                            cc.FkCcId = Convert.ToInt32(arValori[2]);
                            cc.FkTsId = Convert.ToInt32(arValori[1]);
                            cc.Software = Software;

                            mgr.Insert(cc);
                        }
                    }
                }
            }

            Database.CommitTransaction();
        }
        #endregion

        #region Pertinenze
        protected DataTable GridRiduzioniOMISource
        {
            get
            {
                if (Session["GridPertinenzeSource"] == null)
                {
                    Session["GridPertinenzeSource"] = SetGridRiduzioniOMIDataSource();
                }

                return (DataTable)Session["GridPertinenzeSource"];
            }
            set { Session["GridPertinenzeSource"] = value; }
        }
        private DataTable SetGridRiduzioniOMIDataSource()
        {
            List<CanoniCategorie> cols = GetColumns();
            List<CanoniRiduzioniOMI> rows = GetRiduzioniOMIRows();

            DataTable dt = new DataTable();
            DataColumn dc = new DataColumn();
            dc.ColumnName = "RIDUZIONE";
            dc.Caption = "Riduzione OMI";
            dt.Columns.Add(dc);

            foreach (CanoniCategorie col in cols)
            {
                dc = new DataColumn();
                dc.ColumnName = col.Descrizione;
                dc.Caption = col.Descrizione;
                dt.Columns.Add(dc);
            }

            foreach (CanoniRiduzioniOMI row in rows)
            {
                DataRow dr = dt.NewRow();

                dr["RIDUZIONE"] = row.Descrizione;

                foreach (CanoniCategorie col in cols)
                {
                    dr[col.Descrizione] = "C_" + row.Id.ToString() + "_" + col.Id.ToString();
                }

                dt.Rows.Add(dr);
            }
            return dt;
        }
        private List<CanoniRiduzioniOMI> GetRiduzioniOMIRows()
        {
            //arraylist contenente i nomi delle righe
            CanoniRiduzioniOMI filtro = new CanoniRiduzioniOMI();
            filtro.Idcomune = AuthenticationInfo.IdComune;
            filtro.Software = Software;
            filtro.OrderBy = "Descrizione";

            List<CanoniRiduzioniOMI> retVal = new List<CanoniRiduzioniOMI>();
            retVal = new CanoniRiduzioniOMIMgr(AuthenticationInfo.CreateDatabase()).GetList(filtro);

            return retVal;
        }
        private void BindGridPertinenze()
        {
            gvPertinenze.DataSource = GridRiduzioniOMISource;
            gvPertinenze.DataBind();
        }
        private void BindListPertinenze()
        {
            if (ddlPertinenze.Item.Items.Count == 0)
            {
                CanoniTipiSuperfici cts = new CanoniTipiSuperfici();
                cts.Idcomune = AuthenticationInfo.IdComune;
                cts.Software = Software;
                cts.Pertinenza = 1;
                cts.OrderBy = "TipoSuperficie";

                ddlPertinenze.Item.DataTextField = "TipoSuperficie";
                ddlPertinenze.Item.DataValueField = "Id";

                ddlPertinenze.Item.DataSource = GetTipiSuperficiRows(cts);
                ddlPertinenze.Item.DataBind();
            }
        }
        protected void cmdSalvaPertinenze_Click(object sender, EventArgs e)
        {
            Database.BeginTransaction();

            PertinenzeCoefficienti pc = new PertinenzeCoefficienti();
            pc.Idcomune = IdComune;
            pc.Anno = this.txAnno.Item.ValoreInt;
            pc.FkTsid = Convert.ToInt32(ddlPertinenze.Item.SelectedValue);

            PertinenzeCoefficientiMgr mgr = new PertinenzeCoefficientiMgr(Database);
            mgr.DeleteDettagli(pc);

            foreach (GridViewRow gvr in gvPertinenze.Rows)
            {
                if (gvr.RowType == DataControlRowType.DataRow)
                {
                    for (int i = 1; i < gvr.Cells.Count; i++)
                    {
                        TableCell tc = gvr.Cells[i];
                        DoubleTextBox dtb = (tc.Controls[0] as DoubleTextBox);

                        if (dtb.ValoreDouble > double.MinValue)
                        {
                            string sName = dtb.ID;
                            string[] arValori = sName.Split(Convert.ToChar("_"));

                            pc = new PertinenzeCoefficienti();
                            pc.Idcomune = IdComune;
                            pc.Anno = this.txAnno.Item.ValoreInt;
                            pc.FkTsid = Convert.ToInt32(ddlPertinenze.Item.SelectedValue);
                            pc.FkCrid = Convert.ToInt32(arValori[1]);
                            pc.FkCcid = Convert.ToInt32(arValori[2]);
                            pc.PercRiduzione = dtb.ValoreDouble;

                            mgr.Insert(pc);
                        }

                    }
                }
            }

            Database.CommitTransaction();
        }
        protected void cmdConfigurazionePertinenze_Click(object sender, EventArgs e)
        {
            this.pnlConfigTipiSuperfici.Visible = false;
            this.pnlConfigurazione.Visible = false;
            this.pnlConfigPertinenze.Visible = true;

            Session["GridPertinenzeSource"] = null;
            BindGridPertinenze();
        }
        protected void gvPertinenze_RowDataBound(object sender, GridViewRowEventArgs e)
        {
            if (e.Row.RowType == DataControlRowType.DataRow)
            {
                for (int i = 1; i < e.Row.Cells.Count; i++)
                {
                    TableCell tc = e.Row.Cells[i];
                    string[] arVal = tc.Text.Split(Convert.ToChar("_"));

                    //per ogni cella inserisco una griglia che mostri gli oneri da poter impostare
                    int FkCrId = int.MinValue;
                    int FkCcId = int.MinValue;

                    FkCrId = Convert.ToInt32(arVal[1]);
                    FkCcId = Convert.ToInt32(arVal[2]);

                    PertinenzeCoefficienti pc = new PertinenzeCoefficienti();
                    pc.Idcomune = IdComune;
                    pc.Anno = this.txAnno.Item.ValoreInt;
                    pc.FkCcid = FkCcId;

                    pc.FkTsid = Convert.ToInt32(ddlPertinenze.Item.SelectedValue);
                    pc.FkCrid = FkCrId;

                    if (pc.Anno.GetValueOrDefault(int.MinValue) > int.MinValue)
                        pc = new PertinenzeCoefficientiMgr(Database).GetByClass(pc);
                    else
                        pc = null;

                    DoubleTextBox dtb = new DoubleTextBox();
                    dtb.ID = "txRiduzioneOMI_" + arVal[1] + "_" + arVal[2];
                    dtb.Columns = 5;
                    if (pc != null)
                        dtb.ValoreDouble = pc.PercRiduzione.GetValueOrDefault(double.MinValue);

                    //tc.Text = "";
                    tc.Controls.Add(dtb);
                }
            }
        }
        protected void ddlPertinenze_ValueChanged(object sender, EventArgs e)
        {
            BindGridPertinenze();
        }

        #endregion
    }
}
