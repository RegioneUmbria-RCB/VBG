using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.SIGePro.Manager.DTO.DatiDinamici;
using VBG.AppLogic.SSU.APICatalogoServizi.Client;

namespace VBG.AppLogic.SSU.GestioneDatiDinamici
{
    public class SsuDatiDinamiciDomandaSyncService
    {
        private readonly ISalvataggioDomandaStrategy _persistenzaStrategy;

        public SsuDatiDinamiciDomandaSyncService(ISalvataggioDomandaStrategy persistenzaStrategy)
        {
            this._persistenzaStrategy = persistenzaStrategy;
        }

        public async Task SincronizzaModelliDomandaAsync(int idDomanda, IEnumerable<ElementoListaSchedePerProcedimento> schedeSsu)
        {
            var domanda = await this._persistenzaStrategy.GetByIdAsync(idDomanda);
            var modelliSsu = schedeSsu.SelectMany(s => (s.Schede ?? Enumerable.Empty<SchedaDinamica>()).Select(x => new
            {
                IdProcedimento = s.Procedimento.Id,
                Scheda = x
            }))
                .Select((x, idx) => new ModelloDinamicoEndoprocedimentoDaSincronizzare(x.IdProcedimento, x.Scheda.Id!.Value, x.Scheda.Titolo, this.DecodificaTipoFirma(x.Scheda), !x.Scheda.Obbligatoria, idx))
                .ToArray();

            var cmd = new SincronizzaModelliDinamiciCommand(
                Enumerable.Empty<ModelloDinamicoInterventoDaSincronizzare>(),
                modelliSsu,
                null);

            domanda.WriteInterface.DatiDinamici.SincronizzaModelliDinamici(cmd);

            await this._persistenzaStrategy.SalvaAsync(domanda);
        }

        private TipoFirmaEnum DecodificaTipoFirma(SchedaDinamica scheda) => scheda.TipoFirma switch
        {
            TipoFirmaSchedaEnum.FirmaInteroModello => TipoFirmaEnum.InteroModello,
            TipoFirmaSchedaEnum.FirmaABlocchi => TipoFirmaEnum.SingoliBlocchi,
            _ => TipoFirmaEnum.NessunaFirma
        };
    }
}