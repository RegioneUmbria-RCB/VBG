
namespace VBG.Backend.Protocollo.AppLogic.Shared.Interfaces.Abstractions
{
    public interface IRequestContextStorage
    {
        bool TryGetValue<T>(string key, out T value);
        void SetValue<T>(string key, T value);
        void Remove(string key);
    }
}
