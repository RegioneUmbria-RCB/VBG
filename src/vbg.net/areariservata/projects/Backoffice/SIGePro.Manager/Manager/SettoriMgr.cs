using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager 
{
	public class SettoriMgr: BaseManager
	{
		public SettoriMgr( DataBase dataBase ) : base( dataBase ) {}

		public Settori GetById(String pCODICESETTORE, String pIDCOMUNE)
		{
			Settori retVal = new Settori();
			retVal.CODICESETTORE = pCODICESETTORE;
			retVal.IDCOMUNE = pIDCOMUNE;
		
			return db.GetClass(retVal) as Settori;
		}

        public List<Settori> GetList(Settori p_class)
		{
			return this.GetList(p_class,null);
		}

        public List<Settori> GetList(Settori p_class, Settori p_cmpClass)
		{
			return db.GetClassList(p_class,p_cmpClass,false,false).ToList<Settori>();
		}

		public void Delete(Settori p_class)
		{
			db.Delete( p_class) ;
		}

		public Settori Insert( Settori p_class )
		{
			db.Insert( p_class );
			return p_class;
		}
	
		public Settori Update( Settori p_class )
		{
			db.Update( p_class );
			return p_class;
		}
	}
}