namespace AreaRiservataCore.Pages.Commissioni
{
    public class CommissioniBasePage : BasePage
    {
        protected async Task ErroreAccessoAsync()
        {
            await GotoAsync("commissionierroreaccesso");
        }
    }
}
