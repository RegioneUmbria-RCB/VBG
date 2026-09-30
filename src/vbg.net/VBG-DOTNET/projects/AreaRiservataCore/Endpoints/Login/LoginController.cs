using Microsoft.AspNetCore.Mvc;

namespace AreaRiservataCore.Endpoints.Login
{
    [Route("login")]
    public class LoginController : Controller
    {
        [HttpGet("{alias}/{software}")]
        public IActionResult Index(string alias, string software)
        {
            return this.Redirect($"~/{alias}/{software}");
        }
    }
}
