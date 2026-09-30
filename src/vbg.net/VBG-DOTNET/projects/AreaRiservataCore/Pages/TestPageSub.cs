using Microsoft.AspNetCore.Components;

namespace AreaRiservataCore.Pages
{
    [Route("/{IdComune}/{Software}/TestPageSub")]
    public class TestPageSub : TestPage
    {
        protected override string GetText()
        {
            return "SubPage!" + this.IdComune + this.Software;
        }

    }
}
