namespace AspnetCoreServices.AppLogic
{
    public static class Log4NetConfigurationExtensions
    {
        public static WebApplicationBuilder ConfiguraLog4Net(this WebApplicationBuilder builder)
        {
            var log4netConfigFile = builder.Configuration.GetValue<string>("Settings:log4netconfig");

            if (String.IsNullOrEmpty(log4netConfigFile))
            {
                log4netConfigFile = "log4net.gelf.config";
            }

            builder.Logging.AddLog4Net(log4netConfigFile, true);

            return builder;
        }
    }
}
