using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.PagamentiParma
{
    public enum TipologiaCosapParma
    {
        /*
         * 6	2019	Cosap Dehors 
        * 5	2019	Cosap 
        * 30	2019	Cosap Generica 
        */
        Cosap = 5,
        CosapDehors = 6,
        CosapGenerica = 30,
        MezziPubblicitari = 167
    }

    // Il web service utilizza dei codici dversi per la chiamata a pagoPA
    public enum TipologiaCosapParmaPagoPA
    {
        Cosap = 9,
        CosapDehors = 10,
        CosapGenerica = 11,
        Pubblicita = 90,
        MezziPubblicitari = 167
    }
}
