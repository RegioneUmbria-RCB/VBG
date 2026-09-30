
namespace VBG.Backend.Protocollo.AppLogic.Shared.Enums
{
    public enum SourceFascicolazione { 
        NONFASCICOLARE = 0, 
        INSERIMENTO_NORMALE = 1, 
        ON_LINE = 2, 
        INSERIMENTO_RAPIDO = 4, 
        CONTR_RAMO_PADRE = 8, 
        FASC_IST_MOV_AUT_BO = 16 
    }
}
