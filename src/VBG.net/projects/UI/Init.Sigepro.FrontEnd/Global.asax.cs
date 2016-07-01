using System;
using System.Net;
using System.Web;
using Init.Sigepro.FrontEnd.AppLogic.IoC;

namespace Init.Sigepro.FrontEnd
{
	/// <summary>
	/// Descrizione di riepilogo per Global.
	/// </summary>
	public class Global :  HttpApplication
	{
		//static bool VerificaAccessibilitaEffettuata = false;

		//private void VerificaAccessibilitaUrl()
		//{
		//    VerificaAccessibilitaEffettuata = true;

		//    var req = HttpContext.Current.Request;
		//    var url = req.Url.Scheme + "://" + req.Url.Host + ":" + req.Url.Port;

		//    if (!string.IsNullOrEmpty(req.ApplicationPath))
		//        url += req.ApplicationPath;

		//    if (!url.EndsWith("/"))
		//        url += "/";

		//    var urlCompleto = url + "VerificaAccessibilitaUrl.aspx";

		//    try
		//    {
		//        var webClient = new WebClient();

		//        webClient.DownloadData(urlCompleto);
		//    }
		//    catch (Exception)
		//    {
		//        throw new InvalidOperationException("Impossibile raggiungere l'indirizzo " + url + " lato server. Questo problema non permetterebbe la creazione degli allegati dei dati dinamici. Per risolvere il problema aggiungere l'indirizzo " + req.Url.Host + " al file hosts del server oppure configurare un dns che risolva internamente il nome.");
		//    }
		//}

		//protected void Application_Start(object sender, EventArgs e)
		//{
			
		//}

		//protected void Session_Start(object sender, EventArgs e)
		//{

		//}

		//protected void Application_BeginRequest(object sender, EventArgs e)
		//{

		//}

		//protected void Application_AuthenticateRequest(object sender, EventArgs e)
		//{

		//}

		//protected void Application_Error(object sender, EventArgs e)
		//{

		//}

		//protected void Session_End(object sender, EventArgs e)
		//{

		//}

		//protected void Application_End(object sender, EventArgs e)
		//{

		//}

		//protected override Ninject.IKernel CreateKernel()
		//{
		//    FoKernelContainer.Kernel = new StandardKernel(ModuliWebAreaRiservata.Get());

		//    return FoKernelContainer.Kernel;
		//}
	}
}