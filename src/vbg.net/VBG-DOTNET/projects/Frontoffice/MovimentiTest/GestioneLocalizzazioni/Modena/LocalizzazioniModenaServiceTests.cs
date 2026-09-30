using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni.Modena;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.SIGePro.Manager.DTO.StradarioComune;
using System;
using System.Linq;
using System.Threading.Tasks;
using Xunit;

namespace Init.Sigepro.FrontEnd.AppLogicTests.GestioneLocalizzazioni.Modena
{
    public class LocalizzazioniModenaServiceTests
    {
        private class FakeLocalizzazioniService : LocalizzazioniService
        {
            public FakeLocalizzazioniService()
                : base(null, null, null)
            {
            }

            public override StradarioDto GetById(int idStradario)
            {
                return new StradarioDto
                {
                    CodiceStradario = idStradario,
                    CodViario = "123",
                    NomeVia = "Via di test"
                };
            }
        }

        private class FakeSalvataggioStrategy : ISalvataggioDomandaStrategy
        {
            public DomandaOnline Domanda;

            public void Elimina(DomandaOnline domanda) => throw new NotImplementedException();
            public byte[] GetAsXml(int idDomanda) => throw new NotImplementedException();
            public DomandaOnline GetById(int idPresentazione) => this.Domanda;
            public Task<DomandaOnline> GetByIdAsync(int idPresentazione) => throw new NotImplementedException();
            public void ImpostaIdIstanzaOrigine(int idDomanda, int idDomandaOrigine) => throw new NotImplementedException();
            public void Salva(DomandaOnline domanda, bool aggiornaDataultimaModifica) { }
            public ValueTask SalvaAsync(DomandaOnline domanda, bool aggiornaDataultimaModifica) => throw new NotImplementedException();
        }

        private class FakeDomandaOnline : DomandaOnline
        {
            public FakeDomandaOnline()
                : base(PresentazioneIstanzaDataKey.New("TEST", "TT", "123", 1))
            {
                this.WriteInterface.AltriDati.ImpostaCodiceComune("TEST", "54024");
            }
            protected override IEventObserver CreateEventObserver() => null;
        }

        [Fact]
        public void AggiornaLocalizzazioniModenaDaMappa_restituisce_eccezzione_se_localizzazioni_non_impostate()
        {
            var svc = new LocalizzazioniModenaService(new FakeLocalizzazioniService(), new FakeSalvataggioStrategy());
            OpzioniSalvataggioLocalizzazioniModena opzioni = new OpzioniSalvataggioLocalizzazioniModena(-1, "T", "Terreni", -1);
            LocalizzazioniMappaModenaSelezionate localizzazioni = new LocalizzazioniMappaModenaSelezionate();
            Assert.Throws<LocalizzazioniModenaService.ParticellaNonSelezionataException>(() =>
                svc.AggiornaLocalizzazioniModenaDaMappa(-1, localizzazioni, opzioni));
        }

        [Fact]
        public void AggiornaLocalizzazioniModenaDaMappa_modifica_le_localizzazioni_della_domanda()
        {
            var salvataggioStrategy = new FakeSalvataggioStrategy();
            salvataggioStrategy.Domanda = new FakeDomandaOnline();
            var localizzazioni = new LocalizzazioniMappaModenaSelezionate
            {
                ParticelleGrafiche = new[]
                {
                    new LocalizzazioniMappaModenaSelezionate.LocalizzazioneMappaModena
                    {
                        CodCatastale = "TEST",
                        Foglio = "1",
                        Particella = "2"
                    }
                },
                ParticelleManuali = new[]
                {
                    new LocalizzazioniMappaModenaSelezionate.LocalizzazioneMappaModena
                    {
                        CodCatastale = "TEST",
                        Foglio = "1",
                        Particella = "AA"
                    }
                }
            };
            var opzioni = new OpzioniSalvataggioLocalizzazioniModena(-1, "T", "Terreni", -1);
            var svc = new LocalizzazioniModenaService(new FakeLocalizzazioniService(), salvataggioStrategy);
            svc.AggiornaLocalizzazioniModenaDaMappa(-1, localizzazioni, opzioni);
            Assert.Equal(2, salvataggioStrategy.Domanda.ReadInterface.Localizzazioni.Indirizzi.Count());
            Assert.Empty(salvataggioStrategy.Domanda.ReadInterface.DatiDinamici.DatiDinamici);
        }

        [Fact]
        public void AggiornaLocalizzazioniModenaDaMappa_aggiunge_un_campo_dinamico_se_sono_presenti_particelle_fuori_mappa()
        {
            var salvataggioStrategy = new FakeSalvataggioStrategy();
            salvataggioStrategy.Domanda = new FakeDomandaOnline();
            var localizzazioni = new LocalizzazioniMappaModenaSelezionate
            {
                ParticelleManuali = new[]
                {
                    new LocalizzazioniMappaModenaSelezionate.LocalizzazioneMappaModena
                    {
                        CodCatastale = "TEST",
                        Foglio = "1",
                        Particella = "AA"
                    }
                }
            };
            var opzioni = new OpzioniSalvataggioLocalizzazioniModena(-1, "T", "Terreni", 1);
            var svc = new LocalizzazioniModenaService(new FakeLocalizzazioniService(), salvataggioStrategy);
            svc.AggiornaLocalizzazioniModenaDaMappa(-1, localizzazioni, opzioni);
            Assert.Single(salvataggioStrategy.Domanda.ReadInterface.Localizzazioni.Indirizzi);
            Assert.Single(salvataggioStrategy.Domanda.ReadInterface.DatiDinamici.DatiDinamici);
            Assert.Equal("1", salvataggioStrategy.Domanda.ReadInterface.DatiDinamici.DatiDinamici.FirstOrDefault().Valore);
            Assert.Equal("1", salvataggioStrategy.Domanda.ReadInterface.DatiDinamici.DatiDinamici.FirstOrDefault().ValoreDecodificato);
            Assert.Equal(1, salvataggioStrategy.Domanda.ReadInterface.DatiDinamici.DatiDinamici.FirstOrDefault().IdCampo);
        }
    }
}
