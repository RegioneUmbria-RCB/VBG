using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.DocEr.Verticalizzazioni;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale;

namespace Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione.MittentiDestinatari
{
    public class MittentiDestinatariPartenza : IMittentiDestinatari
    {
        IDatiProtocollo _datiProto;
        Enumerators.TipiFirma _firma = Enumerators.TipiFirma.FD;
        VerticalizzazioniConfiguration _vert;
        GestioneDocumentaleService _wrapperGestDoc;

        public MittentiDestinatariPartenza(IDatiProtocollo datiProto, VerticalizzazioniConfiguration vert, GestioneDocumentaleService wrapperGestDoc)
        {
            _datiProto = datiProto;
            _vert = vert;
            _wrapperGestDoc = wrapperGestDoc;
            //var isFirmato = _datiProto.ProtoIn.Allegati.FirstOrDefault().Extension.ToLower() == ProtocolloConstants.P7M;
            //_firma = isFirmato ? Enumerators.TipiFirma.F : Enumerators.TipiFirma.FD;
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
            var res = new List<MittDestType>();

            var amministrazioni = _datiProto.AmministrazioniEsterne.Select(x => x.ToMittDestTypeFromAmministrazione(_wrapperGestDoc, _vert));
            var anagrafiche = _datiProto.AnagraficheProtocollo.Select(x => x.ToMittDestTypeFromAnagrafica(_wrapperGestDoc, _vert));
            var destinatari = amministrazioni.Union(anagrafiche);

            return destinatari.ToArray();

        }

        public FlussoType Flusso
        {
            get
            {
                return new FlussoType
                {
                    Firma = _firma.ToString(),
                    TipoRichiesta = Enumerators.TipoFlusso.U.ToString(),
                    ForzaRegistrazione = _firma == Enumerators.TipiFirma.FD ? 0 : 1
                };
            }
        }


        public SmistamentoType GetSmistamento()
        {
            return null;
        }
    }
}
