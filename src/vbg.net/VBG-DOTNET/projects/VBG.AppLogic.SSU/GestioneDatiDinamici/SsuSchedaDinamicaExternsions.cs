using System.Text;
using VBG.AppLogic.SSU.APICatalogoServizi.Client;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Scripts;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli.Serializables;

namespace VBG.AppLogic.SSU.GestioneDatiDinamici
{
    internal static class SsuSchedaDinamicaExternsions
    {
        public static IStrutturaModelloDinamico ToStrutturaModelloDinamico(this SchedaDinamica scheda)
        {
            IDyn2Modello modello = new ModelloDinamicoDto
            {
                Id = scheda.Id,
                CodiceScheda = $"SCHEDA_{scheda.Id}",
                Descrizione = scheda.Titolo,
                Modellomultiplo = 0,
                FkD2bcId = "IS",    // Sempre contesto istanza
                FlgReadonlyWeb = 0,
                FlgStoricizza = 0
            };

            var testi = scheda.Struttura!.Righe
                .SelectMany(x => x.Colonne.Where(y => y.Testo is not null).Select(y => y.Testo))
                .Select(testo => new ModelloDinamicoTestoDto
                {
                    Id = testo!.Id,
                    IdNelModello = testo.Id,
                    IdTipoTesto = testo.TipoTesto switch
                    {
                        TipoTestoEnum.Titolo => "TI",
                        _ => "TE",
                    },
                    Testo = testo.Testo,
                }).ToArray();

            var campi = scheda.Struttura!
                .Righe
                .SelectMany(x => x.Colonne.Where(y => y.Campo is not null).Select(y => y.Campo))
                .ToArray();

            var campiDinamici = campi.Select(campo => new CampoDinamicoDto
            {
                Etichetta = campo!.Etichetta,
                Descrizione = campo.Note,
                FkD2bcId = "IS", // Sempre contesto istanza
                Id = campo.Id,
                Tipodato = campo.Tipo.ToString(),
                Nomecampo = campo.Nome,
                Obbligatorio = campo.Obbligatorio ? 1 : 0,
            }).ToArray();

            var proprietaCampi = campi.ToDictionary(
                campo => campo!.Id,
                campo => campo.Proprieta!.Select(prop => new CampoDinamicoProprietaDto
                {
                    FkD2cId = campo.Id,
                    Proprieta = prop.Chiave,
                    Valore = prop.Valore
                } as IDyn2ProprietaCampo).ToList() ?? new List<IDyn2ProprietaCampo>()
                );

            var scriptsModello = new Dictionary<TipoScriptEnum, IDyn2ScriptModello> {
                { TipoScriptEnum.Caricamento, new ModelloDinamicoScriptDto {Script = Encoding.UTF8.GetBytes(scheda.Struttura.Script?.Caricamento ?? "")} },
                { TipoScriptEnum.Modifica, new ModelloDinamicoScriptDto {Script = Encoding.UTF8.GetBytes(scheda.Struttura.Script?.Modifica ?? "")} },
                { TipoScriptEnum.Salvataggio, new ModelloDinamicoScriptDto {Script = Encoding.UTF8.GetBytes(scheda.Struttura.Script?.Salvataggio ?? "")} },
                { TipoScriptEnum.Funzioni, new ModelloDinamicoScriptDto {Script = Encoding.UTF8.GetBytes(scheda.Struttura.Script?.FunzioniCondivise ?? "")} },
             };

            var struttura = scheda.Struttura!.Righe
                .SelectMany(x => x.Colonne.Select(y => new ModelloDinamicoDettagliDto
                {
                    Id = y.Campo?.Id ?? y.Testo?.Id,
                    FkD2cId = y.Campo?.Id,
                    FkD2mdtId = y.Testo?.Id,
                    FlgMultiplo = x.Multipla.GetValueOrDefault(false) ? 1 : 0,
                    FlgSpezzaTabella = x.InterrompeTabella.GetValueOrDefault(false) ? 1 : 0,
                    Posorizzontale = y.PosizioneOrizzontale,
                    Posverticale = x.PosizioneVerticale
                } as IDyn2DettagliModello))
                .ToList();

            return new StrutturaModelloDinamico
            {
                Modello = modello,
                ListaCampiDinamici = (campiDinamici?.ToDictionary((CampoDinamicoDto x) => x.Id!.Value, (Func<CampoDinamicoDto, IDyn2Campo>)((CampoDinamicoDto x) => x)) ?? new Dictionary<int, IDyn2Campo>()),
                ListaTesti = (testi?.ToDictionary((ModelloDinamicoTestoDto x) => x.Id.Value, (Func<ModelloDinamicoTestoDto, IDyn2TestoModello>)((ModelloDinamicoTestoDto x) => x)) ?? new Dictionary<int, IDyn2TestoModello>()),
                ProprietaCampiDinamici = proprietaCampi,
                ScriptsCampiDinamici = new(),
                ScriptsModello = scriptsModello,
                Struttura = struttura
            };
        }
    }
}
