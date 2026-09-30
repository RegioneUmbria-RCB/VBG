using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using System;
using System.Collections.Generic;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    public class ListaSegnapostoRiepilogoDomanda
    {
        private readonly List<ISegnapostoRiepilogo> _items = new List<ISegnapostoRiepilogo>();
        public SegnapostoNoteCampi SegnapostoNoteCampi { get; }

        public ListaSegnapostoRiepilogoDomanda(SegnapostoNoteCampi segnapostoNoteCampi)
        {
            this.SegnapostoNoteCampi = segnapostoNoteCampi;
        }

        internal IEnumerable<ISegnapostoRiepilogo> Items => this._items;

        internal void Add(ISegnapostoRiepilogo segnaposto)
        {
            this._items.Add(segnaposto);
        }
    }
}
