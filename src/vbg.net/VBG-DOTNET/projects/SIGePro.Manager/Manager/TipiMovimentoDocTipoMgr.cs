using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per TipiMovimentoDocTipoMgr.\n	/// </summary>
    public class TipiMovimentoDocTipoMgr : BaseManager
    {

        public TipiMovimentoDocTipoMgr(DataBase dataBase) : base(dataBase) { }

        private TipiMovimentoDocTipo DataIntegrations(TipiMovimentoDocTipo p_class)
        {
            TipiMovimentoDocTipo retVal = (TipiMovimentoDocTipo)p_class.Clone();

            return retVal;
        }

        private void Validate(TipiMovimentoDocTipo p_class, Init.SIGePro.Manager.Validator.AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(p_class, ambitoValidazione);

            //ForeignValidate( p_class );
        }

        public TipiMovimentoDocTipo Insert(TipiMovimentoDocTipo p_class)
        {
            p_class = this.DataIntegrations(p_class);

            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            return p_class;
        }





        #region Metodi per l'accesso di base al DB


        /// <summary>
        /// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
        /// </summary>
        /// <param name="p_class">Criteri di ricerca</param>
        /// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<TipiMovimentoDocTipo> GetList(TipiMovimentoDocTipo p_class)
        {
            return this.db.GetClassList(p_class, false).ToList<TipiMovimentoDocTipo>();
        }

        /*public ArrayList GetList(TipiMovimentoDocTipo p_class, TipiMovimentoDocTipo p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}*/

        #endregion
    }
}
