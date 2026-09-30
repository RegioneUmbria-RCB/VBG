using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.PaDoc.LeggiProtocollo
{
    public class LeggiProtocolloResponseAdapter
    {
        public static DatiProtocolloLettoResponseType Adatta(LeggiProtocolloResponseSegnatura response)
        {
            var classifica = String.Format("{0}.{1}", response.risultato.protocollo.titolo, String.IsNullOrEmpty(response.risultato.protocollo.classe) ? "0" : response.risultato.protocollo.classe);

            var factory = LeggiProtocolloMittentiDestinatariFactory.Create(response.risultato.mittente, response.risultato.destinatario, response.risultato.protocollo.tipologia);

            string data = "";
            var dataRegistrazione = new DateTime();
            var isDate = DateTime.TryParse(response.risultato.protocollo.data_registrazione, out dataRegistrazione);

            if (isDate)
                data = dataRegistrazione.ToString("dd/MM/yyyy");

            var retval = new DatiProtocolloLettoResponseType
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
                Allegati = response.risultato.allegati.Select(x => new AllegatoResponseType
                {
                    Commento = x.nome_file,
                    IDBase = x.node_id.Substring(x.node_id.LastIndexOf('/') + 1),
                    TipoFile = Path.GetExtension(x.nome_file),
                    ContentType = x.mimetype
                }).ToArray()
            };

            return retval;
        }

    }
}
