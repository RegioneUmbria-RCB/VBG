using Microsoft.Extensions.Diagnostics.HealthChecks;
using VBG.AspnetCore.Agent.AppLogic.RegistryStatoServizi;

namespace VBG.AspnetCore.Agent.AppLogic.HealthChecks
{
    public class ListenerStatusHealthCheck : IHealthCheck
    {
        private readonly IListenerStatusRegistry _statusRegistry;

        public ListenerStatusHealthCheck(IListenerStatusRegistry statusRegistry)
        {
            this._statusRegistry = statusRegistry;
        }

        public Task<HealthCheckResult> CheckHealthAsync(HealthCheckContext context, CancellationToken cancellationToken = default)
        {
            var offlineServices = this._statusRegistry.GetOfflineServices();

            if (offlineServices.Any())
            {
                var dict = new Dictionary<string, object>();

                foreach (var s in offlineServices)
                {
                    dict.Add(s.Name, s.Status);
                }

                this._statusRegistry.DumpStatus();

                var errMsg = $"Uno o più servizi sono offline: {String.Join(", ", offlineServices.Select(s => $"{s.Name} {s.Status.StatusEnum} ({s.Status.Reason})").ToArray())}";

                return Task.FromResult(HealthCheckResult.Unhealthy(errMsg, null, dict));
            }

            return Task.FromResult(HealthCheckResult.Healthy("Tutti i servizi funzionano correttamente"));
        }
    }
}
