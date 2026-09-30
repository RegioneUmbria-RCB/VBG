namespace Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.Infrastructure
{
    public interface IRabbitPublisher
    {
        void Publish<T>(string topic, T data);
    }
}
