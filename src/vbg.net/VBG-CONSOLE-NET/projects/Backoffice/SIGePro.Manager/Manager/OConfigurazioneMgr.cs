using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Validator;

namespace Init.SIGePro.Manager
{
    public partial class OConfigurazioneMgr
    {
        public OConfigurazione Save(OConfigurazione cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            if (db.Update(cls) == 0)
                db.Insert(cls);

            return cls;
        }
    }
}
