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
using Parser;
using System.Collections.Specialized;
using SigeproExportData.Utils;
using System.Collections.Generic;

namespace WebSigeproExport
{
	/// <summary>
	/// Descrizione di riepilogo per QueryTrac.
	/// </summary>
	public partial class QueryTrac : BasePage
	{
	
		protected void Page_Load(object sender, System.EventArgs e)
		{
			if (!IsPostBack)
			{
                this.LblEnte.Text += Esp.IDCOMUNE;
                this.LlbExp.Text += Esp.titolo_pagina;

                

                LoadDDTipoTrac();

                this.TxtFileName.Text = Esp.OUT_NOMEFILE;
                this.TxtIDCOMUNE.Text = Trac.IDCOMUNE;

                if (!String.IsNullOrEmpty(Trac.ID))
                {
                    CMessageDelete pMsgDlt = new CMessageDelete();
                    string sMsg = pMsgDlt.GetMsgDelete(Trac, DbDestinazione);
                    BtnElimina.Attributes.Add("OnClick", "return ConfermaElimina('" + sMsg + "');");

                    this.TxtID.Text = Trac.ID;
                    this.TxtDescBreve.Text = Trac.DESCR_BREVE;
                    this.TxtDesc.Text = Trac.DESCRIZIONE;
                    this.TxtOrdine.Text = Trac.OUT_ORDINE;
                    this.TxtXmlTag.Text = Trac.OUT_XMLTAG;
                    this.DDListTypeTrc.SelectedValue = Trac.FK_TIPITRACCIATO_CODICE;
                    

                    if (Trac.OUT_NOMEFILE != "" && Trac.OUT_NOMEFILE != null)
                        this.TxtFileName.Text = Trac.OUT_NOMEFILE;
                    this.TxtQuery.Text = Trac.QUERY;

                    this.BtnSalva.Enabled = ModificaIdcomune;
                    this.BtnQryDettTrac.Enabled = true;
                    this.BtnElimina.Enabled = ModificaIdcomune;
                }
                else
                {
                    this.TxtID.Text = String.Empty;
                    this.TxtDescBreve.Text = String.Empty;
                    this.TxtDesc.Text = String.Empty;
                    this.TxtOrdine.Text = String.Empty;
                    this.TxtXmlTag.Text = String.Empty;
                    this.DDListTypeTrc.SelectedValue = String.Empty;

                    this.BtnSalva.Enabled = true;
                    this.BtnQryDettTrac.Enabled = false;
                    this.BtnElimina.Enabled = false;
                }

                SelectTipoExp();

			}
		}

		#region Codice generato da Progettazione Web Form
		override protected void OnInit(EventArgs e)
		{
			//
			// CODEGEN: questa chiamata è richiesta da Progettazione Web Form ASP.NET.
			//
			InitializeComponent();
			InitializeMyComponent();
			base.OnInit(e);
		}
		
		private void InitializeMyComponent()
		{
			this.Help1.onHelpClick += new WebSigeproExport.Controls.Help.HelpClick(Help1_onHelpClick);
		}

		/// <summary>
		/// Metodo necessario per il supporto della finestra di progettazione. Non modificare
		/// il contenuto del metodo con l'editor di codice.
		/// </summary>
		private void InitializeComponent()
		{    

		}
		#endregion			   

		protected void Help1_onHelpClick(WebSigeproExport.Controls.Help obj)
		{
			StringCollection pCllValues = ListValues();
			StringCollection pCllParametries = ListParametries();
			Help1.HParameter = pCllParametries;
			Help1.HXml = pCllValues;
			Help1.HQuery = Queries();
		}

		private StringCollection ListParametries ()
		{
			ParametriEsportazioneMgr pParEspMgr = new ParametriEsportazioneMgr(DbDestinazione);
			PARAMETRIESPORTAZIONE pParEsp = new PARAMETRIESPORTAZIONE();
            pParEsp.IDCOMUNE = Trac.IDCOMUNE;
			pParEsp.FK_ESP_ID = Trac.FK_ESP_ID;
            List<PARAMETRIESPORTAZIONE> pList = pParEspMgr.GetList(pParEsp);

			StringCollection pCllParametries = new StringCollection();
			
			foreach(PARAMETRIESPORTAZIONE elem in pList)
			{
				pCllParametries.Add(elem.NOME.ToUpper());
			}

			return pCllParametries;
		}

		private StringCollection ListValues ()
		{
			XMLParser pXmlPrs = new XMLParser();
			pXmlPrs.XmlSchema = Esp.INPUT_XSD;
			DataSet ds = pXmlPrs.Parse();
			StringCollection pCllValues = new StringCollection();
			
			if ( ds != null )
			{
				foreach ( DataColumn pClm in ds.Tables[0].Columns )
					pCllValues.Add(pClm.ColumnName);
			}

			return pCllValues;
		}

		private StringCollection Queries()
		{
			StringCollection retVal = new StringCollection();

			try
			{
                string IdComune         = Trac.IDCOMUNE;
                string IdEsportazione	= Trac.FK_ESP_ID;
				string IdTracciato		= Trac.ID;
				int DettOrdineMax		= 0;

                TracciatiDettMgr tracDet = new TracciatiDettMgr(DbDestinazione);

				retVal = tracDet.getQueries( IdEsportazione, IdTracciato, IdComune, DettOrdineMax );

			}
			catch( System.Exception ex )
			{
				throw new Exception("Errore generato durante la creazione della lista delle variabili. Pagina: QueryTrac. Messaggio: "+ex.Message+"\r\n");
			}

			return retVal;
		}

		protected void BtnChiudi_Click(object sender, EventArgs e)
		{
            Response.Redirect("ListEnteTrac.aspx?idcomune=" + Esp.IDCOMUNE + "&idesportazione=" + Esp.ID);
		}

		protected void BtnSalva_Click(object sender, EventArgs e)
		{
			try
			{
                TracciatiMgr pTrcMgr = new TracciatiMgr(DbDestinazione);
                TRACCIATI pTrc = new TRACCIATI();

                pTrc.IDCOMUNE = this.TxtIDCOMUNE.Text;
                pTrc.OUT_ORDINE = this.TxtOrdine.Text;
                pTrc.FK_ESP_ID = Esp.ID;
                pTrc.DESCRIZIONE = this.TxtDesc.Text;
                pTrc.DESCR_BREVE = this.TxtDescBreve.Text;
                pTrc.OUT_NOMEFILE = this.TxtFileName.Text;
                pTrc.OUT_XMLTAG = this.TxtXmlTag.Text;
                pTrc.FK_TIPITRACCIATO_CODICE = this.DDListTypeTrc.SelectedValue;
                pTrc.QUERY = this.TxtQuery.Text;

                if (!String.IsNullOrEmpty(TxtID.Text))
                {
                    pTrc.ID = this.TxtID.Text;
                    pTrc = pTrcMgr.Update(pTrc);
                }
                else
                {
                    pTrc = pTrcMgr.Insert(pTrc);
                }

                Trac = pTrc;
			}
			catch ( Exception ex )
			{
				throw new Exception("Errore generato durante il salvataggio della configurazione del tracciato selezionato. Pagina: QueryTrac. Messaggio: "+ex.Message+"\r\n");
			}

            Response.Redirect("QueryTrac.aspx?idcomune=" + Esp.IDCOMUNE + "&idesportazione=" + Esp.ID);
		}

		protected void BtnQryDettTrac_Click(object sender, EventArgs e)
		{
            Response.Redirect("ListEnteDetTrac.aspx?idcomune=" + Esp.IDCOMUNE + "&idesportazione=" + Esp.ID);
		}			   

		protected void BtnElimina_Click(object sender, EventArgs e)
		{
			try
			{
                TRACCIATI tTrac = (TRACCIATI)Trac.Clone();

                TracciatiMgr TracMgr = new TracciatiMgr(DbDestinazione);
                TracMgr.Delete(Trac);

                Trac = new TRACCIATI();
                Trac.FK_ESP_ID = tTrac.FK_ESP_ID;
                Trac.IDCOMUNE = tTrac.IDCOMUNE;
			}
			catch ( Exception ex )
			{
				throw new Exception("Errore generato durante l'eliminazione della configurazione del tracciato selezionato. Pagina: QueryTrac. Messaggio: "+ex.Message+"\r\n");
			}

            Response.Redirect("ListEnteTrac.aspx?idcomune=" + Esp.IDCOMUNE + "&idesportazione=" + Esp.ID);
		}

        private void LoadDDTipoTrac()
        {
            TIPITRACCIATI pTpTrac = new TIPITRACCIATI();
            pTpTrac.OrderBy = "TIPO";
            TipiTracciatiMgr pTpTracMgr = new TipiTracciatiMgr(DbDestinazione);
            List<TIPITRACCIATI> pLstTipi = pTpTracMgr.GetList(pTpTrac);
            if (pLstTipi != null && pLstTipi.Count >= 0)
            {
                DDListTypeTrc.DataSource = pLstTipi;
                DDListTypeTrc.DataTextField = "Tipo";
                DDListTypeTrc.DataValueField = "CodiceTipo";
                DDListTypeTrc.DataBind();
            }
        }

        private void SelectTipoExp()
        {
            switch (Esp.FK_TIPIESPORTAZIONE_CODICE)
            {
                case "XML":
                    this.LblXmlTag.Visible = true;
                    this.TxtXmlTag.Visible = true;
                    break;
                default:
                    this.LblXmlTag.Visible = false;
                    this.TxtXmlTag.Visible = false;
                    break;
            }
        }

	}
}
