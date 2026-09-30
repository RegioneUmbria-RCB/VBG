using GeneratoreRiepiloghiHtml.AppLogic.GestioneOggetti;

namespace GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghiSchede.FileConverter
{
    public interface IHtmlToPdfFileConverter
    {
        Task<BinaryFile> ConvertiAsync(string nomeFile, string html);
    }
}