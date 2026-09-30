using VBG.DatiDinamici;
using Init.SIGePro.Manager.DTO;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.Posteggi
{
    public class PosteggiDyn2ModelliService
    {
        private readonly string _idComune;
        private readonly DataBase _database;

        public PosteggiDyn2ModelliService(DataBase database, string idComune)
        {
            _idComune = idComune;
            _database = database;
        }

        public List<ElementoListaModelli> GetModelliCollegati(int idPosteggio)
        {
            var sql = $@"SELECT 
  id, descrizione 
FROM 
  mercati_ddyn2modellit
    INNER JOIN dyn2_modellit ON
            dyn2_modellit.idcomune =   mercati_ddyn2modellit.idcomune AND 
            dyn2_modellit.id =   mercati_ddyn2modellit.fk_d2mt_id
WHERE
  mercati_ddyn2modellit.idcomune = {this._database.Specifics.QueryParameterName("idComune")} AND 
  mercati_ddyn2modellit.idPosteggio = {this._database.Specifics.QueryParameterName("idPosteggio")}";

            return this._database.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComune", this._idComune);
                    mp.AddParameter("idPosteggio", idPosteggio);
                },
                dr => new ElementoListaModelli(dr.GetInt("id").Value, dr.GetString("descrizione"))
                ).ToList();
        }

        public void AggiungiModelloAPosteggio(int idPosteggio, int idModello)
        {
            var sql = $"insert into mercati_ddyn2modellit (idcomune, idposteggio, fk_d2mt_id) values (" +
                        this._database.Specifics.QueryParameterName("idComune") + ", " +
                        this._database.Specifics.QueryParameterName("idPosteggio") + ", " +
                        this._database.Specifics.QueryParameterName("idModello") + ")";

            this._database.ExecuteNonQuery(sql, mp =>
            {
                mp.AddParameter("idComune", this._idComune);
                mp.AddParameter("idPosteggio", idPosteggio);
                mp.AddParameter("idModello", idModello);
            });
        }

        public void EliminaScheda(int idPosteggio, int idModello)
        {
            this._database.BeginTransaction();

            try
            {
                this.EliminaCampiInutilizzati(idPosteggio, idModello);
                this.RimuoviSchedaDaPosteggio(idPosteggio, idModello);

                this._database.CommitTransaction();
            }
            catch (Exception)
            {
                this._database.RollbackTransaction();

                throw;
            }
        }

        private void RimuoviSchedaDaPosteggio(int idPosteggio, int idModello)
        {
            var sql = "delete from mercati_ddyn2modellit where " +
                     $"idcomune={this._database.Specifics.QueryParameterName("idComune")} and " +
                     $"idPosteggio={this._database.Specifics.QueryParameterName("idPosteggio")} and " +
                     $"fk_d2mt_id={this._database.Specifics.QueryParameterName("idModello")}";

            this._database.ExecuteNonQuery(sql, mp =>
            {
                mp.AddParameter("idComune", this._idComune);
                mp.AddParameter("idPosteggio", idPosteggio);
                mp.AddParameter("idModello", idModello);
            });
        }

        private void EliminaCampiInutilizzati(int idPosteggio, int idModello)
        {
            var sql = $@"SELECT 
	dyn2_modellid.fk_d2c_ID
FROM 
	mercati_dDyn2modellit,
	dyn2_modellid
WHERE
	dyn2_modellid.idcomune		= mercati_dDyn2modellit.idComune AND
    dyn2_modellid.fk_d2mt_id    = mercati_dDyn2modellit.fk_d2mt_id and 
    dyn2_modellid.fk_d2mdt_id   IS NULL and
    mercati_dDyn2modellit.idComune	    = {this._database.Specifics.QueryParameterName("idComune")} AND
    mercati_dDyn2modellit.idposteggio	= {this._database.Specifics.QueryParameterName("idPosteggio")} AND
    mercati_dDyn2modellit.fk_d2mt_id	    = {this._database.Specifics.QueryParameterName("idModello")} AND
    dyn2_modellid.fk_d2c_ID NOT IN 
	(
		SELECT 
			dyn2_modellid.fk_d2c_ID
		FROM 
			mercati_ddyn2modellit,
			dyn2_modellid
		WHERE
			dyn2_modellid.idcomune	    = mercati_dDyn2modellit.idComune AND
			dyn2_modellid.fk_d2mt_id	= mercati_dDyn2modellit.fk_d2mt_id and 
			dyn2_modellid.fk_d2mdt_id   IS NULL and
			mercati_dDyn2modellit.idComune       = {this._database.Specifics.QueryParameterName("idComune2")} AND
			mercati_dDyn2modellit.idposteggio = {this._database.Specifics.QueryParameterName("idPosteggio2")} AND
			mercati_dDyn2modellit.fk_d2mt_id     <> {this._database.Specifics.QueryParameterName("idModello2")}
	)";


            var listaIdCampi = this._database.ExecuteReader(sql,
                mp => {
                    mp.AddParameter("idComune", this._idComune);
                    mp.AddParameter("idPosteggio", idPosteggio);
                    mp.AddParameter("idModello", idModello);
                    mp.AddParameter("idComune2", this._idComune);
                    mp.AddParameter("idPosteggio2", idPosteggio);
                    mp.AddParameter("idModello2", idModello);
                },
                dr => dr.GetInt("fk_d2c_ID").Value).ToList();

            sql = $"delete from mercati_ddyn2dati where " +
                            $"idcomune={this._database.Specifics.QueryParameterName("idComune")} and " +
                            $"idPosteggio={this._database.Specifics.QueryParameterName("idPosteggio")} and " +
                            $"fk_d2c_id={this._database.Specifics.QueryParameterName("idCampo")}";

            foreach (var idCampo in listaIdCampi)
            {
                this._database.ExecuteNonQuery(sql, mp =>
                {
                    mp.AddParameter("idComune", this._idComune);
                    mp.AddParameter("idPosteggio", idPosteggio);
                    mp.AddParameter("idCampo", idCampo);
                });
            }
        }

        public List<int> GetListaIndiciScheda(int idPosteggio, int idModello)
        {
            var sql = $@"SELECT 
	distinct mercati_ddyn2dati.indice
FROM 
	dyn2_modellid,
	mercati_dDyn2dati
WHERE
	mercati_ddyn2dati.idcomune = dyn2_modellid.idcomune AND
	mercati_ddyn2dati.fk_d2c_id = dyn2_modellid.fk_d2c_id AND
	dyn2_modellid.idcomune = {this._database.Specifics.QueryParameterName("idComune")} AND
	dyn2_modellid.fk_d2mt_id = {this._database.Specifics.QueryParameterName("idModello")} AND
	mercati_ddyn2dati.idposteggio = {this._database.Specifics.QueryParameterName("idPosteggio")}
order by mercati_ddyn2dati.indice asc";


            return this._database.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComune", this._idComune);
                    mp.AddParameter("idModello", idModello);
                    mp.AddParameter("idPosteggio", idPosteggio);
                },
                dr => dr.GetInt("indice").Value
                ).ToList();
        }

        public IEnumerable<BaseDto<int, string>> GetModelliNonUtilizzati(int idPosteggio, string testoCercato, bool cercaSuTT)
        {
            var sqlSoftware = $@"select 
                                  mercati.software
                                from
                                  mercati_d
                                    INNER JOIN mercati ON
                                      mercati.idcomune = mercati_d.idcomune AND
                                      mercati.codicemercato = mercati_d.fkcodicemercato
                                WHERE
                                   mercati_d.idcomune = {this._database.Specifics.QueryParameterName("idComune")} and
                                   mercati_d.idposteggio = {this._database.Specifics.QueryParameterName("idPosteggio")}";

            var software = "'" + this._database.ExecuteScalar(sqlSoftware, "", mp =>
            {
                mp.AddParameter("idComune", this._idComune);
                mp.AddParameter("idPosteggio", idPosteggio);
            }) + "'";

            if (cercaSuTT)
            {
                software += ", 'TT'";
            }

            string sql = $@"SELECT 
	DYN2_MODELLIT.id,
	DYN2_MODELLIT.descrizione 
FROM
	mercati_ddyn2modellit 
    right OUTER JOIN DYN2_MODELLIT ON
      mercati_ddyn2modellit.idPosteggio = {this._database.Specifics.QueryParameterName("idPosteggio")} and
      mercati_ddyn2modellit.idcomune = DYN2_MODELLIT.idcomune AND 
      mercati_ddyn2modellit.fk_d2mt_id = DYN2_MODELLIT.id 
WHERE
	DYN2_MODELLIT.idcomune = {this._database.Specifics.QueryParameterName("idComune")} AND
    DYN2_MODELLIT.software in ({software}) AND
	DYN2_MODELLIT.fk_d2bc_id = 'PO' AND
    {this._database.Specifics.UCaseFunction("DYN2_MODELLIT.descrizione")} like {this._database.Specifics.QueryParameterName("testoCercato")} and
    mercati_ddyn2modellit.idcomune IS null
order by descrizione asc";

            return this._database.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idPosteggio", idPosteggio);
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
