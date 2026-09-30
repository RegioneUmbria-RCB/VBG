using Init.SIGePro.Data;
using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.GestioneStradario.RicercaStradario
{
    public class StradarioRepository : IStradarioRepository
    {
        private readonly DataBase _db;
        private readonly string _idComune;
        private readonly ILog _log = LogManager.GetLogger(typeof(StradarioRepository));

        public StradarioRepository(DataBase db, string idComune)
        {
            this._db = db;
            this._idComune = idComune;
        }

        public IEnumerable<Stradario> FindByMatchParziale(CondizioniRicercaStradarioPerDescrizione condizioniRicerca, bool escludiDisabilitate)
        {
            const string prefissoParametro = "parametro";

            if (!condizioniRicerca.MatchParziali.Any())
            {
                return Enumerable.Empty<Stradario>();
            }

            var idx = 0;

            var partiWhere = condizioniRicerca.MatchParziali
                                              .Select(x => $"{this._db.Specifics.UCaseFunction("DESCRIZIONE")} like {this._db.Specifics.QueryParameterName(prefissoParametro + idx++)}")
                                              .ToArray();

            var where = String.Join($" {condizioniRicerca.SeparatoreCondizioniWhere} ", partiWhere);

            var sql = $@"select IDCOMUNE,
								PREFISSO, 
								CODICESTRADARIO,
								DESCRIZIONE,
								LOCFRAZ,
								CODVIARIO
							from 
								stradario 
							where 
								{(escludiDisabilitate ? "datavalidita is null and" : "")}
								idcomune={this._db.Specifics.QueryParameterName("idComune")}";

            if (!String.IsNullOrEmpty(condizioniRicerca.ComuneLocalizzazione))
            {
                sql += $" and (comune_localizzazione = {this._db.Specifics.QueryParameterName("comuneLocalizzazione")} or comune_localizzazione is null)";
            }


            if (!String.IsNullOrEmpty(condizioniRicerca.CodiceComune))
            {
                sql += $" and {this._db.Specifics.NvlFunction("codiceComune", condizioniRicerca.CodiceComune)} = {this._db.Specifics.QueryParameterName("codiceComune")}";
            }

            sql += $" and ({where}) order by descrizione asc";



            return this._db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComune", this._idComune);

                    if (!String.IsNullOrEmpty(condizioniRicerca.ComuneLocalizzazione))
                    {
                        mp.AddParameter("comuneLocalizzazione", condizioniRicerca.ComuneLocalizzazione);
                    }

                    if (!String.IsNullOrEmpty(condizioniRicerca.CodiceComune))
                    {
                        mp.AddParameter("codiceComune", condizioniRicerca.CodiceComune);
                    }

                    idx = 0;

                    var parziali = condizioniRicerca.MatchParziali.Select(x => condizioniRicerca.ValoreDaTipoLike(x.ToUpperInvariant()));

                    foreach (var parziale in parziali)
                    {
                        mp.AddParameter($"{prefissoParametro}{idx++}", parziale);
                    }

                    this._log.Debug($"Query ricerca stradario: {sql}\r\n[parametri]: comuneLocalizzazione={condizioniRicerca.ComuneLocalizzazione}, codiceComune={condizioniRicerca.CodiceComune}, matchParziali={String.Join("--", parziali.ToArray())}");
                },
                dr => new Stradario
                {
                    IDCOMUNE = dr["IDCOMUNE"].ToString(),
                    PREFISSO = dr["PREFISSO"].ToString(),
                    CODICESTRADARIO = dr["CODICESTRADARIO"].ToString(),
                    DESCRIZIONE = dr["DESCRIZIONE"].ToString(),
                    LOCFRAZ = dr["LOCFRAZ"].ToString(),
                    CODVIARIO = dr["CODVIARIO"].ToString()
                });

        }
    }
}
