
using Init.Sigepro.FrontEnd.AppLogic.Utils;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic
{
    /// <summary>
    /// Tipi di contesto utilizzati per la lettura dei filtri per la visura
    /// </summary>
    public enum TipoContestoVisuraEnum
    {
        FiltriVisura,
        ListaVisura,
        FiltriArchivio,
        ListaArchivio
    }

    public static class TestCompilation
    {
        public static void TestSymbols(IAreaRiservataRuntimeEnvironment runtimeEnvironment)
        {
            Console.WriteLine(runtimeEnvironment.EnvironmentType.ToString());
        }

    }
}
