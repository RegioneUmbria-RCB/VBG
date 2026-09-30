
using System.Runtime.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga
{
    public enum TipoOperazione
    {
        [EnumMember(Value = "letturaFascicoloRequest")]
        LETTURA_FASCICOLO_REQUEST,

        [EnumMember(Value = "newFolderRequest")]
        NEW_FOLDER_REQUEST,

        [EnumMember(Value = "trovaDocFolderRequest")]
        TROVA_DOC_FOLDER_REQUEST,

        [EnumMember(Value = "LoginRequest")]
        LOGIN_REQUEST,

        [EnumMember(Value = "addUdRequest")]
        ADD_UD_REQUEST,

        [EnumMember(Value = "estraiAllegatiRequest")]
        ESTRAI_ALLEGATI_REQUEST,

        [EnumMember(Value = "EstraiAllegatoPrimarioRequest")]
        ESTRAI_ALLEGATO_PRIMARIO_REQUEST,

        [EnumMember(Value = "EstraiAllegatoSecondario")]
        ESTRAI_ALLEGATO_SECONDARIO,

        [EnumMember(Value = "letturaRequest")]
        LETTURA_REQUEST,

        [EnumMember(Value = "updateProtocolloRequest")]
        UPDATE_PROTOCOLLO_REQUEST
    }
}
