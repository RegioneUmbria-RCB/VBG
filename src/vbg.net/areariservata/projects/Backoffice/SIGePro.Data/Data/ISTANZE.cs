using System;
using System.Data;
using Init.SIGePro.Attributes;
using Init.SIGePro.Collection;
using PersonalLib2.Sql.Attributes;
using System.Collections.Generic;
using Init.SIGePro.DatiDinamici.Interfaces;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
	[DataTable("ISTANZE")]
	[Serializable]
	public class Istanze : BaseDataClass, IClasseContestoModelloDinamico
	{
		public Istanze()
		{
			this.Aree = new List<IstanzeAree>();
			this.AnagrafeDocumenti = new List<AnagrafeDocumenti>();
			this.Autorizzazioni = new List<Autorizzazioni>();
			this.DocumentiIstanza = new List<DocumentiIstanza>();
			this.IstanzeDyn2ModelliT = new List<IstanzeDyn2ModelliT>();
			this.IstanzeDyn2Dati = new List<IstanzeDyn2Dati>();
			this.Attivita = new List<IstanzeAttivita>();
			this.Fidejussioni = new List<IstanzeFidejussioni>();
			this.Mappali = new List<IstanzeMappali>();
			this.Oneri = new List<IstanzeOneri>();
			this.EndoProcedimenti = new List<IstanzeProcedimenti>();
			this.Richiedenti = new List<IstanzeRichiedenti>();
			this.Stradario = new List<IstanzeStradario>();
			this.Ruoli = new List<IstanzeRuoli>();
			this.Movimenti = new List<Movimenti>();
			this.Permessi = new List<PermIstanze>();
			this.Orari = new List<OrariAperturaTestata>();
			this.Concessioni = new List<IstanzeConcessioni>();
			this.Affissioni = new List<IstanzeAffissioni>();
			this.AffissioniAssegnazioni = new List<IstanzeAffissioniAssegnazioni>();
			this.Allegati = new List<IstanzeAllegati>();
			this.Eventi = new List<IstanzeEventi>();
            this.IstanzeCollegate = new List<IstanzaCollegata>();

        }


		#region Key Fields

		/// <summary>
		/// Id univoco dell'istanza,fa chiave insieme ad IDCOMUNE
		/// </summary>
		[useSequence]
		[KeyField("CODICEISTANZA", Type=DbType.Decimal)]
		[XmlElement(Order=10)]
		public string CODICEISTANZA{get;set;}

		/// <summary>
		/// Id univoco dell'istanza,fa chiave insieme ad CODICEISTANZA
		/// </summary>
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		[XmlElement(Order = 20)]
		public string IDCOMUNE{get;set;}

		#endregion

		#region Data Fields
		/// <summary>
		/// Codice dell'operatore che ha inserito l'istanza (FK su RESPONSABILI.CODICERESPONSABILE insieme ad IDCOMUNE)
		/// </summary>
		[isRequired]
		[DataField("CODICERESPONSABILE", Type=DbType.Decimal)]
		[XmlElement(Order = 30)]
		public string CODICERESPONSABILE{get;set;}
				
		/// <summary>
		/// Richiedente della pratica (FK su ANAGRAFE.CODICEANAGRAFE insieme ad idcomune)
		/// </summary>
		[isRequired]
		[DataField("CODICERICHIEDENTE", Type=DbType.Decimal)]
		[XmlElement(Order = 40)]
		public string CODICERICHIEDENTE{get;set;}

		
		/// <summary>
		/// Tecnico esterno della pratica (FK su ANAGRAFE.CODICEANAGRAFE insieme ad idcomune)
		/// </summary>
		[DataField("CODICEPROFESSIONISTA", Type=DbType.Decimal)]
		[XmlElement(Order = 50)]
		public string CODICEPROFESSIONISTA{get;set;}

        DateTime? data = null;
		/// <summary>
		/// Data della pratica
		/// </summary>
		[isRequired]
		[DataField("DATA", Type=DbType.DateTime)]
		[XmlElement(Order = 60)]
		public DateTime? DATA
		{
			get { return data; }
            set 
            {
                data = VerificaDataLocale(value);
            }
		}

		/// <summary>
		/// Numero di protocollo della pratica
		/// </summary>
		[DataField("NUMEROPROTOCOLLO",Size=15, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 70)]
		public string NUMEROPROTOCOLLO{get;set;}

        DateTime? dataprotocollo = null;
		/// <summary>
		/// Data protocollo
		/// </summary>
		[DataField("DATAPROTOCOLLO", Type=DbType.DateTime)]
		[XmlElement(Order = 80)]
		public DateTime? DATAPROTOCOLLO
		{
			get { return dataprotocollo; }
            set { dataprotocollo = VerificaDataLocale(value); }
		}

		/// <summary>
		/// Se il software == "SU" è il codice dell'intervento (FK su INTERVENTI.CODICEINTERVENTO)
		/// </summary>
		[isRequired(SOFTWARE="SU")]
		[DataField("CODICEINTERVENTO", Type=DbType.Decimal)]
		[XmlElement(Order = 90)]
		public string CODICEINTERVENTO{get;set;}

		
		/// <summary>
		/// Oggetto della pratica
		/// </summary>
		[DataField("LAVORI",Size=1000, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 100)]
		public string LAVORI{get;set;}

		
		/// <summary>
		/// Procedura che identifica l'iter della pratica (FK su TIPIPROCEDURE.CODICEPROCEDURA)
		/// </summary>
		[isRequired]
		[DataField("CODICEPROCEDURA", Type=DbType.Decimal)]
		[XmlElement(Order = 110)]
		public string CODICEPROCEDURA{get;set;}



		/// <summary>
		/// Utilizzato solo nel software SU
		/// </summary>
		[DataField("CODICELOTTO", Type=DbType.Decimal)]
		[XmlElement(Order = 120)]
		public string CODICELOTTO{get;set;}

		
		/// <summary>
		/// Utilizzata per l'accesso da front-end da parte del cittadino
		/// </summary>
		[isRequired]
		[DataField("PASSWORD",Size=8, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 130)]
		public string PASSWORD{get;set;}

		/// <summary>
		/// 0 = attiva, 1 = non attiva o disabilitata
		/// </summary>
		[isRequired]
		[DataField("ATTIVA", Type=DbType.Decimal)]
		[XmlElement(Order = 140)]
		public string ATTIVA{get;set;}



		/// <summary>
		/// Se software==SU indica una variante al prg
		/// </summary>
		[DataField("VARIANTEPR", Type=DbType.Decimal)]
		[XmlElement(Order = 150)]
		public string VARIANTEPR{get;set;}

		
		/// <summary>
		/// Se software=SU indica il tipo di impianto legato alla pratica (FK su IMPIANTI.CODICEIMPIANTO insiame a IDCOMUNE)
		/// </summary>
		[DataField("CODICEIMPIANTO", Type=DbType.Decimal)]
		[XmlElement(Order = 160)]
		public string CODICEIMPIANTO{get;set;}



		/// <summary>
		/// Se software==SU indica Valutazione Impatto Ambientale
		/// </summary>
		[isRequired]
		[DataField("FLAGVIA", Type=DbType.Decimal)]
		[XmlElement(Order = 170)]
		public string FLAGVIA{get;set;}

		
		/// <summary>
		/// Stato dell'istanza (fk su STATIISTANZA insieme a IDCOMUNE)
		/// </summary>
		[isRequired]
		[DataField("CHIUSURA",Size=2, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 180)]
		public string CHIUSURA{get;set;}



		
		/// <summary>
		/// Note della pratica
		/// </summary>
		[DataField("LAVORIESTESA",Size=4000, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 190)]
		public string LAVORIESTESA{get;set;}



		

		
		/// <summary>
		/// Movimento con cui viene avviato l'iter della pratica (fk su TIPIMOVIMENTO.TIPOMOVIMENTO insieme ad IDCOMUNE)
		/// </summary>
		[isRequired]
		[DataField("TIPOMOVAVVIO",Size=6, Type=DbType.String)]
		[XmlElement(Order = 200)]
		public string TIPOMOVAVVIO{get;set;}


		
		/// <summary>
		/// Utilizzato per identificare l'armadio in cui è stata riposta la pratica (fk su TIPIARCHIVIOISTANZE.CODICEARCHIVIO insieme a IDCOMUNE)
		/// </summary>
		[DataField("TIPOARCHIVIO", Type=DbType.Decimal)]
		[XmlElement(Order = 210)]
		public string TIPOARCHIVIO{get;set;}

		
		/// <summary>
		/// Comune al quale è stata presentata la pratica in caso di comuni associati, se l'installazione non è di tipo associazione di comuni è == a IDCOMUNE (FK su COMUNI.CODICECOMUNE) 
		/// </summary>
		[isRequired]
		[DataField("CODICECOMUNE", Size=5, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 220)]
		public string CODICECOMUNE{get;set;}

		
		/// <summary>
		/// Modulo a cui appartiene la pratica (fk su SOFTWARE.CODICE)
		/// </summary>
		[isRequired]
		[DataField("SOFTWARE",Size=2, Type=DbType.String)]
		[XmlElement(Order = 230)]
		public string SOFTWARE{get;set;}



		
		/// <summary>
		/// Se software != SU è il codice dell'intervento della domanda (fk su ALBEROPROC.SC_ID insieme a IDCOMUNE)
		/// </summary>
		[DataField("CODICEINTERVENTOPROC", Type=DbType.Decimal)]
		[XmlElement(Order = 240)]
		public string CODICEINTERVENTOPROC{get;set;}

		/// <summary>
		/// Numero progressivo dell'istanza (univoco nel comune e nel software)
		/// </summary>
		[isRequired]
		[DataField("NUMEROISTANZA",Size=15, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 250)]
		public string NUMEROISTANZA{get;set;}




		
		/// <summary>
		/// Specifica il tipo di istanza (fk su TIPOLOGIAISTANZA.TI_ID insieme a IDCOMUNE)
		/// </summary>
		[DataField("FKIDTIPOLOGIAISTANZA", Type=DbType.Decimal)]
		[XmlElement(Order = 260)]
		public string FKIDTIPOLOGIAISTANZA{get;set;}

		/// <summary>
		/// Codice dell'anagrafica che contiene la ragione sociale legata al richiedente (fk su ANAGRAFE.CODICEANAGRAFE)
		/// </summary>
		[DataField("CODICETITOLARELEGALE", Type=DbType.Decimal)]
		[XmlElement(Order = 270)]
		public string CODICETITOLARELEGALE{get;set;}


		/// <summary>
		/// Superficie totale legata all'istanza. Se è in uso la gestione tramite "dettaglio informazioni" è la somma di
		/// ISTANZEATTIVITA.METRIQ legati ad un'attivita
		/// </summary>
		[DataField("METRIQUADRATI", Type=DbType.Decimal)]
		[XmlElement(Order = 280)]
		public Double? METRIQUADRATI{get;set;}

		
		/// <summary>
		/// E'il giorno di chiusura se software=CO,PE,SR,CP. In futuro verrà dismesso (fk su GIORNISETTIMANA.GS_ID)
		/// </summary>
		[DataField("GIORNOCHIUSURA",Size=2, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 290)]
		public string GIORNOCHIUSURA{get;set;}

		
		/// <summary>
		/// Permette di creare una catena di istanze legate ad un'attività (Fk su ISTANZE.CODICEISTANZA insieme a IDCOMUNE)
		/// </summary>
		[DataField("CODISTANZACOLLEGATA", Type=DbType.Decimal)]
		[XmlElement(Order = 300)]
		public string CODISTANZACOLLEGATA{get;set;}


		/// <summary>
		/// Azione (fk su AZIONI.AZ_AZIONE)
		/// </summary>
		[DataField("AZIONE",Size=1, Type=DbType.String)]
		[XmlElement(Order = 310)]
		public string AZIONE{get;set;}
		

		/// <summary>
		/// Utilizzato dal software Pubblicità (PU) (fk su PU_FORMATI.FO_ID con idcomune)
		/// </summary>
		[DataField("FKIDFORMATO", Type=DbType.Decimal)]
		[XmlElement(Order = 320)]
		public string FKIDFORMATO{get;set;}


		/// <summary>
		/// Posizione della pratica nell'archivio cartaceo (normalmente utilizzato insieme a TIPOARCHIVIO)
		/// </summary>
		[DataField("POSIZIONEARCHIVIO",Size=20, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 330)]
		public string POSIZIONEARCHIVIO{get;set;}

		/// <summary>
		/// Usato nel software PU. E'una delle misure dell'insegna
		/// </summary>
		[DataField("BASE", Type=DbType.Decimal)]
		[XmlElement(Order = 340)]
		public string BASE{get;set;}

		/// <summary>
		/// Usato nel software PU. E'una delle misure dell'insegna
		/// </summary>
		[DataField("ALTEZZA", Type=DbType.Decimal)]
		[XmlElement(Order = 350)]
		public string ALTEZZA{get;set;}

		/// <summary>
		/// Usato nel software PU. E'una delle misure dell'insegna
		/// </summary>
		[DataField("FACCE", Type=DbType.Decimal)]
		[XmlElement(Order = 360)]
		public string FACCE{get;set;}

		/// <summary>
		/// Id del protocollo generale, utilizzato nel caso in cui il protocollo venga generato dall'istanza SiGePro oppure se l'istanza è generata dal protocollo
		/// </summary>
		[DataField("FKIDPROTOCOLLO", Type=DbType.String)]
		[XmlElement(Order = 370)]
		public string FKIDPROTOCOLLO{get;set;}

		/// <summary>
		/// Tipo soggetto del richiedente. Compare nel dettaglio dell'istanza alla voce "In qualità di..." (fk su TIPISOGGETTO.CODICETIPOSOGGETTO insieme a IDCOMUNE )
		/// </summary>
		[DataField("FKCODICESOGGETTO", Type=DbType.Decimal)]
		[XmlElement(Order = 380)]
		public string FKCODICESOGGETTO{get;set;}

		/// <summary>
		/// Id dell'istruttore della pratica (Fk su RESPONSABILI.CODICERESPONSABILE insieme a IDCOMUNE)
		/// </summary>
		[DataField("CODICEISTRUTTORE", Type=DbType.Decimal)]
		[XmlElement(Order = 390)]
		public string CODICEISTRUTTORE{get;set;}


		/// <summary>
		/// Id del responsabile del procedimento (Fk su RESPONSABILI.CODICERESPONSABILE insieme a IDCOMUNE)
		/// </summary>
		[DataField("CODICERESPONSABILEPROC", Type=DbType.Decimal)]
		[XmlElement(Order = 400)]
		public string CODICERESPONSABILEPROC{get;set;}


		/// <summary>
		/// Attività alla quale è collegata l'istanza (fk su I_ATTIVITA.ID insieme a IDCOMUNE)
		/// </summary>
		[DataField("FK_IDI_ATTIVITA", Type=DbType.Decimal)]
		[XmlElement(Order = 410)]
        public int? FK_IDI_ATTIVITA{get;set;}

        DateTime? datavalidita = null;
		/// <summary>
		/// Viene impostata in base ai criteri di validità stabiliti dalla procedura
		/// </summary>
		[DataField("DATAVALIDITA", Type=DbType.DateTime)]
		[XmlElement(Order = 420)]
		public DateTime? DATAVALIDITA
		{
			get { return datavalidita; }
            set { datavalidita = VerificaDataLocale(value); }
		}

        
		[DataField("I_ATTIVITAORDINE", Type=DbType.Decimal)]
		[XmlElement(Order = 430)]
		public int? I_ATTIVITAORDINE{get;set;}


		/// <summary>
		/// Flag che viene popolato dal NLA quando l'istanza proviene da STC
		/// </summary>
        [DataField("CREATO_DA_STC", Type = DbType.Decimal)]
		[XmlElement(Order = 440)]
        public int? CREATO_DA_STC{get;set;}

		/// <summary>
		/// Descrizione estesa del tipo soggetto, viene popolato ad esempio nel caso in cui il tipo soggetto sia di tipo "Altro(specificare...)"
		/// </summary>
		[DataField("DESCRSOGGETTO", Type = DbType.String, Size=128,CaseSensitive=false)]
		[XmlElement(Order = 450)]
		public string DESCRSOGGETTO{get;set;}

		#endregion

		#region Arraylist per gli inserimenti nelle tabelle collegate e foreign keys
		/// <summary>
		/// Risoluzione FK, Comune in cui è stata presentata la pratica
		/// </summary>
		[ForeignKey("CODICECOMUNE", "CODICECOMUNE")]
		[XmlElement(Order = 460)]
		public Comuni ComuneIstanza { get; set; }

		/// <summary>
		/// Risoluzione FK, Richiedente della pratica
		/// </summary>
		[ForeignKey("IDCOMUNE, CODICERICHIEDENTE", "IDCOMUNE, CODICEANAGRAFE")]
		[XmlElement(Order = 470)]
		public Anagrafe Richiedente { get; set; }

		/// <summary>
		/// Risoluzione FK, Tipo soggetto
		/// </summary>
		[ForeignKey("IDCOMUNE,FKCODICESOGGETTO", "IDCOMUNE, CODICETIPOSOGGETTO")]
		[XmlElement(Order = 480)]
		public TipiSoggetto TipoSoggetto{get;set;}

		/// <summary>
		/// Risoluzione FK, Tecnico esterno della pratica
		/// </summary>
		[ForeignKey("IDCOMUNE, CODICEPROFESSIONISTA", "IDCOMUNE, CODICEANAGRAFE")]
		[XmlElement(Order = 490)]
		public Anagrafe Professionista{get;set;}


		/// <summary>
		/// Risoluzione FK, Azienda della pratica
		/// </summary>
		[ForeignKey("IDCOMUNE, CODICETITOLARELEGALE", "IDCOMUNE, CODICEANAGRAFE")]
		[XmlElement(Order = 500)]
		public Anagrafe AziendaRichiedente{get;set;}

		/// <summary>
		/// Risoluzione FK, Operatore che ha inserito la pratica
		/// </summary>
		[ForeignKey("IDCOMUNE, CODICERESPONSABILE", "IDCOMUNE, CODICERESPONSABILE")]
		[XmlElement(Order = 510)]
		public Responsabili Operatore{get;set;}

		/// <summary>
		/// Risoluzione FK, Istruttore della pratica
		/// </summary>
		[ForeignKey("IDCOMUNE, CODICEISTRUTTORE", "IDCOMUNE, CODICERESPONSABILE")]
		[XmlElement(Order = 520)]
		public Responsabili Istruttore{get;set;}

		/// <summary>
		/// Risoluzione FK, Responsabile del procedimento
		/// </summary>
		[ForeignKey("IDCOMUNE, CODICERESPONSABILEPROC", "IDCOMUNE, CODICERESPONSABILE")]
		[XmlElement(Order = 530)]
		public Responsabili ResponsabileProc{get;set;}

		/// <summary>
		/// Risoluzione FK, Aree
		/// </summary>
		[ForeignKey("IDCOMUNE,CODICEISTANZA", "IDCOMUNE,CODICEISTANZA")]
		[XmlArray(Order = 540)]
		public List<IstanzeAree> Aree{get;set;}

		/// <summary>
		/// Utilizzato internamente
		/// </summary>
		[XmlArray(Order = 550)]
        public List<AnagrafeDocumenti> AnagrafeDocumenti{get;set;}

		/// <summary>
		/// Risoluzione FK, Autorizzazioni
		/// </summary>
		[ForeignKey("IDCOMUNE,CODICEISTANZA", "IDCOMUNE,FKIDISTANZA")]
		[XmlArray(Order = 560)]
        public List<Autorizzazioni> Autorizzazioni{get;set;}

		/// <summary>
		/// Risoluzione FK, Documenti dell'istanza
		/// </summary>
		[ForeignKey("IDCOMUNE,CODICEISTANZA", "IDCOMUNE,CODICEISTANZA")]
		[XmlArray(Order = 570)]
		public List<DocumentiIstanza> DocumentiIstanza{get;set;}

		/// <summary>
		/// Risoluzione FK, Modelli dinamici dell'istanza
		/// </summary>
        [ForeignKey("IDCOMUNE,CODICEISTANZA", "Idcomune,Codiceistanza")]
		[XmlArray(Order = 580)]
        public List<IstanzeDyn2ModelliT> IstanzeDyn2ModelliT{get;set;}

		/// <summary>
		/// Risoluzione FK, Dati dinamici presenti nella domanda
		/// </summary>
        [ForeignKey("IDCOMUNE,CODICEISTANZA", "Idcomune,Codiceistanza")]
		[XmlArray(Order = 590)]
        public List<IstanzeDyn2Dati> IstanzeDyn2Dati{get;set;}

		/// <summary>
		/// Risoluzione FK, Attivita
		/// </summary>
		[ForeignKey("IDCOMUNE,CODICEISTANZA", "IDCOMUNE,CODICEISTANZA")]
		[XmlArray(Order = 600)]
		public List<IstanzeAttivita> Attivita{get;set;}

		/// <summary>
		/// Risoluzione FK, Fidejussioni
		/// </summary>
		[ForeignKey("IDCOMUNE,CODICEISTANZA", "IDCOMUNE,CODICEISTANZA")]
		[XmlArray(Order = 610)]
		public List<IstanzeFidejussioni> Fidejussioni{get;set;}

		/// <summary>
		/// Risoluzione FK, Mappali dell'istanza
		/// </summary>
		[ForeignKey("IDCOMUNE,CODICEISTANZA", "Idcomune,Fkcodiceistanza")]
		[XmlArray(Order = 620)]
		public List<IstanzeMappali> Mappali{get;set;}

		/// <summary>
		/// Risoluzione FK, Oneri dell'istanza
		/// </summary>
		[ForeignKey("IDCOMUNE,CODICEISTANZA", "IDCOMUNE,CODICEISTANZA")]
		[XmlArray(Order = 630)]
		public List<IstanzeOneri> Oneri{get;set;}

		/// <summary>
		/// Risoluzione FK, Endoprocedimenti
		/// </summary>
		[ForeignKey("IDCOMUNE,CODICEISTANZA", "IDCOMUNE,CODICEISTANZA")]
		[XmlArray(Order = 640)]
		public List<IstanzeProcedimenti> EndoProcedimenti{get;set;}

		/// <summary>
		/// Risoluzione FK, Altri soggetti del'listanza
		/// </summary>
		[ForeignKey("IDCOMUNE,CODICEISTANZA", "IDCOMUNE,CODICEISTANZA")]
		[XmlArray(Order = 650)]
		public List<IstanzeRichiedenti> Richiedenti{get;set;}

		/// <summary>
		/// Risoluzione FK, localizzazioni dell'istanza
		/// </summary>
		[ForeignKey("IDCOMUNE,CODICEISTANZA", "IDCOMUNE,CODICEISTANZA")]
		[XmlArray(Order = 660)]
		public List<IstanzeStradario> Stradario{get;set;}

		/// <summary>
		/// Restituisce l'indirizzo primario dell'istanza o null se non esiste uno stradario con il flag primario impostato
		/// </summary>
		[XmlElement(Order = 670)]
		public IstanzeStradario StradarioPrimario
		{
			get 
			{
				foreach (IstanzeStradario istStr in Stradario)
				{
					if (istStr.PRIMARIO == "1")
						return istStr;
				}

				return null;
			}
		}

		/// <summary>
		/// Restituisce tutti gli indirizzi che non hanno il flag primario impostato
		/// </summary>
		[XmlArray(Order = 680)]
        public List<IstanzeStradario> AltriIndirizzi
		{
			get
			{
				List<IstanzeStradario> rVal = new List<IstanzeStradario>();

				foreach (IstanzeStradario istStr in Stradario)
				{
					if (istStr.PRIMARIO != "1")
						rVal.Add(istStr);
				}

				return rVal;
			}
		}

		/// <summary>
		/// Risoluzione FK, Ruoli dell'istanza
		/// </summary>
		[ForeignKey("IDCOMUNE,CODICEISTANZA", "IDCOMUNE,CODICEISTANZA")]
		[XmlArray(Order = 690)]
		public List<IstanzeRuoli> Ruoli{get;set;}

		/// <summary>
		/// Risoluzione FK, Movimenti dell'istanza
		/// </summary>
		[ForeignKey("IDCOMUNE, CODICEISTANZA", "IDCOMUNE, CODICEISTANZA")]
		[XmlArray(Order = 700)]
		public List<Movimenti> Movimenti{get;set;}

		/// <summary>
		/// Utilizzato internamente
		/// </summary>
		[XmlArray(Order = 710)]
        public List<PermIstanze> Permessi{get;set;}

		/// <summary>
		/// Utilizzato internamente
		/// </summary>
		[XmlArray(Order = 720)]
        public List<OrariAperturaTestata> Orari{get;set;}

		/// <summary>
		/// Risoluzione FK, Concessioni dell'istanza
		/// </summary>
        [ForeignKey("IDCOMUNE, CODICEISTANZA", "IDCOMUNE, FKCODICEISTANZA")]
		[XmlArray(Order = 730)]
        public List<IstanzeConcessioni> Concessioni{get;set;}

		/// <summary>
		/// Risoluzione FK, Intervento dell'istanza
		/// </summary>
		[ForeignKey("IDCOMUNE,CODICEINTERVENTOPROC", "Idcomune, Sc_id")]
		[XmlElement(Order = 740)]
		public AlberoProc Intervento{get;set;}

		/// <summary>
		/// Risoluzione FK, Stato dell'istanza
		/// </summary>
		[ForeignKey("IDCOMUNE,SOFTWARE,CHIUSURA", "Idcomune,Software,Codicestato")]
		[XmlElement(Order = 750)]
		public StatiIstanza Stato{get;set;}

		/// <summary>
		/// Risoluzione FK, Affissioni
		/// </summary>
        [ForeignKey("IDCOMUNE, CODICEISTANZA", "IDCOMUNE, CODICEISTANZA")]
		[XmlArray(Order = 760)]
        public List<IstanzeAffissioni> Affissioni{get;set;}

		/// <summary>
		/// Risoluzione FK, Assegnazioni affissioni
		/// </summary>
        [ForeignKey("IDCOMUNE, CODICEISTANZA", "IDCOMUNE, CODICEISTANZA")]
		[XmlArray(Order = 770)]
        public List<IstanzeAffissioniAssegnazioni> AffissioniAssegnazioni{get;set;}

		/// <summary>
		/// Risoluzione FK, Allegati dell'istanza (documenti allegati agli endoprocedimenti)
		/// </summary>
        [ForeignKey("IDCOMUNE, CODICEISTANZA", "IDCOMUNE, CODICEISTANZA")]
		[XmlArray(Order = 780)]
        public List<IstanzeAllegati> Allegati{get;set;}

		/// <summary>
		/// Risoluzione FK, Eventi dell'istanza
		/// </summary>
        [ForeignKey("IDCOMUNE,CODICEISTANZA", "Idcomune,Codiceistanza")]
		[XmlArray(Order = 790)]
        public List<IstanzeEventi> Eventi {get;set;}
		#endregion

		/// <summary>
		/// Utilizzato internamente. Aggiunto per leggere i dati della configurazione nell'area riservata
		/// </summary>
		[XmlElement(Order = 800)]
		public Configurazione ConfigurazioneComune{get;set;}
		

		#region campi dismessi
		/// <summary>
		/// Obsoleto (ora viene utilizzata la tabella IstanzeAree )
		/// </summary>
		[Obsolete("Utilizzare la tabella IstanzeAree", true)]
		[DataField("CODICEAREA", Type = DbType.Decimal)]
		[XmlIgnore]
		public string CODICEAREA{get;set;}

		
		/// <summary>
		/// Obsoleto, vedi ISTANZEATTIVITA
		/// </summary>
		[Obsolete("Vedi ISTANZEATTIVITA", true)]
		[DataField("CODICESETTORE", Size = 5, Type = DbType.String, CaseSensitive = false)]
		[XmlIgnore]
		public string CODICESETTORE{get;set;}

		/// <summary>
		/// Dismesso, vedi ISTANZEATTIVITA
		/// </summary>
		[Obsolete("Vedi ISTANZEATTIVITA", true)]
		[DataField("CODICEATTIVITA", Size = 5, Type = DbType.String, CaseSensitive = false)]
[XmlIgnore]
		public string CODICEATTIVITA{get;set;}


		[Obsolete("Utilizzare i nuovi campi dinamici", true)]
		[XmlIgnore]
		public DynDatiCollection DynDati{get;set;}

		/// <summary>
		/// Dismesso, vd AUTORIZZAZIONI
		/// </summary>
		[Obsolete("Campo dismesso, vd AUTORIZZAZIONI", true)]
		[DataField("PROGREGAUT", Type = DbType.Decimal)]
		[XmlIgnore]
		public string PROGREGAUT{get;set;}


		/// <summary>
		/// Dismesso
		/// </summary>
		[Obsolete("Campo dismesso")]
		[DataField("CODICEARCHIVIO", Size = 15, Type = DbType.String, CaseSensitive = false)]
		[XmlIgnore]
		public string CODICEARCHIVIO{get;set;}


		/// <summary>
		/// Dismesso, vd AUTORIZZAZIONI
		/// </summary>
		[Obsolete("Campo dismesso")]
		[DataField("AUTORIZNUMERO", Size = 25, Type = DbType.String, CaseSensitive = false)]
		[XmlIgnore]
		public string AUTORIZNUMERO{get;set;}

		DateTime? autorizdata = null;
		/// <summary>
		/// Dismesso, vd AUTORIZZAZIONI
		/// </summary>
		[Obsolete("Campo dismesso")]
		[DataField("AUTORIZDATA", Type = DbType.DateTime)]
		[XmlIgnore]
		public DateTime? AUTORIZDATA
		{
			get { return autorizdata; }
			set { autorizdata = VerificaDataLocale(value); }
		}

		DateTime? autorizdataregistr = null;
		/// <summary>
		/// Dismesso, vd AUTORIZZAZIONI
		/// </summary>
		[Obsolete("Campo dismesso")]
		[DataField("AUTORIZDATAREGISTR", Type = DbType.DateTime)]
		[XmlIgnore]
		public DateTime? AUTORIZDATAREGISTR
		{
			get { return autorizdataregistr; }
			set { autorizdataregistr = VerificaDataLocale(value); }
		}

		/// <summary>
		/// Dismesso, vd AUTORIZZAZIONI
		/// </summary>
		[Obsolete("Campo dismesso")]
		[DataField("AUTORIZRESPONSABILE", Size = 80, Type = DbType.String, CaseSensitive = false)]
		[XmlIgnore]
		public string AUTORIZRESPONSABILE{get;set;}


		/// <summary>
		/// Dismesso, vd ISTANZEDYN2MODELLIT
		/// </summary>
		[Obsolete("utilizzare i nuovi dati dinamici", true)]
		[DataField("IDMODELLO", Type = DbType.Decimal)]
		[XmlIgnore]
		public string IDMODELLO{get;set;}

		
		/// <summary>
		/// Dismesso
		/// </summary>
		[Obsolete("Campo dismesso", true)]
		[DataField("SETITOLARELEGALE", Type = DbType.Decimal)]
		[XmlIgnore]
		public string SETITOLARELEGALE{get;set;}

		
		/// <summary>
		/// Campo dismesso
		/// </summary>
		[Obsolete("Utilizzare la tabella ORARIAPERTURATESTATA", true)]
		[DataField("FKTOID", Type = DbType.Decimal)]
		[XmlIgnore]
		public string FKTOID{get;set;}

		
		/// <summary>
		/// Campo dismesso (vd autorizzazioni)
		/// </summary>
		[Obsolete("Campo dismesso (vd autorizzazioni)", true)]
		[DataField("FKIDREGISTRO", Type = DbType.Decimal)]
		[XmlIgnore]
		public string FKIDREGISTRO{get;set;}

		
		/// <summary>
		/// Campo dismesso (vd autorizzazioni)
		/// </summary>
		[Obsolete("Campo dismesso (vd autorizzazioni)", true)]
		[DataField("NOMEATTIVITA", Size = 250, Type = DbType.String, CaseSensitive = false)]
		[XmlIgnore]
		public string NOMEATTIVITA{get;set;}
		#endregion

        /// <summary>
        /// E' il domicilio elettronico di riferimento (tipicamente PEC) per ricevere comunicazioni in merito alla pratica da parte dei richiedenti/professionisti
        /// </summary>
        [DataField("DOMICILIO_ELETTRONICO", Type = DbType.String, Size = 320, CaseSensitive = false)]
		[XmlElement(Order = 810)]
        public string DOMICILIO_ELETTRONICO { get; set; }

        /// <summary>
        /// E' il domicilio elettronico di riferimento (tipicamente PEC) per ricevere comunicazioni in merito alla pratica da parte dei richiedenti/professionisti
        /// </summary>
        [DataField("CODICEPRATICATEL", Type = DbType.String, Size = 40, CaseSensitive = false)]
		[XmlElement(Order = 820)]
        public string CODICEPRATICATEL { get; set; }

        [DataField("UUID", Type = DbType.String, Size = 60, CaseSensitive = false)]
        [XmlElement(Order = 830)]
        public string UUID { get; set; }

        [DataField("NATURA", Type = DbType.String, Size = 20, CaseSensitive = false)]
        [XmlElement(Order = 840)]
        public string Natura { get; set; }

        [ForeignKey("IDCOMUNE, CODICEISTANZA", "IdComune, CodiceIstanza")]
        [XmlElement(Order = 850)]
        public List<IstanzaCollegata> IstanzeCollegate { get; set; }
    }
}