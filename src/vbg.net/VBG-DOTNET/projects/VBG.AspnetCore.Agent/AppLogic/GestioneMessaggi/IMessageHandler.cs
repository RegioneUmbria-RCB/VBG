namespace VBG.AspnetCore.Agent.AppLogic.GestioneMessaggi
{
    internal interface IMessageHandler<T>
    {
        Task<bool> HandleAsync(string messageId, MessaggioRabbit<T> messaggio);
    }
}
