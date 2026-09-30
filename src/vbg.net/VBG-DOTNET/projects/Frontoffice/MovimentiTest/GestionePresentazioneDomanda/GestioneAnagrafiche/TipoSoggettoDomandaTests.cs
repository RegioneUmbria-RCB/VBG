using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Xunit;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogicTests.GestionePresentazioneDomanda.GestioneAnagrafiche
{
    public class TipoSoggettoDomandaTests
    {
        public class EqualityTests
        {
            [Fact]
            public void Equal_operator_test_success()
            {
                var a = new TipoSoggettoDomanda { Id = 1, Descrizione = "descrizione" };
                var b = new TipoSoggettoDomanda { Id = 1, Descrizione = "descrizione" };
                Assert.True(a == b);
            }

            [Fact]
            public void Equal_operator_test_fail()
            {
                var a = new TipoSoggettoDomanda { Id = 1, Descrizione = "descrizione" };
                var b = new TipoSoggettoDomanda { Id = 2, Descrizione = "descrizione2" };
                Assert.False(a == b);
            }

            [Fact]
            public void Equal_operator_test_con_oggetti_null_success()
            {
                TipoSoggettoDomanda a = null;
                TipoSoggettoDomanda b = null;
                Assert.True(a == b);
            }

            [Fact]
            public void Equal_operator_test_con_un_oggetto_null_success()
            {
                TipoSoggettoDomanda a = new TipoSoggettoDomanda { Id = 1, Descrizione = "test" };
                TipoSoggettoDomanda b = null;
                Assert.False(a == b);
            }

            [Fact]
            public void Not_equal_operator_test()
            {
                var a = new TipoSoggettoDomanda { Id = 1, Descrizione = "descrizione" };
                var b = new TipoSoggettoDomanda { Id = 2, Descrizione = "descrizione2" };
                Assert.True(a != b);
            }

            [Fact]
            public void Equals_test_returns_true()
            {
                var a = new TipoSoggettoDomanda { Id = 1, Descrizione = "descrizione" };
                var b = new TipoSoggettoDomanda { Id = 1, Descrizione = "descrizione" };
                Assert.True(a.Equals(b));
            }

            [Fact]
            public void Equals_test_returns_false()
            {
                var a = new TipoSoggettoDomanda { Id = 1, Descrizione = "descrizione" };
                var b = new TipoSoggettoDomanda { Id = 2, Descrizione = "descrizione2" };
                Assert.False(a.Equals(b));
            }
        }
    }
}
