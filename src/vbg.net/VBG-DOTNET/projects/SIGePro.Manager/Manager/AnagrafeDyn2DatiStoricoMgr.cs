
using Init.SIGePro.Data;
//using Init.SIGePro.DatiDinamici.Interfaces.Anagrafe;
using VBG.DatiDinamici;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class AnagrafeDyn2DatiStoricoMgr
    {


        public List<int> LeggiIndiciVersione(string idComune, int codiceAnagrafe, int idModello, int idVersione)
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
									anagrafedyn2dati_storico
								WHERE
									idcomune = {0} AND
									codiceanagrafe = {1} AND
									fk_d2mt_id = {2} AND
									idVersione = {3} order by indice asc";

                sql = String.Format(sql, this.db.Specifics.QueryParameterName("idcomune"),
                                            this.db.Specifics.QueryParameterName("codiceanagrafe"),
                                            this.db.Specifics.QueryParameterName("fk_d2mt_id"),
                                            this.db.Specifics.QueryParameterName("idVersione"));

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idcomune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("codiceanagrafe", codiceAnagrafe));
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

        #region IAnagrafeDyn2DatiStoricoManager Members

        public IEnumerable<AnagrafeDyn2DatiStorico> GetValoriCampiDaIdModello(string idComune, int codiceAnagrafe, int idModello, int indiceModello, int idVersioneStorico)
        {
            var sql = $@"select 
							anagrafedyn2dati_storico.* 
						from 
							anagrafedyn2dati_storico
						WHERE
							anagrafedyn2dati_storico.idcomune = {this.db.Specifics.QueryParameterName("idComune")} AND				
							anagrafedyn2dati_storico.fk_d2mt_id = {this.db.Specifics.QueryParameterName("idModello")} and
							anagrafedyn2dati_storico.codiceanagrafe = {this.db.Specifics.QueryParameterName("codiceAnagrafe")} AND
                            anagrafedyn2dati_storico.idversione = {this.db.Specifics.QueryParameterName("idVersioneStorico")} and
							anagrafedyn2dati_storico.indice = {this.db.Specifics.QueryParameterName("indiceModello")}
						order by 
							anagrafedyn2dati_storico.indice_molteplicita asc";

            return this.db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComune", idComune);
                    mp.AddParameter("idModello", idModello);
                    mp.AddParameter("codiceAnagrafe", codiceAnagrafe);
                    mp.AddParameter("idVersioneStorico", idVersioneStorico);
                    mp.AddParameter("indiceModello", indiceModello);
                },
                dr => new AnagrafeDyn2DatiStorico
                {
                    Codiceanagrafe = codiceAnagrafe,
                    FkD2cId = dr.GetInt("fk_d2c_id"),
                    FkD2mtId = idModello,
                    Idcomune = idComune,
                    Idversione = idVersioneStorico,
                    Indice = indiceModello,
                    IndiceMolteplicita = dr.GetInt("indice_molteplicita"),
                    Valore = dr.GetString("valore")
                });
        }


        //      public List<IAnagrafeDyn2DatiStorico> GetValoriCampo(string idComune, int idAnagrafe, int idCampo, int indiceCampo, int idVersione)
        //{
        //	AnagrafeDyn2DatiStorico filtro = new AnagrafeDyn2DatiStorico();
        //	filtro.Idcomune = idComune;
        //	filtro.Codiceanagrafe = idAnagrafe;
        //	filtro.FkD2cId = idCampo;
        //	filtro.Indice = indiceCampo;
        //	filtro.Idversione = idVersione;
        //	filtro.OrderBy = "indice_molteplicita asc";

        //	var lista = GetList(filtro);

        //	var rVal = new List<IAnagrafeDyn2DatiStorico>();

        //	lista.ForEach(x => rVal.Add(x));

        //	return rVal;
        //}


        public void SalvaStoricoModello(string idComune, int codiceAnagrafe, ModelloDinamicoBase modello)
        {
            // Carico le righe modificate
            var righeStorico = new List<AnagrafeDyn2DatiStorico>();
            var mgr = new AnagrafeDyn2DatiMgr(this.db);

            foreach (var rigaModello in modello.Righe)
            {
                for (int j = 0; j < rigaModello.NumeroColonne; j++)
                {
                    if (rigaModello[j] == null) continue;

                    var valoriDb = mgr.GetValoriCampoNoIndice(modello.IdComune, codiceAnagrafe, rigaModello[j].Id);

                    int indiceMin = int.MaxValue;

                    if (valoriDb.Count == 0)
                        indiceMin = 0;

                    for (int idxCampo = 0; idxCampo < valoriDb.Count; idxCampo++)
                    {
                        var fkD2cId = valoriDb[idxCampo].FkD2cId.Value;
                        var indice = valoriDb[idxCampo].Indice.GetValueOrDefault(0);
                        var indiceMolteplicita = valoriDb[idxCampo].IndiceMolteplicita.GetValueOrDefault(0);
                        var valore = valoriDb[idxCampo].Valore;
                        var valoreDecodificato = valoriDb[idxCampo].Valoredecodificato;

                        if (indiceMin > indiceMolteplicita)
                            indiceMin = indiceMolteplicita;

                        var riga = new AnagrafeDyn2DatiStorico
                        {
                            Idcomune = modello.IdComune,
                            Codiceanagrafe = codiceAnagrafe,
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
            var testataStoricoMgr = new AnagrafeDyn2ModelliTStoricoMgr(this.db);
            var righeStoricoMgr = new AnagrafeDyn2DatiStoricoMgr(this.db);

            // Salvo una nuova riga in AnagrafeDyn2Modellit_storico
            var testataStorico = new AnagrafeDyn2ModelliTStorico
            {
                Idcomune = modello.IdComune,
                Codiceanagrafe = codiceAnagrafe,
                FkD2mtId = modello.IdModello
            };

            testataStorico = testataStoricoMgr.Insert(testataStorico);

            for (int i = 0; i < righeStorico.Count; i++)
            {
                righeStorico[i].Idversione = testataStorico.Idversione;

                righeStoricoMgr.Insert(righeStorico[i]);
            }
        }
        #endregion
    }
}
