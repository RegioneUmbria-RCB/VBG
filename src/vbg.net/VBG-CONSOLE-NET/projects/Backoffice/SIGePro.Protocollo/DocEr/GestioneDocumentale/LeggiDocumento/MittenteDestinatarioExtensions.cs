using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiDocumento.Persone;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiDocumento
{
    public static class MittenteDestinatarioExtensions
    {
        public static MittDestOut ToMittenteDestinatario(this MittDestType mittDest)
        {
            var factory = PersonaFisicaGiuridicaFactory.Create(mittDest.Items[0]);
            return factory.GetPersona();
        }
    }
}
