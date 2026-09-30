using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.ProtocollazioneRegistrazione;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.GestioneDocumentale.LeggiDocumento
{
    public class MittentiDestinatariPartenzaAdapter : ILeggiProtoMittentiDestinatari
    {
        MittDestType _mittenti;
        IEnumerable<MittDestType> _destinatari;
        InCaricoAAdapter.InCaricoADati _inCaricoADati;

        public MittentiDestinatariPartenzaAdapter(MittDestType mittenti, IEnumerable<MittDestType> destinatari)
        {
            _mittenti = mittenti;
            _destinatari = destinatari;

            var inCaricoAAdapter = new InCaricoAAdapter(mittenti);
            _inCaricoADati = inCaricoAAdapter.Adatta();
        }

        public string InCaricoA
        {
            get { return _inCaricoADati.Codice; }
        }

        public string InCaricoADescrizione
        {
            get { return _inCaricoADati.Descrizione; }
        }

        public MittDestOutType[] GetMittenteDestinatario()
        {
            if (_destinatari == null)
                return new MittDestOutType[] { new MittDestOutType { CognomeNome = "", IdSoggetto = "" } };

            return _destinatari.Select(x => x.ToMittenteDestinatario()).ToArray();
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_PARTENZA; }
        }
    }
}
