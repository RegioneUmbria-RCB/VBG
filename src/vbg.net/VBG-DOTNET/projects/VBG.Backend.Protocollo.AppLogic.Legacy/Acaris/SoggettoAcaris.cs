using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris
{
    public class SoggettoAcaris
    {
        public string CodiceFiscale { get; private set; }
        public string PartitaIVA { get; private set; }
        public string Nominativo { get; private set; }

        internal static SoggettoAcaris FromAmministrazione(ProtocolloAmministrazioni amministrazione)
        {
            return new SoggettoAcaris
            {
                CodiceFiscale = null,
                PartitaIVA = amministrazione.PARTITAIVA,
                Nominativo = $"{amministrazione.AMMINISTRAZIONE}".Trim()
            };
        }

        internal static SoggettoAcaris FromAnagrafe(ProtocolloAnagrafe protocolloAnagrafe)
        {
            return new SoggettoAcaris
            {
                CodiceFiscale = protocolloAnagrafe.CODICEFISCALE,
                PartitaIVA = protocolloAnagrafe.PARTITAIVA,
                Nominativo = $"{protocolloAnagrafe.NOMINATIVO} {protocolloAnagrafe.NOME}".Trim()
            };
        }
    }
}
