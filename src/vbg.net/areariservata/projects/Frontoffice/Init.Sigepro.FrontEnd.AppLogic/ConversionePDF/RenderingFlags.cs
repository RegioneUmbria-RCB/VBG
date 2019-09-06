namespace Init.Sigepro.FrontEnd.AppLogic.ConversionePDF
{
    public class RenderingFlags
    {
        public static RenderingFlags Default = new RenderingFlags();

        public bool ConvertToPdfa { get; set; }
        public string RasterizeScript { get; set; }

        public RenderingFlags()
        {
            this.ConvertToPdfa = true;
            this.RasterizeScript = "rasterize.js";
        }
    }
}
