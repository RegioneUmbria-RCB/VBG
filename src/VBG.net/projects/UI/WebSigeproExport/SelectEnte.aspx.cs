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
using PersonalLib2;
using PersonalLib2.Data;
//using System.Data.OracleClient;

namespace WebSigeproExport
{
	/// <summary>
	/// Descrizione di riepilogo per SelectEnte.
	/// </summary>
	public partial class SelectEnte : BasePage
	{
	

		protected void Page_Load(object sender, System.EventArgs e)
		{
			if(!IsPostBack)
			{
				try
				{
					//Carico la combo box con i valori letti dalla tabella ESPORTAZIONI
					LoadDDExp();

                    //Svuoto eventuali session
                    ClearSessions();
				}
				catch ( Exception ex )
				{
					throw new Exception("Errore generato all'apertura della pagina di selezione dell'ente. Pagina: SelectEnte. Messaggio: "+ex.Message+"\r\n");
				}
			}
		}

        private void ClearSessions()
        {
            Esp = null;
            Trac = null;
            TracDett = null;
            Parametro = null;
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

		/// <summary>
		/// Questo metodo è usato per caricare la combo box (Esportazioni) con i valori letti dalla tabella ESPORTAZIONI
		/// </summary>
		private void LoadDDExp()
		{
			ESPORTAZIONI pExp = new ESPORTAZIONI();
            pExp.OrderBy = "DESCRIZIONE";
			EsportazioniMgr pExpMgr = new EsportazioniMgr(DbDestinazione);
			ArrayList pLstExp = pExpMgr.GetList(pExp);
			if ( pLstExp != null && pLstExp.Count >= 0 )
			{
				DDLstExp.DataSource = pLstExp;
                DDLstExp.DataTextField = "desc_estesa";
                DDLstExp.DataValueField = "chiave_primaria";
				DDLstExp.DataBind();
			}
		}			   

		protected void BtnCerca_Click(object sender, System.EventArgs e)
		{
            string idcomune = DDLstExp.SelectedItem.Value.Split(Convert.ToChar("-"))[0];
            string idesportazione = DDLstExp.SelectedItem.Value.Split(Convert.ToChar("-"))[1];
            Esp = new EsportazioniMgr(DbDestinazione).GetById(idcomune, idesportazione);

            Response.Redirect("Exp.aspx?idcomune=" + idcomune + "&idesportazione=" + idesportazione);
		}

		protected void BtnChiudi_Click(object sender, System.EventArgs e)
		{
			Response.Redirect("Main.aspx");
		}

        protected void BtnNuovo_Click(object sender, EventArgs e)
        {
            Response.Redirect("Exp.aspx?idcomune=&idesportazione=");
        }			   
	}
}
