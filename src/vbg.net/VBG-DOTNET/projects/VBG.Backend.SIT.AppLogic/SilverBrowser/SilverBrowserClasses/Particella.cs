namespace VBG.Backend.SIT.AppLogic.SilverBrowser.SilverBrowserClasses
{
    public class Particella
    {
        private string _foglio;
        private string _numero;

        public double centerX { get; set; }
        public double centerY { get; set; }
        public string tipo { get; set; }
        public string numero
        {
            get { return this._numero; }
            set
            {

                if (value == "00000")
                {
                    this._numero = "0";
                    return;
                }

                this._numero = value.TrimStart('0');
            }
        }
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
        public string sez { get; set; }
    }
}
