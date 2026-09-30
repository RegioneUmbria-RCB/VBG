using Init.SIGePro.Manager.Authentication;
using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.ConfigurazioneSchede.SchedeCollegateACampi
{
    public class SchedeCollegateACampiService
    {
        public class SoftwareAttivoSuCampi
        {
            public string Codice { get; set; } = "";
            public string Descrizione { get; set; } = "";
        }

        public class RicercaCampoDinamicoResult
        {
            public int Id { get; set; }
            public string IdCampo { get; set; } = "";
            public string Etichetta { get; set; } = "";
        }

        private readonly string _idComune;
        private readonly DataBase _dataBase;
        private readonly ILog _log = LogManager.GetLogger(typeof(SchedeCollegateACampiService));

        public SchedeCollegateACampiService(IAuthenticationInfoResolver authenticationInfoResolver)
        {
            var authInfo = authenticationInfoResolver.Resolve();

            this._dataBase = authInfo.CreateDatabase();
            this._idComune = authInfo.IdComune;
        }

        public IEnumerable<SoftwareAttivoSuCampi> GetSoftwareCampiDinamici()
        {

            FormattableString sql = @$"
SELECT 
	DISTINCT 
	software.codice,
	software.descrizione
FROM 
	dyn2_campi 
		INNER JOIN software ON
			software.codice = dyn2_campi.software
WHERE
	dyn2_campi.idcomune={this._idComune}";

            return this._dataBase.ExecuteReader(sql, dr => new SoftwareAttivoSuCampi
            {
                Codice = dr.GetString("codice"),
                Descrizione = dr.GetString("descrizione")
            });
        }

        public IEnumerable<RicercaCampoDinamicoResult> RicercaCampiDinamici(string software, string testoParziale)
        {
            var results = this.RicercaCampiDinamiciInternal(software, testoParziale);

            if (results.Any())
            {
                return results;
            }

            results = this.RicercaCampiDinamiciInternal(software, $"{testoParziale}%");

            if (results.Any())
            {
                return results;
            }

            return this.RicercaCampiDinamiciInternal(software, $"%{testoParziale}%");
        }

        private IEnumerable<RicercaCampoDinamicoResult> RicercaCampiDinamiciInternal(string software, string testoParziale)
        {

            var fmt = $@"SELECT
                            dyn2_campi.id, 
                	        dyn2_campi.nomecampo, 
                	        dyn2_campi.etichetta, 
                	        software.codice AS idsoftware, 
                	        software.descrizione AS descrizionesoftware
                        FROM 
                	        dyn2_campi 
                	        INNER JOIN software ON 
                		        software.codice = dyn2_campi.software 
                        WHERE 
                	        dyn2_campi.idcomune = {{0}} AND 
                	        dyn2_campi.software = {{1}} AND 
                	        (
                                {this._dataBase.Specifics.UCaseFunction("dyn2_campi.nomecampo")} like {{2}} or
                                {this._dataBase.Specifics.UCaseFunction("dyn2_campi.etichetta")} like {{2}} 
                            )";

            var args = new[]{
                this._idComune,
                software,
                testoParziale
            };

            var sql = FormattableStringFactory.Create(fmt, args);

            return this._dataBase.ExecuteReader(sql, dr => new RicercaCampoDinamicoResult
            {
                Id = dr.GetInt("id")!.Value,
                IdCampo = dr.GetString("nomecampo"),
                Etichetta = dr.GetString("etichetta")
            });
        }

        public void AggiungiCampoCollegato(int idModello, int idCampo)
        {
            this._log.DebugFormat("Aggiungo campo collegato al modello. idModello: {0}, idCampo: {1}", idModello, idCampo);

            FormattableString sql = $"select count(*) from dyn2_modelli_campi_collegati where idcomune={this._idComune} and fkidmodello={idModello} and fkidcampo={idCampo}";

            var count = this._dataBase.ExecuteScalar(sql, 0);

            if (count > 0)
            {
                this._log.DebugFormat("Il campo è già collegato al modello. idModello: {0}, idCampo: {1}", idModello, idCampo);
                // il campo è già collegato al modello, non faccio nulla
                return;
            }

            this._log.DebugFormat("Il campo non è collegato al modello, procedo con l'inserimento. idModello: {0}, idCampo: {1}", idModello, idCampo);

            sql = $"insert into dyn2_modelli_campi_collegati (idcomune, fkidmodello, fkidcampo) values ({this._idComune}, {idModello}, {idCampo})";
            this._dataBase.ExecuteNonQuery(sql);

            this._log.DebugFormat("Campo collegato al modello con successo. idModello: {0}, idCampo: {1}", idModello, idCampo);
        }

        public void RimuoviCampoCollegato(int idModello, int idCampo)
        {
            this._log.DebugFormat("Rimuovo campo collegato al modello. idModello: {0}, idCampo: {1}", idModello, idCampo);

            FormattableString sql = $"delete from dyn2_modelli_campi_collegati where idcomune={this._idComune} and fkidmodello={idModello} and fkidcampo={idCampo}";
            this._dataBase.ExecuteNonQuery(sql);

            this._log.DebugFormat("Campo scollegato dal modello con successo. idModello: {0}, idCampo: {1}", idModello, idCampo);
        }

        public IEnumerable<RicercaCampoDinamicoResult> GetCampiCollegatiDaIdScheda(int value)
        {
            FormattableString sql = $@"select 
                                            *
                                        from 
                                            dyn2_modelli_campi_collegati
                                                inner join dyn2_campi on 
                                            dyn2_campi.idcomune = dyn2_modelli_campi_collegati.idcomune and
                                        dyn2_campi.id = dyn2_modelli_campi_collegati.fkidcampo
                                        where 
                                            dyn2_modelli_campi_collegati.idcomune = {this._idComune} and
                                            dyn2_modelli_campi_collegati.fkidmodello = {value}";

            return this._dataBase.ExecuteReader(sql, dr => new RicercaCampoDinamicoResult
            {
                Id = dr.GetInt("id")!.Value,
                IdCampo = dr.GetString("nomecampo"),
                Etichetta = dr.GetString("etichetta"),
            });
        }

        public IEnumerable<int> GetListaSchedeCollegateDaIdModello(int idModello, IEnumerable<int> listaSchedeDellaDomanda)
        {
            var sql = $@"
SELECT
	DISTINCT dyn2_modelli_campi_collegati.fkidmodello
FROM
	dyn2_modellid scheda_modificata 
	
	INNER JOIN dyn2_modelli_campi_collegati ON
		dyn2_modelli_campi_collegati.idcomune = scheda_modificata.idcomune AND
		dyn2_modelli_campi_collegati.fkidcampo = scheda_modificata.fk_d2c_id AND
		dyn2_modelli_campi_collegati.fkidmodello <> scheda_modificata.fk_d2mt_id AND
		dyn2_modelli_campi_collegati.fkidmodello IN ({string.Join(",", listaSchedeDellaDomanda)})
WHERE 
	scheda_modificata.fk_d2c_id IS NOT NULL AND
	scheda_modificata.idcomune = {{0}} AND
	scheda_modificata.fk_d2mt_id = {{1}}";

            FormattableString fmt = FormattableStringFactory.Create(sql, this._idComune, idModello);

            return this._dataBase.ExecuteReader(fmt, dr => dr.GetInt("fkidmodello")!.Value);
        }
    }
}
