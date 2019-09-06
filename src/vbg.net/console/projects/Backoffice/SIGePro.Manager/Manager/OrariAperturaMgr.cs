using System;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using PersonalLib2.Data;
using Init.SIGePro.Validator;
using System.Collections.Generic;

namespace Init.SIGePro.Manager 
{ 	///<summary>
	/// Descrizione di riepilogo per OrariAperturaMgr.\n	/// </summary>
	public class OrariAperturaMgr: BaseManager
    {
		public OrariAperturaMgr( DataBase dataBase ) : base( dataBase ) {}

    	public OrariApertura GetById(String pOA_ID, String pIDCOMUNE)
		{
			OrariApertura retVal = new OrariApertura();
						retVal.OA_ID = pOA_ID;
			retVal.IDCOMUNE = pIDCOMUNE;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as OrariApertura;
			
			return null;
		}

        public List<OrariApertura> GetList(OrariApertura p_class)
		{
			return this.GetList(p_class,null);
		}

        public List<OrariApertura> GetList(OrariApertura p_class, OrariApertura p_cmpClass)
		{
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<OrariApertura>();
		}

		public OrariApertura Insert( OrariApertura p_class )
		{
			Validate(p_class, AmbitoValidazione.Insert);
			
			db.Insert( p_class );

			return p_class;
		}

		private void Validate(OrariApertura p_class, Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( p_class ,ambitoValidazione);

			ForeignValidate( p_class );
		}

		private void ForeignValidate ( OrariApertura p_class )
		{
			#region ORARIAPERTURATESTATA.ID
			if ( ! IsStringEmpty( p_class.OA_FKIDTESTATA ) )
			{
				if ( this.recordCount("ORARIAPERTURATESTATA","ID","WHERE ID = " + p_class.OA_FKIDTESTATA + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("ORARIAPERTURA.OA_FKIDITESTATA (" + p_class.OA_FKIDTESTATA + ") non trovato nella tabella ORARIAPERTURATESTATA"));
				}
			}
			#endregion

			#region ISTANZE.CODICEISTANZA
			if ( ! IsStringEmpty( p_class.OA_FKIDISTANZA ) )
			{
				if ( this.recordCount("ISTANZE","CODICEISTANZA","WHERE CODICEISTANZA = " + p_class.OA_FKIDISTANZA + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("ORARIAPERTURA.OA_FKIDISTANZA (" + p_class.OA_FKIDISTANZA + ") non trovato nella tabella ISTANZE"));
				}
			}
			#endregion

			#region GIORNISETTIMANA.GS_ID
			if ( ! IsStringEmpty( p_class.OA_FKIDGIORNO ) )
			{
				if ( this.recordCount("GIORNISETTIMANA","GS_ID","WHERE GS_ID = " + p_class.OA_FKIDGIORNO) == 0 )
				{
					throw( new RecordNotfoundException("ORARIAPERTURA.OA_FKIDGIORNO (" + p_class.OA_FKIDGIORNO + ") non trovato nella tabella GIORNISETTIMANA"));
				}
			}
			#endregion

			#region TIPIAPERTURA.TA_ID
			if ( ! IsStringEmpty( p_class.OA_FKIDORARIO ) )
			{
				if ( this.recordCount("TIPIAPERTURA","TA_ID","WHERE TA_ID = " + p_class.OA_FKIDORARIO + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("ORARIAPERTURA.OA_FKIDORARIO (" + p_class.OA_FKIDORARIO + ") non trovato nella tabella TIPIAPERTURA"));
				}
			}
			#endregion

			
		}

        public void Delete(OrariApertura p_class)
        {
            db.Delete(p_class);
        }

	}
}