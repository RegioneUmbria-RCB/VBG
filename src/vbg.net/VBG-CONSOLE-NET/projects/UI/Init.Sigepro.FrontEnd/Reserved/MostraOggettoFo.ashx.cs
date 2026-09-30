using Init.Sigepro.FrontEnd.AppLogic.AllegatiDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.GestioneConversioneFiles;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
using Ninject;
using System;
using System.Web;
using System.Web.Services;
using System.Web.SessionState;

namespace Init.Sigepro.FrontEnd.Reserved
{
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    public class MostraOggettoFo : Ninject.Web.HttpHandlerBase, IRequiresSessionState
    {
        [Inject]
        public FileConverterService _fileConverterService { get; set; }
        [Inject]
        public IVerificaFirmaDigitaleService _firmaDigitaleService { get; set; }
        [Inject]
        public IDatiDomandaFoRepository _datiDomandaFoRepository { get; set; }
        [Inject]
        public IAllegatiDomandaFoRepository _allegatiDomandaFoRepository { get; set; }
        [Inject]
        protected PathUtils _pathUtils { get; set; }

        protected override void DoProcessRequest(HttpContext context)
        {
            var uar = (UserAuthenticationResult)context.Items["UserAuthenticationResult"];

            var idComune = context.Request.QueryString[PathUtils.UrlParameters.IdComune].ToString();
            var software = context.Request.QueryString[PathUtils.UrlParameters.Software].ToString();
            var idPresentazione = Convert.ToInt32(context.Request.QueryString[PathUtils.UrlParameters.IdPresentazione]);
            var codiceOggetto = Convert.ToInt32(context.Request.QueryString[PathUtils.UrlParameters.CodiceOggetto]);
            var strShowInline = context.Request.QueryString["inline"];
            var inline = !String.IsNullOrEmpty(strShowInline) && strShowInline == "1";

            var convert = context.Request.QueryString[PathUtils.UrlParameters.Convert];

            var oggetto = this._allegatiDomandaFoRepository.LeggiAllegato(idPresentazione, codiceOggetto);

            if (oggetto == null)
            {
                context.Response.ContentType = "text/plain";
                context.Response.Write("Errore: Oggetto " + codiceOggetto + "non trovato");
                context.Response.End();
                return;
            }

            var contenutoFile = oggetto.FileContent;
            var mimeType = oggetto.MimeType;
            var nomeFile = oggetto.FileName;


            try
            {
                if (!inline)
                {
                    context.Response.AddHeader("content-disposition", "attachment;filename=\"" + nomeFile + "\"");
                }

                context.Response.ContentType = mimeType;

                context.Response.BinaryWrite(contenutoFile);
            }
            catch (Exception ex)
            {
                context.Response.ContentType = "text/plain";
                context.Response.Write("Errore: " + ex.Message);
            }
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
