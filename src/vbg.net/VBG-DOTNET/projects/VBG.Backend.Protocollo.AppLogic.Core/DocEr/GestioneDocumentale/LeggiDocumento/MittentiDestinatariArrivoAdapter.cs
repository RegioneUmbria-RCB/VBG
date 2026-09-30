using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Core.DocEr.ProtocollazioneRegistrazione;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.GestioneDocumentale.LeggiDocumento
{
    public class MittentiDestinatariArrivoAdapter : ILeggiProtoMittentiDestinatari
    {
        IEnumerable<MittDestType> _mittenti;
        MittDestType _destinatari;
        InCaricoAAdapter.InCaricoADati _inCaricoADati;

        public MittentiDestinatariArrivoAdapter(IEnumerable<MittDestType> mittenti, MittDestType destinatari)
        {
            _mittenti = mittenti;
            _destinatari = destinatari;

            var inCaricoAAdapter = new InCaricoAAdapter(destinatari);
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
            if (_mittenti == null)
                return new MittDestOutType[] { new MittDestOutType { CognomeNome = "", IdSoggetto = "" } };

            return _mittenti.Select(x => x.ToMittenteDestinatario()).ToArray();
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_ARRIVO; }
        }
    }
}
