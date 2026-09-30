using PersonalLib2.Data;
using SIGePro.Data.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;


namespace VBG.Backend.Protocollo.AppLogic.Shared.Metadati
{
    public class AutorizzazioneMetadatiManager : MetadatiManagerBase<AutorizzazioniMetadati>, IMetadatiManager
    {
        public AutorizzazioneMetadatiManager(DataBase db) : base(db) { }

        protected override string ColumnName => "FKIDAUTORIZZAZIONE";

        protected override string TableName => "autorizzazioni_metadati";

        public bool IsProtocolloEsitato(string idComune, int fkIdAutorizzazione)
        {
            return GetValue(idComune, fkIdAutorizzazione, MetadatiConstants.PROTOCOLLO_ESITATO) == "1";
        }

        public void SetProtocolloEsitato(string idComune, int fkIdAutorizzazione, bool esitato = true)
        {
            SetValue(idComune, fkIdAutorizzazione, MetadatiConstants.PROTOCOLLO_ESITATO, esitato ? "1" : "0");
        }
    }
}
