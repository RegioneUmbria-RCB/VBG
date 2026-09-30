using Microsoft.AspNetCore.Components;
using Microsoft.AspNetCore.Components.Web;
using VBG.DatiDinamici.Agid.WebControls;
using VBG.DatiDinamici.Agid.WebControls.NoteCompilazione;
using VBG.DatiDinamici.WebControls.MaschereCampiNonVisibili;

namespace VBG.DatiDinamici.Agid.HtmlRendering
{
    public class CoreModelloDinamicoHtmlRenderer : IModelloDinamicoHtmlRenderer
    {
        private readonly HtmlRenderer _htmlRenderer;
        private readonly NoteModelloService _noteModelloService;
        private readonly IServiceProvider _serviceProvider;

        public CoreModelloDinamicoHtmlRenderer(HtmlRenderer htmlRenderer, NoteModelloService noteModelloService, IServiceProvider serviceProvider)
        {
            this._htmlRenderer = htmlRenderer;
            this._noteModelloService = noteModelloService;
            this._serviceProvider = serviceProvider;
        }

        // In teoria sotto core questo metodo non dovrebbe mai essere chiamato
        public string GetHtml(ModelloDinamicoBase modelloDinamico, ICampiNonVisibili campiNonVisibili = default!)
        {
            var t = GetHtmlAsync(modelloDinamico, campiNonVisibili);

#pragma warning disable VSTHRD002 // Avoid problematic synchronous waits
            t.Wait();

            return t.Result;
#pragma warning restore VSTHRD002 // Avoid problematic synchronous waits
        }

        public Task<string> GetHtmlAsync(ModelloDinamicoBase modelloDinamico, ICampiNonVisibili? campiNonVisibili = null)
        {
            if (campiNonVisibili == null)
                campiNonVisibili = CampiNonVisibili.TuttiICampiVisibili;

            var parDictionary = new Dictionary<string, object?>()
            {
                { nameof(ModelloDinamicoReadonlyRenderer.Modello), modelloDinamico},
                { nameof(ModelloDinamicoReadonlyRenderer.CampiNascosti), campiNonVisibili},
                { nameof(ModelloDinamicoReadonlyRenderer.NoteModelloService), _noteModelloService},
            };

            return _htmlRenderer.Dispatcher.InvokeAsync(async () =>
            {
                var output = await _htmlRenderer.RenderComponentAsync<ModelloDinamicoReadonlyRenderer>(ParameterView.FromDictionary(parDictionary));

                return output.ToHtmlString();
            });
        }
    }
}
