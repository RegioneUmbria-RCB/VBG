using System;
using System.IO;
using System.Collections;
using System.ComponentModel;
using System.Web;
using System.Web.SessionState;
using System.Configuration;

namespace WSSigeproExport 
{
	/// <summary>
	/// Descrizione di riepilogo per Global.
	/// </summary>
	public class Global : System.Web.HttpApplication
	{
		/// <summary>
		/// Variabile di progettazione necessaria.
		/// </summary>
		private System.ComponentModel.IContainer components = null;

		public Global()
		{
			InitializeComponent();
		}	
		
		protected void Application_Start(Object sender, EventArgs e)
		{
            try
            {
                string sFolderPath = Server.MapPath(ConfigurationSettings.AppSettings["FILE_PATH"].ToString());
                if (Directory.Exists(sFolderPath))
                {
                    foreach (string dir in Directory.GetDirectories(sFolderPath))
                        Directory.Delete(dir, true);

                    foreach (string files in Directory.GetFiles(sFolderPath))
                        File.Delete(files);
                }
            }
            catch
            {
            }
		}
 
		protected void Session_Start(Object sender, EventArgs e)
		{
		}

		protected void Application_BeginRequest(Object sender, EventArgs e)
		{

		}

		protected void Application_EndRequest(Object sender, EventArgs e)
		{

		}

		protected void Application_AuthenticateRequest(Object sender, EventArgs e)
		{

		}

		protected void Application_Error(Object sender, EventArgs e)
		{

		}

		protected void Session_End(Object sender, EventArgs e)
		{
		}

		protected void Application_End(Object sender, EventArgs e)
		{

		}
			
		#region Codice generato da Progettazione Web Form
		/// <summary>
		/// Metodo necessario per il supporto della finestra di progettazione. Non modificare
		/// il contenuto del metodo con l'editor di codice.
		/// </summary>
		private void InitializeComponent()
		{    
			this.components = new System.ComponentModel.Container();
		}
		#endregion
	}
}

