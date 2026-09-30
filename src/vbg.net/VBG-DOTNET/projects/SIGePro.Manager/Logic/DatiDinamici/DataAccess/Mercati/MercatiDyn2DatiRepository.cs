using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Utils;
using VBG.DatiDinamici.VisibilitaCampi;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.Mercati
{
    internal class MercatiDyn2DatiRepository : IDyn2DatiRepository
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
        private readonly int _codiceMercato;
        private readonly string _idComune;

        public MercatiDyn2DatiRepository(DataBase database, int codiceMercato, string idComune)
        {
            this._database = database;
            this._codiceMercato = codiceMercato;
            this._idComune = idComune;
        }

        public void EliminaValoriCampi(DatiIdentificativiModello modello, IEnumerable<DatiIdentificativiCampo> campiDaEliminare)
        {
            bool commitTrans = !this._database.IsInTransaction;

            if (commitTrans)
            {
                this._database.BeginTransaction();
            }

            try
            {
                this.EliminaValoriCampiInternal(modello, campiDaEliminare);

                if (commitTrans)
                {
                    this._database.CommitTransaction();
                }
            }
            catch (Exception)
            {
                if (commitTrans)
                {
                    this._database.RollbackTransaction();
                }
                throw;
            }
        }

        private void EliminaValoriCampiInternal(DatiIdentificativiModello modello, IEnumerable<DatiIdentificativiCampo> campiDaEliminare)
        {
            foreach (var campo in campiDaEliminare)
            {
                var sql = $@"delete from mercatidyn2dati where 
                                        idcomune={this._database.Specifics.QueryParameterName("idcomune")} and 
                                        codicemercato={this._database.Specifics.QueryParameterName("codicemercato")} and
                                        fk_d2c_id = {this._database.Specifics.QueryParameterName("idCampo")} and
                                        indice = {this._database.Specifics.QueryParameterName("indice")}";

                this._database.ExecuteNonQuery(sql, mp =>
                {
                    mp.AddParameter("idcomune", this._idComune);
                    mp.AddParameter("codicemercato", this._codiceMercato);
                    mp.AddParameter("idCampo", campo.Id);
                    mp.AddParameter("indice", modello.IndiceModello);
                });
            }
        }

        public SerializableDictionary<int, IEnumerable<IValoreCampo>> GetValoriCampiDaIdModello(int idModello, int indiceModello)
        {
            var sql = $@"select 
							mercatiDyn2Dati.* 
						from 
							mercatiDyn2Dati,
							dyn2_modellid
						WHERE
							mercatiDyn2Dati.idcomune = dyn2_modellid.idcomune AND
							mercatiDyn2Dati.fk_d2c_id = dyn2_modellid.fk_d2c_id and
							mercatiDyn2Dati.idcomune = {this._database.Specifics.QueryParameterName("idcomune")} and									
							dyn2_modellid.fk_d2mt_id = {this._database.Specifics.QueryParameterName("idModello")} and
							mercatiDyn2Dati.codicemercato = {this._database.Specifics.QueryParameterName("codiceMercato")} and
							mercatiDyn2Dati.indice = {this._database.Specifics.QueryParameterName("indiceModello")}
						order by 
							indice_molteplicita asc";

            var dati = this._database.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idcomune", this._idComune);
                    mp.AddParameter("idModello", idModello);
                    mp.AddParameter("codiceMercato", this._codiceMercato);
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
            bool commitTrans = !this._database.IsInTransaction;

            if (commitTrans)
            {
                this._database.BeginTransaction();
            }

            try
            {
                this.EliminaValoriCampiInternal(idModello, campiDaSalvare.Select(x => new DatiIdentificativiCampo(x.Id)));

                var sql = $@"INSERT INTO mercatidyn2dati (idcomune, codicemercato, fk_d2c_id, valore, indice, valoredecodificato, indice_molteplicita) VALUES 
                                (
                                    {this._database.Specifics.QueryParameterName("idComune")}, 
                                    {this._database.Specifics.QueryParameterName("codiceMercato")}, 
                                    {this._database.Specifics.QueryParameterName("idCampo")}, 
                                    {this._database.Specifics.QueryParameterName("valore")}, 
                                    {this._database.Specifics.QueryParameterName("indice")}, 
                                    {this._database.Specifics.QueryParameterName("valoreDecodificato")}, 
                                    {this._database.Specifics.QueryParameterName("indiceMolteplicita")}
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

                        this._database.ExecuteNonQuery(sql, mp =>
                        {
                            mp.AddParameter("idComune", this._idComune);
                            mp.AddParameter("codiceMercato", this._codiceMercato);
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
                    this._database.CommitTransaction();
                }
            }
            catch (Exception)
            {
                if (commitTrans)
                {
                    this._database.RollbackTransaction();
                }
                throw;
            }

        }

        public void SalvaCampiNonVisibili(DatiIdentificativiModello idModello, IEnumerable<IdValoreCampo> enumerable)
        {
        }
    }
}