using Init.SIGePro.Data;
using Init.SIGePro.Manager.DTO.Common;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per TipiProcedureDocumentiMgr.\n	/// </summary>
    public class TipiProcedureDocumentiMgr : BaseManager
    {

        public TipiProcedureDocumentiMgr(DataBase dataBase) : base(dataBase) { }


        public IEnumerable<TipiProcedureDocumenti> GetByIdProcedura(string idComune, int idProcedura, AmbitoRicerca ambitoRicercaDocumenti)
        {
            var sql = "select * from tipiprocedure_documenti where idcomune={0} and tp_fkprocedura={1} and pubblica in (" + FiltroRicercaFlagPubblica.Get(ambitoRicercaDocumenti) + ")";

            sql = this.PreparaQueryParametrica(sql, "idComune", "idProcedura");

            using (var cmd = this.db.CreateCommand(sql))
            {
                cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                cmd.Parameters.Add(this.db.CreateParameter("idProcedura", idProcedura));

                return this.db.GetClassList<TipiProcedureDocumenti>(cmd);
            }
        }



        #region Metodi per l'accesso di base al DB

        public TipiProcedureDocumenti GetById(String pTP_ID, String pIDCOMUNE)
        {
            TipiProcedureDocumenti retVal = new TipiProcedureDocumenti();
            retVal.TP_ID = pTP_ID;
            retVal.IDCOMUNE = pIDCOMUNE;

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }


        public TipiProcedureDocumenti Insert(TipiProcedureDocumenti p_class)
        {
            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            return p_class;
        }

        private void Validate(TipiProcedureDocumenti p_class, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(p_class, ambitoValidazione);
        }



        /// <summary>
        /// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
        /// </summary>
        /// <param name="p_class">Criteri di ricerca</param>
        /// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<TipiProcedureDocumenti> GetList(TipiProcedureDocumenti p_class)
        {
            return this.db.GetClassList(p_class, false).ToList<TipiProcedureDocumenti>();
        }
        #endregion
    }
}