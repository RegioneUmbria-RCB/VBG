using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Logic.OggettiLogic;
using Ninject;
using Ninject.Web;
using System;
using System.Web;
using System.Web.Script.Serialization;

namespace Sigepro.net.Istanze.DatiDinamici.Helper.FileUpload
{
    /// <summary>
    /// Summary description for ReadHandler
    /// </summary>
    public class ReadHandler : HttpHandlerBase
    {
        private HttpContext context;

        [Inject]
        public IAuthenticationManager _authenticationManager { get; set; }

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

                var datiOggetto = new OggettiServiceProxy(authInfo.CreateDatabase()).GetById(this.CodiceOggetto);

                if (datiOggetto == null)
                    throw new Exception("L'oggetto identificato dal codiceoggetto " + this.CodiceOggetto + " non è stato trovato");

                var obj = new
                {
                    codiceOggetto = this.CodiceOggetto,
                    nomeFile = datiOggetto.NOMEFILE,
                    size = datiOggetto.OGGETTO.Length,
                    mime = ""
                };

                this.SerializeResponse(obj);
            }
            catch (Exception ex)
            {
                this.SerializeResponse(new { Errori = ex.Message });
            }
        }

        public override bool IsReusable => false;

        private void SerializeResponse(object result)
        {
            JavaScriptSerializer jss = new JavaScriptSerializer();
            var responseText = jss.Serialize(result);

            this.context.Response.ContentType = "text/plain";
            this.context.Response.Write(responseText);
        }

        private void ImpedisciCaching()
        {
            HttpContext.Current.Response.Cache.SetCacheability(HttpCacheability.NoCache);
            HttpContext.Current.Response.Cache.SetNoServerCaching();
            HttpContext.Current.Response.Cache.SetNoStore();
            HttpContext.Current.Response.Cache.SetExpires(DateTime.Now.AddDays(-1));
        }
    }
}