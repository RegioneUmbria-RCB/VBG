using Init.SIGePro.Data;
using Init.SIGePro.Manager.DTO.DatiDinamici.OnceOnly;
using Init.SIGePro.Manager.IOC;
using Init.SIGePro.Manager.Logic.GestioneSchedeAttivita.Eventi;
using Init.SIGePro.Manager.Validator;
using Init.Utils;
using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Linq;
using System.Reflection;
using Vbg.EventBus.Abstractions;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Utils;
using VBG.DatiDinamici.Web;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class IstanzeDyn2DatiMgr //: IIstanzeDyn2DatiManager
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(IstanzeDyn2DatiMgr));


        internal List<IstanzeDyn2Dati> GetValoriCampoNoIndice(string idComune, int idIstanza, int idCampo)
        {
            IstanzeDyn2Dati filtro = new IstanzeDyn2Dati();
            filtro.Idcomune = idComune;
            filtro.Codiceistanza = idIstanza;
            filtro.FkD2cId = idCampo;
            filtro.OrderBy = "indice_molteplicita asc";

            return this.GetList(filtro);
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

            return (IstanzeDyn2Dati)this.db.GetClass(filtro);
        }

        private void Validate(IstanzeDyn2Dati cls, AmbitoValidazione ambitoValidazione)
        {
            if (ambitoValidazione == AmbitoValidazione.Insert && !cls.IndiceMolteplicita.HasValue)
                cls.IndiceMolteplicita = 0;

            this.RequiredFieldValidate(cls, ambitoValidazione);
        }

        public List<IstanzeDyn2Dati> GetByIdNoIndice(string idcomune, int codiceistanza, int fk_d2c_id)
        {
            IstanzeDyn2Dati c = new IstanzeDyn2Dati();

            c.Idcomune = idcomune;
            c.Codiceistanza = codiceistanza;
            c.FkD2cId = fk_d2c_id;

            return this.db.GetClassList(c).ToList<IstanzeDyn2Dati>();
        }

        public List<IstanzeDyn2Dati> GetListByIdIndiceModello(string idcomune, int codiceistanza, int fk_d2c_id, int indiceModello)
        {
            IstanzeDyn2Dati c = new IstanzeDyn2Dati();

            c.Idcomune = idcomune;
            c.Codiceistanza = codiceistanza;
            c.FkD2cId = fk_d2c_id;
            c.Indice = indiceModello;

            return this.db.GetClassList(c).ToList<IstanzeDyn2Dati>();
        }

        public IEnumerable<IstanzeDyn2Dati> GetListByCodiceIstanzaIdModello(string idComune, int codiceIstanza, int idModello)
        {

            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                var sql = this.PreparaQueryParametrica(@"SELECT 
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

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("idModello", idModello));
                    cmd.Parameters.Add(this.db.CreateParameter("codiceIstanza", codiceIstanza));

                    return this.db.GetClassList<IstanzeDyn2Dati>(cmd);
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }

        }

        public IstanzeDyn2Dati[] GetListByCodiceIstanza(string idcomune, int codiceistanza)
        {
            IstanzeDyn2Dati c = new IstanzeDyn2Dati();

            c.Idcomune = idcomune;
            c.Codiceistanza = codiceistanza;

            return this.db.GetClassList(c).ToArray<IstanzeDyn2Dati>();
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

        public SerializableDictionary<int, List<IstanzeDyn2Dati>> GetValoriCampiDaIdModello(string idComune, int codiceIstanza, int idModello, int indiceModello)
        {

            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
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

                sql = this.PreparaQueryParametrica(sql, "idComune", "idModello", "codiceIstanza", "indiceModello");

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("idModello", idModello));
                    cmd.Parameters.Add(this.db.CreateParameter("codiceIstanza", codiceIstanza));
                    cmd.Parameters.Add(this.db.CreateParameter("indiceModello", indiceModello));

                    using (var dr = cmd.ExecuteReader())
                    {
                        var rVal = new SerializableDictionary<int, List<IstanzeDyn2Dati>>();

                        while (dr.Read())
                        {
                            var val = new IstanzeDyn2Dati
                            {
                                Idcomune = idComune,
                                Codiceistanza = codiceIstanza,
                                FkD2cId = Convert.ToInt32(dr["fk_d2c_id"]),
                                Indice = indiceModello,
                                IndiceMolteplicita = Convert.ToInt32(dr["Indice_Molteplicita"]),
                                Valore = dr["valore"].ToString(),
                                Valoredecodificato = dr["Valoredecodificato"].ToString()
                            };

                            int key = val.FkD2cId.Value;

                            if (!rVal.ContainsKey(key))
                                rVal.Add(key, new List<IstanzeDyn2Dati>());

                            rVal[key].Add(val);
                        }

                        return rVal;
                    }
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
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

        public void SalvaValoriCampi(string idComune, int codiceIstanza, DatiIdentificativiModello modello, IEnumerable<CampoDaSalvare> campiDaSalvare)
        {
            using (CodeProfiler.Track(MethodInfo.GetCurrentMethod()))
            {
                var commitTrans = !this.db.IsInTransaction;
                var indiceModello = modello.IndiceModello;
                //var idComune = modello.IdComune;

                if (commitTrans)
                    this.db.BeginTransaction();

                try
                {
                    //if (salvaStorico)
                    //    SalvaStoricoModello(modello);



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
                        if (!campoDaSalvare.IsCampoUpload)
                            continue;

                        var listaCampiNelDb = this.GetListByIdIndiceModello(idComune, codiceIstanza, campoDaSalvare.Id, modello.IndiceModello);

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
                        if (!campoDaSalvare.IsCampoUpload)
                            continue;

                        for (int indiceMolteplicita = 0; indiceMolteplicita < campoDaSalvare.ListaValori.Count; indiceMolteplicita++)
                        {
                            var valoreCampo = campoDaSalvare.ListaValori[indiceMolteplicita];

                            if (String.IsNullOrEmpty(valoreCampo.Valore))
                                continue;

                            if (listaOggettiNonModificati.Contains(valoreCampo.Valore))
                                continue;

                            listaOggettiDaCreare.Add(new KeyValuePair<int, string>(Convert.ToInt32(valoreCampo.Valore), $"{campoDaSalvare.Etichetta} {indiceMolteplicita}"));
                        }
                    }



                    // Elimino tutti i valori del campo per la scheda corrente
                    foreach (var campoDaSalvare in campiDaSalvare)
                    {
                        var idCampo = campoDaSalvare.Id;

                        this.EliminaValoriMultipliCampo(idComune, codiceIstanza, idCampo, indiceModello);
                    }

                    // Ricreo tutti i valori dei campi che non siano == ""
                    foreach (var campo in campiDaSalvare)
                    {

                        for (int indiceMolteplicita = 0; indiceMolteplicita < campo.ListaValori.Count; indiceMolteplicita++)
                        {
                            var valore = campo.ListaValori[indiceMolteplicita].Valore;
                            var valoreDecodificato = campo.ListaValori[indiceMolteplicita].ValoreDecodificato;

                            if (String.IsNullOrEmpty(valore?.Trim()))
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

                            this.Insert(cls);
                        }
                    }

                    if (commitTrans)
                        this.db.CommitTransaction();

                    commitTrans = false;

                    // L'operazione va effettuata fuori transazione altrimenti si creerebbe un lock sulla tabella oggetti
                    // visto che la cancellazione dell'oggetto dal db viene effettuata dal web service java.
                    // TODO: spostare l'eliminazione dell'allegato dell'istanza nel web service java
                    foreach (var codiceOggetto in listaOggettiDaEliminare)
                        this.EliminaAllegatiIstanza(idComune, codiceIstanza, Convert.ToInt32(codiceOggetto));

                    foreach (var allegatoDaAggiungere in listaOggettiDaCreare)
                        this.CreaAllegatoIstanza(idComune, codiceIstanza, allegatoDaAggiungere.Value, allegatoDaAggiungere.Key);


                    this.NotificaEventoSalvataggioScheda(idComune, codiceIstanza, modello.IdModello);
                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat("Errore durante il salvataggio dei dati dinamici nell'idcomune={0} e codiceistanza={1}: {2}", idComune, codiceIstanza, ex.ToString());

                    if (commitTrans)
                        this.db.RollbackTransaction();

                    throw;
                }
            }
        }

        private void NotificaEventoSalvataggioScheda(string idComune, int codiceIstanza, int idScheda)
        {
            /*
            var istanza = new IstanzeMgr(db).GetById(idComune, codiceIstanza);

            if (istanza == null)
                throw new Exception(String.Format("Istanza con idcomune {0} e codiceistanza {1} non trovata durante la verifica del collegamento con una attivita", idComune, codiceIstanza));

            if (!istanza.FK_IDI_ATTIVITA.HasValue)
                return;
            */
            var eventPublisher = StaticKernelContainer.GetService<IEventPublisher>();

            eventPublisher.Publish(new SchedaDinamicaIstanzaSalvata(codiceIstanza, idScheda));
        }

        private void EliminaAllegatiIstanza(string idComune, int codiceIstanza, int codiceOggetto)
        {
            var mgr = new DocumentiIstanzaMgr(this.db);

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

        public void CreaAllegatoIstanza(string idComune, int codiceIstanza, string nomeAllegato, int codiceOggetto)
        {
            var mgr = new DocumentiIstanzaMgr(this.db);

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

            var lista = this.GetList(filtro);

            for (int i = 0; i < lista.Count; i++)
                this.Delete(lista[i]);
        }

        public void EliminaValoriCampi(string idComune, int codiceIstanza, DatiIdentificativiModello modello, IEnumerable<DatiIdentificativiCampo> campiDaEliminare)
        {
            bool commitTrans = !this.db.IsInTransaction;

            if (commitTrans)
                this.db.BeginTransaction();
            try
            {
                foreach (var campo in campiDaEliminare)
                {
                    var sql = $@"delete from istanzedyn2dati where 
                                    idcomune = {this.db.Specifics.QueryParameterName("idComune")} and 
                                    codiceistanza = {this.db.Specifics.QueryParameterName("codiceIstanza")} and 
                                    fk_d2c_id = {this.db.Specifics.QueryParameterName("idCampo")} and 
                                    indice = {this.db.Specifics.QueryParameterName("indice")}";

                    this.db.ExecuteNonQuery(sql, mp =>
                    {
                        mp.AddParameter("idComune", idComune);
                        mp.AddParameter("codiceIstanza", codiceIstanza);
                        mp.AddParameter("idCampo", campo.Id);
                        mp.AddParameter("indice", modello.IndiceModello);
                    });
                }

                if (commitTrans)
                    this.db.CommitTransaction();
            }
            catch (Exception)
            {
                if (commitTrans)
                    this.db.RollbackTransaction();

                throw;
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
                var sql = "select codiceistanza from istanzedyn2dati WHERE idcomune = {0} AND fk_d2c_id = {1} AND " + this.db.Specifics.ClobLike("valore", "valoreCampo", true);

                sql = this.PreparaQueryParametrica(sql, "idComune", "codiceCampo");


                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("codiceCampo", codiceCampo));
                    cmd.Parameters.Add(this.db.CreateParameter("valoreCampo", valore.ToUpper()));

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
                    this.db.Connection.Close();
            }

        }

        public FonteDatiOnceOnlyDto[] GetIdentificativiOnceOnlyByIdInterventoEndo(string idComune, int idIntervento, int[] listaIdEndo)
        {
            var rval = new List<FonteDatiOnceOnlyDto>();

            rval.AddRange(this.GetIdentificativiOnceOnlyByIdIntervento(idComune, idIntervento));
            rval.AddRange(this.GetIdentificativiOnceOnlyByIdEndo(idComune, listaIdEndo));

            return rval.ToArray();
        }

        private IEnumerable<FonteDatiOnceOnlyDto> GetIdentificativiOnceOnlyByIdEndo(string idComune, int[] listaIdEndo)
        {
            if (!listaIdEndo.Any())
            {
                return Enumerable.Empty<FonteDatiOnceOnlyDto>();
            }

            var listaCodiciInventario = String.Join(",", listaIdEndo.Select(x => x.ToString()));


            var sql = $@"SELECT 
	                        dyn2_campi.id,
	                        dyn2_campi.nomecampo,
	                        dyn2_modellid.fonte_interna,
	                        dyn2_modellid.fonte_esterna,
                            dyn2_campi.tipodato
                        FROM
	                        inventarioprocdyn2modellit
		                        INNER JOIN dyn2_modellid ON
			                        dyn2_modellid.idcomune = inventarioprocdyn2modellit.idcomune AND
			                        dyn2_modellid.FK_D2MT_ID = inventarioprocdyn2modellit.fk_d2mt_id
		                        INNER JOIN dyn2_campi ON
			                        dyn2_campi.idcomune = dyn2_modellid.idcomune AND
			                        dyn2_campi.id = dyn2_modellid.FK_D2C_ID
                        WHERE
	                        dyn2_modellid.fonte_interna IS NOT NULL AND
	                        inventarioprocdyn2modellit.idcomune = {this.db.QueryParameter("idComune")}  AND 
	                        inventarioprocdyn2modellit.FLAG_PUBBLICA = 1 AND 
	                        inventarioprocdyn2modellit.codiceinventario IN ({listaCodiciInventario})";

            return this.db.ExecuteReader(
                sql,
                mp => mp.Add("idComune", idComune),
                dr => new FonteDatiOnceOnlyDto
                {
                    IdCampo = dr.GetInt("id").Value,
                    NomeCampo = dr.GetString("nomecampo"),
                    IsUpload = dr.GetString("tipodato") == TipoControlloEnum.Upload.ToString(),
                    FonteEsterna = dr.GetString("fonte_esterna") ?? "",
                    FonteInterna = dr.GetString("fonte_interna")
                });
        }

        private IEnumerable<FonteDatiOnceOnlyDto> GetIdentificativiOnceOnlyByIdIntervento(string idComune, int idIntervento)
        {
            var alberoMgr = new AlberoProcMgr(this.db);

            var ramoAlbero = alberoMgr.GetById(idIntervento, idComune);

            var listaScId = ramoAlbero.GetListaScCodice();

            var condWhere = listaScId.ToString();

            var sql = $@"SELECT 
	                        dyn2_campi.id,
	                        dyn2_campi.nomecampo,
	                        dyn2_modellid.fonte_interna,
	                        dyn2_modellid.fonte_esterna,
                            dyn2_campi.tipodato
                        FROM 
	                        alberoproc
		                        INNER JOIN alberoproc_dyn2modellit ON
			                        alberoproc_dyn2modellit.idcomune = alberoproc.idcomune AND
			                        alberoproc_dyn2modellit.fk_sc_id = alberoproc.sc_id
			
		                        INNER JOIN dyn2_modellid ON 
			                        dyn2_modellid.idcomune = alberoproc_dyn2modellit.idComune AND
			                        dyn2_modellid.FK_D2MT_ID = alberoproc_dyn2modellit.fk_d2mt_id
			
		                        INNER JOIN dyn2_campi ON
			                        dyn2_campi.idcomune = dyn2_modellid.idcomune AND
			                        dyn2_campi.id = dyn2_modellid.FK_D2C_ID
	
	                        WHERE
	                        dyn2_modellid.fonte_interna IS NOT NULL AND
	                        alberoproc.idComune = {this.db.QueryParameter("idComune")} AND 
	                        alberoproc.software = {this.db.QueryParameter("software")} AND 
	                        alberoproc_dyn2modellit.flag_pubblica = 1 and
                            alberoproc.sc_codice IN ({condWhere})";

            return this.db.ExecuteReader(
                sql,
                mp => mp.Add("idComune", idComune)
                        .Add("software", ramoAlbero.SOFTWARE),
                dr => new FonteDatiOnceOnlyDto
                {
                    IdCampo = dr.GetInt("id").Value,
                    NomeCampo = dr.GetString("nomecampo"),
                    IsUpload = dr.GetString("tipodato") == TipoControlloEnum.Upload.ToString(),
                    FonteEsterna = dr.GetString("fonte_esterna") ?? "",
                    FonteInterna = dr.GetString("fonte_interna")
                });

        }
    }
}
