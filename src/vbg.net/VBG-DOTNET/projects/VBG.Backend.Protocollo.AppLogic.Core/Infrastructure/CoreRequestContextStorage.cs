using Microsoft.AspNetCore.Http;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces.Abstractions;

namespace VBG.Backend.Protocollo.AppLogic.Core.Infrastructure
{
    public class CoreRequestContextStorage : IRequestContextStorage
    {
        private readonly IHttpContextAccessor _accessor;

        public CoreRequestContextStorage(IHttpContextAccessor accessor)
        {
            _accessor = accessor;
        }

        public bool TryGetValue<T>(string key, out T value)
        {
            if (_accessor.HttpContext.Items.TryGetValue(key, out var obj) && obj is T t)
            {
                value = t;
                return true;
            }
            value = default;
            return false;
        }

        public void SetValue<T>(string key, T value)
        {
            _accessor.HttpContext.Items[key] = value;
        }

        public void Remove(string key)
        {
            _accessor.HttpContext.Items.Remove(key);
        }
    }
}
