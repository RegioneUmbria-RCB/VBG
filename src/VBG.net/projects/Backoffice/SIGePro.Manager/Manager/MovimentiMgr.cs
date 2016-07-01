using System;
using System.Collections;
using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.Utils;
using PersonalLib2.Data;
using Init.SIGePro.Validator;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.Manager;
using Init.SIGePro.Manager.Logic.DatiDinamici;
using Init.SIGePro.Manager.Logic.Visura;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.Manager.Logic.DatiDinamici.DataAccessProviders;

namespace Init.SIGePro.Manager
{
	public class EsitoVerificaMovimento
	{
		public List<string> Errori { get; set; }
		public List<string> Avvertimenti { get; set; }
		public List<string> Informazioni { get; set; }

		public EsitoVerificaMovimento()
		{
			Avvertimenti = new List<string>();
			Informazioni = new List<string>();
			Errori = new List<string>();
		}
	}

	public class EsitoInserimentoMovimento : EsitoVerificaMovimento
	{
		public Movimenti Movimento { get; set; }
		public bool RilascioAutorizzazione { get; internal set; }
		public bool EffettuaChiusura { get; internal set; }
		public bool ProtocollazioneEffettuata { get; internal set; }
		public string NumeroProtocollo { get; internal set; }
		public string DataProtocollo { get; internal set; }

		public EsitoInserimentoMovimento()
			: base()
		{
			RilascioAutorizzazione = false;
			EffettuaChiusura = false;
			ProtocollazioneEffettuata = false;
		}

		public EsitoInserimentoMovimento(EsitoVerificaMovimento evm)
			: this()
		{
			Avvertimenti = evm.Avvertimenti;
			Informazioni = evm.Informazioni;
			Errori = evm.Errori;
		}
	}


	public class MovimentiMgr : BaseManager
	{
		#region proprietà per la protocollazione
		/// <summary>
		/// Se impostato a true effettua la chiamata al manager di protocollazione
		/// </summary>
		bool _protocollamovimento = false;
		public bool Protocollamovimento
		{
			get { return _protocollamovimento; }
			set { _protocollamovimento = value; }
		}

		/// <summary>
		/// Definisce da dove arriva la richiesta di protocollazione
		/// </summary>
        int _protocollamovimentoSource = 32; //Corrisponde a Source.PROT_MOV_ONLINE della classe ProtocolloMgr dell'assembly Sigepro.Protocollo
		public int ProtocollamovimentoSource
		{
			get { return _protocollamovimentoSource; }
			set { _protocollamovimentoSource = value; }
		}
		#endregion

        #region proprietà per la fascicolazione
        /// <summary>
        /// Se impostato a true effettua la chiamata al manager di protocollazione
        /// </summary>
        bool _fascicolamovimento = false;
        public bool Fascicolamovimento
        {
            get { return _fascicolamovimento; }
            set { _fascicolamovimento = value; }
        }

        /// <summary>
        /// Definisce da dove arriva la richiesta di fascicolazione
        /// </summary>
        int _fascicolamovimentoSource = 32;  //Corrisponde a Source.FASC_MOV_ONLINE della classe ProtocolloMgr dell'assembly Sigepro.Protocollo
        public int FascicolamovimentoSource
        {
            get { return _fascicolamovimentoSource; }
            set { _fascicolamovimentoSource = value; }
        }
        #endregion

		public MovimentiMgr(DataBase dataBase) : base(dataBase) { }

		#region Lettura dei dati dal db

		public Movimenti GetById(string idComune, int codiceMovimento)
		{
			return GetById(codiceMovimento.ToString(), idComune);
		}

		[Obsolete]
		public Movimenti GetById(String pCODICEMOVIMENTO, String pIDCOMUNE)
		{
			Movimenti retVal = new Movimenti();
			retVal.CODICEMOVIMENTO = pCODICEMOVIMENTO;
			retVal.IDCOMUNE = pIDCOMUNE;

			return (Movimenti)db.GetClass(retVal);
		}

		public Movimenti GetByClass(Movimenti pClass)
		{

			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(pClass, true, false);
			if (mydc.Count != 0)
				return (mydc[0]) as Movimenti;

			return null;
		}

        public IEnumerable<Movimenti> GetMovimentiProtocollati(string idComune, string codiceIstanza)
        {
            var movimenti = this.GetMovimentiIstanza(idComune, codiceIstanza);
            var movimentiProtocollati = movimenti.Where(x => !String.IsNullOrEmpty(x.NUMEROPROTOCOLLO) && x.DATAPROTOCOLLO.HasValue);
            return movimentiProtocollati;
        }

        public List<Movimenti> GetMovimentiIstanza(string idComune, string codiceIstanza)
        {
            var movimenti = new Movimenti { IDCOMUNE = idComune, CODICEISTANZA = codiceIstanza };
            return db.GetClassList(movimenti).ToList<Movimenti>();
        }

		public List<Movimenti> GetList(Movimenti p_class)
		{
			return this.GetList(p_class, null);
		}

		public List<Movimenti> GetList(Movimenti p_class, Movimenti p_cmpClass)
		{
			return db.GetClassList(p_class, p_cmpClass, false, false).ToList<Movimenti>();
		}

		#endregion

        public void UpdateDatiProtocollo(string idProtocollo, string numeroProtocollo, DateTime dataProtocollo, string idComune, int codiceMovimento)
        {
            string sql = "update movimenti set fkidprotocollo = {0}, numeroprotocollo = {1}, dataprotocollo = {2} where idcomune = {3} and codicemovimento = {4}";

            bool closeCnn = false;

            if (db.Connection.State == ConnectionState.Closed)
            {
                closeCnn = true;
                db.Connection.Open();
            }

            try
            {
                sql = PreparaQueryParametrica(sql, "fkidProtocollo", "numeroProtocollo", "dataProtocollo", "idComune", "codiceMovimento");

                using (IDbCommand cmd = db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(db.CreateParameter("fkidProtocollo", String.IsNullOrEmpty(idProtocollo) ? DBNull.Value : (object)idProtocollo));
                    cmd.Parameters.Add(db.CreateParameter("numeroProtocollo", numeroProtocollo));
                    cmd.Parameters.Add(db.CreateParameter("dataProtocollo", dataProtocollo));
                    cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(db.CreateParameter("codiceMovimento", codiceMovimento));

                    cmd.ExecuteNonQuery();
                }

            }
            finally
            {
                if (closeCnn)
                    db.Connection.Close();
            }
        }

		public EsitoInserimentoMovimento Update(Movimenti cls, ComportamentoElaborazioneEnum comportamentoElaborazione)
		{
			bool commitTrans = false;

			try
			{
				if (!db.IsInTransaction)
				{
					commitTrans = true;
					db.BeginTransaction();
				}
				var rVal = new EsitoInserimentoMovimento();

				if (String.IsNullOrEmpty(cls.CODICEMOVIMENTO))
					throw new ArgumentException("MovimentiMgr->Update: codice movimento non passato");

				InsertOrUpdate(false,cls, rVal);

				if (commitTrans)
					db.CommitTransaction();

				new ElaborazioneScadenzarioMgr().Elabora(cls.IDCOMUNE, Convert.ToInt32(cls.CODICEISTANZA), comportamentoElaborazione);

				return rVal;
			}
			catch (Exception ex)
			{
				if (commitTrans)
					db.RollbackTransaction();

				throw;
			}
		}

		#region Metodi utilizzati durante l'inserimento o l'aggiornamento
		/// <summary>
		/// Effettua l'inserimento o l'aggiornamento del movimento
		/// </summary>
		/// <param name="cls"></param>
		/// <param name="insert"></param>
		/// <param name="esito"></param>
		private void InsertOrUpdate(bool insert, Movimenti cls, EsitoInserimentoMovimento esito)
		{
			if (insert)
			{
				/* Effettua l'inserimento nel database */
				db.Insert(cls);
			}
			else
			{
				db.Update(cls);
			}

			esito.Movimento = cls;

			/* Verifica se il movimento deve rilasciare un'autorizzazione */
			esito.RilascioAutorizzazione = VerificaRilascioAutorizzazione(cls);

			/* Verifico se il movimento è il movimento di chiusura */
			esito.EffettuaChiusura = VerificaChiusura(cls);
		}


		/// <summary>
		/// Verifica se in base al movimento vanno collegate delle schede dinamiche
		/// </summary>
		/// <param name="cls"></param>
		/// <param name="esito"></param>
		private void CollegaSchedeDinamiche(Movimenti cls, EsitoInserimentoMovimento esito)
		{
			throw new NotImplementedException("Questo metodo non dovrebbe essere utilizzato");
			/*
			string idComune = cls.IDCOMUNE;
			int codiceMovimento = Convert.ToInt32(cls.CODICEMOVIMENTO);
			int codiceIstanza = Convert.ToInt32(cls.CODICEISTANZA);

			var filtro = new TipiMovimentiDyn2ModelliT
			{
				Idcomune = cls.IDCOMUNE,
				Tipomovimento = cls.TIPOMOVIMENTO
			};

			var listaSchedeDinamiche = new TipiMovimentiDyn2ModelliTMgr(db).GetList(filtro);

			var mgrSchedeDinamiche = new MovimentiDyn2ModelliTMgr(db);

			for (int i = 0; i < listaSchedeDinamiche.Count; i++)
			{
				int idModello = listaSchedeDinamiche[i].FkD2mtId.Value;

				var schedaDinamica = mgrSchedeDinamiche.GetById(idComune, idModello, codiceMovimento);

				if (schedaDinamica == null)
					continue;

				schedaDinamica = new MovimentiDyn2ModelliT
				{
					Idcomune = idComune,
					Codiceistanza = codiceIstanza,
					FkD2mtId = idModello,
					Codicemovimento = codiceMovimento
				};

				mgrSchedeDinamiche.Insert(schedaDinamica);

				var dap = new IstanzeDyn2DataAccessProvider(db,codiceIstanza, idComune);
				var mdi = new ModelloDinamicoIstanza(dap, idComune, idModello, codiceIstanza, 0, false);
				mdi.Salva();

				var msg = String.Format("Aggiunta la scheda dinamica \"{0}\"", mdi.Descrizione);
				esito.Informazioni.Add(msg);
			}
			*/
		}

		/// <summary>
		/// Verifica se il movimento è  quello di chiusura dell’istanza in base alla procedura 
		/// </summary>
		/// <param name="cls"></param>
		/// <returns></returns>
		private bool VerificaChiusura(Movimenti cls)
		{
			var idComune = cls.IDCOMUNE;
			var codiceIstanza = Convert.ToInt32(cls.CODICEISTANZA);
			var codTipoMovimento = cls.TIPOMOVIMENTO;

			var procedura = new TipiProcedureMgr(db).GetByCodiceIstanza(idComune, codiceIstanza);

			return (procedura.Idchiusuraistanza == codTipoMovimento);
		}


		/// <summary>
		/// Verifica se il movimento rilascia un autorizzazione
		/// -	Verifica su tipimovimento il flag flag_registro, se == 1 il movimento causa 
		///		il rilascio di un autorizzazione
		///	-	Legge nella procedura se IDESITOPROVVAUTORIZZATIVO == tipo movimento. 
		///		Se true causa il rilascio di un’autorizzazione
		/// </summary>
		/// <param name="cls"></param>
		/// <param name="esito"></param>
		private bool VerificaRilascioAutorizzazione(Movimenti cls)
		{
			// TESTME
			var idComune = cls.IDCOMUNE;
			var codiceIstanza = Convert.ToInt32(cls.CODICEISTANZA);
			var codTipoMovimento = cls.TIPOMOVIMENTO;

			/* verifico se il movimento rilascia l'autorizzazione */
			var tipoMovimento = new TipiMovimentoMgr(db).GetById(codTipoMovimento, idComune);
			var procedura = new TipiProcedureMgr(db).GetByCodiceIstanza(idComune, codiceIstanza);

			if (tipoMovimento.FlagRegistro.GetValueOrDefault(0) == 0 && procedura.Idesitoprovvautorizzativo != codTipoMovimento)
				return false;

			return true;
		}


		/// <summary>
		/// Verifica se il movimento crea una cds
		///		- Verifica se in base alla procedura il movimento è quello che crea una cds
		///		-	Verifica se il tipo movimento ha il FLG_CREACDS = 1
		///		-	Verifica che il movimento non abbia già generato una cds (in caso di update del movimento)
		///		-	Se la cds va creata
		///			-	Dalla procedura vanno letti i giorni da aggiungere alla data del movimento per sapere la data di convocazione
		///			-	Prende tutte le amministrazioni che hanno attivato un endo nell’istanza e le aggiunge alla lista degli invitati (CDSINVITATI) 
		/// </summary>
		/// <param name="cls"></param>
		/// <param name="esito"></param>
		private void CreaCDS(Movimenti cls, EsitoInserimentoMovimento esito)
		{
			// TESTME

			var idComune = cls.IDCOMUNE;
			var codiceIstanza = Convert.ToInt32(cls.CODICEISTANZA);
			var codTipoMovimento = cls.TIPOMOVIMENTO;


			/* Verifico se il movimento deve creare una CDS in base alla configurazione o alla procedura*/
			var configurazione = new ConfigurazioneMgr(db).GetById(idComune, "TT");					// lettura della configurazione di SIGePro
			var procedura = new TipiProcedureMgr(db).GetByCodiceIstanza(idComune, codiceIstanza);	// lettura della configurazione della procedura

			//if (codTipoMovimento != configurazione.IDCDSVPR && codTipoMovimento != procedura.IDCOMUNCDS)
			//    return;

			/* Verifico se il movimento ha già generato una CDS */
			var where = String.Format("idcomune = '{0}' and codiceistanza = {1} and codicemovimento = {2}", idComune, codiceIstanza, cls.CODICEMOVIMENTO);
			var count = recordCount("CDS", "codicemovimento", where);

			if (count > 0)
				return;

			/* Verifico se il movimento FLAG_CDS impostato nella tabella movimenti */
			var tipoMovimento = new TipiMovimentoMgr(db).GetById(codTipoMovimento, idComune);

			if (tipoMovimento.FlagCds.GetValueOrDefault(0) != 1)
				return;

			/* procedo con la creazione della cds */
			var istanza = new IstanzeMgr(db).GetById(idComune, codiceIstanza);

			var flagVPR = istanza.VARIANTEPR == "1";


			var filtroWhere = new List<KeyValuePair<string, object>>();
			filtroWhere.Add(new KeyValuePair<string, object>("CODICEISTANZA", codiceIstanza));

			var idTestata = FindMax("IDTESTATA", "CDS", idComune, filtroWhere);
            
			var dataConvocazione = cls.DATA.Value.AddDays(Convert.ToInt32(procedura.Numggcdspiudata));

			var nuovaCds = new Cds
			{

				Idcomune = idComune,
				Idtestata = idTestata,
				Codiceistanza = codiceIstanza,
				Codiceatto = 0,
				Dataconvocazione = dataConvocazione,
				Oraconvocazione = "08:30",
				Invitorichiedente = 0,
				Flagvia = flagVPR ? 1 : 0,
				Codicemovimento = Convert.ToInt32(cls.CODICEMOVIMENTO)
			};

			new CdsMgr(db).Insert(nuovaCds);

			/* Inserisco le convocazioni */
			var convocazione = new CdsConvocazioni
			{
				Id = 1, // Ho creato ora la cds, l'id è sempre 1
				Idcomune = idComune,
				Codiceistanza = codiceIstanza,
				Idtestata = idTestata,
				Dataconvocazione = dataConvocazione
			};

			new CdsConvocazioniMgr(db).Insert(convocazione);


			/* inserisco gli inviti */
			var listaAmministrazioni = new IstanzeProcedimentiMgr(db).GetListaAmministrazioniCoinvolte(idComune, codiceIstanza);

			var cdsInvitatiMgr = new CdsInvitatiMgr(db);

			foreach (var amministrazione in listaAmministrazioni)
			{
				var invitato = new CdsInvitati
				{
					Codiceatto = 0,
					Codiceistanza = codiceIstanza,
					Codiceamministrazione = amministrazione,
					Idcomune = idComune,
					Fkidtestata = idTestata
				};

				cdsInvitatiMgr.Insert(invitato);
			}

			esito.Informazioni.Add("E'stata creata una nuova cds");
		}


		/// <summary>
		/// Imposta il prezzo dell’istruttoria come il prezzo dell’onere. 
		/// Se c’è una join TIPIMOVIMENTOONERI e TIPICAUSALIONERI con TIPIMOVIMENTOONERI.CODICECOMPORTAMENTO= 4. 
		/// Prende tutti gli oneri non pagati con prezzo > 0 e prezzo istruttoria = 0 o nullo e 
		/// gli imposta PREZZOISTRUTTORIA = PREZZO
		/// </summary>
		/// <param name="cls"></param>
		/// <param name="esito"></param>
		private void SpostaImportoOneri(Movimenti cls, EsitoInserimentoMovimento esito)
		{
			// TESTME

			var idComune = cls.IDCOMUNE;
			var codiceIstanza = Convert.ToInt32(cls.CODICEISTANZA);
			var tipoMovimento = cls.TIPOMOVIMENTO;

			var istanza = new IstanzeMgr(db).GetById(idComune, codiceIstanza);

			var causali = new TipiMovimentoOneriMgr(db).GetOneriDaTipoMovimento(idComune, tipoMovimento, CodiceComportamentoOneriEnum.SpostaImporto1SuImporto2);

			var istanzeOneriMgr = new IstanzeOneriMgr(db);

			string etichetta = new LayoutTestiBaseMgr(db).GetValoreTesto("IMPORTOCAUSALE", istanza.IDCOMUNE, istanza.SOFTWARE);
			string fmtMsg = "E' stato copiato l'importo dell'onere dalla causale " + etichetta + " alla causale \"{0}\"";

			foreach (var c in causali)
			{
				var filtroIstanzeOneri = new IstanzeOneri
				{
					IDCOMUNE = idComune,
					CODICEISTANZA = cls.CODICEISTANZA,
					FKIDTIPOCAUSALE = c.CoId.Value.ToString(),
					FLENTRATAUSCITA = "1",
					OthersWhereClause = new ArrayList { "DATAPAGAMENTO IS NULL" }
				};

				var listaOneri = istanzeOneriMgr.GetList(filtroIstanzeOneri);

				var somma = (from o in listaOneri
							 where (o.PREZZOISTRUTTORIA.GetValueOrDefault(0) == 0 && o.PREZZO.GetValueOrDefault(0) > 0)
							 select o).Sum(o => o.PREZZO.GetValueOrDefault(0));

				if (somma == 0)
					continue;

				listaOneri.ForEach(o =>
				{
					o.PREZZOISTRUTTORIA = somma;
					istanzeOneriMgr.Update(o);
					esito.Informazioni.Add(String.Format(fmtMsg, c.CoDescrizione));
				});
			}


			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}
				// TESTME
				string sql = @"SELECT " + db.Specifics.SumFunction("PREZZO") + @" AS TOTALE 
								FROM 
									ISTANZEONERI 
								WHERE 
									IDCOMUNE = {0} AND 
									CODICEISTANZA = {1} AND 
									FKIDTIPOCAUSALE = {2} AND 
									FLENTRATAUSCITA = 1 AND 
									DATAPAGAMENTO IS NULL AND " +
									db.Specifics.NvlFunction("PREZZOISTRUTTORIA", 0) + " = 0 AND " +
									db.Specifics.NvlFunction("PREZZO", 0) + " > 0";

				sql = PreparaQueryParametrica(sql, "idComune", "codiceIstanza", "codiceCausale");

				foreach (var c in causali)
				{
					using (IDbCommand cmd = db.CreateCommand(sql))
					{
						cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
						cmd.Parameters.Add(db.CreateParameter("codiceIstanza", codiceIstanza));
						cmd.Parameters.Add(db.CreateParameter("codiceCausale", c.CoId.Value));

						object totale = cmd.ExecuteScalar();

						if (totale == null || totale == DBNull.Value || Convert.ToInt32(totale) == 0)
							continue;
					}


				}

			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}




		}

		/// <summary>
		/// Imposta la data di pagamento di tutti gli oneri non pagati. 
		/// Se c’è una join TIPIMOVIMENTOONERI e TIPICAUSALIONERI con 
		/// TIPIMOVIMENTOONERI.CODICECOMPORTAMENTO= 2 imposta tutti gli oneri letti dalla query 
		/// come pagati alla data del movimento
		/// </summary>
		/// <param name="cls">Movimento inserito</param>
		/// <param name="esito">Struttura che contiene i messaggi per il movimento inserito</param>
		private void RichiediPagamentoOneri(Movimenti cls, EsitoInserimentoMovimento esito)
		{
			var idComune = cls.IDCOMUNE;
			var codiceIstanza = Convert.ToInt32(cls.CODICEISTANZA);
			var tipoMovimento = cls.TIPOMOVIMENTO;

			var causali = new TipiMovimentoOneriMgr(db).GetOneriDaTipoMovimento(idComune, tipoMovimento, CodiceComportamentoOneriEnum.RichiedePagamento);

			var istanzeOneriMgr = new IstanzeOneriMgr(db);

			foreach (var c in causali)
			{
				var filtroIstanzeOneri = new IstanzeOneri
				{
					IDCOMUNE = cls.IDCOMUNE,
					CODICEISTANZA = cls.CODICEISTANZA,
					FKIDTIPOCAUSALE = c.CoId.Value.ToString(),
					OthersWhereClause = new ArrayList { "DATAPAGAMENTO IS NULL" }
				};

				var oneri = istanzeOneriMgr.GetList(filtroIstanzeOneri);

				foreach (var onere in oneri)
				{
					onere.DATAPAGAMENTO = cls.DATA;

					istanzeOneriMgr.Update(onere);

					esito.Informazioni.Add(String.Format("E' stata impostata la data pagamento dell'onere \"{0}\"", c.CoDescrizione));
				}
			}
		}
		/*
		/// <summary>
		/// Verifica se vanno inseriti oneri (se c’è una join TIPIMOVIMENTOONERI e TIPICAUSALIONERI
		/// con TIPIMOVIMENTOONERI.CODICECOMPORTAMENTO= 3). 
		/// Se esistono una o più righe questi vengono aggiunti all’istanza
		/// </summary>
		/// <param name="cls">Movimento inserito</param>
		/// <param name="esito">Struttura che contiene i messaggi per il movimento inserito</param>
		private void InserisciOneri(Movimenti cls, EsitoInserimentoMovimento esito, string idComuneAlias)
		{
			var idComune = cls.IDCOMUNE;
			var codiceIstanza = Convert.ToInt32(cls.CODICEISTANZA);
			var tipoMovimento = cls.TIPOMOVIMENTO;

			var causali = new TipiMovimentoOneriMgr(db).GetOneriDaTipoMovimento(idComune, tipoMovimento, CodiceComportamentoOneriEnum.InserisceOnere);

			var istanzeOneriMgr = new IstanzeOneriMgr(db);

			foreach (var c in causali)
			{
				// Se esiste già un onere con la stessa causale passo al successivo
				string where = String.Format(@"WHERE IDCOMUNE = '{0}' AND 
														CODICEISTANZA = {1} AND 
														FKIDTIPOCAUSALE = {2}",
													idComune,
													codiceIstanza,
													c.CoId.Value);

				int count = recordCount("ISTANZEONERI", "ID", where);

				if (count > 0)
					continue;

				// calcolo in nuovo nr documento (se il sistema di pagamenti regulus è attivo)
				var nrDocumento = istanzeOneriMgr.CalcolaNumeroDocumento(idComune, idComuneAlias, codiceIstanza, c.CoId.Value);

				// inserisco il nuovo onere
				var nuovoOnere = new IstanzeOneri
				{
					CODICEINVENTARIO = cls.CODICEINVENTARIO,
					CODICEISTANZA = cls.CODICEISTANZA,
					PREZZO = 0.0d,
					FLENTRATAUSCITA = "1",
					DATA = cls.DATA,
					CODICEUTENTE = "0",
					FLRIBASSO = "0",
					PERCRIBASSO = "100",
					IDCOMUNE = cls.IDCOMUNE,
					PREZZOISTRUTTORIA = 0.0d,
					FKIDTIPOCAUSALE = c.CoId.Value.ToString(),
					NUMERORATA = "1",
					NR_DOCUMENTO = nrDocumento
				};

				istanzeOneriMgr.Insert(nuovoOnere);

				esito.Informazioni.Add(String.Format("E'stato inserito l'onere \"{0}\"", c.CoDescrizione));
			}
		}
		*/
		/// <summary>
		/// Effettua una verifica dei prerequisiti del movimento popolando la struttura dei messaggi, avvertimenti ed errori
		/// </summary>
		/// <param name="cls">movimento che si sta per effettuare</param>
		private EsitoVerificaMovimento VerificaPrerequisiti(Movimenti cls)
		{
			EsitoVerificaMovimento rVal = new EsitoVerificaMovimento();

			string idComune = cls.IDCOMUNE;
			int codiceIstanza = Convert.ToInt32(cls.CODICEISTANZA);

			/* Se la data del movimento è antecedente alla data dell'istanza aggiunge uno warning */
			var istanzeMgr = new IstanzeMgr(db);
			var istanza = istanzeMgr.GetById(idComune, codiceIstanza);

			if (istanza.DATA.Value > cls.DATA.Value)
			{
				string msg = String.Format("Il movimento ha data antecedente alla data di presentazione dell'istanza ( data movimento: {0:dd/MM/yyyy}, data istanza: {0:dd/MM/yyyy})",
							istanza.DATA,
							cls.DATA);
				rVal.Avvertimenti.Add(msg);
			}

			/* Verifica se il movimento che sta effettuando è quello di chiusura dell’istanza, 
			 * in questo caso viene aggiunto uno warning se la data del movimento è antecedente 
			 * alla data dell’ultimo movimento effettuato
			 */
			var tipiProcedureMgr = new TipiProcedureMgr(db);
			var tipoProcedura = tipiProcedureMgr.GetByCodiceIstanza(idComune, codiceIstanza);

			if (tipoProcedura.Idchiusuraistanza == cls.TIPOMOVIMENTO)
			{
				var dataUltimoMovimento = GetDataUltimoMovimento(idComune, codiceIstanza);

				if (dataUltimoMovimento.HasValue && dataUltimoMovimento.Value > cls.DATA.Value)
				{
					string msg = String.Format("Il movimento di chiusura ha una data antecedente a quella dell'ultimo " +
												"movimento effettuato ( data ultimo movimento: {0:dd/MM/yyyy}, data movimento di chiusura: {0:dd/MM/yyyy})",
												dataUltimoMovimento.Value,
												cls.DATA);
					rVal.Avvertimenti.Add(msg);
				}
			}

			/* Nel caso in cui sia stato specificato il Nr. giorni Richiesta integr. documentale 
			 * (TIPIPROCEDURE.nautoggrichiestadoc)e siamo in inserimento va comunicato uno warning
			 */
			if (tipoProcedura.Idautorichiestadoc == cls.TIPOMOVIMENTO)
			{
                if (tipoProcedura.Nautoggrichiestadoc.HasValue && tipoProcedura.Nautoggrichiestadoc.Value > 0)
				{
					var numeroGiorni = Convert.ToInt32(tipoProcedura.Nautoggrichiestadoc);

					var delta = istanza.DATA - cls.DATA;

					if (Math.Abs(delta.Value.Days) > numeroGiorni)
					{
						string errMsg = "Non sono stati rispettati i termini entro i quali si può effettuare la richiesta di integrazione documentale. ( data movimento: {0:dd/MM/yyyy}, scadenza dei termini: {1:dd/MM/yyyy} )";
						rVal.Avvertimenti.Add(String.Format(errMsg, cls.DATA, istanza.DATA.Value.AddDays(numeroGiorni)));
					}
				}
			}

			/* TODO: Si verifica la data di scadenza dalla tabella BATCH_SCADENZARIO (è lo scadenzario già elaborato) 
			 * codice istanza, tipo movimento, amministrazione, codice inventario.
			 * - Verifica se il movimento è stato registrato oltre i termini previsti 
			 *   rispetto ai tempi di attesa specificati
			 * - Verifica se il movimento è un contromovimento. Se è un contro movimento
			 *		- Ricava la data del movimento che ha scaturito il contro movimento ricercandolo 
			 *		  tra i movimenti fatti dall’istanza utilizzando: codice istanza, tipo movimento, 
			 *		  amministrazione, codice inventario.
			 *		- Legge la configurazione dei tempi di attesa. Se presente
			 *			- Verifica se la differenza va calcolata dalla data dell’istanza o dalla data del 
			 *			  movimento padre
			 *			- Verifica se la differenza è maggiore o minore dei tempi di attesa 
			 *			  (verificando eventuali sospensioni o interruzioni dell’istanza)
			 *			- Se il movimento è stato fatto fuori dai termini mostra uno warning all’utente
			 */

			return rVal;
		}

		/// <summary>
		/// Ottiene la data dell'ultimo movimento dell'istanza passata
		/// </summary>
		/// <param name="idComune">filtro idcomune</param>
		/// <param name="codiceIstanza">codice istanza</param>
		/// <returns></returns>
		private DateTime? GetDataUltimoMovimento(string idComune, int codiceIstanza)
		{

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = @"SELECT 
									Max(data) AS data 
								FROM 
									movimenti 
								WHERE 
									movimenti.idcomune={0} AND 
									codiceistanza = {1}";

				sql = PreparaQueryParametrica(sql, "idcomune", "codiceIstanza");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("codiceIstanza", codiceIstanza));

					object oDate = cmd.ExecuteScalar();

					if (oDate == null || oDate == DBNull.Value)
						return null;

					return Convert.ToDateTime(oDate);
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}

		}

		private Movimenti DataIntegrations(Movimenti cls)
		{
			Movimenti rVal = (Movimenti)cls.Clone();

			/*if (string.IsNullOrEmpty(rVal.CODICEINVENTARIO))
				rVal.CODICEINVENTARIO = "0";*/

			if (string.IsNullOrEmpty(rVal.MOVIMENTO))
			{
				if (String.IsNullOrEmpty(rVal.TIPOMOVIMENTO))
					throw new RequiredFieldException("Il campo TIPOMOVIMENTO è obbligatorio");

				var tipiMovMgr = new TipiMovimentoMgr(this.db);
				var tipoMov = tipiMovMgr.GetById(rVal.TIPOMOVIMENTO, rVal.IDCOMUNE);
				rVal.MOVIMENTO = tipoMov == null ? String.Empty : tipoMov.Movimento;
			}

			if (!rVal.FLAG_DA_LEGGERE.HasValue)
				rVal.FLAG_DA_LEGGERE = 0;

			/* Se il codice responsabile non è stato impostato (campo codiceresponsabile è vuoto o == 0)
			 * Lo imposto in base al codice responsabile dell'istanza */
			if (String.IsNullOrEmpty(rVal.CODICERESPONSABILE) || rVal.CODICERESPONSABILE == "0")
			{
				IstanzeMgr istanzeMgr = new IstanzeMgr(this.db);
                Istanze istanza = istanzeMgr.GetById(rVal.IDCOMUNE, Convert.ToInt32(rVal.CODICEISTANZA));

				rVal.CODICERESPONSABILE = String.IsNullOrEmpty(istanza.CODICERESPONSABILEPROC) ? istanza.CODICERESPONSABILE : istanza.CODICERESPONSABILEPROC;
			}

			///* Se il codice amministrazione non è impostato lo imposto in base alla logica specificata in 
			// * RicavaCodiceAmministrazione */
			//if (string.IsNullOrEmpty(rVal.CODICEAMMINISTRAZIONE))
			//{
			//    var idComune = rVal.IDCOMUNE;
			//    var codResponsabile = Convert.ToInt32(rVal.CODICERESPONSABILE);
			//    var codInventario = Convert.ToInt32(rVal.CODICEINVENTARIO);

			//    rVal.CODICEAMMINISTRAZIONE = RicavaCodiceAmministrazione(idComune, codResponsabile, codInventario).ToString();
			//}

			///* Se il flag pubblica non è stato impostato provo ad impostarlo in base al tipo movimento o 
			// * in base alla configurazione del software a cui appartiene l'istanza */
			//if (string.IsNullOrEmpty(rVal.PUBBLICA))
			//{
			//    var tipiMovMgr = new TipiMovimentoMgr(db);
			//    var tipoMov = tipiMovMgr.GetById(rVal.TIPOMOVIMENTO, rVal.IDCOMUNE);
			//    rVal.PUBBLICA = tipoMov.FlagPubblicamovimento.GetValueOrDefault(int.MinValue) == int.MinValue ? "" : tipoMov.FlagPubblicamovimento.ToString();

			//    if (string.IsNullOrEmpty(rVal.PUBBLICA))
			//    {
			//        //prendo il software da istanze
			//        var istanzaMgr = new IstanzeMgr(this.db);
			//        var istanza = istanzaMgr.GetById(rVal.IDCOMUNE, Convert.ToInt32(rVal.CODICEISTANZA));

			//        var configMgr = new ConfigurazioneMgr(this.db);
			//        var config = configMgr.GetById(rVal.IDCOMUNE, istanza.SOFTWARE);

			//        rVal.PUBBLICA = config.PUBBLICAISTANZE;
			//    }

			//}

			if (!rVal.DATAINSERIMENTO.HasValue)
				rVal.DATAINSERIMENTO = DateTime.Now;

			if (string.IsNullOrEmpty(rVal.ESITO))
				rVal.ESITO = "1";

			return rVal;
		}

		/// <summary>
		/// Ricava il codice amministrazione per il movimento
		/// </summary>
		/// <param name="idComune">Filtro idCOmune</param>
		/// <param name="codiceResponsabile">codice responsabile</param>
		/// <param name="codiceInventario">codice inventario del movimento</param>
		/// <returns>codice amministrazione per il movimento</returns>
		private int RicavaCodiceAmministrazione(string idComune, int codiceResponsabile, int codiceInventario)
		{
			/* Se è stato impostato il codiceinventario allora il codice amministrazione viene letto 
			 * dalla tabella InventarioProcedimenti */
			if (codiceInventario != 0)
			{
				var inventarioProcMgr = new InventarioProcedimentiMgr(this.db);
				var inventarioProc = inventarioProcMgr.GetById(idComune, codiceInventario);

				return inventarioProc.Amministrazione.Value;
			}

			/* Legge la lista delle amministrazioni collegate al responsabile
			 * Se ne viene trovata solo una allora quella viene impostata come 
			 * amministrazione del movimento, altrimenti legge dalla configurazione
			 * il valore della colonna CODAMMSPORTELLOUNICO */
			var respMgr = new ResponsabiliMgr(db);
			var listaAmministrazioniResponsabile = respMgr.GetListaAmministrazioniResponsabile(idComune, codiceResponsabile);

			if (listaAmministrazioniResponsabile.Count == 1)
				return Convert.ToInt32(listaAmministrazioniResponsabile[0].CODICEAMMINISTRAZIONE);

			var cfgMgr = new ConfigurazioneMgr(this.db);
			var configurazione = cfgMgr.GetById(idComune, "TT");
			return Convert.ToInt32(configurazione.CODAMMSPORTELLOUNICO);
		}

        //private void Validate(Movimenti cls, AmbitoValidazione ambitoValidazione)
        //{
        //    RequiredFieldValidate(cls, ambitoValidazione);

        //    // Verifico che il responsabile che sta effettuando il movimento abbia i permessi per farlo
        //    if (!VerificaPermessi(cls))
        //    {
        //        string errMsg = "Il responsabile {0} non dispone di permessi sufficienti per effettuare l'operazione";
        //        throw new InvalidOperationException(String.Format(errMsg, cls.CODICERESPONSABILE));
        //    }

        //    if (cls.ESITO != "0" && cls.ESITO != "1" && cls.ESITO != "-1")
        //        throw (new TypeMismatchException("Impossibile inserire" + cls.ESITO + " in MOVIMENTI.ESITO"));

        //    if (cls.PUBBLICA != "0" && cls.PUBBLICA != "1" && cls.PUBBLICA != "-1")
        //        throw (new TypeMismatchException("Impossibile inserire" + cls.PUBBLICA + " in MOVIMENTI.PUBBLICA"));

        //    if (cls.FLAG_DA_LEGGERE.GetValueOrDefault(0) != 0 && cls.FLAG_DA_LEGGERE.GetValueOrDefault(0) != 1)
        //        throw (new TypeMismatchException("Impossibile inserire" + cls.FLAG_DA_LEGGERE.Value.ToString() + " in MOVIMENTI.FLAG_DA_LEGGERE"));

        //    ForeignValidate(cls);
        //}

        ///// <summary>
        ///// Verifica se il responsabile che sta inserendo o aggiornando il movimento ha i permessi per farlo
        ///// </summary>
        ///// <param name="cls"></param>
        //private bool VerificaPermessi(Movimenti cls)
        //{
        //    var istanzeMgr = new IstanzeMgr(db);
        //    var permessi = istanzeMgr.PermessiIstanza(cls.IDCOMUNE, Convert.ToInt32(cls.CODICERESPONSABILE), Convert.ToInt32(cls.CODICEISTANZA));

        //    if (!permessi.AccessoConsentito)
        //        return false;

        //    if (permessi.SolaLettura)
        //        return false;

        //    if (!permessi.GestMovimenti)
        //        return false;

        //    if (permessi.DisabilitaGestMovimentiAmminNonInterna)
        //    {
        //        var amminMgr = new AmministrazioniMgr(db);
        //        var ammin = amminMgr.GetById(cls.IDCOMUNE, Convert.ToInt32(cls.CODICEAMMINISTRAZIONE), "" , "");

        //        if (ammin.FLAG_AMMINISTRAZIONEINTERNA == "1")
        //            return false;
        //    }

        //    return true;
        //}

		/// <summary>
		/// Verifica la consistenza dei dati verso le foreign keys
		/// </summary>
		/// <param name="p_class"></param>
		private void ForeignValidate(Movimenti p_class)
		{
			#region MOVIMENTI.CODICEISTANZA
			if (this.recordCount("ISTANZE", "CODICEISTANZA", "WHERE CODICEISTANZA = " + p_class.CODICEISTANZA + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0)
			{
				throw (new RecordNotfoundException("MOVIMENTI.CODICEISTANZA non trovato nella tabella ISTANZE"));
			}
			#endregion

			#region MOVIMENTI.TIPOMOVIMENTO
			if (this.recordCount("TIPIMOVIMENTO", "TIPOMOVIMENTO", "WHERE TIPOMOVIMENTO = '" + p_class.TIPOMOVIMENTO + "' AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0)
			{
				throw (new RecordNotfoundException("MOVIMENTI.TIPOMOVIMENTO non trovato nella tabella TIPIMOVIMENTO"));
			}
			#endregion

			#region MOVIMENTI.CODICEINVENTARIO
			if (p_class.CODICEINVENTARIO != "0" && !String.IsNullOrEmpty(p_class.CODICEINVENTARIO))
			{
				if (this.recordCount("INVENTARIOPROCEDIMENTI", "CODICEINVENTARIO", "WHERE CODICEINVENTARIO = " + p_class.CODICEINVENTARIO + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0)
				{
					throw (new RecordNotfoundException("MOVIMENTI.CODICEINVENTARIO non trovato nella tabella INVENTARIOPROCEDIMENTI"));
				}
			}
			#endregion

			//#region MOVIMENTI.CODICEAMMINISTRAZIONE
			//if (this.recordCount("AMMINISTRAZIONI", "CODICEAMMINISTRAZIONE", "WHERE CODICEAMMINISTRAZIONE = " + p_class.CODICEAMMINISTRAZIONE + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0)
			//{
			//    throw (new RecordNotfoundException("MOVIMENTI.CODICEAMMINISTRAZIONE non trovato nella tabella AMMINISTRAZIONI"));
			//}

			//#endregion

			#region MOVIMENTI.CODAMMRICHIEDENTE
			if (!StringChecker.IsStringEmpty(p_class.CODAMMRICHIEDENTE))
			{
				if (this.recordCount("AMMINISTRAZIONI", "CODICEAMMINISTRAZIONE", "WHERE CODICEAMMINISTRAZIONE = " + p_class.CODAMMRICHIEDENTE + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0)
				{
					throw (new RecordNotfoundException("MOVIMENTI.CODAMMRICHIEDENTE non trovato nella tabella AMMINISTRAZIONI"));
				}
			}
			#endregion

			#region MOVIMENTI.CODICEUFFICIO
			if (!StringChecker.IsStringEmpty(p_class.CODICEUFFICIO))
			{
				if (this.recordCount("AMMINISTRAZIONIREFERENTI", "ID", "WHERE ID = " + p_class.CODICEUFFICIO + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0)
				{
					throw (new RecordNotfoundException("MOVIMENTI.CODICEUFFICIO non trovato nella tabella AMMINISTRAZIONIREFERENTI"));
				}
			}
			#endregion

			#region MOVIMENTI.CODICERESPONSABILE
			if (this.recordCount("RESPONSABILI", "CODICERESPONSABILE", "WHERE CODICERESPONSABILE = " + p_class.CODICERESPONSABILE + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0)
			{
				throw (new RecordNotfoundException("MOVIMENTI.CODICERESPONSABILE non trovato nella tabella RESPONSABILI"));
			}
			#endregion
		}

		private Movimenti ChildDataIntegrations(Movimenti cls)
		{
			for (int i = 0; i < cls.MovimentiAllegati.Count; i++)
			{
				var allegatoMovimento = cls.MovimentiAllegati[i];

				if (string.IsNullOrEmpty(allegatoMovimento.IDCOMUNE))
					allegatoMovimento.IDCOMUNE = cls.IDCOMUNE;
				else if (allegatoMovimento.IDCOMUNE != cls.IDCOMUNE)
					throw (new IncongruentDataException("MOVIMENTIALLEGATI.IDCOMUNE è diverso da MOVIMENTI.IDCOMUNE"));

				if (string.IsNullOrEmpty(allegatoMovimento.CODICEMOVIMENTO))
					allegatoMovimento.CODICEMOVIMENTO = cls.CODICEMOVIMENTO;
				else if (allegatoMovimento.CODICEMOVIMENTO != cls.CODICEMOVIMENTO)
					throw (new IncongruentDataException("MOVIMENTIALLEGATI.CODICEMOVIMENTO è diverso da MOVIMENTI.CODICEMOVIMENTO"));
			}

			foreach (MovimentiDyn2ModelliT dyn2Modello in cls.MovimentiDyn2ModelliT)
			{
				if (string.IsNullOrEmpty(dyn2Modello.Idcomune))
					dyn2Modello.Idcomune = cls.IDCOMUNE;
				else if (dyn2Modello.Idcomune != cls.IDCOMUNE)
					throw (new IncongruentDataException("MOVIMENTIDYN2MODELLIT.IDCOMUNE è diverso da MOVIMENTI.IDCOMUNE"));

				if (dyn2Modello.Codiceistanza.GetValueOrDefault(int.MinValue) == int.MinValue)
					dyn2Modello.Codiceistanza = Convert.ToInt32(cls.CODICEISTANZA);
				else if (dyn2Modello.Codiceistanza != Convert.ToInt32(cls.CODICEISTANZA))
					throw (new IncongruentDataException("MOVIMENTIDYN2MODELLIT.CODICEISTANZA è diverso da MOVIMENTI.CODICEISTANZA"));

				if (dyn2Modello.Codicemovimento.GetValueOrDefault(int.MinValue) == int.MinValue)
					dyn2Modello.Codicemovimento = Convert.ToInt32(cls.CODICEMOVIMENTO);
				else if (dyn2Modello.Codicemovimento != Convert.ToInt32(cls.CODICEMOVIMENTO))
					throw (new IncongruentDataException("MOVIMENTIDYN2MODELLIT.CODICEMOVIMENTO è diverso da MOVIMENTI.CODICEMOVIMENTO"));
			}

			if (cls.Autorizzazione != null)
			{
				if (String.IsNullOrEmpty(cls.Autorizzazione.IDCOMUNE))
					cls.Autorizzazione.IDCOMUNE = cls.IDCOMUNE;
				else if (cls.Autorizzazione.IDCOMUNE != cls.IDCOMUNE)
					throw (new IncongruentDataException("MOVIMENTI.AUTORIZZAZIONE.IDCOMUNE è diverso da MOVIMENTI.IDCOMUNE"));

				if (String.IsNullOrEmpty(cls.Autorizzazione.CODICEMOVIMENTO))
					cls.Autorizzazione.CODICEMOVIMENTO = cls.CODICEMOVIMENTO;
				else if (cls.Autorizzazione.CODICEMOVIMENTO != cls.CODICEMOVIMENTO)
					throw (new IncongruentDataException("MOVIMENTI.AUTORIZZAZIONE.CODICEMOVIMENTO è diverso da MOVIMENTI.CODICEMOVIMENTO"));

				if (String.IsNullOrEmpty(cls.Autorizzazione.FKIDISTANZA))
					cls.Autorizzazione.FKIDISTANZA = cls.CODICEISTANZA;
				else if (cls.Autorizzazione.FKIDISTANZA != cls.CODICEISTANZA)
					throw (new IncongruentDataException("MOVIMENTI.AUTORIZZAZIONE.FKIDISTANZA è diverso da MOVIMENTI.CODICEISTANZA"));

				if (String.IsNullOrEmpty(cls.Autorizzazione.FKIDREGISTRO))
				{
					TipiMovimentoMgr tipiMovimentoMgr = new TipiMovimentoMgr(this.db);
					TipiMovimento tipoMovimento = new TipiMovimento();

					tipoMovimento.Idcomune = cls.IDCOMUNE;
					tipoMovimento.Tipomovimento = cls.TIPOMOVIMENTO;
					tipoMovimento.FlagRegistro = 1;

					List<TipiMovimento> al = tipiMovimentoMgr.GetList(tipoMovimento);

					if (al.Count == 1)
						cls.Autorizzazione.FKIDREGISTRO = al[0].Fkidregistro.GetValueOrDefault(int.MinValue) == int.MinValue ? String.Empty : al[0].Fkidregistro.ToString();
				}
			}

			return cls;
		}
		/*
		private void ChildInsert(Movimenti cls, EsitoInserimentoMovimento esito, string idComuneAlias)
		{
			var idComune = cls.IDCOMUNE;
			var codiceIstanza = Convert.ToInt32(cls.CODICEISTANZA);
			var tipoMovimento = cls.TIPOMOVIMENTO;
			int? codiceInventario = String.IsNullOrEmpty(cls.CODICEINVENTARIO)? (int?)null : Convert.ToInt32(cls.CODICEINVENTARIO);
			int? codiceAmministrazione = String.IsNullOrEmpty(cls.CODICEAMMINISTRAZIONE)? (int?)null: Convert.ToInt32(cls.CODICEAMMINISTRAZIONE);

			InserisciMovimentiAllegati(cls);

			InserisciAutorizzazione(cls);

			// Verifica se il movimento è presente nella tabella TIPIMOVIMENTO_DIS. 
			// In questo caso il movimento in TIPIMOVIMENTO_DIS viene eliminato 
			// var tipiMovimentoDisMgr = new TipiMovimento_DisMgr(db);
			// tipiMovimentoDisMgr.RimuoviTipoMovimento(idComune, codiceIstanza, tipoMovimento, codiceInventario, codiceAmministrazione);
			

			// Verifico se il movimento aggiunge oneri all'istanza
            InserisciOneri(cls, esito, idComuneAlias);

			// Verifico se il movimento richiede il pagamento dell'onere
			RichiediPagamentoOneri(cls, esito);

			// Verifico se il movimento richiede lo spostamento dell'imoprto degli oneri
			SpostaImportoOneri(cls, esito);

			// Verifico se il movimento crea una cds
			CreaCDS(cls, esito);

			if (cls.MovimentiDyn2ModelliT.Count == 0)
			{
				// Collego eventuali schede dinamiche al movimento
				CollegaSchedeDinamiche(cls, esito);
			}
			else
			{
				var mgr = new MovimentiDyn2ModelliTMgr(db);

				foreach (MovimentiDyn2ModelliT mdm in cls.MovimentiDyn2ModelliT)
					mgr.Insert(mdm);
			}
		}
		*/
		private void InserisciAutorizzazione(Movimenti cls)
		{
			if (cls.Autorizzazione != null)
			{
				new AutorizzazioniMgr(this.db).Insert(cls.Autorizzazione);
			}
		}

		private void InserisciMovimentiAllegati(Movimenti cls)
		{
			var movAllegatiMgr = new MovimentiAllegatiMgr(this.db);

			for (int i = 0; i < cls.MovimentiAllegati.Count; i++)
			{
				movAllegatiMgr.Insert(cls.MovimentiAllegati[i]);
			}
		}

		#endregion
		
	}
}