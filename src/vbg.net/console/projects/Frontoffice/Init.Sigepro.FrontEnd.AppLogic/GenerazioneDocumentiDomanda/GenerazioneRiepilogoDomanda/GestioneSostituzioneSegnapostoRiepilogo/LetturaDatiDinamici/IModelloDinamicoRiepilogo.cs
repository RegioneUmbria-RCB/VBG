using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using static Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici.ModelloDinamico;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici
{
    public interface IModelloDinamicoRiepilogo
    {
        int IdModello { get; }
        bool Compilato { get; }
        TipoFirmaEnum TipoFirma { get; }
        string Descrizione { get; }
    }
}
