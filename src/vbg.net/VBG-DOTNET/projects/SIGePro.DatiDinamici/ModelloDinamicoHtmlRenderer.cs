using Init.SIGePro.DatiDinamici.WebControls;
using System.IO;
using System.Threading.Tasks;
using System.Web.UI;
using VBG.DatiDinamici;
using VBG.DatiDinamici.WebControls.MaschereCampiNonVisibili;

namespace Init.SIGePro.DatiDinamici
{
    public class ModelloDinamicoHtmlRenderer : IModelloDinamicoHtmlRenderer
    {
        public ModelloDinamicoHtmlRenderer()
        {
        }

        public string GetHtml(ModelloDinamicoBase modelloDinamico, ICampiNonVisibili campiNonVisibili = null)
        {

            if (campiNonVisibili == null)
                campiNonVisibili = CampiNonVisibili.TuttiICampiVisibili;

            var renderer = new ModelloDinamicoRenderer
            {
                ID = "renderer",
                ReadOnly = true,
                DataSource = modelloDinamico,
                CampiNascosti = campiNonVisibili
            };

            renderer.DataBind();

            var stringWriter = new StringWriter();
            var tw = new HtmlTextWriter(stringWriter);

            renderer.RenderControl(tw);
            renderer.CleanupSession();  // Necessario chiamarlo per evitare che vengano fatti persistere in session
                                        // alcuni dati (principalmente la visibilità campi ma potrebbero essercene altri)

            return stringWriter.ToString();
        }

        public Task<string> GetHtmlAsync(ModelloDinamicoBase modelloDinamico, ICampiNonVisibili campiNonVisibili = null)
        {
            return Task.FromResult(this.GetHtml(modelloDinamico, campiNonVisibili));
        }
    }
}
