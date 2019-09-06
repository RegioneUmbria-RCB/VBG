using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using Init.SIGePro.Validator;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager 
 { 	///<summary>
	/// Descrizione di riepilogo per TipiProcedureDocumentiMgr.\n	/// </summary>
	public class TipiProcedureDocumentiMgr: BaseManager
{

		public TipiProcedureDocumentiMgr( DataBase dataBase ) : base( dataBase ) {}


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
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as TipiProcedureDocumenti;
			
			return null;
		}

 
		public TipiProcedureDocumenti Insert( TipiProcedureDocumenti p_class )
		{
			Validate( p_class ,AmbitoValidazione.Insert);
			
			db.Insert( p_class );

			return p_class;
		}

		private void Validate(TipiProcedureDocumenti p_class, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( p_class , ambitoValidazione);
		}



		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(TipiProcedureDocumenti p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(TipiProcedureDocumenti p_class, TipiProcedureDocumenti p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}
#endregion
	}
}