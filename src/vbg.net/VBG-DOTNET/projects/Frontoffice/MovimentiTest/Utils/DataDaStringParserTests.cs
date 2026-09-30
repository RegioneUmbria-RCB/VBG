using System;
using Init.Sigepro.FrontEnd.AppLogic.Utils;
using Xunit;

namespace Init.Sigepro.FrontEnd.AppLogicTests.Utils
{
    public class DataDaStringParserTests
    {
        [Fact]
        public void restituisce_null_se_data_stringa_vuota()
        {
            var dp = new DataDaStringParser("");
            var result = dp.Parse();
            Assert.False(result.HasValue);
        }

        [Fact]
        public void restituisce_data_se_separatore_barra()
        {
            var dp = new DataDaStringParser("19/03/1979");
            var result = dp.Parse();
            Assert.True(result.HasValue);
            Assert.Equal(new DateTime(1979, 03, 19), result.Value);
        }

        [Fact]
        public void restituisce_data_se_separatore_tratto()
        {
            var dp = new DataDaStringParser("19-03-1979");
            var result = dp.Parse();
            Assert.True(result.HasValue);
            Assert.Equal(new DateTime(1979, 03, 19), result.Value);
        }

        [Fact]
        public void restituisce_null_se_data_non_valida()
        {
            var dp = new DataDaStringParser("asdasdasd");
            var result = dp.Parse();
            Assert.False(result.HasValue);
        }
    }
}
