using System;
using System.ComponentModel;
using System.Web;
using System.Configuration;
using System.IO;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.Manager;
using Init.SIGePro.Protocollo.ProtocolloFactories;

namespace SIGePro.Net
{
    /// <summary>
    /// Descrizione di riepilogo per Global.
    /// </summary>
    public class Global : HttpApplication
    {
        /// <summary>
        /// Variabile di progettazione necessaria.
        /// </summary>
        private IContainer components = null;

        public Global()
        {
            InitializeComponent();
        }

        private void InitializeComponent()
        {
            //throw new Exception("The method or operation is not implemented.");
        }

        protected void Application_Start(Object sender, EventArgs e)
        {
            const int DEFAULT_GG_DELETE_TEMP = 4;

            try
            {
                string folderPath = Server.MapPath(ConfigurationManager.AppSettings["TempPath"].ToString());

                int GGDeleteTemp = DEFAULT_GG_DELETE_TEMP;

                if (ConfigurationManager.AppSettings["GG_DELETE_TEMP"] != null)
                {
                    bool isNumber = int.TryParse(ConfigurationManager.AppSettings["GG_DELETE_TEMP"].ToString(), out GGDeleteTemp);

                    if (!isNumber)
                        GGDeleteTemp = DEFAULT_GG_DELETE_TEMP;
                }

                if (Directory.Exists(folderPath))
                {
                    foreach (string dir in Directory.GetDirectories(folderPath))
                    {
                        var dataCreazione = Directory.GetCreationTime(dir);
                        if(dataCreazione.AddDays(GGDeleteTemp) < DateTime.Now)
                            Directory.Delete(dir, true);
                    }

                    foreach (string files in Directory.GetFiles(folderPath))
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
            log4net.Config.XmlConfigurator.Configure();
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


    }
}