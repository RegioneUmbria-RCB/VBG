using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiDocumento
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

        public WsDataClass.MittDestOut[] GetMittenteDestinatario()
        {
            return _destinatari.Select(x => x.ToMittenteDestinatario()).ToArray();
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_PARTENZA; }
        }
    }
}
