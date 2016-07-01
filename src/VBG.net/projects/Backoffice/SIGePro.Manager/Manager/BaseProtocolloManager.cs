using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Sql;
using PersonalLib2.Data;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Sql.Collections;

namespace Init.SIGePro.Manager
{
    public class BaseProtocolloManager : BaseManager
    {
        protected BaseProtocolloManager(DataBase db) : base(db)
        {

        }

        protected List<T> GetByClassProtocollo<T>(T c, string software, string codiceComune) where T : BaseDataClassProtocollo, new()
        {
            return String.IsNullOrEmpty(codiceComune) ? GetListByCodiceComuneVuoto<T>(c, software) : GetListByCodiceComuneValorizzato<T>(c, software, codiceComune);
        }

        private List<T> GetListByCodiceComuneVuoto<T>(T c, string software) where T : BaseDataClassProtocollo, new()
        {
            c.CodiceComune = null;
            c.Software = software;
            c.OthersWhereClause.Add("CODICECOMUNE is null");

            var res = db.GetClassList(c);
            
            if ((res == null || res.Count == 0) && software != "TT")
            {
                c.Software = "TT";
                res = db.GetClassList(c);
            }

            return res == null ? null : res.ToList<T>();
        }

        private List<T> GetListByCodiceComuneValorizzato<T>(T c, string software, string codiceComune) where T : BaseDataClassProtocollo, new()
        {
            c.CodiceComune = codiceComune;
            c.Software = software;

            var res = db.GetClassList(c);

            if (res != null && res.Count > 0)
                return res.ToList<T>();

            if (software != "TT")
            {
                c.Software = "TT";
                res = db.GetClassList(c);
            }

            return res != null && res.Count > 0 ? res.ToList<T>() : GetListByCodiceComuneVuoto<T>(c, software);
        }

        protected T GetByIdProtocollo<T>(T c, string software, string codiceComune) where T : BaseDataClassProtocollo, new()
        {
            return String.IsNullOrEmpty(codiceComune) ? GetByCodiceComuneVuoto<T>(c, software) : GetByCodiceComuneValorizzato<T>(c, software, codiceComune);
        }

        private T GetByCodiceComuneVuoto<T>(T c, string software) where T : BaseDataClassProtocollo, new()
        {
            c.CodiceComune = null;
            c.Software = software;
            c.OthersWhereClause.Add("CODICECOMUNE is null");

            var res = (T)db.GetClass(c);
            if (res == null && software != "TT")
            {
                c.Software = "TT";
                return (T)db.GetClass(c);
            }
            else
                return res;
        }

        private T GetByCodiceComuneValorizzato<T>(T c, string software, string codiceComune) where T : BaseDataClassProtocollo, new()
        {
            c.CodiceComune = codiceComune;
            c.Software = software;

            var res = (T)db.GetClass(c);

            if (res != null)
                return res;

            if (software != "TT")
            {
                c.Software = "TT";
                res = (T)db.GetClass(c);
            }

            return res != null ? res : GetByCodiceComuneVuoto<T>(c, software);
        }

    }
}
