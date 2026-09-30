using Microsoft.IdentityModel.Clients.ActiveDirectory;
using OAuth2Service.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace OAuth2Service.Controllers
{
    public class OAuth2Controller : ApiController
    {
        // GET: api/OAuth2
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }

        // GET: api/OAuth2/5
        public string Get(int id)
        {
            return "value";
        }

        // POST: api/OAuth2
        public OAuth2Response Post([FromBody] OAuth2Info request)
        {
            var authenticationContext = new AuthenticationContext(request.AuthContextURL);
            var credential = new ClientCredential(request.ClientID, request.Secret);
            AuthenticationResult result = authenticationContext.AcquireToken(request.ResourceUrlWs, credential);

            if (result == null)
            {
                throw new InvalidOperationException("Tentativo fallito di ottenere il token JWT");
            }

            return new OAuth2Response { Token = result.AccessToken };
        }

        // PUT: api/OAuth2/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE: api/OAuth2/5
        public void Delete(int id)
        {
        }
    }
}
