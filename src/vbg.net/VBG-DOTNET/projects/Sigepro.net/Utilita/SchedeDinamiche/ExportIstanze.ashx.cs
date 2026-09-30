using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Authentication;
using Ninject;
using Ninject.Web;
using PersonalLib2.Sql;
using System;
using System.Web;

namespace Sigepro.net.Utilita.SchedeDinamiche
{
    /// <summary>
    /// Summary description for ExportIstanze
    /// </summary>
    public class ExportIstanze : HttpHandlerBase
    {

        [Inject]
        public IAuthenticationManager AuthenticationManager { get; set; }

        protected override void DoProcessRequest(HttpContext context)
        {
            var alias = context.Request.QueryString["alias"];
            var codiceIstanza = context.Request.QueryString["codiceIstanza"];

            if (string.IsNullOrEmpty(alias) || string.IsNullOrEmpty(codiceIstanza))
            {
                context.Response.StatusCode = 400;
                context.Response.StatusDescription = "Bad Request";
                context.Response.Write("Parametro mancante: " + (string.IsNullOrEmpty(alias) ? "alias" : "codiceIstanza"));
                return;
            }

            var authInfo = this.AuthenticationManager.GetTokenApplicativo(alias);

            using (var db = authInfo.CreateDatabase())
            {
                context.Response.AddHeader("Content-Disposition", $"attachment; filename=\"{alias}_{codiceIstanza}_istanza.xml\"");
                context.Response.ContentType = "application/xml";


                var istanza = new IstanzeMgr(db).GetById(authInfo.IdComune, Convert.ToInt32(codiceIstanza), useForeignEnum.Recoursive);

                var serializer = new System.Xml.Serialization.XmlSerializer(typeof(Init.SIGePro.Data.Istanze));
                serializer.Serialize(context.Response.OutputStream, istanza);
            }
        }

        override public bool IsReusable => false;
    }
}