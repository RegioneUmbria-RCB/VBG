using Init.SIGePro.Data;
using Init.SIGePro.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per TipiProcedureDocumentiMgr.\n	/// </summary>
    public class TipiProcedureDocumentiMgr : BaseManager
    {

        public TipiProcedureDocumentiMgr(DataBase dataBase) : base(dataBase) { }


        public IEnumerable<TipiProcedureDocumenti> GetByIdProcedura(string idComune, int idProcedura, AmbitoRicerca ambitoRicercaDocumenti)
        {
            return Enumerable.Empty<TipiProcedureDocumenti>();
            /*
			var sql = "select * from tipiprocedure_documenti where idcomune={0} and tp_fkprocedura={1} and pubblica in (" + FiltroRicercaFlagPubblica.Get(ambitoRicercaDocumenti) + ")";

			sql = PreparaQueryParametrica(sql, "idComune", "idProcedura");

			using (var cmd = this.db.CreateCommand(sql))
			{
				cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
				cmd.Parameters.Add(this.db.CreateParameter("idProcedura", idProcedura));

				return this.db.GetClassList<TipiProcedureDocumenti>(cmd);
			}*/
        }



        #region Metodi per l'accesso di base al DB

        public TipiProcedureDocumenti GetById(String pTP_ID, String pIDCOMUNE)
        {
            TipiProcedureDocumenti retVal = new TipiProcedureDocumenti();
            retVal.TP_ID = pTP_ID;
            retVal.IDCOMUNE = pIDCOMUNE;

            List<TipiProcedureDocumenti> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as TipiProcedureDocumenti;

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

        #endregion
    }
}