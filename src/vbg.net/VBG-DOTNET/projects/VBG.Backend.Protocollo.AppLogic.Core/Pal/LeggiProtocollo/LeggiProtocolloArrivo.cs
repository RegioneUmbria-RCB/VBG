using VBG.Backend.Protocollo.AppLogic.Core.Pal.Organigramma;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Pal.LeggiProtocollo
{
    public class LeggiProtocolloArrivo : ILeggiProtoMittentiDestinatari
    {
        ProtocollazioneType _response;
        OrganigrammaServiceWrapper _organigrammaService;
        string _idOrganigramma = "";

        public LeggiProtocolloArrivo(ProtocollazioneType response, OrganigrammaServiceWrapper organigrammaService)
        {
            this._response = response;
            this._organigrammaService = organigrammaService;

            if (_response.Intestazione.Assegnatari != null && _response.Intestazione.Assegnatari.Assegnatario != null && _response.Intestazione.Assegnatari.Assegnatario.Item is SettoreType)
            {
                this._idOrganigramma = ((SettoreType)_response.Intestazione.Assegnatari.Assegnatario.Item).Organigramma;
            }
        }

        public string InCaricoA
        {
            get
            {
                return this._idOrganigramma;
            }
        }

        public string InCaricoADescrizione
        {
            get
            {
                if (!String.IsNullOrEmpty(this._idOrganigramma))
                {
                    var org = this._organigrammaService.GetOrganigramma(this._idOrganigramma);
                    if (org != null)
                    {
                        return org.descrizione;
                    }
                }
                return "";
            }
        }

        public string Flusso => ProtocolloConstants.COD_ARRIVO;

        public MittDestOutType[] GetMittenteDestinatario()
        {
            return this._response.Intestazione.Mittenti.Select(x => new MittDestOutType
            {
                CognomeNome = ((MittenteEsternoType)x.Item).Denominazione
            }).ToArray();
        }
    }
}
