using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Xunit;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogicTests.GestioneVisuraIstanza
{
    public class LivelloAccessoVisuraTests
    {
        private static class Constants
        {
            public const int DatiGenerali = 1 << 0;
            public const int Schede = 1 << 1;
            public const int Documenti = 1 << 2;
            public const int Endoprocedimenti = 1 << 3;
            public const int Oneri = 1 << 4;
            public const int MovimentiEffettuati = 1 << 5;
            public const int Autorizzazioni = 1 << 6;
        }

        [Fact]
        public void AccessoDatiGenerali()
        {
            var livello = LivelloAccessoVisura.DaValoreFlag(Constants.DatiGenerali);
            Assert.True(livello.DatiGenerali);
        }

        [Fact]
        public void AccessoSchede()
        {
            var livello = LivelloAccessoVisura.DaValoreFlag(Constants.Schede);
            Assert.True(livello.Schede);
        }

        [Fact]
        public void AccessoDocumenti()
        {
            var livello = LivelloAccessoVisura.DaValoreFlag(Constants.Documenti);
            Assert.True(livello.Documenti);
        }

        [Fact]
        public void AccessoEndoprocedimenti()
        {
            var livello = LivelloAccessoVisura.DaValoreFlag(Constants.Endoprocedimenti);
            Assert.True(livello.Endoprocedimenti);
        }

        [Fact]
        public void AccessoOneri()
        {
            var livello = LivelloAccessoVisura.DaValoreFlag(Constants.Oneri);
            Assert.True(livello.Oneri);
        }

        [Fact]
        public void AccessoMovimentiEffettuati()
        {
            var livello = LivelloAccessoVisura.DaValoreFlag(Constants.MovimentiEffettuati);
            Assert.True(livello.MovimentiEffettuati);
        }

        [Fact]
        public void AccessoAutorizzazioni()
        {
            var livello = LivelloAccessoVisura.DaValoreFlag(Constants.Autorizzazioni);
            Assert.True(livello.Autorizzazioni);
        }

        [Fact]
        public void AccessoCompleto()
        {
            var livello = LivelloAccessoVisura.Completo;
            Assert.True(livello.DatiGenerali);
            Assert.True(livello.Schede);
            Assert.True(livello.Documenti);
            Assert.True(livello.Endoprocedimenti);
            Assert.True(livello.Oneri);
            Assert.True(livello.MovimentiEffettuati);
            Assert.True(livello.Autorizzazioni);
        }

        [Fact]
        public void Verifica_operatore_di_uguaglianza()
        {
            var v1 = LivelloAccessoVisura.DaValoreFlag(Constants.Autorizzazioni + Constants.Documenti);
            var v2 = LivelloAccessoVisura.DaValoreFlag(Constants.Documenti + Constants.Autorizzazioni);
            var result = v1 == v2;
            Assert.True(result);
            v2 = LivelloAccessoVisura.DaValoreFlag(Constants.Autorizzazioni);
            result = v1 == v2;
            Assert.False(result);
            result = v1 != v2;
            Assert.True(result);
        }

        [Fact]
        public void TestSerializzazione_e_deserializzazione()
        {
            var v1 = LivelloAccessoVisura.AccessoAnonimo;
            var code = v1.ToSerializationCode();
            var v2 = LivelloAccessoVisura.FromSerializationCode(code);
            var result = v1 == v2;
            Assert.True(result);
        }
    }
}
