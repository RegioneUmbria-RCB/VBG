using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAltriDati
{
	public class AltriDatiReadInterface : IAltriDatiReadInterface
	{
		private static class Constants
		{
			public const string IdEventoInviata = "AR-SOGGETTOINVIO";
			public const string IdEventoRecapitata = "AR-INVIO";
			public const string IdEventoTrasferita = "AR-TRASFERIMENTO";
			public const string IdEventoTrasferimentoAnnullato = "AR-ANNULLAMENTO";
			public const string IdEventoSottoscritta = "AR-SOTTOS";
			public const string IdEventoNuovoEnte = "AR-NUOVO-ENTE";
			
		}

		PresentazioneIstanzaDbV2 _database;
		DatiIntervento _intervento = null;
		string _denominazioneAttivita = String.Empty;
		string _descrizioneLavori = String.Empty;
		string _note = String.Empty;
		IEnumerable<Evento> _eventi;
		string _domicilioElettronico;
		string _identificativoDomanda;
		int _idPresentazione;
		string _codiceComune = String.Empty;
		string _aliasComune = String.Empty;
		string _software = String.Empty;
		PresentazioneIstanzaDataKey _dataKeyDomanda;
		bool _flagPrivacy;
		int? _attivitaAtecoPrimaria;

		public AltriDatiReadInterface(PresentazioneIstanzaDataKey dataKeyDomanda, PresentazioneIstanzaDbV2 database)
		{
			this._database = database;

			this._dataKeyDomanda		= dataKeyDomanda;
			this._idPresentazione		= dataKeyDomanda.IdPresentazione;
			this._identificativoDomanda = dataKeyDomanda.ToSerializationCode();
			this._aliasComune			= dataKeyDomanda.IdComune;
			this._software				= dataKeyDomanda.Software;

			PreparaDatiIntervento();
			PreparadatiDescrizioneLavori();
			PreparaEventi();
			PreparaDomicilioElettronico();
			PreparaCodiceComune();
			PreparaDatiPrivacy();
			PreparaAttivitaAteco();
		}

		private void PreparaAttivitaAteco()
		{
			if(this._database.AttivitaAteco.Count == 0)
				return;

			var rowPrimaria = this._database.AttivitaAteco.Where( x => x.Primaria ).FirstOrDefault();

			this._attivitaAtecoPrimaria = rowPrimaria.IdAteco;
		}

		private void PreparaDatiPrivacy()
		{
			if (this._database.ISTANZE.Count == 0 || this._database.ISTANZE[0].IsFlgPrivacyNull())
				return;

			this._flagPrivacy = this._database.ISTANZE[0].FlgPrivacy;
		}

		private void PreparaCodiceComune()
		{
			if (this._database.ISTANZE.Count == 0)
				return;

			this._codiceComune = this._database.ISTANZE[0].CODICECOMUNE;
		}

		private void PreparaDomicilioElettronico()
		{
			if (this._database.ISTANZE.Count == 0)
				return;

			this._domicilioElettronico = this._database.ISTANZE[0].IndirizzoDomicilioEletronico;
		}

		private void PreparaEventi()
		{
			// Gli eventi non sono più utilizzati (servivano per la gestione dei trasferimenti)
			// Per compatibilità vengono solamente mantenuti gli eventi 
			// di invio istanza e trasferimento al comune
			var codiceEvento1 = Constants.IdEventoInviata;
			var testoEvento1  = String.Format("{0} in data {1} alle ore {2} ha trasferito l'istanza al comune",
												GetNominativoUtenteCheEffettuaInvio(),
												DateTime.Now.ToString("dd/MM/yyyy"),
												DateTime.Now.ToString("HH:mm"));

			var codiceEvento2 = Constants.IdEventoRecapitata;
			var testoEvento2  = String.Format("In data {0} alle ore {1} l'istanza e' stata trasferita al comune",
												DateTime.Now.ToString("dd/MM/yyyy"),
												DateTime.Now.ToString("HH:mm"));

			var listaEventi = new List<Evento>
			{
				new Evento( codiceEvento1 , testoEvento1 , DateTime.Now ),
				new Evento( codiceEvento2 , testoEvento2 , DateTime.Now )
			};


			if (_database.AutorizzazioniMercati.Count > 0 && String.IsNullOrEmpty(_database.AutorizzazioniMercati[0].CodiceEnte))
			{
				var codice = Constants.IdEventoNuovoEnte;
				var descrizione = String.Format("Durante la compilazione dei dati relativi all'autorizzazione è stato impostato un ente non presente nella lista degli enti locali. Nome Ente: {0}", _database.AutorizzazioniMercati[0].DescrizioneEnte);

				listaEventi.Add(new Evento(codice, descrizione, DateTime.Now));
			}

			this._eventi = listaEventi;
		}

		private string GetNominativoUtenteCheEffettuaInvio()
		{
			var richiedente = this._database.ANAGRAFE
											.Where(x => x.TIPOANAGRAFE == "F" && x.CODICEFISCALE.ToUpperInvariant() == this._dataKeyDomanda.CodiceUtente.ToUpperInvariant())
											.FirstOrDefault();

			if (richiedente == null)
				return String.Empty;

			return richiedente.NOMINATIVO + " " + richiedente.NOME;
		}

		private void PreparadatiDescrizioneLavori()
		{
			if (this._database.ISTANZE.Count == 0)
				return;

			this._denominazioneAttivita = this._database.ISTANZE[0].DENOMINAZIONE_ATTIVITA;
			this._descrizioneLavori		= this._database.ISTANZE[0].OGGETTO;
			this._note					= this._database.ISTANZE[0].NOTE;
		}

		private void PreparaDatiIntervento()
		{
			this._intervento = null;

			if (this._database.ISTANZE.Count == 0 || this._database.ISTANZE[0].IsCODICEINTERVENTONull())
				return;

			var codIntervento = this._database.ISTANZE[0].CODICEINTERVENTO;
			var desIntervento = this._database.ISTANZE[0].DescrizioneEstesaIntervento;

			this._intervento = new DatiIntervento(codIntervento, desIntervento);
		}



		#region IAltriDatiReadInterface Members

		public DatiIntervento Intervento
		{
			get { return this._intervento; }
		}

		public string DescrizioneLavori
		{
			get { return this._descrizioneLavori; }
		}

		public string DenominazioneAttivita
		{
			get { return this._denominazioneAttivita; }
		}

		public string Note
		{
			get { return this._note; }
		}

		public IEnumerable<Evento> Eventi { get { return _eventi; } }

		public string DomicilioElettronico
		{
			get { return this._domicilioElettronico; }
		}

		public string IdentificativoDomanda
		{
			get { return this._identificativoDomanda; }
		}

		public string CodiceComune
		{
			get { return this._codiceComune; }
		}

		public string AliasComune
		{
			get { return this._aliasComune; }
		}

		public string Software
		{
			get { return this._software; }
		}

		public int IdPresentazione
		{
			get { return this._idPresentazione; }
		}

		public bool FlagPrivacy
		{
			get { return this._flagPrivacy; }
		}

		public int? AttivitaAtecoPrimaria 
		{
			get
			{
				return this._attivitaAtecoPrimaria;
			}
		}

		#endregion
	}
}
