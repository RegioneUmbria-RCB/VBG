// -----------------------------------------------------------------------
// <copyright file="OggettiService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti
{
    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public interface IOggettiService
    {
        BinaryFile GetById(int codiceOggetto);
        Task<BinaryFile> GetByIdAsync(int codiceOggetto);
        int InserisciOggetto(BinaryFile file);
        int InserisciOggetto(string nomeFile, string mimeType, byte[] data);
        Task<int> InserisciOggettoAsync(string nomeFile, string mimeType, byte[] data);
        string GetNomeFile(int codiceOggetto);
        Task<string> GetNomeFileAsync(int codiceOggetto);
        void AggiornaOggetto(int codiceOggetto, byte[] data);
        string GetMd5_non_usare_non_sempre_valorizzato(int codiceOggetto);
    }
}
