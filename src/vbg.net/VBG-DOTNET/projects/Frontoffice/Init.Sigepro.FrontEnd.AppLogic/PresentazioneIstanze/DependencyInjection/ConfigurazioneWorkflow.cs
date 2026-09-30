using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.MergepointFinder;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.PropertyBinder;

namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.DependencyInjection
{
    internal static class ConfigurazioneWorkflow
    {
        public static IDIProvider ConfiguraWorkflow(this IDIProvider services)
        {
            services.AddScoped<IFrameworkToCoreWorkflowUrlMapper, FrameworkToCoreWorkflowUrlMapper>();
            services.AddScoped<WorkflowService>();
            services.AddScoped<IWorkflowService, CachedWorkflowService>();
            services.AddScoped<IWorkflowEditService, WorkflowEditService>();
            services.AddScoped<IWorkflowLoaderService, WorkflowLoaderService>();
            services.AddScoped<IMergepointFinder, MergepointFinder>();
            services.AddScoped<WorkflowStepPropertiesBinder>();

            return services;
        }
    }
}
