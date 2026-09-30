using Init.SIGePro.Manager.DTO.Visura.ProssimiPassi;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneProssimiPassi
{
    public interface IProssimiPassiService
    {
        IEnumerable<ProssimiPassiDto> GetProssimiPassi(int codiceIstanza);
        Task<IEnumerable<ProssimiPassiDto>> GetProssimiPassiAsync(int codiceIstanza);
    }
}
