using System;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.DynDati;
using PersonalLib2.Data;
using Init.SIGePro.Validator;
using System.Collections.Generic;

namespace Init.SIGePro.Manager 
{ 	///<summary>
	/// Descrizione di riepilogo per DynDatiMgr.\n	/// </summary>
	public class DynDatiMgr: BaseManager
	{

		public DynDatiMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public DynDati GetById(String pCODICERECORDCORRELATO, String pCODICECAMPO, String pFKIDTESTATA, String pIDCOMUNE)
		{
			DynDati retVal = new DynDati();
			
			retVal.CODICERECORDCORRELATO = pCODICERECORDCORRELATO;
			retVal.CODICECAMPO = pCODICECAMPO;
			retVal.FKIDTESTATA = pFKIDTESTATA;
			retVal.IDCOMUNE = pIDCOMUNE;

			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as DynDati;
			
			return null;
		}

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<DynDati> GetList(DynDati p_class)
		{
			return this.GetList(p_class,null);
		}

        public List<DynDati> GetList(DynDati p_class, DynDati p_cmpClass)
		{
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<DynDati>();
		}

		public void Delete(DynDati p_class)
		{	
			db.Delete( p_class) ;
		}

		public DynDati Insert( DynDati p_class )
		{

			Validate(p_class, AmbitoValidazione.Insert);

			db.Insert( p_class ) ;

			return p_class;

		}

		public DynDati Update( DynDati p_class )
		{
			db.Update( p_class ) ;

			return p_class;

		}

		private void Validate(DynDati p_class, Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( p_class ,ambitoValidazione);

			ForeignValidate( p_class );
		}

		private void ForeignValidate ( DynDati p_class )
		{
			#region DYN_DATI.FKIDTESTATA
			if ( ! IsStringEmpty( p_class.FKIDTESTATA ) )
			{
				if (  this.recordCount( "DYN_TMODELLI","IDMODELLO","WHERE IDMODELLO = " + p_class.FKIDTESTATA + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException( p_class, "DYN_DATI.FKIDTESTATA non trovato nella tabella DYN_TMODELLI"));
				}
			}
			#endregion

			#region DYN_DATI.CODICECAMPO
			if ( ! IsStringEmpty( p_class.CODICECAMPO ) )
			{
				if (  this.recordCount( "DYN_CAMPI","CODICE","WHERE CODICE = " + p_class.CODICECAMPO + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException(p_class, "DYN_DATI.CODICECAMPO non trovato nella tabella DYN_CAMPI"));
				}
			}
			#endregion
		}

		#endregion


	}
}