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
	public class OrariAperturaTestataMgr: BaseManager
    {
		public OrariAperturaTestataMgr( DataBase dataBase ) : base( dataBase ) {}

		public OrariAperturaTestata GetById(String pID, String pIDCOMUNE)
		{
			OrariAperturaTestata retVal = new OrariAperturaTestata();
			retVal.ID = pID;
			retVal.IDCOMUNE = pIDCOMUNE;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as OrariAperturaTestata ;
			
			return null;
		}

        public List<OrariAperturaTestata> GetList(OrariAperturaTestata p_class)
		{
			return this.GetList(p_class,null);
		}

        public List<OrariAperturaTestata> GetList(OrariAperturaTestata p_class, OrariAperturaTestata p_cmpClass)
		{
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<OrariAperturaTestata>();
		}

		public OrariAperturaTestata Insert( OrariAperturaTestata p_class )
		{
			Validate(p_class, AmbitoValidazione.Insert);
			
			db.Insert( p_class );

			p_class = ChildDataIntegrations( p_class );

			ChildInsert( p_class );

			return p_class;
		}

		private void Validate(OrariAperturaTestata p_class, Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( p_class ,ambitoValidazione);

			ForeignValidate( p_class );
		}

		private void ForeignValidate ( OrariAperturaTestata p_class )
		{
			#region ISTANZE.CODICEISTANZA
			if ( ! IsStringEmpty( p_class.CODICEISTANZA ) )
			{
				if ( this.recordCount("ISTANZE","CODICEISTANZA","WHERE CODICEISTANZA = " + p_class.CODICEISTANZA + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("ORARIAPERTURATESTATA.CODICEISTANZA (" + p_class.CODICEISTANZA + ") non trovato nella tabella ISTANZE"));
				}
			}
			#endregion

			#region TIPIORARIO.TO_ID
			if ( ! IsStringEmpty( p_class.FKTOID ) )
			{
				if ( this.recordCount("TIPIORARIO","TO_ID","WHERE TO_ID = " + p_class.FKTOID + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("ORARIAPERTURATESTATA.FKTOID (" + p_class.FKTOID + ") non trovato nella tabella TIPIORARIO"));
				}
			}
			#endregion

			
		}

		private OrariAperturaTestata ChildDataIntegrations(  OrariAperturaTestata p_class  )
		{
			OrariAperturaTestata retVal = ( OrariAperturaTestata ) p_class.Clone();

			#region ii. Integrazione delle classi figlio con i dati della classe padre
			
			#region 1.	OrariTestata
			
			foreach( OrariApertura orari in retVal.OrariApertura )
			{
				orari.OA_FKIDTESTATA = retVal.ID;

				if ( IsStringEmpty( orari.IDCOMUNE ) )
					orari.IDCOMUNE = retVal.IDCOMUNE;
				else if ( orari.IDCOMUNE.ToUpper() != retVal.IDCOMUNE.ToUpper() )
					throw new Exceptions.IncongruentDataException("ORARIAPERTURA.IDCOMUNE diverso da ORARIAPERTURATESTATA.IDCOMUNE");
			}

			#endregion

			#endregion

			return retVal;
		}

		private void ChildInsert( OrariAperturaTestata p_class )
		{
			foreach( OrariApertura orari in p_class.OrariApertura )
			{
				OrariAperturaMgr pManager = new OrariAperturaMgr( this.db );
				pManager.Insert( orari );
			}
		}

        public void Delete(OrariAperturaTestata p_class)
        {
            VerificaRecordCollegati(p_class);

            EffettuaCancellazioneACascata(p_class);

            db.Delete(p_class);
        }

        private void VerificaRecordCollegati(OrariAperturaTestata cls)
        {
        }

        private void EffettuaCancellazioneACascata(OrariAperturaTestata cls)
        {
            #region ORARIAPERTURA
            OrariApertura oa = new OrariApertura();
            oa.IDCOMUNE = cls.IDCOMUNE;
            oa.OA_FKIDTESTATA = cls.ID;

            List<OrariApertura> lOrari = new OrariAperturaMgr(db).GetList(oa);
            foreach (OrariApertura orario in lOrari)
            {
                OrariAperturaMgr mgr = new OrariAperturaMgr(db);
                mgr.Delete(orario);
            }
            #endregion
        }

	}
}