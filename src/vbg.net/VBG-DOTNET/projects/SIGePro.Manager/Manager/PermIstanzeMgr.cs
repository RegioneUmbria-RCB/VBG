using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{
    public class PermIstanzeMgr : BaseManager
    {
        public PermIstanzeMgr(DataBase dataBase) : base(dataBase) { }


        #region Metodi per l'accesso di base al DB

        public PermIstanze GetById(String pID, String pIDCOMUNE)
        {
            PermIstanze retVal = new PermIstanze();
            retVal.ID = pID;
            retVal.IDCOMUNE = pIDCOMUNE;

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }


        public void Delete(PermIstanze p_class)
        {
            this.db.Delete(p_class);
        }

        public PermIstanze Insert(PermIstanze p_class)
        {

            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            return p_class;
        }

        public PermIstanze Update(PermIstanze p_class)
        {

            this.db.Update(p_class);

            return p_class;
        }

        private void Validate(PermIstanze p_class, Init.SIGePro.Manager.Validator.AmbitoValidazione ambitoValidazione)
        {
            p_class.TABELLA = "1";

            this.RequiredFieldValidate(p_class, ambitoValidazione);

            this.ForeignValidate(p_class);
        }

        private void ForeignValidate(PermIstanze p_class)
        {
            #region PERMISTANZE.CODICEISTANZA
            if (!String.IsNullOrEmpty(p_class.CODICEISTANZA))
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("CODICEISTANZA", p_class.CODICEISTANZA),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };

                if (this.recordCount("ISTANZE", "CODICEISTANZA", conditions) == 0)
                {
                    throw (new RecordNotfoundException("PERMISTANZE.CODICEISTANZA non trovato nella tabella ISTANZE"));
                }
            }
            #endregion
        }

        #endregion

        public bool VerificaPermessiUtente(string idComune, int codiceResponsabile, int codiceIstanza, int tabella)
        {
            PermIstanze filtro = new PermIstanze();
            filtro.IDCOMUNE = idComune;
            filtro.CODICERESPONSABILE = codiceResponsabile.ToString();
            filtro.CODICEISTANZA = codiceIstanza.ToString();
            filtro.TABELLA = tabella.ToString();

            List<PermIstanze> l = this.db.GetClassList(filtro).ToList<PermIstanze>();

            if (l == null || l.Count == 0)
                return false;

            return true;
        }
    }
}
