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
using Init.SIGeProExport.Data;
using Init.SIGeProExport.Manager;

namespace WebSigeproExport
{
	/// <summary>
	/// Descrizione di riepilogo per Parametri.
	/// </summary>
	public partial class Parametri : BasePage
	{

		protected void Page_Load(object sender, System.EventArgs e)
		{
			if(!IsPostBack)
			{
				try
				{
					LlbExp.Text += Esp.titolo_pagina;

                    this.BtnSalva.Enabled = ModificaIdcomune;
                    this.BtnNuovo.Enabled = ModificaIdcomune;

                    if (Parametro != null)
                    {
                        this.TxtID.Text = Parametro.ID;
                        this.txtIdcomune.Text = Parametro.IDCOMUNE;
                        this.TxtNome.Text = Parametro.NOME;
                        this.TxtDesc.Text = Parametro.DESCRIZIONE;
                    }
                    else {
                        this.txtIdcomune.Text = Esp.IDCOMUNE;
                    }

				}
				catch ( Exception ex )
				{
					throw new Exception("Errore generato all'apertura del parametro selezionato. Pagina: Parametro. Messaggio: "+ex.Message+"\r\n");
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

		}
		#endregion

		protected void BtnNuovo_Click(object sender, System.EventArgs e)
		{
			TxtID.Text = "";
			TxtNome.Text = "";
			TxtDesc.Text = "";

            Parametro = null;
		}

		protected void BtnSalva_Click(object sender, System.EventArgs e)
		{
			try
			{
				SaveParametro();
			}
			catch ( Exception ex )
			{
				throw new Exception("Errore generato durante il salvataggio del parametro selezionato. Pagina: Parametro. Messaggio: "+ex.Message+"\r\n");
			}
		}

		/// <summary>
		/// Metodo usato per salvare un nuovo tracciato o modificarne uno esistente
		/// </summary>
		private void SaveParametro()
		{
			ParametriEsportazioneMgr pParEspMgr = new ParametriEsportazioneMgr(DbDestinazione);
			PARAMETRIESPORTAZIONE pParEsp = new PARAMETRIESPORTAZIONE();

			pParEsp.NOME = TxtNome.Text;
			pParEsp.DESCRIZIONE = TxtDesc.Text;
			pParEsp.FK_ESP_ID = Esp.ID;
			pParEsp.FK_ESP_ID_002 = Esp;
            pParEsp.IDCOMUNE = this.txtIdcomune.Text;


			if ( TxtID.Text != "" )
			{
				pParEsp.ID = TxtID.Text;
				pParEsp = pParEspMgr.Update(pParEsp);
			}
			else
			{
				pParEsp = pParEspMgr.Insert(pParEsp);
				TxtID.Text = pParEsp.ID;
			}
			Parametro = pParEsp;
		}


		protected void BtnChiudi_Click(object sender, System.EventArgs e)
		{
            Response.Redirect("ListParametri.aspx?idcomune=" + Esp.IDCOMUNE + "&idesportazione=" + Esp.ID);
		}
	}
}
