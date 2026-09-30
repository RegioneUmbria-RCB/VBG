using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    public class MovimentiAllegatiMgr : BaseManager
    {
        public MovimentiAllegatiMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public MovimentiAllegati GetById(String pIDCOMUNE, String pIDALLEGATO, String pCODICEMOVIMENTO)
        {
            MovimentiAllegati retVal = new MovimentiAllegati();
            retVal.IDCOMUNE = pIDCOMUNE;
            retVal.IDALLEGATO = pIDALLEGATO;
            retVal.CODICEMOVIMENTO = pCODICEMOVIMENTO;

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }



        /// <summary>
        /// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
        /// </summary>
        /// <param name="p_class">Criteri di ricerca</param>
        /// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<MovimentiAllegati> GetList(MovimentiAllegati p_class)
        {
            return this.db.GetClassList(p_class).ToList<MovimentiAllegati>();
        }

        public void Delete(MovimentiAllegati cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);

            this.EliminaOggetto(cls);
        }

        private void EliminaOggetto(MovimentiAllegati cls)
        {
            if (!String.IsNullOrEmpty(cls.CODICEOGGETTO))
                new OggettiMgr(this.db).EliminaOggetto(cls.IDCOMUNE, Convert.ToInt32(cls.CODICEOGGETTO));
        }

        private void EffettuaCancellazioneACascata(MovimentiAllegati cls)
        {
        }

        private void VerificaRecordCollegati(MovimentiAllegati cls)
        {

        }

        public MovimentiAllegati Insert(MovimentiAllegati p_class)
        {
            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            p_class = this.ChildDataIntegrations(p_class);

            //			ChildInsert( p_class );

            return p_class;
        }

        public MovimentiAllegati Update(MovimentiAllegati p_class)
        {
            this.db.Update(p_class);

            return p_class;
        }
        private MovimentiAllegati ChildDataIntegrations(MovimentiAllegati p_class)
        {
            MovimentiAllegati retVal = (MovimentiAllegati)p_class.Clone();

            if (String.IsNullOrEmpty(retVal.CODICEOGGETTO) && retVal.Oggetto != null)
            {
                if (String.IsNullOrEmpty(retVal.Oggetto.IDCOMUNE))
                {
                    retVal.Oggetto.IDCOMUNE = retVal.IDCOMUNE;
                }
                else
                {
                    if (retVal.Oggetto.IDCOMUNE != retVal.IDCOMUNE)
                        throw (new IncongruentDataException("MOVIMENTIALLEGATI.IDCOMUNE è diverso da MOVIMENTIALLEGATI.OGGETTI.IDCOMUNE"));
                }
            }

            return retVal;
        }


        private void Validate(MovimentiAllegati p_class, AmbitoValidazione ambitoValidazione)
        {
            throw new NotImplementedException();
            /*
			 * Gestito da JAVA
			 * 
			*/
        }



        #endregion
    }
}