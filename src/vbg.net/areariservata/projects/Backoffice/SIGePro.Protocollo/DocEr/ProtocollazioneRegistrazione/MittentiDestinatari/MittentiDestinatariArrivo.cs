using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.DocEr.Verticalizzazioni;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale;
using System.IO;

namespace Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione.MittentiDestinatari
{
    public class MittentiDestinatariArrivo : IMittentiDestinatari
    {
        IDatiProtocollo _datiProto;
        Enumerators.TipiFirma _firma = Enumerators.TipiFirma.NF;
        VerticalizzazioniConfiguration _vert;
        GestioneDocumentaleService _wrapperGestDoc;

        public MittentiDestinatariArrivo(IDatiProtocollo datiProto, VerticalizzazioniConfiguration vert, GestioneDocumentaleService wrapperGestDoc)
        {
            _datiProto = datiProto;
            _wrapperGestDoc = wrapperGestDoc;
            _vert = vert;
            var isFirmato = Path.GetExtension(_datiProto.ProtoIn.Allegati.FirstOrDefault().NOMEFILE.ToLower()) == ProtocolloConstants.P7M;
            _firma = isFirmato ? Enumerators.TipiFirma.FD : Enumerators.TipiFirma.FE;
        }

        public MittDestType[] GetMittenti()
        {
            var res = new List<MittDestType>();

            var amministrazioni = _datiProto.AmministrazioniEsterne.Select(x => x.ToMittDestTypeFromAmministrazione(_wrapperGestDoc, _vert));
            var anagrafiche = _datiProto.AnagraficheProtocollo.Select(x => x.ToMittDestTypeFromAnagrafica(_wrapperGestDoc, _vert));
            var mittenti = amministrazioni.Union(anagrafiche);

            return mittenti.ToArray();
        }

        public MittDestType[] GetDestinatari()
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
                            Items = new object[]
                            { 
                                new UnitaOrganizzativaType
                                { 
                                    Identificativo = new IdentificativoType{ Text = new string[]{ _datiProto.Amministrazione.PROT_UO } },
                                    Denominazione = new DenominazioneType{ Text = new string[] { _datiProto.Amministrazione.AMMINISTRAZIONE } }
                                }
                            }
                        }, 
                        new AOOType{ CodiceAOO = new CodiceAOOType{ Text = new string[]{ _vert.CodiceAoo } } }
                    }
                }
            };
        }

        public FlussoType Flusso
        {
            get
            {
                return new FlussoType
                {
                    Firma = _firma.ToString(),
                    TipoRichiesta = Enumerators.TipoFlusso.E.ToString(),
                    ForzaRegistrazione = _firma == Enumerators.TipiFirma.FD ? 0 : 1
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
