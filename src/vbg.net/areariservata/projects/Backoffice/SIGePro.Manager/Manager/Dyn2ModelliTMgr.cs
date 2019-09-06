
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System.Data;
using System.ComponentModel;
using Init.SIGePro.Authentication;

using PersonalLib2.Sql;
using Init.Utils.Sorting;
using Init.SIGePro.Validator;
using Init.SIGePro.DatiDinamici.Interfaces;
using log4net;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
	public partial class Dyn2ModelliTMgr : IDyn2ModelliManager
    {
		ILog _log = LogManager.GetLogger(typeof(Dyn2ModelliTMgr));


		[DataObjectMethod(DataObjectMethodType.Select)]
		public static List<Dyn2ModelliT> Find(string token, string software, string id , string codice, string descrizione , string contesto, string sortExpression)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			var mgr = new Dyn2ModelliTMgr(authInfo.CreateDatabase());

			var filtro = new Dyn2ModelliT
			{
				Software = software,
				Idcomune = authInfo.IdComune,
				Id = String.IsNullOrEmpty(id) ? (int?)null : Convert.ToInt32(id),
				CodiceScheda = codice,
				Descrizione = descrizione,
				FkD2bcId = contesto,
			};

			var  filtroCompare = new Dyn2ModelliT
			{
				CodiceScheda = "LIKE",
				Descrizione = "LIKE",
				FkD2bcId = "LIKE"
			};

			List<Dyn2ModelliT> list = authInfo.CreateDatabase()
											  .GetClassList(filtro, filtroCompare, false, true)
											  .ToList<Dyn2ModelliT>();

			ListSortManager<Dyn2ModelliT>.Sort(list, sortExpression);

			return list;
		}

		public IEnumerable<Dyn2ModelliT> GetListaModelliPerStatistiche(string idcomune, string software)
		{
			bool internalOpen = false;
			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					internalOpen = true;
				}

				string cmdText = "SELECT software,id,descrizione FROM dyn2_modellit WHERE IDCOMUNE = {0} AND (software = {1} or software = 'TT') order by Descrizione asc";

				cmdText = PreparaQueryParametrica(cmdText, "idcomune", "software");

				using (IDbCommand cmd = db.CreateCommand(cmdText))
				{
					cmd.Parameters.Add(db.CreateParameter("idcomune", idcomune));
					cmd.Parameters.Add(db.CreateParameter("software", software));

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
					db.Connection.Close();
			}
		}

		public void Delete(Dyn2ModelliT cls)
		{
			bool commitTrans = false;

			try
			{
				if (!db.IsInTransaction)
				{
					db.BeginTransaction();
					commitTrans = true;
				}

				VerificaRecordCollegati(cls);

				EffettuaCancellazioneACascata(cls);

				db.Delete(cls);

				if (commitTrans)
					db.CommitTransaction();
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante l'eliminazione del modello con idcomune={0} e id={1}: {2}", cls.Idcomune, cls.Id, ex.ToString());

				if (commitTrans)
					db.RollbackTransaction();

				throw;
			}
		}


		private void EffettuaCancellazioneACascata(Dyn2ModelliT cls)
		{
			// Cancello tutti i dettagli del modello
			Dyn2ModelliD filtroDettagli = new Dyn2ModelliD();
			filtroDettagli.Idcomune = cls.Idcomune;
			filtroDettagli.FkD2mtId = cls.Id;

			Dyn2ModelliDMgr mgrDettagli = new Dyn2ModelliDMgr(db);

			List<Dyn2ModelliD> dettagli = mgrDettagli.GetList(filtroDettagli);

			for (int i = 0; i < dettagli.Count; i++)
			{
				mgrDettagli.Delete(dettagli[i]);
			}

			var formuleMgr = new Dyn2ModelliScriptMgr(db);
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
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle

			// Verifico se il modello è contenuto in un'istanza
			IstanzeDyn2ModelliT filtroIstanze = new IstanzeDyn2ModelliT();
			filtroIstanze.Idcomune = cls.Idcomune;
			filtroIstanze.FkD2mtId = cls.Id;

			List<IstanzeDyn2ModelliT> modelliIstanze = new IstanzeDyn2ModelliTMgr(db).GetList(filtroIstanze);

			if (modelliIstanze.Count > 0)
				throw new ReferentialIntegrityException("ISTANZEDYN2MODELLIT");

			// Verifico se il modello è contenuto in un'attivita
			IAttivitaDyn2ModelliT filtroAttivita = new IAttivitaDyn2ModelliT();
			filtroAttivita.Idcomune = cls.Idcomune;
			filtroAttivita.FkD2mtId = cls.Id;

			List<IAttivitaDyn2ModelliT> modelliAttivita = new IAttivitaDyn2ModelliTMgr(db).GetList(filtroAttivita);

			if (modelliAttivita.Count > 0)
				throw new ReferentialIntegrityException("I_ATTIVITADYN2MODELLIT");

			// Verifico se il modello è contenuto in un'anagrafica
			AnagrafeDyn2ModelliT filtroAnagrafe = new AnagrafeDyn2ModelliT();
			filtroAnagrafe.Idcomune = cls.Idcomune;
			filtroAnagrafe.FkD2mtId = cls.Id;

			List<AnagrafeDyn2ModelliT> modelliAnagrafe = new AnagrafeDyn2ModelliTMgr(db).GetList(filtroAnagrafe);

			if (modelliAnagrafe.Count > 0)
				throw new ReferentialIntegrityException("ANAGRAFEDYN2MODELLIT");

            // Verifico se il modello è collegato all'albero dei procedimenti
            AlberoProcDyn2ModelliT filtroAlberoProc = new AlberoProcDyn2ModelliT();
            filtroAlberoProc.Idcomune = cls.Idcomune;
            filtroAlberoProc.FkD2mtId = cls.Id;

            List<AlberoProcDyn2ModelliT> modelliAlberoProc = new AlberoProcDyn2ModelliTMgr(db).GetList(filtroAlberoProc);

            if (modelliAlberoProc.Count > 0)
                throw new ReferentialIntegrityException("ALBEROPROC_DYN2MODELLIT");

            // Verifico se il modello è collegato agli interventi
            InterventiDyn2ModelliT filtroInterventi = new InterventiDyn2ModelliT();
            filtroInterventi.Idcomune = cls.Idcomune;
            filtroInterventi.FkD2mtId = cls.Id;

            List<InterventiDyn2ModelliT> modelliInterventi = new InterventiDyn2ModelliTMgr(db).GetList(filtroInterventi);

            if (modelliInterventi.Count > 0)
                throw new ReferentialIntegrityException("INTERVENTIDYN2MODELLIT");

            // Verifico se il modello è collegato agli endoprocedimenti
            InventarioProcDyn2ModelliT filtroInventarioProc = new InventarioProcDyn2ModelliT();
            filtroInventarioProc.Idcomune = cls.Idcomune;
            filtroInventarioProc.FkD2mtId = cls.Id;

            List<InventarioProcDyn2ModelliT> modelliInventarioProc = new InventarioProcDyn2ModelliTMgr(db).GetList(filtroInventarioProc);

            if (modelliInventarioProc.Count > 0)
                throw new ReferentialIntegrityException("INVENTARIOPROCDYN2MODELLIT");

            // Verifico se il modello è collegato ai movimenti
            MovimentiDyn2ModelliT filtroMovimenti = new MovimentiDyn2ModelliT();
            filtroMovimenti.Idcomune = cls.Idcomune;
            filtroMovimenti.FkD2mtId = cls.Id;

            List<MovimentiDyn2ModelliT> modelliMovimenti = new MovimentiDyn2ModelliTMgr(db).GetList(filtroMovimenti);

            if (modelliMovimenti.Count > 0)
                throw new ReferentialIntegrityException("MOVIMENTIDYN2MODELLIT");

            // Verifico se il modello è collegato ai tipimovimenti
            TipiMovimentiDyn2ModelliT filtroTipiMovimenti = new TipiMovimentiDyn2ModelliT();
            filtroTipiMovimenti.Idcomune = cls.Idcomune;
            filtroTipiMovimenti.FkD2mtId = cls.Id;

            List<TipiMovimentiDyn2ModelliT> modelliTipiMovimenti = new TipiMovimentiDyn2ModelliTMgr(db).GetList(filtroTipiMovimenti);

            if (modelliTipiMovimenti.Count > 0)
                throw new ReferentialIntegrityException("TIPIMOVIMENTIDYN2MODELLIT");

            // Verifico se il modello è collegato ai bandi
            TipiBando filtroTipiBando = new TipiBando();
            filtroTipiBando.Idcomune = cls.Idcomune;
            filtroTipiBando.FkD2mtId = cls.Id;

            List<TipiBando> modelliTipiBando = new TipiBandoMgr(db).GetList(filtroTipiBando);

            if (modelliTipiBando.Count > 0)
                throw new ReferentialIntegrityException("TIPIBANDO");

            bool internalOpen = false;
            if (db.Connection.State == ConnectionState.Closed)
            {
                db.Connection.Open();
                internalOpen = true;
            }

            string cmdText = "SELECT COUNT(*) FROM MERCATI_CONFIGURAZIONE WHERE IDCOMUNE = '" + cls.Idcomune + "' AND FK_DYN2MODELLIT = " + cls.Id.ToString();
            using (IDbCommand cmd = db.CreateCommand(cmdText))
            {
                object obj = cmd.ExecuteScalar();
                if (obj != null && Convert.ToInt32(obj) > 0)
                {
                    throw new ReferentialIntegrityException("MERCATI_CONFIGURAZIONE");
                }
            }

            if (internalOpen)
                db.Connection.Close();
		}

		/// <summary>
		/// Verifica se nel modello passato sono presenti righe con il flag flg_multiplo == 1
		/// </summary>
		/// <param name="idComune"></param>
		/// <param name="idModello"></param>
		/// <returns></returns>
		public bool VerificaEsistenzaRigheMultiple(string idComune, int idModello)
		{

			int cnt = recordCount( "dyn2_modellid","*" , @"where idcomune = '" + idComune + @"' AND
																 fk_d2mt_id = " + idModello + @" AND
																 flg_multiplo = 1");

			return cnt > 0;
		}



		private void Validate(Dyn2ModelliT cls, AmbitoValidazione ambitoValidazione)
		{
			var modificaCodice = String.IsNullOrEmpty(cls.CodiceScheda);

			if (modificaCodice)
				cls.CodiceScheda = "SCHEDA";

			RequiredFieldValidate(cls, ambitoValidazione);

			if (modificaCodice)
				cls.CodiceScheda = "SCHEDA_" + cls.Id;

			if (ambitoValidazione == AmbitoValidazione.Update)
			{
				Dyn2ModelliT clsTmp = GetById(cls.Idcomune, cls.Id.GetValueOrDefault(-1));

				int recCount = 0; // Utilizzato nelel varie count

				#region Verifica del flag "Contesto"
				if (clsTmp.FkD2bcId != cls.FkD2bcId) // il contesto è stato modificato
				{
					bool ambitoIstanza = false;
					bool ambitoAnagrafica = false;
					bool ambitoAttivita = false;

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

					if (ambitoIstanza)
					{
						recCount = recordCount("istanzedyn2modellit", "fk_d2mt_id", "where idcomune='" + cls.Idcomune + "' and fk_d2mt_id=" + cls.Id);

						if (recCount > 0)
							throw new InvalidOperationException("Impossibile modificare il contesto del modello in quanto ci sono istanze che lo utilizzano");
					}

					if (ambitoAnagrafica)
					{
						recCount = recordCount("anagrafedyn2modellit", "fk_d2mt_id", "where idcomune='" + cls.Idcomune + "' and fk_d2mt_id=" + cls.Id);

						if (recCount > 0)
							throw new InvalidOperationException("Impossibile modificare il contesto del modello in quanto ci sono anagrafiche che lo utilizzano");
					}

					if (ambitoAttivita)
					{
						recCount = recordCount("i_attivitadyn2modellit", "fk_d2mt_id", "where idcomune='" + cls.Idcomune + "' and fk_d2mt_id=" + cls.Id);

						if (recCount > 0)
							throw new InvalidOperationException("Impossibile modificare il contesto del modello in quanto ci sono attività che lo utilizzano");
					}
				}


				#endregion

				#region Verifica del flag "Modello multiplo"
				if ( cls.Modellomultiplo != clsTmp.Modellomultiplo && cls.Modellomultiplo.GetValueOrDefault(0) == 0)
				{

					// Se è stato rimosso il flag di modello multiplo ma esistono nell'istanza campi con un indice > 0 
					// sollevo un'eccezione
					bool ambitoIstanza = false;
					bool ambitoAnagrafica = false;
					bool ambitoAttivita = false;

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
						recCount = recordCount("istanzedyn2dati,istanzedyn2modellit",
													"fk_d2c_id",
													String.Format(@"where istanzedyn2dati.idcomune = istanzedyn2modellit.idcomune AND
																		  istanzedyn2dati.codiceistanza = istanzedyn2modellit.codiceistanza AND
																		  istanzedyn2modellit.idcomune = '{0}' AND
																		  istanzedyn2modellit.fk_d2mt_id = {1} and
																		  istanzedyn2dati.indice > 0", cls.Idcomune, cls.Id)
													);

						if ( recCount > 0 )
							throw new Exception("Impossibile rimuovere il flag multiplo per il modello in quanto è già stato utilizzato in una o più istanze");
					}

					if (ambitoAnagrafica)
					{
						recCount = recordCount("anagrafedyn2dati,anagrafedyn2modellit",
													"fk_d2c_id",
													String.Format(@"where anagrafedyn2dati.idcomune = anagrafedyn2modellit.idcomune AND
																		  anagrafedyn2dati.codiceanagrafe = anagrafedyn2modellit.codiceanagrafe AND
																		  anagrafedyn2modellit.idcomune = '{0}' AND
																		  anagrafedyn2modellit.fk_d2mt_id = {1} and
																		  anagrafedyn2dati.indice>0", cls.Idcomune, cls.Id )
													);

						if (recCount > 0)
							throw new Exception("Impossibile rimuovere il flag multiplo per il modello in quanto è già stato utilizzato in una o più anagrafiche");
					}

					if (ambitoAttivita)
					{
						recCount = recordCount("i_attivitadyn2dati,i_attivitadyn2modellit",
												"fk_d2c_id",
												String.Format(@"where i_attivitadyn2dati.idcomune = i_attivitadyn2modellit.idcomune AND
																	  i_attivitadyn2dati.fk_ia_id = i_attivitadyn2modellit.fk_ia_id AND
																	  i_attivitadyn2modellit.idcomune = '{0}' AND
																	  i_attivitadyn2modellit.fk_d2mt_id = {1} and
																	  i_attivitadyn2dati.indice > 0", cls.Idcomune, cls.Id)
												);

						if (recCount > 0)
							throw new Exception("Impossibile rimuovere il flag multiplo per il modello in quanto è già stato utilizzato in una o più attività");
					}
				}
				#endregion

				
			}
		}

		#region IDyn2ModelliManager Members

		IDyn2Modello IDyn2ModelliManager.GetById(string idComune, int idModello)
		{
			return GetById(idComune, idModello);
		}

		#endregion


		public int CopiaScheda(string IdComune, int idSchedaDaCopiare, bool copiaFormule)
		{
			var campiMgr = new Dyn2ModelliDMgr(db);
			var formuleMgr = new Dyn2ModelliScriptMgr(db);
			var testiMgr = new Dyn2ModelliDTestiMgr(db);

			var modelloDaCopiare = GetById(IdComune, idSchedaDaCopiare);
			var campiDaCopiare = campiMgr.GetList(new Dyn2ModelliD { 
				Idcomune = IdComune,
				FkD2mtId = idSchedaDaCopiare
			});


			try
			{
				db.BeginTransaction();

				modelloDaCopiare.Id = null;
				modelloDaCopiare.Descrizione = "Copia di " + modelloDaCopiare.Descrizione;
				modelloDaCopiare.CodiceScheda = modelloDaCopiare.CodiceScheda + "_COPIA";

				var nuovoModello = Insert(modelloDaCopiare);

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

				db.CommitTransaction();

				return nuovoModello.Id.Value;				
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore in CopiaScheda con idComune={0}, idSchedaDaCopiare={1}, copiaFormule={2}: {3}", IdComune, idSchedaDaCopiare, copiaFormule, ex.ToString());

				db.RollbackTransaction();

				throw;
			}
		}



		public IEnumerable<Dyn2ModelliT> GetSchedeContenentiIlCampo(string idComune, int? idCampo)
		{

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
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

				sql = PreparaQueryParametrica(sql, "idComune", "idCampo");

				using (var cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("idCampo", idCampo.GetValueOrDefault(int.MinValue)));

					return db.GetClassList<Dyn2ModelliT>(cmd);
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
				