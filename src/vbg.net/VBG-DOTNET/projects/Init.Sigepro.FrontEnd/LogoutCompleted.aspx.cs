using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd
{
    public partial class LogoutCompleted : BasePage
    {
        [Inject]
        public IConfigurazioneVbgRepository _configurazioneVbgRepository { get; set; }
        [Inject]
        public IConfigurazione<ParametriAspetto> _configurazioneAspetto { get; set; }


        protected void Page_Load(object sender, EventArgs e)
        {
        }

        protected void cmdChiudi_Click(object sender, EventArgs e)
        {
            var url = UrlBuilder.Url("~/login.aspx", pb =>
            {
                pb.Add(new QsAliasComune(Request.QueryString));
                pb.Add(new QsSoftware(Request.QueryString));
            });

            Response.Redirect(url);
        }
    }
}
