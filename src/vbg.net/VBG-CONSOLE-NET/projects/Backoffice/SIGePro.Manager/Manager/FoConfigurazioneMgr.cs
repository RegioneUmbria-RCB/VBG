
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System.Data;
using System.ComponentModel;
using Init.SIGePro.Authentication;

using PersonalLib2.Sql;
using Init.Utils.Sorting;

namespace Init.SIGePro.Manager
{
	public class CampoVisuraFrontoffice
	{
		public string Etichetta { get; set; }
		public int Codice { get; set; }
		public string Valore { get; set; }
	}

    [DataObject(true)]
    public partial class FoConfigurazioneMgr
    {
		public List<CampoVisuraFrontoffice> GetFiltriVisuraFrontoffice(string idComune, string software)
		{
			return LeggiCampiVisura(idComune, software, true);
		}

		public List<CampoVisuraFrontoffice> GetCampiTabellaVisura(string idComune, string software)
		{
			return LeggiCampiVisura(idComune, software, false);
		}

		public List<CampoVisuraFrontoffice> GetFiltriArchivioIstanzeFrontoffice(string idComune, string software)
		{
			return LeggiCampiArchivioIstanze(idComune, software, true);
		}

		public List<CampoVisuraFrontoffice> GetCampiTabellaArchivioIstanze(string idComune, string software)
		{
			return LeggiCampiArchivioIstanze(idComune, software, false);
		}



		public int GetRecordPerPagina(string idComune, string software)
		{

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = @"select 
									VALORE 
								from 
									FO_CONFIGURAZIONE 
								where 
									IDCOMUNE={0} and 
									SOFTWARE={1} and 
									FK_IDCONFIGURAZIONEBASE='68'";

				sql = PreparaQueryParametrica(sql, "IdComune", "Software");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("IdComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("Software", software));

					object obj = cmd.ExecuteScalar();

					if (obj == null || obj == DBNull.Value)
						return 0;

					return Convert.ToInt32( obj );
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}
		}

		private List<CampoVisuraFrontoffice> LeggiCampiVisura(string idComune, string software, bool campoFiltro)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = @"select 
									BAS.ETICHETTA as etichetta,
									BAS.CODICE as codice,
									VALORE
								from 
									FO_CONFIGURAZIONEBASE BAS,
									FO_CONFIGURAZIONE CFG
								where 
									BAS.FK_CONTESTO = '" + (campoFiltro ? "ISI-FIL" : "ISI-LIS") + @"' and
									CFG.FK_IDCONFIGURAZIONEBASE = BAS.CODICE and
									CFG.IDCOMUNE = {0} and
									CFG.SOFTWARE = {1} and
									CFG.VALORE <> '00' and CFG.VALORE is not null
								order by 
									CFG.VALORE asc";

				sql = PreparaQueryParametrica(sql, "idComune", "software");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add( db.CreateParameter( "idComune", idComune ));
					cmd.Parameters.Add(db.CreateParameter("software", software));

					using (IDataReader dr = cmd.ExecuteReader())
					{
						var rVal = new List<CampoVisuraFrontoffice>();

						while (dr.Read())
						{
							if (String.IsNullOrEmpty(dr["Valore"].ToString()))
								continue;

							var campo = new CampoVisuraFrontoffice{
								Codice = Convert.ToInt32( dr["codice"] ),
								Etichetta = dr["Etichetta"].ToString(),
								Valore = dr["Valore"].ToString()
							};

							rVal.Add(campo);

						}

						return rVal;
					}
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}
		}

		private List<CampoVisuraFrontoffice> LeggiCampiArchivioIstanze(string idComune, string software, bool campoFiltro)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = @"select 
									BAS.ETICHETTA as etichetta,
									BAS.CODICE as codice,
									VALORE
								from 
									FO_CONFIGURAZIONEBASE BAS,
									FO_CONFIGURAZIONE CFG
								where 
									BAS.FK_CONTESTO = '" + (campoFiltro ? "AIP-FIL" : "AIP-LIS") + @"' and
									CFG.FK_IDCONFIGURAZIONEBASE = BAS.CODICE and
									CFG.IDCOMUNE = {0} and
									CFG.SOFTWARE = {1} and
									CFG.VALORE <> '00' and CFG.VALORE is not null
								order by 
									CFG.VALORE asc";

				sql = PreparaQueryParametrica(sql, "idComune", "software");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("software", software));

					using (IDataReader dr = cmd.ExecuteReader())
					{
						var rVal = new List<CampoVisuraFrontoffice>();

						while (dr.Read())
						{
							if (String.IsNullOrEmpty(dr["Valore"].ToString()))
								continue;

							var campo = new CampoVisuraFrontoffice
							{
								Codice = Convert.ToInt32(dr["codice"]),
								Etichetta = dr["Etichetta"].ToString(),
								Valore = dr["Valore"].ToString()
							};

							rVal.Add(campo);

						}

						return rVal;
					}
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
				