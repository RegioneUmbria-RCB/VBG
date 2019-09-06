using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Ninject;

namespace Init.Sigepro.FrontEnd
{
	public partial class _default : Ninject.Web.PageBase
    {
        [Inject]
        protected RedirectService _redirectService { get; set; }

        [DebuggerStepThrough]
		protected void Page_Load(object sender, EventArgs e)
		{
			this._redirectService.RedirectToHomeContenuti();
		}
	}
}
