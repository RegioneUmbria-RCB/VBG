using PersonalLib2.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Metadati
{
    public static class MetadatiManagerFactory
    {
        public static IMetadatiManager Create(AmbitoProtocollazioneEnum ambito, DataBase db)
        {
            switch (ambito)
            {
                case AmbitoProtocollazioneEnum.DA_ISTANZA:
                    return new IstanzaMetadatiManager(db);

                case AmbitoProtocollazioneEnum.DA_MOVIMENTO:
                    return new MovimentoMetadatiManager(db);

                case AmbitoProtocollazioneEnum.DA_AUTORIZZAZIONE:
                    return new AutorizzazioneMetadatiManager(db);
                
                default:
                    throw new NotSupportedException($"Ambito {ambito} non supportato");
            }
        }
    }
}
