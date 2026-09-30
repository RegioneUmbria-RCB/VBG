using VBG.Backend.Protocollo.AppLogic.Core.Tinn.Segnatura;
using VBG.Backend.Protocollo.AppLogic.Core.Tinn.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.Tinn.Protocollazione.MittentiDestinatari
{
    public class MittentiDestinatariInterno : MittentiDestinatariBase, IMittentiDestinatariFlusso
    {
        public MittentiDestinatariInterno(IDatiProtocollo datiProto, VerticalizzazioniConfiguration verticalizzazione)
            : base(datiProto, verticalizzazione)
        {

        }

        public Mittente[] GetMittenti()
        {
            return new Mittente[] { new Mittente 
                                            { Items = new object[] { new Amministrazione 
                                                                        { 
                                                                            CodiceAmministrazione = Vert.CodiceAmministrazione, 
                                                                            ItemElementName = new ItemChoiceType[] { ItemChoiceType.UnitaOrganizzativa }, 
                                                                            Items = new object[] { new UnitaOrganizzativa { id = DatiProto.Uo } } 
                                                                        }, 
                                        new AOO { CodiceAOO = Vert.CodiceAoo } } } };
        }

        public Destinatario[] GetDestinatari()
        {
            var destinatario = DatiProto.AmministrazioniProtocollo[0];

            return new Destinatario[] { new Destinatario 
                                            { Items = new object[] { new Amministrazione 
                                                                        { 
                                                                            CodiceAmministrazione = Vert.CodiceAmministrazione, 
                                                                            ItemElementName = new ItemChoiceType[] { ItemChoiceType.UnitaOrganizzativa }, 
                                                                            Items = new object[] { new UnitaOrganizzativa { id = destinatario.PROT_UO } } 
                                                                        }, 
                                        new AOO { CodiceAOO = Vert.CodiceAoo } } } };
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_INTERNO_DOCAREA; }
        }
    }
}
