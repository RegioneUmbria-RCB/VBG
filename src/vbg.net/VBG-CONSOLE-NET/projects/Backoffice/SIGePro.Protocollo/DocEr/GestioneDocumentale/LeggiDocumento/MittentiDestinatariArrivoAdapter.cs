using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiDocumento
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

        public MittDestOut[] GetMittenteDestinatario()
        {
            if (_mittenti == null)
                return new MittDestOut[] { new MittDestOut { CognomeNome = "", IdSoggetto = "" } };

            return _mittenti.Select(x => x.ToMittenteDestinatario()).ToArray();
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_ARRIVO; }
        }
    }
}
