using System;
using System.Data;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Ninject;
using log4net;
using System.Text.RegularExpressions;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;


namespace Init.Sigepro.FrontEnd.AppLogic.Adapters
{
	/// <summary>
	/// Classe che permette di convertire una classe Anagrafe in vari formati
	/// </summary>
	public class AnagrafeAdapter
	{
		ILog _log = LogManager.GetLogger(typeof(AnagrafeAdapter));

		[Inject]
		public IComuniService _comuniService { get; set; }


		Anagrafe _anagraficaAreaRiservata;

		public AnagrafeAdapter(Anagrafe anagraficaAreaRiservata)
		{
			FoKernelContainer.Inject(this);

			_anagraficaAreaRiservata = anagraficaAreaRiservata;
		}

		/// <summary>
		/// Adatta l'anagrafica nel formato utilizzato dal dataset della presentazione istanze
		/// </summary>
		/// <returns>riga popolata</returns>
		public PresentazioneIstanzaDbV2.ANAGRAFERow ToAnagrafeRow()
		{
			var anagrafeTable = new PresentazioneIstanzaDbV2.ANAGRAFEDataTable();

			var newRow = (PresentazioneIstanzaDbV2.ANAGRAFERow)anagrafeTable.NewRow();

			newRow.ANAGRAFE_PK = -1;

			if (_anagraficaAreaRiservata == null)
				return null;

			Type anagrafeType = _anagraficaAreaRiservata.GetType();
			foreach (DataColumn dc in newRow.Table.Columns)
			{
				string columnName = dc.ColumnName;

				var propInfo = anagrafeType.GetProperty(columnName);

				if (propInfo != null)
				{
					object value = propInfo.GetValue(_anagraficaAreaRiservata, null);

					if (value == null)
						value = DBNull.Value;
					try
					{
						newRow[columnName] = value;
					}
					catch (Exception ex)
					{
						_log.WarnFormat("Impossibile adattare la proprietà {0} (tipo: {1}, tipo destinazione: {2}) dell'anagrafica con id {3} per il seguente errore: {4}",
										columnName, propInfo.PropertyType, anagrafeTable.Columns[columnName].DataType, _anagraficaAreaRiservata.CODICEANAGRAFE, ex.ToString());
					}
				}
			}
			newRow.PartitaIva = _anagraficaAreaRiservata.PARTITAIVA;
			newRow.IdAlbo = _anagraficaAreaRiservata.CODICEELENCOPRO;
			newRow.NumeroAlbo = _anagraficaAreaRiservata.NUMEROELENCOPRO;
			newRow.ProvinciaAlbo = _anagraficaAreaRiservata.PROVINCIAELENCOPRO;

			//DatiSigepro datiSigepro = new DatiSigepro(aliasComune);

			if (_anagraficaAreaRiservata.TIPOANAGRAFE == "F")
			{
				// in SIGepro la provincia di nascita non è memorizzata...

				if (newRow.IsPROVINCIANASCITANull() || String.IsNullOrEmpty( newRow.PROVINCIANASCITA ) )
				{
					var provincia = _comuniService.GetProvinciaDaCodiceComune(_anagraficaAreaRiservata.CODCOMNASCITA);

					newRow.PROVINCIANASCITA = provincia == null ? String.Empty : provincia.SiglaProvincia;
				}
			}
			else
			{
				/*
				if (_anagraficaAreaRiservata.PARTITAIVA != null && _anagraficaAreaRiservata.PARTITAIVA.Length > 0)
				{
					newRow.CODICEFISCALE = _anagraficaAreaRiservata.PARTITAIVA;
				}
				*/
				if (newRow.IsCODPROVREGDITTENull() || newRow.CODPROVREGDITTE == String.Empty)
				{
					var provincia = _comuniService.GetProvinciaDaCodiceComune(_anagraficaAreaRiservata.CODCOMREGDITTE);

					newRow.CODPROVREGDITTE = provincia == null ? String.Empty : provincia.SiglaProvincia;
				}

				if (newRow.IsCODPROVREGTRIBNull() || newRow.CODPROVREGTRIB == String.Empty)
				{
					var provincia = _comuniService.GetProvinciaDaCodiceComune(_anagraficaAreaRiservata.CODCOMREGTRIB);

					newRow.CODPROVREGTRIB = provincia == null ? String.Empty : provincia.SiglaProvincia;
				}
			}

			return newRow;
		}

		public AnagraficaDomanda ToAnagraficaDomanda()
		{
			return AnagraficaDomanda.FromAnagrafeRow(this.ToAnagrafeRow());
		}



		/// <summary>
		/// Adatta l'anagrafica dal formato utilizzato nell'area riservata al formato utilizzato dal web service di creazione anagrafiche
		/// </summary>
		/// <returns>Anagrafica nel formato del web service di creazione anagrafiche</returns>
		public Init.Sigepro.FrontEnd.AppLogic.CreazioneAnagrafeService.AnagrafeType ToAnagrafeType()
		{
			var rVal = new Init.Sigepro.FrontEnd.AppLogic.CreazioneAnagrafeService.AnagrafeType();

			rVal.codiceFiscale = _anagraficaAreaRiservata.CODICEFISCALE;
			rVal.sesso = _anagraficaAreaRiservata.SESSO == "M" ?
							Init.Sigepro.FrontEnd.AppLogic.CreazioneAnagrafeService.AnagrafeTypeSesso.M :
							Init.Sigepro.FrontEnd.AppLogic.CreazioneAnagrafeService.AnagrafeTypeSesso.F;

			rVal.cognome = _anagraficaAreaRiservata.NOMINATIVO;
			rVal.comuneNascita = AdattaComune(_anagraficaAreaRiservata.CODCOMNASCITA);

			if (_anagraficaAreaRiservata.DATANASCITA.HasValue)
			{
				rVal.dataNascita = _anagraficaAreaRiservata.DATANASCITA.Value;
				rVal.dataNascitaSpecified = true;
			}

			if (!String.IsNullOrEmpty(_anagraficaAreaRiservata.CAP) ||
				!String.IsNullOrEmpty(_anagraficaAreaRiservata.INDIRIZZO) ||
				!String.IsNullOrEmpty(_anagraficaAreaRiservata.COMUNERESIDENZA) ||
				!String.IsNullOrEmpty(_anagraficaAreaRiservata.CITTA))
			{
				rVal.residenza = new Init.Sigepro.FrontEnd.AppLogic.CreazioneAnagrafeService.LocalizzazioneType
				{
					cap = _anagraficaAreaRiservata.CAP,
					indirizzo = String.IsNullOrEmpty(_anagraficaAreaRiservata.INDIRIZZO) ? String.Empty : _anagraficaAreaRiservata.INDIRIZZO,
					comune = AdattaComune(_anagraficaAreaRiservata.COMUNERESIDENZA),
					localita = _anagraficaAreaRiservata.CITTA
				};
			}

			if (!String.IsNullOrEmpty(_anagraficaAreaRiservata.CAPCORRISPONDENZA) ||
				!String.IsNullOrEmpty(_anagraficaAreaRiservata.INDIRIZZOCORRISPONDENZA) ||
				!String.IsNullOrEmpty(_anagraficaAreaRiservata.COMUNECORRISPONDENZA) ||
				!String.IsNullOrEmpty(_anagraficaAreaRiservata.CITTACORRISPONDENZA))
			{
				rVal.corrispondenza = new Init.Sigepro.FrontEnd.AppLogic.CreazioneAnagrafeService.LocalizzazioneType
				{
					cap = _anagraficaAreaRiservata.CAPCORRISPONDENZA,
					indirizzo = String.IsNullOrEmpty(_anagraficaAreaRiservata.INDIRIZZOCORRISPONDENZA) ? String.Empty : _anagraficaAreaRiservata.INDIRIZZOCORRISPONDENZA,
					comune = AdattaComune(_anagraficaAreaRiservata.COMUNECORRISPONDENZA),
					localita = _anagraficaAreaRiservata.CITTACORRISPONDENZA
				};
			}


			rVal.email = _anagraficaAreaRiservata.EMAIL;
			rVal.fax = _anagraficaAreaRiservata.FAX;
			rVal.nome = _anagraficaAreaRiservata.NOME;
			rVal.partitaIva = _anagraficaAreaRiservata.PARTITAIVA;
			rVal.pec = _anagraficaAreaRiservata.Pec;
			rVal.strongAuthId = _anagraficaAreaRiservata.Username;
			rVal.tecnico = false;
			rVal.telefono = _anagraficaAreaRiservata.TELEFONO;
			//rVal.cellulare = _anagraficaAreaRiservata.TELEFONOCELLULARE;

			return rVal;
		}

		/// <summary>
		/// Adatta l'anagrafica dal formato utilizzato nell'area riservata al formato utilizzato per gestire i dati dell'utente
		/// </summary>
		/// <returns>Anagrafica nel formato del web service di creazione anagrafiche</returns>
		public AnagraficaUtente ToAnagraficaUtente()
		{
			if (_anagraficaAreaRiservata == null)
				return null;

			AnagraficaUtente rVal = new AnagraficaUtente();

			if (!String.IsNullOrEmpty(_anagraficaAreaRiservata.CODICEANAGRAFE))
				rVal.Codiceanagrafe = Convert.ToInt32(_anagraficaAreaRiservata.CODICEANAGRAFE);

			rVal.Idcomune = _anagraficaAreaRiservata.IDCOMUNE;
			rVal.Nominativo = _anagraficaAreaRiservata.NOMINATIVO;

			if (!String.IsNullOrEmpty(_anagraficaAreaRiservata.FORMAGIURIDICA))
				rVal.Formagiuridica = Convert.ToInt32(_anagraficaAreaRiservata.FORMAGIURIDICA);

			if (!String.IsNullOrEmpty(_anagraficaAreaRiservata.TIPOLOGIA))
				rVal.Tipologia = Convert.ToInt32(_anagraficaAreaRiservata.TIPOLOGIA);

			rVal.Indirizzo = _anagraficaAreaRiservata.INDIRIZZO;
			rVal.Citta = _anagraficaAreaRiservata.CITTA;
			rVal.Cap = _anagraficaAreaRiservata.CAP;
			rVal.Provincia = _anagraficaAreaRiservata.PROVINCIA;
			rVal.Telefono = _anagraficaAreaRiservata.TELEFONO;
			rVal.Telefonocellulare = _anagraficaAreaRiservata.TELEFONOCELLULARE;
			rVal.Fax = _anagraficaAreaRiservata.FAX;
			rVal.Partitaiva = _anagraficaAreaRiservata.PARTITAIVA;
			rVal.Codicefiscale = _anagraficaAreaRiservata.CODICEFISCALE;
			rVal.Note = _anagraficaAreaRiservata.NOTE;
			rVal.Email = _anagraficaAreaRiservata.EMAIL;
			rVal.Regditte = _anagraficaAreaRiservata.REGDITTE;
			rVal.Regtrib = _anagraficaAreaRiservata.REGTRIB;
			rVal.Codcomregditte = _anagraficaAreaRiservata.CODCOMREGDITTE;
			rVal.Codcomregtrib = _anagraficaAreaRiservata.CODCOMREGTRIB;
			rVal.Codcomnascita = _anagraficaAreaRiservata.CODCOMNASCITA;
			rVal.Datanascita = _anagraficaAreaRiservata.DATANASCITA;
			rVal.Dataregditte = _anagraficaAreaRiservata.DATAREGDITTE;
			rVal.Dataregtrib = _anagraficaAreaRiservata.DATAREGTRIB;

			if (!String.IsNullOrEmpty(_anagraficaAreaRiservata.INVIOEMAIL))
				rVal.Invioemail = Convert.ToInt32(_anagraficaAreaRiservata.INVIOEMAIL);

			rVal.Sesso = _anagraficaAreaRiservata.SESSO;
			rVal.Nome = _anagraficaAreaRiservata.NOME;

			if (!String.IsNullOrEmpty(_anagraficaAreaRiservata.TITOLO))
				rVal.Titolo = Convert.ToInt32(_anagraficaAreaRiservata.TITOLO);


			rVal.Tipoanagrafe = _anagraficaAreaRiservata.TIPOANAGRAFE;
			rVal.Datanominativo = _anagraficaAreaRiservata.DATANOMINATIVO;

			if (!String.IsNullOrEmpty(_anagraficaAreaRiservata.INVIOEMAILTEC))
				rVal.Invioemailtec = Convert.ToInt32(_anagraficaAreaRiservata.INVIOEMAILTEC);



			if (!String.IsNullOrEmpty(_anagraficaAreaRiservata.CODICECITTADINANZA))
				rVal.Codicecittadinanza = Convert.ToInt32(_anagraficaAreaRiservata.CODICECITTADINANZA);


			rVal.Comuneresidenza = _anagraficaAreaRiservata.COMUNERESIDENZA;
			rVal.Password = _anagraficaAreaRiservata.PASSWORD;
			rVal.Indirizzocorrispondenza = _anagraficaAreaRiservata.INDIRIZZOCORRISPONDENZA;
			rVal.Cittacorrispondenza = _anagraficaAreaRiservata.CITTACORRISPONDENZA;
			rVal.Capcorrispondenza = _anagraficaAreaRiservata.CAPCORRISPONDENZA;
			rVal.Provinciacorrispondenza = _anagraficaAreaRiservata.PROVINCIACORRISPONDENZA;
			rVal.Comunecorrispondenza = _anagraficaAreaRiservata.COMUNECORRISPONDENZA;
			rVal.Provinciarea = _anagraficaAreaRiservata.PROVINCIAREA;
			rVal.Numiscrrea = _anagraficaAreaRiservata.NUMISCRREA;
			rVal.Dataiscrrea = _anagraficaAreaRiservata.DATAISCRREA;

			if (!String.IsNullOrEmpty(_anagraficaAreaRiservata.FLAG_NOPROFIT))
				rVal.FlagNoprofit = Convert.ToInt32(_anagraficaAreaRiservata.FLAG_NOPROFIT);

			if (!String.IsNullOrEmpty(_anagraficaAreaRiservata.FLAG_DISABILITATO))
				rVal.FlagDisabilitato = Convert.ToInt32(_anagraficaAreaRiservata.FLAG_DISABILITATO);

			rVal.DataDisabilitato = _anagraficaAreaRiservata.DATA_DISABILITATO;

			if (!String.IsNullOrEmpty(_anagraficaAreaRiservata.CODICEELENCOPRO))
				rVal.Codiceelencopro = Convert.ToInt32(_anagraficaAreaRiservata.CODICEELENCOPRO);


			rVal.Numeroelencopro = _anagraficaAreaRiservata.NUMEROELENCOPRO;
			rVal.Provinciaelencopro = _anagraficaAreaRiservata.PROVINCIAELENCOPRO;
			rVal.UtenteTester = _anagraficaAreaRiservata.FoUtenteTester.GetValueOrDefault(0) == 1;

			return rVal;
		}

		private static Init.Sigepro.FrontEnd.AppLogic.CreazioneAnagrafeService.ComuneType AdattaComune(string codicecomune)
		{
			if (String.IsNullOrEmpty(codicecomune)) return null;

			var comune = new Init.Sigepro.FrontEnd.AppLogic.CreazioneAnagrafeService.ComuneType();
			
			// A seconda del tipo di registrazione che si sta effettuando (authGateway, fed, federa etc...)
			// il valore di codiceComune potrebbe essere 
			// - il codice belfiore
			// - il codice istat
			// - il nome del comune
			// le regexp servono ad identificare il tipo di dato che sta arrivando

			comune.ItemElementName = Init.Sigepro.FrontEnd.AppLogic.CreazioneAnagrafeService.ItemChoiceType.comune;

			if (Regex.IsMatch(codicecomune, @"^\w\d{3}$"))
			{
				comune.ItemElementName = Init.Sigepro.FrontEnd.AppLogic.CreazioneAnagrafeService.ItemChoiceType.codiceCatastale;
			}
			else if (Regex.IsMatch(codicecomune, @"^\d{6}$"))
			{
				comune.ItemElementName = Init.Sigepro.FrontEnd.AppLogic.CreazioneAnagrafeService.ItemChoiceType.codiceIstat;
			}

			comune.Item = codicecomune;

			return comune;
		}
	}
}
