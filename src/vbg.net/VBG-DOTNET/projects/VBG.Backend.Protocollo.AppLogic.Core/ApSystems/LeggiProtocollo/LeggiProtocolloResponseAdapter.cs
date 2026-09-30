using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.ApSystems.LeggiProtocollo
{
    public class LeggiProtocolloResponseAdapter
    {
        public LeggiProtocolloResponseAdapter()
        {

        }

        public List<DatiProtocolloLettoResponseType> Adatta(protocolli response, string formatoData, string formatTime)
        {
            var factory = LeggiProtocolloFactory.Create(response);

            var frmtDataOra = $"{formatoData} {formatTime}";
            var r = response.protocollo[0];

            var retVal = new DatiProtocolloLettoResponseType
            {
                IdProtocollo = r.codice,
                NumeroProtocollo = r.numero,
                DataProtocollo = DateTime.ParseExact(r.data, frmtDataOra, null).ToString("dd/MM/yyyy"),
                AnnoProtocollo = DateTime.ParseExact(r.data, frmtDataOra, null).ToString("yyyy"),
                Oggetto = r.oggetto,
                Classifica_Descrizione = r.classificazione,
                Origine = factory.Flusso,
                InCaricoA = factory.InCaricoA,
                InCaricoA_Descrizione = factory.InCaricoADescrizione,
                MittentiDestinatari = factory.GetMittenteDestinatario()
            };

            if (response.allegato.Rows.Count > 0)
                retVal.Allegati = response.allegato.Select(x => new AllegatoResponseType { IDBase = x.codice, Serial = x.nome }).ToArray();

            return new List<DatiProtocolloLettoResponseType>() { retVal };
        }
    }
}
