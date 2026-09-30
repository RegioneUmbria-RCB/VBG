using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Xunit;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;

namespace MovimentiTest.GestionePresentazioneDomanda.GestioneLocalizzazioni
{
    public class LocalizzazioniTests
    {
        public class LocalizzazioniWriteInterfaceTests
        {
            private NuovaLocalizzazione CreaIndirizzo()
            {
                return new NuovaLocalizzazione(1, "indirizzo", "civico");
            }

            PresentazioneIstanzaDbV2 _db;
            ILocalizzazioniWriteInterface _writeInterface;

            public LocalizzazioniWriteInterfaceTests()
            {
                this._db = new PresentazioneIstanzaDbV2();
                this._writeInterface = new LocalizzazioniWriteInterface(_db);
                var r = this._db.ISTANZE.NewISTANZERow();
                r.CODICECOMUNE = "E256";
                this._db.ISTANZE.AddISTANZERow(r);
            }

            [Fact]
            public void Su_creazione_localizzazione_viene_popolato_il_suo_uuid()
            {
                this._writeInterface.AggiungiLocalizzazione(CreaIndirizzo());
                Assert.Equal(1, this._db.ISTANZESTRADARIO.Count);
                Assert.False(this._db.ISTANZESTRADARIO[0].IsUuidNull());
                Assert.False(string.IsNullOrEmpty(this._db.ISTANZESTRADARIO[0].Uuid));
            }

            [Fact]
            public void Su_creazione_localizzazione_se_impostato_tipo_localizzazione_viene_salvato_su_db()
            {
                var indirizzo = CreaIndirizzo();
                indirizzo.TipoLocalizzazione = "TipoLocalizzazione";
                this._writeInterface.AggiungiLocalizzazione(indirizzo);
                Assert.Equal(1, this._db.ISTANZESTRADARIO.Count);
                Assert.False(string.IsNullOrEmpty(this._db.ISTANZESTRADARIO[0].TipoLocalizzazione));
                Assert.Equal(indirizzo.TipoLocalizzazione, this._db.ISTANZESTRADARIO[0].TipoLocalizzazione);
            }

            [Fact]
            public void Su_creazione_localizzazione_latitudine_e_longitudine_vengono_salvate_su_db()
            {
                var indirizzo = CreaIndirizzo();
                indirizzo.Latitudine = "Latitudine";
                indirizzo.Longitudine = "Longitudine";
                this._writeInterface.AggiungiLocalizzazione(indirizzo);
                Assert.Equal(1, this._db.ISTANZESTRADARIO.Count);
                Assert.False(string.IsNullOrEmpty(this._db.ISTANZESTRADARIO[0].Latitudine));
                Assert.False(string.IsNullOrEmpty(this._db.ISTANZESTRADARIO[0].Longitudine));
                Assert.Equal(indirizzo.Latitudine, this._db.ISTANZESTRADARIO[0].Latitudine);
                Assert.Equal(indirizzo.Longitudine, this._db.ISTANZESTRADARIO[0].Longitudine);
            }

            [Fact]
            public void Su_creazione_localizzazione_con_mappali_i_mappali_vengono_salvati_su_db()
            {
                var indirizzo = CreaIndirizzo();
                this._writeInterface.AggiungiLocalizzazione(indirizzo);
                var codiceIndirizzo = this._db.ISTANZESTRADARIO[0].ID;
                var datiCatastali1 = new NuovoRiferimentoCatastale("F", "F1", "1", "2", "3");
                this._writeInterface.AssegnaRiferimentiCatastaliALocalizzazione(codiceIndirizzo, datiCatastali1);
                Assert.Equal(1, this._db.DATICATASTALI.Count);
                Assert.Equal(codiceIndirizzo, this._db.DATICATASTALI[0].IdLocalizzazione);
                Assert.Equal(datiCatastali1.TipoCatasto, this._db.DATICATASTALI[0].TipoCatasto);
                Assert.Equal(datiCatastali1.CodiceTipoCatasto, this._db.DATICATASTALI[0].CodiceTipoCatasto);
                Assert.Equal(datiCatastali1.Foglio, this._db.DATICATASTALI[0].Foglio);
                Assert.Equal(datiCatastali1.Particella, this._db.DATICATASTALI[0].Particella);
                Assert.Equal(datiCatastali1.Sub, this._db.DATICATASTALI[0].Sub);
            }

            [Fact]
            public void Si_possono_aggiungere_due_riferimenti_catastali_allo_stesso_indirizzo()
            {
                var indirizzo = CreaIndirizzo();
                this._writeInterface.AggiungiLocalizzazione(indirizzo);
                var codiceIndirizzo = this._db.ISTANZESTRADARIO[0].ID;
                var datiCatastali1 = new NuovoRiferimentoCatastale("F", "F1", "1", "2", "3");
                var datiCatastali2 = new NuovoRiferimentoCatastale("F", "F1", "1", "2", "3");
                this._writeInterface.AssegnaRiferimentiCatastaliALocalizzazione(codiceIndirizzo, datiCatastali1);
                this._writeInterface.AssegnaRiferimentiCatastaliALocalizzazione(codiceIndirizzo, datiCatastali2);
                Assert.Equal(codiceIndirizzo, this._db.DATICATASTALI[0].IdLocalizzazione);
                Assert.Equal(codiceIndirizzo, this._db.DATICATASTALI[1].IdLocalizzazione);
            }

            [Fact]
            public void Si_puo_creare_una_localizzazione_con_i_riferimenti_catastali_in_una_sola_chiamata()
            {
                var datiCatastali = new NuovoRiferimentoCatastale("F", "F1", "1", "2", "3");
                this._writeInterface.AggiungiLocalizzazioneConRiferimentiCatastali(CreaIndirizzo(), datiCatastali);
                var idLocalizzazione = this._db.ISTANZESTRADARIO[0].ID;
                Assert.Equal(1, this._db.DATICATASTALI.Count);
                Assert.Equal(idLocalizzazione, this._db.DATICATASTALI[0].IdLocalizzazione);
                Assert.Equal(datiCatastali.TipoCatasto, this._db.DATICATASTALI[0].TipoCatasto);
                Assert.Equal(datiCatastali.CodiceTipoCatasto, this._db.DATICATASTALI[0].CodiceTipoCatasto);
                Assert.Equal(datiCatastali.Foglio, this._db.DATICATASTALI[0].Foglio);
                Assert.Equal(datiCatastali.Particella, this._db.DATICATASTALI[0].Particella);
                Assert.Equal(datiCatastali.Sub, this._db.DATICATASTALI[0].Sub);
            }

            [Fact]
            public void L_eliminazione_di_una_localizzazione_elimina_anche_i_relativi_dati_catastali()
            {
                var datiCatastali = new NuovoRiferimentoCatastale("F", "F1", "1", "2", "3");
                this._writeInterface.AggiungiLocalizzazioneConRiferimentiCatastali(CreaIndirizzo(), datiCatastali);
                var idLocalizzazione = this._db.ISTANZESTRADARIO[0].ID;
                this._writeInterface.EliminaLocalizzazione(idLocalizzazione);
                Assert.Equal(0, this._db.ISTANZESTRADARIO.Count);
                Assert.Equal(0, this._db.DATICATASTALI.Count);
            }
        }
    }
}
