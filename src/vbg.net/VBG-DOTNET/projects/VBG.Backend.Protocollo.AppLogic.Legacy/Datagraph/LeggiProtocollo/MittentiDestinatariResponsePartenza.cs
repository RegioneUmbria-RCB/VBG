using System;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Datagraph.LeggiProtocollo
{
    public class MittentiDestinatariResponsePartenza : ILeggiProtoMittentiDestinatari
    {
        string _flusso;
        Mittente _mittente;
        Destinatario _destinatario;

        public MittentiDestinatariResponsePartenza(string flusso, Mittente mittente, Destinatario destinatario)
        {
            this._flusso = flusso;
            this._mittente = mittente;
            this._destinatario = destinatario;
        }

        public string InCaricoA => this._mittente.Amministrazione.UnitaOrganizzativa != null ? this._mittente.Amministrazione.UnitaOrganizzativa.id : "";

        public string InCaricoADescrizione => this._mittente.Amministrazione.Denominazione;

        public string Flusso => "P";

        public MittDestOutType[] GetMittenteDestinatario()
        {
            return this._destinatario.Persona.Select(x => new MittDestOutType
            {
                IdSoggetto = x.id,
                CognomeNome = String.IsNullOrEmpty(x.Denominazione) ? $"{x.Nome} {x.Cognome}" : x.Denominazione
            }).ToArray();
        }
    }
}
