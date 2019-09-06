using System;
using System.Linq;
using System.Collections;
using System.Data;
using System.Text.RegularExpressions;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Validator;
using Init.SIGePro.Verticalizzazioni;
using Init.Utils;
using PersonalLib2.Data;
using Init.SIGePro.Manager.Manager;
using Init.SIGePro.DatiDinamici.Interfaces.Attivita;
using Init.SIGePro.DatiDinamici.Interfaces;
using System.Collections.Generic;

namespace Init.SIGePro.Manager 
{ 	///<summary>
	/// Descrizione di riepilogo per CittadinanzaMgr.\n	/// </summary>
	public class IAttivitaMgr : BaseManager, IIAttivitaManager
	{

		public IAttivitaMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public IAttivita GetById( string idComune, int id )
		{
			IAttivita retVal = new IAttivita();
			retVal.Id = id;
			retVal.IdComune = idComune;

			return (IAttivita)db.GetClass(retVal);
		}

		public ArrayList GetList(IAttivita p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(IAttivita p_class, IAttivita p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}

		#endregion


		#region IIAttivitaManager Members

		public IClasseContestoModelloDinamico LeggiAttivita(string idComune, int idAttivita)
		{
			return GetById(idComune, idAttivita);
		}

		#endregion


		public List<Software> GetSoftwareAttiviDaIdAttivita(string idComune, string idComuneAlias, int idAttivita)
		{
			var softwareMgr = new SoftwareMgr(this.db);
			
			var istanzaUltima = GetIstanzaUltima(idComune, idAttivita);

			if (istanzaUltima == null)
				return softwareMgr.GetSoftwareAttivi(idComune);

			var verticalizzazioneIAttivita = new VerticalizzazioneIAttivita(idComuneAlias, istanzaUltima.SOFTWARE);

			if (!verticalizzazioneIAttivita.Attiva || String.IsNullOrEmpty(verticalizzazioneIAttivita.Grupposoftware))
				return softwareMgr.GetSoftwareAttivi(idComune);

			return softwareMgr.GetListaSoftware(verticalizzazioneIAttivita.Grupposoftware.Split(','));

		}

		private Istanze GetIstanzaUltima(string idComune, int idAttivita)
		{

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var sql = @"SELECT 
								istanze.* 
							FROM 
								i_attivita, 
								istanze 
							WHERE
								istanze.idcomune = i_attivita.idcomune AND                                              
								istanze.codiceistanza = i_attivita.codiceistanzaultima and
								i_attivita.idcomune = {0} and i_attivita.id = {1}";

				sql = PreparaQueryParametrica(sql, "idComune", "idAttivita");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("idAttivita", idAttivita));

					return db.GetClassList<Istanze>(cmd).FirstOrDefault();
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}

		}
	}
}