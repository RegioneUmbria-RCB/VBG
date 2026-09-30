using VBG.Backend.SIT.AppLogic.SilverBrowser.SilverBrowserClasses;
using Xunit;

namespace VBG.Backend.SIT.Tests.SilverBrowser
{
    public class CodiceViarioTests
    {
        [Fact]
        public void Id_stringa_a_numero()
        {
            var codViario = new CodiceViario("E25600000012");
            var expected = "12";
            var result = codViario.ToString();

            Assert.Equal(expected, result);
        }
    }
}
