using VBG.Backend.Protocollo.AppLogic.Core.InsielMercato2.Services;
using Init.SIGePro.Data;
using VBG.Backend.Protocollo.AppLogic.Core.InsielMercato2.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using ProtocolloInsielMercatoService2;

namespace VBG.Backend.Protocollo.AppLogic.Core.InsielMercato2.Protocollazione
{
    public class ProtocollazionePartenza : IProtocollazioneInsielMercato2
    {
        IDatiProtocollo _datiProto;
        ProtocollazioneService _wrapper;
        VerticalizzazioniConfiguration _vert;

        public ProtocollazionePartenza(IDatiProtocollo datiProto, ProtocollazioneService wrapper, VerticalizzazioniConfiguration vert)
        {
            _datiProto = datiProto;
            _wrapper = wrapper;
            _vert = vert;
        }

        public direction1 Flusso
        {
            get { return direction1.P; }
        }

        public sender[] GetMittenti()
        {
            return null;
        }

        public recipient[] GetDestinatari()
        {
            var destinatariAnagrafe = _datiProto.AnagraficheProtocollo.Select(x => x.ToRecipientAnagrafe(_wrapper, _vert));
            var destinatariAmministrazioni = _datiProto.AmministrazioniEsterne.Select(x => x.ToRecipientAmministrazione(_wrapper, _vert));

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
