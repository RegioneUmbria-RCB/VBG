using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Xunit;
using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters;
using Init.Sigepro.FrontEnd.AppLogic.StcService;

namespace MovimentiTest.Adapters
{
    public class IstanzaSTCAdapterTests
    {
        public class LocalizzazioneTests
        {
            LocalizzazioneAdapter _adapter;
            PresentazioneIstanzaDbV2 _db;
            DomandaOnlineReadInterface _readInterface;

            public LocalizzazioneTests()
            {
                this._adapter = new LocalizzazioneAdapter();
                this._db = new PresentazioneIstanzaDbV2();
                this._readInterface = new DomandaOnlineReadInterface(PresentazioneIstanzaDataKey.New("E256", "SS", "1", 1), this._db, false);
            }

            [Fact]
            public void Il_campo_uuid_di_LocalizzazioneNelComuneType_viene_popolato()
            {
                var domandaStc = new DettaglioPraticaType();
                var uuid = "123456";
                var row = this._db.ISTANZESTRADARIO.NewISTANZESTRADARIORow();
                row.ID = 1;
                row.CODICESTRADARIO = 1;
                row.Uuid = uuid;
                this._db.ISTANZESTRADARIO.AddISTANZESTRADARIORow(row);
                this._adapter.Adapt(this._readInterface, domandaStc);
                Assert.Single(domandaStc.localizzazione);
                Assert.False(string.IsNullOrEmpty(domandaStc.localizzazione[0].uuid));
                Assert.Equal(uuid, domandaStc.localizzazione[0].uuid);
            }

            [Fact]
            public void I_campi_latitudine_e_longitudine_vengono_popolati()
            {
                var domandaStc = new DettaglioPraticaType();
                var uuid = "123456";
                var row = this._db.ISTANZESTRADARIO.NewISTANZESTRADARIORow();
                row.ID = 1;
                row.CODICESTRADARIO = 1;
                row.Uuid = uuid;
                row.Longitudine = "longitudine";
                row.Latitudine = "latitudine";
                this._db.ISTANZESTRADARIO.AddISTANZESTRADARIORow(row);
                this._adapter.Adapt(this._readInterface, domandaStc);
                Assert.Single(domandaStc.localizzazione);
                Assert.NotNull(domandaStc.localizzazione[0].coordinate);
                Assert.False(string.IsNullOrEmpty(domandaStc.localizzazione[0].coordinate.longitudine));
                Assert.False(string.IsNullOrEmpty(domandaStc.localizzazione[0].coordinate.latitudine));
                Assert.Equal(row.Longitudine, domandaStc.localizzazione[0].coordinate.longitudine);
                Assert.Equal(row.Latitudine, domandaStc.localizzazione[0].coordinate.latitudine);
            }

            [Fact]
            public void Il_campo_tipoLocalizzazione_viene_popolato()
            {
                var domandaStc = new DettaglioPraticaType();
                var uuid = "123456";
                var row = this._db.ISTANZESTRADARIO.NewISTANZESTRADARIORow();
                row.ID = 1;
                row.CODICESTRADARIO = 1;
                row.Uuid = uuid;
                row.TipoLocalizzazione = "tipoLocalizzazione";
                this._db.ISTANZESTRADARIO.AddISTANZESTRADARIORow(row);
                this._adapter.Adapt(this._readInterface, domandaStc);
                Assert.Single(domandaStc.localizzazione);
                Assert.NotNull(domandaStc.localizzazione[0].tipo);
                Assert.Equal(row.TipoLocalizzazione, domandaStc.localizzazione[0].tipo.descrizione);
            }

            [Fact]
            public void Popolamento_dei_campi_di_localizzazioneType()
            {
                var domandaStc = new DettaglioPraticaType();
                var loc = this._db.ISTANZESTRADARIO.NewISTANZESTRADARIORow();
                loc.Cap = "Cap";
                loc.Circoscrizione = "Circoscrizione";
                loc.CIVICO = "CIVICO";
                loc.CODICECOMUNE = "CODICECOMUNE";
                loc.CODICESTRADARIO = 2;
                loc.COLORE = "COLORE";
                loc.Esponente = "Esponente";
                loc.EsponenteInterno = "EsponenteInterno";
                loc.Fabbricato = "Fabbricato";
                loc.ID = 1;
                loc.Interno = "Interno";
                loc.Km = "Km";
                loc.Latitudine = "Latitudine";
                loc.Longitudine = "Longitudine";
                loc.NOTE = "NOTE";
                loc.Piano = "Piano";
                loc.Scala = "Scala";
                loc.STRADARIO = "STRADARIO";
                loc.TipoLocalizzazione = "TipoLocalizzazione";
                loc.Uuid = "Uuid";
                this._db.ISTANZESTRADARIO.AddISTANZESTRADARIORow(loc);
                this._adapter.Adapt(this._readInterface, domandaStc);
                Assert.Single(domandaStc.localizzazione);
                Assert.Equal(loc.STRADARIO, domandaStc.localizzazione[0].denominazione);
                Assert.Equal(loc.CIVICO, domandaStc.localizzazione[0].civico);
                Assert.Equal(loc.CODICESTRADARIO.ToString(), domandaStc.localizzazione[0].id);
                Assert.Equal(loc.COLORE, domandaStc.localizzazione[0].colore);
                Assert.Equal(loc.Esponente, domandaStc.localizzazione[0].esponente);
                Assert.Equal(loc.EsponenteInterno, domandaStc.localizzazione[0].esponenteInterno);
                Assert.Equal(loc.Fabbricato, domandaStc.localizzazione[0].fabbricato);
                Assert.Equal(loc.Interno, domandaStc.localizzazione[0].interno);
                Assert.Equal(loc.Km, domandaStc.localizzazione[0].km);
                Assert.Equal(loc.Latitudine, domandaStc.localizzazione[0].coordinate.latitudine);
                Assert.Equal(loc.Longitudine, domandaStc.localizzazione[0].coordinate.longitudine);
                Assert.Equal(loc.Piano, domandaStc.localizzazione[0].piano);
                Assert.Equal(loc.Scala, domandaStc.localizzazione[0].scala);
                Assert.Equal(loc.TipoLocalizzazione, domandaStc.localizzazione[0].tipo.descrizione);
                Assert.Equal(loc.Uuid, domandaStc.localizzazione[0].uuid);
            }

            [Fact]
            public void Popolamento_dei_campi_di_riferimento_catastale()
            {
                var domandaStc = new DettaglioPraticaType();
                var loc = this._db.ISTANZESTRADARIO.NewISTANZESTRADARIORow();
                loc.ID = 0;
                loc.CODICESTRADARIO = 1;
                var dc1 = this._db.DATICATASTALI.NewDATICATASTALIRow();
                dc1.IdLocalizzazione = loc.ID;
                dc1.CodiceTipoCatasto = "F";
                dc1.TipoCatasto = "F1";
                dc1.Foglio = "1";
                dc1.Particella = "2";
                dc1.Sub = "3";
                var dc2 = this._db.DATICATASTALI.NewDATICATASTALIRow();
                dc2.IdLocalizzazione = loc.ID;
                dc2.CodiceTipoCatasto = "T";
                dc2.TipoCatasto = "F12";
                dc2.Foglio = "12";
                dc2.Particella = "22";
                this._db.ISTANZESTRADARIO.AddISTANZESTRADARIORow(loc);
                this._db.DATICATASTALI.AddDATICATASTALIRow(dc1);
                this._db.DATICATASTALI.AddDATICATASTALIRow(dc2);
                this._adapter.Adapt(this._readInterface, domandaStc);
                Assert.Single(domandaStc.localizzazione);
                Assert.Equal(2, domandaStc.localizzazione[0].riferimentoCatastale.Length);
                Assert.Equal(RiferimentoCatastaleTypeTipoCatasto.EdilizioUrbano, domandaStc.localizzazione[0].riferimentoCatastale[0].tipoCatasto);
                Assert.Equal(dc1.Foglio, domandaStc.localizzazione[0].riferimentoCatastale[0].foglio);
                Assert.Equal(dc1.Particella, domandaStc.localizzazione[0].riferimentoCatastale[0].particella);
                Assert.Equal(dc1.Sub, domandaStc.localizzazione[0].riferimentoCatastale[0].sub);
                Assert.Equal(RiferimentoCatastaleTypeTipoCatasto.Terreni, domandaStc.localizzazione[0].riferimentoCatastale[1].tipoCatasto);
                Assert.Equal(dc2.Foglio, domandaStc.localizzazione[0].riferimentoCatastale[1].foglio);
                Assert.Equal(dc2.Particella, domandaStc.localizzazione[0].riferimentoCatastale[1].particella);
                Assert.Equal(dc2.Sub, domandaStc.localizzazione[0].riferimentoCatastale[1].sub);
            }

            [Fact]
            public void Viene_generata_una_localizzazioneType_per_ogni_localizzazione_della_domanda()
            {
                var domandaStc = new DettaglioPraticaType();
                var loc1 = this._db.ISTANZESTRADARIO.NewISTANZESTRADARIORow();
                loc1.ID = 1;
                loc1.CODICESTRADARIO = 1;
                var loc2 = this._db.ISTANZESTRADARIO.NewISTANZESTRADARIORow();
                loc2.ID = 2;
                loc2.CODICESTRADARIO = 2;
                this._db.ISTANZESTRADARIO.AddISTANZESTRADARIORow(loc1);
                this._db.ISTANZESTRADARIO.AddISTANZESTRADARIORow(loc2);
                this._adapter.Adapt(this._readInterface, domandaStc);
                Assert.Equal(2, domandaStc.localizzazione.Count());
            }
        }

        public class OneriTests
        {
            OneriAdapter _adapter;
            PresentazioneIstanzaDbV2 _db;
            DomandaOnlineReadInterface _readInterface;

            public OneriTests()
            {
                this._adapter = new OneriAdapter();
                this._db = new PresentazioneIstanzaDbV2();
                this._readInterface = new DomandaOnlineReadInterface(PresentazioneIstanzaDataKey.New("E256", "SS", "1", 1), this._db, false);
            }

            [Fact]
            public void Popolamento_degli_importi_pagati()
            {
                var domandaStc = new DettaglioPraticaType();
                var expectedValue = 66.6d;
                var row = this._db.OneriDomanda.NewOneriDomandaRow();
                row.Causale = "Causale";
                row.CodiceCausale = 1;
                row.CodiceInterventoOEndoOrigine = 0;
                row.CodiceTipoPagamento = "CodTipoPagamento";
                row.DataPagmento = "11/11/2014";
                row.DescrizioneTipoPagamento = "Tipo Pagamento";
                row.Importo = Convert.ToSingle(100.0d);
                row.ImportoPagato = Convert.ToSingle(expectedValue);
                row.InterventoOEndoOrigine = "InterventoOEndoOrigine";
                row.Note = "note";
                row.NumeroPagamento = "123";
                row.TipoOnere = "TipoOnere";
                row.ModalitaPagamento = "2";    // GiaPagato
                this._db.OneriDomanda.AddOneriDomandaRow(row);
                this._adapter.Adapt(this._readInterface, domandaStc);
                Assert.Single(domandaStc.oneri);
                Assert.Equal(expectedValue, domandaStc.oneri[0].importo);
                Assert.Single(domandaStc.oneri[0].scadenze);
                Assert.Equal(expectedValue, domandaStc.oneri[0].scadenze[0].importoRata);
                Assert.Single(domandaStc.oneri[0].scadenze[0].pagamenti);
                Assert.Equal(expectedValue, domandaStc.oneri[0].scadenze[0].pagamenti[0].importo);
            }
        }
    }
}
