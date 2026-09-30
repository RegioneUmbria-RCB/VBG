using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Interfaces;
using Init.SIGePro.Manager.Logic.GestioneSchedeAttivita.Eventi;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Vbg.EventBus.Abstractions;

namespace Init.SIGePro.Manager.Logic.GestioneSchedeAttivita
{
    public class SchedeDinamicheAttivitaService : ISchedeDinamicheAttivitaService
    {
        private readonly AuthenticationInfo _authenticationInfo;
        private readonly IEventPublisher _eventPublisher;

        public SchedeDinamicheAttivitaService(AuthenticationInfo authenticationInfo, IEventPublisher eventPublisher)
        {
            _authenticationInfo = authenticationInfo;
            _eventPublisher = eventPublisher;
        }

        public void EliminaValoriCampi(int idAttivita, DatiIdentificativiModello modello, IEnumerable<DatiIdentificativiCampo> campiDaEliminare)
        {
            using (var db = this._authenticationInfo.CreateDatabase())
            {
                try
                {
                    db.BeginTransaction();

                    foreach (var campo in campiDaEliminare)
                    {
                        var sql = $@"delete from i_attivitadyn2dati where 
                                                idcomune = {db.Specifics.QueryParameterName("idComune")} and 
                                                fk_ia_id = {db.Specifics.QueryParameterName("idAttivita")} and 
                                                fk_d2c_id = {db.Specifics.QueryParameterName("idCampo")} and 
                                                indice = {db.Specifics.QueryParameterName("indice")}";

                        db.ExecuteNonQuery(sql, mp =>
                        {
                            mp.AddParameter("idComune", this._authenticationInfo.IdComune);
                            mp.AddParameter("idAttivita", idAttivita);
                            mp.AddParameter("idCampo", campo.Id);
                            mp.AddParameter("indice", modello.IndiceModello);
                        });
                    }

                    db.CommitTransaction();
                }
                catch (Exception)
                {
                    db.RollbackTransaction();

                    throw;
                }
            }
        }

        public IEnumerable<IAttivitaDyn2Dati> GetValoriCampiDaIdModello(int idAttivita, int idModello, int indiceModello)
        {
            using (var db = this._authenticationInfo.CreateDatabase())
            {
                string sql = $@"select 
        							i_attivitadyn2dati.* 
        						from 
        							dyn2_modellid 
                                        INNER JOIN  i_attivitadyn2dati ON
                                            i_attivitadyn2dati.idcomune = dyn2_modellid.idcomune AND
        									i_attivitadyn2dati.fk_d2c_id = dyn2_modellid.fk_d2c_id
        						WHERE
        							dyn2_modellid.idcomune = {db.Specifics.QueryParameterName("idComune")} and									
        							dyn2_modellid.fk_d2mt_id = {db.Specifics.QueryParameterName("idModello")} and
        							i_attivitadyn2dati.fk_ia_id = {db.Specifics.QueryParameterName("idAttivita")} and
        							i_attivitadyn2dati.indice = {db.Specifics.QueryParameterName("indiceModello")}
        						order by 
        							i_attivitadyn2dati.indice_molteplicita";

                return db.ExecuteReader(sql,
                    mp =>
                    {
                        mp.AddParameter("idComune", this._authenticationInfo.IdComune);
                        mp.AddParameter("idModello", idModello);
                        mp.AddParameter("idAttivita", idAttivita);
                        mp.AddParameter("indiceModello", indiceModello);
                    },
                    dr => new IAttivitaDyn2Dati
                    {
                        FkIaId = idAttivita,
                        FkD2cId = dr.GetInt("fk_d2c_id"),
                        Idcomune = this._authenticationInfo.IdComune,
                        Indice = indiceModello,
                        IndiceMolteplicita = dr.GetInt("indice_molteplicita"),
                        Valore = dr.GetString("valore"),
                        Valoredecodificato = dr.GetString("valoredecodificato")
                    });
            }

        }

        public IEnumerable<IAttivitaDyn2DatiStorico> GetValoriCampiDaIdModelloStorico(int idAttivita, int idModello, int indiceModello, int idVersioneStorico)
        {
            using (var db = this._authenticationInfo.CreateDatabase())
            {
                var sql = $@"select 
							i_attivitadyn2dati_storico.* 
						from 
							i_attivitadyn2dati_storico
						WHERE
							i_attivitadyn2dati_storico.idcomune = {db.Specifics.QueryParameterName("idComune")} AND				
							i_attivitadyn2dati_storico.fk_d2mt_id = {db.Specifics.QueryParameterName("idModello")} and
							i_attivitadyn2dati_storico.fk_ia_id = {db.Specifics.QueryParameterName("idAttivita")} AND
                            i_attivitadyn2dati_storico.idversione = {db.Specifics.QueryParameterName("idVersioneStorico")} and
							i_attivitadyn2dati_storico.indice = {db.Specifics.QueryParameterName("indiceModello")}
						order by 
							i_attivitadyn2dati_storico.indice_molteplicita asc";

                return db.ExecuteReader(sql,
                    mp =>
                    {
                        mp.AddParameter("idComune", this._authenticationInfo.IdComune);
                        mp.AddParameter("idModello", idModello);
                        mp.AddParameter("idAttivita", idAttivita);
                        mp.AddParameter("idVersioneStorico", idVersioneStorico);
                        mp.AddParameter("indiceModello", indiceModello);
                    },
                    dr => new IAttivitaDyn2DatiStorico
                    {
                        FkIaId = idAttivita,
                        FkD2cId = dr.GetInt("fk_d2c_id"),
                        FkD2mtId = idModello,
                        Idcomune = this._authenticationInfo.IdComune,
                        Idversione = idVersioneStorico,
                        Indice = indiceModello,
                        IndiceMolteplicita = dr.GetInt("indice_molteplicita"),
                        Valore = dr.GetString("valore")
                    });
            }
        }

        public void SalvaStoricoModello(int idAttivita, ModelloDinamicoBase modelloDinamicoBase)
        {
            using (var db = this._authenticationInfo.CreateDatabase())
            {
                try
                {
                    db.BeginTransaction();

                    var idComune = this._authenticationInfo.IdComune;

                    // Carico le righe modificate
                    List<IAttivitaDyn2DatiStorico> righeStorico = new List<IAttivitaDyn2DatiStorico>();
                    // var mgr = new IAttivitaDyn2DatiMgr(this.db);

                    foreach (var riga in modelloDinamicoBase.Righe)
                    {
                        for (int j = 0; j < riga.NumeroColonne; j++)
                        {
                            if (riga[j] == null) continue;

                            var valoriDb = this.GetValoriCampoNoIndice(db, idAttivita, riga[j].Id);

                            int indiceMin = int.MaxValue;

                            if (valoriDb.Count() == 0)
                                indiceMin = 0;

                            foreach (var valoreCampo in valoriDb)
                            {
                                var fkD2cId = valoreCampo.FkD2cId.Value;
                                var indice = valoreCampo.Indice.GetValueOrDefault(0);
                                var indiceMolteplicita = valoreCampo.IndiceMolteplicita.GetValueOrDefault(0);
                                var valore = valoreCampo.Valore;
                                var valoreDecodificato = valoreCampo.Valoredecodificato;

                                if (indiceMin > indiceMolteplicita)
                                    indiceMin = indiceMolteplicita;

                                var rigaStorico = new IAttivitaDyn2DatiStorico
                                {
                                    Idcomune = idComune,
                                    FkIaId = idAttivita,
                                    FkD2mtId = modelloDinamicoBase.IdModello,
                                    FkD2cId = fkD2cId,
                                    Indice = indice,
                                    IndiceMolteplicita = indiceMolteplicita,
                                    Valore = String.IsNullOrEmpty(valoreDecodificato) ? valore : valoreDecodificato

                                };
                                righeStorico.Add(rigaStorico);
                            }
                        }
                    }

                    // Se non è stata caricata nessuna riga allora non salvo la versione perchè sarebbe un modello vuoto
                    if (righeStorico.Count == 0)
                        return;

                    // Preparo il salvataggio della testata
                    var testataStoricoMgr = new IAttivitaDyn2ModelliTStoricoMgr(db);
                    var righeStoricoMgr = new IAttivitaDyn2DatiStoricoMgr(db);

                    // Salvo una nuova riga in IAttivitaDyn2Modellit_storico
                    var testataStorico = new IAttivitaDyn2ModelliTStorico
                    {
                        Idcomune = idComune,
                        FkIaId = idAttivita,
                        FkD2mtId = modelloDinamicoBase.IdModello
                        // TODO: Perché non imposto l'id versione???
                    };

                    testataStorico = testataStoricoMgr.Insert(testataStorico);

                    for (int i = 0; i < righeStorico.Count; i++)
                    {
                        righeStorico[i].Idversione = testataStorico.Idversione;

                        righeStoricoMgr.Insert(righeStorico[i]);
                    }
                }
                catch (Exception)
                {
                    db.RollbackTransaction();

                    throw;
                }
            }
        }

        private IEnumerable<IAttivitaDyn2Dati> GetValoriCampoNoIndice(DataBase db, int idAttivita, int id)
        {
            var sql = $@"select
                            *
                        from
                            i_attivitadyn2dati
                        where
                            idcomune = {db.Specifics.QueryParameterName("idComune")} and
                            fk_ia_id = {db.Specifics.QueryParameterName("idAttivita")} and
                            fk_d2c_id = {db.Specifics.QueryParameterName("id")}
                        order by 
                            indice_molteplicita asc";

            return db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComune", this._authenticationInfo.IdComune);
                    mp.AddParameter("idAttivita", idAttivita);
                    mp.AddParameter("id", id);
                },
                dr => new IAttivitaDyn2Dati
                {
                    FkD2cId = dr.GetInt("fk_d2c_id"),
                    FkIaId = dr.GetInt("fk_ia_id"),
                    Idcomune = dr.GetString("idComune"),
                    Indice = dr.GetInt("indice"),
                    IndiceMolteplicita = dr.GetInt("indice_molteplicita"),
                    Valore = dr.GetString("valore"),
                    Valoredecodificato = dr.GetString("valoredecodificato")
                });
        }

        public void SalvaValoriCampi(int idAttivita, DatiIdentificativiModello modello, IEnumerable<CampoDaSalvare> campiDaSalvare)
        {
            using (var db = this._authenticationInfo.CreateDatabase())
            {
                try
                {
                    db.BeginTransaction();

                    var indiceModello = modello.IndiceModello;

                    // Elimino tutti i valori del campo per la scheda corrente
                    foreach (var campo in campiDaSalvare)
                        EliminaValoriMultipliCampo(db, idAttivita, campo.Id, indiceModello);

                    // Ricreo tutti i valori dei campi che non siano == ""
                    foreach (var campo in campiDaSalvare)
                    {
                        for (int indiceMolteplicita = 0; indiceMolteplicita < campo.ListaValori.Count; indiceMolteplicita++)
                        {
                            var valore = campo.ListaValori[indiceMolteplicita].Valore;
                            var valoreDecodificato = campo.ListaValori[indiceMolteplicita].ValoreDecodificato;

                            if (String.IsNullOrEmpty(valore.Trim()))
                                continue;

                            if (String.IsNullOrEmpty(valoreDecodificato))
                                valoreDecodificato = valore;

                            var cls = new IAttivitaDyn2Dati
                            {
                                Idcomune = this._authenticationInfo.IdComune,
                                FkD2cId = campo.Id,
                                FkIaId = idAttivita,
                                Valore = valore,
                                Valoredecodificato = valoreDecodificato,
                                Indice = indiceModello,
                                IndiceMolteplicita = indiceMolteplicita
                            };

                            db.Insert(cls);
                        }
                    }

                    db.CommitTransaction();
                }
                catch (Exception ex)
                {
                    db.RollbackTransaction();

                    throw;
                }

                // Notifico al servizio di snapshotting che la scheda è stata modificata
                this._eventPublisher.Publish(new SchedaDinamicaAttivitaSalvata(idAttivita, modello.IdModello));
            }
        }

        private void EliminaValoriMultipliCampo(DataBase db, int idAttivita, int idCampo, int indiceMolteplicitaModello)
        {
            var sql = $"delete from i_attivitadyn2dati where " +
                        $"idcomune={db.Specifics.QueryParameterName("idcomune")} and " +
                        $"fk_ia_id={db.Specifics.QueryParameterName("idAttivita")} and " +
                        $"fk_d2c_id={db.Specifics.QueryParameterName("idCampo")} and " +
                        $"indice={db.Specifics.QueryParameterName("indice")}";

            db.ExecuteNonQuery(sql, mp =>
            {
                mp.AddParameter("idcomune", this._authenticationInfo.IdComune);
                mp.AddParameter("idAttivita", idAttivita);
                mp.AddParameter("idCampo", idCampo);
                mp.AddParameter("indice", indiceMolteplicitaModello);
            });
        }

        public void AggiungiSchedaDinamicaAdAttivita(int codiceAttivita, int idModello)
        {
            using (var db = this._authenticationInfo.CreateDatabase())
            {
                db.Insert(new IAttivitaDyn2ModelliT
                {
                    Idcomune = this._authenticationInfo.IdComune,
                    FkIaId = codiceAttivita,
                    FkD2mtId = idModello
                });
            }

            this._eventPublisher.Publish(new SchedaDinamicaAggiuntaAdAttivita(codiceAttivita, idModello));
        }

        public void EliminaModello(int codiceAttivita, int idModello)
        {
            IEnumerable<int> campiDaEliminare;

            using (var db = this._authenticationInfo.CreateDatabase())
            {
                //var logicaEliminazione = new LogicaEliminazioneDatiSchedaAttivita(db, this._authenticationInfo.IdComune, codiceAttivita);

                //logicaEliminazione.Elimina(idModello);

                campiDaEliminare = SelezionaCampiDaEliminare(db, codiceAttivita, idModello);

                EliminaValori(db, codiceAttivita, campiDaEliminare);
                EliminaModello(db, codiceAttivita, idModello);
            }

            this._eventPublisher.Publish(new SchedaDinamicaRimossaDaAttivita(codiceAttivita, idModello, campiDaEliminare));
        }


        public IEnumerable<int> GetCampiModelloPresentiAncheNelleIstanze(int codiceAttivita, int idModello)
        {
            using (var db = this._authenticationInfo.CreateDatabase())
            {
                /*
                var sql = $@"SELECT
            					istanzedyn2dati.fk_d2c_id AS idCampo,
            					Count(istanzedyn2dati.fk_d2c_id) AS datiPresenti
            				FROM
            					dyn2_modellid,
            					istanze,
            					istanzedyn2dati
            				WHERE
            					istanzedyn2dati.idcomune = dyn2_modellid.idcomune AND
            					istanzedyn2dati.fk_d2c_id= dyn2_modellid.fk_d2c_id and
            					istanze.idcomune         = istanzedyn2dati.idcomune and
            					istanze.codiceistanza    = istanzedyn2dati.codiceistanza AND
            					dyn2_modellid.idcomune   = {db.Specifics.QueryParameterName("idComune")} AND
            					dyn2_modellid.fk_d2mt_id = {db.Specifics.QueryParameterName("idModello")} AND
            					istanze.fk_idi_attivita = {db.Specifics.QueryParameterName("idAttivita")}
            				GROUP BY
            					istanze.fk_idi_attivita,
            					istanzedyn2dati.fk_d2c_id
                            HAVING Count(istanzedyn2dati.fk_d2c_id) > 1";
                */
                var sql = $@"SELECT
                                    dyn2_modellid.fk_d2c_id as idCampo
                                FROM
                                    dyn2_modellid
                                    INNER JOIN i_attivitadyn2modellit ON 
                                        dyn2_modellid.idcomune = i_attivitadyn2modellit.idcomune
                                        AND dyn2_modellid.fk_d2mt_id = i_attivitadyn2modellit.fk_d2mt_id
                                    
                                    INNER JOIN i_attivita ON 
                                        i_attivitadyn2modellit.idcomune = i_attivita.idcomune
                                        AND i_attivitadyn2modellit.fk_ia_id = i_attivita.id

                                    INNER JOIN istanze ON 
                                        i_attivita.idcomune = istanze.idcomune
                                        AND i_attivita.id = istanze.fk_idi_attivita
                                        AND istanze.datavalidita IS NOT NULL

                                    INNER JOIN istanzedyn2modellit ON 
                                        istanze.idcomune = istanzedyn2modellit.idcomune
                                        AND istanze.codiceistanza = istanzedyn2modellit.codiceistanza

                                    INNER JOIN dyn2_modellid modelli_istanza ON 
                                        istanzedyn2modellit.idcomune = modelli_istanza.idcomune
                                        AND istanzedyn2modellit.fk_d2mt_id = modelli_istanza.fk_d2mt_id
                                        AND dyn2_modellid.idcomune = modelli_istanza.idcomune
                                        AND dyn2_modellid.fk_d2c_id = modelli_istanza.fk_d2c_id

                                WHERE
                                    i_attivitadyn2modellit.idcomune = {db.Specifics.QueryParameterName("idComune")}
                                    AND i_attivitadyn2modellit.fk_ia_id = {db.Specifics.QueryParameterName("idAttivita")}
                                    AND i_attivitadyn2modellit.fk_d2mt_id = {db.Specifics.QueryParameterName("idModello")}
                                    AND dyn2_modellid.fk_d2c_id IS NOT NULL

                                GROUP BY
                                    dyn2_modellid.fk_d2c_id
                                ORDER BY
                                    dyn2_modellid.fk_d2c_id";

                return db.ExecuteReader(sql,
                    mp =>
                    {
                        mp.AddParameter("idComune", this._authenticationInfo.IdComune);
                        mp.AddParameter("idAttivita", codiceAttivita);
                        mp.AddParameter("idModello", idModello);
                    },
                    dr => dr.GetInt("idCampo").Value).ToList();

            }
        }

        // Logica di Eliminazione
        private IEnumerable<int> SelezionaCampiDaEliminare(DataBase db, int codiceAttivita, int idScheda)
        {
            var sql = $@"SELECT 
							dyn2_modellid.fk_d2c_ID
						FROM 
							i_attivitaDyn2modellit,
							dyn2_modellid
						WHERE
							dyn2_modellid.idcomune   = i_attivitaDyn2modellit.idComune AND
							dyn2_modellid.fk_d2mt_id = i_attivitaDyn2modellit.fk_d2mt_id and 
							dyn2_modellid.fk_d2mdt_id IS NULL and
							i_attivitaDyn2modellit.idComune = {db.Specifics.QueryParameterName("idcomune1")} AND
							i_attivitaDyn2modellit.fk_ia_id = {db.Specifics.QueryParameterName("idAttivita1")} AND
							i_attivitaDyn2modellit.fk_d2mt_id = {db.Specifics.QueryParameterName("idModello1")} AND
							dyn2_modellid.fk_d2c_ID NOT IN 
							(
								SELECT 
									dyn2_modellid.fk_d2c_ID                         
								FROM 
									i_attivitaDyn2modellit,
									dyn2_modellid
								WHERE
									dyn2_modellid.idcomune      =   i_attivitaDyn2modellit.idComune AND
									dyn2_modellid.fk_d2mt_id    =   i_attivitaDyn2modellit.fk_d2mt_id and 
									dyn2_modellid.fk_d2mdt_id IS NULL and
									i_attivitaDyn2modellit.idComune = {db.Specifics.QueryParameterName("idcomune2")} AND
									i_attivitaDyn2modellit.fk_ia_id = {db.Specifics.QueryParameterName("idAttivita2")} AND
									i_attivitaDyn2modellit.fk_d2mt_id <> {db.Specifics.QueryParameterName("idModello2")}
							)";

            return db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idcomune1", this._authenticationInfo.IdComune);
                    mp.AddParameter("idAttivita1", codiceAttivita);
                    mp.AddParameter("idModello1", idScheda);
                    mp.AddParameter("idcomune2", this._authenticationInfo.IdComune);
                    mp.AddParameter("idAttivita2", codiceAttivita);
                    mp.AddParameter("idModello2", idScheda);
                },
                dr => dr.GetInt32(0)).ToList();
        }


        private void EliminaValori(DataBase db, int codiceAttivita, IEnumerable<int> listaCampi)
        {
            var sql = $@"DELETE FROM i_attivitadyn2dati 
						WHERE idcomune  = {db.Specifics.QueryParameterName("idComune")} AND 
							  fk_IA_ID  = {db.Specifics.QueryParameterName("codiceAttivita")} AND 
							  FK_D2C_ID = {db.Specifics.QueryParameterName("idCampo")}";

            foreach (var id in listaCampi)
            {
                db.ExecuteNonQuery(sql, mp =>
                {
                    mp.AddParameter("idComune", this._authenticationInfo.IdComune);
                    mp.AddParameter("codiceAttivita", codiceAttivita);
                    mp.AddParameter("idCampo", id);
                });
            }
        }

        private void EliminaModello(DataBase db, int codiceAttivita, int idModello)
        {
            var sql = $@"delete from i_attivitadyn2modellit 
						WHERE idcomune = {db.Specifics.QueryParameterName("idComune")} AND 
							  fk_d2mt_id = {db.Specifics.QueryParameterName("idModello")} AND 
							  fk_ia_id = {db.Specifics.QueryParameterName("idAttivita")}";

            db.ExecuteNonQuery(sql, mp =>
            {
                 mp.AddParameter("idComune", this._authenticationInfo.IdComune);
                 mp.AddParameter("idModello", idModello);
                 mp.AddParameter("idAttivita", codiceAttivita);
            });
        }

    }
}
