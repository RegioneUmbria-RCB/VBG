using PersonalLib2.Data;
using SIGePro.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Metadati
{
    public class IstanzaMetadatiManager : MetadatiManagerBase<IstanzeMetadati>, IMetadatiManager
    {
        public IstanzaMetadatiManager(DataBase db) : base(db) { }

        protected override string ColumnName => "CODICEISTANZA";

        protected override string TableName => "istanze_metadati";

        public bool IsProtocolloEsitato(string idComune, int codice)
        {
            return GetValue(idComune, codice, MetadatiConstants.PROTOCOLLO_ESITATO) == "1";
        }

        public void SetProtocolloEsitato(string idComune, int codice, bool esitato = true)
        {
            SetValue(idComune, codice, MetadatiConstants.PROTOCOLLO_ESITATO, esitato ? "1" : "0");
        }
    }
}
