using Init.SIGeProExport.Data;
using Init.SIGeProExport.Manager;
using System;
using System.Web.UI;

namespace WebSigeproExport
{
    /// <summary>
    /// Descrizione di riepilogo per Exp.
    /// </summary>
    public partial class Exp : BasePage
    {
        protected System.Web.UI.WebControls.Label prova;
        protected System.Web.UI.WebControls.Button BtnBrowse;




        protected void Page_Load(object sender, System.EventArgs e)
        {
            try
            {

                if (!this.IsPostBack)
                {
                    this.MostraPannello(false);

                    this.BtnElimina.Attributes.Add("OnClick", "return ConfermaElimina('Sei sicuro di voler eliminare questa esportazione e relativi tracciati?');");

                    this.LoadDDTipoExp();
                    this.LoadDDTipoContextExp();

                    this.Esp = new ESPORTAZIONI();
                    if (!String.IsNullOrEmpty(this.qsIdEsportazione))
                    {
                        this.Esp = new EsportazioniMgr(this.DbDestinazione).GetById(this.qsIdComune, this.qsIdEsportazione);
                        this.DDLstType.Enabled = false;
                        this.DDLstTypeContext.Enabled = false;
                        this.txtIdcomune.ReadOnly = true;
                        this.TxtXsd.Text = this.Esp.INPUT_XSD;

                        this.BtnSalva.Enabled = this.ModificaIdcomune;
                        this.BtnPersonalizza.Enabled = true;
                        this.BtnParametri.Enabled = true;
                        this.BtnTracciati.Enabled = true;
                        this.BtnExport.Enabled = true;
                        this.BtnImport.Enabled = false;
                        this.BtnElimina.Enabled = this.ModificaIdcomune;
                    }
                    else
                    {
                        this.DDLstType.Enabled = true;
                        this.DDLstTypeContext.Enabled = true;
                        this.txtIdcomune.ReadOnly = false;

                        this.TxtXsd.Text = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" +
                                            "<xs:schema id=\"LISTAISTANZE\" xmlns=\"\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\r\n" +
                                            "<xs:element name=\"LISTAISTANZE\">\r\n" +
                                            "<xs:complexType>\r\n" +
                                            "<xs:choice maxOccurs=\"unbounded\">\r\n" +
                                            "<xs:element name=\"ISTANZA\">\r\n" +
                                            "<xs:complexType>\r\n" +
                                            "<xs:sequence>\r\n" +
                                            "<xs:element name=\"IDCOMUNE\" type=\"xs:string\" minOccurs=\"0\" />\r\n" +
                                            "<xs:element name=\"CODICE\" type=\"xs:string\" minOccurs=\"0\" />\r\n" +
                                            "<xs:element name=\"CODICECOMUNE\" type=\"xs:string\" minOccurs=\"0\" />\r\n" +
                                            "<xs:element name=\"DATA\" type=\"xs:string\" minOccurs=\"0\" />\r\n" +
                                            "</xs:sequence>\r\n" +
                                            "</xs:complexType>\r\n" +
                                            "</xs:element>\r\n" +
                                            "</xs:choice>\r\n" +
                                            "</xs:complexType>\r\n" +
                                            "</xs:element>\r\n" +
                                            "</xs:schema>";

                        this.BtnSalva.Enabled = true;
                        this.BtnPersonalizza.Enabled = false;
                        this.BtnParametri.Enabled = false;
                        this.BtnTracciati.Enabled = false;
                        this.BtnExport.Enabled = false;
                        this.BtnImport.Enabled = true;
                        this.BtnElimina.Enabled = false;
                    }

                    this.TxtID.Text = this.Esp.ID;
                    this.txtIdcomune.Text = this.Esp.IDCOMUNE;
                    this.TxtDesc.Text = this.Esp.DESCRIZIONE;

                    this.TxtFileName.Text = this.Esp.OUT_NOMEFILE;
                    this.TxtXmlTag.Text = this.Esp.OUT_XMLTAG;

                    if (this.Esp.ANNULLA_DATI == "1")
                        this.ChckAnnDati.Checked = true;
                    if (this.Esp.INSERISCI_NULLI == "1")
                        this.ChckInsNull.Checked = true;
                    if (this.Esp.FLG_ABILITATA == "1")
                        this.ChckFlgAbilitata.Checked = true;

                    this.DDLstType.SelectedValue = this.Esp.FK_TIPIESPORTAZIONE_CODICE;
                    this.DDLstTypeContext.SelectedValue = this.Esp.FK_TIPICONTESTOESP_CODICE;

                    this.SelectTipoExp();
                }

            }
            catch (Exception ex)
            {
                throw new Exception("Errore generato all'apertura dell'esportazione selezionata. Pagina: Exp. Messaggio: " + ex.Message + "\r\n");
            }
        }

        #region Codice generato da Progettazione Web Form
        override protected void OnInit(EventArgs e)
        {
            //
            // CODEGEN: questa chiamata è richiesta da Progettazione Web Form ASP.NET.
            //
            this.InitializeComponent();
            base.OnInit(e);
        }

        /// <summary>
        /// Metodo necessario per il supporto della finestra di progettazione. Non modificare
        /// il contenuto del metodo con l'editor di codice.
        /// </summary>
        private void InitializeComponent()
        {

        }
        #endregion

        protected void BtnChiudi_Click(object sender, System.EventArgs e)
        {
            this.Response.Redirect("SelectEnte.aspx");
        }

        private void MostraPannello(bool bMostra)
        {
            if (!bMostra)
            {
                this.txtEnte.Text = "";
            }

            this.pnlEnte.Visible = bMostra;
        }

        protected void DDLstType_SelectedIndexChanged(object sender, System.EventArgs e)
        {
            this.SelectTipoExp();
        }

        /// <summary>
        /// Metodo usato per gestire il cambiamento del tipo esportazione
        /// </summary>
        private void SelectTipoExp()
        {
            switch (this.DDLstType.SelectedValue)
            {
                case "TXT":
                    this.LblXmlTag.Visible = false;
                    this.TxtXmlTag.Visible = false;
                    this.RequiredFieldValidator4.Visible = false;
                    this.RequiredFieldValidator4.ErrorMessage = "";
                    break;
                case "XML":
                    this.LblXmlTag.Visible = true;
                    this.TxtXmlTag.Visible = true;
                    this.RequiredFieldValidator4.Visible = true;
                    break;
                case "CSV":
                    this.LblXmlTag.Visible = false;
                    this.TxtXmlTag.Visible = false;
                    this.RequiredFieldValidator4.Visible = false;
                    this.RequiredFieldValidator4.ErrorMessage = "";
                    break;
                default:
                    break;
            }
        }

        /// <summary>
        /// Metodo usato per caricare nella combo box il tipo di esportazione selezionato in precedenza
        /// </summary>
        private void LoadDDTipoExp()
        {
            TIPIESPORTAZIONE pTpExp = new TIPIESPORTAZIONE();
            TipiEsportazioneMgr pTpExpMgr = new TipiEsportazioneMgr(this.DbDestinazione);
            var pLstTipi = pTpExpMgr.GetList(pTpExp);
            if (pLstTipi != null && pLstTipi.Count > 0)
            {
                this.DDLstType.DataSource = pLstTipi;
                this.DDLstType.DataTextField = "TIPO";
                this.DDLstType.DataValueField = "CODICETIPO";
                this.DDLstType.DataBind();
            }
        }

        /// <summary>
        /// Metodo usato per caricare nella combo box il tipo di contesto di esportazione
        /// </summary>
        private void LoadDDTipoContextExp()
        {
            TIPICONTESTOESPORTAZIONE pTpContextExp = new TIPICONTESTOESPORTAZIONE();
            TipiContestoEsportazioneMgr pTpContextExpMgr = new TipiContestoEsportazioneMgr(this.DbDestinazione);
            var pLstTipi = pTpContextExpMgr.GetList(pTpContextExp);
            if (pLstTipi != null && pLstTipi.Count > 0)
            {
                this.DDLstTypeContext.DataSource = pLstTipi;
                this.DDLstTypeContext.DataTextField = "DESCRIZIONE";
                this.DDLstTypeContext.DataValueField = "CODICE";
                this.DDLstTypeContext.DataBind();
            }
        }


        /// <summary>
        /// Metodo usato per salvare una nuova esportazione o modificarne una esistente
        /// </summary>
        private void SaveExp()
        {
            EsportazioniMgr pExpMgr = new EsportazioniMgr(this.DbDestinazione);

            ESPORTAZIONI pExp = new ESPORTAZIONI();
            pExp.IDCOMUNE = this.txtIdcomune.Text;
            pExp.DESCRIZIONE = this.TxtDesc.Text;
            pExp.INPUT_XSD = this.TxtXsd.Text;
            pExp.OUT_NOMEFILE = this.TxtFileName.Text;
            pExp.FK_TIPIESPORTAZIONE_CODICE = this.DDLstType.SelectedValue;
            pExp.FK_TIPICONTESTOESP_CODICE = this.DDLstTypeContext.SelectedValue;
            pExp.OUT_XMLTAG = this.TxtXmlTag.Text;
            if (this.ChckAnnDati.Checked)
                pExp.ANNULLA_DATI = "1";
            else
                pExp.ANNULLA_DATI = "0";

            if (this.ChckInsNull.Checked)
                pExp.INSERISCI_NULLI = "1";
            else
                pExp.INSERISCI_NULLI = "0";

            if (this.ChckFlgAbilitata.Checked)
                pExp.FLG_ABILITATA = "1";
            else
                pExp.FLG_ABILITATA = "0";

            if (this.TxtID.Text != "")
            {
                pExp.ID = this.TxtID.Text;
                pExpMgr.Update(pExp);

            }
            else
            {
                pExp = pExpMgr.Insert(pExp);
                this.TxtID.Text = pExp.ID;
                this.DDLstType.Enabled = false;
            }
            this.Esp = pExp;
        }

        protected void BtnSalva_Click(object sender, System.EventArgs e)
        {
            try
            {
                this.SaveExp();
            }
            catch (Exception ex)
            {
                throw new Exception("Errore generato durante il salvataggio dell'esportazione selezionata. Pagina: Exp. Messaggio: " + ex.Message + "\r\n");
            }

            this.Response.Redirect("Exp.aspx?idcomune=" + this.Esp.IDCOMUNE + "&idesportazione=" + this.Esp.ID);

        }

        protected void BtnTracciati_Click(object sender, System.EventArgs e)
        {
            this.Response.Redirect("ListTrac.aspx");
        }

        protected void BtnParametri_Click(object sender, System.EventArgs e)
        {
            this.Response.Redirect("ListParametri.aspx?idcomune=" + this.qsIdComune + "&idesportazione=" + this.qsIdEsportazione);
        }

        protected void BtnTracciati_Click1(object sender, EventArgs e)
        {
            this.Response.Redirect("ListEnteTrac.aspx?idcomune=" + this.qsIdComune + "&idesportazione=" + this.qsIdEsportazione);
        }

        protected void BtnElimina_Click(object sender, EventArgs e)
        {
            ESPORTAZIONI exp = new ESPORTAZIONI();
            exp.IDCOMUNE = this.txtIdcomune.Text;
            exp.ID = this.TxtID.Text;

            EsportazioniMgr mgr = new EsportazioniMgr(this.DbDestinazione);
            mgr.Delete(exp);

            this.Response.Redirect("SelectEnte.aspx");
        }

        protected void BtnPersonalizza_Click(object sender, EventArgs e)
        {
            this.MostraPannello(true);
        }

        protected void imgChiudi_Click(object sender, ImageClickEventArgs e)
        {
            this.MostraPannello(false);
        }

        protected void btnConfermaReplica_Click(object sender, EventArgs e)
        {
            if (string.IsNullOrEmpty(this.txtEnte.Text))
                throw new Exception("Non è stato specificato l'idcomune per il quale si intende copiare o importare la configurazione dell'esportazione");

            if (string.IsNullOrEmpty(this.TxtID.Text) && this.fuFileupload.PostedFile == null)
                throw new Exception("Non è stato specificato il file da importare");

            ESPORTAZIONI exp = new ESPORTAZIONI();

            if ((this.fuFileupload.PostedFile != null) && (this.fuFileupload.PostedFile.ContentLength > 0))
            {
                try
                {
                    exp = ESPORTAZIONI.Deserialize(this.fuFileupload.FileBytes);
                    if (this.txtEnte.Text != this.IdComuneDefault)
                    {
                        //se non si importa un'esportazione di default allora gli id vanno ricalcolati
                        exp.ID = null;
                        exp.IDCOMUNE = this.txtEnte.Text;

                        foreach (PARAMETRIESPORTAZIONE pe in exp.Parametri)
                        {
                            pe.FK_ESP_ID = null;
                            pe.ID = null;
                            pe.IDCOMUNE = exp.IDCOMUNE;
                        }

                        foreach (TRACCIATI t in exp.Tracciati)
                        {
                            t.ID = null;
                            t.FK_ESP_ID = null;
                            t.IDCOMUNE = exp.IDCOMUNE;

                            foreach (TRACCIATIDETTAGLIO td in t.TracciatiDettagli)
                            {
                                td.ID = null;
                                td.FK_TRACCIATI_ID = null;
                                td.IDCOMUNE = t.IDCOMUNE;
                            }
                        }
                    }
                    else
                    {
                        //non è possibile utilizzare un'altra esportazione per crearne una di default
                        if (exp.IDCOMUNE != this.IdComuneDefault)
                        {
                            throw new Exception("Attenzione: non è possibile utilizzare l'importazione per il comune " + exp.IDCOMUNE + " per crearne una di default ( idcomune: " + this.IdComuneDefault + ")");
                        }
                    }

                    new EsportazioniMgr(this.DbDestinazione).InsertAll(exp);
                }
                catch (Exception ex)
                {
                    this.Response.Write("Error: " + ex.Message);
                }
            }
            else
            {
                EsportazioniMgr mgr = new EsportazioniMgr(this.DbDestinazione);
                exp = mgr.ReplicaEsportazione(this.txtIdcomune.Text, this.TxtID.Text, this.txtEnte.Text);
            }

            this.Response.Redirect("Exp.aspx?idcomune=" + exp.IDCOMUNE + "&idesportazione=" + exp.ID);
        }

        protected void BtnImport_Click(object sender, EventArgs e)
        {
            this.MostraPannello(true);
        }

        protected void BtnExport_Click(object sender, EventArgs e)
        {
            ESPORTAZIONI exp = new EsportazioniMgr(this.DbDestinazione).GetById(this.qsIdComune, this.TxtID.Text, true);

            this.Response.ContentType = "text/xml";
            this.Response.AddHeader("content-disposition", "attachment; filename=" + exp.IDCOMUNE + "_" + exp.ID + "_Configfile.xml");
            this.Response.BinaryWrite(exp.Serialize());
            this.Response.End();
        }
    }
}
