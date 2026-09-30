using VBG.Backend.Protocollo.AppLogic.Core.DocEr.ProtocollazioneRegistrazione;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.GestioneDocumentale.LeggiDocumento
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

        public MittDestOutType[] GetMittenteDestinatario()
        {
            return new MittDestOutType[] 
            { 
                new MittDestOutType 
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
