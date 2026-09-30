using System;
using System.Collections.Concurrent;

namespace PersonalLib2.Extensions.FastMap
{
    internal static class TypesCache
    {
        private static readonly ConcurrentDictionary<string, MappedTypeDescriptor> _typesDictionary = new ConcurrentDictionary<string, MappedTypeDescriptor>();

        internal static MappedType GetCachedType(Type type)
        {
            var key = type.Name;

            var descriptor = _typesDictionary.GetOrAdd(key, new TypeMappingFactory().CreateMapping(type));

            return new MappedType(descriptor);
        }
    }
}