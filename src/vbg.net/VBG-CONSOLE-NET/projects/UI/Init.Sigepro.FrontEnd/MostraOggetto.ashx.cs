using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Ninject;
using System;
using System.Web;
using System.Web.Services;
using System.Web.SessionState;

namespace Init.Sigepro.FrontEnd
{
    /// <summary>
    /// Summary description for $codebehindclassname$
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    public class MostraOggetto : Ninject.Web.HttpHandlerBase, IReadOnlySessionState
    {
        [Inject]
        public IOggettiService _oggettiService { get; set; }

        [Inject]
        public IIstanzePresentateRepository _istanzePresentateRepository { get; set; }

        private HttpContext _context;
        private HttpResponse _response;
        private HttpRequest _request;

        protected override void DoProcessRequest(HttpContext context)
        {
            this._context = context;
            this._response = context.Response;
            this._request = context.Request;
            /*
			_response.Cache.SetCacheability(HttpCacheability.NoCache);
			_response.Cache.SetNoServerCaching();
			_response.Cache.SetNoStore();
			_response.Cache.SetExpires(DateTime.Now.AddDays(-1));
			*/
            var idComune = this._request.QueryString["IdComune"];
            var codiceOggetto = this._request.QueryString["codiceOggetto"];
            var software = this._request.QueryString["software"];
            var fromStc = !String.IsNullOrEmpty(this._request.QueryString["STC"]);

            try
            {
                if (string.IsNullOrEmpty(idComune))
                    throw new Exception("IdComune non impostato");

                if (string.IsNullOrEmpty(codiceOggetto))
                    throw new Exception("Codice oggetto non impostato");

                if (fromStc && String.IsNullOrEmpty(software))
                    throw new Exception("Il parametro software è obbligatorio per gli oggetti di proveninza STC");

                var file = fromStc ? this.CaricaOggettoStc(idComune, software, codiceOggetto) : this.CaricaOggetto(idComune, codiceOggetto);

                if (file == null)
                    return;

                this._response.AddHeader("content-disposition", "attachment; filename=\"" + file.FileName.Replace("\"", "_") + "\"");
                this._response.ContentType = file.MimeType;
                this._response.BinaryWrite(file.FileContent);
            }
            catch (Exception ex)
            {
                this._response.ContentType = "text/plain";
                this._response.Write(ex.Message);
            }
        }

        private BinaryFile CaricaOggetto(string alias, string codiceOggetto)
        {
            var nomeFile = this._oggettiService.GetNomeFile(Convert.ToInt32(codiceOggetto));

            //if (Path.GetExtension(nomeFile).ToUpper() == ".P7M")
            //{
            //	string fmt = "~/MostraOggettoP7M.aspx?IdComune={0}&CodiceOggetto={1}";
            //	string url = String.Format(fmt, alias, codiceOggetto);

            //	_response.Redirect(url);
            //	return null;
            //}

            return this._oggettiService.GetById(Convert.ToInt32(codiceOggetto));
        }

        private BinaryFile CaricaOggettoStc(string alias, string software, string codiceOggetto)
        {
            return this._istanzePresentateRepository.GetDocumentoPratica(alias, software, codiceOggetto);
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
