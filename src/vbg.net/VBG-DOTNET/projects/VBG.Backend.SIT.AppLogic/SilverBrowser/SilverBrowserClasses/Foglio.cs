namespace VBG.Backend.SIT.AppLogic.SilverBrowser.SilverBrowserClasses
{
    public class Foglio : Sezione
    {
        private string _foglio;
        public string foglio
        {
            get { return this._foglio; }
            set
            {

                if (value == "0000")
                {
                    this._foglio = "0";
                    return;
                }

                this._foglio = value.TrimStart('0');
            }
        }
    }
}
