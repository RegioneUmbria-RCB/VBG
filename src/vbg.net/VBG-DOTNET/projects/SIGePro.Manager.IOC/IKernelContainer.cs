using System;

namespace Init.SIGePro.Manager.IOC
{
    public interface IKernelContainer
    {
        T GetService<T>();
        object GetService(Type t);
    }
}
