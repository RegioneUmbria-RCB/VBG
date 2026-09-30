using VBG.DatiDinamici.Interfaces;
using VisuraVbg;

namespace GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghiSchede
{
    public class ClassLoader : IClasseContestoLoader
    {
        private readonly Istanze _istanza;

        public ClassLoader(Istanze istanza)
        {
            this._istanza = istanza;
        }

        public IClasseContestoModelloDinamico LoadClass()
        {
            return this._istanza;
        }
    }
}
