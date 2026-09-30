namespace GeneratoreRiepiloghiHtml.AppLogic.GestioneOggetti
{
    public interface IOggettiService
    {
        Task<BinaryFile> GetByIdAsync(int codiceOggetto);
    }
}
