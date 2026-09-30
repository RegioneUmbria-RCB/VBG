
namespace GeneratoreRiepiloghiHtml.AppLogic.GestioneInterventi
{
    public interface IInterventiService
    {
        Task<int?> GetCodiceOggettoCertificatoDiInvioDaIdInterventoAsync(int idIntervento);
        Task<int?> GetCodiceOggettoDelModelloDiRiepilogoAsync(int idIntervento);
    }
}