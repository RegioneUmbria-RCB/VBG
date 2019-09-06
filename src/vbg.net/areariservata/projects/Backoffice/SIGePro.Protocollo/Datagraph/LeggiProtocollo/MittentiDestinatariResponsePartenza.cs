using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloDatagraphService;

namespace Init.SIGePro.Protocollo.Datagraph.LeggiProtocollo
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

        public MittDestOut[] GetMittenteDestinatario()
        {
            return this._destinatario.Persona.Select(x => new MittDestOut
            {
                IdSoggetto = x.id,
                CognomeNome = String.IsNullOrEmpty(x.Denominazione) ? $"{x.Nome} {x.Cognome}" : x.Denominazione
            }).ToArray();
        }
    }
}
