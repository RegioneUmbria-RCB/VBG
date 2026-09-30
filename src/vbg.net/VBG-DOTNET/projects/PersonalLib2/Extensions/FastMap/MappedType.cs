using System.Collections.Generic;
using System.Data;

namespace PersonalLib2.Extensions.FastMap
{

    internal class MappedType
    {
        private readonly MappedTypeDescriptor _descriptor;
        private List<MappingFunction> _fieldAssignFunction;

        public MappedType(MappedTypeDescriptor descriptor)
        {
            this._descriptor = descriptor;
        }

        internal object MapDataReader(IDataReader dr)
        {
            if (this._fieldAssignFunction == null)
            {
                this.AnalyseDataReader(dr);
            }

            var instance = this._descriptor.CreateInstance();

            for (var i = 0; i < this._fieldAssignFunction.Count; i++)
            {
                this._fieldAssignFunction[i].Map(instance, dr);
            }

            return instance;
        }

        private void AnalyseDataReader(IDataReader dr)
        {
            var fieldCount = dr.FieldCount;

            this._fieldAssignFunction = new List<MappingFunction>(fieldCount);

            for (int i = 0; i < fieldCount; i++)
            {
                var fieldName = dr.GetName(i);

                var mappingFunction = this._descriptor.CreateMapper(fieldName, i);

                if (mappingFunction != null)
                {
                    this._fieldAssignFunction.Add(mappingFunction);
                }
            }

        }
    }
}