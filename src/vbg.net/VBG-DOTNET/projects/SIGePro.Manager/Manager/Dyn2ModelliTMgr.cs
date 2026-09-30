using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.Validator;
using Init.Utils.Sorting;
using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Linq;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli.Serializables;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class Dyn2ModelliTMgr
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(Dyn2ModelliTMgr));

        public List<Dyn2ModelliT> CercaModelli(string idComune, string software, string id, string codice, string descrizione, string contesto, string sortExpression = nameof(Dyn2ModelliT.Id))
        {
            var filtro = new Dyn2ModelliT
            {
                Software = software,
                Idcomune = idComune,
                Id = String.IsNullOrEmpty(id) ? (int?)null : Convert.ToInt32(id),
                CodiceScheda = codice,
                Descrizione = descrizione,
                FkD2bcId = contesto,
            };

            var filtroCompare = new Dyn2ModelliT
            {
                CodiceScheda = "LIKE",
                Descrizione = "LIKE",
                FkD2bcId = "LIKE"
            };

            var list = this.db.GetClassList(filtro, filtroCompare);
            ListSortManager<Dyn2ModelliT>.Sort(list, sortExpression);

            return list;
        }

        public IEnumerable<Dyn2ModelliT> GetListaModelliPerStatistiche(string idcomune, string software)
        {
            var internalOpen = false;
            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    internalOpen = true;
                }

                var cmdText = "SELECT software,id,descrizione FROM dyn2_modellit WHERE IDCOMUNE = {0} AND (software = {1} or software = 'TT') order by Descrizione asc";

                cmdText = this.PreparaQueryParametrica(cmdText, "idcomune", "software");

                using (var cmd = this.db.CreateCommand(cmdText))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idcomune", idcomune));
                    cmd.Parameters.Add(this.db.CreateParameter("software", software));

                    using (var dr = cmd.ExecuteReader())
                    {
                        var rVal = new List<Dyn2ModelliT>();

                        while (dr.Read())
                        {
                            rVal.Add(new Dyn2ModelliT
                            {
                                Idcomune = idcomune,
                                Id = Convert.ToInt32(dr["id"]),
                                Software = dr["software"].ToString(),
                                Descrizione = dr["descrizione"] + " (" + dr["software"] + ")"
                            });
                        }

                        return rVal;
                    }
                }
            }
            finally
            {
                if (internalOpen)
                    this.db.Connection.Close();
            }
        }

        public void Delete(Dyn2ModelliT cls)
        {
            var commitTrans = false;

            try
            {
                if (!this.db.IsInTransaction)
                {
                    this.db.BeginTransaction();
                    commitTrans = true;
                }

                this.VerificaRecordCollegati(cls);

                this.EffettuaCancellazioneACascata(cls);

                this.db.Delete(cls);

                if (commitTrans)
                    this.db.CommitTransaction();
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante l'eliminazione del modello con idcomune={0} e id={1}: {2}", cls.Idcomune, cls.Id, ex.ToString());

                if (commitTrans)
                    this.db.RollbackTransaction();

                throw;
            }
        }

        private void EffettuaCancellazioneACascata(Dyn2ModelliT cls)
        {
            // Leggo tutti i testi per eliminarli successivamente
            var testiMgr = new Dyn2ModelliDTestiMgr(this.db);
            var testi = testiMgr.GetListByIdModello(cls.Idcomune, cls.Id.Value);


            // Cancello la struttura del modello
            var filtroDettagli = new Dyn2ModelliD
            {
                Idcomune = cls.Idcomune,
                FkD2mtId = cls.Id
            };

            var mgrDettagli = new Dyn2ModelliDMgr(this.db);

            var dettagli = mgrDettagli.GetList(filtroDettagli);

            for (var i = 0; i < dettagli.Count; i++)
            {
                mgrDettagli.Delete(dettagli[i]);
            }

            // cancello tutti i testi del modello
            foreach (var testo in testi)
            {
                testiMgr.Delete(testo);
            }

            // Cancello tutti gli script
            var formuleMgr = new Dyn2ModelliScriptMgr(this.db);
            var formule = formuleMgr.GetList(new Dyn2ModelliScript
            {
                IdComune = cls.Idcomune,
                FkD2mtId = cls.Id.Value
            });

            foreach (var formula in formule)
            {
                formuleMgr.Delete(formula);
            }
        }

        private void VerificaRecordCollegati(Dyn2ModelliT cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle

            // Verifico se il modello è contenuto in un'istanza
            var filtroIstanze = new IstanzeDyn2ModelliT
            {
                Idcomune = cls.Idcomune,
                FkD2mtId = cls.Id
            };

            var modelliIstanze = new IstanzeDyn2ModelliTMgr(this.db).GetList(filtroIstanze);

            if (modelliIstanze.Count > 0)
                throw new ReferentialIntegrityException("ISTANZEDYN2MODELLIT");

            // Verifico se il modello è contenuto in un'attivita
            var filtroAttivita = new IAttivitaDyn2ModelliT
            {
                Idcomune = cls.Idcomune,
                FkD2mtId = cls.Id
            };

            var modelliAttivita = new IAttivitaDyn2ModelliTMgr(this.db).GetList(filtroAttivita);

            if (modelliAttivita.Count > 0)
                throw new ReferentialIntegrityException("I_ATTIVITADYN2MODELLIT");

            // Verifico se il modello è contenuto in un'anagrafica
            var filtroAnagrafe = new AnagrafeDyn2ModelliT
            {
                Idcomune = cls.Idcomune,
                FkD2mtId = cls.Id
            };

            var modelliAnagrafe = new AnagrafeDyn2ModelliTMgr(this.db).GetList(filtroAnagrafe);

            if (modelliAnagrafe.Count > 0)
                throw new ReferentialIntegrityException("ANAGRAFEDYN2MODELLIT");

            // Verifico se il modello è collegato all'albero dei procedimenti
            var filtroAlberoProc = new AlberoProcDyn2ModelliT
            {
                Idcomune = cls.Idcomune,
                FkD2mtId = cls.Id
            };

            var modelliAlberoProc = new AlberoProcDyn2ModelliTMgr(this.db).GetList(filtroAlberoProc);

            if (modelliAlberoProc.Count > 0)
                throw new ReferentialIntegrityException("ALBEROPROC_DYN2MODELLIT");

            // Verifico se il modello è collegato agli interventi
            var filtroInterventi = new InterventiDyn2ModelliT
            {
                Idcomune = cls.Idcomune,
                FkD2mtId = cls.Id
            };

            var modelliInterventi = new InterventiDyn2ModelliTMgr(this.db).GetList(filtroInterventi);

            if (modelliInterventi.Count > 0)
                throw new ReferentialIntegrityException("INTERVENTIDYN2MODELLIT");

            // Verifico se il modello è collegato agli endoprocedimenti
            var filtroInventarioProc = new InventarioProcDyn2ModelliT
            {
                Idcomune = cls.Idcomune,
                FkD2mtId = cls.Id
            };

            var modelliInventarioProc = new InventarioProcDyn2ModelliTMgr(this.db).GetList(filtroInventarioProc);

            if (modelliInventarioProc.Count > 0)
                throw new ReferentialIntegrityException("INVENTARIOPROCDYN2MODELLIT");

            // Verifico se il modello è collegato ai movimenti
            var filtroMovimenti = new MovimentiDyn2ModelliT
            {
                Idcomune = cls.Idcomune,
                FkD2mtId = cls.Id
            };

            var modelliMovimenti = new MovimentiDyn2ModelliTMgr(this.db).GetList(filtroMovimenti);

            if (modelliMovimenti.Count > 0)
                throw new ReferentialIntegrityException("MOVIMENTIDYN2MODELLIT");

            // Verifico se il modello è collegato ai tipimovimenti
            var filtroTipiMovimenti = new TipiMovimentiDyn2ModelliT
            {
                Idcomune = cls.Idcomune,
                FkD2mtId = cls.Id
            };

            var modelliTipiMovimenti = new TipiMovimentiDyn2ModelliTMgr(this.db).GetList(filtroTipiMovimenti);

            if (modelliTipiMovimenti.Count > 0)
                throw new ReferentialIntegrityException("TIPIMOVIMENTIDYN2MODELLIT");

            // Verifico se il modello è collegato ai bandi
            var filtroTipiBando = new TipiBando
            {
                Idcomune = cls.Idcomune,
                FkD2mtId = cls.Id
            };

            var modelliTipiBando = new TipiBandoMgr(this.db).GetList(filtroTipiBando);

            if (modelliTipiBando.Count > 0)
                throw new ReferentialIntegrityException("TIPIBANDO");

            var internalOpen = false;
            if (this.db.Connection.State == ConnectionState.Closed)
            {
                this.db.Connection.Open();
                internalOpen = true;
            }

            var cmdText = "SELECT COUNT(*) FROM MERCATI_CONFIGURAZIONE WHERE IDCOMUNE = '" + cls.Idcomune + "' AND FK_DYN2MODELLIT = " + cls.Id.ToString();
            using (var cmd = this.db.CreateCommand(cmdText))
            {
                var obj = cmd.ExecuteScalar();
                if (obj != null && Convert.ToInt32(obj) > 0)
                {
                    throw new ReferentialIntegrityException("MERCATI_CONFIGURAZIONE");
                }
            }

            if (internalOpen)
                this.db.Connection.Close();
        }

        /// <summary>
        /// Verifica se nel modello passato sono presenti righe con il flag flg_multiplo == 1
        /// </summary>
        /// <param name="idComune"></param>
        /// <param name="idModello"></param>
        /// <returns></returns>
        public bool VerificaEsistenzaRigheMultiple(string idComune, int idModello)
        {
            var sql = $@"select 
                            count(*) 
                        from 
                            dyn2_modellid 
                        where 
                            idcomune = {this.db.Specifics.QueryParameterName("idComune")} and
                            fk_d2mt_id = {this.db.Specifics.QueryParameterName("idModello")} and 
                            flg_multiplo = 1";

            var cnt = this.db.ExecuteScalar(sql, 0, mp =>
            {
                mp.AddParameter("idComune", idComune);
                mp.AddParameter("idModello", idModello);
            });

            return cnt > 0;
        }

        private void Validate(Dyn2ModelliT cls, AmbitoValidazione ambitoValidazione)
        {
            var modificaCodice = String.IsNullOrEmpty(cls.CodiceScheda);

            if (modificaCodice)
                cls.CodiceScheda = "SCHEDA";

            this.RequiredFieldValidate(cls, ambitoValidazione);

            if (modificaCodice)
                cls.CodiceScheda = "SCHEDA_" + cls.Id;

            if (ambitoValidazione == AmbitoValidazione.Update)
            {
                var clsTmp = this.GetById(cls.Idcomune, cls.Id.GetValueOrDefault(-1));

                #region Verifica del flag "Contesto"
                if (clsTmp.FkD2bcId != cls.FkD2bcId) // il contesto è stato modificato
                {
                    var ambitoIstanza = false;
                    var ambitoAnagrafica = false;
                    var ambitoAttivita = false;

                    switch (clsTmp.FkD2bcId) // Verifico che nel vecchio contesto non ci siano record collegati al modello
                    {
                        case ("IS"):
                            ambitoIstanza = true;
                            break;
                        case ("AT"):
                            ambitoAttivita = true;
                            break;
                        case ("AN"):
                            ambitoAnagrafica = true;
                            break;
                        default:
                            ambitoAnagrafica = true;
                            ambitoAttivita = true;
                            ambitoIstanza = true;
                            break;
                    }

                    var conditions = new List<KeyValuePair<string, string>>
                        {
                            new KeyValuePair<string, string>("idcomune", cls.Idcomune),
                            new KeyValuePair<string, string>("fk_d2mt_id", cls.Id.ToString())
                        };

                    if (ambitoIstanza)
                    {

                        var recCount = this.recordCount("istanzedyn2modellit", "fk_d2mt_id", conditions);
                        if (recCount > 0)
                            throw new InvalidOperationException("Impossibile modificare il contesto del modello in quanto ci sono istanze che lo utilizzano");
                    }

                    if (ambitoAnagrafica)
                    {
                        var recCount = this.recordCount("anagrafedyn2modellit", "fk_d2mt_id", conditions);
                        if (recCount > 0)
                            throw new InvalidOperationException("Impossibile modificare il contesto del modello in quanto ci sono anagrafiche che lo utilizzano");
                    }

                    if (ambitoAttivita)
                    {
                        var recCount = this.recordCount("i_attivitadyn2modellit", "fk_d2mt_id", conditions);
                        if (recCount > 0)
                            throw new InvalidOperationException("Impossibile modificare il contesto del modello in quanto ci sono attività che lo utilizzano");
                    }
                }
                #endregion

                #region Verifica del flag "Modello multiplo"
                if (cls.Modellomultiplo != clsTmp.Modellomultiplo && cls.Modellomultiplo.GetValueOrDefault(0) == 0)
                {
                    // Se è stato rimosso il flag di modello multiplo ma esistono nell'istanza campi con un indice > 0 
                    // sollevo un'eccezione
                    var ambitoIstanza = false;
                    var ambitoAnagrafica = false;
                    var ambitoAttivita = false;

                    switch (cls.FkD2bcId)
                    {
                        case ("IS"):
                            ambitoIstanza = true;
                            break;
                        case ("AT"):
                            ambitoAttivita = true;
                            break;
                        case ("AN"):
                            ambitoAnagrafica = true;
                            break;
                        default:
                            ambitoAnagrafica = true;
                            ambitoAttivita = true;
                            ambitoIstanza = true;
                            break;
                    }

                    if (ambitoIstanza)
                    {
                        var sql = $@"SELECT 
	COUNT(*)
FROM 
	istanzedyn2modellit 
	INNER JOIN istanzedyn2dati ON
		istanzedyn2dati.idcomune = istanzedyn2modellit.idcomune AND
		istanzedyn2dati.codiceistanza = istanzedyn2modellit.codiceistanza
WHERE 
	istanzedyn2modellit.idcomune = {this.db.QueryParameter("idcomune")} AND
	istanzedyn2modellit.fk_d2mt_id = {this.db.QueryParameter("idmodello")} AND
	istanzedyn2dati.indice > 0";

                        var recCount = this.db.ExecuteScalar(sql, 0, mp =>
                        {
                            mp.Add("idcomune", cls.Idcomune);
                            mp.Add("idmodello", cls.Id);
                        });

                        if (recCount > 0)
                            throw new Exception("Impossibile rimuovere il flag multiplo per il modello in quanto è già stato utilizzato in una o più istanze");
                    }

                    if (ambitoAnagrafica)
                    {
                        var sql = $@"SELECT 
	COUNT(*)
FROM 
	anagrafedyn2modellit 
	INNER JOIN anagrafedyn2dati ON
		anagrafedyn2dati.idcomune = anagrafedyn2modellit.idcomune AND
		anagrafedyn2dati.codiceanagrafe = anagrafedyn2modellit.codiceanagrafe
WHERE 
	anagrafedyn2modellit.idcomune = {this.db.QueryParameter("idcomune")} AND
	anagrafedyn2modellit.fk_d2mt_id = {this.db.QueryParameter("idmodello")} AND
	anagrafedyn2dati.indice > 0";

                        var recCount = this.db.ExecuteScalar(sql, 0, mp =>
                        {
                            mp.Add("idcomune", cls.Idcomune);
                            mp.Add("idmodello", cls.Id);
                        });

                        if (recCount > 0)
                            throw new Exception("Impossibile rimuovere il flag multiplo per il modello in quanto è già stato utilizzato in una o più anagrafiche");
                    }

                    if (ambitoAttivita)
                    {
                        var sql = $@"SELECT 
	COUNT(*)
FROM 
	i_attivitadyn2modellit 
	INNER JOIN i_attivitadyn2dati ON
		i_attivitadyn2dati.idcomune = i_attivitadyn2modellit.idcomune AND
		i_attivitadyn2dati.fk_ia_id = i_attivitadyn2modellit.fk_ia_id
WHERE 
	i_attivitadyn2modellit.idcomune = {this.db.QueryParameter("idcomune")} AND
	i_attivitadyn2modellit.fk_d2mt_id = {this.db.QueryParameter("idmodello")} AND
	i_attivitadyn2dati.indice > 0";

                        var recCount = this.db.ExecuteScalar(sql, 0, mp =>
                        {
                            mp.Add("idcomune", cls.Idcomune);
                            mp.Add("idmodello", cls.Id);
                        });

                        if (recCount > 0)
                            throw new Exception("Impossibile rimuovere il flag multiplo per il modello in quanto è già stato utilizzato in una o più attività");
                    }
                }
                #endregion

            }
        }

        #region IDyn2ModelliManager Members

        public ModelloDinamicoDto? GetModelloById(string idComune, int idModello)
        {
            FormattableString sql = $@"SELECT 
                            CODICE_SCHEDA,
                            DESCRIZIONE, 
                            FK_D2BC_ID,
                            FLG_READONLY_WEB,
                            FLG_STORICIZZA,
                            ID,
                            MODELLOMULTIPLO,
                            SCRIPTCODE
                    FROM 
                        dyn2_modellit 
                    WHERE 
                        idcomune={idComune} AND 
                        id={idModello}";

            return this.db.ExecuteReader(sql,
                dr => new ModelloDinamicoDto
                {
                    CodiceScheda = dr.GetString("CODICE_SCHEDA"),
                    Descrizione = dr.GetString("DESCRIZIONE"),
                    FkD2bcId = dr.GetString("FK_D2BC_ID"),
                    FlgReadonlyWeb = dr.GetInt("FLG_READONLY_WEB").GetValueOrDefault(0),
                    FlgStoricizza = dr.GetInt("FLG_STORICIZZA").GetValueOrDefault(0),
                    Id = dr.GetInt("ID")!.Value,
                    Modellomultiplo = dr.GetInt("MODELLOMULTIPLO").GetValueOrDefault(0),
                    Scriptcode = dr.GetString("SCRIPTCODE")
                }).FirstOrDefault();
        }

        #endregion

        public int CopiaScheda(string IdComune, int idSchedaDaCopiare, bool copiaFormule)
        {
            var campiMgr = new Dyn2ModelliDMgr(this.db);
            var formuleMgr = new Dyn2ModelliScriptMgr(this.db);
            var testiMgr = new Dyn2ModelliDTestiMgr(this.db);

            var modelloDaCopiare = this.GetById(IdComune, idSchedaDaCopiare);
            var campiDaCopiare = campiMgr.GetList(new Dyn2ModelliD
            {
                Idcomune = IdComune,
                FkD2mtId = idSchedaDaCopiare
            });

            try
            {
                this.db.BeginTransaction();

                modelloDaCopiare.Id = null;
                modelloDaCopiare.Descrizione = "Copia di " + modelloDaCopiare.Descrizione;
                modelloDaCopiare.CodiceScheda += "_COPIA";

                var nuovoModello = this.Insert(modelloDaCopiare);

                foreach (var campo in campiDaCopiare)
                {
                    campo.Id = null;
                    campo.FkD2mtId = nuovoModello.Id;

                    // Se il campo non è un campo dinamico duplico il testo contenuto
                    if (campo.FkD2mdtId.HasValue)
                    {
                        var testo = testiMgr.GetById(IdComune, campo.FkD2mdtId.Value);

                        testo.Id = null;

                        testo = testiMgr.Insert(testo);

                        campo.FkD2mdtId = testo.Id;
                    }

                    campiMgr.Insert(campo);
                }

                if (copiaFormule)
                {
                    var listaFormule = formuleMgr.GetList(new Dyn2ModelliScript
                    {
                        IdComune = IdComune,
                        FkD2mtId = idSchedaDaCopiare
                    });

                    foreach (var formula in listaFormule)
                    {
                        formula.FkD2mtId = nuovoModello.Id;

                        formuleMgr.Insert(formula);
                    }
                }

                this.db.CommitTransaction();

                return nuovoModello.Id.Value;
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore in CopiaScheda con idComune={0}, idSchedaDaCopiare={1}, copiaFormule={2}: {3}", IdComune, idSchedaDaCopiare, copiaFormule, ex.ToString());

                this.db.RollbackTransaction();

                throw;
            }
        }

        public IEnumerable<Dyn2ModelliT> GetSchedeContenentiIlCampo(string idComune, int? idCampo)
        {

            var closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                var sql = @"SELECT dyn2_modellit.* FROM 
								dyn2_modellit,
								dyn2_modellid
							WHERE
								dyn2_modellit.idcomune = dyn2_modellid.idcomune AND
								dyn2_modellit.id = dyn2_modellid.fk_d2mt_id AND
								dyn2_modellid.idcomune = {0} AND
								dyn2_modellid.fk_d2c_id = {1}
							order by descrizione asc";

                sql = this.PreparaQueryParametrica(sql, "idComune", "idCampo");

                using (var cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("idCampo", idCampo.GetValueOrDefault(int.MinValue)));

                    return this.db.GetClassList<Dyn2ModelliT>(cmd);
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }

        }

        public class InterventoCollegatoAScheda
        {
            public string Software { get; set; }
            public int Id { get; set; }
            public string Descrizione { get; set; }
        }

        public class EndoCollegatoAScheda
        {
            public string Software { get; set; }
            public int Id { get; set; }
            public string Descrizione { get; set; }
        }

        public IEnumerable<InterventoCollegatoAScheda> GetListaInterventiDaIdModello(string idComune, int idModello)
        {
            var sql = $@"SELECT 
	                        alberoproc.software,
	                        alberoproc.sc_id,
	                        alberoproc.descrizione_completa
                        FROM 
	                        alberoproc_dyn2modellit 
	                        INNER JOIN alberoproc ON
		                        alberoproc.idcomune = alberoproc_dyn2modellit.idcomune AND
		                        alberoproc.sc_id = alberoproc_dyn2modellit.fk_sc_id
                        WHERE 
	                        alberoproc_dyn2modellit.idcomune = {this.db.QueryParameter("idComune")} AND 
	                        alberoproc_dyn2modellit.FK_D2MT_ID = {this.db.QueryParameter("idModello")}
                        ORDER BY 
	                        alberoproc.software, 
	                        alberoproc.sc_codice";

            return this.db.ExecuteReader(sql,
                mp =>
                {
                    mp.Add("idComune", idComune)
                      .Add("idModello", idModello);
                },
                dr => new InterventoCollegatoAScheda
                {
                    Id = dr.GetInt("sc_id").Value,
                    Descrizione = dr.GetString("descrizione_completa"),
                    Software = dr.GetString("software"),
                });
        }

        public IEnumerable<EndoCollegatoAScheda> GetListaEndoDaIdModello(string idComune, int idModello)
        {
            var sql = $@"SELECT 
                    inventarioprocedimenti.software,
	                inventarioprocedimenti.CODICEINVENTARIO,
	                inventarioprocedimenti.PROCEDIMENTO
                FROM

                    inventarioprocdyn2modellit

                    INNER JOIN inventarioprocedimenti ON

                        inventarioprocedimenti.idcomune = inventarioprocdyn2modellit.idcomune AND

                        inventarioprocedimenti.CODICEINVENTARIO = inventarioprocdyn2modellit.CODICEINVENTARIO
                WHERE
                    inventarioprocdyn2modellit.idcomune = {this.db.QueryParameter("idComune")} AND
                    inventarioprocdyn2modellit.FK_D2MT_ID = {this.db.QueryParameter("idModello")}
                ORDER BY
                    inventarioprocedimenti.software, 
	                inventarioprocedimenti.PROCEDIMENTO";

            return this.db.ExecuteReader(sql,
                mp =>
                {
                    mp.Add("idComune", idComune)
                      .Add("idModello", idModello);
                },
                dr => new EndoCollegatoAScheda
                {
                    Id = dr.GetInt("CODICEINVENTARIO").Value,
                    Descrizione = dr.GetString("PROCEDIMENTO"),
                    Software = dr.GetString("software"),
                });
        }

        public int? GetIdModelloDaCodice(string idComune, string software, string codiceModello)
        {
            var id = this.GetIdModelloDaCodiceInternal(idComune, software, codiceModello);
            if (!id.HasValue)
            {
                id = this.GetIdModelloDaCodiceInternal(idComune, "TT", codiceModello);
            }
            return id;
        }

        private int? GetIdModelloDaCodiceInternal(string idComune, string software, string codiceModello)
        {
            var cmdText = $@"SELECT ID FROM DYN2_MODELLIT
                                WHERE 
                                    IDCOMUNE = {this.db.QueryParameter("idComune")} AND 
                                    SOFTWARE = {this.db.QueryParameter("software")} AND 
                                    CODICE_SCHEDA = {this.db.QueryParameter("codiceModello")}";

            var id = this.db.ExecuteReader(cmdText,
                                        par => par.Add("idComune", idComune)
                                                  .Add("software", software)
                                                  .Add("codiceModello", codiceModello),
                                        dr => dr.GetInt("ID")).FirstOrDefault();
            return id;
        }

        public bool VerificaEsistenzaModelloDinamico(string idComune, int idModello)
        {
            var cmdText = $@"SELECT COUNT(ID) FROM DYN2_MODELLIT
                                WHERE 
                                    IDCOMUNE = {this.db.QueryParameter("idComune")} AND 
                                    ID = {this.db.QueryParameter("idModello")}";

            var id = this.db.ExecuteScalar(cmdText, 0,
                                        par => par.Add("idComune", idComune)
                                                  .Add("idModello", idModello)
                                                  );
            return id > 0;
        }
    }
}
