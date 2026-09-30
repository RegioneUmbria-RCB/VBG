//using Hangfire;

namespace VBG.AppLogic.SSU.GeneratoreRicevute.Configurazione
{
    public static class HangfireExtensions
    {
        //public static IServiceCollection AddCustomHangfireConfiguration(this IServiceCollection services, IConfiguration configuration)
        //{
        //    // Configurazione Hangfire: storage, serializer, ecc.
        //    services.AddHangfire(config =>
        //    {
        //        config.UseSimpleAssemblyNameTypeSerializer()
        //              .UseRecommendedSerializerSettings()
        //              .UseInMemoryStorage(); // Cambia con SQL/Redis in produzione
        //    });

        //    // Polling interval opzionale (default 15s)
        //    int polling = configuration.GetValue("Hangfire:SchedulePollingIntervalInSeconds", 15);

        //    services.AddHangfireServer(options =>
        //    {
        //        options.SchedulePollingInterval = TimeSpan.FromSeconds(polling);
        //    });

        //    return services;
        //}

        //public static void ConfigureRecurringJobs(this IServiceProvider serviceProvider, IConfiguration configuration)
        //{
        //    var recurringJobConfig = configuration.GetSection("Hangfire:RecurringJobs:GeneratoreRicevute");
        //    var jobName = recurringJobConfig.GetValue<string>("JobName");
        //    var cronExpression = recurringJobConfig.GetValue<string>("CronExpression");

        //    RecurringJob.AddOrUpdate<GeneratoreRicevuteTask>(
        //        jobName,
        //        task => task.RunAsync(),
        //        cronExpression);
        //}

        //public static IApplicationBuilder UseCustomHangfireDashboard(this IApplicationBuilder app, IConfiguration configuration)
        //{
        //    var dashboardPath = configuration.GetValue<string>("Hangfire:DashboardPath", "/hangfire");

        //    // Add Hangfire Dashboard with custom options
        //    app.UseHangfireDashboard(dashboardPath, new DashboardOptions
        //    {
        //        Authorization = new[] { new AllowAllDashboardAuthorizationFilter() }
        //    });

        //    return app;
        //}

        //// Example authorization filter to allow everyone (use this for development only)
        //private class AllowAllDashboardAuthorizationFilter : Hangfire.Dashboard.IDashboardAuthorizationFilter
        //{
        //    public bool Authorize(Hangfire.Dashboard.DashboardContext context) => true;
        //}
    }
}
