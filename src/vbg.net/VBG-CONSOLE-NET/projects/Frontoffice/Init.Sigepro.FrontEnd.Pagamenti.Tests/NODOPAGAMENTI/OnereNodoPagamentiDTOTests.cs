using Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Linq;



namespace Init.Sigepro.FrontEnd.Pagamenti.Tests.NODOPAGAMENTI
{
    [TestClass]
    public class OnereNodoPagamentiDTOTests
    {
        // public OnereNodoPagamentiDTO(string uniqueId, string codiceCausale, string descrizione, decimal importo)
        [TestMethod]
        [ExpectedException(typeof(ArgumentException))]
        public void Ctor_uniqueId_non_puo_essere_null()
        {
            new OnereNodoPagamentiDTO(null, "a", "a", 0);
        }

        [TestMethod]
        [ExpectedException(typeof(ArgumentException))]
        public void Ctor_uniqueId_non_puo_essere_vuoto()
        {
            new OnereNodoPagamentiDTO("", "a", "a", 0);
        }

        [TestMethod]
        [ExpectedException(typeof(ArgumentException))]
        public void Ctor_codiceMappatura_non_puo_essere_null()
        {
            new OnereNodoPagamentiDTO("a", null, "a", 0);
        }

        [TestMethod]
        [ExpectedException(typeof(ArgumentException))]
        public void Ctor_codiceMappatura_non_puo_essere_vuoto()
        {
            new OnereNodoPagamentiDTO("a", "", "a", 0);
        }

        [TestMethod]
        [ExpectedException(typeof(ArgumentException))]
        public void Ctor_descrizione_non_puo_essere_null()
        {
            new OnereNodoPagamentiDTO("a", "a", null, 0);
        }

        [TestMethod]
        [ExpectedException(typeof(ArgumentException))]
        public void Ctor_descrizione_non_puo_essere_vuoto()
        {
            new OnereNodoPagamentiDTO("a", "a", "", 0);
        }

        [TestMethod]
        [ExpectedException(typeof(ArgumentException))]
        public void Ctor_importo_non_puo_essere_minore_di_0()
        {
            new OnereNodoPagamentiDTO("a", "a", "a", -1);
        }

        [TestMethod]
        public void Ctor_importo_puo_essere_0()
        {
            new OnereNodoPagamentiDTO("a", "a", "a", 0);
        }

        [TestMethod]
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

            Assert.AreEqual(UniqueId, registrazione.rate[0].riferimentiClient.FirstOrDefault());
            Assert.AreEqual(CodiceMappatura, registrazione.rate[0].importi[0].codiceMappatura);
            Assert.AreEqual(DescrizioneCausale, registrazione.rate[0].descrizione);
            Assert.AreEqual<decimal>(Importo, registrazione.rate[0].importi[0].importo);
        }
    }
}
