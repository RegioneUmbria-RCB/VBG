using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
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

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }



        public void Delete(MercatiStradario p_class)
        {
            this.db.Delete(p_class);
        }

        public MercatiStradario Insert(MercatiStradario p_class)
        {

            p_class = this.DataIntegrations(p_class);

            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            return p_class;
        }

        public MercatiStradario Update(MercatiStradario p_class)
        {
            this.db.Update(p_class);
            return p_class;
        }


        private MercatiStradario DataIntegrations(MercatiStradario p_class)
        {
            MercatiStradario retVal = (MercatiStradario)p_class.Clone();

            if (String.IsNullOrEmpty(retVal.FKCODICESTRADARIO) && retVal.Stradario != null)
            {
                if (String.IsNullOrEmpty(retVal.Stradario.IDCOMUNE))
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

        private void Validate(MercatiStradario p_class, Init.SIGePro.Manager.Validator.AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(p_class, ambitoValidazione);

            this.ForeignValidate(p_class);
        }

        private void ForeignValidate(MercatiStradario p_class)
        {
            #region MERCATISTRADARIO.FKCODICEMERCATO
            if (p_class.FKCODICEMERCATO.GetValueOrDefault(int.MinValue) > int.MinValue)
            {
                var mercatoConditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("CODICEMERCATO", p_class.FKCODICEMERCATO.ToString()),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };

                if (this.recordCount("MERCATI", "CODICEMERCATO", mercatoConditions) == 0)
                {
                    throw (new RecordNotfoundException("MERCATISTRADARIO.FKCODICEMERCATO " + p_class.FKCODICEMERCATO.ToString() + " non trovato nella tabella MERCATI"));
                }
            }
            #endregion

            #region MERCATISTRADARIO.CODICESTRADARIO
            if (!String.IsNullOrEmpty(p_class.FKCODICESTRADARIO))
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("CODICESTRADARIO", p_class.FKCODICESTRADARIO),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };

                if (this.recordCount("STRADARIO", "CODICESTRADARIO", conditions) == 0)
                {
                    throw (new RecordNotfoundException("MERCATISTRADARIO.FKCODICESTRADARIO non trovato nella tabella STRADARIO"));
                }
            }
            #endregion
        }

        #endregion
    }
}