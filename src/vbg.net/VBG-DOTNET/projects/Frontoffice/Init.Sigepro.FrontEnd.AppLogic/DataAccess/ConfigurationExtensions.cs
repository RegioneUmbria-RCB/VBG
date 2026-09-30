using VBG.Shared.Infrastructure.DependencyInjection;

namespace Init.Sigepro.FrontEnd.AppLogic.DataAccess
{
    internal static class ConfigurazioneDataAccess
    {
        public static IDIProvider ConfiguraDataAccess(this IDIProvider kernel)
        {
            kernel.AddScoped<DbConnectionFactory>();
            kernel.AddScoped<FoDomandeRepository>();
            // kernel.AddScoped<SequenceTableService>();


            return kernel;
        }
    }
}
