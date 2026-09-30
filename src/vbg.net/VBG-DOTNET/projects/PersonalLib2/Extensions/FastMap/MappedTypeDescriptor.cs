using System;

namespace PersonalLib2.Extensions.FastMap
{
    internal class MappedTypeDescriptor
    {
        private readonly Type _type;
        private readonly IPropertyNameResolutionStrategy _propertyNameResolutionStrategy;

        public MappedTypeDescriptor(Type type, IPropertyNameResolutionStrategy propertyNameResolutionStrategy)
        {
            this._type = type;
            this._propertyNameResolutionStrategy = propertyNameResolutionStrategy;
        }

        internal object CreateInstance()
        {
            return Activator.CreateInstance(this._type);
        }

        internal MappingFunction CreateMapper(string fieldName, int i)
        {
            string propName = this._propertyNameResolutionStrategy.TransformFieldName(fieldName);

            if (string.IsNullOrEmpty(propName))
            {
                return null;
            }

            var bindingFlags = System.Reflection.BindingFlags.Public |
                                System.Reflection.BindingFlags.NonPublic |
                                System.Reflection.BindingFlags.Instance |
                                System.Reflection.BindingFlags.IgnoreCase;

            var property = this._type.GetProperty(propName, bindingFlags);

            if (property == null)
            {
                return null;
            }

            return new MappingFunction(property, i);
        }


    }
}