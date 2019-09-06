using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF.Ghostscript;
using log4net;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading;

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

        public Byte[] RenderHtml(string html, RenderingFlags renderingFlags)
        {
            if (renderingFlags == null)
            {
                renderingFlags = RenderingFlags.Default;
            }

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
                var arg = String.Format($" {renderingFlags.RasterizeScript} \"file:///{(htmlTempFile.Replace("\\", "/"))}\" \"{pdfTempFile}\" \"A4\"");

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

                if (!renderingFlags.ConvertToPdfa)
                {
                    return ReadAllBytes(pdfTempFile);
                }

                new PdfToPdfAConverter().ConvertiInPdfA(pdfTempFile, pdfATempFile);

                Thread.Sleep(100);

                return ReadAllBytes(pdfATempFile);
            }
            finally
            {
                Thread.Sleep(100);
                DeleteIfExists(htmlTempFile);
                DeleteIfExists(pdfTempFile);
                DeleteIfExists(pdfATempFile);
            }
        }

        private byte[] ReadAllBytes(string file)
        {
            byte[] buffer = null;
            
            using (FileStream fs = File.Open(file, System.IO.FileMode.Open, System.IO.FileAccess.Read, System.IO.FileShare.ReadWrite))
            {
                int streamLength = Convert.ToInt32(fs.Length);
                buffer = new byte[streamLength];
                fs.Read(buffer, 0, streamLength);
                fs.Close();
            }

            return buffer;
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
