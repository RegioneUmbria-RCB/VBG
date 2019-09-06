using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using System.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{ 	///<summary>
    /// Descrizione di riepilogo per BatchScadenzarioMgr.\n	/// </summary>
    public class BatchScadenzarioMgr : BaseManager
    {
        public BatchScadenzarioMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public Batch_Scadenzario GetById(int id, string idcomune)
        {
            Batch_Scadenzario retVal = new Batch_Scadenzario();
            retVal.ID = id.ToString();
            retVal.IDCOMUNE = idcomune;

            PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal, true, false);
            if (mydc.Count != 0)
                return (mydc[0]) as Batch_Scadenzario;

            return null;
        }

        public Batch_Scadenzario GetByClass(Batch_Scadenzario pClass)
        {
            PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(pClass, true, true);
            if (mydc.Count != 0)
                return (mydc[0]) as Batch_Scadenzario;

            return null;
        }

        public List<Batch_Scadenzario> GetList(Batch_Scadenzario p_class)
        {
            return this.GetList(p_class, null);
        }

        public List<Batch_Scadenzario> GetList(Batch_Scadenzario p_class, Batch_Scadenzario p_cmpClass)
        {
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<Batch_Scadenzario>();
        }

        public void Delete(Batch_Scadenzario p_class)
        {
            VerificaRecordCollegati(p_class);

            EffettuaCancellazioneACascata(p_class);

            db.Delete(p_class);
        }

        private void VerificaRecordCollegati(Batch_Scadenzario cls)
        {

        }

        private void EffettuaCancellazioneACascata(Batch_Scadenzario cls)
        {
        }
        #endregion
    }
}