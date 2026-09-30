using System.Collections.Generic;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto.Notifiche
{
    public interface INotificheTipiSoggettoService
    {
        Task<IEnumerable<string>> GetListaNominativiPerNotificheAsync(int idDomanda);
    }
}
