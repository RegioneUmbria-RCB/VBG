using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.DocumentiIstanza;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
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

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }

        public DocumentiIstanza GetByClass(DocumentiIstanza pClass)
        {
            var mydc = this.db.GetClassList(pClass, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }

        /// <summary>
        /// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
        /// </summary>
        /// <param name="p_class">Criteri di ricerca</param>
        /// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<DocumentiIstanza> GetList(DocumentiIstanza p_class)
        {
            return this.db.GetClassList(p_class);
        }

        public void Delete(DocumentiIstanza cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);

            this.EliminaOggetto(cls);
        }

        private void EffettuaCancellazioneACascata(DocumentiIstanza cls)
        {
        }

        private void EliminaOggetto(DocumentiIstanza cls)
        {
            if (!String.IsNullOrEmpty(cls.CODICEOGGETTO))
                new OggettiMgr(this.db).EliminaOggetto(cls.IDCOMUNE, Convert.ToInt32(cls.CODICEOGGETTO));
        }


        private void VerificaRecordCollegati(DocumentiIstanza cls)
        {

        }


        public DocumentiIstanza Insert(DocumentiIstanza p_class)
        {

            //p_class = DataIntegrations(p_class);

            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            return p_class;
        }

        public DocumentiIstanza Update(DocumentiIstanza p_class)
        {
            this.Validate(p_class, AmbitoValidazione.Update);

            this.db.Update(p_class);

            return p_class;
        }

        #region BeforeInsert
        private void Validate(DocumentiIstanza p_class, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(p_class, ambitoValidazione);

            if (String.IsNullOrEmpty(p_class.NECESSARIO))
                p_class.NECESSARIO = "0";

            if (String.IsNullOrEmpty(p_class.PRESENTE))
                p_class.PRESENTE = "0";

            if (p_class.NECESSARIO != "0" && p_class.NECESSARIO != "1")
                throw (new TypeMismatchException(p_class, "Impossibile inserire" + p_class.NECESSARIO + " in DOCUMENTIISTANZA.NECESSARIO"));

            if (p_class.PRESENTE != "0" && p_class.PRESENTE != "1")
                throw (new TypeMismatchException(p_class, "Impossibile inserire" + p_class.PRESENTE + " in DOCUMENTIISTANZA.PRESENTE"));

            this.ForeignValidate(p_class);
        }

        private void ForeignValidate(DocumentiIstanza p_class)
        {
            #region DOCUMENTIISTANZA.CODICEISTANZA
            if (!String.IsNullOrEmpty(p_class.CODICEISTANZA))
            {
                var condizioniIstanze = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("CODICEISTANZA", p_class.CODICEISTANZA),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };

                if (this.recordCount("ISTANZE", "CODICEISTANZA", condizioniIstanze) == 0)
                {
                    throw (new RecordNotfoundException(p_class, "DOCUMENTIISTANZA.CODICEISTANZA non trovato nella tabella ISTANZE"));
                }
            }
            #endregion

            #region DOCUMENTIISTANZA.CODICEOGGETTO
            if (!String.IsNullOrEmpty(p_class.CODICEOGGETTO))
            {
                var condizioniOggetti = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("CODICEOGGETTO", p_class.CODICEOGGETTO),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };

                if (this.recordCount("OGGETTI", "CODICEOGGETTO", condizioniOggetti) == 0)
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

            var sql = this.PreparaQueryParametrica(
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

            return this.ExecuteInConnection(() =>
            {
                using (var cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idcomune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("codiceistanza", codiceIstanza));

                    return this.db.GetClassList<DocumentiIstanza>(cmd);
                }
            });

        }

        public bool VerificaPresenzaDocumentiSenzaCodiceOggetto(string idComune, string codiceIstanza)
        {
            var d = new DocumentiIstanza
            {
                IDCOMUNE = idComune,
                CODICEISTANZA = codiceIstanza
            };
            d.OthersWhereClause.Add("CODICEOGGETTO IS NULL");

            var lista = this.GetList(d);

            return (lista == null || lista.Count > 0);
        }
    }
}