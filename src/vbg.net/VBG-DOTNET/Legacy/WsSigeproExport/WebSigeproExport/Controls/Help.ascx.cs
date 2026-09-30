using System;
using System.Data;
using System.Drawing;
using System.Web;
using System.Collections.Specialized;
using System.Web.UI.WebControls;
using System.Web.UI.HtmlControls;

namespace WebSigeproExport.Controls
{
	/// <summary>
	///		Descrizione di riepilogo per Help.
	/// </summary>
	public partial class Help : System.Web.UI.UserControl
	{
		
		
			
		private StringCollection hquery = new StringCollection();
		public StringCollection HQuery
		{
			get{ return hquery; }
			set{ hquery = value; }
		}

		private StringCollection hxml = new StringCollection();
		public StringCollection HXml
		{
			get{ return hxml; }
			set{ hxml = value; }
		}

		private StringCollection hparameter = new StringCollection();
		public StringCollection HParameter
		{
			get{ return hparameter; }
			set{ hparameter = value; }
		}


		public delegate void HelpClick( WebSigeproExport.Controls.Help control );
		public event HelpClick onHelpClick;

		protected void Page_Load(object sender, System.EventArgs e)
		{
			// Inserire qui il codice utente necessario per inizializzare la pagina.
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
			this.imgHelp.Click += new System.Web.UI.ImageClickEventHandler(this.imgHelp_Click);

		}
		#endregion

		private void imgHelp_Click(object sender, System.Web.UI.ImageClickEventArgs e)
		{
			this.lblList.Text = String.Empty;

			if ( onHelpClick != null )
				onHelpClick( this );

			SigeproExportData.Utils.Help hl = new SigeproExportData.Utils.Help( hquery, hxml, hparameter );

			StringCollection vars = hl.GetVarList();

			AddValues( vars );
		}

		private void AddValues( StringCollection vars )
		{
			foreach( string var in vars )
			{
				string tVar = var;

				//1. Tolgo eventuali owner e nome tabella
				string[] ar = tVar.Split( Convert.ToChar(".") );
				tVar = ar[ ar.Length -1 ];

				//2. Controllo che la variabile non sia stata precedentemente aggiunta
				string pList = "<br>" + this.lblList.Text;
				if ( pList.IndexOf("<br>" + tVar + "<br>" ) == -1 )
					this.lblList.Text += tVar + "<br>";
			}
		}

	}
}
