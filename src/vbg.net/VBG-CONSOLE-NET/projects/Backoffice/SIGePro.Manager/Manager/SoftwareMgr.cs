using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using Init.SIGePro.Data;
using System.Data;

namespace Init.SIGePro.Manager
{
	public partial class SoftwareMgr : BaseManager
	{
		public SoftwareMgr(DataBase db) : base(db) { }

		public Software GetById(string codice)
		{
			Software s = new Software();
			s.CODICE = codice;

			return (Software)db.GetClass(s);
		}

		public List<Software> GetList(Software filtro)
		{
			return db.GetClassList(filtro).ToList < Software>();
		}

		public List<Software> GetSoftwareAttivi(string idComune)
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
							  software.* 
							FROM 
							  softwareattivi, 
							  software 
							WHERE
							  software.codice  = softwareattivi.fk_software AND
							  softwareattivi.idcomune = {0}
							ORDER BY 
							  descrizione asc";

				sql = PreparaQueryParametrica(sql, "idComune");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));

					return db.GetClassList<Software>(cmd);
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}

		}

		internal List<Software> GetListaSoftware(IEnumerable<string> listaSoftwareDaEstrarre)
		{

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var sql = String.Format( @"SELECT
										  software.*
										FROM
										  software
										WHERE 
										  codice IN ('{0}')
										ORDER BY descrizione",
										string.Join( "','", listaSoftwareDaEstrarre.ToArray() ) );

				
				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					return db.GetClassList<Software>(cmd);
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
