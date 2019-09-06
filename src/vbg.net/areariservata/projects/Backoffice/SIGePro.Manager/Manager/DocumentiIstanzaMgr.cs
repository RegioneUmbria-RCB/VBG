using System;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.DocumentiIstanza;
using PersonalLib2.Data;
using Init.SIGePro.Validator;
using System.Collections.Generic;
using Init.Utils;

namespace Init.SIGePro.Manager
{ 	///<summary>
	/// Descrizione di riepilogo per DocumentiIstanzaMgr.\n	/// </summary>
	public class DocumentiIstanzaMgr : BaseManager
	{
		public DocumentiIstanzaMgr(DataBase dataBase) : base(dataBase) { }

		#region Metodi per l'accesso di base al DB

		public DocumentiIstanza GetById(String pCODICEISTANZA, String pCODICEDOCUMENTO, String pIDCOMUNE)
		{
			DocumentiIstanza retVal = new DocumentiIstanza();

			retVal.CODICEISTANZA = pCODICEISTANZA;
			retVal.CODICEDOCUMENTO = pCODICEDOCUMENTO;
			retVal.IDCOMUNE = pIDCOMUNE;

			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal, true, false);
			if (mydc.Count != 0)
				return (mydc[0]) as DocumentiIstanza;

			return null;
		}

		public DocumentiIstanza GetByClass(DocumentiIstanza pClass)
		{
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(pClass, true, false);
			if (mydc.Count != 0)
				return (mydc[0]) as DocumentiIstanza;

			return null;
		}

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public List<DocumentiIstanza> GetList(DocumentiIstanza p_class)
		{
			return this.GetList(p_class, null);
		}

		public List<DocumentiIstanza> GetList(DocumentiIstanza p_class, DocumentiIstanza p_cmpClass)
		{
			return db.GetClassList(p_class, p_cmpClass, false, false).ToList<DocumentiIstanza>();
		}

		public void Delete(DocumentiIstanza cls)
		{
			VerificaRecordCollegati(cls);

			EffettuaCancellazioneACascata(cls);

			db.Delete(cls);

			EliminaOggetto(cls);
		}

		private void EffettuaCancellazioneACascata(DocumentiIstanza cls)
		{
		}

		private void EliminaOggetto(DocumentiIstanza cls)
		{
			if (!String.IsNullOrEmpty(cls.CODICEOGGETTO))
				new OggettiMgr(db).EliminaOggetto(cls.IDCOMUNE, Convert.ToInt32(cls.CODICEOGGETTO));
		}


		private void VerificaRecordCollegati(DocumentiIstanza cls)
		{

		}


		public DocumentiIstanza Insert(DocumentiIstanza p_class)
		{

			//p_class = DataIntegrations(p_class);

			Validate(p_class, AmbitoValidazione.Insert);

			db.Insert(p_class);

			return p_class;
		}

		public DocumentiIstanza Update(DocumentiIstanza p_class)
		{
			Validate(p_class, AmbitoValidazione.Update);

			db.Update(p_class);

			return p_class;
		}
		/*
		private DocumentiIstanza DataIntegrations(DocumentiIstanza p_class)
		{
			DocumentiIstanza retVal = (DocumentiIstanza)p_class.Clone();

			if (String.IsNullOrEmpty(retVal.CODICEDOCUMENTO) && retVal.Oggetto != null)
			{
				if (String.IsNullOrEmpty(retVal.Oggetto.IDCOMUNE))
					retVal.Oggetto.IDCOMUNE = retVal.IDCOMUNE;
				else if (retVal.Oggetto.IDCOMUNE.ToUpper() != retVal.IDCOMUNE.ToUpper())
					throw new IncongruentDataException(p_class, "DOCUMENTIISTANZA.OGGETTI.IDCOMUNE diverso da DOCUMENTIISTANZA.IDCOMUNE");

				OggettiMgr oggettiMgr = new OggettiMgr(db);
				retVal.CODICEOGGETTO = oggettiMgr.Insert(retVal.Oggetto).CODICEOGGETTO;
			}

			if (String.IsNullOrEmpty(retVal.CODICEDOCUMENTO))
			{
				var filtro = new List<KeyValuePair<string, object>>();

				filtro.Add(new KeyValuePair<string, object>("CODICEISTANZA", retVal.CODICEISTANZA));

				retVal.CODICEDOCUMENTO = FindMax("CODICEDOCUMENTO", "DOCUMENTIISTANZA", retVal.IDCOMUNE, filtro).ToString();
			}

			if (!string.IsNullOrEmpty(retVal.CODICEOGGETTO))
				retVal.PRESENTE = "1";

			return retVal;
		}
		*/
		#region BeforeInsert
		private void Validate(DocumentiIstanza p_class, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate(p_class, ambitoValidazione);

			if (IsStringEmpty(p_class.NECESSARIO))
				p_class.NECESSARIO = "0";

			if (IsStringEmpty(p_class.PRESENTE))
				p_class.PRESENTE = "0";

			if (p_class.NECESSARIO != "0" && p_class.NECESSARIO != "1")
				throw (new TypeMismatchException(p_class, "Impossibile inserire" + p_class.NECESSARIO + " in DOCUMENTIISTANZA.NECESSARIO"));

			if (p_class.PRESENTE != "0" && p_class.PRESENTE != "1")
				throw (new TypeMismatchException(p_class, "Impossibile inserire" + p_class.PRESENTE + " in DOCUMENTIISTANZA.PRESENTE"));

			ForeignValidate(p_class);
		}

		private void ForeignValidate(DocumentiIstanza p_class)
		{
			#region DOCUMENTIISTANZA.CODICEISTANZA
			if (!IsStringEmpty(p_class.CODICEISTANZA))
			{
				if (this.recordCount("ISTANZE", "CODICEISTANZA", "WHERE CODICEISTANZA = '" + p_class.CODICEISTANZA + "' AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0)
				{
					throw (new RecordNotfoundException(p_class, "DOCUMENTIISTANZA.CODICEISTANZA non trovato nella tabella ISTANZE"));
				}
			}
			#endregion

			#region DOCUMENTIISTANZA.CODICEOGGETTO
			if (!IsStringEmpty(p_class.CODICEOGGETTO))
			{
				if (this.recordCount("OGGETTI", "CODICEOGGETTO", "WHERE CODICEOGGETTO = '" + p_class.CODICEOGGETTO + "' AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0)
				{
					throw (new RecordNotfoundException(p_class, "DOCUMENTIISTANZA.CODICEOGGETTO non trovato nella tabella OGGETTI"));
				}
			}
			#endregion
		}

		#endregion


		#endregion



        internal IEnumerable<DocumentiIstanza> GetListDocumentiSostituibili(string idComune, int codiceIstanza, bool sostituisciFilesNonValidi, bool sostituisciFilesnonVerificati)
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
                  * 
                FROM 
                  documentiistanza 
                WHERE 
                  idcomune={0} AND 
                  codiceistanza={1} and
                  codiceoggetto is not null and
                  (flg_da_modello_dinamico is null OR flg_da_modello_dinamico=0 OR flg_da_modello_dinamico='') and " + filtroControllo + @"
                ORDER BY documento asc", "idcomune", "codiceistanza");

            return ExecuteInConnection(() =>
            {
                using (var cmd = db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(db.CreateParameter("idcomune", idComune));
                    cmd.Parameters.Add(db.CreateParameter("codiceistanza", codiceIstanza));

                    return db.GetClassList<DocumentiIstanza>(cmd);
                }
            });
                                       
        }
    }
}