using System;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using PersonalLib2.Data;
using PersonalLib2.Sql.Collections;
using Init.SIGePro.Validator;
using System.Collections.Generic;
using PersonalLib2.Sql;

namespace Init.SIGePro.Manager 
{ 	

	public class Mercati_DAttivitaIstatMgr: BaseManager
	{

		public Mercati_DAttivitaIstatMgr( DataBase dataBase ) : base( dataBase ) {}
		
		public Mercati_DAttivitaIstat GetById(int pFKCODICEMERCATO, int pFKIDPOSTEGGIO, String pFKCODICEATTIVITAISTAT, String pIDCOMUNE )
		{
			Mercati_DAttivitaIstat retVal = new Mercati_DAttivitaIstat();
			retVal.IDCOMUNE = pIDCOMUNE;
			retVal.FKCODICEMERCATO = pFKCODICEMERCATO;
			retVal.FKIDPOSTEGGIO= pFKIDPOSTEGGIO;
			retVal.FkCodiceAttivitaIstat = pFKCODICEATTIVITAISTAT;

			DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as Mercati_DAttivitaIstat;
			
			return null;
		}


        public List<Mercati_DAttivitaIstat> GetList(Mercati_DAttivitaIstat p_class, useForeignEnum foreignEnum)
        {
            p_class.UseForeign = foreignEnum;
            return this.GetList(p_class, null);
        }

        public List<Mercati_DAttivitaIstat> GetList(Mercati_DAttivitaIstat p_class)
		{
            return this.GetList(p_class, useForeignEnum.Yes);
		}

        public List<Mercati_DAttivitaIstat> GetList(Mercati_DAttivitaIstat p_class, Mercati_DAttivitaIstat p_cmpClass)
		{
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<Mercati_DAttivitaIstat>();
		}


		public void Delete(Mercati_DAttivitaIstat p_class)
		{
			db.Delete( p_class) ;
		}

		public Mercati_DAttivitaIstat Insert( Mercati_DAttivitaIstat p_class )
		{
			p_class = DataIntegrations( p_class );

			Validate(p_class, AmbitoValidazione.Insert);
			
			db.Insert( p_class );

			return p_class;
		}

		private Mercati_DAttivitaIstat DataIntegrations ( Mercati_DAttivitaIstat p_class )
		{
			Mercati_DAttivitaIstat retVal = ( Mercati_DAttivitaIstat ) p_class.Clone();

			if ( IsStringEmpty( retVal.IDCOMUNE ) )
				throw new RequiredFieldException("MERCATI_DATTIVITAISTAT.IDCOMUNE obbligatorio");

            if (retVal.FKCODICEMERCATO.GetValueOrDefault(int.MinValue) == int.MinValue)
				throw new RequiredFieldException("MERCATI_DATTIVITAISTAT.FKCODICEMERCATO obbligatorio");

            if (retVal.FKIDPOSTEGGIO.GetValueOrDefault(int.MinValue) == int.MinValue)
				throw new RequiredFieldException("MERCATI_DATTIVITAISTAT.FKIDPOSTEGGIO obbligatorio");

			if ( IsStringEmpty( retVal.FkCodiceAttivitaIstat ) )
				throw new RequiredFieldException("MERCATI_DATTIVITAISTAT.FKCODICEATTIVITAISTAT obbligatorio");

			if ( IsStringEmpty( retVal.Flag_Consentito ) )
				retVal.Flag_Consentito = "1";

			return retVal;
		}



		private void Validate(Mercati_DAttivitaIstat p_class, Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( p_class ,ambitoValidazione);

			ForeignValidate( p_class );
		}

		private void ForeignValidate ( Mercati_DAttivitaIstat p_class )
		{
			#region MERCATI_DATTIVITAISTAT.FKCODICEMERCATO
            if (p_class.FKCODICEMERCATO.GetValueOrDefault(int.MinValue) > int.MinValue)
			{
				if ( this.recordCount("MERCATI","CODICEMERCATO","WHERE CODICEMERCATO = " + p_class.FKCODICEMERCATO.ToString() + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
                    throw (new RecordNotfoundException("MERCATI_DATTIVITAISTAT.FKCODICEMERCATO (" + p_class.FKCODICEMERCATO.ToString() + ") non trovato nella tabella MERCATI"));
				}
			}
			#endregion

			#region MERCATI_DATTIVITAISTAT.FKIDPOSTEGGIO
            if (p_class.FKIDPOSTEGGIO.GetValueOrDefault(int.MinValue) > int.MinValue)
			{
				if ( this.recordCount("MERCATI_D","IDPOSTEGGIO","WHERE IDPOSTEGGIO = " + p_class.FKIDPOSTEGGIO.ToString() + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
                    throw (new RecordNotfoundException("MERCATI_DATTIVITAISTAT.FKIDPOSTEGGIO (" + p_class.FKIDPOSTEGGIO.ToString() + ") non trovato nella tabella MERCATI_D"));
				}
			}
			#endregion

			#region MERCATI_DATTIVITAISTAT.FKCODICEATTIVITAISTAT
			if ( ! IsStringEmpty( p_class.FkCodiceAttivitaIstat ) )
			{
				if ( this.recordCount("ATTIVITA","CODICEISTAT","WHERE CODICEISTAT = '" + p_class.FkCodiceAttivitaIstat + "' AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("MERCATI_DATTIVITAISTAT.FKCODICEATTIVITAISTAT (" + p_class.FkCodiceAttivitaIstat + ") non trovato nella tabella ATTIVITA"));
				}
			}
			#endregion
		}

	}
}