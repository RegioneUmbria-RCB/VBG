using Init.SIGePro.DatiDinamici.WebControls.RenderersRigheModelloDinamico;
using System.Threading.Tasks;
using VBG.DatiDinamici.Web;

namespace Init.Sigepro.FrontEnd.WebForms.GestioneDatiDinamici.NoteModello
{
    public class AccumulatoreNoteModelloFramework : IAccumulatoreNoteModelloService
    {
        public AccumulatoreNoteModelloFramework()
        {

        }

        public void Inizializza()
        {
            AccumulatoreNoteModello.InitContextInstance();
        }

        public string GetHtml()
        {
            var instance = AccumulatoreNoteModello.GetContextInstance();

            if (instance == null)
            {
                return string.Empty;
            }

            return instance.ToHtml();
        }

        public Task<string> GetHtmlAsync()
        {
            throw new System.NotImplementedException();
        }
    }
}