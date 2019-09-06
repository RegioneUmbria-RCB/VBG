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
    public class MittentiDestinatariInternoAdapter : ILeggiProtoMittentiDestinatari
    {
        MittDestType _mittenti;
        MittDestType _destinatari;
        InCaricoAAdapter.InCaricoADati _inCaricoADatiMittenti;
        InCaricoAAdapter.InCaricoADati _inCaricoADatiDestinatari;

        public MittentiDestinatariInternoAdapter(MittDestType mittenti, MittDestType destinatari)
        {
            _mittenti = mittenti;
            _destinatari = destinatari;

            var inCaricoAAdapterMittenti = new InCaricoAAdapter(mittenti);
            _inCaricoADatiMittenti = inCaricoAAdapterMittenti.Adatta();

            var inCaricoAAdapterDestinatari = new InCaricoAAdapter(destinatari);
            _inCaricoADatiDestinatari = inCaricoAAdapterDestinatari.Adatta();

        }

        public string InCaricoA
        {
            get { return _inCaricoADatiMittenti.Codice; ; }
        }

        public string InCaricoADescrizione
        {
            get { return _inCaricoADatiMittenti.Descrizione; }
        }

        public MittDestOut[] GetMittenteDestinatario()
        {
            return new MittDestOut[] 
            { 
                new MittDestOut 
                { 
                    CognomeNome = _inCaricoADatiDestinatari.Descrizione, 
                    IdSoggetto = _inCaricoADatiDestinatari.Codice 
                } 
            };
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_INTERNO; }
        }
    }
}
