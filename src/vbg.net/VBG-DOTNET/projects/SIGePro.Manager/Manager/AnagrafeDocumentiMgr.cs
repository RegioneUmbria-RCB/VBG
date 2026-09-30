using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.AnagrafeDocumenti;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;

namespace Init.SIGePro.Manager
{
    public class AnagrafeDocumentiMgr : BaseManager
    {

        public AnagrafeDocumentiMgr(DataBase dataBase) : base(dataBase) { }


        public AnagrafeDocumenti GetById(String pID, String pIDCOMUNE)
        {
            AnagrafeDocumenti retVal = new AnagrafeDocumenti();
            retVal.ID = pID;
            retVal.IDCOMUNE = pIDCOMUNE;

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }

        /// <summary>
        /// Effettua l'eliminazione dell'oggetto dal database
        /// </summary>
        /// <param name="p_class">L'oggetto da eliminare</param>
        public void Delete(AnagrafeDocumenti p_class)
        {
            this.db.Delete(p_class);
        }


        public AnagrafeDocumenti Insert(AnagrafeDocumenti p_class)
        {
            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            p_class = this.ChildDataIntegrations(p_class);

            // this.ChildInsert(p_class);

            return p_class;
        }

        public AnagrafeDocumenti Update(AnagrafeDocumenti p_class)
        {

            this.db.Update(p_class);

            return p_class;
        }


        private AnagrafeDocumenti ChildDataIntegrations(AnagrafeDocumenti retVal)
        {

            if (String.IsNullOrEmpty(retVal.CODICEOGGETTO) && retVal.Oggetto != null)
            {
                if (String.IsNullOrEmpty(retVal.Oggetto.IDCOMUNE))
                {
                    retVal.Oggetto.IDCOMUNE = retVal.IDCOMUNE;
                }
                else
                {
                    if (retVal.Oggetto.IDCOMUNE.ToUpper() != retVal.IDCOMUNE.ToUpper())
                        throw new IncongruentDataException(retVal, "ANAGRAFEDOCUMENTI.OGGETTI.IDCOMUNE diverso da ANAGRAFEDOCUMENTI.IDCOMUNE");
                }
            }

            return retVal;
        }
        /*
        private void ChildInsert(AnagrafeDocumenti p_class)
        {
            if (p_class.Oggetto != null)
            {
                OggettiMgr oggettiMgr = new OggettiMgr(this.db);
                oggettiMgr.Insert(p_class.Oggetto);
            }
        }
        */

        private void Validate(AnagrafeDocumenti p_class, Init.SIGePro.Manager.Validator.AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(p_class, ambitoValidazione);

            this.ForeignValidate(p_class);
        }

        private void ForeignValidate(AnagrafeDocumenti p_class)
        {
            #region ANAGRAFEDOCUMENTI.CODICEANAGRAFE
            if (!String.IsNullOrEmpty(p_class.CODICEANAGRAFE))
            {
                var condizioniAnagrafe = new System.Collections.Generic.List<System.Collections.Generic.KeyValuePair<string, string>>
                {
                    new System.Collections.Generic.KeyValuePair<string, string>("CODICEANAGRAFE", p_class.CODICEANAGRAFE),
                    new System.Collections.Generic.KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };
                if (this.recordCount("ANAGRAFE", "CODICEANAGRAFE", condizioniAnagrafe) == 0)
                {
                    throw (new RecordNotfoundException(p_class, "ANAGRAFEDOCUMENTI.CODICEANAGRAFE non trovato nella tabella ANAGRAFE"));
                }
            }
            #endregion

            #region ANAGRAFEDOCUMENTI.IDTIPODOCUMENTO
            if (!String.IsNullOrEmpty(p_class.IDTIPODOCUMENTO))
            {
                var condizioniTipoDocumento = new System.Collections.Generic.List<System.Collections.Generic.KeyValuePair<string, string>>
                {
                    new System.Collections.Generic.KeyValuePair<string, string>("IDTIPODOCUMENTO", p_class.IDTIPODOCUMENTO),
                    new System.Collections.Generic.KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };
                if (this.recordCount("TIPIDOCUMENTO", "IDTIPODOCUMENTO", condizioniTipoDocumento) == 0)
                {
                    throw (new RecordNotfoundException(p_class, "ANAGRAFEDOCUMENTI.IDTIPODOCUMENTO non trovato nella tabella TIPIDOCUMENTO"));
                }
            }
            #endregion

            #region ANAGRAFEDOCUMENTI.CODICEISTANZA
            if (!String.IsNullOrEmpty(p_class.CODICEISTANZA))
            {
                var condizioniIstanza = new System.Collections.Generic.List<System.Collections.Generic.KeyValuePair<string, string>>
                {
                    new System.Collections.Generic.KeyValuePair<string, string>("CODICEISTANZA", p_class.CODICEISTANZA),
                    new System.Collections.Generic.KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };
                if (this.recordCount("ISTANZE", "CODICEISTANZA", condizioniIstanza) == 0)
                {
                    throw (new RecordNotfoundException(p_class, "ANAGRAFEDOCUMENTI.CODICEISTANZA non trovato nella tabella ISTANZE"));
                }
            }
            #endregion

            #region ANAGRAFEDOCUMENTI.CODICEOGGETTO
            if (!String.IsNullOrEmpty(p_class.CODICEOGGETTO))
            {
                var condizioniOggetto = new System.Collections.Generic.List<System.Collections.Generic.KeyValuePair<string, string>>
                {
                    new System.Collections.Generic.KeyValuePair<string, string>("CODICEOGGETTO", p_class.CODICEOGGETTO),
                    new System.Collections.Generic.KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };
                if (this.recordCount("OGGETTI", "CODICEOGGETTO", condizioniOggetto) == 0)
                {
                    throw (new RecordNotfoundException(p_class, "ANAGRAFEDOCUMENTI.CODICEOGGETTO non trovato nella tabella OGGETTI"));
                }
            }
            #endregion
        }
    }
}