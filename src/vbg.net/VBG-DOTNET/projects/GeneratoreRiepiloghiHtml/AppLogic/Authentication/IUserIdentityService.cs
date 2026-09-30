namespace GeneratoreRiepiloghiHtml.AppLogic.Authorization
{
    public interface IUserIdentityService
    {
        ITokenClaims? GetUserClaims();
    }
}