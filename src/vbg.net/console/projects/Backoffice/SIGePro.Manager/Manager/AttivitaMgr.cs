using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using System.Collections.Generic;
using Init.SIGePro.Authentication;
using System.ComponentModel;

namespace Init.SIGePro.Manager 
{ 	
    [DataObject(true)]
	public class AttivitaMgr: BaseManager
	{
		public AttivitaMgr( DataBase dataBase ) : base( dataBase ) {}

		public Attivita GetById(String pCODICEISTAT, String pIDCOMUNE)
		{
			Attivita retVal = new Attivita();
			
			retVal.CodiceIstat = pCODICEISTAT;
			retVal.IDCOMUNE = pIDCOMUNE;
		
			return db.GetClass(retVal) as Attivita;
		}

        [DataObjectMethod( DataObjectMethodType.Select ) ]
        public static List<Attivita> Find(string token, string codiceSettore)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            Attivita filtro = new Attivita();
            filtro.IDCOMUNE = authInfo.IdComune;
            filtro.CODICESETTORE = codiceSettore;

			return authInfo.CreateDatabase().GetClassList(filtro).ToList < Attivita>();
        }

 
		public ArrayList GetList(Attivita p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(Attivita p_class, Attivita p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}



		public void Delete(Attivita p_class)
		{
			db.Delete( p_class) ;
		}

		public Attivita Insert( Attivita p_class  )
		{
			db.Insert( p_class ) ;
			return p_class;
		}

		public Attivita Update( Attivita p_class  )
		{
			db.Update( p_class ) ;
			return p_class;
		}
	}
}