using Init.SIGePro.Protocollo.MittentiDestinatari;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;
using Init.SIGePro.Protocollo.WsDataClass;
using log4net;
using Xunit;

namespace Init.SIGePro.Protocollo.Test.MittentiDestinatari
{
    public class MittentiDestinatariFactoryTests
    {
        private DatiAnagrafici Richiedente()
        {
            return new DatiAnagrafici
            {
                Cod = "1",
                Email = "stefano.mendichi@retelit.it",
                Mezzo = "piedi",
                ModalitaTrasmissione = "piccione viaggiatore"
            };
        }

        private DatiAnagrafici Azienda()
        {
            return new DatiAnagrafici
            {
                Cod = "10",
                Email = "aziendaprova@retelit.it",
                Mezzo = "auto",
                ModalitaTrasmissione = "piccione viaggiatore"
            };
        }

        private DatiAnagrafici Tecnico()
        {
            return new DatiAnagrafici
            {
                Cod = "100",
                Email = "tecnico@retelit.it",
                Mezzo = "auto",
                ModalitaTrasmissione = "piccione viaggiatore"
            };
        }

        [Fact]
        public void GetTipoMittenteAziendaRichiedenteTornaRichiedenteSeNonPresenteAzienda()
        {
            var logger = LogManager.GetLogger(this.GetType());
            var richiedente = this.Richiedente();
            var azienda = (DatiAnagrafici)null;
            var tecnico = (DatiAnagrafici)null;


            var soggetti = new MittentiDestinatariFactory(logger, new FakeQualificaDittaIndividualeService(), richiedente, azienda, tecnico)
                                .GetSoggetti(TipoMittenteEnum.AZIENDA_RICHIEDENTE);

            Assert.Single(soggetti);
            Assert.Equal(richiedente.Cod, soggetti[0].Cod);
        }

        [Fact]
        public void GetTipoMittenteAziendaRichiedenteTornaAziendaERichiedenteSeValorizzati()
        {
            var logger = LogManager.GetLogger(this.GetType());
            var richiedente = this.Richiedente();
            var azienda = this.Azienda();
            var tecnico = (DatiAnagrafici)null;


            var soggetti = new MittentiDestinatariFactory(logger, new FakeQualificaDittaIndividualeService(), richiedente, azienda, tecnico)
                                .GetSoggetti(TipoMittenteEnum.AZIENDA_RICHIEDENTE);

            Assert.Equal(2, soggetti.Count);
            Assert.Equal(richiedente.Cod, soggetti[0].Cod);
            Assert.Equal(azienda.Cod, soggetti[1].Cod);
        }

        [Fact]
        public void GetTipoMittenteRichiedente()
        {
            var logger = LogManager.GetLogger(this.GetType());
            var richiedente = this.Richiedente();
            var azienda = (DatiAnagrafici)null;
            var tecnico = (DatiAnagrafici)null;


            var soggetti = new MittentiDestinatariFactory(logger, new FakeQualificaDittaIndividualeService(), richiedente, azienda, tecnico)
                                .GetSoggetti(TipoMittenteEnum.RICHIEDENTE);

            Assert.Single(soggetti);
            Assert.Equal(richiedente.Cod, soggetti[0].Cod);
        }

        [Fact]
        public void GetTipoMittenteAzienda()
        {
            var logger = LogManager.GetLogger(this.GetType());
            var richiedente = this.Richiedente();
            var azienda = this.Azienda();
            var tecnico = (DatiAnagrafici)null;


            var soggetti = new MittentiDestinatariFactory(logger, new FakeQualificaDittaIndividualeService(), richiedente, azienda, tecnico)
                                .GetSoggetti(TipoMittenteEnum.AZIENDA);

            Assert.Single(soggetti);
            Assert.Equal(azienda.Cod, soggetti[0].Cod);
        }

        [Fact]
        public void GetTipoMittenteAziendaTecnicoTornaAziendaTecnicoSeValorizzati()
        {
            var logger = LogManager.GetLogger(this.GetType());
            var richiedente = this.Richiedente();
            var azienda = this.Azienda();
            var tecnico = this.Tecnico();


            var soggetti = new MittentiDestinatariFactory(logger, new FakeQualificaDittaIndividualeService(), richiedente, azienda, tecnico)
                                .GetSoggetti(TipoMittenteEnum.AZIENDA_TECNICO);

            Assert.Equal(2, soggetti.Count);
            Assert.Equal(azienda.Cod, soggetti[0].Cod);
            Assert.Equal(tecnico.Cod, soggetti[1].Cod);
        }

        [Fact]
        public void GetTipoMittenteAziendaTecnicoTornaRichiedenteTecnicoSeAziendaNonValorizzata()
        {
            var logger = LogManager.GetLogger(this.GetType());
            var richiedente = this.Richiedente();
            var azienda = (DatiAnagrafici)null;
            var tecnico = this.Tecnico();

            var soggetti = new MittentiDestinatariFactory(logger, new FakeQualificaDittaIndividualeService(), richiedente, azienda, tecnico)
                                .GetSoggetti(TipoMittenteEnum.AZIENDA_TECNICO);

            Assert.Equal(2, soggetti.Count);
            Assert.Equal(richiedente.Cod, soggetti[0].Cod);
            Assert.Equal(tecnico.Cod, soggetti[1].Cod);
        }

        [Fact]
        public void GetTipoMittenteAziendaTecnicoTornaAziendaSeTecnicoNonValorizzato()
        {
            var logger = LogManager.GetLogger(this.GetType());
            var richiedente = this.Richiedente();
            var azienda = (DatiAnagrafici)null;
            var tecnico = this.Tecnico();

            var soggetti = new MittentiDestinatariFactory(logger, new FakeQualificaDittaIndividualeService(), richiedente, azienda, tecnico)
                                .GetSoggetti(TipoMittenteEnum.AZIENDA_TECNICO);

            Assert.Equal(2, soggetti.Count);
            Assert.Equal(richiedente.Cod, soggetti[0].Cod);
            Assert.Equal(tecnico.Cod, soggetti[1].Cod);
        }

        [Fact]
        public void GetTipoMittenteAziendaTecnicoTornaRichiedenteSeTecnicoEAziendaNonValorizzati()
        {
            var logger = LogManager.GetLogger(this.GetType());
            var richiedente = this.Richiedente();
            var azienda = (DatiAnagrafici)null;
            var tecnico = (DatiAnagrafici)null;

            var soggetti = new MittentiDestinatariFactory(logger, new FakeQualificaDittaIndividualeService(), richiedente, azienda, tecnico)
                                .GetSoggetti(TipoMittenteEnum.AZIENDA_TECNICO);

            Assert.Single(soggetti);
            Assert.Equal(richiedente.Cod, soggetti[0].Cod);
        }

        [Fact]
        public void GetTipoMittenteTecnicoTornaTecnicoSeValorizzato()
        {
            var logger = LogManager.GetLogger(this.GetType());
            var richiedente = this.Richiedente();
            var azienda = (DatiAnagrafici)null;
            var tecnico = this.Tecnico();


            var soggetti = new MittentiDestinatariFactory(logger, new FakeQualificaDittaIndividualeService(), richiedente, azienda, tecnico)
                                .GetSoggetti(TipoMittenteEnum.TECNICO);

            Assert.Single(soggetti);
            Assert.Equal(tecnico.Cod, soggetti[0].Cod);
        }

        [Fact]
        public void GetTipoMittenteTecnicoTornaRichiedenteSeTecnicoNonValorizzato()
        {
            var logger = LogManager.GetLogger(this.GetType());
            var richiedente = this.Richiedente();
            var azienda = this.Azienda();
            var tecnico = (DatiAnagrafici)null;


            var soggetti = new MittentiDestinatariFactory(logger, new FakeQualificaDittaIndividualeService(), richiedente, azienda, tecnico)
                                .GetSoggetti(TipoMittenteEnum.TECNICO);

            Assert.Single(soggetti);
            Assert.Equal(richiedente.Cod, soggetti[0].Cod);
        }

        [Fact]
        public void GetTipoMittenteDittaIndividualeTornaRichiedenteSeDittaIndividuale()
        {
            var logger = LogManager.GetLogger(this.GetType());
            var richiedente = this.Richiedente();
            var azienda = this.Azienda();
            var tecnico = (DatiAnagrafici)null;


            var soggetti = new MittentiDestinatariFactory(logger, new FakeQualificaDittaIndividualeService(), richiedente, azienda, tecnico)
                                .GetSoggetti(TipoMittenteEnum.DITTA_INDIVIDUALE);

            Assert.Single(soggetti);
            Assert.Equal(richiedente.Cod, soggetti[0].Cod);
        }

        [Fact]
        public void GetTipoMittenteDittaIndividualeTornaRichiedenteSeNonDittaIndividualeEAziendaNull()
        {
            var logger = LogManager.GetLogger(this.GetType());
            var richiedente = this.Richiedente();
            var azienda = (DatiAnagrafici)null;
            var tecnico = (DatiAnagrafici)null;


            var soggetti = new MittentiDestinatariFactory(logger, new FakeQualificaDittaIndividualeService(false), richiedente, azienda, tecnico)
                                .GetSoggetti(TipoMittenteEnum.DITTA_INDIVIDUALE);

            Assert.Single(soggetti);
            Assert.Equal(richiedente.Cod, soggetti[0].Cod);
        }

        [Fact]
        public void GetTipoMittenteDittaIndividualeTornaAziendaSeNonDittaIndividualeEAziendaValorizzata()
        {
            var logger = LogManager.GetLogger(this.GetType());
            var richiedente = this.Richiedente();
            var azienda = this.Azienda();
            var tecnico = (DatiAnagrafici)null;


            var soggetti = new MittentiDestinatariFactory(logger, new FakeQualificaDittaIndividualeService(false), richiedente, azienda, tecnico)
                                .GetSoggetti(TipoMittenteEnum.DITTA_INDIVIDUALE);

            Assert.Single(soggetti);
            Assert.Equal(azienda.Cod, soggetti[0].Cod);
        }
    }
}
