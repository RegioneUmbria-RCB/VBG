using System;
using System.Collections.Generic;

namespace PersonalLib2.Data.V2
{
    public static class ManagedDataProviderRegistry
    {
        private static readonly Dictionary<ProviderType, Func<IDataProviderFactory>> _providerDictionary = new Dictionary<ProviderType, Func<IDataProviderFactory>> {
            { ProviderType.MySqlClient, () => new MySqlDataProviderV2()},
#if NET9_0_OR_GREATER
            { ProviderType.OracleClient, () => new OracleNetStandardDataProvider()}
#else
            { ProviderType.OracleClient, () => new OracleClientDataProviderV2()}
#endif
        };

        public static void RegisterProvider(ProviderType type, Func<IDataProviderFactory> factory)
        {
            _providerDictionary.Add(type, factory);
        }

        public static bool Supports(ProviderType type)
        {
            return _providerDictionary.ContainsKey(type);
        }

        public static IDataProviderFactory Create(ProviderType type)
        {
            return _providerDictionary[type]();
        }
    }
}
