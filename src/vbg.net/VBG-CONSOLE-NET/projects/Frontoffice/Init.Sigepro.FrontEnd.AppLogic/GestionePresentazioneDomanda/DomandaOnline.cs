using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using System.Data;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda
{
	public class DomandaOnline
	{
		public class DomandaOnlineFlags
		{
			public bool Presentata { get; private set; }

			public DomandaOnlineFlags(bool presentata)
			{
				this.Presentata = presentata;
			}
		}

		public IDomandaOnlineReadInterface ReadInterface { get; private set; }
		internal IDomandaOnlineWriteInterface WriteInterface { get; private set; }

		PresentazioneIstanzaDataKey _dataKey;
		PresentazioneIstanzaDbV2	_database;
		DomandaOnlineFlags			_flags;

		IEventObserver _eventObserver;

		public static DomandaOnline FacSimile(string idComune, string software)
		{
			var domanda = new DomandaOnline(PresentazioneIstanzaDataKey.New(idComune, software, "XXXXX", 0), new ObjectSpace.PresentazioneIstanza.PresentazioneIstanzaDbV2(), false);

			domanda.WriteInterface.Anagrafiche.AggiungiOAggiorna(AnagraficaDomanda.FacSimileRichiedente(), new NullLogicaSincronizzazioneTipiSoggetto());

			return domanda;
		}

		public DomandaOnline( PresentazioneIstanzaDataKey dataKey, PresentazioneIstanzaDbV2 database , bool presentata)
		{
			this._database = database;
			this._dataKey = dataKey;
			this._flags = new DomandaOnlineFlags(presentata);

			this.ReadInterface = new DomandaOnlineReadInterface(this._dataKey, database, presentata);
			this.WriteInterface = new DomandaOnlineWriteInterface(database);

			this._eventObserver = CreateEventObserver();
		}

		protected virtual IEventObserver CreateEventObserver()
		{
			return new EventObserver(this._database, this.WriteInterface, this.ReadInterface);
		}

		public PresentazioneIstanzaDataKey DataKey
		{
			get { return this._dataKey; }
		}

		public DomandaOnlineFlags Flags
		{
			get { return this._flags; }
		}



		internal byte[] SerializeTo(ConversioneVersioniDataSetDomanda.V5DataSetSerializer serializer)
		{
			this._database.AcceptChanges();

			var data = serializer.Serialize(this._database);

			if (VersionInformationsHelper.GetVersion(data) != VersionInformationsHelper.CurrentVersion)
			{
				var errMsg = "si sta cercando di serializzare il dataset della domanda in un formato diverso dalla versione corrente. Versione dataset: {0}, Versione Attuale: {1}";
				var versioneDataset = VersionInformationsHelper.GetVersion(data);

				throw new Exception( String.Format( errMsg, versioneDataset, VersionInformationsHelper.CurrentVersion ) );
			}

			return data;
		}

		internal bool UtentePuoAccedere(string codiceFiscaleUtente)
		{
			return codiceFiscaleUtente.ToUpperInvariant() == this.DataKey.CodiceUtente.ToUpperInvariant();
		}

		internal void ImpostaComePresentata()
		{
			this._flags = new DomandaOnlineFlags(true);
		}
	}
}
