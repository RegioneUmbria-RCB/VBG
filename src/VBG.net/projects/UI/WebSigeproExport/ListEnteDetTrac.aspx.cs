using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.ComponentModel;
using System.Drawing;
using System.Web.SessionState;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.HtmlControls;

using Init.SIGeProExport.Manager;
using Init.SIGeProExport.Data;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace WebSigeproExport
{
	public partial class ListEnteDetTrac : BasePage
	{

		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
			{
				try
				{
                    this.LblEnte.Text += Esp.IDCOMUNE;
                    this.LblExp.Text += Esp.titolo_pagina;
                    this.LblTrac.Text += Trac.titolo_pagina;

                    this.LblInfoCSV.Visible = (Esp.FK_TIPIESPORTAZIONE_CODICE == "CSV");

                    DataGridEnteDetTrac.PageSize = DatagridPageSize;

                    this.BtnNuovo.Enabled = ModificaIdcomune;
					
					SelectEnteDetTrac();
				}
				catch ( Exception ex )
				{
					throw new Exception("Errore generato all'apertura della lista delle configurazioni dei dettagli tracciati. Pagina: ListEnteDetTracciato. Messaggio: "+ex.Message+"\r\n");
				}
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
			this.DataGridEnteDetTrac.PageIndexChanged += new System.Web.UI.WebControls.DataGridPageChangedEventHandler(this.DataGridEnteDetTrac_PageIndexChanged);
			this.DataGridEnteDetTrac.DeleteCommand += new System.Web.UI.WebControls.DataGridCommandEventHandler(this.DataGridEnteDetTrac_DeleteCommand);
			this.DataGridEnteDetTrac.ItemDataBound += new System.Web.UI.WebControls.DataGridItemEventHandler(this.DataGridEnteDetTrac_ItemDataBound);
            this.DataGridEnteDetTrac.UpdateCommand += new System.Web.UI.WebControls.DataGridCommandEventHandler(this.DataGridEnteDetTrac_UpdateCommand);

		}

		#endregion

		private TRACCIATIDETTAGLIO GetTracDett(string IdTracciatiDettaglio, string sEnte)
		{
            TracciatiDettMgr mgr = new TracciatiDettMgr(DbDestinazione);
            return mgr.GetById(sEnte, IdTracciatiDettaglio);
		}

		/// <summary>
		/// Metodo usato per caricare il datagrid delle configurazioni dei  dettagli tracciati in base ai filtri selezionati in precedenza 
		/// </summary>
		private void SelectEnteDetTrac()
		{
            //aggiungo le colonne
            DataTable dt = new DataTable();
            DataRow dr;

            dt.Columns.Add(new DataColumn("ID", typeof(string)));
            dt.Columns.Add(new DataColumn("IDCOMUNE", typeof(string)));
            dt.Columns.Add(new DataColumn("DESCRIZIONE", typeof(string)));
            dt.Columns.Add(new DataColumn("OBBLIGATORIO", typeof(string)));
            dt.Columns.Add(new DataColumn("QUERY", typeof(string)));
            dt.Columns.Add(new DataColumn("VALORE", typeof(string)));
            dt.Columns.Add(new DataColumn("CAMPOTESTO", typeof(string)));

            //aggiungo i dati
			TRACCIATIDETTAGLIO pTracDett = new TRACCIATIDETTAGLIO();
            pTracDett.IDCOMUNE = Trac.IDCOMUNE;
			pTracDett.FK_TRACCIATI_ID = Trac.ID;
			pTracDett.OrderBy = "OUT_ORDINE ASC";

			TracciatiDettMgr pTracDettMgr = new TracciatiDettMgr( DbDestinazione );
            List<TRACCIATIDETTAGLIO> pLstTracDett = pTracDettMgr.GetList(pTracDett);

            foreach (TRACCIATIDETTAGLIO tracDett in pLstTracDett)
            {
                dr = dt.NewRow();

                dr[0] = tracDett.ID;
                dr[1] = tracDett.IDCOMUNE;
                dr[2] = tracDett.DESCRIZIONE;
                dr[3] = tracDett.OBBLIGATORIO;
                dr[4] = tracDett.QUERY;
                dr[5] = tracDett.VALORE;
                dr[6] = tracDett.CAMPOTESTO;

                dt.Rows.Add(dr);
            }

			DataGridEnteDetTrac.DataSource = dt;
			Cache["EnteDetTracTable"] = dt;
			DataGridEnteDetTrac.DataBind();
		}

		/// <summary>
		/// Metodo usato per modificare un record nel datagrid
		/// </summary>
		protected void DataGridEnteDetTrac_SelectedIndexChanged(object sender, EventArgs e)
		{
			int iRowIndex = DataGridEnteDetTrac.SelectedIndex /*+ DataGridEnteDetTrac.CurrentPageIndex * DataGridEnteDetTrac.PageSize*/;
			string idTracciatoDettaglio = DataGridEnteDetTrac.DataKeys[iRowIndex].ToString();

            TRACCIATIDETTAGLIO pEnteDetTrac = GetTracDett(idTracciatoDettaglio, Trac.IDCOMUNE);
		
			if ( pEnteDetTrac != null )
			{
				TracDett = pEnteDetTrac;
                Response.Redirect("QueryDettTrac.aspx?idcomune=" + Esp.IDCOMUNE + "&idesportazione=" + Esp.ID);
			}

		}

		/// <summary>
		/// Metodo usato per gestire la paginazione del datagrid
		/// </summary>
		protected void DataGridEnteDetTrac_PageIndexChanged(object source, DataGridPageChangedEventArgs e)
		{
			DataTable dt = (DataTable)Cache["EnteDetTracTable"];
			DataGridEnteDetTrac.DataSource = dt;
			DataGridEnteDetTrac.SelectedIndex = -1;
			DataGridEnteDetTrac.CurrentPageIndex = e.NewPageIndex;
			DataGridEnteDetTrac.DataBind();
			DbDestinazione.Dispose();
		}

		/// <summary>
		/// Metodo usato per eliminare un record dal datagrid
		/// </summary>
		protected void DataGridEnteDetTrac_DeleteCommand(object source, DataGridCommandEventArgs e)
		{
			try
			{
				string idTracciatoDettaglio = DataGridEnteDetTrac.DataKeys[e.Item.ItemIndex].ToString();
                TRACCIATIDETTAGLIO pEnteDetTrac = GetTracDett(idTracciatoDettaglio, Trac.IDCOMUNE);

                TracciatiDettMgr pEnteDetTracMgr = new TracciatiDettMgr(DbDestinazione);
				pEnteDetTracMgr.Delete(pEnteDetTrac);

				SelectEnteDetTrac();
			}
			catch ( Exception ex )
			{
				throw new Exception("Errore generato durante l'eliminazione della configurazione di un dettaglio tracciato. Pagina: ListEnteDetTrac. Messaggio: "+ex.Message+"\r\n");
			}

		}

        protected void DataGridEnteDetTrac_UpdateCommand(object source, DataGridCommandEventArgs e)
        {
            string IdTracciatiDettaglio = DataGridEnteDetTrac.DataKeys[e.Item.ItemIndex].ToString();
            TRACCIATIDETTAGLIO pEnteDetTrac = GetTracDett(IdTracciatiDettaglio, Trac.IDCOMUNE);
            TracciatiDettMgr pEnteDetTracMgr = new TracciatiDettMgr(DbDestinazione);

            pEnteDetTrac.QUERY = ((TextBox)e.Item.Cells[4].Controls[1]).Text;
            if (((CheckBox)e.Item.Cells[5].Controls[3]).Checked)
            {
                pEnteDetTrac.VALORE = "\r\n";
            }
            else
            {
                pEnteDetTrac.VALORE = ((TextBox)e.Item.Cells[5].Controls[1]).Text;
            }

            if (((CheckBox)e.Item.Cells[5].Controls[5]).Checked)
            {
                pEnteDetTrac.CAMPOTESTO = "1";
            }
            else
            {
                pEnteDetTrac.CAMPOTESTO = "0";
            }

            pEnteDetTracMgr.Update(pEnteDetTrac);
            //}

            SelectEnteDetTrac();
        }
		
		protected void BtnChiudi_Click(object sender, EventArgs e)
		{
            Response.Redirect("QueryTrac.aspx?idcomune=" + Esp.IDCOMUNE + "&idesportazione=" + Esp.ID);
		}

		private void DataGridEnteDetTrac_ItemDataBound(object sender, System.Web.UI.WebControls.DataGridItemEventArgs e)
		{
			if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
			{
                ((TextBox)e.Item.Cells[4].Controls[1]).Text = ((System.Data.DataRowView)(e.Item.DataItem)).Row["QUERY"].ToString();

                if (((System.Data.DataRowView)(e.Item.DataItem)).Row["VALORE"].ToString() == "\r\n")
                {
                    ((TextBox)e.Item.Cells[5].Controls[1]).Text = "";
                    ((CheckBox)e.Item.Cells[5].Controls[3]).Checked = true;
                }
                else 
                {
                    ((TextBox)e.Item.Cells[5].Controls[1]).Text = ((System.Data.DataRowView)(e.Item.DataItem)).Row["VALORE"].ToString();
                    ((CheckBox)e.Item.Cells[5].Controls[3]).Checked = false;
                }

                ((CheckBox)e.Item.Cells[5].Controls[5]).Visible = (Esp.FK_TIPIESPORTAZIONE_CODICE == "CSV");

                if (((System.Data.DataRowView)(e.Item.DataItem)).Row["CAMPOTESTO"].ToString() == "1")
                {
                    ((CheckBox)e.Item.Cells[5].Controls[5]).Checked = true;
                }
                else
                {
                    ((CheckBox)e.Item.Cells[5].Controls[5]).Checked = false;
                }

                

                ((LinkButton)e.Item.Cells[6].Controls[0]).Enabled = ModificaIdcomune;
                ((LinkButton)e.Item.Cells[8].Controls[0]).Enabled = ModificaIdcomune;
                if (ModificaIdcomune)
                    ((LinkButton)e.Item.Cells[8].Controls[0]).Attributes.Add("OnClick", "return ConfermaElimina('Sei sicuro di voler eliminare questa configurazione del Tracciato Dettaglio?');");
                    
                
			}
		}

        protected void BtnNuovo_Click(object sender, EventArgs e)
        {
            TracDett = new TRACCIATIDETTAGLIO();
            TracDett.FK_TRACCIATI_ID = Trac.ID;
            TracDett.IDCOMUNE = Trac.IDCOMUNE;
            TracDett.FK_TRACCIATI_ID_001 = Trac;

            Response.Redirect("QueryDettTrac.aspx?idcomune=" + Esp.IDCOMUNE + "&idesportazione=" + Esp.ID);
        }

	}
}
