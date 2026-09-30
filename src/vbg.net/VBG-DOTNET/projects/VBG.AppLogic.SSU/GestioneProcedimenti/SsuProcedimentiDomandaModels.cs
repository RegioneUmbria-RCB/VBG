using VBG.AppLogic.SSU.APICatalogoServizi.Client;

namespace VBG.AppLogic.SSU.GestioneProcedimenti
{
    public class SsuFattispecieSecondariaDomanda
    {
        public required int Id { get; set; }
        public required string Descrizione { get; set; }
        public required string DescrizioneEstesa { get; set; }
        public required bool Obbligatoria { get; set; }
        public required SsuProcedimentoBaseDomanda Procedimento { get; set; }

    }

    public class SsuFattispeciePrimariaDomanda
    {
        public required int Id { get; set; }
        public required string Descrizione { get; set; }
        public required string DescrizioneEstesa { get; set; }

        public required bool Obbligatoria { get; set; }
        public List<SsuFattispecieSecondariaDomanda> FattispecieSecondarie { get; set; } = new();
    }

    public class SsuProcedimentoBaseDomanda
    {
        public required int Id { get; set; }
        public required string Descrizione { get; set; }
        public required string DescrizioneEstesa { get; set; }
    }

    public class SsuProcedimentoDomanda : SsuProcedimentoBaseDomanda
    {
        public List<SsuFattispeciePrimariaDomanda> FattispeciePrimarie { get; set; } = new();

        internal SsuProcedimentoBaseDomanda ToProcedimentoBase() => this;
    }


    public class SsuProcedimentiDomanda
    {
        public List<SsuProcedimentoDomanda> Procedimenti { get; set; } = new();
    }

    public static class SsuProcedimentiDomandaExtensions
    {
        public static SsuProcedimentoDomanda ToProcedimentoDomanda(this Procedimento procedimento)
        {
            return new SsuProcedimentoDomanda
            {
                Id = procedimento.Id,
                Descrizione = procedimento.Descrizione,
                DescrizioneEstesa = procedimento.DescrizioneEstesa
            };
        }

        public static SsuProcedimentoBaseDomanda ToProcedimentoBaseDomanda(this Procedimento procedimento)
        {
            return new SsuProcedimentoBaseDomanda
            {
                Id = procedimento.Id,
                Descrizione = procedimento.Descrizione,
                DescrizioneEstesa = procedimento.DescrizioneEstesa
            };
        }
    }
}
