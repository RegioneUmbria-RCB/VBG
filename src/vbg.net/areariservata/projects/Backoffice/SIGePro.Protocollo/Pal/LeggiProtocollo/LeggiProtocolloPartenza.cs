using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Pal.Organigramma;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Pal.LeggiProtocollo
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

        public MittDestOut[] GetMittenteDestinatario()
        {
            return this._response.Intestazione.Destinatari.Destinatario.Select(x => new MittDestOut
            {
                CognomeNome = x.Denominazione
            }).ToArray();
        }
    }
}
