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

    public class SITCartechMgr : BaseManager
    {

        public SITCartechMgr(DataBase dataBase) : base(dataBase) { }

        public Sit_Cartech GetById(string IdComune, int CodiceIstanza)
        {
            Sit_Cartech retVal = new Sit_Cartech();

            retVal.IDCOMUNE = IdComune;
            retVal.CODICEISTANZA = CodiceIstanza.ToString();

            DataClassCollection mydc = db.GetClassList(retVal, true, false);
            if (mydc.Count != 0)
                return (mydc[0]) as Sit_Cartech;

            return null;
        }

        public List<Sit_Cartech> GetList(Sit_Cartech p_class)
        {
            return this.GetList(p_class, null);
        }

        public List<Sit_Cartech> GetList(Sit_Cartech p_class, Sit_Cartech p_cmpClass)
        {
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<Sit_Cartech>();
        }

        public void Delete(Sit_Cartech p_class)
        {
            db.Delete(p_class);
        }

        public Sit_Cartech Insert(Sit_Cartech p_class)
        {

            Validate(p_class, AmbitoValidazione.Insert);

            db.Insert(p_class);

            return p_class;
        }

        private void Validate(Sit_Cartech p_class, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(p_class, ambitoValidazione);

            ForeignValidate(p_class);

        }

        private void ForeignValidate(Sit_Cartech p_class)
        {
            #region SIT_CARTECH.CODICEISTANZA
            if (!IsStringEmpty(p_class.CODICEISTANZA))
            {
                if (this.recordCount("ISTANZE", "CODICEISTANZA", "WHERE IDCOMUNE = '" + p_class.IDCOMUNE + "' AND CODICEISTANZA = " + p_class.CODICEISTANZA) == 0)
                {
                    throw (new RecordNotfoundException("SIT_CARTECH.CODICEISTANZA non trovato nella tabella ISTANZE"));
                }
            }
            #endregion
        }

    }
}