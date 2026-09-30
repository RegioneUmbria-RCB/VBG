using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF.Ghostscript;
using log4net;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.ConversionePDF
{
    public class PhantomjsRenderer
    {
        string _phantomjsPath;
        ILog _log = LogManager.GetLogger(typeof(PhantomjsRenderer));

        public PhantomjsRenderer(string phantomjsPath)
        {
            _phantomjsPath = phantomjsPath;
        }

        public Byte[] RenderHtml(string html)
        {
            var guid = Guid.NewGuid().ToString();
            var htmlTempFile = Path.Combine(Path.GetTempPath(), guid + ".html");
            var pdfTempFile = Path.Combine(Path.GetTempPath(), guid + ".pdf");
            var pdfATempFile = Path.Combine(Path.GetTempPath(), guid + ".pdfa.pdf");


            File.WriteAllText(htmlTempFile, html);

            try
            {
                // var t = new Thread(new ParameterizedThreadStart(x =>
                // {
                var command = String.Format(Path.Combine(this._phantomjsPath, "phantomjs.exe"));
                var arg = String.Format(" rasterize.js \"file:///{0}\" \"{1}\" \"A4\"", htmlTempFile.Replace("\\", "/"), pdfTempFile);

                _log.DebugFormat("Generazione pdf da html: {0} {1}", command, arg);

                ProcessStartInfo pi;
                Process p;

                pi = new ProcessStartInfo(command, arg);
                pi.CreateNoWindow = true;
                pi.UseShellExecute = false;
                pi.WorkingDirectory = this._phantomjsPath;

                p = Process.Start(pi);

                p.WaitForExit();

                if (!File.Exists(pdfTempFile))
                {
                    throw new Exception(String.Format("File {0} non creato", pdfTempFile));
                }

                new PdfToPdfAConverter().ConvertiInPdfA(pdfTempFile, pdfATempFile);

                return File.ReadAllBytes(pdfATempFile);
            }
            finally
            {
                DeleteIfExists(htmlTempFile);
                DeleteIfExists(pdfTempFile);
                DeleteIfExists(pdfATempFile);
            }
        }

        private void DeleteIfExists(string tempFile)
        {
            try
            {
                if (File.Exists(tempFile))
                    File.Delete(tempFile);
            }
            catch(Exception ex)
            {
                this._log.ErrorFormat("Impossibile eliminare il file temporaneo {0}: {1}", tempFile, ex.ToString());
            }
        }

    }
}
