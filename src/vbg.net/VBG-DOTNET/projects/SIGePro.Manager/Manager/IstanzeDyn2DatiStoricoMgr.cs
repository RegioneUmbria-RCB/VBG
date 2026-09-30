using Init.SIGePro.Data;
using Init.Utils;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Reflection;
using VBG.DatiDinamici;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class IstanzeDyn2DatiStoricoMgr //: IIstanzeDyn2DatiStoricoManager
    {
        internal List<IstanzeDyn2DatiStorico> GetValoriNoIndiceNoVersione(string idComune, int idIstanza, int idCampo)
        {
            IstanzeDyn2DatiStorico filtro = new IstanzeDyn2DatiStorico();
            filtro.Idcomune = idComune;
            filtro.Codiceistanza = idIstanza;
            filtro.FkD2cId = idCampo;

            return this.GetList(filtro);
        }

        public List<int> LeggiIndiciVersione(string idComune, int codiceIstanza, int idModello, int idVersione)
        {
            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                string sql = @"SELECT DISTINCT 
									indice 
								FROM 
									istanzedyn2dati_storico
								WHERE
									idcomune = {0} AND
									codiceistanza = {1} AND
									fk_d2mt_id = {2} AND
									idVersione = {3} order by indice asc";

                sql = String.Format(sql, this.db.Specifics.QueryParameterName("idcomune"),
                                            this.db.Specifics.QueryParameterName("codiceistanza"),
                                            this.db.Specifics.QueryParameterName("fk_d2mt_id"),
                                            this.db.Specifics.QueryParameterName("idVersione"));

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idcomune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("codiceistanza", codiceIstanza));
                    cmd.Parameters.Add(this.db.CreateParameter("fk_d2mt_id", idModello));
                    cmd.Parameters.Add(this.db.CreateParameter("idVersione", idVersione));

                    List<int> rVal = new List<int>();

                    using (IDataReader dr = cmd.ExecuteReader())
                    {
                        while (dr.Read())
                            rVal.Add(Convert.ToInt32(dr[0]));
                    }

                    return rVal;
                }

            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }
        }

        #region IIstanzeDyn2DatiStoricoManager Members

        public IEnumerable<IstanzeDyn2DatiStorico> GetValoriCampiDaIdModello(string idComune, int codiceIstanza, int idModello, int indiceModello, int idVersioneStorico)
        {
            var sql = $@"select 
							istanzedyn2dati_storico.* 
						from 
							istanzedyn2dati_storico
						WHERE
							istanzedyn2dati_storico.idcomune = {this.db.Specifics.QueryParameterName("idComune")} AND				
							istanzedyn2dati_storico.fk_d2mt_id = {this.db.Specifics.QueryParameterName("idModello")} and
							istanzedyn2dati_storico.codiceistanza = {this.db.Specifics.QueryParameterName("codiceIstanza")} AND
                            istanzedyn2dati_storico.idversione = {this.db.Specifics.QueryParameterName("idVersioneStorico")} and
							istanzedyn2dati_storico.indice = {this.db.Specifics.QueryParameterName("indiceModello")}
						order by 
							istanzedyn2dati_storico.indice_molteplicita asc";

            return this.db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComune", idComune);
                    mp.AddParameter("idModello", idModello);
                    mp.AddParameter("codiceIstanza", codiceIstanza);
                    mp.AddParameter("idVersioneStorico", idVersioneStorico);
                    mp.AddParameter("indiceModello", indiceModello);
                },
                dr => new IstanzeDyn2DatiStorico
                {
                    Codiceistanza = codiceIstanza,
                    FkD2cId = dr.GetInt("fk_d2c_id"),
                    FkD2mtId = idModello,
                    Idcomune = idComune,
                    Idversione = idVersioneStorico,
                    Indice = indiceModello,
                    IndiceMolteplicita = dr.GetInt("indice_molteplicita"),
                    Valore = dr.GetString("valore")
                });
        }

        public void SalvaStoricoModello(string idComune, int codiceIstanza, ModelloDinamicoBase modello)
        {
            var istanzeMgr = new IstanzeDyn2DatiMgr(this.db);

            using (CodeProfiler.Track(MethodInfo.GetCurrentMethod()))
            {
                // Carico le righe modificate
                List<IstanzeDyn2DatiStorico> righeStorico = new List<IstanzeDyn2DatiStorico>();

                foreach (var rigaModello in modello.Righe)
                {
                    for (int j = 0; j < rigaModello.NumeroColonne; j++)
                    {
                        if (rigaModello[j] == null) continue;
                        if (rigaModello[j].Id < 0) continue;

                        var valoriDb = istanzeMgr.GetValoriCampoNoIndice(idComune, codiceIstanza, rigaModello[j].Id);

                        int indiceMin = int.MaxValue;

                        if (valoriDb.Count == 0)
                            indiceMin = 0;

                        foreach (var valoreDb in valoriDb)
                        {
                            var fkD2cId = valoreDb.FkD2cId.Value;
                            var indice = valoreDb.Indice.GetValueOrDefault(0);
                            var indiceMolteplicita = valoreDb.IndiceMolteplicita.GetValueOrDefault(0);
                            var valore = valoreDb.Valore;
                            var valoreDecodificato = valoreDb.Valoredecodificato;

                            if (indiceMin > indiceMolteplicita)
                                indiceMin = indiceMolteplicita;

                            IstanzeDyn2DatiStorico riga = new IstanzeDyn2DatiStorico
                            {
                                Idcomune = idComune,
                                Codiceistanza = codiceIstanza,
                                FkD2mtId = modello.IdModello,
                                FkD2cId = fkD2cId,
                                Indice = indice,
                                IndiceMolteplicita = indiceMolteplicita,
                                Valore = String.IsNullOrEmpty(valoreDecodificato) ? valore : valoreDecodificato

                            };
                            righeStorico.Add(riga);
                        }
                    }
                }

                // Se non è stata caricata nessuna riga allora non salvo la versione perchè sarebbe un modello vuoto
                if (righeStorico.Count == 0)
                    return;

                // Preparo il salvataggio della testata
                var testataStoricoMgr = new IstanzeDyn2ModelliTStoricoMgr(this.db);
                var righeStoricoMgr = new IstanzeDyn2DatiStoricoMgr(this.db);

                // Salvo una nuova riga in IstanzeDyn2Modellit_storico
                var testataStorico = new IstanzeDyn2ModelliTStorico
                {
                    Idcomune = idComune,
                    Codiceistanza = codiceIstanza,
                    FkD2mtId = modello.IdModello
                };

                testataStorico = testataStoricoMgr.Insert(testataStorico);

                for (int i = 0; i < righeStorico.Count; i++)
                {
                    righeStorico[i].Idversione = testataStorico.Idversione;

                    righeStoricoMgr.Insert(righeStorico[i]);
                }
            }
        }
        #endregion
    }
}
