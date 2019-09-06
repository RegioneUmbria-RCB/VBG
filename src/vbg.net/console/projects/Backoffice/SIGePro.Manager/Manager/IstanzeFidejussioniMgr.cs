using System;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.IstanzeFidejussioni;
using PersonalLib2.Data;
using Init.SIGePro.Validator;
using System.Collections.Generic;

namespace Init.SIGePro.Manager 
{ 	

	public class IstanzeFidejussioniMgr: BaseManager
	{

		public IstanzeFidejussioniMgr( DataBase dataBase ) : base( dataBase ) {}


		public IstanzeFidejussioni GetById(String pCODICEISTANZA, String pIDFIDEJUSSIONE, String pIDCOMUNE)
		{
			IstanzeFidejussioni retVal = new IstanzeFidejussioni();
			
			retVal.CODICEISTANZA = pCODICEISTANZA;
			retVal.IDFIDEJUSSIONE = pIDFIDEJUSSIONE;
			retVal.IDCOMUNE = pIDCOMUNE;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as IstanzeFidejussioni;
			
			return null;
		}


        public List<IstanzeFidejussioni> GetList(IstanzeFidejussioni p_class)
		{
			return this.GetList(p_class,null);
		}

        public List<IstanzeFidejussioni> GetList(IstanzeFidejussioni p_class, IstanzeFidejussioni p_cmpClass)
		{
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<IstanzeFidejussioni>();
		}

		
		public void Delete(IstanzeFidejussioni p_class)
		{	
			db.Delete( p_class) ;
		}


		public IstanzeFidejussioni Insert( IstanzeFidejussioni p_class )
		{
			
			Validate( p_class ,AmbitoValidazione.Insert);
			
			db.Insert( p_class );

			return p_class;
		}

		private void Validate(IstanzeFidejussioni p_class, AmbitoValidazione ambitoValidazione)
		{	

			RequiredFieldValidate( p_class , ambitoValidazione);

			ForeignValidate( p_class );

		}

		private void ForeignValidate ( IstanzeFidejussioni p_class )
		{
			#region ISTANZEFIDEJUSSIONI.CODICEISTANZA
			if ( ! IsStringEmpty( p_class.CODICEISTANZA ) )
			{
				if ( this.recordCount("ISTANZE","CODICEISTANZA","WHERE CODICEISTANZA = " + p_class.CODICEISTANZA + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException( p_class, "ISTANZEFIDEJUSSIONI.CODICEISTANZA non trovato nella tabella ISTANZE"));
				}
			}
			#endregion

			#region ISTANZEFIDEJUSSIONI.FK_CODICESTATO
			if ( ! IsStringEmpty( p_class.FK_CODICESTATO ) )
			{
				if ( this.recordCount("FIDEJUSSIONESTATI","CODICESTATO","WHERE IDCOMUNE = '" + p_class.IDCOMUNE + "' AND CODICESTATO = " + p_class.FK_CODICESTATO) == 0 )
				{
					throw( new RecordNotfoundException( p_class, "ISTANZEFIDEJUSSIONI.FK_CODICESTATO (" + p_class.FK_CODICESTATO + ") non trovato nella tabella FIDEJUSSIONESTATI"));
				}
			}
			#endregion

			#region ISTANZEFIDEJUSSIONI.FK_RCO_ID
			if ( ! IsStringEmpty( p_class.FK_RCO_ID ) )
			{
				if ( this.recordCount("RAGGRUPPAMENTOCAUSALIONERI","RCO_ID","WHERE IDCOMUNE = '" + p_class.IDCOMUNE + "' AND RCO_ID = " + p_class.FK_RCO_ID) == 0 )
				{
					throw( new RecordNotfoundException(p_class, "ISTANZEFIDEJUSSIONI.FK_RCO_ID (" + p_class.FK_RCO_ID + ") non trovato nella tabella RAGGRUPPAMENTOCAUSALIONERI"));
				}
			}
			#endregion
		}

	}
}