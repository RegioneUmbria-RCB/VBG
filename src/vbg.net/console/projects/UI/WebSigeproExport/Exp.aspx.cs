using System;
using System.Collections;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Web;
using System.Web.SessionState;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.HtmlControls;

using Init.SIGeProExport.Manager;
using Init.SIGeProExport.Data;
using PersonalLib2.Data;
using System.IO;
using System.Xml.Serialization;

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
		
				if(!IsPostBack)
				{
                    MostraPannello(false);

                    this.BtnElimina.Attributes.Add("OnClick", "return ConfermaElimina('Sei sicuro di voler eliminare questa esportazione e relativi tracciati?');");

                    LoadDDTipoExp();
                    LoadDDTipoContextExp();

                    Esp = new ESPORTAZIONI();
                    if (!String.IsNullOrEmpty(qsIdEsportazione))
                    {
                        Esp = new EsportazioniMgr(DbDestinazione).GetById(qsIdComune, qsIdEsportazione);
                        DDLstType.Enabled = false;
                        DDLstTypeContext.Enabled = false;
                        this.txtIdcomune.ReadOnly = true;
                        this.TxtXsd.Text = Esp.INPUT_XSD;

                        this.BtnSalva.Enabled = ModificaIdcomune;
                        this.BtnPersonalizza.Enabled = true;
                        this.BtnParametri.Enabled = true;
                        this.BtnTracciati.Enabled = true;
                        this.BtnExport.Enabled = true;
                        this.BtnImport.Enabled = false;
                        this.BtnElimina.Enabled = ModificaIdcomune;
                    }
                    else 
                    {
                        DDLstType.Enabled = true;
                        DDLstTypeContext.Enabled = true;
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
				
					this.TxtID.Text = Esp.ID;
                    this.txtIdcomune.Text = Esp.IDCOMUNE;
                    this.TxtDesc.Text = Esp.DESCRIZIONE;
                    
                    this.TxtFileName.Text = Esp.OUT_NOMEFILE;
                    this.TxtXmlTag.Text = Esp.OUT_XMLTAG;

					if (Esp.ANNULLA_DATI == "1")
                        this.ChckAnnDati.Checked = true;
					if (Esp.INSERISCI_NULLI == "1")
                        this.ChckInsNull.Checked = true;
                    if (Esp.FLG_ABILITATA == "1")
                        this.ChckFlgAbilitata.Checked = true;

                    this.DDLstType.SelectedValue = Esp.FK_TIPIESPORTAZIONE_CODICE;
                    this.DDLstTypeContext.SelectedValue = Esp.FK_TIPICONTESTOESP_CODICE;

					SelectTipoExp();
				}

			}
			catch ( Exception ex )
			{
				throw new Exception("Errore generato all'apertura dell'esportazione selezionata. Pagina: Exp. Messaggio: "+ex.Message+"\r\n");
			}
		}

		#region Codice generato da Progettazione Web Form
		override protected void OnInit(EventArgs e)
		{
			//
			// CODEGEN: questa chiamata è richiesta da Progettazione Web Form ASP.NET.
			//
			InitializeComponent();
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
            Response.Redirect("SelectEnte.aspx");
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
			SelectTipoExp();
		}

		/// <summary>
		/// Metodo usato per gestire il cambiamento del tipo esportazione
		/// </summary>
		private void SelectTipoExp()
		{
			switch (DDLstType.SelectedValue)
			{
				case "TXT":
					LblXmlTag.Visible = false;
					TxtXmlTag.Visible = false;
					RequiredFieldValidator4.Visible = false;
					RequiredFieldValidator4.ErrorMessage = "";
					break;
				case "XML":
					LblXmlTag.Visible = true;
					TxtXmlTag.Visible = true;
					RequiredFieldValidator4.Visible = true;
					break;
                case "CSV":
                    LblXmlTag.Visible = false;
					TxtXmlTag.Visible = false;
					RequiredFieldValidator4.Visible = false;
					RequiredFieldValidator4.ErrorMessage = "";
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
			TipiEsportazioneMgr pTpExpMgr = new TipiEsportazioneMgr(DbDestinazione);
			ArrayList pLstTipi = pTpExpMgr.GetList(pTpExp);
			if ( pLstTipi != null && pLstTipi.Count > 0 )
			{				
				DDLstType.DataSource = pLstTipi;
				DDLstType.DataTextField = "TIPO";
				DDLstType.DataValueField = "CODICETIPO";
				DDLstType.DataBind();
			}
		}

        /// <summary>
        /// Metodo usato per caricare nella combo box il tipo di contesto di esportazione
        /// </summary>
        private void LoadDDTipoContextExp()
        {
            TIPICONTESTOESPORTAZIONE pTpContextExp = new TIPICONTESTOESPORTAZIONE();
            TipiContestoEsportazioneMgr pTpContextExpMgr = new TipiContestoEsportazioneMgr(DbDestinazione);
            ArrayList pLstTipi = pTpContextExpMgr.GetList(pTpContextExp);
            if (pLstTipi != null && pLstTipi.Count > 0)
            {
                DDLstTypeContext.DataSource = pLstTipi;
                DDLstTypeContext.DataTextField = "DESCRIZIONE";
                DDLstTypeContext.DataValueField = "CODICE";
                DDLstTypeContext.DataBind();
            }
        }


		/// <summary>
		/// Metodo usato per salvare una nuova esportazione o modificarne una esistente
		/// </summary>
		private void SaveExp()
		{
			EsportazioniMgr pExpMgr = new EsportazioniMgr(DbDestinazione);
			
            ESPORTAZIONI pExp = new ESPORTAZIONI();
            pExp.IDCOMUNE = this.txtIdcomune.Text;
            pExp.DESCRIZIONE = this.TxtDesc.Text;
            pExp.INPUT_XSD = this.TxtXsd.Text;
            pExp.OUT_NOMEFILE = this.TxtFileName.Text;
            pExp.FK_TIPIESPORTAZIONE_CODICE = this.DDLstType.SelectedValue;
            pExp.FK_TIPICONTESTOESP_CODICE = this.DDLstTypeContext.SelectedValue;
            pExp.OUT_XMLTAG = this.TxtXmlTag.Text;
			if (  ChckAnnDati.Checked )
				pExp.ANNULLA_DATI = "1";
			else
				pExp.ANNULLA_DATI = "0";
			
            if (  ChckInsNull.Checked )
				pExp.INSERISCI_NULLI = "1";
			else
				pExp.INSERISCI_NULLI = "0";

            if (this.ChckFlgAbilitata.Checked)
                pExp.FLG_ABILITATA = "1";
            else
                pExp.FLG_ABILITATA = "0";

			if ( TxtID.Text != "" )
			{
                pExp.ID = this.TxtID.Text;
				pExpMgr.Update(pExp);

			}
			else
			{
                pExp = pExpMgr.Insert(pExp);
                this.TxtID.Text = pExp.ID;
    			DDLstType.Enabled = false;
			}
			Esp = pExp;
		}

		protected void BtnSalva_Click(object sender, System.EventArgs e)
		{
			try
			{
				SaveExp();
			}
			catch ( Exception ex )
			{
				throw new Exception("Errore generato durante il salvataggio dell'esportazione selezionata. Pagina: Exp. Messaggio: "+ex.Message+"\r\n");
			}

            Response.Redirect("Exp.aspx?idcomune=" + Esp.IDCOMUNE + "&idesportazione=" + Esp.ID);
                               
		}

		protected void BtnTracciati_Click(object sender, System.EventArgs e)
		{
			Response.Redirect("ListTrac.aspx");
		}

		protected void BtnParametri_Click(object sender, System.EventArgs e)
		{
            Response.Redirect("ListParametri.aspx?idcomune=" + qsIdComune + "&idesportazione=" + qsIdEsportazione);
		}

        protected void BtnTracciati_Click1(object sender, EventArgs e)
        {
            Response.Redirect("ListEnteTrac.aspx?idcomune=" + qsIdComune + "&idesportazione=" + qsIdEsportazione);
        }

        protected void BtnElimina_Click(object sender, EventArgs e)
        {
            ESPORTAZIONI exp = new ESPORTAZIONI();
            exp.IDCOMUNE = this.txtIdcomune.Text;
            exp.ID = this.TxtID.Text;

            EsportazioniMgr mgr = new EsportazioniMgr(DbDestinazione);
            mgr.Delete(exp);

            Response.Redirect("SelectEnte.aspx");
        }

        protected void BtnPersonalizza_Click(object sender, EventArgs e)
        {
            MostraPannello(true);
        }

        protected void imgChiudi_Click(object sender, ImageClickEventArgs e)
        {
            MostraPannello(false);
        }

        protected void btnConfermaReplica_Click(object sender, EventArgs e)
        {
            if( string.IsNullOrEmpty(this.txtEnte.Text) )
                throw new Exception("Non è stato specificato l'idcomune per il quale si intende copiare o importare la configurazione dell'esportazione");

            if (string.IsNullOrEmpty(this.TxtID.Text) && fuFileupload.PostedFile == null)
                throw new Exception("Non è stato specificato il file da importare");

            ESPORTAZIONI exp = new ESPORTAZIONI();

            if ((fuFileupload.PostedFile != null) && (fuFileupload.PostedFile.ContentLength > 0))
            {
                try
                {
                    exp = ESPORTAZIONI.Deserialize(fuFileupload.FileBytes);
                    if (this.txtEnte.Text != IdComuneDefault)
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
                        if (exp.IDCOMUNE != IdComuneDefault)
                        {
                            throw new Exception("Attenzione: non è possibile utilizzare l'importazione per il comune " + exp.IDCOMUNE + " per crearne una di default ( idcomune: " + IdComuneDefault + ")");
                        }
                    }

                    new EsportazioniMgr(DbDestinazione).InsertAll(exp);
                }
                catch (Exception ex)
                {
                    Response.Write("Error: " + ex.Message);
                }
            }
            else
            {
                EsportazioniMgr mgr = new EsportazioniMgr(DbDestinazione);
                exp = mgr.ReplicaEsportazione(this.txtIdcomune.Text, this.TxtID.Text, this.txtEnte.Text);
            }

            Response.Redirect("Exp.aspx?idcomune=" + exp.IDCOMUNE + "&idesportazione=" + exp.ID);
        }

        protected void BtnImport_Click(object sender, EventArgs e)
        {
            MostraPannello(true);
        }

        protected void BtnExport_Click(object sender, EventArgs e)
        {
            ESPORTAZIONI exp = new EsportazioniMgr(DbDestinazione).GetById(qsIdComune, this.TxtID.Text,true);

            Response.ContentType = "text/xml";
            Response.AddHeader("content-disposition", "attachment; filename=" + exp.IDCOMUNE + "_" + exp.ID + "_Configfile.xml");
            Response.BinaryWrite( exp.Serialize() );
            Response.End();
        }
	}
}
