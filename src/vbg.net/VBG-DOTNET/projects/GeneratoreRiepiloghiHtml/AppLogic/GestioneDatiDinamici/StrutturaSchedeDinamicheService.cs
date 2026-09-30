using VBG.DatiDinamici.Standard.Utils.CreazioneModelli;
using VbgDatiDinamici;

namespace GeneratoreRiepiloghiHtml.AppLogic.GestioneDatiDinamici
{
    public record SchedaInterventoOEndo
    {
        public required int IdModello { get; init; }
        public required bool Compilato { get; init; }
        public required string Descrizione { get; init; }
        public required int Ordine { get; init; }
    }

    public class StrutturaSchedeDinamicheService : IStrutturaSchedeDinamicheService
    {
        private readonly StrutturaSchedeDinamicheServiceCreator _serviceCreator;

        public StrutturaSchedeDinamicheService(StrutturaSchedeDinamicheServiceCreator serviceCreator)
        {
            this._serviceCreator = serviceCreator;
        }

        public async Task<IStrutturaModelloDinamico> GetStrutturaModelloAsync(int idScheda)
        {
            return await this._serviceCreator.CallAsync(async ws =>
            {
                var response = await ws.Service.GetStrutturaModelloDinamicoAsync(ws.Token, idScheda);

                return response.ToStrutturaModelloDinamico();
            });
        }

        public async Task<IEnumerable<SchedaInterventoOEndo>> GetSchedeDaEndoprocedimentiAsync(IEnumerable<int> idEndoSelezionati)
        {
            return await this.GetSchedeDaInterventoEEndoAsync(-1, idEndoSelezionati);
        }

        public async Task<IEnumerable<SchedaInterventoOEndo>> GetSchedeDaInterventoAsync(int idIntervento)
        {
            return await this.GetSchedeDaInterventoEEndoAsync(idIntervento, Array.Empty<int>());
        }

        public async Task<IEnumerable<SchedaInterventoOEndo>> GetSchedeDaInterventoEEndoAsync(int idIntervento, IEnumerable<int> idEndoSelezionati)
        {
            return await this._serviceCreator.CallAsync(async ws =>
            {
                var req = new GetModelliDinamiciDaInterventoEEndoRequest
                {
                    CodiceIntervento = idIntervento,
                    ListaEndo = idEndoSelezionati.ToArray(),
                    ListaTipiLocalizzazioni = Array.Empty<string>(),
                    IgnoraTipiLocalizzazione = true
                };

                var schedeDinamicheRichieste = await ws.Service.GetModelliDinamiciDaInterventoEEndoAsync(ws.Token, req);

                var listaSchedeEndo = schedeDinamicheRichieste.SchedeEndoprocedimenti.Select(x => new SchedaInterventoOEndo
                {
                    IdModello = x.Id,
                    Compilato = true,
                    Descrizione = x.Descrizione,
                    Ordine = x.Ordine.GetValueOrDefault(9999)
                });

                var listaSchedeintervento = schedeDinamicheRichieste.SchedeIntervento.Select(x => new SchedaInterventoOEndo
                {
                    IdModello = x.Id,
                    Compilato = true,
                    Descrizione = x.Descrizione,
                    Ordine = x.Ordine.GetValueOrDefault(9999)
                });

                return listaSchedeintervento.Union(listaSchedeEndo)
                            .OrderBy(x => x.Ordine)
                            .ThenBy(x => x.Descrizione)
                            .ToArray();
            });
        }
    }
}
