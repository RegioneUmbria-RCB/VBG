using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using System.Diagnostics;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.ConversionePDF
{
    public class PhantomjsFileConverter : HtmlToPdfFileConverterBase, IHtmlToPdfAsyncFileConverter
    {
        private readonly PhantomjsRenderer _renderer;
        private readonly string _phantomjsPath;

        public PhantomjsFileConverter(IConfigurazione<ParametriPhantomjs> config, PhantomjsRenderer renderer)
        {
            this._phantomjsPath = config.Parametri.PhantomjsPath;
            this._renderer = renderer;
        }

        public override BinaryFile Converti(string nomeFile, string html, RenderingFlags? renderingFlags = null)
        {
            var sw = Stopwatch.StartNew();

            try
            {
                var fileContent = this._renderer.RenderHtml(this._phantomjsPath, html, renderingFlags);

                return new BinaryFile(nomeFile, "application/pdf", fileContent);
            }
            finally
            {
                sw.Stop();

                Debug.WriteLine("Generazione del file \"{0}\" effettuata in {1} ms", nomeFile, sw.ElapsedMilliseconds);
            }
        }

        public Task<BinaryFile> ConvertiAsync(string nomeFile, string html, RenderingFlags? renderingFlags = null)
        {
            return Task.FromResult(this.Converti(nomeFile, html, renderingFlags));
        }

        public Task<BinaryFile> TrasformaEConvertiAsync(string nomeFile, string xml, string xsl)
        {
            return Task.FromResult(this.TrasformaEConverti(nomeFile, xml, xsl));
        }
    }
}
