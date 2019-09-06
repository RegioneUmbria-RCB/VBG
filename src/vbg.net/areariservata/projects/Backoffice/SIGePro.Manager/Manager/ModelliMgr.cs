using Init.SIGePro.Manager.DTO.Modulistica;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Data;
using System.Globalization;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Manager
{
    public static class DataReaderExtensions
    {
        public static T GetValue<T>(this IDataReader dr, string nomeCampo, T defaultValue)
        {
            var val = dr[nomeCampo];

            if (val == null || val == DBNull.Value)
            {
                return defaultValue;
            }

            var destType = typeof(T);

            if (destType.IsGenericType)
            {
                destType = destType.GetGenericArguments()[0];
            }

            return (T)Convert.ChangeType(val, destType, CultureInfo.InvariantCulture);
        }
    }


    public class ModelliMgr : BaseManager2
    {
        public ModelliMgr(DataBase db, string idComune)
            :base(db, idComune)
        {

        }

        public IEnumerable<CategoriaModulisticaDto> GetModulisticaFrontoffice(string software)
        {

            bool closeCnn = false;

            try
            {
                if (this.Database.Connection.State == ConnectionState.Closed)
                {
                    this.Database.Connection.Open();
                    closeCnn = true;
                }

                var sql = @"SELECT
                              tipimodelli.id AS codicecategoria,
                              tipimodelli.descrizione AS descrizionecategoria, 
                              modelli.* 
                            FROM 
                              modelli 
                                left OUTER JOIN tipimodelli ON
                                  modelli.idcomune = tipimodelli.idcomune AND
                                  modelli.fk_tipimodulo = tipimodelli.id 
                            where 
                                modelli.FLAG_PUBBLICA=1 and 
                                modelli.idcomune={0} and 
                                modelli.tiposuap in ('TT','" + software + @"') 
                            order by descrizionecategoria asc, ordine desc";

                sql = PreparaQueryParametrica(sql, "idComune");

                using (var cmd = this.Database.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.Database.CreateParameter("idComune", this.IdComune));

                    using(var dr = cmd.ExecuteReader())
                    {
                        var dict = new Dictionary<int, CategoriaModulisticaDto>();

                        while (dr.Read())
                        {
                            var modello = new ModulisticaDto
                            {
                                Codice = dr.GetValue("codicemodello", -1),
                                Titolo = dr.GetValue("titolo", String.Empty),
                                Descrizione = dr.GetValue("descrizione", String.Empty),
                                NomeFile = dr.GetValue("nomeFile", String.Empty),
                                Ordine = dr.GetValue("ordine", 0),
                                Url = dr.GetValue("indirizzoWeb", String.Empty),
                                CodiceOggetto = dr.GetValue("codiceoggetto", (int?)null)
                            };

                            var idCategoria = dr.GetValue("codicecategoria", -1);
                            var descCategoria = dr.GetValue("descrizionecategoria", "Altra modulistica");

                            if (!dict.ContainsKey(idCategoria))
                            {
                                dict.Add(idCategoria, new CategoriaModulisticaDto
                                {
                                    Codice = idCategoria,
                                    Descrizione = descCategoria
                                });
                            }

                            dict[idCategoria].Modulistica.Add(modello);
                        }

                        return dict.Select( x => x.Value);
                    }
                }
            }
            finally
            {
                if (closeCnn)
                    this.Database.Connection.Close();
            }
            
        }
    }
}
