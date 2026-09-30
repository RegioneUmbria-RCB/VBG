using VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.Protocollazione.Segnatura.Request;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.Protocollazione.Anagrafiche
{
    public interface IPersonaFisicaGiuridica
    {
        Firm GetFirm();
        EsibDest GetEsibDest();
    }
}
