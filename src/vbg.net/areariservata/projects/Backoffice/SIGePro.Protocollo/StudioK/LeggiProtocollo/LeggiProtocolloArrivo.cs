using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.StudioK.LeggiProtocollo
{
    public class LeggiProtocolloArrivo : ILeggiProtoMittentiDestinatari
    {
        Persona _mittente;
        Amministrazione _destinatario;

        public LeggiProtocolloArrivo(Persona mittente, Amministrazione destinatario)
        {
            _mittente = mittente;
            _destinatario = destinatario;
        }

        public string InCaricoA
        {
            get 
            {
                return _destinatario.CodiceAmministrazione;
            }
        }

        public string InCaricoADescrizione
        {
            get { return _destinatario.Denominazione; }
        }

        public MittDestOut[] GetMittenteDestinatario()
        {
            return new MittDestOut[] { new MittDestOut { CognomeNome = _mittente.Denominazione } };
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_ARRIVO; }
        }
    }
}
