using System;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using PersonalLib2.Data;
using PersonalLib2.Sql.Collections;
using Init.SIGePro.Validator;

namespace Init.SIGePro.Manager 
{ 	

	public class PosteggiTipoSpazioMgr: BaseManager
	{

		public PosteggiTipoSpazioMgr( DataBase dataBase ) : base( dataBase ) {}
		
		public PosteggiTipoSpazio GetById(String pCODICE, String pIDCOMUNE )
		{
			PosteggiTipoSpazio retVal = new PosteggiTipoSpazio();
			retVal.IDCOMUNE = pIDCOMUNE;
			retVal.CODICE = pCODICE;

			DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as PosteggiTipoSpazio;
			
			return null;
		}
		

		public ArrayList GetList(PosteggiTipoSpazio p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(PosteggiTipoSpazio p_class, PosteggiTipoSpazio p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}


		public void Delete(PosteggiTipoSpazio p_class)
		{
			db.Delete( p_class) ;
		}

		public PosteggiTipoSpazio Insert( PosteggiTipoSpazio p_class )
		{
			p_class = DataIntegrations( p_class );

			Validate( p_class ,AmbitoValidazione.Insert);
			
			db.Insert( p_class );

			return p_class;
		}

		private PosteggiTipoSpazio DataIntegrations ( PosteggiTipoSpazio p_class )
		{
			PosteggiTipoSpazio retVal = ( PosteggiTipoSpazio ) p_class.Clone();

			if ( IsStringEmpty( retVal.IDCOMUNE ) )
				throw new RequiredFieldException("POSTEGGITIPOSPAZIO.IDCOMUNE obbligatorio");

			return retVal;
		}



		private void Validate(PosteggiTipoSpazio p_class, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( p_class , ambitoValidazione);
		}
	}
}