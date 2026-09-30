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

using System.Configuration;
using Init.SIGeProExport.Manager;
using Init.SIGeProExport.Data;
using PersonalLib2.Data;
using SigeproExportData.Utils;
using System.Collections.Generic;

namespace WebSigeproExport
{
	/// <summary>
	/// Descrizione di riepilogo per ListEnteTrac.
	/// </summary>
	public partial class ListEnteTrac : BasePage
	{

		protected void Page_Load(object sender, System.EventArgs e)
		{
			if(!IsPostBack)
			{
				try
				{
                    this.BtnNuovoTrac.Enabled = ModificaIdcomune;

                    DataGridEnte.PageSize = DatagridPageSize;

                    this.LblEnte.Text += Esp.IDCOMUNE;
                    this.lblEsportazione.Text += Esp.titolo_pagina;

					SelectEnteTrac();
				}
				catch ( Exception ex )
				{
					throw new Exception("Errore generato all'apertura della lista delle configurazioni dei tracciati. Pagina: ListEnteTrac. Messaggio: "+ex.Message+"\r\n");
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
			this.DataGridEnte.PageIndexChanged += new System.Web.UI.WebControls.DataGridPageChangedEventHandler(this.DataGridEnte_PageIndexChanged);
			this.DataGridEnte.DeleteCommand += new System.Web.UI.WebControls.DataGridCommandEventHandler(this.DataGridEnte_DeleteCommand);
			this.DataGridEnte.ItemDataBound += new System.Web.UI.WebControls.DataGridItemEventHandler(this.DataGridEnte_ItemDataBound);

		}
		#endregion

		private TRACCIATI GetTrac(string sID, string sEnte)
		{
            TracciatiMgr mgr = new TracciatiMgr(DbDestinazione);
            return mgr.GetById(sEnte, sID);
		}

		/// <summary>
		/// Metodo usato per caricare il datagrid delle configurazioni dei tracciati in base ai filtri selezionati in precedenza (IDCOMUNE) 
		/// </summary>
		private void SelectEnteTrac()
		{
			TRACCIATI pTrac = new TRACCIATI();
            pTrac.IDCOMUNE = Esp.IDCOMUNE;
			pTrac.FK_ESP_ID = Esp.ID;
			pTrac.OrderBy = "OUT_ORDINE ASC";

			TracciatiMgr pTracMgr = new TracciatiMgr( DbDestinazione );
            List<TRACCIATI> pLstTrac = pTracMgr.GetList(pTrac);

			DataTable dt = new DataTable();
			DataRow dr;

			dt.Columns.Add(new DataColumn("ID", typeof(string)));
			dt.Columns.Add(new DataColumn("IDCOMUNE", typeof(string)));
			dt.Columns.Add(new DataColumn("DESCRIZIONE", typeof(string)));

            foreach (TRACCIATI tracciato in pLstTrac)
            {
                dr = dt.NewRow();

                dr[0] = tracciato.ID;
                dr[1] = tracciato.IDCOMUNE;
                dr[2] = tracciato.DESCRIZIONE;

                dt.Rows.Add(dr);
            }


			DataGridEnte.DataSource = dt;
			Cache["EnteTracTable"] = dt;
			DataGridEnte.DataBind();
		}

		/// <summary>
		/// Metodo usato per modificare un record nel datagrid
		/// </summary>
		protected void DataGridEnte_SelectedIndexChanged(object sender, EventArgs e)
	    {
			int iRowIndex = DataGridEnte.SelectedIndex /*+ DataGridEnte.CurrentPageIndex * DataGridEnte.PageSize*/;
			string idTracciato = DataGridEnte.DataKeys[iRowIndex].ToString();

            TRACCIATI pEnteTrac = GetTrac(idTracciato, Esp.IDCOMUNE);

			if ( pEnteTrac != null )
			{
				Trac = pEnteTrac;
				Response.Redirect("QueryTrac.aspx?idocmune=" + Esp.IDCOMUNE + "&idesportazione=" + Esp.ID);
			}
        }

		/// <summary>
		/// Metodo usato per gestire la paginazione del datagrid
		/// </summary>
		protected void DataGridEnte_PageIndexChanged(object source, DataGridPageChangedEventArgs e)
		{
			DataTable dt = (DataTable)Cache["EnteTracTable"];
			DataGridEnte.DataSource = dt;
			DataGridEnte.SelectedIndex = -1;
			DataGridEnte.CurrentPageIndex = e.NewPageIndex;
			DataGridEnte.DataBind();
			DbDestinazione.Dispose();
		}

		/// <summary>
		/// Metodo usato per eliminare un record dal datagrid
		/// </summary>
		protected void DataGridEnte_DeleteCommand(object source, DataGridCommandEventArgs e)
		{
			try
			{
				string idTracciato = DataGridEnte.DataKeys[e.Item.ItemIndex].ToString();
                TRACCIATI pEnteTrac = GetTrac(idTracciato, Esp.IDCOMUNE);
				Delete(pEnteTrac);

				SelectEnteTrac();
			}
			catch ( Exception ex )
			{
				throw new Exception("Errore generato durante l'eliminazione della configurazione di un tracciato. Pagina: ListEnteTrac. Messaggio: "+ex.Message+"\r\n");
			}
		}

		private void Delete(TRACCIATI pDataCls)
		{
            TracciatiMgr mgr = new TracciatiMgr(DbDestinazione);
            mgr.Delete(pDataCls);
        }

		protected void BtnChiudi_Click(object sender, EventArgs e)
		{
            Response.Redirect("Exp.aspx?idcomune=" + Esp.IDCOMUNE + "&idesportazione=" + Esp.ID);
		}

		private void DataGridEnte_ItemDataBound(object sender, System.Web.UI.WebControls.DataGridItemEventArgs e)
		{
			if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
			{
				string idTracciato = DataGridEnte.DataKeys[e.Item.ItemIndex].ToString();
                TRACCIATI pEnteTrac = GetTrac(idTracciato, Esp.IDCOMUNE);

				CMessageDelete pMsgDlt = new CMessageDelete();
				string sMsg = pMsgDlt.GetMsgDelete(pEnteTrac,DbDestinazione);
                ((LinkButton)e.Item.Cells[4].Controls[0]).Enabled = ModificaIdcomune;
                if (ModificaIdcomune)
                    ((LinkButton)e.Item.Cells[4].Controls[0]).Attributes.Add("OnClick", "return ConfermaElimina('" + sMsg + "');");
			}
		}

        protected void btnEsportazione_Click(object sender, EventArgs e)
        {
            Response.Redirect("Exp.aspx?idcomune=" + Esp.IDCOMUNE + "&idesportazione=" + Esp.ID);
        }

        protected void BtnNuovoTrac_Click(object sender, EventArgs e)
        {
            Trac = new TRACCIATI();
            Trac.IDCOMUNE = Esp.IDCOMUNE;
            Trac.FK_ESP_ID = Esp.ID;
            Response.Redirect("QueryTrac.aspx?idcomune=" + Esp.IDCOMUNE + "&idesportazione=" + Esp.ID);
        }
	}
}
