using ProtocolloInsielMercatoService2;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.InsielMercato2.Protocollazione
{
    public class ProtocollazioneArrivo : IProtocollazioneInsielMercato2
    {
        IDatiProtocollo _datiProto;

        public ProtocollazioneArrivo(IDatiProtocollo datiProto)
        {
            _datiProto = datiProto;
        }

        public direction1 Flusso
        {
            get { return direction1.A; }
        }

        public sender[] GetMittenti()
        {
            var mittentiAnagrafe = _datiProto.AnagraficheProtocollo.Select(x => x.ToSenderAnagrafe());
            var mittentiAmministrazioni = _datiProto.AmministrazioniEsterne.Select(x => x.ToSenderAmministrazione());

            var mittenti = mittentiAnagrafe.Union(mittentiAmministrazioni);

            var dic = mittenti.GroupBy(x => x.description.ToUpperInvariant());
            var res = dic.Select(x => x.First()).ToArray();

            return res;
        }

        public recipient[] GetDestinatari()
        {
            return null;
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

            return allegati;
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
            get { return (DateTime?)null; }
        }

        public previous[] GetProtocolliCollegati()
        {
            return null;
        }
    }
}
