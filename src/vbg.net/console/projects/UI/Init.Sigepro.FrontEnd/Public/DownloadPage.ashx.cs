using System;
using System.Net;
using System.Text;
using System.Web;
using System.Web.Services;
using Init.Sigepro.FrontEnd.AppLogic.Services.ConversioneDomanda;
using Ninject;


namespace Init.Sigepro.FrontEnd.Public
{
	/// <summary>
	/// Summary description for $codebehindclassname$
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	public class DownloadPage : Ninject.Web.HttpHandlerBase
	{
		[Inject]
		public FileConverterService _fileConverterService { get; set; }


		protected override void DoProcessRequest(HttpContext context)
		{
			var idComune = context.Request.QueryString["idComune"];
			var url = context.Request.QueryString["url"];

			var contenuto = LeggiPagina(url);

			var res = _fileConverterService.Converti( "FoDocument.html", contenuto, "PDF");

			context.Response.ContentType = res.MimeType;
			context.Response.AddHeader("content-disposition", "attachment; filename: " + res.FileName);
			context.Response.BinaryWrite(res.FileContent);

		}

		private byte[] LeggiPagina(string url)
		{
			// used on each read operation
			byte[] buf = new byte[8192];

			// prepare the web page we will be asking for
			var request = (HttpWebRequest)WebRequest.Create(url);

			// execute the request
			var response = (HttpWebResponse)request.GetResponse();

			// we will read data via the response stream
			var resStream = response.GetResponseStream();
			
			var rVal = new byte[response.ContentLength];

			var count = 0;
			var startOffset = 0;

			do
			{
				// fill the buffer with data
				count = resStream.Read(buf, 0, buf.Length);

				if (count > 0)
				{
					Buffer.BlockCopy(buf, 0, rVal, startOffset , count);

					startOffset += count;
				}
			}
			while (count > 0); // any more data to read?

			return rVal;
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
