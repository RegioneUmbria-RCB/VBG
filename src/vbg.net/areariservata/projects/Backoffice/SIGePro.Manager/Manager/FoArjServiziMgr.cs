using Init.SIGePro.Data;
using Init.SIGePro.Manager.DTO.Bookmarks;
using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Manager
{
    public class FoArjServiziMgr : BaseManager
    {
        string _idComune;
        ILog _log = LogManager.GetLogger(typeof(FoArjServiziMgr));

        public FoArjServiziMgr(DataBase db, string idComune)
            :base(db)
        {
            this._idComune = idComune;
        }

        public BookmarkInterventoDto GetBookmarkByName(string nomeLink)
        {
            var cls = (FoArjServizi)this.db.GetClass(new FoArjServizi
            {
                IdComune = this._idComune,
                UrlServizio = nomeLink
            });

            if (cls == null)
            {
                _log.ErrorFormat("Bookmark {0} non trovato", nomeLink);

                return null;
            }

            var alberoMgr = new AlberoProcMgr(this.db);

            var dataValida = alberoMgr.DataInterventoValida(this._idComune, cls.FkAlberoprocScId.Value);

            if (!dataValida)
            {
                _log.ErrorFormat("Il bookmark {0} non è attivo, verificare la data validità", nomeLink);

                return null;
            }

            try
            {
                var nlaDestinatario = (NlaServizi)this.db.GetClass(new NlaServizi
                {
                    IdComune = this._idComune,
                    Id = cls.FkNlaServiziId
                });

                var parametriNla = this.db.GetClassList(new NlaServiziAltriDati
                {
                    IdComune = this._idComune,
                    FkNlaServiziId = cls.FkNlaServiziId.Value
                }).ToList<NlaServiziAltriDati>();

                return new BookmarkInterventoDto
                {
                    IdComune = cls.IdComune,
                    Id = cls.Id.Value,
                    IdIntervento = cls.FkAlberoprocScId.Value,
                    Anonimo = cls.Anonimo != 0,
                    Url = cls.UrlServizio,
                    NodoDestinatario = new BookmarkInterventoDto.NodoDestinazioneDto
                    {
                        IdComune = nlaDestinatario.IdComune,
                        Id = nlaDestinatario.Id.Value,
                        Descrizione = nlaDestinatario.Descrizione,
                        IdNodo = nlaDestinatario.IdNodo,
                        IdEnte = nlaDestinatario.IdEnte,
                        IdSportello = nlaDestinatario.IdSportello,
                        Pec = nlaDestinatario.Pec,
                        Parametri = parametriNla.Select(x => new BookmarkInterventoDto.NodoDestinazioneParameteriDto
                        {
                            Nome = x.NomeParametro,
                            Valore = x.Valore
                        }).ToList()
                    }
                };
            }catch(Exception ex)
            {
                _log.ErrorFormat("Errore durante la lettura dei dati del bookmark {0}: {1}", nomeLink, ex.ToString());

                throw;
            }
        }
    }
}
