using Init.SIGePro.Data;
using PersonalLib2.Data;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager
{
    public partial class AlberoProcMgr : BaseManager
    {

        public AlberoProcMgr(DataBase dataBase) : base(dataBase) { }

        public AlberoProc GetByClass(AlberoProc p_class)
        {
            List<AlberoProc> mydc = this.db.GetClassList(p_class, true);
            if (mydc.Count != 0)
                return (mydc[0]) as AlberoProc;

            return null;
        }

        public List<AlberoProc> GetList(AlberoProc p_class)
        {
            return this.GetList(p_class, null);
        }

        public List<AlberoProc> GetList(AlberoProc p_class, AlberoProc p_cmpClass)
        {
            return this.db.GetClassList(p_class, p_cmpClass, false).ToList<AlberoProc>();
        }

        public AlberoProc Update(AlberoProc p_class)
        {
            this.db.Update(p_class);

            return p_class;
        }


    }
}
