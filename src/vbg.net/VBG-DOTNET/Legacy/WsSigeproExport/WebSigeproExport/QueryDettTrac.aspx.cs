using Init.SIGeProExport.Data;
using Init.SIGeProExport.Manager;
using Parser;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Data;

namespace WebSigeproExport
{
    /// <summary>
    /// Descrizione di riepilogo per QueryDettTrac.
    /// </summary>
    public partial class QueryDettTrac : BasePage
    {

        protected void Page_Load(object sender, System.EventArgs e)
        {
            if (!this.IsPostBack)
            {
                this.BtnElimina.Attributes.Add("OnClick", "return ConfermaElimina('Sei sicuro di voler eliminare questa configurazione del dettaglio tracciato?');");

                this.LblExp.Text += this.Esp.titolo_pagina;
                this.LblEnte.Text += this.TracDett.IDCOMUNE;
                this.LblTrac.Text += this.Trac.titolo_pagina;
                this.LblInfoCSV.Visible = (this.Esp.FK_TIPIESPORTAZIONE_CODICE == "CSV");
                this.TxtLungh.Visible = this.LblLungh.Visible = (this.Esp.FK_TIPIESPORTAZIONE_CODICE == "TXT");


                this.TxtIDCOMUNE.Text = this.TracDett.IDCOMUNE;

                this.BtnSalva.Enabled = this.ModificaIdcomune;

                if (!String.IsNullOrEmpty(this.TracDett.ID))
                {
                    this.TxtDesc.Text = this.TracDett.DESCRIZIONE;
                    this.TxtID.Text = this.TracDett.ID;
                    this.TxtLungh.Text = this.TracDett.LUNGHEZZA;
                    this.TxtNote.Text = this.TracDett.NOTE;
                    this.TxtOrdine.Text = this.TracDett.OUT_ORDINE;
                    this.TxtQuery.Text = this.TracDett.QUERY;
                    this.ChkBoxCampoTestuale.Checked = (this.TracDett.CAMPOTESTO == "1");

                    if (this.TracDett.VALORE == "\r\n")
                    {
                        this.ChkBoxFineRiga.Checked = true;
                        this.TxtValore.Text = "";
                        this.TxtValore.Enabled = false;
                    }
                    else
                    {
                        this.ChkBoxFineRiga.Checked = false;
                        this.TxtValore.Enabled = true;
                        this.TxtValore.Text = this.TracDett.VALORE;
                    }
                    this.TxtXmlTag.Text = this.TracDett.OUT_XMLTAG;
                    this.ChckObbl.Checked = (this.TracDett.OBBLIGATORIO == "1");
                    this.BtnElimina.Enabled = this.ModificaIdcomune;
                }
                else
                {
                    this.TxtDesc.Text = String.Empty;
                    this.TxtID.Text = String.Empty;
                    this.TxtLungh.Text = String.Empty;
                    this.TxtNote.Text = String.Empty;
                    this.ChckObbl.Checked = false;
                    this.TxtOrdine.Text = this.CalcolaOrdineDettaglioTracciato();
                    this.TxtXmlTag.Text = String.Empty;
                    this.TxtQuery.Text = String.Empty;
                    this.TxtValore.Text = String.Empty;
                    this.BtnElimina.Enabled = false;
                }
            }

            this.ChkBoxCampoTestuale.Visible = (this.Esp.FK_TIPIESPORTAZIONE_CODICE == "CSV");
            this.LabelCampoTestuale.Visible = this.ChkBoxCampoTestuale.Visible;


        }

        private string CalcolaOrdineDettaglioTracciato()
        {
            TRACCIATIDETTAGLIO filter = new TRACCIATIDETTAGLIO();
            filter.IDCOMUNE = this.Trac.IDCOMUNE;
            filter.FK_TRACCIATI_ID = this.Trac.ID;
            filter.OrderBy = "OUT_ORDINE DESC";

            TracciatiDettMgr tdm = new TracciatiDettMgr(this.DbDestinazione);
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
            this.InitializeComponent();
            this.InitializeMyComponent();
            base.OnInit(e);
        }

        private void InitializeMyComponent()
        {
            this.Help1.onHelpClick += new WebSigeproExport.Controls.Help.HelpClick(this.Help1_onHelpClick);
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
            StringCollection pCllValues = this.ListValues();
            StringCollection pCllParametries = this.ListParametries();
            this.Help1.HParameter = pCllParametries;
            this.Help1.HXml = pCllValues;
            this.Help1.HQuery = this.Queries();
        }

        private StringCollection ListParametries()
        {
            ParametriEsportazioneMgr pParEspMgr = new ParametriEsportazioneMgr(this.DbDestinazione);
            PARAMETRIESPORTAZIONE pParEsp = new PARAMETRIESPORTAZIONE();
            pParEsp.IDCOMUNE = this.Trac.IDCOMUNE;
            pParEsp.FK_ESP_ID = this.Trac.FK_ESP_ID;
            List<PARAMETRIESPORTAZIONE> pList = pParEspMgr.GetList(pParEsp);

            StringCollection pCllParametries = new StringCollection();

            foreach (PARAMETRIESPORTAZIONE elem in pList)
            {
                pCllParametries.Add(elem.NOME.ToUpper());
            }

            return pCllParametries;
        }

        private StringCollection ListValues()
        {
            XMLParser pXmlPrs = new XMLParser();
            pXmlPrs.XmlSchema = this.Esp.INPUT_XSD;
            DataSet ds = pXmlPrs.Parse();
            StringCollection pCllValues = new StringCollection();
            if (ds != null)
            {
                foreach (DataColumn pClm in ds.Tables[0].Columns)
                    pCllValues.Add(pClm.ColumnName);
            }
            return pCllValues;
        }

        private StringCollection Queries()
        {
            StringCollection retVal = new StringCollection();

            try
            {
                string IdComune = this.Trac.IDCOMUNE;
                string IdEsportazione = this.Trac.FK_ESP_ID;
                string IdTracciato = this.Trac.ID;

                int DettOrdineMax = -1;

                if (!String.IsNullOrEmpty(this.TracDett.OUT_ORDINE))
                    DettOrdineMax = Convert.ToInt32(this.TracDett.OUT_ORDINE);

                TracciatiDettMgr tracDet = new TracciatiDettMgr(this.DbDestinazione);

                retVal = tracDet.GetQueries(IdEsportazione, IdTracciato, IdComune, DettOrdineMax);

            }
            catch (System.Exception ex)
            {
                throw new Exception("Errore generato durante la creazione della lista delle variabili. Pagina: QueryDettTrac. Messaggio: " + ex.Message + "\r\n");
            }

            return retVal;
        }

        private void SetValueFineRiga(TRACCIATIDETTAGLIO pTracDet)
        {
            if (this.ChkBoxFineRiga.Checked)
                pTracDet.VALORE = "\r\n";
            else
                pTracDet.VALORE = this.TxtValore.Text;
        }

        /// <summary>
        /// Metodo usato per salvare una nuova configurazione dei dettagli tracciati o modificarne una esistente
        /// </summary>
        private void SaveQuery()
        {
            TracciatiDettMgr pCfg_TracDetMgr = new TracciatiDettMgr(this.DbDestinazione);

            this.TracDett.QUERY = this.TxtQuery.Text;
            this.SetValueFineRiga(this.TracDett);

            if (String.IsNullOrEmpty(this.TracDett.ID))
            {
                this.TracDett = pCfg_TracDetMgr.Insert(this.TracDett);
                this.BtnElimina.Visible = true;
                //sMod = "true";
                this.Page.RegisterStartupScript("test", "<script language='javascript'>InfoMess('Inserimento effettuato!')</script>");
            }
            else
            {
                pCfg_TracDetMgr.Update(this.TracDett);
                this.Page.RegisterStartupScript("test", "<script language='javascript'>InfoMess('Aggiornamento effettuato!')</script>");
            }
        }

        protected void BtnSalva_Click(object sender, System.EventArgs e)
        {
            try
            {
                TracciatiDettMgr pCfg_TracDetMgr = new TracciatiDettMgr(this.DbDestinazione);

                this.TracDett.DESCRIZIONE = this.TxtDesc.Text;
                this.TracDett.FK_TRACCIATI_ID = this.Trac.ID;
                this.TracDett.IDCOMUNE = this.TxtIDCOMUNE.Text;
                this.TracDett.LUNGHEZZA = this.TxtLungh.Text;
                this.TracDett.NOTE = this.TxtNote.Text;
                this.TracDett.OBBLIGATORIO = (this.ChckObbl.Checked) ? "1" : "0";
                this.TracDett.OUT_ORDINE = this.TxtOrdine.Text;
                this.TracDett.OUT_XMLTAG = this.TxtXmlTag.Text;
                this.TracDett.QUERY = this.TxtQuery.Text;
                this.TracDett.CAMPOTESTO = (this.ChkBoxCampoTestuale.Checked) ? "1" : "0";

                this.SetValueFineRiga(this.TracDett);
                if (String.IsNullOrEmpty(this.TracDett.ID))
                {
                    this.TracDett = pCfg_TracDetMgr.Insert(this.TracDett);
                    this.BtnElimina.Enabled = true;
                }
                else
                {
                    pCfg_TracDetMgr.Update(this.TracDett);
                }

                this.Response.Redirect("QueryDettTrac.aspx?idcomune=" + this.Esp.IDCOMUNE + "&idesportazione=" + this.Esp.ID);

            }
            catch (Exception ex)
            {
                throw new Exception("Errore generato durante il salvataggio della configurazione del dettaglio tracciato selezionato. Pagina: QueryDettTrac. Messaggio: " + ex.Message + "\r\n");
            }
        }

        protected void BtnElimina_Click(object sender, System.EventArgs e)
        {
            try
            {
                TRACCIATIDETTAGLIO tTracdett = (TRACCIATIDETTAGLIO)this.TracDett.Clone();

                TracciatiDettMgr pEnteTracDetMgr = new TracciatiDettMgr(this.DbDestinazione);
                pEnteTracDetMgr.Delete(this.TracDett);

                this.TracDett = new TRACCIATIDETTAGLIO();
                this.TracDett.FK_TRACCIATI_ID = tTracdett.FK_TRACCIATI_ID;
                this.TracDett.FK_TRACCIATI_ID_001 = tTracdett.FK_TRACCIATI_ID_001;
                this.TracDett.IDCOMUNE = tTracdett.IDCOMUNE;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore generato durante l'eliminazione della configurazione del dettaglio tracciato selezionato. Pagina: QueryDettTrac. Messaggio: " + ex.Message + "\r\n");
            }

            this.Response.Redirect("QueryDettTrac.aspx?idcomune=" + this.Esp.IDCOMUNE + "&idesportazione=" + this.Esp.ID);
        }

        protected void BtnChiudi_Click(object sender, System.EventArgs e)
        {
            this.Response.Redirect("ListEnteDetTrac.aspx?idcomune=" + this.Esp.IDCOMUNE + "&idesportazione=" + this.Esp.ID);
        }

        protected void ChkBoxFineRiga_CheckedChanged(object sender, System.EventArgs e)
        {
            this.TxtValore.Text = "";
            if (this.ChkBoxFineRiga.Checked)
                this.TxtValore.Enabled = false;
            else
                this.TxtValore.Enabled = true;
        }
    }
}
