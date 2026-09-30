using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per OrariAperturaMgr.\n	/// </summary>
    public class OrariAperturaMgr : BaseManager
    {
        public OrariAperturaMgr(DataBase dataBase) : base(dataBase) { }

        public OrariApertura GetById(String pOA_ID, String pIDCOMUNE)
        {
            OrariApertura retVal = new OrariApertura();
            retVal.OA_ID = pOA_ID;
            retVal.IDCOMUNE = pIDCOMUNE;

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }

        public List<OrariApertura> GetList(OrariApertura p_class)
        {
            return this.db.GetClassList(p_class).ToList<OrariApertura>();
        }

        public OrariApertura Insert(OrariApertura p_class)
        {
            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            return p_class;
        }

        private void Validate(OrariApertura p_class, Init.SIGePro.Manager.Validator.AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(p_class, ambitoValidazione);

            this.ForeignValidate(p_class);
        }

        private void ForeignValidate(OrariApertura p_class)
        {
            #region ORARIAPERTURATESTATA.ID
            if (!String.IsNullOrEmpty(p_class.OA_FKIDTESTATA))
            {
                var condTestata = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("ID", p_class.OA_FKIDTESTATA),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };
                if (this.recordCount("ORARIAPERTURATESTATA", "ID", condTestata) == 0)
                {
                    throw (new RecordNotfoundException("ORARIAPERTURA.OA_FKIDITESTATA (" + p_class.OA_FKIDTESTATA + ") non trovato nella tabella ORARIAPERTURATESTATA"));
                }
            }
            #endregion

            #region ISTANZE.CODICEISTANZA
            if (!String.IsNullOrEmpty(p_class.OA_FKIDISTANZA))
            {
                var condIstanze = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("CODICEISTANZA", p_class.OA_FKIDISTANZA),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };
                if (this.recordCount("ISTANZE", "CODICEISTANZA", condIstanze) == 0)
                {
                    throw (new RecordNotfoundException("ORARIAPERTURA.OA_FKIDISTANZA (" + p_class.OA_FKIDISTANZA + ") non trovato nella tabella ISTANZE"));
                }
            }
            #endregion

            #region GIORNISETTIMANA.GS_ID
            if (!String.IsNullOrEmpty(p_class.OA_FKIDGIORNO))
            {
                var condGiorno = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("GS_ID", p_class.OA_FKIDGIORNO)
                };
                if (this.recordCount("GIORNISETTIMANA", "GS_ID", condGiorno) == 0)
                {
                    throw (new RecordNotfoundException("ORARIAPERTURA.OA_FKIDGIORNO (" + p_class.OA_FKIDGIORNO + ") non trovato nella tabella GIORNISETTIMANA"));
                }
            }
            #endregion

            #region TIPIAPERTURA.TA_ID
            if (!String.IsNullOrEmpty(p_class.OA_FKIDORARIO))
            {
                var condOrario = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("TA_ID", p_class.OA_FKIDORARIO),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };
                if (this.recordCount("TIPIAPERTURA", "TA_ID", condOrario) == 0)
                {
                    throw (new RecordNotfoundException("ORARIAPERTURA.OA_FKIDORARIO (" + p_class.OA_FKIDORARIO + ") non trovato nella tabella TIPIAPERTURA"));
                }
            }
            #endregion


        }

        public void Delete(OrariApertura p_class)
        {
            this.db.Delete(p_class);
        }

    }
}