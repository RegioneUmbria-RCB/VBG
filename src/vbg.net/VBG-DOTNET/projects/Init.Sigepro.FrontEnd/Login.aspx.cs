using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Ninject;
using System;
using System.Web;

namespace Init.Sigepro.FrontEnd
{


    public partial class Login : BasePage
    {
        [Inject]
        public IRedirectService _redirectService { get; set; }


        protected string ReturnTo
        {
            get
            {
                string returnTo = this.Request.QueryString["ReturnTo"];
                return returnTo;
            }
        }


        protected void Page_Load(object sender, EventArgs e)
        {
            if (!String.IsNullOrEmpty(this.ReturnTo))
                this.Context.Response.Cookies.Add(new HttpCookie("ReturnTo", this.ReturnTo));

            this._redirectService.RedirectToHomeAreaRiservata(this.Server.UrlEncode(this.ReturnTo));
        }
    }
}