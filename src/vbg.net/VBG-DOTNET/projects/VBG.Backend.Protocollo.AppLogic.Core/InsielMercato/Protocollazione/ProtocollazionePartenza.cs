using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using ProtocolloInsielMercatoService;

namespace VBG.Backend.Protocollo.AppLogic.Core.InsielMercato.Protocollazione
{
    public class ProtocollazionePartenza : IProtocollazioneInsielMercato
    {
        IDatiProtocollo _datiProto;

        public ProtocollazionePartenza(IDatiProtocollo datiProto)
        {
            _datiProto = datiProto;
        }

        public direction1 Flusso
        {
            get { return direction1.P; }
        }

        public sender[] GetMittenti()
        {
            return new sender[] { new sender { code = _datiProto.Uo } };
        }

        public recipient[] GetDestinatari()
        {
            var destinatariAnagrafe = _datiProto.AnagraficheProtocollo.Select(x => x.ToRecipientAnagrafe());

            var destinatariAmministrazioni = _datiProto.AmministrazioniEsterne.Select(x => x.ToRecipientAmministrazione());

            var destinatari = destinatariAnagrafe.Union(destinatariAmministrazioni);

            var dic = destinatari.GroupBy(x => x.description.ToUpperInvariant());
            var res = dic.Select(x => x.First()).ToArray();

            return res;
        }

        public document[] GetAllegati()
        {
            var allegati = _datiProto.ProtoIn.RecuperaAllegati().Select(x => new document
            {
                name = x.NOMEFILE,
                file = x.OGGETTO,
                primary = false,
                primarySpecified = true
            }).ToArray();

            if (allegati.Length > 0)
            {
                var firstElement = allegati.First();
                firstElement.primary = true;
                firstElement.primarySpecified = true;
            }

            return allegati; ;
        }

        public string Registro
        {
            get { return _datiProto.Uo; }
        }

        public string CodiceUfficioOperante
        {
            get { return _datiProto.Ruolo; }
        }

        public DateTime? DataSpedizione
        {
            get { return DateTime.MaxValue; }
        }
    }
}
