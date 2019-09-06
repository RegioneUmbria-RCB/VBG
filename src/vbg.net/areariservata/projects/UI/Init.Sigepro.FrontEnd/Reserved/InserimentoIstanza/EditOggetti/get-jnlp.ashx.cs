using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.EditOggetti
{
    /// <summary>
    /// Summary description for get_jnlp
    /// </summary>
    public class get_jnlp : Ninject.Web.HttpHandlerBase, IHttpHandler
    {
        [Inject]
        public IAuthenticationDataResolver _authDataResolver { get; set; }
        [Inject]
        public IOggettiService _oggetiService { get; set; } 

        public static string ResolveUrl(string originalUrl)
        {
            if (originalUrl == null)
                return null;

            // *** Absolute path - just return
            if (originalUrl.IndexOf("://") != -1)
                return originalUrl;

            // *** Fix up image path for ~ root app dir directory
            if (originalUrl.StartsWith("~"))
            {
                string newUrl = "";
                if (HttpContext.Current != null)
                    newUrl = HttpContext.Current.Request.ApplicationPath +
                          originalUrl.Substring(1).Replace("//", "/");
                else
                    // *** Not context: assume current directory is the base directory
                    throw new ArgumentException("Invalid URL: Relative URL not allowed.");

                // *** Just to be sure fix up any double slashes
                return newUrl;
            }

            return originalUrl;
        }

        public static string ResolveServerUrl(string serverUrl, bool forceHttps = false)
        {
            // *** Is it already an absolute Url?
            if (serverUrl.IndexOf("://") > -1)
                return serverUrl;

            // *** Start by fixing up the Url an Application relative Url
            string newUrl = ResolveUrl(serverUrl);

            Uri originalUri = HttpContext.Current.Request.Url;
            newUrl = (forceHttps ? "https" : originalUri.Scheme) +
                     "://" + originalUri.Authority + newUrl;

            return newUrl;
        }


        protected override void DoProcessRequest(HttpContext context)
        {
            context.Response.ContentType = "application/x-java-jnlp-file";
            context.Response.AddHeader("Content-Disposition", "attachment; filename=modifica-documento.jnlp");

            var aliasComune = new QsAliasComune(context.Request.QueryString);
            var software = new QsSoftware(context.Request.QueryString);
            var idDomanda = new QsIdDomandaOnline(context.Request.QueryString);
            var idAllegato = Convert.ToInt32(context.Request.QueryString["IdAllegato"]);
            var codiceOggettoModello =context.Request.QueryString["IdModello"];
            var codiceOggetto = context.Request.QueryString["CodiceOggetto"];
            var tipoAllegato = context.Request.QueryString["TipoAllegato"];
            var token = this._authDataResolver.DatiAutenticazione.Token;
            var codeBase = ResolveServerUrl("~/reserved/inserimentoistanza/editoggetti/");

            var file = this._oggetiService.GetById(Convert.ToInt32(codiceOggettoModello));

            var urlDownload = ResolveServerUrl(UrlBuilder.Url("~/reserved/inserimentoistanza/downloadpdfcompilabile.ashx", qs => {
                qs.Add(aliasComune);
                qs.Add(software);
                qs.Add(idDomanda);
                qs.Add("codiceoggetto", Convert.ToInt32(String.IsNullOrEmpty(codiceOggetto) ? codiceOggettoModello : codiceOggetto));
                //qs.Add("token", token);
            })).Replace("&", "&amp;");

            var urlUpload = ResolveServerUrl(UrlBuilder.Url("~/reserved/inserimentoistanza/editoggetti/upload.ashx", qs => {
                qs.Add(aliasComune);
                qs.Add(software);
                qs.Add(idDomanda);
                qs.Add("IdAllegato", idAllegato);
                qs.Add("codiceOggetto", codiceOggetto);
                qs.Add("TipoAllegato", tipoAllegato);
                qs.Add("nome", file.FileName);
            })).Replace("&", "&amp;");

            var str = $@"<?xml version=""1.0"" encoding=""utf-8""?>
<jnlp spec=""1.0+"" codebase=""{codeBase}"" href="""">
    <information>
        <title>Edit Docs Application</title>
        <vendor>In.I.T.</vendor>
    </information>
    <security>
     <all-permissions/>
      </security>
    <resources os=""Windows"">
        <j2se version=""1.6+"" href=""http://java.sun.com/products/autodl/j2se"" />
        <jar href=""applets/init-editdocs-jws.jar"" main=""true"" />
    </resources>
    <application-desc main-class=""it.gruppoinit.pal.gp.backoffice.jws.EditDocsApplication"">
       <!--urlOggetti (obbligatorio)-->
       <argument>{urlDownload}</argument>
       <!--urlUploadOggetti (obbligatorio)-->
       <argument>{urlUpload}</argument>
       <!-- fileId (obbligatorio)-->
       <argument>{codiceOggettoModello}</argument>
       <!-- Token (obbligatorio)-->
       <argument>{token}</argument>
       <!--labelBtnInviaModifiche-->
       <argument>null</argument>
       <!--labelDownloadCompletato -->
       <argument>null</argument>
       <!--debug -->
       <argument>true</argument>
    </application-desc>
    <update check=""background""/>
</jnlp>";

            context.Response.Write(str);

        }

        public override bool IsReusable
        {
            get
            {
                return false;
            }
        }
        
    }
}