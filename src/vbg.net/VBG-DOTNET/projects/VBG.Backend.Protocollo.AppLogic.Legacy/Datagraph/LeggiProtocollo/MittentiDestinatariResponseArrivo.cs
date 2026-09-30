using System;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Datagraph.LeggiProtocollo
{
    public class MittentiDestinatariResponseArrivo : ILeggiProtoMittentiDestinatari
    {
        string _flusso;
        Mittente _mittente;
        Destinatario _destinatario;

        public MittentiDestinatariResponseArrivo(string flusso, Mittente mittente, Destinatario destinatario)
        {
            this._flusso = flusso;
            this._mittente = mittente;
            this._destinatario = destinatario;
        }

        public string InCaricoA => this._destinatario.Amministrazione.UnitaOrganizzativa != null  ? this._destinatario.Amministrazione.UnitaOrganizzativa.id : "";

        public string InCaricoADescrizione => this._destinatario.Amministrazione.Denominazione;

        public string Flusso => "A";

        public MittDestOutType[] GetMittenteDestinatario()
        {
            return this._mittente.Persona.Select(x => new MittDestOutType
            {
                IdSoggetto = x.id,
                CognomeNome = String.IsNullOrEmpty(x.Denominazione) ? $"{x.Nome} {x.Cognome}" : x.Denominazione
            }).ToArray();
        }
    }
}
