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

namespace WebSigeproExport
{
	/// <summary>
	/// Descrizione di riepilogo per WebForm1.
	/// </summary>
	public partial class Main : BasePage
	{
		protected void Page_Load(object sender, System.EventArgs e)
		{
            Response.Redirect("SelectEnte.aspx");
		}

		 #region Codice generato da Progettazione Web Form
					   override protected void OnInit(EventArgs e)
					   {
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
	}
}
