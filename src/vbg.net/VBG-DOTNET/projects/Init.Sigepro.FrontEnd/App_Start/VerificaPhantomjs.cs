[assembly: WebActivatorEx.PostApplicationStartMethod(typeof(Init.Sigepro.FrontEnd.App_Start.VerificaPhantomjs), "Test")]


namespace Init.Sigepro.FrontEnd.App_Start
{

    using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF;
    using Init.Sigepro.FrontEnd.Infrastructure.IOC;
    using System;

    public class VerificaPhantomjs
    {
        public static void Test()
        {
            var renderer = FoKernelContainer.GetService<IHtmlToPdfFileConverter>();

            try
            {
                renderer.Converti("prova.pdf", "<html><body>It works!</body></html>", RenderingFlags.Default);
            }
            catch (Exception ex)
            {

                throw new InvalidOperationException(
                    $"<h1>Il servizio di generazione files non è disponibile</h1>. Scaricare il file all'indirizzo " +
                    $"<a href='https://devel3.init.gruppoinit.it/download/ghostscript/ghostscript-dll-32-64.zip'>https://devel3.init.gruppoinit.it/download/ghostscript/ghostscript-dll-32-64.zip</a>" +
                    $"ed estrarre il contenuto nella cartella bin dell'area riservata. Errore: {ex}");
            }
        }
    }
}