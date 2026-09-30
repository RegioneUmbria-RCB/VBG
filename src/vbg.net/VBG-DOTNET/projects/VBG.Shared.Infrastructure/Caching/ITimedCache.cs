using System;

namespace VBG.Shared.Infrastructure.Caching
{
    public interface ITimedCache
    {
        T GetOrAdd<T>(string key, int timeoutMinutes, Func<T> addCallback) where T : class;
        void Remove(string key);

        void Clear();
    }
}
