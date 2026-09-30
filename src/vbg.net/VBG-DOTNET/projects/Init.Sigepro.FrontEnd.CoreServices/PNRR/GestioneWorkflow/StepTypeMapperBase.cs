namespace Init.Sigepro.FrontEnd.CoreServices.PNRR.GestioneWorkflow
{
    public abstract class StepTypeMapperBase
    {
        public class StepMapping
        {
            public Type Type { get; }
            public Dictionary<string, object> Properties { get; }

            public StepMapping(Type type, Dictionary<string, object> properties)
            {
                this.Type = type;
                this.Properties = properties ?? new();
            }
        }

        public StepMapping MapStep(WorkflowStep step)
        {
            var type = MapStepType(step.StepType);
            var properties = step.Properties
                                 .Select(x => new
                                 {
                                     x.Name,
                                     CastType = type.GetProperty(x.Name)?.PropertyType,
                                     x.Value
                                 })
                                .Where(x => x.CastType is not null)
                                .Select(x => new
                                {
                                    x.Name,
                                    Value = (object)Convert.ChangeType(x.Value, x.CastType)
                                })
                                .ToDictionary(x => x.Name, x => x.Value);

            return new StepMapping(type, properties);
        }

        public abstract Type MapStepType(StepTypeEnum stepType);
        /*
        private static Type MapStepType(StepTypeEnum stepType)
        {
            return stepType switch
            {
                StepTypeEnum.InformativaPrivacy => typeof(GestionePrivacyStep),
                StepTypeEnum.Anagrafiche => typeof(GestioneAnagraficheStepV2),
                StepTypeEnum.Sottoscrittori => typeof(GestioneSottoscrittoriStep),
                StepTypeEnum.DomicilioElettronico => typeof(DomicilioElettronicoStep),
                StepTypeEnum.DatiIstanza => typeof(DatiIstanzaStep),
                StepTypeEnum.TestoLibero => typeof(TestoLiberoStep),
                StepTypeEnum.Informativa => typeof(InformativaStep),
                StepTypeEnum.Procure => typeof(ProcureStep),
                StepTypeEnum.AllegatiIntervento => typeof(AllegatiInterventoStep),
                StepTypeEnum.AllegatiEndo => typeof(AllegatiEndoprocedimentiStep),
                StepTypeEnum.ListaSchedeDinamiche => typeof(DatiDinamiciStep),
                StepTypeEnum.Localizzazioni => typeof(GestioneLocalizzazioniStep),
                StepTypeEnum.LocalizzazioniSIT => typeof(GestioneLocalizzazioniStep),
                _ => typeof(UnknownStepComponent),
            };
        }
        */
    }
}
