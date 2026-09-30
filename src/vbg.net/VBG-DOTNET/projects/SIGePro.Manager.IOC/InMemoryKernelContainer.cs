using System;
using VBG.Shared.Infrastructure.DependencyInjection;

namespace Init.SIGePro.Manager.IOC
{
    public class InMemoryKernelContainer : IKernelContainer
    {
        private readonly IDIServiceProvider _serviceProvider;

        public InMemoryKernelContainer(IDIServiceProvider serviceProvider)
        {
            this._serviceProvider = serviceProvider;
        }

        public T GetService<T>() => this._serviceProvider.GetService<T>();
        public object GetService(Type t) => this._serviceProvider.GetService(t);
    }
}
