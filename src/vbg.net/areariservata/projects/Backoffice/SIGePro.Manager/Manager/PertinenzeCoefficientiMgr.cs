using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using System.Data;

namespace Init.SIGePro.Manager
{
    public partial class PertinenzeCoefficientiMgr
    {
        public PertinenzeCoefficienti GetById(string idcomune, int id)
        {
            PertinenzeCoefficienti c = new PertinenzeCoefficienti();

            c.Idcomune = idcomune;
            c.Id = id;

            return GetByClass(c);
        }

        public PertinenzeCoefficienti GetByClass(PertinenzeCoefficienti c)
        {
            return (PertinenzeCoefficienti)db.GetClass(c);
        }

        public void DeleteDettagli(PertinenzeCoefficienti c)
        {
            List<PertinenzeCoefficienti> ar = GetList(c);
            foreach (PertinenzeCoefficienti cc in ar)
            {
                Delete(cc);
            }
        }
    }
}
