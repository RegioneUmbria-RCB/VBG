using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza.VisuraSigepro;
using Init.SIGePro.Manager.DTO.Visura.ProssimiPassi;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneProssimiPassi
{
    public class ProssimiPassiService : IProssimiPassiService
    {
        private readonly IstanzeServiceCreator _serviceCreator;

        public ProssimiPassiService(IstanzeServiceCreator serviceCreator)
        {
            this._serviceCreator = serviceCreator;
        }

        public IEnumerable<ProssimiPassiDto> GetProssimiPassi(int codiceIstanza)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                var prossimiPassi = ws.Service.GetProssimiPassi(ws.Token, codiceIstanza);

                return prossimiPassi ?? Enumerable.Empty<ProssimiPassiDto>();
            }
        }

        public async Task<IEnumerable<ProssimiPassiDto>> GetProssimiPassiAsync(int codiceIstanza)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                var prossimiPassi = await ws.Service.GetProssimiPassiAsync(ws.Token, codiceIstanza);

                return prossimiPassi ?? Enumerable.Empty<ProssimiPassiDto>();
            }
        }
    }
}
