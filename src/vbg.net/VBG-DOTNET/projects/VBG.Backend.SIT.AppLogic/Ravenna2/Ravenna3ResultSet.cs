using System;
using System.Collections.Generic;

namespace VBG.Backend.SIT.AppLogic.Ravenna2
{
    public class Ravenna3ResultSet
    {
        private readonly List<string> _result;

        public Ravenna3ResultSet(String jsonString, String fieldName, string fieldType)
        {
            if (!String.IsNullOrEmpty(jsonString) && jsonString.TrimEnd().Length > 0)
            {
                this._result = JsonExtensions.PickElements(jsonString, fieldName, fieldType);
            }
        }
        internal Data.RetSit ToRetSit()
        {
            return new Data.RetSit(this._result.Count > 0, this._result);
        }
    }
}
