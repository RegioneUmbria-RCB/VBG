// -----------------------------------------------------------------------
// <copyright file="OggettiService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti
{
    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public interface IOggettiService
    {
        BinaryFile GetById(int codiceOggetto);
        int InserisciOggetto(BinaryFile file);
        int InserisciOggetto(string nomeFile, string mimeType, byte[] data);
        string GetNomeFile(int codiceOggetto);
        BinaryFile GetRisorsaFrontoffice(string idRisorsa);
        void AggiornaOggetto(int codiceOggetto, byte[] data);
    }
}
