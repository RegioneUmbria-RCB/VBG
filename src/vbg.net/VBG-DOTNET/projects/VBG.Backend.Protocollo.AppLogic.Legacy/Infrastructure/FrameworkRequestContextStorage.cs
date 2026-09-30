using System.Web;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces.Abstractions;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Infrastructure
{
    public class FrameworkRequestContextStorage : IRequestContextStorage
    {
        public bool TryGetValue<T>(string key, out T value)
        {
            if (HttpContext.Current?.Items[key] is T t)
            {
                value = t;
                return true;
            }
            value = default;
            return false;
        }

        public void SetValue<T>(string key, T value)
        {
            HttpContext.Current.Items[key] = value;
        }

        public void Remove(string key)
        {
            HttpContext.Current.Items.Remove(key);
        }
    }
}