using System;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using PersonalLib2.Data;
using PersonalLib2.Sql.Collections;
using Init.SIGePro.Validator;

namespace Init.SIGePro.Manager 
{ 	

	public class ConcessioniUsoMgr: BaseManager
	{

		public ConcessioniUsoMgr( DataBase dataBase ) : base( dataBase ) {}
		
		public ConcessioniUso GetById(String pCODICE, String pIDCOMUNE )
		{
			ConcessioniUso retVal = new ConcessioniUso();
			retVal.IDCOMUNE = pIDCOMUNE;
			retVal.CODICE = pCODICE;

			DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as ConcessioniUso;
			
			return null;
		}
		

		public ArrayList GetList(ConcessioniUso p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(ConcessioniUso p_class, ConcessioniUso p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}


		public void Delete(ConcessioniUso p_class)
		{
			db.Delete( p_class) ;
		}

		public ConcessioniUso Insert( ConcessioniUso p_class )
		{
			p_class = DataIntegrations( p_class );

			Validate(p_class, AmbitoValidazione.Insert);
			
			db.Insert( p_class );

			return p_class;
		}

		private ConcessioniUso DataIntegrations ( ConcessioniUso p_class )
		{
			ConcessioniUso retVal = ( ConcessioniUso ) p_class.Clone();

			if ( IsStringEmpty( retVal.IDCOMUNE ) )
				throw new RequiredFieldException("CONCESSIONIUSO.IDCOMUNE obbligatorio");

			if ( IsStringEmpty( retVal.SOFTWARE ) )
				throw new RequiredFieldException("CONCESSIONIUSO.SOFTWARE obbligatorio");

			return retVal;
		}



		private void Validate(ConcessioniUso p_class, Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( p_class ,ambitoValidazione);
		}
	}
}