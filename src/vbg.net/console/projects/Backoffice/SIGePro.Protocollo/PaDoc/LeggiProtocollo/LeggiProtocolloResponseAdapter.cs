using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.PaDoc.LeggiProtocollo
{
    public class LeggiProtocolloResponseAdapter
    {
        public static DatiProtocolloLetto Adatta(LeggiProtocolloResponseSegnatura response)
        {
            var classifica = String.Format("{0}.{1}", response.risultato.protocollo.titolo, String.IsNullOrEmpty(response.risultato.protocollo.classe) ? "0" : response.risultato.protocollo.classe);

            var factory = LeggiProtocolloMittentiDestinatariFactory.Create(response.risultato.mittente, response.risultato.destinatario, response.risultato.protocollo.tipologia);
            
            string data = "";
            var dataRegistrazione = new DateTime();
            var isDate = DateTime.TryParse(response.risultato.protocollo.data_registrazione, out dataRegistrazione);

            if (isDate)
                data = dataRegistrazione.ToString("dd/MM/yyyy");

            var retval = new DatiProtocolloLetto
            {
                AnnoProtocollo = response.risultato.protocollo.anno,
                DataProtocollo = String.Format("{0} {1}", data, response.risultato.protocollo.ora_registrazione),
                Classifica = classifica,
                Classifica_Descrizione = classifica,
                Oggetto = response.risultato.protocollo.oggetto,
                InCaricoA = factory.InCaricoA,
                InCaricoA_Descrizione = factory.InCaricoADescrizione,
                MittentiDestinatari = factory.GetMittenteDestinatario(),
                NumeroProtocollo = response.risultato.protocollo.numero,
                Origine = factory.Flusso,
                Allegati = response.risultato.allegati.Select(x => new AllOut
                {
                    Commento = x.nome_file,
                    IDBase = x.hash,
                    TipoFile = Path.GetExtension(x.nome_file),
                    ContentType = x.mimetype
                }).ToArray()
            };

            return retval;
        }
    }
}
