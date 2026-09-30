using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Logic.DatiDinamici.Export;
using Ninject;
using Ninject.Web;
using System.Web;

namespace Sigepro.net
{
    /// <summary>
    /// Summary description for ExportSchedeDinamiche
    /// </summary>
    public class ExportSchedeDinamiche : HttpHandlerBase
    {
        [Inject]
        public IAuthenticationManager AuthenticationManager { get; set; }

        protected override void DoProcessRequest(HttpContext context)
        {
            var alias = context.Request.QueryString["alias"];
            var idScheda = context.Request.QueryString["idScheda"];

            if (string.IsNullOrEmpty(alias) || string.IsNullOrEmpty(idScheda))
            {
                context.Response.StatusCode = 400;
                context.Response.StatusDescription = "Bad Request";
                context.Response.Write("Parametro mancante: " + (string.IsNullOrEmpty(alias) ? "alias" : "idScheda"));
                return;
            }

            var authInfo = this.AuthenticationManager.GetTokenApplicativo(alias);

            using (var db = authInfo.CreateDatabase())
            {
                var export = new ExportSchedeDinamicheService(db, authInfo.IdComune);
                var result = export.Export(int.Parse(idScheda));
                context.Response.AddHeader("Content-Disposition", $"attachment; filename=\"{alias}_{idScheda}_{result.Modello.CodiceScheda}.json\"");
                context.Response.ContentType = "application/json";
                context.Response.Write(result.ToJson());
            }

        }

        public override bool IsReusable => false;
    }
}