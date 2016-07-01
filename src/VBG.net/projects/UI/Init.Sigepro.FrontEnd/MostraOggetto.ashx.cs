using System;
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

		HttpContext _context;
		HttpResponse _response;
		HttpRequest _request;

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
			string idComune = _request.QueryString["IdComune"];
			string codiceOggetto = _request.QueryString["codiceOggetto"];
			string software = _request.QueryString["software"];
			bool fromStc = !String.IsNullOrEmpty(_request.QueryString["STC"]);

			try
			{
				if (string.IsNullOrEmpty(idComune))
					throw new Exception("IdComune non impostato");

				if (string.IsNullOrEmpty(codiceOggetto))
					throw new Exception("Codice oggetto non impostato");

				if(fromStc && String.IsNullOrEmpty( software ) )
					throw new Exception("Il parametro software è obbligatorio per gli oggetti di proveninza STC");

				var file = fromStc ? CaricaOggettoStc(idComune, software , codiceOggetto) : CaricaOggetto(idComune, codiceOggetto);

				if (file == null)
					return;

				_response.AddHeader("content-disposition", "attachment; filename=" + file.FileName);
				_response.ContentType = file.MimeType;
				_response.BinaryWrite(file.FileContent);
			}
			catch (Exception ex)
			{
				_response.ContentType = "text/plain";
				_response.Write(ex.Message);
			}
		}

		private BinaryFile CaricaOggetto(string alias, string codiceOggetto)
		{
			var nomeFile = _oggettiService.GetNomeFile( Convert.ToInt32(codiceOggetto));

			if (Path.GetExtension(nomeFile).ToUpper() == ".P7M")
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
