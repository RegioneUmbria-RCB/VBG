using Init.Sigepro.FrontEnd.AppLogic.GestioneDatiExtra;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneStatoCompilazioneStep
{
    public class StatoCompilazioneStepService : IStatoCompilazioneStepService
    {
        public class StepCompilatoDomanda
        {
            public int Id { get; set; } = 0;
            public string Descrizione { get; set; } = "";
            public bool Compilato { get; set; } = false;
        }

        private static class Constants
        {
            public const string DatiExtraKey = "StatoCompilazioneStepService.StepsCompilati";
        }

        private readonly IDatiExtraService _datiExtraService;

        public StatoCompilazioneStepService(IDatiExtraService datiExtraService)
        {
            this._datiExtraService = datiExtraService;
        }

        public void RegistraStepsDaCompilare(int idDomanda, IEnumerable<InfoStepDomanda> stepDomanda)
        {
            // Se gli steps sono uguali non faccio niente altrimenti elimino i vecchi step e aggiungo quelli nuovi
            var oldSteps = this._datiExtraService.Get<List<StepCompilatoDomanda>>(idDomanda, Constants.DatiExtraKey);

            var dict = oldSteps?.ToDictionary(x => x.Id, x => x) ?? new Dictionary<int, StepCompilatoDomanda>();

            var newSteps = stepDomanda.Select(x => new StepCompilatoDomanda
            {
                Id = x.Id,
                Descrizione = x.Descrizione,
                Compilato = dict.TryGetValue(x.Id, out var el) ? el.Compilato : false
            }).ToList();

            this._datiExtraService.Set(idDomanda, Constants.DatiExtraKey, newSteps);
        }

        public void ContrassegnaStepComeCompilato(int idDomanda, int indiceStep)
        {
            var oldSteps = this._datiExtraService.Get<List<StepCompilatoDomanda>>(idDomanda, Constants.DatiExtraKey) ?? new List<StepCompilatoDomanda>();

            var step = oldSteps.FirstOrDefault(x => x.Id == indiceStep);

            if (step == null)
            {
                step = new StepCompilatoDomanda
                {
                    Id = indiceStep,
                    Descrizione = ""
                };
                oldSteps.Add(step);
            }

            step.Compilato = true;

            this._datiExtraService.Set(idDomanda, Constants.DatiExtraKey, oldSteps);
        }

        public bool TuttiGliStepSonoStatiCompilati(int idDomanda)
        {
            var oldSteps = this._datiExtraService.Get<List<StepCompilatoDomanda>>(idDomanda, Constants.DatiExtraKey) ?? new List<StepCompilatoDomanda>();

            return oldSteps.All(x => x.Compilato);
        }

        public int GetIndicePrimoStepNonCompilato(int idDomanda)
        {
            var oldSteps = this._datiExtraService.Get<List<StepCompilatoDomanda>>(idDomanda, Constants.DatiExtraKey) ?? new List<StepCompilatoDomanda>();

            return oldSteps.FirstOrDefault(x => !x.Compilato)?.Id ?? 0;
        }
    }
}
