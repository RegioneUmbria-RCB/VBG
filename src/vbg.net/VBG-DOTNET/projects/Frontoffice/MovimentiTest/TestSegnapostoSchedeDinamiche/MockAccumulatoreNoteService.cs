using System.Threading.Tasks;
using VBG.DatiDinamici.Web;

namespace Init.Sigepro.FrontEnd.AppLogicTests.TestSegnapostoSchedeDinamiche
{
    public class MockAccumulatoreNoteService : IAccumulatoreNoteModelloService
    {
        public string GetHtml()
        {
            return $"<b>[NOTE-MODELLO]</b>";
        }

        public Task<string> GetHtmlAsync()
        {
            throw new System.NotImplementedException();
        }

        public void Inizializza()
        {
        }
    }
}
