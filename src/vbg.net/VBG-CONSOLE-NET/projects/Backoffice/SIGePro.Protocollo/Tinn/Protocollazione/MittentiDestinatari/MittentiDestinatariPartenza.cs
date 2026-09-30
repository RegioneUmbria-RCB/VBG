using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Tinn.Segnatura;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Tinn.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.Tinn.Protocollazione.MittentiDestinatari
{
    public class MittentiDestinatariPartenza : MittentiDestinatariBase, IMittentiDestinatariFlusso
    {

        public MittentiDestinatariPartenza(IDatiProtocollo datiProto, VerticalizzazioniConfiguration verticalizzazione)
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
            var soggetti = DatiProto.AnagraficheProtocollo.Select(x => new Destinatario
            {
                Items = new object[] { x.GetPersonaAnagrafica() }
            });

            var amministrazioni = DatiProto.AmministrazioniEsterne.Select(x => new Destinatario
            {
                Items = new object[] { x.GetPersonaAmministrazione() }
            });

            return soggetti.Union(amministrazioni).ToArray();
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_PARTENZA_DOCAREA; }
        }
    }
}
