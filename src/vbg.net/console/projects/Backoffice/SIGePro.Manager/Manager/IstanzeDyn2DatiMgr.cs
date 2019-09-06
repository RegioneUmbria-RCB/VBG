using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Linq;
using Init.SIGePro.Data;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.DatiDinamici.Interfaces.Istanze;
using Init.SIGePro.DatiDinamici.Utils;
using Init.SIGePro.DatiDinamici.WebControls;
using Init.SIGePro.Manager.Logic.GestioneSchedeAttivita;
using Init.SIGePro.Manager.Logic.GestioneSchedeAttivita.Eventi;
using Init.SIGePro.Validator;
using Init.Utils;
using log4net;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class IstanzeDyn2DatiMgr : IIstanzeDyn2DatiManager
    {
        ILog _log = LogManager.GetLogger(typeof(IstanzeDyn2DatiMgr));


        internal List<IstanzeDyn2Dati> GetValoriCampoNoIndice(string idComune, int idIstanza, int idCampo)
        {
            IstanzeDyn2Dati filtro = new IstanzeDyn2Dati();
            filtro.Idcomune = idComune;
            filtro.Codiceistanza = idIstanza;
            filtro.FkD2cId = idCampo;
            filtro.OrderBy = "indice_molteplicita asc";

            return GetList(filtro);
        }

        /// <summary>
        /// Utilizzato per mantenere la compatibilità
        /// l'indice di molteplicità va SEMPRE utilizzato. Il metodo solleva un'eccezione se vengono trovati più di un record
        /// </summary>
        /// <param name="idcomune">idComune</param>
        /// <param name="codiceistanza">codiceIstanza</param>
        /// <param name="fk_d2c_id">id campo dinamico</param>
        /// <param name="indice">indice per schede multiple</param>
        /// <returns>dati del campo se trovato</returns>
        public IstanzeDyn2Dati GetById(string idcomune, int codiceistanza, int fk_d2c_id, int indice)
        {
            IstanzeDyn2Dati filtro = new IstanzeDyn2Dati();
            filtro.Idcomune = idcomune;
            filtro.Codiceistanza = codiceistanza;
            filtro.FkD2cId = fk_d2c_id;
            filtro.Indice = indice;

            return (IstanzeDyn2Dati)db.GetClass(filtro);
        }

        private void Validate(IstanzeDyn2Dati cls, AmbitoValidazione ambitoValidazione)
        {
            if (ambitoValidazione == AmbitoValidazione.Insert && !cls.IndiceMolteplicita.HasValue)
                cls.IndiceMolteplicita = 0;

            RequiredFieldValidate(cls, ambitoValidazione);
        }

        public List<IstanzeDyn2Dati> GetByIdNoIndice(string idcomune, int codiceistanza, int fk_d2c_id)
        {
            IstanzeDyn2Dati c = new IstanzeDyn2Dati();

            c.Idcomune = idcomune;
            c.Codiceistanza = codiceistanza;
            c.FkD2cId = fk_d2c_id;

            return db.GetClassList(c).ToList<IstanzeDyn2Dati>();
        }

        public List<IstanzeDyn2Dati> GetListByIdIndiceModello(string idcomune, int codiceistanza, int fk_d2c_id, int indiceModello)
        {
            IstanzeDyn2Dati c = new IstanzeDyn2Dati();

            c.Idcomune = idcomune;
            c.Codiceistanza = codiceistanza;
            c.FkD2cId = fk_d2c_id;
            c.Indice = indiceModello;

            return db.GetClassList(c).ToList<IstanzeDyn2Dati>();
        }

        public IEnumerable<IstanzeDyn2Dati> GetListByCodiceIstanzaIdModello(string idComune, int codiceIstanza, int idModello)
        {

            bool closeCnn = false;

            try
            {
                if (db.Connection.State == ConnectionState.Closed)
                {
                    db.Connection.Open();
                    closeCnn = true;
                }

                var sql = PreparaQueryParametrica(@"SELECT 
														istanzedyn2dati.*
													FROM 
														istanzedyn2dati,
														dyn2_modellid
													WHERE
														istanzedyn2dati.idcomune = dyn2_modellid.idcomune AND
														istanzedyn2dati.fk_d2c_id = dyn2_modellid.fk_d2c_id AND
														dyn2_modellid.idcomune = {0} AND
														dyn2_modellid.fk_d2mt_id = {1} AND
														istanzedyn2dati.codiceistanza = {2}",
                                                    "idComune",
                                                    "idModello",
                                                    "codiceIstanza");

                using (IDbCommand cmd = db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(db.CreateParameter("idModello", idModello));
                    cmd.Parameters.Add(db.CreateParameter("codiceIstanza", codiceIstanza));

                    return db.GetClassList<IstanzeDyn2Dati>(cmd);
                }
            }
            finally
            {
                if (closeCnn)
                    db.Connection.Close();
            }

        }

        private void EffettuaCancellazioneACascata(IstanzeDyn2Dati cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
            /*
			IstanzeDyn2DatiStoricoMgr storicoMgr = new IstanzeDyn2DatiStoricoMgr(db);

			var listaCampi = storicoMgr.GetValoriNoIndiceNoVersione(cls.Idcomune, cls.Codiceistanza.Value, cls.FkD2cId.Value);

			listaCampi.ForEach(C => storicoMgr.Delete(C));
            */
        }

        #region IIstanzeDyn2DatiManager Members

        public SerializableDictionary<int, List<IIstanzeDyn2Dati>> GetValoriCampoDaIdModello(string idComune, int codiceIstanza, int idModello, int indiceCampo)
        {

            bool closeCnn = false;

            try
            {
                if (db.Connection.State == ConnectionState.Closed)
                {
                    db.Connection.Open();
                    closeCnn = true;
                }

                string sql = @"select 
									IstanzeDyn2Dati.* 
								from 
									IstanzeDyn2Dati,
									dyn2_modellid
								WHERE
									IstanzeDyn2Dati.idcomune = dyn2_modellid.idcomune AND
									IstanzeDyn2Dati.fk_d2c_id = dyn2_modellid.fk_d2c_id and
									IstanzeDyn2Dati.idcomune = {0} and									
									dyn2_modellid.fk_d2mt_id = {1} and
									IstanzeDyn2Dati.codiceistanza = {2} and
									IstanzeDyn2Dati.indice = {3}
								order by 
									indice_molteplicita asc";

                sql = PreparaQueryParametrica(sql, "idComune", "idModello", "codiceIstanza", "indice");

                using (IDbCommand cmd = db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(db.CreateParameter("idModello", idModello));
                    cmd.Parameters.Add(db.CreateParameter("codiceIstanza", codiceIstanza));
                    cmd.Parameters.Add(db.CreateParameter("indice", indiceCampo));

                    using (var dr = cmd.ExecuteReader())
                    {
                        var rVal = new SerializableDictionary<int, List<IIstanzeDyn2Dati>>();

                        while (dr.Read())
                        {
                            var val = new IstanzeDyn2Dati
                            {
                                Idcomune = idComune,
                                Codiceistanza = codiceIstanza,
                                FkD2cId = Convert.ToInt32(dr["fk_d2c_id"]),
                                Indice = indiceCampo,
                                IndiceMolteplicita = Convert.ToInt32(dr["Indice_Molteplicita"]),
                                Valore = dr["valore"].ToString(),
                                Valoredecodificato = dr["Valoredecodificato"].ToString()
                            };

                            int key = val.FkD2cId.Value;

                            if (!rVal.ContainsKey(key))
                                rVal.Add(key, new List<IIstanzeDyn2Dati>());

                            rVal[key].Add(val);
                        }

                        return rVal;
                    }
                }
            }
            finally
            {
                if (closeCnn)
                    db.Connection.Close();
            }

        }
        
        public IstanzeDyn2Dati[] GetDyn2DatiByIdModello(string idComune, int codiceIstanza, int idModello, int indiceCampo)
        {
            var sql = @"select 
                            istanzedyn2dati.* 
                        from 
                            istanzedyn2dati
                              INNER JOIN dyn2_modellid ON 
                                istanzedyn2dati.idcomune = dyn2_modellid.idcomune AND
                                istanzedyn2dati.fk_d2c_id = dyn2_modellid.fk_d2c_id
                        WHERE
                          dyn2_modellid.idcomune = {0} AND 
                          dyn2_modellid.fk_d2mt_id = {1} AND
                          istanzedyn2dati.codiceistanza = {2} AND
                          istanzedyn2dati.indice = {3}
                        ORDER BY 
                          istanzedyn2dati.fk_d2c_id asc";

            return this.db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComune", idComune);
                    mp.AddParameter("idModello", idModello);
                    mp.AddParameter("codiceIstanza", codiceIstanza);
                    mp.AddParameter("indiceCampo", indiceCampo);
                },
                dr =>
                {
                    // idcomune, codiceistanza, fk_d2c_id, valore, indice,
                    // valoredecodificato, indice_molteplicita
                    return new IstanzeDyn2Dati
                    {
                        Idcomune = idComune, 
                        Codiceistanza = codiceIstanza,
                        FkD2cId = dr.GetInt("fk_d2c_id"),
                        Valore = dr.GetString("valore"),
                        Valoredecodificato = dr.GetString("valoredecodificato"),
                        Indice = dr.GetInt("indice"),
                        IndiceMolteplicita = dr.GetInt("indice_molteplicita")
                    };
                }).ToArray();
        }

        public void SalvaValoriCampi(bool salvaStorico, ModelloDinamicoIstanza modello, IEnumerable<CampoDinamico> campiDaSalvare)
        {
            using (var cp = new CodeProfiler("SalvaValoriCampi"))
            {
                var commitTrans = !db.IsInTransaction;
                var indiceModello = modello.IndiceModello;
                var idComune = modello.IdComune;
                var codiceIstanza = modello.IdIstanza;

                if (commitTrans)
                    db.BeginTransaction();

                try
                {
                    if (salvaStorico)
                        SalvaStoricoModello(modello);



                    // Se il campo è di tipo Upload 
                    //	- Leggo il vecchio valore del campo
                    //	- Se il campo esiste ed il valore è cambiato
                    //		- Elimino l'allegato dall'istanza
                    //	- Se il valore del campo è != ""
                    //		- Inserisco il nuovo allegato nell'istanza
                    var listaOggettiDaEliminare = new List<string>();
                    var listaOggettiNonModificati = new List<string>();
                    var listaOggettiDaCreare = new List<KeyValuePair<int, string>>();

                    foreach (var campoDaSalvare in campiDaSalvare)
                    {
                        if (campoDaSalvare.TipoCampo != TipoControlloEnum.Upload)
                            continue;

                        var listaCampiNelDb = GetListByIdIndiceModello(idComune, codiceIstanza, campoDaSalvare.Id, campoDaSalvare.ModelloCorrente.IndiceModello);

                        foreach (var campoNelDb in listaCampiNelDb)
                        {
                            // Cerco se il codice oggetto letto dal db è utilizzato in almeno uno dei campi da aggiornare
                            var campoTrovato = campoDaSalvare.ListaValori.FirstOrDefault(x => x.Valore == campoNelDb.Valore);

                            if (campoTrovato == null)
                                listaOggettiDaEliminare.Add(campoNelDb.Valore);
                            else
                                listaOggettiNonModificati.Add(campoNelDb.Valore);
                        }
                    }

                    // in listaOggettiNonModificati sono contenuti tutti gli allegati che non sono stati modificati,
                    // faccio un nuovo loop tra i valori dei campi per vedere quali nuovi allegati vanno salvati
                    foreach (var campoDaSalvare in campiDaSalvare)
                    {
                        if (campoDaSalvare.TipoCampo != TipoControlloEnum.Upload)
                            continue;

                        for (int indiceMolteplicita = 0; indiceMolteplicita < campoDaSalvare.ListaValori.Count; indiceMolteplicita++)
                        {
                            var valoreCampo = campoDaSalvare.ListaValori[indiceMolteplicita];

                            if (String.IsNullOrEmpty(valoreCampo.Valore))
                                continue;

                            if (listaOggettiNonModificati.Contains(valoreCampo.Valore))
                                continue;

                            listaOggettiDaCreare.Add(new KeyValuePair<int, string>(Convert.ToInt32(valoreCampo.Valore), String.Format("{0} {1}", campoDaSalvare.Etichetta, indiceMolteplicita)));
                        }
                    }



                    // Elimino tutti i valori del campo per la scheda corrente
                    foreach (var campoDaSalvare in campiDaSalvare)
                    {
                        var idCampo = campoDaSalvare.Id;

                        EliminaValoriMultipliCampo(idComune, codiceIstanza, idCampo, indiceModello);
                    }

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

                            var cls = new IstanzeDyn2Dati
                            {
                                Idcomune = idComune,
                                FkD2cId = campo.Id,
                                Codiceistanza = codiceIstanza,
                                Valore = valore,
                                Valoredecodificato = valoreDecodificato,
                                Indice = indiceModello,
                                IndiceMolteplicita = indiceMolteplicita
                            };

                            Insert(cls);
                        }
                    }

                    if (commitTrans)
                        db.CommitTransaction();

                    commitTrans = false;

                    // L'operazione va effettuata fuori transazione altrimenti si creerebbe un lock sulla tabella oggetti
                    // visto che la cancellazione dell'oggetto dal db viene effettuata dal web service java.
                    // TODO: spostare l'eliminazione dell'allegato dell'istanza nel web service java
                    foreach (var codiceOggetto in listaOggettiDaEliminare)
                        EliminaAllegatiIstanza(idComune, codiceIstanza, Convert.ToInt32(codiceOggetto));

                    foreach (var allegatoDaAggiungere in listaOggettiDaCreare)
                        CreaAllegatoIstanza(idComune, codiceIstanza, allegatoDaAggiungere.Value, allegatoDaAggiungere.Key);


                    NotificaEventoSalvataggioScheda(idComune, codiceIstanza, modello.IdModello);
                }
                catch (Exception ex)
                {
                    _log.ErrorFormat("Errore durante il salvataggio dei dati dinamici nell'idcomune={0} e codiceistanza={1}: {2}", idComune, codiceIstanza, ex.ToString());

                    if (commitTrans)
                        db.RollbackTransaction();

                    throw;
                }
            }
        }

        private void NotificaEventoSalvataggioScheda(string idComune, int codiceIstanza, int idScheda)
        {
            var istanza = new IstanzeMgr(db).GetById(idComune, codiceIstanza);

            if (istanza == null)
                throw new Exception(String.Format("Istanza con idcomune {0} e codiceistanza {1} non trovata durante la verifica del collegamento con una attivita", idComune, codiceIstanza));

            if (!istanza.FK_IDI_ATTIVITA.HasValue)
                return;

            var svc = new EventiSchedeDinamicheAttivitaService();

            svc.Handle(new SchedaDinamicaIstanzaSalvata(istanza.FK_IDI_ATTIVITA.Value, codiceIstanza, idScheda));
        }



        private void EliminaAllegatiIstanza(string idComune, int codiceIstanza, int codiceOggetto)
        {
            var mgr = new DocumentiIstanzaMgr(db);

            var filtro = new DocumentiIstanza
            {
                IDCOMUNE = idComune,
                CODICEISTANZA = codiceIstanza.ToString(),
                CODICEOGGETTO = codiceOggetto.ToString(),
                FlgDaModelloDinamico = 1
            };

            var cls = mgr.GetList(filtro);

            foreach (var it in cls)
                mgr.Delete(it);

        }

        private void CreaAllegatoIstanza(string idComune, int codiceIstanza, string nomeAllegato, int codiceOggetto)
        {
            var mgr = new DocumentiIstanzaMgr(db);

            mgr.Insert(new DocumentiIstanza
            {
                IDCOMUNE = idComune,
                CODICEISTANZA = codiceIstanza.ToString(),
                DATA = DateTime.Now,
                DOCUMENTO = nomeAllegato,
                CODICEOGGETTO = codiceOggetto.ToString(),
                PRESENTE = "1",
                FlgDaModelloDinamico = 1
            });
        }

        private void EliminaValoriMultipliCampo(string idComune, int idIstanza, int idCampo, int indiceMolteplicitaModello)
        {
            var filtro = new IstanzeDyn2Dati
            {
                Idcomune = idComune,
                Codiceistanza = idIstanza,
                FkD2cId = idCampo,
                Indice = indiceMolteplicitaModello
            };

            var lista = GetList(filtro);

            for (int i = 0; i < lista.Count; i++)
                Delete(lista[i]);
        }


        private void SalvaStoricoModello(ModelloDinamicoIstanza modello)
        {
            using (var cp = new CodeProfiler("SalvaStoricoModello"))
            {
                // Carico le righe modificate
                List<IstanzeDyn2DatiStorico> righeStorico = new List<IstanzeDyn2DatiStorico>();

                foreach (var rigaModello in modello.Righe)
                {
                    for (int j = 0; j < rigaModello.NumeroColonne; j++)
                    {
                        if (rigaModello[j] == null) continue;
                        if (rigaModello[j].Id < 0) continue;

                        var valoriDb = GetValoriCampoNoIndice(modello.IdComune, modello.IdIstanza, rigaModello[j].Id);

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
                                Idcomune = modello.IdComune,
                                Codiceistanza = modello.IdIstanza,
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
                var testataStoricoMgr = new IstanzeDyn2ModelliTStoricoMgr(db);
                var righeStoricoMgr = new IstanzeDyn2DatiStoricoMgr(db);

                // Salvo una nuova riga in IstanzeDyn2Modellit_storico
                var testataStorico = new IstanzeDyn2ModelliTStorico
                {
                    Idcomune = modello.IdComune,
                    Codiceistanza = modello.IdIstanza,
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

        public void EliminaValoriCampi(ModelloDinamicoIstanza modello, IEnumerable<CampoDinamico> campiDaEliminare)
        {
            bool commitTrans = !db.IsInTransaction;

            if (commitTrans)
                db.BeginTransaction();
            try
            {
                foreach (var campo in campiDaEliminare)
                {
                    for (int j = 0; j < campo.ListaValori.Count; j++)
                    {
                        var cls = GetById(modello.IdComune, modello.IdIstanza, campo.Id, campo.ModelloCorrente.IndiceModello, j);

                        if (cls != null)
                            Delete(cls);
                    }
                }
            }
            catch (Exception)
            {
                if (commitTrans)
                    db.RollbackTransaction();

                throw;
            }
            finally
            {
                if (commitTrans)
                    db.CommitTransaction();
            }
        }

        #endregion


        /// <summary>
        /// Verifica se esiste un campo collegato ad un istanza con il codice e il valore o parte el valore indicato (sintassi LIKE)
        /// </summary>
        /// <param name="idComune">id comune</param>
        /// <param name="codiceCampo">codice del campo</param>
        /// <param name="valore">Valore o parte del valore con sintassi "like"</param>
        /// <returns>True se esiste almeno un campo con il codice e il valore passato</returns>
        internal IEnumerable<int> GetCodiciIstanzaByCodicecampoEValore(string idComune, int codiceCampo, string valore)
        {

            bool closeCnn = false;

            try
            {
                var sql = "select codiceistanza from istanzedyn2dati WHERE idcomune = {0} AND fk_d2c_id = {1} AND " + db.Specifics.ClobLike("valore", "valoreCampo", true);

                sql = PreparaQueryParametrica(sql, "idComune", "codiceCampo");


                if (db.Connection.State == ConnectionState.Closed)
                {
                    db.Connection.Open();
                    closeCnn = true;
                }

                using (IDbCommand cmd = db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(db.CreateParameter("codiceCampo", codiceCampo));
                    cmd.Parameters.Add(db.CreateParameter("valoreCampo", valore.ToUpper()));

                    var codiciIstanza = new List<int>();

                    using (var dr = cmd.ExecuteReader())
                    {
                        while (dr.Read())
                        {
                            codiciIstanza.Add(Convert.ToInt32(dr[0]));
                        }
                    }

                    return codiciIstanza;
                }
            }
            finally
            {
                if (closeCnn)
                    db.Connection.Close();
            }

        }
    }
}
