using PersonalLib2.Sql;
using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Data;
using System.Diagnostics;
using System.Linq;
using System.Reflection;

namespace PersonalLib2.Data.Metadata
{

    public class MetadataAnalyzer
    {
        private readonly HashSet<string> _analyzedAssemblies = new(StringComparer.OrdinalIgnoreCase);
        private readonly object _analysisLock = new();

        public static MetadataAnalyzer Instance { get; } = new MetadataAnalyzer();

        private MetadataAnalyzer() { }

        public void EnsureAnalysisExistsFor(Type typeToAnalyze)
        {
            if (typeToAnalyze == null)
            {
                throw new ArgumentNullException(nameof(typeToAnalyze));
            }

            lock (this._analysisLock)
            {
                var assembly = typeToAnalyze.Assembly;
                var key = assembly.IsDynamic ? assembly.FullName : assembly.Location ?? assembly.FullName;

                if (this._analyzedAssemblies.Contains(key))
                {
                    return;
                }

                this.Analyze(key, typeToAnalyze.Assembly);
            }
        }

        private void Analyze(string key, Assembly assembly)
        {
            var t = assembly.GetTypes().ToArray();
            var types = t.Where(x => typeof(DataClass).IsAssignableFrom(x));

            foreach (var type in types)
            {
                var metadata = this.Analyze(type);

                if (metadata != null)
                {
                    MetadataStore.Instance.AddMetadata(type, metadata);
                }
            }
#if DEBUG
            MetadataStore.Instance.DumpToDebugConsole();
#endif
            this._analyzedAssemblies.Add(key);
        }

        private DataClassMetadata Analyze(Type type)
        {
            var dataTable = ((DataTableAttribute[])type.GetCustomAttributes(typeof(DataTableAttribute), true)).FirstOrDefault();

            if (dataTable == null)
            {
                Debug.WriteLine($"Il tipo {type.Name} non è mappato su nessuna tabella");
                return null;
            }

            var assignableProperties = type.GetProperties(BindingFlags.Public | BindingFlags.Instance)
                                         .Select(x => new
                                         {
                                             Property = x,
                                             CustomAttribute = (BaseFieldAttribute)Attribute.GetCustomAttribute(x, typeof(BaseFieldAttribute), true)
                                         })
                                         .Where(x => x.CustomAttribute != null)
                                         .Select(x => new PropertyMetadata(dataTable.TableName, x.Property, x.CustomAttribute)).ToArray();


            var foreignKeys = type.GetProperties(BindingFlags.Public | BindingFlags.Instance)
                                            .Cast<PropertyInfo>()
                                            .Select(prop => new
                                            {
                                                Property = prop,
                                                Attribute = (ForeignKeyAttribute)Attribute.GetCustomAttribute(prop, typeof(ForeignKeyAttribute)),
                                                IsList = ForeignKeyAttribute.PropertyIsList(prop)
                                            })
                                            .Where(x => x.Attribute != null)
                                            .Select(x => new ForeignKeyMetadata(x.Property, x.Attribute, x.IsList ? ForeignKeyType.OneToMany : ForeignKeyType.OneToOne))
                                            .ToArray();

            return new DataClassMetadata(type, dataTable.TableName, assignableProperties, foreignKeys);
        }
    }
}
