using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using Init.Utils.Sorting;
using PersonalLib2.Sql;
using System;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CanoniConfigAreeMgr
    {
        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CanoniConfigAree> Find(string token, int anno, string sortExpression)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            CanoniConfigAree filtro = new CanoniConfigAree();

            filtro.Idcomune = authInfo.IdComune;
            filtro.Anno = anno;
            filtro.UseForeign = useForeignEnum.Yes;

            if (String.IsNullOrEmpty(sortExpression))
                sortExpression = "Aree";

            // gestione ordinamento
            List<CanoniConfigAree> list = authInfo.CreateDatabase().GetClassList(filtro, false);
            ListSortManager<CanoniConfigAree>.Sort(list, sortExpression);
            // fine gestione ordinamento

            return list;

        }

        public CanoniConfigAree GetByAnnoZona(string idcomune, int anno, int codicearea)
        {
            CanoniConfigAree c = new CanoniConfigAree();

            c.Idcomune = idcomune;
            c.Anno = anno;
            c.Codicearea = codicearea;

            return (CanoniConfigAree)this.db.GetClass(c);
        }
    }
}
