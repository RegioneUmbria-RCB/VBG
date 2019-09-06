using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;
using Init.SIGePro.Data;

namespace Init.SIGePro.Manager
{
	public partial class AlberoProcMgr : BaseManager
	{

		public AlberoProcMgr(DataBase dataBase) : base(dataBase) { }

		public AlberoProc GetByClass(AlberoProc p_class)
		{
			var mydc = db.GetClassList(p_class, true, false);
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
			return db.GetClassList(p_class, p_cmpClass, false, false).ToList<AlberoProc>();
		}

		public AlberoProc Update(AlberoProc p_class)
		{
			db.Update(p_class);

			return p_class;
		}


    }
}
