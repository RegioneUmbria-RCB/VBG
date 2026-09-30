using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAltriDati;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAutorizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneBandiUmbria;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneBookmarks;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiExtra;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDelegaATrasmettere;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneProcure;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneRiepiloghiSchedeDinamiche;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda
{
    public class DomandaOnlineReadInterface : IDomandaOnlineReadInterface
    {
        private IAltriDatiReadInterface _altriDati;
        private IAnagraficheReadInterface _anagrafiche;
        internal readonly PresentazioneIstanzaDbV2 _database;
        private IDatiDinamiciReadInterface _datiDinamici;
        private IDelegaATrasmettereReadInterface _delegaATrasmettere;
        private IDocumentiReadInterface _documenti;
        private IEndoprocedimentiReadInterface _endoprocedimenti;
        private readonly PresentazioneIstanzaDataKey _istanzaKey;
        private ILocalizzazioniReadInterface _localizzazioni;
        private IOneriReadInterface _oneri;
        private IAutorizzazioniMercatiReadInterface _autorizzazioni;
        private IBandiUmbriaReadInterface _bandiUmbria;
        private IDatiExtraReadInterface _datiExtra;
        private IBookmarksReadInterface _bookmarks;
        private readonly bool _presentata = false;
        private IProcureReadInterface _procure;
        private IRiepiloghiSchedeDinamicheReadInterface _riepiloghiSchedeDinamiche;

        public DomandaOnlineReadInterface(PresentazioneIstanzaDataKey istanzaKey, PresentazioneIstanzaDbV2 database, bool presentata)
        {
            this._istanzaKey = istanzaKey;

            this._database = database;

            this._presentata = presentata;
        }

        #region IDomandaOnlineReadInterface Members


        public IAltriDatiReadInterface AltriDati => this._altriDati ?? (this._altriDati = new AltriDatiReadInterface(this._istanzaKey, this._database));

        public IAnagraficheReadInterface Anagrafiche => this._anagrafiche ?? (this._anagrafiche = new AnagraficheReadInterface(this._istanzaKey, this._database));

        public IDatiDinamiciReadInterface DatiDinamici => this._datiDinamici ?? (this._datiDinamici = new DatiDinamiciReadInterface(this._database, this.RiepiloghiSchedeDinamiche));

        public IDelegaATrasmettereReadInterface DelegaATrasmettere => this._delegaATrasmettere ?? (this._delegaATrasmettere = new DelegaATrasmettereReadInterface(this._istanzaKey.CodiceUtente, this.Anagrafiche, this._database));

        public IDocumentiReadInterface Documenti => this._documenti ?? (this._documenti = new DocumentiReadInterface(this._database));

        public IEndoprocedimentiReadInterface Endoprocedimenti => this._endoprocedimenti ?? (this._endoprocedimenti = new EndoprocedimentiReadInterface(this._database));

        public ILocalizzazioniReadInterface Localizzazioni => this._localizzazioni ?? (this._localizzazioni = new LocalizzazioniReadInterface(this._database));

        public IOneriReadInterface Oneri => this._oneri ?? (this._oneri = new OneriReadInterface(this._database));

        public IProcureReadInterface Procure => this._procure ?? (this._procure = new ProcureReadInterface(this._database));

        public IRiepiloghiSchedeDinamicheReadInterface RiepiloghiSchedeDinamiche => this._riepiloghiSchedeDinamiche ?? (this._riepiloghiSchedeDinamiche = new RiepiloghiSchedeDinamicheReadInterface(this._database));

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
        }

        public bool IsPresentata()
        {
            return this._presentata;
        }

        public bool UtentePuoAccedere(string codiceUtente)
        {
            return string.Equals(this._istanzaKey.CodiceUtente, codiceUtente, System.StringComparison.InvariantCultureIgnoreCase);
        }


        public IAutorizzazioniMercatiReadInterface AutorizzazioniMercati => this._autorizzazioni ?? (this._autorizzazioni = new AutorizzazioniMercatiReadInterface(this._database));

        #endregion

        public IBandiUmbriaReadInterface BandiUmbria => this._bandiUmbria ?? (this._bandiUmbria = new BandiUmbriaReadInterface(this._database));

        public IDatiExtraReadInterface DatiExtra => this._datiExtra ?? (this._datiExtra = new DatiExtraReadInterface(this._database));

        public IBookmarksReadInterface Bookmarks => this._bookmarks ?? (this._bookmarks = new BookmarksReadInterface(this._database));

        public bool DomandaEliminabile => !this.Oneri.GetWarningsPagamenti().Any() && !this.IsPresentata();
    }
}
