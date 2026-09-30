using System;
using System.Linq;
using VBG.Pagamenti.NodoPagamenti;
using Xunit;

namespace Init.Sigepro.FrontEnd.Pagamenti.Tests.NODOPAGAMENTI
{
    public class OnereNodoPagamentiDTOTests
    {
        [Fact]
        public void Ctor_uniqueId_non_puo_essere_null()
        {
            Assert.Throws<ArgumentException>(() => new OnereNodoPagamentiDTO(null, "a", "a", 0));
        }

        [Fact]
        public void Ctor_uniqueId_non_puo_essere_vuoto()
        {
            Assert.Throws<ArgumentException>(() => new OnereNodoPagamentiDTO("", "a", "a", 0));
        }

        [Fact]
        public void Ctor_codiceMappatura_non_puo_essere_null()
        {
            Assert.Throws<ArgumentException>(() => new OnereNodoPagamentiDTO("a", null, "a", 0));
        }

        [Fact]
        public void Ctor_codiceMappatura_non_puo_essere_vuoto()
        {
            Assert.Throws<ArgumentException>(() => new OnereNodoPagamentiDTO("a", "", "a", 0));
        }

        [Fact]
        public void Ctor_descrizione_non_puo_essere_null()
        {
            Assert.Throws<ArgumentException>(() => new OnereNodoPagamentiDTO("a", "a", null, 0));
        }

        [Fact]
        public void Ctor_descrizione_non_puo_essere_vuoto()
        {
            Assert.Throws<ArgumentException>(() => new OnereNodoPagamentiDTO("a", "a", "", 0));
        }

        [Fact]
        public void Ctor_importo_non_puo_essere_minore_di_0()
        {
            Assert.Throws<ArgumentException>(() => new OnereNodoPagamentiDTO("a", "a", "a", -1));
        }

        [Fact]
        public void Ctor_importo_puo_essere_0()
        {
            new OnereNodoPagamentiDTO("a", "a", "a", 0);
        }

        [Fact]
        public void Ctor_inizializzazione_argomenti_corretta()
        {
            var UniqueId = "uniqueId";
            var CodiceMappatura = "CodiceMappatura";
            var DescrizioneCausale = "descrizione";
            var Importo = 123;
            var idPratica = "123456789";
            DateTime dataScadenza = new DateTime(2022, 1, 1, 0, 0, 0);
            SoggettoDebitoreType sogg = new SoggettoDebitoreType
            {

            };

            var onere = new OnereNodoPagamentiDTO(UniqueId, CodiceMappatura, DescrizioneCausale, Importo);
            var registrazione = onere.ToRegistrazioneContabileType(sogg, idPratica, dataScadenza);

            Assert.Equal(UniqueId, registrazione.rate[0].riferimentiClient.FirstOrDefault());
            Assert.Equal(CodiceMappatura, registrazione.rate[0].importi[0].codiceMappatura);
            Assert.Equal(DescrizioneCausale, registrazione.rate[0].descrizione);
            Assert.Equal(Importo, registrazione.rate[0].importi[0].importo);
        }
    }
}
