//using VBG.Shared.Infrastructure.DependencyInjection;
//using System;

//namespace Init.SIGePro.Manager.IOC
//{
//    public interface IKernelContainer
//    {
//        T GetService<T>();
//        object GetService(Type t);
//    }

//    public class InMemoryKernelContainer : IKernelContainer
//    {
//        private readonly IDIServiceProvider _serviceProvider;

//        public InMemoryKernelContainer(IDIServiceProvider serviceProvider)
//        {
//            this._serviceProvider = serviceProvider;
//        }

//        public T GetService<T>() => this._serviceProvider.GetService<T>();
//        public object GetService(Type t) => this._serviceProvider.GetService(t);
//    }

//    public static class StaticKernelContainer
//    {
//        private static IKernelContainer _instance = null;

//        public static void Initialize(IKernelContainer serviceProvider)
//        {
//            _instance = serviceProvider;
//        }

//public static T GetService<T>()
//{
//    if (_instance == null)
//    {
//        throw new Exception("Il servizio IKernelContainer non è inizializzato");
//        //return default(T);
//    }

//    var service = _instance.GetService<T>();

//    if (service == null)
//    {
//        throw new Exception($"Il servizio {typeof(T)} non può essere recuperato");
//    }

//    return service;
//}

//        public static object GetService(Type t)
//        {
//            if (_instance == null)
//            {
//                return null;
//            }

//            return _instance.GetService(t);
//        }
//    }
//}
