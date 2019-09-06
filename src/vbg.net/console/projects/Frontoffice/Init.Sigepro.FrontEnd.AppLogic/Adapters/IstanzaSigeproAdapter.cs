using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Xml.Serialization;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TraduzioneIdComune;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Utils;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
//using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters
{
	public partial class IstanzaSigeproAdapter
	{
		//[Inject]
		//public IInterventiRepository _alberoprocRepository { get; set; }

		[Inject]
		public IComuniService _comuniService { get; set; }
		[Inject]
		public ICittadinanzeService _cittadinanzeService { get; set; }

		[Inject]
		public IFormeGiuridicheRepository _formeGiuridicheService { get; set; }

		[Inject]
		public IConfigurazioneAreaRiservataRepository _configurazioneAreaRiservataRepository { get; set; }

		[Inject]
		public IConfigurazioneVbgRepository _configurazioneVbgRepository { get; set; }

		[Inject]
		public IStatiIstanzaRepository _statiIstanzaRepository { get; set; }

		[Inject]
		public IStradarioRepository _stradarioRepository { get; set; }

		[Inject]
		public IAliasToIdComuneTranslator _aliasToIdComuneTranslator { get; set; }
        [Inject]
        public IConfigurazione<ParametriAllegati> _parametriAllegati { get; set; }



		string _idComuneTradotto;
		IDomandaOnlineReadInterface _readInterface;
		Istanze _istanzaAdattata;

		public bool AggiungiPdfSchedeAListaAllegati { get; set; }



        public IstanzaSigeproAdapter(IDomandaOnlineReadInterface readInterface, bool aggiungiPdfSchedeAListaAllegati)
            :this(readInterface)
        {
            this.AggiungiPdfSchedeAListaAllegati = aggiungiPdfSchedeAListaAllegati;
        }

		public IstanzaSigeproAdapter(IDomandaOnlineReadInterface readInterface)
		{
			FoKernelContainer.Inject(this);

			AggiungiPdfSchedeAListaAllegati = true;

			this._readInterface = readInterface;
			this._idComuneTradotto = _aliasToIdComuneTranslator.Translate(_readInterface.AltriDati.AliasComune);
		}



		public Istanze Adatta()
		{
			if (_istanzaAdattata != null)
				return _istanzaAdattata;

			var aliasComune = _readInterface.AltriDati.AliasComune;
			var software = _readInterface.AltriDati.Software;
			

			Istanze istanzaSigepro = new Istanze();

			istanzaSigepro.IDCOMUNE = _idComuneTradotto;
			istanzaSigepro.SOFTWARE = software;

			if (istanzaSigepro.SOFTWARE == "SU")
				istanzaSigepro.CODICEINTERVENTO = _readInterface.AltriDati.Intervento.Codice.ToString();
			else
				istanzaSigepro.CODICEINTERVENTOPROC = _readInterface.AltriDati.Intervento.Codice.ToString();

			// Risolvo la descrizione dell'intervento
			istanzaSigepro.Intervento = new Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService.AlberoProc
			{
				SC_DESCRIZIONE = _readInterface.AltriDati.Intervento.Descrizione
			};
			

			istanzaSigepro.CODICECOMUNE = _readInterface.AltriDati.CodiceComune;

			var com = _comuniService.GetDatiComune( istanzaSigepro.CODICECOMUNE );

			if (com != null)
			{
				istanzaSigepro.ComuneIstanza = new Comuni
				{
					CODICECOMUNE = com.CodiceComune,
					COMUNE = com.Comune,
					PROVINCIA = com.Provincia,
					SIGLAPROVINCIA = com.SiglaProvincia
				};
			}


			istanzaSigepro.LAVORIESTESA = _readInterface.AltriDati.Note;
			istanzaSigepro.LAVORI		= _readInterface.AltriDati.DescrizioneLavori;

			istanzaSigepro.DATA = DateTime.Now.Date;
            /*
			istanzaSigepro.CHIUSURA	= _configurazioneAreaRiservataRepository.DatiConfigurazione(aliasComune, software).StatoInizialeIstanza;

			if (!String.IsNullOrEmpty(istanzaSigepro.CHIUSURA))
			{
				istanzaSigepro.Stato = new StatiIstanza
				{
					Codicestato = istanzaSigepro.CHIUSURA,
					Stato = _statiIstanzaRepository.GetById(aliasComune, software, istanzaSigepro.CHIUSURA).Stato
				};
			};
            */
			istanzaSigepro.Richiedente = AdattaAnagrafe(_readInterface.Anagrafiche.GetRichiedente());
			istanzaSigepro.AziendaRichiedente = AdattaAnagrafe(_readInterface.Anagrafiche.GetAzienda());
			istanzaSigepro.Professionista = AdattaAnagrafe(_readInterface.Anagrafiche.GetTecnico());
            istanzaSigepro.DOMICILIO_ELETTRONICO = _readInterface.AltriDati.DomicilioElettronico;
            istanzaSigepro.Natura = _readInterface.AltriDati.NaturaBase;

			FillAnagrafiche(istanzaSigepro);
			FillStradario(istanzaSigepro);
			FillProcedimenti(istanzaSigepro);
			FillOneri(istanzaSigepro);
			FillDocumenti(istanzaSigepro);
			FillDatiCatastali(istanzaSigepro);
			FillEventi(istanzaSigepro);
			FillDatiDinamici(istanzaSigepro);

			istanzaSigepro.ConfigurazioneComune = _configurazioneVbgRepository.LeggiConfigurazioneComune(software);
			/*
			using (FileStream fs = File.Open(@"c:\temp\istanzaSerializzata.xml", FileMode.Create))
			{
				XmlSerializer xs = new XmlSerializer(istanzaSigepro.GetType());
				xs.Serialize(fs, istanzaSigepro);
			}
			*/

			_istanzaAdattata = istanzaSigepro;

			return _istanzaAdattata;
		}

		private void FillDatiDinamici(Istanze istanzaSigepro)
		{
			istanzaSigepro.IstanzeDyn2ModelliT = _readInterface.DatiDinamici
																.Modelli
																.Where(x => x.Compilato)
																.Select(x => new IstanzeDyn2ModelliT
																{
																	FkD2mtId = x.IdModello
																})
																.ToArray();

			istanzaSigepro.IstanzeDyn2Dati = _readInterface.DatiDinamici
															.DatiDinamici
															.Select(foDato => new IstanzeDyn2Dati
															{
																FkD2cId = foDato.IdCampo,
																Valore = foDato.Valore,
																Valoredecodificato = foDato.ValoreDecodificato,
																IndiceMolteplicita = foDato.IndiceMolteplicita,
																Indice = 0,
																CampoDinamico = new Dyn2Campi
																{
																	Nomecampo = foDato.NomeCampo
																}
															})
															.ToArray();
		}

		private void FillEventi(Istanze istanzaSigepro)
		{
			istanzaSigepro.Eventi = _readInterface.AltriDati
													.Eventi
													.Select(x => new IstanzeEventi
													{
														Data = x.Data,
														Fkidcategoriaevento = x.Codice,
														Descrizione = x.Descrizione
													})
													.ToArray();
		}


		public static string ConvertiIstanzaPerCompilazioneModello(Istanze istanza)
		{
			using (MemoryStream ms = new MemoryStream())
			{
				//XmlTextWriter swXml = new XmlTextWriter(ms, Encoding.UTF8);

				XmlSerializer xs = new XmlSerializer(istanza.GetType());
				xs.Serialize(ms, istanza);

				string xml = StreamUtils.StreamToString(ms);
				xml = xml.Replace("xmlns=\"http://init.sigepro.it\"", "");

				return xml;
			}
		}

		public string AdattaToString(string codiceDomandaFo)
		{
			Istanze istanza = Adatta();

			istanza.NUMEROISTANZA = codiceDomandaFo;

			return ConvertiIstanzaPerCompilazioneModello(istanza);
		}

		private string GetStatoDefault()
		{
			throw new Exception("The method or operation is not implemented.");
		}

		private void FillDatiCatastali(Istanze ist)
		{
			ist.Mappali = _readInterface.Localizzazioni
										.Indirizzi
										.SelectMany( x=>x.RiferimentiCatastali)
										.Select(x => new IstanzeMappali
										{
											Fkcodiceistanza = int.MinValue,
											Idmappale		= int.MinValue,
											Codicecatasto	= x.TipoCatasto == RiferimentoCatastale.TipoCatastoEnum.Terreni ? "T" : "F",
											Foglio			= x.Foglio,
											Particella		= x.Particella,
											Sub				= x.Sub
										})
										.ToArray();
		}
		
		private void FillAnagrafiche(Istanze ist)
		{

			List<IstanzeRichiedenti> istRichiedenti = new List<IstanzeRichiedenti>();

			foreach (var row in _readInterface.Anagrafiche.Anagrafiche)
			{
				var ric = new IstanzeRichiedenti
				{
					Richiedente = AdattaAnagrafe(row),
					CODICETIPOSOGGETTO = row.TipoSoggetto.Id.ToString(),
					DESCRSOGGETTO = String.IsNullOrEmpty(row.TipoSoggetto.DescrizioneEstesa) ? row.TipoSoggetto.Descrizione : row.TipoSoggetto.DescrizioneEstesa,

					TipoSoggetto = new TipiSoggetto
					{
						CODICETIPOSOGGETTO = row.TipoSoggetto.Id.ToString(),
						TIPOSOGGETTO = row.TipoSoggetto.Descrizione,
						TIPODATO = row.TipoSoggetto.RuoloAsCodiceBackoffice()
					}
				};


				if (row.IdAnagraficaCollegata.HasValue)
				{
					var anagrafeCollegata = _readInterface.Anagrafiche.GetById(row.IdAnagraficaCollegata.Value);

					if (anagrafeCollegata != null)
						ric.AnagrafeCollegata = AdattaAnagrafe(anagrafeCollegata);
				}

                string codAventeProcura = String.Empty;

                if (ric.Richiedente.TIPOANAGRAFE == "F")
                {
                    codAventeProcura = _readInterface.Procure.GetCodiceFiscaleDelProcuratoreDi(row.Codicefiscale);
                }

				if (!String.IsNullOrEmpty(codAventeProcura))
				{
					var procuratore = _readInterface.Anagrafiche.FindByRiferimentiSoggetto(GestionePresentazioneDomanda.GestioneAnagrafiche.TipoPersonaEnum.Fisica,codAventeProcura);

					if (procuratore != null)
						ric.Procuratore = AdattaAnagrafe(procuratore);

                    var procura = _readInterface
                                             .Procure
                                             .Procure
                                             .Where(x => x.Procuratore != null && x.Procuratore.CodiceFiscale == codAventeProcura && x.Procurato.CodiceFiscale == row.Codicefiscale)
                                             .FirstOrDefault();

                    if (procura != null && procura.Allegato != null)
                    {
                        ric.CodiceoggettoProcura = procura.Allegato.CodiceOggetto;
                        ric.OggettoProcura = new Oggetti
                        {
                            IDCOMUNE = _idComuneTradotto,
                            CODICEOGGETTO = procura.Allegato.CodiceOggetto.ToString(),
                            NOMEFILE = procura.Allegato.NomeFile
                        };
                    }

				}

				istRichiedenti.Add(ric);
			}

			ist.Richiedenti = istRichiedenti.ToArray();
		}

		private Anagrafe AdattaAnagrafe(AnagraficaDomanda row)
		{
			if (row == null) return null;

			Anagrafe ret = new Anagrafe();
			ret.IDCOMUNE = _idComuneTradotto;

			ret.NOME = row.Nome;
			ret.NOMINATIVO = row.Nominativo;
			ret.NOTE = row.Note;
			ret.DATANASCITA = row.DatiNascita.Data;
			ret.DATANOMINATIVO = row.DataCostituzione;
			ret.SESSO = row.Sesso == SessoEnum.Maschio ? "M" : "F";
			ret.TIPOANAGRAFE = row.TipoPersona == TipoPersonaEnum.Fisica ? "F" : "G";
			ret.TITOLO = row.IdTitolo.ToString();

			// Residenza
			ret.CAP = row.IndirizzoResidenza.Cap;
			ret.CITTA = row.IndirizzoResidenza.Citta;
			ret.COMUNERESIDENZA = row.IndirizzoResidenza.CodiceComune;
			ret.INDIRIZZO = row.IndirizzoResidenza.Via;
			ret.PROVINCIA = String.Empty;

			var comuneResidenza = _comuniService.GetDatiComune( ret.COMUNERESIDENZA);

			if (comuneResidenza != null)
			{
				ret.PROVINCIA = comuneResidenza.SiglaProvincia;
				ret.ComuneResidenza = new Comuni
				{
					COMUNE = comuneResidenza.Comune + "(" + comuneResidenza.SiglaProvincia + ")"
				};
			}

			// Corrispondenza
			ret.CAPCORRISPONDENZA = row.IndirizzoCorrispondenza.Cap;
			ret.CITTACORRISPONDENZA = row.IndirizzoCorrispondenza.Citta;
			ret.COMUNECORRISPONDENZA = row.IndirizzoCorrispondenza.CodiceComune;
			ret.INDIRIZZOCORRISPONDENZA = row.IndirizzoCorrispondenza.Via;
			ret.PROVINCIACORRISPONDENZA = String.Empty;

			var comuneCorrispondenza = _comuniService.GetDatiComune( ret.COMUNECORRISPONDENZA);

			if (comuneCorrispondenza != null)
			{
				ret.PROVINCIACORRISPONDENZA = comuneCorrispondenza.SiglaProvincia;
				ret.ComuneCorrispondenza = new Comuni
				{
					COMUNE = comuneCorrispondenza.Comune + "(" + comuneCorrispondenza.SiglaProvincia + ")"
				};
			}


			ret.CODCOMNASCITA = row.DatiNascita.CodiceComune;

			ret.CODCOMREGTRIB = row.DatiIscrizioneRegTrib == null ? String.Empty : row.DatiIscrizioneRegTrib.CodiceComune;

			if (!String.IsNullOrEmpty(ret.CODCOMNASCITA))
			{
				var comuneNascita = _comuniService.GetDatiComune( ret.CODCOMNASCITA);

				ret.ComuneNascita = new Comuni();

				if (comuneNascita != null)
					ret.ComuneNascita.COMUNE = comuneNascita.Comune + "(" + comuneNascita.SiglaProvincia + ")";
			}

			if (row.IdCittadinanza.HasValue)
			{
				var datiCittadinanza = _cittadinanzeService.GetCittadinanzaDaId( row.IdCittadinanza.Value );

				if (datiCittadinanza != null)
				{
					ret.Cittadinanza = new Cittadinanza
					{
						Codice = datiCittadinanza.Codice,
						Descrizione = datiCittadinanza.Descrizione
					};
					ret.CODICECITTADINANZA = row.IdCittadinanza.ToString();
				}
			}

			ret.CODICEFISCALE = row.Codicefiscale;
			ret.PARTITAIVA = row.PartitaIva;

			// Iscrizione rea
			ret.DATAISCRREA = null;
			ret.NUMISCRREA = String.Empty;
			ret.PROVINCIAREA = String.Empty;

			if (row.DatiIscrizioneRea != null)
			{
				ret.DATAISCRREA = row.DatiIscrizioneRea.Data;
				ret.NUMISCRREA = row.DatiIscrizioneRea.Numero;
				ret.PROVINCIAREA = row.DatiIscrizioneRea.SiglaProvincia;
			}

			// Iscrizione reg trib
			ret.DATAREGTRIB = null;
			ret.REGTRIB = String.Empty;

			if (row.DatiIscrizioneRegTrib != null)
			{
				ret.DATAREGTRIB = row.DatiIscrizioneRegTrib.Data;
				ret.REGTRIB = row.DatiIscrizioneRegTrib.Numero;
			}

			// Iscrizione CCIAA
			ret.CODCOMREGDITTE = String.Empty;
			ret.DATAREGDITTE = null;
			ret.REGDITTE = String.Empty;

			if (row.DatiIscrizioneCciaa != null)
			{
				ret.CODCOMREGDITTE = row.DatiIscrizioneCciaa.CodiceComune;
				ret.DATAREGDITTE = row.DatiIscrizioneCciaa.Data;
				ret.REGDITTE = row.DatiIscrizioneCciaa.Numero;

				var comuneRegDitte = _comuniService.GetDatiComune(ret.CODCOMREGDITTE);

				if (comuneRegDitte != null)
				{
					ret.ComuneRegDitte = new Comuni
					{
						COMUNE = String.Format("{0} ({1})", comuneRegDitte.Comune, comuneRegDitte.SiglaProvincia)
					};
				}
			}



			// Contatti
			ret.EMAIL = row.Contatti.Email;
			ret.FAX = row.Contatti.Fax;
            ret.Pec = row.Contatti.Pec;
			ret.TELEFONO = row.Contatti.Telefono;
			ret.TELEFONOCELLULARE = row.Contatti.TelefonoCellulare;
            ret.Pec = row.Contatti.Pec;



			// Forma giuridica
			ret.FORMAGIURIDICA = row.IdFormagiuridica.HasValue ? row.IdFormagiuridica.ToString() : String.Empty;
			
			if(!String.IsNullOrEmpty(ret.FORMAGIURIDICA))
			{
				var fg = _formeGiuridicheService.GetById(ret.FORMAGIURIDICA);

				if (fg != null)
				{
					ret.FormaGiuridicaClass = new FormeGiuridiche
					{
						FORMAGIURIDICA = fg.FORMAGIURIDICA
					};
				};
			}

			if (row.DatiIscrizioneAlboProfessionale != null)
			{
				ret.CODICEELENCOPRO = row.DatiIscrizioneAlboProfessionale.IdAlbo.ToString();
				ret.PROVINCIAELENCOPRO = row.DatiIscrizioneAlboProfessionale.SiglaProvincia;
				ret.NUMEROELENCOPRO = row.DatiIscrizioneAlboProfessionale.Numero;

				ret.ElencoProfessionale = new ElenchiProfessionaliBase
				{
					EpId = row.DatiIscrizioneAlboProfessionale.IdAlbo,
					EpDescrizione = row.DatiIscrizioneAlboProfessionale.Descrizione
				};

			}

            if (row.DatiIscrizioneInps != null)
            {
                ret.InpsMatricola = row.DatiIscrizioneInps.Matricola;
                ret.InpsCodiceSede = row.DatiIscrizioneInps.CodiceSede;
                ret.SedeInps = new ElencoInpsBase
                {
                    Codice = row.DatiIscrizioneInps.CodiceSede,
                    Descrizione = row.DatiIscrizioneInps.DescrizioneSede
                };
            }

            if (row.DatiIscrizioneInail != null)
            {
                ret.InailMatricola = row.DatiIscrizioneInail.Matricola;
                ret.InailCodiceSede = row.DatiIscrizioneInail.CodiceSede;
                ret.SedeInail = new ElencoInailBase
                {
                    Codice = row.DatiIscrizioneInail.CodiceSede,
                    Descrizione = row.DatiIscrizioneInail.DescrizioneSede
                };
            }


			return ret;
		}


		private void FillStradario(Istanze ist)
		{
			var aliasComune = _readInterface.AltriDati.AliasComune;

			var stradarioList = new List<IstanzeStradario>();

			foreach(var indirizzo in _readInterface.Localizzazioni.Indirizzi)
			{
				var codStradario = indirizzo.CodiceStradario;

				var rigaStradario = _stradarioRepository.GetByCodiceStradario(aliasComune, codStradario);

				if (rigaStradario == null)
				{
					continue;
				}

				var str = new IstanzeStradario
				{
					CODICESTRADARIO		= codStradario.ToString(),
					CIVICO				= indirizzo.Civico,
					ESPONENTE			= indirizzo.Esponente,
					COLORE				= indirizzo.Colore,
					SCALA				= indirizzo.Scala,
					INTERNO				= indirizzo.Interno,
					ESPONENTEINTERNO	= indirizzo.EsponenteInterno,
					Piano				= indirizzo.Piano,
					NOTE				= indirizzo.Note,

					Stradario = new Stradario
					{
						PREFISSO = rigaStradario.PREFISSO,
						CODICESTRADARIO = codStradario.ToString(),
						DESCRIZIONE = indirizzo.Indirizzo,//rigaStradario.DESCRIZIONE,
						LOCFRAZ = rigaStradario.LOCFRAZ,
						CAP = rigaStradario.CAP,
                        CODVIARIO = rigaStradario.CODVIARIO
					}

				};

                if(rigaStradario.ComuneLocalizzazione != null)
                {
                    str.ComuneLocalizzazione = new Comuni
                    {
                        COMUNE = rigaStradario.ComuneLocalizzazione.COMUNE,
                        PROVINCIA = rigaStradario.ComuneLocalizzazione.PROVINCIA,
                        CODICECOMUNE = rigaStradario.ComuneLocalizzazione.CODICECOMUNE,
                        SIGLAPROVINCIA = rigaStradario.ComuneLocalizzazione.SIGLAPROVINCIA
                    };
                }
                

				stradarioList.Add(str);
			}

			ist.Stradario = stradarioList.ToArray();
		}

		private void FillProcedimenti(Istanze ist)
		{
			var listaEndo = new List<IstanzeProcedimenti>();

			foreach(var endo in _readInterface.Endoprocedimenti.Endoprocedimenti)
			{
				var pro = new IstanzeProcedimenti
				{
					CODICEINVENTARIO = endo.Codice.ToString(),
					DATAATTIVAZIONE = DateTime.Now.Date,
					Endoprocedimento = new InventarioProcedimenti
					{
						Codiceinventario = endo.Codice,
						Procedimento = endo.Descrizione,
					},
					IstanzeAllegati = _readInterface
										.Documenti
										.Endo
										.GetByIdEndo(endo.Codice)
										.Where(x => x.AllegatoDellUtente != null)
										.Select(x => new IstanzeAllegati
										{
											PRESENTE = "1",
											ALLEGATOEXTRA = x.Descrizione,
											CODICEINVENTARIO = endo.Codice.ToString(),
											CODICEOGGETTO = x.AllegatoDellUtente.CodiceOggetto.ToString(),
											Oggetto = new IstanzeAllegatiOggetto
											{
												NOMEFILE = x.AllegatoDellUtente.NomeFile
											}
										})
										.Union(
											_readInterface
												.RiepiloghiSchedeDinamiche
												.GetByCodiceEndo(endo.Codice)
												.Where(x => x.AllegatoDellUtente != null)
												.Select(x => new IstanzeAllegati
												{
													PRESENTE = "1",
													ALLEGATOEXTRA = x.Descrizione,
													CODICEINVENTARIO = endo.Codice.ToString(),
													CODICEOGGETTO = x.AllegatoDellUtente.CodiceOggetto.ToString(),
													Oggetto = new IstanzeAllegatiOggetto
													{
														NOMEFILE = x.AllegatoDellUtente.NomeFile
													}
												})
										)
										.Union(
											_readInterface
												.Endoprocedimenti
												.Acquisiti
												.Where(x => x.Codice == endo.Codice && endo.Riferimenti.Allegato != null)
												.Select(x => new IstanzeAllegati
												{
													PRESENTE = "1",
													ALLEGATOEXTRA = x.Descrizione,
													CODICEINVENTARIO = x.Codice.ToString(),
													CODICEOGGETTO = x.Riferimenti.Allegato.CodiceOggetto.ToString(),
													Oggetto = new IstanzeAllegatiOggetto
													{
														NOMEFILE = x.Riferimenti.Allegato.NomeFile
													}

												})
										)
										.ToArray()
				};

                if (endo.Riferimenti != null)
                {
                    pro.PROT_NUM = endo.Riferimenti.NumeroAtto;
                    pro.PROT_DEL = endo.Riferimenti.DataAtto;
                    pro.TipoAtto = endo.Riferimenti.TipoTitolo != null ? endo.Riferimenti.TipoTitolo.Descrizione : String.Empty;
                    pro.Note = endo.Riferimenti.Note;
                    pro.RilasciatoDa = endo.Riferimenti.RilasciatoDa;
                }
			
				listaEndo.Add(pro);
			}

			ist.EndoProcedimenti = listaEndo.ToArray();

		}

		private void FillDocumenti(Istanze istanzaOut)
		{
			List<DocumentiIstanza> documenti = new List<DocumentiIstanza>();

			// Delega a trasmettere
			if (_readInterface.DelegaATrasmettere.Allegato != null)
			{
                var descrizioneDelega = _parametriAllegati.Parametri.DescrizioneDelegaATrasmettere;
                descrizioneDelega = String.IsNullOrEmpty(descrizioneDelega) ? "Delega a trasmettere" : descrizioneDelega;

                documenti.Add(_readInterface.DelegaATrasmettere.Allegato.ToDocumentiIstanza(descrizioneDelega));
			}

            if (_readInterface.DelegaATrasmettere.DocumentoIdentita != null)
            {
                var descrizione = _parametriAllegati.Parametri.DescrizioneDelegaATrasmettere;

                descrizione = (String.IsNullOrEmpty(descrizione) ? "Delega a trasmettere" : descrizione) + " - documento d'identità";

                documenti.Add(_readInterface.DelegaATrasmettere.DocumentoIdentita.ToDocumentiIstanza(descrizione));
            }

            // Allegati dell'intervento
            documenti.AddRange(_readInterface.Documenti
											  .Intervento
											  .GetAllegatiPresenti()
											  .Select(documento => new DocumentiIstanza
											  {
												  DOCUMENTO = documento.Descrizione,
												  DATA = DateTime.Now,
												  CODICEOGGETTO = documento.AllegatoDellUtente.CodiceOggetto.ToString(),
												  NECESSARIO = documento.Richiesto ? "1" : "0",
												  Oggetto = new DocumentiIstanzaOggetti
												  {
													  NOMEFILE = documento.AllegatoDellUtente.NomeFile
												  }
												  /*
												  // L'allegato fa parte di una categoria?
												  if (!row.IsCategoriaNull() && !String.IsNullOrEmpty(row.CodiceCategoria) && row.CodiceCategoria != "-1")
												  {
													  doc.FKIDCATEGORIA = Convert.ToInt32(row.CodiceCategoria);
													  doc.Categoria = new AlberoProcDocumentiCat();
													  doc.Categoria.Id = Convert.ToInt32(row.CodiceCategoria);
													  doc.Categoria.Descrizione = row.Categoria;
												  }
												  */
											  }));
			

			foreach(var riepilogodd in _readInterface.RiepiloghiSchedeDinamiche.GetRiepiloghiInterventoConAllegatoUtente() )
			{
				var rigaModello = _readInterface.DatiDinamici.Modelli.Where( x => x.IdModello == riepilogodd.IdModello).FirstOrDefault();

				if (rigaModello == null || (rigaModello.TipoFirma == ModelloDinamico.TipoFirmaEnum.Nessuna && !AggiungiPdfSchedeAListaAllegati))
					continue;

				documenti.Add( new DocumentiIstanza
				{
					DOCUMENTO = riepilogodd.Descrizione,
					DATA = DateTime.Now,
					CODICEOGGETTO = riepilogodd.AllegatoDellUtente.CodiceOggetto.ToString(),
					Oggetto = new DocumentiIstanzaOggetti
					{
						NOMEFILE = riepilogodd.AllegatoDellUtente.NomeFile
					}
				} );
			}

			// Se presente aggiungo ila copia del bollettino che attesta l'avvenuto pagamento
			if (_readInterface.Oneri.AttestazioneDiPagamento.Presente)
			{
				documenti.Add(new DocumentiIstanza
				{
					DOCUMENTO = "Copia della ricevuta attestante l'avvenuto pagamento degli oneri",
					DATA = DateTime.Now,
					CODICEOGGETTO = _readInterface.Oneri.AttestazioneDiPagamento.CodiceOggetto.ToString(),
					Oggetto = new DocumentiIstanzaOggetti
					{
						NOMEFILE = _readInterface.Oneri.AttestazioneDiPagamento.NomeFile
					}
				});
			}

			istanzaOut.DocumentiIstanza = documenti.ToArray();
		}


		private void FillOneri(Istanze istanzaOut)
		{
			if (_readInterface.Oneri.Oneri.Count() == 0)
				return;

			istanzaOut.Oneri = _readInterface.Oneri.Oneri
													.Select(x => new IstanzeOneri
													{
														CODICEINVENTARIO	= x.Provenienza == OnereFrontoffice.ProvenienzaOnereEnum.Endoprocedimento ? x.EndoOInterventoOrigine.ToString() : String.Empty,
														PREZZO				= x.Importo,
														FLENTRATAUSCITA		= "1",
														DATA				= DateTime.Now,
														ImportoPagato		= _readInterface.Oneri.AttestazioneDiPagamento.Presente ? 1 : 0,
														CausaleOnere = new TipiCausaliOneri
														{
															CoDescrizione = x.Causale.Descrizione
														}
													})
													.ToArray();
		}
	}
}
