using PersonalLib2.Sql;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PersonalLib2.Data
{
    public class GetClassListFlags
    {
        public readonly useForeignEnum UseForeign;
        public readonly bool IgnoreSetMode;
        public readonly bool SingleRowException;

        public GetClassListFlags(useForeignEnum useForeign, bool ignoreSetMode = false, bool singleRowException = false)
        {
            this.UseForeign = useForeign;
            this.IgnoreSetMode = ignoreSetMode;
            this.SingleRowException = singleRowException;
        }
    }
}
