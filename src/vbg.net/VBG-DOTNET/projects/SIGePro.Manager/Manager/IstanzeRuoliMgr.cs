using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per IstanzeOneriMgr.\n	/// </summary>
    public class IstanzeRuoliMgr : BaseManager
    {
        public IstanzeRuoliMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB
        /// <summary>
        /// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
        /// </summary>
        /// <param name="p_class">Criteri di ricerca</param>
        /// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<IstanzeRuoli> GetList(IstanzeRuoli p_class)
        {
            return this.db.GetClassList(p_class);
        }

        public void Delete(IstanzeRuoli p_class)
        {
            this.db.Delete(p_class);
        }

        public IstanzeRuoli Insert(IstanzeRuoli p_class)
        {

            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            return p_class;
        }


        private void Validate(IstanzeRuoli p_class, Init.SIGePro.Manager.Validator.AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(p_class, ambitoValidazione);

            this.ForeignValidate(p_class);
        }

        private void ForeignValidate(IstanzeRuoli p_class)
        {
            #region ISTANZERUOLI.CODICEISTANZA
            if (!String.IsNullOrEmpty(p_class.CODICEISTANZA))
            {
                var condizioniIstanza = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("CODICEISTANZA", p_class.CODICEISTANZA),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };

                if (this.recordCount("ISTANZE", "CODICEISTANZA", condizioniIstanza) == 0)
                {
                    throw (new RecordNotfoundException("ISTANZERUOLI.CODICEISTANZA non trovato nella tabella ISTANZE"));
                }
            }
            #endregion

            #region ISTANZERUOLI.IDRUOLO
            if (!String.IsNullOrEmpty(p_class.IDRUOLO))
            {
                var condizioniRuolo = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("ID", p_class.IDRUOLO),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };

                if (this.recordCount("RUOLI", "ID", condizioniRuolo) == 0)
                {
                    throw (new RecordNotfoundException("ISTANZERUOLI.IDRUOLO non trovato nella tabella RUOLI"));
                }
            }
            #endregion
        }
        #endregion
    }
}

