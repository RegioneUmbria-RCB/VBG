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

namespace WebSigeproExport
{
	/// <summary>
	/// Descrizione di riepilogo per Help.
	/// </summary>
	public partial class Help : System.Web.UI.Page
	{
	
		protected void Page_Load(object sender, System.EventArgs e)
		{
			
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
			this.Help1.onHelpClick += new WebSigeproExport.Controls.Help.HelpClick(Help1_onHelpClick);
		}
		#endregion

		private void Help1_onHelpClick(WebSigeproExport.Controls.Help control)
		{
			control.HQuery.Add( this.TextBox1.Text );
		}
	}
}
