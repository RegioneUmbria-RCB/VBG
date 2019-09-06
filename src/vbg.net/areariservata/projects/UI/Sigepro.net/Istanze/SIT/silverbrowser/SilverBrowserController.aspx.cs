using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Sigepro.net.Istanze.Sit.silverbrowser
{
    public partial class SilverBrowserController : System.Web.UI.Page
    {


        public string Modo
        {
            get { return Request.QueryString["modo"]; }
        }

        public bool UseRemoteServer
        {
            get
            {
                return !String.IsNullOrEmpty(Request.QueryString["Remote"]);
            }
        }

        public string UrlSilverBrowser
        {
            get
            {
                var configUrl = ConfigurationManager.AppSettings["Silverbrowser.UrlJavascriptBackend"];

                if (String.IsNullOrEmpty(configUrl))
                {

                    if (UseRemoteServer)
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
        }

        public string Foglio
        {
            get { return Request.QueryString["f"].PadLeft(4, '0'); }
        }

        public string Particella
        {
            get { return Request.QueryString["p"].PadLeft(5, '0'); }
        }

        public string CodiceVia
        {
            get { return Request.QueryString["v"].Replace("F844", "").TrimStart('0'); }
        }

        public string Civico
        {
            get { return Request.QueryString["c"]; }
        }

        public string Esponente
        {
            get { return Request.QueryString["e"]; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
        }
    }
}