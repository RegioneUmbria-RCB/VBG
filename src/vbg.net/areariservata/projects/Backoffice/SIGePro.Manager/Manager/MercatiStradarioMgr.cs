using System;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using PersonalLib2.Data;
using PersonalLib2.Sql.Collections;
using Init.SIGePro.Validator;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{ 	///<summary>
    /// Descrizione di riepilogo per IstanzeStradarioMgr.\n	/// </summary>
    public class MercatiStradarioMgr : BaseManager
    {

        public MercatiStradarioMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public MercatiStradario GetById(int pFKCODICEMERCATO, String pFKCODICESTRADARIO, String pIDCOMUNE)
        {
            MercatiStradario retVal = new MercatiStradario();
            retVal.FKCODICEMERCATO = pFKCODICEMERCATO;
            retVal.FKCODICESTRADARIO = pFKCODICESTRADARIO;
            retVal.IDCOMUNE = pIDCOMUNE;

            DataClassCollection mydc = db.GetClassList(retVal, true, false);
            if (mydc.Count != 0)
                return (mydc[0]) as MercatiStradario;

            return null;
        }



        /// <summary>
        /// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
        /// </summary>
        /// <param name="p_class">Criteri di ricerca</param>
        /// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<MercatiStradario> GetList(MercatiStradario p_class)
        {
            return this.GetList(p_class, null);
        }

        public List<MercatiStradario> GetList(MercatiStradario p_class, MercatiStradario p_cmpClass)
        {
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<MercatiStradario>();
        }


        public void Delete(MercatiStradario p_class)
        {
            db.Delete(p_class);
        }

        public MercatiStradario Insert(MercatiStradario p_class)
        {

            p_class = DataIntegrations(p_class);

            Validate(p_class, AmbitoValidazione.Insert);

            db.Insert(p_class);

            return p_class;
        }

        public MercatiStradario Update(MercatiStradario p_class)
        {
            db.Update(p_class);
            return p_class;
        }


        private MercatiStradario DataIntegrations(MercatiStradario p_class)
        {
            MercatiStradario retVal = (MercatiStradario)p_class.Clone();

            if (IsStringEmpty(retVal.FKCODICESTRADARIO) && retVal.Stradario != null)
            {
                if (IsStringEmpty(retVal.Stradario.IDCOMUNE))
                    retVal.Stradario.IDCOMUNE = retVal.IDCOMUNE;
                else if (retVal.Stradario.IDCOMUNE.ToUpper() != retVal.IDCOMUNE.ToUpper())
                    throw new IncongruentDataException("STRADARIO.IDCOMUNE diverso da MERCATISTRADARIO.IDCOMUNE");


                StradarioMgr pStradarioMgr = new StradarioMgr(this.db);
                Stradario pStradario = pStradarioMgr.Extract(retVal.Stradario);

                if (pStradario != null)
                    retVal.FKCODICESTRADARIO = pStradario.CODICESTRADARIO;
            }

            return retVal;
        }

        private void Validate(MercatiStradario p_class, Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(p_class, ambitoValidazione);

            ForeignValidate(p_class);
        }

        private void ForeignValidate(MercatiStradario p_class)
        {
            #region MERCATISTRADARIO.FKCODICEMERCATO
            if (p_class.FKCODICEMERCATO.GetValueOrDefault(int.MinValue) > int.MinValue)
            {
                if (this.recordCount("MERCATI", "CODICEMERCATO", "WHERE CODICEMERCATO = " + p_class.FKCODICEMERCATO.ToString() + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0)
                {
                    throw (new RecordNotfoundException("MERCATISTRADARIO.FKCODICEMERCATO " + p_class.FKCODICEMERCATO.ToString() + " non trovato nella tabella MERCATI"));
                }
            }
            #endregion

            #region MERCATISTRADARIO.CODICESTRADARIO
            if (!IsStringEmpty(p_class.FKCODICESTRADARIO))
            {
                if (this.recordCount("STRADARIO", "CODICESTRADARIO", "WHERE CODICESTRADARIO = " + p_class.FKCODICESTRADARIO + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0)
                {
                    throw (new RecordNotfoundException("MERCATISTRADARIO.FKCODICESTRADARIO non trovato nella tabella STRADARIO"));
                }
            }
            #endregion
        }

        #endregion
    }
}