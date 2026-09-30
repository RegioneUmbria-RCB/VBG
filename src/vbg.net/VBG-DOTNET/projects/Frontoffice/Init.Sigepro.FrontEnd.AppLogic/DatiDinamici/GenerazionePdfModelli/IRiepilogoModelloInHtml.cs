// -----------------------------------------------------------------------
// <copyright file="IriepilogoModelloInHtml.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli
{
    using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
    using System.Threading.Tasks;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public interface IRiepilogoModelloInHtml
    {
        BinaryFile ConvertiInPdf(string fileName, bool wrapinHtml = true);
        Task<BinaryFile> ConvertiInPdfAsync(string fileName, bool wrapinHtml = true);

    }
}
