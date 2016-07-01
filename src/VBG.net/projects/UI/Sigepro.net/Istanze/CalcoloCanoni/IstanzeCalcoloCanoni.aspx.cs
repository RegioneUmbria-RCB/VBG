using System;
using System.Linq;
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
using Init.SIGePro.Manager;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using PersonalLib2.Sql;
using Init.Utils.Web.UI;
using System.Collections.Generic;
using Init.SIGePro.Manager.Logic.Ricerche;
using SIGePro.WebControls.Ajax;
using Init.Utils.Sorting;
using System.Drawing;
using Init.SIGePro.Manager.Logic.GestioneOneri;

namespace Sigepro.net.Istanze.CalcoloCanoni
{
    public partial class IstanzeCalcoloCanoni : BasePage
    {
        #region proprietà
        public override string Software
        {
            get
            {
                return Istanza.SOFTWARE;
            }
        }
        protected int? CodiceIstanza
        {
            get { return String.IsNullOrEmpty( Request.QueryString["codiceistanza"] ) ? (int?)null : Convert.ToInt32(Request.QueryString["codiceistanza"]); }
        }
        Init.SIGePro.Data.Istanze m_istanza = null;
        private Init.SIGePro.Data.Istanze Istanza
        {
            get
            {
                if (m_istanza == null)
                    m_istanza = new IstanzeMgr(AuthenticationInfo.CreateDatabase()).GetById(IdComune, CodiceIstanza.Value);

                return m_istanza;
            }
        }
        #endregion

        #region Metodi di Databind
        public override void DataBind()
        {
            gvLista.DataSource = new IstanzeCalcoloCanoniTMgr(AuthenticationInfo.CreateDatabase()).GetList(IdComune, CodiceIstanza.GetValueOrDefault(int.MinValue));
            gvLista.DataBind();

            multiView.ActiveViewIndex = 0;
        }

        
        private void BindDropDownListTipoCalcolo()
        {
            this.ddlTipoCalcolo.Items.Clear();
            this.ddlTipoCalcolo.Items.Add(new ListItem("", ""));
            this.ddlTipoCalcolo.Items.Add(new ListItem("ANNUALE", "A"));
            this.ddlTipoCalcolo.Items.Add(new ListItem("STAGIONALE", "S"));

        }
         
        private void BindDettaglio(IstanzeCalcoloCanoniT cls)
        {
            BindConfigurazioni( cls.Anno.GetValueOrDefault(int.MinValue) );
            BindDropDownListTipoCalcolo();
            IsInserting = cls.Id.GetValueOrDefault(int.MinValue) == int.MinValue;
            cmdEliminaCalcolo.Visible = !IsInserting;
            cmdRiportaOneri.Visible = !IsInserting;
            cmdStampa.Visible = !IsInserting;
            pnlDettaglioCalcolo.Visible = !IsInserting;
            this.ddlConfigurazione.Enabled = IsInserting;

            if (IsInserting)
            {
                BindInserimento(cls);
            }
            else
            {
                BindAggiornamento(cls);
            }

            multiView.ActiveViewIndex = 1;
        }
        private void BindInserimento(IstanzeCalcoloCanoniT cls)
        {
            //recupero le addizionali dalla configurazione
            CanoniConfigurazione cc = new CanoniConfigurazioneMgr(Database).GetById(IdComune, Convert.ToInt32(this.ddlConfigurazione.Item.SelectedValue));

            this.lblImportoMinimo.Value = (cc.CanoneMinimo.GetValueOrDefault(double.MinValue) > double.MinValue) ? cc.CanoneMinimo.Value.ToString("N2") : "";

            this.txPercAddizRegionale.Item.ValoreDouble = cc.PercAddizRegionale.GetValueOrDefault(double.MinValue);
            this.txPercAddizComunale.Item.ValoreDouble = cc.PercAddizComunale.GetValueOrDefault(double.MinValue);

            this.lblCodice.Value = "Nuovo";
            this.txDescrizione.Value = cls.Descrizione;

        }
        private void BindAggiornamento(IstanzeCalcoloCanoniT cls)
        {
            this.lblCodice.Value = cls.Id.ToString();
            this.ddlConfigurazione.Item.SelectedValue = cls.Anno.ToString();
            this.txDescrizione.Value = cls.Descrizione;
            this.txPercAddizRegionale.Item.ValoreDouble = cls.Percaddizregionale.GetValueOrDefault(double.MinValue);
            this.txPercAddizComunale.Item.ValoreDouble = cls.Percaddizcomunale.GetValueOrDefault(double.MinValue);
            //this.ddlTipoCalcolo.SelectedValue = cls.Tipocalcolo;

            List<IstanzeCalcoloCanoniD> listccd = GetRepeaterList();

            if(!String.IsNullOrEmpty(cls.Tipocalcolo))
            {
                this.ddlTipoCalcolo.Items.Clear();
                this.ddlTipoCalcolo.Items.Add(new ListItem(cls.Tipocalcolo == "S" ? "STAGIONALE" : "ANNUALE", cls.Tipocalcolo));
            }

            CanoniConfigurazione cc = new CanoniConfigurazioneMgr(Database).GetById(IdComune, cls.Anno.GetValueOrDefault(int.MinValue));
            this.lblImportoMinimo.Value = (cc.CanoneMinimo.GetValueOrDefault(double.MinValue) > double.MinValue) ? cc.CanoneMinimo.Value.ToString("N2") : "";

            this.dtbDa.Visible = this.dtbA.Visible = cls.Tipocalcolo == "S";

            this.dtbDa.Text = "";
            this.dtbA.Text = "";
            this.txMq.Text = "";
            this.txImportoUnitario.Text = "";
            this.txTotParziale.Text = "";
            this.txPercRiduzione.Text = "";
            this.txPeriodo.ValoreDouble = cls.Tipocalcolo == "S" ? 182.5 : 365;

            this.lblTotale.Text = "";
            this.lblTotMetriq.Text = "";
            this.lblAddizionaleComunale.Text = "";
            this.lblAddizionaleRegionale.Text = "";

            string sigeproStampaUrl = BuildSigeproPath("backoffice/cl_stampaletteretipo.asp", "idtestata=" + cls.Id.ToString() + "&docbase=CALCOLOCANONE&CodiceIstanza=" + cls.Codiceistanza.Value.ToString());
            this.cmdStampa.Attributes.Add("onClick", "Stampa('" + sigeproStampaUrl + "'); return false;");

            BindRepeater();
            BindDropDownListCategorie();
            BindDropDownListTipiSuperfici();

            this.ddlAree.Item.SelectedValue = cls.Idconfigarea.ToString();
        }
        private void BindConfigurazioni( int? anno )
        {
            CanoniConfigurazione cc = new CanoniConfigurazione();
            cc.Idcomune = IdComune;
            cc.Software = Software;
            cc.OrderBy = "ANNO DESC";

            this.ddlConfigurazione.Item.DataTextField = "Anno";
            this.ddlConfigurazione.Item.DataValueField = "Anno";

            this.ddlConfigurazione.Item.DataSource = new CanoniConfigurazioneMgr(Database).GetList(cc);
            this.ddlConfigurazione.Item.DataBind();

            //se viene passato l'anno, lo imposto come selezionato
            if( anno != null && anno > int.MinValue )
            {
                for (int i = 0; i < this.ddlConfigurazione.Item.Items.Count; i++)
                {
                    this.ddlConfigurazione.Item.Items[i].Selected = false;

                    if (this.ddlConfigurazione.Item.Items[i].Value == anno.ToString())
                    {
                        this.ddlConfigurazione.Item.Items[i].Selected = true;
                        break;
                    }
                }
            }
            BindAree();
        }
        private void BindAree()
        {
            if (!String.IsNullOrEmpty(this.ddlConfigurazione.Value))
            {
                CanoniConfigAree cca = new CanoniConfigAree();
                cca.Idcomune = IdComune;
                cca.Anno = Convert.ToInt32(this.ddlConfigurazione.Value);
                cca.UseForeign = useForeignEnum.Yes;

                List<CanoniConfigAree> l = new CanoniConfigAreeMgr(Database).GetList(cca);
                ListSortManager<CanoniConfigAree>.Sort(l, "Aree");

                this.ddlAree.Item.DataTextField = "AreaDenominazione";
                this.ddlAree.Item.DataValueField = "Id";

                this.ddlAree.Item.DataSource = l;
                this.ddlAree.Item.DataBind();
            }
            else
            {
                this.ddlAree.Item.Items.Clear();
            }
        }
        private void BindRepeater(List<IstanzeCalcoloCanoniD> l)
        {
            this.rptDettaglio.DataSource = l;
            this.rptDettaglio.DataBind();

            //calcolo l'importo dell'addizionale regionale
            double totale = 0;
            double metri = 0;
            double importo = 0;
            foreach (IstanzeCalcoloCanoniD cls in l)
            {
                importo += cls.Totale.GetValueOrDefault(0);

                if (cls.TipoSuperficie.FlagConteggiaMq == 1)
                    metri += cls.Misura.GetValueOrDefault(0);

                totale += cls.Totale.GetValueOrDefault(0);
            }

            this.lblTotMetriq.Text = metri.ToString();
            this.lblTotale.Text = importo.ToString("N2");

            this.lblTotaleDovuto.Text = this.lblTotale.Text;

            if (importo < double.Parse(lblImportoMinimo.Value))
            {
                this.lblTotaleDovuto.Text = lblImportoMinimo.Value;
                totale = double.Parse(lblImportoMinimo.Value);
            }

            if (this.txPercAddizRegionale.Item.ValoreDouble > 0)
            {
                double totaleRegionale = Math.Round(((totale * this.txPercAddizRegionale.Item.ValoreDouble) / 100), 2);
                this.lblTotaleDovuto.Text = (Convert.ToDouble(this.lblTotaleDovuto.Text) + totaleRegionale).ToString("N2");
                this.lblAddizionaleRegionale.Text = totaleRegionale.ToString("N2");
            }

            if (this.txPercAddizComunale.Item.ValoreDouble > 0)
            {
                double totaleComunale = Math.Round(((totale * this.txPercAddizComunale.Item.ValoreDouble) / 100), 2);
                this.lblTotaleDovuto.Text = (Convert.ToDouble(this.lblTotaleDovuto.Text) + totaleComunale).ToString("N2");
                this.lblAddizionaleComunale.Text = totaleComunale.ToString("N2");
            }

            MostraAvvisoCanoneMinimo();
        }
        private void BindRepeater()
        {
            List<IstanzeCalcoloCanoniD> l = GetRepeaterList();
            BindRepeater(l);
        }
        private List<IstanzeCalcoloCanoniD> GetRepeaterList()
        {
            IstanzeCalcoloCanoniD filtro = new IstanzeCalcoloCanoniD();
            filtro.Idcomune = IdComune;
            filtro.FkIdtestata = Convert.ToInt32(this.lblCodice.Value);
            filtro.OrderBy = "FK_TSID";
            filtro.UseForeign = useForeignEnum.Yes;

            List<IstanzeCalcoloCanoniD> l = new IstanzeCalcoloCanoniDMgr(Database).GetList(filtro);
            return l;
        }
        private void BindDropDownListCategorie()
        {
            CanoniCategorie cc = new CanoniCategorie();
            cc.Idcomune = IdComune;
            cc.Software = Software;
            cc.OrderBy = "DESCRIZIONE";

            List<CanoniCategorie> l = new CanoniCategorieMgr(Database).GetList(cc);
            l.Insert(0, new CanoniCategorie());

            this.ddlCategorie.DataSource = l;
            this.ddlCategorie.DataBind();
        }
        private void BindDropDownListTipiSuperfici()
        {
            CanoniTipiSuperfici cts = new CanoniTipiSuperfici();
            cts.Idcomune = IdComune;
            cts.Software = Software;

            if (!String.IsNullOrEmpty(ddlTipoCalcolo.SelectedValue))
                cts.Tipocalcolo = ddlTipoCalcolo.SelectedValue;

            cts.OrderBy = "TIPOSUPERFICIE";

            List<CanoniTipiSuperfici> l = new CanoniTipiSuperficiMgr(Database).GetList(cts);
            l.Insert(0, new CanoniTipiSuperfici());

            this.ddlTipiSuperfici.DataSource = l;
            this.ddlTipiSuperfici.DataBind();
        }
        #endregion

        protected void Page_Load(object sender, EventArgs e)
        {
            ImpostaScriptEliminazione(cmdEliminaCalcolo); 

            if (!IsPostBack)
            {
                if (!String.IsNullOrEmpty(Request.QueryString["IdCalcolo"]))
                {
                    int id = Convert.ToInt32(Request.QueryString["IdCalcolo"]); 
                    BindDettaglio(new IstanzeCalcoloCanoniTMgr(Database).GetById(IdComune, id));
                }
                else
                {
                    DataBind();
                }
            }
        }
        private void MostraAvvisoCanoneMinimo()
        {
            if( String.IsNullOrEmpty( this.lblTotale.Text ) || String.IsNullOrEmpty( this.lblImportoMinimo.Value ))
            {
                this.hiTotale.Visible = false;
                this.hiAddizionaleComunale.Visible = false;
                this.hiAddizionaleRegionale.Visible = false;
                return;
            }

            bool showIcon = (Convert.ToDouble(this.lblTotale.Text) < Convert.ToDouble(this.lblImportoMinimo.Value));

            this.hiTotale.Visible = showIcon;
            this.hiAddizionaleComunale.Visible = showIcon;
            this.hiAddizionaleRegionale.Visible = showIcon;

            this.lblTotale.CssClass = this.lblDescTotale.CssClass = showIcon ? "ErroreCampo" : "";

        }
        private void GetImportoUnitario(  )
        {
            if ( string.IsNullOrEmpty(this.ddlCategorie.SelectedValue) || Convert.ToInt32(this.ddlCategorie.SelectedValue) == int.MinValue)
                return;


            if ( string.IsNullOrEmpty(this.ddlTipiSuperfici.SelectedValue) || Convert.ToInt32(this.ddlTipiSuperfici.SelectedValue) == int.MinValue)
                return;

            int anno = Convert.ToInt32(this.ddlConfigurazione.Item.SelectedValue);
            int idCategoria = Convert.ToInt32(this.ddlCategorie.SelectedValue);
            int idTipoSuperficie = Convert.ToInt32(this.ddlTipiSuperfici.SelectedValue);

            CanoniCoefficienti cc = new CanoniCoefficienti();
            cc.Idcomune = IdComune;
            cc.Software = Software;
            cc.Anno = anno;
            cc.FkCcId = idCategoria;
            cc.FkTsId = idTipoSuperficie;

            cc = new CanoniCoefficientiMgr(Database).GetByClass(cc);
            if (cc != null)
                this.txImportoUnitario.ValoreDouble = cc.Coefficiente.GetValueOrDefault(double.MinValue);
            else
                this.txImportoUnitario.Text = "";

        }
        protected void gvLista_RowDataBound(object sender, GridViewRowEventArgs e)
        {
            ImageButton cmdElimina = e.Row.FindControl("cmdElimina") as ImageButton;

            if (cmdElimina != null)
                ImpostaScriptEliminazione(cmdElimina);
        }
        protected void gvLista_RowDeleting(object sender, GridViewDeleteEventArgs e)
        {
            int selId = Convert.ToInt32(gvLista.DataKeys[e.RowIndex][0]);

            IstanzeCalcoloCanoniTMgr mgr = new IstanzeCalcoloCanoniTMgr(Database);

            IstanzeCalcoloCanoniT cls = mgr.GetById(IdComune, selId);

            mgr.Delete(cls);

            DataBind();
        }
        protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
        {
            int selId = Convert.ToInt32(gvLista.DataKeys[gvLista.SelectedIndex][0]);

            IstanzeCalcoloCanoniTMgr mgr = new IstanzeCalcoloCanoniTMgr(Database);

            IstanzeCalcoloCanoniT cls = mgr.GetById(IdComune, selId);

            BindDettaglio(cls);
        }
        protected void multiView_ActiveViewChanged(object sender, EventArgs e)
        {
            switch (multiView.ActiveViewIndex)
            {
                case (0):
                    Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;
                    return;
                case (1):
                    Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
                    return;
                case (2):
                    Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
                    return;
            }
        }
        protected void cmdNuovo_Click(object sender, EventArgs e)
        {
            BindDettaglio(new IstanzeCalcoloCanoniT());
        }
        protected void cmdChiudiLista_Click(object sender, EventArgs e)
        {
            base.CloseCurrentPage(); 
        }
        protected void cmdChiudi_Click(object sender, EventArgs e)
        {
            DataBind();
        }
        protected void cmdSalva_Click(object sender, EventArgs e)
        {
            try
            {
                IstanzeCalcoloCanoniT ict = new IstanzeCalcoloCanoniT();
                ict.Idcomune = IdComune;
                ict.Id = IsInserting ? (int?)null : Convert.ToInt32(this.lblCodice.Value);
                ict.Codiceistanza = CodiceIstanza;
                ict.Anno = String.IsNullOrEmpty(this.ddlConfigurazione.Value) ? (int?)null : Convert.ToInt32(this.ddlConfigurazione.Value);
                ict.Descrizione = this.txDescrizione.Value;
                ict.Percaddizregionale = this.txPercAddizRegionale.Item.ValoreDouble;
                ict.Percaddizcomunale = this.txPercAddizComunale.Item.ValoreDouble;
                ict.Idconfigarea = String.IsNullOrEmpty(this.ddlAree.Value) ? (int?)null : Convert.ToInt32(this.ddlAree.Value);
                ict.Tipocalcolo =  this.ddlTipoCalcolo.SelectedValue;

                IstanzeCalcoloCanoniTMgr mgr = new IstanzeCalcoloCanoniTMgr(Database);

                if (IsInserting)
                    ict = mgr.Insert(ict);
                else
                    ict = mgr.Update(ict);

                BindDettaglio(ict);
            }
            catch (RequiredFieldException rfe)
            {
                MostraErrore("Attenzione, i campi contrassegnati con un asterisco sono obbligatori.", rfe);
            }
            catch (Exception ex)
            {
                MostraErrore("Errore durante l'inserimento: " + ex.Message, ex);
            }
        }
        protected void cmdEliminaCalcolo_Click(object sender, EventArgs e)
        {
            int selId = Convert.ToInt32(lblCodice.Value);

            IstanzeCalcoloCanoniTMgr mgr = new IstanzeCalcoloCanoniTMgr(Database);
            IstanzeCalcoloCanoniT cls = mgr.GetById(IdComune, selId);

            mgr.Delete(cls);

            DataBind();

            multiView.ActiveViewIndex = 0;
        }
        protected void rptDettaglio_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.AlternatingItem || e.Item.ItemType == ListItemType.Item)
            {
                IstanzeCalcoloCanoniD cls = (IstanzeCalcoloCanoniD)e.Item.DataItem;

                ((Label)e.Item.FindControl("lblCategoria")).Text = cls.Categoria.ToString();
                ((Label)e.Item.FindControl("lblTipoSuperficie")).Text = cls.TipoSuperficie.ToString();
                
                //l'importo dei metriquadri di questa riga non deve essere conteggiato nel totale 
                if (cls.TipoSuperficie.FlagConteggiaMq == 0)
                {
                    ((Label)e.Item.FindControl("lblMq")).CssClass = "ImportoDisabilitato";
                    ((Label)e.Item.FindControl("lblMq")).ToolTip = "Non conteggiati nel totale dei mq.";

                }
                ((Label)e.Item.FindControl("lblDa")).Text = cls.DataDa.HasValue ? cls.DataDa.Value.ToString("dd/MM/yyyy") : String.Empty;
                ((Label)e.Item.FindControl("lblA")).Text = cls.DataA.HasValue ? cls.DataA.Value.ToString("dd/MM/yyyy") : String.Empty;

                ((Label)e.Item.FindControl("lblMq")).Text = cls.Misura.ToString();
                ((Label)e.Item.FindControl("lblImportoUnitario")).Text = (cls.Coefficiente.GetValueOrDefault(double.MinValue) > double.MinValue) ? cls.Coefficiente.ToString() : "";
                ((Label)e.Item.FindControl("lblPercRiduzione")).Text = (cls.PercRiduzione.GetValueOrDefault(double.MinValue) > double.MinValue) ? cls.PercRiduzione.ToString() : "";
                ((Label)e.Item.FindControl("lblPeriodo")).Text = cls.Periodo.ToString() + " gg.";
                ((Label)e.Item.FindControl("lblTotParziale")).Text = cls.Totale.Value.ToString("N2");
                ((ImageButton)e.Item.FindControl("cmdCancellaDettaglio")).Visible = true;
                ((ImageButton)e.Item.FindControl("cmdCancellaDettaglio")).CommandArgument = cls.Id.ToString();
                ImpostaScriptEliminazione(((ImageButton)e.Item.FindControl("cmdCancellaDettaglio")));
            }
        }
        protected void cmdSalvaDettaglio_Click(object sender, ImageClickEventArgs e)
        {
            IstanzeCalcoloCanoniD icc = new IstanzeCalcoloCanoniD();
            icc.Coefficiente = this.txImportoUnitario.ValoreDouble;
            icc.FkCcid = String.IsNullOrEmpty(this.ddlCategorie.SelectedValue) ? (int?) null : Convert.ToInt32(this.ddlCategorie.SelectedValue);
            icc.FkIdtestata = Convert.ToInt32(this.lblCodice.Value);
            icc.FkTsid = String.IsNullOrEmpty(this.ddlTipiSuperfici.SelectedValue) ? (int?) null : Convert.ToInt32(this.ddlTipiSuperfici.SelectedValue);
            icc.Idcomune = IdComune;
            icc.Misura = this.txMq.ValoreDouble;
            icc.Periodo = (this.txPeriodo.ValoreDouble > double.MinValue) ? this.txPeriodo.ValoreDouble : 365;
            icc.PercRiduzione = (this.txPercRiduzione.ValoreDouble == double.MinValue) ? (double?)null : this.txPercRiduzione.ValoreDouble;
            icc.Totale = this.txTotParziale.ValoreDouble;
            icc.DataDa = dtbDa.DateValue;
            icc.DataA = dtbA.DateValue;

            IstanzeCalcoloCanoniDMgr mgr = new IstanzeCalcoloCanoniDMgr(Database);
            mgr.Insert(icc);

            BindDettaglio(new IstanzeCalcoloCanoniTMgr(Database).GetById(IdComune, Convert.ToInt32( this.lblCodice.Value ) ));
        }
        protected void rptDettaglio_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            int selId = Convert.ToInt32( e.CommandArgument );

            IstanzeCalcoloCanoniDMgr mgr = new IstanzeCalcoloCanoniDMgr(Database);

            IstanzeCalcoloCanoniD cls = mgr.GetById(IdComune, selId);

            mgr.Delete(cls);

            BindDettaglio(new IstanzeCalcoloCanoniTMgr(Database).GetById(IdComune, Convert.ToInt32(this.lblCodice.Value)));

        }
        protected void ddlCategorie_SelectedIndexChanged(object sender, EventArgs e)
        {
            GetImportoUnitario();
        }
        protected void ddlTipiSuperfici_SelectedIndexChanged(object sender, EventArgs e)
        {
            GetImportoUnitario();
        }
        protected void txMq_TextChanged(object sender, EventArgs e)
        {
            SetImportoTotale();
        }
        protected void SetImportoTotale()
        {
            try
            {
                if (String.IsNullOrEmpty(this.ddlTipiSuperfici.SelectedValue)) throw new Exception("La superficie non è stata valorizzata");

                int selId = Convert.ToInt32(this.ddlTipiSuperfici.SelectedValue);
                double totale = 0;

                if (selId == int.MinValue) return;
                if (this.txMq.ValoreDouble == double.MinValue) return;

                CanoniTipiSuperfici cts = new CanoniTipiSuperficiMgr(Database).GetById(IdComune, selId);
                if (cts.Pertinenza == 1)
                {
                    CanoniConfigurazione cc = new CanoniConfigurazioneMgr(Database).GetById(IdComune, Convert.ToInt16(this.ddlConfigurazione.Value));
                    if (cc.TipoRipartizioneOMI == 1)
                    {
                        totale = GetImportoSuperficieComplessiva(cc);
                    }
                    else
                    {
                        totale = GetImportoSperficieParziale(cc);
                    }
                }
                else
                {
                    totale = GetImportoTipoSuperficie();
                }

                //ripartizione del totale per i giorni 
                totale = Convert.ToDouble((totale / 365) * this.txPeriodo.ValoreDouble);

                //applico l'eventuale riduzione
                if (this.txPercRiduzione.ValoreDouble > 0)
                {
                    totale = totale - (totale * this.txPercRiduzione.ValoreDouble / 100);
                }

                this.txTotParziale.Text = totale.ToString("N2");
            }
            catch (Exception ex)
            {
                MostraErrore(ex);
            }
        }
        protected double GetImportoSperficieParziale(CanoniConfigurazione cc)
        {
            double retVal = 0;
            double importo = 0;

            int anno = Convert.ToInt32(this.ddlConfigurazione.Value);
            int idCategoria = Convert.ToInt32(this.ddlCategorie.SelectedValue);
            int idTipoSuperficie = Convert.ToInt32(this.ddlTipiSuperfici.SelectedValue);
            int idConfigAree = Convert.ToInt32(this.ddlAree.Value);
            
            double metriQRimanenti = this.txMq.ValoreDouble;
            double metriQAnalizzati = 0;

            double mediaOmi = cc.ValoreBaseOMI.GetValueOrDefault(double.MinValue);
            double coefficientieOmi = CoefficienteOMI(anno, idConfigAree);

            double metriInteressati = 0;

            PertinenzeCoefficienti filtro = new PertinenzeCoefficienti();
            filtro.Anno = cc.Anno;
            filtro.FkCcid = idCategoria;
            filtro.FkTsid = idTipoSuperficie;
            filtro.Idcomune = IdComune;
            filtro.UseForeign = useForeignEnum.Yes;
            filtro.OrderBy = "CANONI_RIDUZIONIOMI.MQ_A ASC";

            filtro.OthersTables.Add("CANONI_RIDUZIONIOMI");
            filtro.OthersWhereClause.Add("CANONI_RIDUZIONIOMI.IDCOMUNE = PERTINENZE_COEFFICIENTI.IDCOMUNE");
            filtro.OthersWhereClause.Add("CANONI_RIDUZIONIOMI.ID = PERTINENZE_COEFFICIENTI.FK_CRID");

            List<PertinenzeCoefficienti> list = new PertinenzeCoefficientiMgr(Database).GetList(filtro);
            foreach (PertinenzeCoefficienti pc in list)
            {
                if (metriQRimanenti > 0)
                {
                    if (pc.RiduzioneOMI.MqA <= this.txMq.ValoreDouble)
                        metriInteressati = pc.RiduzioneOMI.MqA.GetValueOrDefault(double.MinValue) - metriQAnalizzati;
                    else
                        metriInteressati = metriQRimanenti;

                    importo = (metriInteressati * mediaOmi * coefficientieOmi);
                    metriQAnalizzati += metriInteressati;
                    metriQRimanenti -= metriInteressati;
                    if (importo > 0)
                    {
                        if (pc.PercRiduzione > 0)
                            importo = importo - Convert.ToDouble(importo * pc.PercRiduzione / 100);

                        retVal += importo;
                    }
                }
                else
                {
                    break;
                }

            }

            return retVal;
        }
        protected double GetImportoSuperficieComplessiva(CanoniConfigurazione cc)
        {
            double retVal = 0;
            double importo = 0;

            int anno = Convert.ToInt32(this.ddlConfigurazione.Value);
            int idCategoria = Convert.ToInt32(this.ddlCategorie.SelectedValue);
            int idTipoSuperficie = Convert.ToInt32(this.ddlTipiSuperfici.SelectedValue);
            int idConfigAree = Convert.ToInt32(this.ddlAree.Value);

            double mediaOmi = cc.ValoreBaseOMI.GetValueOrDefault(double.MinValue);
            double coefficientieOmi = CoefficienteOMI(anno, idConfigAree);

            PertinenzeCoefficienti filtro = new PertinenzeCoefficienti();
            filtro.Anno = cc.Anno;
            filtro.FkCcid = idCategoria;
            filtro.FkTsid = idTipoSuperficie;
            filtro.Idcomune = IdComune;
            filtro.UseForeign = useForeignEnum.Yes;
            filtro.OrderBy = "CANONI_RIDUZIONIOMI.MQ_A ASC";

            filtro.OthersTables.Add("CANONI_RIDUZIONIOMI");
            filtro.OthersWhereClause.Add("CANONI_RIDUZIONIOMI.IDCOMUNE = PERTINENZE_COEFFICIENTI.IDCOMUNE");
            filtro.OthersWhereClause.Add("CANONI_RIDUZIONIOMI.ID = PERTINENZE_COEFFICIENTI.FK_CRID");

            List<PertinenzeCoefficienti> list = new PertinenzeCoefficientiMgr(Database).GetList(filtro);
            foreach (PertinenzeCoefficienti pc in list)
            {
                if (pc.RiduzioneOMI.MqA >= this.txMq.ValoreDouble)
                {

                    importo = (this.txMq.ValoreDouble * mediaOmi * coefficientieOmi);
                    if (importo > 0)
                    {
                        if (pc.PercRiduzione > 0)
                            importo = importo - Convert.ToDouble(importo * pc.PercRiduzione / 100);

                        retVal = importo;
                        break;
                    }
                }
            }

            return retVal;
        }
        protected double GetImportoTipoSuperficie()
        {
            if (this.txImportoUnitario.ValoreDouble == double.MinValue) return 0;

            return (this.txImportoUnitario.ValoreDouble * this.txMq.ValoreDouble);
        }
        protected double CoefficienteOMI(int anno, int idconfigaree )
        {
            CanoniConfigAree cca = new CanoniConfigAreeMgr(Database).GetById(IdComune, idconfigaree);

            if (cca != null)
                return cca.CoefficienteOMI.GetValueOrDefault(double.MinValue);

            return double.MinValue;
        }
        protected void cmdRiportaOneri_Click(object sender, EventArgs e)
        {
            CanoniConfigurazione conf = new CanoniConfigurazioneMgr(Database).GetById(IdComune, Convert.ToInt32(this.ddlConfigurazione.Item.SelectedValue));
            
            IstanzeOneri totale = new IstanzeOneri();
            IstanzeOneri addizionalecomunale = new IstanzeOneri();
            IstanzeOneri addizionaleregionale = new IstanzeOneri();

            totale.CODICEISTANZA = CodiceIstanza.ToString();
            totale.CODICEUTENTE = AuthenticationInfo.CodiceResponsabile.ToString();
            totale.DATA = DateTime.Now.Date;
            totale.FKIDTIPOCAUSALE = conf.FkCoIdTotale.ToString();
            totale.IDCOMUNE = IdComune;
            totale.PREZZO = Convert.ToDouble(this.lblTotale.Text);

            double dTotale = string.IsNullOrEmpty(this.lblTotale.Text) ? double.MinValue : Convert.ToDouble(this.lblTotale.Text);
            double dMinimo = string.IsNullOrEmpty(this.lblImportoMinimo.Value) ? double.MinValue : Convert.ToDouble(this.lblImportoMinimo.Value);

            if (dTotale < dMinimo)
                totale.PREZZO = dMinimo;

            #region Addizionale comunale
            if (this.txPercAddizComunale.Item.ValoreDouble > 0)
            {
                double totaleComunale = Math.Round(((totale.PREZZO.GetValueOrDefault(0) * this.txPercAddizComunale.Item.ValoreDouble) / 100), 2);
                if (conf.FkCoIdAddizComunale == conf.FkCoIdTotale)
                {
                    totale.PREZZO += Convert.ToDouble(totaleComunale);
                }
                else
                {
                    addizionalecomunale.CODICEISTANZA = CodiceIstanza.ToString();
                    addizionalecomunale.CODICEUTENTE = AuthenticationInfo.CodiceResponsabile.ToString();
                    addizionalecomunale.DATA = DateTime.Now.Date;
                    addizionalecomunale.FKIDTIPOCAUSALE = conf.FkCoIdAddizComunale.ToString();
                    addizionalecomunale.IDCOMUNE = IdComune;
                    addizionalecomunale.PREZZO = totaleComunale;
                }
            }
            #endregion

            #region Addizionale regionale
            if (this.txPercAddizRegionale.Item.ValoreDouble > 0)
            {
                double totaleRegionale = Math.Round(((totale.PREZZO.GetValueOrDefault(0) * this.txPercAddizRegionale.Item.ValoreDouble) / 100), 2);
                if (conf.FkCoId == conf.FkCoIdTotale)
                {
                    totale.PREZZO += totaleRegionale;
                }
                else if (conf.FkCoId == conf.FkCoIdAddizComunale)
                {
                    addizionalecomunale.PREZZO += totaleRegionale;
                }
                else
                {
                    addizionaleregionale.CODICEISTANZA = CodiceIstanza.ToString();
                    addizionaleregionale.CODICEUTENTE = AuthenticationInfo.CodiceResponsabile.ToString();
                    addizionaleregionale.DATA = DateTime.Now.Date;
                    addizionaleregionale.FKIDTIPOCAUSALE = conf.FkCoId.ToString();
                    addizionaleregionale.IDCOMUNE = IdComune;
                    addizionaleregionale.PREZZO = totaleRegionale;
                }
            }
            #endregion

            try
            {
				Database.BeginTransaction();

                var listaOneri = new List<IstanzeOneri>();
				var oneriService = new OneriService(Token, IdComune);

                listaOneri.Add(totale);

				if (addizionalecomunale.PREZZO.GetValueOrDefault(double.MinValue) > double.MinValue)
				{
					listaOneri.Add(addizionalecomunale);
				}

				if (addizionaleregionale.PREZZO.GetValueOrDefault(double.MinValue) > double.MinValue)
				{
					listaOneri.Add(addizionaleregionale);
				}

				listaOneri = oneriService.Inserisci(listaOneri).ToList();

                IstanzeCalcoloCanoniOMgr mgr = new IstanzeCalcoloCanoniOMgr( Database );

                mgr.InserisciOneri(IdComune, Convert.ToInt32(this.lblCodice.Value), listaOneri);

				Database.CommitTransaction();
            }
            catch (Exception ex)
            {
                Database.RollbackTransaction();

                MostraErrore("Si è verificato un errore durante la copia degli oneri: " + ex.Message ,ex); 
            }
        }
        protected void txImportoUnitario_TextChanged(object sender, EventArgs e)
        {
            txMq_TextChanged(sender, e);
        }
        protected void txPeriodo_TextChanged(object sender, EventArgs e)
        {
            txMq_TextChanged(sender, e);
        }
        protected void ddlConfigurazione_ValueChanged(object sender, EventArgs e)
        {
            BindDettaglio(new IstanzeCalcoloCanoniT());
            BindAree();
        }
        protected void txPercRiduzione_TextChanged(object sender, EventArgs e)
        {
            if (this.txPercRiduzione.ValoreDouble > 100)
            {
                MostraErrore("La percentuale di riduzione specificata non è valida.", null); 
            }
            else
            {
                txMq_TextChanged(sender, e);
            }
        }
        protected void dtbDa_TextChanged(object sender, EventArgs e)
        {
            SetPeriodo();
            dtbA.Focus();
        }
        private void SetPeriodo()
        {
            DateTime? da = dtbDa.DateValue;
            DateTime? a = dtbA.DateValue;

            if (da.HasValue && a.HasValue && da.Value < a.Value)
            {
                TimeSpan ts = a.Value.Subtract(da.Value);
                txPeriodo.ValoreDouble = ts.TotalDays+1;
            }

            SetImportoTotale();

        }
        protected void dtbA_TextChanged(object sender, EventArgs e)
        {
            SetPeriodo();
            txPeriodo.Focus();
        }
    }
}
