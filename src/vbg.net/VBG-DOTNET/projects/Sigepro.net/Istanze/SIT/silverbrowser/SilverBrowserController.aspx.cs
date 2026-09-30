using System;
using System.Configuration;

namespace Sigepro.net.Istanze.Sit.silverbrowser
{
    public partial class SilverBrowserController : System.Web.UI.Page
    {


        public string Modo
        {
            get { return this.Request.QueryString["modo"]; }
        }

        public bool UseRemoteServer
        {
            get
            {
                return !String.IsNullOrEmpty(this.Request.QueryString["Remote"]);
            }
        }

        public string ConfigKey
        {
            get
            {
                return this.Request.QueryString["ConfigKey"] ?? "";
            }
        }

        public string UrlSilverBrowser
        {
            get
            {
                if (String.IsNullOrEmpty(this.ConfigKey))
                {


                    var configUrl = ConfigurationManager.AppSettings["Silverbrowser.UrlJavascriptBackend"];

                    if (String.IsNullOrEmpty(configUrl))
                    {

                        if (this.UseRemoteServer)
                        {
                            configUrl = "http://silverbrowser.comune.narni.tr.it/SilverBrowser/bus/API/silverbrowser-bus.js?token=GRUPPOINIT_VBG";
                        }
                        else
                        {
                            configUrl = "http://10.101.126.30:7777/SilverBrowser/bus/API/silverbrowser-bus.js?token=GRUPPOINIT_VBG";
                        }
                    }


                    return configUrl;
                }

                return ConfigurationManager.AppSettings[this.ConfigKey];
            }
        }

        public string Foglio
        {
            get { return this.Request.QueryString["f"].PadLeft(4, '0'); }
        }

        public string Particella
        {
            get { return this.Request.QueryString["p"].PadLeft(5, '0'); }
        }

        public string CodiceVia
        {
            get { return this.Request.QueryString["v"].Replace("F844", "").TrimStart('0'); }
        }

        public string Civico
        {
            get { return this.Request.QueryString["c"]; }
        }

        public string Esponente
        {
            get { return this.Request.QueryString["e"]; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
        }
    }
}