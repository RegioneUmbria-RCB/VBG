using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager;
using Ninject;
using Ninject.Web;
using System;
using System.IO;
using System.Web;
using System.Web.Script.Serialization;

namespace Sigepro.net.Istanze.DatiDinamici.Helper.FileUpload
{
    /// <summary>
    /// Summary description for UploadHandler
    /// </summary>
    public class UploadHandler : HttpHandlerBase
    {
        private HttpContext context;

        [Inject]
        public IAuthenticationManager _authenticationManager { get; set; }

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
                if (context.Request.Files.Count > 1)
                    throw new Exception("Errore interno (è stato caricato più di un file)");

                if (context.Request.Files.Count == 0 || context.Request.Files[0].ContentLength == 0)
                    throw new Exception("Nessun file caricato");

                var file = context.Request.Files[0];

                var authInfo = this._authenticationManager.CheckToken(this.Token);

                if (authInfo == null)
                    throw new Exception("Token non valido o non impostato");

                var oggettiMgr = new OggettiMgr(authInfo.CreateDatabase());

                var mimeType = file.ContentType;
                var fileName = Path.GetFileName(file.FileName);
                var buffer = new byte[file.ContentLength];
                using (var ms = new MemoryStream())
                {
                    file.InputStream.CopyTo(ms);

                    ms.Seek(0, SeekOrigin.Begin);
                    buffer = ms.ToArray();
                }

                var oggettoInserito = oggettiMgr.Insert(authInfo.IdComune, mimeType, fileName, buffer);

                var obj = new
                {
                    codiceOggetto = Convert.ToInt32(oggettoInserito.CODICEOGGETTO),
                    fileName = oggettoInserito.NOMEFILE,
                    length = file.ContentLength,
                    mime = file.ContentType
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