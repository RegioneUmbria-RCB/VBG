using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.RedirectFineDomanda
{
    public class TestoBoxFineDomanda
    {
        public readonly string Titolo;
        public readonly string Messaggio;
        public readonly string TestoBottone;

        public TestoBoxFineDomanda(string titolo, string messaggio, string testoBottone)
        {
            this.Titolo = titolo;
            this.Messaggio = messaggio;
            this.TestoBottone = testoBottone;
        }
    }
}
