namespace Init.Sigepro.FrontEnd.CoreServices.PNRR.GestioneWorkflow
{
    public class WorkflowStep
    {
        public class StepProperty
        {
            public StepProperty()
            {
            }

            public StepProperty(string name, string value)
            {
                this.Name = name;
                this.Value = value;
            }

            public string Name { get; set; } = "";
            public string Value { get; set; } = "";
        }

        public StepTypeEnum StepType { get; set; } = StepTypeEnum.Unknown;
        public string Titolo { get; set; } = "";
        public string Descrizione { get; set; } = "";
        public bool NascondiSuRiepilogo { get; set; } = false;
        public IEnumerable<StepProperty> Properties { get; set; } = Enumerable.Empty<StepProperty>();
    }
}
