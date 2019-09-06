using System;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.IstanzeAllegati;
using PersonalLib2.Data;
using Init.SIGePro.Validator;
using System.Collections.Generic;
using System.Text;

namespace Init.SIGePro.Manager 
{ 	
	public class IstanzeAllegatiMgr: BaseManager
	{

		public IstanzeAllegatiMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public IstanzeAllegati GetById(String pCODICEINVENTARIO, String pNUMEROALLEGATO, String pCODICEISTANZA, String pIDCOMUNE)
		{
			IstanzeAllegati retVal = new IstanzeAllegati();
			
			retVal.CODICEINVENTARIO = pCODICEINVENTARIO;
			retVal.NUMEROALLEGATO = pNUMEROALLEGATO;
			retVal.CODICEISTANZA = pCODICEISTANZA;
			retVal.IDCOMUNE = pIDCOMUNE;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as IstanzeAllegati;
			
			return null;
		}

        public IstanzeAllegati GetByClass(IstanzeAllegati pClass)
        {
            PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(pClass, true, false);
            if (mydc.Count != 0)
                return (mydc[0]) as IstanzeAllegati;

            return null;
        }

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<IstanzeAllegati> GetList(IstanzeAllegati p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public List<IstanzeAllegati> GetList(IstanzeAllegati p_class, IstanzeAllegati p_cmpClass )
		{
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList < IstanzeAllegati>();
		}

        public void Delete(IstanzeAllegati cls)
        {
			VerificaRecordCollegati(cls);

			EffettuaCancellazioneACascata(cls);

			db.Delete(cls);

			EliminaOggetto(cls);
        }

		private void EliminaOggetto(IstanzeAllegati cls)
		{
			if(!String.IsNullOrEmpty(cls.CODICEOGGETTO ))
				new OggettiMgr(db).EliminaOggetto(cls.IDCOMUNE, Convert.ToInt32(cls.CODICEOGGETTO));
		}

		private void EffettuaCancellazioneACascata(IstanzeAllegati cls)
		{
		}

        private void VerificaRecordCollegati(IstanzeAllegati cls)
        { 
            
        }

		

		public IstanzeAllegati Insert( IstanzeAllegati p_class )
		{

			Validate(p_class, AmbitoValidazione.Insert);

			db.Insert( p_class ) ;

			p_class = ChildDataIntegrations( p_class );

			ChildInsert( p_class );

			return p_class;
		}

		public IstanzeAllegati Update( IstanzeAllegati p_class )
		{

			db.Update( p_class ) ;

			return p_class;
		}
		
		private void ChildInsert(IstanzeAllegati p_class)
		{
			/*
			if (p_class.Oggetto != null && p_class.Oggetto.OGGETTO != null)
			{
				OggettiMgr oggettiMgr = new OggettiMgr(db);
				oggettiMgr.Insert(p_class.Oggetto);
			}
			*/
		}
		#region BeforeInsert
		private IstanzeAllegati ChildDataIntegrations( IstanzeAllegati p_class )
		{
			IstanzeAllegati retVal = ( IstanzeAllegati ) p_class.Clone();

			if ( string.IsNullOrEmpty( retVal.CODICEOGGETTO ) && retVal.Oggetto != null )
			{
				if ( IsStringEmpty( retVal.Oggetto.IDCOMUNE ) )
					retVal.Oggetto.IDCOMUNE = retVal.IDCOMUNE;
				else if ( retVal.Oggetto.IDCOMUNE.ToUpper() != retVal.IDCOMUNE.ToUpper() )
						throw new IncongruentDataException( p_class, "ISTANZEALLEGATI.OGGETTO.IDCOMUNE diverso da ISTANZEALLEGATI.IDCOMUNE");
			}


			return retVal;
		}

		private void Validate(IstanzeAllegati p_class, Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
		{
			if ( IsStringEmpty( p_class.CODICEINVENTARIO ))
				throw( new RequiredFieldException( p_class, "ISTANZEALLEGATI.CODICEINVENTARIO non presente") );

			if ( IsStringEmpty( p_class.VERIFICATO ) )
				p_class.VERIFICATO = "0";

			if ( IsStringEmpty( p_class.CONTROLLOOK ) )
				p_class.CONTROLLOOK = "0";

			if ( IsStringEmpty( p_class.PRESENTE ) )
				p_class.PRESENTE = "0";

			if ( IsStringEmpty( p_class.SEENDO ) )
				p_class.SEENDO = "0";

			if ( IsStringEmpty( p_class.SELEZIONATO ) )
				p_class.SELEZIONATO = "0";

			if ( p_class.VERIFICATO != "0" && p_class.VERIFICATO != "1" )
				throw( new TypeMismatchException( p_class, "Impossibile inserire" + p_class.VERIFICATO + " in ISTANZEALLEGATI.VERIFICATO") );

			if ( p_class.CONTROLLOOK != "0" && p_class.CONTROLLOOK != "1" )
				throw( new TypeMismatchException( p_class,  "Impossibile inserire" + p_class.CONTROLLOOK + " in ISTANZEALLEGATI.CONTROLLOOK") );

			if ( p_class.PRESENTE != "0" && p_class.PRESENTE != "1" )
				throw( new TypeMismatchException( p_class, "Impossibile inserire" + p_class.PRESENTE + " in ISTANZEALLEGATI.PRESENTE") );

			if ( p_class.SEENDO != "0" && p_class.SEENDO != "1" )
				throw( new TypeMismatchException( p_class, "Impossibile inserire" + p_class.SEENDO + " in ISTANZEALLEGATI.SEENDO") );

			if ( p_class.SELEZIONATO != "0" && p_class.SELEZIONATO != "1" )
				throw( new TypeMismatchException( p_class, "Impossibile inserire" + p_class.SELEZIONATO + " in ISTANZEALLEGATI.SELEZIONATO") );

			RequiredFieldValidate( p_class ,ambitoValidazione);

			ForeignValidate( p_class );

		}
		private void ForeignValidate ( IstanzeAllegati p_class )
		{
			#region ISTANZEALLEGATI.CODICEISTANZA
			if ( ! IsStringEmpty( p_class.CODICEISTANZA ) )
			{
				if (  this.recordCount( "ISTANZE","CODICEISTANZA","WHERE CODICEISTANZA = " + p_class.CODICEISTANZA + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException( p_class, "ISTANZEALLEGATI.CODICEISTANZA non trovato nella tabella ISTANZE"));
				}
			}
			#endregion

			#region ISTANZEALLEGATI.CODICEINVENTARIO
			if ( ! IsStringEmpty( p_class.CODICEINVENTARIO ) )
			{
				if (  this.recordCount( "INVENTARIOPROCEDIMENTI","CODICEINVENTARIO","WHERE CODICEINVENTARIO = " + p_class.CODICEINVENTARIO + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException( p_class, "ISTANZEALLEGATI.CODICEINVENTARIO non trovato nella tabella INVENTARIOPROCEDIMENTI"));
				}
			}
			#endregion

			#region ISTANZEALLEGATI.CODICEOGGETTO
			if ( ! IsStringEmpty( p_class.CODICEOGGETTO ) )
			{
				if ( this.recordCount("OGGETTI","CODICEOGGETTO","WHERE CODICEOGGETTO = " + p_class.CODICEOGGETTO + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'" ) == 0 )
				{
					throw( new RecordNotfoundException( p_class, "ISTANZEALLEGATI.CODICEOGGETTO non trovato nella tabella OGGETTI"));
				}
			}
			#endregion
		}
		#endregion
		
		#endregion

        internal Dictionary<string, List<IstanzeAllegati>> GetListDocumentiSostituibili(string idComune, int codiceIstanza, bool sostituisciFilesNonValidi, bool sostituisciFilesnonVerificati)
        {
            var condizioni = new List<string>();

            if (sostituisciFilesNonValidi)
            {
                condizioni.Add("controllook=0");
            }

            if (sostituisciFilesnonVerificati)
            {
                condizioni.Add("controllook is null");
                condizioni.Add("controllook = ''");
            }

            var filtroControllo = String.Format("({0})", String.Join(" OR ", condizioni.ToArray()));

            var sql = PreparaQueryParametrica(
                @"SELECT 
                  istanzeallegati.* 
                FROM 
                  istanzeallegati 
                WHERE 
                    idcomune={0} AND 
                    codiceistanza={1} and
                    codiceoggetto is not null and " + filtroControllo + @"
                ORDER BY allegatoextra asc", "idcomune", "codiceistanza");

            return ExecuteInConnection(() =>
            {
                var listaAllegati = new List<IstanzeAllegati>();
                var allegatiMgr = new InventarioProcedimentiMgr(this.db);
                var rVal = new Dictionary<string, List<IstanzeAllegati>>();

                using (var cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(db.CreateParameter("idcomune", idComune));
                    cmd.Parameters.Add(db.CreateParameter("codiceistanza", codiceIstanza));

                    listaAllegati = db.GetClassList<IstanzeAllegati>(cmd);
                }

                foreach(var allegato in listaAllegati)
                {
                    var endo = allegatiMgr.GetById(allegato.IDCOMUNE, Convert.ToInt32(allegato.CODICEINVENTARIO));
                    var nomeEndo = endo.Procedimento;
                    List<IstanzeAllegati>  lst = null;

                    if (!rVal.TryGetValue(nomeEndo, out lst))
                    {
                        lst = new List<IstanzeAllegati>();

                        rVal.Add(nomeEndo, lst);
                    }

                    lst.Add(allegato);
                }

                return rVal;
            });
                
        }
    }
}