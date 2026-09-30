using System;
using VBG.Backend.Protocollo.AppLogic.Legacy.GeProt.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.GeProt.Protocollazione.MittentiDestinatari
{
    public static class AmministrazioneExtensions
    {
        public static Denominazione ToDenominazione(this ProtocolloAmministrazioni amministrazione)
        {
            return new Denominazione { Text = new string[] { amministrazione.AMMINISTRAZIONE } };
        }

        public static Amministrazione ToAmministrazioneInterna(this ProtocolloAmministrazioni amministrazione, VerticalizzazioniConfiguration vert)
        {
            return new Amministrazione
            {
                Denominazione = new Denominazione { Text = new string[] { vert.DenominazioneAmministrazione } },
                CodiceAmministrazione = new CodiceAmministrazione { Text = new string[] { vert.CodiceAmministrazione } },
                Items = new object[]
                {
                    new UnitaOrganizzativa
                    {
                        Denominazione = new Denominazione { Text = new string[] { amministrazione.AMMINISTRAZIONE } },
                        Identificativo = new Identificativo { Text = new string[] { amministrazione.PROT_UO } },
                        Items = new object[]
                        {
                            new IndirizzoPostale
                            {
                                Items = new object[] { new Denominazione { Text = new string[] { amministrazione.INDIRIZZO } } }
                            },
                        }
                    }
                }
            };
        }

        public static Amministrazione ToAmministrazione(this ProtocolloAmministrazioni amministrazione)
        {
            return new Amministrazione
            {
                Denominazione = new Denominazione
                {
                    Text = new string[]
                    {
                        String.Format("{0}-{1}-{2}-{3}", amministrazione.AMMINISTRAZIONE, amministrazione.INDIRIZZO, amministrazione.CITTA, amministrazione.CAP)
                    }
                },
                Items = new object[]
                {
                    new IndirizzoPostale
                    {
                        Items = new object[] { new Denominazione { Text = new string[] { amministrazione.INDIRIZZO } } }
                    } ,
                    new Telefono{ Text = new string[] { amministrazione.TELEFONO1, amministrazione.TELEFONO2 } }
                }
            };
        }
    }
}
