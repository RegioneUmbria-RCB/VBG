using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF.Ghostscript;
using Init.Sigepro.FrontEnd.Infrastructure.Server;
using log4net;
using System;
using System.Diagnostics;
using System.IO;
using System.Runtime.InteropServices;
using System.Threading;

namespace Init.Sigepro.FrontEnd.AppLogic.ConversionePDF
{
    /* ATTENZIONE: Per il corretto funzionamento sotto linux è necessario scaricare Phantomjs da
     * https://phantomjs.org/download.html (seguire le indicazioni per Linux 64bit)
     * e il pacchetto fontconfig (sudo apt-get install fontconfig)
     */
    public class PhantomjsRenderer
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(PhantomjsRenderer));
        private readonly PdfToPdfAConverter _pdfToPdfAConverter;
        private readonly IPathMapper _pathMapper;

        public PhantomjsRenderer(PdfToPdfAConverter pdfToPdfAConverter, IPathMapper pathMapper)
        {
            this._pdfToPdfAConverter = pdfToPdfAConverter;
            this._pathMapper = pathMapper;
        }

        public Byte[] RenderHtml(string phantomjsPath, string html, RenderingFlags? renderingFlags)
        {
            if (renderingFlags == null)
            {
                renderingFlags = RenderingFlags.Default;
            }

            if (phantomjsPath.StartsWith("~"))
            {
                phantomjsPath = this._pathMapper.MapPath(phantomjsPath);
            }

            var guid = Guid.NewGuid().ToString();
            var htmlTempFile = Path.Combine(Path.GetTempPath(), guid + ".html");
            var pdfTempFile = Path.Combine(Path.GetTempPath(), guid + ".pdf");
            var pdfATempFile = Path.Combine(Path.GetTempPath(), guid + ".pdfa.pdf");


            File.WriteAllText(htmlTempFile, html, System.Text.Encoding.UTF8);

            try
            {
                // var t = new Thread(new ParameterizedThreadStart(x =>
                // {
                var phantomFileName = RuntimeInformation.IsOSPlatform(OSPlatform.Windows) ?
                        "phantomjs.exe" :
                        "phantomjs";

                var sourceFileName = RuntimeInformation.IsOSPlatform(OSPlatform.Windows) ?
                    $"file:///{(htmlTempFile.Replace("\\", "/"))}" :
                    htmlTempFile;

                var command = Path.Combine(phantomjsPath, phantomFileName);
                var arg = $" {renderingFlags.RasterizeScript} \"{sourceFileName}\" \"{pdfTempFile}\" \"A4\"";

                this._log.DebugFormat("Generazione pdf da html: {0} {1}", command, arg);

                ProcessStartInfo pi;

                pi = new ProcessStartInfo(command, arg);
                pi.CreateNoWindow = true;
                pi.UseShellExecute = false;
                pi.WorkingDirectory = phantomjsPath;

                using (var p = Process.Start(pi))
                {
                    this._log.Debug("Attesa della fine del processo phantomjs");

                    p.WaitForExit();

                    p.Close();
                }

                if (!File.Exists(pdfTempFile))
                {
                    throw new Exception($"File {pdfTempFile} non creato");
                }

                if (!renderingFlags.ConvertToPdfa)
                {
                    return this.ReadAllBytes(pdfTempFile);
                }

                this._pdfToPdfAConverter.ConvertiInPdfA(pdfTempFile, pdfATempFile);

                Thread.Sleep(100);

                return this.ReadAllBytes(pdfATempFile);
            }
            finally
            {
                Thread.Sleep(100);
                this._log.Debug("Eliminazione del file html");
                this.DeleteIfExists(htmlTempFile);
                this._log.Debug("Eliminazione del file PDF");
                this.DeleteIfExists(pdfTempFile);
                this._log.Debug("Eliminazione del file PDF/A");
                this.DeleteIfExists(pdfATempFile);
            }
        }

        private byte[] ReadAllBytes(string file)
        {
            return File.ReadAllBytes(file);
        }

        private void DeleteIfExists(string tempFile)
        {
            try
            {
                if (File.Exists(tempFile))
                {
                    File.Delete(tempFile);
                }
                else
                {
                    this._log.Debug($"DeleteIfExists: Il file {tempFile} non esiste");
                }
            }
            catch (Exception ex)
            {
                this._log.Error($"Impossibile eliminare il file temporaneo {tempFile}: {ex}");
            }
        }

    }
}
