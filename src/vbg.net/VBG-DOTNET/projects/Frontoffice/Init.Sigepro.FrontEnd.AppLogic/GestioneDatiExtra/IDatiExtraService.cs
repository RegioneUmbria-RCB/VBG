using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneDatiExtra
{
    public interface IDatiExtraService
    {
        void Set<T>(int idPratica, string chiave, T valore) where T : class;
        T Get<T>(int idPratica, string chiave) where T : class;

        Task SetAsync<T>(int idPratica, string chiave, T valore) where T : class;
        Task<T> GetAsync<T>(int idPratica, string chiave) where T : class;
    }
}
