using VBG.Backend.Protocollo.AppLogic.Core.Tinn.Segnatura;
using VBG.Backend.Protocollo.AppLogic.Core.Tinn.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.Tinn.Protocollazione.MittentiDestinatari
{
    public class MittentiDestinatariArrivo : MittentiDestinatariBase, IMittentiDestinatariFlusso
    {

        public MittentiDestinatariArrivo(IDatiProtocollo datiProto, VerticalizzazioniConfiguration verticalizzazione) : base(datiProto, verticalizzazione)
        {
            
        }

        public Mittente[] GetMittenti()
        {
            var soggetti = DatiProto.AnagraficheProtocollo.Select(x => new Mittente
            {
                Items = new object[] { x.GetPersonaAnagrafica() }
            });

            var amministrazioni = DatiProto.AmministrazioniEsterne.Select(x => new Mittente
            {
                Items = new object[] { x.GetPersonaAmministrazione() }
            });

            return soggetti.Union(amministrazioni).ToArray();
        }

        public Destinatario[] GetDestinatari()
        {
            return new Destinatario[] { new Destinatario 
                                            { Items = new object[] { new Amministrazione 
                                                                        { 
                                                                            CodiceAmministrazione = Vert.CodiceAmministrazione, 
                                                                            ItemElementName = new ItemChoiceType[] { ItemChoiceType.UnitaOrganizzativa }, 
                                                                            Items = new object[] { new UnitaOrganizzativa { id = DatiProto.Uo } } 
                                                                        }, 
                                        new AOO { CodiceAOO = Vert.CodiceAoo } } } };
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_ARRIVO_DOCAREA; }
        }
    }
}
