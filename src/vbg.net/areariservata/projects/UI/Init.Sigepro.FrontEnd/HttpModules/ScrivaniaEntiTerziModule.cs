using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEntiTerzi;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Ninject;
using System;
using System.Web;

namespace Init.Sigepro.FrontEnd.HttpModules
{
    public class ScrivaniaEntiTerziModule : IHttpModule
    {
        [Inject]
        public IScrivaniaEntiTerziService _service { get; set; }
        [Inject]
        public ISoftwareResolver _resolveSoftware { get; set; }
        [Inject]
        public IAuthenticationDataResolver _authDataResolver { get; set; }

        HttpApplication _context;

        public void Init(HttpApplication context)
        {
            _context = context;

            FoKernelContainer.Inject(this);

            context.PostAuthorizeRequest += Context_PostAuthorizeRequest; ;
        }

        private void Context_PostAuthorizeRequest(object sender, EventArgs e)
        {
            var localPath = new ApplicationPath(_context.Request.Url.LocalPath);


            if (!localPath.IsReserved)
            {
                return;
            }

            if (_service.ModuloAttivo(_resolveSoftware.Software) && !_service.UtentePuoAccedere(new ETCodiceAnagrafe(_authDataResolver.DatiAutenticazione.DatiUtente.Codiceanagrafe.Value)))
            {
                _context.Response.StatusCode = 404;
                _context.Response.End();

            }
        }


        public void Dispose()
        {

        }
    }
}