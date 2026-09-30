using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.ConfigurazioneBackoffice
{
    public interface IDatiDinamiciOnceOnlyRepository
    {
        Task<ListaIdentificativiOnceOnly> GetIdentificativiOnceOnlyByIdInterventoEndoAsync(int idIntervento, List<int> endoSelezionati);
    }

    public class DatiDinamiciOnceOnlyRepository : IDatiDinamiciOnceOnlyRepository
    {
        private readonly WsDatiDinamiciServiceCreator _serviceCreator;

        public DatiDinamiciOnceOnlyRepository(WsDatiDinamiciServiceCreator serviceCreator)
        {
            this._serviceCreator = serviceCreator;
        }

        public async Task<ListaIdentificativiOnceOnly> GetIdentificativiOnceOnlyByIdInterventoEndoAsync(int idIntervento, List<int> endoSelezionati)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                var idOnceOnly = await ws.Service.GetIdentificativiOnceOnlyByIdInterventoEndoAsync(ws.Token, idIntervento, endoSelezionati.ToArray());

                return new ListaIdentificativiOnceOnly(idOnceOnly.Select(x => new IdentificativoDatoOnceOnly
                {
                    IdCampo = x.IdCampo,
                    NomeCampo = x.NomeCampo,
                    FonteEsterna = x.FonteEsterna,
                    IsUpload = x.IsUpload,
                    FonteInterna = x.FonteInterna
                }));
            }
        }
    }
}
