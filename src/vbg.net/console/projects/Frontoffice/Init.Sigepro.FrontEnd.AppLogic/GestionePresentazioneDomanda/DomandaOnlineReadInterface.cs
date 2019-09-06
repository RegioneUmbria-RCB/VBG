using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDelegaATrasmettere;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAltriDati;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneProcure;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneRiepiloghiSchedeDinamiche;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAutorizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneTares;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneTasi;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneImu;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneBandiUmbria;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiExtra;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDenunceTares;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneBookmarks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda
{
	public class DomandaOnlineReadInterface : IDomandaOnlineReadInterface
	{
		IAltriDatiReadInterface _altriDati;
		IAnagraficheReadInterface _anagrafiche;
		PresentazioneIstanzaDbV2 _database;
		IDatiDinamiciReadInterface _datiDinamici;
		IDelegaATrasmettereReadInterface _delegaATrasmettere;
		IDocumentiReadInterface _documenti;
		IEndoprocedimentiReadInterface _endoprocedimenti;
		PresentazioneIstanzaDataKey _istanzaKey;
		ILocalizzazioniReadInterface _localizzazioni;
		IOneriReadInterface _oneri;
		IAutorizzazioniMercatiReadInterface _autorizzazioni;
		ITaresBariReadInterface _taresBari;
		ITasiBariReadInterface _tasiBari;
		IImuBariReadInterface _imuBari;
		IBandiUmbriaReadInterface _bandiUmbria;
		IDatiExtraReadInterface _datiExtra;
		IDenunceTaresBariReadInterface _denunceTaresBari;
        IBookmarksReadInterface _bookmarks;
		
		bool _presentata = false;
		
		IProcureReadInterface _procure;
		IRiepiloghiSchedeDinamicheReadInterface _riepiloghiSchedeDinamiche;

		public DomandaOnlineReadInterface(PresentazioneIstanzaDataKey istanzaKey, PresentazioneIstanzaDbV2 database, bool presentata)
		{
			this._istanzaKey = istanzaKey;

			this._database = database;

			this._presentata = presentata;
		}

		#region IDomandaOnlineReadInterface Members


		public IAltriDatiReadInterface AltriDati
		{
			get
			{
				if (this._altriDati == null)
					this._altriDati = new AltriDatiReadInterface(this._istanzaKey, this._database);

				return this._altriDati;
			}
		}

		public IAnagraficheReadInterface Anagrafiche
		{
			get
			{
				if (this._anagrafiche == null)
					this._anagrafiche = new AnagraficheReadInterface(this._database);

				return this._anagrafiche;
			}
		}

		public IDatiDinamiciReadInterface DatiDinamici
		{
			get
			{
				if (this._datiDinamici == null)
					this._datiDinamici = new DatiDinamiciReadInterface(this._database, this.RiepiloghiSchedeDinamiche);

				return this._datiDinamici;
			}
		}

		public IDelegaATrasmettereReadInterface DelegaATrasmettere
		{
			get
			{
				if (this._delegaATrasmettere == null)
					this._delegaATrasmettere = new DelegaATrasmettereReadInterface(this._istanzaKey.CodiceUtente, this.Anagrafiche, this._database);

				return _delegaATrasmettere;
			}
		}

		public IDocumentiReadInterface Documenti
		{
			get
			{
				if (this._documenti == null)
					this._documenti = new DocumentiReadInterface(this._database);

				return this._documenti;
			}
		}

		public IEndoprocedimentiReadInterface Endoprocedimenti
		{
			get
			{
				if (this._endoprocedimenti == null)
					this._endoprocedimenti = new EndoprocedimentiReadInterface(this._database);

				return this._endoprocedimenti;
			}
		}

		public ILocalizzazioniReadInterface Localizzazioni
		{
			get
			{
				if (this._localizzazioni == null)
					this._localizzazioni = new LocalizzazioniReadInterface(this._database);

				return this._localizzazioni;
			}
		}

		public IOneriReadInterface Oneri
		{
			get
			{
				if (this._oneri == null)
					this._oneri = new OneriReadInterface(this._database);

				return this._oneri;
			}
		}

		public IProcureReadInterface Procure
		{
			get 
			{
				if (this._procure == null)
					this._procure = new ProcureReadInterface(this._database);

				return this._procure; 
			}
		}

		public IRiepiloghiSchedeDinamicheReadInterface RiepiloghiSchedeDinamiche
		{
			get 
			{
				if (this._riepiloghiSchedeDinamiche == null)
					this._riepiloghiSchedeDinamiche = new RiepiloghiSchedeDinamicheReadInterface(this._database);

				return this._riepiloghiSchedeDinamiche; 
			}
		}

		public void Invalidate()
		{
			this._oneri = null;
			this._anagrafiche = null;
			this._delegaATrasmettere = null;
			this._altriDati = null;
			this._documenti = null;
			this._procure = null;
			this._riepiloghiSchedeDinamiche = null;
			this._endoprocedimenti = null;
			this._datiDinamici = null;
			this._localizzazioni = null;
			this._taresBari = null;
		}

		public bool IsPresentata()
		{
			return this._presentata;
		}

		public bool UtentePuoAccedere(string codiceUtente)
		{
			return this._istanzaKey.CodiceUtente.ToUpperInvariant() == codiceUtente.ToUpperInvariant();
		}


		public IAutorizzazioniMercatiReadInterface AutorizzazioniMercati
		{
			get {
				if (this._autorizzazioni == null)
					this._autorizzazioni = new AutorizzazioniMercatiReadInterface(this._database);

				return this._autorizzazioni;			
			}
		}

		#endregion


		public ITaresBariReadInterface TaresBari
		{
			get 
			{
				if (this._taresBari == null)
					this._taresBari = new TaresBariReadInterface(this._database);

				return this._taresBari;
			
			}
		}

		public ITasiBariReadInterface TasiBari
		{
			get
			{
				if (this._tasiBari == null)
					this._tasiBari = new TasiBariReadInterface(this._database);

				return this._tasiBari;
			}
		}

		public IImuBariReadInterface ImuBari
		{
			get
			{
				if (this._imuBari == null)
					this._imuBari = new ImuBariReadInterface(this._database);

				return this._imuBari;
			}
		}


		public IBandiUmbriaReadInterface BandiUmbria
		{
			get
			{
				if (this._bandiUmbria == null)
					this._bandiUmbria = new BandiUmbriaReadInterface(this._database);

				return this._bandiUmbria;
			}
		}

		public IDatiExtraReadInterface DatiExtra
		{
			get
			{
				if (this._datiExtra == null)
					this._datiExtra = new DatiExtraReadInterface(this._database);

				return this._datiExtra;
			}
		}


		public IDenunceTaresBariReadInterface DenunceTaresBari
		{
			get
			{
				if (this._denunceTaresBari == null)
					this._denunceTaresBari = new DenunceTaresBariReadInterface(this._database);

				return this._denunceTaresBari;
			}
		}

        public IBookmarksReadInterface Bookmarks
        {
            get
            {
                if (this._bookmarks == null)
                    this._bookmarks = new BookmarksReadInterface(this._database);

                return this._bookmarks;
            }
        }
	}
}
