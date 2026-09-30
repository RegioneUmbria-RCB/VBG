using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Logic.OggettiLogic;
using Ninject;
using System;
using System.Web;

namespace Sigepro.net.DatiDinamici.Mercati.Helper.FileUploadHandlers
{
    /// <summary>
    /// Summary description for DownloadHandler
    /// </summary>
    public class DownloadHandler : Ninject.Web.HttpHandlerBase
    {
        [Inject]
        public IAuthenticationManager _authenticationManager { get; set; }

        private HttpContext context;

        private int CodiceOggetto
        {
            get { return Convert.ToInt32(this.context.Request.QueryString["codiceOggetto"]); }
        }

        private string Token
        {
            get { return this.context.Request.QueryString["Token"]; }
        }

        protected override void DoProcessRequest(HttpContext context)
        {
            this.ImpedisciCaching();

            this.context = context;

            try
            {

                var authInfo = this._authenticationManager.CheckToken(this.Token);

                if (authInfo == null)
                    throw new Exception("Token non valido o non impostato");

                var datiOggetto = new OggettiServiceProxy(authInfo.CreateDatabase()).GetByIdNativo(this.CodiceOggetto);

                if (datiOggetto == null)
                    throw new Exception("L'oggetto identificato dal codiceoggetto " + this.CodiceOggetto + " non è stato trovato");

                context.Response.Clear();
                context.Response.ContentType = datiOggetto.mimeType;
                context.Response.AddHeader("Content-Disposition", "attachment; filename=\"" + datiOggetto.fileName + "\"");
                context.Response.BinaryWrite(datiOggetto.binaryData);

            }
            catch (Exception ex)
            {
                context.Response.Clear();
                context.Response.ContentType = "text/plain";
                context.Response.Write("Errore durante la lettura del file specificato: " + ex.Message);
            }
        }

        public override bool IsReusable => false;

        private void ImpedisciCaching()
        {
            HttpContext.Current.Response.Cache.SetCacheability(HttpCacheability.NoCache);
            HttpContext.Current.Response.Cache.SetNoServerCaching();
            HttpContext.Current.Response.Cache.SetNoStore();
            HttpContext.Current.Response.Cache.SetExpires(DateTime.Now.AddDays(-1));
        }
    }
}