using System;
using System.Configuration;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.SessionState;
using System.Web.Services;
using System.IO;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Sigepro.FrontEnd.AppLogic.Common;

namespace Init.Sigepro.FrontEnd
{
	/// <summary>
	/// Summary description for $codebehindclassname$
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	public class MostraOggetto : Ninject.Web.HttpHandlerBase, IReadOnlySessionState
	{
        private static class Constants
        {
            public const string ResirectSuP7MConfigKey = "MostraOggetto.RedirectSuP7M";
            public const string Hmac = "c";
        }

		[Inject]
		public IOggettiService _oggettiService { get; set; }

		[Inject]
		public IIstanzePresentateRepository _istanzePresentateRepository { get; set; }

		HttpContext _context;
		HttpResponse _response;
		HttpRequest _request;

        private bool RedirectSuP7M => !String.IsNullOrEmpty(ConfigurationManager.AppSettings[Constants.ResirectSuP7MConfigKey]) && ConfigurationManager.AppSettings[Constants.ResirectSuP7MConfigKey].ToUpper() == "TRUE";

        private bool HmacCheck
        {
            get
            {
                var codiceOggetto = _request.QueryString["codiceoggetto"];
                var check = _request.QueryString[Constants.Hmac];

                if (String.IsNullOrEmpty(check))
                {
                    return true;
                }

                return new HmacCreator().Encode("codiceoggetto=" + codiceOggetto) == check;
            }
        }

        protected override void DoProcessRequest(HttpContext context)
		{
			_context = context;
			_response = context.Response;
			_request = context.Request;
			/*
			_response.Cache.SetCacheability(HttpCacheability.NoCache);
			_response.Cache.SetNoServerCaching();
			_response.Cache.SetNoStore();
			_response.Cache.SetExpires(DateTime.Now.AddDays(-1));
			*/
			var idComune = new QsAliasComune(_request.QueryString);
            var codiceOggetto = _request.QueryString["codiceoggetto"];
            var software = new QsSoftware(_request.QueryString);
			var fromStc = !String.IsNullOrEmpty(_request.QueryString["STC"]);

			try
			{
				if (!idComune.HasValue)
					throw new Exception("IdComune non impostato");

				if (String.IsNullOrEmpty(codiceOggetto))
					throw new Exception("Codice oggetto non impostato");

				if(fromStc && !software.HasValue)
					throw new Exception("Il parametro software è obbligatorio per gli oggetti di proveninza STC");

				var file = fromStc ? CaricaOggettoStc(idComune.Value, software.Value , codiceOggetto) : CaricaOggetto(idComune.Value, Convert.ToInt32(codiceOggetto));

                if (!HmacCheck)
                {
                    return;
                }

				if (file == null)
					return;

				_response.AddHeader("content-disposition", "attachment; filename=\"" + file.FileName.Replace("\"", "_") + "\"");
				_response.ContentType = file.MimeType;
				_response.BinaryWrite(file.FileContent);
			}
			catch (Exception ex)
			{
				_response.ContentType = "text/plain";
				_response.Write(ex.Message);
			}
		}

		private BinaryFile CaricaOggetto(string alias, int codiceOggetto)
		{
			var nomeFile = _oggettiService.GetNomeFile( Convert.ToInt32(codiceOggetto));

			if (this.RedirectSuP7M && Path.GetExtension(nomeFile).ToUpper() == ".P7M")
			{
				string fmt = "~/MostraOggettoP7M.aspx?IdComune={0}&CodiceOggetto={1}";
				string url = String.Format(fmt, alias, codiceOggetto);

				_response.Redirect(url);
				return null;
			}

			return _oggettiService.GetById(Convert.ToInt32(codiceOggetto));
		}

		private BinaryFile CaricaOggettoStc(string alias, string software, string codiceOggetto)
		{
			return _istanzePresentateRepository.GetDocumentoPratica(alias, software, codiceOggetto);
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
