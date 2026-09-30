using VBG.Backend.Protocollo.AppLogic.Core.Pal.Organigramma;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Pal.LeggiProtocollo
{
    public class LeggiProtocolloInterno : ILeggiProtoMittentiDestinatari
    {
        ProtocollazioneType _response;
        OrganigrammaServiceWrapper _organigrammaService;
        string _idOrganigrammaDestinatario = "";
        string _descrizioneOrganigrammaDestinatario = "";

        string _idOrganigrammaMittente = "";
        string _descrizioneOrganigrammaMittente = "";

        public LeggiProtocolloInterno(ProtocollazioneType response, OrganigrammaServiceWrapper organigrammaService)
        {
            this._response = response;
            this._organigrammaService = organigrammaService;

            if (_response.Intestazione.Assegnatari != null && _response.Intestazione.Assegnatari.Assegnatario != null && _response.Intestazione.Assegnatari.Assegnatario.Item is SettoreType)
            {
                this._idOrganigrammaDestinatario = ((SettoreType)_response.Intestazione.Assegnatari.Assegnatario.Item).Organigramma;
                if (!String.IsNullOrEmpty(this._idOrganigrammaDestinatario))
                {
                    var org = this._organigrammaService.GetOrganigramma(this._idOrganigrammaDestinatario);
                    if (org != null)
                    {
                        this._descrizioneOrganigrammaDestinatario = org.descrizione;
                    }
                }
            }

            if (this._response.Intestazione.Mittenti != null && this._response.Intestazione.Mittenti.Count() > 0 && this._response.Intestazione.Mittenti[0].Item != null)
            {
                this._idOrganigrammaMittente = ((SettoreType)((MittenteInternoType)this._response.Intestazione.Mittenti[0].Item).Item).Organigramma;
                if (!String.IsNullOrEmpty(this._idOrganigrammaMittente))
                {
                    var org = this._organigrammaService.GetOrganigramma(this._idOrganigrammaMittente);
                    if (org != null)
                    {
                        this._descrizioneOrganigrammaMittente = org.descrizione;
                    }
                }
            }
        }

        public string InCaricoA => this._idOrganigrammaMittente;

        public string InCaricoADescrizione
        {
            get
            {
                return this._descrizioneOrganigrammaMittente;
            }
        }

        public string Flusso => ProtocolloConstants.COD_INTERNO;

        public MittDestOutType[] GetMittenteDestinatario()
        {
            return new MittDestOutType[]
            {
                new MittDestOutType
                {
                    IdSoggetto = this._idOrganigrammaDestinatario,
                    CognomeNome = this._descrizioneOrganigrammaDestinatario
                }
            };
        }
    }
}
