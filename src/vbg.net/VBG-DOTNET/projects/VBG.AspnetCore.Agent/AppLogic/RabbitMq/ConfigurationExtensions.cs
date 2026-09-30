using RabbitMQ.Client;
using VBG.AspnetCore.Agent.AppLogic.GestioneMessaggi;
using VBG.AspnetCore.Agent.AppLogic.GestioneMessaggi.DomandeInBozza;
using VBG.AspnetCore.Agent.AppLogic.GestioneMessaggi.DomandeInBozza.Contracts;
using VBG.AspnetCore.Agent.AppLogic.RabbitMq.Configuration;
using VBG.AspnetCore.Agent.AppLogic.RabbitMq.Listener.Connections;
using VBG.AspnetCore.Agent.AppLogic.RegistryStatoServizi;

namespace VBG.AspnetCore.Agent.AppLogic.RabbitMq
{
    public static class ConfigurationExtensions
    {
        public static IServiceCollection AddRabbitMq(this IServiceCollection services, string clientName)
        {
            // recupera settings
            services.AddSingleton<RabbitSettings>();

            // Configura il servizio RabbitMQ
            services.AddSingleton<IConnection>((sp) =>
            {
                BrokerConfig broker = sp.GetRequiredService<RabbitSettings>().GetBrokerConfig();

                ConnectionFactory factory = new ConnectionFactory
                {
                    HostName = broker.Server,
                    Port = broker.Port,
                    UserName = broker.Username,
                    Password = broker.Password,
                    // DispatchConsumersAsync = true,
                    ClientProvidedName = clientName
                };

                return factory.CreateConnectionAsync().Result;
            });

            services.AddSingleton<IConnectionInstance, ConnectionInstance>();
            services.AddSingleton<IListenerStatusRegistry, ListenerStatusRegistry>();

            // Registrazione dei listener
            //services.AddSingleton<DomandeInBozzaListener>();

            services.AddMessageHandler<DomandaInBozzaEliminataMessageBody, DomandeInBozzaListener>();

            return services;
        }

        private static IServiceCollection AddMessageHandler<TMessage, THandler>(this IServiceCollection services)
            where THandler : RabbitMqBaseListenerService, IMessageHandler<TMessage>
        {
            services.AddScoped<THandler>();
            services.AddScoped<IMessageHandler<TMessage>>(serviceProvider =>
            {
                var svc = serviceProvider.GetRequiredService<THandler>();
                return svc;
            });
            return services;
        }

    }
}
