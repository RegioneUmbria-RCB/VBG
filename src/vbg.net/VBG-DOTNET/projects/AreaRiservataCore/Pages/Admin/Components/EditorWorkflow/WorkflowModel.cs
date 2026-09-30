using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.V2;

namespace AreaRiservataCore.Pages.Admin.Components.EditorWorkflow
{

    public class WorkflowModel
    {
        public List<Step> Steps { get; set; } = new List<Step>();
    }

    public class Step(Guid? id = null)
    {
        public Guid Id { get; } = id ?? Guid.NewGuid();

        public string Title { get; set; } = "";

        public string Description { get; set; } = "";

        public string Control { get; set; } = "";

        public List<ControlProperty> ControlProperties { get; set; } = new List<ControlProperty>();
        public bool Disabled { get; set; }
        public WorkflowSteps StepId { get; set; } = WorkflowSteps.Undefined;
    }

    public class ControlProperty
    {
        public string Nome { get; set; } = "";
        public string Valore { get; set; } = "";
    }
}
