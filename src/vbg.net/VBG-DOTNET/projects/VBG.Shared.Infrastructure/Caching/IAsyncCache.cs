using System;
using System.Threading.Tasks;

namespace VBG.Shared.Infrastructure.Caching
{
    public interface IAsyncCache
    {
        Task<T> GetOrAddAsync<T>(string key, Func<Task<T>> addCallbackAsync) where T : class;

    }
}
