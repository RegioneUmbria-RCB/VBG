using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Core.DocEr.Verticalizzazioni;

namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.ProtocollazioneRegistrazione
{
    public class ClassificaAdapter
    {
        string _classifica;
        VerticalizzazioniConfiguration _vert;

        public ClassificaAdapter(string classifica, VerticalizzazioniConfiguration vert)
        {
            _classifica = classifica;
            _vert = vert;
        }

        public ClassificaType Adatta()
        {
            return new ClassificaType
            {
                Classifica = _classifica,
                CodiceAmministrazione = _vert.CodiceAmministrazione,
                CodiceAOO = _vert.CodiceAoo
            };
        }
    }
}
