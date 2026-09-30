using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.SIGePro.Manager.DTO;
using Init.SIGePro.Manager.DTO.Oneri;
using System;
using System.Collections.Generic;
using System.Linq;
using Xunit;

namespace MovimentiTest.GestioneOneri
{
    public class LogicaSincronizzazioneOneriTests
    {
        public class FakeOneriRepository : IOneriRepository
        {
            public List<Onere> ListaOneri = new List<Onere>();


            public IEnumerable<Onere> GetByIdInterventoIdEndo(int codiceIntervento, List<int> listaIdEndo)
            {
                return this.ListaOneri;
            }

            public string GetCodiceCausaleOnereTraslazione(int idCausale)
            {
                throw new NotImplementedException();
            }

            public TipoPagamento GetModalitaPagamentoById(string modalitaPagamentoId)
            {
                throw new NotImplementedException();
            }

            public IEnumerable<TipoPagamento> GetModalitaPagamento()
            {
                throw new NotImplementedException();
            }

            public IEnumerable<TipoPagamento> GetTipiPagamento()
            {
                throw new NotImplementedException();
            }
        }

        public class FakeDomandaOnline : DomandaOnline
        {
            public FakeDomandaOnline()
                : base(PresentazioneIstanzaDataKey.New("XXX", "ZZ", "YY", 1))
            {
                this.WriteInterface.AltriDati.ImpostaCodiceComune("XXX", "54024");
            }

            protected override IEventObserver CreateEventObserver()
            {
                return null;
            }
        }


        [Fact]
        public void Aggiunge_oneri_non_presenti_alla_domanda()
        {
            var oneriRepository = new FakeOneriRepository();
            var logicaSincronizzazioneOneri = new LogicaSincronizzazioneOneri(oneriRepository);
            var domanda = new FakeDomandaOnline();

            domanda.WriteInterface.AltriDati.ImpostaIntervento(1);

            oneriRepository.ListaOneri.Add(new Onere(new OnereDto
            {
                Codice = 1,
                CodiceInterventoOEndoOrigine = 1,
                OrigineOnere = "I",
                Descrizione = "Onere intervento",
                Importo = 10,
                InterventoOEndoOrigine = "Intervento 1"
            }));

            oneriRepository.ListaOneri.Add(new Onere(new OnereDto
            {
                Codice = 2,
                CodiceInterventoOEndoOrigine = 1,
                OrigineOnere = "E",
                Descrizione = "Onere Endoprocedimento",
                Importo = 20,
                InterventoOEndoOrigine = "Endoprocedimento 2"
            }));

            logicaSincronizzazioneOneri.SincronizzaOneri(domanda);

            Assert.Equal<int>(2, domanda.ReadInterface.Oneri.Oneri.Count());
            Assert.Single(domanda.ReadInterface.Oneri.OneriEndoprocedimenti);
            Assert.Single(domanda.ReadInterface.Oneri.OneriIntervento);

            var onereIntervento = domanda.ReadInterface.Oneri.OneriIntervento.First();
            Assert.Equal(1, onereIntervento.Causale.Codice);
            Assert.Equal("Onere intervento", onereIntervento.Causale.Descrizione);
            Assert.Equal(1, onereIntervento.EndoOInterventoOrigine.Codice);
            Assert.Equal("Intervento 1", onereIntervento.EndoOInterventoOrigine.Descrizione);
            Assert.Equal(10.0m, onereIntervento.Importo);

            var onereEndo = domanda.ReadInterface.Oneri.OneriEndoprocedimenti.First();
            Assert.Equal(2, onereEndo.Causale.Codice);
            Assert.Equal("Onere Endoprocedimento", onereEndo.Causale.Descrizione);
            Assert.Equal(1, onereEndo.EndoOInterventoOrigine.Codice);
            Assert.Equal("Endoprocedimento 2", onereEndo.EndoOInterventoOrigine.Descrizione);
            Assert.Equal(20.0m, onereEndo.Importo);
        }

        [Fact]
        public void Rimuove_oneri_presenti_nella_domanda_ma_non_richiesti()
        {
            var oneriRepository = new FakeOneriRepository();
            var logicaSincronizzazioneOneri = new LogicaSincronizzazioneOneri(oneriRepository);
            var domanda = new FakeDomandaOnline();

            domanda.WriteInterface.AltriDati.ImpostaIntervento(1);
            domanda.WriteInterface.Oneri.CreaOAggiornaDatiOnere(new BaseDto<int, string> { Codice = 3, Descrizione = "Causale intervento da rimuovere" }, ProvenienzaOnere.Intervento,
                                                          new BaseDto<int, string> { Codice = 1, Descrizione = "Intervento 1" }, 666, "");

            domanda.WriteInterface.Oneri.CreaOAggiornaDatiOnere(new BaseDto<int, string> { Codice = 4, Descrizione = "Causale endo da rimuovere" }, ProvenienzaOnere.Endo,
                                                          new BaseDto<int, string> { Codice = 1, Descrizione = "Endoprocedimento 2" }, 999, "");

            oneriRepository.ListaOneri.Add(new Onere(new OnereDto
            {
                Codice = 1,
                CodiceInterventoOEndoOrigine = 1,
                OrigineOnere = "I",
                Descrizione = "Onere intervento",
                Importo = 10,
                InterventoOEndoOrigine = "Intervento 1"
            }));

            oneriRepository.ListaOneri.Add(new Onere(new OnereDto
            {
                Codice = 2,
                CodiceInterventoOEndoOrigine = 1,
                OrigineOnere = "E",
                Descrizione = "Onere Endoprocedimento",
                Importo = 20,
                InterventoOEndoOrigine = "Endoprocedimento 2"
            }));

            logicaSincronizzazioneOneri.SincronizzaOneri(domanda);

            Assert.Equal<int>(2, domanda.ReadInterface.Oneri.Oneri.Count());
            Assert.Single(domanda.ReadInterface.Oneri.OneriEndoprocedimenti);
            Assert.Single(domanda.ReadInterface.Oneri.OneriIntervento);

            var onereIntervento = domanda.ReadInterface.Oneri.OneriIntervento.First();
            Assert.Equal(1, onereIntervento.Causale.Codice);
            Assert.Equal("Onere intervento", onereIntervento.Causale.Descrizione);
            Assert.Equal(1, onereIntervento.EndoOInterventoOrigine.Codice);
            Assert.Equal("Intervento 1", onereIntervento.EndoOInterventoOrigine.Descrizione);
            Assert.Equal(10.0m, onereIntervento.Importo);

            var onereEndo = domanda.ReadInterface.Oneri.OneriEndoprocedimenti.First();
            Assert.Equal(2, onereEndo.Causale.Codice);
            Assert.Equal("Onere Endoprocedimento", onereEndo.Causale.Descrizione);
            Assert.Equal(1, onereEndo.EndoOInterventoOrigine.Codice);
            Assert.Equal("Endoprocedimento 2", onereEndo.EndoOInterventoOrigine.Descrizione);
            Assert.Equal(20.0m, onereEndo.Importo);
        }

        [Fact]
        public void Rimuove_attestazione_di_pagamento_se_presente()
        {
            var oneriRepository = new FakeOneriRepository();
            var logicaSincronizzazioneOneri = new LogicaSincronizzazioneOneri(oneriRepository);
            var domanda = new FakeDomandaOnline();

            domanda.WriteInterface.AltriDati.ImpostaIntervento(1);
            domanda.WriteInterface.Oneri.CreaOAggiornaDatiOnere(new BaseDto<int, string> { Codice = 3, Descrizione = "Causale intervento da rimuovere" }, ProvenienzaOnere.Intervento,
                                                          new BaseDto<int, string> { Codice = 1, Descrizione = "Intervento 1" }, 666, "");

            domanda.WriteInterface.Oneri.CreaOAggiornaDatiOnere(new BaseDto<int, string> { Codice = 4, Descrizione = "Causale endo da rimuovere" }, ProvenienzaOnere.Endo,
                                                          new BaseDto<int, string> { Codice = 1, Descrizione = "Endoprocedimento 2" }, 999, "");

            domanda.WriteInterface.Oneri.SalvaAttestazioneDiPagamento(123, "test.txt", true);

            Assert.True(domanda.ReadInterface.Oneri.AttestazioneDiPagamento.Presente);

            oneriRepository.ListaOneri.Add(new Onere(new OnereDto
            {
                Codice = 1,
                CodiceInterventoOEndoOrigine = 1,
                OrigineOnere = "I",
                Descrizione = "Onere intervento",
                Importo = 10,
                InterventoOEndoOrigine = "Intervento 1"
            }));

            oneriRepository.ListaOneri.Add(new Onere(new OnereDto
            {
                Codice = 2,
                CodiceInterventoOEndoOrigine = 1,
                OrigineOnere = "E",
                Descrizione = "Onere Endoprocedimento",
                Importo = 20,
                InterventoOEndoOrigine = "Endoprocedimento 2"
            }));

            logicaSincronizzazioneOneri.SincronizzaOneri(domanda);

            domanda.ReadInterface.Invalidate(); // NECESSARIO per aggiornare la read interface perché nelle classi di test non è presente un eventobserver

            Assert.False(domanda.ReadInterface.Oneri.AttestazioneDiPagamento.Presente);
        }

        [Fact]
        public void Se_flag_impostato_non_sincronizza_oneri_a_0()
        {
            var oneriRepository = new FakeOneriRepository();
            var logicaSincronizzazioneOneri = new LogicaSincronizzazioneOneri(oneriRepository);
            var domanda = new FakeDomandaOnline();

            domanda.WriteInterface.AltriDati.ImpostaIntervento(1);

            oneriRepository.ListaOneri.Add(new Onere(new OnereDto
            {
                Codice = 1,
                CodiceInterventoOEndoOrigine = 1,
                OrigineOnere = "I",
                Descrizione = "Onere intervento",
                Importo = 10,
                InterventoOEndoOrigine = "Intervento 1"
            }));

            oneriRepository.ListaOneri.Add(new Onere(new OnereDto
            {
                Codice = 2,
                CodiceInterventoOEndoOrigine = 1,
                OrigineOnere = "E",
                Descrizione = "Onere Endoprocedimento",
                Importo = 0.0f,
                InterventoOEndoOrigine = "Endoprocedimento 2"
            }));

            logicaSincronizzazioneOneri.ComportamentoOneriSenzaImporto = ComportamentoSincronizzazioneOneriSenzaImporto.Ignora;
            logicaSincronizzazioneOneri.SincronizzaOneri(domanda);

            Assert.Single(domanda.ReadInterface.Oneri.Oneri);
            Assert.Empty(domanda.ReadInterface.Oneri.OneriEndoprocedimenti);
            Assert.Single(domanda.ReadInterface.Oneri.OneriIntervento);

            var onereIntervento = domanda.ReadInterface.Oneri.OneriIntervento.First();
            Assert.Equal(1, onereIntervento.Causale.Codice);
            Assert.Equal("Onere intervento", onereIntervento.Causale.Descrizione);
            Assert.Equal(1, onereIntervento.EndoOInterventoOrigine.Codice);
            Assert.Equal("Intervento 1", onereIntervento.EndoOInterventoOrigine.Descrizione);
            Assert.Equal(10.0m, onereIntervento.Importo);

        }

    }
}
