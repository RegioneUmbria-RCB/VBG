using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using System;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.LeggiProtocollo
{
    public class IdProtocolloAdapter
    {
        string _idProtocollo;
        string _separatore;

        public IdProtocolloAdapter(string idProtocollo, string separatore)
        {
            _idProtocollo = idProtocollo;
            _separatore = separatore;
        }

        public IdRegistrazioneProtocollo Adatta()
        {
            if (String.IsNullOrEmpty(_idProtocollo))
                throw new Exception("L'ID DEL PROTOCOLLO NON E' VALORIZZATO, NON E' POSSIBILE LEGGERE IL PROTOCOLLO");

            var arrIdProtocollo = _idProtocollo.Split(_separatore.ToCharArray());
            if (arrIdProtocollo.Length == 1)
                throw new Exception(String.Format("L'ID DEL PROTOCOLLO DEVE CONTENERE IL PROGDOC E IL PROGMOVI SEPARATI DA UN PUNTO E VIRGOLA"));

            return new IdRegistrazioneProtocollo()
            {
                ProgressivoDocumento = arrIdProtocollo[0],
                ProgressivoMovimento = arrIdProtocollo[1]
            };
        }
    }
}
