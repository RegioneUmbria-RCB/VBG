using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Utils;
using VBG.DatiDinamici.VisibilitaCampi;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.Posteggi
{
    public class PosteggiDyn2DatiRepository: IDyn2DatiRepository
    {
        public class ValoreCampo : IValoreCampo
        {
            public int IdCampo { get; set; }
            public int? Indice { get; set; }
            public int? IndiceMolteplicita { get; set; }
            public string Valore { get; set; }
            public string Valoredecodificato { get; set; }
        }

        private readonly DataBase _database;
        private readonly int _idPosteggio;
        private readonly string _idComune;

        public PosteggiDyn2DatiRepository(DataBase database, int idPosteggio, string idComune)
        {
            _database = database;
            _idPosteggio = idPosteggio;
            _idComune = idComune;
        }

        public void EliminaValoriCampi(DatiIdentificativiModello modello, IEnumerable<DatiIdentificativiCampo> campiDaEliminare)
        {
            bool commitTrans = !_database.IsInTransaction;

            if (commitTrans)
            {
                _database.BeginTransaction();
            }

            try
            {
                EliminaValoriCampiInternal(modello, campiDaEliminare);

                if (commitTrans)
                {
                    _database.CommitTransaction();
                }
            }
            catch (Exception)
            {
                if (commitTrans)
                {
                    _database.RollbackTransaction();
                }
                throw;
            }
        }

        private void EliminaValoriCampiInternal(DatiIdentificativiModello modello, IEnumerable<DatiIdentificativiCampo> campiDaEliminare)
        {
            foreach (var campo in campiDaEliminare)
            {
                var sql = $@"delete from mercati_ddyn2dati where 
                                        idcomune={_database.Specifics.QueryParameterName("idcomune")} and 
                                        idPosteggio={_database.Specifics.QueryParameterName("idPosteggio")} and
                                        fk_d2c_id = {_database.Specifics.QueryParameterName("idCampo")} and
                                        indice = {_database.Specifics.QueryParameterName("indice")}";

                _database.ExecuteNonQuery(sql, mp =>
                {
                    mp.AddParameter("idcomune", _idComune);
                    mp.AddParameter("idPosteggio", _idPosteggio);
                    mp.AddParameter("idCampo", campo.Id);
                    mp.AddParameter("indice", modello.IndiceModello);
                });
            }
        }

        public SerializableDictionary<int, IEnumerable<IValoreCampo>> GetValoriCampiDaIdModello(int idModello, int indiceModello)
        {
            var sql = $@"select 
							mercati_ddyn2dati.* 
						from 
							mercati_ddyn2dati,
							dyn2_modellid
						WHERE
							mercati_ddyn2dati.idcomune = dyn2_modellid.idcomune AND
							mercati_ddyn2dati.fk_d2c_id = dyn2_modellid.fk_d2c_id and
							mercati_ddyn2dati.idcomune = {_database.Specifics.QueryParameterName("idcomune")} and									
							dyn2_modellid.fk_d2mt_id = {_database.Specifics.QueryParameterName("idModello")} and
							mercati_ddyn2dati.idPosteggio = {_database.Specifics.QueryParameterName("idPosteggio")} and
							mercati_ddyn2dati.indice = {_database.Specifics.QueryParameterName("indiceModello")}
						order by 
							indice_molteplicita asc";

            var dati = _database.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idcomune", _idComune);
                    mp.AddParameter("idModello", idModello);
                    mp.AddParameter("idPosteggio", _idPosteggio);
                    mp.AddParameter("indiceModello", indiceModello);
                },
                dr => new ValoreCampo
                {
                    IdCampo = dr.GetInt("fk_d2c_id").Value,
                    Indice = dr.GetInt("indice"),
                    IndiceMolteplicita = dr.GetInt("indice_molteplicita"),
                    Valore = dr.GetString("Valore"),
                    Valoredecodificato = dr.GetString("valoredecodificato"),
                });

            var dict = dati.GroupBy(x => x.IdCampo, x => (IValoreCampo)x)
                           .ToDictionary(x => x.Key, x => x.ToList().AsEnumerable());

            return new SerializableDictionary<int, IEnumerable<IValoreCampo>>(dict);
        }

        public void SalvaValoriCampi(DatiIdentificativiModello idModello, IEnumerable<CampoDaSalvare> campiDaSalvare)
        {
            // Elimino tutti i vecchi valori dei campi e li inserisco di nuovo
            bool commitTrans = !_database.IsInTransaction;

            if (commitTrans)
            {
                _database.BeginTransaction();
            }

            try
            {
                EliminaValoriCampiInternal(idModello, campiDaSalvare.Select(x => new DatiIdentificativiCampo(x.Id)));

                var sql = $@"INSERT INTO mercati_ddyn2dati (idcomune, idPosteggio, fk_d2c_id, valore, indice, valoredecodificato, indice_molteplicita) VALUES 
                                (
                                    {_database.Specifics.QueryParameterName("idComune")}, 
                                    {_database.Specifics.QueryParameterName("idPosteggio")}, 
                                    {_database.Specifics.QueryParameterName("idCampo")}, 
                                    {_database.Specifics.QueryParameterName("valore")}, 
                                    {_database.Specifics.QueryParameterName("indice")}, 
                                    {_database.Specifics.QueryParameterName("valoreDecodificato")}, 
                                    {_database.Specifics.QueryParameterName("indiceMolteplicita")}
                                )";

                foreach (var campo in campiDaSalvare)
                {
                    for (int i = 0; i < campo.ListaValori.Count; i++)
                    {
                        var val = campo.ListaValori[i];

                        if (String.IsNullOrEmpty(val.Valore) && String.IsNullOrEmpty(val.ValoreDecodificato))
                        {
                            continue;
                        }

                        _database.ExecuteNonQuery(sql, mp =>
                        {
                            mp.AddParameter("idComune", _idComune);
                            mp.AddParameter("idPosteggio", _idPosteggio);
                            mp.AddParameter("idCampo", campo.Id);
                            mp.AddParameter("valore", val.Valore);
                            mp.AddParameter("indice", idModello.IndiceModello);
                            mp.AddParameter("valoreDecodificato", val.ValoreDecodificato);
                            mp.AddParameter("indiceMolteplicita", i);
                        });
                    }
                }

                if (commitTrans)
                {
                    _database.CommitTransaction();
                }
            }
            catch (Exception)
            {
                if (commitTrans)
                {
                    _database.RollbackTransaction();
                }
                throw;
            }

        }

        public void SalvaCampiNonVisibili(DatiIdentificativiModello idModello, IEnumerable<IdValoreCampo> enumerable)
        {
        }
    }
}
