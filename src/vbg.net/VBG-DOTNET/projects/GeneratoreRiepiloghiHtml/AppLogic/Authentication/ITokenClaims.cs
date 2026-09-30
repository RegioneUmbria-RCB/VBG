using IBCSecurityV2;

namespace GeneratoreRiepiloghiHtml.AppLogic.Authorization
{
    public interface ITokenClaims
    {
        string Alias { get; }
        string IdComune { get; }
        ContestoType Contesto { get; }
        string Token { get; }
        string UserId { get; }
    }
}