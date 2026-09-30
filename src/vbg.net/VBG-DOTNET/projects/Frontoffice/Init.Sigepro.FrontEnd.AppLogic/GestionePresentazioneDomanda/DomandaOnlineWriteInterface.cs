using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAllegati;
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

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda
{
    public class DomandaOnlineWriteInterface : IDomandaOnlineWriteInterface
    {
        public DomandaOnlineWriteInterface(PresentazioneIstanzaDbV2 database)
        {
            this.Documenti = new DocumentiWriteInterface(database);
            this.RiepiloghiSchedeDinamiche = new RiepiloghiSchedeDinamicheWriteInterface(database);
            this.DatiDinamici = new DatiDinamiciWriteInterface(database, this.RiepiloghiSchedeDinamiche);
            this.Endoprocedimenti = new EndoprocedimentiWriteInterface(database);
            this.DelegaATrasmettere = new DelegaATrasmettereWriteInterface(database);
            this.Oneri = new OneriWriteInterface(database);
            this.Localizzazioni = new LocalizzazioniWriteInterface(database);
            this.AltriDati = new AltriDatiWriteInterface(database);
            this.Procure = new ProcureWriteInterface(database);
            this.Anagrafiche = new AnagraficheWriteInterface(database);
            this.Allegati = new AllegatiWriteInterface(database);
            this.AutorizzazioniMercati = new AutorizzazioniMercatiWriteInterface(database);
            this.BandiUmbria = new BandiUmbriaWriteInterface(database);
            this.DatiExtra = new DatiExtraWriteInterface(database);
            this.Bookmarks = new BookmarksWriteInterface(database);
        }

        #region IDomandaOnlineWriteInterface Members

        public IDocumentiWriteInterface Documenti { get; }

        public IDatiDinamiciWriteInterface DatiDinamici { get; }

        public IRiepiloghiSchedeDinamicheWriteInterface RiepiloghiSchedeDinamiche { get; }

        public IEndoprocedimentiWriteInterface Endoprocedimenti { get; }

        public IDelegaATrasmettereWriteInterface DelegaATrasmettere { get; }

        public IOneriWriteInterface Oneri { get; }

        public ILocalizzazioniWriteInterface Localizzazioni { get; }

        public IAltriDatiWriteInterface AltriDati { get; }

        public IProcureWriteInterface Procure { get; }

        public IAnagraficheWriteInterface Anagrafiche { get; }

        public IAllegatiWriteInterface Allegati { get; }

        public IAutorizzazioniMercatiWriteInterface AutorizzazioniMercati { get; }

        public IBandiUmbriaWriteInterface BandiUmbria { get; }

        public IDatiExtraWriteinterface DatiExtra { get; }

        public IBookmarksWriteInterface Bookmarks { get; }

        #endregion
    }
}
