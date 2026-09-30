using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per ResponsabiliMgr.\n	/// </summary>
    public class ResponsabiliRuoliMgr : BaseManager
    {

        public ResponsabiliRuoliMgr(DataBase dataBase) : base(dataBase) { }


        #region Metodi per l'accesso di base al DB

        public IEnumerable<ResponsabiliRuoli> GetList(ResponsabiliRuoli p_class)
        {
            return this.db.GetClassList(p_class).ToList<ResponsabiliRuoli>();
        }

        public void Delete(ResponsabiliRuoli p_class)
        {
            this.db.Delete(p_class);
        }

        public ResponsabiliRuoli Insert(ResponsabiliRuoli p_class)
        {
            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            return p_class;
        }



        private void Validate(ResponsabiliRuoli p_class, Init.SIGePro.Manager.Validator.AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(p_class, ambitoValidazione);

            this.ForeignValidate(p_class);
        }

        private void ForeignValidate(ResponsabiliRuoli p_class)
        {
            #region RESPONSABILI.CODICERESPONSABILE
            if (!String.IsNullOrEmpty(p_class.CODICERESPONSABILE))
            {
                var responsabiliConditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("CODICERESPONSABILE", p_class.CODICERESPONSABILE),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };

                if (this.recordCount("RESPONSABILI", "CODICERESPONSABILE", responsabiliConditions) == 0)
                {
                    throw (new RecordNotfoundException("RESPONSABILIRUOLI.CODICERESPONSABILE (" + p_class.CODICERESPONSABILE + ") non trovato nella tabella RESPONSABILI"));
                }
            }
            #endregion

            #region RUOLI.ID
            if (!String.IsNullOrEmpty(p_class.IDRUOLO))
            {
                var ruoliConditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("ID", p_class.IDRUOLO),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };

                if (this.recordCount("RUOLI", "ID", ruoliConditions) == 0)
                {
                    throw (new RecordNotfoundException("RESPONSABILIRUOLI.IDRUOLO (" + p_class.IDRUOLO + ") non trovato nella tabella RUOLI"));
                }
            }
            #endregion

        }


        #endregion
    }
}