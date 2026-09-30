using System.Collections.Generic;
using System.Data;

namespace Init.SIGePro.Manager.Logic.Visura.QueryRicerca
{

    public class ParameterDefinition
    {
        public readonly string ParameterName;
        public readonly object Value;

        public ParameterDefinition(string name, object value)
        {
            this.ParameterName = name;
            this.Value = value;
        }
    }

    public interface IQueryCondition
    {
        string GetQuery();
        IEnumerable<ParameterDefinition> GetParameters();

        //void VerifyParameters();
    }
}