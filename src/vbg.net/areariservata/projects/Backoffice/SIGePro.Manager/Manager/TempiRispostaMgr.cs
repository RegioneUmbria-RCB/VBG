using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using Init.SIGePro.Exceptions;
using Init.Utils;
using Init.SIGePro.Validator;

namespace Init.SIGePro.Manager 
 { 	///<summary>
	/// Descrizione di riepilogo per TempiRispostaMgr.\n	/// </summary>
	public class TempiRispostaMgr: BaseManager
{

		public TempiRispostaMgr( DataBase dataBase ) : base( dataBase ) {}


		#region Metodi per l'accesso di base al DB


		public TempiRisposta GetById(String pCODICEPROCEDURA, String pCODICEAMMINISTRAZIONE, String pTIPOMOVIMENTO, String pTIPOCONTROMOVIMENTO, String pIDCOMUNE)
		{
			TempiRisposta retVal = new TempiRisposta();
			retVal.CODICEPROCEDURA = pCODICEPROCEDURA;
			retVal.CODICEAMMINISTRAZIONE = pCODICEAMMINISTRAZIONE;
			retVal.TIPOMOVIMENTO = pTIPOMOVIMENTO;
			retVal.TIPOCONTROMOVIMENTO = pTIPOCONTROMOVIMENTO;
			retVal.IDCOMUNE = pIDCOMUNE;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as TempiRisposta;
			
			return null;
		}

 
		private TempiRisposta DataIntegrations ( TempiRisposta p_class )
		{
			TempiRisposta retVal = ( TempiRisposta ) p_class.Clone();

			if ( StringChecker.IsStringEmpty( retVal.ATTESA ) )
				retVal.ATTESA = "0";

			if ( StringChecker.IsStringEmpty( retVal.CALCOLADAINIZIOISTANZA ) )
				retVal.CALCOLADAINIZIOISTANZA = "0";

			return retVal;
		}


		private void Validate( TempiRisposta p_class , Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
		{
			if ( p_class.CALCOLADAINIZIOISTANZA != "0" && p_class.CALCOLADAINIZIOISTANZA != "1" )
				throw new IncongruentDataException("TEMPIRISPOSTA.CALCOLADAINIZIOISTANZA = " + p_class.CALCOLADAINIZIOISTANZA);

			RequiredFieldValidate( p_class , ambitoValidazione);

			//ForeignValidate( p_class );
		}


		private void ForeignValidate ( TempiRisposta p_class )
		{
			#region TEMPIRISPOSTA.CODICEPROCEDURA
			if ( ! IsStringEmpty( p_class.CODICEPROCEDURA ) )
			{
				if ( this.recordCount("TIPIPROCEDURE","CODICEPROCEDURA","WHERE IDCOMUNE = '" + p_class.IDCOMUNE + "' AND CODICEPROCEDURA = " + p_class.CODICEPROCEDURA ) == 0 )
				{
					throw( new RecordNotfoundException("TEMPIRISPOSTA.CODICEPROCEDURA (" + p_class.CODICEPROCEDURA + ") non trovato nella tabella TIPIPROCEDURE"));
				}
			}
			#endregion

			#region TEMPIRISPOSTA.CODICEAMMINISTRAZIONE
			if ( ! IsStringEmpty( p_class.CODICEAMMINISTRAZIONE ) )
			{
				if ( this.recordCount("AMMINISTRAZIONI","CODICEAMMINISTRAZIONE","WHERE IDCOMUNE = '" + p_class.IDCOMUNE + "' AND CODICEAMMINISTRAZIONE = " + p_class.CODICEAMMINISTRAZIONE ) == 0 )
				{
					throw( new RecordNotfoundException("TEMPIRISPOSTA.CODICEAMMINISTRAZIONE (" + p_class.CODICEAMMINISTRAZIONE + ") non trovato nella tabella AMMINISTRAZIONI"));
				}
			}
			#endregion

			#region TEMPIRISPOSTA.TIPOMOVIMENTO
			if ( ! IsStringEmpty( p_class.TIPOMOVIMENTO ) )
			{
				if ( this.recordCount("TIPIMOVIMENTO","TIPOMOVIMENTO","WHERE IDCOMUNE = '" + p_class.IDCOMUNE + "' AND TIPOMOVIMENTO = '" + p_class.TIPOMOVIMENTO + "'" ) == 0 )
				{
					throw( new RecordNotfoundException("TEMPIRISPOSTA.TIPOMOVIMENTO (" + p_class.TIPOMOVIMENTO + ") non trovato nella tabella TIPIMOVIMENTO"));
				}
			}
			#endregion

			#region TEMPIRISPOSTA.TIPOCONTROMOVIMENTO
			if ( ! IsStringEmpty( p_class.TIPOCONTROMOVIMENTO ) )
			{
				if ( this.recordCount("TIPIMOVIMENTO","TIPOMOVIMENTO","WHERE IDCOMUNE = '" + p_class.IDCOMUNE + "' AND TIPOMOVIMENTO = '" + p_class.TIPOCONTROMOVIMENTO + "'" ) == 0 )
				{
					throw( new RecordNotfoundException("TEMPIRISPOSTA.TIPOCONTROMOVIMENTO (" + p_class.TIPOCONTROMOVIMENTO + ") non trovato nella tabella TIPIMOVIMENTO"));
				}
			}
			#endregion
		}


		public TempiRisposta Insert( TempiRisposta p_class )
		{
			p_class = DataIntegrations( p_class );

			Validate(p_class, AmbitoValidazione.Insert);
			
			db.Insert( p_class );

			return p_class;
		}


		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(TempiRisposta p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(TempiRisposta p_class, TempiRisposta p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}


		#endregion
	}
}
