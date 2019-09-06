using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using System.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager 
 { 	///<summary>
	/// Descrizione di riepilogo per BatchScadIstanzeMgr.\n	/// </summary>
	public class BatchScadIstanzeMgr: BaseManager
	{
		public BatchScadIstanzeMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public Batch_Scad_Istanze GetById(int codiceistanza, string idcomune)
		{
            Batch_Scad_Istanze retVal = new Batch_Scad_Istanze();
            retVal.CODICEISTANZA = codiceistanza.ToString();
			retVal.IDCOMUNE = idcomune;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
                return (mydc[0]) as Batch_Scad_Istanze;
			
			return null;
		}

        public Batch_Scad_Istanze GetByClass(Batch_Scad_Istanze pClass)
		{
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(pClass,true,true);
			if (mydc.Count!=0)
                return (mydc[0]) as Batch_Scad_Istanze;
			
			return null;
		}

        public List<Batch_Scad_Istanze> GetList(Batch_Scad_Istanze p_class)
		{
			return this.GetList(p_class,null);
		}

        public List<Batch_Scad_Istanze> GetList(Batch_Scad_Istanze p_class, Batch_Scad_Istanze p_cmpClass)
		{
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<Batch_Scad_Istanze>();
		}

        public void Delete(Batch_Scad_Istanze p_class)
        {
            VerificaRecordCollegati(p_class);

            EffettuaCancellazioneACascata(p_class);

            db.Delete(p_class);
        }

        private void VerificaRecordCollegati(Batch_Scad_Istanze cls)
        {

        }

        private void EffettuaCancellazioneACascata(Batch_Scad_Istanze cls)
        {
        }

		#endregion
	}
}