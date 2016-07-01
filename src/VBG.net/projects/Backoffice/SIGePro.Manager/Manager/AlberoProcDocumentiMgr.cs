using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using System.Collections.Generic;
using System.Text;
using System.Data;
using PersonalLib2.Sql;

namespace Init.SIGePro.Manager 
 { 	///<summary>
	/// Descrizione di riepilogo per AlberoProcDocumentiMgr.\n	/// </summary>
	public class AlberoProcDocumentiMgr: BaseManager
	{
		public AlberoProcDocumentiMgr( DataBase dataBase ) : base( dataBase ) {}

		public List<AlberoProcDocumenti> GetListDaCodiceIntervento(string idComune, int scId, AmbitoRicerca ambitoRicercaDocumenti)
		{
			var alberoProcMgr = new AlberoProcMgr( db );
			var ramo = alberoProcMgr.GetById(scId, idComune);

			if (ramo == null)
				throw new ArgumentException("il codice intervento " + scId + " non è valido per l'id comune " + idComune);

			var whereScId = ramo.GetListaScCodice().ToString();

			var sql = @"SELECT 
						  alberoproc_documenti.*
						FROM 
						  alberoproc_documenti,
						  alberoproc
						WHERE
						  alberoproc_documenti.idcomune  = alberoproc.idcomune AND
						  alberoproc_documenti.sm_fkscid = alberoproc.sc_id AND
						  alberoproc.idcomune = {0} AND
						  alberoproc.software = {1} AND
						  alberoproc.sc_codice IN (" + whereScId + @") and
						  alberoproc_documenti.pubblica in (" + FiltroRicercaFlagPubblica.Get(ambitoRicercaDocumenti ) + ")";

			sql = PreparaQueryParametrica(sql, "idComune", "software");

			using (var cmd = db.CreateCommand(sql))
			{
				cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
				cmd.Parameters.Add(db.CreateParameter("software", ramo.SOFTWARE));

				var cls = new AlberoProcDocumenti { UseForeign = useForeignEnum.Yes };

				return db.GetClassList(cmd, cls, false, true).ToList<AlberoProcDocumenti>();
			}
		}

		#region Metodi per l'accesso di base al DB

		public AlberoProcDocumenti GetById(String pSM_ID, String pIDCOMUNE)
		{
			AlberoProcDocumenti retVal = new AlberoProcDocumenti();
			retVal.SM_ID = pSM_ID;
			retVal.IDCOMUNE = pIDCOMUNE;

			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as AlberoProcDocumenti;

			return null;
		}

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(AlberoProcDocumenti p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(AlberoProcDocumenti p_class, AlberoProcDocumenti p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}

		#endregion
	}
}
