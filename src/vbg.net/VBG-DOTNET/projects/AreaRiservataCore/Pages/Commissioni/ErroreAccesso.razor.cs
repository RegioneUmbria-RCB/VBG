namespace AreaRiservataCore.Pages.Commissioni
{
    public partial class ErroreAccesso
    {

        public async Task OnBtnCloseAsync()
        {
            //Navigation.NavigateTo($"/{this.IdComune}/{this.Software}");
            await GotoAsync("home");
        }
    }
}
