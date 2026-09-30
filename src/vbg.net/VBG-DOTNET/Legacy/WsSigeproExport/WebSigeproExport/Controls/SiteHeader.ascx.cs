namespace WebSigeproExport.Controls
{
	using System;
	using System.Data;
	using System.Drawing;
	using System.Web;
	using System.Web.UI.WebControls;
	using System.Web.UI.HtmlControls;
    using PersonalLib2.Data;
    using System.Configuration;
    using Init.SIGeProExport.Manager;

	/// <summary>
	///		Descrizione di riepilogo per SiteHeader.
	/// </summary>
	public partial class SiteHeader : System.Web.UI.UserControl
	{

		protected void Page_Load(object sender, System.EventArgs e)
		{
            this.PreRender += new EventHandler(SiteHeader_PreRender);
		}

        void SiteHeader_PreRender(object sender, EventArgs e)
        {
            if (string.IsNullOrEmpty(this.lblVersione.Text))
            {
                ProviderType initialProviderType = (ProviderType)Enum.Parse(typeof(ProviderType), ConfigurationSettings.AppSettings["PROVIDERTYPE_CONFIG"].ToString(), true);
                using (var db = new DataBase(ConfigurationSettings.AppSettings["CONNECTIONSTRING_CONFIG"].ToString(), initialProviderType))
                {
                    this.lblVersione.Text = "Versione " + new VersioneMgr(db).GetVersione();
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
	}
}
