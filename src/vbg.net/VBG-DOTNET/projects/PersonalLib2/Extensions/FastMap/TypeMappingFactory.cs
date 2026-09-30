using PersonalLib2.Extensions.FastMap.ProperyNameResolutionStrategies;
using System;

namespace PersonalLib2.Extensions.FastMap
{
    internal class TypeMappingFactory
    {
        internal MappedTypeDescriptor CreateMapping(Type type)
        {
            return new MappedTypeDescriptor(type, new RemoveUnderscores());
        }
    }
}
