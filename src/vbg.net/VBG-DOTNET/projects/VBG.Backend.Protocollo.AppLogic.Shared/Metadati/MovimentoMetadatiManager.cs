using PersonalLib2.Data;
using SIGePro.Data.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Metadati
{
    public class MovimentoMetadatiManager : MetadatiManagerBase<MovimentiMetadati>, IMetadatiManager
    {
        public MovimentoMetadatiManager(DataBase db) : base(db) { }

        protected override string ColumnName => "CODICEMOVIMENTO";

        protected override string TableName => "movimenti_metadati";

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
