#if NET9_0_OR_GREATER
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.DependencyInjection.Extensions;
using System;
using System.Collections.Generic;
using System.Text;

namespace VBG.Shared.Infrastructure.DependencyInjection.Standard
{
    public class CoreDIProvider : IDIProvider
    {

        private readonly IServiceCollection _services;

        public CoreDIProvider(IServiceCollection services)
        {
            this._services = services;
        }

        public void AddScoped<T1, T2>() where T1 : class where T2 : class, T1
        {
            this._services.AddScoped<T1, T2>();
        }

        public void AddScoped<T1>(Func<IDIServiceProvider, T1> factory) where T1 : class
        {
            this._services.AddScoped<T1>(x => factory(new CoreDIServiceProvider(x)));
        }

        public void AddScoped<T1>() where T1 : class
        {
            this._services.AddScoped<T1>();
        }

        public void AddSingleton<T1, T2>() where T1 : class where T2 : class, T1
        {
            this._services.AddSingleton<T1, T2>();
        }

        public void AddSingleton<T1>(Func<IDIServiceProvider, T1> factory) where T1 : class
        {
            this._services.AddSingleton<T1>(x => factory(new CoreDIServiceProvider(x)));
        }

        public void AddSingleton<T1>() where T1 : class
        {
            this._services.AddSingleton<T1>();
        }

        public void AddTransient<T1>() where T1 : class
        {
            this._services.AddTransient<T1>();
        }

        public void AddTransient<T1, T2>() where T1 : class where T2 : class, T1
        {
            this._services.AddTransient<T1, T2>();
        }

        public void AddTransient<T1>(Func<IDIServiceProvider, T1> factory) where T1 : class
        {
            this._services.AddTransient<T1>(x => factory(new CoreDIServiceProvider(x)));
        }

        void IDIProvider.ReplaceSingleton<T1, T2>()
        {
            var descriptor =
                new ServiceDescriptor(
                    typeof(T1),
                    typeof(T2),
                    ServiceLifetime.Singleton);
            this._services.Replace(descriptor);
        }
    }

    public class CoreDIServiceProvider : IDIServiceProvider
    {
        private readonly IServiceProvider _serviceProvider;

        public CoreDIServiceProvider(IServiceProvider serviceProvider)
        {
            this._serviceProvider = serviceProvider;
        }
        public T GetService<T>()
        {
            return this._serviceProvider.GetService<T>();

        }

        public object GetService(Type serviceType)
        {
            return this._serviceProvider.GetService(serviceType);
        }
    }
}
#endif