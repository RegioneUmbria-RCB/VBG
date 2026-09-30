using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;

namespace Init.SIGePro.Manager
{
    public partial class SorteggiTestataMgr
    {
        public SorteggiTestata GetByClass(SorteggiTestata cls)
        {
            return (SorteggiTestata)db.GetClass(cls);
        }
    }
}
