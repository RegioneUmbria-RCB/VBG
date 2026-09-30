//using System;
//using System.IO;

//namespace Init.Sigepro.FrontEnd.AppLogic.ConversionePDF
//{
//    public class PhantomjsBootstrapper
//    {
//        public static class Constants
//        {
//            public const string PhantomJs = "phantomjs.exe";
//        }

//        public void Bootstrap()
//        {

//            var phantomjsPath = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "phantomjs");

//            if (!File.Exists(Path.Combine(phantomjsPath, Constants.PhantomJs)))
//            {
//                throw new InvalidOperationException("Phantomjs non trovato nel path");
//            }

//            try
//            {
//                new PhantomjsRenderer(phantomjsPath).RenderHtml("<html><body>It works!</body></html>", RenderingFlags.Default);
//            }
//            catch (Exception ex)
//            {

//                throw new InvalidOperationException(
//                    $"<h1>Il servizio di generazione files non è disponibile</h1>. Scaricare il file all'indirizzo " +
//                    $"<a href='https://devel3.init.gruppoinit.it/download/ghostscript/ghostscript-dll-32-64.zip'>https://devel3.init.gruppoinit.it/download/ghostscript/ghostscript-dll-32-64.zip</a>" +
//                    $"ed estrarre il contenuto nella cartella bin dell'area riservata. Errore: {ex}");
//            }

//        }
//    }
//}
