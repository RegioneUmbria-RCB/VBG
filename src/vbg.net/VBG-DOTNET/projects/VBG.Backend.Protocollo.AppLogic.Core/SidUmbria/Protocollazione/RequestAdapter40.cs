using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;

namespace VBG.Backend.Protocollo.AppLogic.Core.SidUmbria.Protocollazione
{
    public class RequestAdapter40 : RequestAdapterBase, IRequestAdapter
    {
        public string Token
        {
            get
            {
                return _uo[0];
            }
        }

        public string Service
        {
            get
            {
                return _ruolo[0];
            }
        }

        List<string> _uo;
        List<string> _ruolo;

        public RequestAdapter40(IDatiProtocollo datiProto, VerticalizzazioniConfiguration vert, ResolveDatiProtocollazioneService datiIstanzaService, TipoProvenienza provenienza) : base(datiProto, vert, datiIstanzaService, provenienza)
        {
            this._uo = _datiProto.Uo.Split('|').ToList();
            this._ruolo = _datiProto.Ruolo.Split('|').ToList();
        }

        public infoProtocollo Adatta()
        {
            var request = base.AdattaDatiBase();

            request.ftp = _vert.UsaFtp;

            if (_uo.Count() == 2)
            {
                request.UORDestinataria = new uorDestinataria { idipa = _uo[1], idinterno = _ruolo[1] };
            }

            return request;
        }
    }
}
