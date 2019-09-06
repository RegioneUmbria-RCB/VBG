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

using Init.Utils;
using Init.SIGeProExport.Manager;
using Init.SIGeProExport.Data;
using PersonalLib2.Data;
using Parser;
using System.Collections.Specialized;
using System.Collections.Generic;

namespace WebSigeproExport
{
	/// <summary>
	/// Descrizione di riepilogo per QueryDettTrac.
	/// </summary>
	public partial class QueryDettTrac : BasePage
	{
	
		protected void Page_Load(object sender, System.EventArgs e)
		{
			if (!IsPostBack)
			{
				BtnElimina.Attributes.Add("OnClick","return ConfermaElimina('Sei sicuro di voler eliminare questa configurazione del dettaglio tracciato?');");

                this.LblExp.Text += Esp.titolo_pagina;
                this.LblEnte.Text += TracDett.IDCOMUNE;
                this.LblTrac.Text += Trac.titolo_pagina;
                this.LblInfoCSV.Visible = (Esp.FK_TIPIESPORTAZIONE_CODICE == "CSV");
                this.TxtLungh.Visible = this.LblLungh.Visible = (Esp.FK_TIPIESPORTAZIONE_CODICE == "TXT");

                
                this.TxtIDCOMUNE.Text = TracDett.IDCOMUNE;

                this.BtnSalva.Enabled = ModificaIdcomune;

                if (!String.IsNullOrEmpty(TracDett.ID))
                {
                    this.TxtDesc.Text = TracDett.DESCRIZIONE;
                    this.TxtID.Text = TracDett.ID;
                    this.TxtLungh.Text = TracDett.LUNGHEZZA;
                    this.TxtNote.Text = TracDett.NOTE;
                    this.TxtOrdine.Text = TracDett.OUT_ORDINE;
                    this.TxtQuery.Text = TracDett.QUERY;
                    this.ChkBoxCampoTestuale.Checked = (TracDett.CAMPOTESTO == "1");

                    if (TracDett.VALORE == "\r\n")
                    {
                        this.ChkBoxFineRiga.Checked = true;
                        this.TxtValore.Text = "";
                        this.TxtValore.Enabled = false;
                    }
                    else
                    {
                        this.ChkBoxFineRiga.Checked = false;
                        this.TxtValore.Enabled = true;
                        this.TxtValore.Text = TracDett.VALORE;
                    }
                    this.TxtXmlTag.Text = TracDett.OUT_XMLTAG;
                    this.ChckObbl.Checked = (TracDett.OBBLIGATORIO == "1");
                    this.BtnElimina.Enabled = ModificaIdcomune;
                }
                else
                {
                    this.TxtDesc.Text = String.Empty;
                    this.TxtID.Text =  String.Empty;
                    this.TxtLungh.Text = String.Empty;
                    this.TxtNote.Text = String.Empty;
                    this.ChckObbl.Checked = false;
                    this.TxtOrdine.Text = CalcolaOrdineDettaglioTracciato();
                    this.TxtXmlTag.Text = String.Empty;
                    this.TxtQuery.Text = String.Empty;
                    this.TxtValore.Text = String.Empty;
                    this.BtnElimina.Enabled = false;
                }
			}

            this.ChkBoxCampoTestuale.Visible = (Esp.FK_TIPIESPORTAZIONE_CODICE == "CSV");
            this.LabelCampoTestuale.Visible = this.ChkBoxCampoTestuale.Visible;


		}

        private string CalcolaOrdineDettaglioTracciato()
        {
            TRACCIATIDETTAGLIO filter = new TRACCIATIDETTAGLIO();
            filter.IDCOMUNE = Trac.IDCOMUNE;
            filter.FK_TRACCIATI_ID = Trac.ID;
            filter.OrderBy = "OUT_ORDINE DESC";

            TracciatiDettMgr tdm = new TracciatiDettMgr( DbDestinazione );
            List<TRACCIATIDETTAGLIO> tracciati = tdm.GetList(filter);

            if (tracciati == null || tracciati.Count == 0)
                return "10";

            return (Convert.ToInt32(tracciati[0].OUT_ORDINE) + 10).ToString();
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

        private StringCollection Queries(  )
		{
			StringCollection retVal = new StringCollection();

			try
			{
                string IdComune         = Trac.IDCOMUNE;
				string IdEsportazione	= Trac.FK_ESP_ID;
				string IdTracciato		= Trac.ID;
				
				int DettOrdineMax		= -1;

				if ( ! StringChecker.IsStringEmpty( TracDett.OUT_ORDINE ) )
					DettOrdineMax = Convert.ToInt32( TracDett.OUT_ORDINE );

                TracciatiDettMgr tracDet = new TracciatiDettMgr(DbDestinazione);

				retVal = tracDet.getQueries( IdEsportazione, IdTracciato, IdComune, DettOrdineMax );

			}
			catch( System.Exception ex )
			{
				throw new Exception("Errore generato durante la creazione della lista delle variabili. Pagina: QueryDettTrac. Messaggio: "+ex.Message+"\r\n");
			}

			return retVal;
		}

		private void SetValueFineRiga(TRACCIATIDETTAGLIO pTracDet)
		{
			if ( ChkBoxFineRiga.Checked )
                pTracDet.VALORE = "\r\n";
			else
                pTracDet.VALORE = TxtValore.Text;
		}

		/// <summary>
		/// Metodo usato per salvare una nuova configurazione dei dettagli tracciati o modificarne una esistente
		/// </summary>
		private void SaveQuery()
		{
            TracciatiDettMgr pCfg_TracDetMgr = new TracciatiDettMgr(DbDestinazione);

            TracDett.QUERY = TxtQuery.Text;
            SetValueFineRiga(TracDett);

			if ( String.IsNullOrEmpty( TracDett.ID ) )
			{
                TracDett = pCfg_TracDetMgr.Insert(TracDett);
				BtnElimina.Visible = true;
				//sMod = "true";
				Page.RegisterStartupScript("test", "<script language='javascript'>InfoMess('Inserimento effettuato!')</script>");
			}
			else
			{
                pCfg_TracDetMgr.Update(TracDett);
                Page.RegisterStartupScript("test", "<script language='javascript'>InfoMess('Aggiornamento effettuato!')</script>");				
			}
		}

		protected void BtnSalva_Click(object sender, System.EventArgs e)
		{
			try
			{
                TracciatiDettMgr pCfg_TracDetMgr = new TracciatiDettMgr(DbDestinazione);

                TracDett.DESCRIZIONE = this.TxtDesc.Text;
                TracDett.FK_TRACCIATI_ID = Trac.ID;
                TracDett.IDCOMUNE = this.TxtIDCOMUNE.Text;
                TracDett.LUNGHEZZA = this.TxtLungh.Text;
                TracDett.NOTE = this.TxtNote.Text;
                TracDett.OBBLIGATORIO = (this.ChckObbl.Checked) ? "1" : "0";
                TracDett.OUT_ORDINE = this.TxtOrdine.Text;
                TracDett.OUT_XMLTAG = this.TxtXmlTag.Text;
                TracDett.QUERY = this.TxtQuery.Text;
                TracDett.CAMPOTESTO = (this.ChkBoxCampoTestuale.Checked) ? "1" : "0";

                SetValueFineRiga(TracDett);
                if (String.IsNullOrEmpty(TracDett.ID))
                {
                    TracDett = pCfg_TracDetMgr.Insert(TracDett);
                    BtnElimina.Enabled = true;
                }
                else
                {
                    pCfg_TracDetMgr.Update(TracDett);
                }

                Response.Redirect("QueryDettTrac.aspx?idcomune=" + Esp.IDCOMUNE + "&idesportazione=" + Esp.ID);

			}
			catch ( Exception ex )
			{
				throw new Exception("Errore generato durante il salvataggio della configurazione del dettaglio tracciato selezionato. Pagina: QueryDettTrac. Messaggio: "+ex.Message+"\r\n");
			}
		}

		protected void BtnElimina_Click(object sender, System.EventArgs e)
		{
			try
			{
                TRACCIATIDETTAGLIO tTracdett = (TRACCIATIDETTAGLIO)TracDett.Clone();

                TracciatiDettMgr pEnteTracDetMgr = new TracciatiDettMgr(DbDestinazione);
				pEnteTracDetMgr.Delete(TracDett);

                TracDett = new TRACCIATIDETTAGLIO();
                TracDett.FK_TRACCIATI_ID = tTracdett.FK_TRACCIATI_ID;
                TracDett.FK_TRACCIATI_ID_001 = tTracdett.FK_TRACCIATI_ID_001;
                TracDett.IDCOMUNE = tTracdett.IDCOMUNE;
			}
			catch ( Exception ex )
			{
				throw new Exception("Errore generato durante l'eliminazione della configurazione del dettaglio tracciato selezionato. Pagina: QueryDettTrac. Messaggio: "+ex.Message+"\r\n");
			}

            Response.Redirect("QueryDettTrac.aspx?idcomune=" + Esp.IDCOMUNE + "&idesportazione=" + Esp.ID);
		}

		protected void BtnChiudi_Click(object sender, System.EventArgs e)
		{
            Response.Redirect("ListEnteDetTrac.aspx?idcomune=" + Esp.IDCOMUNE + "&idesportazione=" + Esp.ID);
		}

		protected void ChkBoxFineRiga_CheckedChanged(object sender, System.EventArgs e)
		{
			TxtValore.Text = "";
			if (ChkBoxFineRiga.Checked)
				TxtValore.Enabled = false;
			else
				TxtValore.Enabled = true;
		}
	}
}
