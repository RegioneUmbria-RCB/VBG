using VBG.DatiDinamici.Standard.Utils.CreazioneModelli;

namespace GeneratoreRiepiloghiHtml.AppLogic.GestioneDatiDinamici
{
    public interface IStrutturaSchedeDinamicheService
    {
        Task<IStrutturaModelloDinamico> GetStrutturaModelloAsync(int idScheda);
        Task<IEnumerable<SchedaInterventoOEndo>> GetSchedeDaEndoprocedimentiAsync(IEnumerable<int> idEndoSelezionati);
        Task<IEnumerable<SchedaInterventoOEndo>> GetSchedeDaInterventoAsync(int idIntervento);
        Task<IEnumerable<SchedaInterventoOEndo>> GetSchedeDaInterventoEEndoAsync(int idIntervento, IEnumerable<int> idEndoSelezionati);
    }
}
