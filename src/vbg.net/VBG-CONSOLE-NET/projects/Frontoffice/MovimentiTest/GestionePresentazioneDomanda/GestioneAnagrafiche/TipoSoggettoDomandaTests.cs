using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogicTests.GestionePresentazioneDomanda.GestioneAnagrafiche
{
    public class TipoSoggettoDomandaTests
    {
        [TestClass]
        public class EqualityTests
        {
            [TestMethod]
            public void Equal_operator_test_success()
            {
                var a = new TipoSoggettoDomanda
                {
                    Id = 1,
                    Descrizione = "descrizione"
                };

                var b = new TipoSoggettoDomanda
                {
                    Id = 1,
                    Descrizione = "descrizione"
                };

                Assert.IsTrue(a == b);
            }

            [TestMethod]
            public void Equal_operator_test_fail()
            {
                var a = new TipoSoggettoDomanda
                {
                    Id = 1,
                    Descrizione = "descrizione"
                };

                var b = new TipoSoggettoDomanda
                {
                    Id = 2,
                    Descrizione = "descrizione2"
                };

                Assert.IsFalse(a == b);

            }

            [TestMethod]
            public void Equal_operator_test_con_oggetti_null_success()
            {
                TipoSoggettoDomanda a = null;
                TipoSoggettoDomanda b = null;

                Assert.IsTrue(a == b);
            }

            [TestMethod]
            public void Equal_operator_test_con_un_oggetto_null_success()
            {
                TipoSoggettoDomanda a = new TipoSoggettoDomanda { Id = 1, Descrizione = "test" };
                TipoSoggettoDomanda b = null;

                Assert.IsFalse(a == b);

            }

            [TestMethod]
            public void Not_equal_operator_test()
            {
                var a = new TipoSoggettoDomanda
                {
                    Id = 1,
                    Descrizione = "descrizione"
                };

                var b = new TipoSoggettoDomanda
                {
                    Id = 2,
                    Descrizione = "descrizione2"
                };

                Assert.IsTrue(a != b);
            }

            [TestMethod]
            public void Equals_test_returns_true()
            {
                var a = new TipoSoggettoDomanda
                {
                    Id = 1,
                    Descrizione = "descrizione"
                };

                var b = new TipoSoggettoDomanda
                {
                    Id = 1,
                    Descrizione = "descrizione"
                };

                Assert.IsTrue(a.Equals(b));
            }

            [TestMethod]
            public void Equals_test_returns_false()
            {
                var a = new TipoSoggettoDomanda
                {
                    Id = 1,
                    Descrizione = "descrizione"
                };

                var b = new TipoSoggettoDomanda
                {
                    Id = 2,
                    Descrizione = "descrizione2"
                };

                Assert.IsFalse(a.Equals(b));
            }
        }
    }
}
