
using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.Validator;
using Init.Utils.Sorting;
using log4net;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class Dyn2ModelliTMgr : IDyn2ModelliManager
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(Dyn2ModelliTMgr));


        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<Dyn2ModelliT> Find(string token, string software, string id, string codice, string descrizione, string contesto, string sortExpression)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            Dyn2ModelliTMgr mgr = new Dyn2ModelliTMgr(authInfo.CreateDatabase());

            Dyn2ModelliT filtro = new Dyn2ModelliT
            {
                Software = software,
                Idcomune = authInfo.IdComune,
                Id = String.IsNullOrEmpty(id) ? (int?)null : Convert.ToInt32(id),
                CodiceScheda = codice,
                Descrizione = descrizione,
                FkD2bcId = contesto,
            };

            Dyn2ModelliT filtroCompare = new Dyn2ModelliT
            {
                CodiceScheda = "LIKE",
                Descrizione = "LIKE",
                FkD2bcId = "LIKE"
            };

            List<Dyn2ModelliT> list = authInfo.CreateDatabase()
                                              .GetClassList(filtro, filtroCompare, false);

            ListSortManager<Dyn2ModelliT>.Sort(list, sortExpression);

            return list;
        }

        public IEnumerable<Dyn2ModelliT> GetListaModelliPerStatistiche(string idcomune, string software)
        {
            bool internalOpen = false;
            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    internalOpen = true;
                }

                string cmdText = "SELECT software,id,descrizione FROM dyn2_modellit WHERE IDCOMUNE = {0} AND (software = {1} or software = 'TT') order by Descrizione asc";

                cmdText = this.PreparaQueryParametrica(cmdText, "idcomune", "software");

                using (IDbCommand cmd = this.db.CreateCommand(cmdText))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idcomune", idcomune));
                    cmd.Parameters.Add(this.db.CreateParameter("software", software));

                    using (var dr = cmd.ExecuteReader())
                    {
                        List<Dyn2ModelliT> rVal = new List<Dyn2ModelliT>();

                        while (dr.Read())
                        {
                            rVal.Add(new Dyn2ModelliT
                            {
                                Idcomune = idcomune,
                                Id = Convert.ToInt32(dr["id"]),
                                Software = dr["software"].ToString(),
                                Descrizione = dr["descrizione"].ToString() + " (" + dr["software"].ToString() + ")"
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
            bool commitTrans = false;

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
            // Cancello tutti i dettagli del modello
            Dyn2ModelliD filtroDettagli = new Dyn2ModelliD();
            filtroDettagli.Idcomune = cls.Idcomune;
            filtroDettagli.FkD2mtId = cls.Id;

            Dyn2ModelliDMgr mgrDettagli = new Dyn2ModelliDMgr(this.db);

            List<Dyn2ModelliD> dettagli = mgrDettagli.GetList(filtroDettagli);

            for (int i = 0; i < dettagli.Count; i++)
            {
                mgrDettagli.Delete(dettagli[i]);
            }

            Dyn2ModelliScriptMgr formuleMgr = new Dyn2ModelliScriptMgr(this.db);
            var formule = formuleMgr.GetList(new Dyn2ModelliScript
            {
                Idcomune = cls.Idcomune,
                FkD2mtId = cls.Id.Value
            });

            foreach (var formula in formule)
            {
                formuleMgr.Delete(formula);
            }
        }

        private void VerificaRecordCollegati(Dyn2ModelliT cls)
        {
        }

        /// <summary>
        /// Verifica se nel modello passato sono presenti righe con il flag flg_multiplo == 1
        /// </summary>
        /// <param name="idComune"></param>
        /// <param name="idModello"></param>
        /// <returns></returns>
        public bool VerificaEsistenzaRigheMultiple(string idComune, int idModello)
        {

            int cnt = this.recordCount("dyn2_modellid", "*", @"where idcomune = '" + idComune + @"' AND
																 fk_d2mt_id = " + idModello + @" AND
																 flg_multiplo = 1");

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

        }

        #region IDyn2ModelliManager Members

        IDyn2Modello IDyn2ModelliManager.GetById(string idComune, int idModello)
        {
            return this.GetById(idComune, idModello);
        }

        #endregion


        public int CopiaScheda(string IdComune, int idSchedaDaCopiare, bool copiaFormule)
        {
            Dyn2ModelliDMgr campiMgr = new Dyn2ModelliDMgr(this.db);
            Dyn2ModelliScriptMgr formuleMgr = new Dyn2ModelliScriptMgr(this.db);
            Dyn2ModelliDTestiMgr testiMgr = new Dyn2ModelliDTestiMgr(this.db);

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
                modelloDaCopiare.CodiceScheda = modelloDaCopiare.CodiceScheda + "_COPIA";

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
                        Idcomune = IdComune,
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

            bool closeCnn = false;

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
    }
}
