using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici.LetturaDaVisuraSigepro
{
    public class VisuraModelloDinamicoRiepilogo : IModelloDinamicoRiepilogo
    {
        public int IdModello { get; internal set; }
        public bool Compilato { get; internal set; }
        public ModelloDinamico.TipoFirmaEnum TipoFirma { get; internal set; }
        public string Descrizione { get; internal set; }
        public int Ordine { get; set; }
    }
}
