using System;

namespace VBG.Shared.Infrastructure.Caching
{
    public interface ICache
    {
        T GetOrAdd<T>(string key, Func<T> addCallback) where T : class;
        void Clear();
    }
}
