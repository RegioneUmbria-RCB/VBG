using System;

namespace PersonalLib2.Extensions.FastMap.ProperyNameResolutionStrategies
{
    internal class RemoveUnderscores : IPropertyNameResolutionStrategy
    {
        public string TransformFieldName(string fieldName)
        {
            if (String.IsNullOrEmpty(fieldName))
            {
                return String.Empty;
            }

            return fieldName.Replace("_", "");
        }
    }
}
