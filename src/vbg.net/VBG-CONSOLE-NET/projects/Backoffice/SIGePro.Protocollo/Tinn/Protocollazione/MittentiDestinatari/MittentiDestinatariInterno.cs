using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Tinn.Segnatura;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Tinn.Verticalizzazioni;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;

namespace Init.SIGePro.Protocollo.Tinn.Protocollazione.MittentiDestinatari
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
