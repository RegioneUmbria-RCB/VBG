using System;
using System.Collections;
using System.Data;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using PersonalLib2.Data;
using Init.Utils;
using Init.SIGePro.Validator;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{ 	///<summary>
    /// Descrizione di riepilogo per IstanzeAreeMgr.\n	/// </summary>
    public class IstanzeReplicateMgr : BaseManager
    {

        public IstanzeReplicateMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public IstanzeReplicate GetById(string idComune, int codiceIstanzaPadre, int codiceIstanzaFiglia)
        {
            IstanzeReplicate retVal = new IstanzeReplicate();
            retVal.IdComune = idComune;
            retVal.CodiceIstanzaPadre = codiceIstanzaPadre;
            retVal.CodiceIstanzaFiglia = codiceIstanzaFiglia;

            PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal, true, false);
            if (mydc.Count != 0)
                return (mydc[0]) as IstanzeReplicate;

            return null;
        }

        public IstanzeReplicate GetByClass(IstanzeReplicate pClass)
        {
            PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(pClass, true, false);
            if (mydc.Count != 0)
                return (mydc[0]) as IstanzeReplicate;

            return null;
        }

        /// <summary>
        /// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
        /// </summary>
        /// <param name="p_class">Criteri di ricerca</param>
        /// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<IstanzeReplicate> GetList(IstanzeReplicate p_class)
        {
            return this.GetList(p_class, null);
        }

        public List<IstanzeReplicate> GetList(IstanzeReplicate p_class, IstanzeReplicate p_cmpClass)
        {
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<IstanzeReplicate>();
        }

        public void Delete(IstanzeReplicate p_class)
        {
            db.Delete(p_class);
        }

        public IstanzeReplicate Insert(IstanzeReplicate p_class)
        {

            p_class = DataIntegrations(p_class);

            Validate(p_class, AmbitoValidazione.Insert);

            db.Insert(p_class);

            return p_class;
        }

        private IstanzeReplicate DataIntegrations(IstanzeReplicate p_class)
        {
            IstanzeReplicate retVal = (IstanzeReplicate)p_class.Clone();
            return retVal;
        }

        private void Validate(IstanzeReplicate p_class, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(p_class, ambitoValidazione);

            ForeignValidate(p_class);
        }

        private void ForeignValidate(IstanzeReplicate p_class)
        {
            #region ISTANZEREPLICATE.CODICEISTANZAPADRE
            if ( p_class.CodiceIstanzaPadre.HasValue && p_class.CodiceIstanzaPadre.Value > int.MinValue )
            {
                if (this.recordCount("ISTANZE", "CODICEISTANZA", "WHERE IDCOMUNE = '" + p_class.IdComune + "' AND CODICEISTANZA = " + p_class.CodiceIstanzaPadre.ToString()) == 0)
                {
                    throw (new RecordNotfoundException("ISTANZEREPLICATE.CODICEISTANZAPADRE non trovato nella tabella ISTANZE"));
                }
            }
            #endregion

            #region ISTANZEREPLICATE.CODICEISTANZAFIGLIA
            if (p_class.CodiceIstanzaFiglia.HasValue && p_class.CodiceIstanzaFiglia.Value > int.MinValue)
            {
                if (this.recordCount("ISTANZE", "CODICEISTANZA", "WHERE IDCOMUNE = '" + p_class.IdComune + "' AND CODICEISTANZA = " + p_class.CodiceIstanzaFiglia.ToString()) == 0)
                {
                    throw (new RecordNotfoundException("ISTANZEREPLICATE.CODICEISTANZAFIGLIA non trovato nella tabella ISTANZE"));
                }
            }
            #endregion
        }

        #endregion
    }
}