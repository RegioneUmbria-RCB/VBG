using System;
using System.Collections;
using System.ComponentModel;
using System.Configuration;
using System.Data;
using System.Drawing;
using System.Web;
using System.Web.SessionState;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.HtmlControls;
using Init.SIGeProExport.Data;
using Init.SIGeProExport.Manager;
using SigeproExportData.Utils;
using System.Collections.Generic;

namespace WebSigeproExport
{
	/// <summary>
	/// Descrizione di riepilogo per ListParametri.
	/// </summary>
	public partial class ListParametri : BasePage
	{

		protected void Page_Load(object sender, System.EventArgs e)
		{
			if(!IsPostBack)
			{
				try
				{
                    DataGridParametri.PageSize = DatagridPageSize;
                    LblParametri.Text += Esp.titolo_pagina;

					SelectParametri();

                    this.BtnAgg.Enabled = ModificaIdcomune;
				}
				catch ( Exception ex )
				{
					throw new Exception("Errore generato all'apertura della lista dei tracciati. Pagina: ListTrac. Messaggio: "+ex.Message+"\r\n");
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
			this.DataGridParametri.PageIndexChanged += new System.Web.UI.WebControls.DataGridPageChangedEventHandler(this.DataGridParametri_PageIndexChanged);
			this.DataGridParametri.DeleteCommand += new System.Web.UI.WebControls.DataGridCommandEventHandler(this.DataGridParametri_DeleteCommand);
			this.DataGridParametri.ItemDataBound += new System.Web.UI.WebControls.DataGridItemEventHandler(this.DataGridParametri_ItemDataBound);

		}
		#endregion

		/// <summary>
		/// Metodo usato per caricare il datagrid dei tracciati in base all'esportazione selezionata in precedenza
		/// </summary>
		private void SelectParametri()
		{
			PARAMETRIESPORTAZIONE pParEsp = new PARAMETRIESPORTAZIONE();
            pParEsp.IDCOMUNE = Esp.IDCOMUNE;
			pParEsp.FK_ESP_ID = Esp.ID;
	
			ParametriEsportazioneMgr pParEspMgr = new ParametriEsportazioneMgr(DbDestinazione);
            List<PARAMETRIESPORTAZIONE> pLstParEsp = pParEspMgr.GetList(pParEsp);
			if ( pLstParEsp != null && pLstParEsp.Count >= 0 )
			{
				DataGridParametri.DataSource = pLstParEsp;
				Cache["ParametriTable"] = pLstParEsp;
				DataGridParametri.DataBind();
			}
		}

		protected void BtnAgg_Click(object sender, System.EventArgs e)
		{
            Parametro = new PARAMETRIESPORTAZIONE();
            Parametro.IDCOMUNE = Esp.IDCOMUNE;
            Parametro.FK_ESP_ID = Esp.ID;

			Response.Redirect("Parametro.aspx?idcomune=" + Esp.IDCOMUNE + "&idesportazione=" + Esp.ID);
		}

		protected void BtnChiudi_Click(object sender, System.EventArgs e)
        {
            Response.Redirect("Exp.aspx?idcomune=" + Esp.IDCOMUNE + "&idesportazione=" + Esp.ID);
		}

		private void DataGridParametri_ItemDataBound(object sender, System.Web.UI.WebControls.DataGridItemEventArgs e)
		{
			if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
			{
				string sID = DataGridParametri.DataKeys[e.Item.ItemIndex].ToString();
				PARAMETRIESPORTAZIONE pParEsp = new PARAMETRIESPORTAZIONE();
				pParEsp.ID = sID;
				CMessageDelete pMsgDlt = new CMessageDelete();
				string sMsg = pMsgDlt.GetMsgDelete(pParEsp,DbDestinazione);
                ((LinkButton)e.Item.Cells[5].Controls[0]).Enabled = ModificaIdcomune;    //elimina
				((LinkButton)e.Item.Cells[5].Controls[0]).Attributes.Add("OnClick","return ConfermaElimina('" + sMsg + "');");
			}
		}

		private void DataGridParametri_PageIndexChanged(object source, System.Web.UI.WebControls.DataGridPageChangedEventArgs e)
		{
			ArrayList pLstParEsp = (ArrayList)Cache["ParametriTable"];
			DataGridParametri.DataSource = pLstParEsp;
			DataGridParametri.SelectedIndex = -1;
			DataGridParametri.CurrentPageIndex = e.NewPageIndex;
			DataGridParametri.DataBind();

			DbDestinazione.Dispose();
		}

		protected void DataGridParametri_SelectedIndexChanged(object sender, System.EventArgs e)
		{
			try
			{
                string sIdParametro = DataGridParametri.SelectedItem.Cells[0].Text;
                string sIdComune = DataGridParametri.SelectedItem.Cells[1].Text;
                
                ParametriEsportazioneMgr pParEspMgr = new ParametriEsportazioneMgr(DbDestinazione);
                PARAMETRIESPORTAZIONE pParEsp = pParEspMgr.GetById(sIdComune,sIdParametro);
                Parametro = pParEsp;
			}
			catch ( Exception ex )
			{
				throw new Exception("Errore generato durante la selezione di un parametro. Pagina: ListParametri. Messaggio: "+ex.Message+"\r\n");
			}

            Response.Redirect("Parametro.aspx?idcomune=" + Esp.IDCOMUNE + "&idesportazione=" + Esp.ID);
		}

		private void DataGridParametri_DeleteCommand(object source, System.Web.UI.WebControls.DataGridCommandEventArgs e)
		{
            
			try
			{
                string sIdParametro = e.Item.Cells[0].Text;
                string sIdComune = e.Item.Cells[1].Text;

				ParametriEsportazioneMgr pParEspMgr = new ParametriEsportazioneMgr(DbDestinazione);
				PARAMETRIESPORTAZIONE pParEsp = pParEspMgr.GetById(sIdComune,sIdParametro);
				Delete(pParEsp);
				SelectParametri();
			}
			catch ( Exception ex )
			{
				throw new Exception("Errore generato durante l'eliminazione di un parametro. Pagina: ListParametri. Messaggio: "+ex.Message+"\r\n");
			}
            
		}

		private void Delete(PARAMETRIESPORTAZIONE pDataCls)
		{
            
			//Verifico che il parametro che si intende cancellare non è utilizzato in nessuna query della configurazione ente
			TRACCIATI pTrac = new TRACCIATI();
            pTrac.IDCOMUNE = pDataCls.IDCOMUNE;
			pTrac.FK_ESP_ID = pDataCls.FK_ESP_ID;
			pTrac.OrderBy = "OUT_ORDINE ASC";

			TracciatiMgr pTracMgr = new TracciatiMgr( DbDestinazione );
			List<TRACCIATI> pListTrac = pTracMgr.GetList( pTrac );

            foreach (TRACCIATI tracciato in pListTrac)
        	{
                if ( ! String.IsNullOrEmpty( tracciato.QUERY ) && tracciato.QUERY.IndexOf("&" + pDataCls.NOME.ToUpper()) > -1)
                {
                    string sMsg = "Impossibile eliminare il parametro perchè utilizzato nella query del tracciato: " + tracciato.DESCRIZIONE;
                    Page.RegisterStartupScript("test", "<script language='javascript'>InfoMess('" + sMsg + "')</script>");
                    return;
                }

		        TRACCIATIDETTAGLIO pTracDett = new TRACCIATIDETTAGLIO();
                pTracDett.IDCOMUNE = tracciato.IDCOMUNE;
                pTracDett.FK_TRACCIATI_ID = tracciato.ID;
                
                TracciatiDettMgr pTracDettMgr = new TracciatiDettMgr( DbDestinazione );
                List<TRACCIATIDETTAGLIO> pLstTracDett = pTracDettMgr.GetList(pTracDett);

                foreach (TRACCIATIDETTAGLIO dettaglio in pLstTracDett)
            	{
                    if (!String.IsNullOrEmpty(dettaglio.QUERY) && dettaglio.QUERY.IndexOf("&" + pDataCls.NOME.ToUpper()) != -1)
                    {
	                    string sMsg = "Impossibile eliminare il parametro perchè utilizzato in una query del dettaglio " + dettaglio.DESCRIZIONE + " del tracciato: " + tracciato.DESCRIZIONE;
	                    Page.RegisterStartupScript("test", "<script language='javascript'>InfoMess('" + sMsg + "')</script>");
	                    return;
                    }
            	}
	        }

			ParametriEsportazioneMgr pParEspMgr = new ParametriEsportazioneMgr(DbDestinazione);
			pParEspMgr.Delete(pDataCls);
        }
	}
}
