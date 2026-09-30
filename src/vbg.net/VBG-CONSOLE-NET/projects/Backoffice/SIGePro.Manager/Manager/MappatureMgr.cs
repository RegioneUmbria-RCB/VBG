using Init.SIGePro.Manager.DTO.Mappature;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Manager
{
    public class MappatureMgr : BaseManager2
    {
        public MappatureMgr(DataBase db, string idComune)
            : base(db, idComune)
        {
        }

        public IEnumerable<MappaturaDto> GetList(string software)
        {
            bool closeConnection = false;

            try
            {
                if (this.Database.Connection.State != ConnectionState.Open)
                {
                    this.Database.Connection.Open();
                    closeConnection = true;
                }

                var sql = @"select 
                  dyn2_campi.id,
                  dyn2_campi.NOMECAMPO,
                  mappature.NOMETAGPEOPLE
                from 
                  mappature, 
                  dyn2_campi
                where
                  dyn2_campi.IDCOMUNE = mappature.idcomune and
                  dyn2_campi.ID = mappature.FKIDCAMPO and
                  mappature.IDCOMUNE = {0} and 
                  dyn2_campi.SOFTWARE = {1}";

                sql = PreparaQueryParametrica(sql, "idComune", "software");

                using (var cmd = this.Database.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.Database.CreateParameter("idComune", IdComune));
                    cmd.Parameters.Add(this.Database.CreateParameter("software", software));

                    using (var dr = cmd.ExecuteReader())
                    {
                        var result = new List<MappaturaDto>();

                        while (dr.Read())
                        {
                            result.Add(new MappaturaDto
                            {
                                IdCampo = Convert.ToInt32(dr["Id"]),
                                NomeCampo = dr["NOMECAMPO"].ToString(),
                                Espressione = dr["NOMETAGPEOPLE"].ToString()
                            });
                        }

                        return result;
                    }
                }
            }
            finally
            {
                if (closeConnection)
                {
                    this.Database.Connection.Close();
                }
            }
        }
    }
}
