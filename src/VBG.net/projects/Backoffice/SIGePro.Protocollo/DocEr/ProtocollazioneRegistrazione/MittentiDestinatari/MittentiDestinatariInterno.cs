using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.DocEr.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione.MittentiDestinatari
{
    public class MittentiDestinatariInterno : IMittentiDestinatari
    {
        IDatiProtocollo _datiProto;
        VerticalizzazioniConfiguration _vert;

        public MittentiDestinatariInterno(IDatiProtocollo datiProto, VerticalizzazioniConfiguration vert)
        {
            _datiProto = datiProto;
            _vert = vert;
        }

        public MittDestType[] GetMittenti()
        {
            return new MittDestType[]
            { 
                new MittDestType
                {
                    Items = new object[]
                    { 
                        new AmministrazioneType
                        {                 
                            CodiceAmministrazione = new CodiceAmministrazioneType { Text = new string[] { _vert.CodiceAmministrazione } },
                            Items = new object[]{ new UnitaOrganizzativaType
                            { 
                                Identificativo = new IdentificativoType{ Text = new string[]{ _datiProto.Amministrazione.PROT_UO } }, 
                                Denominazione = new DenominazioneType{ Text = new string[] { _datiProto.Amministrazione.AMMINISTRAZIONE } } } 
                            }
                        },
                        new AOOType{ CodiceAOO = new CodiceAOOType{ Text = new string[]{ _vert.CodiceAoo } } }
                    }
                }
            };
        }

        public MittDestType[] GetDestinatari()
        {
            var amministrazioni = _datiProto.AmministrazioniInterne[0].ToMittDestTypeFromAmministrazione();
            return new MittDestType[]{ amministrazioni };
        }

        public FlussoType Flusso
        {
            get
            {
                return new FlussoType
                    {
                        Firma = Enumerators.TipiFirma.NF.ToString(),
                        TipoRichiesta = Enumerators.TipoFlusso.I.ToString(),
                        ForzaRegistrazione = 1
                    };
            }
        }


        public SmistamentoType GetSmistamento()
        {
            return new SmistamentoType
            {
                UnitaOrganizzativa = new UnitaOrganizzativaType[] 
                { 
                    new UnitaOrganizzativaType 
                    { 
                        Identificativo = new IdentificativoType 
                        { 
                            Text = new string[] { _vert.Uo } 
                        } 
                    } 
                }
            };
        }
    }
}
