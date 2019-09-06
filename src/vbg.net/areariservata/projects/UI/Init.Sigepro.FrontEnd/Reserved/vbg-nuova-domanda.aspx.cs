using Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneAidaSmart;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved
{
    public partial class vbg_nuova_domanda : ReservedBasePage
    {
        [Inject]
        protected IAidaSmartService _aidaService { get; set; }

        protected string RedirUrl { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            var returnToUrl = "//" + HttpContext.Current.Request.Url.Authority + HttpContext.Current.Request.ApplicationPath + UrlBuilder.Url("/reserved/benvenuto.aspx", qs => {
                qs.Add(new QsAliasComune(this.IdComune));
                qs.Add(new QsSoftware(this.Software));
            });


            this.RedirUrl = this._aidaService.GetUrlNuovaDomanda(this.UserAuthenticationResult.DatiUtente) + "&url-scrivania-virtuale=" + Server.UrlEncode(returnToUrl);


        }
    }
}