using System;

namespace VBG.Shared.Infrastructure.DependencyInjection
{
    public interface IDIProvider
    {
        void AddScoped<T1>() where T1 : class;
        void AddScoped<T1, T2>() where T1 : class where T2 : class, T1;
        void AddScoped<T1>(Func<IDIServiceProvider, T1> factory) where T1 : class;
        void AddSingleton<T1, T2>() where T1 : class where T2 : class, T1;
        void AddSingleton<T1>() where T1 : class;
        void AddSingleton<T1>(Func<IDIServiceProvider, T1> factory) where T1 : class;
        void AddTransient<T1, T2>() where T1 : class where T2 : class, T1;
        void AddTransient<T1>() where T1 : class;
        void AddTransient<T1>(Func<IDIServiceProvider, T1> factory) where T1 : class;

#if NET9_0_OR_GREATER
        void ReplaceSingleton<T1, T2>() where T1 : class where T2 : class, T1;
#endif
    }

    public interface IDIServiceProvider
    {
        T GetService<T>();
        object GetService(Type serviceType);
    }
}
