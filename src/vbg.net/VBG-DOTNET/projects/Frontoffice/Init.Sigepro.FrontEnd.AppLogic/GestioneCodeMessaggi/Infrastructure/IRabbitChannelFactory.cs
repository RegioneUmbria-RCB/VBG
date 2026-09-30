using Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.Configurazione;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.Infrastructure
{
    public interface IRabbitChannelFactory
    {
        IRabbitPublisher CreatePublisher(ConfigurazioneRabbitMQ configurazione);
    }
}
