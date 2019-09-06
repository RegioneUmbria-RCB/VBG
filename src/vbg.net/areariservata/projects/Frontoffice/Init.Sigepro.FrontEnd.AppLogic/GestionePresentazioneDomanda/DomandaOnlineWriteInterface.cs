using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneRiepiloghiSchedeDinamiche;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDelegaATrasmettere;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAltriDati;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneProcure;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAllegati;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAutorizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneTares;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneTasi;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneImu;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneBandiUmbria;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiExtra;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneBandiUmbria;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiExtra;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDenunceTares;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneBookmarks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda
{
	public class DomandaOnlineWriteInterface : IDomandaOnlineWriteInterface
	{
		PresentazioneIstanzaDbV2					_database;
		IDocumentiWriteInterface					_documenti;
		IDatiDinamiciWriteInterface					_datiDinamici;
		IRiepiloghiSchedeDinamicheWriteInterface	_riepiloghiSchedeDinamiche;
		IEndoprocedimentiWriteInterface				_endoprocedimenti;
		IDelegaATrasmettereWriteInterface			_delegaATrasmettere;
		IOneriWriteInterface						_oneri;
		ILocalizzazioniWriteInterface				_localizzazioni;
		IAltriDatiWriteInterface					_altriDati;
		IProcureWriteInterface						_procure;
		IAnagraficheWriteInterface					_anagrafiche;
		IAllegatiWriteInterface						_allegati;
		IAutorizzazioniMercatiWriteInterface		_autorizzazioni;
		ITaresBariWriteInterface					_taresBari;
		ITasiBariWriteInterface						_tasiBari;
		IImuBariWriteInterface						_imuBari;
		IBandiUmbriaWriteInterface					_bandiUmbria;
		IDatiExtraWriteinterface					_datiExtra;
		IDenunceTaresBariWriteInterface				_denunceTaresBari;
        IBookmarksWriteInterface                    _bookmarks;

		public DomandaOnlineWriteInterface(PresentazioneIstanzaDbV2 database)
		{
			this._database = database;

			this._documenti = new DocumentiWriteInterface(database);
			this._riepiloghiSchedeDinamiche = new RiepiloghiSchedeDinamicheWriteInterface(database);
			this._datiDinamici = new DatiDinamiciWriteInterface(database, this._riepiloghiSchedeDinamiche);
			this._endoprocedimenti = new EndoprocedimentiWriteInterface( database );
			this._delegaATrasmettere = new DelegaATrasmettereWriteInterface( database );
			this._oneri = new OneriWriteInterface( database );
			this._localizzazioni = new LocalizzazioniWriteInterface(database);
			this._altriDati = new AltriDatiWriteInterface(database);
			this._procure = new ProcureWriteInterface(database);
			this._anagrafiche = new AnagraficheWriteInterface(database);
			this._allegati = new AllegatiWriteInterface(database);
			this._autorizzazioni = new AutorizzazioniMercatiWriteInterface(database);
			this._taresBari = new TaresBariWriteInterface(database);
			this._tasiBari = new TasiBariWriteInterface(database);
            this._imuBari = new ImuBariWriteInterface(database);
			this._bandiUmbria = new BandiUmbriaWriteInterface(database);
			this._datiExtra = new DatiExtraWriteInterface(database);
			this._denunceTaresBari = new DenunceTaresBariWriteInterface(database);
            this._bookmarks = new BookmarksWriteInterface(database);
		}

		#region IDomandaOnlineWriteInterface Members

		public IDocumentiWriteInterface Documenti
		{
			get { return this._documenti; }
		}

		public IDatiDinamiciWriteInterface DatiDinamici
		{
			get { return this._datiDinamici; }
		}

		public IRiepiloghiSchedeDinamicheWriteInterface RiepiloghiSchedeDinamiche
		{
			get { return this._riepiloghiSchedeDinamiche; }
		}

		public IEndoprocedimentiWriteInterface Endoprocedimenti
		{
			get { return this._endoprocedimenti; }
		}

		public IDelegaATrasmettereWriteInterface DelegaATrasmettere
		{
			get { return this._delegaATrasmettere; }
		}

		public IOneriWriteInterface Oneri
		{
			get { return this._oneri; }
		}

		public ILocalizzazioniWriteInterface Localizzazioni
		{
			get { return this._localizzazioni; }
		}

		public IAltriDatiWriteInterface AltriDati
		{
			get { return this._altriDati; }
		}


		public IProcureWriteInterface Procure
		{
			get { return this._procure; }
		}

		public IAnagraficheWriteInterface Anagrafiche
		{
			get { return this._anagrafiche; }
		}

		
		public IAllegatiWriteInterface Allegati
		{
			get { return this._allegati; }
		}

		public IAutorizzazioniMercatiWriteInterface AutorizzazioniMercati
		{
			get { return this._autorizzazioni; }
		}

		public ITaresBariWriteInterface TaresBari
		{
			get { return this._taresBari; }
		}

		public ITasiBariWriteInterface TasiBari
		{
			get { return this._tasiBari; }
		}

		public IImuBariWriteInterface ImuBari
		{
			get { return this._imuBari; }
		}

		public IBandiUmbriaWriteInterface BandiUmbria
		{
			get { return this._bandiUmbria; }
		}

		public IDatiExtraWriteinterface DatiExtra
		{
			get { return this._datiExtra; }
		}

		public IDenunceTaresBariWriteInterface DenunceTaresBari
		{
			get { return this._denunceTaresBari; }
		}


        public IBookmarksWriteInterface Bookmarks
        {
            get { return this._bookmarks; }
        }

		#endregion
	}
}
