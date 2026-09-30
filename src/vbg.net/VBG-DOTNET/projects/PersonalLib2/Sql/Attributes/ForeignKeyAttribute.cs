using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;

namespace PersonalLib2.Sql.Attributes
{
    /// <summary>
    /// Attributo associabile ad una proprietà di una classe di tipo DataClass, viene utilizzato per specificare
    /// che la proprietà contiene una tabella correlata.
    /// </summary>
    [AttributeUsage(AttributeTargets.Property, AllowMultiple = false), Serializable]
    public class ForeignKeyAttribute : Attribute
    {
        internal static DataClass InstantiateDataClass(PropertyInfo property)
        {
            Type propType = property.PropertyType;

            if (propType.IsSubclassOf(typeof(DataClass)))   // La proprietà è una dataclass, la istanzio direttamente
                return (DataClass)Activator.CreateInstance(propType);


            if (propType.IsGenericType && propType.GetGenericTypeDefinition() == typeof(List<>))    // La proprietà è una lista generica, istanzio il tipo dell'item contenuto
            {
                propType = propType.GetGenericArguments()[0];

                if (propType.IsSubclassOf(typeof(DataClass)))
                    return (DataClass)Activator.CreateInstance(propType);
            }

            string errMsg = "Il tipo {0} non è supportato dall'attributo ForeignKeyAttribute, la proprietà decorata dall'attributo deve essere di tipo DataClass o List<DataClass> ";

            throw new NotSupportedException(String.Format(errMsg, propType.Name.ToString()));
        }

        internal static bool PropertyIsList(PropertyInfo property)
        {
            return property.PropertyType.IsGenericType && property.PropertyType.GetGenericTypeDefinition() == typeof(List<>);
        }

        internal string[] ClassPropertiesNames { get; }
        internal string[] ForeignPropertiesNames { get; }

        public ForeignKeyAttribute(string localClassProperties, string remoteClassProperties)
        {
            this.ClassPropertiesName = localClassProperties;

            this.ClassPropertiesNames = localClassProperties.Split(',').Select(x => x.Trim()).ToArray();
            this.ForeignPropertiesNames = remoteClassProperties.Split(',').Select(x => x.Trim()).ToArray();
        }


        /// <summary>
        /// E' la lista dei nomi delle proprietà che si legano in join con ForeignPropertiesName.
        /// </summary>
        private string ClassPropertiesName { get; }

        public bool ClassPropertiesNameHasValue => !String.IsNullOrEmpty(this.ClassPropertiesName);

        public void CopyToList<T>(IList targetList, List<T> values)
        {
            targetList.Clear();

            var targetType = targetList.GetType();

            // Se è un generic di tipo List<T> utilizzo il metodo AddRange per effettuare una copia rapida
            if (targetType.IsGenericType && targetType.GetGenericTypeDefinition() == typeof(List<>))
            {
                var addRange = targetType.GetMethod("AddRange");
                var targetItemType = targetType.GetGenericArguments()[0];
                var vals = this.GetType()
                            .GetMethod("ChangeListType")
                            .MakeGenericMethod(targetItemType, typeof(T))
                            .Invoke(this, new[] { values });

                addRange.Invoke(targetList, new[] { vals });
                return;
            }

            foreach (var item in values)
            {
                targetList.Add(item);
            }
        }

        public IEnumerable<T> ChangeListType<T, Y>(IEnumerable<Y> values)
        {
            List<T> list = new List<T>();
            foreach (var item in values)
            {
                list.Add((T)Convert.ChangeType(item, typeof(T)));
            }
            return list;
        }
    }


}