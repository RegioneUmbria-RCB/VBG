using System;

namespace Init.SIGePro.Manager.IOC
{

    public static class StaticKernelContainer
    {
        private static IKernelContainer _instance = null;

        public static void Initialize(IKernelContainer serviceProvider)
        {
            _instance = serviceProvider;
        }

        public static T GetService<T>()
        {
            if (_instance == null)
            {
                return default(T);
            }

            return _instance.GetService<T>();
        }

        public static object GetService(Type t)
        {
            if (_instance == null)
            {
                return null;
            }

            return _instance.GetService(t);
        }
    }
}
