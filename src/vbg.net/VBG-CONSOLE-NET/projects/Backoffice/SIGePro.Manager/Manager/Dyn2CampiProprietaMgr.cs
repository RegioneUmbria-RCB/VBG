
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
using Init.SIGePro.DatiDinamici.Interfaces;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
	public partial class Dyn2CampiProprietaMgr : IDyn2ProprietaCampiManager
    {
		public void DeleteByIdCampo(string IdComune, int idCampo)
		{
			Dyn2CampiProprieta filtro = new Dyn2CampiProprieta();
			filtro.Idcomune = IdComune;
			filtro.FkD2cId = idCampo;

			List<Dyn2CampiProprieta> list = GetList(filtro);

			list.ForEach(delegate(Dyn2CampiProprieta campiProp) { Delete(campiProp); });
		}

		public List<Dyn2CampiProprieta> GetListDaIdModello(string idComune, int idModello)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = PreparaQueryParametrica(@"SELECT 
														  dyn2_campiproprieta.* 
														FROM 
														  dyn2_campiproprieta,
														  dyn2_modellid
														WHERE 
														  dyn2_campiproprieta.idComune = dyn2_modellid.idComune AND
														  dyn2_campiproprieta.fk_d2c_id = dyn2_modellid.fk_d2c_id AND
														  dyn2_modellid.idComune = {0} AND
														  dyn2_modellid.fk_d2mt_id = {1}", "idComune", "idModello");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("idModello", idModello));

					return db.GetClassList<Dyn2CampiProprieta>(cmd);
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}
		}

		#region IDyn2ProprietaCampiManager Members
		public List<Dyn2CampiProprieta> GetList(string idComune, int idCampo)
		{
			var filtro = new Dyn2CampiProprieta
			{
				Idcomune = idComune,
				FkD2cId = idCampo
			};

			return GetList(filtro);
		}

		public List<IDyn2ProprietaCampo> GetProprietaCampo(string idComune, int idCampo)
		{
			var lista = GetList(idComune , idCampo);

			return new List<IDyn2ProprietaCampo>( lista.ToArray() );

			//lista.ForEach(x => rVal.Add(x));

			//return rVal;
		}

		#endregion
	}
}
				