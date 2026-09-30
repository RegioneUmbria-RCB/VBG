using VBG.DatiDinamici;
using Init.SIGePro.Manager.DTO;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.Mercati
{
    public class MercatiDyn2ModelliService
    {
        private readonly string _idComune;
        private readonly DataBase _database;

        public MercatiDyn2ModelliService(DataBase database, string idComune)
        {
            _idComune = idComune;
            _database = database;
        }

        public List<ElementoListaModelli> GetModelliCollegati(int codiceMercato)
        {
            var sql = $@"SELECT 
  id, descrizione 
FROM 
  mercatidyn2modellit
    INNER JOIN dyn2_modellit ON
            dyn2_modellit.idcomune =   mercatidyn2modellit.idcomune AND 
            dyn2_modellit.id =   mercatidyn2modellit.fk_d2mt_id
WHERE
  mercatidyn2modellit.idcomune = {this._database.Specifics.QueryParameterName("idComune")} AND 
  mercatidyn2modellit.codicemercato = {this._database.Specifics.QueryParameterName("codiceMercato")}";

            return this._database.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComune", this._idComune);
                    mp.AddParameter("codiceMercato", codiceMercato);
                },
                dr => new ElementoListaModelli(dr.GetInt("id").Value, dr.GetString("descrizione"))
                ).ToList();
        }

        public void AggiungiModelloAMercato(int codiceMercato, int idModello)
        {
            var sql = $"insert into mercatidyn2modellit (idcomune, codicemercato, fk_d2mt_id) values (" +
                        this._database.Specifics.QueryParameterName("idComune") + ", " +
                        this._database.Specifics.QueryParameterName("codiceMercato") + ", " +
                        this._database.Specifics.QueryParameterName("idModello") + ")";

            this._database.ExecuteNonQuery(sql, mp =>
            {
                mp.AddParameter("idComune", this._idComune );
                mp.AddParameter("codiceMercato", codiceMercato);
                mp.AddParameter("idModello", idModello);
            });
        }

        public void EliminaScheda(int codiceMercato, int idModello)
        {
            this._database.BeginTransaction();

            try
            {
                this.EliminaCampiInutilizzati(codiceMercato, idModello);
                this.RimuoviSchedaDaMercato(codiceMercato, idModello);

                this._database.CommitTransaction();
            }
            catch (Exception)
            {
                this._database.RollbackTransaction();

                throw;
            }
        }

        private void RimuoviSchedaDaMercato(int codiceMercato, int idModello)
        {
            var sql = "delete from mercatidyn2modellit where " +
                     $"idcomune={this._database.Specifics.QueryParameterName("idComune")} and " +
                     $"codicemercato={this._database.Specifics.QueryParameterName("codiceMercato")} and " +
                     $"fk_d2mt_id={this._database.Specifics.QueryParameterName("idModello")}";

            this._database.ExecuteNonQuery(sql, mp =>
            {
                mp.AddParameter("idComune", this._idComune);
                mp.AddParameter("codiceMercato", codiceMercato);
                mp.AddParameter("idModello", idModello);
            });
        }

        private void EliminaCampiInutilizzati(int codiceMercato, int idModello)
        {
            var sql = $@"SELECT 
	dyn2_modellid.fk_d2c_ID
FROM 
	mercatiDyn2modellit,
	dyn2_modellid
WHERE
	dyn2_modellid.idcomune		= mercatiDyn2modellit.idComune AND
    dyn2_modellid.fk_d2mt_id    = mercatiDyn2modellit.fk_d2mt_id and 
    dyn2_modellid.fk_d2mdt_id   IS NULL and
    mercatiDyn2modellit.idComune	    = {this._database.Specifics.QueryParameterName("idComune")} AND
    mercatiDyn2modellit.codiceMercato	= {this._database.Specifics.QueryParameterName("codiceMercato")} AND
    mercatiDyn2modellit.fk_d2mt_id	    = {this._database.Specifics.QueryParameterName("idModello")} AND
    dyn2_modellid.fk_d2c_ID NOT IN 
	(
		SELECT 
			dyn2_modellid.fk_d2c_ID
		FROM 
			mercatiDyn2modellit,
			dyn2_modellid
		WHERE
			dyn2_modellid.idcomune	    = mercatiDyn2modellit.idComune AND
			dyn2_modellid.fk_d2mt_id	= mercatiDyn2modellit.fk_d2mt_id and 
			dyn2_modellid.fk_d2mdt_id   IS NULL and
			mercatiDyn2modellit.idComune       = {this._database.Specifics.QueryParameterName("idComune2")} AND
			mercatiDyn2modellit.codiceMercato = {this._database.Specifics.QueryParameterName("codiceMercato2")} AND
			mercatiDyn2modellit.fk_d2mt_id     <> {this._database.Specifics.QueryParameterName("idModello2")}
	)";


            var listaIdCampi = this._database.ExecuteReader(sql,
                mp => {
                    mp.AddParameter("idComune", this._idComune);
                    mp.AddParameter("codiceMercato", codiceMercato);
                    mp.AddParameter("idModello", idModello);
                    mp.AddParameter("idComune2", this._idComune);
                    mp.AddParameter("codiceMercato2", codiceMercato);
                    mp.AddParameter("idModello2", idModello);
                },
                dr => dr.GetInt("fk_d2c_ID").Value).ToList();

            sql = $"delete from mercatidyn2dati where " +
                            $"idcomune={this._database.Specifics.QueryParameterName("idComune")} and " +
                            $"codiceMercato={this._database.Specifics.QueryParameterName("codiceMercato")} and " +
                            $"fk_d2c_id={this._database.Specifics.QueryParameterName("idCampo")}";

            foreach (var idCampo in listaIdCampi)
            {
                this._database.ExecuteNonQuery(sql, mp =>
                {
                    mp.AddParameter("idComune", this._idComune);
                    mp.AddParameter("codiceMercato", codiceMercato);
                    mp.AddParameter("idCampo", idCampo);
                });
            }
        }

        public List<int> GetListaIndiciScheda(int codiceMercato, int idModello)
        {
            var sql = $@"SELECT 
	distinct mercatidyn2dati.indice
FROM 
	dyn2_modellid,
	mercatidyn2dati
WHERE
	mercatidyn2dati.idcomune = dyn2_modellid.idcomune AND
	mercatidyn2dati.fk_d2c_id = dyn2_modellid.fk_d2c_id AND
	dyn2_modellid.idcomune = {this._database.Specifics.QueryParameterName("idComune")} AND
	dyn2_modellid.fk_d2mt_id = {this._database.Specifics.QueryParameterName("idModello")} AND
	mercatidyn2dati.codicemercato = {this._database.Specifics.QueryParameterName("codiceMercato")}
order by mercatidyn2dati.indice asc";


            return this._database.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComune", this._idComune);
                    mp.AddParameter("idModello", idModello);
                    mp.AddParameter("codiceMercato", codiceMercato);
                },
                dr => dr.GetInt("indice").Value
                ).ToList();
        }

        public IEnumerable<BaseDto<int, string>> GetModelliNonUtilizzati(int codiceMercato, string testoCercato, bool cercaTT)
        {
            var sqlSw = $@"select software from mercati where idcomune={this._database.Specifics.QueryParameterName("idComune")} and codicemercato={this._database.Specifics.QueryParameterName("codiceMercato")}";

            var software = "'" + this._database.ExecuteScalar(sqlSw, "", mp =>
            {
                mp.AddParameter("idComune", this._idComune);
                mp.AddParameter("codiceMercato", codiceMercato);
            }) + "'";

            if (cercaTT)
            {
                software += ", 'TT'"; 
            }


            string sql = $@"SELECT 
	DYN2_MODELLIT.id,
	DYN2_MODELLIT.descrizione 
FROM
	mercatidyn2modellit 
    right OUTER JOIN DYN2_MODELLIT ON
      mercatidyn2modellit.codiceMercato = {this._database.Specifics.QueryParameterName("codiceMercato")} and
      mercatidyn2modellit.idcomune = DYN2_MODELLIT.idcomune AND 
      mercatidyn2modellit.fk_d2mt_id = DYN2_MODELLIT.id 
WHERE
	DYN2_MODELLIT.idcomune = {this._database.Specifics.QueryParameterName("idComune")} AND
    DYN2_MODELLIT.software in ({software}) AND
	DYN2_MODELLIT.fk_d2bc_id = 'ME' AND
    {this._database.Specifics.UCaseFunction("DYN2_MODELLIT.descrizione")} like {this._database.Specifics.QueryParameterName("testoCercato")} and
    MERCATIDYN2MODELLIT.idcomune IS null
order by descrizione asc";

            return this._database.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("codiceMercato", codiceMercato);
                    mp.AddParameter("idComune", this._idComune);
                    mp.AddParameter("testoCercato", testoCercato.ToUpper() + "%");
                },
                dr => new BaseDto<int, string>
                {
                    Codice = dr.GetInt("id").Value,
                    Descrizione = dr.GetString("descrizione")
                });
        }
    }
}
