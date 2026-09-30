#if NET48
using Ninject;
using Ninject.Web.Common;
using System;
using System.Collections.Generic;
using System.Text;

namespace VBG.Shared.Infrastructure.DependencyInjection.Framework
{
    internal class NinjectDIProvider : IDIProvider, IDIServiceProvider
    {
        private readonly IKernel _kernel;

        public NinjectDIProvider(IKernel kernel)
        {
            this._kernel = kernel;
        }

        public void AddScoped<T1, T2>() where T1 : class where T2 : class, T1
        {
            this._kernel.Bind<T1>().To<T2>().InRequestScope();
        }

        public void AddScoped<T1>(Func<IDIServiceProvider, T1> factory) where T1 : class
        {
            this._kernel.Bind<T1>().ToMethod(_ => factory(this)).InRequestScope();
        }

        public void AddScoped<T1>() where T1 : class
        {
            this._kernel.Bind<T1>().ToSelf().InRequestScope();
        }

        public void AddSingleton<T1, T2>() where T1 : class where T2 : class, T1
        {
            this._kernel.Bind<T1>().To<T2>().InSingletonScope();
        }

        public void AddSingleton<T1>(Func<IDIServiceProvider, T1> factory) where T1 : class
        {
            this._kernel.Bind<T1>().ToMethod(_ => factory(this)).InSingletonScope();
        }

        public void AddSingleton<T1>() where T1 : class
        {
            this._kernel.Bind<T1>().ToSelf().InSingletonScope();
        }

        public void AddTransient<T1>() where T1 : class
        {
            this._kernel.Bind<T1>().ToSelf().InTransientScope();
        }

        public void AddTransient<T1, T2>() where T1 : class where T2 : class, T1
        {
            this._kernel.Bind<T1>().To<T2>().InTransientScope();
        }

        public void AddTransient<T1>(Func<IDIServiceProvider, T1> factory) where T1 : class
        {
            this._kernel.Bind<T1>().ToMethod(_ => factory(this)).InTransientScope();
        }

        public T GetService<T>()
        {
            return this._kernel.Get<T>();
        }

        public object GetService(Type serviceType)
        {
            return this._kernel.Get(serviceType);
        }
    }
}
#endif
