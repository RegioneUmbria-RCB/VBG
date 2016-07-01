using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using Init.SIGePro.Validator;
using System.Collections.Generic;
using PersonalLib2.Sql.Collections;
using Init.SIGePro.Exceptions;

namespace Init.SIGePro.Manager
{

    public class PareriMgr : BaseManager
    {

        public PareriMgr(DataBase dataBase) : base(dataBase) { }


        public Pareri GetById(string IdComune, int CodiceMovimento)
        {
            Pareri retVal = new Pareri();

            retVal.IDCOMUNE = IdComune;
            retVal.MOVIMENTO = CodiceMovimento.ToString();

            DataClassCollection mydc = db.GetClassList(retVal, true, false);
            if (mydc.Count != 0)
                return (mydc[0]) as Pareri;

            return null;
        }


        public List<Pareri> GetList(Pareri p_class)
        {
            return this.GetList(p_class, null);
        }

        public List<Pareri> GetList(Pareri p_class, Pareri p_cmpClass)
        {
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<Pareri>();
        }

        public void Delete(Pareri p_class)
        {
            db.Delete(p_class);
        }

        public Pareri Insert(Pareri p_class)
        {

            Validate(p_class, AmbitoValidazione.Insert);

            db.Insert(p_class);

            return p_class;
        }

        private void Validate(Pareri p_class, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(p_class, ambitoValidazione);

            ForeignValidate(p_class);

        }

        private void ForeignValidate(Pareri p_class)
        {
            #region PARERI.ISTANZA
            if (!IsStringEmpty(p_class.ISTANZA))
            {
                if (this.recordCount("ISTANZE", "CODICEISTANZA", "WHERE IDCOMUNE = '" + p_class.IDCOMUNE + "' AND CODICEISTANZA = " + p_class.ISTANZA) == 0)
                {
                    throw (new RecordNotfoundException("PARERI.ISTANZA non trovato nella tabella ISTANZE"));
                }
            }
            #endregion

            #region PARERI.AMMINISTRAZIONE
            if (!IsStringEmpty(p_class.AMMINISTRAZIONE))
            {
                if (this.recordCount("AMMINISTRAZIONI", "CODICEAMMINISTRAZIONE", "WHERE IDCOMUNE = '" + p_class.IDCOMUNE + "' AND CODICEAMMINISTRAZIONE = " + p_class.AMMINISTRAZIONE) == 0)
                {
                    throw (new RecordNotfoundException("PARERI.AMMINISTRAZIONE non trovato nella tabella AMMINISTRAZIONI"));
                }
            }
            #endregion

            #region PARERI.MOVIMENTO
            if (!IsStringEmpty(p_class.MOVIMENTO))
            {
                if (this.recordCount("MOVIMENTI", "CODICEMOVIMENTO", "WHERE IDCOMUNE = '" + p_class.IDCOMUNE + "' AND CODICEMOVIMENTO = " + p_class.MOVIMENTO) == 0)
                {
                    throw (new RecordNotfoundException("PARERI.MOVIMENTO non trovato nella tabella MOVIMENTI"));
                }
            }
            #endregion
        }

    }
}