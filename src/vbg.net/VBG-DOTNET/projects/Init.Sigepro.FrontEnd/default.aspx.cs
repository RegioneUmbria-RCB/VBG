using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Ninject;
using System;
using System.Diagnostics;

namespace Init.Sigepro.FrontEnd
{
    public partial class _default : BasePage
    {
        [Inject]
        protected IRedirectService _redirectService { get; set; }

        [DebuggerStepThrough]
        protected void Page_Load(object sender, EventArgs e)
        {
            this._redirectService.RedirectToHomeContenuti();
        }
    }
}
