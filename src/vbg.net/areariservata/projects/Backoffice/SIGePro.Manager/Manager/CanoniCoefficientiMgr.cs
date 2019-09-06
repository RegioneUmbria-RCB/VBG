using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;

namespace Init.SIGePro.Manager
{
    public partial class CanoniCoefficientiMgr
    {
        public CanoniCoefficienti GetById(string idcomune, int fk_tsid, int anno, int fk_ccid)
        {
            CanoniCoefficienti c = new CanoniCoefficienti();


            c.Idcomune = idcomune;
            c.FkTsId = fk_tsid;
            c.Anno = anno;
            c.FkCcId = fk_ccid;

            return GetByClass(c);
        }

        public CanoniCoefficienti GetByClass(CanoniCoefficienti c)
        {
            return (CanoniCoefficienti)db.GetClass(c);
        }

        public void DeleteDettagli(CanoniCoefficienti c)
        {
            List<CanoniCoefficienti> ar = GetList(c);
            foreach (CanoniCoefficienti cc in ar)
            {
                Delete(cc);
            }
        }
    }
}
