using VBG.Backend.Protocollo.AppLogic.Core.Pal.Organigramma;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Pal.LeggiProtocollo
{
    public class LeggiProtocolloPartenza : ILeggiProtoMittentiDestinatari
    {
        ProtocollazioneType _response;
        OrganigrammaServiceWrapper _organigrammaService;
        string _idOrganigramma = "";

        public LeggiProtocolloPartenza(ProtocollazioneType response, OrganigrammaServiceWrapper organigrammaService)
        {
            this._response = response;
            this._organigrammaService = organigrammaService;

            if (response.Intestazione.Mittenti != null && response.Intestazione.Mittenti.Count() > 0 && response.Intestazione.Mittenti.First().Item is MittenteInternoType)
            {
                this._idOrganigramma = ((SettoreType)((MittenteInternoType)response.Intestazione.Mittenti.First().Item).Item).Organigramma;
            }
        }

        public string InCaricoA => this._idOrganigramma;

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

        public string Flusso => ProtocolloConstants.COD_PARTENZA;

        public MittDestOutType[] GetMittenteDestinatario()
        {
            return this._response.Intestazione.Destinatari.Destinatario.Select(x => new MittDestOutType
            {
                CognomeNome = x.Denominazione
            }).ToArray();
        }
    }
}
