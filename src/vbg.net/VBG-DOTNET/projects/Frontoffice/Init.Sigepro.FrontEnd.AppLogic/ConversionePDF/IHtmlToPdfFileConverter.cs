using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.ConversionePDF
{
    public interface IHtmlToPdfFileConverter
    {
        BinaryFile Converti(string nomeFile, string html, RenderingFlags? renderingFlags = null);
        BinaryFile TrasformaEConverti(string nomeFile, string xml, string xsl);
    }

    public interface IHtmlToPdfAsyncFileConverter
    {
        Task<BinaryFile> ConvertiAsync(string nomeFile, string html, RenderingFlags? renderingFlags = null);
        Task<BinaryFile> TrasformaEConvertiAsync(string nomeFile, string xml, string xsl);
    }
}
