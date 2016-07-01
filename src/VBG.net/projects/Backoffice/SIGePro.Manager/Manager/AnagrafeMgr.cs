using System;
using System.Collections.Generic;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.Anagrafe;
using Init.SIGePro.Manager.Logic.DatiDinamici;
using Init.SIGePro.Validator;
using PersonalLib2.Data;
using PersonalLib2.Sql.Collections;
using IncongruentDataException = Init.SIGePro.Exceptions.IncongruentDataException;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.DatiDinamici.Interfaces.Anagrafe;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.Manager.Manager;
using System.Data;
using Init.SIGePro.Manager.Logic.DatiDinamici.DataAccessProviders;

namespace Init.SIGePro.Manager
{
	public class AnagrafeMgr : BaseManager, IAnagrafeManager
	{
		public enum TipoPersona
		{
			Fisica,
			Giuridica
		}

		bool m_escludiControlliSuAnagraficheDisabilitate = false;
		bool m_forzainserimentoanagrafe = false;
		bool m_ricercasolocf_piva = false;
		bool m_escludiverificaincongruenze = false;

		public bool EscludiControlliSuAnagraficheDisabilitate
		{
			get { return m_escludiControlliSuAnagraficheDisabilitate; }
			set { m_escludiControlliSuAnagraficheDisabilitate = value; }
		}
		public bool ForzaInserimentoAnagrafe
		{
			get { return m_forzainserimentoanagrafe; }
			set { m_forzainserimentoanagrafe = value; }
		}
		public bool RicercaSoloCF_PIVA
		{
			get { return m_ricercasolocf_piva; }
			set { m_ricercasolocf_piva = value; }
		}
		public bool EscludiVerificaIncongruenze
		{
			get { return m_escludiverificaincongruenze; }
			set { m_escludiverificaincongruenze = value; }
		}

		public AnagrafeMgr(DataBase dataBase) : base(dataBase) { }
        [Obsolete()]
		public Anagrafe GetById(String pCODICEANAGRAFE, String pIDCOMUNE)
		{
			Anagrafe retVal = new Anagrafe();
			retVal.CODICEANAGRAFE = pCODICEANAGRAFE;
			retVal.IDCOMUNE = pIDCOMUNE;

			DataClassCollection anagrafe = db.GetClassList(retVal, true, false);
			if (anagrafe.Count != 0)
				return (anagrafe[0]) as Anagrafe;

			return null;
		}

		public Anagrafe GetById(string idComune, int codiceAnagrafe)
		{
			Anagrafe filtro = new Anagrafe();

			filtro.IDCOMUNE = idComune;
			filtro.CODICEANAGRAFE = codiceAnagrafe.ToString();

			return (Anagrafe)db.GetClass(filtro);
		}

		public Anagrafe GetPersonaFisicaByCodiceFiscale(string idComune, string codFiscale)
		{
			var filtro = new Anagrafe
			{
				IDCOMUNE = idComune,
				CODICEFISCALE = codFiscale,
				FLAG_DISABILITATO = "0",
				TIPOANAGRAFE = "F"
			};

			return (Anagrafe)db.GetClass(filtro);
		}

		public Anagrafe GetByCodiceFiscalePartitaIvaETipoPersona(string idComune, string codFiscalePartitaIva, TipoPersona tipoPersona)
		{
			var filtro = new Anagrafe
			{
				IDCOMUNE = idComune,
				CODICEFISCALE = codFiscalePartitaIva,
				TIPOANAGRAFE = tipoPersona == TipoPersona.Fisica ? "F" : "G",
				FLAG_DISABILITATO = "0"
			};

			var anagrafe = (Anagrafe)db.GetClass(filtro);

			if (anagrafe != null)
				return anagrafe;

			filtro = new Anagrafe
			{
				IDCOMUNE = idComune,
				PARTITAIVA = codFiscalePartitaIva,
				TIPOANAGRAFE = tipoPersona == TipoPersona.Fisica ? "F" : "G",
				FLAG_DISABILITATO = "0"
			};

			return (Anagrafe)db.GetClass(filtro);
		}

		public List<Anagrafe> GetList(Anagrafe p_class)
		{
			return this.GetList(p_class, null);
		}

		public List<Anagrafe> GetList(Anagrafe p_class, Anagrafe p_cmpClass)
		{
			return db.GetClassList(p_class, p_cmpClass, false, false).ToList<Anagrafe>();
		}


		public Anagrafe Insert(Anagrafe p_class)
		{
			return Insert(p_class, true);
		}

		public Anagrafe Insert(Anagrafe p_class, bool VerifyIfExist)
		{
			Anagrafe retVal;

			if (VerifyIfExist)
			{
				//Quando il secondo parametro del metodo extract è = true il confronto delle incongruenze viene fatto solamente per codice fiscale e/o partita IVA.
				//Questo perchè in fase di inserimento, se almeno uno di questi dati corrisponde non posso procedere con l'inserimento.
				//Nel caso in cui tali dati non vengano passati, devo fare un controllo su tutti gli altri dati sensibili e per fare questo imposto il secondo filtro
				// = false
				if (string.IsNullOrEmpty(p_class.CODICEFISCALE) && string.IsNullOrEmpty(p_class.PARTITAIVA))
					retVal = Extract(p_class, false);
				else
					retVal = Extract(p_class, true);

				if (!string.IsNullOrEmpty(retVal.IDCOMUNE))
				{
					throw new Exceptions.Anagrafe.OmonimiaException(p_class, "Esiste già un record simile in anagrafica! I campi vuoti sono stati aggiornati con i dati della classe passata.");
				}
			}

			retVal = (Anagrafe)p_class.Clone();

			Validate(retVal, AmbitoValidazione.Insert);

			retVal = DataIntegrations(retVal);

			db.Insert(retVal);

			retVal = ChildDataIntegrations(retVal);

			ChildInsert(retVal);

			return retVal;
		}

		public Anagrafe Update(Anagrafe p_class)
		{
			db.Update(p_class);
			return p_class;
		}

		private Anagrafe DataIntegrations(Anagrafe p_class)
		{
			Anagrafe retVal = (Anagrafe)p_class.Clone();

			if (string.IsNullOrEmpty(retVal.CODCOMNASCITA) && retVal.ComuneNascita != null)
			{
				Comuni c = new ComuniMgr(db).GetByClass(retVal.ComuneNascita);
				if (c != null)
					retVal.CODCOMNASCITA = c.CODICECOMUNE;
			}

			if (string.IsNullOrEmpty(retVal.CODCOMREGDITTE) && retVal.ComuneRegDitte != null)
			{
				Comuni c = new ComuniMgr(db).GetByClass(retVal.ComuneRegDitte);
				if (c != null)
					retVal.CODCOMREGDITTE = c.CODICECOMUNE;
			}

			if (string.IsNullOrEmpty(retVal.CODCOMREGTRIB) && retVal.ComuneRegTrib != null)
			{
				Comuni c = new ComuniMgr(db).GetByClass(retVal.ComuneRegTrib);
				if (c != null)
					retVal.CODCOMREGTRIB = c.CODICECOMUNE;
			}

			if (string.IsNullOrEmpty(retVal.COMUNECORRISPONDENZA) && retVal.ComuneCorrispondenza != null)
			{
				Comuni c = new ComuniMgr(db).GetByClass(retVal.ComuneCorrispondenza);
				if (c != null)
					retVal.COMUNECORRISPONDENZA = c.CODICECOMUNE;
			}

			if (string.IsNullOrEmpty(retVal.COMUNERESIDENZA) && retVal.ComuneResidenza != null)
			{
				Comuni c = new ComuniMgr(db).GetByClass(retVal.ComuneResidenza);
				if (c != null)
					retVal.COMUNERESIDENZA = c.CODICECOMUNE;
			}

			if (string.IsNullOrEmpty(retVal.CODICECITTADINANZA) && retVal.Cittadinanza != null)
			{
				Cittadinanza c = new CittadinanzaMgr(db).GetByClass(retVal.Cittadinanza);
				if (c != null)
					retVal.CODICECITTADINANZA = c.Codice.ToString();
			}

			if (string.IsNullOrEmpty(retVal.FORMAGIURIDICA) && retVal.FormaGiuridicaClass != null)
			{
				FormeGiuridiche c = new FormeGiuridicheMgr(db).GetByClass(retVal.FormaGiuridicaClass);
				if (c != null)
					retVal.FORMAGIURIDICA = c.FORMAGIURIDICA;
			}

			if (retVal.TIPOANAGRAFE == "F")
			{
				if (!String.IsNullOrEmpty(p_class.FORMAGIURIDICA))
					throw new Exceptions.Anagrafe.IncongruentDataException(p_class, "Non è possibile specificare ANAGRAFE.FORMAGIURIDICA per una persona fisica (ANAGRAFE.TIPOANAGRAFE = F) ");

				//if ( ! IsStringEmpty( p_class.PARTITAIVA ) )
				//	throw new IncongruentDataException(p_class, "Non è possibile specificare ANAGRAFE.PARTITAIVA per una persona fisica (ANAGRAFE.TIPOANAGRAFE = F) ");

				//if ( ! IsStringEmpty( p_class.REGDITTE ) )
				//	throw new Exceptions.Anagrafe.IncongruentDataException(p_class, "Non è possibile specificare ANAGRAFE.REGDITTE per una persona fisica (ANAGRAFE.TIPOANAGRAFE = F) ");

				if (!String.IsNullOrEmpty(p_class.REGTRIB))
					throw new Exceptions.Anagrafe.IncongruentDataException(p_class, "Non è possibile specificare ANAGRAFE.REGTRIB per una persona fisica (ANAGRAFE.TIPOANAGRAFE = F) ");

				//if ( ! IsStringEmpty( p_class.CODCOMREGDITTE ) )
				//	throw new Exceptions.Anagrafe.IncongruentDataException(p_class, "Non è possibile specificare ANAGRAFE.CODCOMREGDITTE per una persona fisica (ANAGRAFE.TIPOANAGRAFE = F) ");

				if (!String.IsNullOrEmpty(p_class.CODCOMREGTRIB))
					throw new Exceptions.Anagrafe.IncongruentDataException(p_class, "Non è possibile specificare ANAGRAFE.CODCOMREGTRIB per una persona fisica (ANAGRAFE.TIPOANAGRAFE = F) ");

				//if ( ! IsObjectEmpty( p_class.DATAREGDITTE) )
				//	throw new Exceptions.Anagrafe.IncongruentDataException(p_class, "Non è possibile specificare ANAGRAFE.DATAREGDITTE per una persona fisica (ANAGRAFE.TIPOANAGRAFE = F) ");

				if (p_class.DATAREGTRIB.GetValueOrDefault(DateTime.MinValue) != DateTime.MinValue)
					throw new Exceptions.Anagrafe.IncongruentDataException(p_class, "Non è possibile specificare ANAGRAFE.DATAREGTRIB per una persona fisica (ANAGRAFE.TIPOANAGRAFE = F) ");

				if (p_class.DATANOMINATIVO.GetValueOrDefault( DateTime.MinValue) != DateTime.MinValue)
					throw new Exceptions.Anagrafe.IncongruentDataException(p_class, "Non è possibile specificare ANAGRAFE.DATANOMINATIVO per una persona fisica (ANAGRAFE.TIPOANAGRAFE = F) ");

				if (!String.IsNullOrEmpty(p_class.PROVINCIAREA))
					throw new Exceptions.Anagrafe.IncongruentDataException(p_class, "Non è possibile specificare ANAGRAFE.PROVINCIAREA per una persona fisica (ANAGRAFE.TIPOANAGRAFE = F) ");

				//if ( ! IsStringEmpty( p_class.NUMISCRREA) )
				//	throw new Exceptions.Anagrafe.IncongruentDataException(p_class, "Non è possibile specificare ANAGRAFE.NUMISCRREA per una persona fisica (ANAGRAFE.TIPOANAGRAFE = F) ");

				//if ( ! IsObjectEmpty( p_class.DATAISCRREA) )
				//	throw new Exceptions.Anagrafe.IncongruentDataException(p_class, "Non è possibile specificare ANAGRAFE.DATAISCRREA per una persona fisica (ANAGRAFE.TIPOANAGRAFE = F) ");

				if (!String.IsNullOrEmpty(p_class.FLAG_NOPROFIT) && p_class.FLAG_NOPROFIT != "0")
					throw new Exceptions.Anagrafe.IncongruentDataException(p_class, "Non è possibile specificare ANAGRAFE.FLAG_NOPROFIT per una persona fisica (ANAGRAFE.TIPOANAGRAFE = F) ");
			}
			else
			{

				if (!String.IsNullOrEmpty(p_class.CODCOMNASCITA))
					throw new Exceptions.Anagrafe.IncongruentDataException(p_class, "Non è possibile specificare ANAGRAFE.CODCOMNASCITA per una persona giuridica (ANAGRAFE.TIPOANAGRAFE = G) ");

				if (p_class.DATANASCITA.GetValueOrDefault(DateTime.MinValue) != DateTime.MinValue)
					throw new Exceptions.Anagrafe.IncongruentDataException(p_class, "Non è possibile specificare ANAGRAFE.DATANASCITA per una persona giuridica (ANAGRAFE.TIPOANAGRAFE = G) ");

				if (!String.IsNullOrEmpty(p_class.SESSO))
					throw new Exceptions.Anagrafe.IncongruentDataException(p_class, "Non è possibile specificare ANAGRAFE.SESSO per una persona giuridica (ANAGRAFE.TIPOANAGRAFE = G) ");

				if (!String.IsNullOrEmpty(p_class.NOME))
					throw new Exceptions.Anagrafe.IncongruentDataException(p_class, "Non è possibile specificare ANAGRAFE.NOME per una persona giuridica (ANAGRAFE.TIPOANAGRAFE = G) ");

				if (!String.IsNullOrEmpty(p_class.TITOLO))
					throw new Exceptions.Anagrafe.IncongruentDataException(p_class, "Non è possibile specificare ANAGRAFE.TITOLO per una persona giuridica (ANAGRAFE.TIPOANAGRAFE = G) ");

				if (!String.IsNullOrEmpty(p_class.CODICECITTADINANZA))
					throw new Exceptions.Anagrafe.IncongruentDataException(p_class, "Non è possibile specificare ANAGRAFE.CODICECITTADINANZA per una persona giuridica (ANAGRAFE.TIPOANAGRAFE = G) ");
			}

			return retVal;
		}

		private Anagrafe ChildDataIntegrations(Anagrafe p_class)
		{
			Anagrafe retVal = (Anagrafe)p_class.Clone();

			#region i. Integrazione delle classi figlio con i dati della classe padre

			#region 1.	AnagrafeDocumenti

			foreach (AnagrafeDocumenti p_doc in retVal.AnagrafeDocumenti)
			{
				if (String.IsNullOrEmpty(p_doc.IDCOMUNE))
					p_doc.IDCOMUNE = retVal.IDCOMUNE;
				else if (p_doc.IDCOMUNE.ToUpper() != retVal.IDCOMUNE.ToUpper())
					throw new IncongruentDataException("ANAGRAFEDOCUMENTI.IDCOMUNE diverso da ANAGRAFE.IDCOMUNE");

				if (String.IsNullOrEmpty(p_doc.CODICEANAGRAFE))
					p_doc.CODICEANAGRAFE = retVal.CODICEANAGRAFE;
			}

			#endregion

			#region 2.	DynDati
			foreach (AnagrafeDyn2ModelliT p_dyn in retVal.AnagrafeDyn2ModelliT)
			{
				if (string.IsNullOrEmpty(p_dyn.Idcomune))
					p_dyn.Idcomune = retVal.IDCOMUNE;
				else if (p_dyn.Idcomune != retVal.IDCOMUNE)
					throw new Exceptions.IncongruentDataException("ANAGRAFEDYN2MODELLIT.IDCOMUNE diverso da ANAGRAFE.IDCOMUNE");

				if (p_dyn.Codiceanagrafe.GetValueOrDefault(int.MinValue) == int.MinValue)
					p_dyn.Codiceanagrafe = Convert.ToInt32(retVal.CODICEANAGRAFE);
				else if (p_dyn.Codiceanagrafe.Value != Convert.ToInt32(retVal.CODICEANAGRAFE))
					throw new Exceptions.IncongruentDataException("ANAGRAFEDYN2MODELLIT.CODICEANAGRAFE diverso da ANAGRAFE.CODICEANAGRAFE");
			}

			foreach (AnagrafeDyn2Dati p_dynDat in retVal.AnagrafeDyn2Dati)
			{
				if (string.IsNullOrEmpty(p_dynDat.Idcomune))
					p_dynDat.Idcomune = retVal.IDCOMUNE;
				else if (p_dynDat.Idcomune != retVal.IDCOMUNE)
					throw new Exceptions.IncongruentDataException("ANAGRAFEDYN2DATI.IDCOMUNE diverso da ANAGRAFE.IDCOMUNE");

				if (p_dynDat.Codiceanagrafe.GetValueOrDefault(int.MinValue) > int.MinValue)
					p_dynDat.Codiceanagrafe = Convert.ToInt32(retVal.CODICEANAGRAFE);
				else if (p_dynDat.Codiceanagrafe.Value != Convert.ToInt32(retVal.CODICEANAGRAFE))
					throw new Exceptions.IncongruentDataException("ANAGRAFEDYN2DATI.CODICEANAGRAFE diverso da ANAGRAFE.CODICEANAGRAFE");
			}
			#endregion

			#region 2.	DynDati
			foreach (AnagrafeDyn2ModelliT p_dyn in retVal.AnagrafeDyn2ModelliT)
			{
				if (string.IsNullOrEmpty(p_dyn.Idcomune))
					p_dyn.Idcomune = retVal.IDCOMUNE;
				else if (p_dyn.Idcomune != retVal.IDCOMUNE)
					throw new Exceptions.IncongruentDataException("ANAGRAFEDYN2MODELLIT.IDCOMUNE diverso da ANAGRAFE.IDCOMUNE");

				if (p_dyn.Codiceanagrafe.GetValueOrDefault(int.MinValue) == int.MinValue)
					p_dyn.Codiceanagrafe = Convert.ToInt32(retVal.CODICEANAGRAFE);
				else if (p_dyn.Codiceanagrafe.Value != Convert.ToInt32(retVal.CODICEANAGRAFE))
					throw new Exceptions.IncongruentDataException("ANAGRAFEDYN2MODELLIT.CODICEANAGRAFE diverso da ANAGRAFE.CODICEANAGRAFE");
			}

			foreach (MercatiPresenzeStorico p_preSto in retVal.PresenzeStoriche)
			{
				if (string.IsNullOrEmpty(p_preSto.Idcomune))
					p_preSto.Idcomune = retVal.IDCOMUNE;
				else if (p_preSto.Idcomune != retVal.IDCOMUNE)
					throw new Exceptions.IncongruentDataException("MERCATIPRESENZE_STORICO.IDCOMUNE diverso da ANAGRAFE.IDCOMUNE");

				if (p_preSto.Codiceanagrafe.GetValueOrDefault(int.MinValue) == int.MinValue)
					p_preSto.Codiceanagrafe = Convert.ToInt32(retVal.CODICEANAGRAFE);
				else if (p_preSto.Codiceanagrafe.Value != Convert.ToInt32(retVal.CODICEANAGRAFE))
					throw new Exceptions.IncongruentDataException("MERCATIPRESENZE_STORICO.CODICEANAGRAFE diverso da ANAGRAFE.CODICEANAGRAFE");
			}
			#endregion

			#endregion


			return retVal;
		}

		public void Validate(Anagrafe p_class, AmbitoValidazione ambitoValidazione)
		{
			if (String.IsNullOrEmpty(p_class.TIPOLOGIA))
				p_class.TIPOLOGIA = "0";

			if (String.IsNullOrEmpty(p_class.FLAG_DISABILITATO))
				p_class.FLAG_DISABILITATO = "0";

			RequiredFieldValidate(p_class, ambitoValidazione);

			if (p_class.TIPOLOGIA != "0" && p_class.TIPOLOGIA != "-1")
				throw (new Exceptions.Anagrafe.TypeMismatchException(p_class, "Impossibile inserire" + p_class.TIPOLOGIA + " in ANAGRAFE.TIPOLOGIA"));

			if (p_class.TIPOANAGRAFE != "F" && p_class.TIPOANAGRAFE != "G")
				throw (new Exceptions.Anagrafe.TypeMismatchException(p_class, "Impossibile inserire" + p_class.TIPOANAGRAFE + " in ANAGRAFE.TIPOANAGRAFE"));

			if (p_class.FLAG_DISABILITATO != "0" && p_class.FLAG_DISABILITATO != "1")
				throw (new Exceptions.Anagrafe.TypeMismatchException(p_class, "Impossibile inserire" + p_class.FLAG_DISABILITATO + " in ANAGRAFE.FLAG_DISABILITATO"));

			ConfigurazioneMgr pConfigurazioneMgr = new ConfigurazioneMgr(this.db);
			Configurazione pConfigurazione = pConfigurazioneMgr.GetById(p_class.IDCOMUNE, "TT");

			//if (pConfigurazione.CODFISOBBLIG == "1")
			//{
			//    if (string.IsNullOrEmpty(p_class.CODICEFISCALE) && string.IsNullOrEmpty(p_class.PARTITAIVA))
			//    {
			//        throw new Exceptions.Anagrafe.RequiredFieldException(p_class, "Il campo ANAGRAFE.CODICEFISCALE o ANAGRAFE.PARTITAIVA è obbligatorio perchè richiesto da CONFIGURAZIONE.CODFISOBBLIG");
			//    }
			//}

			ForeignValidate(p_class);
		}

		private void ForeignValidate(Anagrafe p_class)
		{

			#region ANAGRAFE.FORMAGIURIDICA
			if (!String.IsNullOrEmpty(p_class.FORMAGIURIDICA))
			{
				if (this.recordCount("FORMEGIURIDICHE", "CODICEFORMAGIURIDICA", "WHERE CODICEFORMAGIURIDICA = " + p_class.FORMAGIURIDICA + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0)
				{
					throw (new Exceptions.Anagrafe.RecordNotfoundException(p_class, "ANAGRAFE.FORMAGIURIDICA non trovato nella tabella FORMEGIURIDICHE"));
				}
			}
			#endregion

			#region ANAGRAFE.CODCOMREGDITTE
			if (!String.IsNullOrEmpty(p_class.CODCOMREGDITTE))
			{
				if (this.recordCount("COMUNI", "CODICECOMUNE", "WHERE CODICECOMUNE = '" + p_class.CODCOMREGDITTE + "'") == 0)
				{
					throw (new Exceptions.Anagrafe.RecordNotfoundException(p_class, "ANAGRAFE.CODCOMREGDITTE non trovato nella tabella COMUNI"));
				}
			}
			#endregion

			#region ANAGRAFE.CODCOMREGTRIB
			if (!String.IsNullOrEmpty(p_class.CODCOMREGTRIB))
			{
				if (this.recordCount("COMUNI", "CODICECOMUNE", "WHERE CODICECOMUNE = '" + p_class.CODCOMREGTRIB + "'") == 0)
				{
					throw (new Exceptions.Anagrafe.RecordNotfoundException(p_class, "ANAGRAFE.CODCOMREGTRIB non trovato nella tabella COMUNI"));
				}
			}
			#endregion

			#region ANAGRAFE.CODCOMNASCITA
			if (!String.IsNullOrEmpty(p_class.CODCOMNASCITA))
			{
				if (this.recordCount("COMUNI", "CODICECOMUNE", "WHERE CODICECOMUNE = '" + p_class.CODCOMNASCITA + "'") == 0)
				{
					throw (new Exceptions.Anagrafe.RecordNotfoundException(p_class, "ANAGRAFE.CODCOMNASCITA non trovato nella tabella COMUNI"));
				}
			}
			#endregion

			#region ANAGRAFE.COMUNERESIDENZA
			if (!String.IsNullOrEmpty(p_class.COMUNERESIDENZA))
			{
				if (this.recordCount("COMUNI", "CODICECOMUNE", "WHERE CODICECOMUNE = '" + p_class.COMUNERESIDENZA + "'") == 0)
				{
					throw (new Exceptions.Anagrafe.RecordNotfoundException(p_class, "ANAGRAFE.COMUNERESIDENZA non trovato nella tabella COMUNI"));
				}
			}
			#endregion

			#region ANAGRAFE.COMUNECORRISPONDENZA
			if (!String.IsNullOrEmpty(p_class.COMUNECORRISPONDENZA))
			{
				if (this.recordCount("COMUNI", "CODICECOMUNE", "WHERE CODICECOMUNE = '" + p_class.COMUNECORRISPONDENZA + "'") == 0)
				{
					throw (new Exceptions.Anagrafe.RecordNotfoundException(p_class, "ANAGRAFE.COMUNECORRISPONDENZA non trovato nella tabella COMUNI"));
				}
			}
			#endregion

			#region ANAGRAFE.TITOLO
			if (!String.IsNullOrEmpty(p_class.TITOLO))
			{
				if (this.recordCount("TITOLI", "CODICETITOLO", "WHERE CODICETITOLO = " + p_class.TITOLO + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0)
				{
					throw (new Exceptions.Anagrafe.RecordNotfoundException(p_class, "ANAGRAFE.TITOLO non trovato nella tabella TITOLI"));
				}
			}
			#endregion

			#region ANAGRAFE.CODICECITTADINANZA
			if (!String.IsNullOrEmpty(p_class.CODICECITTADINANZA))
			{
				if (this.recordCount("CITTADINANZA", "CODICE", "WHERE CODICE = " + p_class.CODICECITTADINANZA) == 0)
				{
					throw (new Exceptions.Anagrafe.RecordNotfoundException(p_class, "ANAGRAFE.CODICECITTADINANZA non trovato nella tabella CITTADINANZA"));
				}
			}
			#endregion


		}

		private void ChildInsert(Anagrafe p_class)
		{
			for (int i = 0; i < p_class.AnagrafeDocumenti.Count; i++)
			{
				AnagrafeDocumentiMgr pManager = new AnagrafeDocumentiMgr(this.db);
				pManager.Insert(p_class.AnagrafeDocumenti[i]);
			}

			foreach (AnagrafeDyn2ModelliT id2mt in p_class.AnagrafeDyn2ModelliT)
			{
				AnagrafeDyn2ModelliTMgr pManager = new AnagrafeDyn2ModelliTMgr(this.db);
				pManager.Insert(id2mt);

				var dap = new Dyn2DataAccessProvider(db);
				var loader = new ModelloDinamicoLoader(dap, p_class.IDCOMUNE, false);
				var mda = new ModelloDinamicoAnagrafica(loader, id2mt.FkD2mtId.Value, id2mt.Codiceanagrafe.Value, 0, false);

				foreach (AnagrafeDyn2Dati id2d in p_class.AnagrafeDyn2Dati)
				{
					var cdb = mda.TrovaCampoDaId(id2d.FkD2cId.Value);

					if (cdb != null)
						cdb.ListaValori[id2d.IndiceMolteplicita.GetValueOrDefault(0)].Valore = id2d.Valore;
				}

				mda.ValidaModello();
				mda.Salva();
			}

			foreach (MercatiPresenzeStorico mps in p_class.PresenzeStoriche)
			{
				MercatiPresenzeStoricoMgr pManager = new MercatiPresenzeStoricoMgr(this.db);

				pManager.EscludiControlliSuAnagraficheDisabilitate = EscludiControlliSuAnagraficheDisabilitate;
				pManager.EscludiVerificaIncongruenze = EscludiVerificaIncongruenze;
				pManager.ForzaInserimentoAnagrafe = false;
				pManager.InsertAnagrafe = false;
				pManager.RicercaSoloCF_PIVA = RicercaSoloCF_PIVA;
				pManager.UpdateAnagrafe = false;

				pManager.Insert(mps);
			}
		}

		public Anagrafe Extract(Anagrafe p_class)
		{
			return Extract(p_class, false);
		}

		public Anagrafe Extract(Anagrafe p_class, bool forInsert)
		{
			return Extract(p_class, forInsert, true);
		}
		/// <summary>
		/// Estrae una anagrafica dalle anagrafiche di sigepro utilizzando le ricerche
		/// per Partita iva e Codice Fiscale, quindi per Nominativo e ragione sociale.
		/// </summary>
		/// <param name="p_class">E' la classe di tipo Anagrafe da ricercare. Non deve essere passato il codiceanagrafe.</param>
		/// <param name="forInsert">Se true la ricerca viene fatta solo su codicefiscale e partita iva (se specificati). Non viene fatta la ricerca per nominativo o ragione sociale.</param>
		/// <param name="updateSIGePro">Se è stata trovata un'anagrafica e il parametro è true aggiorna i dati mancanti dell'anagrafica trovata con i dati dell'anagrafica passata.</param>
		/// <returns>L'anagrafe trovata o una classe di tipo Anagrafe vuota.</returns>
		public Anagrafe Extract(Anagrafe anagraficaCercata, bool forInsert, bool updateSIGePro)
		{
			Anagrafe anagraficaTrovata = new Anagrafe();

			if (String.IsNullOrEmpty(anagraficaCercata.IDCOMUNE))
				throw new RequiredFieldException(anagraficaCercata, "ANAGRAFE.IDCOMUNE non presente");

			if (!String.IsNullOrEmpty(anagraficaCercata.CODICEANAGRAFE))
			{
				throw new IncongruentDataException("Non è possibile utilizzare il metodo AnagrafeMgr.Extract se si conosce già il valore della colonna CODICEANAGRAFE(" + anagraficaCercata.CODICEANAGRAFE + ". Anagrafica=" + anagraficaCercata.NOMINATIVO + " " + anagraficaCercata.NOME + " CF=" + anagraficaCercata.CODICEFISCALE + " PIVA=" + anagraficaCercata.PARTITAIVA);
			}

			if (anagraficaTrovata == null || String.IsNullOrEmpty(anagraficaTrovata.NOMINATIVO))	//se retval.NOMINATIVO = "" allora non ha trovato niente per codice effettuo altre ricerche
			{
				if (!String.IsNullOrEmpty(anagraficaCercata.NOMINATIVO))// se la p_class.NOMINATIVO != "" ...
				{
					if (String.IsNullOrEmpty(anagraficaCercata.TIPOANAGRAFE)) // ... se p_class.TIPOANAGRAFE = "" ...
					{
						anagraficaCercata.TIPOANAGRAFE = String.IsNullOrEmpty(anagraficaCercata.NOME) ? "G" : "F";
					}
				}


				if (String.IsNullOrEmpty(anagraficaCercata.TIPOANAGRAFE))
					throw new RequiredFieldException(anagraficaCercata, "ANAGRAFE.TIPOANAGRAFE non presente");

				//RICERCA DEL RECORD IN ANAGRAFE
				anagraficaTrovata = null;

				/* Aggiunto da Nicola Gargagli il 26/04/2010
				 * viene impostato a true se il flag_disabilitato dell'anagrafica cercata è == 1
				 * in questo caso non verranno effettuate le ricerche sulle anagrafiche abilitate 
				 * ma solo su quelle disabilitate
				 */
				bool ricercaSoloAnagraficheDisabilitate = anagraficaCercata.FLAG_DISABILITATO == "1";

				if (!String.IsNullOrEmpty(anagraficaCercata.CODICEFISCALE))
				{
					//1°
					if (!ricercaSoloAnagraficheDisabilitate) /* Aggiunto da Nicola Gargagli il 26/04/2010 */
						anagraficaTrovata = RicercaPerCodiceFiscaleAbilitato(anagraficaCercata, forInsert);

					//2°
					if (anagraficaTrovata == null && !EscludiControlliSuAnagraficheDisabilitate)
						anagraficaTrovata = RicercaPerCodiceFiscaleDisabilitato(anagraficaCercata, forInsert);
				}

				if (anagraficaTrovata == null && !String.IsNullOrEmpty(anagraficaCercata.PARTITAIVA))
				{
					//3°
					if (!ricercaSoloAnagraficheDisabilitate) /* Aggiunto da Nicola Gargagli il 26/04/2010 */
						anagraficaTrovata = RicercaPerPartitaIVAAbilitata(anagraficaCercata, forInsert);

					//4°
					if (anagraficaTrovata == null && !EscludiControlliSuAnagraficheDisabilitate)
						anagraficaTrovata = RicercaPerPartitaIVADisabilitata(anagraficaCercata, forInsert);
				}

				if (forInsert && !(String.IsNullOrEmpty(anagraficaCercata.CODICEFISCALE) && String.IsNullOrEmpty(anagraficaCercata.PARTITAIVA)))
				{
					//Se siamo in inserimento e il codice fiscale o la partita iva sono stati specificati allora si può uscire.
					if (anagraficaTrovata == null)
						anagraficaTrovata = new Anagrafe();

					return anagraficaTrovata;
				}


				if (!RicercaSoloCF_PIVA)
				{
					if (anagraficaTrovata == null)
					{
						if (anagraficaCercata.TIPOANAGRAFE == "F")
						{
							//6.1°
							if (!ricercaSoloAnagraficheDisabilitate) /* Aggiunto da Nicola Gargagli il 26/04/2010 */
								anagraficaTrovata = RicercaPerPersonaFisicaAbilitata(anagraficaCercata, forInsert);

							//6.2°
							if (anagraficaTrovata == null && !EscludiControlliSuAnagraficheDisabilitate)
								anagraficaTrovata = RicercaPerPersonaFisicaDisabilitata(anagraficaCercata, forInsert);
						}
						else if (anagraficaCercata.TIPOANAGRAFE == "G")
						{
							//6.1°
							if (!ricercaSoloAnagraficheDisabilitate) /* Aggiunto da Nicola Gargagli il 26/04/2010 */
								anagraficaTrovata = RicercaPerPersonaGiuridicaAbilitata(anagraficaCercata, forInsert);

							//6.2°
							if (anagraficaTrovata == null && !EscludiControlliSuAnagraficheDisabilitate)
								anagraficaTrovata = RicercaPerPersonaGiuridicaDisabilitata(anagraficaCercata, forInsert);
						}
					}
				}
			}

			if (anagraficaTrovata == null)
			{
				anagraficaTrovata = new Anagrafe();
			}
			else
			{
				if (updateSIGePro)
				{
					SetClassAnagrafe(anagraficaTrovata, anagraficaCercata);
					anagraficaTrovata = Update(anagraficaTrovata);
				}
			}


			return anagraficaTrovata;

		}

		private Anagrafe RicercaPerCodiceFiscaleAbilitato(Anagrafe p_class, bool forInsert)
		{

			Anagrafe retVal = new Anagrafe();

			//retVal.TIPOLOGIA = p_class.TIPOLOGIA;
			retVal.TIPOANAGRAFE = p_class.TIPOANAGRAFE;
			retVal.FLAG_DISABILITATO = "0";
			retVal.CODICEFISCALE = p_class.CODICEFISCALE;
			retVal.IDCOMUNE = p_class.IDCOMUNE;

			List<Anagrafe> ar = GetList(retVal);

			switch (ar.Count)
			{
				case 0:
					{
						retVal = null;
						break;
					}
				case 1:
					{
						retVal = ar[0];
						VerificaIncongruenze("RicercaPerCodiceFiscaleAbilitato", p_class, retVal, forInsert);
						break;
					}
				default:
					{
						#region + anagrafiche trovate
						//viene effettuato un controllo su tutte le anagrafiche trovate per verificare se una e solo una anagrafica coincide con tutti
						//i dati cercati
						List<Anagrafe> anagraficheCoincidenti = new List<Anagrafe>();
						foreach (Anagrafe a in ar)
						{
							try
							{
								//effettuo la verifica delle incongruenze
								VerificaIncongruenze("RicercaPerCodiceFiscaleAbilitato", p_class, a, forInsert);

								//se va a buon fine aggiungo l'anagrafica trovata al contenitore delle anagrafiche coincidenti
								anagraficheCoincidenti.Add(a);
							}
							catch (OmonimiaExceptionWarning)
							{
								//in questa fase l'eccezione viene temporaneamente ignorata per verificare se c'è almeno un'anagrafica con i dati coincidenti
							}
						}

						switch (anagraficheCoincidenti.Count)
						{
							case 0:
								{
									//nessuna delle anagrafiche trovate coincide realmente con quella cercata, per cui se non è possibie effettuare un controllo
									//su quelle disabilitate viene sollevata un'eccezione
									if (EscludiControlliSuAnagraficheDisabilitate)
									{
										throw new OmonimiaException(p_class, "La ricerca anagrafica per Codice Fiscale=" + p_class.CODICEFISCALE + ", Tipo Anagrafe=" + p_class.TIPOANAGRAFE + ", Tipologia=" + p_class.TIPOLOGIA + ", Abilitata=SI ha ritornato n." + ar.Count + " anagrafiche ma nessuna coincide con i dati passati.");
									}
									else
									{
										retVal = null;
									}
									break;
								}
							case 1:
								{
									//di tutte le anagrafiche trovate una sola coincide realmente con quella cercata per cui viene utilizzata
									retVal = anagraficheCoincidenti[0];
									break;
								}
							default:
								{
									//più anagrafiche coincidono con i dati passati, viene sollevata l'eccezione
									throw new OmonimiaException(p_class, "La ricerca anagrafica per Codice Fiscale=" + p_class.CODICEFISCALE + ", Tipo Anagrafe=" + p_class.TIPOANAGRAFE + ", Tipologia=" + p_class.TIPOLOGIA + ", Abilitata=SI ha ritornato n." + ar.Count + " anagrafiche.");
								}
						}
						#endregion
						break;
					}
			}

			return retVal;
		}

		private Anagrafe RicercaPerCodiceFiscaleDisabilitato(Anagrafe p_class, bool forInsert)
		{

			Anagrafe retVal = new Anagrafe();

			//			retVal.TIPOLOGIA = p_class.TIPOLOGIA;
			retVal.TIPOANAGRAFE = p_class.TIPOANAGRAFE;
			retVal.FLAG_DISABILITATO = "1";
			retVal.CODICEFISCALE = p_class.CODICEFISCALE;
			retVal.IDCOMUNE = p_class.IDCOMUNE;
			retVal.OrderBy = "DATA_DISABILITATO DESC";

			List<Anagrafe> ar = GetList(retVal);

			switch (ar.Count)
			{
				case 0:
					{
						retVal = null;
						break;
					}
				case 1:
					{
						retVal = ar[0];
						VerificaIncongruenze("RicercaPerCodiceFiscaleDisabilitato", p_class, retVal, forInsert);
						break;
					}
				default:
					{
						#region + anagrafiche trovate
						//viene effettuato un controllo su tutte le anagrafiche trovate per verificare se una e solo una anagrafica coincide con tutti
						//i dati cercati
						List<Anagrafe> anagraficheCoincidenti = new List<Anagrafe>();
						foreach (Anagrafe a in ar)
						{
							try
							{
								//effettuo la verifica delle incongruenze
								VerificaIncongruenze("RicercaPerCodiceFiscaleDisabilitato", p_class, a, forInsert);

								//se va a buon fine aggiungo l'anagrafica trovata al contenitore delle anagrafiche coincidenti
								anagraficheCoincidenti.Add(a);
							}
							catch (OmonimiaExceptionWarning)
							{
								//in questa fase l'eccezione viene temporaneamente ignorata per verificare se c'è almeno un'anagrafica con i dati coincidenti
							}
						}

						switch (anagraficheCoincidenti.Count)
						{
							case 0:
								{
									//nessuna delle anagrafiche trovate coincide realmente con quella cercata allora viene sollevata un'eccezione
									throw new OmonimiaExceptionWarning(p_class, "La ricerca anagrafica per Codice Fiscale=" + p_class.CODICEFISCALE + ", Tipo Anagrafe=" + p_class.TIPOANAGRAFE + ", Tipologia=" + p_class.TIPOLOGIA + ", Abilitata=NO ha ritornato n." + ar.Count + " anagrafiche ma nessuna coincide con i dati passati.");
								}
							case 1:
								{
									//di tutte le anagrafiche trovate una sola coincide realmente con quella cercata per cui viene utilizzata
									retVal = anagraficheCoincidenti[0];
									break;
								}
							default:
								{
									//più anagrafiche coincidono con i dati passati, viene presa la prima ( si tratta di anagrafiche uguali e disabilitate ).
									retVal = ar[0];
									break;
								}
						}
						#endregion
						break;
					}
			}

			return retVal;
		}

		private Anagrafe RicercaPerPartitaIVAAbilitata(Anagrafe p_class, bool forInsert)
		{

			Anagrafe retVal = new Anagrafe();

			//			retVal.TIPOLOGIA = p_class.TIPOLOGIA;
			retVal.TIPOANAGRAFE = p_class.TIPOANAGRAFE;
			retVal.FLAG_DISABILITATO = "0";
			retVal.PARTITAIVA = p_class.PARTITAIVA;
			retVal.IDCOMUNE = p_class.IDCOMUNE;

			List<Anagrafe> ar = GetList(retVal);

			switch (ar.Count)
			{
				case 0:
					{
						retVal = null;
						break;
					}
				case 1:
					{
						retVal = ar[0];
						VerificaIncongruenze("RicercaPerPartitaIVAAbilitata", p_class, retVal, forInsert);
						break;
					}
				default:
					{
						#region + anagrafiche trovate
						//viene effettuato un controllo su tutte le anagrafiche trovate per verificare se una e solo una anagrafica coincide con tutti
						//i dati cercati
						List<Anagrafe> anagraficheCoincidenti = new List<Anagrafe>();
						foreach (Anagrafe a in ar)
						{
							try
							{
								//effettuo la verifica delle incongruenze
								VerificaIncongruenze("RicercaPerPartitaIVAAbilitata", p_class, a, forInsert);

								//se va a buon fine aggiungo l'anagrafica trovata al contenitore delle anagrafiche coincidenti
								anagraficheCoincidenti.Add(a);
							}
							catch (OmonimiaExceptionWarning)
							{
								//in questa fase l'eccezione viene temporaneamente ignorata per verificare se c'è almeno un'anagrafica con i dati coincidenti
							}
						}

						switch (anagraficheCoincidenti.Count)
						{
							case 0:
								{
									//nessuna delle anagrafiche trovate coincide realmente con quella cercata, per cui se non è possibie effettuare un controllo
									//su quelle disabilitate viene sollevata un'eccezione
									if (EscludiControlliSuAnagraficheDisabilitate)
									{
										throw new OmonimiaException(p_class, "La ricerca anagrafica per Partita Iva=" + p_class.PARTITAIVA + ", Tipo Anagrafe=" + p_class.TIPOANAGRAFE + ", Tipologia=" + p_class.TIPOLOGIA + ", Abilitata=SI ha ritornato n." + ar.Count + " anagrafiche ma nessuna coincide con i dati passati.");
									}
									else
									{
										retVal = null;
									}
									break;
								}
							case 1:
								{
									//di tutte le anagrafiche trovate una sola coincide realmente con quella cercata per cui viene utilizzata
									retVal = anagraficheCoincidenti[0];
									break;
								}
							default:
								{
									//più anagrafiche coincidono con i dati passati, viene sollevata l'eccezione
									throw new OmonimiaException(p_class, "La ricerca anagrafica per Partita Iva=" + p_class.PARTITAIVA + ", Tipo Anagrafe=" + p_class.TIPOANAGRAFE + ", Tipologia=" + p_class.TIPOLOGIA + ", Abilitata=SI ha ritornato n." + ar.Count + " anagrafiche.");
								}
						}
						#endregion
						break;
					}
			}

			return retVal;
		}

		private Anagrafe RicercaPerPartitaIVADisabilitata(Anagrafe p_class, bool forInsert)
		{

			Anagrafe retVal = new Anagrafe();

			retVal.TIPOANAGRAFE = p_class.TIPOANAGRAFE;
			retVal.FLAG_DISABILITATO = "1";
			retVal.PARTITAIVA = p_class.PARTITAIVA;
			retVal.IDCOMUNE = p_class.IDCOMUNE;
			retVal.OrderBy = "DATA_DISABILITATO DESC";

			List<Anagrafe> ar = GetList(retVal);

			switch (ar.Count)
			{
				case 0:
					{
						retVal = null;
						break;
					}
				case 1:
					{
						retVal = ar[0];
						VerificaIncongruenze("RicercaPerPartitaIVADisabilitata", p_class, retVal, forInsert);
						break;
					}
				default:
					{
						#region + anagrafiche trovate
						//viene effettuato un controllo su tutte le anagrafiche trovate per verificare se una e solo una anagrafica coincide con tutti
						//i dati cercati
						List<Anagrafe> anagraficheCoincidenti = new List<Anagrafe>();
						foreach (Anagrafe a in ar)
						{
							try
							{
								//effettuo la verifica delle incongruenze
								VerificaIncongruenze("RicercaPerPartitaIVADisabilitata", p_class, a, forInsert);

								//se va a buon fine aggiungo l'anagrafica trovata al contenitore delle anagrafiche coincidenti
								anagraficheCoincidenti.Add(a);
							}
							catch (OmonimiaExceptionWarning)
							{
								//in questa fase l'eccezione viene temporaneamente ignorata per verificare se c'è almeno un'anagrafica con i dati coincidenti
							}
						}

						switch (anagraficheCoincidenti.Count)
						{
							case 0:
								{
									//nessuna delle anagrafiche trovate coincide realmente con quella cercata allora viene sollevata un'eccezione
									throw new OmonimiaExceptionWarning(p_class, "La ricerca anagrafica per Partita Iva=" + p_class.PARTITAIVA + ", Tipo Anagrafe=" + p_class.TIPOANAGRAFE + ", Tipologia=" + p_class.TIPOLOGIA + ", Abilitata=SI ha ritornato n." + ar.Count + " anagrafiche ma nessuna coincide con i dati passati.");
								}
							case 1:
								{
									//di tutte le anagrafiche trovate una sola coincide realmente con quella cercata per cui viene utilizzata
									retVal = anagraficheCoincidenti[0];
									break;
								}
							default:
								{
									//più anagrafiche coincidono con i dati passati, viene presa la prima ( si tratta di anagrafiche uguali e disabilitate ).
									retVal = ar[0];
									break;
								}
						}
						#endregion
						break;
					}
			}

			return retVal;
		}

		private Anagrafe RicercaPerPersonaFisicaAbilitata(Anagrafe p_class, bool forInsert)
		{

			Anagrafe retVal = null;
			Anagrafe cmpClass = new Anagrafe();

			//if ( ! String.IsNullOrEmpty( p_class.NOMINATIVO ) && ! String.IsNullOrEmpty( p_class.NOME ) )
			if (!String.IsNullOrEmpty(p_class.NOMINATIVO))
			{
				retVal = new Anagrafe();

				if (!String.IsNullOrEmpty(p_class.CODICEFISCALE))
				{
					//Il codice fiscale è presente quindi devo cercare solo le anagrafiche
					//che NON hanno il codicefiscale impostato
					cmpClass.CODICEFISCALE = "is null";
				}
				if (!String.IsNullOrEmpty(p_class.PARTITAIVA))
				{
					//La partita iva è presente quindi devo cercare solo le anagrafiche
					//che NON hanno la partita iva impostata
					cmpClass.PARTITAIVA = "is null";
				}
				retVal.TIPOANAGRAFE = p_class.TIPOANAGRAFE;
				retVal.FLAG_DISABILITATO = "0";
				retVal.IDCOMUNE = p_class.IDCOMUNE;
				retVal.NOMINATIVO = p_class.NOMINATIVO;
				retVal.NOME = p_class.NOME;

				List<Anagrafe> ar = GetList(retVal, cmpClass);

				switch (ar.Count)
				{
					case 0: retVal = null; break;
					case 1:
						retVal = ar[0];
						VerificaIncongruenze("RicercaPerPersonaFisicaAbilitata", p_class, retVal, forInsert);
						break;
					default:
						{
							#region + anagrafiche trovate
							//viene effettuato un controllo su tutte le anagrafiche trovate per verificare se una e solo una anagrafica coincide con tutti
							//i dati cercati
							List<Anagrafe> anagraficheCoincidenti = new List<Anagrafe>();
							foreach (Anagrafe a in ar)
							{
								try
								{
									//effettuo la verifica delle incongruenze
									VerificaIncongruenze("RicercaPerPersonaFisicaAbilitata", p_class, a, forInsert);

									//se va a buon fine aggiungo l'anagrafica trovata al contenitore delle anagrafiche coincidenti
									anagraficheCoincidenti.Add(a);
								}
								catch (OmonimiaExceptionWarning)
								{
									//in questa fase l'eccezione viene temporaneamente ignorata per verificare se c'è almeno un'anagrafica con i dati coincidenti
								}
							}

							switch (anagraficheCoincidenti.Count)
							{
								case 0:
									{
										//nessuna delle anagrafiche trovate coincide realmente con quella cercata, per cui se non è possibie effettuare un controllo
										//su quelle disabilitate viene sollevata un'eccezione
										if (EscludiControlliSuAnagraficheDisabilitate)
										{
											throw new OmonimiaException(p_class, "La ricerca anagrafica per Cognome=" + p_class.NOMINATIVO + ", Nome=" + p_class.NOME + ", Data di nascita=" + p_class.DATANASCITA + ", Tipo Anagrafe=" + p_class.TIPOANAGRAFE + ", Tipologia=" + p_class.TIPOLOGIA + ", Abilitata=SI ha ritornato n." + ar.Count + " anagrafiche ma nessuna coincide con i dati passati.");
										}
										else
										{
											retVal = null;
										}
										break;
									}
								case 1:
									{
										//di tutte le anagrafiche trovate una sola coincide realmente con quella cercata per cui viene utilizzata
										retVal = anagraficheCoincidenti[0];
										break;
									}
								default:
									{
										//più anagrafiche coincidono con i dati passati, viene sollevata l'eccezione
										throw new OmonimiaException(p_class, "La ricerca anagrafica per Cognome=" + p_class.NOMINATIVO + ", Nome=" + p_class.NOME + ", Data di nascita=" + p_class.DATANASCITA + ", Tipo Anagrafe=" + p_class.TIPOANAGRAFE + ", Tipologia=" + p_class.TIPOLOGIA + ", Abilitata=SI ha ritornato n." + ar.Count + " anagrafiche.");
									}
							}
							#endregion
							break;
						}
				}
			}

			return retVal;
		}

		private Anagrafe RicercaPerPersonaFisicaDisabilitata(Anagrafe p_class, bool forInsert)
		{

			Anagrafe retVal = null;
			Anagrafe cmpClass = new Anagrafe();

			//if ( ! String.IsNullOrEmpty( p_class.NOMINATIVO ) && ! String.IsNullOrEmpty( p_class.NOME ) )
			if (!String.IsNullOrEmpty(p_class.NOMINATIVO))
			{
				retVal = new Anagrafe();

				if (!String.IsNullOrEmpty(p_class.CODICEFISCALE))
				{
					//Il codice fiscale è presente quindi devo cercare solo le anagrafiche
					//che NON hanno il codicefiscale impostato
					cmpClass.CODICEFISCALE = "is null";
				}
				if (!String.IsNullOrEmpty(p_class.PARTITAIVA))
				{
					//La partita iva è presente quindi devo cercare solo le anagrafiche
					//che NON hanno la partita iva impostata
					cmpClass.PARTITAIVA = "is null";
				}
				//retVal.TIPOLOGIA = p_class.TIPOLOGIA;
				retVal.TIPOANAGRAFE = p_class.TIPOANAGRAFE;
				retVal.FLAG_DISABILITATO = "1";
				retVal.IDCOMUNE = p_class.IDCOMUNE;
				retVal.NOMINATIVO = p_class.NOMINATIVO;
				retVal.NOME = p_class.NOME;

				List<Anagrafe> ar = GetList(retVal, cmpClass);

				switch (ar.Count)
				{
					case 0: retVal = null; break;
					case 1:
						retVal = ar[0];
						VerificaIncongruenze("RicercaPerPersonaFisicaDisabilitata", p_class, retVal, forInsert);
						break;
					default:
						#region + anagrafiche trovate
						//viene effettuato un controllo su tutte le anagrafiche trovate per verificare se una e solo una anagrafica coincide con tutti
						//i dati cercati
						List<Anagrafe> anagraficheCoincidenti = new List<Anagrafe>();
						foreach (Anagrafe a in ar)
						{
							try
							{
								//effettuo la verifica delle incongruenze
								VerificaIncongruenze("RicercaPerPersonaFisicaDisabilitata", p_class, a, forInsert);

								//se va a buon fine aggiungo l'anagrafica trovata al contenitore delle anagrafiche coincidenti
								anagraficheCoincidenti.Add(a);
							}
							catch (OmonimiaExceptionWarning)
							{
								//in questa fase l'eccezione viene temporaneamente ignorata per verificare se c'è almeno un'anagrafica con i dati coincidenti
							}
						}

						switch (anagraficheCoincidenti.Count)
						{
							case 0:
								{
									//nessuna delle anagrafiche trovate coincide realmente con quella cercata allora viene sollevata un'eccezione
									throw new OmonimiaExceptionWarning(p_class, "La ricerca anagrafica per Cognome=" + p_class.NOMINATIVO + ", Nome=" + p_class.NOME + ", Data di nascita=" + p_class.DATANASCITA + ", Tipo Anagrafe=" + p_class.TIPOANAGRAFE + ", Tipologia=" + p_class.TIPOLOGIA + ", Abilitata=NO ha ritornato n." + ar.Count + " anagrafiche ma nessuna coincide con i dati passati.");
								}
							case 1:
								{
									//di tutte le anagrafiche trovate una sola coincide realmente con quella cercata per cui viene utilizzata
									retVal = anagraficheCoincidenti[0];
									break;
								}
							default:
								{
									//più anagrafiche coincidono con i dati passati, viene presa la prima ( si tratta di anagrafiche uguali e disabilitate ).
									retVal = ar[0];
									break;
								}
						}
						#endregion
						break;
				}
			}

			return retVal;
		}

		private Anagrafe RicercaPerPersonaGiuridicaAbilitata(Anagrafe p_class, bool forInsert)
		{

			Anagrafe retVal = null;
			Anagrafe cmpClass = new Anagrafe();

			if (!String.IsNullOrEmpty(p_class.NOMINATIVO))
			{
				retVal = new Anagrafe();

				if (!String.IsNullOrEmpty(p_class.CODICEFISCALE))
				{
					//Il codice fiscale è presente quindi devo cercare solo le anagrafiche
					//che NON hanno il codicefiscale impostato
					cmpClass.CODICEFISCALE = "is null";
				}
				if (!String.IsNullOrEmpty(p_class.PARTITAIVA))
				{
					//La partita iva è presente quindi devo cercare solo le anagrafiche
					//che NON hanno la partita iva impostata
					cmpClass.PARTITAIVA = "is null";
				}
				retVal.TIPOANAGRAFE = p_class.TIPOANAGRAFE;
				retVal.FLAG_DISABILITATO = "0";
				retVal.IDCOMUNE = p_class.IDCOMUNE;
				retVal.NOMINATIVO = p_class.NOMINATIVO;

				List<Anagrafe> ar = GetList(retVal, cmpClass);

				switch (ar.Count)
				{
					case 0: retVal = null; break;
					case 1:
						retVal = ar[0];
						VerificaIncongruenze("RicercaPerPersonaGiuridicaAbilitata", p_class, retVal, forInsert);
						break;
					default:
						{
							#region + anagrafiche trovate
							//viene effettuato un controllo su tutte le anagrafiche trovate per verificare se una e solo una anagrafica coincide con tutti
							//i dati cercati
							List<Anagrafe> anagraficheCoincidenti = new List<Anagrafe>();
							foreach (Anagrafe a in ar)
							{
								try
								{
									//effettuo la verifica delle incongruenze
									VerificaIncongruenze("RicercaPerPersonaGiuridicaAbilitata", p_class, a, forInsert);

									//se va a buon fine aggiungo l'anagrafica trovata al contenitore delle anagrafiche coincidenti
									anagraficheCoincidenti.Add(a);
								}
								catch (OmonimiaExceptionWarning)
								{
									//in questa fase l'eccezione viene temporaneamente ignorata per verificare se c'è almeno un'anagrafica con i dati coincidenti
								}
							}

							switch (anagraficheCoincidenti.Count)
							{
								case 0:
									{
										//nessuna delle anagrafiche trovate coincide realmente con quella cercata, per cui se non è possibie effettuare un controllo
										//su quelle disabilitate viene sollevata un'eccezione
										if (EscludiControlliSuAnagraficheDisabilitate)
										{
											throw new OmonimiaException(p_class, "La ricerca anagrafica per Ragione Sociale=" + p_class.NOMINATIVO + ", Data di costituzione=" + p_class.DATANOMINATIVO + ", Tipo Anagrafe=" + p_class.TIPOANAGRAFE + ", Tipologia=" + p_class.TIPOLOGIA + ", Abilitata=SI ha ritornato n." + ar.Count + " anagrafiche ma nessuna coincide con i dati passati.");
										}
										else
										{
											retVal = null;
										}
										break;
									}
								case 1:
									{
										//di tutte le anagrafiche trovate una sola coincide realmente con quella cercata per cui viene utilizzata
										retVal = anagraficheCoincidenti[0];
										break;
									}
								default:
									{
										//più anagrafiche coincidono con i dati passati, viene sollevata l'eccezione
										throw new OmonimiaException(p_class, "La ricerca anagrafica per Ragione Sociale=" + p_class.NOMINATIVO + ", Data di costituzione=" + p_class.DATANOMINATIVO + ", Tipo Anagrafe=" + p_class.TIPOANAGRAFE + ", Tipologia=" + p_class.TIPOLOGIA + ", Abilitata=SI ha ritornato n." + ar.Count + " anagrafiche.");
									}
							}
							#endregion
							break;
						}
				}
			}

			return retVal;
		}

		private Anagrafe RicercaPerPersonaGiuridicaDisabilitata(Anagrafe p_class, bool forInsert)
		{


			Anagrafe retVal = null;
			Anagrafe cmpClass = new Anagrafe();

			if (!String.IsNullOrEmpty(p_class.NOMINATIVO))
			{
				retVal = new Anagrafe();

				if (!String.IsNullOrEmpty(p_class.CODICEFISCALE))
				{
					//Il codice fiscale è presente quindi devo cercare solo le anagrafiche
					//che NON hanno il codicefiscale impostato
					cmpClass.CODICEFISCALE = "is null";
				}
				if (!String.IsNullOrEmpty(p_class.PARTITAIVA))
				{
					//La partita iva è presente quindi devo cercare solo le anagrafiche
					//che NON hanno la partita iva impostata
					cmpClass.PARTITAIVA = "is null";
				}
				//retVal.TIPOLOGIA = p_class.TIPOLOGIA;
				retVal.TIPOANAGRAFE = p_class.TIPOANAGRAFE;
				retVal.FLAG_DISABILITATO = "1";
				retVal.IDCOMUNE = p_class.IDCOMUNE;
				retVal.NOMINATIVO = p_class.NOMINATIVO;

				List<Anagrafe> ar = GetList(retVal, cmpClass);

				switch (ar.Count)
				{
					case 0:
						{
							retVal = null;
							break;
						}
					case 1:
						{
							retVal = ar[0];
							VerificaIncongruenze("RicercaPerPersonaGiuridicaDisabilitata", p_class, retVal, forInsert);
							break;
						}
					default:
						{
							#region + anagrafiche trovate
							//viene effettuato un controllo su tutte le anagrafiche trovate per verificare se una e solo una anagrafica coincide con tutti
							//i dati cercati
							List<Anagrafe> anagraficheCoincidenti = new List<Anagrafe>();
							foreach (Anagrafe a in ar)
							{
								try
								{
									//effettuo la verifica delle incongruenze
									VerificaIncongruenze("RicercaPerPersonaGiuridicaDisabilitata", p_class, a, forInsert);

									//se va a buon fine aggiungo l'anagrafica trovata al contenitore delle anagrafiche coincidenti
									anagraficheCoincidenti.Add(a);
								}
								catch (OmonimiaExceptionWarning)
								{
									//in questa fase l'eccezione viene temporaneamente ignorata per verificare se c'è almeno un'anagrafica con i dati coincidenti
								}
							}

							switch (anagraficheCoincidenti.Count)
							{
								case 0:
									{
										//nessuna delle anagrafiche trovate coincide realmente con quella cercata allora viene sollevata un'eccezione
										throw new OmonimiaExceptionWarning(p_class, "La ricerca anagrafica per Ragione Sociale=" + p_class.NOMINATIVO + ", Data di costituzione=" + p_class.DATANOMINATIVO + ", Tipo Anagrafe=" + p_class.TIPOANAGRAFE + ", Tipologia=" + p_class.TIPOLOGIA + ", Abilitata=NO ha ritornato n." + ar.Count + " anagrafiche ma nessuna coincide con i dati passati.");
									}
								case 1:
									{
										//di tutte le anagrafiche trovate una sola coincide realmente con quella cercata per cui viene utilizzata
										retVal = anagraficheCoincidenti[0];
										break;
									}
								default:
									{
										//più anagrafiche coincidono con i dati passati, viene presa la prima ( si tratta di anagrafiche uguali e disabilitate ).
										retVal = ar[0];
										break;
									}
							}
							#endregion
							break;
						}

				}
			}

			return retVal;
		}

		private void VerificaIncongruenze(string functionName, Anagrafe dataClassCercata, Anagrafe dataClassTrovata, bool forInsert)
		{
			if (EscludiVerificaIncongruenze) return;

			if ((!String.IsNullOrEmpty(dataClassCercata.CODICEFISCALE)) && (!String.IsNullOrEmpty(dataClassTrovata.CODICEFISCALE)) && (dataClassCercata.CODICEFISCALE.ToUpper().Trim() != dataClassTrovata.CODICEFISCALE.ToUpper().Trim()))
			{
				throw new OmonimiaExceptionWarning(dataClassCercata, dataClassTrovata, "CODICEFISCALE");
			}
			if ((!String.IsNullOrEmpty(dataClassCercata.PARTITAIVA)) && (!String.IsNullOrEmpty(dataClassTrovata.PARTITAIVA)) && (dataClassCercata.PARTITAIVA.ToUpper().Trim() != dataClassTrovata.PARTITAIVA.ToUpper().Trim()))
			{
				throw new OmonimiaExceptionWarning(dataClassCercata, dataClassTrovata, "PARTITAIVA");
			}
			if (forInsert) return;

			if ((!String.IsNullOrEmpty(dataClassCercata.NOMINATIVO)) && (!String.IsNullOrEmpty(dataClassTrovata.NOMINATIVO)) && (dataClassCercata.NOMINATIVO.ToUpper().Trim() != dataClassTrovata.NOMINATIVO.ToUpper().Trim()))
			{
				throw new OmonimiaExceptionWarning(dataClassCercata, dataClassTrovata, "NOMINATIVO");
			}
			if ((!String.IsNullOrEmpty(dataClassCercata.NOME)) && (!String.IsNullOrEmpty(dataClassTrovata.NOME)) && (dataClassCercata.NOME.ToUpper().Trim() != dataClassTrovata.NOME.ToUpper().Trim()))
			{
				throw new OmonimiaExceptionWarning(dataClassCercata, dataClassTrovata, "NOME");
			}

			if ((!String.IsNullOrEmpty(dataClassCercata.COMUNERESIDENZA)) && (!String.IsNullOrEmpty(dataClassTrovata.COMUNERESIDENZA)) && (dataClassCercata.COMUNERESIDENZA.Trim() != dataClassTrovata.COMUNERESIDENZA.Trim()))
			{
				throw new OmonimiaExceptionWarning(dataClassCercata, dataClassTrovata, "COMUNERESIDENZA");
			}

			if (dataClassCercata.TIPOANAGRAFE == "F")
			{
				if ((dataClassCercata.DATANASCITA.GetValueOrDefault(DateTime.MinValue) != DateTime.MinValue) && (dataClassTrovata.DATANASCITA.GetValueOrDefault(DateTime.MinValue) != DateTime.MinValue) && (dataClassCercata.DATANASCITA.Value.ToString("dd/mm/yyyy") != dataClassTrovata.DATANASCITA.Value.ToString("dd/mm/yyyy")))
				{
					throw new OmonimiaExceptionWarning(dataClassCercata, dataClassTrovata, "DATANASCITA");
				}

				if ((!String.IsNullOrEmpty(dataClassCercata.SESSO)) && (!String.IsNullOrEmpty(dataClassTrovata.SESSO)) && (dataClassCercata.SESSO != dataClassTrovata.SESSO))
				{
					throw new OmonimiaExceptionWarning(dataClassCercata, dataClassTrovata, "SESSO");
				}

				if ((!String.IsNullOrEmpty(dataClassCercata.CODCOMNASCITA)) && (!String.IsNullOrEmpty(dataClassTrovata.CODCOMNASCITA)) && (dataClassCercata.CODCOMNASCITA.Trim() != dataClassTrovata.CODCOMNASCITA.Trim()))
				{
					throw new OmonimiaExceptionWarning(dataClassCercata, dataClassTrovata, "CODCOMNASCITA");
				}

			}

			if (dataClassCercata.TIPOANAGRAFE == "G" && (dataClassCercata.DATANOMINATIVO.GetValueOrDefault(DateTime.MinValue) != DateTime.MinValue) && (dataClassTrovata.DATANOMINATIVO.GetValueOrDefault(DateTime.MinValue) != DateTime.MinValue) && (dataClassCercata.DATANOMINATIVO.Value.ToString("dd/mm/yyyy") != dataClassTrovata.DATANOMINATIVO.Value.ToString("dd/mm/yyyy")))
			{
				throw new OmonimiaExceptionWarning(dataClassCercata, dataClassTrovata, "DATANOMINATIVO");
			}
		}

		private void SetClassAnagrafe(Anagrafe anagrafeSIGePro, Anagrafe anagrafeNuova)
		{
			// TODO: aggiornare il metodo con le lettura delle proprietà della classe
			//Aggiornare solo le proprietà vuote dell'anagrafica presente in SIGePro
			//con le proprietà dell'anagrafica di Access
			//anagrafeMgr.Update(anagrafeAccess);
			if (String.IsNullOrEmpty(anagrafeSIGePro.CODICEFISCALE))
				anagrafeSIGePro.CODICEFISCALE = anagrafeNuova.CODICEFISCALE;
			if (String.IsNullOrEmpty(anagrafeSIGePro.PARTITAIVA))
				anagrafeSIGePro.PARTITAIVA = anagrafeNuova.PARTITAIVA;
			if (String.IsNullOrEmpty(anagrafeSIGePro.NOMINATIVO))
				anagrafeSIGePro.NOMINATIVO = anagrafeNuova.NOMINATIVO;
			if (String.IsNullOrEmpty(anagrafeSIGePro.NOME))
				anagrafeSIGePro.NOME = anagrafeNuova.NOME;

			if (String.IsNullOrEmpty(anagrafeSIGePro.CAP))
				anagrafeSIGePro.CAP = anagrafeNuova.CAP;
			if (String.IsNullOrEmpty(anagrafeSIGePro.CAPCORRISPONDENZA))
				anagrafeSIGePro.CAPCORRISPONDENZA = anagrafeNuova.CAPCORRISPONDENZA;
			if (String.IsNullOrEmpty(anagrafeSIGePro.CITTA))
				anagrafeSIGePro.CITTA = anagrafeNuova.CITTA;
			if (String.IsNullOrEmpty(anagrafeSIGePro.CITTACORRISPONDENZA))
				anagrafeSIGePro.CITTACORRISPONDENZA = anagrafeNuova.CITTACORRISPONDENZA;
			if (String.IsNullOrEmpty(anagrafeSIGePro.CODCOMNASCITA))
				anagrafeSIGePro.CODCOMNASCITA = anagrafeNuova.CODCOMNASCITA;
			if (String.IsNullOrEmpty(anagrafeSIGePro.CODCOMREGDITTE))
				anagrafeSIGePro.CODCOMREGDITTE = anagrafeNuova.CODCOMREGDITTE;
			if (String.IsNullOrEmpty(anagrafeSIGePro.CODCOMREGTRIB))
				anagrafeSIGePro.CODCOMREGTRIB = anagrafeNuova.CODCOMREGTRIB;
			if (String.IsNullOrEmpty(anagrafeSIGePro.CODICECITTADINANZA))
				anagrafeSIGePro.CODICECITTADINANZA = anagrafeNuova.CODICECITTADINANZA;
			if (String.IsNullOrEmpty(anagrafeSIGePro.COMUNECORRISPONDENZA))
				anagrafeSIGePro.COMUNECORRISPONDENZA = anagrafeNuova.COMUNECORRISPONDENZA;
			if (String.IsNullOrEmpty(anagrafeSIGePro.COMUNERESIDENZA))
				anagrafeSIGePro.COMUNERESIDENZA = anagrafeNuova.COMUNERESIDENZA;
			if (anagrafeSIGePro.DATAISCRREA.GetValueOrDefault(DateTime.MinValue) == DateTime.MinValue)
				anagrafeSIGePro.DATAISCRREA = anagrafeNuova.DATAISCRREA;
			if (anagrafeSIGePro.DATANASCITA.GetValueOrDefault(DateTime.MinValue) == DateTime.MinValue)
				anagrafeSIGePro.DATANASCITA = anagrafeNuova.DATANASCITA;
			if (anagrafeSIGePro.DATANOMINATIVO.GetValueOrDefault(DateTime.MinValue) == DateTime.MinValue)
				anagrafeSIGePro.DATANOMINATIVO = anagrafeNuova.DATANOMINATIVO;
			if (anagrafeSIGePro.DATAREGDITTE.GetValueOrDefault(DateTime.MinValue) == DateTime.MinValue)
				anagrafeSIGePro.DATAREGDITTE = anagrafeNuova.DATAREGDITTE;
			if (anagrafeSIGePro.DATAREGTRIB.GetValueOrDefault(DateTime.MinValue) == DateTime.MinValue)
				anagrafeSIGePro.DATAREGTRIB = anagrafeNuova.DATAREGTRIB;
			if (String.IsNullOrEmpty(anagrafeSIGePro.EMAIL))
				anagrafeSIGePro.EMAIL = anagrafeNuova.EMAIL;
			if (String.IsNullOrEmpty(anagrafeSIGePro.FAX))
				anagrafeSIGePro.FAX = anagrafeNuova.FAX;
			if (String.IsNullOrEmpty(anagrafeSIGePro.FLAG_NOPROFIT))
				anagrafeSIGePro.FLAG_NOPROFIT = anagrafeNuova.FLAG_NOPROFIT;
			if (String.IsNullOrEmpty(anagrafeSIGePro.FORMAGIURIDICA))
				anagrafeSIGePro.FORMAGIURIDICA = anagrafeNuova.FORMAGIURIDICA;
			if (String.IsNullOrEmpty(anagrafeSIGePro.INDIRIZZO))
				anagrafeSIGePro.INDIRIZZO = anagrafeNuova.INDIRIZZO;
			if (String.IsNullOrEmpty(anagrafeSIGePro.INDIRIZZOCORRISPONDENZA))
				anagrafeSIGePro.INDIRIZZOCORRISPONDENZA = anagrafeNuova.INDIRIZZOCORRISPONDENZA;
			if (String.IsNullOrEmpty(anagrafeSIGePro.INVIOEMAIL))
				anagrafeSIGePro.INVIOEMAIL = anagrafeNuova.INVIOEMAIL;
			if (String.IsNullOrEmpty(anagrafeSIGePro.INVIOEMAILTEC))
				anagrafeSIGePro.INVIOEMAILTEC = anagrafeNuova.INVIOEMAILTEC;
			if (String.IsNullOrEmpty(anagrafeSIGePro.NOTE))
				anagrafeSIGePro.NOTE = anagrafeNuova.NOTE;
			if (String.IsNullOrEmpty(anagrafeSIGePro.NUMISCRREA))
				anagrafeSIGePro.NUMISCRREA = anagrafeNuova.NUMISCRREA;
			if (String.IsNullOrEmpty(anagrafeSIGePro.PROVINCIA))
				anagrafeSIGePro.PROVINCIA = anagrafeNuova.PROVINCIA;
			if (String.IsNullOrEmpty(anagrafeSIGePro.PROVINCIACORRISPONDENZA))
				anagrafeSIGePro.PROVINCIACORRISPONDENZA = anagrafeNuova.PROVINCIACORRISPONDENZA;
			if (String.IsNullOrEmpty(anagrafeSIGePro.PROVINCIAREA))
				anagrafeSIGePro.PROVINCIAREA = anagrafeNuova.PROVINCIAREA;
			if (String.IsNullOrEmpty(anagrafeSIGePro.REFERENTE))
				anagrafeSIGePro.REFERENTE = anagrafeNuova.REFERENTE;
			if (String.IsNullOrEmpty(anagrafeSIGePro.REGDITTE))
				anagrafeSIGePro.REGDITTE = anagrafeNuova.REGDITTE;
			if (String.IsNullOrEmpty(anagrafeSIGePro.REGTRIB))
				anagrafeSIGePro.REGTRIB = anagrafeNuova.REGTRIB;
			if (String.IsNullOrEmpty(anagrafeSIGePro.SESSO))
				anagrafeSIGePro.SESSO = anagrafeNuova.SESSO;
			if (String.IsNullOrEmpty(anagrafeSIGePro.TELEFONO))
				anagrafeSIGePro.TELEFONO = anagrafeNuova.TELEFONO;
			if (String.IsNullOrEmpty(anagrafeSIGePro.TELEFONOCELLULARE))
				anagrafeSIGePro.TELEFONOCELLULARE = anagrafeNuova.TELEFONOCELLULARE;
			if (String.IsNullOrEmpty(anagrafeSIGePro.TIPOANAGRAFE))
				anagrafeSIGePro.TIPOANAGRAFE = anagrafeNuova.TIPOANAGRAFE;
            if ((String.IsNullOrEmpty(anagrafeSIGePro.TIPOLOGIA) || anagrafeSIGePro.TIPOLOGIA == "0") && !String.IsNullOrEmpty(anagrafeNuova.TIPOLOGIA))
				anagrafeSIGePro.TIPOLOGIA = anagrafeNuova.TIPOLOGIA;
			if (String.IsNullOrEmpty(anagrafeSIGePro.TITOLO))
				anagrafeSIGePro.TITOLO = anagrafeNuova.TITOLO;
			if (String.IsNullOrEmpty(anagrafeSIGePro.CODICEELENCOPRO))
				anagrafeSIGePro.CODICEELENCOPRO = anagrafeNuova.CODICEELENCOPRO;
			if (String.IsNullOrEmpty(anagrafeSIGePro.NUMEROELENCOPRO))
				anagrafeSIGePro.NUMEROELENCOPRO = anagrafeNuova.NUMEROELENCOPRO;
			if (String.IsNullOrEmpty(anagrafeSIGePro.PROVINCIAELENCOPRO))
				anagrafeSIGePro.PROVINCIAELENCOPRO = anagrafeNuova.PROVINCIAELENCOPRO;
		}

		public void SalvaPresenzeStoriche(Anagrafe cls, bool InsertAnagrafe)
		{
			Anagrafe anagrafe = null;
			bool inserisciPresenze = false;

			//cerca l'anagrafica e se la trova la aggiorna in sigepro
			try
			{
				anagrafe = Extract(cls, false, false);

				if (string.IsNullOrEmpty(anagrafe.CODICEANAGRAFE) && InsertAnagrafe)
				{
					//Se l'anagrafica non è stata trovata viene inserita ( con le relative presenze storiche ).
					anagrafe = Insert(cls);
				}
				else
				{
					//L'anagrafica esiste, vanno aggiornate solamente le presenze
					inserisciPresenze = true;
				}
			}
			catch (Init.SIGePro.Exceptions.Anagrafe.OmonimiaExceptionWarning oew)
			{
				if (ForzaInserimentoAnagrafe)
				{
					Anagrafe anagDisabilitata = (cls.Clone() as Anagrafe);
					anagDisabilitata.FLAG_DISABILITATO = "1";
					anagDisabilitata.DATA_DISABILITATO = DateTime.Now.Date;

					try
					{
						anagrafe = Extract(cls, false, false);

						if (string.IsNullOrEmpty(anagrafe.CODICEANAGRAFE) && InsertAnagrafe)
						{
							//Se l'anagrafica non è stata trovata viene inserita ( con le relative presenze storiche ).
							anagrafe = Insert(anagDisabilitata);
						}
						else
						{
							//L'anagrafica esiste, vanno aggiornate solamente le presenze
							inserisciPresenze = true;
						}
					}
					catch (Init.SIGePro.Exceptions.Anagrafe.OmonimiaExceptionWarning oew2)
					{
						//esiste un'anagrafica disabilitata ma anche in questo caso alcuni dei dati
						//non corrispondono, l'inserimento dell'anagrafica disabilitata viene forzato
						//( Vengono inserite anche le relative presenze storiche ).
						anagrafe = Insert(anagDisabilitata, false);
					}
				}
				else
				{
					throw oew;
				}
			}

			//l'anagrafica è stata trovata ( ATTENZIONE: TROVATA NON INSERITA!! ), 
			//si può procedere con l'aggiornamento delle presenze
			if (anagrafe != null && inserisciPresenze)
			{
				foreach (MercatiPresenzeStorico mps in cls.PresenzeStoriche)
				{
					mps.Idcomune = anagrafe.IDCOMUNE;
					mps.Codiceanagrafe = Convert.ToInt32(anagrafe.CODICEANAGRAFE);

					MercatiPresenzeStoricoMgr mgr = new MercatiPresenzeStoricoMgr(this.db);
					mgr.EscludiControlliSuAnagraficheDisabilitate = EscludiControlliSuAnagraficheDisabilitate;
					mgr.EscludiVerificaIncongruenze = EscludiVerificaIncongruenze;
					mgr.ForzaInserimentoAnagrafe = false;
					mgr.InsertAnagrafe = false;
					mgr.RicercaSoloCF_PIVA = RicercaSoloCF_PIVA;
					mgr.UpdateAnagrafe = false;
					mgr.Insert(mps);
				}
			}
		}

		public Anagrafe GetByUserId(string idComune, string userId)
		{

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var sql = @"select 
								* 
							from 
								anagrafe 
							where 
								idcomune={0} and " +
								db.Specifics.NvlFunction( "FLAG_DISABILITATO" , "0") + " = 0 and ( " + 
									db.Specifics.UCaseFunction( "CODICEFISCALE") + " = {1} or " + 
									db.Specifics.UCaseFunction( "STRONG_AUTH_ID" ) + " = {2} )";

				sql = PreparaQueryParametrica(sql, "idComune", "codicefiscale", "authId");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("codicefiscale", userId.ToUpper()));
					cmd.Parameters.Add(db.CreateParameter("authId", userId.ToUpper()));

					return db.GetClass<Anagrafe>(cmd);
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}

		}


		#region IAnagrafeManager Members

		public IClasseContestoModelloDinamico LeggiAnagrafica(string idComune, int codiceAnagrafe)
		{
			return GetById(idComune, codiceAnagrafe);
		}

		#endregion
	}
}
