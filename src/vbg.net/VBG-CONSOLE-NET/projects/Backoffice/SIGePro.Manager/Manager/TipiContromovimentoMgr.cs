using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Validator;
using Init.Utils;
using PersonalLib2.Data;


namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per TipiContromovimentoMgr.\n	/// </summary>
    public class TipiContromovimentoMgr : BaseManager
    {

        public TipiContromovimentoMgr(DataBase dataBase) : base(dataBase) { }


        #region Metodi per l'accesso di base al DB

        private TipiContromovimento DataIntegrations(TipiContromovimento p_class)
        {
            TipiContromovimento retVal = (TipiContromovimento)p_class.Clone();

            if (retVal.Contatore.GetValueOrDefault(int.MinValue) == int.MinValue)
                retVal.Contatore = this.FindMax("CONTATORE", "TIPICONTROMOVIMENTO", retVal.IDCOMUNE, null);

            if (StringChecker.IsStringEmpty(retVal.AMMMOV))
                retVal.AMMMOV = "0";

            if (StringChecker.IsStringEmpty(retVal.AMMRITSU))
                retVal.AMMRITSU = "0";

            if (StringChecker.IsStringEmpty(retVal.FLAGBASE))
                retVal.FLAGBASE = "1";

            if (StringChecker.IsStringEmpty(retVal.SOLOSEESITONEGATIVO))
                retVal.SOLOSEESITONEGATIVO = "0";

            return retVal;
        }


        private void Validate(TipiContromovimento p_class, Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
        {
            if (p_class.FLAGBASE != "0" && p_class.FLAGBASE != "1")
                throw new IncongruentDataException("TIPICONTROMOVIMENTO.FLAGBASE = " + p_class.FLAGBASE);

            this.RequiredFieldValidate(p_class, ambitoValidazione);

            //ForeignValidate( p_class );
        }


        private void ForeignValidate(TipiContromovimento p_class)
        {
            #region TIPICONTROMOVIMENTO.TIPOMOVIMENTO
            if (!this.IsStringEmpty(p_class.TIPOMOVIMENTO))
            {
                if (this.recordCount("TIPIMOVIMENTO", "TIPOMOVIMENTO", "WHERE IDCOMUNE = '" + p_class.IDCOMUNE + "' AND TIPOMOVIMENTO = '" + p_class.TIPOMOVIMENTO + "'") == 0)
                {
                    throw (new RecordNotfoundException("TIPICONTROMOVIMENTO.TIPOMOVIMENTO (" + p_class.TIPOMOVIMENTO + ") non trovato nella tabella TIPIMOVIMENTO"));
                }
            }
            #endregion

            #region TIPICONTROMOVIMENTO.AMMMOV
            if (!this.IsStringEmpty(p_class.AMMMOV))
            {
                if (this.recordCount("AMMINISTRAZIONI", "CODICEAMMINISTRAZIONE", "WHERE IDCOMUNE = '" + p_class.IDCOMUNE + "' AND CODICEAMMINISTRAZIONE = " + p_class.AMMMOV) == 0)
                {
                    throw (new RecordNotfoundException("TIPICONTROMOVIMENTO.AMMMOV (" + p_class.AMMMOV + ") non trovato nella tabella AMMINISTRAZIONI"));
                }
            }
            #endregion

            #region TIPICONTROMOVIMENTO.AMMRITSU
            if (!this.IsStringEmpty(p_class.AMMRITSU))
            {
                if (this.recordCount("AMMINISTRAZIONI", "CODICEAMMINISTRAZIONE", "WHERE IDCOMUNE = '" + p_class.IDCOMUNE + "' AND CODICEAMMINISTRAZIONE = " + p_class.AMMRITSU) == 0)
                {
                    throw (new RecordNotfoundException("TIPICONTROMOVIMENTO.AMMRITSU (" + p_class.AMMRITSU + ") non trovato nella tabella AMMINISTRAZIONI"));
                }
            }
            #endregion

            #region TIPICONTROMOVIMENTO.CODICEPROCEDURA
            if (!this.IsStringEmpty(p_class.CODICEPROCEDURA))
            {
                if (this.recordCount("TIPIPROCEDURE", "CODICEPROCEDURA", "WHERE IDCOMUNE = '" + p_class.IDCOMUNE + "' AND CODICEPROCEDURA = " + p_class.CODICEPROCEDURA) == 0)
                {
                    throw (new RecordNotfoundException("TIPICONTROMOVIMENTO.CODICEPROCEDURA (" + p_class.CODICEPROCEDURA + ") non trovato nella tabella TIPIPROCEDURE"));
                }
            }
            #endregion
        }


        public TipiContromovimento Insert(TipiContromovimento p_class)
        {
            p_class = this.DataIntegrations(p_class);

            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            return p_class;
        }

        #endregion
    }
}
