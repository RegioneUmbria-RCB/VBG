using System;
using System.Collections.Concurrent;
using System.Diagnostics;
using System.Threading;

namespace VBG.Backend.SIT.AppLogic.Manager
{
    internal class SitRegistry
    {
        private static readonly Lazy<ConcurrentDictionary<string, Type>> _lazyRegistry =
            new Lazy<ConcurrentDictionary<string, Type>>(BuildRegistry, LazyThreadSafetyMode.ExecutionAndPublication);

        private static ConcurrentDictionary<string, Type> Registry => _lazyRegistry.Value;

        public ISitApi GetSitInstance(string sitName)
        {
            if (Registry.TryGetValue(sitName, out var sitType))
            {
                return (ISitApi)Activator.CreateInstance(sitType);
            }

            throw new ArgumentException($"SIT non implementato per il nome: {sitName}");
        }

        private static ConcurrentDictionary<string, Type> BuildRegistry()
        {
            var registry = new ConcurrentDictionary<string, Type>();
            var sitTypes = typeof(SitRegistry).Assembly.GetTypes();

            foreach (var t in sitTypes)
            {
                var attr = t.GetCustomAttributes(typeof(SitImplementationAttribute), false);
                if (attr.Length > 0)
                {
                    var sitAttr = (SitImplementationAttribute)attr[0];
                    registry.TryAdd(sitAttr.SitName, t);
                }
            }

#if DEBUG
            Debug.WriteLine("SIT Registry inizializzato:");
            foreach (var entry in registry)
            {
                Debug.WriteLine($"[{entry.Key}]  => {entry.Value.FullName}");
            }
#endif

            return registry;
        }
    }
}
